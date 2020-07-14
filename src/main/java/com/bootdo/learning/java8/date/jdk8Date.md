# 简介

    伴随 lambda表达式、streams 以及一系列小优化，Java 8 推出了全新的日期时间API。
    
    Java处理日期、日历和时间的不足之处：将 java.util.Date 设定为可变类型，以及 SimpleDateFormat 的非线程安全使其应用非常受限。然后就在 java8 上面增加新的特性。
    
    全新API的众多好处之一就是，明确了日期时间概念，例如：瞬时（instant）、 长短（duration）、日期、时间、时区和周期。
    
    同时继承了Joda 库按人类语言和计算机各自解析的时间处理方式。不同于老版本，新API基于ISO标准日历系统，java.time包下的所有类都是不可变类型而且线程安全。

# 关键类

    Instant：瞬时实例。
    LocalDate：本地日期，不包含具体时间 例如：2014-01-14 可以用来记录生日、纪念日、加盟日等。
    LocalTime：本地时间，不包含日期。
    LocalDateTime：组合了日期和时间，但不包含时差和时区信息。
    ZonedDateTime：最完整的日期时间，包含时区和相对UTC或格林威治的时差。
    
    新API还引入了 ZoneOffSet 和 ZoneId 类，使得解决时区问题更为简便。解析、格式化时间的 DateTimeFormatter类也全部重新设计。

# 为什么需要需要LocalDate、LocalTime、LocalDateTime

    1. Date如果不格式化，打印出的日期可读性差
    2. 用SimpleDateFormat对时间进行格式化，但SimpleDateFormat是线程不安全的
        查看SimpleDateFormat的format方法最终调用代码：
        1）calendar是共享变量，并且这个共享变量没有做线程安全控制
        2）当多个线程同时使用相同的SimpleDateFormat对象【如用static修饰的SimpleDateFormat】调用format方法时，
           多个线程会同时调用calendar.setTime方法，可能一个线程刚设置好time值另外的一个线程马上把设置的time值给修改了导致返回的格式化时间可能是错误的
        3）在多并发情况下使用SimpleDateFormat需格外注意SimpleDateFormat除了format是线程不安全以外，
            parse方法也是线程不安全的
        4）parse方法实际调用alb.establish(calendar).getTime()方法来解析，
        5） alb.establish(calendar)方法里主要完成了 
               1.重置日期对象cal的属性值
               2.使用calb中中属性设置cal
               3.返回设置好的cal对象
            但是这三步不是原子操作
            
            多线程并发如何保证线程安全 
            - 避免线程之间共享一个SimpleDateFormat对象，每个线程使用时都创建一次SimpleDateFormat对象 => 创建和销毁对象的开销大 
            - 对使用format和parse方法的地方进行加锁 => 线程阻塞性能差 
            - 使用ThreadLocal保证每个线程最多只创建一次SimpleDateFormat对象 => 较好的方法
            
# 如何解决SimpleDateFormat存在的问题
    
    1. 使用局部变量 SimpleDateFormat变成了局部变量，就不会被多个线程同时访问到了，就避免了线程安全问题。
    2. 加同步锁 除了改成局部变量以外，还有一种方法大家可能比较熟悉的，就是对于共享变量进行加锁。通过加锁，使多个线程排队顺序执行。
        避免了并发导致的线程安全问题。
        其实以上代码还有可以改进的地方，就是可以把锁的粒度再设置的小一点，可以只对simpleDateFormat.format这一行加锁，这样效率更高一些。
    3. 使用ThreadLocal
        就是使用 ThreadLocal。 ThreadLocal 可以确保每个线程都可以得到单独的一个 SimpleDateFormat 的对象，那么自然也就不存在竞争问题了。
    
# 实战

    在教程中我们将通过一些简单的实例来学习如何使用新API，因为只有在实际的项目中用到，才是学习新知识以及新技术最快的方式。
    
   
# 总结


    Java 8日期时间API的重点：
    
    提供了javax.time.ZoneId 获取时区。
    
    提供了LocalDate和LocalTime类。
    
    Java 8 的所有日期和时间API都是不可变类并且线程安全，而现有的Date和Calendar API中的java.util.Date和SimpleDateFormat是非线程安全的。
    
    主包是 java.time,包含了表示日期、时间、时间间隔的一些类。里面有两个子包java.time.format用于格式化， java.time.temporal用于更底层的操作。
    
    时区代表了地球上某个区域内普遍使用的标准时间。每个时区都有一个代号，格式通常由区域/城市构成（Asia/Tokyo），在加上与格林威治或 UTC的时差。例如：东京的时差是+09:00。

    1.和 SimpleDateFormat 相比，DateTimeFormatter 是线程安全的。
    2.Instant 的精确度更高，可以精确到纳秒级。
    3.Duration 可以便捷得到时间段内的天数、小时数等。
    4.LocalDateTime 能够快速地获取年、月、日、下一月等。
    5.TemporalAdjusters 类中包含许多常用的静态方法，避免自己编写工具类。
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    