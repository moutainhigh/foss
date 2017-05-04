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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/hessian/WaybillRfcHessianRemoting.java
 * 
 * FILE NAME        	: WaybillRfcHessianRemoting.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.hessian;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.deploy.hessian.annotation.Remote;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerCircleRelationNewService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerCircleNewDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPrintInfoDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOAErrorService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ITodoActionService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IValuateFoss2EcsWaybillNoService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.PrintInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LostRepDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.QueryVirtualResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TodoConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcImportDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcSubmitDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillImportException;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillRfcHessianRemoting;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.packaging.api.server.service.IQueryPackedService;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPackedConditionEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPackedResultEntity;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillService;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.HandOverAndUnloadDto;

/**
 * 
 * 更改单Hessian服务
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:102246-foss-shaohongliang,date:2012-10-25
 * 上午10:59:10,content:TODO
 * </p>
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-25 上午10:59:10
 * @since
 * @version
 */
@Remote
public class WaybillRfcHessianRemoting implements IWaybillRfcHessianRemoting {

	@Resource
	private IWaybillRfcService waybillRfcService;

	@Resource
	private IOAErrorService oaErrorService;

	@Resource
	IPrintInfoDao printInfoDao;

	/**
	 * 业务互斥锁服务 提供业务互斥锁服务接口
	 */
	@Resource
	private IBusinessLockService businessLockService;
	
	/**
	 * 查询待办Service
	 */
	@Resource
	private ITodoActionService todoActionService;
	
	/**
	 * 业务锁定超时自动释放时间:15秒
	 */
	private static final int LOCK_TIMEOUT = 0;
	/**
	 * 运单快递服务接口
	 */
	@Resource
	private IWaybillExpressService waybillExpressService;
	
	@Resource
	private IQueryPackedService queryPackedService;
	@Resource
	private IValuateFoss2EcsWaybillNoService valuateFoss2EcsWaybillNoService;
	@Resource
	private IConfigurationParamsService configurationParamsService;
    @Resource
	private IStockService stockService;
	/**
	 * 是否已经结清货款（部分结清同样是true）
	 */
	@Override
	public boolean isExistRepayment(String waybillNo){
		return waybillRfcService.isExistRepayment(waybillNo);		
	}	
	@Resource
	ICustomerCircleRelationNewService customerCircleRelationNewService;

	/**
	 * 
	 * <p>
	 * 根据运单号导入运单
	 * </p>
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-25 上午11:00:07
	 * @param waybillNo
	 * @return
	 * @throws BusinessException
	 * @see
	 */
	@Override
	public WaybillRfcImportDto importWaybillByNumber(String waybillNo) {
		WaybillRfcImportDto waybillRfcImportDto = null;
		String deptCode = FossUserContext.getCurrentDeptCode();
		MutexElement mutexElement = new MutexElement(waybillNo,
				WaybillConstants.IMPORT_WAYBILL_NUMBER,
				MutexElementType.RFC_IMPORT);// 创建对象
		// 互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, LOCK_TIMEOUT);
		if (!isLocked) {
			throw new WaybillImportException(
					WaybillImportException.WAYBILL_NUMBER_LOCKED);
		}
		try {
			waybillRfcImportDto = waybillRfcService.importWaybillByNumber(
					waybillNo, deptCode);
		} finally {
			// 释放锁
			businessLockService.unlock(mutexElement);
		}
		return waybillRfcImportDto;

	}
	
	/**
	 * 
	 * <p>
	 * 根据运单号导入运单
	 * </p>
	 * 
	 * @author 265475-foss-邹胜利
	 * @date 2016-1-18 上午11:00:07
	 * @param waybillNo
	 * @return
	 * @throws BusinessException
	 * @see
	 */
	@Override
	public WaybillRfcImportDto importWaybillBy(String waybillNo) {
		WaybillRfcImportDto waybillRfcImportDto = null;
		String deptCode = FossUserContext.getCurrentDeptCode();
		MutexElement mutexElement = new MutexElement(waybillNo,
				WaybillConstants.IMPORT_WAYBILL_NUMBER,
				MutexElementType.RFC_IMPORT);// 创建对象
		// 互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, LOCK_TIMEOUT);
		if (!isLocked) {
			throw new WaybillImportException(
					WaybillImportException.WAYBILL_NUMBER_LOCKED);
		}
		try {
			waybillRfcImportDto = waybillRfcService.importWaybillBy(
					waybillNo, deptCode);
		} finally {
			// 释放锁
			businessLockService.unlock(mutexElement);
		}
		return waybillRfcImportDto;

	}

