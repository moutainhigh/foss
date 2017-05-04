package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressSalesAgentMapEntity;


public class ExpressSalesAgentMapVo implements Serializable{
	/**
	 *  序列化UID
	 */
	private static final long serialVersionUID = -1612160770498654130L;
	/**
	 *  “虚拟营业部快递代理网点”映射关系的实体
	 */
	private ExpressSalesAgentMapEntity expressSalesAgentMapEntity;
	/**
	 *  “虚拟营业部快递代理网点”映射关系的实体集合
	 */
	private List<ExpressSalesAgentMapEntity> expressSalesAgentMapEntityList;
	/**
	 *   id集合，用于批量删除
	 */
	private List<String> ids;
	/**
	 * <p>获取“虚拟营业部快递代理网点”映射关系的实体</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午2:51:19
	 * @return
	 * @see
	 */
	public ExpressSalesAgentMapEntity getExpressSalesAgentMapEntity() {
		return expressSalesAgentMapEntity;
	}
	/**
	 * <p>保存“虚拟营业部快递代理网点”映射关系的实体</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午2:52:07
	 * @param expressSalesAgentMapEntity
	 * @see
	 */
	public void setExpressSalesAgentMapEntity(
			ExpressSalesAgentMapEntity expressSalesAgentMapEntity) {
		this.expressSalesAgentMapEntity = expressSalesAgentMapEntity;
	}
	/**
	 * <p>获取“虚拟营业部快递代理网点”映射关系的实体集合</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午2:52:17
	 * @return
	 * @see
	 */
	public List<ExpressSalesAgentMapEntity> getExpressSalesAgentMapEntityList() {
		return expressSalesAgentMapEntityList;
	}
	/**
	 * <p>保存“虚拟营业部快递代理网点”映射关系的实体集合</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午2:52:36
	 * @param expressSalesAgentMapEntityList
	 * @see
	 */
	public void setExpressSalesAgentMapEntityList(
			List<ExpressSalesAgentMapEntity> expressSalesAgentMapEntityList) {
		this.expressSalesAgentMapEntityList = expressSalesAgentMapEntityList;
	}
	/**
	 * <p>获取id集合</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午2:52:47
	 * @return
	 * @see
	 */
	public List<String> getIds() {
		return ids;
	}
	/**
	 * <p>保存id集合</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午2:53:00
	 * @param ids
	 * @see
	 */
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	
}
