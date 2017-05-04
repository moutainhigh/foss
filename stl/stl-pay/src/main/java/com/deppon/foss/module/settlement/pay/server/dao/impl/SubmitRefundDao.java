/**
 * Copyright 2014 STL TEAM
 */

package com.deppon.foss.module.settlement.pay.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.pay.api.server.dao.ISubmitRefundDao;
import com.deppon.foss.module.settlement.pay.api.shared.dto.SubmitRefundDto;

/**
 * 转报销接口实现
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-19 上午11:16:55
 */
public class SubmitRefundDao extends iBatis3DaoImpl implements ISubmitRefundDao {

	private static final String NAMESPACES = "foss.stl.SubmitRefundDao.";

	@Override
	public int handleSubmitRefund(SubmitRefundDto dto){
		return this.getSqlSession().update(NAMESPACES+"handleSubmitRefund", dto);
	}
}
