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
 *  PROJECT NAME  : tfr-stockchecking-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/server/dao/IStTaskDao.java
 *  
 *  FILE NAME          :IStTaskDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.server.action;
/**
 * 引入包
 */
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import org.apache.struts2.ServletActionContext;

/**
 * 引入包
 */
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
/**
 * 引入包
 */
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.server.service.IAssignUnloadTaskService;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.dto.ArriveBillDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskTotalDto;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.dto.AssignUnloadTaskDto;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.dto.LoaderDto;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.vo.AssignUnloadTaskVo;


/**
 * Action开发规范
 * 
 * 1.必须继承com.deppon.foss.framework.server.web.action.AbstractAction
 * 
 * 2.必须生成serialVersionUID
 * 
 * 3.类名必须以Action结尾
 * 
 * 4.前台传参必须封装到Vo中,不能直接使用Entity,Vo必须添加getter、setter方法
 * 
 * 5.方法上必须添加com.deppon.foss.framework.server.web.result.json.annotation.JSON @JSON 注解
 * 
 * 6.方法中必须try catch异常,成功调用returnSuccess系列重载函数,异常调用returnError系列重载函数
 * 
 * 7.禁止添加Service的getter方法
 * 
 * 8.禁止注入Dao、只允许注入Service
 */
public class AssignUnloadTaskAction extends AbstractAction{
	/** 
	 * The Constant serialVersionUID. 
	 */
	private static final long serialVersionUID = 850014849677520019L;
	/** 
	 * The assign unload task service. 
	 */
	private IAssignUnloadTaskService assignUnloadTaskService;
	/** 
	 * The vo. 
	 * 
	 */
	private AssignUnloadTaskVo vo;
	
	/** 导出Excel 文件流*/
	private InputStream excelStream;
	
	/** 导出Excel 文件名*/
	private String fileName;
	/**
	 * Index.
	 *
	 * @return the string
	 */
	public String index(){
		return "success";
	}
	/**
	 * Query arrive vehicle.
	 *
	 * @return the string
	 */
	@JSON
	public String queryArriveVehicle(){
	
		try{
			if (vo == null) {
				throw new TfrBusinessException("传入信息为空");
			}
			List<AssignUnloadTaskTotalDto> vehicles = assignUnloadTaskService.queryArriveVehicle(vo.getVehicle(),this.getLimit(), this.getStart());
			vo.setVehicles(vehicles);
			/*for(int i=0; i <= vehicles.size()-1;i++) {
				AssignUnloadTaskTotalDto vehicle = vehicles.get(i);
				//未分配总票数
	            assignWayBillQtyTotal = assignWayBillQtyTotal + vehicle.getWayBillQtyTotal();
	            //未分配总件数
	            assignGoodsQtyTotal = assignGoodsQtyTotal +vehicle.getGoodsQtyTotal(); 
	    		//未分配总重量
	            assignWeightTotal = assignWeightTotal + vehicle.getWeightTotal();
	    		//未分配总体积
	            assignVolumeTotal = assignVolumeTotal +vehicle.getVolumeTotal(); 
			}
			vo.setAssignGoodsQtyTotal(assignGoodsQtyTotal);
			vo.setAssignVolumeTotal(assignVolumeTotal);
			vo.setAssignWayBillQtyTotal(assignWayBillQtyTotal);
			vo.setAssignWeightTotal(assignWeightTotal);*/
			this.setTotalCount(totalCount);
			/*String remind =  assignUnloadTaskService.queryArriveBillCount();
			vo.setRemind(remind);*/
			return this.returnSuccess();
		}catch(TfrBusinessException e){
			return this.returnError(e);
		}
	}
	
	/**
	 * Query arrive vehicle.
	 *
	 * @return the string
	 */
	@JSON
	public String queryArriveVehicletotal(){
		//未分配总票数
		int assignWayBillQtyTotal = 0;
		//未分配总件数
		int assignGoodsQtyTotal = 0; 
		//未分配总重量
		double assignWeightTotal = 0;
		//未分配总体积
		double assignVolumeTotal = 0; 
		try{
			if (vo == null) {
				throw new TfrBusinessException("传入信息为空");
			}
			List<AssignUnloadTaskTotalDto> vehicles = assignUnloadTaskService.queryArriveVehicle(vo.getVehicle());
			vo.setVehicles(vehicles);
			for(int i=0; i <= vehicles.size()-1;i++) {
				AssignUnloadTaskTotalDto vehicle = vehicles.get(i);
				//未分配总票数
	            assignWayBillQtyTotal = assignWayBillQtyTotal + vehicle.getWayBillQtyTotal()+vehicle.getExpressWayBillQty();
	            //未分配总件数
	            assignGoodsQtyTotal = assignGoodsQtyTotal +vehicle.getGoodsQtyTotal(); 
	    		//未分配总重量
	            assignWeightTotal = assignWeightTotal + vehicle.getWeightTotal();
	    		//未分配总体积
	            assignVolumeTotal = assignVolumeTotal +vehicle.getVolumeTotal(); 
			}
			vo.setAssignGoodsQtyTotal(assignGoodsQtyTotal);
			vo.setAssignVolumeTotal(assignVolumeTotal);
			vo.setAssignWayBillQtyTotal(assignWayBillQtyTotal);
			vo.setAssignWeightTotal(assignWeightTotal);
			this.setTotalCount(totalCount);
			String remind =  assignUnloadTaskService.queryArriveBillCount();
			vo.setRemind(remind);
			return this.returnSuccess();
		}catch(TfrBusinessException e){
			return this.returnError(e);
		}
	}
	
