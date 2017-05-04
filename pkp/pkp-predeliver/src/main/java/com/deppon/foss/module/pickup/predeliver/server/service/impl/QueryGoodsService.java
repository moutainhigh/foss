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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/service/impl/QueryGoodsService.java
 * 
 * FILE NAME        	: QueryGoodsService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IQueryGoodsDao;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IQueryGoodsService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.GoodsInfoConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.GoodsInfoDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.util.BigDecimalOperationUtil;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 查询货量Service
 * @author 043258-foss-zhaobin
 * @date 2012-10-16 下午2:37:24
 * @since
 * @version
 */
public class QueryGoodsService implements IQueryGoodsService 
{	
	/**
	 * 全部
	 */
	private static final String TOTAL = "ALL";
	/** 
	 * 常量20000.
	 */
	private static final int NUMBER = 20000;
	/**
	 * 获得查询货量Dao
	 */
	private IQueryGoodsDao queryGoodsDao;
	
	/**
	 * 外场相关共通接口
	 */
	private IHandleQueryOutfieldService handleQueryOutfieldService;
	
	/**
	 * Sets the query goods dao.
	 *
	 * @param queryGoodsDao the new query goods dao
	 */
	public void setQueryGoodsDao(IQueryGoodsDao queryGoodsDao) {
		this.queryGoodsDao = queryGoodsDao;
	}
	
	public void setHandleQueryOutfieldService(
			IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}

	/**
	 * 查询货量.
	 *
	 * @param goodsInfoConditionDto the goods info condition dto
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 043258-foss-zhaobin
	 * @date 2012-10-22 下午5:18:40
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IQueryGoodsDao#getGoodsInfoCount(com.deppon.foss.module.pickup.predeliver.api.shared.dto.GoodsInfoConditionDto)
	 */
	@Override
	public List<GoodsInfoDto> queryGoods(GoodsInfoConditionDto goodsInfoConditionDto,int start, int limit) 
	{
		//如果传入dto不为空
		if(goodsInfoConditionDto != null)
		{
			//如果提货方式为"全部"
			if(goodsInfoConditionDto.getReceiveMethod().equals(TOTAL))
			{	
				//传入空
				goodsInfoConditionDto.setReceiveMethod("");
			}
			//如果是否排单为"全部"
			if(goodsInfoConditionDto.getArrangementState().equals(TOTAL))
			{
				//传入空
				goodsInfoConditionDto.setArrangementState("");
			}
			
			// 获取当前部门
			String currOrgCode = FossUserContextHelper.getOrgCode();
			//获取当前用户所在部门
			goodsInfoConditionDto.setDepartmentCode(currOrgCode);
			//传入更改单状态 
			//已同意
			goodsInfoConditionDto.setWbrStatus(WaybillRfcConstants.ACCECPT);
			//有效
			goodsInfoConditionDto.setActive(FossConstants.ACTIVE);
			
			
			//查询货物信息
			List<GoodsInfoDto> goodsInfoDtoList = queryGoodsDao.queryGoods(goodsInfoConditionDto, start, limit);
			for (GoodsInfoDto goodsInfoDto : goodsInfoDtoList) {
				//拼接得到客户地址 省-市-区县-具体地址
				String custAddr = handleQueryOutfieldService.getCompleteAddressAttachAddrNote(goodsInfoDto.getReceiveCustomerProvCode(), goodsInfoDto.getReceiveCustomerCityCode(), goodsInfoDto.getReceiveCustomerDistCode(), goodsInfoDto.getReceiveCustomerAddress(), goodsInfoDto.getReceiveCustomerAddressNote());
				goodsInfoDto.setReceiveCustomerAddress(custAddr);
			}
			//返回查询信息
			return goodsInfoDtoList;
		}
		//返回空
		return null;
	}
	
	/**
	 * 查询货量总条数，用于分页.
	 *
	 * @param goodsInfoConditionDto the goods info condition dto
	 * @return the goods info count
	 * @author 043258-foss-zhaobin
	 * @date 2012-10-22 下午5:18:40
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IQueryGoodsDao#getGoodsInfoCount(com.deppon.foss.module.pickup.predeliver.api.shared.dto.GoodsInfoConditionDto)
	 */
	@Override
	public Long getGoodsInfoCount(GoodsInfoConditionDto goodsInfoConditionDto) 
	{
		//如果传入dto不为空
		if(goodsInfoConditionDto != null)
		{
			//如果提货方式为"全部"
			if(goodsInfoConditionDto.getReceiveMethod().equals(TOTAL))
			{
				//提货方式传空
				goodsInfoConditionDto.setReceiveMethod("");
			}
			//如果是否排单为"全部"
			if(goodsInfoConditionDto.getArrangementState().equals(TOTAL))
			{
				//排单传空
				goodsInfoConditionDto.setArrangementState("");
			}
			// 获取当前部门
			String currOrgCode = FossUserContextHelper.getOrgCode();
			//传入更改单状态 
			//已同意
			goodsInfoConditionDto.setWbrStatus(WaybillRfcConstants.ACCECPT);
			//有效
			goodsInfoConditionDto.setActive(FossConstants.ACTIVE);
			//获取当前用户所在部门
			goodsInfoConditionDto.setDepartmentCode(currOrgCode);
			
			//返回货量总条数
			return queryGoodsDao.getGoodsInfoCount(goodsInfoConditionDto);
		}
		//返回0
		return Long.valueOf(0);
	}
	
