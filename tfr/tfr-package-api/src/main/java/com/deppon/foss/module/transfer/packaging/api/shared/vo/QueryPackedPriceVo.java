package com.deppon.foss.module.transfer.packaging.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackageAssistPriceEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackageMainPriceEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPDAPackConditionEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPDAPackResultEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPdaPcPackConditionEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPdaPcPackResultEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.QueryAssistPackedDto;

public class QueryPackedPriceVo implements java.io.Serializable {
	private static final long serialVersionUID = 360328874828601421L;

	//辅助包装金额
	private List<PackageAssistPriceEntity> packageAssistPriceEntityList;
	
	private PackageAssistPriceEntity packageAssistPriceEntity;
	
	//主要包装金额
	private List<PackageMainPriceEntity> packageMainPriceEntityList;
	
	//查询辅助包装金额dto
	private QueryAssistPackedDto queryAssistPackedDto=new QueryAssistPackedDto();
	/**
	 * PDA端扫描生成包装金额查询条
	 */
	private QueryPDAPackConditionEntity pdaQueryPackConditionEntity;
	/**
	 * PDA端扫描生成包装金额查询结果
	 */
	private List<QueryPDAPackResultEntity> pdaQueryPackResultList ;
    /**
     * 包装金额汇总（PDA与PC）查询条件
     */
	private QueryPdaPcPackConditionEntity  queryPdaPcPackConditionEntity;
	/**
     * 包装金额汇总（PDA与PC）查询结果
     */
	private List<QueryPdaPcPackResultEntity> queryPdaPcPackResultList;
	private List<String> waybillNos;
	//辅助包装金额导入条数
	private int importTotalCount;

	private String id;

	/**
	 * 审核类型
	 * */
	private String auditType;
	
	/**
	 * id 用于审核（反审核）时传递给后台的
	 * */
	private List<String> ids;
	
	
	
	
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	public String getAuditType() {
		return auditType;
	}
	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// set and get	
	public List<PackageAssistPriceEntity> getPackageAssistPriceEntityList() {
		return packageAssistPriceEntityList;
	}

	/**
	 * 获取包装金额汇总（PDA与PC）查询条件 queryPdaPcPackConditionEntity
	 * @return  queryPdaPcPackConditionEntity
	 */
	public QueryPdaPcPackConditionEntity getQueryPdaPcPackConditionEntity() {
		return queryPdaPcPackConditionEntity;
	}

	/**
	 *设置 包装金额汇总（PDA与PC）查询条件queryPdaPcPackConditionEntity
	 */
	public void setQueryPdaPcPackConditionEntity(
			QueryPdaPcPackConditionEntity queryPdaPcPackConditionEntity) {
		this.queryPdaPcPackConditionEntity = queryPdaPcPackConditionEntity;
	}

	/**
	 * 获取包装金额汇总（PDA与PC）查询结果queryPdaPcPackResultList
	 * @return  queryPdaPcPackResultList
	 */
	public List<QueryPdaPcPackResultEntity> getQueryPdaPcPackResultList() {
		return queryPdaPcPackResultList;
	}

	/**
	 *设置包装金额汇总（PDA与PC）查询结果queryPdaPcPackResultList
	 */
	public void setQueryPdaPcPackResultList(
			List<QueryPdaPcPackResultEntity> queryPdaPcPackResultList) {
		this.queryPdaPcPackResultList = queryPdaPcPackResultList;
	}

	public void setPackageAssistPriceEntityList(
			List<PackageAssistPriceEntity> packageAssistPriceEntityList) {
		this.packageAssistPriceEntityList = packageAssistPriceEntityList;
	}

	public List<PackageMainPriceEntity> getPackageMainPriceEntityList() {
		return packageMainPriceEntityList;
	}

	public void setPackageMainPriceEntityList(
			List<PackageMainPriceEntity> packageMainPriceEntityList) {
		this.packageMainPriceEntityList = packageMainPriceEntityList;
	}

	public QueryAssistPackedDto getQueryAssistPackedDto() {
		return queryAssistPackedDto;
	}

	public void setQueryAssistPackedDto(QueryAssistPackedDto queryAssistPackedDto) {
		this.queryAssistPackedDto = queryAssistPackedDto;
	}

	/**
	 * 获取pdaQueryPackConditionEntity
	 * @return  pdaQueryPackConditionEntity
	 */
	public QueryPDAPackConditionEntity getPdaQueryPackConditionEntity() {
		return pdaQueryPackConditionEntity;
	}

	/**
	 *设置pdaQueryPackConditionEntity
	 */
	public void setPdaQueryPackConditionEntity(
			QueryPDAPackConditionEntity pdaQueryPackConditionEntity) {
		this.pdaQueryPackConditionEntity = pdaQueryPackConditionEntity;
	}


	public PackageAssistPriceEntity getPackageAssistPriceEntity() {
		return packageAssistPriceEntity;
	}

	public void setPackageAssistPriceEntity(
			PackageAssistPriceEntity packageAssistPriceEntity) {
		this.packageAssistPriceEntity = packageAssistPriceEntity;
	}

	public int getImportTotalCount() {
		return importTotalCount;
	}

	public void setImportTotalCount(int importTotalCount) {
		this.importTotalCount = importTotalCount;
	}
	
	/**
	 * 获取pdaQueryPackResultList
	 * @return  pdaQueryPackResultList
	 */
	public List<QueryPDAPackResultEntity> getPdaQueryPackResultList() {
		return pdaQueryPackResultList;
	}

	/**
	 *设置pdaQueryPackResultList
	 */
	public void setPdaQueryPackResultList(
			List<QueryPDAPackResultEntity> pdaQueryPackResultList) {
		this.pdaQueryPackResultList = pdaQueryPackResultList;
	}

	public List<String> getWaybillNos() {
		return waybillNos;
	}

	public void setWaybillNos(List<String> waybillNos) {
		this.waybillNos = waybillNos;
	}
	
	
	

}
