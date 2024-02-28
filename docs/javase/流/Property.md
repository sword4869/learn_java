```java

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;

public class Client2 {
    public static void main(String[] args) {
        Properties p = new Properties();

        /*
         * load可以加载字节流和字符流。字符流可以解决中文乱码问题。
         * 
         * public synchronized void load(InputStream inStream) throws IOException {
         * 
         * public synchronized void load(Reader reader) throws IOException {
         */
        
        // try(FileInputStream fis = new FileInputStream("config.properties")) {
        // p.load(fis);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

        // 解决中文乱码
        try (FileReader fr = new FileReader("config.properties", Charset.forName("UTF-8"))) {
            p.load(fr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 判断属性是否存在
        System.out.println(p.containsKey("name"));

        // 获取属性
        String name = p.getProperty("name");
        int age = Integer.parseInt(p.getProperty("age"));
        System.out.println(name + " " + age);

        // 设置属性
        p.setProperty("name", "zhangsan");
        p.setProperty("age", "20");
        System.out.println(p.getProperty("name") + " " + p.getProperty("age"));

        // 保存属性
        try (FileOutputStream fos = new FileOutputStream("config.properties")) {
            p.store(fos, "update name and age");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```