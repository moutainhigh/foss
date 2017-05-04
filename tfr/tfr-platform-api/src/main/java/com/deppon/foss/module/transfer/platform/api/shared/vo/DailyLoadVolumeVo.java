/**   
* @Title: DailyLoadVolumeVo.java 
* @Package com.deppon.foss.module.transfer.platform.api.shared.vo 
* @Description: 日承载货量vo
* @author shiwei-045923-shiwei@outlook.com
* @date 2014年6月26日 下午4:51:13 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.DailyLoadVolumeEntity;

/** 
 * @ClassName: DailyLoadVolumeVo 
 * @Description: 日承载货量vo
 * @author shiwei-045923-shiwei@outlook.com
 * @date 2014年6月26日 下午4:51:13 
 *  
 */
public class DailyLoadVolumeVo {
	
	/**
	 * 用于新增、修改以及分页查询、单个查询历史记录时传递条件
	 */
	private DailyLoadVolumeEntity dailyLoadVolumeEntity;
	
	/**
	 * 分页查询、查询历史记录时用来传递查询结果
	 */
	private List<DailyLoadVolumeEntity> dailyLoadVolumeEntityList;

	/** 
	 * @return dailyLoadVolumeEntity 
	 */
	public DailyLoadVolumeEntity getDailyLoadVolumeEntity() {
		return dailyLoadVolumeEntity;
	}

	/** 
	 * @param dailyLoadVolumeEntity 要设置的 dailyLoadVolumeEntity 
	 */
	public void setDailyLoadVolumeEntity(DailyLoadVolumeEntity dailyLoadVolumeEntity) {
		this.dailyLoadVolumeEntity = dailyLoadVolumeEntity;
	}

	/** 
	 * @return dailyLoadVolumeEntityList 
	 */
	public List<DailyLoadVolumeEntity> getDailyLoadVolumeEntityList() {
		return dailyLoadVolumeEntityList;
	}

	/** 
	 * @param dailyLoadVolumeEntityList 要设置的 dailyLoadVolumeEntityList 
	 */
	public void setDailyLoadVolumeEntityList(
			List<DailyLoadVolumeEntity> dailyLoadVolumeEntityList) {
		this.dailyLoadVolumeEntityList = dailyLoadVolumeEntityList;
	}

}
