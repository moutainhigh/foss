package com.deppon.foss.module.pickup.waybill.server.mybatis;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.deppon.foss.module.pickup.waybill.shared.domain.CustomerCouponEntity;

public class MybatisMapper {

	public static void main(String[] args) {
		printResultMap(CustomerCouponEntity.class);
	}

	private static void printResultMap(Class<?> cls) {
		StringBuilder sb = new StringBuilder();
		sb.append("<resultMap id=\"" + cls.getSimpleName() + "Map\"" + " type=\""+ cls.getName() + "\" >\r");
		while (cls != null) {
			Field[] fs = cls.getDeclaredFields();
			for (Field f : fs) {
				if (Modifier.isStatic(f.getModifiers())
						|| Modifier.FINAL == f.getModifiers()) {
					continue;
				}
				String name = f.getName();
				if (name.equals("id")) {
					sb.append("  <id column=\"id\"   property=\"id\" />\r");
				} else {
					sb.append("  <result column=\"\" property=\"" + name+ "\" />\r");
				}
			}
			cls = cls.getSuperclass();
		}
		sb.append("</resultMap>");
		System.out.println(sb);
	}
}