	/**
	 * 查询总货量 非分页.
	 *
	 * @param goodsInfoConditionDto the goods info condition dto
	 * @return the goods info condition dto
	 * @author 043258-foss-zhaobin
	 * @date 2012-11-22 下午3:31:16
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IQueryGoodsService#queryGoodsTotal(com.deppon.foss.module.pickup.predeliver.api.shared.dto.GoodsInfoConditionDto)
	 */
	@Override
	public GoodsInfoConditionDto queryGoodsTotal(GoodsInfoConditionDto goodsInfoConditionDto) 
	{
		//如果传入dto不为空
		if(goodsInfoConditionDto != null)
		{
			//默认值设为0
			BigDecimal goodsWeightTotal = BigDecimal.ZERO;
			//默认值设为0
			BigDecimal goodsVolumeTotal = BigDecimal.ZERO;
			//默认值设为0
			BigDecimal toPayAmountTotal = BigDecimal.ZERO;
			Integer goodsPieceTotal = NumberConstants.ZERO;
			//查询货量返回Dto
			List<GoodsInfoDto> goodsInfoDtoList = queryGoodsDao.queryGoodsInfo(goodsInfoConditionDto);
			//循环货量List
			for (GoodsInfoDto goodsInfoDto : goodsInfoDtoList) 
			{	
				//货物重量=运单重量/运单件数*（货物在途件数+当前部门库存件数）
				goodsWeightTotal = goodsWeightTotal.add(BigDecimalOperationUtil.mul(
						BigDecimalOperationUtil.div(goodsInfoDto.getGoodsWeight(),BigDecimal.valueOf(goodsInfoDto.getGoodsQtyTotal())), 
						BigDecimal.valueOf(goodsInfoDto.getGoodsHandoverTotal()+goodsInfoDto.getGoodsStoreTotal()), 2));
				//货物体积=运单体积/运单件数*（货物在途件数+当前部门库存件数）
				goodsVolumeTotal = goodsVolumeTotal.add(BigDecimalOperationUtil.mul(
						BigDecimalOperationUtil.div(goodsInfoDto.getGoodsVolume(),BigDecimal.valueOf(goodsInfoDto.getGoodsQtyTotal())),
						BigDecimal.valueOf(goodsInfoDto.getGoodsHandoverTotal()+goodsInfoDto.getGoodsStoreTotal()),2));
				goodsPieceTotal = goodsPieceTotal+(goodsInfoDto.getGoodsHandoverTotal()+goodsInfoDto.getGoodsStoreTotal());
				toPayAmountTotal = toPayAmountTotal.add(goodsInfoDto.getToPayAmount());
			}
			//设置总重量
			goodsInfoConditionDto.setGoodsWeightTotal(BigDecimalOperationUtil.div(goodsWeightTotal,new BigDecimal(NumberConstants.NUMBER_1000),NumberConstants.NUMBER_3).toString());
			//设置总体积
			goodsInfoConditionDto.setGoodsVolumeTotal(goodsVolumeTotal.toString());
			//设置总件数
			goodsInfoConditionDto.setGoodsPieceTotal(goodsPieceTotal.toString());
			//设置总到付运费
			goodsInfoConditionDto.setToPayAmountTotal(toPayAmountTotal.toString());
			return goodsInfoConditionDto;
		}
		//返回空
		return null;
	}
	
