package com.bootdo.learning.com.stream.baseAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/07/31 11:03 <br>
 * @TODO 该类做什么？？
 * @see com.bootdo.learning.com.stream.baseAPI <br>
 */
public class mapTest {

    /**
     * 取出所有的用户名字
     */
    public static void getUserNameList(){
        List<PersonModel> data = Data.getData();

        //old
        List<String> list=new ArrayList<>();
        for (PersonModel persion:data) {
            list.add(persion.getName());
        }
        System.out.println(list);

        List<String> collect3 = data.stream().map(p -> p.getName()).collect(Collectors.toList());

        //new 1
        List<String> collect = data.stream().map(person -> person.getName()).collect(Collectors.toList());
        System.out.println(collect);

        //new 2
        List<String> collect1 = data.stream().map(PersonModel::getName).collect(Collectors.toList());
        System.out.println(collect1);

        //new 3
        List<String> collect2 = data.stream().map(person -> {
            System.out.println(person.getName());
            return person.getName();
        }).collect(Collectors.toList());
    }
}
