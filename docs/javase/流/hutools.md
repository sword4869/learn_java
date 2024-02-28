```java
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.io.FileUtil;

public class b {
    public static void main(String[] args) {
        /*
         * FileUtil类:
         * 
         * file: 特性在于：多参数路径拼接
         * touch：特性在于：多级目录不存在自动创建 
         * 
         * writeLines：把集合中的数据写出到文件中，覆盖模式。
         * appendLines：把集合中的数据写出到文件中，续写模式。
         * readLines：指定字符编码，把文件中的数据，读到集合中。
         * readUtf8Lines：按照UTF-8的形式，把文件中的数据，读到集合中
         * 
         * copy：拷贝文件或者文件夹
         */

        File file1 = FileUtil.file("D:\\", "aaa", "bbb", "a.txt");
        System.out.println(file1);// D:\aaa\bbb\a.txt

        File touch = FileUtil.touch(file1);
        System.out.println(touch);

        ArrayList<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("aaa");
        list.add("aaa");

        // hutool的相对路径不是相对于项目的根目录，而是class文件所在的目录
        File file2 = FileUtil.writeLines(list, "writeLines.txt", "UTF-8");
        System.out.println(file2);
        // C:\Users\lab\AppData\Roaming\Code\User\workspaceStorage\2fdd4064eada67bd93f926d5f645374b\redhat.java\jdt_ws\myio_25f7955b\bin\writeLines.txt

        File file3 = FileUtil.appendLines(list, "appendLines.txt", "UTF-8");
        System.out.println(file3);

        List<String> list2 = FileUtil.readLines("a.txt", "UTF-8");
        System.out.println(list2);
    }
}
```