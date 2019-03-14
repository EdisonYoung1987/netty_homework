package gupao.designPatterns.e_Delegate;

public class EmployeeA implements IEmployee {

	@Override
	public void doing(String command) {
		System.out.println("A IS DOING "+command);
	}

}
