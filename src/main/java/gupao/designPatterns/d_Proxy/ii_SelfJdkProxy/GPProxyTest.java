package gupao.designPatterns.d_Proxy.ii_SelfJdkProxy;

import gupao.designPatterns.d_Proxy.service.I_Person;
import gupao.designPatterns.d_Proxy.service.Son;

/**
 * Created by Tom on 2019/3/10.
 */
public class GPProxyTest {

    public static void main(String[] args) {
        try {

            //JDK动态代理的实现原理
            I_Person obj = (I_Person) new GPMeipo().getInstance(new Son());
            System.out.println(obj.getClass());
            obj.findLover();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
