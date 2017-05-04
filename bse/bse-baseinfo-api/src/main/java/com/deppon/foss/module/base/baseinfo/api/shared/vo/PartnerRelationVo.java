package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PartnerRelationEntity;
/**
 * 
 *  合伙人网点映射的vo
 * @author 308861 
 * @date 2016-8-25 上午8:51:28
 * @since
 * @version
 */
public class PartnerRelationVo {
	//条件查询参数 合伙人网点映射实体
	private PartnerRelationEntity partnerRelationEntity;
	//合伙人网点映射列表
	private List<PartnerRelationEntity> partnerRelationList;
	//idList,批量作废时用的
	private List<String> idList;
	//作废集合
	private List<PartnerRelationEntity> deletePartnerRelationList;
	//一级网点code
	private PartnerRelationOnePartnerNameVo oneVo;
	//二级网点
	private PartnerRelationTwoPartnerNameVo twoVo;
	
	public PartnerRelationEntity getPartnerRelationEntity() {
		return partnerRelationEntity;
	}
	public void setPartnerRelationEntity(PartnerRelationEntity partnerRelationEntity) {
		this.partnerRelationEntity = partnerRelationEntity;
	}
	public List<PartnerRelationEntity> getPartnerRelationList() {
		return partnerRelationList;
	}
	public void setPartnerRelationList(
			List<PartnerRelationEntity> partnerRelationList) {
		this.partnerRelationList = partnerRelationList;
	}
	public List<String> getIdList() {
		return idList;
	}
	public void setIdList(List<String> idList) {
		this.idList = idList;
	}
	public List<PartnerRelationEntity> getDeletePartnerRelationList() {
		return deletePartnerRelationList;
	}
	public void setDeletePartnerRelationList(
			List<PartnerRelationEntity> deletePartnerRelationList) {
		this.deletePartnerRelationList = deletePartnerRelationList;
	}
	public PartnerRelationOnePartnerNameVo getOneVo() {
		return oneVo;
	}
	public void setOneVo(PartnerRelationOnePartnerNameVo oneVo) {
		this.oneVo = oneVo;
	}
	public PartnerRelationTwoPartnerNameVo getTwoVo() {
		return twoVo;
	}
	public void setTwoVo(PartnerRelationTwoPartnerNameVo twoVo) {
		this.twoVo = twoVo;
	}
}
