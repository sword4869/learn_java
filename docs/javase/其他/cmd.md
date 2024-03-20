```java
// 创建ProcessBuilder
ProcessBuilder builder = new ProcessBuilder();
List<String> cmds = Arrays.asList("D:\\EditPlus.exe", "-e", "720");
builder.command(cmds);  // 命令参数
builder.redirectErrorStream(true);  //将标准输入流和错误输入流合并，通过标准输入流程读取信息
// 启动Process
Process process = builder.start();
```