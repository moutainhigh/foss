package com.deppon.pda.bdm.module.core.shared.util;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.util.Assert;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.IBatis3Dao;

public class PDASqlSessionDaoSupport extends DaoSupport implements IBatis3Dao {

	private SqlSession sqlSession;

	@Autowired(required = false)
	public final void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
	}

	/**
	 * Users should use this method to get a SqlSession to call its statement
	 * methods This is SqlSession is managed by spring. Users should not
	 * commit/rollback/close it because it will be automatically done.
	 * 
	 * @return Spring managed thread safe SqlSession
	 */
	public final SqlSession getSqlSession() {
		return this.sqlSession;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void checkDaoConfig() {
		Assert.notNull(this.sqlSession,
				"Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required");
	}

}
