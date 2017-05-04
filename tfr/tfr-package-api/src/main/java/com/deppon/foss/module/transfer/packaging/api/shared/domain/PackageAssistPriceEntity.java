package com.deppon.foss.module.transfer.packaging.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

/*
 * @desc   辅助包装金额
 * @author foss-105795-wqh
 * @date   2014-04-29
 * */

public class PackageAssistPriceEntity extends PackageMainPriceEntity {
	
	private static final long serialVersionUID = 6465238623659259911L;
	//木条长度
	private BigDecimal woodenBarLong;
	//气泡膜体积
	private BigDecimal bubbVelamenVolume;
	//缠绕膜体积
	private BigDecimal bindVelamenVolume;
	//包带根数
	private int bagBeltNum;
	//是否激活
	private String active;
	//审核人姓名
	private String auditorName;
	//审核人code
	private String auditorCode;
	//审核日期
	private Date auditDate;
	//反审核人姓名
	private String deauditorName;
	//反审核人code
	private String deauditorCode;
	//反审核日期
	private Date deauditDate;
	
	//set and get
	public BigDecimal getWoodenBarLong() {
		return woodenBarLong;
	}
	public void setWoodenBarLong(BigDecimal woodenBarLong) {
		this.woodenBarLong = woodenBarLong;
	}
	public BigDecimal getBubbVelamenVolume() {
		return bubbVelamenVolume;
	}
	public void setBubbVelamenVolume(BigDecimal bubbVelamenVolume) {
		this.bubbVelamenVolume = bubbVelamenVolume;
	}
	public BigDecimal getBindVelamenVolume() {
		return bindVelamenVolume;
	}
	public void setBindVelamenVolume(BigDecimal bindVelamenVolume) {
		this.bindVelamenVolume = bindVelamenVolume;
	}
	public int getBagBeltNum() {
		return bagBeltNum;
	}
	public void setBagBeltNum(int bagBeltNum) {
		this.bagBeltNum = bagBeltNum;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getAuditorName() {
		return auditorName;
	}
	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}
	public String getAuditorCode() {
		return auditorCode;
	}
	public void setAuditorCode(String auditorCode) {
		this.auditorCode = auditorCode;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	public String getDeauditorName() {
		return deauditorName;
	}
	public void setDeauditorName(String deauditorName) {
		this.deauditorName = deauditorName;
	}
	public String getDeauditorCode() {
		return deauditorCode;
	}
	public void setDeauditorCode(String deauditorCode) {
		this.deauditorCode = deauditorCode;
	}
	public Date getDeauditDate() {
		return deauditDate;
	}
	public void setDeauditDate(Date deauditDate) {
		this.deauditDate = deauditDate;
	}
	/**
	 * 
	     *
		 * @desc 重写equals,以下属性相等则两个实体相等  
		 * 实际打木架/实际打木箱/实际打木托/木条长度/气泡膜体积/缠绕膜体积/包带根数
		 * @author 042795
		 * @date 2014-6-27 下午12:33:38
		 * @see equals
	 */
	public boolean equals(Object o){
		if(!(o instanceof PackageAssistPriceEntity)){
			return false; 
		}
		else{
			PackageAssistPriceEntity p = (PackageAssistPriceEntity)o;
			System.out.println( p.getActualFrameVolume().equals(this.getActualFrameVolume()));
			if((p.getActualFrameVolume() == null && this.getActualFrameVolume() != null) 
				||(p.getActualWoodenVolume() == null && this.getActualWoodenVolume() != null) 
				||(p.getActualMaskNumber() == null && this.getActualMaskNumber() != null)
				||(p.getWoodenBarLong() == null && this.getWoodenBarLong() != null)
				||(p.getBubbVelamenVolume() == null && this.getBubbVelamenVolume() != null)
				||(p.getBindVelamenVolume() == null && this.getBindVelamenVolume() != null)){
				return false;
			}else{
				return   p.getActualFrameVolume().equals(this.getActualFrameVolume())
						 &&p.getActualWoodenVolume().equals(this.getActualWoodenVolume())
						 &&p.getActualMaskNumber().equals(this.getActualMaskNumber())
						 &&p.getWoodenBarLong().equals(this.getWoodenBarLong())
						 &&p.getBubbVelamenVolume().equals(this.getBubbVelamenVolume())
						 &&p.getBindVelamenVolume().equals(this.getBindVelamenVolume())
						 &&p.getBagBeltNum() == this.getBagBeltNum();
			}
		}
	}
}
