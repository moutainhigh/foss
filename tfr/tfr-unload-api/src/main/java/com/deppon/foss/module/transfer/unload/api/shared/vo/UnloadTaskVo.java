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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/vo/UnloadTaskVo.java
 *  
 *  FILE NAME          :UnloadTaskVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.api.shared.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillDto;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ConfirmUnloadTaskBillsDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ConfirmUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PlanUnloadBillDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskAddnewDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadTaskModifyDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadWaybillDetailDto;

/**
 * 卸车任务Vo
 * @author ibm-zhangyixin
 * @date 2012-11-29 上午10:47:07
 */
public class UnloadTaskVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2296654754477505167L;

	/**
	 * 
	 */
	private UnloadTaskDto unloadTaskDto = new UnloadTaskDto();
	
	/**
	 * 
	 */
	private List<UnloadTaskDto> unloadTaskDtoList = new ArrayList<UnloadTaskDto>();
	
	/**
	 * 
	 */
	private List<UnloadWaybillDetailDto> unloadWaybillDetailDtoList = new ArrayList<UnloadWaybillDetailDto>();
	/**卸车流水信息**/
	private List<UnloadSerialNoDetailEntity> unloadSerialNoDetailList = new ArrayList<UnloadSerialNoDetailEntity>();
	/**卸车运单ID**/
	
   //是否商务专递272681
	private Boolean isAir;
	
	private String unloadWaybillDetailId;
	//月台号
	/**
	 * 
	 */
	private String platformCode;
	//车牌号
	/**
	 * 
	 */
	private String vehicleNo;
	//航班号272681
	private String flightNo;
	//返回的待卸单据信息list
	/**
	 * 
	 */
	private List<PlanUnloadBillDto> billList;
	//单据编号
	/**
	 * 
	 */
	private String billNo;
	//返回的待卸单据信息
	/**
	 * 
	 */
	private PlanUnloadBillDto billInfo;
	//新增时传入的dto
	/**
	 * 
	 */
	private UnloadTaskAddnewDto addnewDto;
	//卸车任务编号
	/**
	 * 
	 */
	private String unloadTaskNo;
	//卸车任务ID
	/**
	 * 
	 */
	private String unloadTaskId;
	//卸车任务种类
	/**
	 * 
	 */
	private String unloadType;
	//卸车方式
	/**
	 * 
	 */
	private String unloadWay;
	//卸车任务基本信息
	/**
	 * 
	 */
	private UnloadTaskEntity baseEntity;
	//卸车任务单据列表
	/**
	 * 
	 */
	private List<UnloadBillDetailEntity> billDetailList;
	//卸车任务卸车员list
	/**
	 * 
	 */
	private List<LoaderParticipationEntity> loaderDetailList;
	//修改卸车任务时传入的dto
	/**
	 * 
	 */
	private UnloadTaskModifyDto unloadTaskModifyDto;
	//配载车次号
	/**
	 * 
	 */
	private String vehicleAssembleNo;
	//交接单编号
	/**
	 * 
	 */
	private String handOverBillNo;
	//运单号
	/**
	 * 
	 */
	private String waybillNo;
	//流水号
	/**
	 * 
	 */
	private String serialNo;
	
	/**
	 * 区分零担快递的标识,express表示快递,Lingdan表示零担
	 */
	private String expressOrLingdan;
	//交接单list
	/**
	 * 
	 */
	private List<QueryHandOverBillDto> handOverBillList;
	//运单list
	/**
	 * 
	 */
	private List<HandOverBillDetailEntity> waybillList;
	//流水号list
	/**
	 * 
	 */
	private List<HandOverBillSerialNoDetailEntity> serialNoList;
	//交接单号List
	/**
	 * 
	 */
	private List<String> handOverBillNoList;
	//确认卸车任务时提交的dto
	/**
	 * 
	 */
	private ConfirmUnloadTaskDto confirmUnloadTaskDto;
	//添加多货时校验运单号、流水号
	/**
	 * 
	 */
	private int isAddedNosRight;
	//卸车完成时间
	/**
	 * 
	 */
	private Date unloadEndDate;
	
	/**
	 * 件号
	 */
	private String cargoNo;
	
	/**
	 * 件类型
	 */
	private String cargoType;
	
	/**
	 * 
	 *
	 * @return 
	 */
	public Date getUnloadEndDate() {
		return unloadEndDate;
	}
	
	private String outfieldCode;
	
	public String getOutfieldCode() {
		return outfieldCode;
	}

	public void setOutfieldCode(String outfieldCode) {
		this.outfieldCode = outfieldCode;
	}

	/**
	 * 
	 *
	 * @param unloadEndDate 
	 */
	public void setUnloadEndDate(Date unloadEndDate) {
		this.unloadEndDate = unloadEndDate;
	}
	//长途卸车界面，根据运单号获取运单所在的<配载单、交接单>list
	/**
	 * 
	 */
	private List<ConfirmUnloadTaskBillsDto> billNosList;
	/**理货员**/
	private List<LoaderParticipationEntity> loaderParticipationList = new ArrayList<LoaderParticipationEntity>();
	/**
	 * 当前登录部门所属的上级大部门code，包括营业部、派送部、外场、总调
	 */
	private String superOrgCode;
	
	/**
	 * 大部门是否为外场
	 */
	private String beTransferCenter;

	/**
	 * Gets the 当前登录部门所属的上级大部门code，包括营业部、派送部、外场、总调.
	 *
	 * @return the 当前登录部门所属的上级大部门code，包括营业部、派送部、外场、总调
	 */
	public String getSuperOrgCode() {
		return superOrgCode;
	}
	
	/**
	 * Sets the 当前登录部门所属的上级大部门code，包括营业部、派送部、外场、总调.
	 *
	 * @param superOrgCode the new 当前登录部门所属的上级大部门code，包括营业部、派送部、外场、总调
	 */
	public void setSuperOrgCode(String superOrgCode) {
		this.superOrgCode = superOrgCode;
	}
	
	/**
	 * Gets the 大部门是否为外场.
	 *
	 * @return the 大部门是否为外场
	 */
	public String getBeTransferCenter() {
		return beTransferCenter;
	}
	
	/**
	 * Sets the 大部门是否为外场.
	 *
	 * @param beTransferCenter the new 大部门是否为外场
	 */
	public void setBeTransferCenter(String beTransferCenter) {
		this.beTransferCenter = beTransferCenter;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public List<ConfirmUnloadTaskBillsDto> getBillNosList() {
		return billNosList;
	}
	
	/**
	 * 
	 *
	 * @param billNosList 
	 */
	public void setBillNosList(List<ConfirmUnloadTaskBillsDto> billNosList) {
		this.billNosList = billNosList;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public String getUnloadType() {
		return unloadType;
	}
	
	/**
	 * 
	 *
	 * @param unloadType 
	 */
	public void setUnloadType(String unloadType) {
		this.unloadType = unloadType;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public String getSerialNo() {
		return serialNo;
	}
	
	/**
	 * 
	 *
	 * @param serialNo 
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public int getIsAddedNosRight() {
		return isAddedNosRight;
	}
	
	/**
	 * 
	 *
	 * @param isAddedNosRight 
	 */
	public void setIsAddedNosRight(int isAddedNosRight) {
		this.isAddedNosRight = isAddedNosRight;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public ConfirmUnloadTaskDto getConfirmUnloadTaskDto() {
		return confirmUnloadTaskDto;
	}
	
	/**
	 * 
	 *
	 * @param confirmUnloadTaskDto 
	 */
	public void setConfirmUnloadTaskDto(ConfirmUnloadTaskDto confirmUnloadTaskDto) {
		this.confirmUnloadTaskDto = confirmUnloadTaskDto;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public List<String> getHandOverBillNoList() {
		return handOverBillNoList;
	}
	
	/**
	 * 
	 *
	 * @param handOverBillNoList 
	 */
	public void setHandOverBillNoList(List<String> handOverBillNoList) {
		this.handOverBillNoList = handOverBillNoList;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	
	/**
	 * 
	 *
	 * @param waybillNo 
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public String getVehicleAssembleNo() {
		return vehicleAssembleNo;
	}
	
	/**
	 * 
	 *
	 * @param vehicleAssembleNo 
	 */
	public void setVehicleAssembleNo(String vehicleAssembleNo) {
		this.vehicleAssembleNo = vehicleAssembleNo;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public String getHandOverBillNo() {
		return handOverBillNo;
	}
	
	/**
	 * 
	 *
	 * @param handOverBillNo 
	 */
	public void setHandOverBillNo(String handOverBillNo) {
		this.handOverBillNo = handOverBillNo;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public List<QueryHandOverBillDto> getHandOverBillList() {
		return handOverBillList;
	}
	
	/**
	 * 
	 *
	 * @param handOverBillList 
	 */
	public void setHandOverBillList(
			List<QueryHandOverBillDto> handOverBillList) {
		this.handOverBillList = handOverBillList;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public List<HandOverBillDetailEntity> getWaybillList() {
		return waybillList;
	}
	
	/**
	 * 
	 *
	 * @param waybillList 
	 */
	public void setWaybillList(List<HandOverBillDetailEntity> waybillList) {
		this.waybillList = waybillList;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public List<HandOverBillSerialNoDetailEntity> getSerialNoList() {
		return serialNoList;
	}
	
	/**
	 * 
	 *
	 * @param serialNoList 
	 */
	public void setSerialNoList(List<HandOverBillSerialNoDetailEntity> serialNoList) {
		this.serialNoList = serialNoList;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public UnloadTaskModifyDto getUnloadTaskModifyDto() {
		return unloadTaskModifyDto;
	}
	
	/**
	 * 
	 *
	 * @param unloadTaskModifyDto 
	 */
	public void setUnloadTaskModifyDto(UnloadTaskModifyDto unloadTaskModifyDto) {
		this.unloadTaskModifyDto = unloadTaskModifyDto;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public String getUnloadTaskId() {
		return unloadTaskId;
	}
	
	/**
	 * 
	 *
	 * @param unloadTaskId 
	 */
	public void setUnloadTaskId(String unloadTaskId) {
		this.unloadTaskId = unloadTaskId;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public UnloadTaskEntity getBaseEntity() {
		return baseEntity;
	}
	
	/**
	 * 
	 *
	 * @param baseEntity 
	 */
	public void setBaseEntity(UnloadTaskEntity baseEntity) {
		this.baseEntity = baseEntity;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public List<UnloadBillDetailEntity> getBillDetailList() {
		return billDetailList;
	}
	
	/**
	 * 
	 *
	 * @param billDetailList 
	 */
	public void setBillDetailList(List<UnloadBillDetailEntity> billDetailList) {
		this.billDetailList = billDetailList;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public List<LoaderParticipationEntity> getLoaderDetailList() {
		return loaderDetailList;
	}
	
	/**
	 * 
	 *
	 * @param loaderDetailList 
	 */
	public void setLoaderDetailList(List<LoaderParticipationEntity> loaderDetailList) {
		this.loaderDetailList = loaderDetailList;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}
	
	/**
	 * 
	 *
	 * @param unloadTaskNo 
	 */
	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public UnloadTaskAddnewDto getAddnewDto() {
		return addnewDto;
	}
	
	/**
	 * 
	 *
	 * @param addnewDto 
	 */
	public void setAddnewDto(UnloadTaskAddnewDto addnewDto) {
		this.addnewDto = addnewDto;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public String getBillNo() {
		return billNo;
	}
	
	/**
	 * 
	 *
	 * @param billNo 
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public PlanUnloadBillDto getBillInfo() {
		return billInfo;
	}
	
	/**
	 * 
	 *
	 * @param billInfo 
	 */
	public void setBillInfo(PlanUnloadBillDto billInfo) {
		this.billInfo = billInfo;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * 
	 *
	 * @param vehicleNo 
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public List<PlanUnloadBillDto> getBillList() {
		return billList;
	}
	
	/**
	 * 
	 *
	 * @param billList 
	 */
	public void setBillList(List<PlanUnloadBillDto> billList) {
		this.billList = billList;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public String getPlatformCode() {
		return platformCode;
	}
	
	/**
	 * 
	 *
	 * @param platformCode 
	 */
	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public UnloadTaskDto getUnloadTaskDto() {
		return unloadTaskDto;
	}
	
	/**
	 * 
	 *
	 * @param unloadTaskDto 
	 */
	public void setUnloadTaskDto(UnloadTaskDto unloadTaskDto) {
		this.unloadTaskDto = unloadTaskDto;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public List<UnloadTaskDto> getUnloadTaskDtoList() {
		return unloadTaskDtoList;
	}
	
	/**
	 * 
	 *
	 * @param unloadTaskDtoList 
	 */
	public void setUnloadTaskDtoList(List<UnloadTaskDto> unloadTaskDtoList) {
		this.unloadTaskDtoList = unloadTaskDtoList;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public List<UnloadWaybillDetailDto> getUnloadWaybillDetailDtoList() {
		return unloadWaybillDetailDtoList;
	}
	
	/**
	 * 
	 *
	 * @param unloadWaybillDetailDtoList 
	 */
	public void setUnloadWaybillDetailDtoList(
			List<UnloadWaybillDetailDto> unloadWaybillDetailDtoList) {
		this.unloadWaybillDetailDtoList = unloadWaybillDetailDtoList;
	}
	
	/**
	 * Gets the 卸车流水信息*.
	 *
	 * @return the 卸车流水信息*
	 */
	public List<UnloadSerialNoDetailEntity> getUnloadSerialNoDetailList() {
		return unloadSerialNoDetailList;
	}
	
	/**
	 * Sets the 卸车流水信息*.
	 *
	 * @param unloadSerialNoDetailList the new 卸车流水信息*
	 */
	public void setUnloadSerialNoDetailList(
			List<UnloadSerialNoDetailEntity> unloadSerialNoDetailList) {
		this.unloadSerialNoDetailList = unloadSerialNoDetailList;
	}
	
	/**
	 * Gets the 卸车运单ID*.
	 *
	 * @return the 卸车运单ID*
	 */
	public String getUnloadWaybillDetailId() {
		return unloadWaybillDetailId;
	}
	
	/**
	 * Sets the 卸车运单ID*.
	 *
	 * @param unloadWaybillDetailId the new 卸车运单ID*
	 */
	public void setUnloadWaybillDetailId(String unloadWaybillDetailId) {
		this.unloadWaybillDetailId = unloadWaybillDetailId;
	}
	
	/**
	 * Gets the 理货员*.
	 *
	 * @return the 理货员*
	 */
	public List<LoaderParticipationEntity> getLoaderParticipationList() {
		return loaderParticipationList;
	}
	
	/**
	 * Sets the 理货员*.
	 *
	 * @param loaderParticipationList the new 理货员*
	 */
	public void setLoaderParticipationList(
			List<LoaderParticipationEntity> loaderParticipationList) {
		this.loaderParticipationList = loaderParticipationList;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public String getUnloadWay() {
		return unloadWay;
	}
	
	/**
	 * 
	 *
	 * @param unloadWay 
	 */
	public void setUnloadWay(String unloadWay) {
		this.unloadWay = unloadWay;
	}

	public Boolean getIsAir() {
		return isAir;
	}

	public void setIsAir(Boolean isAir) {
		this.isAir = isAir;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	/**
	 * @description 获取 件号
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:19:58
	 */
	public String getCargoNo() {
		return cargoNo;
	}

	/**
	 * @description 设置 件号
	 * @param cargoNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:20:34
	 */
	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}

	/**
	 * @description 获取 件类型
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:19:58
	 */
	public String getCargoType() {
		return cargoType;
	}

	/**
	 * @description 设置 件类型
	 * @param cargoNo
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2016年4月29日 下午1:20:34
	 */
	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	/**
	* @description 获取  零担快递标识
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月4日 下午1:24:41
	 */
	public String getExpressOrLingdan() {
		return expressOrLingdan;
	}

	/**
	* @description 设置  零担快递表示
	* @param expressOrLingdan
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月4日 下午1:24:45
	 */
	public void setExpressOrLingdan(String expressOrLingdan) {
		this.expressOrLingdan = expressOrLingdan;
	}
	
	 
}