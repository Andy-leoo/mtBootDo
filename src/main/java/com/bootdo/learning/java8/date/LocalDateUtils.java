package com.bootdo.learning.java8.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * @author jiangxiao
 * @Title: LocalDateUitl
 * @Package
 * @Description: 学习 java8 全新时间API
 * @date 2020/7/1315:16
 */
public class LocalDateUtils {

    /**
     * 1. 获取当前的日期
     *     Java 8 中的 LocalDate 用于表示当天日期。和 java.util.Date不同，它只有日期，不包含时间。当你仅需要表示日期时就用这个类。
     */
    //获取今天的日期,只有日期，不包含时间。当你仅需要表示日期时就用这个类
    public static void getCurrentDate(){
        LocalDate today = LocalDate.now();
        System.out.println("Today's Local date : " + today);

        //这个是作为对比
        Date date = new Date();
        System.out.println(date);
    }

    /**
     * 2. 获取年、月、日信息
     *     LocalDate 提供了获取年、月、日的快捷方法，其实例还包含很多其它的日期属性。
     *     通过调用这些方法就可以很方便的得到需要的日期信息，不用像以前一样需要依赖java.util.Calendar类了。
     */
    //获取年、月、日信息
    public static void getDetailDate(){
        LocalDate today = LocalDate.now();
        // 获取年、月、日、星期几
        int year = today.getYear();
        int year1 = today.get(ChronoField.YEAR);
//        Month month = today.getMonth();
        int month = today.getMonthValue();
        int month1 = today.get(ChronoField.MONTH_OF_YEAR);
        int day = today.getDayOfMonth();
        int day1 = today.get(ChronoField.DAY_OF_MONTH);
//        DayOfWeek dayOfWeek = today.getDayOfWeek();
        int dayOfWeek1 = today.get(ChronoField.DAY_OF_WEEK);

        System.out.printf("Year : %d  Month : %d  day : %d  week : %d t %n", year, month, day,dayOfWeek1);
        System.out.printf("Year : %d  Month : %d  day : %d week : %d t %n", year1, month1, day1,dayOfWeek1);
    }

    /**
     * 3.处理特定日期
     *     在第一个例子里，我们通过静态工厂方法now()非常容易地创建了当天日期。我们还可以调用另一个有用的工厂方法 LocalDate.of() 创建任意日期，
     *     该方法需要传入年、月、日做参数，返回对应的LocalDate实例。
     *     这个方法的好处是没再犯老API的设计错误，比如年度起始于1900，月份是从 0 开始等等。日期所见即所得，就像下面这个例子表示了1月21日，直接明了。
     */
    //处理特定日期
    public static void handleSpecilDate(){
        LocalDate dateOfBirth = LocalDate.of(2018, 01, 21);
        System.out.println("The specil date is : " + dateOfBirth);
    }

    /**
     * 4.判断两个日期是否相等
     *     现实生活中有一类时间处理就是判断两个日期是否相等。在项目开发的时候总会遇到这样子的问题。
     *     下面这个例子会帮助你用Java 8的方式去解决，LocalDate 重载了equal方法。
     *     注意，如果比较的日期是字符型的，需要先解析成日期对象再作判断。
     */
    //判断两个日期是否相等
    public static void compareDate(){
        LocalDate today = LocalDate.now();
        LocalDate date1 = LocalDate.of(2020, 07, 13);

        if(date1.equals(today)){
            System.out.printf("TODAY %s and DATE1 %s are same date %n", today, date1);
        }
    }

    /**
     *5.检查像生日这种周期性事件
     *Java 中另一个日期时间的处理就是检查类似生日、纪念日、法定假日（国庆以及春节）、或者每个月固定时间发送邮件给客户 这些周期性事件。
     * Java中如何检查这些节日或其它周期性事件呢？
     * 答案就是MonthDay类。这个类组合了月份和日，去掉了年，这意味着你可以用它判断每年都会发生事件。
     * 和这个类相似的还有一个YearMonth类。这些类也都是不可变并且线程安全的值类型
     */
    //处理周期性的日期
    public static void cycleDate(){
        LocalDate today = LocalDate.now();
        LocalDate dateOfBirth = LocalDate.of(2020, 07 , 14);

        MonthDay birthday = MonthDay.of(dateOfBirth.getMonth(), dateOfBirth.getDayOfMonth());
        MonthDay currentMonthDay = MonthDay.from(today);

        if(currentMonthDay.equals(birthday)){
            System.out.println("Many Many happy returns of the day !!");
        }else{
            System.out.println("Sorry, today is not your birthday");
        }
    }

