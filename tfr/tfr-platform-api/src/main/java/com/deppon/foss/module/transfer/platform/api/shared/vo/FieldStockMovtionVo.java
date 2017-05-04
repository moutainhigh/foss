/**
 * 
 */
package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.platform.api.shared.domain.FieldStockMovtionDetailEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.FieldStockMovtionEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QueryConditionStockMovtionDto;

/**
 * @author 105795
 *
 */
public class FieldStockMovtionVo implements java.io.Serializable{

	private static final long serialVersionUID = -3270853828427159489L;
	
    //库存库区流动总体情况
	private FieldStockMovtionEntity fieldStockMovtionEntity=new FieldStockMovtionEntity();
	
	private List<FieldStockMovtionEntity> fieldStockMovtionList=new ArrayList<FieldStockMovtionEntity>();
	
	//库存库区流动明细
	private List<FieldStockMovtionDetailEntity> fieldStockMovtionDetailList=new ArrayList<FieldStockMovtionDetailEntity>();
	
	//库存库区查询条件
	private QueryConditionStockMovtionDto queryConditionStockMovtionDto=new QueryConditionStockMovtionDto();
	
	/** 产品List*/
	private List<BaseDataDictDto> productList=new ArrayList<BaseDataDictDto>();
	

	/**
	 * @return the fieldStockMovtionEntity
	 */
	public FieldStockMovtionEntity getFieldStockMovtionEntity() {
		return fieldStockMovtionEntity;
	}

	/**
	 * @param fieldStockMovtionEntity the fieldStockMovtionEntity to set
	 */
	public void setFieldStockMovtionEntity(
			FieldStockMovtionEntity fieldStockMovtionEntity) {
		this.fieldStockMovtionEntity = fieldStockMovtionEntity;
	}

	
	

	/**
	 * @return the fieldStockMovtionDetailList
	 */
	public List<FieldStockMovtionDetailEntity> getFieldStockMovtionDetailList() {
		return fieldStockMovtionDetailList;
	}

	/**
	 * @param fieldStockMovtionDetailList the fieldStockMovtionDetailList to set
	 */
	public void setFieldStockMovtionDetailList(
			List<FieldStockMovtionDetailEntity> fieldStockMovtionDetailList) {
		this.fieldStockMovtionDetailList = fieldStockMovtionDetailList;
	}

	/**
	 * @return the queryConditionStockMovtionDto
	 */
	public QueryConditionStockMovtionDto getQueryConditionStockMovtionDto() {
		return queryConditionStockMovtionDto;
	}

	/**
	 * @param queryConditionStockMovtionDto the queryConditionStockMovtionDto to set
	 */
	public void setQueryConditionStockMovtionDto(
			QueryConditionStockMovtionDto queryConditionStockMovtionDto) {
		this.queryConditionStockMovtionDto = queryConditionStockMovtionDto;
	}

	/**
	 * @return the fieldStockMovtionList
	 */
	public List<FieldStockMovtionEntity> getFieldStockMovtionList() {
		return fieldStockMovtionList;
	}

	/**
	 * @param fieldStockMovtionList the fieldStockMovtionList to set
	 */
	public void setFieldStockMovtionList(
			List<FieldStockMovtionEntity> fieldStockMovtionList) {
		this.fieldStockMovtionList = fieldStockMovtionList;
	}

	/**
	 * @return the productList
	 */
	public List<BaseDataDictDto> getProductList() {
		return productList;
	}

	/**
	 * @param productList the productList to set
	 */
	public void setProductList(List<BaseDataDictDto> productList) {
		this.productList = productList;
	}
	
	
    
	
	
}
