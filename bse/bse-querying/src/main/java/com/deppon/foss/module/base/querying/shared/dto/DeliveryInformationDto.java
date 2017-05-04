package com.deppon.foss.module.base.querying.shared.dto;

import java.util.Date;

/**
 * 提货信息实体 ，对应综合查询 -特殊增值服务信息 字段
 * @author 268984
 *
 */
public class DeliveryInformationDto {
		private String waybillno;
	 
		private Date   acceptTime;   //显示传入时间（家装模块接收到供应商信息的时间）
		 
	   private String providerOrderNo; //供应商订单号
		
	   private String  deliveryName;  // 提货人姓名
	   
	   private String providerName;   //	供应商名称

	   private String providerPhone; //	供应商联系电话
		

	   private Date pickUpTime;  //安装师傅提货时间
				
	   private Date installTime; //	 供应商与客户预约的安装时间
		
	   private  int valid;    //是否有效
	
	private  String   remark ;// 备注

	public Date getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getProviderOrderNo() {
		return providerOrderNo;
	}

	public void setProviderOrderNo(String providerOrderNo) {
		this.providerOrderNo = providerOrderNo;
	}

	public String getDeliveryName() {
		return deliveryName;
	}
	 public String getWaybillno() {
			return waybillno;
		}

		public void setWaybillno(String waybillno) {
			this.waybillno = waybillno;
		}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProviderPhone() {
		return providerPhone;
	}

	public void setProviderPhone(String phone) {
		this.providerPhone = phone;
	}

	public Date getPickUpTime() {
		return pickUpTime;
	}

	public void setPickUpTime(Date pickUpTime) {
		this.pickUpTime = pickUpTime;
	}

	public Date getInstallTime() {
		return installTime;
	}

	public void setInstallTime(Date installTime) {
		this.installTime = installTime;
	}

	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
