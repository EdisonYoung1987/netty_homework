package gupao.designPatterns.h_Adapter.loginAdapter;

/**注册和登录类*/
public class SignUpInService {
	public ResultMsg regist(String user,String passwd) {
		return new ResultMsg(200, "注册成功", new Member());
	}
	
	public ResultMsg login(String user,String passwd) {
		return null;
	}
}
