import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 下载器
 */
public class DownLoader {

    private ThreadPoolExecutor executor;
    private int CORE_COUNT = Runtime.getRuntime().availableProcessors();
    private int CORE_POOL_SIZE = CORE_COUNT + 1;
    private int CORE_POOL_MAX_SIZE = CORE_COUNT * 2 + 1;
    private int KEEP_ALIVE = 10;

    ThreadPoolExecutor poolExecutor;


    ExecutorService cachedThreadPool;
    private volatile static DownLoader downLoader;

    public static DownLoader getDownLoader() {
        if (downLoader == null) {
            synchronized (DownLoader.class) {
                downLoader = new DownLoader();
            }
        }
        return downLoader;
    }


    private DownLoader() {

//        cachedThreadPool = Executors.newCachedThreadPool();

        BlockingQueue<Runnable> runnableBlockingQueue = new LinkedBlockingQueue<>();
        poolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, CORE_POOL_MAX_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, runnableBlockingQueue);

    }

    public void startDownload(String id) {

        Runnable runnable = () -> {
            download(id);
        };

        poolExecutor.execute(runnable);
    }

    private void download(String id) {

        String url = "http://img.aibizhi.adesk.com/" + id;


        try {
            Request.Builder builder1 = new Request.Builder().url(url).method("GET", null);

            Request request = builder1.build();

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true);

            OkHttpClient okHttpClient = builder.build();

            Call call = okHttpClient.newCall(request);

            Response response = call.execute();

            String type = response.header("Content-Type");

            if (type != null && type.startsWith("image/")) {

                String path = "/Volumes/download/动漫壁纸/";

                String image_path = path + id + "." + type.substring(6, type.length());

                File file = new File(image_path);



                System.out.println("" + file.getName());

                FileOutputStream fileOutputStream = new FileOutputStream(file);

                FileChannel fileChannel = fileOutputStream.getChannel();

                ByteBuffer buffer = ByteBuffer.allocate(2048);


                byte[] bytes = response.body().bytes();

                for (byte aByte : bytes) {

                    if (buffer.hasRemaining()) {
                        buffer.put(aByte);
                    } else {
                        //如果buffer满了，则写入文件
                        buffer.flip();
                        fileChannel.write(buffer);
                        buffer.compact();
                        buffer.put(aByte);
                    }
                }

                //将剩余的也一并写入文件
                buffer.flip();
                fileChannel.write(buffer);
                buffer.clear();

                fileChannel.close();
                fileOutputStream.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
