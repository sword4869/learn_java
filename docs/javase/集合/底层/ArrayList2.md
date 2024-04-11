
## ArrayList底层实现

ArrayList底层是动态数组。

数组名字：elementDate，定义变量size。

## 空参构造

- 在底层创建一个默认长度为0的数组
- 添加第一个元素时，底层会创建- -个新的长度为10的数组
- 存满时，会扩容1.5倍
- 如果一次添加多个元素，1.5倍还放不下，则新创建数组的长度以实际为准


![Alt text](../../../../images/image-18.png)

![Alt text](../../../../images/image-19.png)

![Alt text](../../../../images/image-20.png)

![Alt text](../../../../images/image-21.png)
