```java
// 创建ProcessBuilder
List<String> cmds = Arrays.asList("D:\\EditPlus.exe", "-e", "720");


String outstring = null;
try{
    ProcessBuilder builder = new ProcessBuilder();
    builder.command(cmds);  // 命令参数
    builder.redirectErrorStream(true);  //将标准输入流和错误输入流合并，通过标准输入流程读取信息
    // 启动Process
    Process process = builder.start();
    outstring = waitFor(process);
}
catch (Exception e){
    e.printStackTrace();
}
```