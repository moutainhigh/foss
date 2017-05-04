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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/IWaybillManagerService.java
 * 
 * FILE NAME        	: IWaybillManagerService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.deppon.foss.esb.crm.server.order.FossQueryAcctinfoRequest;
import com.deppon.foss.esb.crm.server.order.FossQueryAcctinfoResponse;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkActivitiesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesProductEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AgencyBranchOrCompanyDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.DebitForCUBCDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.QueryMoneyConditionDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.QueryMoneyDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.OrderLockEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.OrderLockResult;
import com.deppon.foss.module.pickup.waybill.shared.domain.PartnersWaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillAcHisPdaEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillMessageEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcTranferEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AbandonedGoodsCondition;
import com.deppon.foss.module.pickup.waybill.shared.dto.AbandonedGoodsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddedPlanFeeCalculateDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmActiveInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmWaybillServiceDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EffectiveDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.FreightRouteDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GUIPrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GisDepartmentDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GisPickupOrgDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.NeedCreateTodoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PDAGoodsAttrDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.QueryPickupPointDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.RouteLineInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillLogEntityQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOtherChargeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillQueryInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillSystemDto;
import com.deppon.foss.module.pickup.waybill.shared.request.HisSegMatchRequest;
import com.deppon.foss.module.pickup.waybill.shared.response.HisSegMatchResponse;
import com.deppon.foss.module.pickup.waybill.shared.vo.BranchQueryVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.CrmActiveParamVo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillBadAccountEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleDto;
import com.deppon.foss.module.transfer.stock.api.shared.domain.QmsYchExceptionReportEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;

/**
 * 
 * 运单管理接口
 * 
 * @author foss-sunrui
 * @date 2012-10-30 下午4:08:25
 * @version
 */
public interface IWaybillManagerService extends IService {

	/**
	 * DMANA-8928 FOSS开单品名自动匹配货源品类需求
	 * 
	 * @author Foss-206860
	 */
	String queryIndustrySourceCategory(String goodsName);

	/**
	 * 
	 * <p>
	 * 通过运单号获取运单信息
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午4:08:45
	 * @param waybillNo
	 * @return
	 * @see
	 */
	WaybillEntity queryWaybillBasicByNo(String waybillNo);

	/**
	 * 
	 * <p>
	 * 通过运单号码集合获取运单信息
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午4:09:39
	 * @param waybillNoList
	 * @return
	 * @see
	 */
	List<WaybillEntity> queryWaybillBasicByNoList(List<String> waybillNoList);

	/**
	 * 
	 * <p>
	 * 通过运单号获取运单信息
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午4:08:45
	 * @param waybillNo
	 * @return
	 * @see
	 */
	WaybillDto queryWaybillByNo(String waybillNo);

	/**
	 * 
	 * <p>
	 * 通过运单号码集合获取运单信息
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午4:09:39
	 * @param waybillNoList
	 * @return
	 * @see
	 */
	List<WaybillDto> queryWaybillByNoList(List<String> waybillNoList);

	/**
	 * 
	 * <p>
	 * 通过运单号判断运单是否存在
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午7:43:55
	 * @param waybillNo
	 * @return
	 * @see
	 */
	boolean isWayBillExsits(String waybillNo);

	/**
	 * 
	 * <p>
	 * 通过运单号判断运单是否存在
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午7:43:55
	 * @param waybillNo
	 * @return
	 * @see
	 */
	boolean isWayBillExsitsAndOrderNo(String waybillNo, String orderNo);

	/**
	 * 
	 * 通过运单号判断非PDA待补录运单是否存在
	 * 
	 * @author foss-sunrui
	 * @date 2013-3-21 下午5:39:06
	 * @param waybillNo
	 * @return
	 * @see
	 */
	boolean isWayBillExsitsNoPDAPending(String waybillNo);

	/**
	 * 
	 * <p>
	 * 通过运单号判断暂存运单是否存在
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午7:43:55
	 * @param waybillNo
	 * @return
	 * @see
	 */
	boolean isWayBillPendingExsits(String waybillNo);

	/**
	 * 
	 * 通过运单号、流水号校验 当前流水号是否存在
	 * 
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-5 上午9:28:56
	 * @param waybillNo
	 * @return
	 * @see
	 */
	boolean isSerialNoExsits(String waybillNo, String serialNo);

	/**
	 * 
	 * <p>
	 * 通过订单号判断运单是否存在
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午7:43:55
	 * @param orderNo
	 * @return
	 * @see
	 */
	boolean isWayBillExsitsByOrderNo(String orderNo);

	/**
	 * 
	 * <p>
	 * 通过订单号判断暂存运单是否存在
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午7:43:55
	 * @param orderNo
	 * @return
	 * @see
	 */
	boolean isWayBillPendingExsitsByOrderNo(String orderNo);

	/**
	 * 后台运单提交
	 * 
	 * @param waybill
	 * @return Result
	 */
	void submitWaybill(WaybillDto waybill);

	/**
	 * 后台大客户激活
	 * 
	 * @param waybill
	 * @return String 激活失败，抛出异常的内容
	 */
	void submitEWaybill(WaybillDto waybill);

