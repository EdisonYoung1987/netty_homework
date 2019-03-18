package gupao.designPatterns.h_Adapter.dcAdapter;

/**现有交流电220v*/
public class AC220 {
	public int outputAC220V(){
		int output=220;
		System.out.println("输出220v交流电");
		return 220;
	}
}
