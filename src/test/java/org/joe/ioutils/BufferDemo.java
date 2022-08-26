package org.joe.ioutils;

/**
 * @author lujj
 * @date 2022/8/26 10:18
 * @apiNote String StringBuilder StringBuffer
 */
public class BufferDemo {
    public static void main(String[] args) {

        //字符串常量池概念

        String s1 = "hello";
        String s2 = new String("hello");
        String s3 = "hello";

        System.out.println(s1==s2);  //false
        System.out.println(s1==s3); //true

        //string本质上不可变的，查看源码可以发现 private final char value[];
        String s4 = s1 + "world";

        //线程不安全
        StringBuilder sb1 = new StringBuilder();
        sb1.append("hello");
        sb1.append("world");

        //线程安全  查看源码可以发现 所有方法都是用synchronized修饰了
        StringBuffer sBuffer1 = new StringBuffer();
        sBuffer1.append("hello");
        sBuffer1.append("world");

    }
}
