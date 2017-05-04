package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.Date;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IBalanceDao;

/**
 * 
 * 结账批次DAO
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-3-15 下午3:28:03
 */
public class BalanceDao extends iBatis3DaoImpl implements IBalanceDao {

	/**
	 * mybatis配置namespace
	 */
	private static final String NAME_SPACE = "foss.stv.BalanceMapper.";

	/**
	 * 结账
	 */
	@Override
	public void balance(Date date) {
		getSqlSession().selectOne(NAME_SPACE + "proc_balance", date);
	}

	/**
	 * 反结账
	 */
	@Override
	public void unBalance(Date date) {
		getSqlSession().selectOne(NAME_SPACE + "proc_unbalance", date);
	}

	/**
	 * 结账是否在运行
	 */
	@Override
	public int isBalanceRun() {
		return (Integer) getSqlSession().selectOne(NAME_SPACE + "isBalanceRun");
	}

	/**
	 * 查询当前结账期间
	 */
	@Override
	public Date currentBalanceDate() {
		return (Date) getSqlSession().selectOne(NAME_SPACE + "currentBalanceDate");
	}

}
