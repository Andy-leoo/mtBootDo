package com.bootdo.learning.com.stream.baseAPI;

import com.bootdo.learning.com.stream.base.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/07/31 14:37 <br>
 * @T    distinct(去重)
 * @see com.bootdo.learning.com.stream.baseAPI <br>
 */
public class distinctTest {

    public static void main(String [] args) {

//        testDistinct1();
        testDistinct2();
    }

    /**
     * 集合去重（引用对象）
     */
    private static void testDistinct2() {
        //引用对象的去重，引用对象要实现hashCode和equal方法，否则去重无效
        Student s1 = new Student(1L, "肖战", 15, "浙江");
        Student s2 = new Student(2L, "王一博", 15, "湖北");
        Student s3 = new Student(3L, "杨紫", 17, "北京");
        Student s4 = new Student(4L, "李现", 17, "浙江");
        Student s5 = new Student(1L, "肖战", 15, "浙江");
        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        students.add(s5);
        students.stream().distinct().forEach(System.out::println);
    }

    /**
     * 集合去重（基本类型）
     */
    private static void testDistinct1() {
        //简单字符串的去重
        List<String> list = Arrays.asList("111","222","333","111","222").stream().distinct().collect(Collectors.toList());
        list.forEach(System.out::println);
    }
}
