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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/WaybillChargeDtlService.java
 * 
 * FILE NAME        	: WaybillChargeDtlService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillChargeDtlDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillChargeDtlService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOtherChargeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillSystemDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 运单费用明细服务
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-31 上午10:03:03,content:TODO
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-31 上午10:03:03
 * @since
 * @version
 */
public class WaybillChargeDtlService implements IWaybillChargeDtlService {

	/**
	 * 运单费用明细DAO
	 */
	private IWaybillChargeDtlDao waybillChargeDtlDao;

	/**
	 * @param waybillChargeDtlDao
	 *            the waybillChargeDtlDao to set.
	 */
	public void setWaybillChargeDtlDao(IWaybillChargeDtlDao waybillChargeDtlDao) {
		this.waybillChargeDtlDao = waybillChargeDtlDao;
	}

	@Override
	public int insert(WaybillChargeDtlEntity record) {
		return waybillChargeDtlDao.insert(record);
	}

	/**
	 * 批量新增费用明细实体
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 上午11:42:02
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillChargeDtlService#addWaybillChargeDtlEntityBatch(java.util.List)
	 */
	public int addWaybillChargeDtlEntityBatch(List<WaybillChargeDtlEntity> waybillChargeDtlList, WaybillSystemDto systemDto) {
		for (WaybillChargeDtlEntity entity : waybillChargeDtlList) {
			entity.setId(UUIDUtils.getUUID());
			// 设置创建人、创建时间、更新人、更新时间
			entity.setCreateTime(systemDto.getCreateTime());
			entity.setModifyTime(systemDto.getModifyTime());
			entity.setBillTime(systemDto.getBillTime());
			// 设置是否有效
			entity.setActive(FossConstants.ACTIVE);
		}
		return waybillChargeDtlDao.addBatch(waybillChargeDtlList);
	}

	@Override
	public WaybillChargeDtlEntity queryByPrimaryKey(String id) {
		return waybillChargeDtlDao.queryByPrimaryKey(id);
	}

	@Override
	public List<WaybillChargeDtlEntity> queryChargeDtlEntityByNo(String waybillNo) {
		return waybillChargeDtlDao.queryChargeDtlEntityByNo(waybillNo);
	}

	@Override
	public int updateByPrimaryKeySelective(WaybillChargeDtlEntity record) {
		return waybillChargeDtlDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<WaybillChargeDtlEntity> queryNewChargeDtlEntityByNO(LastWaybillRfcQueryDto queryDto) {
		return waybillChargeDtlDao.queryNewChargeDtlEntityByNo(queryDto);
	}

	/**
	 * 删除费用实体
	 * 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillChargeDtlService#deleteWaybillChargeDtlEntityById(java.lang.String)
	 */
	@Override
	public int deleteWaybillChargeDtlEntityById(String id) {
		return waybillChargeDtlDao.deleteWaybillChargeDtlEntityById(id);
	}
	
	/**
	 * 根据运单号删除费用实体
	 * 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillChargeDtlService#deleteWaybillChargeDtlEntityById(java.lang.String)
	 */
	@Override
	public int deleteWaybillChargeDtlEntityByWaybillNo(String waybillNo) {
		return waybillChargeDtlDao.deleteWaybillChargeDtlEntityByWaybillNo(waybillNo);
	}

	@Override
	public int appendWaybillChargeDtlEntityBatchAfterChange(List<WaybillChargeDtlEntity> waybillChargeDtlList, WaybillSystemDto systemDto) {
		for (WaybillChargeDtlEntity entity : waybillChargeDtlList) {
			entity.setId(UUIDUtils.getUUID());
			// 设置创建人、创建时间、更新人、更新时间
			entity.setCreateTime(systemDto.getCreateTime());
			entity.setModifyTime(systemDto.getModifyTime());
			entity.setBillTime(systemDto.getBillTime());
			// 设置是否有效
			entity.setActive(FossConstants.INACTIVE);
		}
		return waybillChargeDtlDao.addBatch(waybillChargeDtlList);
	}

	/**
	 * 根据运单号查询费用明细中的其它费用
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-15 下午3:01:04
	 */
	@Override
	public List<WaybillOtherChargeDto> queryOtherChargeByNo(String waybillNo) {
		return waybillChargeDtlDao.queryOtherChargeByNo(waybillNo);
	}

	/**
	 * 激活运单明细---电子运单
	 * @author liyongfei--DMANA-2035
	 * @param waybillNoList
	 * @return
	 */
	@Override
	public int setWaybillChargeDtlActive(List<String> waybillNoList) {
		// TODO Auto-generated method stub
		return waybillChargeDtlDao.setWaybillChargeDtlActive(waybillNoList);
	}
	
	
}