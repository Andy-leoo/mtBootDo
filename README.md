# myBootDo

02/27 初始化项目，springboot 整合mybatis
    
    Q :这里出了一个小问题，
        启动的时候报错 ：
        Error starting ApplicationContext. To display the conditions report re-run your application with 'debug' enabled.
        2019-02-27 15:20:23.710 ERROR 22908 --- [           main] o.s.b.d.LoggingFailureAnalysisReporter   : 
        
        ***************************
        APPLICATION FAILED TO START
        ***************************
        
        Description:
        
        Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.
        
        Reason: Failed to determine a suitable driver class
    
    A :这个问题是，你的pom 中加入了mybatis的依赖 而你现在并没有使用到，目前来看你先 去除掉mybatis的依赖，然后再次启动
    
    
02/28 添加 mysql 数据库驱动，以及线程池c3p0 依赖
           创建 mybatis-config.xml文件
    
03/03 创建dao层，创建 service 层，config.servier 添加事务管理
    
03/04 创建 controller层， 创建统一的异常处理类

03/05  入门小程序

      百度  小程序文档 -- API 小程序  https://developers.weixin.qq.com/
      
03/06 加入devtools 热部署
    
    （1） devtools可以实现页面热部署（即页面修改后会立即生效，这个可以直接在application.properties文件中配置spring.thymeleaf.cache=false来实现），
    实现类文件热部署（类文件修改后不会立即生效），实现对属性文件的热部署。
    即devtools会监听classpath下的文件变动，并且会立即重启应用（发生在保存时机），注意：因为其采用的虚拟机机制，该项重启是很快的
    （2）配置了后在修改java文件后也就支持了热启动，不过这种方式是属于项目重启（速度比较快的项目重启），会清空session中的值，也就是如果有用户登陆的话，项目重启后需要重新登陆。
    
    默认情况下，/META-INF/maven，/META-INF/resources，/resources，/static，/templates，/public这些文件夹下的文件修改不会使应用重启，但是会重新加载（devtools内嵌了一个LiveReload server，当资源发生改变时，浏览器刷新）。
    
    devtools的配置
    
    在application.properties中配置spring.devtools.restart.enabled=false，此时restart类加载器还会初始化，但不会监视文件更新。
    在SprintApplication.run之前调用System.setProperty(“spring.devtools.restart.enabled”, “false”);可以完全关闭重启支持，配置内容：
    
    #热部署生效
    spring.devtools.restart.enabled: true
    #设置重启的目录
    #spring.devtools.restart.additional-paths: src/main/java
    #classpath目录下的WEB-INF文件夹内容修改不重启
    spring.devtools.restart.exclude: WEB-INF/**

  03/07 整合redis
  
        创建 redis工具包  一些简单的 方法
  
  03/09 整合定时任务
  
        使用注解@EnableScheduling 开启定时任务  在application类中
        再定义一个 Task类 使用 @Component 扫描  使用@Scheduled 定义 扫描时间
        支持 corn 表达式   ：生成地址  cron.qqe2.com
        spring boot 不支持年的表达式
        
        
  03/14 静态资源处理
        
        在web开发中，静态资源的访问是必不可少的，如：图片、js、css 等资源的访问。
        spring Boot 对静态资源访问提供了很好的支持，基本使用默认配置就能满足开发需求。
        
        一、默认静态资源映射
        Spring Boot 对静态资源映射提供了默认配置
        
        Spring Boot 默认将 /** 所有访问映射到以下目录：
        classpath:/static
        classpath:/public
        classpath:/resources
        classpath:/META-INF/resources
        如：在resources目录下新建 public、resources、static 三个目录，并分别放入 a.jpg b.jpg c.jpg 图片
        
        浏览器分别访问：
        http://localhost:8080/a.jpg
        http://localhost:8080/b.jpg
        http://localhost:8080/c.jpg
        均能正常访问相应的图片资源。那么说明，Spring Boot 默认会挨个从 public resources static 里面找是否存在相应的资源，如果有则直接返回。
        
        二、自定义静态资源映射
        在实际开发中，可能需要自定义静态资源访问路径，那么可以继承WebMvcConfigurerAdapter来实现。
        
        第一种方式：静态资源配置类
            WebMvcConfig 
        第二种方式：在application.properties配置  (好像无用)
            在application.properties中添加配置：
            spring.mvc.static-path-pattern=/static/**
            
            注意：通过spring.mvc.static-path-pattern这种方式配置，会使Spring Boot的默认配置失效，也就是说，/public /resources 等默认配置不能使用。
            配置中配置了静态模式为/static/，就只能通过/static/来访问。
            
            
  03/15 整合 thymeleaf freemarker模板
        
        1.pom 添加依赖
        2.controller 编写
        3.编写html代码
        4.可修改模板的默认配置
        
  03/16 整合swagger2 - 用于生成RESTful 接口文档
        
        Swagger 是一个规范和完整的框架，用于生成、描述、调用和可视化 RESTful 风格的 Web 服务。
        总体目标是使客户端和文件系统作为服务器以同样的速度来更新。
        文件的方法，参数和模型紧密集成到服务器端的代码，允许API来始终保持同步。
        
        1.添加pom依赖
        2.创建实体
        其中 .apis(RequestHandlerSelectors.basePackage("com.sam.demo.controller")) 指定了以扫描包的方式进行，会把com.sam.demo.controller包下的controller都扫描到。
        
        
        注意1：@ApiImplicitParam 和 @ApiParam 方式均能指定参数规则。
        注意2：使用@ApiImplicitParam的时候，需要指定paramType。
        附：Swagger2相关注解介绍
        
        @Api：用在类上，说明该类的作用
        @ApiOperation：用在方法上，说明方法的作用
        @ApiImplicitParams：用在方法上包含一组参数说明
        @ApiImplicitParam：用在 @ApiImplicitParams 注解中，指定一个请求参数的各个方面
            paramType：参数放在哪个地方
                · header --> 请求参数的获取：@RequestHeader
                · query -->请求参数的获取：@RequestParam
                · path（用于restful接口）--> 请求参数的获取：@PathVariable
                · body（不常用）
                · form（不常用）
            name：参数名
            dataType：参数类型
            required：参数是否必须传
            value：参数的意思
            defaultValue：参数的默认值
        @ApiResponses：用于表示一组响应
        @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
            code：数字，例如400
            message：信息，例如"请求参数没填好"
            response：抛出异常的类
        @ApiModel：描述一个Model的信息（这种一般用在post创建的时候，使用@RequestBody这样的场景，请求参数无法使用@ApiImplicitParam注解进行描述的时候）
        @ApiModelProperty：描述一个model的属性
        
        启动应用，浏览器访问：http://localhost:8090/swagger-ui.html
        
  添加发送邮箱
       
       使用场景
            1.注册验证
            2.网站营销
            3.安全最后一道防线
            4.提醒，监控告警
            5.触发机制
       发送原理
            邮件传输协议：SMTP POP3协议
            内容不断发展：IMAP Mime协议
            
       整合简单的文本邮件
            1.引入相关jar
            2.配置邮箱参数
                这里注意一下，我这里使用 163 邮箱向 QQ邮箱发送邮件，所以我的163邮箱需要 设置他的三方授权码，
                具体操作： 登入163-设置-邮箱设置-客户端授权密码  在配置文件中设置的 password 就是刚才设置的 客户端授权密码
            3.simpleMailMessage
            4.javaMailSendle发送
            
       html邮件
       附件邮件
       图片邮件
       模板邮件
  
  springboot2.X 整合 ElasticSearch5.X 
     
        简书地址放上： https://www.jianshu.com/p/ed03b42ee3d1
