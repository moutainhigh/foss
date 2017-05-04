package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OfvPaymentReportQueryDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OfvPaymentReportResultDto;

/**
 * 外请车付款报表servcie接口
 * @author foss-zhangxiaohui
 * @date Dec 20, 2012 4:12:48 PM
 */
public interface IOfvPaymentReportQueryService extends IService {

	/**
	 * 外请车付款报表servcie接口--查询外请车付款报表
	 * @author foss-zhangxiaohui
	 * @date Dec 20, 2012 4:13:28 PM
	 */
	List<OfvPaymentReportResultDto> queryOfvPaymentReportByDate(OfvPaymentReportQueryDto dto,
			CurrentInfo cInfo);

	/**
	 * 外请车付款报表servcie接口--查询外请车付款报表数据总数
	 * @author foss-zhangxiaohui
	 * @date Dec 20, 2012 4:13:51 PM
	 */
	OfvPaymentReportResultDto queryOfvPaymentReportTotalByDate(OfvPaymentReportQueryDto dto,
			CurrentInfo cInfo);
	
	/**
	 * 外请车付款报表servcie接口-- 分页查询外请车付款报表
	 * @author foss-zhangxiaohui
	 * @date Dec 20, 2012 4:14:48 PM
	 */
	List<OfvPaymentReportResultDto> queryOfvPaymentReportByDateAndPage(
			 int offset,int start,OfvPaymentReportQueryDto dto,
				CurrentInfo cInfo);
	
	/**
	 * 外请车付款报表servcie接口--查询外请车付款报表
	 * @author foss-zhangxiaohui
	 * @date Dec 20, 2012 4:13:28 PM
	 */
	List<OfvPaymentReportResultDto> queryOfvPaymentReportByWorkFlowNo(OfvPaymentReportQueryDto dto);
	/**
	* @Description: 外请车付款报表servcie接口--根据合同编号查询外请车付款报表
	* @author 321603
	* @date 2016-11-4
	* @throws
	 */
	List<OfvPaymentReportResultDto> queryOfvPaymentReportByContractCodesNo(
			OfvPaymentReportQueryDto dto, CurrentInfo cInfo);
	/**
	 * 
	* @Description: 外请车付款报表servcie接口--查询外请车付款报表(包括整车)
	* @author 321603
	* @date 2016-11-10
	* @throws
	 */
	List<OfvPaymentReportResultDto> queryOfvPaymentReportByDate2(
			OfvPaymentReportQueryDto dto, CurrentInfo cInfo);
}
