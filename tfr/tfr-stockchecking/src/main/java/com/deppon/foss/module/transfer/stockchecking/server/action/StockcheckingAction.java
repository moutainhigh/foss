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
 *  PROJECT NAME  : tfr-stockchecking
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/server/action/StockcheckingAction.java
 *  
 *  FILE NAME          :StockcheckingAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.server.action;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;

import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.ScanDetailDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;

import com.deppon.foss.module.transfer.stockchecking.api.server.service.IStockcheckingService;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StOperatorEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.GoodsStockDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StTaskDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StTaskWaybillNoListDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StWaybillInfoDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.vo.StockcheckingVO;
import com.deppon.foss.util.define.FossConstants;

/**
 * 处理清仓任务相关界面
 * @author foss-wuyingjie
 * @date 2012-10-16 下午4:31:36
 */
public class StockcheckingAction extends AbstractAction{

	private static final long serialVersionUID = 3715756705823350504L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StockcheckingAction.class);
//	处理清仓任务的service
	private IStockcheckingService stockcheckingService;
//	存储清仓任务相关页面实体的VO
	private StockcheckingVO stockcheckingVO = new StockcheckingVO();
	
	/** 导出Excel 文件流*/
	private InputStream excelStream;  
	/** 导出Excel 文件名*/
	private String fileName; 

	/**
	 * 查询清仓任务信息列表（用于查询清仓任务页面）
	 * @author foss-wuyingjie
	 * @date 2012-10-18 下午2:11:58
	 * @param
	 * @return String
	 */
	@JSON
	public String queryStTask(){
		LOGGER.trace("into action...");
		try{
//			通过查询条件获取清仓任务查询结果，需判断登陆人所在部门与创建部门一致，空运总调部门，需找到对应的外场
			List<StTaskDto> stTaskDtoList = stockcheckingService.queryStTaskDtoList(stockcheckingVO.getStTaskDto(), this.getStart(), this.getLimit());
			Long totalCount = stockcheckingService.queryStTaskDtoListCount(stockcheckingVO.getStTaskDto());

			stockcheckingVO.setStTaskDtoList(stTaskDtoList);
			this.setTotalCount(totalCount);
			
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.error("清仓任务查询异常:" + e);
			
			return super.returnError(e);
		}
	}
	
	/**
	 * 按清仓任务ID导出清仓任务统计信息
	 * @author foss-wuyingjie
	 * @date 2012-11-15 下午3:40:49
	 * @param
	 * @return String
	 */
	public String exportTaskById(){
		try{
			fileName = stockcheckingService.createFileName("清仓任务明细信息");
			excelStream = stockcheckingService.exportTaskById(stockcheckingVO.getStTaskDto().getId());
			
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.error("清仓任务导出异常:" + e);
			
			return super.returnError(e);
		}
	}

	/**
	 * <pre>
	 * 查询库区列表（用于新建及更新清仓任务界面）
	 * 1、新建任务时，获取本部门所有库区的情况
	 * 2、编辑和确认任务时，通过清仓任务ID获取库区信息
	 * </pre>
	 * @author foss-wuyingjie
	 * @date 2012-10-23 下午3:32:01
	 * @param
	 * @return String
	 */
	@JSON
	public String queryGoodsStock(){
		try{
			List<GoodsStockDto> goodsStockDtoList = stockcheckingService.queryGoodsStockDtoList(stockcheckingVO.getGoodsStockDto());

			stockcheckingVO.setGoodsStockDtoList(goodsStockDtoList);
			
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.error("查询库区异常:" + e);
			
			return super.returnError(e);
		}
	}
	
	/**
	 * <pre>
	 * 手工创建清仓任务
	 * 1、传入库区列表
	 * 2、关闭新建清仓任务页面
	 * 3、刷新查询清仓任务列表
	 * </pre>
	 * 
	 * @author foss-wuyingjie
	 * @date 2012-10-24 下午4:52:02
	 * @param
	 * @return String
	 */
	@JSON
	public String createStTask(){
		try{
			System.out.println("创建任务类型为:"+stockcheckingVO.stType);
			stockcheckingService.addStTask(stockcheckingVO.getSelectedGoodsAreasList(), stockcheckingVO.getSelectedOperatorList(),stockcheckingVO.getReceiveMethod(),stockcheckingVO.getDistrictCode(),stockcheckingVO.getDistrictName(),stockcheckingVO.getStartQty(),stockcheckingVO.getEndQty(),stockcheckingVO.getStType());
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.error("创建清仓任务异常:" + e);
			
			return super.returnError(e);
		}
	}
	
	/**
	 * 更新清仓任务，只更新清仓人绑定信息
	 * @author foss-wuyingjie
	 * @date 2012-11-1 上午11:47:12
	 * @param
	 * @return String
	 */
	@JSON
	public String updateStTask(){
		try{
			stockcheckingService.updateStTask(stockcheckingVO.getStTaskDto().getId(), stockcheckingVO.getSelectedOperatorList());
			
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.error("更新清仓任务异常:" + e);
			
			return super.returnError(e);
		}
	}
	
	/**
	 * <pre>
	 * 手工确认清仓任务，并更新清仓任务状态至TransferConstants.STOCK_CHECKING_DONE状态，同时生成此清仓任务对应结果
	 * 1、页面中货物清仓打勾的表示为"少货"货件，未打勾的为"正常"货件
	 * 2、页面中货区多货清单新增的货件为"多货"货件
	 * </pre>
	 * @author foss-wuyingjie
	 * @date 2012-11-12 下午7:51:13
	 * @param
	 * @return String
	 */
	@JSON
	public String confirmStTask(){
		try{
			stockcheckingService.confirmStTask(stockcheckingVO.getStTaskDto().getId(),
											   stockcheckingVO.getSelectedOperatorList(), 
											   stockcheckingVO.getScanResultOkList(),
											   stockcheckingVO.getScanResultHaveNotList(),
											   stockcheckingVO.getScanResultSurplusList());
			
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.error("确认清仓任务异常:" + e);
			
			return super.returnError(e);
		}
	}
	
	/**
	 * 通过ID获取清仓任务信息，包含：清仓任务、清仓人信息、库区信息、库存快照(包含件列表)
	 * @author foss-wuyingjie
	 * @date 2012-11-7 上午11:05:14
	 * @param
	 * @return String
	 */
	@JSON
	public String selectStTaskById(){
		try{
			//获取清仓任务信息
			StTaskDto stTaskDto = stockcheckingService.queryStTaskById(stockcheckingVO.getStTaskDto().getId());
			//获取清仓人信息
			List<StOperatorEntity> stOperatorList = stockcheckingService.queryOperatorsByStTaskId(stockcheckingVO.getStTaskDto().getId());
			//获取清仓货物清单
			List<StTaskWaybillNoListDto> stTaskWaybillNoList = stockcheckingService.queryStTaskWaybillNoListByStTaskId(stockcheckingVO.getStTaskDto().getId());
			
			stockcheckingVO.setStTaskDto(stTaskDto);
			stockcheckingVO.setStOperatorEntityList(stOperatorList);
			stockcheckingVO.setStTaskWaybillNoList(stTaskWaybillNoList);
			
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.error("获取清仓任务异常:" + e);
			
			return super.returnError(e);
		}
	}
	
	/**
	 * 通过ID获取清仓任务信息，包含：清仓任务、运单快照明细信息
	 * @author foss-wuyingjie
	 * @date 2012-11-7 上午11:05:14
	 * @param
	 * @return String
	 */
	@JSON
	public String viewStTaskById(){
		try{
			//获取清仓任务信息
			StTaskDto stTaskDto = stockcheckingService.queryStTaskById(stockcheckingVO.getStTaskDto().getId());
			//获取清仓人信息
			List<StOperatorEntity> stOperatorList = stockcheckingService.queryOperatorsByStTaskId(stockcheckingVO.getStTaskDto().getId());
			//获取运单统计信息
			List<StWaybillInfoDto> stWaybillInfoDtoList = stockcheckingService.queryStWaybillInfoDtoByStTaskId(stockcheckingVO.getStTaskDto().getId());
			
			
			stockcheckingVO.setStTaskDto(stTaskDto);
			stockcheckingVO.setStOperatorEntityList(stOperatorList);
			stockcheckingVO.setStWaybillInfoDtoList(stWaybillInfoDtoList);
			
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.error("查看清仓任务异常:" + e);
			
			return super.returnError(e);
		}
	}
	
	/**
	 * 取消清仓任务，删除快照
	 * @author foss-wuyingjie
	 * @date 2012-11-2 上午09:02:01
	 * @param
	 * @return String
	 */
	@JSON
	public String cancelStTask(){
		try{
			stockcheckingService.cancelStTask(stockcheckingVO.getStTaskDto().getId());
			
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.error("撤销清仓任务异常:" + e);
			
			return super.returnError(e);
		}
	}
	
	/**
	 * 获取库区下拉框类别的下拉框列表信息记录
	 * @author foss-wuyingjie
	 * @date 2012-10-29 下午7:07:11
	 * @param
	 * @return String
	 */
	@JSON
	public String queryGoodsAreaUsage(){
		try{
			List<BaseDataDictDto> goodsAreaUsageList = stockcheckingService.queryGoodsAreaUsage();

			stockcheckingVO.setGoodsAreaUsageList(goodsAreaUsageList);

			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.error("查询库区异常:" + e);
			
			return super.returnError(e);
		}
	}

	/**
	 * 通过清仓任务ID获取清仓人列表
	 * @author foss-wuyingjie
	 * @date 2012-11-1 上午10:12:53
	 * @param
	 * @return String
	 */
	@JSON
	public String queryOperatorsByStTaskId(){
		try{
			List<StOperatorEntity> stOperatorList = stockcheckingService.queryOperatorsByStTaskId(stockcheckingVO.getStTaskDto().getId());
			
			stockcheckingVO.setStOperatorEntityList(stOperatorList);
			
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.error("获取清仓人异常:" + e);
			
			return super.returnError(e);
		}
	}
	
	/**
	 * 查询扫描明细列表信息
	 * @author foss-wuyingjie
	 * @date 2012-11-14 下午7:55:13
	 * @param
	 * @return String
	 */
	@JSON
	public String queryScanDetailInStTask(){
		try{
			List<ScanDetailDto> scanDetailDtoList = stockcheckingService.queryScanDetailInStTask(stockcheckingVO.getStTaskDto().getId(), stockcheckingVO.getStTaskDto().getWaybillNo());
			
			stockcheckingVO.setScanDetailDtoList(scanDetailDtoList);
			
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.error("获取扫描明细异常:" + e);
			
			return super.returnError(e);
		}
	}
	
	/**
	 * 通过部门编号、库区编号获取是否存在为结束的任务
	 * @return: 
	 *
	 * @author foss-wuyingjie
	 * @date 2013-3-20 下午3:44:13
	 * @param
	 * @return String
	 */
	@JSON
	public String queryTaskInProcess(){
		try{
			List<StTaskDto> stTaskList = stockcheckingService.queryTaskInProcess(stockcheckingVO.getGoodsStockDto().getDeptNo(), stockcheckingVO.getGoodsStockDto().getGoodsAreaCode());
			//不是按件清仓时的DOING任务
			if(stTaskList != null && stTaskList.size() > 0 
					&& (stTaskList.get(0).getStartQty() == null || stTaskList.get(0).getEndQty() == null)) {
				stockcheckingVO.setStTaskDtoList(stTaskList);
			}
			
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.error("" + e);
			
			return super.returnError(e);
		}
	}
	/**
	 * 通过当前部门获取所属有库存的大部门
	 * @author 097457-foss-wangqiang
	 * @date 2013-5-18 下午2:39:38
	 * @param
	 * @return String
	 */
	@JSON
	public String queryStockOrg(){
		try{
			OrgAdministrativeInfoEntity stockOrg = null;
			CurrentInfo user = FossUserContext.getCurrentInfo();//获取当前登录的信息
			if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null){//判断是不是快递员
				String code = expressVehiclesService.queryExpressVehiclesByCode(user.getEmpCode());
				OrgAdministrativeInfoEntity expressOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(code);
				stockOrg=stockcheckingService.getBigOrg(expressOrgEntity);
			}else{
				stockOrg=stockcheckingService.getBigOrg(FossUserContext.getCurrentDept());
			}
			//外场
			if(StringUtils.equals(FossConstants.YES, stockOrg.getTransferCenter())){
				stockcheckingVO.setIsTransferCenter(FossConstants.YES);
				stockcheckingVO.setStockOrgCode(stockOrg.getCode());
		    //非外场
			}else{
				stockcheckingVO.setIsTransferCenter(FossConstants.NO);
			}
			return returnSuccess();
		}catch(BusinessException e){
			return returnError(e);
		}
	}
	/**
	 * 获取外场统计信息
	 * @author 097457-foss-zenghaibin
	 * @date 2014-10-18 下午2:39:38
	 * @param
	 * @return String
	 */
	@JSON
	public String statistics(){
		try{
			stockcheckingVO=stockcheckingService.statistics();
		return returnSuccess();
		}catch(BusinessException e){
			return returnError(e);
		}
	}
	/**
	 * @author niuly
	 * @date 2013-07-18 17:09:28
	 * @funcion 判断该货区是否为驻地派送货区
	 * @return
	 * @param
	 * @return String
	 */
	public String isBasStation() {
		try{
			//取上级外场或空运总调
			OrgAdministrativeInfoEntity orgEntity = stockcheckingService.getBigOrg(FossUserContext.getCurrentDept());
			boolean isBasStation = false;
			if(orgEntity != null) {
				isBasStation = stockcheckingService.isBasStation(orgEntity.getCode(),stockcheckingVO.getGoodsStockDto().getGoodsArea());
			}
			
			stockcheckingVO.setBasStation(isBasStation);
			return returnSuccess();
		}catch(BusinessException e){
			return returnError(e);
		}
	}
	/**
	 * @author nly
	 * @date 2014年10月30日 上午9:01:31
	 * @function 是否为试点外场
	 * @return
	 */
	public String isTestTrans() {
		try{
			boolean isTestTrans = stockcheckingService.isTestTrans();
			stockcheckingVO.setTestTrans(isTestTrans);
			return returnSuccess();
		}catch(BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * @author nly
	 * @date 2014年11月11日 下午4:36:21
	 * @function 查询所属外场
	 * @return
	 */
	public String queryTransferCode() {
		try{
			String transferCode = stockcheckingService.queryTransferCode();
			stockcheckingVO.setTransferCode(transferCode);
			CurrentInfo user = FossUserContext.getCurrentInfo();//获取当前登录的信息
			if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null){
				String code = expressVehiclesService.queryExpressVehiclesByCode(user.getEmpCode());//快递员车辆绑定营业部的部门编号
				//若为营业部，则判断是否为驻地营业部
				SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySimpleSaleDepartmentByCode(code);
				if(StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getStation())) {
					stockcheckingVO.setIsBasDept("Y");
				}else{
					stockcheckingVO.setIsBasDept("N");
				}
			}
			
			return returnSuccess();
		}catch(BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * author 268084
	 * 判断是不是快递员
	 * @return
	 */
	public String isExpressClerk(){
		try {
			CurrentInfo user = FossUserContext.getCurrentInfo();//获取当前登录的信息
			if(commonExpressEmployeeService.queryCourierByCode(user.getEmpCode())!=null){//判断是不是快递员
				stockcheckingVO.setQueryIfIsExpressClerk(true);
				String code = expressVehiclesService.queryExpressVehiclesByCode(user.getEmpCode());
				//根据营业部编号获取组织架构
                OrgAdministrativeInfoEntity expressOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(code); 
				stockcheckingVO.setIsTransferCenter(expressOrgEntity.getTransferCenter());
			}else{
				stockcheckingVO.setQueryIfIsExpressClerk(false);
				stockcheckingVO.setIsTransferCenter(FossUserContext.getCurrentDept().getTransferCenter());
			}
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
		
	}
	/**
	 * 判断库区类型
	 * @return
	 */
	public String judgeIsExceptionGoodsArea(){
		String deptCode = FossUserContext.getCurrentDeptCode();
		try {
			OrgAdministrativeInfoEntity transferCenter = stockcheckingService
					.querySupTfrCtr(deptCode);
			System.out.println(stockcheckingVO.getGoodsStockDto().getGoodsAreaType()+"库区编号");
			GoodsAreaEntity goodsAreaEntity=goodsAreaService.queryGoodsAreaByCode(transferCenter.getCode(), stockcheckingVO.getGoodsStockDto().getGoodsAreaType());
			if(goodsAreaEntity==null){
				throw new TfrBusinessException("调用综合接口查询库区信息失败","");
			}
			System.out.println("调用综合接口成功！");
			System.out.println(goodsAreaEntity.getGoodsAreaType());
			if(goodsAreaEntity.getGoodsAreaType().equals("BSE_GOODSAREA_TYPE_EXCEPTION")){
				stockcheckingVO.getGoodsStockDto().setGoodsAreaType("excp");
			}else{
				stockcheckingVO.getGoodsStockDto().setGoodsAreaType(null);
			}
			return returnSuccess();
		} catch (BusinessException e) {
			System.out.println(e);
			return returnError(e);
		}
		
		
	}
	/**
	 * @author nly
	 * @date 2014年11月7日 上午10:50:50
	 * @function 校验是否存在交叉清仓任务
	 * @return
	 */
//	public String checkStockChecking() {
//		try{
//			stockcheckingService.checkStockChecking(stockcheckingVO.getGoodsStockDto());
//			return returnSuccess();
//		}catch(BusinessException e) {
//			return returnError(e);
//		}
//	}
	
	/**
	 * 查询营业部信息，(用于判断是不是驻地营业部)
	 */
	private ISaleDepartmentService saleDepartmentService;
	
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	/**
	 * 根据库区编号判断库区类型
	 */
	private IGoodsAreaService goodsAreaService;
	
	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}
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
	public void setStockcheckingService(IStockcheckingService stockcheckingService) {
		this.stockcheckingService = stockcheckingService;
	}
	
	public StockcheckingVO getStockcheckingVO() {
		return stockcheckingVO;
	}

	public void setStockcheckingVO(StockcheckingVO stockcheckingVO) {
		this.stockcheckingVO = stockcheckingVO;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}