	/**
	 * export Assign unloadtask.
	 *
	 * @return the string
	 */
	public String exportAssignunloadtask(){
		try {
			if (vo == null) {
				throw new TfrBusinessException("传入信息为空");
			}
			fileName = encodeFileName("未分配卸车任务");
			excelStream = assignUnloadTaskService.exportAssignunloadtask(vo.getVehicle());
		} catch (BusinessException e) {
			return returnError(e);
		} catch (UnsupportedEncodingException e) {
			return returnError(new TfrBusinessException("转换字符串编码时出错!"));
		}
		return returnSuccess();
	}
	
	/**
	 * export Assign unloadtask.
	 *
	 * @return the string
	 */
	public String exportAssignunloadedtask(){
		try {
			if (vo == null) {
				throw new TfrBusinessException("传入信息为空");
			}
			fileName = encodeFileName("分配卸车任务");
			excelStream = assignUnloadTaskService.exportAssignunloadedtask(vo.getTask());
		} catch (BusinessException e) {
			return returnError(e);
		} catch (UnsupportedEncodingException e) {
			return returnError(new TfrBusinessException("转换字符串编码时出错!"));
		}
		return returnSuccess();
	}
	
	
	public String encodeFileName(String fileName)
			throws UnsupportedEncodingException {
		String returnStr;
		String agent = (String) ServletActionContext.getRequest().getHeader(
				"USER-AGENT");
		if (agent != null && agent.indexOf("MSIE") == -1) {
			returnStr = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
		} else {
			returnStr = URLEncoder.encode(fileName, "UTF-8");
		}
		return returnStr;
	}
	
	/**
	 * Query arrive bill.
	 *
	 * @return the string
	 */
	@JSON
	public String queryArriveBill(){
		try{
			if (vo == null) {
				throw new TfrBusinessException("传入信息为空");
			}
			List<ArriveBillDto> bills = assignUnloadTaskService.queryArriveBill(vo.getBill());
			vo.setBills(bills);
			return this.returnSuccess();
		}catch(TfrBusinessException e){
			return this.returnError(e);
		}
	}
	
	/**
	 * Query loader.
	 *
	 * @return the string
	 */
	@JSON
	public String queryLoader(){
		if (vo == null) {
			throw new TfrBusinessException("传入分配信息为空");
		}
		try{
			List<LoaderDto> loaders = assignUnloadTaskService.queryLoader(vo.getLoader(), this.getLimit(), this.getStart());
			vo.setLoaders(loaders);
			Long totalCount = assignUnloadTaskService.getLoaderCount(vo.getLoader());
			this.setTotalCount(totalCount);
			return this.returnSuccess();
		}catch(TfrBusinessException e){
			return this.returnError(e);
		}
	}
	
	/**
	 * Assign.
	 *
	 * @return the string
	 */
	@JSON
	public String assign(){
		if (vo == null) {
			throw new TfrBusinessException("传入分配信息为空，请勾选相应查询条件");
		}
		try{
			assignUnloadTaskService.insertAssignUnloadTask(vo.getLoader(), vo.getVehicle(), vo.getBills());
			//expressAssign(vo);
			return this.returnSuccess();
		}catch(TfrBusinessException e){
			return this.returnError(e);
		}
	}
	
	/**
	* @description FOSS同步分配卸车任务到悟空系统
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年4月29日 下午2:37:11
	 */
   
	/*private void expressAssign(AssignUnloadTaskVo vo){
		
		try {
			assignUnloadTaskService.assignUnloadTask(vo);
		} catch (Exception e) {
			LOG.error(e.getMessage(), "");
			e.printStackTrace();
		}
			
    }*/
	
	/**
	 * Refresh assigned task list.
	 *
	 * @return the string
	 */
	@JSON
	public String refreshAssignedTaskList(){
		try{
			List<AssignUnloadTaskTotalDto> tasks =  assignUnloadTaskService.queryUnStartTask( this.getLimit(), this.getStart());
			vo = new AssignUnloadTaskVo();
			vo.setTotalTasks(tasks);
			this.setTotalCount(assignUnloadTaskService.queryUnStartTaskCount());
			return this.returnSuccess();
		}catch(TfrBusinessException e){
			return this.returnError(e);
		}
	}
	
