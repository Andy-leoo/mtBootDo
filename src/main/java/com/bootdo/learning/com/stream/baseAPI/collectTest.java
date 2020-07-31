package com.bootdo.learning.com.stream.baseAPI;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/07/31 7:37 <br>
 * @ collect 转化为
 * @see com.bootdo.learning.com.stream.base <br>
 */
public class collectTest {

    /**
     * toList
     */
    public static void toListTest(){
        List<PersonModel> data = Data.getData();
        List<String> collect = data.stream()
                                .map(PersonModel::getName)
                                .collect(Collectors.toList());
    }

    /**
     * toSet
     */
    public static void toSetTest(){
        List<PersonModel> data = Data.getData();
        Set<String> collect = data.stream()
                                .map(PersonModel::getName)
                                .collect(Collectors.toSet());
    }

    /**
     * toMap
     */
    public static void toMapTest(){
        List<PersonModel> data = Data.getData();

        Map<String, Integer> collect2 = data.stream().collect(Collectors.toMap(PersonModel::getName, PersonModel::getAge));



        Map<String, Integer> collect = data.stream()
                .collect(
                        Collectors.toMap(PersonModel::getName, PersonModel::getAge)
                );

        Map<String, String> collect1 = data.stream()
                .collect(Collectors.toMap(per -> per.getName(), value -> {
                    return value + "1";
                }));
    }

    /**
     * 指定类型
     */
    public static void toTreeSetTest(){
        List<PersonModel> data = Data.getData();

        LinkedList<PersonModel> collect1 = data.stream().collect(Collectors.toCollection(LinkedList::new));


        TreeSet<PersonModel> collect = data.stream()

                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println(collect);
    }

    /**
     * 分组
     */
    public static void toGroupTest(){
        List<PersonModel> data = Data.getData();
        Map<Boolean, List<PersonModel>> collect = data.stream()
                .collect(Collectors.groupingBy(per -> "男".equals(per.getSex())));
        System.out.println(collect);
    }

    /**
     * 分隔
     */
    public static void toJoiningTest(){
        List<PersonModel> data = Data.getData();
        String collect = data.stream()
                .map(personModel -> personModel.getName())
                .collect(Collectors.joining(",", "{", "}"));

        System.out.println(collect);
    }

    /**
     * 自定义
     */
    public static void reduce(){
        List<String> collect = Stream.of("1", "2", "3").collect(
                Collectors.reducing(new ArrayList<String>(), x -> Arrays.asList(x), (y, z) -> {
                    y.addAll(z);
                    return y;
                }));
        System.out.println(collect);
    }

    public static void main(String[] args) {
//        toGroupTest();
//        reduce();
        toJoiningTest();
    }

}
