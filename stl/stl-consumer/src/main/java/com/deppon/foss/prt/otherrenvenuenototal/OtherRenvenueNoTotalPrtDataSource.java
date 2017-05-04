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
 * FILE PATH        	: src/main/java/com/deppon/foss/prt/otherrenvenuenototal/OtherRenvenueNoTotalPrtDataSource.java
 * 
 * FILE NAME        	: OtherRenvenueNoTotalPrtDataSource.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 * 
 * 
 * 
 * 
 *	SUC-5 -打印小票信息
 *  相关业务用例
 *  BUC_FOSS_4.7.10.30_010  现金小票
 *  
 *1.2用例描述
 * 营业部、
 * 派送部及配载部门、
 * 会计新增小票信息提交成功后，
 * 按照模板打印出小票信息；
 * 或者营业部、
 * 派送部及配载部门、
 * 会计查询出的小票信息显示在列表中，
 * 选择一条小票信息打印，
 * 按照模板打印出纸质版小票信息，
 * 供客户签字。
 * 
 * 
 * 1.3	用例条件
 * 前置条件	
 * 	 1.	存在小票单据	
 * 		新增小票系统用例
	          查询小票系统用例
	          
	          
	1.4	操作用户角色
 * 
 * 收银员
 * 	
 * 	打印部门所有业务类型的小票信息
 * 
 * 营业员	
 * 
 * 	打印部门所有业务类型的小票信息
 * 
 * 配载员、
 * 
 * 偏线操作员
 * 	
 * 	打印偏线代理的小票信息
 * 
 * 会计
 * 	
 * 	打印超期预收款的小票信息
 * 
 * 
 * 小票凭证打印模板
 * 
 * 页面组件描述
 * 1.	小票单据的编号
 * 
 * 		与小票信息中的小票单号保持一致（打印不显示）；
 * 
 * 2.	客户名称来源于小票信息中的客户信息；
 * 
 * 3.	单号来源于小票信息中的运单单号；
 * 
 * 4.	付款方式来源于小票信息中的收款方式
 * 		（
 * 			现金、
 * 			非现金、
 * 			银行卡中一种
 * 		）；
 * 
 * 
 * 5.	计费类别来源于小票信息中的收入类别
 * 		（
 * 			自提改派送费、
 * 			仓储费、
 * 			开票税金、
 * 			加收送货费、
 * 			返货费、
 * 			提货费、
 * 			接货费、
 * 			更改单、
 * 			其他
 * 		）；
 * 
 * 6.	金额来源于小票收入金额，
 * 		系统自动填充金额栏，
 * 		同时需要将小写金额自动转化成大写打印在小写金额后栏位；
 * 
 * 7.	部门名称、
 * 		地址、
 * 		部门电话、
 * 		传真来源与小票信息中的收入部门的信息，
 * 		系统自动调用综合管理子系统中的信息；
 * 8.	自动获取小票信息中,
 * 		的收银人员名称为制单人；
 * 
 *  9.	自动获取小票信息,
 *  	保存后系统中的录入日期为制单时间。
 *  
 * 
 * 1.6	操作步骤
 * 
 * 基本步骤
 * 
 *	“新增小票”界面，
 *	 新增小票信息提交后弹出
 *	“是否打印”
 *	  打印
 *
 *	相关数据
*	打印小票信息
*	小票信息
 * 
 * 
 *  补充步骤
 *	1、	点击“是”按照小票模板打印信息
 *	2、	参见扩展事件1b
 *  1 、输出小票信息
 *
 * 查询-打印操作步骤
 * 
 * 基本步骤
 *“查询小票”界面，
 * 查询出的小票信息显示在列表中，
 * 选择一条小票信息，
 *  点击“打印”
 * 打印
 *
 * 
 * 相关数据
 * 
 * 	打印小票信息
 * 	小票信息
 * 
 * 
 * 补充步骤
 * 1、	点击“是”按照小票模板打印信息
 *  2、	参见扩展事件3b
 *  1、输出小票信息
 *
 * 扩展事件
 * 
 * 扩展事件
*	新增小票信息需提交不成功不能弹出
*	“是否打印”提示
*	点击“否”则退出对话框
*	选择的小票信息为无效时，
*	无法打印小票信息
*	点击“否”则退出对话框
 * 
 * 1.8	数据元素
 * 1.8.1	打印小票（输出）
 * 字段名称 
 * 部门名称
 * 收入部门编号
