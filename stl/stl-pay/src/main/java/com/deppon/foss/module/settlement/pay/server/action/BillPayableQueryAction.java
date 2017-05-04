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
 * PROJECT NAME	: stl-pay
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/pay/server/action/BillPayableQueryAction.java
 * 
 * FILE NAME        	: BillPayableQueryAction.java
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
 * 
 * 
 * 
 * 
			 * 1.	SUC-678-查询应付单列表
			1.1	相关业务用例
			BUC_FOSS_4.7.30.10_040  申请支付服务补救
			BUC_FOSS_4.7.30.10_090  给客户支付现金
			BUC_FOSS_4.7.30.10_100  给客户汇款（除代收货款之外的8种）
			BUC_FOSS_4.7.30.10_036  自动生成应付单-装卸费
			1.2	用例描述
			用户根据客户信息、单据子类型以及应付单业务的开始、
			结束日期查询出可支付、有效且未完全核销的应付单信息，
			用户可以对查询出的应付单进行单条或批量付款/审核/反审核的操作，
			也可以对查询出信息进行导出操作。
			1.3	用例条件
			条件类型	描述	引用系统用例
			前置条件		
			后置条件	1.	查询出应付单信息，用户进行付款/审核/反审核操作	
			1.4	操作用户角色
			操作用户	描述
			收银员	能够查询所属部门应付单信息
			会计	能够查询下属部门应付单信息
			1.5	界面要求
			1.5.1	表现方式
			Web
			1.5.2	界面原型
				按日期查询界面
			 
			以上界面“付款”按钮旁增加“审核”、“反审核”按钮。
				按单号（应付单和运单）查询界面
			 
				根据来源单号查询
			 
			1.5.3	界面描述
				页面初始化组件描述：
			1、开始日期和结束日期是应付单的业务发生日期，
				页面初始化时系统自动提供默认值，用户也可以通过选择、
				直接修改的方式修改时间数据。 
				页面输入组件描述：
			1、用户在输入部门名称、客户名称、大区、小区名称时
			通过公共查询框选择信息;
			当登录用户为收银员时，部门名称、大区、小区名称为
			当前登录所属部门、区域信息且不可修改；
			当登录用户为会计时，部门名称、大区、小区只能选择下属区域、部门信息。
			2、用户输入单号时，一次可以输入一个也可以输入多个，
			单号之间用输入法半角状态逗号隔开，最多只能输入10个。
			其中单号输入框可以输入应付单单号，也可以输入运单号
				页面表格组件功能描述：
			1、页面初始化时，应付单结果集界面部分在页面不显示。 
			2、用户可以对查询出的结果通过多选框选择一条或者多条记录，
			也可以通过点击结果集左上角第一个多选框，
			实现对结果集信息的全选和清空操作。
			3、用户可以分别通过客户编号、总金额、
			会计日期对数据结果集进行升序或者降序排列。
			系统默认按业务日期升序排列。
			4、用户可以自定义显示结果集中的数据列。
			5、结果集分页显示，可选择每页显示30、50、100 ，默认30条
				客户类型以下拉列表形式显示，内容为：
				客户
				代理
				核销状态以下拉列表形式显示，内容为：
				完全核销
				部分核销
				未核销
				付款状态以下拉列表形式显示，内容为：
				已付款
				未付款
				生效状态以下拉列表形式显示，内容为：
				已生效
				未生效
				审核状态以下拉列表形式显示，内容为：
				全部
				未审核
				已审核
				是否有效以下啦列表形式显示，内容为：
				全部
				是
				否
				单据子类型以下拉列表形式显示，内容为：
				装卸费
				服务补救
				整车收款应付
				整车尾款应付
				航空公司成本
				空运始发代理成本
				空运中转代理成本
				空运到达代理成本
				偏线代理成本
			单据子类型从基础资料读取
				查询应付单信息界面提供以下按钮：
				查询
				重置
				导出
				付款
				根据来源单号查询
				外请车、整车、空运配载时合并产生的应付单需要
			支持能够按照配载单号、航空正单号查询
			1.6	操作步骤
			1.6.1	查询
			序号	基本步骤	相关数据	补充步骤
			1	页面初始化	开始日期、结束日期信息	
			1、	系统默认选择按日期查询界面
			2、	规则-请参见业务规SR1
			2	当按日期查询时，选择业务开始日期及结束日期，
			输入客户编码或名称 ，选择部门名称、大区、小区、客户名称、
			单据子类型、核销状态、生效状态、付款状态	客户信息（包含客户和代理）	
			1、	选择部门信息时，
			调用综合管理查询接口查询部门信息
			2、	选择客户信息时，
			调用综合管理查询客户信息接口查询客户信息
			3、	选择大区、小区信息时，
			调用综合管理查询区域信息接口查询区域信息
			4、	规则-请参见业务规SR2
			3	当按单号查询时，
			输入运单号或应付单号	运单号、应付单号	
			1、	检查输入的运单或应付单总数是否超过最大值
			2、	规则-请参见业务规SR3
			4	点击”查询”按钮	应付单详细信息	
			1、	查询应付单信息
			2、	规则-请参见业务规SR4
			3、	扩展事件参加4a、4b
			1.6.2	查看明细
			序号	基本步骤	相关数据	补充步骤
			5	点击操作列“明细”操作按钮	应付单信息	
			1、	系统自动跳转到应付单明细界面
			1.6.2	查看理赔分摊明细
			序号	基本步骤	相关数据	补充步骤
			56	点击操作列“分摊明细明细”操作按钮	应付单信息	
			2、	系统自动跳转到应付单明细界面x
			1、	系统通过弹出形式显示理赔分摊明细；
			
			1.6.3	付款
			序号	基本步骤	相关数据	补充步骤
			6	点击操作列付款按钮或同时选择多条应付单信息，
			点击批量“付款”按钮，进行付款操作	应付单信息	
			1、	如果单据子类型为“外请车尾款应付单”，
			则调用《生效长途外请车运费应付单》校验应付单是否可以付款
			1、2、	如果应付单进入对账单，则不允许进行付款操作
			2、3、	系统自动跳转到录入付款单页面
			3、4、	自动将所选择的应付单信息传递给该页面
			4、5、	规则-请参见业务规SR5
			5、6、	扩展事件参见6a、6b、6c、6d、6e、6f
			
			1.6.4	导出
			序号	基本步骤	相关数据	补充步骤
			7	点击“导出”按钮	应收单详细信息	
			1、	调用导出数据接口，
			导出所对应查询出的应付单信息（按页面显示字段导出）
			2、	导出完成后自动跳转到文件下载界面
			
			序号	扩展事件	相关数据	备注
			3a	当输入的运单号或应付单号个数超过10个时，
			进行输入单号过多提示		
			当用户所输入的运单号或应付单号超过10个点击查询时，
			输入框左下角，用红色字体显示“输入单号已超过10个，
			请修改后重新查询”；
			4a	当筛选不到结果时,提示没有符合条件的单据		
			提示信息以弹出框形式显示，内容“在当前条件查询范围内，
			没有符合要求的数据！”
			4b	点击“重置”按钮，页面查询条件数值恢复页面初始化状态		
			界面恢复系统初始化状态
			6a	选择的应付单信息为空，
			点击“还款”按钮时，提示错误信息		
			错误信息以弹出框形式提示，
			内容为：“应付单信息为空，
			不能进行还款操作!”
			6b	批量还款时，选择的应付单信息的客户汇款信息不一致时，提示错误信息		
			错误信息以弹出框形式提示，
			提示内容为：“批量还款时，只能选择同一个同一汇款账号的应付单!”
			6c	所选择的应付单信息中，
			存在已经付款的单据，则不能付款，并提示错误信息		
			错误信息为：“一个付款单只能付款一次”。
			6d	所选择的应付单信息中，
			存在不可支付、未冻结或已完全核销的单据，则不能付款，
			并提示错误信息		错误信息为：“不可支付、
			未冻结或已完全核销的单据不能进行付款”。
			6e	所选择的应付单已经入对账单，
			且该对账单已确认时，则不能付款，并提示错误信息		
			错误信息为：“已进入对账单且对账单已被确认的应付单信息不能付款！”
			6f	所选择的应付单为外请车尾款应付单，
			且校验失败，则不能付款，且提示错误信息		
			错误信息为：“外请车尾款应付单校验失败，不能付款”
			
			1.7	业务规则
			序号	描述
			SR1	1、	系统初始化的开始日期和结束日期默认相差3天。
			2、	结束日期为当前日期。 
			SR2	1、	开始日期和结束日期相差不能大于31天 
			SR3	1、	输入的运单或应付单号的个数不能超过10个。
			2、	单号都不能为空。
			SR4	1、	应付单是有效的。
			2、	应付单的是否红单字段的值为否
			3、1、	应付单的应付部门必须为当前登录用户所属或下属部门
			 SR5	1、	如果批量付款时，应付单的选择条数不能为空，最大条数100条
			2、	已完全核销的应付单不能付款
			3、	一个应付单不能重复付款
			4、	未生效的应付单不能进行付款
			5、	所选择的应付单的客户的客户汇款账号必须一致
			6、	应付单已经入对账单且对账单已确认不能进行付款
			7、	应付单相关的运单有未受理的更改单时不能进行付款

		1.9	非功能性需求
		使用量	全网每月产生应付单27万，
		部门最大量为13625,参考2012.3月ERP数据
		2012年全网估计用户数	收银员数量约2236名
		(截止2012.4.12，其增长速度与网点增长速度成正比)
		响应要求（如果与全系统要求 不一致的话）	
		查询在3秒内响应；付款在3秒内响应
		使用时间段	营业部正常上班时间（8:00-21:30）
		高峰使用时间段	9:00-11:00 13:00-17:30
		
		1.10	接口描述：
		接口名称 	对方系统（外部系统或内部其他模块）	接口描述
		查询客户信息接口	FOSS-综合管理子系统	查询客户信息
		查询部门信息接口	FOSS-综合管理子系统	查询部门信息
 	变更内容：
 		增加业务规则，如果应付单的单据类型为应付理赔，
 		可以查询理赔分摊明细；
		增加审核/反审核功能
		根据ISSUE-1529，增加根据来源单号查询功能
		根据ISSUE-1660，查询时，增加“是否有效”查询条件
		根据ISSUE-1861,付款时增加判断，
		如果应付单已经进去对账单，则不允许付款
		
		数据字典
		1.8.1	界面查询信息（输入）
		业务开始日期
		业务结束日期
		客户名称
		客户编号
		单号
		单据子类型
		部门名称
		大区名称
		小区名称
		核销状态
		付款状态
		生效状态
		
		1.8.2	应付单信息（输出）
		
		应付单号
		运单号
		应付部门编码
		应付部门名称
		录入部门编码
		录入部门名称
		客户名称
		客户编号
		总金额
		已核销金额
		未核销金额
		业务日期
		记账日期
		生效时间
		单据子类型
		生效状态
		付款状态
		生效状态
		创建人名称
		1.8.3	理赔分摊信息（输出）
		
		 字段名称 
		分摊部门编码
		分摊部门名称
		分摊金额
		分摊说明
		
		数据字典
		1.8.1	界面查询信息（输入）
		业务开始日期
		业务结束日期
		客户名称
		客户编号
		单号
		单据子类型
		部门名称
		大区名称
		小区名称
		核销状态
		付款状态
		生效状态
		
		1.8.2	应付单信息（输出）
		
		应付单号
		运单号
		应付部门编码
		应付部门名称
		录入部门编码
		录入部门名称
		客户名称
		客户编号
		总金额
		已核销金额
		未核销金额
		业务日期
		记账日期
		生效时间
		单据子类型
		生效状态
		付款状态
		生效状态
		创建人名称
		1.8.3	理赔分摊信息（输出）
		
		 字段名称 
		分摊部门编码
		分摊部门名称
		分摊金额
		分摊说明
		数据字典
		1.8.1	界面查询信息（输入）
		业务开始日期
		业务结束日期
		客户名称
		客户编号
		单号
		单据子类型
		部门名称
		大区名称
		小区名称
		核销状态
		付款状态
		生效状态
		
		1.8.2	应付单信息（输出）
		
		应付单号
		运单号
		应付部门编码
		应付部门名称
		录入部门编码
		录入部门名称
		客户名称
		客户编号
		总金额
		已核销金额
		未核销金额
		业务日期
		记账日期
		生效时间
		单据子类型
		生效状态
		付款状态
		生效状态
		创建人名称
		1.8.3	理赔分摊信息（输出）
		
		 字段名称 
		分摊部门编码
		分摊部门名称
		分摊金额
		分摊说明
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
 ***********************************************************************************************/
