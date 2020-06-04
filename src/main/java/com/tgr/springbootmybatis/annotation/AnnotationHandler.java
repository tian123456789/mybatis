package com.tgr.springbootmybatis.annotation;

import java.lang.reflect.Field;

public class AnnotationHandler {
	
	public static void getUseInfo(Class<?> clazz) {//字段
		String usedFiledName = "被注解的字段名";
		
		Field[] fields = clazz.getDeclaredFields();
		
		for(Field field : fields) {
			if(field.isAnnotationPresent(MyAnnotation.class)) {
				MyAnnotation myAnnotation = field.getAnnotation(MyAnnotation.class);
				usedFiledName = usedFiledName+myAnnotation.value();
				System.out.println("usedFiledName = "+usedFiledName);
			}
		}
	}
}
