package com.deppon.foss.module.settlement.writeoff.api.server.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillRecWriteoffBillPayDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillRecWriteoffBillPayResultDto;

/**
 * 提供查询应收单应付单、应收冲应付服务
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-26 上午8:54:54
 */
public interface IBillRecWriteoffBillPayService extends IService {

	/**
	 * 
	 * 查询待核销的应收单应付单列表
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-10-26 上午8:53:59
	 * @param billRecWriteoffBillPayDto,
	 * 			cInfo
	 * 			应收冲应付参数Dto,
	 *  		当前登录用户
	 * @return BillRecWriteoffBillPayResultDto
	 * 			应收冲应付返回dto
	 */
	BillRecWriteoffBillPayResultDto queryRecWriteoffPayList(
			BillRecWriteoffBillPayDto billRecWriteoffBillPayDto,
			CurrentInfo cInfo);

	/**
	 * 
	 * 按照应收单单号、运单号、应付单、运单号查询待核销单据信息
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-10-29 下午2:35:41
	 * @param billRecWriteoffBillPayDto,
	 * 			cInfo
	 * 			应收冲应付参数Dto,
	 *  		当前登录用户
	 * @return BillRecWriteoffBillPayResultDto
	 * 			应收冲应付返回dto
	 */
	BillRecWriteoffBillPayResultDto queryBillRecOrBillPayNums(
			BillRecWriteoffBillPayDto billRecWriteoffBillPayDto,
			CurrentInfo cInfo);

	/**
	 * 
	 * 核销应收单应付单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-10-26 上午8:56:55
	 * @param billRecWriteoffBillPayDto,
	 * 			cInfo
	 * 			应收冲应付参数Dto,
	 *  		当前登录用户
	 * @return BillRecWriteoffBillPayResultDto
	 * 			应收冲应付返回dto
	 */
	BillRecWriteoffBillPayResultDto writeoffBillRecivableBillPaybable(
			BillRecWriteoffBillPayDto billRecWriteoffBillPayDto,
			CurrentInfo cInfo) throws SettlementException;

	/**
	 * 导出应收单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-28 下午4:43:53
	 * @param billRecPayDto,
	 * 			currentInfo
	 * 			应收冲应付参数Dto,
	 *  		当前登录用户
	 * @return HSSFWorkbook
	 * 			报表
	 */
	HSSFWorkbook exportWriteoffBillRec(BillRecWriteoffBillPayDto billRecPayDto,
			CurrentInfo currentInfo);

	/**
	 * 导出应付单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-28 下午4:43:57
	 * @param billRecPayDto,
	 * 			currentInfo
	 * 			应收冲应付参数Dto,
	 *  		当前登录用户
	 * @return HSSFWorkbook
	 * 			报表
	 */
	HSSFWorkbook exportWriteoffBillPay(BillRecWriteoffBillPayDto billRecPayDto,
			CurrentInfo currentInfo);
}
