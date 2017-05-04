package com.deppon.foss.module.transfer.edi.server.domain;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;

/**
 * 
* @description 根据OPP传来的运单号查询FOSS流水号
* @version 1.0
* @author 269701-foss-lln
* @update 2016年5月12日 上午11:42:51
 */
public class SerialNoByOppWaybillNoResponse {

	//运单号
	private String waybillNo;
	//运单基础信息
	private WaybillEntity waybillEntity;
	
	//流水号
	List<String> serialNoList;
	
	//返回是否成功的标志  true ,false
	private boolean beSuccess ;
	//失败原因 
	private String failureReason;
	//返回类型，接口类型 
	private String returnType;

	/**
	 * @return waybillNo : return the property waybillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月12日 上午11:44:35
	 * @version V1.0
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo : set the property waybillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月12日 上午11:44:35
	 * @version V1.0
	 */
	
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return serialNoList : return the property serialNoList.
	 * @author 269701-foss-lln
	 * @update 2016年5月12日 上午11:44:35
	 * @version V1.0
	 */
	public List<String> getSerialNoList() {
		return serialNoList;
	}

	/**
	 * @param serialNoList : set the property serialNoList.
	 * @author 269701-foss-lln
	 * @update 2016年5月12日 上午11:44:35
	 * @version V1.0
	 */
	
	public void setSerialNoList(List<String> serialNoList) {
		this.serialNoList = serialNoList;
	}

	/**
	 * @return beSuccess : return the property beSuccess.
	 * @author 269701-foss-lln
	 * @update 2016年5月12日 上午11:51:12
	 * @version V1.0
	 */
	public boolean isBeSuccess() {
		return beSuccess;
	}

	/**
	 * @param beSuccess : set the property beSuccess.
	 * @author 269701-foss-lln
	 * @update 2016年5月12日 上午11:51:12
	 * @version V1.0
	 */
	
	public void setBeSuccess(boolean beSuccess) {
		this.beSuccess = beSuccess;
	}

	/**
	 * @return failureReason : return the property failureReason.
	 * @author 269701-foss-lln
	 * @update 2016年5月12日 上午11:51:12
	 * @version V1.0
	 */
	public String getFailureReason() {
		return failureReason;
	}

	/**
	 * @param failureReason : set the property failureReason.
	 * @author 269701-foss-lln
	 * @update 2016年5月12日 上午11:51:12
	 * @version V1.0
	 */
	
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	/**
	 * @return returnType : return the property returnType.
	 * @author 269701-foss-lln
	 * @update 2016年5月12日 上午11:51:12
	 * @version V1.0
	 */
	public String getReturnType() {
		return returnType;
	}

	/**
	 * @param returnType : set the property returnType.
	 * @author 269701-foss-lln
	 * @update 2016年5月12日 上午11:51:12
	 * @version V1.0
	 */
	
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public WaybillEntity getWaybillEntity() {
		return waybillEntity;
	}

	public void setWaybillEntity(WaybillEntity waybillEntity) {
		this.waybillEntity = waybillEntity;
	}
	
	
}