*部门电话
*地址
*传真
*运单单号
*客户名称
*付款方式
*计费类别
*金额
*制单人
*制单人员编号
*版本号
*是否有效
*是否作废
*业务时间
 * 
 * 
 * 使用量	全国大约一个月30万票
 *	2012年全网估计用户数	偏线外发员、
 *  统计员、
 *  配载员、
 *  收银员、
 *  会计数量约名12000人
 *  (
 *  	截止2012.4.12，
 *  	其增长速度与网点增长速度成正比
 *  )
 *		响应要求（如果与全系统要求 不一致的话）	查询在3秒内响应；核销在10秒内响应
 *		使用时间段	正常白天上班时间（8:00-17：30）
 *		高峰使用时间段	特殊业务，无高峰使用时间
 *
 * 
 * 
 * 
 * 1.10	接口描述：
 * 
 * 接口名称 	
 * 对方系统（
 * 	外部系统或内部其他模块
 * ）	
 * 接口描述
 * 查询网点信息接口	
 * FOSS-综合管理子系统
 * 系统自动调用综合管理系统，
 * 填充部门信息
 * 
 * 
 * 4.7.4	BUC_FOSS_4.7.10.30_010（现金小票）
 * 
 * 	4.7.4.1	业务说明
 * 
 * 	营业部、派送部以现金方式（
 * 	现金，银行卡
 *  ）收取客户以下费用：
 *  仓储费、
 *  自提改派送、
 *  加收送货费、
 *  会员卡费、
 *  包装费、
 *  放空费 、
 *  卖废品、
 *  其他，
 *  做为营业外收入，
 *  是运单收入的有机补充；
 * （收取代理其他费用）。
 * 
 * 4.7.4.3	需求描述
 * 
 * 需求名称：	小票收入-现金小票
 * 
 * 
 * 	需求编号：	BUC_FOSS_4.7.10.30_010
 * 
 *	主执行人：	
 *	营业员、
 *	收银员、
 *	偏线操作员	
*	相关人员：
*	客户、
*	偏线代理、
*	会计
*
 *	前置条件：
 *		1营业部、
 *			派送部已经领取小票单据
 *		2营业部、
 *			派送部与客户确认小票现金支付小票业务
 *	3营业员，
 *			收银员填写小票现金小票单据
 *	4偏线操作人员确认需要收取偏线代理，
 *		其他应收款。#
 *
 *后置条件	
 *
 *1提交小票成功后，
 *生成现金收款单，
 *进入当日现金收入报表，
 *收银员需要进行收入确认
 *
 *触发事件：	
 *营业部、
 *派送部、
 *偏线操作部门发生小票业务
 *
 *需求详细说明：
 *	1. 营业员、
 *	收银员、
 *	偏线操作员根据小票单据，
 *	选择小票单据；
 *2.确定收入部门、
 *	所属的公司名称；
 *3.1营业员、
 *	收银员填写的客户编码与客户名称，
 *	联系电话；
 *3.2偏线操作员填写代理编码与代理名称，
 *	联系电话；
 *4. 营业员、
 *	收银员、
 *	偏线操作员填写付款方式与收款类别、
 *	填写收款金额；
 *5.如果收款类别与运单有关需要营业员、
 *	收银员、
 *	偏线操作员填写运单号；
 *6. 营业员、
 *	收银员、
 *	偏线操作员可以填写相关收款事项，
 *	说明该项收款；
 *7.收银员记录操作人员姓名，
 *	收款日期；
 *8.营业员、
 *	收银员、
 *	偏线操作员打印小票信息，
 *	让客户签字签字确认；
 *9.收银员登记每天小票收入信息；
 *
 *.需求扩展说明（分支）：	
 *	预收转收入时，
 *	由会计做小票
*	有客户编码的，
*	输入客户编码直接从CRM中带出客户小票信息，
*	并在CRM中进行维护操作；
*	散户要新增小票业务，
*	有客户编码产生的客户信息进入CRM，
*	并在CRM中进行维护；
*	散户要新增小票，
*	不产生对应的客户编码，
*	维护在小票管理里面维护；
*	业务规则：
*	R1.必须连号使用，
*		与前一单号连续。
*	R2.收入部门必须是所属公司下辖部门
*	R3. （1）
*   3.1如果是老客户，
*   	需要提供客户编码；
*   	根据客户编码确定客户名称与客户电话
 *   3.2如果是新客户，
 *   	营业员、
 *   	收银员记录客户名称、
 *   	客户电话，
 *   	给客户提供编码
 *   
