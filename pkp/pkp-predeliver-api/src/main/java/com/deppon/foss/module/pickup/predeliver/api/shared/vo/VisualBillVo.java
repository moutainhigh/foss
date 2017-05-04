package com.deppon.foss.module.pickup.predeliver.api.shared.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VisibleHandBillReturnEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisibleAutoSortDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualAddressMarkDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualBillArrageDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualBillConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualBillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualBillPoint;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;

/**
 * * 可视化排单VO
 * @author 239284
 *
 */
public class VisualBillVo {

	//查询条件交互DTO
	private VisualBillConditionDto conditionDto;

	//查询结果交互DTO
	private List<VisualBillDto> listDto = new ArrayList<VisualBillDto>();
	
	//可视化自动排序Dto
	private VisibleAutoSortDto visibleAutoSortDto =new VisibleAutoSortDto();
	
	//可视化自动排序返回Dto
	private List<VisibleAutoSortDto> visibleAutoSortDtoList =new ArrayList<VisibleAutoSortDto>();
	
	//可视化自动排序返回Dto备份
	private List<VisibleAutoSortDto> visibleAutoSortDtoListBak =new ArrayList<VisibleAutoSortDto>();
	
	//待排运单查询条件
	private WaybillToArrangeDto waybillToArrangeDto; 
	
	// 待排运单列表
	private List<WaybillToArrangeDto> waybillToArrangeDtoList;
	
	//已排单信息DTO
	private List<VisualBillArrageDto> arrageListDto = new ArrayList<VisualBillArrageDto>();
	
	// 派送单
	private DeliverbillEntity deliverbill = new DeliverbillEntity();
	//派送单状态
	private String deliverStatus; 
	
	//运单退回
	private VisibleHandBillReturnEntity handBillReturnEntity; 
	
	//可视化地图json
	private String visibleMapJSON;
	
	//总距离
	private String totalDistance;
	
	//与GIS地图交互参数
	private String paramForGIS; 
	
	//输入单号右移动-运单号
	private String wayBillNo;
	//输入单号右移动-送货日期
	private String rightDeliverDate; 
	
	//已排单运单明细页面下方.总票数
	private String totalTicket;
	//已排单运单明细页面下方.总件数
	private String totalCount;
	//已排单运单明细页面下方.总重量
	private String totalWeight;
	//已排单运单明细页面下方.总体积
	private String totalVolumn;
	//已排单运单明细页面下方.到付金额
	private String totalPayAmount;
	//已排单运单明细页面下方.装载率(容量和体积)
	private String loadRate;
	//已排单运单明细页面下方.额定净空/额定载重率
	private String nominalRate;
	
	//查询排班信息条件.车牌号
	private String vehilceNo;
	//查询排班信息条件.送货日期
	private String deliverDate;
	//查询排班信息.班次
	private String frequencyNo;
	//查询排班信息.带货(方)
	private String expectedBringVolume;
	//相关提示：此车牌号XX，送货日期XX，有派送单X，为XX状态
	private String vehilceNoTips;
	//排程二期-出车任务
	private String carTaskinfo;
	//排程二期-预计出车时间
	private String preCartaskTime;
	//排程二期-带货部门编码
	private String takeGoodsDeptcode;
	//排程二期-带货部门
	private String takeGoodsDeptname;
	//排程二期-转货部门编码
	private String transferDeptcode;
	//排程二期-转货部门
	private String transferDeptname;



	//运单自动排序-运单坐标
	private List<VisualBillPoint> listPoints = new ArrayList<VisualBillPoint>();
	//运单自动排序-目的站
	private String lastLoadOrgCode;
	//运单自动排序-返回的排序map
	private Map sortMap;
	
	
	//特殊地址标记-运单号
	private String specialWayBillNO;
	//特殊地址标记-小区名称(坐标标记共用)
	private String specialSmallZoneName;
	//特殊地址标记-小区代码(坐标标记共用)
	private String specialSmallZoneCode;
	//特殊地址标记-特殊地址类型
	private String specialType;
	//特殊地址标记-特殊地址
	private String specialAddress;
	
	//地址坐标标记集合
	List<VisualAddressMarkDto> listAddresMarks = new ArrayList<VisualAddressMarkDto>();
	//坐标标记实际小区名称
	private String actualSmallzoneName;
	//坐标标记实际小区代码
	private String actualSmallzoneCode;
	//坐标标记-经度
	private String lng;
	//坐标标记-纬度
	private String lat;
	//坐标标记-坐标实体
	private VisualAddressMarkDto markDto;
	
