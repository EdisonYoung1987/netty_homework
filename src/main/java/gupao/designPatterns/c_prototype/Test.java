package gupao.designPatterns.c_prototype;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		UserEntity user=new UserEntity();
		user.setAge(18);
		user.setName("张三");
		
		List<String> hobbies=new ArrayList<>();
		hobbies.add("乒乓球");
		hobbies.add("反恐精英CS");
		
		user.setHobbies(hobbies);
		
		//浅克隆
		UserEntity shallowClone=user.shallowClone();
		System.out.println("浅克隆对象是否相等："+(user==shallowClone));
		System.out.println("浅克隆对象内容："+shallowClone.toString());
		
		user.hobbies.add("守望先锋");
		System.out.println("原对象引用属性改变后，浅克隆对象内容改变："+shallowClone.toString());//跟着改变
		
		//深克隆
		UserEntity deepClone=user.deepClone();
		System.out.println("深克隆对象是否相等："+(user==deepClone));
		System.out.println("深克隆对象内容："+deepClone.toString());
		
		user.hobbies.add("美食");
		System.out.println("原对象引用属性改变后，深克隆对象内容不变："+deepClone.toString());//跟着改变


	}

}
