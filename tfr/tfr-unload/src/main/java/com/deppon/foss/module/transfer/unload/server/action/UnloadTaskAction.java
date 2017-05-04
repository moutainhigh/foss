/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-unload
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/server/action/UnloadTaskAction.java
 *  
 *  FILE NAME          :UnloadTaskAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
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
package com.deppon.foss.module.transfer.unload.server.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillDto;
import com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskService;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ConfirmUnloadTaskBillsDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ConfirmUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ExpressQueryUnloadWaybillDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.FossToWKResponseDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PlanUnloadBillDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryArrivedBillNoDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskAddnewDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskModifyDto;
import com.deppon.foss.module.transfer.unload.api.shared.vo.UnloadTaskVo;
import com.deppon.foss.module.transfer.unload.api.tools.UnloadCommonUtils;
import net.sf.json.JSONObject;

/** 
 * @className: UnloadTaskAction
 * @author: ShiWei shiwei@outlook.com
 * @description: 卸车任务action类
 * @date: 2012-12-12 下午6:40:16
 * 
 */
public class UnloadTaskAction extends AbstractAction {

	private static final long serialVersionUID = -117561159069405180L;
	
	/**
	 * 前后台传参的VO对象
	 */
	private UnloadTaskVo unloadTaskVo = new UnloadTaskVo();
	
	/**
	 * 该模块service
	 */
	private IUnloadTaskService unloadTaskService;
	
	private IConfigurationParamsService configurationParamsService;
	

	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setUnloadTaskService(IUnloadTaskService unloadTaskService) {
		this.unloadTaskService = unloadTaskService;
	}

	public UnloadTaskVo getUnloadTaskVo() {
		return unloadTaskVo;
	}

	public void setUnloadTaskVo(UnloadTaskVo unloadTaskVo) {
		this.unloadTaskVo = unloadTaskVo;
	}
	
