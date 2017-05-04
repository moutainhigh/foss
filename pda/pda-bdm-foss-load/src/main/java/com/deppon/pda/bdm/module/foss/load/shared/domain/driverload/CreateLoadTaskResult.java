package com.deppon.pda.bdm.module.foss.load.shared.domain.driverload;

import java.util.List;

import com.deppon.pda.bdm.module.foss.load.shared.domain.LoaderModel;

/**
 * 司机接驳装车换回实体类
 * @ClassName KdCreateLoadTaskResult.java 
 * @Description 
 * @author 245955
 * @date 2015-4-21
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

public String getCreateUser() {
	return createUser;
}

public void setCreateUser(String createUser) {
	this.createUser = createUser;
}

public double getRatedLoad() {
	return ratedLoad;
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

public List<LoaderModel> getUserCodes() {
	return userCodes;
}

public void setUserCodes(List<LoaderModel> userCodes) {
	this.userCodes = userCodes;
}

public String getTaskCode() {
	return taskCode;
}

public void setTaskCode(String taskCode) {
	this.taskCode = taskCode;
}

public double getExpressConvertParameter() {
	return expressConvertParameter;
}

public void setExpressConvertParameter(double expressConvertParameter) {
	this.expressConvertParameter = expressConvertParameter;
}

	//private String serialNoStr;

	//private String waybillStr;

	/** 贵重物品货区编码 */
	// private String valueGoodsAreaCode;
	/** 包装获取编码 */
	// private String packGoodsAreaCode;
	// private List<LoadCrgDetail> crgDetails;

	// private String taskStr;

	// private String taskCode;
}
