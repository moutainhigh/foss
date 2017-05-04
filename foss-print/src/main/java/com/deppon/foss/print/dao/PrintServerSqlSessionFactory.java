package com.deppon.foss.print.dao;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.deppon.foss.print.util.ClassPathResourceUtil;

public class PrintServerSqlSessionFactory extends PrintSqlSessionFactory {

	private static final String SERVER_FILE_MYBATIS_XML = "com/deppon/foss/print/isbatis/mybatis.xml";
	public PrintServerSqlSessionFactory() throws Exception {
		super();
	}
	
	public SqlSessionFactory buildServerMyBatis() throws Exception {
		
		ClassPathResourceUtil resourceUtil = new ClassPathResourceUtil();
		InputStream inStream = resourceUtil.getInputStream(SERVER_FILE_MYBATIS_XML);
		
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		return builder.build(inStream);
	}

	@Override
	public SqlSessionFactory buildSqlSessionFactory() throws Exception {
		return buildServerMyBatis(); 
	}
}