	/**
	 * 
	 * <p>
	 * 更新货物数量
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-13 下午6:18:02
	 * @param waybillNo
	 * @param goodNum
	 * @param userCode
	 * @param orgCode
	 * @return
	 * @see
	 */
	ResultDto updateGoodsNum(String waybillNo, int goodNum, String userCode,
			String orgCode);

	/**
	 * 删除已补录的PDA运单
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-30 下午7:03:38
	 */
	void deleteAdditionalState(String waybillNum);

	/**
	 * 新增线路信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-3 上午9:11:01
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#addLine()
	 */
	void addLine(WaybillDto waybill);

	/**
	 * 新增签收单返单
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-10-30 下午7:05:37
	 */
	void addSignReturnBill(WaybillDto waybillDto);

	/**
	 * 更新在线运单打印次数
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-15 下午11:48:25
	 * @param waybillNo
	 * @return
	 * @see
	 */
	/**
	 * 根据出发部门、到达部门、产品类型、开单时间（数据生成时间）获得预计出发时间
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-19 下午3:40:43
	 */
	Date searchPreLeaveTime(String departDeptCode, String specialLine,
			String productCode, Date createTime);

	/**
	 * 根据预计出发日期、出发部门、到达部门、产品类型、开单时间（数据生成时间）获得承诺自提时间
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-20 下午3:31:06
	 */
	EffectiveDto searchPreSelfPickupTime(String departDeptCode,
			String arriveDetpCode, String productCode, Date preLeaveTime,
			Date createTime);

	/**
	 * 根据承诺自提时间、出发部门、到达部门、产品类型、开单时间（数据生成时间）获得承诺派送时间
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-20 下午3:31:49
	 */
	EffectiveDto searchPreDeliveryTime(String departDeptCode,
			String arriveDetpCode, String productCode, Date preLeaveTime,
			Date createTime);

	/**
	 * 
	 * <p>
	 * 运单与订单重绑定，该接口提供给接送货2组
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-11-28 下午4:09:20
	 * @param oldWaybillNo
	 * @param newWaybillNo
	 * @param orderNo
	 * @return
	 * @see
	 */
	ResultDto reBindOrder(String oldWaybillNo, String newWaybillNo,
			String orderNo);

	/**
	 * 
	 * <p>
	 * 更新运单<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-11-29
	 * @param waybill
	 * @return int
	 */
	int updateWaybillEntity(WaybillEntity waybill);

	/**
	 * 
	 * <p>
	 * 根据运单Id查询运单<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-11-30
	 * @param waybillId
	 * @return WaybillEntity
	 */
	WaybillDto getWaybillDtoById(String waybillId);

	/**
	 * 
	 * <p>
	 * 删除运单对象
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-12-3
	 * @param waybillEntity
	 * @return int
	 */
	int deleteWaybillEntityById(String waybillId);

	/**
	 * 
	 * 追加更改后临时运单
	 * 
	 * @author 102246-foss-shaohongliang
	 * @param currentDate
	 * @date 2012-12-3 下午3:23:32
	 */
	void appendTempWaybillAfterChanged(WaybillDto waybillDto, Date currentDate);

	/**
	 * 得到综合的走货路径实体 查询参数为运单号 出发营业部 到达营业部
	 * 
	 * @author foss-jiangfei
	 * @param waybillNo
	 * @param serialNos
	 * @return
	 */

	FreightRouteEntity getRouteLineInfo(String waybillNo, String startOrg,
			String destOrg);

	/**
	 * <p>
	 * 得到综合的走货路径 经过外场部门编码列表 查询参数为运单号 出发营业部 到达营业部
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-12-4 下午9:05:26
	 * @param waybillNo
	 * @param startOrg
	 * @param destOrg
	 * @return
	 * @see
	 */
	List<String> getRouteLineCodeInfo(String waybillNo, String startOrg,
			String destOrg);

	/**
	 * 得到中转的走货路径线段列表
	 * 
	 * @author foss-jiangfei
	 * @param waybillNo
	 * @param serialNos
	 * @return
	 */

	List<RouteLineInfoDto> getRouteDetailInfo(String waybillNo, String serialNos);

	/**
	 * 得到中转的走货路径 有顺序的途径外场 包含始发到达部门编码集合
	 * 
	 * @author foss-jiangfei
	 * @param waybillNo
	 * @param serialNos
	 * @return
	 */

	List<String> getRouteDetailCodeInfo(String waybillNo, String serialNos);

	/**
	 * 检查是否有此外请车编号</br>
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-12-4 下午4:48:45
	 * @param inviteNo
	 *            外请车编号
	 * @param deptCode
	 *            部门编码
	 * @return 外请车申请状态
	 */
	String checkInviteNoIsExists(String inviteNo, String deptCode);

	/**
	 * 根据外请车编号查询 请车价格 </br> 约车申请不存在 抛出异常
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-12-4 下午2:32:17
	 * @param inviteNo
	 *            外请车编号
	 * @return 外请车价格
	 */
	InviteVehicleDto queryInviteCostByInviteNo(String inviteNo);

	/**
	 * 
	 * 查询运单
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2012-11-26 下午08:37:46
	 */
	List<WaybillEntity> queryWaybill(WaybillDto waybillDto);

