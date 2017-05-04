package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IDebtCreditLimitInfoQueryDao;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.DebtCreditLimitInfoResultDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 查询最早欠款客户已用额度信息Dao实现
 * 
 * @author foss-zhangxiaohui
 * @date Jan 15, 2013 3:13:32 PM
 */
public class DebtCreditLimitInfoQueryDao extends iBatis3DaoImpl implements IDebtCreditLimitInfoQueryDao {

	/**
	 * 命名空间路径
	 */
	public static final String NAMESPACES = "foss.stl.DebtCreditLimitInfoQueryDao.";
	
	/**
	 * 查询最早欠款客户已用额度信息
	 * 
	 * @author foss-zhangxiaohui
	 * @date Jan 15, 2013 3:14:01 PM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DebtCreditLimitInfoResultDto> queryDebtCreditLimitInfo(String active,Date beginDate,Date endDate) {
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("active", active);
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		
		//查询开单为月结的数据
		map.put("payment_type_CT", SettlementDictionaryConstants.OTHER_REVENUE__PAYMENT_TYPE__CREDIT);
		
		//初始化数据不发送给CRM
		map.put("isInit", FossConstants.NO);
		map.put("fossOnlineDate", SettlementConstants.FOSS_ONLINE_DATE);
		
		//返回查询结果
		return (List<DebtCreditLimitInfoResultDto> )this.getSqlSession().selectList(NAMESPACES + "queryDebtCreditLimitInfo",map);
	}
}
