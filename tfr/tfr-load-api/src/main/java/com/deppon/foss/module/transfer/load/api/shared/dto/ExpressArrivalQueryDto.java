package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.Date;


/**
* @description 快递到达DTO
* @version 1.0
* @author 218381-foss-lijie
* @update 2015年5月12日 上午11:01:25
*/
public class ExpressArrivalQueryDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	
	/**
	* @fields 运单号
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午10:56:09
	* @version V1.0
	*/
	private String waybillNo;
	
	
	/**
	* @fields 起始时间
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午10:56:13
	* @version V1.0
	*/
	private Date beginTime;
	
	
	/**
	* @fields 结束时间
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午10:56:16
	* @version V1.0
	*/
	private Date endTime;
	
	
	/**
	* @fields 部门code
	* @author 218381-foss-lijie
	* @update 2015年5月12日 下午5:43:39
	* @version V1.0
	*/
	private String orgCode;

	
	
	/**
	* @description 获取部门code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 下午5:43:58
	*/
	public String getOrgCode() {
		return orgCode;
	}



	
	/**
	* @description 设置部门code
	* @param orgCode
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 下午5:44:06
	*/
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}



	/**
	* @description 获取运单号
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午10:57:02
	*/
	public String getWaybillNo() {
		return waybillNo;
	}


	
	/**
	* @description 设置运单号
	* @param waybillNo
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午10:57:11
	*/
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}


	
	/**
	* @description 获取开始时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午10:57:20
	*/
	public Date getBeginTime() {
		return beginTime;
	}


	
	/**
	* @description 设置开始时间
	* @param beginTime
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午10:57:30
	*/
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}


	
	/**
	* @description 获取结束时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午10:57:39
	*/
	public Date getEndTime() {
		return endTime;
	}


	
	/**
	* @description 设置结束时间
	* @param endTime
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午10:57:49
	*/
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}
