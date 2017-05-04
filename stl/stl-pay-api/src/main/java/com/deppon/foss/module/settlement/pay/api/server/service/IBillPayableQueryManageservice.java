package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageResultDto;


/**
 * TODO（描述类的职责）
 * @author 045738-foss-maojianqiang
 * @date 2012-11-17 下午5:58:36
 */
public interface IBillPayableQueryManageservice extends IService {
	
	/**
	 * 查询应付单
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-17 下午6:05:34
	 */
	BillPayableManageResultDto queryBillPayable(BillPayableManageDto dto,int start,int limit,CurrentInfo currentInfo );
	
	/**
	 * 导出excel
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-21 上午10:05:18
	 */
	HSSFWorkbook exportBillPayable(BillPayableManageDto dto,CurrentInfo currentInfo);
	/**
	 * 付款操作
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-21 下午8:08:35
	 */
	BillPayableManageResultDto payForBillPayable(BillPayableManageDto dto);
	
	/**
	 * 校验是否可以支付
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-22 下午4:26:38
	 */
	void validateCanPay(List<BillPayableEntity> payableList,String payType);
	
	/**
	 * 审核应付单
	 * @author 045738-foss-maojianqiang
	 * @param 前台选择需要审核单据，当前登录信息
	 * @date 2012-12-20 下午1:53:24
	 * @return
	 */
	public void auditPayable(BillPayableManageDto dto,CurrentInfo currentInfo);
	/**
	 * 反审核应付单
	 * @author 045738-foss-maojianqiang
	 * @param 前台选择需要反审核单据，当前登录信息
	 * @date 2012-12-20 下午1:53:24
	 * @return
	 */
	public void unAuditPayable(BillPayableManageDto dto,CurrentInfo currentInfo);
	
	
	/**
	 *  根据付款单号和付款单明细来源单号查询应付单
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-3-20 下午6:01:03
	 * @param
	 * @return 成功失败标记
	 * @exception SettlementException
	 * @see
	 */
	List<BillPayableEntity> queryListByPaymentNo(BillPayableManageDto dto);
	
	/**
	 * 根据传的运单号和客户编码查询应收单是否已经核销
	 * 
	 * @author 268217
	 * @date 2016-04-14
	 */
	public String queryReceivableBill(BillPayableManageDto dto);
}
