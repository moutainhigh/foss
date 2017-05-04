package com.deppon.pda.bdm.module.foss.load.shared.domain.tallyload;

/**
 *接驳装车返回实体
 * @ClassName TranCreateLoadTaskResult.java 
 * @Description 
 * @author 245955
 * @date 2015-4-14
 */
public class CreateLoadTaskResult {
	/**
	 * 额定重量
	 */
	private double ratedLoad;
	/**
	 * 总体积
	 */
	private double ratedVolume;

	/**
	 * 任务号
	 */
	private String taskCode;

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
	
	public String getTaskCode() {
		return taskCode;
	}
	
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
}
