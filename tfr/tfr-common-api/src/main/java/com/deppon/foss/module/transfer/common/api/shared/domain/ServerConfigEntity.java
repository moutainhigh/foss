/**  
 * Project Name:tfr-common-api  
 * File Name:ServerConfigEntity.java  
 * Package Name:com.deppon.foss.module.transfer.common.api.shared.domain  
 * Date:2015年6月10日下午8:38:15  
 *  
 */
package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**  
 * ClassName: ServerConfigEntity <br/>  
 * Function: 服务端配置实体类. <br/>  
 * date: 2015年6月10日 下午8:38:15 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public class ServerConfigEntity implements Serializable {

	/**  
	 * serialVersionUID.  
	 * @since JDK 1.6  
	 */
	private static final long serialVersionUID = -165461153534165315L;
	
	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 模块名
	 */
	private String module;
	
	/**
	 * 编码
	 */
	private String code;
	
	/**
	 * 值
	 */
	private String value;
	
	/**
	 * 配置项描述
	 */
	private String description;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**  
	 * id.  
	 *  
	 * @return  the id  
	 * @since   JDK 1.6  
	 */
	public String getId() {
		return id;
	}

	/**  
	 * id.  
	 *  
	 * @param   id    the id to set  
	 * @since   JDK 1.6  
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**  
	 * module.  
	 *  
	 * @return  the module  
	 * @since   JDK 1.6  
	 */
	public String getModule() {
		return module;
	}

	/**  
	 * module.  
	 *  
	 * @param   module    the module to set  
	 * @since   JDK 1.6  
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**  
	 * code.  
	 *  
	 * @return  the code  
	 * @since   JDK 1.6  
	 */
	public String getCode() {
		return code;
	}

	/**  
	 * code.  
	 *  
	 * @param   code    the code to set  
	 * @since   JDK 1.6  
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**  
	 * value.  
	 *  
	 * @return  the value  
	 * @since   JDK 1.6  
	 */
	public String getValue() {
		return value;
	}

	/**  
	 * value.  
	 *  
	 * @param   value    the value to set  
	 * @since   JDK 1.6  
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**  
	 * description.  
	 *  
	 * @return  the description  
	 * @since   JDK 1.6  
	 */
	public String getDescription() {
		return description;
	}

	/**  
	 * description.  
	 *  
	 * @param   description    the description to set  
	 * @since   JDK 1.6  
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**  
	 * createTime.  
	 *  
	 * @return  the createTime  
	 * @since   JDK 1.6  
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**  
	 * createTime.  
	 *  
	 * @param   createTime    the createTime to set  
	 * @since   JDK 1.6  
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**  
	 * modifyTime.  
	 *  
	 * @return  the modifyTime  
	 * @since   JDK 1.6  
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**  
	 * modifyTime.  
	 *  
	 * @param   modifyTime    the modifyTime to set  
	 * @since   JDK 1.6  
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	

}
