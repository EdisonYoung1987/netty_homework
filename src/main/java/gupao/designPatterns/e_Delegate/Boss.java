package gupao.designPatterns.e_Delegate;

public class Boss {

	public static void main(String[] args) {
		Leader leader=new Leader();
		leader.doing("加密");
		leader.doing("登录");
	}

}
