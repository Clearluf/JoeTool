package org.joe.fileutil;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * @author joe
 * @date 2022/8/9 20:29
 * @apiNote 文件工具类
 */
public class FileUtils {
    static final String fs = File.separator;
    @Test
    void test_list(){
        List<File> list = list(new File("E:\\Tools\\SSR"), true);
        System.out.println(list.size());
        for (File file : list){
            System.out.println(file);
        }
    }

    @Test
    void test_copy(){
//        try {
//            copy(new File("D:\\Temp\\"),new File("D:\\TMP\\ccc"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        try {
            copyRecursion(new File("D:\\Temp"),new File("D:\\TMP\\ccc"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 列出指定目录下所有文件，包含子目录下
     * @param directory 指定目录
     * @param exceptDir 是否排除目录
     * @return 文件列表
     */
    static List<File> list(File directory,boolean exceptDir){
        Stack<File> dirs = new Stack<>();
        List<File> result = new ArrayList<>();
        if (!directory.isDirectory()){
            result.add(directory);
            return result;
        }
        dirs.push(directory);
        while (!dirs.isEmpty()){
            //取得栈顶的元素并删除该元素
            File pop_dir = dirs.pop();
            File[] files_tmp = pop_dir.listFiles();
            for (File f : files_tmp){
                if (f.isDirectory()){
                    dirs.push(f);
                }
                if (!f.isDirectory()){
                    result.add(f);
                }else {
                    if (!exceptDir){
                        result.add(f);
                    }
                }
            }
        }
        return  result;
    }

    static void copy(String srcPath,String desPath) throws IOException {
        Path path = Paths.get(desPath);
        File file = path.toFile();
        Path copy_result = Files.copy(Paths.get(srcPath), path);
        System.out.println(copy_result);
    }
    static void copy(File src,File des) throws IOException {
        //源文件是个文件,目标文件是个目录
        if (src.isFile() && des.isDirectory()){
            Path desPath = Paths.get(des.getAbsolutePath(), src.getName());
            Files.copy(src.toPath(),desPath);
        }
        //源文件是个文件,目标文件也是个文件
        else if (src.isFile() && des.isFile()){
            Files.copy(src.toPath(),des.toPath());
        }
        //源文件是个目录,目标文件是个目录
        else if (src.isDirectory()){
            if (des.exists()){
                //采用递归复制
                Path desPath = Paths.get(des.getAbsolutePath(), src.getName());

                Files.copy(src.toPath(),desPath);
            }else {
                Files.copy(src.toPath(),des.toPath());
            }
        }
        //源文件是个目录,目标文件是个文件
        else{

        }
    }

    static void copyRecursion(File src,File des) throws IOException {
        Stack<File> dirs = new Stack<>();
        dirs.push(src);
        while (!dirs.isEmpty()) {
            //取得栈顶的元素并删除该元素
            File pop_dir = dirs.pop();
            Path relativize = src.toPath().relativize(pop_dir.toPath());
            Path temp_des = Paths.get(des.getAbsolutePath(),src.getName(),relativize.toString());
            Files.copy(pop_dir.toPath(),temp_des);
            File[] files_tmp = pop_dir.listFiles();
            if (Objects.isNull(files_tmp)) {
                continue;
            }
            for (File f : files_tmp) {
                if (f.isDirectory()) {
                    dirs.push(f);
                }else {
                    relativize = src.toPath().relativize(f.toPath());
                    temp_des = Paths.get(des.getAbsolutePath(),src.getName(),relativize.toString());
                    Files.copy(f.toPath(),temp_des);
                }
            }
        }




    }
//    static String getRelativePath(File dir,File target){
//
//    }
    @Test
    void test_getRelativePath(){
        Stack<String> aStack = new Stack<>();
        String aPath = "D:\\Temp\\aaa\\bbb\\cc";
        String bPath = "D:\\Temp\\aaa\\bbb\\cc";
        File aFile = new File(aPath);
        File bFile = new File(bPath);
        Path path_a = aFile.toPath();
        Path path_b = bFile.toPath();


        System.out.println(path_b.relativize(path_a));
    }
}

