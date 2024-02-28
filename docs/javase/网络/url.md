![Alt text](../../../images/image-9.png)

```java
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Web {
    public static void main(String[] args) {
        try {
            String s = webCrawler("http://www.baidu.com");
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            webDownload("https://dldir1.qq.com/qqfile/qq/TIM3.4.8/TIM3.4.8.22124.exe", new File("qq.exe"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // 拼接字符串
    public static String webCrawler(String net) throws IOException {
        //1.定义StringBuilder拼接爬取到的数据
        StringBuilder sb = new StringBuilder();
        //2.创建一个URL对象
        URL url = new URL(net);
        //3.链接上这个网址
        //细节：保证网络是畅通的，而且这个网址是可以链接上的。
        URLConnection conn = url.openConnection();
        //4.读取数据
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        int ch;
        while ((ch = br.read()) != -1){
            sb.append((char)ch);
        }
        br.close();
        return sb.toString();
    }

    // 下载字节文件
    public static void webDownload(String net, File out) throws IOException {
        URL url = new URL(net);
        URLConnection conn = url.openConnection();
        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(out));
        byte[] bytes = new byte[1024];
        int len;
        while((len = bis.read(bytes)) != -1){
            bos.write(bytes, 0, len);
            System.out.println(len);
        }
        bos.close();
        bis.close();
    }
}
```

```java
import cn.hutool.http.HttpUtil;

// 自动拼接字符串
String boyNameStr = HttpUtil.get("http://www.haoming8.cn/baobao/10881.html");
```