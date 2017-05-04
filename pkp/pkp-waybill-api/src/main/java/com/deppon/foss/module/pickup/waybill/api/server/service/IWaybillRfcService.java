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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/IWaybillRfcService.java
 * 
 * FILE NAME        	: IWaybillRfcService.java
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

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.AdjustPlanResultDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.ChangeNodeDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.OrgAdministrativeInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodTodoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PrintInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcForAccountServiceCondition;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcTranferEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpBatchChangeWeightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpBatchChangeWeightQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ModifyBillWriteoffDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ModifyBillWriteoffResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PDAWayBillRfcDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaReceiveGoodsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillLabeledGoodStockDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcForAccountServiceDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcImportDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcPrintDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcSubmitDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillStockStatusDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillRfcException;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.HandOverAndUnloadDto;

/**
 * 
 * 更改单服务接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:102246-foss-shaohongliang,date:2012-10-25
 * 上午10:33:18,content:
 * </p>
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-25 上午10:33:18
 * @since
 * @version
 */
public interface IWaybillRfcService extends IService {
	
	/**
	 * 是否已经结清货款（部分结清同样是true）
	 * @param waybillNo
	 * @return
	 */
	public boolean isExistRepayment(String waybillNo);

	/**
	 * 
	 * <p>
	 * 根据运单号导入运单
	 * </p>
	 * 
	 * @author 265475-foss-邹胜利
	 * @date 2016-1-18上午11:00:07
	 * @param waybillNo
	 * @param deptCode 
	 * @return
	 * @throws BusinessException
	 * @see
	 */
	WaybillRfcImportDto importWaybillBy(String waybillNo, String deptCode);
	
	/**
	 * 
	 * <p>
	 * 根据运单号导入运单
	 * </p>
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-25 上午11:00:07
	 * @param waybillNo
	 * @param deptCode 
	 * @return
	 * @throws BusinessException
	 * @see
	 */
	WaybillRfcImportDto importWaybillByNumber(String waybillNo, String deptCode);

	/**
	 * 
	 * 传入更改单ID，将更改单的状态改为 核销通过、核销不通过状态
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-30 下午2:20:22
	 */
	void writeOffWaybillChange(List<String> waybillChangeIds,
			String notes, String writeoffStatus,CurrentInfo cInfo)throws WaybillRfcException;

	/**
	 * 
	 * 传入更改单ID，将核销通过、核销不通过更改单设置为未核销状态
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-30 下午2:20:07
	 */
	void reverseWaybillChange(List<String> waybillChangeIds, String notes,CurrentInfo cInfo)throws WaybillRfcException;

	/**
	 * 传入运单号，判断传入的运单号是否存在未处理的更改单
	 * 
	 * @param waybillNo
	 * @return
	 */
	Boolean isExsitsWayBillRfc(String waybillNo)throws WaybillRfcException;

	/**
	 * 传入运单号或者运单号数组集合，判断传入的运单号是否存在未处理的更改单
	 * 
	 * @param waybillNo
	 * @return
	 */
	List<String> isExsitsWayBillRfcs(List<String> waybillNoList)throws WaybillRfcException;

	/**
	 * 查询根据传入运单号等条件，获取财务类更改单的输入条件有（更改单受理开始日期、结束日期、核销状态、起草部门、大区、小区、运单单号）
	 * 核销状态有：全部、核销通过、核销不通过、未核销
	 * 
	 * @param modifyBillWriteoffDto
	 * @return
	 */
	List<ModifyBillWriteoffResultDto> queryModifyBillWriteoffResult(
			ModifyBillWriteoffDto modifyBillWriteoffDto,int start, int limit);

	
	
	
	/**
	 * 导出查询根据传入运单号等条件，获取财务类更改单的输入条件有（更改单受理开始日期、结束日期、核销状态、起草部门、大区、小区、运单单号）
	 * 核销状态有：全部、核销通过、核销不通过、未核销
	 * 
	 * @param modifyBillWriteoffDto
	 * @return
	 */
	List<ModifyBillWriteoffResultDto> queryModifyBillWriteoffResult(
			ModifyBillWriteoffDto modifyBillWriteoffDto);
	
	
	
	/**
	 * 
	 * 根据运单号查询交接单明细
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-9 下午4:12:22
	 */

	List<HandOverAndUnloadDto> queryHandoverDetail(String waybillNo);
	
