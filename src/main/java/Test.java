import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) throws IOException{
        List<String> lines = Files.readAllLines(Path.of("D:\\git\\learn_java\\src\\main\\java\\Test.java"));
        for(String line : lines){
            System.out.println(line);
        }

        // writeLines
        List<String> lines2 = new ArrayList<>();
        lines2.add("Hello");
        lines2.add("World");
        
        // 导入commons-io包dependency: org.apache.commons:commons-io:1.3.2
    }
}