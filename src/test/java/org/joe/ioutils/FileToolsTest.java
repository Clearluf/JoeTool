package org.joe.ioutils;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.List;

/**
 * @author joe
 * @date 2022/8/10 22:00
 */
public class FileToolsTest {
    @Test
    void test_list(){
        List<File> list = FileTools.list(new File("E:\\Tools\\SSR"), true);
        System.out.println(list.size());
        for (File file : list){
            System.out.println(file);
        }
    }

    @Test
    void test_copy(){
        try {
            FileTools.copyRecursion(new File("D:\\Temp"),new File("D:\\TMP\\ccc"));
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
       // URL url = new URL("https://t1.ledchuzu.com/2020/02/06/447800193824659e615307adec79ba59.jpg");
       // System.out.println(url.openConnection().getContentType());
       // InputStream inputStream = url.openStream();
       // Files.copy(inputStream, Paths.get("D:","447800193824659e615307adec79ba59.jpg"), StandardCopyOption.REPLACE_EXISTING);

        URL url = new URL("https://b.chaochui.ml/vip20200205%e8%8a%b1%e5%a5%b3%e9%83%8e%e5%90%b3%e6%b2%90%e7%86%99%e7%84%a1%e8%81%96%e5%85%89%e5%a5%97%e5%9c%9640p%e5%8e%9f%e5%9b%be%e5%8c%85%e8%a7%86%e9%a2%91%e4%ba%8c");
        System.out.println(url.openConnection().getContentType());
        InputStream inputStream = url.openStream();
        byte[] bytes = new byte[1024];
//        System.out.println(inputStream.available());
//        byte[] bytes = new byte[inputStream.available()];
//        inputStream.read(bytes);
//        String s = new String(bytes);
//        System.out.println(s);
        while (inputStream.read()>-1){

        }
    }

    @Test
    void test_readFile() throws IOException {
        String file = new String("D:\\TMP\\1.txt");
        InputStream is = Files.newInputStream(Paths.get(file));
        InputStream is2 = Files.newInputStream(Paths.get(file));
        //按字节读取,一个字节一个字节的读
        int index;

        //is.read() 读取输入流的下一个字节,类似于弹栈,返回值为该字节的ASCII的编号 使用charAt或者System.out.write可打印其对应的符号
        while((index = is.read())>-1){
            //is.read(bytes,index,1024);
            System.out.write(index);
            System.out.println(">>>>>");
            //System.out.println(new String(bytes));
        }
        is.close();
        System.out.println("一次读取多个字节");
        //
        int read_index = 0;
        byte[] readByte = new byte[4];
        //is2.read(readByte) 将最上面的四个元素依次放入数组，  比如元素为1234asf
        //第一次放入数组的按照顺序为1234
        //然后接着读,只能读到三个元素asf   将这三个元素填到数组,从而数组等于asf4L
        while ((read_index = is2.read(readByte)) >-1) {
            System.out.println(new String(readByte));
            System.out.write(readByte, 0, read_index);
            System.out.println(">>>>");
        }
    }
    @Test
    void test_replaceFileContent(){
        FileTools.replaceFileContent("HTTP","HTTPS",Paths.get("D:\\TMP\\1.txt"),true);
    }

    @Test
    void test(){
        String lineSeparator = System.lineSeparator();
        //文件追加内容
        Path path = Paths.get("D:\\TMP\\1.txt");
        try {
            List<String> allLines = Files.readAllLines(path);
            allLines.add(1,"hello world");
            Files.write(path,allLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
