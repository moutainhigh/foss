package com.deppon.foss.module.frameworkimpl.sso.server;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import org.apache.commons.lang.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.sso.domain.Token;

public class FossCcToken extends  Token{
	
	
	 
	private static final Logger LOGGER = LoggerFactory.getLogger(FossCcToken.class);
	 
	private String empCode;
	
	 
	private String deptCode;
	
 
	private String deptName;
	
	private Long expireTime = System.currentTimeMillis();

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long millisecond) {
		this.expireTime = millisecond;
	}
	 
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public FossCcToken(String sessionId, String userId, String empCode, String currentDeptCode,String currentDeptName, int expireSecond) {
		this.setUserId(userId);
		this.setSessionId(sessionId);
		this.empCode = empCode;
		this.deptCode = currentDeptCode;
		this.deptName=currentDeptName;
		//鐢熸垚鏃堕棿鎴�
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		int millisecond = expireSecond * NumberConstants.NUMBER_1000;
		c.add(Calendar.MILLISECOND, millisecond);
		this.expireTime = c.getTimeInMillis();
	}
	
	
	public byte[] toBytes() {
		try {
			return this.toString().getBytes(CharEncoding.UTF_8);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage(),e);
		}
		return null;
	}

	@Override
	public String toString() {
		super.toString();
		StringBuffer sb = new StringBuffer(NumberConstants.NUMBER_8);
		sb.append(getSessionId()).append(",");
		sb.append(getUserId()).append(",");
		sb.append(getEmpCode()).append(",");
		sb.append(getDeptCode()).append(",");
		sb.append(getDeptName()).append(",");
		sb.append(getExpireTime());
		return sb.toString();
	}
	
	

}
