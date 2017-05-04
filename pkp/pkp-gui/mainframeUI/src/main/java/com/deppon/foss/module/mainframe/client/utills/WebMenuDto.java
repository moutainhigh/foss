/**
 * 
 */
package com.deppon.foss.module.mainframe.client.utills;

import java.io.Serializable;

/**
 * 菜单实体
 * @author 026123-foss-lifengteng
 *
 */
public class WebMenuDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//中文
	private String name;
	
	//类名
	private String className;
	//uri
	private String uri;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}
	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	
}
