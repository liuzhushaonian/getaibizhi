这是一个爬虫，爬取爱壁纸的壁纸的爬虫。

爱壁纸接口分析：
采用http,请求方式基本为get，分别两个部分，第一部分是请求服务器，获取壁纸信息，第二部分从信息里获取壁纸id，根据id从文件服务器（这里应该是图片服务器）里获取图片并显示。

# 请求信息接口：

http://service.aibizhi.adesk.com/v1/wallpaper/category/4e4d610cdf714d2966000003/wallpaper


（这里仅仅是动漫类的接口，其他类别的接口不作分析，如果想知道，可以用Charles抓数据包然后获取其他类别）
后面接参数

?skip=N，

N为正整数，任意数即可，表示跳过前N张，一次最多取20张图片，图库里大概有90000+动漫类图片……沃日怂了

返回json数据，其中就有个id，用id即可使用图片接口

**注意：请求头出需要参数User-Agent，值为picasso,170,windows，这样才能获取到Windows上面的数据，否则将获取到其他数据。**

# 图片获取接口

http://img.aibizhi.adesk.com/id

后面id则是从信息处提取而来的。


# 使用方法（敲黑板，重点！！）

本项目

**并非**Android应用！
 
**并非**Android应用！

**并非**Android应用！

重要的事情说三遍。

本项目是一个Java写的爬虫，构建工具是maven，以及使用了Java8的lambda表达式，所以至少需要Java8环境。如果不了解这些，请进行一定的学习后再提问。

**真·使用方法：** 在启动GetImageInfo里的main方法，填写你想爬取壁纸的数量然后运行main方法即可！

注意，在[DownLoader](https://github.com/liuzhushaonian/getaibizhi/blob/d0a1f2f8136f5dcac008fc08f89009cf6f8de1e4/src/main/java/DownLoader.java)这个class中的download方法里有个存放图片的路径，需要手动改为你们自己电脑的想要存放的路径，**不能照搬使用我的**！

如果有疑问，可以直接在GitHub上留下issues,当然也可以给我邮件（如果你能找到我的邮箱的话），我看到了自然会回复

# 免责声明

由于子帅将本文弄到了微信公众号，我比较担心会<S>收到官方的律师函</S>被套麻袋打，所以以下的免责声明请一定仔细看，谢谢。

关于你们怎么用那是你们的事，出了事我概不负责。

对了，我删除了开源协议，表示此本代码不允许作为商用！

仅提供学习交流。



