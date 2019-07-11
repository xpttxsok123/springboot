###@Transaction

- @Transaction放在interface上     `spring不建议这样使用`
- @Transaction放在class上         
- @Transaction放在方法上           `public 可见度的方法上, 放在protected、private或者 package可见度的方法上，也不会报错，不过事务设置不会起作用`

`注意：` 
1. Spring建议我们将@Transaction注解放在在类，方法上
因为注解不能被继承，所以业务接口中标注的@Transactional注解不会被业务实现类继承。所以可能会出现不启动事务的情况

2. Spring默认情况下会对运行期例外(RuntimeException)进行事务回滚。RuntimeException的异常称为unchecked异常，如果遇到checked意外就不回滚

        2.1 将派生于Error或者RuntimeException的异常称为unchecked异常，所有其他的异常成为checked异常，
        
        2.2 如果出现了RuntimeException，就一定是程序员自身的问题，比如说，数组下标越界和访问空指针异常等等，
        只要你稍加留心这些异常都是在编码阶段可以避免的异常。
        
        2.3 常见的几种如下： 
        NullPointerException        - 空指针引用异常
        ClassCastException          - 类型强制转换异常。
        IllegalArgumentException    - 传递非法参数异常。
        ArithmeticException         - 算术运算异常
        ArrayStoreException         - 向数组中存放与声明类型不兼容对象异常
        IndexOutOfBoundsException   - 下标越界异常
        NegativeArraySizeException  - 创建一个大小为负数的数组错误异常
        NumberFormatException       - 数字格式异常
        SecurityException - 安全异常
        UnsupportedOperationException - 不支持的操作异常
        
3. 大部分使用spring的事务都是使用代理的模式，代理实现的事务有一定的局限性：仅有在公有方法上标记的@Transactional有效；
   仅有外部方法调用过程才会被代理截获，事务才会有效，也就是说，一个方法调用本对象的另一个方法，没有通过代理类，事务也就无法生效。
   
4.如何改变默认规则：
    
      　　4.1 让checked例外也回滚：在整个方法前加上 @Transactional(rollbackFor=Exception.class)
      　　4.2 让unchecked例外不回滚： @Transactional(notRollbackFor=RunTimeException.class)
      　　4.3 不需要事务管理的(只查询的)方法：@Transactional(propagation=Propagation.NOT_SUPPORTED)
      　　4.4 如果异常被try｛｝catch｛｝了，事务就不回滚了，如果想让事务回滚必须再往外抛try｛｝catch｛throw RuntimeException
  
