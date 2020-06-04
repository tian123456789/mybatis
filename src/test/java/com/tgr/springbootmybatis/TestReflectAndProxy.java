package com.tgr.springbootmybatis;


import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


import org.junit.Test;


public class TestReflectAndProxy {

	@Test
	public void test1ChuanTong() {
		//每个类都会产生一个对应的Class对象，也就是保存在.class文件。
		//所有类都是在对其第一次使用时，动态加载到JVM的，当程序创建一个对类的静态成员的引用时，
		//就会加载这个类。Class对象仅在需要的时候才会加载，static初始化是在类加载时进行的。
		System.out.println(A.name);
		//A的静态代码块
		//我的名字
	}
	
	/**
	 * 测试class对象
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@Test 
	public void test2ClassObject() throws ClassNotFoundException {
		//类加载器首先会检查这个类的Class对象是否已被加载过，如果尚未加载，
		//默认的类加载器就会根据类名查找对应的.class文件。

		//想在运行时使用类型信息，必须获取对象(比如类Base对象)的Class对象的引用，
		//使用功能Class.forName(“Base”)可以实现该目的，或者使用base.class。注意，
		//有一点很有趣，使用功能”.class”来创建Class对象的引用时，不会自动初始化该Class对象，
		//使用forName()会自动初始化该Class对象。为了使用类而做的准备工作一般有以下3个步骤：

		//加载：由类加载器完成，找到对应的字节码，创建一个Class对象
		//链接：验证类中的字节码，为静态域分配空间
		//初始化：如果该类有超类，则对其初始化，执行静态初始化器和静态初始化块 
		Class clazz1 = A.class;//不会初始化静态代码块
		System.out.println("--------");
		Class clazz2 = Class.forName("com.tgr.springbootmybatis.A");//会初始化静态代码块
	}
	
	@Test
	public void test3ClassTypeCast() {
		//编译器将检查类型向下转型是否合法，如果不合法将抛出异常。
		//向下转换类型前，可以使用instanceof判断。
		A a = new B();//B a = new B();结果一样
		if(a instanceof B) {
			System.out.println("ok");
		}else {
			System.out.println("no");
		}
		//A的静态代码块
		//B的静态代码块
		//A的构造函数
		//B的构造函数
		//ok
	}
	
	@Test
	public void test4RefelectRutimeClassInfo() throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException {
		//如果不知道某个对象的确切类型，RTTI可以告诉你，但是有一个前提：
		//这个类型在编译时必须已知，这样才能使用RTTI来识别它。
		//Class类与java.lang.reflect类库一起对反射进行了支持，
		//该类库包含Field、Method和Constructor类，
		//这些类的对象由JVM在启动时创建，用以表示未知类里对应的成员。
		//这样的话就可以使用Contructor创建新的对象，
		//用get()和set()方法获取和修改类中与Field对象关联的字段，
		//用invoke()方法调用与Method对象关联的方法。
		//另外，还可以调用getFields()、getMethods()和getConstructors()等许多便利的方法，
		//以返回表示字段、方法、以及构造器对象的数组，
		//这样，对象信息可以在运行时被完全确定下来，
		//而在编译时不需要知道关于类的任何事情。

		//反射机制并没有什么神奇之处，
		//当通过反射与一个未知类型的对象打交道时，
		//JVM只是简单地检查这个对象，看它属于哪个特定的类。
		//因此，那个类的.class对于JVM来说必须是可获取的，
		//要么在本地机器上，要么从网络获取。所以对于RTTI和反射之间的真正区别只在于：
			//RTTI，编译器在编译时打开和检查.class文件
			//反射，运行时打开和检查.class文件 
		C c = new C("ccc", "33");
		Class<? extends C> clazz = c.getClass();
		
		Field[] fields = clazz.getDeclaredFields();//获取声明的字段
		for(Field field : fields) {
			String key = field.getName();
			PropertyDescriptor descriptor = new PropertyDescriptor(key, clazz);
			Method method = descriptor.getReadMethod();//获取key的get方法
			Object value = method.invoke(c);//执行getKey方法
			
			System.out.println(key+":"+value);
		}
		
		Method methodGetName;
		try {
			methodGetName = clazz.getMethod("getName");
			System.out.println("invokeMethod=="+methodGetName.invoke(c));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		//以上通过getReadMethod()方法调用类的get函数，
		//可以通过getWriteMethod()方法来调用类的set方法。通常来说，我们不需要使用反射工具，
		//但是它们在创建动态代码会更有用，反射在Java中用来支持其他特性的，例如对象的序列化和JavaBean等
		
	}
	
	/**
	 * 测试动态代理
	 */
	@Test
	public void test5DynProxy() {
		
		//https://www.cnblogs.com/aspirant/p/9036805.html
		
		 //代理模式是为了提供额外或不同的操作，而插入的用来替代”实际”对象的对象，
		 //这些操作涉及到与”实际”对象的通信，因此代理通常充当中间人角色。Java的动态代理比代理的思想更前进了一步，
		 //它可以动态地创建并代理并动态地处理对所代理方法的调用。在动态代理上所做的所有调用都会被重定向到单一的调用处理器上，
		 //它的工作是揭示调用的类型并确定相应的策略。 

		 //学习Spring的时候，我们知道Spring主要有两大思想，一个是IoC，另一个就是AOP，对于IoC，它利用的是反射机制，
		 //依赖注入就不用多说了，
		 //而对于Spring的核心AOP来说，使用了动态代理，其实底层也是反射。
		 //我们不但要知道怎么通过AOP来满足的我们的功能，我们更需要学习的是其底层是怎么样的一个原理，
		 //而AOP的原理就是java的动态代理机制，所以本篇随笔就是对java的动态机制进行一个回顾。

		 //在java的动态代理机制中，有两个重要的类或接口，一个是 InvocationHandler(Interface)、另一个则是 Proxy(Class)，
		 //这一个类和接口是实现我们动态代理所必须用到的。首先我们先来看看java的API帮助文档是怎么样对这两个类进行描述的：

		 //InvocationHandler:
		 //每一个动态代理类都必须要实现InvocationHandler这个接口，并且每个代理类的实例都关联到了一个handler，
		 //当我们通过代理对象调用一个方法的时候，这个方法的调用就会被转发为由InvocationHandler这个接口的 invoke 方法来进行调用。
		 //我们来看看InvocationHandler这个接口的唯一一个方法 invoke 方法：

		 //Object invoke(Object proxy, Method method, Object[] args) throws Throwable
		 //我们看到这个方法一共接受三个参数，那么这三个参数分别代表什么呢？

		 //Object invoke(Object proxy, Method method, Object[] args) throws Throwable

		 	//proxy:　　指代我们所代理的那个真实对象
		 	//method:　　指代的是我们所要调用真实对象的某个方法的Method对象
		 	//args:　　指代的是调用真实对象某个方法时接受的参数
		
		//接下来我们来看看Proxy这个类：
		//Proxy这个类的作用就是用来动态创建一个代理对象的类，它提供了许多的方法，
		//但是我们用的最多的就是 newProxyInstance 这个方法：
		//public static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces,  InvocationHandler h)  throws IllegalArgumentException
		//这个方法的作用就是得到一个动态的代理对象，其接收三个参数，我们来看看这三个参数所代表的含义：
		//loader:　　一个ClassLoader对象，定义了由哪个ClassLoader对象来对生成的代理对象进行加载
		//interfaces:　　一个Interface对象的数组，表示的是我将要给我需要代理的对象提供一组什么接口，如果我提供了一组接口给它，那么这个代理对象就宣称实现了该接口(多态)，这样我就能调用这组接口中的方法了
		//h:　　一个InvocationHandler对象，表示的是当我这个动态代理对象在调用方法的时候，会关联到哪一个InvocationHandler对象上
		
		
	}
	
}
class C{
	//private static final long serialVersionUID = 1L;
	private String name;
	private String age;
	public C(String name, String age) {super();this.name = name;this.age = age;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public String getAge() {return age;}
	public void setAge(String age) {this.age = age;}
	public static String get1() {
		return "111";
	}
	
}
class B extends A{
	static {
		System.out.println("B的静态代码块");
	}
	public B() {
		System.out.println("B的构造函数");
	}
	
}
class A{
	public static String name = "我的名字";
	
	static {
		System.out.println("A的静态代码块");
	}
	
	public A() {
		System.out.println("A的构造函数");
	}
}
