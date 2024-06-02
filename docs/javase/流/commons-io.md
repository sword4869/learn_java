
```xml
<dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.4</version>
</dependency>
```
```java
/*
* FileUtils类
* static void copyFile(File srcFile, File destFile) 复制文件
* static void copyDirectory(File srcDir, File destDir) 复制文件夹，destDir的内容就是srcDir的内容
* static void copyDirectoryToDirectory(File srcDir, File destDir) 复制文件夹，destDir的有一个srcDir的文件夹
* static void deleteDirectory(File directory) 删除文件夹
* static void cleanDirectory(File directory) 清空文件夹
* static String readFileToString(File file, Charset encoding) 读取文件中的数据变成成字符串
* static void write(File file, CharSequence data, String encoding) 写出数据
*/

// 复制文件
File src = new File("a.txt");
File dest = new File("copy.txt");
FileUtils.copyFile(src, dest);

// 复制文件夹
File src2 = new File("D:\\aaa");
File dest2 = new File("D:\\bbb");
FileUtils.copyDirectory(src2, dest2);   // bbb
FileUtils.copyDirectoryToDirectory(src2, dest2);    // bbb/aaa

// 删除文件夹，清空文件夹
File src3 = new File("D:\\bbb");
FileUtils.cleanDirectory(src3);
// FileUtils.deleteDirectory(src3);
```
```java
/*
* IOUtils类
* public static int copy(InputStream input, OutputStream output) 复制字节流
* public static int copyLarge(Reader input, Writer output) 复制字符流
* public static List<String> readLines(final Reader reader) 读取多行
* public static void writeLines(final Collection<?> lines, String lineEnding, final Writer writer) 写出多行
*/

// 复制字节流
FileInputStream fis = new FileInputStream("a.txt");
FileInputStream fos = new FileInputStream("b.txt");
IOUtils.copy(inputStream, fileOutputStream);
fos.close();
fis.close();

// 读取多行
File src4 = new File("a.txt");
FileReader fr = new FileReader(src4);
List<String> lines = IOUtils.readLines(fr);
fr.close();
for (String line : lines) {
    System.out.println(line);
}

// 写出多行
List<String> lines = ...;
FileWriter fw = new FileWriter(new File("writeLines.txt"));
IOUtils.writeLines(lines, null, fw);
fw.close();
```