	/**
	 * 
	 * 查询交接单明细
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-9 下午4:10:30
	 */
	@Override
	public List<HandOverAndUnloadDto> queryHandoverDetail(String waybillNo) {
		return waybillRfcService.queryHandoverDetail(waybillNo);
	}

	public Long queryGoodsInfoCount(TodoConditionDto todoConditionDto) {
		return todoActionService.queryGoodsInfoCount(todoConditionDto);

	}

	/**
	 * 
	 * 提交更改单
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-19 下午2:10:24
	 */
	@Override
	public WaybillRfcEntity commitWaybillRfc(WaybillRfcSubmitDto submitDto) {
		WaybillRfcEntity waybillRfcEntity = null;
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		MutexElement mutexElement = new MutexElement(submitDto.getRfcEntity()
				.getWaybillNo(), WaybillConstants.WAYBILL_RFC,
				MutexElementType.WAYBILL_NO);// 创建对象
		// 互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, LOCK_TIMEOUT);
		if (!isLocked) {
			throw new WaybillImportException(
					WaybillImportException.WAYBILL_NUMBER_LOCKED);
		}
		try {
			waybillRfcEntity = waybillRfcService.commitWaybillRfc(submitDto,
					currentInfo);
		} finally {
			// 释放锁
			businessLockService.unlock(mutexElement);
		}
		return waybillRfcEntity;

	}

	/**
	 * 更新在线更改单打印次数 +1
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-15 下午11:48:25
	 * @param waybillNo
	 * @return
	 * @see
	 */
	@Override
	public int updateWaybillRfcPrintTimes(String waybillId) {

		return waybillRfcService.updateWaybillRfcPrintTimes(waybillId);
	}

	/**
	 * 根据更改单ID 查询更改单实体
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-18 下午2:49:01
	 * @param id
	 * @return
	 * @see
	 */
	@Override
	public WaybillRfcEntity queryRfcEntityByWaybillId(String id) {
		return waybillRfcService.queryRfcEntityByWaybillId(id);
	}

	/**
	 * 
	 * 差错查询
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-1 下午6:10:37
	 */
	@Override
	public QueryVirtualResultDto queryErrorHandlingResult(String handingID) {
		return oaErrorService.queryVirtrualWaybillFromOA(handingID);
	}

	/**
	 * 插入一条更改单打印记录
	 * 
	 * @author foss-jiangfei
	 * @date 2012-12-5 下午7:48:25
	 * @param waybillNo
	 * @return
	 * @see
	 */
	public int insertSelective(PrintInfoEntity record) {
		return printInfoDao.insertSelective(record);
	}

	/**
	 * 在线查询更改单打印次数
	 * 
	 * @author foss-jiangfei
	 * @date 2012-12-5 下午7:48:25
	 * @param waybillNo
	 * @return
	 * @see
	 */
	public int queryPrintTimesByWaybillId(String waybillId, String waybillNo) {
		return printInfoDao.queryPrintTimesByWaybillId(waybillId, waybillNo);
	}

	/**
	 * 
	 * 根据Code查询价格DTO
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2013-1-22 下午2:27:16
	 * @see com.deppon.foss.module.pickup.changing.client.service.IWaybillRfcRemotingService#queryValueAddPriceByCode(java.lang.String)
	 */
	@Override
	public PriceEntity queryValueAddPriceByCode(String pricingCodeZz) {
		return waybillRfcService.queryValueAddPriceByCode(pricingCodeZz);
	}

	/**
	 * 
	 * 根据代理网点编码查询外发代理网点和合作伙伴（代理公司）信息
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-12 下午4:52:38
	 */
	@Override
	public OuterBranchEntity queryAgencyBranchCompanyInfo(
			String agencyBranchCode) {
		return waybillRfcService.queryAgencyBranchCompanyInfo(agencyBranchCode);
	}