    /**
     *6.获取当前时间
     *与 获取日期 例子很像，获取时间使用的是 LocalTime 类，一个只有时间没有日期的LocalDate近亲。
     * 可以调用静态工厂方法now()来获取当前时间。默认的格式是hh:mm:ss:nnn。
     */
    //获取当前时间
    public static void getCurrentTime(){
        LocalTime time = LocalTime.now();
        System.out.println("local time now : " + time);

        // 获取小时
        int hour = time.getHour();
        int hour1 = time.get(ChronoField.HOUR_OF_DAY);
        // 获取分
        int minute = time.getMinute();
        int minute1 = time.get(ChronoField.MINUTE_OF_HOUR);
        // 获取秒
        int second = time.getMinute();
        int second1 = time.get(ChronoField.SECOND_OF_MINUTE);
    }

    /**
     *7.在现有的时间上增加小时
     * Java 8 提供了更好的 plusHours() 方法替换 add() ，并且是兼容的。
     * 注意，这些方法返回一个全新的LocalTime实例，由于其不可变性，返回后一定要用变量赋值。
      */
    //增加小时
    public static void plusHours(){
        LocalTime time = LocalTime.now();
        LocalTime newTime = time.plusHours(2); // 增加两小时
        System.out.println("Time after 2 hours : " +  newTime);
    }

    /**
     * 8.如何计算一个星期之后的日期
     * 和上个例子计算两小时以后的时间类似，这个例子会计算一周后的日期。
     * LocalDate日期不包含时间信息，它的plus()方法用来增加天、周、月，ChronoUnit类声明了这些时间单位。
     * 由于LocalDate也是不变类型，返回后一定要用变量赋值。
     * 可以用同样的方法增加1个月、1年、1小时、1分钟甚至一个世纪，更多选项可以查看Java 8 API中的ChronoUnit类。
     * */
    //如何计算一周后的日期
    public static void nextWeek(){
        LocalDate localDateTime = LocalDate.now();
        LocalDate nextWeek = localDateTime.plus(1, ChronoUnit.WEEKS);    //使用变量赋值
        System.out.println("Today is : " + localDateTime);
        System.out.println("Date after 1 week : " + nextWeek);

        // 增加一年
        localDateTime = localDateTime.plusYears(1);
        localDateTime = localDateTime.plus(1, ChronoUnit.YEARS);
        // 减少一个月
        localDateTime = localDateTime.minusMonths(1);
        localDateTime = localDateTime.minus(1, ChronoUnit.MONTHS);
        // 通过with修改某些值
        // 修改年为2020
        localDateTime = localDateTime.withYear(2020);
        localDateTime = localDateTime.with(ChronoField.YEAR, 2020);
        // 时间计算
        // 获取该年的第一天
        LocalDate localDate = LocalDate.now();
        LocalDate localDate1 = localDate.with(TemporalAdjusters.firstDayOfYear());
    }

    /**
     *9.计算一年前或一年后的日期
     * 接着上面的例子中我们通过 LocalDate 的 plus() 方法增加天数、周数或月数，
     * 这个例子我们利用 minus() 方法计算一年前的日期。
     */
    //计算一年前或一年后的日期
    public static void minusDate(){
        LocalDate today = LocalDate.now();
        LocalDate previousYear = today.minus(1, ChronoUnit.YEARS);
        System.out.println("Date before 1 year : " + previousYear);

        LocalDate nextYear = today.plus(1, ChronoUnit.YEARS);
        System.out.println("Date after 1 year : " + nextYear);
    }

    /**
     * 10.使用Java 8的Clock时钟类
     * Java 8增加了一个 Clock 时钟类用于获取当时的时间戳，或当前时区下的日期时间信息。
     * 以前用到System.currentTimeInMillis() 和 TimeZone.getDefault() 的地方都可用Clock替换。
     */
    public static void clock(){
        // 根据系统时间返回当前时间并设置为UTC。
        Clock clock = Clock.systemUTC();
        System.out.println("Clock : " + clock);

        // 根据系统时钟区域返回时间
        Clock defaultClock = Clock.systemDefaultZone();
        System.out.println("defaultClock : " + defaultClock);
    }

