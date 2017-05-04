/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-order
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/order/server/dao/RetainTest.java
 * 
 * FILE NAME        	: RetainTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.dao;

import java.util.ArrayList;
import java.util.List;

public class RetainTest {

	public static void main(String[] args) {
		List<A> list1 = new ArrayList<A>();
		A a1 = new A("a1","a1");
		A a2 = new A("a2","a2");
		list1.add(a1);
		list1.add(a2);
		for(A a : list1){
			a.setName("c1");
			System.out.println(a);
		}
		for(A a : list1){
			System.out.println(a);
		}
		List<A> list2 = new ArrayList<A>();
		A b1 = new A("a1","a1");
		A b2 = new A("b2","b2");
		A b3 = new A("b3","b3");
		list2.add(b1);
		list2.add(b2);
		list2.add(b3);
		list1.retainAll(list2);
		for(A a : list1){
			System.out.println(a);
		}
		
//		String s1 = "50000,100000,200000,300000,500000,";
//		String s2 = "10000,30000,50000,100000,";
//		String[] a1 = s1.split(",");
//		String[] a2 = s2.split(",");
//		List<String> list = new ArrayList(Arrays.asList(a1));
//		list.retainAll(Arrays.asList(a2)); // list 中的就是交集了
//		for(String s : list){
//			System.out.println(s);
//		}
	}
	
}

class A {
	private String name;
	private String code;
	
	public A(String name, String code) {
		this.name = name;
		this.code = code;
	}
	@Override
	public String toString() {
		return name + " " + code;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof A)){
			return false;
		}
		A a = (A) obj;
		return name.equals(a.name) && code.equals(a.code);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + name.hashCode();
		result = 37 * result + code.hashCode();
		return result;
	}
}