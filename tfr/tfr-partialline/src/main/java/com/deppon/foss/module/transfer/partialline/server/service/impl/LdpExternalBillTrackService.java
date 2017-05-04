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
 *  
 *  PROJECT NAME  : tfr-partialline
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 落地配外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/server/service/impl/LdpExternalBillTrackService.java
 * 
 *  FILE NAME     :LdpExternalBillTrackService.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.partialline.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.trackings.api.server.service.IPushTrackForCaiNiaoService;
import com.deppon.foss.module.trackings.api.server.service.IWaybillTrackingsService;
import com.deppon.foss.module.trackings.api.shared.define.WaybillTrackingsConstants;
import com.deppon.foss.module.trackings.api.shared.domain.SynTrackingEntity;
import com.deppon.foss.module.trackings.api.shared.dto.WaybillTrackingsDto;
import com.deppon.foss.module.transfer.partialline.api.server.dao.ILdpExternalBillTrackDao;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillTrackService;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillTrackDto;
import com.deppon.foss.module.transfer.partialline.api.shared.exception.ExternalBillException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 落地配轨迹记录Service
 * 
 * @author ibm-liuzhaowei
 * @date 2013-07-29 上午9:18:36
 */
public class LdpExternalBillTrackService implements ILdpExternalBillTrackService {

	/**
	 * 外发单轨迹DAO接口
	 */
	private ILdpExternalBillTrackDao ldpExternalBillTrackDao;
	
	private IWaybillTrackingsService waybillTrackingsService;
	
	/**
	 * 获取运单信息
	 */
	private IWaybillDao waybillDao;
	
	/**轨迹service***/
	private IPushTrackForCaiNiaoService pushTrackForCaiNiaoService;
	
	public void setPushTrackForCaiNiaoService(
			IPushTrackForCaiNiaoService pushTrackForCaiNiaoService) {
		this.pushTrackForCaiNiaoService = pushTrackForCaiNiaoService;
	}
	
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	
	/**
	 * 保存落地配外发单轨迹记录
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-29 上午11:47:04
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public int addLdpExternalBillTrack(LdpExternalBillTrackDto record) {
	
		//插入轨迹表
		if(StringUtils.equals("1", record.getOperationtype())
				|| StringUtils.equals("2", record.getOperationtype())
				|| StringUtils.equals("3", record.getOperationtype())) {
			String type= WaybillTrackingsConstants.OPERATE_TYPE_LDP_TRACK_ARRIVE;
			if(StringUtils.equals("2", record.getOperationtype())) {
				type = WaybillTrackingsConstants.OPERATE_TYPE_LDP_TRACK_LEAVE;
			} else if(StringUtils.equals("3", record.getOperationtype())) {
				type = WaybillTrackingsConstants.OPERATE_TYPE_LDP_TRACK_DELIVER;
			}
			try{
				WaybillTrackingsDto trackDto = new WaybillTrackingsDto();
				trackDto.setWaybillNo(record.getWaybillNo());
				trackDto.setOperateType(type);
				trackDto.setOperateDeptCode(record.getAgentOrgCode()); 
				trackDto.setOperateDeptName(record.getAgentOrgName()); 
				trackDto.setOperateTime(record.getOperationTime());
				trackDto.setOperatorName(record.getOperationUserName());
				trackDto.setOperateDesc(record.getOperationcity());
				
				waybillTrackingsService.addOneWaybillTrack(trackDto);
				
				//插入待同步轨迹表中
				SynTrackingEntity synTrackEntity = new SynTrackingEntity();
				synTrackEntity.setId(UUIDUtils.getUUID());
				synTrackEntity.setWayBillNo(record.getWaybillNo());
				synTrackEntity.setTrackInfo(record.getOperationDescribe());
				synTrackEntity.setEventType(type);
				synTrackEntity.setOperateCity(record.getOperationcity());
				synTrackEntity.setOrgCode(record.getAgentOrgCode());
				synTrackEntity.setOrgName(record.getAgentOrgName());
				synTrackEntity.setOperateTime(record.getOperationTime());
				synTrackEntity.setOperatorName(record.getOperationUserName());
				
				//2016-8-11 16:08:23 311396 加上目的部门名称
				WaybillEntity waybillEntity = waybillDao.queryWaybillByNo(record.getWaybillNo());//获取运单信息
				if (waybillEntity != null) {
					synTrackEntity.setDestinationDeptName(waybillEntity.getCustomerPickupOrgName());
				}
				pushTrackForCaiNiaoService.addSynTrack(synTrackEntity);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		// 插入落地配外发单轨迹记录
		return ldpExternalBillTrackDao.addLdpExternalBillTrack(record);
	}

	
	/**
	 * 落地配轨迹DAO接口
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-29 上午10:45:01
	 */
	public void setLdpExternalBillTrackDao(ILdpExternalBillTrackDao ldpExternalBillTrackDao) {
		this.ldpExternalBillTrackDao = ldpExternalBillTrackDao;
	}


	@Override
	public List<LdpExternalBillTrackDto> queryLdpExternalBillTrackList(LdpExternalBillTrackDto ldpExternalBillTrackDto) {
		if(ldpExternalBillTrackDto == null || StringUtils.isBlank(ldpExternalBillTrackDto.getWaybillNo())){
			throw new ExternalBillException("查询参数对象不能为空，运单号为必填项", "");
		}
		ldpExternalBillTrackDto.setActive(FossConstants.ACTIVE);
		
		return ldpExternalBillTrackDao.queryLdpExternalBillTrackList(ldpExternalBillTrackDto);
	}

	public void setWaybillTrackingsService(
			IWaybillTrackingsService waybillTrackingsService) {
		this.waybillTrackingsService = waybillTrackingsService;
	}

	/**
	 * @author nly
	 * @date 2015年4月13日 下午2:06:45
	 * @function 查询轨迹信息
	 * @param ldpDto
	 * @return
	 */
	@Override
	public List<LdpExternalBillTrackDto> queryLdpBillTrackList(LdpExternalBillTrackDto ldpDto) {
		return ldpExternalBillTrackDao.queryLdpBillTrackList(ldpDto);
	}

	/**
	 * @author nly
	 * @date 2015年4月15日 下午2:01:41
	 * @function 删除落地配轨迹
	 * @param waybillNo
	 */
	@Override
	public void deleteLDPTrack(String waybillNo) {
		ldpExternalBillTrackDao.deleteLDPTrack(waybillNo);
	}
	
}