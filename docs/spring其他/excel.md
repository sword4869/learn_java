```xml
<poi-version>3.15</poi-version>

<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>${poi-version}</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-scratchpad</artifactId>
    <version>${poi-version}</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml-schemas</artifactId>
    <version>${poi-version}</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>${poi-version}</version>
</dependency>
```

HSSFWorkbook（97-2003版本，xls）、XSSFWorkbook（高于2003版本，xlsx）、Workbook（前两者都实现这个接口）

[Java读写Excel之HSSFWorkbook、XSSFWorkbook、Workbook-CSDN博客](https://blog.csdn.net/qq13933506749/article/details/121273180)

```java
/* 覆盖写：先判断空，则创建 */
// 行
XSSFRow row = sheet.getRow(rowIndex);
if (null == row) {
    row = sheet.createRow(rowIndex);
}
// 列
XSSFCell cell0 = row.getCell(0);
if (null == cell0) {
    cell0 = row.createCell(0);
}
cell0.setCellValue(j);
```

