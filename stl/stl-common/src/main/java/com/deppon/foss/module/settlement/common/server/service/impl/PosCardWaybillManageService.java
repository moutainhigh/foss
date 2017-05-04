package com.deppon.foss.module.settlement.common.server.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.settlement.common.api.server.dao.IPosCardWaybillManageDao;
import com.deppon.foss.module.settlement.common.api.server.service.IPosCardWaybillManageService;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * NCI项目运单更改，预付款不变
 * 
 * @ClassName: PosCardWaybillManageServiceImpl
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-4-6 下午3:03:44
 */
public class PosCardWaybillManageService implements
		IPosCardWaybillManageService {
	// 返回默认值
	private static final String isSuccess = "1";
	/**
	 * Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(PosCardWaybillManageService.class);
	
	/**
	 * 注入Dao
	 */
	private IPosCardWaybillManageDao posCardWaybillManageDao;
	
	/**
	 * 
	 * 运单更改，代收货款预付金额不变 修改对应的明细的单据总金额 预付金额不变
	 * (但是代收金额变了，造成运单总金额发生变化) 
	 * 参数：单据号和总金额
	 * 
	 * @Title: updatePosCardDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-4-6 下午2:57:25
	 */
	@Override
	@Transactional
	public String updatePosCardDetail(PosCardDetailEntity entity) {
		logger.info("NCI--运单更改代收货款预付金额不变 修改对应的明细的单据总金额开始");
		if (entity == null) {
			throw new SettlementException("传入参数为空！");
		}
		// 校验单据号
		if (StringUtils.isBlank(entity.getInvoiceNo())) {
			throw new SettlementException("单据号不能为空！");
		}
		// 校验单据总金额
		if (entity.getAmount().compareTo(BigDecimal.ZERO)< -1) {
			throw new SettlementException("单据总金额不能小于0！");
		}
		/**
		 * 逻辑处理
		 */
		posCardWaybillManageDao.changePosCardDetail(entity);
		logger.info("NCI--运单更改代收货款预付金额不变 修改对应的明细的单据总金额结束");
		return isSuccess;
	}

	/**********getter/setter***********/
	public void setPosCardWaybillManageDao(
			IPosCardWaybillManageDao posCardWaybillManageDao) {
		this.posCardWaybillManageDao = posCardWaybillManageDao;
	}
}
