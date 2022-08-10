package org.joe.ioutils;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * @author joe
 * @date 2022/8/10 22:00
 */
public class FileUtilsTest {
    @Test
    void test_list(){
        List<File> list = FileUtils.list(new File("E:\\Tools\\SSR"), true);
        System.out.println(list.size());
        for (File file : list){
            System.out.println(file);
        }
    }

    @Test
    void test_copy(){
        try {
            FileUtils.copyRecursion(new File("D:\\Temp"),new File("D:\\TMP\\ccc"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test_read() throws IOException {
        File f = new File("D:\\Temp\\test.conf");
        //将文件按行读取
        List<String> strings = Files.readAllLines(f.toPath());
        //strings.forEach(System.out::println);
//        Files.lines(f.toPath()).forEach(s -> {
//            if (s.contains("input")){
//                System.out.println(s);
//            }
//        });
        Files.write(f.toPath(),strings, StandardOpenOption.WRITE);
    }
    @Test
    void test_download() throws IOException {
        URL url = new URL("https://t1.ledchuzu.com/2020/02/06/447800193824659e615307adec79ba59.jpg");
        System.out.println(url.getFile());
        System.out.println(url.getAuthority());
        System.out.println(url.getPort());
        System.out.println(url.getPath());
        System.out.println(url.openConnection().getContentType());
        InputStream inputStream = url.openStream();
        Files.copy(inputStream, Paths.get("D:","447800193824659e615307adec79ba59.jpg"), StandardCopyOption.REPLACE_EXISTING);
    }
}
