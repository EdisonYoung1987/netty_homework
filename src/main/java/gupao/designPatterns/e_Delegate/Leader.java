package gupao.designPatterns.e_Delegate;

import java.util.HashMap;
import java.util.Map;

/**委派的中间类：项目经理<br>
 * 主要工作：根据Boss传达的指令将具体任务交给相应Employee去执行*/
public class Leader implements IEmployee {

	private Map<String, IEmployee> targets = new HashMap<String, IEmployee>();

	public Leader() {
		targets.put("加密", new EmployeeA());
		targets.put("登录", new EmployeeB());	
	}

	// 项目经理自己不干活
	public void doing(String command) {
		System.out.println("Leader 分派任务");
		targets.get(command).doing(command);
	}

}
