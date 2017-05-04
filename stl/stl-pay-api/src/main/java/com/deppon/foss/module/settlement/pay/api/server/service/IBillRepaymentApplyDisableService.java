package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.pay.api.shared.domain.RepaymentDisableApplicationEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.RepaymentDisableDetailEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentDisableDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentDisableResultDto;

/**
 * 还款单申请作废服务类
 *  功能：1 申请作废 2 审核作废 3 查询作废
 *
 *
 * @author 092036-foss-bochenlong
 * @date 2013-11-19 下午4:26:01
 */
public interface IBillRepaymentApplyDisableService {
	/**
	 * 申请作废查询核销明细
	 *  有效还款单和核销明细
	 */
	BillRepaymentDisableResultDto queryRepayment(BillRepaymentDisableDto dto);
	
	/**
	 * 
	 *申请作废查询核销明细
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-11-25 下午1:54:08 
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 */
	BillRepaymentDisableResultDto queryRepaymentWriteoff(BillRepaymentDisableDto dto ,int start ,int limit);
	
	/**
	 * 
	 *添加申请作废记录
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-11-19 下午4:30:18 
	 * @param dto
	 */
	void addDisableApplication(BillRepaymentDisableDto dto);
	/**
	 * 
	 *审核申请作废记录
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-11-19 下午4:30:37 
	 * @param dto
	 */
	void approveApplication(BillRepaymentDisableDto dto);

	
	/**
	 * 
	 *查询作废申请
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-11-19 下午5:43:33 
	 * @param dto
	 * @return
	 */
	List<RepaymentDisableApplicationEntity> queryDisableApplicationByDto(BillRepaymentDisableDto dto);
	
	/**
	 * 
	 * 查询作废申请（分页查询）
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-11-25 下午9:56:37 
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 */
	BillRepaymentDisableResultDto queryDisableApplicationByDto(BillRepaymentDisableDto dto ,int start ,int limit);
	
	/**
	 * 
	 *查询作废申请明细
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-11-19 下午5:43:46 
	 * @param repaymentNo
	 * @return
	 */
	List<RepaymentDisableDetailEntity> queryDisableDetailByDto(BillRepaymentDisableDto dto);

	HSSFWorkbook queryExportBillRepaymentApply(
			BillRepaymentDisableDto billRepayDto, CurrentInfo cInfo);


}
