/**   
 * @Title: UploadFileSnippetBean.java 
 * @Package com.deppon.pda.bdm.module.ocb.shared.domain 
 * @Description: 
 * @author 183272
 * @date 2014年10月11日 下午1:41:41 
 * @version V1.0   
 */
package com.deppon.pda.bdm.module.ocb.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: UploadFileSnippetBean
 * @Description: 断点续传 碎片文件
 * @author 183272
 * @date 2014年10月11日 下午1:41:41
 * 
 */
public class UploadImageSnippetBean implements Serializable {
	/** 
	* @Fields serialVersionUID 
	*/ 
	private static final long serialVersionUID = 1L;
	private String uuid; //序列
	private String wblcode; // 运单号
	private String ordercode; //订单号
	private String usercode; //工号
	private String truckCode; //司机
	private String mobilephone; //手机
	private String deptcode; //所在部门
	private String iscash; //是否现金
	private String isfifty; //是否大票
	private String receiveOrgCode ;//收货部门(出发部门)
	private String fhashcode; //hash码
	private long fsize; //文件大小
	private int fblocks; //块数
	private String ftype; //文件类型 
	private String fready; //已上传完成的块编号
	private String fpath; //存储路径
	private String fpathparent;//存储路径的上一层路径
	private int fisfinished; //要否上传完成
	private Date ffinishedtime; //上传完成时间
	private Date  ftime; //创建时间
	private String baiDuId; //百度push 拼接ID channleID+user_ID
	private String nowTime;//进入开单界面时间
	
	//author:245960 DATE:2015-08-08 COMMENT:王刚要求加字段传给foss
	/**
	 * 是否特殊客户 Y 特殊  N 非特殊
	 */
	private String  specialCustomer;
	
	//author:268974 展会货需求  前台对接鄢凌
	private String exhibition;
	/**
	 * @return  the specialCustomer
	 */
	public String getSpecialCustomer() {
		return specialCustomer;
	}
	
	/**
	 * @param specialCustomer the specialCustomer to set
	 */
	public void setSpecialCustomer(String specialCustomer) {
		this.specialCustomer = specialCustomer;
	}
	
