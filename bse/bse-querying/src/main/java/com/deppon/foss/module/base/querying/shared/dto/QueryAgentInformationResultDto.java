package com.deppon.foss.module.base.querying.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 代理信息
 * 
 * @author WeiXing
 * @date 2014-11-14 下午2:19:29
 */
public class QueryAgentInformationResultDto implements Serializable {

	private static final long serialVersionUID = 1L;
	//跟踪内容
	private String traceContent;
	 //跟踪人
    private String operatorName;
    //跟踪时间
    private Date operatTime;
    /**
     * @author 218392 zhangyongxue
     * @return 2015-05-06 15:47:50
     */
    //流水号
    private String serialNo;
    //状态
    private String status;
    //外发部门负责部门
    private String orgName;
    //联系电话
    private String depTelephone;
    
    
	public String getDepTelephone() {
		return depTelephone;
	}
	public void setDepTelephone(String depTelephone) {
		this.depTelephone = depTelephone;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTraceContent() {
		return traceContent;
	}
	public void setTraceContent(String traceContent) {
		this.traceContent = traceContent;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public Date getOperatTime() {
		return operatTime;
	}
	public void setOperatTime(Date operatTime) {
		this.operatTime = operatTime;
	}
    
}
