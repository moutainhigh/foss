package com.deppon.foss.module.transfer.lostwarning.api.shared.dto;

/**
 * 丢失预警数据中间表
 * 
 * 项目名称：tfr-lostwarning-api
 * 
 * 类名称：LostWarningTempDto
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-6-9 下午3:13:29
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class LostWarningTempDto{
	//运单编号
	private String wayBillNo;
	
	/***
	 * 上报方式编码
		1、出发库存丢货上报
		2、集中接货丢货上报
		3、运输丢货上报
		4、已到达丢货上报
		5、卸车丢货上报
		6、中转库存丢货上报
		7、已交接丢货上报
		8、到达库存丢货上报
		9、派送丢货上报
		10、空运丢货上报 
		11、快递外发丢货上报
	 *注：该属性为自定义信息, 由于零担上报方式中包含快递的上报方式，因此这里存取数据不做区分，待推送数据时按照需求文档中要求进行零担和快递上报方式编码细分
	 */
	private String repType;
	
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public String getRepType() {
		return repType;
	}
	public void setRepType(String repType) {
		this.repType = repType;
	}
	
}	
