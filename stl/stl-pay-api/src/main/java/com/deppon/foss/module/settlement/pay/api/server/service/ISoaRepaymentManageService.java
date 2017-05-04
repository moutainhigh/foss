package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.SoaRepaymentEntity;
/**
 * 对账单还款单关系servcie
 * @author foss-qiaolifeng
 * @date 2012-11-29 下午5:08:30
 */
public interface ISoaRepaymentManageService extends IService {

	/**
	 * 新增对账单还款单关系数据
	 * @author foss-qiaolifeng
	 * @date 2012-11-29 下午4:47:43
	 */
	int add(SoaRepaymentEntity entity);
	
	/**
	 * 根据还款单号查询对账单还款单关系
	 * @author foss-qiaolifeng
	 * @date 2012-11-29 下午4:49:07
	 */
	List<SoaRepaymentEntity> queryListByRepaymentNo(String repaymentNo);
}