	/**
	 * 根据运单号查询运单部分数据（查询付款方式，到付金额，外发代理和币种, 运输性质,是否整车运单,提货方式,总费用,保价声明价值,代收货款,
	 * 货物总件数,最终配载部门，订单编号）
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-13 下午2:50:46
	 */
	WaybillEntity queryPartWaybillByNo(String waybillNo);

	/**
	 * 
	 * 弃货查询
	 * 
	 * @date 2012-12-15
	 * @param condition
	 * @param currentInfo
	 * @return
	 * @return List<AbandonedGoodsDto>
	 * @see
	 */
	List<AbandonedGoodsDto> queryAbandonedGoodsDtoList(
			AbandonedGoodsCondition condition, CurrentInfo currentInfo);

	/**
	 * 
	 * <p>
	 * 调用综合接口查询走货路径
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-17 下午6:16:26
	 * @param sourceCode
	 * @param targetCode
	 * @param productCode
	 * @param time
	 * @return
	 * @see
	 */
	FreightRouteDto queryFreightRouteBySourceTarget(String sourceCode,
			String targetCode, String productCode, Date time);

	/**
	 * 更新弃货导入表
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-17
	 * @return void
	 */
	void updateAbandonedGoods(List<String> oldWaybillNo, String newWaybillNo);

	/**
	 * 
	 * <p>
	 * 通过组织机构编码查组织机构
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-17 下午7:33:20
	 * @param code
	 * @return
	 * @see
	 */
	OrgAdministrativeInfoEntity queryOrgInfoByCode(String code);

	/**
	 * 
	 * 根据银行编码查询银行信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-12-6 下午04:43:42
	 */
	BankEntity queryBankByCode(String code);

	/**
	 * 
	 * <p>
	 * 通过代理网点编码查询代理网点信息
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-18 上午11:51:51
	 * @param agencyBranchCode
	 * @return
	 * @see
	 */
	AgencyBranchOrCompanyDto queryAgencyBranchCompanyInfo(
			String agencyBranchCode);

	/**
	 * 查询当前部门所在大区
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-17
	 * @param currentInfo
	 * @return OrgAdministrativeInfoEntity
	 */
	OrgAdministrativeInfoEntity queryBigRegionOfCurrDept(CurrentInfo currentInfo);

	/**
	 * 运单明细查询
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-18
	 * @param waybillNoList
	 * @return List<CrmWaybillServiceDto>
	 */
	List<CrmWaybillServiceDto> queryWaybillDetail(List<String> waybillNoList);

	/**
	 * 查询部门标杆编码
	 * 
	 * @param waybillNo
	 * @return
	 */
	String queryDepartureDeptNumber(String waybillNo);

	/**
	 * 
	 * <p>
	 * 货物属性变更
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-19 上午11:35:30
	 * @param goodAttr
	 * @return
	 * @see
	 */
	ResultDto updateGoodsAttr(PDAGoodsAttrDto goodAttr);

	/**
	 * 
	 * <p>
	 * 查询出发营业部的默认外场
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-21 上午9:43:15
	 * @param saleCode
	 * @param productCode
	 * @param isTransSale
	 *            是否驻地营业部
	 * @return
	 * @see
	 */
	String queryDefaultTransCodeDept(String saleCode, String productCode,
			boolean isTransSale);

	/**
	 * 运单开单完成之后，更新状态</br> 标识此约车已经完成开单，重复更新抛出异常
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-12-4 下午5:23:02
	 * @param inviteNo
	 *            外请车编号
	 * @param billStatus
	 *            开单状态
	 */
	void updateInviteVehicleForFinishBill(String inviteNo);

	/**
	 * 
	 * 查询到货金额
	 * 
	 * @param
	 * @author 038590-foss-wanghui
	 * @date 2012-12-24 下午8:47:55
	 */
	List<QueryMoneyDto> queryRecieveMoney(
			QueryMoneyConditionDto queryMoneyConditionDto);

	/**
	 * 
	 * 查询发货金额
	 * 
	 * @param
	 * @author 038590-foss-wanghui
	 * @date 2012-12-24 下午8:47:58
	 */
	List<QueryMoneyDto> queryDeliveryMoney(
			QueryMoneyConditionDto queryMoneyConditionDto);

	/**
	 * 
	 * <p>
	 * 判断是否驻地营业部
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-12-27 下午4:25:40
	 * @param saleCode
	 * @return
	 * @see
	 */
	boolean identityTransSale(String saleCode);

	/**
	 * 根据gis网点匹配条件查询网点code
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 上午8:35:53
	 */
	List<GisDepartmentDto> queryPickupOrgCodeByGis(GisPickupOrgDto dto);

	/**
	 * 根据运单id查询运单
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-29
	 * @param id
	 * @return WaybillEntity
	 */
	WaybillEntity queryWaybillById(String id);

	/**
	 * 通过调用GIS的特殊地址查询接口获取地址类型
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-4 下午4:53:17
	 */
	List<String> querySpecialAddressByGis(GisPickupOrgDto dto);

	/**
	 * 
	 * 自有司机通过司机工号判断是否存在
	 * 
	 * @author foss-sunrui
	 * @date 2013-3-28 下午3:42:17
	 * @param driverCode
	 * @return
	 * @see
	 */
	boolean isOwnDriverExists(String driverCode);

	/**
	 * 根据运单号判断该PDA_PENDING状态的运单是否存在
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-1 下午2:46:32
	 */
	boolean isWayBillExsitsPDAPending(String waybillNo);

