package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.AirBillPaidCODGridDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.AirBillPaidCODConditionVo;

/**
 * 空运代收货款审核
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-30 上午11:34:24
 */
public interface IAirBillPaidCODService {

	/**
	 * 按运单号进行查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 上午11:37:22
	 */
	List<AirBillPaidCODGridDto> queryByWaybillNos(CurrentInfo currentInfo,
			List<String> waybillNOs);

	/**
	 * 按签收时间进行查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 上午11:37:36
	 */
	List<AirBillPaidCODGridDto> queryBySignDate(CurrentInfo currentInfo,AirBillPaidCODConditionVo conditionVo,int offset ,int limit);
	
	
	/**
	 * 按签收时间进行查询总行数
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 上午11:37:36
	 */
	AirBillPaidCODGridDto queryTotalRowsBySignDate(CurrentInfo currentInfo,AirBillPaidCODConditionVo conditionVo);

	/**
	 * 按审核时间进行查询（只查询已经审核的代收货款记录）
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 上午11:37:36
	 */
	List<AirBillPaidCODGridDto> queryByAuditDate(CurrentInfo currentInfo,AirBillPaidCODConditionVo conditionVo,int offset ,int limit);
	
	
	/**
	 * 按审核时间进行查询（只查询已经审核的代收货款记录）,合计金额
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 上午11:37:36
	 */
	AirBillPaidCODGridDto queryTotalRowsByAuditDate(CurrentInfo currentInfo,AirBillPaidCODConditionVo conditionVo);
	

	/**
	 * 生效代收货款记录
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 下午2:34:57
	 */
	void makeEffectiveBillCOD(List<String> codIds , CurrentInfo currentInfo);

	/**
	 * 反生效代收货款记录
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 下午2:36:55
	 */
	void reverseEffectiveBillCOD(List<String> codIds ,CurrentInfo currentInfo);

}
