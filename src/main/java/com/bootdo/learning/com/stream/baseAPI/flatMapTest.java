package com.bootdo.learning.com.stream.baseAPI;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/07/31 11:15 <br>
 * @TODO 该类做什么？？
 * @see com.bootdo.learning.com.stream.baseAPI <br>
 */
public class flatMapTest {

    public static void flatMapString() {
        List<PersonModel> data = Data.getData();
        //返回类型不一样
        List<String> collect = data.stream()
                .flatMap(person -> Arrays.stream(person.getName().split(" "))).collect(Collectors.toList());
        System.out.println(collect);
        List<Stream<String>> collect1 = data.stream()
                .map(person -> Arrays.stream(person.getName().split(" "))).collect(Collectors.toList());
        System.out.println(collect1);
        //用map实现
        List<String> collect2 = data
                                .stream()
                                .map(person -> person.getName().split(" "))
                                .flatMap(Arrays::stream)
                                .collect(Collectors.toList());
        System.out.println(collect2);
        //另一种方式
        List<String> collect3 = data
                                .stream()
                                .map(person -> person.getName().split(" "))
                                .flatMap(str -> Arrays.asList(str).stream())
                                .collect(Collectors.toList());
        System.out.println(collect3);
    }

    public static void main(String[] args) {
        flatMapString();
    }
}
