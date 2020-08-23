package com.bootdo.gper.pattern.factory.AbstractFactory.productFactory;

import com.bootdo.gper.pattern.factory.AbstractFactory.product.INote;
import com.bootdo.gper.pattern.factory.AbstractFactory.product.IVideo;
import com.bootdo.gper.pattern.factory.AbstractFactory.product.JavaNote;
import com.bootdo.gper.pattern.factory.AbstractFactory.product.JavaVideo;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/08/23 17:12 <br>
 * @T  java 课程工厂
 * @see com.bootdo.gper.pattern.factory.AbstractFactory <br>
 */
public class JavaCourseFactory implements CourseFactory {
    @Override
    public INote createNote() {
        return new JavaNote();
    }

    @Override
    public IVideo createVideo() {
        return new JavaVideo();
    }
}
