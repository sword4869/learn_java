- [1. tcp](#1-tcp)
- [2. 回写](#2-回写)
- [3. 线程](#3-线程)

---
## 1. tcp

单次
```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        // Server的Socket对象
        ServerSocket ss = new ServerSocket(10086);

        // 监听Client的连接，返回一个Socket对象
        Socket socket = ss.accept();

        // 从Client的Socket对象中获取输入流
        /*
         * 不能中文
         * InputStream is = socket.getInputStream();
         * int b;
         * while ((b = is.read()) != -1) {
         * System.out.println((char) b);
         * }
         */
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        int b;
        while ((b = br.read()) != -1) {
            System.out.print((char) b);
        }

        // 关闭Client的Socket对象、Server的Socket对象
        socket.close();
        ss.close();
    }
}



import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        // 1.创建Socket对象
        // 细节：在创建对象的同时会连接服务端。如果连接不上，代码会报错
        Socket socket = new Socket("127.0.0.1", 10086);
        System.out.println("服务器已连接成功");

        // 2.可以从连接通道中获取输出流
        OutputStream os = socket.getOutputStream();
        os.write("aaa".getBytes());

        // 3.释放资源：流可以不关闭，因为Socket关闭的时候，流也会关闭
        // os.close();
        socket.close();
    }
}
```
循环
```java

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 10086);

        // 流获取一次，重复使用
        Scanner sc = new Scanner(System.in, "GBK");
        OutputStream os = socket.getOutputStream();

        while (true) {
            System.out.println("请输入您要发送的信息");
            String str = sc.nextLine();
            if ("886".equals(str)) {
                os.close();
                break;
            }
            os.write(str.getBytes());
        }
        socket.close();
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(10086);

        Socket socket = ss.accept();

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        int b;
        while ((b = br.read()) != -1) {
            System.out.print((char) b);
        }

        socket.close();
        ss.close();
    }
}
```

## 2. 回写
结束标记

`socket.shutdownOutput();`

```java
/*
* 客户端：发送一条数据，接收服务端反馈的消息并打印
* 服务器：接收数据并打印，再给客户端反馈消息
*/
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 10001);

        String str = "Clinet: 见到你很高兴！";
        OutputStream os = socket.getOutputStream();
        os.write(str.getBytes());

        // 写出一个结束标记
        socket.shutdownOutput();

        // 接收服务端回写的数据
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        int b;
        while ((b = isr.read()) != -1) {
            System.out.print((char) b);
        }

        socket.close();
    }
}

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(10001);

        Socket socket = ss.accept();

        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        int b;
        // read方法会从连接通道中读取数据
        // 细节：需要有一个结束标记，此处的循环才会停止。否则，程序就会一直停在read方法这里，等待读取下面的数据
        while ((b = isr.read()) != -1) {
            System.out.print((char) b);
        }

        // 4.回写数据
        String str = "Server: 到底有多开心？";
        OutputStream os = socket.getOutputStream();
        os.write(str.getBytes());

        socket.close();
        ss.close();

    }
}
```

## 3. 线程

```java
// 客户端：将本地文件上传到服务器。接收服务器的反馈。
// 服务器：接收客户端上传的文件，上传完毕之后给出反馈。

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.UUID;

public class Server {
    public static void main(String[] args) throws IOException {
        
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                3, // 核心线程数量
                16, // 线程池总大小
                60, // 空闲时间
                TimeUnit.SECONDS, // 空闲时间（单位）
                new ArrayBlockingQueue<>(2), // 队列
                Executors.defaultThreadFactory(), // 线程工厂，让线程池如何创建线程对象
                new ThreadPoolExecutor.AbortPolicy()// 阻塞队列
        );

        ServerSocket serverSocket = new ServerSocket(10001);

        while (true) {
            Socket socket = serverSocket.accept();

            // 开启一条线程，一个用户就对应服务端的一条线程
            // new Thread(new MyRunnable(socket)).start();
            pool.submit(new MyRunnable(socket));
        }
    }
}

class MyRunnable implements Runnable {

    Socket socket;

    public MyRunnable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
            String name = UUID.randomUUID().toString().replace("-", "");
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(name + ".jpg"));
            int len;
            byte[] bytes = new byte[1024];
            while ((len = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
            bos.close();
            
            // 回写数据
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write("上传成功");
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 释放资源：检查null，避免空指针异常
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}




import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 10001);

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("a.jpg"));
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        byte[] bytes = new byte[1024];
        int len;
        while ((len = bis.read(bytes)) != -1) {
            bos.write(bytes, 0, len);
        }

        // 往服务器写出结束标记
        socket.shutdownOutput();

        // 接收服务器的回写数据
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line = br.readLine();
        System.out.println(line);

        socket.close();
    }
}
```