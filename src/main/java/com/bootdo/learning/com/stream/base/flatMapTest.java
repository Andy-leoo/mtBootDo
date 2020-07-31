package com.bootdo.learning.com.stream.base;

import java.util.ArrayList;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/07/31 11:18 <br>
 * @TODO 该类做什么？？
 * @see com.bootdo.learning.com.stream.base <br>
 */
public class flatMapTest {

    // 将两个list, 合并 进行 flatmap 统一处理 ，
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>(3);
        students.add(new Student(3L, "杨紫", 17, "北京"));
        students.add(new Student(4L, "李现", 17, "浙江"));

//        List<Student> studentList = Stream.of(students)
//                .flatMap(studentsStr -> Arrays.asList(studentsStr).stream()).collect(Collectors.toList());
//        System.out.println(studentList);
    }
}
