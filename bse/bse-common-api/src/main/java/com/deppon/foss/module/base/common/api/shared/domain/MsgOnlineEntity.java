package com.deppon.foss.module.base.common.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 在线消息实体类
 * 
 * @author 130346-foss-lifanghong
 * @date 2013-0708
 */
public class MsgOnlineEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3479040640901450993L;
	/**
	 * id
	 */
	private String id;
	/**
	 * 接受方编码
	 * 如果是系统发送则用
	 * 常量类 MessageConstants
	 * 属性 MSG__SYS_USER_CODE
	 */
	private String receiveOrgCode;
	private String receiveOrgName;
	/**
	 * 发送方编码
	 */
	private String sendUserCode;
	/**
	 * 发送方名字
	 */
	private String sendUserName;
	/**
	 * 发送方部门编码
	 * 如果是系统发送则用
	 * 常量类 MessageConstants
	 * 属性 MSG__SYS_ORG_CODE
	 */
	private String sendOrgCode;
	private String sendOrgName;
 
	/**
	 * 接收人员编码 
	 */
	private String receiveUserCode;
	private String receiveUserName;
	/**
	 * 接收方角色编码
	 */
	private String receiveRoleCode;

	/**
	 * 接收方类型；
	 * 消息常量类 MessageConstants
	 * 常量
	 * MSG__RECEIVE_TYPE__ORG  组织 
	 * MSG__RECEIVE_TYPE__ORG_ROLE 组织+角色
	 * 
	 */
	private String receiveType;

	/**
	 * 消息内容
	 */
	private String context;
	/**
	 * 站内消息类型
	 * 普通消息/全网通知
	 * 所需常量 
	 * 数据字典类 DictionaryValueConstants 
	 * 常量
	 * MSG_TYPE__NORMAL 普通消息
	 * MSG_TYPE__ALLNET 全网消息
	 */
	private String msgType;
	
	/**
	 * 创建时间
	 * 
	 */
	private Date createTime;
	
	/**
	 * 有效时间
	 * 全网消息必填
	 */
	private Date expireTime;
	/**
	 * 修改时间
	 * 全网消息必填
	 */
	private Date modifyTime;
	
	/**
	 * 受理状态
	 * 
	 * 所需常量
	 * MessageConstants 
	 * MSG__STATUS__PROCESSING 未处理
	 * MSG__STATUS__AGREE 同意
	 * MSG__STATUS__REFUSE 拒绝
	 */
	private String acceptStatus;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 未读消息数量
	 */
	private int noDealNum;
	
	/**
	 * 单号
	 */
	private String waybillNo;
	/**
	 * 更改人姓名
	 */
	private String modifyUserCode;
	/**
	 * 更改人编号
	 */
	private String modifyUserName;
	/**
	 * 跟踪类别
	 */
	private String traceCategories; 
	/**
	 * 搜索起始时间
	 */
	private Date createStartTime;
	/**
	 * 搜索结束时间
	 */
	private Date createEndTime;
	/**
	 * 是否有效
	 * @return
	 */
	private String Active;
	
	
	public String getActive() {
		return Active;
	}

	public void setActive(String active) {
		Active = active;
	}

	public String getReceiveUserCode() {
		return receiveUserCode;
	}

	public void setReceiveUserCode(String receiveUserCode) {
		this.receiveUserCode = receiveUserCode;
	}

	public String getReceiveUserName() {
		return receiveUserName;
	}

	public void setReceiveUserName(String receiveUserName) {
		this.receiveUserName = receiveUserName;
	}

	public Date getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(Date createStartTime) {
		this.createStartTime = createStartTime;
	}

	public Date getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}

	public String getTraceCategories() {
		return traceCategories;
	}

	public void setTraceCategories(String traceCategories) {
		this.traceCategories = traceCategories;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public int getNoDealNum() {
		return noDealNum;
	}

	public void setNoDealNum(int noDealNum) {
		this.noDealNum = noDealNum;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAcceptStatus() {
		return acceptStatus;
	}

	public void setAcceptStatus(String acceptStatus) {
		this.acceptStatus = acceptStatus;
	}


	public String getSendUserCode() {
		return sendUserCode;
	}

	public void setSendUserCode(String sendUserCode) {
		this.sendUserCode = sendUserCode;
	}

	public String getSendUserName() {
		return sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	
	public String getReceiveRoleCode() {
		return receiveRoleCode;
	}

	
	public void setReceiveRoleCode(String receiveRoleCode) {
		this.receiveRoleCode = receiveRoleCode;
	}

	
	public String getReceiveType() {
		return receiveType;
	}

	
	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	
	public Date getExpireTime() {
		return expireTime;
	}

	
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	
	public String getSendOrgCode() {
		return sendOrgCode;
	}

	public void setSendOrgCode(String sendOrgCode) {
		this.sendOrgCode = sendOrgCode;
	}

	public String getSendOrgName() {
		return sendOrgName;
	}

	public void setSendOrgName(String sendOrgName) {
		this.sendOrgName = sendOrgName;
	}

	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	

}
