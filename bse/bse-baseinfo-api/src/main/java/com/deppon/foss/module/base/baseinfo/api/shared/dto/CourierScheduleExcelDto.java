/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CourierScheduleEntity;

/**
 *<p>Title: CourierScheduleExcelDto</p>
 * <p>Description:导入快递排班的dto </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-4-25
 */
public class CourierScheduleExcelDto {
	/**
	 * 快递员排班实体
	 */
	private CourierScheduleEntity scheduleEntity;
	/**
	 * 行号
	 */
	private int rowNum;
	/**
	 * @return the scheduleEntity
	 */
	public CourierScheduleEntity getScheduleEntity() {
		return scheduleEntity;
	}
	/**
	 * @param scheduleEntity the scheduleEntity to set
	 */
	public void setScheduleEntity(CourierScheduleEntity scheduleEntity) {
		this.scheduleEntity = scheduleEntity;
	}
	/**
	 * @return the rowNum
	 */
	public int getRowNum() {
		return rowNum;
	}
	/**
	 * @param rowNum the rowNum to set
	 */
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	} 
	
}
