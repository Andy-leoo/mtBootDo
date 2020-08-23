package com.bootdo.gper.pattern.factory.SimpleFactory;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/08/23 14:31 <br>
 * @TODO 该类做什么？？
 * @see com.bootdo.gper.pattern.factory.SimpleFactory <br>
 */
public class PythonCourse implements ICourse {
    @Override
    public void record() {
        System.out.println("python 录制");
    }
}