	//拖动排序传到后台的json串
	private String dragStr;
	
	

	public VisualBillConditionDto getConditionDto() {
		return conditionDto;
	}

	public void setConditionDto(VisualBillConditionDto conditionDto) {
		this.conditionDto = conditionDto;
	}

	public List<VisualBillDto> getListDto() {
		return listDto;
	}

	public void setListDto(List<VisualBillDto> listDto) {
		this.listDto = listDto;
	}

	public List<VisualBillArrageDto> getArrageListDto() {
		return arrageListDto;
	}

	public void setArrageListDto(List<VisualBillArrageDto> arrageListDto) {
		this.arrageListDto = arrageListDto;
	}

	public DeliverbillEntity getDeliverbill() {
		return deliverbill;
	}

	public void setDeliverbill(DeliverbillEntity deliverbill) {
		this.deliverbill = deliverbill;
	}

	public WaybillToArrangeDto getWaybillToArrangeDto() {
		return waybillToArrangeDto;
	}

	public void setWaybillToArrangeDto(WaybillToArrangeDto waybillToArrangeDto) {
		this.waybillToArrangeDto = waybillToArrangeDto;
	}

	public List<WaybillToArrangeDto> getWaybillToArrangeDtoList() {
		return waybillToArrangeDtoList;
	}

	public void setWaybillToArrangeDtoList(
			List<WaybillToArrangeDto> waybillToArrangeDtoList) {
		this.waybillToArrangeDtoList = waybillToArrangeDtoList;
	}

	public VisibleHandBillReturnEntity getHandBillReturnEntity() {
		return handBillReturnEntity;
	}

	public void setHandBillReturnEntity(
			VisibleHandBillReturnEntity handBillReturnEntity) {
		this.handBillReturnEntity = handBillReturnEntity;
	}

	public String getTotalTicket() {
		return totalTicket;
	}

