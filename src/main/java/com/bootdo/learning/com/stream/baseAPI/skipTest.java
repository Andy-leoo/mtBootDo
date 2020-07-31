package com.bootdo.learning.com.stream.baseAPI;

import java.util.Arrays;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/07/31 15:32 <br>
 * @TODO 该类做什么？？
 * @see com.bootdo.learning.com.stream.baseAPI <br>
 */
public class skipTest {

    public static void main(String [] args) {

        testSkip();
    }

    /**
     * 集合skip，删除前n个元素
     */
    private static void testSkip() {
        List<String> list = Arrays.asList("333","222","111");
        list.stream().skip(2).forEach(System.out::println);
    }
}
