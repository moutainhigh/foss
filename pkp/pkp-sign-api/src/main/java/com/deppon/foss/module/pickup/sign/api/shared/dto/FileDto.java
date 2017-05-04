/**
 *  initial comments.
 */
/**
 * 上传Dto
 */
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;

/***
 * 上传Dto
 * @author foss-meiying
 * @date 2012-11-15 下午4:19:30
 * @since
 * @version
 */
public class FileDto implements Serializable{
	/**
	 * new file status
	 */
	public static final String ADD="add";

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * id
	 */
	private String id;
	
	/**
	 * 文件名
	 */
	private String name;
	
	/**
	 * 文件类型 用后坠如.txt
	 */
	private String type;
	
	/**
	 * 文件大小
	 */
	private String size;
	
	
	/**
	 * 相对路径
	 */
	private String relativePath;
	
	
	/**
	 * 状态
	 */
	private String status;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * @return the relativePath
	 */
	public String getRelativePath() {
		return relativePath;
	}

	/**
	 * @param relativePath the relativePath to set
	 */
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}