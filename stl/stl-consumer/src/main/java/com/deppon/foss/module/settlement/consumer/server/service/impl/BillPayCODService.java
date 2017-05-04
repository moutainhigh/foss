/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-consumer
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/BillPayCODService.java
 * 
 * FILE NAME        	: BillPayCODService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 
 * @修订记录
 *  日期                            修订内容                                                                            修订人员                          版本号                           
 * 2012-05-3            创建版本                                                                            黄小波                           V0.1                          
 * 2012-06-23          修改版本                                                                            毛建强                           V0.5                          
 * 2012-07-25          版本升级                                                                            毛建强                           V0.9                          
 * 2012-10-25          修改附件“代收货款综合查询”，增加界面“批次查询”、
 * 							“代收货款日志”                                             		   保轶                              V1.2                          
 * 2012-12-3            增加业务完结描述                                                              黄小波                           V1.3                          
 * 
 * @参考
 * 	SUC-76 生成应付单（代收货款类型）
 * 
 * @相关业务用例
 * 	BUC_FOSS_4.7.30.10_010  自动生成应付-代收货款
 * 
 * @用例描述
 * 	1、运单开单如果含有代收货款或运单变更涉及代收货款变更时，
 * 		则会调用该接口，生成应付给发货客户的代收货款应付单。
 * 	2、代收货款单据查询统一在代收货款综合查询报表用例承接。
 * 	3、来源单据为运单，增加代收货款应付单或者代收货款应付单的核销金额由0更改为非0，
 * 		增加业务计数；
 * 	4、来源单据为运单，红冲代收货款应付单或者代收货款应付单的核销金额由非0更改为0，
 * 		减少业务计数；
 * 
 * @用例条件
 * 	前置条件	1、	运单的版本有为有效，并且金额在代收货款金额范围内
 * 
 * 	后置条件	1、	生成代收货款应付单
 *						2、	生成代收货款信息单据
 *
 * @操作步骤
 * 
 * 序号						基本步骤								相关数据									补充步骤
 *	1							校验运单号								运单											1、业务规则参见SR1
 *																															2、扩展事件参见1a
 *	2							校验代收货款金额、
 *								收款人信息、
 *								银行帐号信息、
 *								代收货款账号类别					运单											1、业务规则参见SR2
 *																															2、扩展事件参见2a
 *	3							如果运单的产品类型为“精准空运”，
 *								派送方式为“机场自提”，
 *								则代收货款金额不能大于0			运单											1、扩展事件参见3a
 *	4							如果运单的配载类型
 *								为“专线”且提货网点和
 *								最终配载部门不一致，
 *								则返回错误信息						运单											1、扩展事件参见4a
 *	5							校验是否重复调用
 *								生成应付单接口						运单											1、根据传入的运单号查看对应的代收货款应付单是否存在，若存在则抛出异常
 *																															2、扩展事件参见5a
 *																															3、业务规则参见SR3
 *	6							生成代收货款应付单					输入：运单
 *																			输出：应付单
 *																			业务规则参考:SR4
 *	7							生成代收货款信息单据				输入：运单
 *																			输出：代收货款单据						业务规则参考:SR5
 *
 *		修订记录 
        日期 	修订内容 	修订人员 	版本号 
        2012-05-3 	创建版本	黄小波	V0.1
        2012-06-23	修改版本	毛建强	V0.5
        2012-07-25	版本升级	毛建强	V0.9
        2012-10-25
        修改附件“代收货款综合查询”，增加界面“批次查询”、“代收货款日志”	保轶
        V1.2
        
        2012-12-3	增加业务完结描述	黄小波	V1.3
        
        1.	SUC-76 生成应付单（代收货款类型）
        1.1	相关业务用例
        BUC_FOSS_4.7.30.10_010  自动生成应付-代收货款
        1.2	用例描述
        1、运单开单如果含有代收货款或运单变更涉及代收货款变更时，则会调用该接口，生成应付给发货客户的代收货款应付单。
        2、代收货款单据查询统一在代收货款综合查询报表用例承接。
        3、来源单据为运单，增加代收货款应付单或者代收货款应付单的核销金额由0更改为非0，增加业务计数；
        4、来源单据为运单，红冲代收货款应付单或者代收货款应付单的核销金额由非0更改为0，减少业务计数；
        
        1.3	用例条件
        条件类型	描述	引用系统用例
        前置条件	1、	运单的版本有为有效，并且金额在代收货款金额范围内	SUC-439 《DP-FOSS-接送货系统用例-客户上门-开单提货-提交运单》
        SUC-743起草变更单（内部）
        SUC-540 起草变更单（外部）
        后置条件	1、	生成代收货款应付单
        2、	生成代收货款信息单据	
        1.4	操作用户角色
        操作用户	描述
        	
        1.5	界面要求
        1.5.1	表现方式
        接口
        1.5.2	界面原型
        无
        1.5.3	界面描述
        无
        1.6	操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	校验运单号	运单	1、业务规则参见SR1
        2、扩展事件参见1a
        2	校验代收货款金额、收款人信息、银行帐号信息、代收货款账号类别	运单	1、业务规则参见SR2
        2、扩展事件参见2a
        3	如果运单的产品类型为“精准空运”，派送方式为“机场自提”，则代收货款金额不能大于0	运单	1、扩展事件参见3a
        4	如果运单的配载类型为“专线”且提货网点和最终配载部门不一致，则返回错误信息	运单	1、扩展事件参见4a
        5	校验是否重复调用生成应付单接口	运单	1、根据传入的运单号查看对应的代收货款应付单是否存在，若存在则抛出异常
        2、扩展事件参见5a
        3、业务规则参见SR3
        6	生成代收货款应付单	输入：运单
        输出：应付单	业务规则参考:SR4
        7	生成代收货款信息单据	输入：运单
        输出：代收货款单据	业务规则参考:SR5
        
        序号	扩展事件	相关数据	备注
        1a	如果运单号校验失败，则直接返回错误信息	信息内容	返回错误信息：“传入的运单号不存在对应的运单”
        2a	校验代收货款信息失败，则直接返回错误信息	信息内容	1、如果金额不处于代收货款金额范围内，则返回错误信息：“代收货款金额有误，代收货款金额必须在“代收货款的最小值”～“代收货款的最大值”之间（此处最小值和最大值来源于数据字典）
        ２、如果代收货款类型为“三日退或即日退”且其不存在银行账号信息，则返回错误信息：“三日退和即日退的银行账号信息不能为空！”
        3、如果代收货款类型为“即日退”，而其开户银行不在即日退允许银行范围之内，则返回错误信息：“该银行不在即日退所选取范围内” 
        4、如果代收货款类型为即日退且代收货款银行账号类别为“对公”，则直接返回错误信息：“即日退的银行账号类别不能为对公类别！”
        3a	如果运单的产品类型为“精准空运”且派送方式为“机场自提”，并且代收货款金额大于0，则直接返回错误信息	信息内容	返回错误信息：“派送方式为机场自提运单不能办理代收货款业务”
        4a	如果为偏线业务，且代收货款金额大于0，则直接返回错误信息	信息内容	返回错误信息：“汽运偏线业务不能办理代收货款业务”
        5a	如果该运单号已经存在代收货款应付单，则直接返回错误信息	信息内容	返回错误信息：“该运单号已经存在对应的代收货款应付单，不能重复生成!”
        
        
        1.7	业务规则
        序号	描述
        SR1	1、判断传入运单号是否存在真实
        SR2	1、	代收货款金额处于代收货款金额范围之内（代收货款金额范围来源于数据字典） 
        2、	如果代收货款类型为“即日退或三日退”，则收款人且开户行信息不能为空
        3、	如果代收货款类型为“即日退”，则其开户银行必须在即日退所属银行范围内（即日退所属银行范围已经提交给综合管理做基础资料了） 
        4、	如果代收货款类型为“即日退”，则代收货款的退款账号类型不能为“对公帐号”
        SR3	查询应付单条件：
        1、	应付单为有效版本且非红单
        2、	应付单单据类型为“应付代收货款”
        3、	应付单的应付部门编码等于传入的运单号出发部门编码
        4、	应付单的来源单据编号等于传入的运单号
        SR4	应付单生成规则
        应付单：运单
        应付单单号:自动生成 YF1+8位流水号（不足补0）
        应付部门：运单的始发部门
        录入部门：运单的始发部门
        应付客户：运单的发货客户编码
        应付金额：运单的代收货款金额
        已付金额：0
        未付金额：运单的代收货款金额
        支付状态：不可支付
        冻结状态：未冻结
        业务日期：运单的创建日期
        记账日期：当前服务器日期
        生效时间：无
        来源单据：运单单号
        有效版本：有效
        红单标志：非红单
        应付类别：应付代收货款
        SR5	代收货款单据生成规则
        单据编号：自动生成，编码规则：YF1+8位流水号（不足补0）
        应付部门：运单的始发部门编码
        运单号：运单单号
        代收货款类型：运单代收货款类型
        代收货款状态：如果“代收货款类型”是“即日退”或“三日退”，状态设为“未退款”；如果是“审核退”，状态设为“待审核
        收款人与发货人关系：银行账号信息中的客户收款人关系
        收款人开户行：运单收款人开户行
        开户行的支行信息：运单收款人银行的支行信息
        对公对私标志：银行帐号的类别标志
        账号对应的开户名：运单收款人开户名
        收款人帐号：运单收款人帐号
        收款人手机号：运单收款人对应的手机号
        省份：运单选择银行所在的省份
        城市：运单选择银行所在的城市
        业务日期：运单开单日期
        批次号：空
        退款申请人：空
        退款申请时间：空
        退款成功时间：空
        
        1.8	数据元素
        1.8.1	接口参数（输入）
        字段名称 	说明 (来源)	输入限制	长度	是否必填	备注
        运单单号	运单单号	无	参照统一标准	是	无
        产品类型	运单的产品类型	无	参照统一标准	是	精准卡航、精准汽运、精准空运
        配载类型	运单的配载类型	无	参照统一标准	是	无
        派送方式	运单的派送方式	无	参照统一标准	是	无
        创建日期	运单的创建日期	无	参照统一标准	是	无
        代收货款类别	运单的代收货款类别	无	参照统一标准	是	无
        代收货款金额	运单的代收货款金额	无	参照统一标准	是	无
        始发部门名称	运单始发部门名称	无	参照统一标准	是	无
        始发部门编码	运单始发部门编码	无	参照统一标准	是	无
        提货网点	运单提货网点编码	无		是	无
        最终配载部门	运单最终配载部门编码	无		是	无
        发货人编码	运单的发货人编码	无	 50	是	无
        发货人名称	运单的发货人名称	无	50	否	无
        开户行名称	运单的开户行名称	无	   50	否	无
        开户名编码	运单的开户名编码	无	 50	否	无
        开户行帐号	运单的开户行帐号	无	50	否	无
        收款人名称	开户行账号对应的收款人	无	50	否	无
        收款人手机号	收款人的手机号码	无	11	否	无
        收款人与发货人关系	收款人与发货人之间的关系	无	255	否	无
        开户行省份	银行账号所属银行所在的省份	无	10	否	无
        开户行城市	银行账号所属银行所在的城市	无	10	否	无
        开户行地址	银行的详细地址	无	255	否	无
        
        1.8.2	代收货款应付单 （输出）
        字段名称 	说明 	输出限制	输入项提示文本	长度	是否必填	备注
        单据编号	应付单单号	系统获取	无	100	是	规则：21+运单单号
        支付部门名称	应付单的应付部门名称	系统获取	无	100	是	运单的出发部门名称
        支付部门编码	应付单的应付部门编码	系统获取	无	20	是	运单的出发部门编码
        客户名称	应付单的挂账客户名称	系统获取	无	100	是	运单的发货客户名称
        客户编码	应付单的挂账客户编码	系统获取	无	50	是	运单的发货客户编码
        来源单据	应付单生成来源的单据	系统获取	无	50	是	运单单号
        金额	应付单的应付金额	系统获取	无	10	是	运单的代收货款金额
        已核销金额	应付单已经付款的金额	系统获取	无	10	是	0
        未核销金额	应付单还未付款的金额	系统获取	无	10	是	运单的代收货款金额
        单据子类型	应付单单据的类型	系统获取	无	30	是	应付代收货款
        到达部门	应付单的到达部门	系统获取	无	10	是	运单的最终配载部门编码
        支付状态	应付单是否可以付款	系统获取	无	10	是	不可支付
        冻结状态	表明应付单是否在退款中	系统获取	无	10	是	未冻结
        单据状态	应付单的单据状态	系统获取	无	10	是	提交
        出发部门名称	出发部门名称	系统获取	无	100	是	始发配载部门名称
        出发部门编码	出发部门名称	系统获取	无	20	是	始发配载部门编码
        到达部门名称	出发部门名称	系统获取	无	100	是	到达部门名称
        到达部门编码	出发部门名称	系统获取	无	20	是	到达部门编码
        业务日期	应付单业务发生日期	系统获取	无	6	是	运单的创建日期
        会计日期	应付单最新变动日期	系统获取	无	6	是	服务器当前日期
        制单人	应付单的制单人	系统获取	无	20	是	运单创建人和工号
        制单日期	应付单生成时间	系统获取	无	6	是	服务器当前日期
        是否有效版本	标记该应付单是否有效	系统获取	无	10	是	是
        是否红单 	标记该条应付单是否是红冲单据	系统获取	无	10	是	否
        是否初始化	标记该条应付单是否是初始化状态	系统获取	无	10	是	否
        
        1.8.3	代收货款状态表（输出） 
        字段名称 	说明 (来源)	输出限制（类型，限制）	长度	是否必填	备注
        编码	代收货款编码	文本	50	是	规则：67+8位流水号
        应付部门	运单的始发部门编码	文本	参照统一标准	是	无
        运单号	运单单号	文本	10	是	无
        代收货款类别	运单代收货款业务类别	枚举	参考统一标准	是	无
        代收货款状态	代收货款单据状态	枚举	参考统一标准	是	无
        收款人与发货人关系	代收货款的收款人和发货人关系	文本	255	否	无
        开户行	收款人账号所属银行	文本	20	否	无
        开户行支行信息	收款人账号所属银行的支行信息	文本	255	否	无
        对公对私标志	代收货款账号类别：是对公还是对私	枚举	20	否	无
        收款人	收款账号的开户人	文本	50	否	无
        收款人帐号	收款账号	文本	50	否	无
        收款人手机	收款人的手机号	文本	11	否	无
        省份	省份	文本	10	否	无
        城市	城市	文本	10	否	无
        业务日期	业务日期	日期	参考统一标准	是	无
        批次号	批次申请退款的批次号	文本	30	否	无
        退款申请人	申请退款人	文本	50	否	无
        退款申请时间	调用费控接口申请退款时间	日期	参考统一标准	否	无
        退款成功时间	退款成功的时间	日期	参考统一标准	否	无
        
        1.9	非功能性需求
        使用量	目前4000笔/天，预计每年增加60%
        2012年全网估计用户数	8000人	
        响应要求（如果与全系统要求 不一致的话）	5s完成响应
        
        使用时间段	7×24小时
        
        高峰使用时间段	14：00-18：00
        
        1.10	接口描述：
        接口名称 	对方系统（外部系统或内部其他模块）	接口描述
 *
 *       
 *       
 *       
 *       
 *       
 *       
 *       
 *       
 *       
 *       
 *       
 *       
 *       
 *       
 *       
 *       
 *       
 *       
 *       
 *       
 *       
 *       
 *       
 *       
 *       
 *       
 *       
 *       

 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 代收货款服务
 * 
 * @author dp-zengbinwen
 * @date 2012-10-12 下午5:15:10
 */
