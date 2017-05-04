package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SendDistrictMapEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SendDistrictItemAreaEntity;
/**
 * 
* @ClassName: SendDistrictMapVo 
* @Description: 增加派送货区行政区域映射基础资料 Vo
* @author 189284--张许 
* @date 2014-10-20 下午1:40:06 
*
 */
public class SendDistrictMapVo implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -2924893494005357457L;
    /**
     * 增加派送货区行政区域映射基础资料 实体
     */
	private SendDistrictMapEntity sendDistrictMapEntity;
	/**
     * 增加派送货区行政区域映射基础资料 集合
     */
	private List<SendDistrictMapEntity> sendDistrictMapEntities;
	
	/**
	 * 删除 IDs集合
	 */
	private List<String> deleteids;
	/**
	 * 删除件区ID
	 */
	private List<String> delteItemAreas;
	/**
	 * 派送货区行政区域映射基础资料
	 *   件区实体
	 */
	private SendDistrictItemAreaEntity sendDistrictMapItemAreaEntity;
	/**
	 * 派送货区行政区域映射基础资料
	 *   件区实体 集合
	 */
	private List<SendDistrictItemAreaEntity> sendDistrictItemAreaEntitys;
	/**  
	 * 获取 增加派送货区行政区域映射基础资料 实体  
	 * @return  sendDistrictMap  
	 */
	public SendDistrictMapEntity getSendDistrictMapEntity() {
		return sendDistrictMapEntity;
	}
	/**  
	 * 设置 增加派送货区行政区域映射基础资料 实体  
	 * @param  sendDistrictMap  
	 */
	public void setSendDistrictMapEntity(SendDistrictMapEntity sendDistrictMapEntity) {
		this.sendDistrictMapEntity = sendDistrictMapEntity;
	}
	/**
	 * 获取 派送货区行政区域映射基础资料  集合
	 */
	public List<SendDistrictMapEntity> getSendDistrictMapEntities() {
		return sendDistrictMapEntities;
	}
	/**
	 *设置* 派送货区行政区域映射基础资料集合
	 */
	public void setSendDistrictMapEntities(
			List<SendDistrictMapEntity> sendDistrictMapEntities) {
		this.sendDistrictMapEntities = sendDistrictMapEntities;
	}
	/**
	 * 获取 删除 IDs集合
	 */
	public List<String> getDeleteids() {
		return deleteids;
	}
	/**
	 * 设置 删除 IDs集合
	 */
	public void setDeleteids(List<String> deleteids) {
		this.deleteids = deleteids;
	}
	/** 
	 *获取 * 派送货区行政区域映射基础资料
	 *   件区实体
	 * @return  sendDistrictMapItemAreaEntity  
	 */
	public SendDistrictItemAreaEntity getSendDistrictMapItemAreaEntity() {
		return sendDistrictMapItemAreaEntity;
	}
	/**
	 *设置 派送货区行政区域映射基础资料
	 *   件区实体
	 *setSendDistrictMapItemAreaEntity
	 * @param sendDistrictMapItemAreaEntity the sendDistrictMapItemAreaEntity to set
	 * @return the sendDistrictMapItemAreaEntity
	 */
	public void setSendDistrictMapItemAreaEntity(
			SendDistrictItemAreaEntity sendDistrictMapItemAreaEntity) {
		this.sendDistrictMapItemAreaEntity = sendDistrictMapItemAreaEntity;
	}
	/** 
	 *获取派送货区行政区域映射基础资料
	 *   件区实体 集合
	 * @return  sendDistrictMapItemAreaEntitys  
	 */
	public List<SendDistrictItemAreaEntity> getSendDistrictItemAreaEntitys() {
		return sendDistrictItemAreaEntitys;
	}
	/**
	 *设置派送货区行政区域映射基础资料
	 *   件区实体 集合
	 *setSendDistrictMapItemAreaEntitys
	 * @param sendDistrictMapItemAreaEntitys the sendDistrictMapItemAreaEntitys to set
	 * @return the sendDistrictMapItemAreaEntitys
	 */
	public void setSendDistrictItemAreaEntitys(
			List<SendDistrictItemAreaEntity> sendDistrictItemAreaEntitys) {
		this.sendDistrictItemAreaEntitys = sendDistrictItemAreaEntitys;
	}
	/** 
	 *获取 删除件区ID
	 * @return  delteItemAreas  
	 */
	public List<String> getDelteItemAreas() {
		return delteItemAreas;
	}
	/**
	 *设置 删除件区ID
	 *setDelteItemAreas
	 * @param delteItemAreas the delteItemAreas to set
	 * @return the delteItemAreas
	 */
	public void setDelteItemAreas(List<String> delteItemAreas) {
		this.delteItemAreas = delteItemAreas;
	}
	
}
