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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/WaybillDisDtlService.java
 * 
 * FILE NAME        	: WaybillDisDtlService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillDisDtlService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillSystemDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 运单费用折扣明细服务
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-31 上午10:04:41,content:TODO
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-31 上午10:04:41
 * @since
 * @version
 */
public class WaybillDisDtlService implements IWaybillDisDtlService {
	/**
	 * 运单折扣明细DAO
	 */
	private IWaybillDisDtlDao waybillDisDtlDao;

	/**
	 * 
	 * 注入运单折扣明细DAO
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-24 下午10:26:52
	 */
	public void setWaybillDisDtlDao(IWaybillDisDtlDao waybillDisDtlDao) {
		this.waybillDisDtlDao = waybillDisDtlDao;
	}

	/**
	 * 
	 * 插入运单折扣明细 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-24 下午10:27:16
	 */
	@Override
	public int insert(WaybillDisDtlEntity record) {
		return waybillDisDtlDao.insert(record);
	}
	
	/**
     * 插入部分对象数据
     * @author WangQianJin
     * @date 2014-05-21
     */
	@Override
    public int insertSelective(WaybillDisDtlEntity record){
    	return waybillDisDtlDao.insertSelective(record);
    }

	/**
	 * 批量增加WaybillDisDtlEntity实休数据
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 上午10:59:49
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillDisDtlService#addWaybillDisDtlEntityBatch(java.util.List)
	 */
	@Override
	public int addWaybillDisDtlEntityBatch(List<WaybillDisDtlEntity> list, WaybillSystemDto systemDto) {
		for (WaybillDisDtlEntity entity : list) {
			entity.setId(UUIDUtils.getUUID());
			// 设置创建人、创建时间、更新人、更新时间
			entity.setCreateTime(systemDto.getCreateTime());
			entity.setModifyTime(systemDto.getModifyTime());
			entity.setBillTime(systemDto.getBillTime());
			// 设置是否有效
			entity.setActive(FossConstants.ACTIVE);
		}
		return waybillDisDtlDao.addBatch(list);
	}

    /**
     * 根据主键ID查询运单折扣明细
     * @author 026123-foss-lifengteng
     * @date 2012-12-24 下午10:27:50
     */
	@Override
	public WaybillDisDtlEntity queryByPrimaryKey(String id) {
		return waybillDisDtlDao.queryByPrimaryKey(id);
	}

	/**
	 * 根据运单号查询WaybillDisDtlEntity列表
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 下午4:19:24
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillDisDtlService#queryDisDtlEntityByNo(java.lang.String)
	 */
	@Override
	public List<WaybillDisDtlEntity> queryDisDtlEntityByNo(String waybillNo) {
		return waybillDisDtlDao.queryDisDtlEntityByNo(waybillNo);
	}

    /**
     * 选择性更新运单折扣明细信息
     * @author 026123-foss-lifengteng
     * @date 2012-12-24 下午10:28:33
     */
	@Override
	public int updateByPrimaryKeySelective(WaybillDisDtlEntity record) {
		return waybillDisDtlDao.updateByPrimaryKeySelective(record);
	}

	/**
	 * 
	 * <p>
	 * 运单折扣明细<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-12-1
	 * @param waybillNo
	 * @return List<WaybillDisDtlEntity>
	 */
	@Override
	public List<WaybillDisDtlEntity> queryNewDisDtlEntityByNo(LastWaybillRfcQueryDto queryDto) {
		return waybillDisDtlDao.queryNewDisDtlEntityByNo(queryDto);
	}

	/**
	 * 删除折扣明细
	 * 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillDisDtlService#deleteWaybillDisDtlEntityById(java.lang.String)
	 */
	@Override
	public int deleteWaybillDisDtlEntityById(String id) {
		return waybillDisDtlDao.deleteWaybillDisDtlEntityById(id);
	}
	
	/**
	 * 删除折扣明细
	 * 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillDisDtlService#deleteWaybillDisDtlEntityById(java.lang.String)
	 */
	@Override
	public int deleteWaybillDisDtlEntityByWaybillNo(String waybillNo) {
		return waybillDisDtlDao.deleteWaybillDisDtlEntityByWaybillNo(waybillNo);
	}

    /**
     * 
     * 追加更改单起草时WaybillDisDtlEntity实休数据
     * @author 102246-foss-shaohongliang
     * @date 2012-12-3 下午3:49:08
     */
	@Override
	public int appendWaybillDisDtlEntityBatchAfterChange(List<WaybillDisDtlEntity> list, WaybillSystemDto systemDto) {
		for (WaybillDisDtlEntity entity : list) {
			entity.setId(UUIDUtils.getUUID());
			// 设置创建人、创建时间、更新人、更新时间
			entity.setCreateTime(systemDto.getCreateTime());
			entity.setModifyTime(systemDto.getModifyTime());
			entity.setBillTime(systemDto.getBillTime());
			// 设置是否有效
			entity.setActive(FossConstants.INACTIVE);
		}
		return waybillDisDtlDao.addBatch(list);
	}
	
	/**
	 * 根据运单号和类型查询CRM营销活动
	 * @创建时间 2014-4-22 下午8:34:33   
	 * @创建人： WangQianJin
	 */
	@Override
	public WaybillDisDtlEntity queryActiveInfoByNoAndType(String waybillNo){
		return waybillDisDtlDao.queryActiveInfoByNoAndType(waybillNo);
	}
	
	/**
	 * 根据运单号与类型修改折扣信息状态
	 * @创建时间 2014-5-10 下午6:24:22   
	 * @创建人： WangQianJin
	 */
	@Override
	public int updateByWaybillNoAndType(WaybillDisDtlEntity record){
		return waybillDisDtlDao.updateByWaybillNoAndType(record);
	}
	
	/**
	 * 激活运单折扣费用明细
	 * @author liyongfei--DMANA-2035
	 * @param waybillNoList
	 * @return
	 */
	@Override
	public int setWaybillDisDtlActive(List<String> waybillNoList) {
		// TODO Auto-generated method stub
		return waybillDisDtlDao.setWaybillDisDtlActive(waybillNoList);
	}

	
}