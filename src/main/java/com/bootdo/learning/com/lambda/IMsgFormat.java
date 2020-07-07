package com.bootdo.learning.com.lambda;

@FunctionalInterface
public interface IMsgFormat {
    /**
     * 消息转换方法
     * @param  msg 要转换的消息
     * @param format 转换的格式[xml/json..]
     * @return 返回转换后的数据
     */
    String format(String msg , String format);

    /**
     * @desc 来自 object继承的方法
     */
    String toString();

    /**
     *   静态方法
     *
     *   消息合法性验证方法
     * @param msg 要验证的消息
     * @return 返回验证结果
     */
    static boolean verifyMessage(String msg) {
        if (msg != null) {
            return true;
        }
        return false;
    }

}
