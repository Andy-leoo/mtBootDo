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
 * @createDate 2020/07/31 9:57 <br>
 * @TODO 该类做什么？？
 * @see com.bootdo.learning.com.stream.baseAPI <br>
 */
public class filterTest {

    /**
     * 过滤所有的男性
     */
    public static void fiterSex(){
        List<PersonModel> data = Data.getData();

        //old
        List<PersonModel> temp=new ArrayList<>();
        for (PersonModel person:data) {
            if ("男".equals(person.getSex())){
                temp.add(person);
            }
        }
        System.out.println(temp);

        //使用 stream
        List<PersonModel> collect1
                = data.stream().filter(p -> "男".equals(p.getSex())).collect(Collectors.toList());


        //new
        List<PersonModel> collect = data
                .stream()
                .filter(person -> "男".equals(person.getSex()))
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * 过滤所有的男性 并且小于20岁
     */
    public static void fiterSexAndAge(){
        List<PersonModel> data = Data.getData();

        //old
        List<PersonModel> temp=new ArrayList<>();
        for (PersonModel person:data) {
            if ("男".equals(person.getSex())&&person.getAge()<20){
                temp.add(person);
            }
        }


        List<PersonModel> collect2 = data.stream().filter(pm -> pm.getAge() < 20 && "男".equals(pm.getSex())).collect(Collectors.toList());
        //new 1
        List<PersonModel> collect = data
                .stream()
                .filter(person -> {
                    if ("男".equals(person.getSex())&&person.getAge()<20){
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());
        //new 2
        List<PersonModel> collect1 = data
                .stream()
                .filter(person -> ("男".equals(person.getSex())&&person.getAge()<20))
                .collect(Collectors.toList());

    }

}
