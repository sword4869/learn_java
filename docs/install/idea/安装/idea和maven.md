## install

**Maven就用idea捆绑安装的 Bundled (Maven 3)**（也可以 [自行安装](https://maven.apache.org/download.cgi)）



![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407091645825.png)

Maven 推荐直接用idea捆绑安装的，仓储和配置文件可以单独配置（C盘爆炸，容量足够就不用管），具体见 setting->Build,Execution,Deployment -> Build Tools -> Maven 

User setting file 和 Local repository 选择Override即可自定义路径

## config中的setting.xml

### 自用

```xml
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.2.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.2.0 https://maven.apache.org/xsd/settings-1.2.0.xsd">
    
  <!-- 本地仓库：默认~/.m2/repository -->
  <localRepository>D:\Applications\apache-maven-3.9.6\repository</localRepository>
  
  <!-- 远程仓库: 阿里云 -->
  <mirrors>
    <mirror>
        <id>aliyunmaven</id>
        <mirrorOf>*</mirrorOf>
        <name>阿里云公共仓库</name>
        <url>https://maven.aliyun.com/repository/public</url>
    </mirror>
  </mirrors>
  
</settings>
```
### 公司代理

```xml
<!-- 公司服务器 -->
<servers>
 <server>
   <id>nexus-snapshots</id>
   <username>admin</username>
   <password>admin1234</password>
 </server>
 <server>
   <id>nexus-repository</id>
   <username>admin</username>
   <password>admin1234</password>
 </server>
</servers>

<!-- 公司仓库 -->
<mirrors>
<mirror>  
  <id>local</id>  
  <mirrorOf>*</mirrorOf>  
  <name>local maven</name>  
  <url>http://xx.:8081/repository/maven-public/</url>  
</mirror> 
</mirrors>
```

### 其他

```xml
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
```





## idea

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407091645826.png)


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

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407091645827.png)

## maven生命周期

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407091645828.png)

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407091645829.png)

执行install时会触发compile,test,package,install，那么会触发clean吗？不会。

## maven冲突

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407091645830.png)


 tj-user tj-trade tj-search tj-pay tj-message tj-media tj-learning tj-gateway tj-auth  tj-exam tj-data tj-course      