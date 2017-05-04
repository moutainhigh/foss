/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-deliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/deliver/server/service/impl/PdaSignService.java
 * 
 * FILE NAME        	: PdaSignService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 *****************************************************************************
 *修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-04-25 	新增	王辉	V0.1
2012-06-26	根据ITA建议修改	王辉	V0.2
2012-7-11	删除调用出库接口	王辉	V0.6
2012-7-24	业务评审完毕，升至0.9	 王辉	V0.9

1.	
	SUC-485-签收出库(PDA和中转接口)
1.1
	相关业务用例
BUC_FOSS_5.50.30_530 验货签收
1.2	
	用例描述
PDA扫描出库，调用签收出库接口签收货物。
1.3	
	用例条件
条件类型	描述	引用系统用例
前置条件	
1.	
营业员已结清货款
	1.
		提货付款及复核-结清货款（SUC-122）
后置条件		
1.4	
	操作用户角色
操作用户	描述
理货员	理货员扫描货物出库
1.5	
	界面要求
1.5.1
	表现方式
接口
1.5.2	
	界面原型
无
1.5.3	
	界面描述
无

1.6	
	操作步骤
序号	基本步骤	相关数据	补充步骤
1	传入参数	参数信息	参见SR1
2	查询货款结清记录	参数信息	参见SR2
3	更新到达联签收日期、签收部门及状态	参数信息	
4	调用结算子系统“签收接口”	参数信息	参见SR4
5	调用短信接口发送短信至发货人、收货人	参数信息	参见SR3
6	调用在线通知接口发送在线通知至发货部门	参数信息	

扩展事件写非典型或异常操作过程
序号	扩展事件	相关数据	备注
1a	
	当接口参数校验不合法时，终止操作，并给用户异常信息，终止操作	参数信息
	1.	
		运单号为空时，异常信息为“运单号为空，不能签收出库”
	2.
		运单号在系统中不存在时，异常信息为“运单号XX在系统中不存在，不能签收出库”
	3.
		签收部门为空时，异常信息为“签收部门为空，不能签收出库”
	4.	
		出库时间时，异常信息为“出库时间为空，不能签收出库”
2a
	校验运单是否已做货款结清		1.	当运单未做货款结清时，异常信息为“运单未做货款结清，不能签收出库”，终止操作
4a	
	调用结算子系统“签收接口”异常时，将结算子系统接口中异常信息返回，终止操作		
5a	
	调用短信接口异常时，将短信接口异常信息返回，不终止操作		
6a	
	调用在线通知接口异常时，将在线通知接口异常信息返回，不终止操作		

1.7	
	业务规则
序号	描述
SR1	
1.	
	运单号不能为空
2.	
	运单号在系统中存在
3.	
	签收部门不能为空
4.	
	出库时间不能为空
SR2	
	运单必须已做货款结清
SR3	
	短信内容包括：货物单号、此货签收时间、签收件数、签收人、是否有异常
SR4	
	专线正常签收接口：SUC-597
偏线空运正常签收接口：SUC-651
异常丢货、弃货、违禁品签收接口：SUC-159
SR5	
	中转出库接口（SUC-238）

1.8	
	数据元素
1.8.1
	参数信息
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
运单号		文本		10	是	
到达联编号		文本		20	是	
件数		文本		10	是	
签收部门		文本		20	是	
出库时间		文本		6	是		
货物件信息集合		文本			是	参考数据元素“货物件信息”
	

1.9	
	非功能性需求
使用量	每天派送出库80W，客户自提占40%，80W*40%=32W
2012年全网估计用户数	10000用户
响应要求（如果与全系统要求 不一致的话）	高并发要求，3s内响应
使用时间段	营业部上班时间
高峰使用时间段	13:00-20:00


1.10	
	接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
签收接口	FOSS-结算子系统	结算签收接口
短信接口	短信平台	发送短信给发货人、收货人
在线通知接口	Foss-综合管理子系统	发送通知给发货部门


 **/
package com.deppon.foss.module.pickup.deliver.server.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeByHandService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.deliver.server.utils.HttpClientUtils;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaSignService;
import com.deppon.foss.module.pickup.pda.api.shared.define.PdaConstants;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaSignDto;
import com.deppon.foss.module.pickup.pda.api.shared.exception.PdaProcessException;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto;
import com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IPickupService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRookieService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISendWaybillTrackService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignChangeService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignStockJobService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.DeliverHandlerConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.PickupConstants;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillSignResultDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ITodoActionService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ActualFreightDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICUBCSignService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ILineSignService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CUBCSignOrRevSignResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.FOSSSignOrRevSignRequestDto;
import com.deppon.foss.module.settlement.consumer.api.shared.exception.CUBCSignException;
import com.deppon.foss.module.transfer.partialline.api.server.service.IPrintAgentWaybillService;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillRecordEntity;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
 * 签收出库(PDA和中转接口)
 * service实现
 * @author
		foss-meiying
 * @date 
 * 		2012-11-28 上午11:51:55
 * @since
 * @version
 */
public class PdaSignService implements IPdaSignService {
	
	private IWaybillSignResultDao waybillSignResultDao;
	public void setWaybillSignResultDao(IWaybillSignResultDao waybillSignResultDao) {
		this.waybillSignResultDao = waybillSignResultDao;
	}
	
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	private static final String SERVICE_CODE = "com.deppon.foss.module.pickup.deliver.server.service.impl.PdaSignService";
	
	/**
	 * add by 353654  注入CUBC签收服务
	 */
	private ICUBCSignService cUBCSignService;
	
	public void setcUBCSignService(ICUBCSignService cUBCSignService) {
		this.cUBCSignService = cUBCSignService;
	}
	/**
	 * 记录
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PdaSignService.class);
	/**
	 * 运单签收结果
	 * service接口 
	 */
	private IWaybillSignResultService waybillSignResultService;
	/**
	 * 到达联
	 * service接口
	 */
	private IArriveSheetManngerService arriveSheetManngerService;
	/**
	 * 运单
	 * service接口
	 */
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * 外场相关共通接口
	 */
	private IHandleQueryOutfieldService handleQueryOutfieldService;
	/**张新
	 * 打印代理面单service
	 */
	private IPrintAgentWaybillService printAgentWaybillService;
	/**
	 * 结清货款
	 * Service接口
	 */
	private IRepaymentService repaymentService;
	/**
	 * 结算签收
	 * Service接口
	 */
	private ILineSignService lineSignService;
	/**
	 * 员工
	 * service接口
	 */
	private IEmployeeService employeeService;
	/**
	 * 运单快递相关服务类接口
	 */
	private IWaybillExpressService waybillExpressService;
	/**
	 * 菜鸟相关服务类接口
	 */
	private IRookieService rookieService;
	/**
	 * 组织信息
	 * service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 签收明细
	 * service接口
	 */
	private ISignDetailService signDetailService;
	/**
	 * actualFreightService
	 * 接口
	 */
	private IActualFreightService actualFreightService;
	/**
	 *  中转
	 *  出库接口
	 */
	private IStockService stockService;
	
	/**
	 * service层
	 */
	private  IPickupService pickupService;
	
	/**
	 * 签收反签收同步改异步接口
	 */
	private ISignStockJobService signStockJobService;
	
	/**
	 * 运单Dao
	 */
	private IWaybillDao waybillDao;
	/**
	 * 代办事项服务类
	 */
	private ITodoActionService todoActionService;
	/**
	 * 营业部 Service实现
	 */
	private ISaleDepartmentService saleDepartmentService;
	/**
	 * 部门复杂service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 签收变更结果接口
	 */
	private ISignChangeService signChangeService;
	//待人工补码记录service
	private IAutoAddCodeByHandService autoAddCodeByHandService;