	/**
	 * 更新在线更改单打印次数 +1
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-15 下午11:48:25
	 * @param waybillNo
	 * @return
	 * @see
	 */
	int updateWaybillRfcPrintTimes(String waybillNo);

	/**
	 * 根据运单ID 查询更改单实体
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-18 下午2:49:01
	 * @param id
	 * @return
	 * @see
	 */
	WaybillRfcEntity queryRfcEntityByWaybillId(String id);

	/**
	 * 
	 * <p>根据更改单ID 查询更改单实体</p> 
	 * @author foss-gengzhe
	 * @date 2012-11-29 下午9:33:29
	 * @param id
	 * @return
	 * @see
	 */
	WaybillRfcEntity selectByPrimaryKey(String id);
	
	/**
	 * 
	 * 更改单提交
	 * @author 102246-foss-shaohongliang
	 * @param currentInfo 
	 * @date 2012-11-19 下午2:11:12
	 */
	WaybillRfcEntity commitWaybillRfc(WaybillRfcSubmitDto submitDto, CurrentInfo currentInfo);

    /**
     * 
     * 查询运单库存状态
     * @author 102246-foss-shaohongliang
     * @date 2012-11-27 下午8:30:42
     */
	WaybillStockStatusDto queryWaybillStockStatus(String waybillNo, WaybillEntity dto);

	/**
	 * 
	 * 查询运单所有货签库存状态
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-7 下午2:19:08
	 */
	WaybillLabeledGoodStockDto queryWaybillLabeledGoodStock(String waybillNo);
	
	
	/**
	 * 
	 * 查询未审核总条数
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-7 上午9:25:41
	 */
	long queryModifyBillWriteoffResultTotalNumber(ModifyBillWriteoffDto modifyBillWriteoffDto);
	
	
	/**
	 * 装车环节查询该单在当前部门是否有待办
	 * @author 043260-foss-suyujun
	 * @date 2012-12-11
	 * @param waybillNo 运单号
	 * @param serialNo  流水号
	 * @param currentOrgCode 当前部门编码
	 * @return List<String>
	 */
	List<String> queryTodoWhenLoadTruck(String waybillNo,String serialNo,String currentOrgCode);
	
	/**
	 * 强卸的时候，检查上个部门是否有该票的更改代办事项
	 * @author 043260-foss-suyujun
	 * @date 2012-12-11
	 * @param waybillNo
	 * @param serialNo
	 * @param currentOrgCode
	 * @param lastOrgCode
	 * @return List<String>
	 */
	List<String>  queryTodoWhenDumpTruck(String waybillNo,String serialNo,String currentOrgCode,String lastOrgCode);
	
	/**
	 * 传入日期、司机工号、车牌号，查询PDA接货记录
	 * @author 043260-foss-suyujun
	 * @date 2012-12-12
	 * @param queryDate
	 * @param vehicleNo
	 * @param driverCode
	 * @return
	 * @return PdaReceiveGoodsDto
	 * @see
	 */
	List<PdaReceiveGoodsDto> queryPdaReceiveGoodsDto(Date queryDate,String driverCode);
	
	/**
	 * 
	 * <p>是否自动受理</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-20 上午11:20:16
	 * @param waybillRfcNo
	 * @see
	 */
	void updateIsLabourHandle(String waybillRfcNo,String isLabourHandle);
	
	/**
	 * 
	 * 查询更改明细
	 * 
	 * @date 2012-11-19 下午2:18:01
	 */
	List<WaybillRfcChangeDetailEntity> queryRfcChangeDetail(String waybillRfcId);
	
	/**
	 * 
	 * 查询变更节点
	 * 
	 * @date 2012-11-19 下午2:18:01
	 */
	List<LabeledGoodTodoEntity> queryTodoByWaybillRfcId(String waybillRfcId); 
	
	/**
	 * 
	 * 查询变更节点
	 * 
	 * @param waybillRfcId
	 * @param waybillNo
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 17, 2013 2:51:29 PM
	 */
	List<ChangeNodeDto> queryTodoByWaybillRfcInfo(String waybillRfcId, AdjustPlanResultDto adjustPlanResultDto); 
	/**
	 * 
	 * <p>取库存部门，下一库存部门</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-14 上午11:25:57
	 * @param waybillRfc
	 * @param labeledGoodEntity
	 * @param orgCode
	 * @return
	 * @see
	 */
	 String getOrgCodeStr(String waybillNo, String serialNo);
	 
