package com.bootdo.learning.com.stream.baseAPI;

import java.util.stream.Stream;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/07/31 11:44 <br>
 * @TODO 该类做什么？？
 * @see com.bootdo.learning.com.stream.baseAPI <br>
 */
public class ReduceTest {
    public static void reduceTest(){
        //累加，初始化值是 10
        Integer reduce = Stream.of(1, 2, 3, 4)
                .reduce(10, (count, item) ->{
//                    System.out.println("count:"+count);
//                    System.out.println("item:"+item);
                    return count + item;
                } );
//        System.out.println(reduce);

        Integer reduce1 = Stream.of(1, 2, 3, 4)
                .reduce(1, (x, y) -> x + y);
        System.out.println(reduce1);

        String reduce2 = Stream.of("1", "2", "3")
                .reduce("0", (x, y) -> (x + "," + y));
        System.out.println(reduce2);
    }

    public static void main(String[] args) {
        reduceTest();
    }
}