package com.deppon.foss.module.settlement.pay.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPayableQueryManageservice;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.BillPayableManageVo;
import com.deppon.foss.util.DateUtils;


/**
 * 查询应付单Action
 * @author 045738-foss-maojianqiang
 * @date 2012-11-16 下午4:24:03
 */
public class BillPayableQueryAction extends AbstractAction {
	/**
	 * 获取日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(BillPayableQueryAction.class);
	/**
	 * 导出excel名称
	 */
	private static final String  EXPROTNAME = "应付单";
	
	/**
	 * 业务日期常量
	 */
	private static final String QUERY_BUSINESSDATE_FLAG = "1";

	/**
	 * 记账日期常量
	 */
	private static final String QUERY_ACCOUNTDATE_FLAG = "2";
	
	/**
	 * 定义异常返回信息
	 */
	private String errorMessage;
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 6250425463486513629L;
	
	/**
	 * 前后台传参vo
	 */
	private BillPayableManageVo vo = new BillPayableManageVo();
	
	/**
	 *注入service 
	 */
	private IBillPayableQueryManageservice billPayableQueryManageservice;
	
	/**
	 * 导出excel的文件名称
	 */
	private String fileName;
	/**
	 * 导出excel的输入流
	 */
	private ByteArrayInputStream stream;
	