    /**
     * 11.判断日期是早于还是晚于另一个日期
     * LocalDate 类有两类方法 isBefore() 和 isAfter() 用于比较日期。
     * 调用 isBefore() 方法时，如果给定日期小于当前日期则返回 true。
     */
    //如何用Java判断日期是早于还是晚于另一个日期
    public static void isBeforeOrIsAfter(){
        LocalDate today = LocalDate.now();

        LocalDate tomorrow = LocalDate.of(2018, 1, 29);
        if(tomorrow.isAfter(today)){
            System.out.println("Tomorrow comes after today");
        }

        //减去一天
        LocalDate yesterday = today.minus(1, ChronoUnit.DAYS);

        if(yesterday.isBefore(today)){
            System.out.println("Yesterday is day before today");
        }
    }

    /**
     * 12.处理时区
     * Java 8不仅分离了日期和时间，也把时区分离出来了。
     * 现在有一系列单独的类如 ZoneId 来处理特定时区，ZoneDateTime 类来表示某时区下的时间
     */
    //获取特定时区下面的时间
    public static void getZoneTime(){
        //设置时区
        ZoneId america = ZoneId.of("America/New_York");

        LocalDateTime localtDateAndTime = LocalDateTime.now();

        ZonedDateTime dateAndTimeInNewYork  = ZonedDateTime.of(localtDateAndTime, america );
        System.out.println("现在的日期和时间在特定的时区 : " + dateAndTimeInNewYork);
    }


    /**
     * 13.如何体现出固定日期
     * 例如：表示信用卡到期这类固定日期。
     * 与 MonthDay 检查重复事件的例子相似，YearMonth 是另一个组合类，用于表示信用卡到期日、FD到期日、期货期权到期日等。
     * 还可以用这个类得到 当月共有多少天，YearMonth 实例的 lengthOfMonth() 方法可以返回当月的天数，在判断2月有28天还是29天时非常有用。
     */
    //使用 YearMonth类处理特定的日期
    public static void checkCardExpiry(){
        YearMonth currentYearMonth = YearMonth.now();
        System.out.printf("Days in month year %s: %d%n", currentYearMonth, currentYearMonth.lengthOfMonth());

        YearMonth creditCardExpiry = YearMonth.of(2028, Month.FEBRUARY);
        System.out.printf("Your credit card expires on %s %n", creditCardExpiry);
    }

    /**
     * 14.检查闰年
     * LocalDate类有一个很实用的方法 isLeapYear() 判断该实例是否是一个闰年，
     * 如果你还是想重新发明轮子，这有一个代码示例，纯Java逻辑编写的判断闰年的程序。
     */
    //检查闰年
    public static void isLeapYear(){
        LocalDate today = LocalDate.now();
        if(today.isLeapYear()){
            System.out.println("This year is Leap year");
        }else {
            System.out.println("2020 is not a Leap year");
        }
    }


    /**
     * 15.计算两个日期之间的天数和月数
     * 有一个常见日期操作是计算两个日期之间的天数、周数或月数。在Java 8中可以用java.time.Period类来做计算。
     * 下面这个例子中，我们计算了当天和将来某一天之间的月数。下面的例子：现在是一月份，距离到五月份，中间相隔3月
     */
    //计算两个日期之间的天数和月数
    public static void calcDateDays(){
        LocalDate today = LocalDate.now();

        LocalDate java8Release = LocalDate.of(2020, Month.JULY, 14);

        Period periodToNextJavaRelease = Period.between(java8Release,today);

        // 计算月份
        System.out.println("Months left between today and Java 8 release : "
                + periodToNextJavaRelease.getMonths() );
    }


    /**
     * 17.获取当前的时间戳
     * Instant类有一个静态工厂方法now()会返回当前的时间戳，
     */
    public static void getTimestamp(){
        Instant instant = Instant.now();

        // 获取秒数
        long currentSecond = instant.getEpochSecond();
        // 获取毫秒数
        long currentMilli = instant.toEpochMilli();
        System.out.println("What is value of this instant " + instant);
    }

