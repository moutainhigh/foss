package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

public class ExpressSalesAgentMapEntity extends BaseEntity{
	/**
	 *  序列化UID
	 */
	private static final long serialVersionUID = 4835168027345702775L;
	/**
	 *  虚拟营业部编码
	 */
	private String  expressSalesDeptCode;
	/**
	 *  虚拟营业部名称
	 */
	private String  expressSalesDeptName;
	/**
	 *  快递代理网点编码
	 */
	private String  expressAgentDeptCode;
	/**
	 *  快递代理网点名称
	 */
	private String  expressAgentDeptName;
	/**
	 *  状态（是否启用）
	 */
	private String  active;
	/**
	 * <p>获取虚拟营业部编码</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午2:37:48
	 * @return
	 * @see
	 */
	public String getExpressSalesDeptCode() {
		return expressSalesDeptCode;
	}
	/**
	 * <p>保存虚拟营业部编码</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午2:38:28
	 * @param expressSalesDeptCode
	 * @see
	 */
	public void setExpressSalesDeptCode(String expressSalesDeptCode) {
		this.expressSalesDeptCode = expressSalesDeptCode;
	}
	/**
	 * <p>获取虚拟营业部名称</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午2:38:48
	 * @return
	 * @see
	 */
	public String getExpressSalesDeptName() {
		return expressSalesDeptName;
	}
	/**
	 * <p>保存虚拟营业部名称</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午2:39:21
	 * @param expressSalesDeptName
	 * @see
	 */
	public void setExpressSalesDeptName(String expressSalesDeptName) {
		this.expressSalesDeptName = expressSalesDeptName;
	}
	/**
	 * <p>获取快递代理网点编码</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午2:40:16
	 * @return
	 * @see
	 */
	public String getExpressAgentDeptCode() {
		return expressAgentDeptCode;
	}
	/**
	 * <p>保存快递代理网点编码</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午2:40:35
	 * @param expressAgentDeptCode
	 * @see
	 */
	public void setExpressAgentDeptCode(String expressAgentDeptCode) {
		this.expressAgentDeptCode = expressAgentDeptCode;
	}
	/**
	 * <p>获取快递代理网点名称</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午2:40:47
	 * @return
	 * @see
	 */
	public String getExpressAgentDeptName() {
		return expressAgentDeptName;
	}
	/**
	 * <p>保存快递代理网点名称</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午2:41:00
	 * @param expressAgentDeptName
	 * @see
	 */
	public void setExpressAgentDeptName(String expressAgentDeptName) {
		this.expressAgentDeptName = expressAgentDeptName;
	}
	/**
	 * <p>获取是否启用的状态</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午2:41:15
	 * @return
	 * @see
	 */
	public String getActive() {
		return active;
	}
	/**
	 * <p>保存是否启用的状态</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午2:41:33
	 * @param active
	 * @see
	 */
	public void setActive(String active) {
		this.active = active;
	}
	
}