	/**
	 * 
	 * 获取库区信息
	 * 
	 * @author foss-sunrui
	 * @date 2013-4-2 下午1:52:20
	 * @param sourceCode
	 * @param targetCode
	 * @param productCode
	 * @return
	 * @see
	 */
	GoodsAreaEntity queryGoodsAreaByArriveRegionCode(String sourceCode,
			String targetCode, String productCode);

	/**
	 * 是否存在id为传入值 并且active=y的运单
	 * 
	 * @param oldVersionWaybillId
	 * @return
	 */
	boolean existsActiveWaybillById(String id);

	/**
	 * 根据运单号查询费用明细中的其它费用
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-15 下午3:01:04
	 */
	List<WaybillOtherChargeDto> queryOtherChargeByNo(String waybillNo);

	/**
	 * 
	 * 查询系统上线日期
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-11 下午7:22:10
	 */
	Date queryFossGoliveDate();

	/**
	 * 
	 * 应用监控数据添加
	 * 
	 * @author foss-sunrui
	 * @date 2013-4-15 下午3:19:04
	 * @param waybillDto
	 * @see
	 */
	void businessMonitor(WaybillDto waybillDto);

	/**
	 * 
	 * @Description: 计算物流费用 Company:IBM
	 * @author FOSSDP-sz
	 * @date 2013-1-14 下午3:05:21
	 * @param billCalculateDto
	 * @return
	 * @version V1.0
	 */
	List<GuiResultBillCalculateDto> queryGuiBillPrice(
			GuiQueryBillCalculateDto billCalculateDto);

	/**
	 * 通过订单编号查询运单
	 * 
	 * @param orderNo
	 */
	WaybillEntity queryWaybillByOrderNo(String orderNo);

	/**
	 * 
	 * <p>
	 * 通过订单编号判断运单是否存在 同时返回运单号
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-11-14 下午2:49:06
	 * @param orderNo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#isWayBillExsitsByOrderNo(java.lang.String)
	 */
	String getWayBillNoExsitsByOrderNo(String orderNo);

	/**
	 * 
	 * 通过外场编码查询外场
	 * 
	 * @author foss-sunrui
	 * @date 2013-5-21 下午3:09:39
	 * @param code
	 * @return
	 * @see
	 */
	OutfieldEntity queryOutfieldByCode(String code);

	/**
	 * 根据运单号查询ActualFreightEntity对象
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-29 下午4:42:19
	 */
	ActualFreightEntity queryActualFreightByNo(String waybillNo);

	/**
	 * 根据封装的DTO查询运单基本和冗余信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013年5月30日 上午10:43:05
	 */
	List<WaybillDto> queryActualFreightAndBasic(WaybillDto waybillDto);

	List<WaybillDto> queryActualFreightAndBasicNoExpress(WaybillDto waybillDto);

	List<WaybillEntity> queryWaybillNoExpress(WaybillDto waybillDto);

	/**
	 * 根据外场组织编码查驻地营业部
	 * 
	 * @author sunrui
	 * @date 2013年5月30日 上午10:43:05
	 */
	String queryDefaultSalesDeptByTransCenter(String saleCode);

	/**
	 * 根据集中开单组编码查所属外场组织编码
	 * 
	 * @author sunrui
	 * @date 2013年5月30日 上午10:43:05
	 */
	String queryTransCenterByBillingGroupCode(String billingGroupCode);

	/**
	 * 查询营业部
	 * 
	 * @param dto
	 * @return
	 */
	List<SaleDepartmentEntity> queryByDepartmentInfo(QueryPickupPointDto dto);

	/**
	 * 查询外发网点
	 * 
	 * @param dto
	 * @return
	 */
	List<OuterBranchEntity> queryOuterBranchs(QueryPickupPointDto dto);

	/**
	 * 获得服务器时间
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-6-13 下午3:44:42
	 */
	Date gainServerTime();

	/**
	 * 判断产品时效是否存在
	 * 
	 * @author WangQianJin
	 * @date 2013-6-26 下午3:55:19
	 */
	boolean identityEffectivePlan(String departDeptCode, String arriveDetpCode,
			String productCode, Date createTime);

	/**
	 * 集中开单查开单组所属中转场的默认出发部门
	 * 
	 * @author WangQianJin
	 * @date 2013-6-26 下午3:55:19
	 */
	SaleDepartmentEntity queryPickupCentralizedDeptCode(
			String billingGroupOrgCode);

	/**
	 * 
	 * 根据代理网点编码查询代理网点信息
	 * 
	 * @author WangQianJin
	 * @date 2013-6-27
	 */
	OuterBranchEntity queryAgencyBranchInfo(String agencyBranchCode);

	/**
	 * 
	 * 打标签记录日志
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-7-1
	 */
	void addPrintLabelInfo(GUIPrintLabelDto printLabelDto);

	/**
	 * 
	 * 查询人民币根据目标币种转换后的金额
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-7-15
	 */
	BigDecimal getExchangedFee(BigDecimal rmbFee, String currencyCode,
			Date billTime);

	/**
	 * 
	 * 获得指定币种汇率
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-7-15
	 */
	BigDecimal getExchangedFeeRate(String currencyCode, Date billTime);

