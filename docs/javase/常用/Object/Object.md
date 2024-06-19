# Object的五个方法

1. 所有类直接或间接地继承Object类
   
    ![](../../../../images/image_id=412982.jpg)

2. Object有5个虚方法，只要继承Object类就自动会获得这么5个虚方法:
   - `public int hashCode()`
   - `public boolean equals(Object obj)`
   - `public String toString()`
   - `protected Object clone()`
   - `protected void finalize()`: JDK9被弃用

![](../../../../images/image_id=413197.jpg)



### 1.2. toString()

![Alt text](../../../../images/image-43.png)



