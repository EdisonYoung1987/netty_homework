package gupao.designPatterns.h_Adapter;

import gupao.designPatterns.h_Adapter.dcAdapter.AC220;
import gupao.designPatterns.h_Adapter.dcAdapter.IDC5;
import gupao.designPatterns.h_Adapter.dcAdapter.PowerAdapter;
import gupao.designPatterns.h_Adapter.loginAdapter.SigninForThirdService;

public class T_Test {

	public static void main(String[] args) {
		IDC5 adapter=new PowerAdapter(new AC220());
		System.out.println(adapter.outputDC5V());
	
		SigninForThirdService service = new SigninForThirdService();
		//不改变原来的代码， 也要能够兼容新的需求
		//还可以再加一层策略模式
		service.loginForQQ("sdfgdgfwresdf9123sdf");
	}

}