public class BillPayCODService implements IBillPayCODService {

	private static final Logger LOGGER = LogManager
			.getLogger(BillPayCODService.class);

	/**
	 * 代收货款服务
	 */
	private ICodCommonService codCommonService;

	/**
	 * 应付单服务
	 */
	private IBillPayableService billPayableService;
	
	/**
	 * 新增代收货款
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-26 下午3:24:46
	 */
	@Override
	public void addBillCOD(WaybillPickupInfoDto waybill, CurrentInfo currentInfo)
			throws SettlementException {

		LOGGER.info("进入代收货款新增服务.");

		// 代收货款实体
		CODEntity entity = new CODEntity();
		Date now = new Date();

		// ID、运单号、运单ID、应付部门
		entity.setId(UUIDUtils.getUUID());
		entity.setWaybillNo(waybill.getWaybillNo());
		entity.setWaybillId(waybill.getId());
		entity.setPayableOrgCode(waybill.getReceiveOrgCode());
		entity.setPayableOrgName(waybill.getReceiveOrgName());

		// 代收货款类型、收货人账户、收款人、银行
		entity.setCodType(waybill.getRefundType());
		entity.setAccountNo(waybill.getAccountCode());
		entity.setPayeeName(waybill.getAccountName());
		entity.setBankHQName(waybill.getAccountBank());
		entity.setBankHQCode(waybill.getBankHQCode());
		
		
		// 客户编码、客户名称
		entity.setCustomerName(waybill.getDeliveryCustomerName());
		entity.setCustomerCode(waybill.getDeliveryCustomerCode());

		// 收款人电话、收款人与发货人关系、支行编码/名称、对公对私标志、行号、省份、城市
		entity.setPayeePhone(waybill.getPayeePhone());
		entity.setPayeeRelationship(waybill.getPayeeRelationship());
		entity.setBankBranchCode(waybill.getBankBranchCode());
		entity.setBankBranchName(waybill.getBankBranchName());
		entity.setPublicPrivateFlag(waybill.getPublicPrivateFlag());
		entity.setProvinceName(waybill.getProvinceName());
		entity.setProvinceCode(waybill.getProvinceCode());
		entity.setCityName(waybill.getCityName());
		entity.setCityCode(waybill.getCityCode());

		// 业务日期、创建时间、创建人、创建部门、修改时间
		entity.setBusinessDate(waybill.getBillTime());//运单开单时间
		entity.setCreateTime(now);
		entity.setCreateUserName(currentInfo.getEmpName());
		entity.setCreateUserCode(currentInfo.getEmpCode());
		entity.setCreateOrgName(currentInfo.getCurrentDeptName());
		entity.setCreateOrgCode(currentInfo.getCurrentDeptCode());
		entity.setModifyTime(now);

		// 是否初始化、是否有效、代收货款状态、空运代收货款状态
		entity.setIsInit(FossConstants.NO);
		entity.setActive(FossConstants.ACTIVE);
		entity.setStatus(SettlementDictionaryConstants.COD__STATUS__NOT_RETURN);
		//设置空运状态默认未审核
		entity.setAirStatus(SettlementDictionaryConstants.COD__AIR_STATUS__NOT_AUDIT);
		
		//设置快递代理状态默认未审核
		entity.setLandStatus(SettlementDictionaryConstants.COD__AIR_STATUS__NOT_AUDIT);

		// 创建时间、代收货款金额、版本
		entity.setCodAmount(waybill.getCodAmount());
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);

