package com.deppon.pda.bdm.module.foss.load.shared.domain;

import java.util.List;

/**
 * 
 * TODO(创建装车任务返回实体)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-11-8 上午11:19:01,content:TODO
 * </p>
 * 
 * @author Administrator
 * @date 2012-11-8 上午11:19:01
 * @since
 * @version
 */
public class CreateLoadTaskResult {
	/**
	 * 是否主PDA
	 */
	private String createUser;
	/**
	 * 额定重量
	 */
	private double ratedLoad;
	/**
	 * 总体积
	 */
	private double ratedVolume;
	/**
	 * 提示装载率
	 */
	private double alartLoadRate;

	/**
	 * 比重限定值
	 */
	private double gravityLimit;

	private List<LoaderModel> userCodes;
	
	private String taskCode;
	
	//重量体积转换
   private double expressConvertParameter;

	//private String serialNoStr;

	//private String waybillStr;

	/** 贵重物品货区编码 */
	// private String valueGoodsAreaCode;
	/** 包装获取编码 */
	// private String packGoodsAreaCode;
	// private List<LoadCrgDetail> crgDetails;

	// private String taskStr;

	// private String taskCode;
	
	public double getRatedLoad() {
		return ratedLoad;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public void setRatedLoad(double ratedLoad) {
		this.ratedLoad = ratedLoad;
	}

	public double getRatedVolume() {
		return ratedVolume;
	}

	public void setRatedVolume(double ratedVolume) {
		this.ratedVolume = ratedVolume;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public List<LoaderModel> getUserCodes() {
		return userCodes;
	}

	public void setUserCodes(List<LoaderModel> userCodes) {
		this.userCodes = userCodes;
	}
	
	public double getAlartLoadRate() {
		return alartLoadRate;
	}

	public void setAlartLoadRate(double alartLoadRate) {
		this.alartLoadRate = alartLoadRate;
	}

	public double getGravityLimit() {
		return gravityLimit;
	}

	public void setGravityLimit(double gravityLimit) {
		this.gravityLimit = gravityLimit;
	}

	public double getExpressConvertParameter() {
		return expressConvertParameter;
	}

	public void setExpressConvertParameter(double expressConvertParameter) {
		this.expressConvertParameter = expressConvertParameter;
	}

	/*
	 * public String getValueGoodsAreaCode() { return valueGoodsAreaCode; }
	 * public void setValueGoodsAreaCode(String valueGoodsAreaCode) {
	 * this.valueGoodsAreaCode = valueGoodsAreaCode; } public String
	 * getPackGoodsAreaCode() { return packGoodsAreaCode; } public void
	 * setPackGoodsAreaCode(String packGoodsAreaCode) { this.packGoodsAreaCode =
	 * packGoodsAreaCode; }
	 */
	/*
	 * public String getTaskCode() { return taskCode; } public void
	 * setTaskCode(String taskCode) { this.taskCode = taskCode; }
	 */
	/*
	 * public List<LoadCrgDetail> getCrgDetails() { return crgDetails; } public
	 * void setCrgDetails(List<LoadCrgDetail> crgDetails) { this.crgDetails =
	 * crgDetails; }
	 */
/*	public String getSerialNoStr() {
		return serialNoStr;
	}

	public void setSerialNoStr(String serialNoStr) {
		this.serialNoStr = serialNoStr;
	}

	public String getWaybillStr() {
		return waybillStr;
	}

	public void setWaybillStr(String waybillStr) {
		this.waybillStr = waybillStr;
	}*/
	/*
	 * public String getTaskStr() { return taskStr; } public void
	 * setTaskStr(String taskStr) { this.taskStr = taskStr; }
	 */

}
