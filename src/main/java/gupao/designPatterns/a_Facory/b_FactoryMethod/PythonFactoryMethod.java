package gupao.designPatterns.a_Facory.b_FactoryMethod;

import gupao.designPatterns.a_Facory.baseEntity.I_Course;
import gupao.designPatterns.a_Facory.baseEntity.PythonCourse;

public class PythonFactoryMethod implements I_FactoryMethod {

	@Override
	public I_Course create() {
		return new PythonCourse();
	}

}
