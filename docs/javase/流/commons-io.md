```java
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class CommonsIODemo1 {
    public static void main(String[] args) throws IOException {
        /*
         * FileUtils类
         * static void copyFile(File srcFile, File destFile) 复制文件
         * static void copyDirectory(File srcDir, File destDir) 复制文件夹，destDir的内容就是srcDir的内容
         * static void copyDirectoryToDirectory(File srcDir, File destDir) 复制文件夹，destDir的有一个srcDir的文件夹
         * static void deleteDirectory(File directory) 删除文件夹
         * static void cleanDirectory(File directory) 清空文件夹
         * static String readFileToString(File file, Charset encoding) 读取文件中的数据变成成字符串
         * static void write(File file, CharSequence data, String encoding) 写出数据
         * 
         * IOUtils类
         * public static int copy(InputStream input, OutputStream output) 复制文件
         * public static int copyLarge(Reader input, Writer output) 复制大文件
         * public static List<String> readLines(final Reader reader) 读取数据
         * public static void writeLines(final Collection<?> lines, String lineEnding, final Writer writer) 写出数据
         */

        File src = new File("a.txt");
        File dest = new File("copy.txt");
        FileUtils.copyFile(src, dest);

        File src2 = new File("D:\\aaa");
        File dest2 = new File("D:\\bbb");
        FileUtils.copyDirectory(src2, dest2);   // bbb
        FileUtils.copyDirectoryToDirectory(src2, dest2);    // bbb/aaa

        File src3 = new File("D:\\bbb");
        FileUtils.cleanDirectory(src3);

        File src4 = new File("a.txt");
        FileReader fr = new FileReader(src4);
        List<String> lines = IOUtils.readLines(fr);
        fr.close();
        for (String line : lines) {
            System.out.println(line);
        }

        File dest5 = new File("writeLines.txt");
        FileWriter fw = new FileWriter(dest5);
        IOUtils.writeLines(lines, null, fw);
        fw.close();
    }
}
```