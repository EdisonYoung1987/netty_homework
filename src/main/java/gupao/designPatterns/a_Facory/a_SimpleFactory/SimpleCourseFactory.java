package gupao.designPatterns.a_Facory.a_SimpleFactory;

import gupao.designPatterns.a_Facory.baseEntity.I_Course;
import gupao.designPatterns.a_Facory.baseEntity.JavaCourse;
import gupao.designPatterns.a_Facory.baseEntity.PythonCourse;

/**这就是简单工厂*/
public class SimpleCourseFactory {
	public static I_Course create(String courseName) {
		if("JAVA".equals(courseName)) {
			return new JavaCourse();
		}else if ("PYTHON".equals(courseName)) {
			return new PythonCourse();
		}else {
			return null;
		}
	}
	
	public static I_Course create(Class<? extends I_Course> courseClazz) {
		if(courseClazz!=null) {
			try {
				return (I_Course) courseClazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
		
	}
}
