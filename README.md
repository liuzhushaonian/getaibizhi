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


# 免责声明
关于你们怎么用那是你们的事，出了事我概不负责，谢谢。
