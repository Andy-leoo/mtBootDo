package com.bootdo.gper.pattern.factory.AbstractFactory.productFactory;

import com.bootdo.gper.pattern.factory.AbstractFactory.product.INote;
import com.bootdo.gper.pattern.factory.AbstractFactory.product.IVideo;

/**
 * @author Andy-J<br>
 * @version 1.0<br>
 * @createDate 2020/8/23 17:05 <br>
 * @desc 抽象工厂类，
 * 用户主入口
 * 在 Spring 中应用得最为广泛的一种设计模式 易于扩展
 */
public interface CourseFactory {
    INote createNote();
    IVideo createVideo();
}
