package com.deppon.foss.module.pickup.waybill.shared.domain;
/**
 * 阿里巴巴快递自动补录对接信息封装类
 * @author:218371-foss-zhaoyanjun
 * @date:2015-05-14上午10:24
 */
import java.io.Serializable;

/**
 * @author 087584-foss-Zhaoyanjun
 *
 */
public class TimeLookDto implements Serializable{
	private static final long serialVersionUID = 7650000987654327597L;
	
	// 运单号
	private String waybillNo;
	//JobId
	private String jobId;
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
}

