package com.bootdo.learning.com.stream.baseAPI;

import java.util.Arrays;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/07/31 15:30 <br>
 * @T    limit（限制返回个数）
 * @see com.bootdo.learning.com.stream.baseAPI <br>
 */
public class limitTest {

    public static void main(String [] args) {

        testLimit();
    }

    /**
     * 集合limit，返回前几个元素
     */
    private static void testLimit() {
        List<String> list = Arrays.asList("333","222","111");
        list.stream().limit(2).forEach(System.out::println);
    }
}
