- [1. Object](#1-object)
  - [1.1. equals()](#11-equals)
  - [1.2. toString()](#12-tostring)
  - [1.3. clone()](#13-clone)
- [2. Objects](#2-objects)

---

## 1. Object

1. 所有类直接或间接地继承Object类
    
    ![](../../../images/image_id=412982.jpg)

2. Object有5个虚方法，只要继承Object类就自动会获得这么5个虚方法:
   - `public int hashCode()`
   - `public boolean equals(Object obj)`
   - `public String toString()`
   - `protected Object clone()`
   - `protected void finalize()`: JDK9被弃用

### 1.1. equals()

```java
// 只是判断是不是同一个地址值
public boolean equals(Object obj) {
        return (this == obj);
}
```
IDEA自动重写

![](../../../images/image_id=412987.jpg)

### 1.2. toString()

![Alt text](../../../images/image-43.png)

### 1.3. clone()

浅克隆

## 2. Objects

![](../../../images/image_id=413221.jpg)

比较对象会null异常。

![](../../../images/image_id=413222.jpg)

![](../../../images/image_id=413224.jpg)

![](../../../images/image_id=413225.jpg)

![](../../../images/image_id=413226.jpg)