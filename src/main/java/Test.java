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
import java.util.stream.IntStream;
import java.util.stream.Stream;

import java.util.concurrent.CountDownLatch;

public class Test {
    public static void main(String[] args) {
        final Object lock = new Object();
        CountDownLatch latch = new CountDownLatch(1);
        char[] letters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        int[] numbers = IntStream.rangeClosed(1, 26).toArray();

        Thread printer1 = new Thread(() -> {
            synchronized (lock) {
                for (int number : numbers) {
                    System.out.print(number);
                    try {
                        latch.countDown(); // 通知printer2开始打印
                        lock.notify(); // 唤醒等待的线程
                        lock.wait(); // 当前线程等待
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                lock.notify(); // 最后一次唤醒，确保程序能够退出
            }
        }, "Printer1");

        Thread printer2 = new Thread(() -> {
            try {
                latch.await(); // 等待printer1打印第一个数字
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            synchronized (lock) {
                for (char letter : letters) {
                    System.out.print(letter);
                    try {
                        lock.notify(); // 唤醒等待的线程
                        lock.wait(); // 当前线程等待
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                lock.notify(); // 最后一次唤醒，确保程序能够退出
            }
        }, "Printer2");

        printer1.start();
        printer2.start();
    }
}
