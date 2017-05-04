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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/service/IAbandonGoodsApplicationService.java
 * 
 * FILE NAME        	: IAbandonGoodsApplicationService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.upload.AttachementEntity;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AbandonGoodsApplicationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AbandonGoodsImportEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonGoodsResultDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonedGoodsDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonedGoodsSearchDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ResultDto;

/**
 * @ClassName: IAbandonGoodsApplicationService
 * @Description: 弃货处理service
 * @author ibm-lizhiguo
 * @date 2012-10-25 上午10:46:37
 * 
 */
public interface IAbandonGoodsApplicationService extends IService {
	
	/**
	 * 保存导入内部带货
	 * @date 2012-11-9 下午4:48:47
	 */
	void insertAbandonGoodsApplication(
			AbandonGoodsApplicationEntity entity,List<AttachementEntity> attachementFlies);
	/**
	 * 
	 * 查询得到弃货纪录列表
	 * @author ibm-lizhiguo
	 * @date 2012-10-26 下午2:35:24
	 */
	List<AbandonGoodsResultDto> searchAbandonGoodsList(AbandonedGoodsSearchDto dto);
	/**
	 * 
	 * 得到弃货记录总数
	 * @author ibm-lizhiguo
	 * @date 2012-10-26 下午2:35:28
	 */
	Long getTotalCount(AbandonedGoodsSearchDto dto);
	
	/**
	 * 
	 * 根据弃货的ID，获得弃货的详细信息
	 * @author ibm-lizhiguo
	 * @date 2012-10-30 上午10:34:09
	 */
	AbandonedGoodsDetailDto getAbandonGoodsDetailById(String id);
	/**
	 * 
	 * 更具运单号和部门编码，获得货物在该部门的入库时间
	 * @author ibm-lizhiguo
	 * @date 2012-11-12 上午10:19:54
	 */
	Date getInStockTimeByWaybillNoAndOrgCode(String waybillNo, String orgCode);
	
	/**
	 * 保存导入内部带货
	 * @date 2012-11-9 下午4:48:47
	 */
	void createAbandonGoodsImport(String waybillNos);
	
	/**
	 *  1 系统每天晚上12点自动转换，将派送异常运单对应的运单号、始发部门、发货人、
	 * 发货人手机、体积、入库时间、仓储时长转换成预弃货信息，
	 * 同时操作人，操作人为系统管理员账户名。
	 * 
	 * 2 入库时间离当前时间如果超过“3”个月，则系统后台自动转成预弃货信息。
	 * 此处具体弃货时长可以进行人工配置。
	 */
	void preprocess();
	
	/**
	 * 费用弃货工作流
	 * @date 2012-11-28 下午3:32:10
	 */
	ResultDto startOaDiscardWorkflow(String waybillNo);
	/**
	 * 
	 * <p>更新导入数据状态和时间<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-21
	 * @param record
	 * @return
	 * int
	 */
	int updateByPrimaryKeySelective(AbandonGoodsImportEntity record);
	/**
	 * 
	 * 根据运单号，查询弃货信息
	 * @param waybillNo
	 * @return
	 * @author ibm-wangfei
	 * @date Apr 15, 2013 4:31:37 PM
	 */
	AbandonedGoodsDetailDto getAbandoneGoodsDto(String waybillNo);
	
	/**
	 * 
	 * 保存到弃货表<br />
	 * @param rtDto
	 * @author ibm-wangfei
	 * @date Apr 23, 2013 1:36:22 PM
	 */
	void saveAbandonGoodsApplicationEntity(AbandonedGoodsDetailDto rtDto);
	
	/**
	 * 
	 * 保存弃货操作历史记录
	 * 
	 * @author ibm-wangfei
	 * @date Apr 24, 2013 5:56:55 PM
	 */
	void insertAgActionHistoryEntity(AbandonGoodsApplicationEntity abandonentity);
	
	/**
	 *  保存弃货操作历史记录-- JOB运行时保存
	 * @param abandonentity
	 * @param currentInfo
	 * @author ibm-wangfei
	 * @date May 2, 2013 2:38:20 PM
	 */
	void insertAgActionHistoryEntity(AbandonGoodsApplicationEntity abandonentity, CurrentInfo currentInfo);

	AbandonGoodsResultDto queryAbandonedGoodsForWaybillNo(String waybillNo);
	
	/**
	 * 根据运单号和流水号查询无标签弃货记录
	 * @param waybillNo
	 * @param serialNumber
	 * @return
	 */
	AbandonGoodsResultDto queryNoTagAbandonedGoodsByWaybillNoAndSerialNo(String waybillNo,
			String serialNumber);
	
	/**
	 * 编辑预弃货信息
	 * @author hrh
	 * @date Aug 17,2013 10:58:00 AM
	 */
	void editAbandonGoodsApplication(AbandonGoodsApplicationEntity entity);

	/**
	 * 为中转提供的无标签转弃货接口
	 * @author 153161-foss-lufeifei
	 * @param entity
	 */
	void insertAgEntityForNoTagTransfer(AbandonGoodsApplicationEntity entity);
	
	/**
	 * 在可编辑状态下修改转弃货申请数据，可上传附件功能
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-5-14 13:45:08
	 * @param abandonGoodsApplicationEntity
	 * @param attachementFiles
	 */
	void editAbandonGoodAttachFiles(AbandonGoodsApplicationEntity abandonGoodsApplicationEntity,
			List<AttachementEntity> attachementFiles);
	
}