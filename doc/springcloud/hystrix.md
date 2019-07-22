# hystrix

github：https://github.com/Netflix/Hystrix

Hystrix官方文档： https://github.com/Netflix/Hystrix/wiki

官网配置：https://github.com/Netflix/Hystrix/wiki/Configuration



## hystrix是什么

Hystrix是一个用于处理分布式系统的延迟和容错的开源库，在分布式系统里，许多依赖不可避免的会调用失败，比 如超时、异常等，Hystrix能够保证在一个依赖出问题的情况下，不会导致整体服务失败，避免级联故障，以提高分 布式系统的弹性。 

“断路器”本身是一种开关装置，当某个服务单元发生故障之后，通过断路器的故障监控(类似熔断保险丝)，向调 用方返回一个符合预期的、可处理的备选响应(FallBack)，而不是长时间的等待或者抛出调用方无法处理的异 常，这样就保证了服务调用方的线程不会被长时间、不必要地占用，从而避免了故障在分布式系统中的蔓延，乃至 雪崩。 



Hystrix is designed to do the following:

- Give protection from and control over latency and failure from dependencies accessed (typically over the network) via third-party client libraries.

  在通过第三方客户端访问（通常是通过网络）依赖服务出现高延迟或者失败时，为系统提供保护和控制

- Stop cascading failures in a complex distributed system.

  在分布式系统中防止级联失败

- Fail fast and rapidly recover.

  快速失败（Fail fast）同时能快速恢复

- Fallback and gracefully degrade when possible.

  提供失败回退（Fallback）和优雅的服务降级机制

- Enable near real-time monitoring, alerting, and operational control.

  提供近似实时的监控、报警和运维控制手段

  

## 模式

熔断：错误达到最大阀值，拒绝请求进入下方后端，类似保险丝熔断

限流：限制最大并发，高速路限流，电影院限流

超时：主动超时，设置超时时间

降级：服务降级，服务VIP用户，普通用户跳转别的页面

隔离：隔离不同依赖调用





## 架构思想

凡是依赖都有可能失败

凡是资源都有限制

网络并不可靠

延迟是系统的杀手（用户可能会重试）

弹性思维，在弯曲压缩拉伸之后能够回复原状的能力





## 使用

一般写在client

xml

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```



启动类

加入注解@EnableHystrix 或者@EnableCircuitBreaker(他们之间是一个继承关系，2个注解所描述的内容是完全一样的

```java
@SpringBootApplication
@EnableHystrix
public class UcenterApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(UcenterApplication.class, args);
    }
}
```



业务方法

然后在我们的controller上面加入注解@HystrixCommand(fallbackMethod就是我们刚刚说的方法的名字)

```
@Autowired
private UserClient userClient;

@RequestMapping("/getUserDetail")
@HystrixCommand(fallbackMethod = "fallbackMethod")
public Object getUserDetail(String name){
    return userClient.getUserDetail();
}
```



降级fallbackMethod,返回一个一定不会失败的结果，所以该方法最好简单，不要有业务逻辑

```java
public Object fallbackMethod(String name){
	System.out.println(name);
	return R.error("降级信息");
}
```

















## 官网配置中文

英文： https://github.com/Netflix/Hystrix/wiki/Configuration

```
Execution相关的属性的配置
 hystrix.command.default.execution.isolation.strategy 隔离策略，默认是Thread, 可选Thread| Semaphor 

hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds 命令执行超时时 间，默认1000ms 

hystrix.command.default.execution.timeout.enabled 执行是否启用超时，默认启用true hystrix.command.default.execution.isolation.thread.interruptOnTimeout 发生超时是是否中断， 

默认true 

hystrix.command.default.execution.isolation.semaphore.maxConcurrentRequests 最大并发请求 数，默认10，该参数当使用ExecutionIsolationStrategy.SEMAPHORE策略时才有效。如果达到最大并发请求 数，请求会被拒绝。理论上选择semaphore size的原则和选择thread size一致，但选用semaphore时每次执行 的单元要比较小且执行速度快(ms级别)，否则的话应该用thread。 semaphore应该占整个容器(tomcat)的线程 池的一小部分。 Fallback相关的属性 这些参数可以应用于Hystrix的THREAD和SEMAPHORE策略 

hystrix.command.default.fallback.isolation.semaphore.maxConcurrentRequests 如果并发数达到 该设置值，请求会被拒绝和抛出异常并且fallback不会被调用。默认10 

hystrix.command.default.fallback.enabled 当执行失败或者请求被拒绝，是否会尝试调用 hystrixCommand.getFallback() 。默认true 

Circuit Breaker相关的属性
 hystrix.command.default.circuitBreaker.enabled 用来跟踪circuit的健康性，如果未达标则让 request短路。默认true 

