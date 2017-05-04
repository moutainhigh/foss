package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.dao.IOfvPaymentReportQueryDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IOfvPaymentReportQueryService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OfvPaymentReportQueryDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OfvPaymentReportResultDto;

/**
 * 外请车付款报表servcie实现
 * 
 * @author foss-zhangxiaohui
 * @date Dec 20, 2012 5:41:28 PM
 */
public class OfvPaymentReportQueryService implements IOfvPaymentReportQueryService {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(OfvPaymentReportQueryService.class);
	
	/**
	 * 外请车付款报表Dao
	 */
	private IOfvPaymentReportQueryDao ofvPaymentReportQueryDao;
	
	/**
	 * 导出的时候做数据源
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 20, 2012 5:42:00 PM
	 */
	@Override
	public List<OfvPaymentReportResultDto> queryOfvPaymentReportByDate(OfvPaymentReportQueryDto dto,
			CurrentInfo cInfo) {
		//验证输入参数
		this.validateInputParameters(dto,cInfo);
	
		//声明返回结果的List
		List<OfvPaymentReportResultDto> dtoList = null;
		//打印查询外请车付款信息
		logger.debug("查询外请车付款信息开始...");
		//执行查询操作并赋值
		dtoList = ofvPaymentReportQueryDao.queryOfvPaymentReportByDate(dto);
		//打印查询外请车付款信息
		logger.debug("查询外请车付款信息结束...");
		//返回查询结果
		return dtoList;
	}

	/**
	 * 查询分页条件下总的数据条数
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 20, 2012 5:18:57 PM
	 */
	@Override
	public OfvPaymentReportResultDto queryOfvPaymentReportTotalByDate(OfvPaymentReportQueryDto dto,
			CurrentInfo cInfo) {
		//验证输入参数
		this.validateInputParameters(dto,cInfo);
		
		//打印查询外请车付款信息
		logger.debug("查询外请车付款总条数开始...");
		//声明Dto对象存储查询结果
		OfvPaymentReportResultDto resultDto = null;
		//执行查询操作并赋值
		if(CollectionUtils.isNotEmpty(dto.getContractCodesNos())){
			resultDto = ofvPaymentReportQueryDao.queryOfvPaymentReportTotalRecordsByContractCodesNo(dto);
		}else if(dto.getStartPaymentDate()!=null&&dto.getEndPaymentDate()!=null){
			resultDto = ofvPaymentReportQueryDao.queryOfvPaymentReportTotalRecordsByDate2(dto);			
		}else{
			resultDto = ofvPaymentReportQueryDao.queryOfvPaymentReportTotalRecordsByDate(dto);
		}
		if(resultDto!=null&&resultDto.getTotalRecordsInDB()>0){
			OfvPaymentReportResultDto amountDto = null;
			//查询总金额
			if(CollectionUtils.isNotEmpty(dto.getContractCodesNos())){
				amountDto = ofvPaymentReportQueryDao.queryOfvPaymentReportTotalAmountByContractCodesNo(dto);
			}else if(dto.getStartPaymentDate()!=null&&dto.getEndPaymentDate()!=null){
				amountDto = ofvPaymentReportQueryDao.queryOfvPaymentReportTotalAmountByDate2(dto);
			}else{
				amountDto = ofvPaymentReportQueryDao.queryOfvPaymentReportTotalAmountByDate(dto);
			}
			if(amountDto!=null){
				//如果总金额不为空，设置总金额到返回dto上
				resultDto.setTotalFeeSum(amountDto.getTotalFeeSum());
			}
		}
		//打印查询外请车付款信息
		logger.debug("查询外请车付款总条数结束...");
		//返回查询结果
		return resultDto;
	}

	/**
	 * 分页查询的查询实现
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 20, 2012 5:29:57 PM
	 */
	@Override
	public List<OfvPaymentReportResultDto> queryOfvPaymentReportByDateAndPage(int offset,int start,OfvPaymentReportQueryDto dto,
			CurrentInfo cInfo){	
		//验证输入参数
		this.validateInputParameters(dto,cInfo);
		
		//声明返回结果的List
		List<OfvPaymentReportResultDto> dtoList = null;
		//打印分页查询外请车付款信息
		logger.debug("分页查询外请车付款信息开始...");
		//执行查询操作并赋值
		if(CollectionUtils.isNotEmpty(dto.getContractCodesNos())){
			dtoList = ofvPaymentReportQueryDao.queryOfvPaymentReportByContractCodesNoAndPage(offset, start, dto);
		}else if(dto.getStartPaymentDate()!=null&&dto.getEndPaymentDate()!=null){
			dtoList = ofvPaymentReportQueryDao.queryOfvPaymentReportByDateAndPage2(offset, start, dto);
		}else{
			dtoList = ofvPaymentReportQueryDao.queryOfvPaymentReportByDateAndPage(offset, start, dto);
		}
		//打印分页查询外请车付款信息
		logger.debug("分页查询外请车付款信息结束...");
		//返回查询结果
		return dtoList;
	}
	
