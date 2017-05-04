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
 *  PROJECT NAME  : tfr-exceptiongoods
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/exceptiongoods/server/service/impl/PrintLabelService.java
 *  
 *  FILE NAME          :PrintLabelService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.exceptiongoods.server.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IProductService;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPrintInfoService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillPrintDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.define.ExceptionGoodsConstants;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.INoLabelGoodsDao;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.dao.IPrintLabelDao;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IPrintLabelService;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.NoLabelGoodsEntity;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.PrintLabelEntity;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.GoodsLabelPrintDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.PrintLabelDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.SortingAndPringLabelDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.exception.NoLabelGoodsException;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITransportationPathDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 实现打印标签信息的业务操作
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 下午12:19:04
 */
public class PrintLabelService implements IPrintLabelService {
	/**
	* @fields LOGGER
	*/
	private static final Logger LOGGER = LogManager.getLogger(PrintLabelService.class);
	/**
	* 定义了打印标签信息的基本操作
	*/
	private IPrintLabelDao printLabelDao;
	/**
	* 定义了无标签多货的基本操作
	*/
	private INoLabelGoodsDao noLabelGoodsDao;
	/**
	* 走货路径dao
	*/
	private ITransportationPathDao transportationPathDao;
	/**
	* 计算&调整走货路径类
	*/
	private ICalculateTransportPathService calculateTransportPathService;
	/**
	* (得到标签打印的信息) 
	*/
	private ILabelPrintInfoService labelPrintInfoService;
	/**
	* 人员 Service接口
	*/
	private IEmployeeService employeeService;
	/** 综合管理 组织信息 Service*/
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**运单管理Service*/
//	private IWaybillManagerService waybillManagerService;
	@Resource
	private IProductService productService4Dubbo;
	/**产品定义Service*/
	private IProductService productService;
	private IWaybillDao  waybillDao;
	public IWaybillDao getWaybillDao() {
		return waybillDao;
	}
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}
//	public void setProductService(IProductService productService) {
//		this.productService = productService;
//	}
	/*public void setWaybillManagerService(
	    IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}*/
	/**
	* @param printLabelDao
	* @description 设置定义了打印标签信息的基本操作
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:33:29
	*/
	public void setPrintLabelDao(IPrintLabelDao printLabelDao) {
		this.printLabelDao = printLabelDao;
	}
	/**
	* @param transportationPathDao
	* @description 设置走货路径dao
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:35:04
	*/
	public void setTransportationPathDao(
			ITransportationPathDao transportationPathDao) {
		this.transportationPathDao = transportationPathDao;
	}
	/**
	* @param calculateTransportPathService
	* @description 设置计算&调整走货路径类
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:35:45
	*/
	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}
	/**
	* @param labelPrintInfoService
	* @description  设置(得到标签打印的信息) 
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:36:33
	*/
	public void setLabelPrintInfoService(
			ILabelPrintInfoService labelPrintInfoService) {
		this.labelPrintInfoService = labelPrintInfoService;
	}
	/**
	* @param noLabelGoodsDao
	* @description 设置定义了无标签多货的基本操作
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:34:08
	*/
	public void setNoLabelGoodsDao(INoLabelGoodsDao noLabelGoodsDao) {
		this.noLabelGoodsDao = noLabelGoodsDao;
	}
	/**
	* @param orgAdministrativeInfoService
	* @description 设置综合管理 组织信息
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:38:09
	*/
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	/**
	* @param employeeService
	* @description 设置人员 Service接口
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-30 上午11:37:12
	*/
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	/**
	 * 增加打印操作信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-26 下午5:21:46
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IPrintLabelService#addPrintLabel(com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.PrintLabelEntity)
	 */
	@Override
	public int addPrintLabel(PrintLabelEntity printLabelEntity) {
		/*
		 * FOSS20140918版本，新增时读取人员所在部门，存储
		 * shiwei-045923 修改 2014-08-23 17:39:00
		 */
		//此处要获取员工所在的部门名称、编码
		EmployeeEntity eEntity = employeeService.queryEmployeeByEmpCodeNoCache(printLabelEntity.getPrintUserCode());
		//所在部门code
		String orgCode = eEntity.getOrgCode();
		//重新调用组织接口，获取orgName
		String orgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(orgCode);
		//所在部门name
		printLabelEntity.setOrgCode(orgCode);
		printLabelEntity.setOrgName(orgName);
		//保存标签打印记录
		return printLabelDao.addPrintLabel(printLabelEntity);
	}
	
	
	
	
	

	/**
	* @description 保存打印指定标签操作信息 公用方法
	* @param waybillNo
	* @param serialNoList
	* @param userCode
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年2月4日 下午3:00:02
	*/
	private int addPrintAppointedComm(String waybillNo, List<String> serialNoList,
			String userCode,int printType){
		try{
			Date printTime = new Date();
			NoLabelGoodsEntity noLabelGoods = new NoLabelGoodsEntity();
			noLabelGoods.setOriginalWaybillNo(waybillNo);
			//封装操作信息
			PrintLabelEntity printLabelEntity = new PrintLabelEntity();
			if(printType==ExceptionGoodsConstants.PRINT_TYPE){
				printLabelEntity.setPrintType(printType);
			}
			printLabelEntity.setWaybillNo(waybillNo);
			printLabelEntity.setPrintTime(printTime);
			printLabelEntity.setPrintUserCode(userCode);
			String userName = this.getUserNameByCode(userCode);
			printLabelEntity.setPrintUserName(userName);
			//此处要获取员工所在的部门名称、编码
			EmployeeEntity eEntity = employeeService.queryEmployeeByEmpCodeNoCache(userCode);
			//所在部门code
			String orgCode = eEntity.getOrgCode();
			//重新调用组织接口，获取orgName
			String orgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(orgCode);
			for(String serialNo : serialNoList){
				printLabelEntity.setSerialNo(serialNo);
				noLabelGoods.setOriginalSerialNo(serialNo);
				noLabelGoods.setIsPrintOriginalLabel(FossConstants.YES);
				printLabelEntity.setOrgCode(orgCode);
				printLabelEntity.setOrgName(orgName);
				//保存打印操作信息
				try {
					printLabelDao.addPrintLabel(printLabelEntity);
				} catch (Exception e) {
					LOGGER.error("标签打印信息写入失败", e);
					throw new NoLabelGoodsException(NoLabelGoodsException.IN_STOCK_EXCEPTION_ERROR_CODE, ExceptionUtils.getFullStackTrace(e));
				}
				//更新相关联无标签货物
				noLabelGoodsDao.updateNoLabelGoods(noLabelGoods);
			}
		}catch(Exception e){
			return FossConstants.FAILURE;
		}
		return FossConstants.SUCCESS;
	}
	
	/**
	* @description 查询货物打印信息
	* @param printLabelDto
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年2月4日 下午3:52:31
	*/
	@Override
	public Long queryPrintLabelInfoExpressCount(PrintLabelDto printLabelDto) {
		return printLabelDao.queryPrintLabelForExpressCount(printLabelDto);
	}
	
	
	/**
	 * 查询货物标签打印信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-26 下午7:04:40
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IPrintLabelService#queryPrintLabelInfo(com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.PrintLabelDto)
	 */
	@Override
	public List<PrintLabelDto> queryPrintLabelInfo(PrintLabelDto printLabelDto) {
		//查询货件表
		List<PrintLabelDto> goodsList = printLabelDao.queryPrintLabelInfo(printLabelDto);
		// 322610 电子面单非货物标签打印去除CANCLE状态条目
		java.util.Iterator<PrintLabelDto> it = goodsList.iterator();
		while(it.hasNext()) {
			PrintLabelDto printNoGoodsLabel = it.next();
			if(!printNoGoodsLabel.getWaybillNo().isEmpty() && "CANCLE".equals(printNoGoodsLabel.getWayBillStatus())){
				it.remove();
			}
		}
		
		List<PrintLabelDto> printLabelDtoList = new ArrayList<PrintLabelDto>();
		//查询走货路径 判断是否分批配载
		TransportPathEntity transportPathEntity = transportationPathDao.queryTransportPath(printLabelDto.getWaybillNo());
		if(transportPathEntity != null){
			PathDetailEntity pathDetailEntity=null;
			String goodsPosition = "";
			if(StringUtils.equals(transportPathEntity.getIfPartialStowage(), TransportPathConstants.NOTPARTIALSTOWAGE)){
				//没分批,根据运单号查询走货路径明细
				try{
					pathDetailEntity = calculateTransportPathService.queryEffect(printLabelDto.getWaybillNo(), null);
				}catch (Exception e) {
					LOGGER.warn("查询走货路径错误 。(queryEffect)" +  " 运单号：" + printLabelDto.getWaybillNo()+ ExceptionUtils.getFullStackTrace(e));
				}
				
				if(pathDetailEntity != null){
					goodsPosition = this.getGoodsPosition(pathDetailEntity);
				}else{
					//未能从走货路径中查到货物位置
				}
				//设置货物位置
				for(PrintLabelDto printLabel : goodsList){
					printLabel.setGoodsPosition(goodsPosition);
					printLabelDtoList.add(printLabel);
				}
				
			}else{//分批，根据运单号、流水号查询走货路径明细
				for(PrintLabelDto printLabel : goodsList){
					//查询走货路径
					try{
						//pathDetailEntity = calculateTransportPathService.queryEffect(printLabelDto.getWaybillNo(), printLabelDto.getSerialNo());
						pathDetailEntity = calculateTransportPathService.queryEffect(printLabelDto.getWaybillNo(), printLabel.getSerialNo());
					}catch (Exception e) {
						LOGGER.warn("查询走货路径错误 。(queryEffect)" +   " 运单号：" +   printLabelDto.getWaybillNo() + " 流水号：" + printLabel.getSerialNo()+ ExceptionUtils.getFullStackTrace(e));
					}
					
					if(pathDetailEntity != null){
						goodsPosition = this.getGoodsPosition(pathDetailEntity);
					}else{
						//未能从走货路径中查到货物位置
					}
					printLabel.setGoodsPosition(goodsPosition);
					printLabelDtoList.add(printLabel);
				}
			}
			
			return printLabelDtoList;
		}else{
			//查询走货路径失败，无法设置货物位置
		}
		return goodsList;
	}
	
	
	
	/**
	* @description 查询货物打印信息
	* @param printLabelDto
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年2月4日 下午2:03:23
	*/
	@Override
	public List<PrintLabelDto> queryPrintLabelInfoExpress(
			PrintLabelDto printLabelDto,int start,int limit) {
		//查询货件表
		List<PrintLabelDto> goodsList = printLabelDao.queryPrintLabelForExpress(printLabelDto,start,limit);
		return goodsList;
	}
	
	
	
	
	/**
	 * 根据走货路径状态返回货物位置信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-27 上午10:58:43
	 */
	public String getGoodsPosition(PathDetailEntity pathDetailEntity){
		String goodsPosition;
		
		//交接   getOrigOrgCode   
		if(StringUtils.equals(pathDetailEntity.getArriveOrLeave(), TransportPathConstants.PATHDETAIL_STATUS_HANDOVER)){
			goodsPosition = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(pathDetailEntity.getOrigOrgCode());
		//离开  车牌号
		}else if(StringUtils.equals(pathDetailEntity.getArriveOrLeave(), TransportPathConstants.PATHDETAIL_STATUS_LEAVE)){
			goodsPosition = pathDetailEntity.getVehicleNo(); 
		//抵达  getObjectiveOrgCode
		}else if(StringUtils.equals(pathDetailEntity.getArriveOrLeave(),TransportPathConstants.PATHDETAIL_STATUS_ARRIVE)){
			goodsPosition = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(pathDetailEntity.getObjectiveOrgCode());
		//入库  getObjectiveOrgCode
		}else if(StringUtils.equals(pathDetailEntity.getArriveOrLeave(),TransportPathConstants.PATHDETAIL_STATUS_INSTORE)){
			goodsPosition = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(pathDetailEntity.getObjectiveOrgCode());
		//未离开 getOrigOrgCode
		}else if(StringUtils.equals(pathDetailEntity.getArriveOrLeave(),TransportPathConstants.PATHDETAIL_STATUS_NOTLEAVE)){
			goodsPosition = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(pathDetailEntity.getOrigOrgCode());
		}else{
			goodsPosition = "";
		}
		
		return goodsPosition;
	}
	
	/**
	 * 打印指定货物标签
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-26 下午7:15:43
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IPrintLabelService#printAppointedLabel(java.lang.String, java.util.List)
	 */
	@Override
	public List<BarcodePrintLabelDto> printAppointedLabel(String waybillNo, List<String> serialNoList) throws NoLabelGoodsException {
		List<BarcodePrintLabelDto> barcodePrintDtoList = new ArrayList<BarcodePrintLabelDto>();
		try{
			
			//根据运单号获取运单实体
			WaybillEntity waybill=waybillDao.queryWaybillByNo(waybillNo);
		
			String productCode=waybill.getProductCode();
			//根据货物类型编号判断是否为快递
			Boolean isExpress=productService4Dubbo.onlineDetermineIsExpressByProductCode(productCode,new Date());
			//调用快递方法获取打印指定标签货物标签
			if(isExpress){
				barcodePrintDtoList = labelPrintInfoService.getLabelPrintInfoForDepartExpress(waybillNo, serialNoList);
			}else{
				//调用零担方法获取打印指定标签货物标签
				barcodePrintDtoList = labelPrintInfoService.getLabelPrintInfoForDepart(waybillNo, serialNoList);
			}
			
		}catch(BusinessException e){
			LOGGER.error("查询标签打印信息失败", e);
			throw new NoLabelGoodsException(NoLabelGoodsException.QUERY_PRINT_LABEL_INFO_ERROR_CODE, ExceptionUtils.getFullStackTrace(e));
		}
		
		return barcodePrintDtoList;
		
	}
	
	
	
	/**
	* @description 打印指定货件标签 (中转场打印快递标签信息封装)
	* @param waybillNo
	* @param serialNoList
	* @return
	* @throws NoLabelGoodsException
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月5日 下午3:56:20
	*/
	@Override
	public List<BarcodePrintLabelDto> getLabelPrintInfoExpress(String waybillNo, List<String> serialNoList) throws NoLabelGoodsException {
		List<BarcodePrintLabelDto> barcodePrintDtoList = new ArrayList<BarcodePrintLabelDto>();
		try{
			barcodePrintDtoList = labelPrintInfoService.getLabelPrintInfoExpress(waybillNo, serialNoList);
		}catch(BusinessException e){
			LOGGER.error("查询标签打印信息失败", e);
			throw new NoLabelGoodsException(NoLabelGoodsException.QUERY_PRINT_LABEL_INFO_ERROR_CODE, ExceptionUtils.getFullStackTrace(e));
		}
		
		return barcodePrintDtoList;
		
	}
	
	
	/**
	* @description 补打电子运单
	* @param waybillNo
	* @param serialNoList
	* @return
	* @throws NoLabelGoodsException
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年9月2日 上午10:41:08
	*/
	@Override
	public List<EWaybillPrintDto> printEWaybillLabel(String waybillNo,
			List<String> serialNoList) throws NoLabelGoodsException {
		List<EWaybillPrintDto> barcodePrintDtoList = new ArrayList<EWaybillPrintDto>();
		try{
			barcodePrintDtoList = labelPrintInfoService.printEWaybillInfos(waybillNo, serialNoList);
		}catch(BusinessException e){
			LOGGER.error("查询标签打印信息失败", e);
			throw new BusinessException(e.getMessage());
		}
		
		return barcodePrintDtoList;
	}
	
	
	/**
	* @description 根据运单号判断是否电子运单
	* @param waybillNo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年9月2日 上午10:50:35
	*/
	@Override
	public boolean isEWaybillInfoByWaybillNo(String waybillNo) {
		return labelPrintInfoService.isEWaybillInfoByWaybillNo(waybillNo);
	}
	/**
	 * 保存打印指定标签操作信息 
	 * 更新相关联无标签货物是否已重打标签状态
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-27 下午4:27:21
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IPrintLabelService#addPrintLabel(java.lang.String, java.util.List, java.lang.String, java.lang.String)
	 */
	@Override
	public int addPrintAppointedLabel(String waybillNo, List<String> serialNoList,
			String userCode) {
		return addPrintAppointedComm(waybillNo,serialNoList,userCode,0);
	}
	
	/**
	* @description 保存打印指定标签操作信息 
	* @param waybillNo
	* @param serialNoList
	* @param userCode
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年2月4日 下午2:57:15
	*/
	@Override
	public int addPrintAppointedLabel(String waybillNo,
			List<String> serialNoList, String userCode, int printType) {
		return addPrintAppointedComm(waybillNo,serialNoList,userCode,printType);
	}
	
	
	
	/**
	 * 提供给综合查询：根据运单号查询货件标签打印信息
	 * @author 097457-foss-wangqiang
	 * 045923-shiwei 2014-08-08 添加@Deprecated annotation
	 * @date 2012-12-26 上午10:49:13
	 * @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IPrintLabelService#queryLabelPrintByWaybillNo(java.lang.String)
	 */
	@Override
	@Deprecated
	public List<GoodsLabelPrintDto> queryLabelPrintByWaybillNo(String waybillNo) {
		return printLabelDao.queryLabelPrintByWaybillNo(waybillNo);
	}
	/**
	 * 通过用户CODE调用综合接口获取用户姓名
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-7 上午11:00:46
	 */
	private String getUserNameByCode(String userCode){
		EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(userCode);
		if(employee != null){
			return employee.getEmpName();
		}else{
			return userCode;
		}
	}
	/**
	* @param waybillNo
	* @return
	* @description 提供给综合查询：根据运单号查询货件标签打印信息 明细
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-6 上午10:18:33
	*/
	@Override
	public List<GoodsLabelPrintDto> queryLabelPrintByWaybillNoDetail(
			String waybillNo) {
		return printLabelDao.queryLabelPrintByWaybillNoDetail(waybillNo);
	}
	
	/**
	* @description 根据部门code查询标签打印记录
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IPrintLabelService#queryPrintLabelInfoExpressByOrgCode(com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.PrintLabelDto, int, int)
	* @author 218381-foss-lijie
	* @update 2015年7月8日 下午2:35:27
	* @version V1.0
	*/
	@Override                  
	public List<SortingAndPringLabelDto> queryPrintLabelInfoExpressByOrgCode(
			PrintLabelDto printLabelDto, int start, int limit) {
		
		//查询标签打记录
		List<SortingAndPringLabelDto> goodsList = printLabelDao.queryPrintLabelForExpressByOrgCode(printLabelDto,start,limit);
		return goodsList;
		
	}
	
	/**
	* @description 根据部门code查询标签打印count
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IPrintLabelService#queryPrintLabelInfoExpressByOrgCodeCount(com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.PrintLabelDto)
	* @author 218381-foss-lijie
	* @update 2015年7月9日 下午2:54:18
	* @version V1.0
	*/
	@Override
	public Long queryPrintLabelInfoExpressByOrgCodeCount(
			PrintLabelDto printLabelDto) {
		return (Long)printLabelDao.queryPrintLabelInfoExpressByOrgCodeCount(printLabelDto);
	}
	
	/**
	* @description 根据部门code查询上分拣
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IPrintLabelService#querySortingInfoByOrgCode(com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.PrintLabelDto, int, int)
	* @author 218381-foss-lijie
	* @update 2015年7月8日 下午5:27:27
	* @version V1.0
	*/
	@Override
	public List<SortingAndPringLabelDto> querySortingInfoByOrgCode(
			PrintLabelDto printLabelDto, int start, int limit) {
		//查询上分拣记录
		List<SortingAndPringLabelDto> goodsList = printLabelDao.querySortingInfoByOrgCode(printLabelDto,start,limit);
		return goodsList;
	}
	
	/**
	* @description 根据部门code查询上分拣count
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IPrintLabelService#querySortingInfoByOrgCodeCount(com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.PrintLabelDto)
	* @author 218381-foss-lijie
	* @update 2015年7月9日 下午3:19:59
	* @version V1.0
	*/
	@Override
	public Long querySortingInfoByOrgCodeCount(PrintLabelDto printLabelDto) { 
		return (Long)printLabelDao.querySortingInfoByOrgCodeCount(printLabelDto);
	}
	
	/**
	* @description 导出标签打印记录或上分拣记录到Excel
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IPrintLabelService#exportExcelStream(java.lang.String, com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.PrintLabelDto)
	* @author 218381-foss-lijie
	* @update 2015年7月10日 下午2:44:58
	* @version V1.0
	*/
	@Override
	public InputStream exportExcelStream(String ids, SortingAndPringLabelDto sortingAndPringLabelDto) {
		
		InputStream excelStream = null;
	
		try{
				List<SortingAndPringLabelDto> sDtoList = null;
				//用户勾选了需要导出的库存信息
				if(StringUtils.isNotBlank(ids)){
					//如果是"1" 就导出标签打印信息
					if("1".equals(sortingAndPringLabelDto.getIsPrint())){
						List<String> idsList = Arrays.asList(ids.split(","));
						sDtoList = printLabelDao.queryExportPringLabel(idsList);
					}else{
						//导出上分拣信息
						List<String> idsList = Arrays.asList(ids.split(","));
						sDtoList = printLabelDao.queryExportSorting(idsList); 
					}
					
				}
				else{//用户没有勾选，导出当前查询条件下查询出的所有的库存
					if("1".equals(sortingAndPringLabelDto.getIsPrint())){
						String orgCode = FossUserContext.getCurrentDept().getCode();
						sortingAndPringLabelDto.setOrgCode(orgCode);
						sDtoList = printLabelDao.queryExportPringLabelAll(sortingAndPringLabelDto);
					}else{
						String orgCode = FossUserContext.getCurrentDept().getCode();
						sortingAndPringLabelDto.setOrgCode(orgCode);
						//导出上分拣信息
						sDtoList = printLabelDao.queryExportSortingAll(sortingAndPringLabelDto); 
					}
					
				}
				//行List
				List<List<String>> rowList = new ArrayList<List<String>>();
				for(SortingAndPringLabelDto dto : sDtoList){
					//每行的列List
					List<String> columnList = new ArrayList<String>();
					columnList.add(dto.getWaybillNo()+"");
					columnList.add(dto.getSerialNo()+"");
					columnList.add(dto.getOperateName()+"");
					columnList.add(dto.getOrgName()+"");
					columnList.add(DateUtils.convert(dto.getOperateTime(), DateUtils.DATE_TIME_FORMAT)+"");
					
					
					rowList.add(columnList);
				}
				SheetData sheetData = new SheetData();
				sheetData.setRowHeads(ExceptionGoodsConstants.ROW_HEADS);
				sheetData.setRowList(rowList);
				
				ExcelExport excelExportUtil = new ExcelExport();
				excelStream = excelExportUtil.inputToClient(excelExportUtil.exportExcel(sheetData, ExceptionGoodsConstants.SHEET_NAME, ExceptionGoodsConstants.SHEET_SIZE));
		}catch (BusinessException e) {
			LOGGER.error("导出异常", e);
			throw new BusinessException("导出异常",e);
		}
        return excelStream;
	
	}
	
	
	/**
	 * @author nly
	 * @date 2015年4月21日 下午5:07:44
	 * @function 查询最晚标签打印记录
	 * @param waybillNo
	 * @return
	 */
	@Override
	public PrintLabelEntity queryLastLabelPrintByWaybillNo(String waybillNo) {
		PrintLabelEntity entity = null;
		List<PrintLabelEntity> printList = printLabelDao.queryLabelPrintByNo(waybillNo);
		if(CollectionUtils.isNotEmpty(printList)) {
			entity = printList.get(0);
		}
		return entity;
	}
	
}
