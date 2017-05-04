/**
 *  initial comments.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
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
 *  PROJECT NAME  : tfr-management
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/server/action/FocusSignBillAction.java
 *  
 *  FILE NAME          :FocusSignBillAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.management.api.server.service.IFocusSignBillService;
import com.deppon.foss.module.transfer.management.api.shared.dto.FocusSignBillDto;
import com.deppon.foss.module.transfer.management.api.shared.vo.FocusSignBillVo;
/**
 * 集中接货签单Action
 * @author 038300-foss-pengzhen
 * @date 2012-11-30 上午11:29:37
 */
public class FocusSignBillAction  extends AbstractAction{
	
	/**
	 * uid
	 */
	private static final long serialVersionUID = 5524133452197392545L;
	/**日志*/
	private static final Logger LOGGER = LogManager.getLogger(FocusSignBillAction.class);
	/**容器注入service*/
	private IFocusSignBillService focusSignBillService;
	
	/**集中接货签单vo*/
	private FocusSignBillVo vo = new FocusSignBillVo();
	
	/** 导出Excel 文件流*/
	private InputStream excelStream;  
	/** 导出Excel 文件名*/
	private String fileName;  

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
	 * 获取 集中接货签单vo.
	 *
	 * @return the 集中接货签单vo
	 */
	public FocusSignBillVo getVo() {
		return vo;
	}
	
	/**
	 * 设置 集中接货签单vo.
	 *
	 * @param vo the new 集中接货签单vo
	 */
	public void setVo(FocusSignBillVo vo) {
		this.vo = vo;
	}
	
	/**
	 * 设置 容器注入service.
	 *
	 * @param focusSignBillService the new 容器注入service
	 */
	public void setFocusSignBillService(IFocusSignBillService focusSignBillService) {
		this.focusSignBillService = focusSignBillService;
	}
	
	/**
	 * 查询集中接货签单信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-27 下午4:29:57
	 */
	@JSON
	public String queryFocusSignBill(){
		try{
			//封装查询条件
			FocusSignBillDto dto = new FocusSignBillDto();
			//注册日期转换器
			ConvertUtils.register(new DateConverter(null), java.util.Date.class);
			//注册BigDecimal类型转换器
			ConvertUtils.register(new BigDecimalConverter(null), java.math.BigDecimal.class);
			//属性拷贝
			BeanUtils.copyProperties(dto, vo);
			//放入集中接货签单信息
			vo.setFocusSignBillDtoList(focusSignBillService.queryFocusSignBill(dto, this.getStart(), this.getLimit()));
			//放入集中接货签单信息总条数
			this.setTotalCount(focusSignBillService.queryFocusSignBillCount(dto));
			return super.returnSuccess();
		}catch(BusinessException e){
			return super.returnError(e);
		} catch (IllegalAccessException e) {
			LOGGER.error(e);
			return super.returnError(e.getMessage());
		} catch (InvocationTargetException e) {
			LOGGER.error(e);
			return super.returnError(e.getMessage());
		}
		
	}
	
	/**
	 * 查询运单的基本信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-30 上午8:51:58
	 */
	public String queryWayBill(){
		try{
			//查询运单的基本信息
			WaybillEntity waybillInfo = focusSignBillService.queryWayBill(vo.getWayBillNo());
			if(waybillInfo == null) {
				throw new BusinessException("无该运单信息");
			}
			//获取用车部门name
			String orgName = focusSignBillService.getOrgNameByCode(waybillInfo.getReceiveOrgCode());
			//用车部门name
			vo.setUseTruckOrgName(orgName);
			//放入运单的基本信息
			vo.setWaybillInfo(waybillInfo);
			return returnSuccess();
		}catch(BusinessException e){
			return returnError(e);
		}
	}
	
	public String queryOrgNameByCode() {
		try{
			//根据部门Code查name
			String orgName = focusSignBillService.getOrgNameByCode(vo.getUseTruckOrgCode());
			//放入运单的基本信息
			vo.setUseTruckOrgName(orgName);
			return super.returnSuccess();
		}catch(BusinessException e){
			return super.returnError(e);
		}
	}
	
