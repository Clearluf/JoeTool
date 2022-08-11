package org.joe.ioutils;

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
public class FileTools {


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



    /**
     * 替换文件内容，全盘替换
     * @param oldStr 旧字段
     * @param newStr 新字段
     * @param path 文件路径
     */
    public static void replaceFileContent(String oldStr, String newStr, Path path){
        replaceFileContent(oldStr, newStr, path,true);
    }

    /**
     * 替换文件内容
     * @param oldStr 旧字段
     * @param newStr 新字段
     * @param path 文件路径
     * @param allReplace 是否全部替换
     */
    public static void replaceFileContent(String oldStr, String newStr, Path path, boolean allReplace){
        try {
            List<String> allLines = Files.readAllLines(path);
            for (int i=0;i<allLines.size();i++){
                String str = allLines.get(i);
                if (str.contains(oldStr)){
                    allLines.set(i,str.replace(oldStr,newStr));
                    if (!allReplace){
                        break;
                    }
                }
            }
            Files.write(path,allLines,StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 文件增加新行
     * @param line 行内容
     * @param index 行所在位置
     * @param path 文件路径
     */
    public static void appendLine(String line,int index,Path path){
        try {
            List<String> allLines = Files.readAllLines(path);
            allLines.add(index,line);
            Files.write(path,allLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 文件最上面增加内容
     * @param line 行内容
     * @param path 文件路径
     */
    public static void appendLineAtHead(String line,Path path){
        try {
            List<String> allLines = Files.readAllLines(path);
            allLines.add(0,line);
            Files.write(path,allLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 文件最下面增加内容
     * @param line 行内容
     * @param path 文件路径
     */
    public static void appendLineAtTail(String line,Path path){
        try {
            List<String> allLines = Files.readAllLines(path);
            allLines.add(line);
            Files.write(path,allLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

