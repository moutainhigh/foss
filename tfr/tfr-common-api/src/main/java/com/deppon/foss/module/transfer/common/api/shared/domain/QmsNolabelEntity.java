package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.util.Date;

/**
 * @author niuly
 * @function 上报QMS无标签多货实体
 */
public class QmsNolabelEntity {
	// 货物品名 
	private String goodsName;
	// 无标签运单号 
	private String noTranslateCode;
	// 无标签多货流水号 
	private String folwCode;
	// 品牌 
	private String goodsBrand;
	// 品类 
	private String goodsKinds;
	// 车牌号 
	private String carNo;
	// 包装 
	//private Long pack;
	// 重量 
	private String billWeight;
	// 手写关键字 
	private String keyWord;
	// 尺寸 
	private String goodsSize;
	// 体积 
	private String volume;
	// 上报人工号 
	private String subEmpCode;
	// 上报人 
	private String applyuserName;
	// 上一环节部门标杆编码 
	private String linkDeptCode;
	// 上一环节部门 
	private String linkDept;
	// 发现货区 
	private String findArea;
	// 发货时间 
	private Date deliveryTime;
	// 无标签发现类型 
	private String findType;
	// 发现人 
	private String findPerson;
	// 上报人部门标杆编码 
	private String subDeptCode;
	// 上报人部门 
	private String subDept;
	// 总件数 
	private String totalNum;
	// 无标签多货编号 
	private String noLabelCode;
	// 是否为快递货 
	private String isExp;
	// 事情经过 
	private String thProcess;	
	// 是否泄漏货 
	private String isLeakage;
	// 原货物运单号
	//private String goodsCode;
	//事业部名称
	private String respDivisionName;
	// 事业部标杆编码
	private String respDivisionCode;
	// 上报人职位 
	private String subEmpPos;
	// 确认人 
	//private String conPerson;
	// 确认单号 
	//private String conBill;
	// 确认人工号 
	//private String conEmpCode;
	// 货物状态 
	private String goodsStatus;
	// 丢货上报时间
	private Date loseRepTime;
	// 经手类别 
	//private String handleStyle;
	// 上报丢货差错编号 
	//private String reportLoseNo;
	// 上报时间 
	private Date repTime;
	// 包装 
	private String goodsPackage;
	// 发现时间
	private Date findTime ;
	// 正面照路径 
	private String facePhotoPath;
	// 整体照路径 
	private String wholePhotoPath;
	// 内物照路径 
	private String insidePhotoPath;
	// 附件照1路径 
	private String addPhotoPath1;
	// 附件照2路径 
	private String addPhotoPath2;
	// 附件照1名称 
	private String addPhotoName1;
	// 附件照2名称
	private String addPhotoName2;
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getNoTranslateCode() {
		return noTranslateCode;
	}
	public void setNoTranslateCode(String noTranslateCode) {
		this.noTranslateCode = noTranslateCode;
	}
	public String getFolwCode() {
		return folwCode;
	}
	public void setFolwCode(String folwCode) {
		this.folwCode = folwCode;
	}
	public String getGoodsBrand() {
		return goodsBrand;
	}
	public void setGoodsBrand(String goodsBrand) {
		this.goodsBrand = goodsBrand;
	}
	public String getGoodsKinds() {
		return goodsKinds;
	}
	public void setGoodsKinds(String goodsKinds) {
		this.goodsKinds = goodsKinds;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getBillWeight() {
		return billWeight;
	}
	public void setBillWeight(String billWeight) {
		this.billWeight = billWeight;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getGoodsSize() {
		return goodsSize;
	}
	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getSubEmpCode() {
		return subEmpCode;
	}
	public void setSubEmpCode(String subEmpCode) {
		this.subEmpCode = subEmpCode;
	}
	public String getApplyuserName() {
		return applyuserName;
	}
	public void setApplyuserName(String applyuserName) {
		this.applyuserName = applyuserName;
	}
	public String getLinkDeptCode() {
		return linkDeptCode;
	}
	public void setLinkDeptCode(String linkDeptCode) {
		this.linkDeptCode = linkDeptCode;
	}
	public String getLinkDept() {
		return linkDept;
	}
	public void setLinkDept(String linkDept) {
		this.linkDept = linkDept;
	}
	public String getFindArea() {
		return findArea;
	}
	public void setFindArea(String findArea) {
		this.findArea = findArea;
	}
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getFindType() {
		return findType;
	}
	public void setFindType(String findType) {
		this.findType = findType;
	}
	public String getFindPerson() {
		return findPerson;
	}
	public void setFindPerson(String findPerson) {
		this.findPerson = findPerson;
	}
	public String getSubDeptCode() {
		return subDeptCode;
	}
	public void setSubDeptCode(String subDeptCode) {
		this.subDeptCode = subDeptCode;
	}
	public String getSubDept() {
		return subDept;
	}
	public void setSubDept(String subDept) {
		this.subDept = subDept;
	}
	public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
	public String getNoLabelCode() {
		return noLabelCode;
	}
	public void setNoLabelCode(String noLabelCode) {
		this.noLabelCode = noLabelCode;
	}
	public String getIsExp() {
		return isExp;
	}
	public void setIsExp(String isExp) {
		this.isExp = isExp;
	}
	public String getThProcess() {
		return thProcess;
	}
	public void setThProcess(String thProcess) {
		this.thProcess = thProcess;
	}
	public String getIsLeakage() {
		return isLeakage;
	}
	public void setIsLeakage(String isLeakage) {
		this.isLeakage = isLeakage;
	}
	public String getRespDivisionName() {
		return respDivisionName;
	}
	public void setRespDivisionName(String respDivisionName) {
		this.respDivisionName = respDivisionName;
	}
	public String getRespDivisionCode() {
		return respDivisionCode;
	}
	public void setRespDivisionCode(String respDivisionCode) {
		this.respDivisionCode = respDivisionCode;
	}
	public String getSubEmpPos() {
		return subEmpPos;
	}
	public void setSubEmpPos(String subEmpPos) {
		this.subEmpPos = subEmpPos;
	}
	public String getGoodsStatus() {
		return goodsStatus;
	}
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	public Date getLoseRepTime() {
		return loseRepTime;
	}
	public void setLoseRepTime(Date loseRepTime) {
		this.loseRepTime = loseRepTime;
	}
	public Date getRepTime() {
		return repTime;
	}
	public void setRepTime(Date repTime) {
		this.repTime = repTime;
	}
	public String getGoodsPackage() {
		return goodsPackage;
	}
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}
	public Date getFindTime() {
		return findTime;
	}
	public void setFindTime(Date findTime) {
		this.findTime = findTime;
	}
	public String getFacePhotoPath() {
		return facePhotoPath;
	}
	public void setFacePhotoPath(String facePhotoPath) {
		this.facePhotoPath = facePhotoPath;
	}
	public String getWholePhotoPath() {
		return wholePhotoPath;
	}
	public void setWholePhotoPath(String wholePhotoPath) {
		this.wholePhotoPath = wholePhotoPath;
	}
	public String getInsidePhotoPath() {
		return insidePhotoPath;
	}
	public void setInsidePhotoPath(String insidePhotoPath) {
		this.insidePhotoPath = insidePhotoPath;
	}
	public String getAddPhotoPath1() {
		return addPhotoPath1;
	}
	public void setAddPhotoPath1(String addPhotoPath1) {
		this.addPhotoPath1 = addPhotoPath1;
	}
	public String getAddPhotoPath2() {
		return addPhotoPath2;
	}
	public void setAddPhotoPath2(String addPhotoPath2) {
		this.addPhotoPath2 = addPhotoPath2;
	}
	public String getAddPhotoName1() {
		return addPhotoName1;
	}
	public void setAddPhotoName1(String addPhotoName1) {
		this.addPhotoName1 = addPhotoName1;
	}
	public String getAddPhotoName2() {
		return addPhotoName2;
	}
	public void setAddPhotoName2(String addPhotoName2) {
		this.addPhotoName2 = addPhotoName2;
	}
}