	/**
	 * 保存集中接货签单
	 * @author 038300-foss-pengzhen
	 * @param entity 集中接货签单信息
	 * @param addList 增加的明细列表
	 * @param updateList 更新的明细列表
	 * @param deleteIdList 删除的明细列表
	 * @param userInfo 当前用户信息
	 * @date 2012-12-3 下午1:55:36
	 */
	public String saveFocusSignBill(){
		try{
			//获取当前操作人信息
			CurrentInfo userInfo  = FossUserContext.getCurrentInfo();
			// 保存集中接货签单
			focusSignBillService.saveFocusSignBill(vo.getFocusSignBillEntity(), 
						vo.getAddList(), vo.getUpdateList(), vo.getDeleteIdList(), userInfo, vo.isOperType());
			return super.returnSuccess();
		}catch(BusinessException e){
			return super.returnError(e);
		}
	}
	/**
	 * 删除签单信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-15 下午6:46:13
	 */
	public String deleteFocusSignBill(){
		try{
			//签单编号.
			String signBillNumber = vo.getSignBillNo();
			// 删除签单信息
			focusSignBillService.deleteFocusSignBill(signBillNumber);
			return super.returnSuccess();
		}catch(BusinessException e){
			return super.returnError(e);
		}
	}
	/**
	 * 编辑时通过签单号查询签单信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-15 下午6:47:44
	 */
	public String queryFocusSignBillByNo(){
		try{
			//设置签单信息
			vo.setFocusSignBillEntity(focusSignBillService.queryFocusSignBillByNo(vo.getSignBillNo()));
			//设置签单明细
			vo.setFeeDetailList(focusSignBillService.queryWaybillFeeDetailByNo(vo.getSignBillNo()));
			return super.returnSuccess();
		}catch(BusinessException e){
			return super.returnError(e);
		}
	}
	
	/**
	 * 编辑时通过签单ID查询签单信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-15 下午6:47:44
	 */
	public String queryFocusSignBillById(){
		try{
			//设置签单信息
			vo.setFocusSignBillEntity(focusSignBillService.queryFocusSignBillById(vo.getId()));
			//设置签单明细
			vo.setFeeDetailList(focusSignBillService.queryWaybillFeeDetailByNo(vo.getId()));
			return super.returnSuccess();
		}catch(BusinessException e){
			return super.returnError(e);
		}
	}
	
	/**
	 * 查询集中接货签单合计信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-26 下午3:35:56
	 */
	public String queryFocusSignBillTotal(){
		try{
			//封装查询条件
			FocusSignBillDto dto = new FocusSignBillDto();
			//注册日期转换器
			ConvertUtils.register(new DateConverter(null), java.util.Date.class);
			//注册BigDecimal类型转换器
			ConvertUtils.register(new BigDecimalConverter(null), java.math.BigDecimal.class);
			//属性拷贝
			BeanUtils.copyProperties(dto, vo);
			// 放入集中接货签单合计信息
			vo.setTotalDto(focusSignBillService.queryFocusSignBillTotal(dto));
			return super.returnSuccess();
		}catch(BusinessException e){
			return super.returnError(e);
		} catch (IllegalAccessException e) {
			LOGGER.error(e);
			return super.returnError(e.getMessage());
		} catch (InvocationTargetException e) {
			LOGGER.error(e);
			return super.returnError(e.getMessage());
		}
	}
	
	/**
	 * 查询集中接货签单信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-27 下午4:29:57
	 */
	public String queryExportFocusSignBill(){		
		try{
			//封装查询条件
			FocusSignBillDto dto = new FocusSignBillDto();
			//注册日期转换器
			ConvertUtils.register(new DateConverter(null), java.util.Date.class);
			//注册BigDecimal类型转换器
			ConvertUtils.register(new BigDecimalConverter(null), java.math.BigDecimal.class);
			//属性拷贝
			BeanUtils.copyProperties(dto, vo);
			//excel名字
			fileName=URLEncoder.encode("导出集中接货签单","UTF-8");
			// 查询集中接货签单信息
			excelStream=focusSignBillService.queryExportFocusSignBill(dto);		
		}catch (BusinessException e) {
			
			return returnError(e);
		} catch (IllegalAccessException e) {
			LOGGER.error(e);
			return super.returnError(e.getMessage());
		} catch (InvocationTargetException e) {
			LOGGER.error(e);
			return super.returnError(e.getMessage());
		}catch (UnsupportedEncodingException e) {
			
			return returnError(e.getMessage());
		}
		return this.returnSuccess();
	}
}