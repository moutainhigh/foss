package com.deppon.foss.print.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.deppon.foss.print.util.ObjectCreator;

public abstract class PrintSqlSessionFactory {

	private static PrintSqlSessionFactory instance;
	private SqlSessionFactory sqlSessionFactory;
	
	public PrintSqlSessionFactory() throws Exception {
		sqlSessionFactory = buildSqlSessionFactory();
	}
	
	public static PrintSqlSessionFactory getInstance() throws Exception {
		if(instance==null){
			String clz = "com.deppon.foss.print.dao.PrintGuiSqlSessionFactory";
			instance = (PrintSqlSessionFactory)ObjectCreator.createObject(clz);
		}
		return instance;
	}

	public SqlSession getSqlSession() {
		return sqlSessionFactory.openSession();
	}
	
	abstract SqlSessionFactory buildSqlSessionFactory() throws Exception;
	
}
