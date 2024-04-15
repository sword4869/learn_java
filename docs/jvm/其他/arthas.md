

arthas: 可以线上实时查看
- dashboard: 内存、cpu
- dump: 保存class到别处 
- jad：反编译class到源码
- mc：热编译。

## 类加载器
```bash
$ classloader

# 并显示hash值
$ classloader -l

# 通过hash值获取指定加载器类加载的所有jar包的位置
$ classloader - c xxxxhash
```
```bash
[arthas@2712]$ classloader
 name                                       numberOfInstances  loadedCountTotal
 # 启动类加载器
 BootstrapClassLoader                       1                  2721
 # 自定义类加载器
 com.taobao.arthas.agent.ArthasClassloader  1                  1351
 # 扩展类加载器
 sun.misc.Launcher$ExtClassLoader           1                  66
 # 自定义类加载器
 sun.reflect.DelegatingClassLoader          15                 15
 # 应用程序类加载器
 sun.misc.Launcher$AppClassLoader           1                  7
```

## 在线项目某个文件的代码热部署

1. 在出问题的服务器上部署一个 arthas，并启动。
2. jad 命令反编译
   
   ```bash
   jad --source-only 类全限定名 > 输出位置.java
   ```
3. vim 来修改源码
4. 获取对应的类加载器的classLoaderHash
    
    ```bash
    sc -d 类全限定名
    ```
5. mc 命令用来编译修改过的代码
   
    ```bash
    # 不加`-c`就找不到项目的依赖类。
    mc –c 类加载器的classLoaderHash 输出位置.java -d 输出class位置.class
    ```
6. 用retransform 命令加载新的字节码到内存
   
    ```bash
    retransform 输出class位置.class
    ```

限制：
- retransform是加载到内存中，程序重启之后, 字节码文件会恢复。还是需要将class文件放入jar包中进行手动更新。
- 使用retransform不能添加方法或者字段，只能修改方法内部，也不能更新正在执行中的方法。