	/**
	 * 验证输入信息
	 * 
	 * @author foss-zhangxiaohui
	 * @date Dec 26, 2012 5:29:57 PM
	 */
	private void validateInputParameters(OfvPaymentReportQueryDto dto,CurrentInfo cInfo){
		//判断输入的查询条件是否为空
		if(null == dto){
			//如果查询条件为空则抛出异常
			throw new SettlementException("查询输入条件为空！");
		}
		
		//设置当前登录用户员工编码
		dto.setEmpCode(cInfo.getEmpCode());
		
		//单据类型集合
		List<String> payableTypeList = new ArrayList<String>();
		if(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__FIRST.equals(dto.getPayableType())){
			//首款
			payableTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__FIRST);
		}else if(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__LAST.equals(dto.getPayableType())){
			//尾款
			payableTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__LAST);
		}else if(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__FIRST_BACK.equals(dto.getPayableType())){
			//押回单首款
			payableTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__FIRST_BACK);
		}else if(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__LAST_BACK.equals(dto.getPayableType())){
			//押回单尾款
			payableTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__LAST_BACK);
		}else if(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MONTH.equals(dto.getPayableType())){
			//月结
			payableTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MONTH);	
		}else{
			payableTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__FIRST);
			payableTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__LAST);
			payableTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__FIRST_BACK);
			payableTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__LAST_BACK);
			payableTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MONTH);
		}
		dto.setPayableTypeList(payableTypeList);
	}
	
	/**
	 * @param  ofvPaymentReportQueryDao  
	 */
	public void setOfvPaymentReportQueryDao(
			IOfvPaymentReportQueryDao ofvPaymentReportQueryDao) {
		this.ofvPaymentReportQueryDao = ofvPaymentReportQueryDao;
	}

	/** 
	 * 外请车付款报表servcie接口--查询外请车付款报表
	 * @author foss-qiaolifeng
	 * @date 2013-4-1 上午9:18:16
	 * @param dto
	 * @param cInfo
	 * @return
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IOfvPaymentReportQueryService
	 *     #queryOfvPaymentReportByWorkFlowNo(com.deppon.foss.module.settlement.pay.api.shared.dto.
	 *      OfvPaymentReportQueryDto, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public List<OfvPaymentReportResultDto> queryOfvPaymentReportByWorkFlowNo(
			OfvPaymentReportQueryDto dto) {
		
		if(dto==null||StringUtils.isEmpty(dto.getWorkFlowNo())){
			throw new SettlementException("打印外请车付款报表时,选择的工作流号为空");
		}
		
		// 声明返回结果的List
		List<OfvPaymentReportResultDto> dtoList = null;
		// 打印查询外请车付款信息
		logger.debug("查询待打印外请车付款信息开始...");
		// 执行查询操作并赋值
		dtoList = ofvPaymentReportQueryDao.queryOfvPaymentReportByWorkFlowNo(dto);
		// 打印查询外请车付款信息
		logger.debug("查询待打印外请车付款信息结束...");
		// 返回查询结果
		return dtoList;
	}

	@Override
	public List<OfvPaymentReportResultDto> queryOfvPaymentReportByContractCodesNo(
			OfvPaymentReportQueryDto dto, CurrentInfo cInfo) {
		//验证输入参数
		this.validateInputParameters(dto,cInfo);
	
		//声明返回结果的List
		List<OfvPaymentReportResultDto> dtoList = null;
		//打印查询外请车付款信息
		logger.debug("查询外请车付款信息开始...");
		//执行查询操作并赋值
		dtoList = ofvPaymentReportQueryDao.queryOfvPaymentReportByContractCodesNo(dto);
		//打印查询外请车付款信息
		logger.debug("查询外请车付款信息结束...");
		//返回查询结果
		return dtoList;	}

	@Override
	public List<OfvPaymentReportResultDto> queryOfvPaymentReportByDate2(
			OfvPaymentReportQueryDto dto, CurrentInfo cInfo) {
		//验证输入参数
		this.validateInputParameters(dto,cInfo);
	
		//声明返回结果的List
		List<OfvPaymentReportResultDto> dtoList = null;
		//打印查询外请车付款信息
		logger.debug("查询外请车付款信息开始...");
		//执行查询操作并赋值
		dtoList = ofvPaymentReportQueryDao.queryOfvPaymentReportByDate2(dto);
		//打印查询外请车付款信息
		logger.debug("查询外请车付款信息结束...");
		//返回查询结果
		return dtoList;
	}
}
