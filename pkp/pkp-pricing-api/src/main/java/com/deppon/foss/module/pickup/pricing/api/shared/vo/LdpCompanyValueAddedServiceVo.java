package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussValueAddEntity;

/**
 * 快递代理公司增值服务Vo
 * 
 * @author WangPeng
 * @date   2013-08-14 10:34 PM
 *
 */
public class LdpCompanyValueAddedServiceVo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3184632862837932856L;
	
	//快递代理公司增值服务对象
	PartbussValueAddEntity partbussValueAddEntity;
	//快递代理公司增值服务集合
	List<PartbussValueAddEntity> partbussValueAddEntityList;
	
	// 返回前台的INT类型属性
	private int returnInt;
	
	//当前服务器时间
	private Date nowTime;
	
	private String[] ids;
	
	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public PartbussValueAddEntity getPartbussValueAddEntity() {
		return partbussValueAddEntity;
	}

	public void setPartbussValueAddEntity(
			PartbussValueAddEntity partbussValueAddEntity) {
		this.partbussValueAddEntity = partbussValueAddEntity;
	}

	public List<PartbussValueAddEntity> getPartbussValueAddEntityList() {
		return partbussValueAddEntityList;
	}

	public void setPartbussValueAddEntityList(
			List<PartbussValueAddEntity> partbussValueAddEntityList) {
		this.partbussValueAddEntityList = partbussValueAddEntityList;
	}


	/**
	 * @return the returnInt
	 */
	public int getReturnInt() {
		return returnInt;
	}

	/**
	 * @param returnInt the returnInt to set
	 */
	public void setReturnInt(int returnInt) {
		this.returnInt = returnInt;
	}

	/**
	 * @return the nowTime
	 */
	public Date getNowTime() {
		return nowTime;
	}

	/**
	 * @param nowTime the nowTime to set
	 */
	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}
	
	

}
