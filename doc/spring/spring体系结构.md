# spring体系结构

## 体系结构

![springtxjg](images/springtxjg.png)

**Container:**

spring-Core 、spring-beans 包含了框架的核心实现，包括IOC依懒注入等特性

spring-context 在spring-core 基础上构建它提供一种框架方式访问对象的方法



**WEB**

spring-web 提供了基本的面向WEB的功能，多文件上传、使用Servlet监听器的IOC容器初始化。一个面向WEB的应用层上下文

web-mvc: 包含MVC 和rest 服务相关组件



**AOP**

spring-aop 提供了面向切面编程的丰富支持

spring-aspects 提供对AspectJ的支持，以便可以方便的将面向方面的功能集成进IDE中，比如Eclipse AJDT。

instrumentation 提供对javaagent 的支持和类加载器

instrumentation-tomcat 专门针对tomcat 进行类转换与加载管理



DATA

spring-jdbc: 提供了一个JDBC抽象层

spring-tx: 编程式和声明式事物管理

spring-orm： 

spring-oxm：

spring-jms：

spring-redis：







































