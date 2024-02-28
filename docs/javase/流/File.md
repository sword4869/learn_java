- [1. 创建File文件对象](#1-创建file文件对象)
- [2. 方法](#2-方法)
  - [2.1. 获取功能的方法](#21-获取功能的方法)
  - [2.2. 判断功能的方法](#22-判断功能的方法)
  - [2.3. 创建删除功能的方法](#23-创建删除功能的方法)
  - [2.4. 遍历](#24-遍历)
- [3. demo](#3-demo)
  - [3.1. 找到电脑中所有以avi结尾的电影](#31-找到电脑中所有以avi结尾的电影)
  - [3.2. 删除一个多级文件夹](#32-删除一个多级文件夹)
  - [3.3. 统计一个文件夹的总大小](#33-统计一个文件夹的总大小)
  - [3.4. 统计一个文件夹中每种文件的个数](#34-统计一个文件夹中每种文件的个数)


---

## 1. 创建File文件对象

- `public File(String pathname) ` ：根据文件路径 String 创建文件对象 File  
- `public File(String parent, String child) ` ：父路径名字符串和子路径名字符串，而创建文件对象
- `public File(File parent, String child)` ：根据父路径对应文件对象 File 和子路径名字符串 String 创建文件对象 
```java
// 1. public File(String pathname)
//  根据文件路径 String 创建文件对象 File
String str = "C:\\Users\\alienware\\Desktop\\a.txt";
File f1 = new File(str);
System.out.println(f1); // C:\Users\alienware\Desktop\a.txt


// 2. public File(String parent, String child)
// 父路径名字符串和子路径名字符串，而创建文件对象
// 可以根据windows或者 linux 而自动选择对应的路径分隔符。
String parent = "C:\\Users\\alienware\\Desktop";
String child = "a.txt";
File f2 = new File(parent, child);
System.out.println(f2); // C:\Users\alienware\Desktop\a.txt

// 其实就是拼接字符串，所以我们可以手动拼接字符串。对于windows，\\ 转移字符 和 / 都行
File f21 = new File("C:\\Users\\alienware", "Desktop\\a.txt");
System.out.println(f21); // C:\Users\alienware\Desktop\a.txt

File f22 = new File(parent + "\\" + child);
System.out.println(f22); // C:\Users\alienware\Desktop\a.txt

File f23 = new File(parent + "/" + child);
System.out.println(f23); // C:\Users\alienware\Desktop\a.txt


// 3. public File(File parent, String child) 
// 根据父路径对应文件对象 File 和子路径名字符串 String 创建文件对象
File parent2 = new File("C:\\Users\\alienware\\Desktop");
String child2 = "a.txt";
File f3 = new File(parent2, child2);
System.out.println(f3); // C:\Users\alienware\Desktop\a.txt
```

```java
// 并不是想象中的 "aaa"
File f = new File("", "aaa");
System.out.println(f.toString());  // \aaa
System.out.println(f.getAbsolutePath());    // D:\aaa
```

## 2. 方法
### 2.1. 获取功能的方法

- `public String getAbsolutePath() ` ：返回此File的绝对路径名字符串。

- ` public String getPath() ` ：返回定义文件时使用的构造路径

- `public String getName()`  ：返回由此File表示的文件（带后缀）或目录的名称。  

- `public long length()`  ：返回文件的大小（字节数量）

- `public long lastModified()`：返回文件的最后修改时间（时间毫秒值）

```java
// 1.length 返回文件的大小（字节数量）
// 细节1：如果文件或 文件夹不存在，则返回0
// 细节2：无法获取文件夹的大小（都是4096）

File f1 = new File("D:\\aaa\\a.txt");
long len = f1.length();
System.out.println(len);// 12

File f11 = new File("D:\\aaa\\b.txt");
long len11 = f11.length();
System.out.println(len11);// 0

File f2 = new File("D:\\aaa\\bbb");
long len2 = f2.length();
System.out.println(len2);// 0

File f22 = new File("D:\\aaa");
long len22 = f22.length();
System.out.println(len22);// 4096

System.out.println("====================================");

// 2.getAbsolutePath 返回文件的绝对路径
File f3 = new File("D:\\aaa\\a.txt");
String path1 = f3.getAbsolutePath();
System.out.println(path1);

File f4 = new File("myFile\\a.txt");
String path2 = f4.getAbsolutePath();
System.out.println(path2);

System.out.println("====================================");

// 3.getPath 返回定义文件时使用的构造路径
File f5 = new File("D:\\aaa\\a.txt");
String path3 = f5.getPath();
System.out.println(path3);// D:\aaa\a.txt

File f6 = new File("myFile\\a.txt");
String path4 = f6.getPath();
System.out.println(path4);// myFile\a.txt

System.out.println("====================================");

// 4.getName 获取名字
File f7 = new File("D:\\aaa\\a.txt");
String name1 = f7.getName();
System.out.println(name1);

File f8 = new File("D:\\aaa\\bbb");
String name2 = f8.getName();
System.out.println(name2);// bbb

System.out.println("====================================");

// 5.lastModified 返回文件的最后修改时间（时间毫秒值）
File f9 = new File("D:\\aaa\\a.txt");
long time = f9.lastModified();
System.out.println(time);// 1667380952425
```
### 2.2. 判断功能的方法

- `public boolean exists()` ：此File表示的文件或目录是否实际存在。
- `public boolean isDirectory()` ：此File表示的是否为目录。
- `public boolean isFile()` ：此File表示的是否为文件。

```java
// 1.对一个文件的路径进行判断
File f1 = new File("D:\\aaa\\a.txt");
System.out.println(f1.isDirectory());// false
System.out.println(f1.isFile());// true
System.out.println(f1.exists());// true
System.out.println("--------------------------------------");
// 2.对一个文件夹的路径进行判断
File f2 = new File("D:\\aaa\\bbb");
System.out.println(f2.isDirectory());// true
System.out.println(f2.isFile());// false
System.out.println(f2.exists());// true
System.out.println("--------------------------------------");
// 3.对一个不存在的路径进行判断
File f3 = new File("D:\\aaa\\c.txt");
System.out.println(f3.isDirectory());// false
System.out.println(f3.isFile());// false
System.out.println(f3.exists());// false
```
### 2.3. 创建删除功能的方法

- `public boolean createNewFile()` ：当且仅当具有该名称的文件尚不存在时，创建一个新的空文件。 
- `public boolean mkdir()` ：创建由此File表示的目录。
- `public boolean mkdirs()` ：创建由此File表示的目录，包括任何必需但不存在的父目录。
- `public boolean delete()` ：删除由此File表示的文件或目录。  

```java
// 1.createNewFile 创建一个新的空的文件
// 细节1：不存在的则创建成功，方法返回true；存在的则创建失败，方法返回false
// 细节2：如果父级路径是不存在的，那么方法会有异常IOException
File f1 = new File("D:\\aaa\\ddd");
boolean b;
try {
    b = f1.createNewFile();
    System.out.println(b);// true
} catch (IOException e) {
    e.printStackTrace();
}

// 2.mkdir make Directory，文件夹（目录）
// 细节1：不存在的则创建成功，方法返回true；存在的则创建失败，方法返回false
// 细节2：mkdir无法创建多级文件夹，方法返回false
File f2 = new File("D:\\aaa\\aaa\\bbb\\ccc");
boolean b2 = f2.mkdir();
System.out.println(b2);

// 3.mkdirs 创建多级文件夹
// 不存在的则创建成功，方法返回true；存在的则创建失败，方法返回false
// 细节：既可以创建单级的（底层就是调用 mkdir），又可以创建多级的文件夹
File f3 = new File("D:\\aaa\\ggg");
boolean b3 = f3.mkdirs();
System.out.println(b3); // true

// 4. delete 删除文件、空文件夹
// 如果删除的是文件，则直接删除
// 如果删除的是空文件夹，则直接删除
// 如果删除的是有内容的文件夹，则删除失败
// 细节：不走回收站。
File f4 = new File("D:\\aaa\\ddd");
boolean b4 = f4.delete();
System.out.println(b4);
```

### 2.4. 遍历

```java
/*
* public static File[] listRoots() 列出可用的文件系统根
* 
* public String[] list() 获取路径下所有内容(仅仅能获取名字)
* public String[] list(FilenameFilter filter) 利用文件名过滤器获取路径下所有内容。分为 dir 和 name 两部分
* 
* public File[] listFiles() 获取路径下所有内容，并封装成File对象，存储到File数组中
* public File[] listFiles(FilenameFilter filter) 分为 dir 和 name 两部分
* public File[] listFiles(FileFilter filter) 完整路径
*/

// 1.listRoots 获取系统中所有的盘符
File[] arr = File.listRoots();
System.out.println(Arrays.toString(arr));   // [C:\, D:\, E:\, F:\]


// 2.list() 获取路径下所有内容(仅仅能获取名字)
File f1 = new File("D:\\aaa");
String[] arr2 = f1.list();
for (String s : arr2) {
    System.out.println(s);  // 1.txt 2.txt subdir1 subdir2
}

// (1) list(FilenameFilter filter) 利用文件名过滤器获取路径下所有内容
File f2 = new File("D:\\aaa");
String[] arr3 = f2.list(new FilenameFilter() {
    // 需求：我现在要获取D：\\aaa文件夹里面所有的txt文件
    @Override
    // accept方法的形参，依次表示aaa文件夹里面每一个文件或者文件夹的路径
    // 参数一：父级路径
    // 参数二：子级路径
    // 返回值：如果返回值为true，就表示路径保留
    // 如果返回值为false，就表示路径舍弃不要
    public boolean accept(File dir, String name) {
        File src = new File(dir, name);
        return src.isFile() && name.endsWith(".txt");
    }
});
System.out.println(Arrays.toString(arr3));  // [1.txt, 2.txt]


// 3. listFiles方法
// 作用：获取aaa文件夹里面的所有内容，并封装成File对象，存储到File数组中
// ● 路径是文件、路径不存在、路径是需要权限才能访问的文件夹时， 返回null
// ● 路径是空文件夹时, 返回一个长度为0的数组
File f = new File("D:\\aaa");
File[] files = f.listFiles();
for (File file : files) {
    System.out.println(file);   // D:\aaa\1.txt D:\aaa\2.txt D:\aaa\subdir1 D:\aaa\subdir2
}

// (1) 需求：我现在要获取D：\\aaa文件夹里面所有的txt文件
for (File file : arr) {
    if(file.isFile() && file.getName().endsWith(".txt")){
        System.out.println(file);   // D:\aaa\1.txt D:\aaa\2.txt
    }
}

// (2) 调用listFiles(FilenameFilter filter)
File[] arr4 = f.listFiles(new FilenameFilter() {
    @Override
    public boolean accept(File dir, String name) {
        File src = new File(dir, name);
        return src.isFile() && name.endsWith(".txt");
    }
});
System.out.println(Arrays.toString(arr4));  // [D:\aaa\1.txt, D:\aaa\2.txt]

// (3) 调用listFiles(FileFilter filter)
File[] arr5 = f.listFiles(new FileFilter() {
    @Override
    public boolean accept(File pathname) {
        return pathname.isFile() && pathname.getName().endsWith(".txt");
    }
});
System.out.println(Arrays.toString(arr5));  // [D:\aaa\1.txt, D:\aaa\2.txt]
}
```

## 3. demo

### 3.1. 找到电脑中所有以avi结尾的电影

```java
import java.io.File;

public class Test3 {
    public static void main(String[] args) {
        /*
         * 需求：
         * 找到电脑中所有以avi结尾的电影。（需要考虑子文件夹）
         */

        findAVI();
    }

    public static void findAVI() {
        // 获取本地所有的盘符
        File[] arr = File.listRoots();
        for (File f : arr) {
            findAVI(f);
        }
    }

    public static void findAVI(File src) {
        File[] files = src.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    // 如果是文件，就可以执行题目的业务逻辑
                    String name = file.getName();
                    if (name.endsWith(".avi")) {
                        System.out.println(file);
                    }
                } else {
                    // 如果是文件夹，就可以递归
                    findAVI(file);
                }
            }
        }
    }
}
```

### 3.2. 删除一个多级文件夹

```java
import java.io.File;

public class Test4 {
    public static void main(String[] args) {
        /*
         * 删除一个多级文件夹
         */

        File file = new File("D:\\aaa\\src");
        delete(file);

    }

    /*
     * 作用：删除src文件夹
     * 参数：要删除的文件夹
     * 
     * 如果我们要删除一个有内容的文件夹
     * 1.先删除文件夹里面所有的内容
     * 2.再删除自己
     */
    public static void delete(File src) {
        // 1.先删除文件夹里面所有的内容
        // 进入src
        File[] files = src.listFiles();
        // 遍历
        for (File file : files) {
            // 判断,如果是文件，删除
            if (file.isFile()) {
                file.delete();
            } else {
                // 判断,如果是文件夹，就递归
                delete(file);
            }
        }
        // 2.再删除自己
        src.delete();
    }
}
```

### 3.3. 统计一个文件夹的总大小
```java
import java.io.File;

public class Test5 {
    public static void main(String[] args) {
        /*
         * 需求：统计一个文件夹的总大小
         */

        File file = new File("D:\\aaa\\src");

        long len = getLen(file);
        System.out.println(len);// 4919189
    }

    /*
     * 作用：统计一个文件夹的总大小
     * 参数：表示要统计的那个文件夹
     * 返回值：统计之后的结果
     *
     * 文件夹的总大小：说白了，文件夹里面所有文件的大小
     */
    public static long getLen(File src) {
        long len = 0;
        File[] files = src.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                // 我们就把当前文件的大小累加到len当中
                len = len + file.length();
            } else {
                // 判断，如果是文件夹就递归
                len = len + getLen(file);
            }
        }
        return len;
    }
}
```

### 3.4. 统计一个文件夹中每种文件的个数

```java
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Test6 {
    public static void main(String[] args) throws IOException {
        /*
         * 需求：统计一个文件夹中每种文件的个数并打印。（考虑子文件夹）
         * 打印格式如下：txt:3个, doc:4个, jpg:6个
         */

        File file = new File("D:\\aaa\\src");
        HashMap<String, Integer> hm = getCount(file);
        System.out.println(hm);
    }

    /*
     * 作用：统计一个文件夹中每种文件的个数
     * 参数：要统计的那个文件夹
     * 返回值：用来统计map集合。键：后缀名 值：次数
     *
     * a.txt
     * a.a.txt
     * aaa（不需要统计的）
     */
    public static HashMap<String, Integer> getCount(File src) {
        HashMap<String, Integer> hm = new HashMap<>();
        File[] files = src.listFiles();
        for (File file : files) {
            // 判断，如果是文件，统计
            if (file.isFile()) {
                // a.txt
                String name = file.getName();
                String[] arr = name.split("\\.");
                if (arr.length >= 2) {
                    String endName = arr[arr.length - 1];
                    if (hm.containsKey(endName)) {
                        // 存在
                        int count = hm.get(endName);
                        count++;
                        hm.put(endName, count);
                    } else {
                        // 不存在
                        hm.put(endName, 1);
                    }
                }
            } else {
                // 5.判断，如果是文件夹，递归
                HashMap<String, Integer> sonMap = getCount(file);
                // hm: txt=1 jpg=2 doc=3
                // sonMap: txt=3 jpg=1
                // 遍历, 把sonMap里面的值累加到hm当中
                Set<Map.Entry<String, Integer>> entries = sonMap.entrySet();
                for (Map.Entry<String, Integer> entry : entries) {
                    String key = entry.getKey();
                    int value = entry.getValue();
                    if (hm.containsKey(key)) {
                        // 存在
                        int count = hm.get(key);
                        count = count + value;
                        hm.put(key, count);
                    } else {
                        // 不存在
                        hm.put(key, value);
                    }
                }
            }
        }
        return hm;
    }
}
```