	/**
	 * 根据条件查询营业部信息
	 * 
	 * @author WangQianJin
	 * @date 2013-7-19 上午11:15:54
	 */
	SaleDepartmentEntity queryDepartmentInfoByDto(QueryPickupPointDto dto2);

	/**
	 * 根据条件查询偏线信息
	 * 
	 * @author WangQianJin
	 * @date 2013-7-19 下午1:52:21
	 */
	OuterBranchEntity queryOuterBranchByDto(QueryPickupPointDto dto2);

	/**
	 * 
	 * 根据运单号集合查询未补录的运单
	 * 
	 * @author foss-meiying
	 * @date 2013-7-19
	 */
	List<String> queryWaybillNoMakeupByWaybillNo(WaybillQueryInfoDto dto);

	/**
	 * 
	 * 根据运单号查询运单状态
	 * 
	 * @author foss-panguoyang
	 * @date 2013-7-23
	 */

	String getWaybillStatus(String waybillNo);

	/**
	 * 通过词条代码查询
	 * 
	 * @author WangQianJin
	 * @date 2013-7-23 上午11:28:25
	 */
	List<DataDictionaryValueEntity> queryByTermsCode(String termsCode);

	/**
	 * 通过物品的数量，体积，保价金额判断是否属于贵重物品
	 * 
	 * @author WangQianJin
	 * @date 2013-7-24 上午9:57:44
	 */
	boolean isValueGoods(int goodsNum, String goodsVolume, String goodsValue);

	/**
	 * 通过配置项标示查询系统配置,根据confcode和orgcode一起查询
	 * 
	 * @author WangQianJin
	 * @date 2013-7-24 上午10:08:54
	 */
	ConfigurationParamsEntity queryByConfCode(String confCode, String orgCode);

	/**
	 * 通过配置项集合查询系统配置
	 * 
	 * @author WangQianJin
	 * @date 2013-7-24 上午10:18:11
	 */
	List<ConfigurationParamsEntity> queryByConfCodeArray(String[] confCodes,
			String orgCode);

	/**
	 * 
	 * 查询网点自提区域信息
	 * 
	 * @author WangQianJin
	 * @date 2013-7-24
	 */
	List<String> queryByCodeAndPickup(String code);

	/**
	 * 
	 * 查询网点自提区域信息
	 * 
	 * @author WangQianJin
	 * @date 2013-7-24
	 */
	List<String> queryByCodeAndDelivery(String code);

	/**
	 * 查询自有网点
	 * 
	 * @author WangQianJin
	 * @date 2013-7-24 下午9:10:10
	 */
	List<BranchQueryVo> queryListByDepartment(SaleDepartmentEntity entity);

	/**
	 * 查询能开精准大票的自有网点
	 */
	List<BranchQueryVo> queryListByDepartmentAndPro(SaleDepartmentEntity entity);

	/**
	 * 查询汽运偏线、空运网点
	 * 
	 * @author WangQianJin
	 * @date 2013-7-24 下午9:10:10
	 */
	List<BranchQueryVo> queryListByBranch(OuterBranchEntity entity);

	/**
	 * 处理PDA补录单的待办信息
	 * 
	 * @author WangQianJin
	 * @date 2013-7-26 下午5:00:39
	 */
	void handlePDAPendingTODO(WaybillDto waybillDto,
			WaybillPendingEntity waybillPendingEntity);

	SaleDepartmentEntity queryPickupCentralizedDeptCodeAndBillTime(
			String billingGroupOrgCode, Date billTime);

	/**
	 * 
	 * 根据部门Code和所属集中开单组查询
	 * 
	 * @author WangQianJin
	 * @date 2013-08-02
	 */
	List<SalesBillingGroupEntity> querySalesBillGroupByCodeAndBillCode(
			String code, String billingGroup);

	/**
	 * 
	 * 更新锁屏信息
	 * 
	 * @author panguoyang
	 * @date 2013-08-07
	 */
	List<OrderLockResult> crmsyncorderLock(List<OrderLockEntity> records);

	/**
	 * 根据部门名称和所属集中开单组查询
	 * 
	 * @author WangQianJin
	 * @date 2013-8-11 下午3:32:15
	 */
	List<SaleDepartmentEntity> querySaleDepartmentByNameForCentralized(
			String name, String billingGroup);

	/**
	 * 
	 * 获取集中开单组的外场
	 * 
	 * @author 025000-FOSS-panguoyang
	 * @date 2013-8-19 下午06:17:06
	 */
	SaleDepartmentEntity queryPickupCentralizedDeptCodeByBillTime(
			String billingGroupOrgCode, Date billTime);

	/**
	 * 按单号且创建时间升序查询运单
	 * 
	 * @param orderNo
	 */
	WaybillEntity queryWaybillNo(String waybillNo);

	/**
	 * 获取整车的保费相关信息
	 * 
	 * @param billCalculateDto
	 * @return
	 */
	public GuiResultBillCalculateDto getProductPriceDtoOfWVHAndBF(
			GuiQueryBillCalculateDto billCalculateDto);

	boolean isWayBillExsitsOnOnlineAndOffline(String waybillNo);

	boolean isPendingExsits(String mixNo);

	/**
	 * 根据产品编码查询产品信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-12 上午9:56:17
	 */
	ProductEntity queryProductByCode(String code);

