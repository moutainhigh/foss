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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/server/service/impl/ChangeLabelGoodsService.java
 *  
 *  FILE NAME          :ChangeLabelGoodsService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabelPrintInfoService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.unload.api.server.dao.IChangeLabelGoodsDao;
import com.deppon.foss.module.transfer.unload.api.server.service.IChangeLabelGoodsService;
import com.deppon.foss.module.transfer.unload.api.shared.define.ChangeLabelGoodsConstants;
import com.deppon.foss.module.transfer.unload.api.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ChangeLabelGoodsDto;

/**
 * 查询重贴标签货物Service
 * @author ibm-zhangyixin
 * @date 2012-12-25 下午2:18:08
 */
public class ChangeLabelGoodsService implements IChangeLabelGoodsService {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ChangeLabelGoodsService.class);
	/**
	 * 查询重贴标签货物dao
	 */
	private IChangeLabelGoodsDao changeLabelGoodsDao;
	/***
	 * 打印标签service
	 */
	private ILabelPrintInfoService labelPrintInfoService;

	/**
	 * 查询重贴标签货物-卸车查询tab中的查询
	 * @author ibm-zhangyixin
	 * @date 2012-11-28 上午10:03:20
	 */
	@Override
	public List<ChangeLabelGoodsDto> queryChangeLabelGoodsUnload(
			ChangeLabelGoodsDto changeLabelGoodsDto, int limit, int start) {
		changeLabelGoodsDto.setOrgCode(FossUserContext.getCurrentDeptCode());
		//查询重贴标签货物-卸车查询tab中的查询
		return changeLabelGoodsDao.queryChangeLabelGoodsUnload(changeLabelGoodsDto, limit, start);
	}

	/**
	 * 查询重贴标签货物-卸车查询tab中的查询总记录数
	 * @author ibm-zhangyixin
	 * @date 2012-11-28 上午10:03:50
	 */
	@Override
	public Long getTotCountUnload(ChangeLabelGoodsDto changeLabelGoodsDto) {
		changeLabelGoodsDto.setOrgCode(FossUserContext.getCurrentDeptCode());
		//查询重贴标签货物-卸车查询tab中的查询总记录数
		return changeLabelGoodsDao.getTotCountUnload(changeLabelGoodsDto);
	}

	/**
	 * 查询重贴标签货物-清仓查询tab中的查询
	 * @author ibm-zhangyixin
	 * @date 2012-11-28 上午10:04:29
	 */
	@Override
	public List<ChangeLabelGoodsDto> queryChangeLabelGoodsStock(
			ChangeLabelGoodsDto changeLabelGoodsDto, int limit, int start) {
		changeLabelGoodsDto.setOrgCode(FossUserContext.getCurrentDeptCode());
		//查询重贴标签货物-清仓查询tab中的查询
		return changeLabelGoodsDao.queryChangeLabelGoodsStock(changeLabelGoodsDto, limit, start);
	}

	/**
	 * 查询重贴标签货物-清仓查询tab中的查询总记录数 
	 * @author ibm-zhangyixin
	 * @date 2012-11-28 上午10:04:39
	 */
	@Override
	public Long getTotCountStock(ChangeLabelGoodsDto changeLabelGoodsDto) {
		changeLabelGoodsDto.setOrgCode(FossUserContext.getCurrentDeptCode());
		//查询重贴标签货物-清仓查询tab中的查询总记录数 
		return changeLabelGoodsDao.getTotCountStock(changeLabelGoodsDto);
	}

	/**
	 * 查询重贴标签打印所需的数据
	 * @author ibm-zhangyixin
	 * @date 2012-11-28 上午11:46:17
	 */
	@Override
	public List<BarcodePrintLabelDto> printChangeLabelGoodsAgain(
			ChangeLabelGoodsDto changeLabelGoodsDto) {
		//需要打印的新流水号
		//流水号必填, 为空抛出异常
		if(StringUtils.isEmpty(changeLabelGoodsDto.getSerialNo())){
			LOGGER.error("流水号为空");
			throw new TfrBusinessException("流水号为空");
		}
		//运单号必填, 为空抛出异常
		if(StringUtils.isEmpty(changeLabelGoodsDto.getWaybillNo())){
			LOGGER.error("运单号为空");
			throw new TfrBusinessException("运单号为空");
		}
		List<String> serialNoList = new ArrayList<String>(1);
		serialNoList.add(changeLabelGoodsDto.getSerialNo());
		/**
		 * 调用接送货接口获取打印标签的数据
		 * 
		 */
		try {
			List<com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto> barcodePrintDtoList = null;
			if(StringUtils.equals(changeLabelGoodsDto.getChangeReason(), ChangeLabelGoodsConstants.CHANGE_REASON_BY_MODIFY)) {
				//如果为有更改就调用另外一个接口
				barcodePrintDtoList = labelPrintInfoService.getLabelPrintInfoReceAndStockAndArri(changeLabelGoodsDto.getWaybillNo(), serialNoList);
			} else {
				//得到标签打印的信息
				barcodePrintDtoList = labelPrintInfoService.getLabelPrintInfoForDepart(changeLabelGoodsDto.getWaybillNo(), serialNoList);
			}
			if(barcodePrintDtoList == null || barcodePrintDtoList.size() == 0){
				throw new TfrBusinessException("运单打印数据为空");
			}
			
			//构建自己的barcodeprintlabeldto对象
			//因为这边的pom不能引用对方的对象, 所以只能重新写一个
			List<BarcodePrintLabelDto> barcodePrintLabelDtos = new ArrayList<BarcodePrintLabelDto>(barcodePrintDtoList.size());
			for(com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto barcodePrintLabelDto : barcodePrintDtoList) {
				BarcodePrintLabelDto barcodePrintLabelDto2 = new BarcodePrintLabelDto();
				BeanUtils.copyProperties(barcodePrintLabelDto, barcodePrintLabelDto2);
				barcodePrintLabelDtos.add(barcodePrintLabelDto2);
			}
			
			return barcodePrintLabelDtos;
		} catch (NullPointerException e) {
			LOGGER.error("运单相关打印数据不全");
			throw new TfrBusinessException("运单相关打印数据不全");
		}
	}

	/** 
	 * @Title: updateChangeLabelGoods 
	 * @Description: 打印完标签后更新数据库中的状态
	 * @param changeLabelGoodsDto
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IChangeLabelGoodsService#updateChangeLabelGoods(com.deppon.foss.module.transfer.unload.api.shared.dto.ChangeLabelGoodsDto)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-3-28上午10:28:13
	 */ 
	@Override
	public int updateChangeLabelGoods(ChangeLabelGoodsDto changeLabelGoodsDto) {
		changeLabelGoodsDto.setHandleStatus(ChangeLabelGoodsConstants.HANDLESTATUS_PROCESSED);
		changeLabelGoodsDto.setHandlerCode(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
		changeLabelGoodsDto.setHandlerName(FossUserContext.getCurrentUser().getEmployee().getEmpName());
		changeLabelGoodsDto.setHandleTime(new Date());
		return changeLabelGoodsDao.updateChangeLabelGoods(changeLabelGoodsDto);
	}
	
	/**
	 * 设置 查询重贴标签货物dao.
	 *
	 * @param changeLabelGoodsDao the new 查询重贴标签货物dao
	 */
	public void setChangeLabelGoodsDao(IChangeLabelGoodsDao changeLabelGoodsDao) {
		this.changeLabelGoodsDao = changeLabelGoodsDao;
	}
	
	/**
	 * 设置 * 打印标签service.
	 *
	 * @param labelPrintInfoService the new * 打印标签service
	 */
	public void setLabelPrintInfoService(
			ILabelPrintInfoService labelPrintInfoService) {
		this.labelPrintInfoService = labelPrintInfoService;
	}

}