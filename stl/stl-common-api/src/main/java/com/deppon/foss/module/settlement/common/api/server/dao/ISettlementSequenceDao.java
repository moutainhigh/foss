package com.deppon.foss.module.settlement.common.api.server.dao;


/**
 * 查询结算序列号DAO
 * @author ibm-zhuwei
 * @date 2012-10-15 上午10:50:03
 */
public interface ISettlementSequenceDao {

	/**
	 * 获取下一序列号
	 * @author ibm-zhuwei
	 * @date 2012-12-24 下午8:37:56
	 * @param sequenceName
	 * @return
	 */
	long getNextVal(String sequenceName);
}
