package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.LandBillPaidCODGridDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.LandBillPaidCODConditionVo;

/**
 * 快递代理代收货款审核
 * 
 * @author foss-guxinhua
 * @date 2013-07-18 上午11:34:24
 */
public interface ILandBillPaidCODService {

	/**
	 * 按运单号进行查询
	 * 
	 * @author foss-guxinhua
	 * @date 2013-07-18 上午11:37:22
	 */
	List<LandBillPaidCODGridDto> queryByWaybillNos(CurrentInfo currentInfo,
			List<String> waybillNOs);

	/**
	 * 按签收时间进行查询
	 * 
	 * @author foss-guxinhua
	 * @date 2013-07-18 上午11:37:36
	 */
	List<LandBillPaidCODGridDto> queryBySignDate(CurrentInfo currentInfo,LandBillPaidCODConditionVo conditionVo,int offset ,int limit);
	
	
	/**
	 * 按签收时间进行查询总行数
	 * 
	 * @author foss-guxinhua
	 * @date 2013-07-18 上午11:37:36
	 */
	LandBillPaidCODGridDto queryTotalRowsBySignDate(CurrentInfo currentInfo,LandBillPaidCODConditionVo conditionVo);

	/**
	 * 按审核时间进行查询（只查询已经审核的代收货款记录）
	 * 
	 * @author foss-guxinhua
	 * @date 2013-07-18 上午11:37:36
	 */
	List<LandBillPaidCODGridDto> queryByAuditDate(CurrentInfo currentInfo,LandBillPaidCODConditionVo conditionVo,int offset ,int limit);
	
	
	/**
	 * 按审核时间进行查询（只查询已经审核的代收货款记录）,合计金额
	 * 
	 * @author foss-guxinhua
	 * @date 2013-07-18 上午11:37:36
	 */
	LandBillPaidCODGridDto queryTotalRowsByAuditDate(CurrentInfo currentInfo,LandBillPaidCODConditionVo conditionVo);
	

	/**
	 * 生效代收货款记录
	 * 
	 * @author foss-guxinhua
	 * @date 2013-07-18 下午2:34:57
	 */
	void makeEffectiveBillCOD(List<String> codIds , CurrentInfo currentInfo);

	/**
	 * 反生效代收货款记录
	 * 
	 * @author foss-guxinhua
	 * @date 2013-07-18 下午2:36:55
	 */
	void reverseEffectiveBillCOD(List<String> codIds ,CurrentInfo currentInfo);

}
