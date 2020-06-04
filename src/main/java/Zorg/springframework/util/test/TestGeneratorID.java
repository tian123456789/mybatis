package Zorg.springframework.util.test;

import java.util.UUID;

import org.springframework.util.JdkIdGenerator;

import Zorg.springframework.util.AlternativeJdkIdGenerator;
import Zorg.springframework.util.SimpleIdGenerator;

public class TestGeneratorID {
	
	public static void main(String[] args) {
		JDKGeneratorId();
		Spring_JDK_ID();
		SpringId();
		SpringId2();
	}

	public static void JDKGeneratorId() {
		UUID ID = UUID.randomUUID();
		System.err.println(ID.toString().replace("-", ""));
	}
	
	public static void Spring_JDK_ID() {
		JdkIdGenerator jdkIdGenerator = new JdkIdGenerator();
		UUID ID = jdkIdGenerator.generateId();//其实也是调用的UUID.randomUUID()
		System.err.println(ID.toString().replace("-", ""));
	}
	
	public static void SpringId() {
		AlternativeJdkIdGenerator alternativeJdkIdGenerator = new AlternativeJdkIdGenerator();
		UUID ID = alternativeJdkIdGenerator.generateId();//spring自己的算法
		System.err.println(ID.toString().replace("-", ""));
	}
	
	public static void SpringId2() {
		SimpleIdGenerator simpleIdGenerator = new SimpleIdGenerator();
		UUID ID = simpleIdGenerator.generateId();//AtomicLong实现自增
		ID = simpleIdGenerator.generateId();
		System.err.println(ID.toString().replace("-", ""));
	}

}