	/**
	 * 
	 * 通过 标识编码查询
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-12 下午4:56:44
	 */
	@Override
	public SaleDepartmentEntity querySaleDepartmentByCode(
			String customerPickupOrgCode) {
		return waybillRfcService
				.querySaleDepartmentByCode(customerPickupOrgCode);
	}

	/**
	 * 根据virtual code查询走货路径
	 * 
	 * @param virtualCode
	 * @return
	 */
	public FreightRouteEntity queryFreightRouteByVirtualCode(String virtualCode) {
		return waybillRfcService.queryFreightRouteByVirtualCode(virtualCode);
	}

	@Override
	public String queryWaybillNOByID(String waybillID) {
		// TODO Auto-generated method stub
		return waybillRfcService.queryWaybillNO(waybillID);
	}

	/**
	 * @param todoActionService
	 *            the todoActionService to set
	 */
	public void setTodoActionService(ITodoActionService todoActionService) {
		this.todoActionService = todoActionService;
	}

	/**
	 * 通过营业部编码获取运输性质
	 * 
	 * @author WangQianJin
	 * @date 2013-7-22 下午8:45:47
	 */
	public List<ProductEntity> queryTransType(String salesDeptId) {
		return waybillRfcService.queryTransType(salesDeptId);
	}

	/**
	 * 根据到达部门编码获取运输性质
	 * 
	 * @author WangQianJin
	 * @date 2013-7-22 下午9:18:41
	 */
	public List<ProductEntity> queryByArriveDeptProduct(String arriveDept) {
		return waybillRfcService.queryByArriveDeptProduct(arriveDept);
	}

	/**
	 * 通过营业部编码获取运输性质
	 * 
	 * @author WangQianJin
	 * @date 2013-7-22 下午8:45:47
	 */
	public List<ProductEntity> queryBySalesDept(String salesDeptId,
			String productLevel) {
		return waybillRfcService.queryBySalesDept(salesDeptId, productLevel);
	}

	/**
	 * 根据到达部门编码获取运输性质
	 * 
	 * @author WangQianJin
	 * @date 2013-7-22 下午9:18:41
	 */
	public List<ProductEntity> queryByArriveDept(String salesDeptId,
			String productLevel) {
		return waybillRfcService.queryByArriveDept(salesDeptId, productLevel);
	}
	
	 /**
		 * 查询到达部门产品
		 * 
		 * @author 076234-FOSS-pgy
		 * @date 2014-3-5
	*/
	public List<ProductEntity> searchByArriveDept(String salesDeptId,
			String productLevel) {
		return waybillRfcService.searchByArriveDept(salesDeptId, productLevel);
	}

	@Resource
	private ILdpExternalBillService ldpExternalBillService;
	
	/**
	 * 
	 * 根据运单号，快递代理理公司代码查询外发单(用于结算查询是否存在有效外发单)
	 * 
	 * @author 025000-foss-helong
	 * @date 2013-7-24
	 */
	@Override
	public boolean checkIfExistValidLdpExternalBillForStl(String waybillNo,
			String agentCompanyCode) {
		return ldpExternalBillService.checkIfExistValidLdpExternalBillForStl(
				waybillNo, agentCompanyCode);
	}
	
	@Resource
	private IHandOverBillService handOverBillService;
	
	/**
	 * 根据运单号查询运单是否已交接快递代理公司
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-29 下午12:35:20
	 */
	@Override
	public boolean isLdpHandOveredOutBranch(String waybillNo){
		return handOverBillService.queryBeLdpHandOveredByWaybillNo(waybillNo);
	}
	
	/**
	 * 根据运单号查询快递运单信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-30 下午3:33:36
	 */
	@Override
	public WaybillExpressEntity queryWaybillExpressByNo(String waybillNo){
		if(StringUtils.isEmpty(waybillNo)){
			return null;
		}
		return waybillExpressService.queryWaybillExpressByNo(waybillNo);
	}
	
	/**
	 * 是否打过包装
	  * Description: 
	  * @author deppon-157229-zxy
	  * @version 1.0 2014-1-21 上午9:44:17
	  * @param waybillNo
	  * @return
	 */
	@Override
	public boolean isHasPackaged(String waybillNo){
		QueryPackedConditionEntity queryPackedConditionEntity = new QueryPackedConditionEntity();
		queryPackedConditionEntity.setWayBillNumber(waybillNo);
		List<QueryPackedResultEntity> packedResultLst = queryPackedService.queryPackedAllInfo(queryPackedConditionEntity);
		if(packedResultLst != null && packedResultLst.size() > 0)
			return true;
		else 
			return false;
	}
	
