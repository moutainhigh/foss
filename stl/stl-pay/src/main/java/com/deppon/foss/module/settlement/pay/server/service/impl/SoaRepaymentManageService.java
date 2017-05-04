package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.dao.ISoaRepaymentEntityDao;
import com.deppon.foss.module.settlement.pay.api.server.service.ISoaRepaymentManageService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.SoaRepaymentEntity;

/**
 * 对账单还款单关系service
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-29 下午5:12:52
 */
public class SoaRepaymentManageService implements ISoaRepaymentManageService {

	private ISoaRepaymentEntityDao soaRepaymentEntityDao;

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(SoaRepaymentManageService.class);

	/**
	 * 新增对账单还款单关系
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-29 下午5:13:10
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.ISoaRepaymentManageService#add(com.deppon.foss.module.settlement.common.api.shared.domain.SoaRepaymentEntity)
	 */
	@Override
	public int add(SoaRepaymentEntity entity) {

		// 输入参数校验
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| entity.getRepaymentNo() == null
				|| entity.getStatementBillNo() == null
				|| entity.getRepaymentAmount() == null) {
			throw new SettlementException("新增对账单还款单关系数据出错");
		} 
		logger.info("新增对账单还款单关系数据，对账单号：" + entity.getStatementBillNo()
				+ " ,还款单号：" + entity.getRepaymentNo());

		return soaRepaymentEntityDao.add(entity);
	}

	/**
	 * 根据还款单号查询对账单还款单关系
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-29 下午5:13:30
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.ISoaRepaymentManageService#queryListByRepaymentNo(java.lang.String)
	 */
	@Override
	public List<SoaRepaymentEntity> queryListByRepaymentNo(String repaymentNo) {

		if (StringUtils.isEmpty(repaymentNo)) {
			throw new SettlementException("输入对账单号为空");
		}

		logger.debug("根据还款单号查询对账单还款单数据,还款单号："+repaymentNo);
		return soaRepaymentEntityDao.queryListByRepaymentNo(repaymentNo);
	}

	public ISoaRepaymentEntityDao getSoaRepaymentEntityDao() {
		return soaRepaymentEntityDao;
	}

	public void setSoaRepaymentEntityDao(
			ISoaRepaymentEntityDao soaRepaymentEntityDao) {
		this.soaRepaymentEntityDao = soaRepaymentEntityDao;
	}

}
