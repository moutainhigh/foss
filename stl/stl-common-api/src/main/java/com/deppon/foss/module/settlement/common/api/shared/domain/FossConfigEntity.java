package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * esb地址配置
 * @author 269044
 * @date 2015-11-04 
 */
public class FossConfigEntity implements Serializable  {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID 
	 */
	private String id ;
	
	/**
	 * 接口名称 
	 **/
	private String name;
	
	/**
	 * esb地址 
	 **/
	private String esbAddr;
	
	/**
	 * 服务端编码 
	 */
	private String serverCode;
	
	/**
	 * 客户端编码 
	 */
	private String clientCode;
	
	/**
	 * 接口类型 
	 */
	private String agrmt;
	
	/**
	 * 创建时间 
	 */
	private Date createDate;

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id 
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return esbAddr
	 */
	public String getEsbAddr() {
		return esbAddr;
	}

	/**
	 * @param esbAddr 
	 */
	public void setEsbAddr(String esbAddr) {
		this.esbAddr = esbAddr;
	}

	/**
	 * @return serverCode
	 */
	public String getServerCode() {
		return serverCode;
	}

	/**
	 * @param serverCode 
	 */
	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}

	/**
	 * @return clientCode
	 */
	public String getClientCode() {
		return clientCode;
	}

	/**
	 * @param clientCode 
	 */
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	/**
	 * @return agrmt
	 */
	public String getAgrmt() {
		return agrmt;
	}

	/**
	 * @param agrmt 
	 */
	public void setAgrmt(String agrmt) {
		this.agrmt = agrmt;
	}

	/**
	 * @return createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate 
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
