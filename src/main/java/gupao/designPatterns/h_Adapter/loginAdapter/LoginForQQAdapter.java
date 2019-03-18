package gupao.designPatterns.h_Adapter.loginAdapter;

public class LoginForQQAdapter implements LoginAdapter {

	@Override
	public boolean support(Object adapter) {
		return adapter instanceof LoginForQQAdapter;
	}

	@Override
	public ResultMsg login(String id, Object adapter) {
		// TODO Auto-generated method stub
		return null;
	}

}
