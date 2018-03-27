import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.concurrent.TimeUnit;

public class NetUtil {

    private volatile static NetUtil util;

    public static NetUtil getUtil() {
        if (util == null) {
            synchronized (NetUtil.class) {
                util = new NetUtil();
            }
        }
        return util;
    }


    public String getJson(String doing) {

        String url = "http://service.aibizhi.adesk.com/v1/wallpaper/category/4e4d610cdf714d2966000003/wallpaper?" + doing;

        String json = "";

        try {

            Request.Builder builder1 = new Request.Builder().url(url)
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", "picasso,170,windows")
                    .method("GET", null);

            Request request = builder1.build();

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS);

            OkHttpClient okHttpClient = builder.build();

            Call call = okHttpClient.newCall(request);

            json = call.execute().body().string();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }
}
