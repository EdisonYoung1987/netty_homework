package gupao.netty.homeWork.lesson2;

public class TestDemo {

	public static void main(String[] args) {
		String str="---123";
		int capacity=0;
		if (str.substring(0, 3).equals("---")) {
			capacity = Integer.parseInt(str.substring(3, 4));
			System.out.println(capacity);
		} else if (str.substring(0, 2).equals("--")) {
			capacity = Integer.parseInt(str.substring(2, 4));
			System.out.println(capacity);
		} else if (str.substring(0, 1).equals("-")) {
			capacity = Integer.parseInt(str.substring(1, 4));
			System.out.println(capacity);
		}
	}

}
