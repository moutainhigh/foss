package com.deppon.foss.module.settlement.dubbo.api.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.settlement.dubbo.api.dao.IBillDepositReceivedEntityDao4dubbo;
import com.deppon.foss.module.settlement.dubbo.api.define.BillDepositReceivedEntity;

/**
 * 预收单公共DAO接口实现类
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-18 上午11:31:34
 */
public class BillDepositReceivedEntityDao4dubbo extends BaseDao implements IBillDepositReceivedEntityDao4dubbo {

	private static final String NAME_SPACES = "";

	/**
	 * 当前不需要即可访问
	 * @author 335284
	 * @since 2016.11.16
	 */
//	private static final String NAME_SPACES = "com.deppon.foss.module.settlement.dubbo.api.dao.impl.BillDepositReceivedEntityDao.";

	@Override
	public int addPartner(BillDepositReceivedEntity entity) {
		return this.getSqlSession().insert(NAME_SPACES + "insertPartner_4_dubbo", entity);
	}

	@Override
	public List<BillDepositReceivedEntity> queryByRemitNo(String remitNo, String active) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("remitNo", remitNo);
		params.put("active", active);
		return this.getSqlSession().selectList(NAME_SPACES + "queryByRemitNo_4_dubbo", params);
	}
}
