package gupao.designPatterns.d_Proxy.ii_SelfJdkProxy;
import gupao.designPatterns.d_Proxy.service.I_Person;
import java.lang.reflect.*;
public class $Proxy0 implements gupao.designPatterns.d_Proxy.service.I_Person{
GPInvocationHandler h;
public $Proxy0(GPInvocationHandler h) { 
this.h = h;}
public void hardWorking() {
try{
Method m = gupao.designPatterns.d_Proxy.service.I_Person.class.getMethod("hardWorking",new Class[]{});
this.h.invoke(this,m,new Object[]{});
}catch(Error _ex) { }catch(Throwable e){
throw new UndeclaredThrowableException(e);
}return ;}public void findLover() {
try{
Method m = gupao.designPatterns.d_Proxy.service.I_Person.class.getMethod("findLover",new Class[]{});
this.h.invoke(this,m,new Object[]{});
}catch(Error _ex) { }catch(Throwable e){
throw new UndeclaredThrowableException(e);
}return ;}}
