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
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  PROJECT NAME  : tfr-stock
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/server/action/StockAction.java
 *  
 *  FILE NAME          :StockAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stock.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.ChangeGoodsAreaEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsDepartmentEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.MoveGoodsEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.NewAndOldGoodsAreaEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.dto.MoveGoodsStockDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.MoveGoodsStockQueryDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.StockOrgDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockQueryDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockStatisticsDto;
import com.deppon.foss.module.transfer.stock.api.shared.vo.StockVO;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 查询库存
 * 查询必走货
 * 界面跳转Action
 * @author 097457-foss-wangqiang
 * @date 2012-10-12 上午8:48:29
 */
public class StockAction extends AbstractAction{
	private static final Logger LOGGER = LoggerFactory.getLogger(StockAction.class);
	/**序列化*/
	private static final long serialVersionUID = 6065950964734465473L;
	/** 库存service*/
	private IStockService stockService;
	/** 查询库存界面VO*/
	private StockVO stockVO = new StockVO();
	/** 导出Excel 文件流*/
	private InputStream excelStream;  
	/** 导出Excel 文件名*/
	private String fileName;  
	/** 
	 * 根据部门编码查询组织架构
	 * 
	 * */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * 获取快递员车辆绑定的开单营业部 
	 */
    private IExpressVehiclesService expressVehiclesService;
	public void setExpressVehiclesService(
			IExpressVehiclesService expressVehiclesService) {
		this.expressVehiclesService = expressVehiclesService;
	}
	/**
	 * 
	 * 和判断登录的是不是快递员
	 */
	private ICommonExpressEmployeeService commonExpressEmployeeService;
	public void setCommonExpressEmployeeService(
			ICommonExpressEmployeeService commonExpressEmployeeService) {
		this.commonExpressEmployeeService = commonExpressEmployeeService;
	}
	/**
	 * 分页查询运单库存
	 * @param stockVO.getWaybillStockEntity() 运单库存参数
	 * @param stockVO.getBeginInStockTime() 开始时间
	 * @param stockVO.getEndInStockTime() 截止时间
	 * @param this.getLimit() 每页条数
	 * @param this.getStart() 开始条数
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-16 上午11:40:27
	 */
	@JSON
	public String queryWaybillStock(){
		//查询运单库存
		List<WaybillStockQueryDto> waybillStockList = stockService.queryWaybillStock(stockVO.getWaybillStockEntity(),
				stockVO.getBeginInStockTime(),stockVO.getEndInStockTime(), this.getLimit(), this.getStart());
		//设置查询结果
		stockVO.setWaybillStockList(waybillStockList);
		//总记录数
		Long totalCount = stockService.queryCount(stockVO.getWaybillStockEntity(),stockVO.getBeginInStockTime(),stockVO.getEndInStockTime());
		this.setTotalCount(totalCount);
		//查询统计栏数据
		WaybillStockStatisticsDto waybillStockStatisticsDto = stockService.queryWaybillStockStatistics(stockVO.getWaybillStockEntity(), stockVO.getBeginInStockTime(), stockVO.getEndInStockTime());
		//设置统计数据
		stockVO.setWaybillStockStatisticsDto(waybillStockStatisticsDto);
		return returnSuccess();
	}
	
	
	/**
	 * 查询货件库存
	 * @param stockVO.getWaybillStockEntity().waybillNo 运单号
	 * @param stockVO.getWaybillStockEntity().goodsAreaCode 货区
	 * @param stockVO.getWaybillStockEntity().orgCode 部门
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-16 上午11:39:19
	 */
	public String queryStock(){
		List<StockEntity> stockList = stockService.queryStock(stockVO.getWaybillStockEntity());
		stockVO.setStockList(stockList);
		return returnSuccess();
	}
	
