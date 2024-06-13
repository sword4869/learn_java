

![](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202406132147894.png)

1、对于常见的 HTTP 头部，通过**静态表和 Huffman 编码**的方式

2、针对不在静态表范围内的头部，还可以建立**动态表**

# 压缩后的header的二进制格式

![image-20240613213641596](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202406132136631.png)

# Huffman码表示

HTTP/2 根据出现频率将 ASCII 码编码为了 Huffman 编码表



![image-20240613213743860](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202406132137893.png)

# 动态表



动态表一式两份，客户端和服务器双方都会更新。

第一次头全发，之后再发一样内容的头（value也包括），就只发index