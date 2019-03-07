package gupao.designPatterns.a_Facory.c_AbstractFactory;

import gupao.designPatterns.a_Facory.baseEntity.I_Note;
import gupao.designPatterns.a_Facory.baseEntity.I_Video;
import gupao.designPatterns.a_Facory.baseEntity.JavaNote;
import gupao.designPatterns.a_Facory.baseEntity.JavaVideo;

public class JavaCourseFactory implements I_CourseFactory {

	@Override
	public I_Note createNote() {
		return new JavaNote();
	}

	@Override
	public I_Video createVideo() {
		return new JavaVideo();
	}

}