*	（2）3.3如果是老代理，
*	  需要提供代理编码；
*   根据代理编码确定代理名称与代理电话；
*   
*	3.4如果是新代理，
*	偏线操作员需要告知偏线代理管理员记录代理名称、
*
*	代理电话，
*	给偏线提供编码；
*
*	R4.付款方式为：
*	现金、
*	银行卡；
*	4.1收款类别：仓储费、
*	自提改派送、
*	加收送货费、
*	会员卡费、
*	包装费、
*	放空费 、
*	卖废品、
*	其他
*
*	4.2小票类别需要设置成基础资料供后期维护使用
*
*	 收入金额：单位为元，
*	四舍五入取整计算
*	R5.运单号必须已经使用，
*	为8位数字
*	R6.打印小票凭证需要包含以下信息：客户名称，
*	单号，
*	付款方式，
*	计费类别，
*	金额，
*	部门名称，
*	部门电话，
*	地址，
*	传真，
*	制单人，
*	制单日期，
*	打印凭证让客户签字确认
*
*	R7.登记小票信息必须按照编号顺序连续登记
*
* 业务系统关联要求：
* 	1.	CRM系统：提供客户的基本信息，
* 	客户编码，
* 	客户名称，
* 	联系电话，
* 	客户总信用额度。
*	2.	财务系统
*
*假设：	
*	备注：	
*	1. 跳号需要提示，
*		号码相差过大，
*	2.付款方式为：
*		现金、
*		银行卡、
*		月结、
*		临时欠款
*	3. 收银人员系统记录为当前操作人员，
*	收银时间系统记录为保存时的服务器时间
*
 * 
 * 
 * 4.7.4.5	数据元素
 * 字段名称
 * 	小票单号
 * 	版本号
 * 	是否有效版本
 * 	是否作废
 * 	收入部门
 * 	收入公司
 * 	客户编码
 * 	客户名称
 * 	客户电话
 * 	收入金额
 * 	付款方式
 * 	收款类别
 * 	收银员
 * 	运单号
 * 	收银时间
 * 	收款事项
 * 	源单据类型
 * 	会计日期
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 说明
* 小票单号
* 本次小票的版本号
* 标志该条小票记录是否是有效版本
* 标志本条小票是否作废
* 小票收入部门
* 小票收入所属子公司
* 客户编码，或代理名称
* 客户名称或客户名称
* 代理或客户的电话或手机号码
* 小票收入金额
* 小票付款方式
* 收款类别
* 录入小票人员
* 运单号
* 录入小票的时间
* 收款事由
* 小票来源
* 会计日期
* 
* 
* 
******************************************************************************/

package com.deppon.foss.prt.otherrenvenuenototal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IOtherRevenueService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.OtherRevenueEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherRenvenueNoTotalPrtResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherRevenueDto;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.util.DateUtils;


/**
 * 小票打印清单.
 *
 * @author 101911-foss-zhouChunlai
 * @date 2012-11-29 上午8:33:40
 */ 
public class OtherRenvenueNoTotalPrtDataSource implements JasperDataSource {

	// 注入日志
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(OtherRenvenueNoTotalPrtDataSource.class);
	
	/** The result dto. */
	private OtherRenvenueNoTotalPrtResultDto resultDto;
	
