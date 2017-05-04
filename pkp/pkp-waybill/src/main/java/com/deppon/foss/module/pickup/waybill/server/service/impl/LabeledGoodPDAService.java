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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/LabeledGoodPDAService.java
 * 
 * FILE NAME        	: LabeledGoodPDAService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.pickup.waybill.api.server.dao.ILabeledGoodPDADao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodPDAService;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodPDAEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodPDADto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * PDA货签信息服务
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-12-11 下午3:03:23,content </p>
 * @author foss-sunrui
 * @date 2012-12-11 下午3:03:23
 * @since
 * @version
 */
public class LabeledGoodPDAService implements ILabeledGoodPDAService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LabeledGoodPDAService.class);
	
	//PAD暂存运单
	private IWaybillPendingDao waybillPendingDao;
	//PDA标签
	private ILabeledGoodPDADao labeledGoodPDADao;
	/**
	 * 设置  PAD暂存运单dao
	 * @param waybillPendingDao
	 */
	public void setWaybillPendingDao(IWaybillPendingDao waybillPendingDao) {
		this.waybillPendingDao = waybillPendingDao;
	}
	/**
	 *  设置  PDA标签
	 * @param labeledGoodPDADao
	 */
	public void setLabeledGoodPDADao(ILabeledGoodPDADao labeledGoodPDADao) {
		this.labeledGoodPDADao = labeledGoodPDADao;
	}

	/**
	 * 
	 * <p>插入标签信息</p> 
	 * @author foss-sunrui
	 * @date 2012-12-19 上午10:38:47
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodPDAService#insertSelective(com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodPDAEntity)
	 */
	public int insertSelective(LabeledGoodPDAEntity record) {
		return labeledGoodPDADao.insertSelective(record);
	}

	/**
	 * 
	 * <p>通过主键查询</p> 
	 * @author foss-sunrui
	 * @date 2012-12-19 上午10:38:50
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodPDAService#queryByPrimaryKey(java.lang.String)
	 */
	public LabeledGoodPDAEntity queryByPrimaryKey(String id) {
		return labeledGoodPDADao.queryByPrimaryKey(id);
	}

	/**
	 * 
	 * <p>通过运单号查询</p> 
	 * @author foss-sunrui
	 * @date 2012-12-19 上午10:38:53
	 * @param waybillNo
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodPDAService#queryByWaybillNo(java.lang.String)
	 */
	public List<LabeledGoodPDAEntity> queryByWaybillNo(String waybillNo) {
		return labeledGoodPDADao.queryByWaybillNo(waybillNo);
	}

	/**
	 * 
	 * <p>通过主键更新</p> 
	 * @author foss-sunrui
	 * @date 2012-12-19 上午10:38:57
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodPDAService#updateByPrimaryKeySelective(com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodPDAEntity)
	 */
	public int updateByPrimaryKeySelective(LabeledGoodPDAEntity record) {
		return labeledGoodPDADao.updateByPrimaryKeySelective(record);
	}

	/**
	 * 
	 * <p>PDA上传运单信息</p> 
	 * @author foss-sunrui
	 * @date 2012-12-19 上午10:37:07
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodPDAService#createByWaybillNo(com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodPDADto)
	 */
	public int createByWaybillNo(LabeledGoodPDADto record) {
		int retValue = 0;//更新条目数
		if(record!=null){
			WaybillPendingEntity waybill = waybillPendingDao.queryByWaybillNumber(record.getWaybillNo());//根据运单号查询
			if(waybill!=null){
				LabeledGoodPDAEntity labeledGood = new LabeledGoodPDAEntity();//创建 PDA提交货签信息对象
				labeledGood.setWaybillPDAId(waybill.getId());//运单ID
				labeledGood.setWaybillNo(record.getWaybillNo());//运单号
				labeledGood.setSerialNo(record.getSerialNo());//流水号
				labeledGood.setBillTime(waybill.getBillTime());//开单时间
				labeledGood.setActive(FossConstants.ACTIVE);//激活
				labeledGood.setCreateTime(record.getPrintTime());//创建时间
				labeledGood.setModifyTime(record.getPrintTime());//修改时间
				labeledGood.setCreateUserCode(record.getPrintPerson());//创建人工号
				retValue = this.insertSelective(labeledGood);//插入数据库
				LOGGER.info("插入货签信息" + ReflectionToStringBuilder.toString(labeledGood));
			}
		}
		return retValue;//更新条目数
	}
	
	/**
	 * 
	 * <p>根据运单号更新标签信息</p> 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-18 下午5:08:44
	 * @param waybillNo
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodPDAService#updatePdaLabelByWaybillNo(java.lang.String)
	 */
	public int updatePdaLabelByWaybillNo(String waybillNo){
		return labeledGoodPDADao.updateActiveByWaybillNo(waybillNo);
	}

}