package gupao.designPatterns.e_Delegate;

public class EmployeeB implements IEmployee {

	@Override
	public void doing(String command) {
		System.out.println("A IS DOING "+command);
	}


}
