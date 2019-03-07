package gupao.designPatterns.a_Facory.b_FactoryMethod;

import gupao.designPatterns.a_Facory.baseEntity.I_Course;
import gupao.designPatterns.a_Facory.baseEntity.JavaCourse;

public class JavaFactoryMethod implements I_FactoryMethod {

	@Override
	public I_Course create() {
		return new JavaCourse();
	}

}
