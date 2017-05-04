package com.deppon.foss.module.transfer.stock.api.shared.domain;


import com.deppon.foss.framework.entity.BaseEntity;

public class NewAndOldGoodsAreaEntity extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private String id;
	
	private String change_goodsarea_area_id;
	
	/**
	 * 部门code
	 */
	private String org_code;
	/**
	 * 新库区code
	 */
	private String new_goods_area_code;
	/**
	 * 老库区code
	 */
	private String old_goods_area_code;
	
	/**
	* @description 获取id
	* (non-Javadoc)
	* @see com.deppon.foss.framework.entity.BaseEntity#getId()
	* @author 218381-foss-lijie
	* @update 2015年4月8日 上午10:20:52
	* @version V1.0
	*/
	public String getId() {
		return id;
	}
	
	/**
	* @description 设置id
	* (non-Javadoc)
	* @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年4月8日 上午10:21:01
	* @version V1.0
	*/
	public void setId(String id) {
		this.id = id;
	}

	
	/**
	* @description 获取外键id
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月8日 下午2:24:37
	*/
	public String getChange_goodsarea_area_id() {
		return change_goodsarea_area_id;
	}

	
	/**
	* @description 设置外键id
	* @param change_goodsarea_area_id
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月8日 下午2:25:01
	*/
	public void setChange_goodsarea_area_id(String changeGoodsareaAreaId) {
		this.change_goodsarea_area_id = changeGoodsareaAreaId;
	}

	
	/**
	* @description 获取部门code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月8日 下午2:25:10
	*/
	public String getOrg_code() {
		return org_code;
	}

	
	/**
	* @description 设置部门code
	* @param org_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月8日 下午2:25:21
	*/
	public void setOrg_code(String orgCode) {
		this.org_code = orgCode;
	}

	
	/**
	* @description 获取新库区code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月8日 下午2:25:33
	*/
	public String getNew_goods_area_code() {
		return new_goods_area_code;
	}

	
	/**
	* @description 设置新库区code
	* @param new_goods_area_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月8日 下午2:25:46
	*/
	public void setNew_goods_area_code(String newGoodsAreaCode) {
		this.new_goods_area_code = newGoodsAreaCode;
	}

	
	/**
	* @description 获取老库区code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月8日 下午2:25:56
	*/
	public String getOld_goods_area_code() {
		return old_goods_area_code;
	}

	
	/**
	* @description 设置老库区code
	* @param old_goods_area_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年4月8日 下午2:26:08
	*/
	public void setOld_goods_area_code(String oldGoodsAreaCode) {
		this.old_goods_area_code = oldGoodsAreaCode;
	}
	
	
}
