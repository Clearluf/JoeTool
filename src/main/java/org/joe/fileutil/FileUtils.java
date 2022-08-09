package org.joe.fileutil;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author joe
 * @date 2022/8/9 20:29
 * @apiNote 文件工具类
 */
public class FileUtils {
    @Test
    void test_list(){
        List<File> list = list(new File("E:\\Tools\\SSR"), true);
        System.out.println(list.size());
        for (File file : list){
            System.out.println(file);
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

}

