package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.dto.OfvPaymentReportQueryDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OfvPaymentReportResultDto;

/**
 *  外请车付款报表Dao接口
 * @author foss-zhangxiaohui
 * @date Dec 20, 2012 4:53:39 PM
 */
public interface IOfvPaymentReportQueryDao {
	
	/**
	 * 外请车付款报表Dao接口--查询外请车付款报表
	 * @author foss-zhangxiaohui
	 * @date Dec 20, 2012 4:13:28 PM
	 */
	List<OfvPaymentReportResultDto> queryOfvPaymentReportByDate(OfvPaymentReportQueryDto dto);

	/**
	 * 外请车付款报表Dao接口--查询外请车付款报表总条数
	 * @author foss-zhangxiaohui
	 * @date Dec 20, 2012 4:13:51 PM
	 */
	OfvPaymentReportResultDto queryOfvPaymentReportTotalRecordsByDate(OfvPaymentReportQueryDto dto);
	
	/**
	 * 外请车付款报表Dao接口--查询外请车付款报表总金额
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-4-24 下午1:59:08
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	OfvPaymentReportResultDto queryOfvPaymentReportTotalAmountByDate(OfvPaymentReportQueryDto dto);
	
	/**
	 * 外请车付款报表Dao接口-- 分页查询外请车付款报表
	 * @author foss-zhangxiaohui
	 * @date Dec 20, 2012 4:14:48 PM
	 */
	List<OfvPaymentReportResultDto> queryOfvPaymentReportByDateAndPage(
			 int offset,int start,OfvPaymentReportQueryDto dto);
	
	/**
	 * 外请车付款报表Dao接口--根据工作流号查询外请车付款报表
	 * @author foss-zhangxiaohui
	 * @date Dec 20, 2012 4:13:28 PM
	 */
	List<OfvPaymentReportResultDto> queryOfvPaymentReportByWorkFlowNo(OfvPaymentReportQueryDto dto);
	
	/**
	* @Description: 外请车付款报表Dao接口--根据合同编号查询外请车付款报表总条数
	* @author 321603
	* @date 2016-11-4
	* @throws
	*/
	OfvPaymentReportResultDto queryOfvPaymentReportTotalRecordsByContractCodesNo(
			OfvPaymentReportQueryDto dto);
	
	/**
	* @Description: 外请车付款报表Dao接口--根据合同编号查询外请车付款报表总金额
	* @author 321603
	* @date 2016-11-4
	* @throws
	*/
	OfvPaymentReportResultDto queryOfvPaymentReportTotalAmountByContractCodesNo(
			OfvPaymentReportQueryDto dto);

	/**
	* @Description: 外请车付款报表Dao接口--根据合同编号分页查询外请车付款报表
	* @author 321603
	* @date 2016-11-4
	* @throws
	*/
	List<OfvPaymentReportResultDto> queryOfvPaymentReportByContractCodesNoAndPage(
			int offset, int start, OfvPaymentReportQueryDto dto);
	
	/**
	* @Description: 外请车付款报表Dao接口--根据合同编号查询外请车付款报表
	* @author 321603
	* @date 2016-11-4
	* @throws
	*/
	List<OfvPaymentReportResultDto> queryOfvPaymentReportByContractCodesNo(
			OfvPaymentReportQueryDto dto);
	
	/**
	* @Description: 外请车付款报表Dao接口--查询外请车付款报表总金额(包括整车)
	* @author 321603
	* @date 2016-11-10
	* @throws
	*/
	OfvPaymentReportResultDto queryOfvPaymentReportTotalAmountByDate2(
			OfvPaymentReportQueryDto dto);

	/**
	* @Description: 外请车付款报表Dao接口--查询外请车付款报表总条数(包括整车)
	* @author 321603
	* @date 2016-11-10
	* @throws
	*/
	OfvPaymentReportResultDto queryOfvPaymentReportTotalRecordsByDate2(
			OfvPaymentReportQueryDto dto);

	/**
	* @Description: 外请车付款报表Dao接口-- 分页查询外请车付款报表(包括整车)
	* @author 321603
	* @date 2016-11-10
	* @throws
	*/
	List<OfvPaymentReportResultDto> queryOfvPaymentReportByDateAndPage2(
			int offset, int start, OfvPaymentReportQueryDto dto);
	
	/**
	* @Description:  外请车付款报表Dao接口-- 查询外请车付款报表(包括整车)
	* @author 321603
	* @date 2016-11-10
	* @throws
	*/
	List<OfvPaymentReportResultDto> queryOfvPaymentReportByDate2(
			OfvPaymentReportQueryDto dto);
}
