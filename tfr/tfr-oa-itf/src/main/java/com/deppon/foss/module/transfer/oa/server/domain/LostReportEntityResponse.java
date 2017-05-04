package com.deppon.foss.module.transfer.oa.server.domain;

import java.io.Serializable;
import java.util.List;
/**
 * @title: LostReportEntity
 * @description：零担丢货上报接口数据(QMS反馈给FOSS上报情况信息)实体
 * @author： ZHANGDANDAN
 * @date： 2016-11-25 上午9:31:02
 */
public class LostReportEntityResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	//运单号
    private String waybillNum;
    //收货部门名称
    private String takeOverDeptName;
	//收货部门编码
    private String takeOverDeptCode;
    //到达部门名称
    private String arriveDeptName;
    //到达部门编码
    private String arriveDeptCode;
    //运输性质
    private String transNature;
    //发货客户名称
    private String sendClientName;
    //发货客户编码
    private String sendClientCode;
    //收货客户名称
    private String takeOverClientName;
    //收货客户编码
    private String takeOverClientCode;
    //提货方式
    private String pickUpType;
    //付款方式
    private String payType;
    //发货客户手机
    private String sendClientMobile;
    //开单部门名称WayBillEntity实体/**运单提交人所在部门名称
    private String billingDeptName;
    //开单部门编码
    private String billingDeptCode;
    //货物名称
    private String goodsName;
    //总重量
    private String weight;
    //总体积
    private String volume;
    //货物包装
    private String goodsPackage;
    //货物总数量（件数）
    private String goodsNum;
    //开单时间
    private String billingTime;
    //开单金额
    private String billingFee;
    //保价金额
    private String safeMoney;
    //代收货款
    private String agentFee;
    //返单类型
    private String returnBillType;
    //储运方式
    private String storageTransport;
    //上报场景
    private long repScene;
    //上报方式
    private long repType;
    //上报时间
    private String repTime;
    //丢失货物包装
    private String loseGoodsPackage;
    //丢货流水号数量
    private String loseNum;
	//丢货流水号集合（未签收流水号）
	private List<LostCodeEntityResponse> flowCodeList;
	//内部轨迹
	private List<InLocusEntityResponse> LocusEntityList;
	//按件查询--内部轨迹
	private List<InternalLocusEntityResponse> inLocusEntityList;
	public String getWaybillNum() {
		return waybillNum;
	}
	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}
	public String getTakeOverDeptName() {
		return takeOverDeptName;
	}
	public void setTakeOverDeptName(String takeOverDeptName) {
		this.takeOverDeptName = takeOverDeptName;
	}
	public String getTakeOverDeptCode() {
		return takeOverDeptCode;
	}
	public void setTakeOverDeptCode(String takeOverDeptCode) {
		this.takeOverDeptCode = takeOverDeptCode;
	}
	public String getArriveDeptName() {
		return arriveDeptName;
	}
	public void setArriveDeptName(String arriveDeptName) {
		this.arriveDeptName = arriveDeptName;
	}
	public String getArriveDeptCode() {
		return arriveDeptCode;
	}
	public void setArriveDeptCode(String arriveDeptCode) {
		this.arriveDeptCode = arriveDeptCode;
	}
	public String getTransNature() {
		return transNature;
	}
	public void setTransNature(String transNature) {
		this.transNature = transNature;
	}
	public String getSendClientName() {
		return sendClientName;
	}
	public void setSendClientName(String sendClientName) {
		this.sendClientName = sendClientName;
	}
	public String getSendClientCode() {
		return sendClientCode;
	}
	public void setSendClientCode(String sendClientCode) {
		this.sendClientCode = sendClientCode;
	}
	public String getTakeOverClientName() {
		return takeOverClientName;
	}
	public void setTakeOverClientName(String takeOverClientName) {
		this.takeOverClientName = takeOverClientName;
	}
	public String getTakeOverClientCode() {
		return takeOverClientCode;
	}
	public void setTakeOverClientCode(String takeOverClientCode) {
		this.takeOverClientCode = takeOverClientCode;
	}
	public String getPickUpType() {
		return pickUpType;
	}
	public void setPickUpType(String pickUpType) {
		this.pickUpType = pickUpType;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getSendClientMobile() {
		return sendClientMobile;
	}
	public void setSendClientMobile(String sendClientMobile) {
		this.sendClientMobile = sendClientMobile;
	}
	public String getBillingDeptName() {
		return billingDeptName;
	}
	public void setBillingDeptName(String billingDeptName) {
		this.billingDeptName = billingDeptName;
	}
	public String getBillingDeptCode() {
		return billingDeptCode;
	}
	public void setBillingDeptCode(String billingDeptCode) {
		this.billingDeptCode = billingDeptCode;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getGoodsPackage() {
		return goodsPackage;
	}
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}
	public String getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}
	public String getBillingTime() {
		return billingTime;
	}
	public void setBillingTime(String billingTime) {
		this.billingTime = billingTime;
	}
	public String getBillingFee() {
		return billingFee;
	}
	public void setBillingFee(String billingFee) {
		this.billingFee = billingFee;
	}
	public String getSafeMoney() {
		return safeMoney;
	}
	public void setSafeMoney(String safeMoney) {
		this.safeMoney = safeMoney;
	}
	public String getAgentFee() {
		return agentFee;
	}
	public void setAgentFee(String agentFee) {
		this.agentFee = agentFee;
	}
	public String getReturnBillType() {
		return returnBillType;
	}
	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}
	public String getStorageTransport() {
		return storageTransport;
	}
	public void setStorageTransport(String storageTransport) {
		this.storageTransport = storageTransport;
	}
	public long getRepScene() {
		return repScene;
	}
	public void setRepScene(long repScene) {
		this.repScene = repScene;
	}
	public long getRepType() {
		return repType;
	}
	public void setRepType(long repType) {
		this.repType = repType;
	}
	public String getRepTime() {
		return repTime;
	}
	public void setRepTime(String repTime) {
		this.repTime = repTime;
	}
	public String getLoseGoodsPackage() {
		return loseGoodsPackage;
	}
	public void setLoseGoodsPackage(String loseGoodsPackage) {
		this.loseGoodsPackage = loseGoodsPackage;
	}
	public String getLoseNum() {
		return loseNum;
	}
	public void setLoseNum(String loseNum) {
		this.loseNum = loseNum;
	}
	public List<LostCodeEntityResponse> getFlowCodeList() {
		return flowCodeList;
	}
	public void setFlowCodeList(List<LostCodeEntityResponse> flowCodeList) {
		this.flowCodeList = flowCodeList;
	}
	public List<InLocusEntityResponse> getLocusEntityList() {
		return LocusEntityList;
	}
	public void setLocusEntityList(List<InLocusEntityResponse> locusEntityList) {
		LocusEntityList = locusEntityList;
	}
	public List<InternalLocusEntityResponse> getInLocusEntityList() {
		return inLocusEntityList;
	}
	public void setInLocusEntityList(
			List<InternalLocusEntityResponse> inLocusEntityList) {
		this.inLocusEntityList = inLocusEntityList;
	}

	
	
}
