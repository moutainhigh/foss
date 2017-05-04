package com.deppon.foss.module.pickup.pricing.server.calculateservice.advisor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.deppon.foss.module.pickup.pricing.server.calculateservice.annotation.AfterAdv;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.annotation.BeforeAdv;

public abstract class AbstractCalculateAdvisor implements MethodInterceptor{
	private List<String> productNames;	//产品Code
	public boolean NEXT = true;
	private Map<String,Method> methodAfterMap;
	private Map<String,Method> methodBeforeMap;
	
	
	public List<String> getProductNames() {
		return productNames;
	}
	public void setProductNames(List<String> productNames) {
		this.productNames = productNames;
	}
	
	public AbstractCalculateAdvisor(){
		methodAfterMap = new HashMap<String,Method>();
		methodBeforeMap = new HashMap<String,Method>();
		scanFieldAnnotation();
	}
	
	private void scanFieldAnnotation(){
		Method[] methods = getClass().getMethods();
		for (Method method : methods) {
			AfterAdv after = method.getAnnotation(AfterAdv.class);
			if (after != null) {
				methodAfterMap.put(after.value(),method);
			}
			BeforeAdv before = method.getAnnotation(BeforeAdv.class);
			if (before != null) {
				methodBeforeMap.put(before.value(),method);
			}
		}
	}
	
	public Object doNext(MethodInvocation invocation) throws Throwable{
		Object obj = null;
		obj=invocation.proceed(); //调用目标方法，如不调用，目标方法将不被执行
		return obj;
	}
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object obj = null;
//		if(invocation.getArguments().length > 0){
			for(int i = 0; i < methodBeforeMap.size(); i++){
				Method method = methodBeforeMap.get(invocation.getMethod().getName());
				if(method != null){
					Object resObj = method.invoke(this, invocation.getArguments());
					if(resObj != null && resObj instanceof ResultRedirect)
						return ((ResultRedirect)resObj).getResult();
					break;
				}
			}
			obj = doNext(invocation);//调用目标方法，如不调用，目标方法将不被执行
			for(int i = 0; i < methodAfterMap.size(); i++){
				Method method = methodAfterMap.get(invocation.getMethod().getName());
				if(method != null){
					method.invoke(this, invocation.getArguments());
					break;
				}
			}
//		}
		return obj;
	}
}
