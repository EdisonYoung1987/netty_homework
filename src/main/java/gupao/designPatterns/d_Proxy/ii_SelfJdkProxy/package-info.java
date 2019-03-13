/**
 * 这是一个简化了jdk代理流程实现的自定义代理包
 * 前面jdk动态代理用到了 java.lang.reflect.Proxy.newProxyInstance(xxx) 生成代理对象
                      InvocationHandler接口
                      ClassLoader 用于装载
  所以如果要实现自己的jdk动态代理，这三个类或者方法就要重新实现
 */
/**
 * @author Edison
 *
 */
package gupao.designPatterns.d_Proxy.ii_SelfJdkProxy;