	/**
	 * 查询快递价格
	 * 
	 * @param billCalculateDto
	 * @return
	 */
	List<GuiResultBillCalculateDto> queryGuiExpressBillPrice(
			GuiQueryBillCalculateDto billCalculateDto);

	/**
	 * 根据网点编码查询是快递代理理网点信息
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-20 上午11:47:58
	 */
	OuterBranchExpressEntity queryLdpAgencyDeptByCode(String deptCode);

	WaybillDto queryWaybillByReturnBillNo(String waybillNo);

	List<SaleDepartmentEntity> querySaleDeptsByBillingGroupCode(
			String billGroupCode);

	boolean queryIsExpressBill(String waybillNo, String productCode);

	List<WaybillDto> queryActualFreightAndBasicExpress(WaybillDto waybillDto);

	/**
	 * 获得最终外场
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-11-8 下午5:53:04
	 */
	String queryLastOutFieldCode(WaybillEntity waybillEntity);

	/**
	 * 获取刚提交运单时的那条运单信息 WangQianJin
	 * 
	 * @param waybillNo
	 */
	WaybillEntity queryWaybillForFirst(String waybillNo);

	/**
	 * 查询是否为第一票运单信息 MANA-257接货费优化
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2014-2-28 上午11:07:04
	 */
	boolean isFirstDeliveryWaybill(WaybillEntity entity);

	/**
	 * 根据运单号查询是否有运单状态信息
	 * 
	 * @author WangQianJin
	 * @param waybillTransactionEntity
	 * @return
	 */
	boolean isExistWaybillTransaction(String waybillNo);

	/**
	 * <p>
	 * 根据派送单ID查询运单详情
	 * </p>
	 * 
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-2-12 19:35:25
	 */
	List<WaybillEntity> queryWaybillEntityByDeliverbillById(String deliverBillId);

	/**
	 * <p>
	 * 根据交接单No查询运单详情
	 * </p>
	 * 
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-2-12 19:35:25
	 */
	List<WaybillEntity> queryWaybillEntityByHandoverBillNo(String handoverBillNo);

	/**
	 * 根据CRM请求返回运单及营销活动信息
	 * 
	 * @创建时间 2014-4-14 下午7:44:15
	 * @创建人： WangQianJin
	 */
/*	FossQueryAcctinfoResponse fossQueryAcctinfo(FossQueryAcctinfoRequest payload);
*/
	/**
	 * 获取CRM营销活动信息
	 * 
	 * @创建时间 2014-4-16 下午7:46:54
	 * @创建人： WangQianJin
	 */
	CrmActiveInfoDto getActiveInfoList(CrmActiveParamVo pdaParamDto);

	/**
	 * 获取营销活动折扣信息
	 * 
	 * @创建时间 2014-4-22 上午10:51:48
	 * @创建人： WangQianJin
	 */
	MarkActivitiesQueryConditionDto getActiveDiscountInfo(
			MarkActivitiesQueryConditionDto entity);

	/**
	 * 根据客户编码获取客户信息
	 * 
	 * @创建时间 2014-4-22 下午1:24:08
	 * @创建人： WangQianJin
	 */
	CustomerDto queryCustInfoByCode(String custCode);

	/**
	 * 根据运单号和类型查询CRM营销活动
	 * 
	 * @创建时间 2014-4-22 下午8:34:33
	 * @创建人： WangQianJin
	 */
	WaybillDisDtlEntity queryActiveInfoByNoAndType(String waybillNo);

	/**
	 * 根据运单号和类型查询CRM营销活动
	 * 
	 * @创建时间 2014-4-22 下午8:34:33
	 * @创建人： WangQianJin
	 */
	WaybillDisDtlPendingEntity queryPendingActiveInfoByNoAndType(
			String waybillNo);

	/**
	 * 获取CRM营销活动信息(不包含行业相关的营销活动)
	 * 
	 * @创建时间 2014-4-16 下午7:46:54
	 * @创建人： WangQianJin
	 */
	CrmActiveInfoDto getActiveInfoListNoIndurstry(CrmActiveParamVo paramDto);

	/**
	 * 新增运单折扣明细
	 * 
	 * @创建时间 2014-5-8 下午8:33:14
	 * @创建人： WangQianJin
	 */
	int insertWaybillDisDtl(WaybillDisDtlEntity record);

	/**
	 * 插入部分对象数据
	 * 
	 * @author WangQianJin
	 * @date 2014-05-21
	 */
	int insertSelective(WaybillDisDtlEntity record);

	ResultDto createNotAllPackageTodo(NeedCreateTodoDto needCreateTodoDto);

	/**
	 * 查询该发货客户当天是否收取过接货费
	 * 
	 * @author WangQianJin
	 * @date 2014-05-04
	 */
	boolean queryIsGetPickFeeByCust(WaybillEntity entity);

	boolean identityOuterEffectivePlan(String departDeptCode,
			String arriveDetpCode, String productCode, Date createTime);

	/**
	 * 根据部门编码与运输性质获取信息数量
	 * 
	 * @param entity
	 * @author WangQianJin
	 * @return
	 */
	int queryCountByCodeAndProduct(SalesProductEntity entity);