	 /** 
		 * 查询货物全部路径
		 * @author huyue
		 * @date 2012-12-6 下午4:12:20
		 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService#queryTotalPath(java.lang.String, java.lang.String)
	 */
	 String queryFreightRoute(String waybillNo,String serialNo);
	 
	 /** 
		 * 取执行节点列表
		 * @author foss-yangtong
		 * @date 2012-12-6 下午4:12:20
		 * @see 
	   */
	@SuppressWarnings("rawtypes")
	List<OrgAdministrativeInfoDto> getExeNodes(List list);
	
	
	/**
	 * 
	 * 批量修改变更节点
	 * 
	 * @date 2012-11-19 下午2:18:01
	 */
	int updateBatchNodes(List<ChangeNodeDto> list);

	/**
     * 
     * 根据Code查询价格DTO
     * @author 102246-foss-shaohongliang
     * @date 2013-1-22 下午2:27:16
     * @see com.deppon.foss.module.pickup.changing.client.service.IWaybillRfcRemotingService#queryValueAddPriceByCode(java.lang.String)
     */
	PriceEntity queryValueAddPriceByCode(String pricingCodeZz);
	
	/**
	 * 
	 * 官网更改单申请
	 * @author foss-gengzhe
	 * @date 2013-1-23 下午3:51:01
	 * @param waybillRfcForAccountServiceDto
	 * @return
	 * @see
	 */
	boolean applyChangeOrder(WaybillRfcForAccountServiceDto waybillRfcForAccountServiceDto);
	
	/**
	 * 
	 * 阿里更改单申请
	 * @author foss-gengzhe
	 * @date 2013-1-23 下午3:51:01
	 * @param waybillRfcForAccountServiceDto
	 * @return
	 * @see
	 */
	Map<String,Object> aLiChangeOrder(WaybillRfcForAccountServiceDto waybillRfcForAccountServiceDto);
	
	/**
	 * 
	 * 官网更改单查询
	 * @author foss-gengzhe
	 * @date 2013-1-23 下午3:51:07
	 * @param waybillRfcForAccountServiceCondition
	 * @return
	 * @see
	 */
	List<WaybillRfcForAccountServiceDto> queryChangeOrder(WaybillRfcForAccountServiceCondition waybillRfcForAccountServiceCondition);
	
	/**
	 * 
	 * 分页查询更改单
	 * 
	 * @param waybillRfcForAccountServiceCondition
	 * @param start
	 * @param limit
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 29, 2013 10:11:21 AM
	 */
	List<WaybillRfcForAccountServiceDto> queryChangeOrderList(WaybillRfcForAccountServiceCondition waybillRfcForAccountServiceCondition, int start, int limit);

	/**
	 * 
	 * 查询更改单的记录总数
	 * 
	 * @param waybillRfcForAccountServiceCondition
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 29, 2013 10:11:55 AM
	 */
	Long queryChangeOrderCount(WaybillRfcForAccountServiceCondition waybillRfcForAccountServiceCondition);

	/**
	 * 
	 * 更新更改单处理状态
	 * 
	 * @param ids
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 29, 2013 10:12:17 AM
	 */
	int updateWaybillRfcIds(String ids);
	
	/**
	 * 根据更改单ID 查询更改单打印信息
	 * @author 097972-foss-dengtingting
	 * @date 2013-4-9 下午4:02:12
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcVarificationDao#queryWaybillRfcPrintDtoByRfcid(java.lang.String)
	 */
	WaybillRfcPrintDto queryWaybillRfcPrintDtoByRfcid(String rfcId);

	/**
     * 
     * 根据代理网点编码查询外发代理网点和合作伙伴（代理公司）信息 
     * @author 102246-foss-shaohongliang
     * @date 2013-4-12 下午4:52:38
     */
	OuterBranchEntity queryAgencyBranchCompanyInfo(String agencyBranchCode);
	
	/**
	 * 
	 *  通过 标识编码查询
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-12 下午4:56:44
	 */
	SaleDepartmentEntity querySaleDepartmentByCode(String customerPickupOrgCode);

	/**
	 * 根据virtual code查询走货路径
	 * @param virtualCode
	 * @return
	 */
	FreightRouteEntity queryFreightRouteByVirtualCode(String virtualCode);
	