	public void setAutoAddCodeByHandService(
			IAutoAddCodeByHandService autoAddCodeByHandService) {
		this.autoAddCodeByHandService = autoAddCodeByHandService;
	}
	/**
	 * 轨迹推送接口（快递100,、菜鸟）
	 */
	private ISendWaybillTrackService sendWaybillTrackService; 
	/**
	 * 轨迹推送接口 注入
	 */
	public void setSendWaybillTrackService(
			ISendWaybillTrackService sendWaybillTrackService) {
		this.sendWaybillTrackService = sendWaybillTrackService;
	}
	/**
	 * 验证参数
	 * 		如果不满足条件，抛出异常
	 * @author 
	 * 		foss-meiying
	 * @date 
	 * 		2013-3-9 下午4:48:42
	 * @param dto
	 * 			dto.waybillNo 
	 * 				运单号
	 *  		dto.arrivesheetNo 
	 * 				到达联编号
	 *  		dto.signGoodsQty 
	 * 				 签收件数
	 *  		dto.signDeptCode 
	 * 				签收部门编码
	 *  		dto.signTime 
	 * 				签收时间
	 *  		dto.situation 
	 * 				签收情况
	 * 				（正常签收、
	 * 				异常-破损、
	 * 				异常-潮湿、
	 * 				异常-污染、
	 * 				异常-其他、
	 * 				部分签收）
	 *  		dto.operatorCode 
	 * 				操作人工号
	 *  		dto.serialNos 
	 * 				签收流水号列表
	 *  		dto.signNote 
	 * 				 签收备注
	 * 			
	 * @see
	 */
	private void validateParams(PdaSignDto dto) {
		//如果传入的对象为空
		if (null == dto) {//对象为空
			LOGGER.error("接口传入的数据为空");//记录日志
			throw new PdaProcessException("接口传入的数据为空");//接口传入的数据为空
		}else if (StringUtils.isBlank(dto.getWaybillNo())) {//如果运单号为空
			//如果运单号为空
			LOGGER.error("运单号为空，不能签收出库");//记录日志
			throw new PdaProcessException("运单号为空，不能签收出库");//运单号为空，不能签收出库
		} else if (StringUtils.isBlank(dto.getArrivesheetNo())){//如果到达联编号为空
			//如果到达联编号为空
			LOGGER.error("到达联编号为空！,不能签收出库");//记录日志
			throw new PdaProcessException("到达联编号为空！,不能签收出库");//到达联编号为空！,不能签收出库
		} else if(StringUtils.isBlank(dto.getSignDeptCode())) {//如果签收部门为空
			LOGGER.error("签收部门为空，不能签收出库");//记录日志
			throw new PdaProcessException("签收部门为空，不能签收出库");//签收部门为空，不能签收出库
		}else if(dto.getSignTime() == null) {//如果签收时间为空
			LOGGER.error("签收时间为空，不能签收出库");//记录日志
			throw new PdaProcessException("签收时间为空，不能签收出库");//签收时间为空，不能签收出库
		}else if(CollectionUtils.isEmpty(dto.getSerialNos())){//如果签收流水号列表为空
			LOGGER.error("签收流水号列表为空，不能签收出库");//记录日志
			throw new PdaProcessException("签收流水号列表为空，不能签收出库");//签收流水号列表为空，不能签收出库
		}else if(dto.getSignGoodsQty() == null || dto.getSignGoodsQty().intValue()<=0){
			throw new PdaProcessException("签收件数必须大于0,不能签收出库");
		}else if(dto.getSerialNos().size() != dto.getSignGoodsQty()){
			throw new PdaProcessException("签收件数与流水号件数不一致，不能签收出库");
		}else if(StringUtils.isBlank(dto.getSituation())){
			throw new PdaProcessException("签收情况为空，不能签收出库");
		}
	}
	/**
	 * 
	 * pda签收出库操作
	 * @author foss-meiying
	 * @date 2012-12-19 上午11:35:34
	 * @param dto
	 * 			dto.waybillNo 
	 * 				运单号
	 *  		dto.arrivesheetNo 
	 * 				到达联编号
	 *  		dto.signGoodsQty 
	 * 				 签收件数
	 *  		dto.signDeptCode 
	 * 				签收部门编码
	 *  		dto.signTime 
	 * 				签收时间
	 *  		dto.situation 
	 * 				签收情况
	 * 				（正常签收、
	 * 				异常-破损、
	 * 				异常-潮湿、
	 * 				异常-污染、
	 * 				异常-其他、
	 * 				部分签收）
	 *  		dto.operatorCode 
	 * 				操作人工号
	 *  		dto.serialNos 
	 * 				签收流水号列表
	 *  		dto.signNote 
	 * 				 签收备注
	 * 
	 * @see com.deppon.foss.module.pickup.pda.api.server.service.IPdaSignService
	 * #pdaSign(com.deppon.foss.module.pickup.pda.api.shared.dto.PdaSignDto)
	 */
	@Override
	public String pdaSign(PdaSignDto dto) {
		String resultMsg = "";//返回信息
		LOGGER.info(" --开始签收出库操作,pda传入参数---- " + ReflectionToStringBuilder.toString(dto));//记录日志
		this.validateParams(dto);//验证传入的参数
		WaybillEntity waybill = new WaybillEntity();
		//查询运单号是否存在
		if(!waybillManagerService.isWayBillExsits(dto.getWaybillNo())){
			StringBuffer errorMsg = new StringBuffer();//保存错误信息
			errorMsg.append("运单号");//运单号
			errorMsg.append(dto.getWaybillNo());//对应的运单号
			errorMsg.append("在系统中不存在，不能签收出库");//错误信息
			LOGGER.error(errorMsg.toString());//记录日志
			throw new PdaProcessException("该运单号在系统中不存在，不能签收出库");
		} else {
			RepaymentEntity repayment = new RepaymentEntity();
			repayment.setWaybillNo(dto.getWaybillNo());
			try {
				signChangeService.checkWayBillRfcStatus(dto.getWaybillNo());//验证运单签收变更、反签收申请情况
			} catch (SignException e) {
				throw new PdaProcessException("该运单有到达更改单正在审核中，不能签收！");
			}
			//PDA结清     243921
			repayment.setSettleApproach(SettlementDictionaryConstants.SETTLE_APPROACH_MOBILE);
			//调用结清货款接口判断当前运单是否已结清
			boolean result =repaymentService.paymentOperate(repayment);
			//运单未做货款结清，不能签收出库
			if(!result){
				LOGGER.error("--运单未做货款结清，不能签收出库");//记录日志
				throw new PdaProcessException("运单未做货款结清，不能签收出库");//该运单未做货款结清，不能签收出库
			}
			//根据到达联编号,到达联状态为‘生成’查运单运输性质  是否整车运单,到达联id,证件号码,证件类型,提货人,最终配载部门
			SignDto signDto = arriveSheetManngerService.queryPartWaybillpartArrivesheet(dto.getArrivesheetNo(),ArriveSheetConstants.STATUS_GENERATE);
			if(signDto == null){
				throw new PdaProcessException("找不到有效状态的到达联,不能签收出库");//找不到有效状态的到达联,不能签收出库
			}
			if(!dto.getWaybillNo().equals(signDto.getWaybillNo())){
				throw new PdaProcessException("传入的运单号跟到达联对应的运单号不一致，不能签收");//传入的运单号跟到达联对应的运单号不一致，不能签收
			}
			if(signDto.getArriveSheetGoodsQty()!= null && signDto.getArriveSheetGoodsQty()<dto.getSignGoodsQty()){
				throw new PdaProcessException("签收件数不能大于到达联件数,签收出库失败！");//部分签收时，签收件数不能大于到达联件数
			}
			//add by 231438 快递PDA自提签收调用零担接口限制
			if(WaybillConstants.directDetermineIsExpressByProductCode(signDto.getProductCode())){
				throw new PdaProcessException("快递运单无法调用零担接口签收！");//部分签收时，签收件数不能大于到达联件数
			}
			SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(dto.getSignDeptCode());
			if (saleDepartment == null ) {
				// 判断 最终配载部门 是否驻地部门
				SaleDepartmentEntity saleDepar = saleDepartmentService.querySaleDepartmentByCode(signDto.getLastLoadOrgCode());
				// 是否驻地部门
				if (saleDepar != null && saleDepar.checkStation()) {
					String endStockOrgCode = saleDepar.getTransferCenter();//得到驻地部门外场
					//非营业部找到它上级所属外场的编码
					List<String> bizTypes = new ArrayList<String>();
					bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(dto.getSignDeptCode(), bizTypes);
					if(orgAdministrativeInfoEntity != null){
						if(!StringUtils.equals(endStockOrgCode, orgAdministrativeInfoEntity.getCode())){
							throw new PdaProcessException("签收部门与运单的最终库存部门不一致,不能进行签收操作");
						}
					}else{
						throw new PdaProcessException("签收部门与运单的最终库存部门不一致,不能进行签收操作");
					}
				} else {
					throw new PdaProcessException("签收部门与运单的最终配载部门不一致,不能进行签收操作");
				}
			} else {//如果不为空，为营业部
				if(!dto.getSignDeptCode().equals(signDto.getLastLoadOrgCode())){//如果签收部门与运单的最终配载部门不一致
					throw new PdaProcessException("签收部门与运单的最终配载部门不一致,不能进行签收操作");
				}
			}
			CurrentInfo currentInfo = this.getCurrentInfo(dto.getOperatorCode(),dto.getSignDeptCode());
			CurrentInfo currentinfoByLastLoadOrgCode = this.getCurrentInfo(dto.getOperatorCode(),signDto.getLastLoadOrgCode());
			signDto.setSystemDate(new Date());
			ActualFreightEntity actual = actualFreightService.queryByWaybillNo(dto.getWaybillNo());
			BeanUtils.copyProperties(signDto,waybill);//把signDto里的内容复制到waybill里
			boolean isSendInvoiceInfo=waybillSignResultService.isNeedSendInvoiceInfo(waybill,actual);//是否将发票信息传给发票系统
			if(WaybillConstants.INNER_PICKUP.equals(signDto.getReceiveMethod())){//提货方式为汽运内部自提
				LOGGER.info("--提货方式为汽运内部自提 不需要调用结算签收接口----");//记录日志
			}else {
				this.confirmTaking(dto,currentInfo,signDto);
			}
			if(dto.getSerialNos().size()>SignConstants.DEFAULT_SERIALNOS_LIMIT_COUNT){
				// 系统调用中转接口（SUC-238）出库货物
				for (int i = PdaConstants.ZERO; i < dto.getSerialNos().size(); i++) {
					SignDetailEntity signDetail = new SignDetailEntity();
					signDetail.setSerialNo(dto.getSerialNos().get(i));//流水号
					// 到达联编号
					signDetail.setArrivesheetNo(dto.getArrivesheetNo());
					if(ArriveSheetConstants.SITUATION_NORMAL_SIGN.equals(dto.getSituation())){
						// 签收情况
						signDetail.setSituation(dto.getSituation());
					}
					signDetailService.addSignDetailInfo(signDetail);
					SignStockEntity signStock = new SignStockEntity();
					// 运单号
					signStock.setWaybillNo(dto.getWaybillNo());
					// 流水号
					signStock.setSerialNo(dto.getSerialNos().get(i));
					// 部门编码
					signStock.setStockOrgCode(currentinfoByLastLoadOrgCode.getCurrentDeptCode());
					signStock.setStockOrgName(currentinfoByLastLoadOrgCode.getCurrentDeptName());//部门名称 
					// 操作人姓名
					signStock.setOperator(currentinfoByLastLoadOrgCode.getEmpName());
					// 操作人工号
					signStock.setOperatorCode(currentinfoByLastLoadOrgCode.getEmpCode());
					// 出入库类型 签收出库
					signStock.setInoutType(StockConstants.SIGN_OUT_STOCK_TYPE);
					// 添加到sign_stock操作
					try {
						signStockJobService.addSelective(signStock);
					} catch (BusinessException e) {//捕获异常
						LOGGER.error(e.getMessage(), e);//记录日志
						throw new PdaProcessException(e.getErrorCode(),e);//抛出异常
					}
				}
				LOGGER.info("--保存签收明细完成 添加sign_stock完成");//记录日志
			}else {
				for (int i = PdaConstants.ZERO; i < dto.getSerialNos().size(); i++) {
					SignDetailEntity signDetail = new SignDetailEntity();
					signDetail.setSerialNo(dto.getSerialNos().get(i));//流水号
					// 到达联编号
					signDetail.setArrivesheetNo(dto.getArrivesheetNo());
					if(ArriveSheetConstants.SITUATION_NORMAL_SIGN.equals(dto.getSituation())){
						// 签收情况
						signDetail.setSituation(dto.getSituation());
					}
					signDetailService.addSignDetailInfo(signDetail);
					// 系统调用中转接口（SUC-238）出库货物
					InOutStockEntity inOutStock = new InOutStockEntity();
					// 运单号
					inOutStock.setWaybillNO(dto.getWaybillNo());
					// 流水号
					inOutStock.setSerialNO(dto.getSerialNos().get(i));
					// 部门编码
					inOutStock.setOrgCode(currentinfoByLastLoadOrgCode.getCurrentDeptCode());
					// 操作人姓名
					inOutStock.setOperatorName(currentinfoByLastLoadOrgCode.getEmpName());
					// 操作人工号
					inOutStock.setOperatorCode(currentinfoByLastLoadOrgCode.getEmpCode());
					/**
					 * 出入库类型 签收出库
					 */
					inOutStock.setInOutStockType(StockConstants.SIGN_OUT_STOCK_TYPE);
					// 进行出库操作
					try {
						stockService.outStockPC(inOutStock);
					} catch (BusinessException e) {//捕获异常
						LOGGER.error(e.getMessage(), e);//记录日志
						throw new PdaProcessException(e.getErrorCode(),e);//抛出异常
					}
				}
				LOGGER.info("--保存签收明细完成，调用中转出库完成");//记录日志
			}
			try {
				//如果签收件数小于运单开单件数
				if(dto.getSignGoodsQty() != null &&dto.getSignGoodsQty()<waybill.getGoodsQtyTotal()){
					todoActionService.updateLabeledStatusByWaybillNum(dto.getWaybillNo(),dto.getSerialNos());
				}else {
					todoActionService.updateLabeledStatusByWaybillNum(dto.getWaybillNo(),null);
				}
				ActualFreightDto actualFreightDto = new ActualFreightDto(dto.getWaybillNo(),dto.getSignGoodsQty());
				//PDA签收 修改运单状态里的到达未出库件数
				actualFreightService.updateArriveNotoutGoodsQtyByWaybillNo(actualFreightDto);
				//更新到达联信息
				ArriveSheetEntity entity = this.updateArriveSheet(dto,currentInfo,signDto);
				//添加运单签收结果
				String signStatus = this.addWaybillSignResult(entity,signDto,currentInfo,waybill);
				//update
				PickupResultDto pickupResultDto = pickupService.isExistByWaybill(entity.getWaybillNo());
				if(pickupResultDto!=null&&!pickupResultDto.getState().equals(PickupConstants.GOOD_STATE_REVOCATION)){
					if(SignConstants.SIGN_STATUS_ALL.equals(signStatus)){//如果签收状态为全部签收
						//已全部签收
						pickupResultDto.setState(PickupConstants.GOOD_STATE_ALLSIGN);
					}else if(SignConstants.SIGN_STATUS_PARTIAL.equals(signStatus)){//如果签收状态为部分签收
						//已部分签收
						pickupResultDto.setState(PickupConstants.GOOD_STATE_PARTSIGN);
					}
					WaybillEntity rstWaybill = waybillDao.queryWaybillByNo(entity.getWaybillNo());
					updateDataInit(pickupResultDto, rstWaybill, PickupConstants.IS_SIGN_STATE);
					pickupService.updatePickupStateByPickupResultDto(pickupResultDto);
				}
				//update
				WaybillSignResultDto waybillSigndto = new WaybillSignResultDto();
				BeanUtils.copyProperties(entity,waybillSigndto);
				//   如果代收人证件号码为空，则不用给收货客户发送短信
				if(actual!= null  &&StringUtil.isBlank(actual.getCodIdentifyCode() )  && StringUtil.isBlank(actual.getCodIdentifyType())){
					waybillSigndto.setIsSendSMSToReceiveCustomer(FossConstants.NO);//不用给收货人发送短信
				}
				//调用短信接口发送短信  调用在线通知接口发送在线通知至发货部门
				resultMsg =waybillSignResultService.sendMessNotice(waybill, currentInfo,waybillSigndto);
				//应用监控签收调用
				waybillSignResultService.signCounterByWaybillNo(currentInfo, waybill);
				if(isSendInvoiceInfo){
					//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
					waybillSignResultService.sendInvoiceInfo(waybill,actual);
				}
			}catch(BusinessException e){//捕获异常
				LOGGER.error(e.getMessage(), e);//记录日志
				throw new PdaProcessException(e.getErrorCode(),e);//抛出异常
			}
		}
		LOGGER.info(" pda签收出库操作完成---- "+ resultMsg);//记录日志
		return StringUtils.isNotBlank(resultMsg) ? resultMsg : "pda签收出库操作完成";
	
	}
	
