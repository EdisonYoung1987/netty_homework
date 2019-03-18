package gupao.designPatterns.j_Observer;

import java.util.Observable;

public class GPer extends Observable {
	private String name = "GPer 生态圈";
	private static GPer gper = null;

	private GPer() {
	}

	public static GPer getInstance() {
		if (null == gper) {
			gper = new GPer();
		}
		return gper;
	}

	public String getName() {
		return name;
	}

	public void publishQuestion(Question question) {
		System.out.println(question.getUserName() + "在" + this.name + "上提交了一个问题。 ");
		setChanged();//这个就是通知？？
		notifyObservers(question);
	}
}
