package com.bootdo.learning.com;

import com.bootdo.learning.com.lambda.IUserCredential;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/07/07 22:32 <br>
 * @TODO 该类做什么？？
 * @see com.bootdo.learning.com <br>
 */
public class test {

    public static void main(String[] args) {


        //匿名内部类 ，实现抽象方法
        IUserCredential ic2 = new IUserCredential() {
            @Override
            public String verifyUser(String username) {
                return "admin".equals(username)?"管理员":"用户";
            }
        };

        System.out.println(ic2.verifyUser("admin"));
        System.out.println(ic2.verifyUser("user"));

        // 使用 lambda 表达式，针对函数式表达式简单实现
        IUserCredential ic3 = (String username) -> {
            return "admin".equals(username)?"lamd管理员":"lamd用户";
        };

        System.out.println(ic3.verifyUser("admin"));
    }
}
