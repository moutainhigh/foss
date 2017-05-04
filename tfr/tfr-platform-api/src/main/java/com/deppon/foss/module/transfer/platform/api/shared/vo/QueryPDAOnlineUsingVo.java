/**
 * 
 */
package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.PDAOnlineUsingEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.PDAOnlineUsingDto;

/**
 * @desc pda在线查询V哦
 * @date 2015/01/30
 * @author 105795
 *
 */
public class QueryPDAOnlineUsingVo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4356306315151835015L;

	//pda查询实体
	private  List<PDAOnlineUsingEntity> pdaOnlineUsingEntityList=new ArrayList<PDAOnlineUsingEntity>();

    //pdaDtoset
	private List<PDAOnlineUsingDto> pdaOnlineUsingDtoList=new ArrayList<PDAOnlineUsingDto>();
	
	//经营本部编码
	private String hqCode;
	//经营本部名称
	private String hqName;
	//外场code
	private String orgCode;
	//外场名称
	private String orgName;
	//查询日期
	private Date queryDate;

	/**
	 * @return the pdaOnlineUsingEntityList
	 */
	public List<PDAOnlineUsingEntity> getPdaOnlineUsingEntityList() {
		return pdaOnlineUsingEntityList;
	}

	/**
	 * @param pdaOnlineUsingEntityList the pdaOnlineUsingEntityList to set
	 */
	public void setPdaOnlineUsingEntityList(
			List<PDAOnlineUsingEntity> pdaOnlineUsingEntityList) {
		this.pdaOnlineUsingEntityList = pdaOnlineUsingEntityList;
	}

	/**
	 * @return the pdaOnlineUsingDtoList
	 */
	public List<PDAOnlineUsingDto> getPdaOnlineUsingDtoList() {
		return pdaOnlineUsingDtoList;
	}

	/**
	 * @param pdaOnlineUsingDtoList the pdaOnlineUsingDtoList to set
	 */
	public void setPdaOnlineUsingDtoList(
			List<PDAOnlineUsingDto> pdaOnlineUsingDtoList) {
		this.pdaOnlineUsingDtoList = pdaOnlineUsingDtoList;
	}

	/**
	 * @return the hqCode
	 */
	public String getHqCode() {
		return hqCode;
	}

	/**
	 * @param hqCode the hqCode to set
	 */
	public void setHqCode(String hqCode) {
		this.hqCode = hqCode;
	}

	/**
	 * @return the hqName
	 */
	public String getHqName() {
		return hqName;
	}

	/**
	 * @param hqName the hqName to set
	 */
	public void setHqName(String hqName) {
		this.hqName = hqName;
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return the queryDate
	 */
	public Date getQueryDate() {
		return queryDate;
	}

	/**
	 * @param queryDate the queryDate to set
	 */
	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}
	

	
	

}
