package gupao.designPatterns.h_Adapter.loginAdapter;

public interface LoginAdapter {
	boolean support(Object adapter);
	ResultMsg login(String id,Object adapter);
}
