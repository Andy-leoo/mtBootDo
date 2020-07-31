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
 * @createDate 2020/07/30 21:36 <br>
 * @ 了解 filter 筛选
 * @see com.bootdo.learning.com.stream.base <br>
 */
public class filterTest {

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

        List<Student> streamStudents = testFilter(students);

        streamStudents.forEach(System.out::println);

    }
    /**
     * 集合的筛选
     * @param students
     * @return  filter ( 对象 -> 要操作的逻辑【对应字段的大小，对比。。】)
     */
    private static List<Student> testFilter(List<Student> students) {
        //筛选年龄大于15岁的学生
//        return students.stream().filter(student -> student.getAge()>15).collect(Collectors.toList());
//        return students.stream().filter(s -> s.getAge()>15).collect(Collectors.toList());
        //筛选住在浙江省的学生
        return students.stream().filter(s -> s.getAddress().equals("浙江")).collect(Collectors.toList());
    }

}