	/**
	 * 设置前端的打印参数.
	 *
	 * @param jasperContext the jasper context
	 * @return the map
	 * @throws Exception the exception
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-29 上午8:40:04
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createParameterDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	@Override
	public Map<String, Object> createParameterDataSource(
			JasperContext jasperContext) throws Exception {
		logger.info("开始填充小票打印清单数据源");
		// 声明打印值map
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (jasperContext != null && jasperContext.getParamMap() != null) {
			OtherRevenueDto dto = new OtherRevenueDto();
			// 获得查询选项卡标识
			String queryPageTab=(String)jasperContext.get("queryPageTab");
			// 判断查询选项卡标识是否为空
			if(StringUtils.isBlank(queryPageTab)){
				throw new SettlementException("查询参数不正确！");
			}
			// 设置查询选项卡标识
			dto.setQueryPageTab(queryPageTab);
			//获取小票service实例
			IOtherRevenueService otherRevenueService = (IOtherRevenueService) jasperContext.getApplicationContext().getBean("otherRevenueService");
			//按收入日期查询
			if(StringUtils.equals(queryPageTab, SettlementConstants.TAB_QUERY_BY_DATE)){
				// 录入开始时间
				String createStartTime=(String)jasperContext.get("createStartTime");
				// 录入结束时间
				String createEndTime=(String)jasperContext.get("createEndTime");
//				// 收入公司名称
//				String generatingComName=(String)jasperContext.get("generatingComName");
//				// 收入公司编码
//				String generatingComCode=(String)jasperContext.get("generatingComCode");
				// 客户类型
				String customerType=(String)jasperContext.get("customerType");
				// 客户编码
				String customerCode=(String)jasperContext.get("customerCode");
				// 收入部门名称
				String generatingOrgName=(String)jasperContext.get("generatingOrgName");
				// 收入部门编码
				String generatingOrgCode=(String)jasperContext.get("generatingOrgCode");
				// 收款方式
				String paymentType=(String)jasperContext.get("paymentType");
				
				//打印人名称
				String printUserName=(String)jasperContext.get("printUserName");
				//打印人编码
				String printUserCode=(String)jasperContext.get("printUserCode");
				//此处需要判断下，如果选择一个默认为string的，多个才是数组
				if(jasperContext.get("productCodeList")!=null){
					if(jasperContext.get("productCodeList") instanceof String[]){
						String[] productCodeList = (String[]) jasperContext.get("productCodeList");
						if(productCodeList!=null && productCodeList.length>0){
							dto.setProductCodeList(Arrays.asList(productCodeList));
						}
					}else if(jasperContext.get("productCodeList") instanceof String){
						List<String> productList = new ArrayList<String>();
						productList.add((String)jasperContext.get("productCodeList"));
						dto.setProductCodeList(productList);
					}
				}
				
				//设置打印人名称
				parameter.put("printUserName",printUserName);
				//设置打印人编码
				parameter.put("printUserCode",printUserCode);
				
				//打印部门名称
				String printOrgName=(String)jasperContext.get("printOrgName");
				//打印部门编码
				String printOrgCode=(String)jasperContext.get("printOrgCode");
				//设置打印部门名称
				parameter.put("printOrgName",printOrgName);
				//设置打印部门编码
				parameter.put("printOrgCode",printOrgCode);
				
//				String dataString = DateUtils.convert(new Date(), DateUtils.DATE_TIME_FORMAT);
				//设置打印时间
				parameter.put("printTime",new Date());
				//设置打印人
				
				//设置录入时间
				if(StringUtils.isNotBlank(createStartTime) && StringUtils.isNotBlank(createEndTime)){
					// 时间转化
					Date startTime=DateUtils.convert(createStartTime);
					// 时间转化
					Date endTime=DateUtils.convert(createEndTime);
					// 返回两个时间的相差天数
					Long i = DateUtils.getTimeDiff(startTime, endTime);
					// 查询开始日期和结束日期不能超过31天
					if (i != null && i.longValue() > SettlementConstants.DATE_LIMIT_SEX_MONTH) {
						throw new SettlementException("查询开始日期和结束日期不能超过"+SettlementConstants.DATE_LIMIT_DAYS_MONTH+"天！");
					}
					// 设置开始时间
					dto.setCreateStartTime(startTime);
					// 设置结束时间
					dto.setCreateEndTime(DateUtils.getEndTimeAdd(endTime));
				}else{
					throw new SettlementException("录入时间不能为空！");
				}
				
				//设置收入部门编码
				if(StringUtils.isNotBlank(generatingOrgCode) && StringUtils.isNotBlank(generatingOrgName)){
					//设置收入部门编码
					dto.setGeneratingOrgCode(generatingOrgCode);
					//设置收入部门名称
					//dto.setGeneratingOrgName(generatingOrgName);
				}else{
					// 收入部门不能为空
					throw new SettlementException("打印清单时，收入部门不能为空！");
				}
				 
//				//设置收入公司
//				if(StringUtils.isNotBlank(generatingComCode) && StringUtils.isNotBlank(generatingComName)){
//					//设置收入公司编码
//					dto.setGeneratingComCode(generatingComCode);
//					//设置收入公司名称
//					dto.setGeneratingComName(generatingComName);
//				}else{ 
//					 throw new SettlementException("收入公司不能为空");
//				}
				
				//设置客户类型
				if(StringUtils.isNotBlank(customerType)){
					dto.setCustomerType(customerType);
				}
				
				//设置客户编码
				if(StringUtils.isNotBlank(customerCode)){
					dto.setCustomerCode(customerCode);
				}
				
				//设置收款方式
				if(StringUtils.isNotBlank(paymentType)){
					dto.setPaymentType(paymentType);
				}
				// 按时间查询打印小票记录
				resultDto=otherRevenueService.queryOtherRevenue(dto);
				
				
			//按小票单号查询
			}else if(StringUtils.equals(queryPageTab, SettlementConstants.TAB_QUERY_BY_OTHERREVENUE_NO)){
				String otherRevenueNos=(String)jasperContext.get("otherRevenueNos");
				// 把字符串转换为集合.
				List<String> otehrRevenueNosList=convertOtherRevenueNosToList(otherRevenueNos);
				// 设置小票单号集合
				dto.setOtherRevenueNos(otehrRevenueNosList);
				// 按时间查询打印小票记录
				resultDto=otherRevenueService.queryOtherRevenue(dto);
			}else{
				throw new SettlementException("查询参数不正确！");
			}
			
			if(null!=resultDto){
				//总金额
				parameter.put("totalAmount", resultDto.getTotalAmount());
				//银行卡总金额
				parameter.put("cdTotalAmount", resultDto.getCdTotalAmount());
				//现金总金额
				parameter.put("chTotalAmount", resultDto.getChTotalAmount());
				//月结总金额
				parameter.put("ctTotalAmount", resultDto.getCtTotalAmount());
				//临时欠款总金额
				parameter.put("dtTotalAmount", resultDto.getDtTotalAmount());
			}
		}
		return parameter;
	}

	
	/**
	 * 把字符串转换为集合.
	 *
	 * @param otherRevenueNos the other revenue nos
	 * @return the list
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-29 上午10:11:24
	 */
	private List<String> convertOtherRevenueNosToList(String otherRevenueNos){
		if(StringUtils.isNotEmpty(otherRevenueNos)){
			String[] numbers=otherRevenueNos.split(",");
			// 把字符串转换为集合.
			List<String> list=new ArrayList<String>(Arrays.asList(numbers));
			// 判断转换小票单号失败
			if(CollectionUtils.isEmpty(list)){
				throw new SettlementException("转换小票单号失败");
			}
			return list;
		}else{
			throw new SettlementException("小票单号不能为空");
		}
	}
	
