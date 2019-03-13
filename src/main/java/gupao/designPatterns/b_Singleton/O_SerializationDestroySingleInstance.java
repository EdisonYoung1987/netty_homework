package gupao.designPatterns.b_Singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**通过序列化和反序列化的方式破坏单例*/
public class O_SerializationDestroySingleInstance {

	public static void main(String[] args)  {
		//获取内部类的单例对象
//		Object singleInstance=A_HungrySingleInstance2.getSingleInstance();

		//获取枚举类的单例对象
		D_LazySingleton_StaticInnerClass singleInstance=D_LazySingleton_StaticInnerClass.getSingleInstance();

		
		//将对象序列化输出到文件，然后反序列化出来
		try {
			//将对象序列化输出到文件
			FileOutputStream fos=new FileOutputStream("D:\\tmp\\obj");
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			
			oos.writeObject(singleInstance);
			oos.flush();
			oos.close();
			
			//反序列化出来
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream("D:\\tmp\\obj"));
			D_LazySingleton_StaticInnerClass singleInstance2=(D_LazySingleton_StaticInnerClass)ois.readObject();
			
			ObjectInputStream ois2=new ObjectInputStream(new FileInputStream("D:\\tmp\\obj"));
			Object singleInstance3=ois2.readObject();
			
			System.out.println("两对象是否相同："+(singleInstance==singleInstance2));//true
			System.out.println("两对象是否相同："+(singleInstance3==singleInstance2));//true
			
//			System.out.println("两属性对象是否相同："+(singleInstance2.getData()==singleInstance.getData()));//true

		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