	/**
	 * 根据运单号与类型修改折扣信息状态
	 * 
	 * @创建时间 2014-5-21 下午6:18:29
	 * @创建人： WangQianJin
	 */
	int updateInactiveByWaybillNo(String waybillNo);

	/**
	 * 根据活动编码获取活动信息
	 * 
	 * @创建时间 2014-6-12 上午10:09:51
	 * @创建人： WangQianJin
	 */
	MarkActivitiesEntity queryMarkActivitiesByCode(String activityCode);

	/**
	 * @author liyongfei 根据运单号查询运单的操作历史
	 * @param waybillNo
	 *            运单号
	 * @param actionItem
	 *            指定的修改项集合，如goodsQty 表示查询货物件数，licensePlateNum表示车牌号，若为空，则表示全查
	 * @return
	 */
	List<WaybillAcHisPdaEntity> queryWaybillHisByNo(String waybillNo,
			List<String> actionItem);

	/**
	 * 添加实际承运信息
	 * 
	 * @创建时间 2014-9-10 上午10:09:51
	 * @创建人： BaiLei
	 */
	void addActualFreightInfo(WaybillDto waybillDto,
			WaybillSystemDto systemDto, boolean isPdaPending);

	/**
	 * 降价发券用户需求：判断是否需要返券以及返券的逻辑处理
	 * 
	 * @param queryBillCacilateDto
	 *            计算价格实体
	 * @param newWaybillDto
	 *            现运单实体
	 * @param oldWaybillDto
	 *            原运单实体 --针对更改单处理
	 * @param currentInfo
	 *            当前用户 ---针对更改单处理
	 * @param isBill
	 *            开单与否
	 * @param isChangeWaybillNo
	 *            是否改变运单号----针对更改单处理
	 * 
	 * @author Foss-206860
	 * */
	void savePendingSendCoupon(QueryBillCacilateDto queryBillCacilateDto,
			WaybillDto newWaybillDto, WaybillDto oldWaybillDto,
			CurrentInfo currentInfo, String isBill, String isChangeWaybillNo);

	/**
	 * 插入图片开单信息 submitWaybillPictureByPDA: <br/>
	 * 
	 * Date:2014-10-10下午3:39:13
	 * 
	 * @author 157229-zxy
	 * @param waybillPicturePdaDto
	 * @since JDK 1.6
	 */
	void submitWaybillPictureByPDA(WaybillPictureEntity waybillPictureEntity);

	/**
	 * 批量激活运单
	 * 
	 * @author liyongfei--DMANA-2035
	 * @param waybillNoList
	 *            运单号集合
	 * @return
	 */
	int setWaybillActive(List<String> waybillNoList);

	/**
	 * 
	 * 
	 * @param waybillNos
	 * @return
	 * @throws Exception 
	 */
	public List<BillBadAccountEntity> queryByWaybillNOs(List<String> waybillNos);

	/**
	 * 查询返货类型
	 * 
	 * @param waybillno
	 * @return
	 */
	String selectReturnType(String waybillno);

	/**
	 * 根据当前时间查询当前月份所有内部员工发货的优惠金额 dp-foss-dongjialing 225131
	 */
	BigDecimal queryDiscountFeeByEmployeeNo(String employeeNo, Date recevieDate);

	/**
	 * 为配合快递100轨迹推送所需的方法
	 * 
	 * @author 220125 yangxiaolong
	 */
	public void addOneWaybillTrack(WaybillDto waybilldto);

	BigDecimal queryTotalFeeByDelevyCode(String code, Date recevieDate);

	BigDecimal queryDiscountInfo(String waybillNo);

	void updateWaybill(WaybillEntity waybillEntity);

	/**
	 * 根据客户编码查询客户信息
	 * 
	 * @param customerEntity
	 * @return
	 */
	CustomerEntity queryCustInfoByCustomerEntity(CustomerEntity customerEntity);

	/**
	 * 根据Code查找数据字典
	 * 
	 * @param termsCode
	 * @param valueCode
	 * @return
	 */
	DataDictionaryValueEntity queryDataDictoryValueByCode(String termsCode,
			String valueCode);

	/**
	 * 获取地址信息
	 */
	public String getReceivingAddress(String code);

	int updateRfcTranferEntity(WaybillRfcTranferEntity rfcTranfer);
	int updateRfcTranferRfcId(WaybillRfcTranferEntity waybillRfcTranferEntity);
	/**
	 * 获取运单信息组装成map
	 */
	
	public Map<String,WaybillMessageEntity> getWaybillEntityMessage(String[] waybillNo);
	

	/**
	 * 提交快递自动补录运单
	 * @author foss-yangxiaolong
	 * @param waybill
	 * @return Result
	 */
	void submitExpressWaybill(WaybillDto waybill);
	
	/**
	 * 通过调用GIS的详细地址匹配接口查询是否展会货
	 * @author foss-218438
	 */
	String isExhibitCargo(ExhibitionKeywordEntity exhibitionKeyword);
	/**
	 * 根据部门名称和所属集中开单组查询
	 * @param name 要进行模糊查询的部门名称
	 * @param billingGroup 当前开组编号
	 * @param waybillNo 运单号
	 * @return 收货部门实体
	 * @author 272311
	 * @date 2015.10.13
	 */
	List<SaleDepartmentEntity> querySaleDepartmentByNameForCentralizedexp(
			String name, String billingGroup, String waybillNo);
	
