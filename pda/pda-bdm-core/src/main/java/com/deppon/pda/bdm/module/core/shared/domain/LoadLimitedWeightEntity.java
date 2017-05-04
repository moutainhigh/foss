package com.deppon.pda.bdm.module.core.shared.domain;


/**
 * 
  * @ClassName LoadLimitedWeightEntity 装车限重
  * @Description TODO 
  * @author cbb
  * @date 2013-12-3 下午2:56:16
 */
public class LoadLimitedWeightEntity extends DomainEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 车型
	 */
	String models;
	
	/**
	 * 比重限定值
	 */
	double GravityLimit;
	
	/**
	 * 默认装载率
	 */
	double AlartLoadRate;


	public String getModels() {
		return models;
	}

	public void setModels(String models) {
		this.models = models;
	}

	public double getGravityLimit() {
		return GravityLimit;
	}

	public void setGravityLimit(double gravityLimit) {
		GravityLimit = gravityLimit;
	}

	public double getAlartLoadRate() {
		return AlartLoadRate;
	}

	public void setAlartLoadRate(double alartLoadRate) {
		AlartLoadRate = alartLoadRate;
	}

	
	
}
