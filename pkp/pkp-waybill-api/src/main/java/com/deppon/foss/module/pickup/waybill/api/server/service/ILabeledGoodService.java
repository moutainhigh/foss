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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/ILabeledGoodService.java
 * 
 * FILE NAME        	: ILabeledGoodService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillSystemDto;

/**
 * 
 * 货签服务接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-30 下午3:33:40</p>
 * @author foss-sunrui
 * @date 2012-10-30 下午3:33:40
 * @since
 * @version
 */
public interface ILabeledGoodService extends IService {

    /**
     * 
     * <p>插入货签信息</p> 
     * @author foss-sunrui
     * @date 2012-10-30 下午3:35:48
     * @param waybillNo 运单号
     * @param labeledGoodList 标签实体列表
     * @return 
     * @see
     */
	ResultDto insertSerialNo(String oldWaybillNo,String waybillNo, List<LabeledGoodDto> labeledGoodList);
    
    /**
     * 
     * <p>插入货签信息</p> 
     * @author foss-sunrui
     * @date 2012-10-30 下午3:35:48
     * @param waybillNo 运单号
     * @param labeledGoodList 标签实体列表
     * @return 
     * @see
     */
	ResultDto insertSerialNo(String waybillNo, List<LabeledGoodDto> labeledGoodList);
    
    /**
     * 
     * <p>删除货签信息</p> 
     * @author foss-sunrui
     * @date 2012-10-30 下午3:35:48
     * @param waybillNo 运单号
     * @param labeledGoodList 标签实体列表
     * @return 删除记录数目s
     * @see
     */
    int deleteSerialNo(String waybillNo, List<LabeledGoodDto> labeledGoodList);
    
    /**
     * 
     * <p>货签信息更新</p> 
     * @author foss-sunrui
     * @date 2012-10-30 下午3:42:11
     * @param waybillNo
     * @param serialNo
     * @param active
     * @return
     * @see
     */
    ResultDto updateSerialNo(String waybillNo, String serialNo, String active);
    
    /**
     * 
     * <p>
     * 通过运单号查询所有流水号
     * </p>
     * 
     * @author foss-jiangfei
     * @date 2012-10-30 下午4:55:34
     * @param waybillNo
     * @return
     * @see
     */
    List<LabeledGoodEntity> queryAllSerialByWaybillNo(String waybillNo) ;
    
    /**
     * 
     * <p>根据标签号查询标签</p> 
     * @author foss-gengzhe
     * @date 2012-12-7 下午5:02:11
     * @param id
     * @return
     * @see
     */
    LabeledGoodEntity queryByPrimaryKey(String id);

	/**
	 * 根据运单号查询PDA货签是否已生成，若PDA未生成则调用insertSerialNo方法，若生成部分则在货签表里生成余下未生成的货签
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-19 上午10:13:24
	 */
	void addLabeledGoods(WaybillDto waybill,WaybillSystemDto systemDto);

    /**
     * 
     * 更新流水号中的运单号
     * @author 102246-foss-shaohongliang
     * @date 2013-3-18 下午8:11:29
     */
	void modifyWaybillNo(String oldWaybillNo, String newWaybillNo);
	
	/**
	 * 根据运单号和流水号获取状态是否有效(Y:有效  N：无效  不存在：NOTEXIST)
	 * @author WangQianJin
	 * @date 2013-6-20 下午4:02:21
	 */
	String getStautsByWaybillNoAndSerialNo(String waybillNo, String serialNo);

	List<LabeledGoodEntity> queryLabelGoodStatusisNByWaybillNo(String waybillNo);

	List<LabeledGoodEntity> queryLabeledGoodByWaybillNoWithSerial(
			String waybillNo, List<String> serialNoList);

	void deleteLabByWaybillNos(List<String> waybillNos);
	void deleteLabByWaybillNo(String waybillNo);
}