	/**
	 * 是否大件上楼加收
	 */
	private String isBigUp;
	/**
	 * 500KG到1000KG超重件数
	 */
	private Integer fhToOtOverQty;
	/**
	 * 1000KG到2000KG超重件数
	 */
	private Integer otToTtOverQty;
	/**
	 * 劳务费费率
	 */
	private BigDecimal serviceRate;
	/**
	 * 劳务费
	 */
	private BigDecimal serviceFee;
	/** 
	 * @return uuid 
	 */
	public String getUuid() {
		return uuid;
	}
	/** 
	 * @param uuid 要设置的 uuid 
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	/** 
	 * @return wblcode 
	 */
	public String getWblcode() {
		return wblcode;
	}
	/** 
	 * @param wblcode 要设置的 wblcode 
	 */
	public void setWblcode(String wblcode) {
		this.wblcode = wblcode;
	}
	
	
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	/** 
	 * @return ordercode 
	 */
	public String getOrdercode() {
		return ordercode;
	}
	/** 
	 * @param ordercode 要设置的 ordercode 
	 */
	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}
	/** 
	 * @return usercode 
	 */
	public String getUsercode() {
		return usercode;
	}
	
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	/** 
	 * @param usercode 要设置的 usercode 
	 */
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	/** 
	 * @return deptcode 
	 */
	public String getDeptcode() {
		return deptcode;
	}
	/** 
	 * @param deptcode 要设置的 deptcode 
	 */
	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}
	/** 
	 * @return iscash 
	 */
	public String getIscash() {
		return iscash;
	}
	/** 
	 * @param iscash 要设置的 iscash 
	 */
	public void setIscash(String iscash) {
		this.iscash = iscash;
	}
	/** 
	 * @return isfifty 
	 */
	public String getIsfifty() {
		return isfifty;
	}
	/** 
	 * @param isfifty 要设置的 isfifty 
	 */
	public void setIsfifty(String isfifty) {
		this.isfifty = isfifty;
	}
	
	/** 
	 * @return receiveOrgCode 
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}
	/** 
	 * @param receiveOrgCode 要设置的 receiveOrgCode 
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}
	/** 
	 * @return fhashcode 
	 */
	public String getFhashcode() {
		return fhashcode;
	}
	/** 
	 * @param fhashcode 要设置的 fhashcode 
	 */
	public void setFhashcode(String fhashcode) {
		this.fhashcode = fhashcode;
	}
	/** 
	 * @return fsize 
	 */
	public long getFsize() {
		return fsize;
	}
	/** 
	 * @param fsize 要设置的 fsize 
	 */
	public void setFsize(long fsize) {
		this.fsize = fsize;
	}
	/** 
	 * @return fblocks 
	 */
	public int getFblocks() {
		return fblocks;
	}
	/** 
	 * @param fblocks 要设置的 fblocks 
	 */
	public void setFblocks(int fblocks) {
		this.fblocks = fblocks;
	}
	/** 
	 * @return ftype 
	 */
	public String getFtype() {
		return ftype;
	}
	/** 
	 * @param ftype 要设置的 ftype 
	 */
	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
	/** 
	 * @return fready 
	 */
	public String getFready() {
		return fready;
	}
	/** 
	 * @param fready 要设置的 fready 
	 */
	public void setFready(String fready) {
		this.fready = fready;
	}
	/** 
	 * @return fpath 
	 */
	public String getFpath() {
		return fpath;
	}
	/** 
	 * @param fpath 要设置的 fpath 
	 */
	public void setFpath(String fpath) {
		this.fpath = fpath;
	}
	/** 
	 * @return fisfinished 
	 */
	public int getFisfinished() {
		return fisfinished;
	}
	/** 
	 * @param fisfinished 要设置的 fisfinished 
	 */
	public void setFisfinished(int fisfinished) {
		this.fisfinished = fisfinished;
	}
	/** 
	 * @return ffinishedtime 
	 */
	public Date getFfinishedtime() {
		return ffinishedtime;
	}
	/** 
	 * @param ffinishedtime 要设置的 ffinishedtime 
	 */
	public void setFfinishedtime(Date ffinishedtime) {
		this.ffinishedtime = ffinishedtime;
	}
	/** 
	 * @return ftime 
	 */
	public Date getFtime() {
		return ftime;
	}
	/** 
	 * @param ftime 要设置的 ftime 
	 */
	public void setFtime(Date ftime) {
		this.ftime = ftime;
	}
	/** 
	 * @return baiDuId 
	 */
	public String getBaiDuId() {
		return baiDuId;
	}
	/** 
	 * @param baiDuId 要设置的 baiDuId 
	 */
	public void setBaiDuId(String baiDuId) {
		this.baiDuId = baiDuId;
	}
	/** 
	 * @return isBigUp 
	 */
	public String getIsBigUp() {
		return isBigUp;
	}
	/** 
	 * @param isBigUp 要设置的 isBigUp 
	 */
	public void setIsBigUp(String isBigUp) {
		this.isBigUp = isBigUp;
	}
	/** 
	 * @return fhToOtOverQty 
	 */
	public Integer getFhToOtOverQty() {
		return fhToOtOverQty;
	}
	/** 
	 * @param fhToOtOverQty 要设置的 fhToOtOverQty 
	 */
	public void setFhToOtOverQty(Integer fhToOtOverQty) {
		this.fhToOtOverQty = fhToOtOverQty;
	}
	/** 
	 * @return otToTtOverQty 
	 */
	public Integer getOtToTtOverQty() {
		return otToTtOverQty;
	}
	/** 
	 * @param otToTtOverQty 要设置的 otToTtOverQty 
	 */
	public void setOtToTtOverQty(Integer otToTtOverQty) {
		this.otToTtOverQty = otToTtOverQty;
	}
	/** 
	 * @return serviceRate 
	 */
	public BigDecimal getServiceRate() {
		return serviceRate;
	}
	/** 
	 * @param serviceRate 要设置的 serviceRate 
	 */
	public void setServiceRate(BigDecimal serviceRate) {
		this.serviceRate = serviceRate;
	}
	/** 
	 * @return serviceFee 
	 */
	public BigDecimal getServiceFee() {
		return serviceFee;
	}
	/** 
	 * @param serviceFee 要设置的 serviceFee 
	 */
	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public String getNowTime() {
		return nowTime;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public String getFpathparent() {
		return fpathparent;
	}
	/**
	 * @parameters：
	 * @return：
	 */
	public void setFpathparent(String fpathparent) {
		this.fpathparent = fpathparent;
	}

	public String getExhibition() {
		return exhibition;
	}

	public void setExhibition(String exhibition) {
		this.exhibition = exhibition;
	}

	
	
}
