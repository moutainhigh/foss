package com.deppon.foss.module.transfer.unload.api.shared.vo;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.ForkliftDriverEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ForkliftEfficientEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.TransferFieldEntity;

/**
 *叉车效率查询的数据vo，用于传递前后台参数数据 
 * @author zenghaibin
 * @date 2014 0708 16：20
 ***/
public class ForkliftEfficientVo {
	/**部门编码**/
	private String orgCode;
	/**转运场**/
	private String transfField;
	/**叉车数量信息EntityList**/
	private Date configDate;
	private List<ForkliftEfficientEntity> forkliftEfficientEntityList;
	/**叉车数量信息Entity**/
	private ForkliftEfficientEntity forkliftEfficientEntity;
	/**叉车司机工号**/
	private String forkliftDriverCode;
	/**查询日期**/
	private Date queryDate;
	/**电叉司机数据Entity**/
	private List<ForkliftDriverEntity> forkliftDriverEntityList;
	/**转运场数据信息list**/
	private List<TransferFieldEntity> transferFieldEntityList;
	
	public List<TransferFieldEntity> getTransferFieldEntityList() {
		return transferFieldEntityList;
	}
	public void setTransferFieldEntityList(
			List<TransferFieldEntity> transferFieldEntityList) {
		this.transferFieldEntityList = transferFieldEntityList;
	}
	public List<ForkliftDriverEntity> getForkliftDriverEntityList() {
		return forkliftDriverEntityList;
	}
	public void setForkliftDriverEntityList(
			List<ForkliftDriverEntity> forkliftDriverEntityList) {
		this.forkliftDriverEntityList = forkliftDriverEntityList;
	}
	
	
	public String getForkliftDriverCode() {
		return forkliftDriverCode;
	}
	public void setForkliftDriverCode(String forkliftDriverCode) {
		this.forkliftDriverCode = forkliftDriverCode;
	}
	public Date getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}
	public Date getConfigDate() {
		return configDate;
	}
	public void setConfigDate(Date configDate) {
		this.configDate = configDate;
	}
	public ForkliftEfficientEntity getForkliftEfficientEntity() {
		return forkliftEfficientEntity;
	}
	public void setForkliftEfficientEntity(
			ForkliftEfficientEntity forkliftEfficientEntity) {
		this.forkliftEfficientEntity = forkliftEfficientEntity;
	}
	public String getTransfField() {
		return transfField;
	}
	public void setTransfField(String transfField) {
		this.transfField = transfField;
	}
	
	public List<ForkliftEfficientEntity> getForkliftEfficientEntityList() {
		return forkliftEfficientEntityList;
	}
	public void setForkliftEfficientEntityList(
			List<ForkliftEfficientEntity> forkliftEfficientEntityList) {
		this.forkliftEfficientEntityList = forkliftEfficientEntityList;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	
}
