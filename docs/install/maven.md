- [install](#install)
- [idea](#idea)
- [排除某个包中的依赖 exclusion](#排除某个包中的依赖-exclusion)
- [maven常用命令](#maven常用命令)
- [maven生命周期](#maven生命周期)
- [maven冲突](#maven冲突)

---

## install

https://maven.apache.org/download.cgi

![alt text](../../images/image-22.png)

config中的setting.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.2.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.2.0 https://maven.apache.org/xsd/settings-1.2.0.xsd">
  <!-- 本地仓库 -->
  <localRepository>D:\Applications\apache-maven-3.9.6\repository</localRepository>
  <pluginGroups>
  </pluginGroups>
  <proxies>
  </proxies>
  <servers>
  </servers>
  <!-- 远程仓库 -->
  <mirrors>
    <mirror>
        <id>aliyunmaven</id>
        <mirrorOf>*</mirrorOf>
        <name>阿里云公共仓库</name>
        <url>https://maven.aliyun.com/repository/public</url>
    </mirror>
  </mirrors>
  <!-- 默认JDK -->
  <profiles>
    <profile>     
      <id>JDK-1.8</id>       
      <activation>       
          <activeByDefault>true</activeByDefault>       
          <jdk>1.8</jdk>       
      </activation>       
      <properties>       
          <maven.compiler.source>1.8</maven.compiler.source>       
          <maven.compiler.target>1.8</maven.compiler.target>       
          <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>       
      </properties>       
  </profile>
  </profiles>
</settings>
```
## idea

![alt text](../../images/image-118.png)


## 排除某个包中的依赖 exclusion

```xml
<dependency>
    <groupId>com.qcloud</groupId>
    <artifactId>vod_api</artifactId>
    <exclusions>
        <exclusion>
            <artifactId>slf4j-log4j12</artifactId>
            <groupId>org.slf4j</groupId>
        </exclusion>
    </exclusions>
</dependency>
```
`com.qcloud.vod_api` 包中就不依赖 `slf4j-log4j12.org.slf4j` 了。

## maven常用命令

![alt text](../../images/image-126.png)

## maven生命周期

![alt text](../../images/image-384.png)

![alt text](../../images/image-297.png)

执行install时会触发compile,test,package,install，那么会触发clean吗？不会。

## maven冲突

![alt text](../../images/image-127.png)


 tj-user tj-trade tj-search tj-pay tj-message tj-media tj-learning tj-gateway tj-auth  tj-exam tj-data tj-course      