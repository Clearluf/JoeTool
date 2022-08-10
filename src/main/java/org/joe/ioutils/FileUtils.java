package org.joe.ioutils;

import org.junit.jupiter.api.Test;

import java.io.*;
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


    /**
     * 列出指定目录下所有文件，包含子目录下
     * @param directory 指定目录
     * @param exceptDir 是否排除目录
     * @return 文件列表
     */
    public static List<File> list(File directory,boolean exceptDir){
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

    public static void copy(String srcPath,String desPath) throws IOException {
        copy(new File(srcPath),new File(desPath));
    }
    public static void copy(File src,File des) throws IOException {
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
                copyRecursion(src,des);
            }else {
                Files.copy(src.toPath(),des.toPath());
            }
        }
        //源文件是个目录,目标文件是个文件
        else{
            throw new RuntimeException("can not copy a directory to a file");
        }
    }

    /**
     *
     * @param src 源文件
     * @param des 目标文件
     */
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


}

