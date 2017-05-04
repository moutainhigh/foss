package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionAreaPlanEntity;
/**
 * 展馆区域规划Vo
 * @author 187862
 * @date 2015-7-7 上午8:50:16
 *
 */
public class ExhibitionAreaPlanVo implements Serializable{
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1320746273491763680L;
	/**
	 * 展馆区域规划实体
	 */
	private ExhibitionAreaPlanEntity exhibitionAreaPlanEntity;
	
	/**
	 * 展馆区域规划实体List
	 */
	private List<ExhibitionAreaPlanEntity> areaPlanEntityList;
	
	/**
	 * 查询实体code字段[支持批量删除]
	 */
	private String[] codes;
	
	/**
	 * 展馆区域规划特殊角色参数配置
	 */
	private String confValue;
	
	/**
	 * 下面是get,set方法
	 */
	public ExhibitionAreaPlanEntity getExhibitionAreaPlanEntity() {
		return exhibitionAreaPlanEntity;
	}

	public void setExhibitionAreaPlanEntity(
			ExhibitionAreaPlanEntity exhibitionAreaPlanEntity) {
		this.exhibitionAreaPlanEntity = exhibitionAreaPlanEntity;
	}

	public List<ExhibitionAreaPlanEntity> getAreaPlanEntityList() {
		return areaPlanEntityList;
	}

	public void setAreaPlanEntityList(
			List<ExhibitionAreaPlanEntity> areaPlanEntityList) {
		this.areaPlanEntityList = areaPlanEntityList;
	}

	public String[] getCodes() {
		return codes;
	}

	public void setCodes(String[] codes) {
		this.codes = codes;
	}

	public String getConfValue() {
		return confValue;
	}

	public void setConfValue(String confValue) {
		this.confValue = confValue;
	}

}