	/**
	 * 根据提货网点编码查询对应提货网点名称
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-14 20:11:54
	 * @param customerPickUpCode
	 * @return
	 */
	String getCustomerPickUpNameByCode(String customerPickUpCode, String productCode, Date billTime);


		
	/**
	 * 根据参数查询是否是子母件以及其子母件信息
	 * 
	 * @author foss-206860
	 * */
	TwoInOneWaybillDto queryWaybillRelateByWaybillOrOrderNo(
			String waybillNo);
	/**
	 * 根据运单号查询是否在快递丢货管理组
	 * @author 218459-foss-dongsiwei
	 */
	public QmsYchExceptionReportEntity isLoseGroup(String waybillNo);
	
	/**
	 * 根据运单号、流水号、部门编码、以及货区编码查询该单号是否在提供的部门库存内
	 */
	public StockEntity queryStockEntityByNos(String orgCode, String waybillNo,
			String serialNO, String goodsAreaCode);
	
	//根据部门编码在线查询营业部信息
	public SaleDepartmentEntity querySaleDepartmentByCode(String code);
	
	
	/**
	 * 根据子母运单号集合获取运单信息
	 * @author 270293-foss-zhangfeng
	 * @date 2015-10-29下午17:17:02
	 */
	List<WaybillEntity> queryWaybillListWayBillNo(List<String> wayBillNoList);
	
	
	/**
	 * 根据代理网点编码集合返回机构对应的代理网点名称
	 * @author Foss-278328-hujinyang
	 * @date 2015-12-16
	 * @param orgs
	 * @return
	 */
	Map<String,String> queryAgentNameMapsByAgentCode(List<String> orgs);
	/**
	 *
	 * @author Foss-278328-hujinyang
	 * @date 2015-12-16
	 * @param orgs
	 * @return
	 */
	public List<WaybillEntity> queryWaybillForPrint(WaybillDto waybillDto);

	String queryLastOutFieldCode(WaybillPendingEntity waybillEntity);
	
	//通过运单编号查询合伙人表中的运单信息 2016年3月15日 19:49:02 葛亮亮
	PartnersWaybillEntity queryPartnerWaybillEntityByWaybillId(String waybillNoId);
	
	public String queryPickupGoodsByWaybillNo(String waybillNo);
	
	/**
	 * 
	 * <p>
	 * 通过运单号获取运单信息
	 * </p>
	 * 
	 * @author foss-zhuxue
	 * 280747
	 * @param waybillNo
	 * @return
	 * @see
	 */
	WaybillEntity queryNewWaybillBasicByNo(String waybillNo);
	
	/**
	 * FOSS校验悟空快递单号
	 * @param map
	 * @return
	 * @author 272311- sangwenhao
	 */
	boolean validateWaybillNoIsCorrect(Map<String, String> map) ;
	/**
	 * 菜鸟运单作废、中止完整率优化
	 * @param oldVersionDto
	 * @return
	 * @author 265475- zoushengli
	 */
	 void addSynTracks(WaybillDto oldVersionDto);
		
	/**
	 * 提交电子运单接口
	 * @param waybillDto
	 */
	public void submitLTLEWaybill(final WaybillDto waybillDto);

	
	/**
	 * pda运单补录处理加收费
	 * @author Foss-351326-xingjun 
	 * @date 2016-9-1 上午10:22:19
	 * @param waybillDto
	 * @see
	 */
	public void pdaWaybillReplenish(WaybillDto waybillDto);
	
	/**
	 * 合伙人计算加收费
	 * @author Foss-351326-xingjun 
	 * @date 2016-9-1 上午10:41:37
	 * @param targetOrgCode
	 * @param billTime
	 * @param billingtype
	 * @param volume
	 * @param weight
	 * @param createOrChange
	 * @return
	 * @see
	 */
	public AddedPlanFeeCalculateDto caculateAddFee(String targetOrgCode,Date billTime,
			String billingtype,BigDecimal volume,BigDecimal weight,String createOrChange);
	/**
	 * 能否欠款
	 *
	 */
	DebitForCUBCDto isBeBebtForCUBC(String customerCode, String orgCode,
									String debtType, BigDecimal debtAmount,String waybillNo);

	/**
	 * 根据单号判断是否是合伙人开单
	 * 280747
	 * @param WaybillNo
	 * @return
	 */
	String partnerWaybillForPrint(String waybillNo);
	
	
	/**
	 * 根据条件查询推送CUBC接口日志
	 * @param queryDto
	 * @return
	 */
	List<WaybillLogEntity> queryLogEntityByCondition(
			WaybillLogEntityQueryDto queryDto);
	
	/**
	 * 重新推送运单信息至CUBC
	 * @param cubcLogEntitys
	 */
	void pushWaybillToCUBC(List<WaybillLogEntity> cubcLogEntitys);
	/**
	 * 根据gis网点匹配条件查询目的网点信息
	 * 
	 * @author 321993 zhangdinahao
	 * @date 2017-03-16 下午 21:35:53
	 */
	HisSegMatchResponse queryStationInfo(HisSegMatchRequest request);

	FossQueryAcctinfoResponse fossQueryAcctinfo(FossQueryAcctinfoRequest payload);

}