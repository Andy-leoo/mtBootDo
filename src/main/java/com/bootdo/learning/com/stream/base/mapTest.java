package com.bootdo.learning.com.stream.base;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/07/30 21:48 <br>
 * @  map(转换)
 * @see com.bootdo.learning.com.stream.base <br>
 */
public class mapTest {

    public static void main(String [] args) {

        Student s1 = new Student(1L, "肖战", 15, "浙江");
        Student s2 = new Student(2L, "王一博", 15, "湖北");
        Student s3 = new Student(3L, "杨紫", 17, "北京");
        Student s4 = new Student(4L, "李现", 17, "浙江");
        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);

        testMap(students);
    }

    /**
     * 集合转换
     * @param students
     * @return
     */
    private static void testMap(List<Student> students) {
        //在地址前面加上部分信息，只获取地址输出
        List<String> addresses = students.stream().map(s ->"住址:"+s.getAddress()).collect(Collectors.toList());
        addresses.forEach(a ->System.out.println(a));
    }
}
