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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/vo/SealVo.java
 *  
 *  FILE NAME          :SealVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.RelationInfoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealDestDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealOrigDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.BillInfoDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.SealDto;

/**
 * 封签vo
 * @author ibm-zhangyixin
 * @date 2012-12-25 下午6:50:07
 */
public class SealVo extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 2760125924825331929L;
	
	/**
	 * 
	 */
	private String id;
	/**司机相关信息**/
	private RelationInfoEntity relationInfo;
	/**备注**/
	private String memo;
	/**封签dto**/
	private SealDto sealDto;
	/**封签dto集合**/
	private List<SealDto> sealList = new ArrayList<SealDto>();
	/**封签entity**/
	private SealEntity seal = new SealEntity();
	/**是否有差异, 封签校验时用到. true为有差异**/
	private Boolean isdiff;	//,是否有差异, 封签校验时用到. true为有差异
	/**出发封签明细集合**/
	private List<SealOrigDetailEntity> sealOrigDetails = new ArrayList<SealOrigDetailEntity>();
	/**出发封签明细vo集合**/
	private List<SealOrigDetailVo> sealOrigDetailVos = new ArrayList<SealOrigDetailVo>();
	/**到达封签明细vo集合**/
	private List<SealDestDetailVo> sealDestDetailVos = new ArrayList<SealDestDetailVo>();
	/**到达封签明细集合**/
	private List<SealDestDetailEntity> sealDestDetails = new ArrayList<SealDestDetailEntity>();
	/**出发封签明细后门封签**/
	private List<SealOrigDetailEntity> backSealOrigDetails = new ArrayList<SealOrigDetailEntity>();
	/**出发封签明细侧门封签**/
	private List<SealOrigDetailEntity> sideSealOrigDetails = new ArrayList<SealOrigDetailEntity>();
	/**单据信息**/
	private List<BillInfoDto> billInfos = new ArrayList<BillInfoDto>();
	/**确认校验部门是否为最终到达部门**/
	private Integer affirmResult;
	/**车辆任务明细**/
	private TruckTaskDetailEntity truckTaskDetail;
	
	/**
	 * 获取 封签dto集合*.
	 *
	 * @return the 封签dto集合*
	 */
	public List<SealDto> getSealList() {
		return sealList;
	}

	/**
	 * 设置 封签dto集合*.
	 *
	 * @param sealList the new 封签dto集合*
	 */
	public void setSealList(List<SealDto> sealList) {
		this.sealList = sealList;
	}

	/**
	 * 获取 封签entity*.
	 *
	 * @return the 封签entity*
	 */
	public SealEntity getSeal() {
		return seal;
	}

	/**
	 * 设置 封签entity*.
	 *
	 * @param seal the new 封签entity*
	 */
	public void setSeal(SealEntity seal) {
		this.seal = seal;
	}

	/**
	 * 获取 出发封签明细集合*.
	 *
	 * @return the 出发封签明细集合*
	 */
	public List<SealOrigDetailEntity> getSealOrigDetails() {
		return sealOrigDetails;
	}

	/**
	 * 设置 出发封签明细集合*.
	 *
	 * @param sealOrigDetails the new 出发封签明细集合*
	 */
	public void setSealOrigDetails(List<SealOrigDetailEntity> sealOrigDetails) {
		this.sealOrigDetails = sealOrigDetails;
	}

	/**
	 * 获取 到达封签明细集合*.
	 *
	 * @return the 到达封签明细集合*
	 */
	public List<SealDestDetailEntity> getSealDestDetails() {
		return sealDestDetails;
	}

	/**
	 * 设置 到达封签明细集合*.
	 *
	 * @param sealDestDetails the new 到达封签明细集合*
	 */
	public void setSealDestDetails(List<SealDestDetailEntity> sealDestDetails) {
		this.sealDestDetails = sealDestDetails;
	}

	/**
	 * 获取 是否有差异, 封签校验时用到.
	 *
	 * @return the 是否有差异, 封签校验时用到
	 */
	public Boolean getIsdiff() {
		return isdiff;
	}

	/**
	 * 设置 是否有差异, 封签校验时用到.
	 *
	 * @param isdiff the new 是否有差异, 封签校验时用到
	 */
	public void setIsdiff(Boolean isdiff) {
		this.isdiff = isdiff;
	}

	/**
	 * 获取 备注*.
	 *
	 * @return the 备注*
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置 备注*.
	 *
	 * @param memo the new 备注*
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/** 
	 * @author ibm-zhangyixin
	 * @date 2013-3-14 下午2:49:26
	 * @see com.deppon.foss.framework.entity.BaseEntity#getId()
	 */
	public String getId() {
		return id;
	}

	/** 
	 * @author ibm-zhangyixin
	 * @date 2013-3-14 下午2:49:26
	 * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取 出发封签明细vo集合*.
	 *
	 * @return the 出发封签明细vo集合*
	 */
	public List<SealOrigDetailVo> getSealOrigDetailVos() {
		return sealOrigDetailVos;
	}

	/**
	 * 设置 出发封签明细vo集合*.
	 *
	 * @param sealOrigDetailVos the new 出发封签明细vo集合*
	 */
	public void setSealOrigDetailVos(List<SealOrigDetailVo> sealOrigDetailVos) {
		this.sealOrigDetailVos = sealOrigDetailVos;
	}

	/**
	 * 获取 到达封签明细vo集合*.
	 *
	 * @return the 到达封签明细vo集合*
	 */
	public List<SealDestDetailVo> getSealDestDetailVos() {
		return sealDestDetailVos;
	}

	/**
	 * 设置 到达封签明细vo集合*.
	 *
	 * @param sealDestDetailVos the new 到达封签明细vo集合*
	 */
	public void setSealDestDetailVos(List<SealDestDetailVo> sealDestDetailVos) {
		this.sealDestDetailVos = sealDestDetailVos;
	}

	/**
	 * 获取 出发封签明细后门封签*.
	 *
	 * @return the 出发封签明细后门封签*
	 */
	public List<SealOrigDetailEntity> getBackSealOrigDetails() {
		return backSealOrigDetails;
	}

	/**
	 * 设置 出发封签明细后门封签*.
	 *
	 * @param backSealOrigDetails the new 出发封签明细后门封签*
	 */
	public void setBackSealOrigDetails(
			List<SealOrigDetailEntity> backSealOrigDetails) {
		this.backSealOrigDetails = backSealOrigDetails;
	}

	/**
	 * 获取 出发封签明细侧门封签*.
	 *
	 * @return the 出发封签明细侧门封签*
	 */
	public List<SealOrigDetailEntity> getSideSealOrigDetails() {
		return sideSealOrigDetails;
	}

	/**
	 * 设置 出发封签明细侧门封签*.
	 *
	 * @param sideSealOrigDetails the new 出发封签明细侧门封签*
	 */
	public void setSideSealOrigDetails(
			List<SealOrigDetailEntity> sideSealOrigDetails) {
		this.sideSealOrigDetails = sideSealOrigDetails;
	}

	/**
	 * 获取 封签dto*.
	 *
	 * @return the 封签dto*
	 */
	public SealDto getSealDto() {
		return sealDto;
	}

	/**
	 * 设置 封签dto*.
	 *
	 * @param sealDto the new 封签dto*
	 */
	public void setSealDto(SealDto sealDto) {
		this.sealDto = sealDto;
	}

	/**
	 * 获取 司机相关信息*.
	 *
	 * @return the 司机相关信息*
	 */
	public RelationInfoEntity getRelationInfo() {
		return relationInfo;
	}

	/**
	 * 设置 司机相关信息*.
	 *
	 * @param relationInfo the new 司机相关信息*
	 */
	public void setRelationInfo(RelationInfoEntity relationInfo) {
		this.relationInfo = relationInfo;
	}

	/**
	 * 获取 单据信息*.
	 *
	 * @return the 单据信息*
	 */
	public List<BillInfoDto> getBillInfos() {
		return billInfos;
	}

	/**
	 * 设置 单据信息*.
	 *
	 * @param billInfos the new 单据信息*
	 */
	public void setBillInfos(List<BillInfoDto> billInfos) {
		this.billInfos = billInfos;
	}

	/**   
	 * affirmResult   
	 *   
	 * @return  the affirmResult   
	 */
	
	public Integer getAffirmResult() {
		return affirmResult;
	}

	/**   
	 * @param affirmResult the affirmResult to set
	 * Date:2013-4-18下午2:58:14
	 */
	public void setAffirmResult(Integer affirmResult) {
		this.affirmResult = affirmResult;
	}

	/**   
	 * truckTaskDetail   
	 *   
	 * @return  the truckTaskDetail   
	 */
	
	public TruckTaskDetailEntity getTruckTaskDetail() {
		return truckTaskDetail;
	}

	/**   
	 * @param truckTaskDetail the truckTaskDetail to set
	 * Date:2013-4-18下午3:18:53
	 */
	public void setTruckTaskDetail(TruckTaskDetailEntity truckTaskDetail) {
		this.truckTaskDetail = truckTaskDetail;
	}
}