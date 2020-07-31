package com.bootdo.learning.com.stream.base;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/07/31 7:37 <br>
 * @ collect 转化为list
 * @see com.bootdo.learning.com.stream.base <br>
 */
public class collectTest {

    public static void main(String[] args) {

//        Stream.of().collect(Collectors.toList());
        List<Student> studentList = Stream.of(new Student(1L,"路飞", 22, "海盗船"),
                new Student(2L,"红发", 40, "海盗船"),
                new Student(3L,"白胡子", 50, "海盗船")).collect(Collectors.toList());
        System.out.println(studentList);
    }
}