	/**
	 * 用于获取当前登录部门的上级大部门
	 * @author 045923-foss-shiwei
	 * @date 2013-4-3 下午2:45:23
	 */
	@JSON
	public String unloadTaskAddNewIndex(){
		try{
			OrgAdministrativeInfoEntity superOrg = unloadTaskService.querySuperOrgByOrgCode(null);
			unloadTaskVo.setSuperOrgCode(superOrg.getCode());
			unloadTaskVo.setBeTransferCenter(superOrg.getTransferCenter());
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 传入车牌号，根据车牌号获取当前部门待卸的单据列表，同时返回该车辆已分配的月台
	 * @author 045923-foss-shiwei
	 * @date 2012-12-12 下午6:44:53
	 */
	@JSON
	public String queryBillInfoListByVehicleNo(){
		//获取传入的车牌号
		String vehicleNo = unloadTaskVo.getVehicleNo();
		//获取车牌号对应的单据编号list
		List<QueryArrivedBillNoDto> billNoDtoList = unloadTaskService.queryArrivedBillNoList(vehicleNo);
		//返回获取的月台编号
		if(billNoDtoList != null && billNoDtoList.size() != 0){
			QueryArrivedBillNoDto billNoDto = billNoDtoList.get(0);
			unloadTaskVo.setPlatformCode(billNoDto.getPlatformCode());
			//定义快递单号list
			List<String> expressNoList = new ArrayList<String>();
			//定义零担的交接单list
			List<String> linDanNoList = new ArrayList<String>();
			//定义配载单list
			List<String> stowageNoList = new ArrayList<String>();
			for(QueryArrivedBillNoDto dto : billNoDtoList){
				//如果是交接单
				if(dto.getBillType().equals(TaskTruckConstant.BILL_TYPE_HANDOVER)){
					/**
					 * 快递交接单规则
					 * 1)外场-外场长途交接单号为L开头+年月日6位+5位数字,如 L16012700001
					 * 2）营业部-外场短途交接单为S开头+年月日6位+5位数字,如 S16012700001
					 * 3）外场-外场的空运装车生成为T开头+年月日6位+5位数字,如T16012700001
					 */
					//此条件用来区分快递交接单
					if(UnloadCommonUtils.isExpressHandOver(dto.getBillNo())){
						expressNoList.add(dto.getBillNo());
					}else{
					//零担交接单
						linDanNoList.add(dto.getBillNo());
					}
				}else{
					//如果是配载单
					stowageNoList.add(dto.getBillNo());
				}
			}
			
			//定义零担快递合单list
			List<PlanUnloadBillDto> mergeList = new ArrayList<PlanUnloadBillDto>();
			//如果零担交接单不为空
			if(linDanNoList.size()>0){
				List<PlanUnloadBillDto> linDanList=unloadTaskService.queryArrivedHandOverBillInfoByNo(linDanNoList);
				if(linDanList!=null&&linDanList.size()>0){
					mergeList.addAll(linDanList);
				}
			}
			
			//配载单
			if(stowageNoList.size()>0){
				//返回单据list
				List<PlanUnloadBillDto> vehicleassembleList=unloadTaskService.queryArrivedVehicleAssembleBillInfoByNo(stowageNoList);
				if(vehicleassembleList!=null&&vehicleassembleList.size()>0){
					mergeList.addAll(vehicleassembleList);
				}
			}
			
			//如果快递交接单不为空
			if(expressNoList.size()>0){
				List<PlanUnloadBillDto> expressList=unloadTaskService.queryArrivedExpressHandOverBillInfoByNo(expressNoList);
				if(expressList!=null&&expressList.size()>0){
					mergeList.addAll(expressList);
				}
			}
			
			unloadTaskVo.setBillList(mergeList);
			
		}
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 传入单据编号，获取单据信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-12 下午9:22:30
	 */
	@JSON
	public String queryBillInfoByBillNo(){
		//获取单据编号
		String billNo = unloadTaskVo.getBillNo();
		//272681获取是否商务专递的状态
		Boolean isAir = unloadTaskVo.getIsAir();
		//2015/9/11 272681 从前台获取卸车任务编号和卸车类型
		String unloadTaskNo = unloadTaskVo.getUnloadTaskNo();
		String unloadType = unloadTaskVo.getUnloadType();	
		List<String> noList = new ArrayList<String>();
		noList.add(billNo);
		//定义单据实体list
		List<PlanUnloadBillDto> billList = null;
		//2015/9/11 272681 任务编号为空则是新增卸车任务
		if(unloadTaskNo == null){
		//272681是商务专递的交接单
        if(isAir){
        	billList = unloadTaskService.queryArrivedBusinessAirBillInfoByNo(noList);
		}else{		
			//快递交接单
			if(UnloadCommonUtils.isExpressHandOver(billNo)){
				billList=unloadTaskService.queryArrivedExpressHandOverBillInfoByNo(noList);
			}else if(UnloadCommonUtils.isAssemble(billNo)){
				//配载单
				billList = unloadTaskService.queryArrivedVehicleAssembleBillInfoByNo(noList);
			}else{
				//零担的交接单
				billList = unloadTaskService.queryArrivedHandOverBillInfoByNo(noList);
			}
			
		}
		//如果billList不为空
		if(billList.size() != 0){
			unloadTaskVo.setBillInfo(billList.get(0));
			String vehicleNo = billList.get(0).getVehicleNo();
			List<QueryArrivedBillNoDto> dtoList = unloadTaskService.queryArrivedBillNoList(vehicleNo);
			//设置月台号
			if(dtoList.size() != 0){
				unloadTaskVo.setPlatformCode(dtoList.get(0).getPlatformCode());
			}
		}
	   }else{
		   //2015/9/11 272681 卸车任务编号不为空则是修改卸车任务
		   if(UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS.equals(unloadType)){
			 //2015/9/11 272681 卸车类型是商务专递时修改卸车任务
			   billList = unloadTaskService.queryArrivedBusinessAirBillInfoByNo(noList);
			   
		   }else{
				
				//快递交接单
				if(UnloadCommonUtils.isExpressHandOver(billNo)){
					billList=unloadTaskService.queryArrivedExpressHandOverBillInfoByNo(noList);
				}else if(UnloadCommonUtils.isAssemble(billNo)){
					//配载单
					billList = unloadTaskService.queryArrivedVehicleAssembleBillInfoByNo(noList);
				}else{
					//零担的交接单
					billList = unloadTaskService.queryArrivedHandOverBillInfoByNo(noList);
				}
		   }
		 //如果billList不为空
			if(billList.size() != 0){
				unloadTaskVo.setBillInfo(billList.get(0));
				String vehicleNo = billList.get(0).getVehicleNo();
				List<QueryArrivedBillNoDto> dtoList = unloadTaskService.queryArrivedBillNoList(vehicleNo);
				//设置月台号
				if(dtoList.size() != 0){
					unloadTaskVo.setPlatformCode(dtoList.get(0).getPlatformCode());
				}
			}
	   }
		//返回success
		return returnSuccess();
	}

	/**
	 * 手工新增卸车任务
	 * @author 045923-foss-shiwei
	 * @date 2012-12-13 下午6:59:44
	 */
	@JSON
	public String addUnloadTask(){
		//获取传入的新增的卸车任务信息
		UnloadTaskAddnewDto addnewDto = unloadTaskVo.getAddnewDto();
		try{
			//新增卸车任务的同时返回前台卸车任务号
			String unloadTaskNo = unloadTaskService.addUnloadTask(addnewDto);
			//返回前台卸车任务号
			unloadTaskVo.setUnloadTaskNo(unloadTaskNo);
		}catch(BusinessException e){
			//返回业务异常信息
			return returnError(e);
		}catch(Exception e){
			return returnError(new TfrBusinessException(e.getMessage()));
		}
		//返回success
		return returnSuccess();
	}
	
	/**
	 * @description 同步手动新增卸车任务至快递悟空系统
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月27日 下午2:41:57
	 *//*
	private void addExpressUnloadTask(UnloadTaskVo unloadTaskVo){
		
		try {
			unloadTaskService.addExpressUnloadTask(unloadTaskVo);
		} catch (Exception e) {
			throw new TfrBusinessException(e.getMessage());
		}
			
	}*/
	
	/**
	 * 修改卸车任务时，加载修改前的数据
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 上午10:36:27
	 */
	@JSON
	public String loadUnloadTaskInfo(){
		//获取前台传入的ID
		String unloadTaskId = unloadTaskVo.getUnloadTaskId();
		//基本信息
		unloadTaskVo.setBaseEntity(unloadTaskService.queryUnloadTaskBaseInfoById(unloadTaskId));
		//2015/9/16 272681 卸车类型是商务专递卸车
		UnloadTaskEntity unloadTaskEntity = unloadTaskService.queryUnloadTaskBaseInfoById(unloadTaskId);
		if(UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS.equals(unloadTaskEntity.getUnloadType())){
		//2015/9/15 272681 获取商务专递的单据列表
		unloadTaskVo.setBillDetailList(unloadTaskService.queryAirUnloadTaskBillDetailListById(unloadTaskId));
		}else{
			
		List<UnloadBillDetailEntity> billDetailList=unloadTaskService.queryUnloadTaskBillDetailListById(unloadTaskId);
		for(int i=0;i<billDetailList.size();i++){
			//去除快递货
			if(UnloadCommonUtils.isExpressHandOver(billDetailList.get(i).getBillNo())){
				billDetailList.remove(i);
				    --i;
			}
		}
		
		unloadTaskVo.setUnloadType(unloadTaskEntity.getUnloadType());
		
		boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
		LOG.error("根据卸车任务编号返回快递交接单据明细开关" + tfrSwitch4Ecs);
		if(tfrSwitch4Ecs){
			List<UnloadBillDetailEntity> expressBillDetailList=expressLoadUnloadTaskInfo(unloadTaskVo);
			//如果快递单据列表不为空就添加到单据列表中
			if(null!=expressBillDetailList){
				billDetailList.addAll(expressBillDetailList);
			}
		}
		//单据列表
		unloadTaskVo.setBillDetailList(billDetailList);
		}
		//卸车员列表
		unloadTaskVo.setLoaderDetailList(unloadTaskService.queryLoaderDetailListById(unloadTaskId));
		//返回success
		return returnSuccess();
	}
	
	/**
	 * @description 根据卸车任务编号返回快递交接单据明细
	 * @param unloadTaskVo
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月3日 上午8:33:27
	 */
	private List<UnloadBillDetailEntity> expressLoadUnloadTaskInfo(UnloadTaskVo unloadTaskVo){
		
		ExpressQueryUnloadWaybillDetailDto queryUnloadWaybillDetailDto=new ExpressQueryUnloadWaybillDetailDto();
		//设置 卸车任务编号
		queryUnloadWaybillDetailDto.setUnloadTaskNo(unloadTaskVo.getBaseEntity().getUnloadTaskNo());
		List<UnloadBillDetailEntity> unloadBillDetailEntityList=null;
		try {
			//根据卸车任务编号返回快递单据明细
			unloadBillDetailEntityList = unloadTaskService.queryExpressUnloadWaybillDetail(queryUnloadWaybillDetailDto,unloadTaskVo.getUnloadType());
		
		}catch (Exception e) {
			throw new TfrBusinessException(e.getMessage());
		}
			
		  return unloadBillDetailEntityList;
	}
	
	/**
	 * 修改卸车任务时，提交数据
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 下午3:16:18
	 */
	@JSON
	public String updateUnloadTask(){
		//获取前台传入的dto
		UnloadTaskModifyDto unloadTaskModifyDto = unloadTaskVo.getUnloadTaskModifyDto();
		try{
			//调用service，更新卸车任务信息
			unloadTaskService.updateUnloadTask(unloadTaskModifyDto);
			//updateExpressUnloadTask(unloadTaskVo.getUnloadTaskModifyDto());
		}catch(BusinessException e){
			//返回业务异常信息
			return returnError(e);
		}
		//返回success
		return returnSuccess();
	}
	/**
	 * @description Foss修改卸车任务同步数据到悟空系统
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月28日 下午6:42:36
	 */
	/*private void updateExpressUnloadTask(UnloadTaskModifyDto modifyDto){
	
		try {
			//Foss修改卸车任务同步数据到悟空系统
			unloadTaskService.updateExpressUnloadTask(modifyDto);
		} catch (Exception e) {
			throw new TfrBusinessException(e.getMessage());
		}
			
	}*/
	
	/**
	 * 取消卸车任务
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 下午7:59:26
	 */
	public String cancelUnloadTask(){
		//获取前台传入的taskid
		String unloadTaskId = unloadTaskVo.getUnloadTaskId();
		String unloadTaskNo=unloadTaskVo.getUnloadTaskNo();
				
		try{
			/*
			 * 调用service，作废卸车任务，直接删除该任务相关信息
			 */
			unloadTaskService.cancelUnloadTask(unloadTaskId,unloadTaskNo);
		}catch(BusinessException e){
			//返回业务异常信息
			return returnError(e);
		}
		//返回success
		return returnSuccess();
	}
	
	/**
	 * @description Foss同步取消卸车任务到悟空系统
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 上午11:42:31
	 *//*
	private void expressCancelUnloadTask(UnloadTaskVo unloadTaskVo){
		
		ExpressCancelUnloadTaskDto expressCancelUnloadTaskDto=new ExpressCancelUnloadTaskDto();
		expressCancelUnloadTaskDto.setUnloadTaskNo(unloadTaskVo.getUnloadTaskNo());
		expressCancelUnloadTaskDto.setUpdateTime(new Date());
		
		try {
			unloadTaskService.expressCancelUnloadTask(expressCancelUnloadTaskDto);
		} catch (Exception e) {
			LOG.error(e.getMessage(), "");
			throw new TfrBusinessException(e.getMessage());
		}
			
	}*/
	
	/**
	 * 确认卸车界面，根据交接单号获取运单号
	 * @author 045923-foss-shiwei
	 * @date 2012-12-15 下午5:14:18
	 */
	public String queryWaybillListByHandOverBillNo(){
		//获取前台传入的交接单号
		String handOverBillNo = unloadTaskVo.getHandOverBillNo();
		//2015/9/17 272681 获取前台传入的卸车类型
		String unloadType = unloadTaskVo.getUnloadType();
		try{
			if(UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS.equals(unloadType)){
				//2015/9/17 272681返回商务专递交接单下的所有运单号	
			       unloadTaskVo.setWaybillList(unloadTaskService.queryWaybillListByAirHandOverBillNo(handOverBillNo));
				}else{
					//此处区分零担快递交接单做不同处理
					if(UnloadCommonUtils.isExpressHandOver(handOverBillNo)){
						
						List<HandOverBillDetailEntity> expressHandOverBillDetailList=expressQueryWaybillListByHandOverBillNo(unloadTaskVo);
							if(null!=expressHandOverBillDetailList){
								unloadTaskVo.setWaybillList(expressHandOverBillDetailList);
							}
							
					}else{
						 //返回交接单下的所有运单号
						 unloadTaskVo.setWaybillList(unloadTaskService.queryWaybillListByHandOverBillNo(handOverBillNo));
					}
				}
		}catch(BusinessException e){
			//返回业务异常信息
			return returnError(e);
		}
		//返回success
		return returnSuccess();
	}
	
	/**
	 * @description 确认卸车界面，根据交接单号获取快递笼号/包号/运单号
	 * @param unloadTaskVo
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年5月3日 上午10:14:43
	 */
	private List<HandOverBillDetailEntity> expressQueryWaybillListByHandOverBillNo(UnloadTaskVo unloadTaskVo){
		
		Map<String,String> paramMap=new HashMap<String,String>();
		//设置 交接单编号
		paramMap.put(UnloadConstants.HAND_OVER_BILL_NO,unloadTaskVo.getHandOverBillNo());
		//设置 卸车任务编号
		paramMap.put(UnloadConstants.UNLOAD_TASK_NO, unloadTaskVo.getUnloadTaskNo());
		//设置 卸车类型
		paramMap.put("unloadType", unloadTaskVo.getUnloadType());
		List<HandOverBillDetailEntity> handOverBillDetailEntityList=null;
		try {
			//根据交接单号获取快递笼号/包号/运单号
			handOverBillDetailEntityList = unloadTaskService.expressQueryWaybillListByHandOverBillNo(paramMap);
		
		}catch (Exception e) {
			throw new TfrBusinessException(e.getMessage());
		}
		  return handOverBillDetailEntityList;
	}
	
	/**
	 * 确认卸车界面，根据交接单号、运单号获取流水号列表
	 * @author 045923-foss-shiwei
	 * @date 2012-12-17 上午10:45:31
	 */
	public String querySerialNoListByHandOverBillNoAndWaybillNo(){
		//获取前台传入的交接单号、运单号
		String handOverBillNo = unloadTaskVo.getHandOverBillNo();
		//获取前台传入的运单号
		String waybillNo = unloadTaskVo.getWaybillNo();
		//2015/9/2 272681 获取前台传入的卸车类型
		String unloadType = unloadTaskVo.getUnloadType();
		//2015/9/2 272681 当卸车类型是商务专递卸车时根据商务专递的交接单号运单号查询流水号
		if(UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS.equals(unloadType)){
			unloadTaskVo.setSerialNoList(unloadTaskService.querySerialNoListByAirHandOverBillNoAndWaybillNo(handOverBillNo, waybillNo));
		}else{
		//调用交接单模块service，获取流水号列表，放入Vo
		   unloadTaskVo.setSerialNoList(unloadTaskService.querySerialNoListByHandOverBillNoAndWaybillNo(handOverBillNo, waybillNo));
		}
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 确认卸车任务（短途），快速定位功能，根据运单号获取运单号所在的交接单
	 * @author 045923-foss-shiwei
	 * @date 2012-12-19 下午1:48:06
	 */
	public String queryHandOverBillListByWaybillNo(){
		//获取传入的运单号
		String waybillNo = unloadTaskVo.getWaybillNo();
		//获取传入的卸车任务ID
		String unloadTaskId = unloadTaskVo.getUnloadTaskId();
		//2015/9/13 272681获取传入的卸车类型  
		String unloadType = unloadTaskVo.getUnloadType();
		//2015/9/13 272681根据商务专递的运单号获取所在的交接单 
		if(UnloadConstants.UNLOAD_TASK_TYPE_BUSINESS.equals(unloadType)){
			unloadTaskVo.setHandOverBillNoList(unloadTaskService.queryAirHandOverBillListByWaybillNo(unloadTaskId, waybillNo));
		}else{
			/**零担快递拆分代码*/
			//如果是快递包/笼/运单
			/*if(UnloadCommonUtils.isExpressWayBill(waybillNo)){
				Map<String,String> paramMap=new HashMap<String,String>();
				paramMap.put("unloadTaskNo", unloadTaskVo.getUnloadTaskNo());
				paramMap.put("orgCode",FossUserContext.getCurrentDeptCode());
				paramMap.put("cargoNo", waybillNo);				
				List<String> handOverBillList=unloadTaskService.queryExpressHandOverBillListByWaybillNo(paramMap);
				unloadTaskVo.setHandOverBillNoList(handOverBillList);
			}else{
			//获取运单号所在的卸车任务的交接单号list
			unloadTaskVo.setHandOverBillNoList(unloadTaskService.queryHandOverBillListByWaybillNo(unloadTaskId, waybillNo));	
			}*/
			/**配合灰度改造后代码*/
			List<String> handOverBillList=null;
			//获取运单号所在的卸车任务的交接单号list
			handOverBillList=unloadTaskService.queryHandOverBillListByWaybillNo(unloadTaskId, waybillNo);
			
			boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
			LOG.error("确认卸车任务（短途），快速定位功能开关" + tfrSwitch4Ecs);
			if(tfrSwitch4Ecs){
			if(CollectionUtils.isEmpty(handOverBillList)&&UnloadCommonUtils.isExpressWayBill(waybillNo)){
				Map<String,String> paramMap=new HashMap<String,String>();
				paramMap.put("unloadTaskNo", unloadTaskVo.getUnloadTaskNo());
				paramMap.put("orgCode",FossUserContext.getCurrentDeptCode());
				paramMap.put("cargoNo", waybillNo);	
				//获取运单号/包号/笼号所在的卸车任务的交接单号list
				handOverBillList=unloadTaskService.queryExpressHandOverBillListByWaybillNo(paramMap);
			 }
			}
			unloadTaskVo.setHandOverBillNoList(handOverBillList);
		
		}
		//返回success
		return returnSuccess();
	}
	
	
	/**
	 * 确认卸车任务（长途），快速定位功能，根据运单号获取运单号所在的配载单、交接单
	 * @author 045923-foss-shiwei
	 * @date 2012-12-23 下午5:13:09
	 */
	public String queryBillNosListByWaybillNo(){
		//获取传入的运单号
		String waybillNo = unloadTaskVo.getWaybillNo();
		//获取传入的卸车任务ID
		String unloadTaskId = unloadTaskVo.getUnloadTaskId();
		/**零担快递拆分代码*/
		//如果是快递包/笼/运单
		/*if(UnloadCommonUtils.isExpressWayBill(waybillNo)){
				Map<String,String> paramMap=new HashMap<String,String>();
				paramMap.put("unloadTaskNo", unloadTaskVo.getUnloadTaskNo());
	 			paramMap.put("orgCode",FossUserContext.getCurrentDeptCode());
				paramMap.put("cargoNo", waybillNo);				
				List<ConfirmUnloadTaskBillsDto> handOverBillList=unloadTaskService.queryExpressBillNosListByWaybillNo(paramMap);
				unloadTaskVo.setBillNosList(handOverBillList);
		}else{
			//获取运单号所在的卸车任务的配载单号、交接单号list
			unloadTaskVo.setBillNosList(unloadTaskService.queryBillNosListByWaybillNo(unloadTaskId, waybillNo));	
		}*/
		/**配合灰度改造后代码*/
		List<ConfirmUnloadTaskBillsDto> handOverBillList=null;
		handOverBillList=unloadTaskService.queryBillNosListByWaybillNo(unloadTaskId, waybillNo);
		
		boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
		LOG.error("确认卸车任务（长途），快速定位功能开关" + tfrSwitch4Ecs);
		if(tfrSwitch4Ecs){
		if(CollectionUtils.isEmpty(handOverBillList)&&UnloadCommonUtils.isExpressWayBill(waybillNo)){
			Map<String,String> paramMap=new HashMap<String,String>();
			paramMap.put("unloadTaskNo", unloadTaskVo.getUnloadTaskNo());
 			paramMap.put("orgCode",FossUserContext.getCurrentDeptCode());
			paramMap.put("cargoNo", waybillNo);				
			handOverBillList=unloadTaskService.queryExpressBillNosListByWaybillNo(paramMap);
		 }
		}
		unloadTaskVo.setBillNosList(handOverBillList);
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 确认卸车任务
	 * @author 045923-foss-shiwei
	 * @date 2012-12-19 下午8:56:57
	 */
	@JSON
	public String confirmUnloadTask(){
		//获取前台提交的数据
		ConfirmUnloadTaskDto confirmUnloadTaskDto = unloadTaskVo.getConfirmUnloadTaskDto();
		try{
			//调用service，确认卸车任务
			unloadTaskService.confirmUnloadTask(confirmUnloadTaskDto);
			//expressConfirmUnloadTask(unloadTaskVo.getConfirmUnloadTaskDto());
		}catch(BusinessException e){
			//返回业务异常信息
			return returnError(e);
		}
		//返回success
		return returnSuccess();
	}
	
	
	
	
	/**
	 * 确认PDA卸车任务
	 * @author 042795-foss-duyi
	 * @date 2013-04-17 下午10:35:57
	 */
	@JSON
	public String confirmPDAUnloadTask(){
		//获取前台提交的数据
			try{
				String unloadTaskNo = unloadTaskVo.getUnloadTaskNo();
				Date endDate = new Date();
				unloadTaskVo.setUnloadEndDate(endDate);
				//调用service，确认卸车任务
				unloadTaskService.confirmPDAUnloadTask(unloadTaskNo, endDate);
			}catch(BusinessException e){
				//返回业务异常信息
				return returnError(e);
			}
			//返回success
			return returnSuccess();
	}
	

	/**
	 * @description FOSS同步确认卸车任务到悟空
	 * @return 
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午5:21:27
	 */
	/*private void expressConfirmUnloadTask(ConfirmUnloadTaskDto confirmUnloadTaskDto){
		
		try {
			 //FOSS同步确认卸车任务到悟空
			 unloadTaskService.confirmExpressUnloadTask(confirmUnloadTaskDto);
		} catch (Exception e) {
			LOG.error(e.getMessage(), "");
			throw new TfrBusinessException(e.getMessage());
		}
			
	}*/
	
	/**
	 * 长途卸车任务确认界面，根据配载单号获取交接单列表
	 * @author 045923-foss-shiwei
	 * @date 2012-12-21 下午3:51:30
	 */
	@JSON
	public String queryHandOverBillListByVehicleAssembleNo(){
		//获取前台车次号
		String vehicleAssembleNo = unloadTaskVo.getVehicleAssembleNo();
		//如果是快递货
		if(UnloadCommonUtils.isExpressHandOver(vehicleAssembleNo)){
			unloadTaskVo.setHandOverBillNo(vehicleAssembleNo);
			List<HandOverBillDetailEntity> handOverBillDetailList= expressQueryWaybillListByHandOverBillNo(unloadTaskVo);
			List<QueryHandOverBillDto> handOverBillList=new ArrayList<QueryHandOverBillDto>();
			QueryHandOverBillDto queryHandOverBillDto=null;
			for(HandOverBillDetailEntity handOverBillDetailEntity:handOverBillDetailList){
				queryHandOverBillDto=new QueryHandOverBillDto();
				//设置 设置配载单号  
				queryHandOverBillDto.setVehicleAssembleNo(handOverBillDetailEntity.getVehicleAssembleNo());
				//设置 交接单号
				queryHandOverBillDto.setHandOverBillNo(handOverBillDetailEntity.getHandOverBillNo());
				//设置 件类型
				queryHandOverBillDto.setCargoType(handOverBillDetailEntity.getCargoType());
				//设置 件号
				queryHandOverBillDto.setCargoNo(handOverBillDetailEntity.getCargoNo());
				handOverBillList.add(queryHandOverBillDto);
			}
			unloadTaskVo.setHandOverBillList(handOverBillList);
		}else{
			//返回交接单列表
			unloadTaskVo.setHandOverBillList(unloadTaskService.queryHandOverBillListByVehicleAssembleNo(vehicleAssembleNo));
		}
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 添加多货时，校验输入的运单号、流水号是否合法
	 * @author 045923-foss-shiwei
	 * @date 2012-12-23 上午11:42:56
	 */
	@JSON
	public String validateWaybillNoAndSerialNo(){
		    //获取前台传入的卸车任务编号
		    String  unloadTaskNo=unloadTaskVo.getUnloadTaskNo();
			//获取前台传入的、运单号/包号/笼号
			String waybillNo = unloadTaskVo.getWaybillNo();
		
			Map<String,String> map=new HashMap<String,String>();
			//卸车任务编号
			map.put("unloadTaskNo",unloadTaskNo);
			//设置 运单编号
			map.put("cargoNo", waybillNo);
			//设置 部门编号
			map.put("orgCode", FossUserContext.getCurrentDeptCode());
			/**此段注释代码用于零担快递拆分*/
			//如果是快递运单
			/*if(UnloadCommonUtils.isExpressWayBill(waybillNo)){
				
				FossToWKResponseDto fossToWKResponseDto=null;
				boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
				LOG.error("添加多货时，校验输入的运单号、流水号是否合法开关" + tfrSwitch4Ecs);
				if(tfrSwitch4Ecs){
				    fossToWKResponseDto=validateExpressWaybillNoAndSerialNo(map);
				}
				
				
				boolean isExpressExist=false;
				if(null!=fossToWKResponseDto){
					isExpressExist="1".equals(fossToWKResponseDto.getStatus())?true:false;	
				}
				if(isExpressExist){
					//运单号、流水号的组合存在、且不存在于本次卸车任务中
					unloadTaskVo.setIsAddedNosRight(2);
					return returnSuccess(JSONObject.fromObject(fossToWKResponseDto.getData()).toString());
				}else{
					return returnSuccess(JSONObject.fromObject(fossToWKResponseDto).toString());
				}
			}else{
				//获取前台传入的流水号
				String serialNo = unloadTaskVo.getSerialNo();
				//获取前台传入的卸车任务ID
				String unloadTaskId = unloadTaskVo.getUnloadTaskId();
				//获取前台传入的卸车任务类型
				String unloadType = unloadTaskVo.getUnloadType();
				//判断运单号、流水号是否存在
				if(unloadTaskService.validateWaybillNoAndSerialNo(waybillNo, serialNo) == 0){
					//运单号、流水号的组合不存在
					unloadTaskVo.setIsAddedNosRight(0);
					//返回success
					return returnSuccess();
				}
				//判断运单号、流水号是否存在于本次卸车任务中
				if(unloadTaskService.validateWaybillNoAndSerialNoIsInUnloadTask(unloadTaskId, unloadType, waybillNo, serialNo) != 0){
					//运单号、流水号存在于本次卸车任务中
					unloadTaskVo.setIsAddedNosRight(1);
					return returnSuccess();
				}
				//运单号、流水号的组合存在、且不存在于本次卸车任务中
				unloadTaskVo.setIsAddedNosRight(2);
				return returnSuccess();
			}*/
			//获取前台传入的流水号
			String serialNo = unloadTaskVo.getSerialNo();
			//获取前台传入的卸车任务ID
			String unloadTaskId = unloadTaskVo.getUnloadTaskId();
			//获取前台传入的卸车任务类型
			String unloadType = unloadTaskVo.getUnloadType();
			
			FossToWKResponseDto fossToWKResponseDto=null;
			
			boolean tfrSwitch4Ecs = configurationParamsService.queryTfrSwitch4Ecs();
			LOG.error("验证快递运单/包/笼是否合法开关" + tfrSwitch4Ecs);
			if(tfrSwitch4Ecs){
				if(UnloadCommonUtils.isExpressWayBill(waybillNo)){
					//添加快递多货时，校验输入的运单号、流水号是否合法
					fossToWKResponseDto=validateExpressWaybillNoAndSerialNo(map);
				}
			}
			
			/**已下代码用于零担快递并行*/
			if(null!=fossToWKResponseDto){
				 /**
				  * 判断运单号、流水号是否存在
				  * 当返回状态为0时，并且exMsg 为2时说明快递中该包号/笼号/运单号不存在
				  * 当waybillFlag为0时说明零担系统中没有该运单（包括老系统开的快递单）
				  */
				if(unloadTaskService.validateWaybillNoAndSerialNo(waybillNo, serialNo)== 0
						&&"0".equals(fossToWKResponseDto.getStatus())&&"2".equals(fossToWKResponseDto.getExMsg())){
					//运单号、流水号的组合不存在
					unloadTaskVo.setIsAddedNosRight(0);
					//返回success
					return returnSuccess();
				}
				/**
				 * 判断运单号、流水号是否存在于本次卸车任务中
				 * 当返回状态为0时，并且exMsg 为1时说明快递中该包号/笼号/运单号存在并且在本次卸车任务中
				 */
				if(unloadTaskService.validateWaybillNoAndSerialNoIsInUnloadTask(unloadTaskId, unloadType, waybillNo, serialNo) != 0
						||("0".equals(fossToWKResponseDto.getStatus())&&"1".equals(fossToWKResponseDto.getExMsg()))){
					//运单号、流水号存在于本次卸车任务中
					unloadTaskVo.setIsAddedNosRight(1);
					return returnSuccess();
				}
				
				if("1".equals(fossToWKResponseDto.getStatus())){
					//包/笼/运单存在、且不存在于本次卸车任务中
					unloadTaskVo.setIsAddedNosRight(2);
					return returnSuccess(JSONObject.fromObject(fossToWKResponseDto.getData()).toString());
				}
				    //运单号、流水号的组合存在、且不存在于本次卸车任务中
					unloadTaskVo.setIsAddedNosRight(2);	
				
				return returnSuccess();
				
			}else{
				//判断运单号、流水号是否存在
				if(unloadTaskService.validateWaybillNoAndSerialNo(waybillNo, serialNo)== 0){
					//运单号、流水号的组合不存在
					unloadTaskVo.setIsAddedNosRight(0);
					//返回success
					return returnSuccess();
				}
				//判断运单号、流水号是否存在于本次卸车任务中
				if(unloadTaskService.validateWaybillNoAndSerialNoIsInUnloadTask(unloadTaskId, unloadType, waybillNo, serialNo) != 0){
					//运单号、流水号存在于本次卸车任务中
					unloadTaskVo.setIsAddedNosRight(1);
					return returnSuccess();
				}
				//运单号、流水号的组合存在、且不存在于本次卸车任务中
				unloadTaskVo.setIsAddedNosRight(2);
				return returnSuccess();
			}
	}
	
	
	
	/**
	 * 添加快递多货时，校验输入的运单号、流水号是否合法
	 * @author 328768-foss-gaojianfu
	 * @date 2016年5月16日 下午1:02:09
	 */
    private FossToWKResponseDto validateExpressWaybillNoAndSerialNo(Map<String,String> map){
		
    	FossToWKResponseDto fossToWKResponseDto=null;
		try {
			//验证快递 笼号/包号/运单号是否合法
			fossToWKResponseDto=unloadTaskService.validateExpressWaybillNoAndSerialNo(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fossToWKResponseDto;
	}
}