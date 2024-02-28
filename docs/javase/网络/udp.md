- [1. socket](#1-socket)
- [2. DatagramSocket单播](#2-datagramsocket单播)
  - [2.1. 单次](#21-单次)
  - [2.2. 循环](#22-循环)
- [3. MulticastSocket组播](#3-multicastsocket组播)


---
## 1. socket

DatagramSocket
```java
/*
 * 【构造方法】
 * public DatagramSocket(int port) throws SocketException 分配一个端口并绑定
 * 
 * public DatagramSocket() throws SocketException 分配一个空闲的端口
 * 
 * public DatagramSocket(SocketAddress bindaddr) throws SocketException 绑定指定的端口
 * 
 * public DatagramSocket(int port, InetAddress laddr) throws SocketException
 * 分配一个端口并绑定到指定的地址
 * 
 * 【成员方法】
 * public void receive(DatagramPacket p) throws IOException {   接收数据包
 * 
 * public void send(DatagramPacket p) throws IOException {  发送数据包
 */
```
DatagramPacket
```java
/*
* public DatagramPacket(byte[] buf, int length, InetAddress address, int port) 构造数据报包，用来将长度为 length 的包发送到指定主机上的指定端口号。
* 
* 成员方法：
* public byte[] getData()：返回数据。
* public int getLength()：返回要发送的数据的长度或接收到的数据的长度。
* public InetAddress getAddress()：返回数据包发送方的 IP 地址。
* public int getPort()：返回数据包发送方的端口号。
*/
```
## 2. DatagramSocket单播
### 2.1. 单次
```java
// 接收数据
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ReceiveMessageDemo {
    public static void main(String[] args) throws IOException {
        // 1.创建DatagramSocket对象: 绑定接收端口
        DatagramSocket ds = new DatagramSocket(10086);

        // 2.接收数据包
        byte[] bytes = new byte[1024];
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length);
        // 该方法是阻塞的, 等发送端发送消息
        ds.receive(dp);

        // 3.解析数据包
        byte[] data = dp.getData();
        int len = dp.getLength();
        InetAddress address = dp.getAddress();
        int port = dp.getPort();

        System.out.println("接收到数据" + new String(data, 0, len));
        System.out.println("该数据是从" + address + "这台电脑中的" + port + "这个端口发出的");

        // 4.释放资源
        ds.close();

    }
}


// 发送数据
import java.io.IOException;
import java.net.*;

public class SendMessageDemo {
    public static void main(String[] args) throws IOException {
        // 1.创建DatagramSocket对象：通过这个端口往外发送
        // DatagramSocket() 或者 DatagramSocket(int port)
        DatagramSocket ds = new DatagramSocket();
        // DatagramPacket ds = new DatagramSocket(5555);

        // 2.打包数据：发送数据到接收端口（要和接收代码的端口一致）
        byte[] bytes = "你好威啊！！！".getBytes();
        InetAddress address = InetAddress.getByName("127.0.0.1");   // 发送到的地址
        int port = 10086;   // 发送到的端口号
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length, address, port);

        // 3.发送数据
        ds.send(dp);

        // 4.释放资源
        ds.close();
    }
}
```
先启动接收端，让它阻塞着，再启动发送端。

### 2.2. 循环
```java
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ReceiveMessageDemo {
    public static void main(String[] args) throws IOException {
        /*
         * 按照下面的要求实现程序
         * UDP发送数据：数据来自于键盘录入，直到输入的数据是886，发送数据结束
         * UDP接收数据：因为接收端不知道发送端什么时候停止发送，故采用死循环接收
         */

        // 1.创建对象DatagramSocket的对象
        DatagramSocket ds = new DatagramSocket(10086);

        // 2.接收数据包
        byte[] bytes = new byte[1024];
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length);

        while (true) {
            ds.receive(dp);

            // 3.解析数据包
            byte[] data = dp.getData();
            int len = dp.getLength();
            String ip = dp.getAddress().getHostAddress();
            String name = dp.getAddress().getHostName();

            // 4.打印数据
            System.out.println("ip为：" + ip + ",主机名为：" + name + "的人，发送了数据：" + new String(data, 0, len));
        }

    }
}

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class SendMessageDemo {
    public static void main(String[] args) throws IOException {
        /*
         * 按照下面的要求实现程序
         * UDP发送数据：数据来自于键盘录入，直到输入的数据是886，发送数据结束
         * UDP接收数据：因为接收端不知道发送端什么时候停止发送，故采用死循环接收
         */

        DatagramSocket ds = new DatagramSocket();

        // windows下的键盘录入问题
        Scanner sc = new Scanner(System.in, "GBK");
        while (true) {
            System.out.println("请输入您要说的话：");
            String str = sc.nextLine();
            if ("886".equals(str)) {
                break;
            }
            byte[] bytes = str.getBytes();
            InetAddress address = InetAddress.getByName("255.255.255.255");
            int port = 10086;
            DatagramPacket dp = new DatagramPacket(bytes, bytes.length, address, port);
            ds.send(dp);
        }
        ds.close();
    }
}
```


## 3. MulticastSocket组播

```java
/*
 * 方法同DatagramSocket
 * 
 * public void joinGroup(InetAddress mcastaddr) throws IOException {    添加组播地址
 */
```

```java
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ReceiveMessageDemo {
    public static void main(String[] args) throws IOException {
        MulticastSocket ms = new MulticastSocket(10086);
        // 添加224.0.0.1这一组的地址
        InetAddress address = InetAddress.getByName("224.0.0.1");
        ms.joinGroup(address);

        byte[] bytes = new byte[1024];
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length);
        
        while (true) {
            ms.receive(dp);
            
            byte[] data = dp.getData();
            int len = dp.getLength();
            String ip = dp.getAddress().getHostAddress();
            String name = dp.getAddress().getHostName();

            System.out.println("ip为：" + ip + ",主机名为：" + name + "的人，发送了数据：" + new String(data, 0, len));
        }
    }
}

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ReceiveMessageDemo2 {
    public static void main(String[] args) throws IOException {
        MulticastSocket ms = new MulticastSocket(10086);
        // 添加224.0.0.1这一组的地址
        InetAddress address = InetAddress.getByName("224.0.0.1");
        ms.joinGroup(address);

        byte[] bytes = new byte[1024];
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length);
        
        while (true) {
            ms.receive(dp);
            
            byte[] data = dp.getData();
            int len = dp.getLength();
            String ip = dp.getAddress().getHostAddress();
            String name = dp.getAddress().getHostName();

            System.out.println("ip为：" + ip + ",主机名为：" + name + "的人，发送了数据：" + new String(data, 0, len));
        }
    }
}

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class SendMessageDemo {
    public static void main(String[] args) throws IOException {
        MulticastSocket ms = new MulticastSocket();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请输入您要说的话：");
            String str = sc.nextLine();
            if ("886".equals(str)) {
                break;
            }
            byte[] bytes = str.getBytes();
            InetAddress address = InetAddress.getByName("224.0.0.1");
            int port = 10086;
            DatagramPacket dp = new DatagramPacket(bytes, bytes.length, address, port);
            ms.send(dp);
        }
        ms.close();
    }
}
```