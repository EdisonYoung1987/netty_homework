package gupao.designPatterns.a_Facory.c_AbstractFactory;

import gupao.designPatterns.a_Facory.baseEntity.I_Note;
import gupao.designPatterns.a_Facory.baseEntity.I_Video;

/**
* 抽象工厂是用户的主入口
* 在 Spring 中应用得最为广泛的一种设计模式
* 易于扩展
* Created by Tom.
*/
public interface I_CourseFactory {
	I_Note createNote();
	I_Video createVideo();
}