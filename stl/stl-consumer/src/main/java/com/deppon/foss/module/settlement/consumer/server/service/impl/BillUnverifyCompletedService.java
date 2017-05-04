package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.util.List;

import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IBillUnverifyCompletedDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillUnverifyCompletedService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillUnverifyComletedResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillUnverifyCompletedQueryDto;


/**
 * 未完全核销单据查询service实现
 * @author foss-qiaolifeng
 * @date 2013-5-14 下午3:14:56
 */
public class BillUnverifyCompletedService implements
		IBillUnverifyCompletedService {

	private static final Logger LOGGER = LogManager
			.getLogger(BillUnverifyCompletedService.class);
	
	/**
	 * 注入未完全核销单据查询dao
	 */
	private IBillUnverifyCompletedDao billUnverifyCompletedDao;
	
	/** 
	 * 根据客户编码查询该客户的未完全核销单据
	 * @author foss-qiaolifeng
	 * @date 2013-5-14 下午3:18:27
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.IBillUnverifyCompletedService#queryBillUnverifyCompletedList(com.deppon.foss.module.settlement.consumer.api.shared.dto.BillUnverifyCompletedQueryDto)
	 */
	@Override
	public List<BillUnverifyComletedResultDto> queryBillUnverifyCompletedList(
			BillUnverifyCompletedQueryDto dto) {
		
		//如果输入参数为空
		if(dto==null||StringUtils.isEmpty(dto.getCustomerCode())){
			//抛出参数异常信息
			throw new SettlementException("输入客户编码为空,查询客户未完全核销单据失败!");
		}
		//记录日志
		LOGGER.debug("查询客户:"+dto.getCustomerCode()+"未完全核销单据开始...");
		//调用查询dao
		List<BillUnverifyComletedResultDto> rtnList = this.billUnverifyCompletedDao.queryBillUnverifyCompletedList(dto);
		//记录日志
		LOGGER.debug("查询客户:"+dto.getCustomerCode()+"未完全核销单据结束...");
		//返回查询结果
		return rtnList;
	}
	
	/** 
	 * 根据客户编码查询该客户的所有未完全核销单据总条数
	 * @author foss-qiaolifeng
	 * @date 2013-5-14 下午4:19:33
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.IBillUnverifyCompletedService#queryBillUnverifyCompletedTotals(com.deppon.foss.module.settlement.consumer.api.shared.dto.BillUnverifyCompletedQueryDto)
	 */
	@Override
	public Long queryBillUnverifyCompletedTotals(
			BillUnverifyCompletedQueryDto dto) {
		// 如果输入参数为空
		if (dto == null || StringUtils.isEmpty(dto.getCustomerCode())) {
			// 抛出参数异常信息
			throw new SettlementException("输入客户编码为空,查询客户未完全核销单据总条数失败!");
		}
		// 记录日志
		LOGGER.debug("查询客户:" + dto.getCustomerCode() + "未完全核销单据总跳开始...");
		// 调用查询dao
		Long billNums = this.billUnverifyCompletedDao.queryBillUnverifyCompletedTotals(dto);
		// 记录日志
		LOGGER.debug("查询客户:" + dto.getCustomerCode() + "未完全核销单据总条数结束...");
		// 返回查询结果
		return billNums;
	}

	
	/**
	 * @get
	 * @return billUnverifyCompletedDao
	 */
	public IBillUnverifyCompletedDao getBillUnverifyCompletedDao() {
		/*
		 * @get
		 * @return billUnverifyCompletedDao
		 */
		return billUnverifyCompletedDao;
	}

	
	/**
	 * @set
	 * @param billUnverifyCompletedDao
	 */
	public void setBillUnverifyCompletedDao(
			IBillUnverifyCompletedDao billUnverifyCompletedDao) {
		/*
		 *@set
		 *@this.billUnverifyCompletedDao = billUnverifyCompletedDao
		 */
		this.billUnverifyCompletedDao = billUnverifyCompletedDao;
	}
}
