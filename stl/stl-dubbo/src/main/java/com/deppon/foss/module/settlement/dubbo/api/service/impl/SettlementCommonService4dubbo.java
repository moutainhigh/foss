package com.deppon.foss.module.settlement.dubbo.api.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.dubbo.ptp.api.define.exception.SettlementException;
import com.deppon.foss.module.settlement.dubbo.api.dao.ISettlementSequenceDao4dubbo;
import com.deppon.foss.module.settlement.dubbo.api.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.dubbo.api.service.ISettlementCommonService4dubbo;

/**
 * 结算通用服务
 * @author ibm-zhuwei
 * @date 2012-10-15 下午1:33:17
 */
public class SettlementCommonService4dubbo implements ISettlementCommonService4dubbo {

	private static final Logger logger = LogManager.getLogger(SettlementCommonService4dubbo.class);
	
	/**
	 * 结算序列号DAO
	 */
	private ISettlementSequenceDao4dubbo settlementSequenceDao;
	
	/** 
	 * 获取结算单据编号
	 * @author ibm-zhuwei
	 * @date 2012-10-24 上午8:54:56
	 * @param rule 结算单据编号规则
	 * @return
	 * @see com.deppon.foss.module.settlement.ISettlementCommonService4dubbo.api.server.service.ISettlementCommonService#getSettlementNoRule(com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum)
	 */
	@Override
	public String getSettlementNoRule(SettlementNoRuleEnum rule) {
		
		if (rule == null) {
			throw new SettlementException("生成新的单据号出错");
		}
		
		long seq = getSettlementSequenceDao().getNextVal(rule.getSequence());
		String seqStr = String.format("%010d",seq);
		String seqCode = rule.getCode() + seqStr.substring(seqStr.length() - rule.getLength());
		
		logger.debug(rule.getName() + " :" + seqCode);
		
		return seqCode;
	}

	public ISettlementSequenceDao4dubbo getSettlementSequenceDao() {
		return settlementSequenceDao;
	}
	@Autowired
	public void setSettlementSequenceDao(ISettlementSequenceDao4dubbo settlementSequenceDao) {
		this.settlementSequenceDao = settlementSequenceDao;
	}

}