	/**<p>通过更改单编号获取该更改单纪录的打印次数</p> 
     * @author foss-sunrui
     * @date 2012-12-5 下午5:11:55
     * @param waybillRFCId
     * @return
     * @see
     */
    int queryPrintTimesByWaybillRFCId(String waybillRFCId,String waybillNo);

    /**
     * 
     * <p>更新记录</p> 
     * @author foss-sunrui
     * @date 2012-12-5 下午4:54:16
     * @param record
     * @return
     * @see
     */
    int updateByPrimaryKeySelective(PrintInfoEntity record);
    
    /**
	 * 
	 * <p>创建新纪录</p> 
	 * @author foss-sunrui
	 * @date 2012-12-5 下午4:53:26
	 * @param record
	 * @return
	 * @see
	 */
    int insertPrintInfoSelective(PrintInfoEntity record);
    
    /**
     * 获取waybillNO
     * @param waybillID
     * @return
     */
    String queryWaybillNO(String waybillID);
    
    boolean isChangeCustomerPickupOrgCode(WaybillEntity waybillEntity);
    
	/**
	 * 判断一个waybillEntity是否有没有自动处理的代办
	 * @param waybillEntity
	 * @return
	 */
	boolean isExistsNotHandleTodo(WaybillEntity waybillEntity, List<String> serialNos);
	
	/**
	 * 通过营业部编码获取运输性质
	 * @author WangQianJin
	 * @date 2013-7-22 下午8:45:47
	 */
	List<ProductEntity> queryTransType(String salesDeptId);
	
	/**
	 * 根据到达部门编码获取运输性质
	 * @author WangQianJin
	 * @date 2013-7-22 下午9:18:41
	 */
	List<ProductEntity> queryByArriveDeptProduct(String arriveDept);
	
	/**
	 * 通过营业部编码获取运输性质
	 * @author WangQianJin
	 * @date 2013-7-22 下午8:45:47
	 */
	List<ProductEntity> queryBySalesDept(String salesDeptId,String productLevel);
			
	/**
	 * 根据到达部门编码获取运输性质
	 * @author WangQianJin
	 * @date 2013-7-22 下午9:18:41
	 */
	List<ProductEntity> queryByArriveDept(String salesDeptId,String productLevel);
	
	/**
	 * 添加PDA更改信息
	 * @author WangQianJin
	 * @date 2013-7-25 下午4:40:25
	 */
	String addPDAPendingForRfcInfo(PDAWayBillRfcDto dto);

	List<String> queryTodoWhenLoad(String waybillNo, String serialNo,
			String currentOrgCode, String lastOrgCode);
			
	/**
	 * 
	 * 快递接口
	 * 查询根据传入运单号等条件，获取财务类更改单的输入条件有（更改单受理开始日期、结束日期、核销状态、起草部门、大区、小区、运单单号、产品集合）
	 * 核销状态有：全部、核销通过、核销不通过、未核销
	 * 备注：查询记录总条数
	 * @author 025000-foss-helong
	 * @date 2013-7-30
	 */
	long queryExpressModifyBillWriteoffResultCount(
			ModifyBillWriteoffDto modifyBillWriteoffDto,int start, int limit);
	
	
	/**
	 * 
	 * 快递接口 ：
	 * 导出查询根据传入运单号等条件，获取财务类更改单的输入条件有（更改单受理开始日期、结束日期、核销状态、起草部门、大区、小区、运单单号）
	 * 核销状态有：全部、核销通过、核销不通过、未核销
	 * @author 025000-foss-helong
	 * @date 2013-7-27
	 */
	List<ModifyBillWriteoffResultDto> queryExpressModifyBillWriteoffResult(
			ModifyBillWriteoffDto modifyBillWriteoffDto);

	/**
	 * 
	 * 快递接口
	 * 查询根据传入运单号等条件，获取财务类更改单的输入条件有（更改单受理开始日期、结束日期、核销状态、起草部门、大区、小区、运单单号、产品集合）
	 * 核销状态有：全部、核销通过、核销不通过、未核销
	 * @author 025000-foss-helong
	 * @date 2013-7-26
	 */
	Map<Long,List<ModifyBillWriteoffResultDto>> queryExpressModifyBillWriteoffResult(ModifyBillWriteoffDto modifyBillWriteoffDto, int start, int limit);

	List<WaybillRfcEntity> queryWaybillRfcAcceptByWaybillNo(String waybillNo);

