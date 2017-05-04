package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;
import java.util.List;

/**   
 * @ClassName NewPartSalesDeptEntity  
 * @Description TODO   
 * @author  092038 张贞献  
 * @date 2014-9-13    
 */ 
public class NewPartSalesDeptEntity implements Serializable{

	/**  
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 快递员在在点部映射营业部
	 */
	private List<KdPartSalesDeptEntity> kdPartSalesDeptEntitys;
	/**
	 * 快递所在开单部门属性是否能开一票多件
	 */
	private String isOneMany;
	/**
	 *快递运输性质限定中量 临界值
	 */
	private String limitWeight;
	
	/**
	 *  快递外发保价申明价值 获取
	 *  //author:245960 Date:2015-08-22 comment:骆敏霞需求获取  快递保价申明价值上限  1104   快递外发保价申明价值 1105
	 */
	private List<KdInsuredEntity> listKdInsuredEntity;
	/**
	 * PDA-开单一票多件限制优化需求
	 * 重量、体积、件数限制
	 */
	private List<KdOneManyLimitEntity> kdOneManyLimitEntity;
	
	
	
	public List<KdOneManyLimitEntity> getKdOneManyLimitEntity() {
		return kdOneManyLimitEntity;
	}
	public void setKdOneManyLimitEntity(
			List<KdOneManyLimitEntity> kdOneManyLimitEntity) {
		this.kdOneManyLimitEntity = kdOneManyLimitEntity;
	}
	/**
	 * @return  the listKdInsuredEntity
	 */
	public List<KdInsuredEntity> getListKdInsuredEntity() {
		return listKdInsuredEntity;
	}
	/**
	 * @param listKdInsuredEntity the listKdInsuredEntity to set
	 */
	public void setListKdInsuredEntity(List<KdInsuredEntity> listKdInsuredEntity) {
		this.listKdInsuredEntity = listKdInsuredEntity;
	}
	
	public List<KdPartSalesDeptEntity> getKdPartSalesDeptEntitys() {
		return kdPartSalesDeptEntitys;
	}
	public void setKdPartSalesDeptEntitys(
			List<KdPartSalesDeptEntity> kdPartSalesDeptEntitys) {
		this.kdPartSalesDeptEntitys = kdPartSalesDeptEntitys;
	}
	public String getIsOneMany() {
		return isOneMany;
	}
	public void setIsOneMany(String isOneMany) {
		this.isOneMany = isOneMany;
	}
	public String getLimitWeight() {
		return limitWeight;
	}
	public void setLimitWeight(String limitWeight) {
		this.limitWeight = limitWeight;
	}

   
}
