package gupao.netty.homeWork.oj8k;

import org.apache.ibatis.jdbc.Null;

public class E_Volatile_Singleton {
    //此处volatile可以防止某线程执行e_volatile_singleton=new E_Volatile_Singleton();时先将e_volatile_singleton
    //指向了刚刚分配、未初始化的对象的地址，导致其他线程检查是否为null时得到不为null的结论，继而访问未完成初始化的
    //对象
    private static volatile E_Volatile_Singleton e_volatile_singleton;
    private E_Volatile_Singleton(){};

    public static E_Volatile_Singleton getSingleton(){
        if(e_volatile_singleton== null){
            synchronized (E_Volatile_Singleton.class){
                if(e_volatile_singleton==null){
                    e_volatile_singleton=new E_Volatile_Singleton();
                }
            }
        }
        return e_volatile_singleton;
    }
}
