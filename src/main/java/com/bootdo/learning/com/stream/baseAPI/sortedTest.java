package com.bootdo.learning.com.stream.baseAPI;

import com.bootdo.learning.com.stream.base.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/07/31 15:22 <br>
 * @sorted(排序)
 * @see com.bootdo.learning.com.stream.baseAPI <br>
 */
public class sortedTest {

    public static void main(String [] args) {

        testSort1();
    }

    /**
     * 集合排序（默认排序）
     */
    private static void testSort1() {
        List<String> list = Arrays.asList("333","222","111");
        list.stream().sorted().forEach(System.out::println);
    }

    /**
     * 集合排序（指定排序规则）
     */
    private static void testSort2() {
        Student s1 = new Student(1L, "肖战", 15, "浙江");
        Student s2 = new Student(2L, "王一博", 15, "湖北");
        Student s3 = new Student(3L, "杨紫", 17, "北京");
        Student s4 = new Student(4L, "李现", 17, "浙江");
        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        students.stream()
                .sorted((stu1,stu2) ->Long.compare(stu2.getId(), stu1.getId()))
                .sorted((stu1,stu2) -> Integer.compare(stu2.getAge(),stu1.getAge()))
                .forEach(System.out::println);
    }
}