	public void setTotalTicket(String totalTicket) {
		this.totalTicket = totalTicket;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getTotalVolumn() {
		return totalVolumn;
	}

	public void setTotalVolumn(String totalVolumn) {
		this.totalVolumn = totalVolumn;
	}

	public String getTotalPayAmount() {
		return totalPayAmount;
	}

	public void setTotalPayAmount(String totalPayAmount) {
		this.totalPayAmount = totalPayAmount;
	}

	public String getLoadRate() {
		return loadRate;
	}

	public void setLoadRate(String loadRate) {
		this.loadRate = loadRate;
	}

	public String getNominalRate() {
		return nominalRate;
	}

	public void setNominalRate(String nominalRate) {
		this.nominalRate = nominalRate;
	}

	public String getVehilceNo() {
		return vehilceNo;
	}

	public void setVehilceNo(String vehilceNo) {
		this.vehilceNo = vehilceNo;
	}

	public String getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getFrequencyNo() {
		return frequencyNo;
	}

	public void setFrequencyNo(String frequencyNo) {
		this.frequencyNo = frequencyNo;
	}

	public String getExpectedBringVolume() {
		return expectedBringVolume;
	}

	public void setExpectedBringVolume(String expectedBringVolume) {
		this.expectedBringVolume = expectedBringVolume;
	}

	public String getParamForGIS() {
		return paramForGIS;
	}

	public void setParamForGIS(String paramForGIS) {
		this.paramForGIS = paramForGIS;
	}

	public List<VisualBillPoint> getListPoints() {
		return listPoints;
	}

	public void setListPoints(List<VisualBillPoint> listPoints) {
		this.listPoints = listPoints;
	}

	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	public Map getSortMap() {
		return sortMap;
	}

	public void setSortMap(Map sortMap) {
		this.sortMap = sortMap;
	}

	public String getSpecialWayBillNO() {
		return specialWayBillNO;
	}

	public void setSpecialWayBillNO(String specialWayBillNO) {
		this.specialWayBillNO = specialWayBillNO;
	}

	public String getSpecialType() {
		return specialType;
	}

	public void setSpecialType(String specialType) {
		this.specialType = specialType;
	}

	public String getSpecialSmallZoneName() {
		return specialSmallZoneName;
	}

	public void setSpecialSmallZoneName(String specialSmallZoneName) {
		this.specialSmallZoneName = specialSmallZoneName;
	}

	public String getSpecialSmallZoneCode() {
		return specialSmallZoneCode;
	}

	public void setSpecialSmallZoneCode(String specialSmallZoneCode) {
		this.specialSmallZoneCode = specialSmallZoneCode;
	}

	public String getSpecialAddress() {
		return specialAddress;
	}

	public void setSpecialAddress(String specialAddress) {
		this.specialAddress = specialAddress;
	}

	public List<VisualAddressMarkDto> getListAddresMarks() {
		return listAddresMarks;
	}

	public void setListAddresMarks(List<VisualAddressMarkDto> listAddresMarks) {
		this.listAddresMarks = listAddresMarks;
	}

	public String getActualSmallzoneName() {
		return actualSmallzoneName;
	}

	public void setActualSmallzoneName(String actualSmallzoneName) {
		this.actualSmallzoneName = actualSmallzoneName;
	}

	public String getActualSmallzoneCode() {
		return actualSmallzoneCode;
	}

	public void setActualSmallzoneCode(String actualSmallzoneCode) {
		this.actualSmallzoneCode = actualSmallzoneCode;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public VisualAddressMarkDto getMarkDto() {
		return markDto;
	}

	public void setMarkDto(VisualAddressMarkDto markDto) {
		this.markDto = markDto;
	}

	public String getDeliverStatus() {
		return deliverStatus;
	}

	public void setDeliverStatus(String deliverStatus) {
		this.deliverStatus = deliverStatus;
	}

	public String getVehilceNoTips() {
		return vehilceNoTips;
	}

	public void setVehilceNoTips(String vehilceNoTips) {
		this.vehilceNoTips = vehilceNoTips;
	}

	public List<VisibleAutoSortDto> getVisibleAutoSortDtoList() {
		return visibleAutoSortDtoList;
	}

	public void setVisibleAutoSortDtoList(
			List<VisibleAutoSortDto> visibleAutoSortDtoList) {
		this.visibleAutoSortDtoList = visibleAutoSortDtoList;
	}

	public VisibleAutoSortDto getVisibleAutoSortDto() {
		return visibleAutoSortDto;
	}

	public void setVisibleAutoSortDto(VisibleAutoSortDto visibleAutoSortDto) {
		this.visibleAutoSortDto = visibleAutoSortDto;
	}

	public String getVisibleMapJSON() {
		return visibleMapJSON;
	}

	public void setVisibleMapJSON(String visibleMapJSON) {
		this.visibleMapJSON = visibleMapJSON;
	}

	public String getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(String totalDistance) {
		this.totalDistance = totalDistance;
	}
	public String getWayBillNo() {
		return wayBillNo;
	}

	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}

	public String getRightDeliverDate() {
		return rightDeliverDate;
	}

	public void setRightDeliverDate(String rightDeliverDate) {
		this.rightDeliverDate = rightDeliverDate;
	}

	public String getCarTaskinfo() {
		return carTaskinfo;
	}

	public void setCarTaskinfo(String carTaskinfo) {
		this.carTaskinfo = carTaskinfo;
	}

	public String getPreCartaskTime() {
		return preCartaskTime;
	}

	public void setPreCartaskTime(String preCartaskTime) {
		this.preCartaskTime = preCartaskTime;
	}

	public String getTakeGoodsDeptcode() {
		return takeGoodsDeptcode;
	}

	public void setTakeGoodsDeptcode(String takeGoodsDeptcode) {
		this.takeGoodsDeptcode = takeGoodsDeptcode;
	}

	public String getTakeGoodsDeptname() {
		return takeGoodsDeptname;
	}

	public void setTakeGoodsDeptname(String takeGoodsDeptname) {
		this.takeGoodsDeptname = takeGoodsDeptname;
	}

	public String getTransferDeptcode() {
		return transferDeptcode;
	}

	public void setTransferDeptcode(String transferDeptcode) {
		this.transferDeptcode = transferDeptcode;
	}

	public String getTransferDeptname() {
		return transferDeptname;
	}

	public void setTransferDeptname(String transferDeptname) {
		this.transferDeptname = transferDeptname;
	}

	public List<VisibleAutoSortDto> getVisibleAutoSortDtoListBak() {
		return visibleAutoSortDtoListBak;
	}

	public void setVisibleAutoSortDtoListBak(
			List<VisibleAutoSortDto> visibleAutoSortDtoListBak) {
		this.visibleAutoSortDtoListBak = visibleAutoSortDtoListBak;
	}

	public String getDragStr() {
		return dragStr;
	}

	public void setDragStr(String dragStr) {
		this.dragStr = dragStr;
	}
	
}
