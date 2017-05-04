package com.deppon.foss.print.dao;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.deppon.foss.print.util.ClassPathResourceUtil;

public class PrintGuiSqlSessionFactory extends PrintSqlSessionFactory {

	private static final String GUI_FILE_MYBATIS_XML = "mybatis.xml";
	public PrintGuiSqlSessionFactory() throws Exception  {
		super();
	}
	
	public SqlSessionFactory buildGuiMyBatis() throws Exception {
		
		ClassPathResourceUtil resourceUtil = new ClassPathResourceUtil();
		InputStream inStream = resourceUtil.getInputStream(GUI_FILE_MYBATIS_XML);
		
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		return builder.build(inStream);
	}

	@Override
	public SqlSessionFactory buildSqlSessionFactory() throws Exception {
		return buildGuiMyBatis(); 
	}
}
