import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Test {
    public static void main(String[] args) throws IOException {
        // 毫秒
        long timeMillis = System.currentTimeMillis();
        System.out.println(timeMillis);
        // 1713698294409


        // 秒
        LocalDateTime now = LocalDateTime.now();
        long epochSecond = now.toEpochSecond(ZoneOffset.UTC);
        System.out.println(epochSecond);
        // 1713727209
    }
}