hystrix.command.default.circuitBreaker.requestVolumeThreshold 一个rolling window内最小的 请 求数。如果设为20，那么当一个rolling window的时间内(比如说1个rolling window是10秒)收到19个请 求， 即使19个请求都失败，也不会触发circuit break。默认20 

hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds 触发短路的时间值，当该值设 为5000时，则当触发circuit break后的5000毫秒内都会拒绝request，也就是5000毫秒后才会关闭circuit。 默认5000 

hystrix.command.default.circuitBreaker.errorThresholdPercentage错误比率阀值，如果错误率>=该 值，circuit会被打开，并短路所有请求触发fallback。默认50 

hystrix.command.default.circuitBreaker.forceOpen 强制打开熔断器，如果打开这个开关，那么拒绝所 有request，默认false 

hystrix.command.default.circuitBreaker.forceClosed 强制关闭熔断器 如果这个开关打开，circuit将 一直关闭且忽略circuitBreaker.errorThresholdPercentage 

Metrics相关参数 

hystrix.command.default.metrics.rollingStats.timeInMilliseconds 设置统计的时间窗口值的，毫秒 值，circuit break 的打开会根据1个rolling window的统计来计算。若rolling window被设为10000毫秒， 则rolling window会被分成n个buckets，每个bucket包含success，failure，timeout，rejection的次数 的统计信息。默认10000 

hystrix.command.default.metrics.rollingStats.numBuckets 设置一个rolling window被划分的数 量，若numBuckets=10，rolling window=10000，那么一个bucket的时间即1秒。必须符合
 rolling window % numberBuckets == 0。默认10 

hystrix.command.default.metrics.rollingPercentile.enabled 执行时是否enable指标的计算和跟踪， 默认true 

hystrix.command.default.metrics.rollingPercentile.timeInMilliseconds 设置rolling percentile window的时间，默认60000 

hystrix.command.default.metrics.rollingPercentile.numBuckets 设置rolling percentile window的numberBuckets。逻辑同上。默认6 

hystrix.command.default.metrics.rollingPercentile.bucketSize 如果bucket size=100， window =10s，若这10s里有500次执行，只有最后100次执行会被统计到bucket里去。增加该值会增加内存开销以 及排序 的开销。默认100 

hystrix.command.default.metrics.healthSnapshot.intervalInMilliseconds 记录health 快照(用 来统计成功和错误绿)的间隔，默认500ms 

Request Context 相关参数
 hystrix.command.default.requestCache.enabled 默认true，需要重载getCacheKey()，返回null时不 

缓存
 hystrix.command.default.requestLog.enabled 记录日志到HystrixRequestLog，默认true 

Collapser Properties 相关参数
 hystrix.collapser.default.maxRequestsInBatch 单次批处理的最大请求数，达到该数量触发批处理，默 

认 Integer.MAX_VALU
 hystrix.collapser.default.timerDelayInMilliseconds 触发批处理的延迟，也可以为创建批处理的时间 

+该值，默认10
 hystrix.collapser.default.requestCache.enabled 是否对HystrixCollapser.execute() and 

HystrixCollapser.queue()的cache，默认true 

ThreadPool 相关参数 

线程数默认值10适用于大部分情况(有时可以设置得更小)，如果需要设置得更大，那有个基本得公式可以 follow:
 requests per second at peak when healthy × 99th percentile latency in seconds + some breathing room 每秒最大支撑的请求数 (99%平均响应时间 + 缓存值) 比如:每秒能处理1000个请求，99%的请 求响应时间是60ms，那么公式是: 1000 (0.060+0.012) 

基本得原则时保持线程池尽可能小，他主要是为了释放压力，防止资源被阻塞。 当一切都是正常的时候，线程池一般 仅会有1到2个线程激活来提供服务 

hystrix.threadpool.default.coreSize 并发执行的最大线程数，默认10 

hystrix.threadpool.default.maxQueueSize BlockingQueue的最大队列数，当设为-1，会使用 

SynchronousQueue，值为正时使用LinkedBlcokingQueue。该设置只会在初始化时有效，之后不能修改 threadpool的queue size，除非reinitialising thread executor。默认-1。 

hystrix.threadpool.default.queueSizeRejectionThreshold 即使maxQueueSize没有达到，达到 queueSizeRejectionThreshold该值后，请求也会被拒绝。因为maxQueueSize不能被动态修改，这个参数将允 许我们动态设置该值。if maxQueueSize == •1，该字段将不起作用 hystrix.threadpool.default.keepAliveTimeMinutes 如果corePoolSize和maxPoolSize设成一样(默 认 实现)该设置无效。如果通过plugin(https://github.com/Netflix/Hystrix/wiki/Plugins)使用自定 义 实现，该设置才有用，默认1. 

hystrix.threadpool.default.metrics.rollingStats.timeInMilliseconds 线程池统计指标的时间， 默 认10000 

hystrix.threadpool.default.metrics.rollingStats.numBuckets 将rolling window划分为n个 buckets，默认10 
```

