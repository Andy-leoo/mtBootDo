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
public class PythonNote implements INote {
    @Override
    public void edit() {
        System.out.println("编写 PY 笔记");
    }
}