	/**
	 * 查询到达部门产品
	 * 
	 * @author 076234-FOSS-pgy
	 * @date 2014-3-5
	 */
	public List<ProductEntity> searchByArriveDept(String salesDeptId,
			String productLevel);

	/**
	 * 生成包装信息更改详细信息
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-4-8 18:46:22
	 * @param dto
	 * @return
	 */
	String addPackageRfcInfo(PDAWayBillRfcDto dto);
	/**
	 * 查询运单是否有交接记录
	 * 
	 */
	public List<HandOverBillEntity> queryHandOveredRecordsByWaybillNo(String waybillNo);
	

	/**
	 * 查询运单是否在开单部门有卸车记录
	 *  
	 */
	public boolean queryUnloadTaskReport(String waybillNo);
	
	/**
	 * 功能：查询接货信息
	 * @author 045738-foss-maojianqiang
	 * @date 2014-5-5
	 * @return
	 */
	List<PdaReceiveGoodsDto> queryPdaReceiveGoodsDtoByDate(Date startDate,Date endDate,String driverCode);

	/**
	 * 为中转新增卸车扫描时候待办的提醒，不管是否已经打印，都需要提醒
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-2 17:37:08
	 */
	List<String> queryTodoWhenUnloadTruck(String waybillNo, String serialNo, String currentOrgCode);

	/**
	 * 当卸车少货的时候需要重置待办
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-2 18:58:20
	 * @param waybillNo
	 * @param serialNo
	 * @param currentOrgCode
	 * @return
	 */
	ResultDto resetTodoWhenLost(String waybillNo, String serialNo,String currentOrgCode);

	/**
	 * 当少货找到的时候，不能按照卸车少货的情况去重置，此时你不能根据当前部门去重置
	 * @param waybillNo
	 * @param serialNo
	 * @param currentOrgCode
	 * @return
	 */
	ResultDto resetTodoWhenGoodFound(String waybillNo, String serialNo);

	/**
	 * 功能：查询接货信息（快递）
	 * @author WangQianJin
	 * @date 2014-06-06
	 * @return
	 */
	List<PdaReceiveGoodsDto> queryPdaExpressReceiveGoodsDtoByDate(Date startDate,Date endDate,String driverCode);
	
	/**
	 * 根据运单号List 查询这些运单号集合里面那些更改单申请未受理
	 * @param waybillNoList
	 * @return 未受理更改单对应的运单号集合
	 */
	List<String> queryUnActiveRfcWaybillNo(List<String> waybillNoList);

	/**
	 * 查询批量导入更改的结果
	 * @author 136334-foss-bailei 
	 * @return
	 */
	public List<ExpBatchChangeWeightDto> queryExpBatchChangeWeightResultByCondition(
			ExpBatchChangeWeightQueryDto dto);
	
	/**
	 * 批量修改重量并自动审核通过
	 * @author 136334-foss-bailei 
	 * @return
	 */
	public void batchChangeWeight(
			ExpBatchChangeWeightDto expBatchChangeWeightDto,CurrentInfo currentInfo);

	public void canDebit(String deliveryCustomerCode, String receiveOrgCode);
	
	
	/**
	 * 插入转运或返货目的站记录
	 * 
	 * @author foss-206860
	 */
	void addWaybillRfcTransferEntity(WaybillRfcTranferEntity rfcTranferEntity);

	public List<WaybillRfcTranferEntity> queryWaybillRfcTransferEntity(
			WaybillRfcTranferEntity waybillRfcTranferEntity);

	List<WaybillRfcTranferEntity> queryRfcTransferHistory(
			WaybillRfcTranferEntity waybillRfcTranferEntity);
	
	/**
	 * 查询最新的更改单信息
	 */
	List<WaybillRfcEntity> queryRecentRfc(String waybillNo);

	/**
     * 根据更改单id,查询更改单变更信息
     * @param waybillRfcId
     * @return
     * @author 311417 wangfeng
     * @date 2016-4-22
     */
    public String queryRfcChangeItemsByWaybillRfcId(String waybillRfcId);
    
    /**
     * 查询是否可开装卸费
     * @author 354805
     * @date 2016-12-6 16:27:38
     * @param code 装卸费开单部门
     * @param billTime 开单时间
     * @return 是否可开装卸费
     */
    public String queryCanPayServiceFeeByCodeAndTime(String code, Date billTime );

}