		// 设置代收货款状态
		if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE
				.equals(waybill.getRefundType())) {
			entity.setStatus(SettlementDictionaryConstants.COD__STATUS__APPROVING);
		} else {
			entity.setStatus(SettlementDictionaryConstants.COD__STATUS__NOT_RETURN);
		}

		// 新增代收货款实体
		LOGGER.debug("Start:新增代收货款实体");
		codCommonService.createCOD(entity, currentInfo);

		LOGGER.debug("End:新增代收货款实体");

		LOGGER.info("离开代收货款新增服务.");
	}

	/**
	 * 
	 * 作废代收货款
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-12 下午5:33:15
	 */
	public void cancelBillCOD(CODEntity codEntity, CurrentInfo currentInfo)
			throws SettlementException {

		LOGGER.info("进入作废代收货款服务.");

		// 找不到单据，则抛出异常
		if (codEntity == null) {
			throw new SettlementException("代收货款实体找不到");
		}

		// 如果代收货款单据为无效，则抛出异常
		if (FossConstants.INACTIVE.equalsIgnoreCase(codEntity.getActive())) {
			throw new SettlementException("代收货款实体无效");
		}

		codEntity.setActive(FossConstants.INACTIVE);
		codEntity.setModifyTime(new Date());
		codEntity.setModifyUserName(currentInfo.getEmpName());
		codEntity.setModifyUserCode(currentInfo.getEmpCode());

		codCommonService.updateCODCancelStatus(codEntity, currentInfo);

		// 注意：代收货款应收单应付单红冲在调用方实现，本函数不再做红冲

		LOGGER.info("离开作废代收货款服务.");
	}

	/**
	 * 
	 * 审核空运代收货款
	 * 
	 * @author dp-zengbinwen
	 * @date 2012-10-12 下午5:34:29
	 */
	public void auditAirBillCOD(List<String> entityIds, CurrentInfo currentInfo)
			throws SettlementException {

		LOGGER.info("Service start,entityIds:"
				+ (entityIds == null ? null : entityIds.toString()));

		// 如果代收货款实体ID为空，则抛出异常
		if (CollectionUtils.isEmpty(entityIds)) {
			throw new SettlementException("代收货款实体ID为空");
		}

		CODEntity entity = null;
		String status = null;
		BillPayableEntity billPayable = null;

		// 循环对代收货款数据处理
		for (String entityId : entityIds) {

			entity = codCommonService.queryById(entityId);

			// 如果代收货款实体为空，抛出异常
			if (entity == null) {
				throw new SettlementException("代收货款实体为空");
			}

			// 如果代收货款状态为资金部冻结、退款中、已退款，则抛出异常
			status = entity.getStatus();
			if (SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE
					.equals(status)
					|| SettlementDictionaryConstants.COD__STATUS__RETURNING
							.equals(status)
					|| SettlementDictionaryConstants.COD__STATUS__RETURNED
							.equals(status)) {
				throw new SettlementException("代收货款状态不能为资金部冻结、退款中、已退款");
			}

			// 获取代收货款应付单
			billPayable = codCommonService.getBillPayableEntity(entity);

			// 判断应付单是否可支付
			String payStatus = billPayable.getPayStatus();
			String effectiveStatus = billPayable.getEffectiveStatus();
			boolean isPayable = SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO
					.equals(payStatus);
			isPayable = isPayable
					&& SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES
							.equals(effectiveStatus);

			if (isPayable) {
				throw new SettlementException("代收货款应付单可支付，不能进行审核操作");
			}

			// 判断是否签收
			Date signDate = billPayable.getSignDate();
			if (signDate == null) {
				throw new SettlementException("运单未签收，不能进行审核操作");
			}

			// 调用应付单接口生效应付单
			billPayableService.enableBillPayable(billPayable, signDate,
					currentInfo);
			
			//设置更新人信息
			//审核时间
			entity.setAirOrgAuditTime(new Date());
			
			//审核人编码
			entity.setAirOrgAuditUserCode(currentInfo.getEmpCode());			
			//审核人名称
			entity.setAirOrgAuditUserName(currentInfo.getEmpName());
			
			// 更新代收货款状态
			codCommonService.updateAirPaidCodStatus(entity,
					SettlementDictionaryConstants.COD__AIR_STATUS__AUDIT_AGREE,
					entity.getAirStatus(), currentInfo);

		}

		LOGGER.info("Service end.");

	}

	/**
	 * 
	 * 反审核空运代收货款
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-13 下午4:58:32
	 */
	public void antiAuditBillCOD(List<String> entityIds, CurrentInfo currentInfo)
			throws SettlementException {

		LOGGER.info("Service start,entityIds:"
				+ (entityIds == null ? null : entityIds.toString()));

		// 如果代收货款实体ID为空，则抛出异常
		if (CollectionUtils.isEmpty(entityIds)) {
			throw new SettlementException("代收货款实体ID为空");
		}

		CODEntity entity = null;
		String status = null;
		BillPayableEntity billPayable = null;

		// 循环对代收货款数据处理
		for (String entityId : entityIds) {

			entity = codCommonService.queryById(entityId);

			// 如果代收货款实体为空，抛出异常
			if (entity == null) {
				throw new SettlementException("代收货款实体为空");
			}

			// 如果代收货款状态为资金部冻结、退款中、已退款，则抛出异常
			status = entity.getStatus();
			if (SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE
					.equals(status)
					|| SettlementDictionaryConstants.COD__STATUS__RETURNING
							.equals(status)
					|| SettlementDictionaryConstants.COD__STATUS__RETURNED
							.equals(status)) {
				throw new SettlementException("代收货款状态不能为资金部冻结、退款中、已退款");
			}

			// 获取代收货款应付单
			billPayable = codCommonService.getBillPayableEntity(entity);

			// 判断应付单是否可支付
			String payStatus = billPayable.getPayStatus();
			String effectiveStatus = billPayable.getEffectiveStatus();
			//判断是否已经支付，如果已经支付不能反审核
			if(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES
					.equals(payStatus)){
				throw new SettlementException("代收货款应付单已经支付，不能进行反审核操作");
			}
			
			//如果是未生效，不能反审核
			if (!SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES
					.equals(effectiveStatus)) {
				throw new SettlementException("代收货款应付单未生效，不能进行反审核操作");
			}

			// 如果代收货款应付单已核销金额不为0，抛出异常
			if (BigDecimal.ZERO.compareTo(billPayable.getVerifyAmount()) != 0) {
				throw new SettlementException("代收货款应付单已核销金额不为0，不能反审核操作");
			}

			// 调用应付单接口失效应付单
			billPayableService.disableBillPayable(billPayable, currentInfo);
			
			//清空审核人信息
			//审核时间
			entity.setAirOrgAuditTime(null);
			//审核人编码
			entity.setAirOrgAuditUserCode(null);
			//审核人名称
			entity.setAirOrgAuditUserName(null);
			
			// 更新代收货款状态
			codCommonService.updateAirPaidCodStatus(entity,
					SettlementDictionaryConstants.COD__AIR_STATUS__NOT_AUDIT,
					entity.getAirStatus(), currentInfo);

		}

		LOGGER.info("Service end.");

	}

	/**
	 * 
	 * 审核快递代理代收货款
	 * ISSUE-3389 小件业务
	 * @author guxinhua
	 * @date 2012-10-12 下午5:34:29
	 */
	public void auditLandBillCOD(List<String> entityIds, CurrentInfo currentInfo)
			throws SettlementException {

		LOGGER.info("审核快递代理代收货款Service start,entityIds:"
				+ (entityIds == null ? null : entityIds.toString()));

		// 如果代收货款实体ID为空，则抛出异常
		if (CollectionUtils.isEmpty(entityIds)) {
			throw new SettlementException("代收货款实体ID为空");
		}

		CODEntity entity = null;
		String status = null;
		BillPayableEntity billPayable = null;

		// 循环对代收货款数据处理
		for (String entityId : entityIds) {

			entity = codCommonService.queryById(entityId);

			// 如果代收货款实体为空，抛出异常
			if (entity == null) {
				throw new SettlementException("代收货款实体为空");
			}

			// 如果代收货款状态为资金部冻结、退款中、已退款，则抛出异常
			status = entity.getStatus();
			if (SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE
					.equals(status)
					|| SettlementDictionaryConstants.COD__STATUS__RETURNING
							.equals(status)
					|| SettlementDictionaryConstants.COD__STATUS__RETURNED
							.equals(status)) {
				throw new SettlementException("代收货款状态不能为资金部冻结、退款中、已退款");
			}

			// 获取代收货款应付单
			billPayable = codCommonService.getBillPayableEntity(entity);

			// 判断应付单是否可支付
			String payStatus = billPayable.getPayStatus();
			String effectiveStatus = billPayable.getEffectiveStatus();
			boolean isPayable = SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO
					.equals(payStatus);
			isPayable = isPayable
					&& SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES
							.equals(effectiveStatus);

			if (isPayable) {
				throw new SettlementException("代收货款应付单可支付，不能进行审核操作");
			}

			// 判断是否签收
			Date signDate = billPayable.getSignDate();
			if (signDate == null) {
				throw new SettlementException("运单未签收，不能进行审核操作");
			}

			// 调用应付单接口生效应付单
			billPayableService.enableBillPayable(billPayable, signDate,
					currentInfo);
			
			//设置更新人信息
			//审核时间
			entity.setLandOrgAuditTime(new Date());
			
			//审核人编码
			entity.setLandOrgAuditUserCode(currentInfo.getEmpCode());			
			//审核人名称
			entity.setLandOrgAuditUserName(currentInfo.getEmpName());
			
			// 更新代收货款状态
			codCommonService.updateLandPaidCodStatus(entity,
					SettlementDictionaryConstants.COD__AIR_STATUS__AUDIT_AGREE,
					entity.getLandStatus(), currentInfo);

		}

		LOGGER.info("审核快递代理代收货款Service end.");

	}

	/**
	 * 
	 * 反审核快递代理代收货款
	 * ISSUE-3389 小件业务
	 * @author guxinhua
	 * @date 2012-11-13 下午4:58:32
	 */
	public void antiAuditLandBillCOD(List<String> entityIds, CurrentInfo currentInfo)
			throws SettlementException {

		LOGGER.info("反审核快递代理代收货款Service start,entityIds:"
				+ (entityIds == null ? null : entityIds.toString()));

		// 如果代收货款实体ID为空，则抛出异常
		if (CollectionUtils.isEmpty(entityIds)) {
			throw new SettlementException("代收货款实体ID为空");
		}

		CODEntity entity = null;
		String status = null;
		BillPayableEntity billPayable = null;

		// 循环对代收货款数据处理
		for (String entityId : entityIds) {

			entity = codCommonService.queryById(entityId);

			// 如果代收货款实体为空，抛出异常
			if (entity == null) {
				throw new SettlementException("代收货款实体为空");
			}

			// 如果代收货款状态为资金部冻结、退款中、已退款，则抛出异常
			status = entity.getStatus();
			if (SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE
					.equals(status)
					|| SettlementDictionaryConstants.COD__STATUS__RETURNING
							.equals(status)
					|| SettlementDictionaryConstants.COD__STATUS__RETURNED
							.equals(status)) {
				throw new SettlementException("代收货款状态不能为资金部冻结、退款中、已退款");
			}

			// 获取代收货款应付单
			billPayable = codCommonService.getBillPayableEntity(entity);

			// 判断应付单是否可支付
			String payStatus = billPayable.getPayStatus();
			String effectiveStatus = billPayable.getEffectiveStatus();
			//判断是否已经支付，如果已经支付不能反审核
			if(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES
					.equals(payStatus)){
				throw new SettlementException("代收货款应付单已经支付，不能进行反审核操作");
			}
			
			//如果是未生效，不能反审核
			if (!SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES
					.equals(effectiveStatus)) {
				throw new SettlementException("代收货款应付单未生效，不能进行反审核操作");
			}

			// 如果代收货款应付单已核销金额不为0，抛出异常
			if (BigDecimal.ZERO.compareTo(billPayable.getVerifyAmount()) != 0) {
				throw new SettlementException("代收货款应付单已核销金额不为0，不能反审核操作");
			}

			// 调用应付单接口失效应付单
			billPayableService.disableBillPayable(billPayable, currentInfo);
			
			//清空审核人信息
			//审核时间
			entity.setLandOrgAuditTime(null);
			//审核人编码
			entity.setLandOrgAuditUserCode(null);
			//审核人名称
			entity.setLandOrgAuditUserName(null);
			
			// 更新代收货款状态
			codCommonService.updateLandPaidCodStatus(entity,
					SettlementDictionaryConstants.COD__AIR_STATUS__NOT_AUDIT,
					entity.getLandStatus(), currentInfo);

		}

		LOGGER.info("反审核快递代理代收货款Service end.");

	}
	
	/**
	 * @param codCommonService
	 */
	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}

	/**
	 * @param billPayableService
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}
	
}