	/**
	 * 
	 * 查询总货量 非分页 用于导出
	 * @author 043258-foss-zhaobin
	 * @date 2013-5-31 上午11:06:33
	 */
	@Override
	public InputStream queryGoodsInfo(GoodsInfoConditionDto goodsInfoConditionDto)
	{
		//如果传入dto不为空
		if(goodsInfoConditionDto != null)
		{
			//如果提货方式为"全部"
			if(goodsInfoConditionDto.getReceiveMethod().equals(TOTAL))
			{
				//提货方式传空
				goodsInfoConditionDto.setReceiveMethod("");
			}
			//如果是否排单为"全部"
			if(goodsInfoConditionDto.getArrangementState().equals(TOTAL))
			{
				//排单传空
				goodsInfoConditionDto.setArrangementState("");
			}
			// 获取当前部门
			String currOrgCode = FossUserContextHelper.getOrgCode();
			//传入更改单状态 
			//已同意
			goodsInfoConditionDto.setWbrStatus(WaybillRfcConstants.ACCECPT);
			//有效
			goodsInfoConditionDto.setActive(FossConstants.ACTIVE);
			//获取当前用户所在部门
			goodsInfoConditionDto.setDepartmentCode(currOrgCode);
			//查询货量返回Dto
			List<GoodsInfoDto> goodsInfoDtoList = queryGoodsDao.queryGoodsInfo(goodsInfoConditionDto);
			List<List<String>> rowList = new ArrayList<List<String>>();
			for (GoodsInfoDto goodsInfoDto : goodsInfoDtoList) {
				List<String> columnList = new ArrayList<String>();
				//运单号
				columnList.add(goodsInfoDto.getWaybillNo());
				//货物名称
				columnList.add(goodsInfoDto.getGoodsName());
				//重量
				columnList.add(goodsInfoDto.getGoodsWeight().toString());
				//体积
				columnList.add(goodsInfoDto.getGoodsVolume().toString());
				//到付金额
				columnList.add(goodsInfoDto.getToPayAmount().toString());
				//件数
				columnList.add(goodsInfoDto.getGoodsQtyTotal().toString());
				//在途件数
				columnList.add(goodsInfoDto.getGoodsHandoverTotal().toString());
				//库存件数
				columnList.add(goodsInfoDto.getGoodsStoreTotal().toString());
				//客户名称
				columnList.add(goodsInfoDto.getReceiveCustomerName());
				//客户手机
				columnList.add(goodsInfoDto.getReceiveCustomerMobilephone());
				//客户电话
				columnList.add(goodsInfoDto.getReceiveCustomerPhone());
				//拼接得到客户地址 省-市-区县-具体地址
				String custAddr = handleQueryOutfieldService.getCompleteAddressAttachAddrNote(goodsInfoDto.getReceiveCustomerProvCode(), goodsInfoDto.getReceiveCustomerCityCode(), goodsInfoDto.getReceiveCustomerDistCode(), goodsInfoDto.getReceiveCustomerAddress(), goodsInfoDto.getReceiveCustomerAddressNote());
				//客户地址
				columnList.add(custAddr);
				//预计到达时间
				columnList.add(DateUtils.convert(goodsInfoDto.getPreArriveTime(), DateUtils.DATE_TIME_FORMAT));
				String noticeResult = null;
				if(goodsInfoDto.getNotificationtype().equals("SUCCESS"))
				{
					noticeResult = "是";
				}else
				{
					noticeResult = "否";
				}
				//是否联系客户
				columnList.add(noticeResult);
				//预计派送/提货时间
				columnList.add(DateUtils.convert(goodsInfoDto.getPreCustomerPickupTime(), DateUtils.DATE_TIME_FORMAT));
				if(goodsInfoDto.getTransportType().equals("TRANS_VEHICLE"))
				{
					//送货方式
					columnList.add(DictUtil.rendererSubmitToDisplay(goodsInfoDto.getReceiveMethod(), DictionaryConstants.PICKUP_GOODS));
				}else{
					//送货方式
					columnList.add(DictUtil.rendererSubmitToDisplay(goodsInfoDto.getReceiveMethod(), DictionaryConstants.PICKUP_GOODS_AIR));
				}
				if(goodsInfoDto.getArrangementTotal()>0)
				{
					//排单状态
					columnList.add("已排单");
				}else
				{
					//排单状态
					columnList.add("未排单");
				}
				rowList.add(columnList);
			}
			String[] rowHeads = {"运单号","货物名称","重量","体积","到付金额","件数","在途件数","库存件数","客户名称","客户手机","客户电话","客户地址","预计到达时间","是否联系客户","预计派送/提货时间","送货方式","排单状态"};
			
		    ExportResource exportResource = new ExportResource();
		    exportResource.setHeads(rowHeads);
		    exportResource.setRowList(rowList);
		    ExportSetting exportSetting = new ExportSetting();
		    exportSetting.setSheetName("货量列表");
		    exportSetting.setSize(NUMBER);
		    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	        return objExporterExecutor.exportSync(exportResource, exportSetting);
		} else
		{
			//返回空
			return null;
		}
		
	}
}