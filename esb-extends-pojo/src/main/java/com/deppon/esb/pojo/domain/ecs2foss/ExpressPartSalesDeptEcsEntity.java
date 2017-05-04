package com.deppon.esb.pojo.domain.ecs2foss;

import java.io.Serializable;
import java.util.Date;


/**
 * 快递点部营业部映射关系
 * @author foss-qiaolifeng
 * @date 2013-7-24 下午6:20:13
 */
public class ExpressPartSalesDeptEcsEntity implements Serializable {
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 445934543175538535L;
	private String id;// ID
	private Date createDate;// 创建日期
	private String createUser;// 创建人
	private Date modifyDate;// 修改日期
	private String modifyUser;// 修改人
	private String ecsVersionNo;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	
	public String getEcsVersionNo() {
		return ecsVersionNo;
	}

	public void setEcsVersionNo(String ecsVersionNo) {
		this.ecsVersionNo = ecsVersionNo;
	}


	/**
	 * 快递点部编码
	 */
	private String partCode;
	
   /**
    * 快递点部名称
    */

	private String partName;
	

	/**
	 * 快递点部名称
	 */
	private String expressPartName;
	
	/**
	 * 快递点部编码
	 */
	private String expressPartCode;
	/**
	 * 营业部编码
	 */
	private String salesDeptCode;
	/**
	 * 营业部名称
	 */
	private String salesDeptName;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/**
	 * 创建人编码
	 */
	private String createUserCode;
	
	/**
	 * 创建人编码
	 */
	private String createUserName;
	
	/**
	 * 修改人编码
	 */
	private String modifyUserCode;
	
	/**
	 * 创建人编码
	 */
	private String modifyUserName;
	
	/**
	 * 是否有效
	 */
	private String active;
	
	/**
	 * 版本号
	 */
	private Long versionNo;
	
	/**
	 * 生效日期
	 */
	private Date beginTime;
	
	/**
	 * 失效日期
	 */
	private Date endTime;
    /**
     * 操作类型
     */
	private int changeType;
	
	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public String getSalesDeptCode() {
		return salesDeptCode;
	}

	public void setSalesDeptCode(String salesDeptCode) {
		this.salesDeptCode = salesDeptCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public int getChangeType() {
		return changeType;
	}

	public void setChangeType(int changeType) {
		this.changeType = changeType;
	}


	public String getExpressPartName() {
		return expressPartName;
	}

	public void setExpressPartName(String expressPartName) {
		this.expressPartName = expressPartName;
	}

	public String getExpressPartCode() {
		return expressPartCode;
	}

	public void setExpressPartCode(String expressPartCode) {
		this.expressPartCode = expressPartCode;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getSalesDeptName() {
		return salesDeptName;
	}

	public void setSalesDeptName(String salesDeptName) {
		this.salesDeptName = salesDeptName;
	}


}
