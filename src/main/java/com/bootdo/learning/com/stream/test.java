package com.bootdo.learning.com.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/07/09 20:32 <br>
 * stream 概述
 * @see com.bootdo.learning.com.stream <br>
 */
public class test {

    public static void main(String[] args) {
        // 1. 添加测试数据：存储多个账号的列表
        List<String> accounts = new ArrayList<String>();
        accounts.add("tom");
        accounts.add("jerry");
        accounts.add("beita");
        accounts.add("shuke");
        accounts.add("damu");
        // 1.1. 业务要求：长度大于等于5的有效账号
//        for (String account : accounts) {
//            if (account.length() >= 5) {
//                System.out.println("有效账号："  + account);
//            }
//        }

        // 1.2. 迭代方式进行操作
//        Iterator<String> iterator = accounts.iterator();
//        while (iterator.hasNext()) {
//            String next = iterator.next();
//            if (next.length()>=5) {
//                System.out.println("有效账号："  + next);
//            }
//        }

        // 1.3. Stream结合lambda表达式，完成业务处理
        List<String> list = accounts.stream().filter(account -> account.length() >= 5).collect(Collectors.toList());
        System.out.println(list);


    }
}