    /**
     * 18.使用预定义的格式化工具去解析或格式化日期
     * Java 8引入了全新的日期时间格式工具，线程安全而且使用方便。它自带了一些常用的内置格式化工具。
     * 下面这个例子使用了BASIC_ISO_DATE格式化工具将2018年2月10日格式化成20180210。
     */
    // 使用预定义的格式化工具去解析或格式化日期
    public static void formateDate(){
        String dayAfterTommorrow = "20180210";
        LocalDate formatted = LocalDate.parse(dayAfterTommorrow, DateTimeFormatter.BASIC_ISO_DATE);
        System.out.printf("Date generated from String %s is %s %n", dayAfterTommorrow, formatted);
    }

    /**
     * 字符串互转日期类型
     */
    public static void strToDate(){
        LocalDateTime date = LocalDateTime.now();

        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        //日期转字符串
        String str = date.format(format1);

        System.out.println("日期转换为字符串:"+str);

        DateTimeFormatter format2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        //字符串转日期
        LocalDate date2 = LocalDate.parse(str,format2);
        System.out.println("日期类型:"+date2);
    }

    /**
     *LocalDateTime 获取年月日时分秒，相当于 LocalDate + LocalTime
     */
    public static void slocalDateTest(){
        // 创建 LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTime1 = LocalDateTime.of(2019, Month.SEPTEMBER, 10, 14, 46, 56);
//        LocalDateTime localDateTime2 = LocalDateTime.of(localDate, localTime);
//        LocalDateTime localDateTime3 = localDate.atTime(localTime);
//        LocalDateTime localDateTime4 = localTime.atDate(localDate);
        // 获取LocalDate
        LocalDate localDate2 = localDateTime.toLocalDate();
        // 获取LocalTime
        LocalTime localTime2 = localDateTime.toLocalTime();
    }

    //Duration 表示一个时间段
    public static void durationTest (){
        // Duration.between()方法创建 Duration 对象
        LocalDateTime from = LocalDateTime.of(2017, Month.JANUARY, 1, 00, 0, 0);    // 2017-01-01 00:00:00
        LocalDateTime to = LocalDateTime.of(2019, Month.SEPTEMBER, 12, 14, 28, 0);     // 2019-09-15 14:28:00
        Duration duration = Duration.between(from, to);     // 表示从 from 到 to 这段时间
        long days = duration.toDays();              // 这段时间的总天数
        long hours = duration.toHours();            // 这段时间的小时数
        long minutes = duration.toMinutes();        // 这段时间的分钟数
        long seconds = duration.getSeconds();       // 这段时间的秒数
        long milliSeconds = duration.toMillis();    // 这段时间的毫秒数
        long nanoSeconds = duration.toNanos();      // 这段时间的纳秒数
    }

    /**
     * TemporalAdjusters 包含许多静态方法，可以直接调用
     * firstDayOfMonth	返回当月的第一天
     * firstDayOfNextMonth	返回下月的第一天
     * firstDayOfNextYear	返回下一年的第一天
     * firstDayOfYear	返回本年的第一天
     * firstInMonth	返回同一个月中第一个星期几
     * lastDayOfMonth	返回当月的最后一天
     * lastDayOfNextMonth	返回下月的最后一天
     * lastDayOfNextYear	返回下一年的最后一天
     */
    public static void temporalAdjustersTest(){
        // 当前时间为2019-09-01 11:17:24

        // 2019-09-01
        LocalDate.now().with(TemporalAdjusters .firstDayOfMonth());
        // 2019-01-01T11:17:24.070
        LocalDateTime.now().with(TemporalAdjusters .firstDayOfYear());
        LocalDate localDate = LocalDate.of(2019, 9, 12);
        String s1 = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        String s2 = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        // 自定义格式化
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String s3 = localDate.format(dateTimeFormatter);
        //解析时间
        LocalDate localDate1 = LocalDate.parse("20190912", DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate localDate2 = LocalDate.parse("2019-09-12", DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static void main(String[] args) {
//        getCurrentDate();
        getDetailDate();
//        handleSpecilDate();
//        compareDate();
//        cycleDate();
//        getCurrentTime();
//        plusHours();
//        nextWeek();
//        minusDate();
//        clock();
//        isBeforeOrIsAfter();
//        getZoneTime();
//        checkCardExpiry();
//        isLeapYear();
//        calcDateDays();
//        getTimestamp();
//        formateDate();
//        strToDate();
    }





}
