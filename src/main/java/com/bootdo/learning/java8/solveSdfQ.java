package com.bootdo.learning.java8;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2020/07/14 17:14 <br>
 * 解决 SimpleDateFormat存在的问题
 * @see com.bootdo.learning.java8 <br>
 */
public class solveSdfQ {

    /**
     * 定义一个全局的SimpleDateFormat
     */
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 使用ThreadFactoryBuilder定义一个线程池
     */
    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("demo-pool-%d").build();

    private static ExecutorService pool = new ThreadPoolExecutor(5, 200,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    /**
     * 定义一个CountDownLatch，保证所有子线程执行完之后主线程再执行
     */
    private static CountDownLatch countDownLatch = new CountDownLatch(100);


    public static void main(String[] args) {


        /**
         * 1. 使用局部变量
         * SimpleDateFormat变成了局部变量，就不会被多个线程同时访问到了，就避免了线程安全问题。
         */
        for (int i = 0; i < 100; i++) {
            //定义一个线程安全的HashSet
            Set<String> dates = Collections.synchronizedSet(new HashSet<String>());
            //获取当前时间
            Calendar calendar = Calendar.getInstance();
            int finalI = i;
            pool.execute(() -> {
                // SimpleDateFormat声明成局部变量
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //时间增加
                calendar.add(Calendar.DATE, finalI);
                //通过simpleDateFormat把时间转换成字符串
                String dateString = simpleDateFormat.format(calendar.getTime());
                //把字符串放入Set中
                dates.add(dateString);
                //countDown
                countDownLatch.countDown();
            });
        }


        /**
         * 加同步锁
         *
         * 除了改成局部变量以外，还有一种方法大家可能比较熟悉的，就是对于共享变量进行加锁。
         * 通过加锁，使多个线程排队顺序执行。避免了并发导致的线程安全问题。
         *
         * 其实以上代码还有可以改进的地方，就是可以把锁的粒度再设置的小一点，
         * 可以只对simpleDateFormat.format这一行加锁，这样效率更高一些。
         */
        for (int i = 0; i < 100; i++) {
            //定义一个线程安全的HashSet
            Set<String> dates = Collections.synchronizedSet(new HashSet<String>());
            //获取当前时间
            Calendar calendar = Calendar.getInstance();
            int finalI = i;
            pool.execute(() -> {
                //加锁
                synchronized (simpleDateFormat) {
                    //时间增加
                    calendar.add(Calendar.DATE, finalI);
                    //通过simpleDateFormat把时间转换成字符串
                    String dateString = simpleDateFormat.format(calendar.getTime());
                    //把字符串放入Set中
                    dates.add(dateString);
                    //countDown
                    countDownLatch.countDown();
                }
            });
        }

        /**
         * 3. 使用ThreadLocal
         *
         * 第三种方式，就是使用 ThreadLocal。
         * ThreadLocal 可以确保每个线程都可以得到单独的一个 SimpleDateFormat 的对象，那么自然也就不存在竞争问题了。
         */
        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        //用法
        String dateString = simpleDateFormatThreadLocal.get().format(calendar.getTime());


        /**
         * 4. 使用DateTimeFormatter
         *
         * 如果是Java8应用，可以使用DateTimeFormatter代替SimpleDateFormat，这是一个线程安全的格式化工具类。
         * 就像官方文档中说的，这个类 simple beautiful strongimmutable thread-safe。
         */
        //解析日期
        String dateStr= "2016年10月25日";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        LocalDate date= LocalDate.parse(dateStr, formatter);

        //日期转换为字符串
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy年MM月dd日 hh:mm a");
        String nowStr = now .format(format);
        System.out.println(nowStr);

    }

    /**
     * 使用ThreadLocal定义一个全局的SimpleDateFormat
     */
    private static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
}
