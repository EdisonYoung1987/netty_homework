package gupao.designPatterns.b_singleton;

/**通过枚举的方式实现单例，实际上是饿汉式单例*/
public enum E_HungrySingleInstance_Enum {
	INSTANCE;
	
	//构造方法的参数和前面的INSTANCE要一致，如果构造方法参数是(String,int),则INSTANCE("XXX",X)
	private E_HungrySingleInstance_Enum() {}
	
	public static E_HungrySingleInstance_Enum getSingleInstance() {
		return INSTANCE;
	}
	
	private Object data;//增加一个属性对象，看该属性是否也一样
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
