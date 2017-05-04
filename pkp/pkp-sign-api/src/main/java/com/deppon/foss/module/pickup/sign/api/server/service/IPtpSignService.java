package com.deppon.foss.module.pickup.sign.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.vo.PtpSignVo;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;


/**
 * @author 239284
 * @description 合伙人零担签收业务接口
 * @date 2016-01-13
 *
 */
public interface IPtpSignService extends IService {

	/**
	 * 代收货款校验与扣款, 同时生成结清记录更新结清状态
	 * @author 239284 
	 * @date 2016-01-13
	 * @param waybillNo 		运单号
	 * @param deptCode	合伙人部门编码
	 * @param deptName	合伙人部门名称
	 * @param codAmount	代收货款金额
	 * @return 是否成功
	 * 若“代收货款应收单(H)“状态为“未扣款”或者为“扣款失败”，则触发合伙人交易平台进行可用余额扣款，
	 * 	  校验合伙人自助中可用余额与代收货款应收金额（即：代收货款应收单（H）中的应收金额）： 
	 * 			① 若“可用余额-代收货款应收金额”>=“0”元时，则完成扣款，允许正常签收，同时“代收货款应收单(H)”状态变为“扣款成功”
	 * 			②若“可用余额-代收货款应收金额”<0时，则无法正常扣款，“代收货款应收单(H)”扣款状态变为“扣款失败”，同时系统无法签收出库，
	 * 				并提示“可用余额不足，无法签收”。
	 */
	public boolean  validatePayCOD(PtpSignVo vo, CurrentInfo currentInfo);
	
	/**
	 * FOSS设置定时任务将签收结果表同步到PTP，每天 05:00,23:00 点进行签收时间同步,
	 * 同步的为前一天签收的数据， PTP保存并更新最新的签收时间.
	 */
	public void processptpSignjob();
	
	/**
	 * 生成结清记录，同时更新结清状态
	 * 	 @param	vo  运单号
	 *  @param	currentInfo  当前登录信息
	 */
	public void paymentInfoOperation(PtpSignVo vo, CurrentInfo currentInfo);
	
	/**
	 * 核销应收单，同时签收
	 * @param ptpVo 核销参数
	 * @param list 
	 * @param entity
	 * @param dto
	 * @param currentInfo
	 * @param orderNo
	 * @return 签收状态
	 */
	public String  wrieOffAndSign(PtpSignVo ptpVo , List<SignDetailEntity> list,  ArriveSheetEntity entity, LineSignDto dto, CurrentInfo currentInfo, String orderNo);
	
	/**
	 * 
	 * @param ptpVo 参数
	 * @param currentInfo 当前登录信息
	 * @param typeName 场景名称(字符串，如: '零担签收')
	 */
	public void ptpCodDeduct (PtpSignVo ptpVo, CurrentInfo currentInfo, String typeName);
	
	/**
	 * 合伙人签收时生效应付流水
	 * @param request 更新流水状态和生效时间的请求
	 * @return 是否成功
	 */
    /*	public boolean validBillSaleFlowByAsynESB(String waybillNo);*/
	
	
	/**
	 * <p>校验同一单号运单表的“到付金额“=合伙人代收货款应收+到付运费应收+到达应收
	 * 		<ol>①模式H-H, 校验“到付金额“=合伙人代收货款应收+到付运费应收+到达应收；</ol>
	 * 		<ol>②模式D-H, 校验“到付金额“=合伙人代收货款应收+到付运费应收+到达应收；</ol>
	 * </p>
	 * @author 353654
	 * @date 2016年8月20日
	 * @param waybillNo 运单号
	 * @param vestSystemCode 灰度系统归属
	 */
	public void checkToPayEqualReceivable(String waybillNo,String vestSystemCode);
	
	/**
	 * 二级网点历史单据签收校验
	 * @author 353654
	 * @date 2016年10月11日
	 * @param waybillNo 运单号
	 * @param vestSystemCode 灰度系统归属
	 */
	public void checkTwoLevelNetworkBill(String waybillNo,String vestSystemCode);
	
	/**
	 * <p>校验同一单号运单表的“到付金额“=合伙人代收货款应收+到付运费应收+到达应收
	 * 		<ol>①模式H-H, 校验“到付金额“=合伙人代收货款应收+到付运费应收+到达应收；</ol>
	 * 		<ol>②模式D-H, 校验“到付金额“=合伙人代收货款应收+到付运费应收+到达应收；</ol>
	 * </p>
	 * @author gpz
	 * @date 2016年8月20日
	 * @param waybillNo 运单号
	 */
	public void checkToPayEqualReceivable(String waybillNo);
	
	/**
	 * 二级网点历史单据签收校验
	 * @author gpz
	 * @date 2016年10月11日
	 * @param waybillNo 运单号
	 */
	public void checkTwoLevelNetworkBill(String waybillNo);
}
