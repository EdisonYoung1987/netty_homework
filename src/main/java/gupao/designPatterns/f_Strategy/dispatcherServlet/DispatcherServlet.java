package gupao.designPatterns.f_Strategy.dispatcherServlet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispatcherServlet extends HttpServlet {
	private static final Map<String,Handler> URIHANDLERMAPPING=new ConcurrentHashMap<>();
	static {
		try {//注册URI对应的controller的方法
			URIHANDLERMAPPING.put("/login", new Handler(new LoggerController(),
					LoggerController.class.getMethod("login", new Class<?>[] {})));
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	private void doDispatch(HttpServletRequest request, HttpServletResponse response){
		String uri=request.getRequestURI();
		
		//根据uri选择相应的Controller来处理，所以肯定需要一个Map，而且这个Map需要在整个应用启动时
		//就进行了加载，比如Spring的@Controller+@RequestMapping("/login")就注册了uri以及对应的逻辑
		Handler handler=URIHANDLERMAPPING.get(uri);
		if(handler==null) {
			response.setStatus(404);
		}else {
			Method method=handler.getMethod();
			try {
				method.invoke(handler.getController(),method.getParameterTypes() );
				response.setStatus(200);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				response.setStatus(404);
			}
		}
		return;
	}
}

class Handler{
	private Object controller;
	private Method method;
	
	public Handler(Object controller,Method method) {
		this.controller=controller;
		this.method=method;
	}
	
	public Object getController() {
		return controller;
	}
	public void setController(Object controller) {
		this.controller = controller;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	
}
