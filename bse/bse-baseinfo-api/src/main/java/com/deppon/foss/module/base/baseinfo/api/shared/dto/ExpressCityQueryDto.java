package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressCityEntity;

/**
 * 试点城市查询Dto
 * @author foss-qiaolifeng
 * @date 2013-7-16 下午2:39:51
 */
public class ExpressCityQueryDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * id
	 */
	private String id;
	
	/**
	 * 城市编码
	 */
	private String districtCode;
	
	/**
	 * 城市类别
	 */
	private String type;
	
	/**
	 * 是否有效
	 */
	private String active;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/**
	 * 创建人
	 */
	private String createUserCode;
	
	/**
	 * 修改人
	 */
	private String modifyUserCode;
	
	/**
	 * 版本号
	 */
	private Long versionNo;
		
	/**
	 * 单数据参数
	 */
	private ExpressCityEntity expressCityEntity;
	
	/**
	 * 列表参数
	 */
	private List<ExpressCityEntity> newExpressCityEntityList = new ArrayList<ExpressCityEntity>();
	
	/**
	 * 选择数据的ID
	 */
	private String selectedIds;
	
	/**
	 * ID集合
	 */
	private List<String> idList;
	
	
	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	public String getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ExpressCityEntity getExpressCityEntity() {
		return expressCityEntity;
	}

	public void setExpressCityEntity(ExpressCityEntity expressCityEntity) {
		this.expressCityEntity = expressCityEntity;
	}

	public List<ExpressCityEntity> getNewExpressCityEntityList() {
		return newExpressCityEntityList;
	}

	public void setNewExpressCityEntityList(
			List<ExpressCityEntity> newExpressCityEntityList) {
		this.newExpressCityEntityList = newExpressCityEntityList;
	}
	
	
}