	@Autowired
	private IBillPayableService billPayableService;
	
	//邓大伟
	private ISettlementCommonService settlementCommonService;
	/**
	 * 查询应付单
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-16 下午4:26:04
	 */
	@JSON
	public String queryBillPayable(){
		try{
			String queryTab = vo.getManageDto().getQueryTab();
			//获取用户当前登录信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//查询方式
			if(StringUtil.isBlank(queryTab)){
				//抛出异常
				throw new SettlementException("页面查询方式传入为空！");
			}
			//如果按日期查询
			if(SettlementConstants.TAB_QUERY_BY_DATE.equals(queryTab)){
				
				//如果按业务日期查询
				if(QUERY_BUSINESSDATE_FLAG.equals(vo.getManageDto().getQueryDateFlag())){
					//校验信息
					if(vo.getManageDto().getBusinessBeginDate()==null){
						//抛出异常
						throw new SettlementException("业务开始日期不能为空！");
					}
					//非空判断
					if(vo.getManageDto().getBusinessEndDate()==null){
						//抛出异常
						throw new SettlementException("业务结束日期不能为空！");
					}
					//结束日期加1天
					vo.getManageDto().setBusinessEndDate(DateUtils.convert(DateUtils.addDay(vo.getManageDto().getBusinessEndDate(), 1)));
				
				//如果按记账日期查询
				}else if(QUERY_ACCOUNTDATE_FLAG.equals(vo.getManageDto().getQueryDateFlag())){
					//校验信息
					if(vo.getManageDto().getAccountBeginDate()==null){
						//抛出异常
						throw new SettlementException("记账开始日期不能为空！");
					}
					//非空判断
					if(vo.getManageDto().getAccountEndDate()==null){
						//抛出异常
						throw new SettlementException("记账结束日期不能为空！");
					}
					//结束日期加1天
					vo.getManageDto().setAccountEndDate(DateUtils.convert(DateUtils.addDay(vo.getManageDto().getAccountEndDate(), 1)));
				}
				
			}else{
				//如果按单号查询，则单号不能为空
				String[] billNos = vo.getManageDto().getBillNos();
				//非空判断
				if(billNos==null || billNos.length==0){
					//抛出异常
					throw new SettlementException("查询单号不能为空！");
				}
			}
			//查询应付单
			BillPayableManageResultDto resultDto =billPayableQueryManageservice.queryBillPayable(vo.getManageDto(),this.getStart(),this.getLimit(),currentInfo);
			//将结果防止到vo中回传
			vo.setResultDto(resultDto);
			//设置总条数，分页用
			this.setTotalCount(resultDto.getTotalCount());
			//返回成功
			return returnSuccess();
		}catch(BusinessException e){
			//记录日志
			logger.error(e.getErrorCode(),e);
			//返回失败
			return returnError(e);
		}
		
	}
	
