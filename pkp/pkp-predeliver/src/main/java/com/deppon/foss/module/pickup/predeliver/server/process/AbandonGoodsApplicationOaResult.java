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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/process/AbandonGoodsApplicationOaResult.java
 * 
 * FILE NAME        	: AbandonGoodsApplicationOaResult.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.process;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.inteface.domain.workflow.GoodsDiscardRequest;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IAbandonGoodsApplicationDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IAbandonGoodsApplicationService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.AbandGoodsApplicationConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AbandonGoodsApplicationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonGoodsResultDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonedGoodsSearchDto;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillTransactionService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillTransactionEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * FOSS_DISCARD_RESULT ：OA弃货流审批结果接口 implements IProcess
 * 
 * @date 2012-11-22 下午5:07:42
 */
public class AbandonGoodsApplicationOaResult {

	/**
	 * 日志Logger
	 */
	protected static final Logger LOG = LoggerFactory.getLogger(AbandonGoodsApplicationOaResult.class.getName());

	/**
	 * 弃货处理dao
	 */
	protected IAbandonGoodsApplicationDao abandonGoodsApplicationDao;

	/**
	 * 中转出库
	 */
	protected IStockService stockService;

	/**
	 * 货签信息数据持久层接口
	 */
	protected ILabeledGoodDao labeledGoodDao;

	/**
	 * 运单状态
	 */
	protected IActualFreightService actualFreightService;

	/**
	 * 部门组织复杂数据信息service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 运单完结状态操作Service
	 */
	private IWaybillTransactionService waybillTransactionService;

	private IWaybillSignResultService waybillSignResultService;
	
	private IAbandonGoodsApplicationService abandonGoodsApplicationService;

	/**
	 * set对象
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * set对象
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}

	/**
	 * set对象
	 */
	public void setAbandonGoodsApplicationDao(IAbandonGoodsApplicationDao abandonGoodsApplicationDao) {
		this.abandonGoodsApplicationDao = abandonGoodsApplicationDao;
	}

	/**
	 * set对象
	 */
	public void setLabeledGoodDao(ILabeledGoodDao labeledGoodDao) {
		this.labeledGoodDao = labeledGoodDao;
	}

