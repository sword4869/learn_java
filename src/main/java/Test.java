import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args){
        List<File> files = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            File file = new File("file" + i + ".txt");
            files.add(file);
        }
        
        List<File> files2 = Stream.iterate(0, i -> i+1).limit(10).map(i -> new File("file" + i + ".txt")).collect(Collectors.toList());
    }
}