	/**
	 * 设置打印jrxml中Detail模块中的内容.
	 *
	 * @param jasperContext the jasper context
	 * @return the list
	 * @throws Exception the exception
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-29 下午2:54:36
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createDetailDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	@Override
	public List<Map<String, Object>> createDetailDataSource(
			JasperContext jasperContext) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 查询到的明细数据不为空时
		if (resultDto != null && CollectionUtils.isNotEmpty(resultDto.getList())) {
			DataDictionaryEntity ptn = DictUtil.getDataByTermsCode("OTHER_REVENUE__PAYMENT_TYPE");
			DataDictionaryEntity icn = DictUtil.getDataByTermsCode("BILL_RECEIVABLE__COLLECTION_TYPE");
			// 循环小票实体
			for (OtherRevenueEntity entity : resultDto.getList()) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 创建时间
				map.put("createTime", entity.getCreateTime());
				// 小票单号
				map.put("otherRevenueNo", entity.getOtherRevenueNo());
				// 客户名称
				map.put("customerName", entity.getCustomerName());
				// 运单号
				map.put("waybillNo", entity.getWaybillNo());
				// 付款类型
				map.put("paymentTypeName", this.rendererSubmitToDisplay(entity.getPaymentType(), ptn));
				map.put("incomeCategoriesName", this.rendererSubmitToDisplay(entity.getIncomeCategories(), icn));
				map.put("amount", entity.getAmount() != null ? entity.getAmount(): "");
				map.put("createUserName", entity.getCreateUserName());
					
				list.add(map);
			}
		}
		return list;
	}
	
	/**
	 *将业务字典的valueCode（提交值）转换成描述valueName（数据字典显示值）
	 * 使用方法rendererDictionary(valueCode,’abc’);
	 * @param valueCode 所要转换的值
	 * @param termsCode 词条代码
	 */
	private String rendererSubmitToDisplay(String valueCode, DataDictionaryEntity data) {
		if (valueCode!=null && data!=null) {
			for (DataDictionaryValueEntity value : data.getDataDictionaryValueEntityList()) {
				if (valueCode.equals(value.getValueCode())) {
				     return value.getValueName();
				}
			}
		}
		return valueCode;
	}
} 
