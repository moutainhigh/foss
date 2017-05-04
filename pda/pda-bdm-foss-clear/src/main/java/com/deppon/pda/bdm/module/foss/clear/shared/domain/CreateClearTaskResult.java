package com.deppon.pda.bdm.module.foss.clear.shared.domain;

import java.util.List;

/**
 * 
 * TODO(建立清仓任务返回实体)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2013-3-23 下午5:06:10,content:TODO </p>
 * @author Administrator
 * @date 2013-3-23 下午5:06:10
 * @since
 * @version
 */
public class CreateClearTaskResult {
	/**
	 * 任务号
	 */
	private String taskCode;
	/**
	 * 创建人
	 */
	private String createUser;
	
	/**
	 * 货区编号
	 */
	private String goodsAreaCode;
	/**
	 * 货区名称
	 */
	private String goodsAreaName;
	
	private List<StockPostionEntity> positionNoList;
	
	
	public List<StockPostionEntity> getPositionNoList() {
		return positionNoList;
	}
	public void setPositionNoList(List<StockPostionEntity> positionNoList) {
		this.positionNoList = positionNoList;
	}
	// 货区号
	// 货区名称
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	public String getGoodsAreaName() {
		return goodsAreaName;
	}
	public void setGoodsAreaName(String goodsAreaName) {
		this.goodsAreaName = goodsAreaName;
	}
	
}
