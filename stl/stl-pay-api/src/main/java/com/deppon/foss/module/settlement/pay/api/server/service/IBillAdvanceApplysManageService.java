package com.deppon.foss.module.settlement.pay.api.server.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceResultDto;

/**
 * 预付单管理服务
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-30 下午5:09:06
 */
public interface IBillAdvanceApplysManageService extends IService {

	/**
	 * 
	 * 查询预付单
	 * @author 095793-foss-LiQin
	 * @date 2012-11-2 下午2:59:38
	 */
	BillAdvanceResultDto queryBillAdvancePayApply(
			BillAdvanceDto billAdvanceApplysManageDto, int start, int limit,CurrentInfo currentInfo);

	/**
	 * 新增预付单
	 * @author 095793-foss-LiQin
	 * @date 2012-11-20 下午3:32:29
	 */
	void addAdvancePayApply(BillAdvanceDto billAdDto, CurrentInfo cInfo);


	/**
	 * 更新预付单，从费控返回工作流号
	 * @author 095793-foss-LiQin
	 * @date 2012-11-26 下午4:44:59
	 */
	void updateBillPayWorkFlow(BillAdvanceDto billAdDto);
	

	/**
	 * 费控返回审批结果，进行处理
	 * @author 095793-foss-LiQin
	 * @date 2012-11-29 上午11:15:56
	 */
	void dealBillAdvancePayResultProcessor(BillAdvanceDto billAdDto);
	
	
	/**
	 * 导出空运预付款
	 * @author 095793-foss-LiQin
	 * @date 2012-12-5 下午7:44:38
	 */
	HSSFWorkbook exportBillAdvancePay(BillAdvanceDto billAdDto ,CurrentInfo currentInfo);
	
	
	/**
	 * 预付单申请工作流失败，进行处理
	 */
	void  applyAdvancePayWorkflowFail(BillAdvanceDto billAdDto);
}
