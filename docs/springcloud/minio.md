- [md5](#md5)
- [创建MinioClient对象](#创建minioclient对象)
- [minio上传文件](#minio上传文件)
- [minio删除文件](#minio删除文件)
- [minio检测文件是否存在](#minio检测文件是否存在)
- [minio获取文件](#minio获取文件)
- [minio合并分块](#minio合并分块)


---
![alt text](../../images/image-416.png)

```xml
<dependency>
    <groupId>io.minio</groupId>
    <artifactId>minio</artifactId>
    <version>8.4.3</version>
</dependency>
<!-- 根据文件后缀名获取 contentType -->
<dependency>
    <groupId>com.j256.simplemagic</groupId>
    <artifactId>simplemagic</artifactId>
    <version>1.17</version>
</dependency>
```

## md5
```java
//  spring-core中，无须特别依赖
String fileMD5 = DigestUtils.md5DigestAsHex(bytes);     // byte[]

String fileMD5 = DigestUtils.md5Hex(fileInputStream);     // FileInputStream
```
## 创建MinioClient对象
```java
package com.xuecheng.media;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.UploadObjectArgs;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SpringBootTest
public class MinIOTest {
    // 创建MinioClient对象
    static MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://127.0.0.1:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();
```
## minio上传文件
```java
    /**
     * 上传测试方法
     */
    @Test
    public void uploadTest() {
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket("testbucket")   // 桶名
                    .object("pic01.png")    // 要存储的文件名，放在根目录下。
                    // .object("pic/pic01.png")    // 要存储的文件名，放在pic文件夹下。文件夹没有会自动创建
                    .filename("C:\\Users\\15863\\Desktop\\Picture\\background\\01.png") // 本地路径
                    // .contentType(getContentType("pic01.png"))   // 一般不需要设置，只要文件名有后置，minio会自动识别  
                    .build();
            minioClient.uploadObject(uploadObjectArgs);
            System.out.println("上传成功");
        } catch (Exception e) {
            System.out.println("上传失败");
        }
    }

    /**
     * 根据objectName获取对应的MimeType
     *
     * @param objectName 对象名称
     * @return
     */
    private static String getContentType(String objectName) {
        String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE; // 默认content-type为未知二进制流
        if (objectName.contains(".")) { // 判断对象名是否包含 .
            // 有 .  则划分出扩展名
            String extension = objectName.substring(objectName.lastIndexOf("."));
            // 根据扩展名得到content-type，如果为未知扩展名，例如 .abc之类的东西，则会返回null
            ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(extension);
            // 如果得到了正常的content-type，则重新赋值，覆盖默认类型
            if (extensionMatch != null) {
                contentType = extensionMatch.getMimeType();
            }
        }
        return contentType;
    }
```
## minio删除文件
```java
    @Test
    public void deleteTest() {
        try {
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs
                    .builder()
                    .bucket("testbucket")
                    .object("pic01.png")
                    .build();
            minioClient.removeObject(removeObjectArgs);
            System.out.println("删除成功");
        } catch (Exception e) {
            System.out.println("删除失败");
        }
    }
```
## minio检测文件是否存在

```java
public boolean checkFileExistsTest() {
    try {
        GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                .bucket("testbucket")
                .object("pic01.png")
                .build();
        FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
        if(inputStream != null){
            return true;
        }
    } catch (Exception e) {
        System.out.println("下载失败");
    }
    return false;
}
```
## minio获取文件
```java
@Test
public void getFileTest() {
    try {
        GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                .bucket("testbucket")
                .object("pic01.png")
                .build();
        FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\15863\\Desktop\\tmp.png");
        IOUtils.copy(inputStream, fileOutputStream);
        System.out.println("下载成功");
    } catch (Exception e) {
        System.out.println("下载失败");
    }
}
```

## minio合并分块

```java
@Test
public void testMinioMerge() throws IOException {
    // 各分块
    List<ComposeSource> sources = Stream.iterate(0, i -> i + 1).limit(10)
            .map(i->ComposeSource.builder().bucket("testbucket").object("chunk/" + i).build())
            .collect(Collectors.toList());
    ComposeObjectArgs composeObjectArgs = ComposeObjectArgs.builder()
            .bucket("testbucket")
            .object("merger.mp4")   // 合并分块后的结果
            .sources(sources)               // 各分块
            .build();
    // 合并
    try {
        minioClient.composeObject(composeObjectArgs);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
```