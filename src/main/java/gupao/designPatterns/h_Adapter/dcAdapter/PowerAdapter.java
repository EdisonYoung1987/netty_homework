package gupao.designPatterns.h_Adapter.dcAdapter;

/**适配器：将传入的220v转为5v*/
public class PowerAdapter implements IDC5 {
	private AC220 ac220;
	public PowerAdapter(AC220 ac220){
		this.ac220 = ac220;
	}
	
	@Override
	public int outputDC5V() {
		int orgOutput=ac220.outputAC220V(); //原输出电压
		int output=orgOutput/44;//适配器转换
		return output;
	}

}
