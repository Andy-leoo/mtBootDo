package com.bootdo.learning.com.stream.baseAPI;

import com.bootdo.learning.com.stream.base.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/07/31 15:37 <br>
 * @TODO 该类做什么？？
 * @see com.bootdo.learning.com.stream.baseAPI <br>
 */
public class maxMinTest {

    public static void main(String [] args) {


        List<Student> students = new ArrayList<>(3);
        students.add(new Student(1L, "肖战", 14, "浙江"));
        students.add(new Student(2L, "王一博", 15, "湖北"));

        Optional<Student> max = students.stream()
                .max(Comparator.comparing(stu -> stu.getAge()));
        Optional<Student> min = students.stream()
                .min(Comparator.comparing(stu -> stu.getAge()));
        //判断是否有值
        if (max.isPresent()) {
            System.out.println(max.get());
        }
        if (min.isPresent()) {
            System.out.println(min.get());
        }


        testMin();
    }

    /**
     * 求集合中元素的最小值
     */
    private static void testMin() {
        Student s1 = new Student(1L, "肖战", 14, "浙江");
        Student s2 = new Student(2L, "王一博", 15, "湖北");
        Student s3 = new Student(3L, "杨紫", 17, "北京");
        Student s4 = new Student(4L, "李现", 17, "浙江");
        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        Student minS = students.stream().min((stu1,stu2) ->Integer.compare(stu1.getAge(),stu2.getAge())).get();
        System.out.println(minS.toString());
    }
}
