package com.bootdo.gper.pattern.factory.AbstractFactory.product;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/08/23 17:09 <br>
 * @TODO 该类做什么？？
 * @see com.bootdo.gper.pattern.factory.AbstractFactory <br>
 */
public class JavaVideo implements IVideo {
    @Override
    public void record() {
        System.out.println("录制 Java 视频");
    }
}
