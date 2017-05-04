package com.deppon.foss.module.settlement.common.server.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.settlement.common.api.server.dao.ISettlementSequenceDao;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;


/**
 * 结算通用服务
 * @author ibm-zhuwei
 * @date 2012-10-15 下午1:33:17
 */
public class SettlementCommonService implements ISettlementCommonService {

	private static final Logger logger = LogManager.getLogger(SettlementCommonService.class);
	
	/**
	 * 结算序列号DAO
	 */
	private ISettlementSequenceDao settlementSequenceDao;
	
	/** 
	 * 获取结算单据编号
	 * @author ibm-zhuwei
	 * @date 2012-10-24 上午8:54:56
	 * @param rule 结算单据编号规则
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService#getSettlementNoRule(com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum)
	 */
	@Override
	public String getSettlementNoRule(SettlementNoRuleEnum rule) {
		
		if (rule == null) {
			throw new SettlementException("生成新的单据号出错");
		}
		
		long seq = settlementSequenceDao.getNextVal(rule.getSequence());
		String seqStr = String.format("%010d",seq);
		String seqCode = rule.getCode() + seqStr.substring(seqStr.length() - rule.getLength());
		
		logger.debug(rule.getName() + " :" + seqCode);
		
		return seqCode;
	}

	/**
	 * @param settlementSequenceDao
	 */
	public void setSettlementSequenceDao(
			ISettlementSequenceDao settlementSequenceDao) {
		this.settlementSequenceDao = settlementSequenceDao;
	}

}
