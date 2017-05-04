package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;

import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;


/**
* @description foss异步通知快递系统，任务单元
* @version 1.0
* @author 283250-foss-liuyi
* @update 2016年4月26日 上午9:00:34
*/
public class TfrNotifyEntity implements Serializable {
	
	/*ID                VARCHAR2(50) not null,
	  CREATE_TIME       TIMESTAMP(6),
	  NOTIFY_TIME       TIMESTAMP(6),
	  NOTIFY_TYPE       TIMESTAMP(6),
	  NOTIFY_PARAM1     VARCHAR2(50),
	  NOTIFY_PARAM2     VARCHAR2(50),
	  NOTIFY_PARAM3     VARCHAR2(200),
	  NOTIFY_NUM        NUMBER(1),
	  NOTIFY_ERRORINFO VARCHAR2(500)*/
	/**错误信息最大长度*/
	private static int ERROR_INFO_SIZE=TransferConstants.SONAR_NUMBER_500;
	
	private static final long serialVersionUID = 1L;

	//表id
	private String id;

	//任务创建时间
    private String createTime;

    //任务通知时间
    private String notifyTime;

    //任务类型
    private String notifyType;

    //任务预留参数1
    private String notifyParam1;

    //任务预留参数2
    private String notifyParam2;

    //任务预留参数3
    private String notifyParam3;

    //任务已通知次数
    private Integer notifyNum;
    
    //任务失败原因
    private String notifyErrorInfo;
    
    //大文本数据
    private String paramJson;
    
    private String jobId;
    
    /**
	 * @return the jobId
	 */
	public String getJobId() {
		return jobId;
	}

	/**
	 * @param jobId the jobId to set
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public TfrNotifyEntity(){
    	
    }
    
    public TfrNotifyEntity(String id,String notifyType,
    		String notifyParam1,String notifyParam2,String notifyParam3){
    	this.id=id;
    	this.notifyType=notifyType;
    	this.notifyParam1=notifyParam1;
    	this.notifyParam2=notifyParam2;
    	this.notifyParam3=notifyParam3;
    }
    
    /**
     * paramJson 支持大的文本字符存储
     */
    public TfrNotifyEntity(String id,String notifyType,
    		String notifyParam1,String notifyParam2,String notifyParam3,String paramJson){
    	this.id=id;
    	this.notifyType=notifyType;
    	this.notifyParam1=notifyParam1;
    	this.notifyParam2=notifyParam2;
    	this.notifyParam3=notifyParam3;
    	this.paramJson=paramJson;
    }
    
	public String getId() {
		return id;   
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(String notifyTime) {
		this.notifyTime = notifyTime;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public String getNotifyParam1() {
		return notifyParam1;
	}

	public void setNotifyParam1(String notifyParam1) {
		this.notifyParam1 = notifyParam1;
	}

	public String getNotifyParam2() {
		return notifyParam2;
	}

	public void setNotifyParam2(String notifyParam2) {
		this.notifyParam2 = notifyParam2;
	}

	public String getNotifyParam3() {
		return notifyParam3;
	}

	public void setNotifyParam3(String notifyParam3) {
		this.notifyParam3 = notifyParam3;
	}

	public Integer getNotifyNum() {
		return notifyNum;
	}

	public void setNotifyNum(Integer notifyNum) {
		this.notifyNum = notifyNum;
	}

	public String getNotifyErrorInfo() {
		return notifyErrorInfo;
	}

	public void setNotifyErrorInfo(String notifyErrorInfo) {
		this.notifyErrorInfo=notifyErrorInfo;
		if(notifyErrorInfo!=null&&notifyErrorInfo.length()>=ERROR_INFO_SIZE)
			this.notifyErrorInfo=notifyErrorInfo.substring(0, ERROR_INFO_SIZE);
	}

	public String getParamJson() {
		return paramJson;
	}

	public void setParamJson(String paramJson) {
		this.paramJson = paramJson;
	}
    
}
