package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderTaskEntity;
/**
 * 查询点单任务
 * @author 272681-foss-chenlei
 * @date 2015-12-25 
 */
public class OrderTaskDto extends OrderTaskEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**点单差异编号**/
	private String reportNo;

	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

		

}
