package com.sym.proxy;

import com.sym.proxy.cglib.proxy.BaseCallbackFilter;
import com.sym.proxy.cglib.proxy.FirstMethodInterceptor;
import com.sym.proxy.cglib.proxy.SecondMethodInterceptor;
import com.sym.proxy.cglib.proxy.entity.BaseClass;
import com.sym.proxy.cglib.proxy.entity.PropertyEntity;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import net.sf.cglib.proxy.NoOp;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * cglib动态代理实现的测试类
 *
 * @author shenym
 * @date 2019/12/29 10:04
 */

public class ProxyTest {

    @Before
    public void init() {
        // 将cglib动态生成的class文件输出到指定目录下
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "dynamic_classes");
    }

    /**
     * 无参构造方法的类的动态代理测试,
     * 只设置一个{@link Callback}
     */
    @Test
    public void withNoArgsTest() {
        // Enhancer是Cglib的一个字节码增强器，用于生成代理对象
        Enhancer enhancer = new Enhancer();
        // 设置被代理类(即原始类)
        enhancer.setSuperclass(BaseClass.class);
        // 设置拦截器
        enhancer.setCallback(new FirstMethodInterceptor());
        // 当方法在构造方法中被调用时, 设置为false, cglib就不会拦截它
        enhancer.setInterceptDuringConstruction(false);
        // 生成代理对象, 这种方式要求原始类构造方法不带任何参数
        BaseClass baseClass = (BaseClass) enhancer.create();
        // 方法调用
        baseClass.doSomething();
    }

    /**
     * 有参构造方法的类的动态代理测试,
     * 只设置一个{@link Callback}
     */
    @Test
    public void withArgsTest() {
        // Enhancer是Cglib的一个字节码增强器，用于生成代理对象
        Enhancer enhancer = new Enhancer();
        // 设置被代理类(即原始类)
        enhancer.setSuperclass(PropertyEntity.class);
        // 设置拦截器
        enhancer.setCallback(new SecondMethodInterceptor());
        // 当方法在构造方法中被调用时, 设置为false, cglib就不会拦截它
        enhancer.setInterceptDuringConstruction(false);
        // 生成代理对象, 由于 PropertyEntity 没有无参构造方法, 所以必须在创建它的时候手动指定
        // 设置参数Class类型数组, 设置参数值数组
        Class[] argsTypeArray = new Class[]{int.class, String.class};
        Object[] argsValueArray = new Object[]{110, "test"};
        PropertyEntity entity = (PropertyEntity) enhancer.create(argsTypeArray, argsValueArray);
        // 方法调用
        String info = entity.getInfo();
        System.out.println(info);
    }

    /**
     * 拦截器数组{@link Callback}测试
     */
    @Test
    public void callbackArrayTest() {
        // 表示一组代理逻辑
        Callback[] callbacks = new Callback[]{new FirstMethodInterceptor(), new SecondMethodInterceptor(), NoOp.INSTANCE};
        // 使用回调过滤器也是需要Enhancer来生产代理对象的
        Enhancer enhancer = new Enhancer();
        //设置被代理的基本类
        enhancer.setSuperclass(BaseClass.class);
        //设置回调过滤器
        enhancer.setCallbackFilter(new BaseCallbackFilter());
        //设置回调时作选择的Callback数组
        enhancer.setCallbacks(callbacks);
        // 生成代理对象
        BaseClass baseClass = (BaseClass) enhancer.create();
        //执行不同的方法，就会有不同的业务逻辑了
        baseClass.doRun();
        System.out.println();
        baseClass.doSomething();
    }


    /**
     * cglib 不能拦截器带有 final 关键字的方法, 没办法拦截到
     */
    @Test
    public void finalMethodTest() {
        //Enhancer是Cglib的一个字节码增强器，用于生成代理对象
        Enhancer enhancer = new Enhancer();
        //设置被代理类(即基本类)
        enhancer.setSuperclass(BaseClass.class);
        // 设置拦截器
        enhancer.setCallback(new FirstMethodInterceptor());
        //生产代理对象
        BaseClass baseClass = (BaseClass) enhancer.create();
        // 会发现拦截器根本不能拦截 toJson() 方法, 因为它是 final 不可重写的
        // 而 cglib 底层使用的字节码增强工具为基类生成子类, 自然就拦截不到了
        String s = baseClass.toJson();
        System.out.println(s);
    }


    /**
     * InterfaceMaker 可以将某个类Class, 将其转换成接口型, 返回新的Class
     */
    @Test
    public void interfaceMakerTest() {
        InterfaceMaker interfaceMaker = new InterfaceMaker();
        // 抽取某个类的方法生成接口方法
        interfaceMaker.add(BaseClass.class);
        Class<?> targetInterface = interfaceMaker.create();
        for (Method method : targetInterface.getMethods()) {
            System.out.print(method.getName() + "() ");
        }
        System.out.println();
        System.out.println(targetInterface.isInterface());
    }
}