	/**
	 * Refresh assigned task detail.
	 *
	 * @return the string
	 */
	@JSON
	public String refreshAssignedTaskDetail(){
		try{
			if (vo == null) {
				throw new TfrBusinessException("传入信息为空");
			}
			AssignUnloadTaskDto task =  assignUnloadTaskService.refreshAssignedTaskDetail(vo.getTask());
			vo = new AssignUnloadTaskVo();
			vo.setTask(task);
			return this.returnSuccess();
		}catch(TfrBusinessException e){
			return this.returnError(e);
		}
	}
	
	/**
	 * Cancel assigned unload task.
	 *
	 * @return the string
	 */
	@JSON
	public String cancelAssignedUnloadTask(){
		try{
			if (vo == null) {
				throw new TfrBusinessException("传入信息为空");
			}
			assignUnloadTaskService.cancelAssignUnloadTask(vo.getTask());
			//expressCancelAssignedUnloadTask(vo);
			return this.returnSuccess();
		}catch(TfrBusinessException e){
			return this.returnError(e);
		}
	}
	
	/**
	 * @description Foss同步取消分配卸车任务到悟空
	 * @param vo
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午4:03:35
	 */
	/*private void expressCancelAssignedUnloadTask(AssignUnloadTaskVo vo){
		
		//新建取消卸载任务DTO
		ExpressCancelAssignUnloadInstructDto cancelAssignUnloadInstructDto=new ExpressCancelAssignUnloadInstructDto();
		//设置  分配卸车指令编号
		cancelAssignUnloadInstructDto.setCommandNo(vo.getTask().getId());
		//设置  修改时间
		cancelAssignUnloadInstructDto.setUpdateTime(new Date());
		try {
			assignUnloadTaskService.cancelAssignedUnloadTask(cancelAssignUnloadInstructDto);
		} catch (Exception e) {
			LOG.error(e.getMessage(), "");
			e.printStackTrace();
		}
			
	}*/
	
	/**
	 * Query assign unload task.
	 *
	 * @return the string
	 */
	@JSON
	public String queryAssignUnloadTask(){
		try{
			if (vo == null) {
				throw new TfrBusinessException("传入分配信息为空");
			}
			List<AssignUnloadTaskTotalDto> tasks =  assignUnloadTaskService.queryAssignUnloadTask(vo.getTask(), this.getLimit(), this.getStart());
			vo.setTotalTasks(tasks);
			Long totalCount = assignUnloadTaskService.queryAssignUnloadTaskCount(vo.getTask());
			this.setTotalCount(totalCount);
			return this.returnSuccess();
		}catch(TfrBusinessException e){
			return this.returnError(e);
		}
	}
	
	/**
	 * Query assign unload task detail.
	 *
	 * @return the string
	 */
	@JSON
	public String queryAssignUnloadTaskDetail(){
		try{
			if (vo == null) {
				throw new TfrBusinessException("传入信息为空");
			}
			List<ArriveBillDto> bills = assignUnloadTaskService.queryAssignUnloadTaskDetail(vo.getTask());
			vo.setBills(bills);
			return this.returnSuccess();
		}catch(TfrBusinessException e){
			return this.returnError(e);
		}
	}
	
	/**
	 * 查询预分配月台号
	 * */
	@JSON
   public String queryPrePlatformNo(){
	  try {
		  if (vo == null) {
			  throw new TfrBusinessException("传入信息为空");
		  }
		  String vechicleNo=vo.getBill().getVehicleNo(); 
		  String billNo=vo.getBill().getBillNo();
		  vo.setPlatformNo(assignUnloadTaskService.queryPrePlatformNo(vechicleNo, billNo));
		  
		  return this.returnSuccess();
	} catch (TfrBusinessException e) {
		return this.returnError(e);
	}
		
		
   }
	
	
	/**
	 * Sets the assign unload task service.
	 *
	 * @param assignUnloadTaskService the new assign unload task service
	 */
	public void setAssignUnloadTaskService(
			IAssignUnloadTaskService assignUnloadTaskService) {
		this.assignUnloadTaskService = assignUnloadTaskService;
	}
	
	/**
	 * Gets the vo.
	 *
	 * @return the vo
	 */
	public AssignUnloadTaskVo getVo() {
		return vo;
	}
	
	/**
	 * Sets the vo.
	 *
	 * @param vo the new vo
	 */
	public void setVo(AssignUnloadTaskVo vo) {
		this.vo = vo;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public InputStream getExcelStream() {
		return excelStream;
	}
	public String getFileName() {
		return fileName;
	}
}