	/**
	 * 查询运单是否有交接记录
	 * 
	 */
	public List<HandOverBillEntity> queryHandOveredRecordsByWaybillNo(String waybillNo){
		return waybillRfcService.queryHandOveredRecordsByWaybillNo(waybillNo);
	} 
	
	/**
	 * 查询运单是否在开单部门有卸车记录
	 *  
	 */
	public boolean queryUnloadTaskReport(String waybillNo){
		return waybillRfcService.queryUnloadTaskReport(waybillNo);
	}

	@Override
	public void canDebit(String deliveryCustomerCode, String receiveOrgCode) {
		waybillRfcService.canDebit(deliveryCustomerCode, receiveOrgCode);
	}
	/**
	 * 通过运单号查询更改单的所有记录
	 */
	@Override
	public List<WaybillRfcEntity> queryRecentRfc(String waybillNo) {
		// TODO Auto-generated method stub
		return waybillRfcService.queryRecentRfc(waybillNo);
	}
	
	/**
	 * 
	 * qms差错查询
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-1 下午6:10:37
	 */
	@Override
	public LostRepDto queryQmsErrorHandlingResult(String handingID,
			String waybillNo) {
		return oaErrorService.queryLostRepDto(handingID, waybillNo);
	}
	
	/**
     * 根据更改单id,查询更改单变更信息
     * @author 311417 wangfeng
     * @date 2016-4-12
     */
    public String queryRfcChangeItemsByWaybillRfcId(String waybillRfcId) {
        return  waybillRfcService.queryRfcChangeItemsByWaybillRfcId(waybillRfcId);
       
    }
    /**
     * 
     * <p>校验运单号是否存在</p> 
     * @author Foss-351326-xingjun 
     * @date 2016-8-5 上午8:26:43
     * @param waybillNo
     * @param operator 操作人
     * @return 
     * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillRfcHessianRemoting#valuateFoss2EcsWaybillNo(java.lang.String)
     */
    public boolean valuateFoss2EcsWaybillNo(String waybillNo,String operator){
    	return valuateFoss2EcsWaybillNoService.valuateFoss2EcsWaybillNo(waybillNo,operator);
    }
    
    /**
     * 
     * <p>查询传递ECS开关</p> 
     * @author Foss-351326-xingjun 
     * @date 2016-8-5 上午8:26:55
     * @return 
     * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillRfcHessianRemoting#queryPkpSwitch4Ecs()
     */
    public boolean queryPkpSwitch4Ecs(){
		return configurationParamsService.queryPkpSwitch4Ecs();
	}
    
    /**
     * 查询是否可开装卸费
     * @author 354805
     * @date 2016-12-6 16:29:18
     * @param code 装卸费开单部门
     * @param billTime 开单时间
     * @return 是否可开装卸费
     */
    @Override
    public String queryCanPayServiceFeeByCodeAndTime(String code, Date billTime ){
    	return waybillRfcService.queryCanPayServiceFeeByCodeAndTime(code, billTime);
    }
    
    /**
     * 根据运单号查询运单是否存在单票入库记录（入库类型：少货找到，派送拉回，偏线拉回）
     * @Title:        IsWaybillPutInStorage 
     * @param:        @param waybillNo
     * @param:        @return    
     * @return:       boolean    
     * @author        355673 chenpeng
     * @Date          2016-11-26 下午2:30:41 
     * @throws
     */
	@Override
	public boolean IsWaybillPutInStorage(String waybillNo,List<String> inStockTypeList) {
		List<InOutStockEntity> list=stockService.queryInStockInfoByType(waybillNo, null, inStockTypeList);
		boolean flag= list==null||list.size()==0?false:true;
		return flag;
	}

	@Override
	public CustomerCircleNewDto getByCustCode(String provinceCode, Date date,
			String active) {
		return customerCircleRelationNewService.getByCustCode(provinceCode, date, active);
	}
}