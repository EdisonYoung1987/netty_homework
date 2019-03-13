package gupao.designPatterns.c_Prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**一个实体类，需要进行拷贝*/
public class UserEntity implements Serializable {
	int age;
	String name;
	List<String> hobbies;

	/**浅拷贝:拷贝的对象的属性之间存在共享的问题，如hobbies*/
	public UserEntity shallowClone()  {
		UserEntity clone=new UserEntity();
		clone.setAge(this.age);
		clone.setName(this.name);
		clone.setHobbies(this.hobbies); //如果hobbies发生变化，被拷贝和拷贝对象的hobbies都会被影响，所以是浅拷贝
		
		return clone;
	}
	
	/**深克隆：通过序列化和反序列化实现，所有属性都会被拷贝,且如果有很多属性的话，这个深克隆也非常方便，不需要多次get、set
	 * @return null 表示克隆异常*/
	public UserEntity deepClone()  {
		UserEntity clone=null;
		try {
			ByteArrayOutputStream bos=new ByteArrayOutputStream(1024);
			ObjectOutputStream oos=new ObjectOutputStream(bos);
			oos.writeObject(this);
			
			ByteArrayInputStream bis=new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream ois=new ObjectInputStream(bis);
			clone=(UserEntity) ois.readObject();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return clone;
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getHobbies() {
		return hobbies;
	}

	public void setHobbies(List<String> hobbies) {
		this.hobbies = hobbies;
	}

	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		for(String hobby:this.hobbies) {
			sb.append(hobby+"  ");
		}
		return "UserEntity [age=" + age + ", name=" + name + ", hobbies=" + sb + "]";
	}
	
	
}