	/**
	 * 付款
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-16 下午4:26:40
	 */
	@JSON
	public String payForBillPayable(){
		try{
			//邓大伟，2013-08-19，对接共享不用添加100条限制
			//获取对接系统参数
			DataDictionaryEntity dictEntity = DictUtil.getDataByTermsCode("SETTLEMENT__PAYTOSYSTEM_TYPE");
			List<DataDictionaryValueEntity> dataList = dictEntity.getDataDictionaryValueEntityList();
			//如果对接系统数据字典没配置，则抛出异常
			if(CollectionUtils.isEmpty(dataList)){
				throw new SettlementException("FOSS付款的付款工作流对接系统类型数据字典没配置，请去数据字典进行配置！");
			}
			//对接系统必须配置，且必须是1条 Y--财务共享，N--代表费控
			if(dataList.size()!=1){
				throw new SettlementException("付款工作流对接系统类型数据字典配置有误，必须只有1条。其中值为Y代表财务共享，N代表费控！");
			}
			
			//付款对接系统  Y--财务共享，N--代表费控 
			String payToSystem = dataList.get(0).getValueCode();
			//判空
			if(StringUtils.isEmpty(payToSystem)){
				throw new SettlementException("付款工作流对接系统类型数据字典配置有误，不能为空！值必须为Y或N。其中值为Y代表财务共享，N代表费控！");
			}
			
			//获取应付单号集合
			String[] billNos = vo.getManageDto().getBillNos();
			//校验单号集合是否为空
			if(billNos==null || billNos.length==0){
				//抛出异常
				throw new SettlementException("请选择需要支付的应付单进行付款!");
			}
			if(SettlementDictionaryConstants.SETTLEMENT__PAYTOSYSTEM_TYPE_CONSCONTROL.equals(payToSystem)){
				//每次批量付款不能超过100条
				if(billNos.length>SettlementConstants.PAY_LIMIT_MAX){
					//抛出异常
					throw new SettlementException("批量付款一次不能超过"+SettlementConstants.PAY_LIMIT_MAX+"条");
				}
			}
			//调用service进行付款caozuo
			BillPayableManageResultDto resultDto = billPayableQueryManageservice.payForBillPayable(vo.getManageDto());
			//如果dto为null,则new一个传给前台
			if(resultDto==null){
				//实例化
				resultDto = new BillPayableManageResultDto();
			}
			
//			if(SettlementDictionaryConstants.SETTLEMENT__PAYTOSYSTEM_TYPE_FSSC.equals(payToSystem)){
			String batchNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.FK_BN);
			String userCode = FossUserContext.getCurrentInfo().getEmpCode();
			String userName = FossUserContext.getCurrentInfo().getEmpName();
			resultDto.getPaymentEntity().setBatchNo(batchNo);
			resultDto.getPaymentEntity().setCreateUserCode(userCode);
			resultDto.getPaymentEntity().setCreateUserName(userName);
//			}
			vo.setPayToSystem(payToSystem);
			
			//设置属性值
			vo.setResultDto(resultDto);
			//返回成功
			return returnSuccess();
		}catch(BusinessException e){
			//记录日志
			logger.error(e.getMessage(),e);
			//返回失败
			return returnError(e);
		}
	}
	
	/**
	 * 导出应付单
	 * @author 045738-foss-maojianqiang
	 * @date 2012-11-16 下午4:28:06
	 */
	public String exportBillPayable(){
		//输入流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			//获取用户当前登录信息
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//如果按日期查询
			if(SettlementConstants.TAB_QUERY_BY_DATE.equals(vo.getManageDto().getQueryTab())){
				
				//如果按业务日期查询
				if(QUERY_BUSINESSDATE_FLAG.equals(vo.getManageDto().getQueryDateFlag())){
			
					//结束日期加1天
					vo.getManageDto().setBusinessEndDate(DateUtils.convert(DateUtils.addDay(vo.getManageDto().getBusinessEndDate(), 1)));
				
				//如果按记账日期查询
				}else if(QUERY_ACCOUNTDATE_FLAG.equals(vo.getManageDto().getQueryDateFlag())){

					//结束日期加1天
					vo.getManageDto().setAccountEndDate(DateUtils.convert(DateUtils.addDay(vo.getManageDto().getAccountEndDate(), 1)));
				}
				
			
			}
			//设置excel名称
    		try {	//设置excel名称
					this.setFileName(new String((EXPROTNAME).getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
				} catch (UnsupportedEncodingException e1) {
					//抛出异常
					throw new SettlementException("导出文件名编码转化错误！");
				}
    			//调用接口获取导出ecel
        		HSSFWorkbook wookBook = billPayableQueryManageservice.exportBillPayable(vo.getManageDto(),currentInfo);
			try {
				//将excel写到输出流中
				wookBook.write(baos);
				//进行流包装
				stream = new ByteArrayInputStream(baos.toByteArray()); 
			} catch (IOException e) {
				//抛出异常
				throw new SettlementException("生成excel流错误！");
			}
			//返回成功
    		return returnSuccess();
		}catch(BusinessException e){
			//错误编码
			errorMessage = e.getErrorCode();
			//返回失败
			return returnError(e.getMessage());
		}finally{
			//关闭流
    		if(baos!=null){
				try {
					//关闭流
					baos.close();
				} catch (IOException e) {
					//抛出异常
					throw new SettlementException("流关闭错误！");
				}
			}
		}
	}
	
	/**
	 * 审核应付单
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-20 下午1:43:51
	 * @return
	 */
	@JSON
	public String auditPayable(){
		try{
			//获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//获取应付单号集合
			String[] billNos = vo.getManageDto().getBillNos();
			//校验单号集合是否为空
			if(billNos==null || billNos.length==0){
				//抛出异常
				throw new SettlementException("请选择需要审核的应付单进行审核!");
			}
			//调用service进行社会和操作
			billPayableQueryManageservice.auditPayable(vo.getManageDto(), currentInfo);
			//返回成功
			return returnSuccess();
		}catch(BusinessException e){
			//记录日志
			logger.error(e.getMessage(),e);
			//返回成功
			return returnError(e);
		}
	}
	
	/**
	 * 反审核应付单
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-20 下午1:44:19
	 * @return
	 */
	@JSON
	public String unAuditPayable(){
		try{
			//获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//获取应付单号集合
			String[] billNos = vo.getManageDto().getBillNos();
			//校验单号集合是否为空
			if(billNos==null || billNos.length==0){
				//抛出异常
				throw new SettlementException("请选择需要审核的应付单进行审核!");
			}
			//调用service进行社会和操作
			billPayableQueryManageservice.unAuditPayable(vo.getManageDto(), currentInfo);
			//返回成功
			return returnSuccess();
		}catch(BusinessException e){
			//记录日志
			logger.error(e.getMessage(),e);
			return returnError(e);
		}
	}
	
	/**
	 * 根据传的运单号和客户编码查询应收单是否已经核销
	 * 
	 * @author 268217
	 * @date 2016-04-14
	 */
	@JSON
	public String queryReceivableBill(){
		try{			
			String waybillNo =vo.getManageDto().getWaybillNo();
			if(StringUtils.isEmpty(waybillNo)){
				throw new SettlementException("请选择需要查询的应付单!");
			}
			String suc=billPayableQueryManageservice.queryReceivableBill(vo.getManageDto());
			vo.setIsNot(suc);
			//返回成功
			return returnSuccess();
		}catch(BusinessException e){
			//记录日志
			logger.error(e.getMessage(),e);
			return returnError(e);
		}
	}
	
	 /**
     * 长期未支付有效应付单自动限制付款解除
     * @author 340778-foss-zf
     * @date 2016-7-22 下午3:02:01
     * @description
     */
	@JSON
	public String updateBillPayableAutoLimitRestore(){
		try{
			//获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//获取应付单号集合
			String[] billNos = vo.getManageDto().getBillNos();
			//校验单号集合是否为空
			if(billNos==null || billNos.length==0){
				//抛出异常
				throw new SettlementException("请选择需要解除限制的应付单");
			}
			//调用service进行社会和操作
			billPayableService.updateBillPayableAutoLimitRestore(Arrays.asList(billNos), currentInfo);
			//返回成功
			return returnSuccess();
		}catch(BusinessException e){
			//记录日志
			logger.error(e.getMessage(),e);
			return returnError(e);
		}
	}

	/**
	 * @GET
	 * @return errorMessage
	 */
	public String getErrorMessage() {
		/*
		 *@get
		 *@ return errorMessage
		 */
		return errorMessage;
	}

	/**
	 * @SET
	 * @param errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
		/*
		 *@set
		 *@this.errorMessage = errorMessage
		 */
		this.errorMessage = errorMessage;
	}

	/**
	 * @GET
	 * @return vo
	 */
	public BillPayableManageVo getVo() {
		/*
		 *@get
		 *@ return vo
		 */
		return vo;
	}

	/**
	 * @SET
	 * @param vo
	 */
	public void setVo(BillPayableManageVo vo) {
		/*
		 *@set
		 *@this.vo = vo
		 */
		this.vo = vo;
	}

	/**
	 * @GET
	 * @return fileName
	 */
	public String getFileName() {
		/*
		 *@get
		 *@ return fileName
		 */
		return fileName;
	}

	/**
	 * @SET
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		/*
		 *@set
		 *@this.fileName = fileName
		 */
		this.fileName = fileName;
	}

	/**
	 * @GET
	 * @return stream
	 */
	public ByteArrayInputStream getStream() {
		/*
		 *@get
		 *@ return stream
		 */
		return stream;
	}

	/**
	 * @SET
	 * @param stream
	 */
	public void setStream(ByteArrayInputStream stream) {
		/*
		 *@set
		 *@this.stream = stream
		 */
		this.stream = stream;
	}

	/**
	 * @SET
	 * @param billPayableQueryManageservice
	 */
	public void setBillPayableQueryManageservice(
			IBillPayableQueryManageservice billPayableQueryManageservice) {
		/*
		 *@set
		 *@this.billPayableQueryManageservice = billPayableQueryManageservice
		 */
		this.billPayableQueryManageservice = billPayableQueryManageservice;
	}

	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}
	
}