	/**
	 * 初始化符合条件的插入数据
	 * @date 2014-11-24 下午6:45:19
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.SignService#updateDataInit(com.deppon.foss.module.pickup.predeliver.api.shared.dto.PickupResultDto)
	 */
	private PickupResultDto updateDataInit(PickupResultDto rstPickupResultDto,WaybillEntity rstWaybill,String state) {
		if(rstWaybill!=null){
			rstPickupResultDto.setBillingGoodsQty(rstWaybill.getGoodsQtyTotal());
			rstPickupResultDto.setGoodPackage(rstWaybill.getGoodsPackage());
			rstPickupResultDto.setGoodSize(rstWaybill.getGoodsSize());
			rstPickupResultDto.setGoodVolume(rstWaybill.getGoodsVolumeTotal());
			rstPickupResultDto.setGoodWeight(rstWaybill.getGoodsWeightTotal());
			rstPickupResultDto.setOperTime(new Date());
			String pickUpState = rstPickupResultDto.getState();
			if(StringUtils.isNotEmpty(state)&&state.equals(PickupConstants.IS_SIGN_STATE)){
				if(StringUtils.isNotEmpty(pickUpState)){
					if(pickUpState.equals(PickupConstants.GOOD_STATE_ALLSIGN)||pickUpState.equals(PickupConstants.GOOD_STATE_PARTSIGN)){
						//不操作
					}
				}
			}else{
				rstPickupResultDto.setState(PickupConstants.GOOD_STATE_HASINFORM);
			}
			if(StringUtil.isNotEmpty(state)&&StringUtils.equals(state, PickupConstants.PICKUP_INSERT)){//插入操作
				rstPickupResultDto.setWaybillNo(rstWaybill.getWaybillNo());
				rstPickupResultDto.setId(UUIDUtils.getUUID());
				CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
				rstPickupResultDto.setOperateOrgCode(currentInfo.getCurrentDeptCode());
				rstPickupResultDto.setOperateOrgName(currentInfo.getCurrentDeptName());
				rstPickupResultDto.setOperateUserName(currentInfo.getEmpName());
				rstPickupResultDto.setOperateUserCode(currentInfo.getEmpCode());
				rstPickupResultDto.setState(PickupConstants.GOOD_STATE_HASINFORM);
				List<String> endStockOrgCodes = handleQueryOutfieldService.getEndStockCodeAndAreaCode(currentInfo.getCurrentDeptCode());
				// 获取对应的营业部或驻地外场
				if(!CollectionUtils.isEmpty(endStockOrgCodes) && StringUtils.isNotEmpty(endStockOrgCodes.get(0))){
					rstPickupResultDto.setEndStockOrgCode(endStockOrgCodes.get(0));
				}else {
					rstPickupResultDto.setEndStockOrgCode(DeliverHandlerConstants.DEFAULT_TRANSFER_CENTER);
				}
				return rstPickupResultDto;
			}
		}
		return rstPickupResultDto;
	}
	
	
	/**
	 * 通过录入员工编码查询姓名 ,
	 * 通过签收部门编码查询部门名称 
	 * @author foss-meiying
	 * @date 2012-12-18 下午5:21:08
	 * @param   dto
	 * 			dto.waybillNo 
	 * 				运单号
	 *  		dto.arrivesheetNo 
	 * 				到达联编号
	 *  		dto.signGoodsQty 
	 * 				 签收件数
	 *  		dto.signDeptCode 
	 * 				签收部门编码
	 *  		dto.signTime 
	 * 				签收时间
	 *  		dto.situation 
	 * 				签收情况
	 * 				签收情况
	 * 				（正常签收、
	 * 				异常-破损、
	 * 				异常-潮湿、
	 * 				异常-污染、
	 * 				异常-其他、
	 * 				部分签收）
	 *  		dto.operatorCode 
	 * 				操作人工号
	 *  		dto.serialNos 
	 * 				签收流水号列表
	 *  		dto.signNote 
	 * 				 签收备注
	 * @return
	 * @see
	 * 
	 */
	private CurrentInfo getCurrentInfo(String operatorCode,String deptCode){
		//通过录入员编码查询姓名
		EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(operatorCode);
		UserEntity user = new UserEntity();
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		// 获取操作部门
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(deptCode);
		if(emp != null){//如果不为空
			user.setEmployee(emp);
		}else {
			emp = new EmployeeEntity();
			emp.setEmpName(PdaConstants.DEFAULT_EMP_NAME_PDA);//默认值
			emp.setEmpCode(operatorCode);//员工编码
			user.setEmployee(emp);//用户信息
		}
		if(org != null){
			dept.setName(org.getName());// 得到部门名称
			dept.setUnifiedCode(org.getUnifiedCode());//标杆编码
		}else {
			dept.setName(PdaConstants.DEFAULT_EMP_NAME_PDA);//默认值 “PDA"
		}
		//部门编码
		dept.setCode(deptCode);
		return new CurrentInfo(user, dept);//返回对象
	}
	/**
	 * 添加运单签收结果信息
	 * (时间建模)
	 * @author 
	 * 		foss-meiying
	 * @date 
	 * 		2012-12-28 上午11:41:38
	 * @param entity 到达联对象
	 * 		 entity.waybill 
	 * 			运单号
	 * 		entity.arrivesheetNo
	 * 			到达联编号
	 * 		entity.deliverymanname
	 * 			提货人名称
	 * 		entity.identifyType
	 * 			证件类型
	 * 		entity.identifyCode
	 * 			证件号码
	 * 		entity.situation
	 * 			签收情况
	 * 		entity.arriveSheetGoodsQty
	 * 			到达联件数
	 * 		entity.signGoodsQty
	 * 			签收件数
	 * 		entity.signNote
	 * 			签收备注
	 * 		entity.signTime
	 * 			签收时间
	 * 		entity.status
	 * 			到达联状态
	 * 		entity.isPdaSign
	 * 			是否PDA签到
	 * 		entity.operateTime
	 * 			签收操作时间
	 * 		entity.operator
	 * 			操作人
	 * 		entity.operatorCode
	 * 			操作人编码
	 * 		entity.operateOrgName
	 * 			 操作部门名称
	 * 		entity.operateOrgCode
	 * 			操作部门编码
	 * 		entity.destroyed
	 * 			是否作废
	 * @param signDto 
	 * 			signDto.arriveSheetGoodsQty 
	 * 				到达联件数
	 * 			signDto.identifyType 
	 * 				证件类型
	 *  		signDto.identifyCode 
	 * 				证件号码
	 * 	 		signDto.deliverymanName 
	 * 				提货人名称
	 * 	 		signDto.productCode 
	 * 				运输性质
	 *    		signDto.isWholeVehicle 
	 * 				是否整车运单
	 * 	  		signDto.lastLoadOrgCode 
	 * 				最终配载部门
	 * 			signDto.waybillNo 
	 * 				运单号
	 * 			signDto.receiveCustomerName
	 * 				收货人(收货客户名称)
	 * 			signDto.receiveCustomerPhone
	 * 				货客户电话
	 * 			signDto.receiveCustomerMobilephone
	 * 				收货人手机
	 * 			signDto.active
	 * 				运单状态
	 * 			signDto.settleTimeStart
	 * 				结清时间起
	 * 			signDto.settleTimeEnd
	 * 				 结清时间止
	 * 			signDto.settleStatus
	 * 				结清状态
	 * 			signDto.isWholeVehicle
	 * 				是否整车运单
	 * 			signDto.endStockOrgCode
	 * 				最后库存code
	 * 			signDto.goodsAreaCode
	 * 				库区
	 * 			signDto.orderNo
	 * 				订单号
	 * @param currentInfo
	 * 			currentInfo.operate 
	 * 				操作人
	 *          currentInfo.operatorCode 
	 *          	操作人编码
	 *          currentInfo.operateOrgName 
	 *          	操作部门名称
	 *          currentInfo.operateOrgCode 
	 *          	操作部门编码
	 * @see
	 * 
	 */
	private String addWaybillSignResult(ArriveSheetEntity entity,SignDto signDto,CurrentInfo currentInfo,WaybillEntity waybill){
		//证件号码
		entity.setIdentifyCode(signDto.getIdentifyCode());
		//证件类型
		entity.setIdentifyType(signDto.getIdentifyType());
		//提货人
		entity.setDeliverymanName(signDto.getDeliverymanName());
		try {
			WaybillSignResultEntity wayEntity = waybillSignResultService.checkWaybillSignResultSign(entity,waybill,signDto.getSystemDate(),currentInfo);
			// 是PDA签收
			wayEntity.setIsPdaSign(FossConstants.YES);
			// 添加运单签收结果信息
			waybillSignResultService.addWaybillSignResult(wayEntity);
			
			//DN201511090005 添加“签收人类型”字段  243921
			wayEntity.setDeliverymanType(entity.getDeliverymanType());
			
			//菜鸟轨迹 add by 231438
			//封装的轨迹方法 赋值，判断是否符合条件 调用中转的轨迹接口
			sendWaybillTrackService.rookieTrackingsForSign(wayEntity);
						
			//pda签收，推送轨迹 --add by 231438，快递100，轨迹推送需求RFOSS2015031706 
			sendWaybillTrackService.packagingMethodForSign(wayEntity, currentInfo);
			

			if(SignConstants.SIGN_STATUS_ALL.equals(wayEntity.getSignStatus())){//如果签收状态为全部签收
				waybillSignResultService.updateCrmOrder(signDto.getOrderNo(), currentInfo, wayEntity);
			}
			LOGGER.info("--添加运单签收结果完成----");//记录日志
			return StringUtils.isNotEmpty(wayEntity.getSignStatus())?wayEntity.getSignStatus():null;
		} catch (SignException e) {//异常信息
			throw new PdaProcessException(e.getErrorCode(), e);//抛出异常 
		}
	}
	/**
	 * 
	 *  调用结算子系统“签收接口”
	 * @author foss-meiying
	 * @date 2012-12-18 下午5:20:47
	 * @param dto
	 * 		  dto.waybillNo 
	 * 				运单号
	 *  		dto.arrivesheetNo 
	 * 				到达联编号
	 *  		dto.signGoodsQty 
	 * 				 签收件数
	 *  		dto.signDeptCode 
	 * 				签收部门编码
	 *  		dto.signTime 
	 * 				签收时间
	 *  		dto.situation 
	 * 				签收情况
	 * 				签收情况
	 * 				（正常签收、
	 * 				异常-破损、
	 * 				异常-潮湿、
	 * 				异常-污染、
	 * 				异常-其他、
	 * 				部分签收）
	 *  		dto.operatorCode 
	 * 				操作人工号
	 *  		dto.serialNos 
	 * 				签收流水号列表
	 *  		dto.signNote 
	 * 				 签收备注
	 * @param currentInfo
	 * 		 currentInfo.empName
	 * 				 操作人名称
	 * 		  currentInfo.empCode
	 * 				 操作人编码
	 *        currentInfo.currentDeptName 
	 *        		签收部门名称
	 *        currentInfo.currentDeptCode 
	 *        		签收部门编码
	 * @param signDto
	 * 			signDto.arriveSheetGoodsQty 
	 * 				到达联件数
	 * 			signDto.identifyType 
	 * 				证件类型
	 *  		signDto.identifyCode 
	 * 				证件号码
	 * 	 		signDto.deliverymanName 
	 * 				提货人名称
	 * 	 		signDto.productCode 
	 * 				运输性质
	 *    		signDto.isWholeVehicle 
	 * 				是否整车运单
	 * 	  		signDto.lastLoadOrgCode 
	 * 				最终配载部门
	 * 			signDto.waybillNo 
	 * 				运单号
	 * 			signDto.receiveCustomerName
	 * 				收货人(收货客户名称)
	 * 			signDto.receiveCustomerPhone
	 * 				货客户电话
	 * 			signDto.receiveCustomerMobilephone
	 * 				收货人手机
	 * 			signDto.active
	 * 				运单状态
	 * 			signDto.settleTimeStart
	 * 				结清时间起
	 * 			signDto.settleTimeEnd
	 * 				 结清时间止
	 * 			signDto.settleStatus
	 * 				结清状态
	 * 			signDto.isWholeVehicle
	 * 				是否整车运单
	 * 			signDto.endStockOrgCode
	 * 				最后库存code
	 * 			signDto.goodsAreaCode
	 * 				库区
	 * 			signDto.orderNo
	 * 				订单号
	 * 			
	 * @see
	 * 
	 */
	private void confirmTaking(PdaSignDto dto,CurrentInfo currentInfo,SignDto signDto){
		try {
			LineSignDto lineSigndto = new LineSignDto();
			// 系统调用财务接口完成财务出库 首先判断是否第一次签收。
			//如果是就调用，如果不是，就不调用。 根据运单号判断到达联表里是否存在签收。
			 ArriveSheetEntity arriveSheetEntity=new ArriveSheetEntity(dto.getWaybillNo(),ArriveSheetConstants.STATUS_SIGN,FossConstants.ACTIVE,FossConstants.NO);
			 Long signCountLong = arriveSheetManngerService.queryArriveExistSign(arriveSheetEntity);
			//系统调用财务接口完成财务出库
			 if(signCountLong == null || PdaConstants.ZERO >= signCountLong ){ 
				//签收日期
				 lineSigndto.setSignDate(signDto.getSystemDate());
				//运单号
				 lineSigndto.setWaybillNo(dto.getWaybillNo());
				//操作人名称
				 lineSigndto.setOperatorName(currentInfo.getEmpName());
				//操作人编码
				 lineSigndto.setOperatorCode(currentInfo.getEmpCode());
				//签收部门名称
				 lineSigndto.setSignOrgName(currentInfo.getCurrentDeptName());
				//签收部门编码 
				 lineSigndto.setSignOrgCode(currentInfo.getCurrentDeptCode());
				 lineSigndto.setSignType(SettlementConstants.LINE_SIGN);
				//是PDA签收
				 lineSigndto.setIsPdaSign(FossConstants.YES);
				//运输性质
				 lineSigndto.setProductType(signDto.getProductCode());
				//是否整车运单
				 lineSigndto.setIsWholeVehicle(signDto.getIsWholeVehicle());
				//签收情况 by 243921
				 lineSigndto.setSignSituation(dto.getSituation());
				//CUBC签收   灰度改造    353654 ---------------------------start
				 String vestSystemCode = null;
				 try {
	              	List<String> arrayList = new ArrayList<String>();
	              	arrayList.add(lineSigndto.getWaybillNo());
	              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
	              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".confirmTaking",
	              			SettlementConstants.TYPE_FOSS);
	              	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
	              	List<VestBatchResult> list = response.getVestBatchResult();
	              	vestSystemCode = list.get(0).getVestSystemCode();	
		  			} catch (Exception e) {
		  				LOGGER.info("灰度分流失败,"+"运单号："+lineSigndto.getWaybillNo());
						throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
		  			}
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
						lineSignService.confirmTaking(lineSigndto, currentInfo);
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
//						//查询运单总件数
//						WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
//						//查询签收结果表
//				        WaybillSignResultEntity wayEntity = new WaybillSignResultEntity(dto.getWaybillNo(),FossConstants.ACTIVE);
//				        WaybillSignResultEntity waybillSignResultEntity = waybillSignResultDao.queryWaybillSignResult(wayEntity);
//				        //获取已签收件数+此次签收件数与运单总件数比较判断全部和部分签收
//				        int signCount = (waybillSignResultEntity == null ? 0 : waybillSignResultEntity.getSignGoodsQty()) + dto.getSignGoodsQty();
//				        LOGGER.info("运单号:"+dto.getWaybillNo()+",PDA传递签收件数为:"+ dto.getSignGoodsQty());
//						String signStatus = signCount >= waybill.getGoodsQtyTotal() ? SignConstants.SIGN_STATUS_ALL
//								: SignConstants.SIGN_STATUS_PARTIAL;
//						if(SignConstants.SIGN_STATUS_ALL.equals(signStatus)){
							FOSSSignOrRevSignRequestDto reqDto = new FOSSSignOrRevSignRequestDto();
							reqDto.setCurrentInfo(currentInfo);
							reqDto.setLineSignDto(lineSigndto);
							CUBCSignOrRevSignResultDto resultDto1 = cUBCSignService.sendSignReqToCUBC(reqDto);
							if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
								if(StringUtils.isNotBlank(resultDto1.getMeg())){
									LOGGER.error("调用CUBC签收接口异常信息如下："+resultDto1.getMeg());
									throw new CUBCSignException(resultDto1.getMeg());
								}else{
									throw new CUBCSignException("调用CUBC签收接口失败但未获取到异常信息");
								}
							}			
//						}else{
//							LOGGER.info("PdaSignService.confirmTaking,运单号："+dto.getWaybillNo()+",部分签收,不调CUBC签收接口");
//						}
					}
					//CUBC签收   灰度改造    353654 ---------------------------end
			 }else{
				//这里不作处理  
			 }
		} catch (SettlementException se) {//捕获异常
			LOGGER.error("调用财务接口抛出异常",se);//记录日志
			throw new PdaProcessException(se.getErrorCode(),se);//抛出异常
		}
		LOGGER.info("--调用结算子系统“签收接口”完成----");//记录日志
	}
	/**
	 * 
	 * 更新到达联信息
	 * @author foss-meiying
	 * @date 2012-12-18 上午11:18:05
	 * @param dto
	 * 			 dto.waybillNo 
	 * 				运单号
	 *  		dto.arrivesheetNo 
	 * 				到达联编号
	 *  		dto.signGoodsQty 
	 * 				 签收件数
	 *  		dto.signDeptCode 
	 * 				签收部门编码
	 *  		dto.signTime 
	 * 				签收时间
	 *  		dto.situation 
	 * 				签收情况
	 * 				签收情况
	 * 				（正常签收、
	 * 				异常-破损、
	 * 				异常-潮湿、
	 * 				异常-污染、
	 * 				异常-其他、
	 * 				部分签收）
	 *  		dto.operatorCode 
	 * 				操作人工号
	 *  		dto.serialNos 
	 * 				签收流水号列表
	 *  		dto.signNote 
	 * 				 签收备注
	 * @param currentInfo
	 * 			 currentInfo.operate 
	 * 				操作人
	 * 			 currentInfo.operatorCode 
	 * 				操作人编码
	 * 			 currentInfo.operateOrgName
	 * 				 操作部门名称
	 * 			 currentInfo.operateOrgCode 
	 * 				操作部门编码
	 * @see
	 * 
	 */
	private ArriveSheetEntity updateArriveSheet(PdaSignDto dto,CurrentInfo currentInfo,SignDto signDto){
		ArriveSheetEntity entity = new ArriveSheetEntity();
		//到达联编号
		entity.setArrivesheetNo(dto.getArrivesheetNo());
		//签收时间
		entity.setSignTime(dto.getSignTime());
		// PDA签收
		entity.setIsPdaSign(FossConstants.YES);
		//到达联状态为签收
		entity.setStatus(ArriveSheetConstants.STATUS_SIGN);
		//签收件数
		entity.setSignGoodsQty(dto.getSignGoodsQty());
		//备注
		entity.setSignNote(dto.getSignNote());
		//签收情况
		entity.setSituation(dto.getSituation());
		//签收操作时间
		entity.setOperateTime(signDto.getSystemDate());
		// 操作人
		entity.setOperator(currentInfo.getEmpName());
		// 操作人编码
		entity.setOperatorCode(currentInfo.getEmpCode());
		// 操作部门名称
		entity.setOperateOrgName(currentInfo.getCurrentDeptName());
		// 操作部门编码
		entity.setOperateOrgCode(currentInfo.getCurrentDeptCode());
		entity.setReceiveMethod(ArriveSheetConstants.SIGN_RECEIVE_METHOD_PICKUP);//提货方式--自提
		//签收前的到达联状态为生成 --并发控制
		entity.setOldStatus(ArriveSheetConstants.STATUS_GENERATE);
		// 签收人类型
		entity.setDeliverymanType(dto.getDeliverymanType());
		if(signDto.getArriveSheetGoodsQty()!= null && signDto.getArriveSheetGoodsQty()>dto.getSignGoodsQty()){
			entity.setSignStatus(SignConstants.SIGN_STATUS_PARTIAL);//到达联签收状态为部分签收
		}else {
			entity.setSignStatus(SignConstants.SIGN_STATUS_ALL);//到达联签收状态为全部签收
		}
		if(arriveSheetManngerService.updateArriveSheetByArrivesheetNo(entity)<=0){//如果修改到达联失败
			LOGGER.error("签收失败，请重新查询一下!"+ ReflectionToStringBuilder.toString(entity));//记录日志
			throw new PdaProcessException("PDA签收失败，请重新查询一下!");//抛出异常
		}
		//运单号
		entity.setWaybillNo(dto.getWaybillNo());
		LOGGER.info("--修改到达联完成----");//记录日志
		return entity;//返回到达联信息
	}
	/**
	 * Sets 
	 * 		the 
	 * 		运单签收结果service.
	 *
	 * @param waybillSignResultService 
	 * 		the new 运单签收结果service
	 */
	public void setWaybillSignResultService(IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}
	/**
	 * 
	 * Sets 
	 * 		the 到达联service.
	 *
	 * @param arriveSheetManngerService 
	 * 		the new 到达联service
	 * 
	 */
	public void setArriveSheetManngerService(IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}
	/**
	 * 
	 * Sets 
	 * 		the 运单service.
	 *
	 *
	 * @param waybillManagerService 
	 * 		the new 运单service
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	/**
	 * 
	 * Sets 
	 * 		the 结清货款Service.
	 *
	 *
	 * @param repaymentService 
	 * 		the new 结清货款Service
	 */
	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}
	/**
	 * 
	 * Sets 
	 * 		the 结算签收Service.
	 *
	 *
	 * @param lineSignService 
	 * 		the new 结算签收Service
	 */
	public void setLineSignService(ILineSignService lineSignService) {
		this.lineSignService = lineSignService;
	}
	/**
	 * 
	 * Sets the 
	 * 		员工service.
	 *
	 *
	 * @param 
	 * 		employeeService 
	 * 		the new 员工service
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	/**
	 * 
	 * Sets
	 * 		 the
			组织信息service.
	 *
	 *
	 * @param 
	 * 		orgAdministrativeInfoService 
	 * 		the new 组织信息service
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * 
	 * Sets 
	 * 		the 
	 * 		签收明细service.
	 *
	 *
	 * @param 
	 * 		signDetailService 
	 * 		the new 签收明细service
	 */
	public void setSignDetailService(ISignDetailService signDetailService) {
		this.signDetailService = signDetailService;
	}
	/**
	 * 
	 * Sets 
	 * 		the
	 * 		 actualFreightService.
	 *
	 *
	 * @param 
	 * 		actualFreightService
	 * 		 the new actualFreightService
	 */
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}
	/**
	 * 
	 * Sets 
	 * 		the
	 * 		 中转出库接口.
	 *
	 * @param 
	 * 		stockService 
	 * 		the new 中转出库接口
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	public void setSignStockJobService(ISignStockJobService signStockJobService) {
		this.signStockJobService = signStockJobService;
	}
	public void setTodoActionService(ITodoActionService todoActionService) {
		this.todoActionService = todoActionService;
	}
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	public void setPickupService(IPickupService pickupService) {
		this.pickupService = pickupService;
	}
	
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}


	/**
	 * Sets the 外场相关共通接口.
	 *
	 * @param handleQueryOutfieldService the new 外场相关共通接口
	 */
	public void setHandleQueryOutfieldService(IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}

	/**
	 * 
	 * 快递PDA签收
	 * 
	 * @author 025000-foss-helong
	 * @date 2013-7-26
	 */
	@Override
	public String pdaExpressSign(PdaSignDto dto) {
		String resultMsg = "";//返回信息
		LOGGER.info(" --开始签收出库操作,pda传入参数---- " + ReflectionToStringBuilder.toString(dto));//记录日志
		this.validateParams(dto);//验证传入的参数
		WaybillEntity waybill = new WaybillEntity();
		//查询运单号是否存在
		if(!waybillManagerService.isWayBillExsits(dto.getWaybillNo())){
			StringBuffer errorMsg = new StringBuffer();//保存错误信息
			errorMsg.append("运单号");//运单号
			errorMsg.append(dto.getWaybillNo());//对应的运单号
			errorMsg.append("在系统中不存在，不能签收出库");//错误信息
			LOGGER.error(errorMsg.toString());//记录日志
			throw new PdaProcessException("该运单号在系统中不存在，不能签收出库");
		} else {
			//张新 限制签收
			List<PrintAgentWaybillRecordEntity> sdExternalBillRecords = printAgentWaybillService.queryRecordByWaybillNo(dto.getWaybillNo(),SignConstants.LIMIT_SIGN);
			if(!CollectionUtils.isEmpty(sdExternalBillRecords)) 
			{
				//有效的营业部外发
				LOGGER.error("--该单已外发，如需人工签收，请于“营业部外发单号绑定”中解除绑定");//记录日志
				throw new PdaProcessException("该单已外发，如需人工签收，请于“营业部外发单号绑定”中解除绑定");//签收限制
			}
			RepaymentEntity repayment = new RepaymentEntity();
			repayment.setWaybillNo(dto.getWaybillNo());
			//PDA结清     243921
			repayment.setSettleApproach(SettlementDictionaryConstants.SETTLE_APPROACH_MOBILE);
			//调用结清货款接口判断当前运单是否已结清
			boolean result =repaymentService.paymentOperate(repayment);
			//运单未做货款结清，不能签收出库
			if(!result){
				LOGGER.error("--运单未做货款结清，不能签收出库");//记录日志
				throw new PdaProcessException("运单未做货款结清，不能签收出库");//该运单未做货款结清，不能签收出库
			}
			//根据到达联编号,到达联状态为‘生成’查运单运输性质  是否整车运单,到达联id,证件号码,证件类型,提货人,最终配载部门
			SignDto signDto = arriveSheetManngerService.queryPartWaybillpartArrivesheet(dto.getArrivesheetNo(),ArriveSheetConstants.STATUS_GENERATE);
			if(signDto == null){
				throw new PdaProcessException("找不到有效状态的到达联,不能签收出库");//找不到有效状态的到达联,不能签收出库
			}
			if(!dto.getWaybillNo().equals(signDto.getWaybillNo())){
				throw new PdaProcessException("传入的运单号跟到达联对应的运单号不一致，不能签收");//传入的运单号跟到达联对应的运单号不一致，不能签收
			}
			SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(dto.getSignDeptCode());
			if (saleDepartment == null ) {
				// 判断 最终配载部门 是否驻地部门
				SaleDepartmentEntity saleDepar = saleDepartmentService.querySaleDepartmentByCode(signDto.getLastLoadOrgCode());
				// 是否驻地部门
				if (saleDepar != null && saleDepar.checkStation()) {
					String endStockOrgCode = saleDepar.getTransferCenter();//得到驻地部门外场
					//非营业部找到它上级所属外场的编码
					List<String> bizTypes = new ArrayList<String>();
					bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(dto.getSignDeptCode(), bizTypes);
					if(orgAdministrativeInfoEntity != null){
						if(!StringUtils.equals(endStockOrgCode, orgAdministrativeInfoEntity.getCode())){
							throw new PdaProcessException("签收部门与运单的最终库存部门不一致,不能进行签收操作");
						}
					}else{
						throw new PdaProcessException("签收部门与运单的最终库存部门不一致,不能进行签收操作");
					}
				} else {
					throw new PdaProcessException("签收部门与运单的最终配载部门不一致,不能进行签收操作");
				}
			} else {//如果不为空，为营业部
				if(!dto.getSignDeptCode().equals(signDto.getLastLoadOrgCode())){//如果签收部门与运单的最终配载部门不一致
					throw new PdaProcessException("签收部门与运单的最终配载部门不一致,不能进行签收操作");
				}
			}
			CurrentInfo currentInfo = this.getCurrentInfo(dto.getOperatorCode(),dto.getSignDeptCode());
			CurrentInfo currentinfoByLastLoadOrgCode = this.getCurrentInfo(dto.getOperatorCode(),signDto.getLastLoadOrgCode());
			signDto.setSystemDate(new Date());
			BeanUtils.copyProperties(signDto,waybill);//把signDto里的内容复制到waybill里
			ActualFreightEntity actual = actualFreightService.queryByWaybillNo(dto.getWaybillNo());
			boolean isSendInvoiceInfo=waybillSignResultService.isNeedSendInvoiceInfo(waybill,actual);//是否将发票信息传给发票系统
			if(WaybillConstants.INNER_PICKUP.equals(signDto.getReceiveMethod())){//提货方式为汽运内部自提
				LOGGER.info("--提货方式为汽运内部自提 不需要调用结算签收接口----");//记录日志
			}else {
				this.confirmTaking(dto,currentInfo,signDto);
			}
			if(dto.getSerialNos().size()>SignConstants.DEFAULT_SERIALNOS_LIMIT_COUNT){
				// 系统调用中转接口（SUC-238）出库货物
				for (int i = PdaConstants.ZERO; i < dto.getSerialNos().size(); i++) {
					SignDetailEntity signDetail = new SignDetailEntity();
					signDetail.setSerialNo(dto.getSerialNos().get(i));//流水号
					// 到达联编号
					signDetail.setArrivesheetNo(dto.getArrivesheetNo());
					if(ArriveSheetConstants.SITUATION_NORMAL_SIGN.equals(dto.getSituation())){
						// 签收情况
						signDetail.setSituation(dto.getSituation());
					}
					signDetailService.addSignDetailInfo(signDetail);
					SignStockEntity signStock = new SignStockEntity();
					// 运单号
					signStock.setWaybillNo(dto.getWaybillNo());
					// 流水号
					signStock.setSerialNo(dto.getSerialNos().get(i));
					// 部门编码
					signStock.setStockOrgCode(currentinfoByLastLoadOrgCode.getCurrentDeptCode());
					signStock.setStockOrgName(currentinfoByLastLoadOrgCode.getCurrentDeptName());//部门名称 
					// 操作人姓名
					signStock.setOperator(currentinfoByLastLoadOrgCode.getEmpName());
					// 操作人工号
					signStock.setOperatorCode(currentinfoByLastLoadOrgCode.getEmpCode());
					// 出入库类型 签收出库
					signStock.setInoutType(StockConstants.SIGN_OUT_STOCK_TYPE);
					// 添加到sign_stock操作
					try {
						signStockJobService.addSelective(signStock);
					} catch (BusinessException e) {//捕获异常
						LOGGER.error(e.getMessage(), e);//记录日志
						throw new PdaProcessException(e.getErrorCode(),e);//抛出异常
					}
				}
				LOGGER.info("--保存签收明细完成 添加sign_stock完成");//记录日志
			}else {
				for (int i = PdaConstants.ZERO; i < dto.getSerialNos().size(); i++) {
					SignDetailEntity signDetail = new SignDetailEntity();
					signDetail.setSerialNo(dto.getSerialNos().get(i));//流水号
					// 到达联编号
					signDetail.setArrivesheetNo(dto.getArrivesheetNo());
					if(ArriveSheetConstants.SITUATION_NORMAL_SIGN.equals(dto.getSituation())){
						// 签收情况
						signDetail.setSituation(dto.getSituation());
					}
					signDetailService.addSignDetailInfo(signDetail);
					// 系统调用中转接口（SUC-238）出库货物
					InOutStockEntity inOutStock = new InOutStockEntity();
					// 运单号
					inOutStock.setWaybillNO(dto.getWaybillNo());
					// 流水号
					inOutStock.setSerialNO(dto.getSerialNos().get(i));
					// 部门编码
					inOutStock.setOrgCode(currentinfoByLastLoadOrgCode.getCurrentDeptCode());
					// 操作人姓名
					inOutStock.setOperatorName(currentinfoByLastLoadOrgCode.getEmpName());
					// 操作人工号
					inOutStock.setOperatorCode(currentinfoByLastLoadOrgCode.getEmpCode());
					/**
					 * 出入库类型 签收出库
					 */
					inOutStock.setInOutStockType(StockConstants.SIGN_OUT_STOCK_TYPE);
					// 进行出库操作
					try {
						stockService.outStockPC(inOutStock);
					} catch (BusinessException e) {//捕获异常
						LOGGER.error(e.getMessage(), e);//记录日志
						throw new PdaProcessException(e.getErrorCode(),e);//抛出异常
					}
				}
				LOGGER.info("--保存签收明细完成，调用中转出库完成");//记录日志
			}
			try {
				//如果签收件数小于运单开单件数
				if(dto.getSignGoodsQty() != null &&dto.getSignGoodsQty()<waybill.getGoodsQtyTotal()){
					todoActionService.updateLabeledStatusByWaybillNum(dto.getWaybillNo(),dto.getSerialNos());
				}else {
					todoActionService.updateLabeledStatusByWaybillNum(dto.getWaybillNo(),null);
				}
				ActualFreightDto actualFreightDto = new ActualFreightDto(dto.getWaybillNo(),dto.getSignGoodsQty());
				//PDA签收 修改运单状态里的到达未出库件数
				actualFreightService.updateArriveNotoutGoodsQtyByWaybillNo(actualFreightDto);
				//更新到达联信息
				ArriveSheetEntity entity = this.updateArriveSheet(dto,currentInfo,signDto);
				//添加运单签收结果
				this.addWaybillSignResult(entity,signDto,currentInfo,waybill);
				WaybillSignResultDto waybillSigndto = new WaybillSignResultDto();
				BeanUtils.copyProperties(entity,waybillSigndto);
				//调用短信接口发送短信  调用在线通知接口发送在线通知至发货部门
				resultMsg =waybillSignResultService.sendMessNotice(waybill, currentInfo,waybillSigndto);
				//调取FOSS中转补码查询的接口--中转-自动补码-补码后将待人工补码记录删除
				autoAddCodeByHandService.deleteAddCodeByHand(dto.getWaybillNo());
				//应用监控签收调用
				waybillSignResultService.signCounterByWaybillNo(currentInfo, waybill);
				LOGGER.info("-------------------签收绑定单号-------------------");//记录日志
				signBindWaybillNo(dto,currentInfo);
				LOGGER.info("-------------------签收绑定单号-------------------");//记录日志
				if(isSendInvoiceInfo){
					//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
					waybillSignResultService.sendInvoiceInfo(waybill,actual);
				}
			}catch(BusinessException e){//捕获异常
				LOGGER.error(e.getMessage(), e);//记录日志
				throw new PdaProcessException(e.getErrorCode(),e);//抛出异常
			}
			
		}
		LOGGER.info(" pda签收出库操作完成---- "+ resultMsg);//记录日志
		return StringUtils.isNotBlank(resultMsg) ? resultMsg : "pda签收出库操作完成";
	}
	
	/**
	 * 菜鸟----------签收绑定的原单号
	 * @date 2015-2-5 上午10:58:10
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.server.service.impl.ExpSignService#signBindWaybillNo(com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity)
	 */
	private void signBindWaybillNo(PdaSignDto entity,CurrentInfo currentInfo) {
		LOGGER.info("-------------------签收绑定单号 begin-------------------");//记录日志
		WaybillExpressEntity newWaybillNoEntity = waybillExpressService.queryWaybillByWaybillNo(entity.getWaybillNo(), WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
		//判断新单号
		if(newWaybillNoEntity!=null){
			String oldWaybillNo = newWaybillNoEntity.getOriginalWaybillNo();
			if(StringUtils.isNotEmpty(oldWaybillNo)){
				WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(oldWaybillNo);
				if(waybill!= null){
					WaybillSignResultEntity wEntity = new WaybillSignResultEntity();
					wEntity.setWaybillNo(oldWaybillNo);
					wEntity.setActive(FossConstants.YES);
					WaybillSignResultEntity oldresultWEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(wEntity);
					if(oldresultWEntity!=null&&SignConstants.SIGN_STATUS_ALL.equals(oldresultWEntity.getSignStatus())){
					}else{
						WaybillSignResultEntity resultWEntity=new WaybillSignResultEntity();
						//DN201511090005  添加“签收人类型”字段
						resultWEntity.setDeliverymanType(entity.getDeliverymanType());
						resultWEntity.setSignSituation(entity.getSituation());
						resultWEntity.setSignTime(entity.getSignTime());
						resultWEntity.setSignNote(entity.getSignNote());
						resultWEntity.setWaybillNo(oldWaybillNo);
						resultWEntity.setIsPdaSign(FossConstants.YES);
						resultWEntity.setSignGoodsQty(waybill.getGoodsQtyTotal());
						resultWEntity.setReceiveMethod(waybill.getReceiveMethod());
						rookieService.exceuteSignStatusPart(oldresultWEntity, waybill, resultWEntity,null, currentInfo, null, false);
					}
				}
			}
		}else{
			//运单号
			if(StringUtils.isNotEmpty(entity.getWaybillNo())){
				WaybillExpressEntity waybillExpressEntity = waybillExpressService.queryWaybillByOriginalWaybillNo(entity.getWaybillNo(), WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
				if(waybillExpressEntity!=null){
					throw new SignException("该单号已经返货新开单,请使用新单号进行操作!");//该运单号不存在
				}
			}
		}
		LOGGER.info("-------------------签收绑定单号 end-------------------");//记录日志
	}
	public void setSignChangeService(ISignChangeService signChangeService) {
		this.signChangeService = signChangeService;
	}
	public void setWaybillExpressService(IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}
	public void setRookieService(IRookieService rookieService) {
		this.rookieService = rookieService;
	}
	public void setPrintAgentWaybillService(
			IPrintAgentWaybillService printAgentWaybillService) {
		this.printAgentWaybillService = printAgentWaybillService;
	}
	
}