	/**
	 * set对象
	 */
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}

	/**
	 * 处理异常 应该是不需要处理的 因为不可能去改变弃货的工作流状态
	 */
	public Object errorHandler(Object request) throws ESBBusinessException {

		//没有逻辑
		return null;
	}

	/**
	 * 将弃货开运单到大区办公室时查看弃货工作流审批结果
	 */
	@Transactional
	public Object process(Object request) throws ESBBusinessException {

		LOG.info("###OA弃货审批结果回写start");
		
		//接口数据
		GoodsDiscardRequest req = (GoodsDiscardRequest) request;

		LOG.info("GoodsDiscardRequest" + ReflectionToStringBuilder.toString(req));

		//创建弃货更新对象
		AbandonGoodsApplicationEntity abandonentity = ceateAbandonGoodsApplication(req);

		//查询传递参数dto
		AbandonedGoodsSearchDto dto = new AbandonedGoodsSearchDto();
		dto.setWaybillNo(req.getWaybillNumber());
		dto.setProcessId(req.getWorkFlowNo());
		dto.setStatus(AbandGoodsApplicationConstants.ABANDGOODS_STATUS_APPROVAL);
		
		//根据oa审批人信息创建用户
		CurrentInfo currentInfo = null;
		
		List<AbandonGoodsResultDto> isNoTagList = abandonGoodsApplicationDao.searchNoTagAbandonGoodsList(dto);
		if(isNoTagList != null && isNoTagList.size() > 0){
					
			AbandonGoodsResultDto temp = isNoTagList.get(0);
			
			if(LOG.isInfoEnabled()){
				LOG.info("###noTag AbandonGoods###id=" + temp.getId());
			}
			
			// 设置abandonentity的ID
			abandonentity.setId(temp.getId());
			// 操作人员信息
			currentInfo = createCurrentInfo(temp);
			// 操作时间
			abandonentity.setOperateTime(new Date());
			// 审批结果
			if (req.isResult()) {
				// 审批通过
				abandonentity.setStatus(AbandGoodsApplicationConstants.ABANDGOODS_STATUS_PASS);
			}else{
				// 审批不通过
				abandonentity.setStatus(AbandGoodsApplicationConstants.ABANDGOODS_STATUS_REFUSE);	
			}			
		}else{	
			//查询弃货
			List<AbandonGoodsResultDto> abandonedGoodsList = abandonGoodsApplicationDao.searchAbandonGoodsList(dto);
	
			if (CollectionUtils.isEmpty(abandonedGoodsList)) {
				return null;
			}
			// 设置abandonentity的ID
			abandonentity.setId(abandonedGoodsList.get(0).getId());
	
			// 操作人员信息
			currentInfo = createCurrentInfo(abandonedGoodsList.get(0));
	
			// 操作时间
			abandonentity.setOperateTime(new Date());
			/**
			 * FOSS系统调用签收出库接口(参见SUC-789 签收出库)对此票货自动签收出库，
			 * 并标示此货为仓库异常货物，如果此货有到付款、应收款等财务数据， 系统自动清零，引用结算用例（参见SUC-159-异常出库）
			 */
			//审批通过
			if (req.isResult()) {//审批结果
				//审批通过
				abandonentity.setStatus(AbandGoodsApplicationConstants.ABANDGOODS_STATUS_PASS);
	
				//中转出库
				outStock(req.getWaybillNumber(), currentInfo);
	
				//更新ActualFreight表中的结清状态为已结清
				updateActualFreight(req.getWaybillNumber());
	
				// 弃货签收接口
				waybillSignResultService.addAbandonGoodsSign(req.getWaybillNumber(),currentInfo);
	
				//需要调用业务完结接口
				overWaybillTransaction(req);
	
			} else {
				//审批不通过 客户要求返货
				if (AbandGoodsApplicationConstants.OA_REFUSE_DATE_CUSTOMER_REFUSE.equals(req.getReason())) {//事由 == 2 (客户要求返货)
					//--审批不通过 客户要求返货
					abandonentity.setStatus(AbandGoodsApplicationConstants.ABANDGOODS_STATUS_CUSTOMERREQUIRECHANGE);//客户要求返货
				} else {//审批不通过 1数据格式不对 3other
					abandonentity.setStatus(AbandGoodsApplicationConstants.ABANDGOODS_STATUS_REFUSE); //事由!=2 (就设置为审批不通过)
				}
			}
		}
		//更新审批结果
		abandonGoodsApplicationDao.updateByKey(abandonentity);
		// 设置操作内容
		abandonentity.setOperateContent(ReflectionToStringBuilder.toString(req));
		// 保存弃货操作历史记录
		abandonGoodsApplicationService.insertAgActionHistoryEntity(abandonentity, currentInfo);
		return null;
	}

	/**
	 * 需要调用业务完结接口
	 * 
	 * @param req
	 */
	private void overWaybillTransaction(GoodsDiscardRequest req) {
		//创建业务完结对象
		WaybillTransactionEntity waybillTransactionEntity = new WaybillTransactionEntity();
		//设置运单号
		waybillTransactionEntity.setWaybillNo(req.getWaybillNumber());
		//业务完结 YES
		waybillTransactionEntity.setBusinessOver(FossConstants.YES);
		//业务完结 update
		waybillTransactionService.updateBusinessOver(waybillTransactionEntity);
	}

	/**
	 * 更新ActualFreight表中的结清状态为已结清
	 * 
	 * @param waybillNo
	 */
	private void updateActualFreight(String waybillNo) {
		ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(waybillNo);

		if (actualFreightEntity != null) {
			// 更新到达未出库数量为0
			actualFreightEntity.setArriveNotoutGoodsQty(NumberUtils.INTEGER_ZERO);
			//运单号
			ActualFreightEntity actualFreightEntityForUpdate = new ActualFreightEntity();
			//创建对象
			actualFreightEntityForUpdate.setId(actualFreightEntity.getId());
			//ID
			actualFreightEntityForUpdate.setSettleStatus("Y");
			//已结清
			actualFreightEntityForUpdate.setSettleTime(new Date());
			//结清时间
			actualFreightService.updateWaybillActualFreight(actualFreightEntity);
		}
		//UPDATE
	}

	/**
	 * 中转出库
	 * 
	 * @param waybillNo 运单号
	 * @param currentInfo 用户
	 */
	private void outStock(String waybillNo, CurrentInfo currentInfo) {
		//获得该运单号对应的流水号列表
		List<LabeledGoodEntity> labelGoodList = labeledGoodDao.queryAllSerialByWaybillNo(waybillNo);

		if (labelGoodList != null && !labelGoodList.isEmpty()) {
			//流水号列表不为空
			for (Iterator<LabeledGoodEntity> iterator = labelGoodList.iterator(); iterator.hasNext();) {
				//迭代列表
				LabeledGoodEntity labeledGoodEntity = iterator.next();
				//流水号

				InOutStockEntity stockEntity = new InOutStockEntity();
				//创建出库对象
				stockEntity.setCreateDate(new Date());//出库时间
				stockEntity.setCreateUser(currentInfo.getEmpCode());
				//出库人
				stockEntity.setDeviceType(StockConstants.PC_DEVICE_TYPE);
				//出入库设备类型PC
				stockEntity.setWaybillNO(waybillNo);
				//运单号
				stockEntity.setSerialNO(labeledGoodEntity.getSerialNo());
				//流水号
				stockEntity.setOperatorCode(currentInfo.getEmpCode());
				//操作人编号
				stockEntity.setOperatorName(currentInfo.getEmpName());
				//操作人名
				stockEntity.setInOutStockType(StockConstants.ABANDON_GOODS_OUT_STOCK_TYPE);
				//弃货出库出库 
				stockEntity.setOrgCode(currentInfo.getCurrentDeptCode());
				//org code
				int resultStock = stockService.outStockPC(stockEntity);
				//中转出库
				if (FossConstants.SUCCESS == resultStock) {
					//出库成功
					//出库成功  -- 啥都不作
					LOG.info(waybillNo + "出库成功");
				} else {//出库失败
						//出库失败 -- 啥都不作
					LOG.info(waybillNo + "出库FAIL");
				}
			}
		}
	}

	/**
	 * 创建弃货更新对象
	 * 
	 * @param req
	 * @return
	 */
	private AbandonGoodsApplicationEntity ceateAbandonGoodsApplication(GoodsDiscardRequest req) {
		//工作流号 PROCESSINSTID number
		//运单号 ORDERNO VARCHAR2(50)
		//最后审批人工号 userid VARCHAR2(50)
		//流程名称 workFlowName VARCHAR2(100)
		//最后审批人工号 userid VARCHAR2(50)
		//最后审批人名称 lastName VARCHAR2(50)
		//最后审批部门标杆编码 finacode VARCHAR2(50)
		//最后审批部门名称 deptName VARCHAR2(120)
		//审批结果 result number
		//不同意原因   reason VARCHAR2(2)

		AbandonGoodsApplicationEntity abandonentity = new AbandonGoodsApplicationEntity();
		//创建弃货更新对象
		abandonentity.setWaybillNo(req.getWaybillNumber());
		//运单号
		abandonentity.setProcessReason(req.getReason());
		//事由
		abandonentity.setProcessId(StringUtils.substringBefore(req.getWorkFlowNo(), "."));
		//工作流号
		return abandonentity;
		//返回弃货更新对象
	}

	/**
	 * 根据oa审批人信息创建用户
	 * 
	 * @param req 接口数据
	 * @return
	 */
	private CurrentInfo createCurrentInfo(AbandonGoodsResultDto dto) {
		//当前没有session所以后面的操作人要根据oa返回的操作人来确定
		UserEntity user = createUserEmtity(dto);
		//当前没有session所以后面的操作人要根据oa返回的审批部门来确定
		OrgAdministrativeInfoEntity org = createOrg(dto);
		//创建currentInfo对象
		return new CurrentInfo(user, org);
		//返回操作人
	}

	/**
	 * 根据oa审批人信息创建部门
	 * 
	 * @param req 接口数据
	 * @return
	 */
	private OrgAdministrativeInfoEntity createOrg(AbandonGoodsResultDto dto) {

		OrgAdministrativeInfoEntity orgByUnify = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(dto.getOperateOrgCode());

		if (orgByUnify == null) {
			LOG.error(dto.getWaybillNo() + "部门编码在综合中无法查询到 可能会出错, 部门编码" + dto.getOperateOrgCode());
		}

		//返回部门信息
		return orgByUnify;

	}

	/**
	 * 根据oa审批人信息创建用户
	 * 
	 * @param req 接口数据
	 * @return
	 */
	private UserEntity createUserEmtity(AbandonGoodsResultDto dto) {
		UserEntity user = new UserEntity();
		//用户
		EmployeeEntity employee = new EmployeeEntity();
		//职员
		employee.setEmpCode(dto.getOperatorCode());
		//最后审批人工号
		employee.setEmpName(dto.getOperatorCode());
		//最后审批人name
		user.setEmpCode(dto.getOperatorCode());
		//最后审批人工号
		user.setEmpName(dto.getOperatorCode());
		//最后审批人name
		user.setUserName(dto.getOperatorCode());
		//最后审批人name
		user.setEmployee(employee);
		//职员   currentInfo中需要使用
		return user;//返回用户信息
	}

	/**
	 * @param waybillTransactionService the waybillTransactionService to set
	 */
	public void setWaybillTransactionService(IWaybillTransactionService waybillTransactionService) {
		this.waybillTransactionService = waybillTransactionService;
	}

	public void setWaybillSignResultService(IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	public void setAbandonGoodsApplicationService(IAbandonGoodsApplicationService abandonGoodsApplicationService) {
		this.abandonGoodsApplicationService = abandonGoodsApplicationService;
	}

}