	/**
	 * PC端单件入库
	 * @author dp-wangqiang
	 * @date 2012-10-12 上午8:52:51
	 */
	public String inStock(){
		//工号
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		//姓名
		String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		//获取前台入库信息
		InOutStockEntity inOutStockEntity = stockVO.getInOutStockEntity();
		//设置工号
		inOutStockEntity.setOperatorCode(userCode);
		//设置姓名
		inOutStockEntity.setOperatorName(userName);
		try{
			//入库
			stockService.inStockSerialNOs(inOutStockEntity, stockVO.getSerialNOs(), stockVO.getConfirmFlag(), stockVO.getInStockConfirmFlag());
			return returnSuccess();
		}catch(BusinessException e){
			//设置异常CODE
			stockVO.setExceptionCode(e.getErrorCode());
			return returnError(e);
		}
		
	}
	/**
	 * 出库
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-22 上午10:01:18
	 */
	public String outStock(){
		//工号
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		//姓名
		String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		try{
			//出库
			stockService.outStockList(stockVO.getOutStockList(), userCode, userName);
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 查询货件
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-18 下午4:22:27
	 */
	public String queryGoods(){
		//获取查询参数
		InOutStockEntity inOutStockEntity = stockVO.getInOutStockEntity();
		//查询货件
		List<StockEntity> stockList = stockService.queryGoods(inOutStockEntity,FossUserContext.getCurrentDeptCode());
		//设置查询结果
		stockVO.setStockList(stockList);
		return returnSuccess();
	}
	/**
	 * 将货件从特殊货区出库
	 * 入库到正常货区
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-19 上午10:08:51
	 */
	public String logoutSpecialGoodsArea(){
		//工号
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		//姓名
		String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		try{
			//登出
			stockService.logoutSpecialGoodsArea(stockVO.getOutStockList(), userCode, userName);
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 将库存查询结果导出到Excel文件
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-26 上午10:42:37
	 */
	public String exportExcel(){
		try{
			//文件名
			//fileName = stockService.encodeFileName("库存清单");
			try {
				fileName = this.encodeFileName("库存清单");
			} catch (UnsupportedEncodingException e) {
				//sonar-352203
				LOGGER.info("StockAction.exportExcel 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
			}
			//********获取文件流**********
			//stockVO.getIds() 勾选的库存记录ID
			//stockVO.getWaybillStockEntity() 库存信息
			//stockVO.getBeginInStockTime() 开始时间
			//stockVO.getEndInStockTime() 截止时间
			excelStream = stockService.exportExcelStream(stockVO.getIds(),stockVO.getWaybillStockEntity(),
					stockVO.getBeginInStockTime(),stockVO.getEndInStockTime());
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 导出必走货一级记录
	 * @author foss-wuyingjie
	 * @date 2013-04-25 上午11:26:22
	 */
	public String exportPriorityGoods(){
		try{
			//文件名
			//fileName = stockService.encodeFileName("必走货信息");
			try {
				fileName = this.encodeFileName("必走货信息");
			} catch (UnsupportedEncodingException e) {
				//sonar-352203
				LOGGER.info("StockAction.exportPriorityGoods 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
			}

			excelStream = stockService.exportPriorityGoods(stockVO.getWaybillStockQueryDto());
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 分页查询必走货
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-30 下午3:20:33
	 */
	public String queryPriorityGoods(){
		//查询必走货
		List<WaybillStockQueryDto> priorityGoodsList = stockService.queryPriorityGoods(stockVO.getWaybillStockQueryDto(),this.getLimit(),this.getStart());
		//设置查询结果
		stockVO.setWaybillStockList(priorityGoodsList);
		//总数
		Long totalCount = stockService.queryPriorityGoodsCount(stockVO.getWaybillStockQueryDto());
		this.setTotalCount(totalCount);
		//查询统计栏数据
		WaybillStockStatisticsDto waybillStockStatisticsDto = stockService.queryPriorityGoodsStatistics(stockVO.getWaybillStockQueryDto());
		//设置统计数据
		stockVO.setWaybillStockStatisticsDto(waybillStockStatisticsDto);
		return returnSuccess();
	}
	
	/**
	 * 获取产品List
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-4 下午7:36:18
	 */
	public String queryProductList(){
		//设置产品List
		stockVO.setProductList(stockService.queryProductList());
		return returnSuccess();
	}
	
	/**
	* @return
	* @description 检验货区是否是驻地派送部的货区
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-10 下午3:16:35
	*/
	public String checkGoodsArea(){
		OrgAdministrativeInfoEntity stockOrg = stockService.queryStockOrg(FossUserContext.getCurrentDept());
		if(stockService.checkGoodsArea(stockVO.getGoodsAreaCode(), stockOrg.getCode())){
			return returnSuccess();
		}else{
			return returnError("货区不是驻地派送部的货区");
		}
		
	}
	
	/**
	* @return
	* @description 根据当前部门所在的外场查询对应的货区
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-9 上午11:12:26
	*/
	public String areaByOrgcodeList(){
//		OrgAdministrativeInfoEntity stockOrg = stockService.queryStockOrg(FossUserContext.getCurrentDept());
		String orgCode="";
		CurrentInfo user = FossUserContext.getCurrentInfo();//获取当前登录的信息
		if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null)                {//判断是不是快递员
			 orgCode=expressVehiclesService.queryExpressVehiclesByCode(user.getEmpCode());
		}else{
			 orgCode = stockService.transferCenterCodeByBigOrg(FossUserContext.getCurrentDept());
		}
		
		List<BaseDataDictDto> list = stockService.queryGoodsAreaListByOrganizationCode(orgCode);
		stockVO.setAreaByOrgcodeList(list);
		return returnSuccess();
	}
	
	
	/**
	* @description 根据驻地部门code查询对应的驻地库区
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年6月8日 下午6:40:41
	*/
	public String stationAreaByOrgcodeList(){
		CurrentInfo user = FossUserContext.getCurrentInfo();//获取当前登录的信息
		String orgCode = "";
		if(stockService.ifIsExpressWaybill(stockVO.getWaybillNOs())){//判断是不是快递
			System.out.println("快递员登录------");
			orgCode=expressVehiclesService.queryExpressVehiclesByCode(user.getEmpCode());
		}else{
			orgCode=FossUserContext.getCurrentDept().getCode();
		}
		List<BaseDataDictDto> list = stockService.stationAreaByOrgcodeList(orgCode);
		stockVO.setAreaByOrgcodeList(list);
		return returnSuccess();
	}
	
	/**
	* @return 库位的集合
	* @description 获取库位的集合
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-8 上午11:20:44
	*/
	public String queryPositionList(){
//		OrgAdministrativeInfoEntity stockOrg = stockService.queryStockOrg(FossUserContext.getCurrentDept());
		String orgCode = stockService.transferCenterCodeByBigOrg(FossUserContext.getCurrentDept());
		List<BaseDataDictDto> list = stockService.queryStorageListByGoodsAreaFrom(orgCode, stockVO.getGoodsAreaCode());
		stockVO.setPositionList(list);
		return returnSuccess();
	}
	/**
	 * 获取特殊货区List
	 * 贵重物品货区
	 * 包装货区
	 * 异常货区
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-4 下午7:35:54
	 */
	public String querySpecialAreaList(){
		//查询特殊货区
		List<BaseDataDictDto> specialAreaList = stockService.querySpecialAreaList(stockVO.getStockOrgCode());
		stockVO.setSpecialAreaList(specialAreaList);
		return returnSuccess();
	}
	/**
	 * 获取当前用户的库存部门
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-6 下午5:53:44
	 */
	public String queryStockOrgCode(){
		try{
			//根据当前部门查询库存部门信息
			OrgAdministrativeInfoEntity stockOrg = stockService.queryStockOrg(FossUserContext.getCurrentDept());
			//设置部门CODE
			stockVO.setStockOrgCode(stockOrg.getCode());
			//设置部门名称
			stockVO.setStockOrgName(stockOrg.getName());
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 获取部门信息：
	 *   编号
	 *   是否是外场
	 *   特殊货区List
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-4 上午11:21:12
	 */
	public String queryOrgCodeInfo(){
		try{
			CurrentInfo user = FossUserContext.getCurrentInfo();//获取当前登录的信息
			StockOrgDto stockOrgDto = null;
			if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null){
				//根据快递员的工号查找快递员车辆绑定的开单营业部编号
				String code = expressVehiclesService.queryExpressVehiclesByCode(user.getEmpCode());
				//根据营业部编号获取组织架构
				OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(code);
				stockOrgDto = stockService.queryStockOrgInfo(orgEntity);
			}else{
			//根据当前部门查询库存信息
				stockOrgDto = stockService.queryStockOrgInfo(FossUserContext.getCurrentDept());
			}
			//设置部门CODE
			stockVO.setStockOrgCode(stockOrgDto.getOrgCode());
			//设置部门名称
			stockVO.setStockOrgName(stockOrgDto.getOrgName());
			//外场
			if(StringUtils.endsWith(FossConstants.YES, stockOrgDto.getIsTransferCenter())){
				//是否外场
				stockVO.setIsTransferCenter(FossConstants.YES);
				//查询特殊货区
				List<BaseDataDictDto> specialAreaList = stockService.querySpecialAreaList(stockOrgDto.getOrgCode());
				stockVO.setSpecialAreaList(specialAreaList);
			}
			//驻地派送部
			if(StringUtils.endsWith(FossConstants.YES, stockOrgDto.getIsStationDelivery())){
				//是否是驻地派送部
				stockVO.setIsStationDelivery(FossConstants.YES);
				//货区CODE
				stockVO.setGoodsAreaCode(stockOrgDto.getGoodsAreaCode());
				//货区名称
				stockVO.setGoodsAreaName(stockOrgDto.getGoodsAreaName());
			}
			//空运总调
			if(StringUtils.endsWith(FossConstants.YES, stockOrgDto.getIsAirDispatch())){
				//是否空运总调
				stockVO.setIsAirDispatch(FossConstants.YES);
				//货区CODE
				stockVO.setGoodsAreaCode(stockOrgDto.getGoodsAreaCode());
				//货区名称
				stockVO.setGoodsAreaName(stockOrgDto.getGoodsAreaName());
			}
			
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 散货货区货物入库
	 * 从散货区出库
	 * 入库到正常货区
	 * @author 097457-foss-wangqiang
	 * @date 2013-2-26 上午10:45:31
	 */
	public String bulkGoodsInStock(){
		//工号
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		//姓名
		String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		try{
			//散货入库
			stockService.bulkGoodsInStock(stockVO.getInStockList(),userCode,userName);
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	* @return
	* @description 更新当前外场编码对应货区的库位
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-8 上午9:32:42
	*/
	public String updatePosition(){
		//部门编号
		String orgCode = FossUserContext.getCurrentDept().getCode();
		//工号
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		//姓名
		String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		try{
			stockService.updateStockStockPosition(stockVO.getInStockList(),stockVO.getPosition(),orgCode,userCode,userName);
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 设置 库存service.
	 *
	 * @param stockService the new 库存service
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}


	/**
	 * 获取 查询库存界面VO.
	 *
	 * @return the 查询库存界面VO
	 */
	public StockVO getStockVO() {
		return stockVO;
	}

	/**
	 * 设置 查询库存界面VO.
	 *
	 * @param stockVO the new 查询库存界面VO
	 */
	public void setStockVO(StockVO stockVO) {
		this.stockVO = stockVO;
	}


	/**
	 * 获取 导出Excel 文件流.
	 *
	 * @return the 导出Excel 文件流
	 */
	public InputStream getExcelStream() {
		return excelStream;
	}


	/**
	 * 设置 导出Excel 文件流.
	 *
	 * @param excelStream the new 导出Excel 文件流
	 */
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}


	/**
	 * 获取 导出Excel 文件名.
	 *
	 * @return the 导出Excel 文件名
	 */
	public String getFileName() {
		return fileName;
	}


	/**
	 * 设置 导出Excel 文件名.
	 *
	 * @param fileName the new 导出Excel 文件名
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	 /**
     * 转换导出文件的文件名
    * @param name
    * @return
    * @throws UnsupportedEncodingException
    * @description 转换导出文件的文件名
    * @version 1.0
    * @author 14022-foss-songjie
    * @update 2013年09月29日 上午10:30:50
    */
    public String encodeFileName(String name) throws UnsupportedEncodingException {
    	String returnStr;
    	String agent = (String)ServletActionContext.getRequest().getHeader("USER-AGENT");
    	if(agent != null && agent.indexOf("MSIE") == -1) {
    		returnStr = new String(name.getBytes("UTF-8"), "iso-8859-1");
    	} else {
    		returnStr = URLEncoder.encode(name, "UTF-8");
    	}
    	return returnStr;
    }
//**************************************************我是分割线,下面是库存迁移****************************
    /**
	 * 
	* @description 查询库存迁移
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-13 下午2:32:08
	 */
	public String queryMoveGoodsStock(){  
		//工号
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		stockVO.getMoveGoodsStockDto().setApplicant_code(userCode);
		//根据code查询用户的角色
		List<UserOrgRoleEntity> userOrgRoleEntityList = stockService.queryOrgRoleByCode(userCode);
		for(UserOrgRoleEntity u : userOrgRoleEntityList){
			//这个是场地搬迁信息维护员对应的角色code 是FOSS_LD_KCQYCZY
			if(u.getRoleCode().equals("FOSS_LD_KCQYCZY")){
				stockVO.getMoveGoodsStockDto().setIsMoveGoodsMan("Y");
				break;
			}
		}
		List<MoveGoodsStockQueryDto> moveGoodsStockQueryDtoList = stockService.queryMoveGoods(stockVO.getMoveGoodsStockDto(), this.getLimit(), this.getStart());
		stockVO.setMoveGoodsStockQueryDtoList(moveGoodsStockQueryDtoList);
		//查询总记录数
		Long totalCount = stockService.queryMoveGoodsCount(stockVO.getMoveGoodsStockDto());
		this.setTotalCount(totalCount);
		return returnSuccess();
	}
	

	
	/**
	* @description 撤销库存迁移申请(未审核和已退回)
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-19 下午2:45:01
	*/
	public String revocationStock(){
		//工号
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		//姓名
		String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		try{
			MoveGoodsEntity moveGoodsEntity = new MoveGoodsEntity();
			moveGoodsEntity.setId(stockVO.getMoveGoodsEntityId());
			//撤销申请
			stockService.revocationStock(moveGoodsEntity, userCode, userName);
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	
	/**
	* @description 审核库存迁移申请
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-23 下午5:38:23
	*/
	public String auditorStock(){
		//工号
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		//姓名
		String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		try{
			MoveGoodsEntity moveGoodsEntity = new MoveGoodsEntity();
			moveGoodsEntity.setId(stockVO.getMoveGoodsEntityId());
			//撤销申请
			stockService.auditorStock(moveGoodsEntity, userCode, userName);
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	
	/**
	* @description 作废库存迁移申请
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-23 下午7:24:45
	*/
	public String invalidateStock(){
		//工号
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		//姓名
		String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		try{
			MoveGoodsEntity moveGoodsEntity = new MoveGoodsEntity();
			moveGoodsEntity.setId(stockVO.getMoveGoodsEntityId());
			//撤销申请
			stockService.invalidateStock(moveGoodsEntity, userCode, userName);
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	
	/**
	* @description 退回库存迁移申请
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-23 下午7:37:52
	*/
	public String returnStock(){
		//工号
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		//姓名
		String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		try{
			MoveGoodsEntity moveGoodsEntity = new MoveGoodsEntity();
			moveGoodsEntity.setId(stockVO.getMoveGoodsEntityId());
			//撤销申请
			stockService.returnStock(moveGoodsEntity, userCode, userName);
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	
	/**
	* @description 根据id查询库存迁移明细
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-24 上午10:50:05
	*/
	public String viewMoveGoodsById(){
		try{
			String id = stockVO.getMoveGoodsEntityId();
			//撤销申请
			MoveGoodsStockQueryDto moveGoodsStockQueryDto = stockService.viewMoveGoodsById(id);
			stockVO.setMoveGoodsStockQueryDto(moveGoodsStockQueryDto);
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	
	/**
	* @description 根据部门code获取库区code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-25 下午5:40:35
	*/
	public String areaByOrgcode(){
		//部门code
		String orgCode = stockVO.getStockOrgCode();
		//部门name
		String orgName = stockVO.getStockOrgName();
		try {
			List<BaseDataDictDto> list = stockService.queryAreaCodeAndAreaNameByOrgCode(orgCode,orgName);
			stockVO.setAreaByOrgcodeList(list);
		}catch(BusinessException e){
			return returnError(e);
		}
		//根据部门code 获得货区code,name
		return returnSuccess();
	}
	
	
	/**
	* @description 将页面的数据写入数据库
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-27 下午5:53:18
	*/
	public String moveGoodsInStock(){
		int goodsType = stockVO.getMoveGoodsStockQueryDto().getGoods_type();
		String remarks = stockVO.getMoveGoodsStockQueryDto().getRemarks();
		String moveoutName = stockVO.getMoveGoodsStockQueryDto().getMoveout_name();
		String moveoutCode = stockVO.getMoveGoodsStockQueryDto().getMoveout_code();
		String moveoutAreaname = stockVO.getMoveGoodsStockQueryDto().getMoveout_areaname();
		String moveoutAreacode = stockVO.getMoveGoodsStockQueryDto().getMoveout_areacode();
		String moveinName = stockVO.getMoveGoodsStockQueryDto().getMovein_name();
		String moveinCode = stockVO.getMoveGoodsStockQueryDto().getMovein_code();
		String moveinAreaname = stockVO.getMoveGoodsStockQueryDto().getMovein_areaname();
		String moveinAreacode = stockVO.getMoveGoodsStockQueryDto().getMovein_areacode();
		
		//判断移出部门的库区的目的部门编码和移出部门的库区的目的部门编码是否一致,如果不一致,不允许迁移
		try {
			stockService.queryMoveOutCodeAndMoveInCodeIsEqual(moveoutCode,moveoutAreacode,moveinCode,moveinAreacode);
		} catch(BusinessException e){
			stockVO.setExceptionCode(e.getErrorCode());
			return returnError(e);
		}
		
		//申请人工号
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		//申请人姓名
		String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		//申请人部门编号
		String orgCode = FossUserContext.getCurrentDept().getCode();
		//申请人部门名称
		String orgName = FossUserContext.getCurrentDept().getName();
		//申请时间
		Date data = new Date();
		//主表uuid
		String id1 = UUIDUtils.getUUID();
		//副表uuid
		String id2 = UUIDUtils.getUUID();
		MoveGoodsEntity moveGoodsEntity = new MoveGoodsEntity();
		moveGoodsEntity.setId(id1);
		moveGoodsEntity.setState("1");
		moveGoodsEntity.setRemarks(remarks);
		moveGoodsEntity.setApplicant_time(data);
		moveGoodsEntity.setApplicant_name(userName);
		moveGoodsEntity.setApplicant_code(userCode);
		moveGoodsEntity.setOrg_name(orgName);
		moveGoodsEntity.setOrg_code(orgCode);
		
		MoveGoodsDepartmentEntity moveGoodsDepartmentEntity = new MoveGoodsDepartmentEntity();
		moveGoodsDepartmentEntity.setId(id2);
		moveGoodsDepartmentEntity.setT_OPT_MOVESTOCK_id(id1);
		moveGoodsDepartmentEntity.setGoods_type(goodsType);
		moveGoodsDepartmentEntity.setMoveout_code(moveoutCode);
		moveGoodsDepartmentEntity.setMoveout_name(moveoutName);
		moveGoodsDepartmentEntity.setMoveout_areacode(moveoutAreacode);
		moveGoodsDepartmentEntity.setMoveout_areaname(moveoutAreaname);
		moveGoodsDepartmentEntity.setMovein_code(moveinCode);
		moveGoodsDepartmentEntity.setMovein_name(moveinName);
		moveGoodsDepartmentEntity.setMovein_areacode(moveinAreacode);
		moveGoodsDepartmentEntity.setMovein_areaname(moveinAreaname);
		try {
			stockService.moveGoodsInStock(moveGoodsEntity,moveGoodsDepartmentEntity);
			return returnSuccess();
		} catch(BusinessException e){
			//设置异常CODE
			stockVO.setExceptionCode(e.getErrorCode());
			return returnError(e);
		}
	}
	
	
	/**
	* @description 向数据库中插入修改人的信息,及修改人修改的信息
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-30 上午10:15:22
	*/
	public String moveGoodsModifyInStock(){
		int goodsType = stockVO.getMoveGoodsStockQueryDto().getGoods_type();
		String remarks = stockVO.getMoveGoodsStockQueryDto().getRemarks();
		String moveoutName = stockVO.getMoveGoodsStockQueryDto().getMoveout_name();
		String moveoutCode = stockVO.getMoveGoodsStockQueryDto().getMoveout_code();
		String moveoutAreaname = stockVO.getMoveGoodsStockQueryDto().getMoveout_areaname();
		String moveoutAreacode = stockVO.getMoveGoodsStockQueryDto().getMoveout_areacode();
		String moveinName = stockVO.getMoveGoodsStockQueryDto().getMovein_name();
		String moveinCode = stockVO.getMoveGoodsStockQueryDto().getMovein_code();
		String moveinAreaname = stockVO.getMoveGoodsStockQueryDto().getMovein_areaname();
		String moveinAreacode = stockVO.getMoveGoodsStockQueryDto().getMovein_areacode();
		//修改人工号
		String modifyCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		//修改人姓名
		String modifyName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		//修改时间
		Date data = new Date();
		//主表id 
		String id = stockVO.getMoveGoodsStockQueryDto().getId();
		//主表Entity
		MoveGoodsEntity moveGoodsEntity = new MoveGoodsEntity();
		moveGoodsEntity.setId(id);
		moveGoodsEntity.setRemarks(remarks);
		moveGoodsEntity.setModify_code(modifyCode);
		moveGoodsEntity.setModify_name(modifyName);
		moveGoodsEntity.setModify_time(data);
		//副表Entity
		MoveGoodsDepartmentEntity moveGoodsDepartmentEntity = new MoveGoodsDepartmentEntity();
		moveGoodsDepartmentEntity.setT_OPT_MOVESTOCK_id(id);
		moveGoodsDepartmentEntity.setGoods_type(goodsType);
		moveGoodsDepartmentEntity.setMoveout_code(moveoutCode);
		moveGoodsDepartmentEntity.setMoveout_name(moveoutName);
		moveGoodsDepartmentEntity.setMoveout_areacode(moveoutAreacode);
		moveGoodsDepartmentEntity.setMoveout_areaname(moveoutAreaname);
		moveGoodsDepartmentEntity.setMovein_code(moveinCode);
		moveGoodsDepartmentEntity.setMovein_name(moveinName);
		moveGoodsDepartmentEntity.setMovein_areacode(moveinAreacode);
		moveGoodsDepartmentEntity.setMovein_areaname(moveinAreaname);
		try {
			stockService.moveGoodsModifyInStock(moveGoodsEntity,moveGoodsDepartmentEntity);
			return returnSuccess();
		}  catch(BusinessException e){
			//设置异常CODE
			stockVO.setExceptionCode(e.getErrorCode());
			return returnError(e);
		}
		
		
	}
	
	
	
	/**
	* @description 判断部门是否是外场
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015-1-7 上午8:51:47
	*/
	public String  queryIsTransferCenter(){
		//申请人部门编号
		String orgCode = FossUserContext.getCurrentDept().getCode();
		//申请人部门名称
		String orgName = FossUserContext.getCurrentDept().getName();
		//判断部门是不是外场,或者部门的顶级部门是不是外场,如果不是就不显示页面
		try {
			//返回的是外场code
			orgCode = stockService.queryIsTransferCenter(orgCode);	
			stockVO.setStockOrgCode(orgCode);
			stockVO.setStockOrgName(orgName);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	//判断是否能打开'库存迁移'页面
	//1,如果是场地搬迁信息维护员对应的角色  直接放行
	//2,如果是在外场也可以显示
	public String canLookMoveGoods(){
		//1,如果是场地搬迁信息维护员对应的角色  直接放行
		String isTfrStockMoveGoods = FossConstants.NO;
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		//根据code查询用户的角色
		List<UserOrgRoleEntity> userOrgRoleEntityList = stockService.queryOrgRoleByCode(userCode);
		for(UserOrgRoleEntity u : userOrgRoleEntityList){
			//这个是场地搬迁信息维护员对应的角色code 是FOSS_LD_KCQYCZY
			if(u.getRoleCode().equals("FOSS_LD_KCQYCZY")){
				isTfrStockMoveGoods = FossConstants.YES;
				break;
			}
		}
		if(FossConstants.YES.equals(isTfrStockMoveGoods)){
			return returnSuccess();
		//2,如果是在外场也可以显示
		}else {
			//申请人部门编号
			String orgCode = FossUserContext.getCurrentDept().getCode();
			//申请人部门名称
			String orgName = FossUserContext.getCurrentDept().getName();
			//判断部门是不是外场,或者部门的顶级部门是不是外场,如果不是就不显示页面
			try {
				//返回的是外场code
				orgCode = stockService.queryIsTransferCenter(orgCode);	
				stockVO.setStockOrgCode(orgCode);
				stockVO.setStockOrgName(orgName);
			} catch (BusinessException e) {
				return returnError(e);
			}
			return returnSuccess();
			
		}
	}
	
	/**
	* @description 确认迁移方法
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年1月24日 上午11:12:03
	*/
	public String  confirmStock(){
		//id用来改变state状态
		String id = stockVO.getMoveGoodsStockQueryDto().getId();
		//货物类型
		int goodsType = stockVO.getMoveGoodsStockQueryDto().getGoods_type();
		String moveoutCode = stockVO.getMoveGoodsStockQueryDto().getMoveout_code();
		String moveoutAreacode = stockVO.getMoveGoodsStockQueryDto().getMoveout_areacode();
		String moveinCode = stockVO.getMoveGoodsStockQueryDto().getMovein_code();
		String moveinAreacode = stockVO.getMoveGoodsStockQueryDto().getMovein_areacode();
		//要判断货物类型哈,1 零担  , (0 快递   待用)  (快递的库区要和零担分开了,没做判断)
		
		
		// 从部门移出部门移入到部门移入部门
		try {
			stockService.moveGoodsFromMoveOutToMoveInCode(moveoutCode,moveoutAreacode,moveinCode,moveinAreacode,id,goodsType);
			
		}catch(BusinessException e){
			stockVO.setExceptionCode(e.getErrorCode());
			return returnError(e);
		}
		return returnSuccess();
	}
	
	
	/**
	* @description 判断用户角色是否有外场经理
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年3月7日 上午9:57:19
	*/
	public String selectUserRoleByUserCode(){
		//申请人工号
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		//根据code查询用户的角色
		List<UserOrgRoleEntity> userOrgRoleEntityList = stockService.queryOrgRoleByCode(userCode);
		MoveGoodsStockDto m = new MoveGoodsStockDto();
		m.setIsMoveGoodsMan("N");
		stockVO.setMoveGoodsStockDto(m);
		for(UserOrgRoleEntity u : userOrgRoleEntityList){
			//这个是外场经理对应的角色code 是TFR_TRANSFER_MANAGER
			if(u.getRoleCode().equals("TFR_TRANSFER_MANAGER")){
				stockVO.getMoveGoodsStockDto().setIsMoveGoodsMan("Y");
				break;
			}
		}
		return returnSuccess();
	}
	
	
	/**
	* @description 查询库区编号修改的数据记录
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月3日 下午4:22:13
	*/
	public String queryChangeGoodsArea(){
		List<ChangeGoodsAreaEntity> changeGoodsAreaEntityList = null;
		try {
			changeGoodsAreaEntityList = stockService.queryChangeGoodsArea(stockVO.getChangeGoodsAreaQueryDto(), this.getLimit(), this.getStart());
		} catch (Exception e) {
			stockVO.setExceptionCode(e.getMessage());
			return returnError("error",e);
		}
		stockVO.setChangeGoodsAreaEntityList(changeGoodsAreaEntityList);
		//查询总记录数
		Long totalCount = stockService.queryChangeGoodsAreaCount(stockVO.getChangeGoodsAreaQueryDto());
		this.setTotalCount(totalCount);
		return returnSuccess();
	}
	
	
	
	/**
	* @description 作废库区编号修改申请
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月7日 下午3:17:40
	*/
	public String invalidateChangeGoodsArea(){
		try{
			ChangeGoodsAreaEntity changeGoodsAreaEntity = new ChangeGoodsAreaEntity();
			changeGoodsAreaEntity.setId(stockVO.getChangeGoodsAreaEntityId());
			//撤销申请
			stockService.invalidateChangeGoodsArea(changeGoodsAreaEntity);
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	
	
	/**
	* @description 根据部门code获取NewAndOldGoodsAreaEntity
	* @param orgCode
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月8日 上午10:23:10
	*/
	public String lookGoodsAreaByOrgcode(){
		List<NewAndOldGoodsAreaEntity> list = stockService.lookGoodsAreaByOrgcode(stockVO.getChangeGoodsAreaOrgCode());
		stockVO.setNewAndOldGoodsAreaEntityList(list);
		return returnSuccess();
	}
	
	/**
	* @description 将页面申请的 库区编号修改的信息 写入数据库
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月9日 下午3:07:52
	*/
	
	public String changeGoodsAreaInStock(){
		//主表需要的信息
		String orgCode = stockVO.getChangeGoodsAreaEntity().getOrg_code();
		String orgName = stockVO.getChangeGoodsAreaEntity().getOrg_name();
		String remarks = stockVO.getChangeGoodsAreaEntity().getRemarks();
		//申请人工号  
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		//申请人姓名
		String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		//申请时间
		Date data = new Date();
		//主表uuid
		String id1 = UUIDUtils.getUUID();
		String state = "1";
		//将这些信息放入主表实体类  ChangeGoodsAreaEntity
		ChangeGoodsAreaEntity c = new ChangeGoodsAreaEntity();
		c.setId(id1);
		c.setState(state);
		c.setRemarks(remarks);
		c.setApplicant_time(data);
		c.setApplicant_code(userCode);
		c.setApplicant_name(userName);
		c.setOrg_code(orgCode);
		c.setOrg_name(orgName);
		
		//下面是副表需要的信息
		List<NewAndOldGoodsAreaEntity> naoList = new ArrayList<NewAndOldGoodsAreaEntity>();
		List<String> list = stockVO.getStringList();//[067,1111, 093,2222, 095,3333, 1,, 10,, 1001,, 101,, 107,, 11,, 111,, 1133,, 11卡,, 13,, 17,, 17A,, 201,, 21,, 23,, 234,, 23卡,, 23普,, 25卡,, 25普,, 27,, 27卡,, 27普,, 28,, 29,, 29卡,, 31,, 31卡,, 32,, 33卡,, 365,, 38,, 38卡,, 44,, 44卡,, 44普,, 46,, 479,, 48卡,, 48普,, 500,, 502,, 504,, 506,, 507,, 508,, 509,, 511,, 513,, 515,, 516,, 517,, 518,, 519,, 520,, 521,, 525,, 526,, 531,, 534,, 56,, 58,, 60,, 66普,, 68,, 70卡,, 74,, 75,, 78,, 9,, 999,, BULK_GOODS_AREA,, D001,, KD001,, N/A,, WHOLE_GOODS_AREA,]
		int size = list.size();
		String[] str = (String[])list.toArray(new String[size]); //此时是79个数组
		for(int i = 0;i<size;i++){
			NewAndOldGoodsAreaEntity n = new NewAndOldGoodsAreaEntity();
			String[] oldAndNew = str[i].split(",");
			if(oldAndNew.length == 1){
				String oldGoodsAreaCode = oldAndNew[0];
				n.setOld_goods_area_code(oldGoodsAreaCode);	
			}else{
				String oldGoodsAreaCode = oldAndNew[0];//[067, ]
				String newGoodsAreaCode = oldAndNew[1];
				n.setNew_goods_area_code(newGoodsAreaCode);
				n.setOld_goods_area_code(oldGoodsAreaCode);	
			}
			String id2 = UUIDUtils.getUUID();
			String changeGoodsareaAreaId = id1;
			String orgCodeT = orgCode;
			n.setId(id2);
			n.setChange_goodsarea_area_id(changeGoodsareaAreaId);
			n.setOrg_code(orgCodeT);
			naoList.add(n);
			
		}
		
		try {
			stockService.changeGoodsAreaInStock(c,naoList);
		} catch (Exception e) {
			stockVO.setExceptionCode(e.getMessage());
			return returnError("error",e);
		}
		
		return returnSuccess();
	}
	
	
	
	/**
	* @description 库区编号修改,修改时查询新旧库区对应关系
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月11日 下午3:45:47
	*/
	public String lookModifyGoodsAreaByOrgcode(){
		String orgCode = stockVO.getChangeGoodsAreaOrgCode();
		String id = stockVO.getChangeGoodsAreaEntityId();
		List<NewAndOldGoodsAreaEntity> list = stockService.lookModifyGoodsAreaByOrgcode(orgCode,id);
		stockVO.setNewAndOldGoodsAreaEntityList(list);
		return returnSuccess();
	}
	
	
	/**
	* @description 库区编号修改,查看是时查询新旧库区对应关系(查询页面)
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月16日 下午3:56:55
	*/
	public String lookLookGoodsAreaByOrgcode(){
		String orgCode = stockVO.getChangeGoodsAreaOrgCode();
		String id = stockVO.getChangeGoodsAreaEntityId();
		List<NewAndOldGoodsAreaEntity> list = stockService.lookLookGoodsAreaByOrgcode(orgCode,id);
		stockVO.setNewAndOldGoodsAreaEntityList(list);
		return returnSuccess();
	}
	
	/**
	* @description 将修改 库区编号变更的数据写入数据库表中(副表)
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月15日 上午10:16:33
	*/
	public String  modifyGoodsAreaInStock(){
		//修改主表的备注信息
		String id1 = stockVO.getChangeGoodsAreaEntity().getId();
		String remarks = stockVO.getChangeGoodsAreaEntity().getRemarks(); 
		ChangeGoodsAreaEntity c = new ChangeGoodsAreaEntity();
		c.setId(id1);
		c.setRemarks(remarks);
		//下面是修改新旧库区编号对应表
		List<NewAndOldGoodsAreaEntity> naoList = new ArrayList<NewAndOldGoodsAreaEntity>();
		List<String> list = stockVO.getStringList();
		int size = list.size();
		String[] str = (String[])list.toArray(new String[size]); //此时是79个数组
		for(int i = 0;i<size;i++){
			NewAndOldGoodsAreaEntity n = new NewAndOldGoodsAreaEntity();
			String[] idAndNew = str[i].split(",");
			String id = idAndNew[0];
			String newGoodsAreaCode = idAndNew[1];
			n.setId(id);
			n.setNew_goods_area_code(newGoodsAreaCode);
			naoList.add(n);
		}
		try {
			stockService.updateGoodsAreaInStockById(naoList,c);
		} catch (BusinessException e) {
			stockVO.setExceptionCode(e.getErrorCode());
			return returnError(e);
		}
		
		return returnSuccess();
	}
	
	
	
	/**
	* @description 确认修改
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月27日 上午9:35:16
	*/
	public String confirmModify(){
		String id = stockVO.getChangeGoodsAreaEntity().getId();
		String orgCode = stockVO.getChangeGoodsAreaOrgCode();
		//根据id和orgCode 去查询新旧库区编号对应关系
		List<NewAndOldGoodsAreaEntity> list = stockService.lookLookGoodsAreaByOrgcode(orgCode,id);
		// 将list的数据插入到此表当中同时将新库区code改为新库区code_new
		try {
			stockService.insertDataById(list,orgCode,id);
		} catch (BusinessException e) {
			stockVO.setExceptionCode(e.getErrorCode());
			return returnError(e);
		}
		
		return returnSuccess();
	}
	
	/**
	 * 判断是不是快递运单号
	 * @return
	 */
	public String ifIsExpressWaybill(){
		CurrentInfo user = FossUserContext.getCurrentInfo();//获取当前登录的信息
		if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null){//判断是不是快递员
			System.out.println(stockVO.getWaybillNOs()+"~~~~~~~~~~~~~~~~");
			if(stockService.ifIsExpressWaybill(stockVO.getWaybillNOs())){//判断是不是快递
				
				System.out.println(stockService.ifIsExpressWaybill(stockVO.getWaybillNOs())+">>>>>>>>>>>>>>>>>>");
				stockVO.setIfIsExpressWaybill(true);//如果是快递员且运单是快递运单就可以单票入库以后的操作
			}else{
				stockVO.setIfIsExpressWaybill(false);
			}
			String code = expressVehiclesService.queryExpressVehiclesByCode(user.getEmpCode());
			OrgAdministrativeInfoEntity expressOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(code);
			stockVO.setStockOrgCode(expressOrgEntity.getCode());
			stockVO.setStockOrgName(expressOrgEntity.getName());
		}else{
			stockVO.setIfIsExpressWaybill(true);
			stockVO.setStockOrgCode(user.getCurrentDeptCode());
			stockVO.setStockOrgName(user.getCurrentDeptName());
		}
		return returnSuccess();
	}
	
}




















