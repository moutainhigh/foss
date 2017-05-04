package com.deppon.foss.module.transfer.edi.server.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpDetialInfoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpInfoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpSerialEntity;


/**
 * 
* @description 更改单传数据 更新合大票相关表
* @version 1.0
* @author 269701-foss-lln
* @update 2016年5月12日 下午5:35:26
 */
public class RfcPushDataToFossRequst implements Serializable{
	
	/**
	* @fields serialVersionUID
	* @author 269701-foss-lln
	* @update 2016年5月19日 下午4:42:36
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;
	//运单号
	private String waybillNo;
	//清单号
	private String airWaybillNo;
	//合大票清单主表id
	private String airPickId;
	//合大票清单明细表id
	private String airPickDetialId;
	//操作类型：更改件数:AMOUNT/更改费用：FEE
	private String operType;
	//件数异常类型--多货 20/少货30
	private String exceptionType;
	//送货费：代理送货费修改前后的差异
	private BigDecimal deliverFee=new BigDecimal(0);
	//清单主表数据--清单id 总票数 总件数 总送货费
	private AirPickUpInfoEntity airPickInfo;
	//清单明细数据--清单明细id 总件数 送货费
	private AirPickUpDetialInfoEntity detialInfo;
	//清单流水数据--多货：需要新增的流水信息 少货：需要删除的流水信息
	private List<AirPickUpSerialEntity> serialList;
	//异常运单件数 :-1~ 运单整体丢失;0 ~部分少货; 1~ 运单整体增加;2运单部分丢失 
	private int waybillExcCount;
	
	//修改人编码
		private String modifyUserCode;
		//修改人名称
		private String modifyUserName;
		//修改人所在部门编码
		private String modifyDeptCode;
		//修改人所在部门名称
		private String modifyDeptName;
	
	
	/**
	 * @return waybillExcCount : return the property waybillExcCount.
	 * @author 269701-foss-lln
	 * @update 2016年5月19日 下午3:56:00
	 * @version V1.0
	 */
	public int getWaybillExcCount() {
		return waybillExcCount;
	}
	/**
	 * @param waybillExcCount : set the property waybillExcCount.
	 * @author 269701-foss-lln
	 * @update 2016年5月19日 下午3:56:00
	 * @version V1.0
	 */
	
	public void setWaybillExcCount(int waybillExcCount) {
		this.waybillExcCount = waybillExcCount;
	}
	/**
	 * @return airPickId : return the property airPickId.
	 * @author 269701-foss-lln
	 * @update 2016年5月19日 下午3:27:22
	 * @version V1.0
	 */
	public String getAirPickId() {
		return airPickId;
	}
	/**
	 * @param airPickId : set the property airPickId.
	 * @author 269701-foss-lln
	 * @update 2016年5月19日 下午3:27:22
	 * @version V1.0
	 */
	
	public void setAirPickId(String airPickId) {
		this.airPickId = airPickId;
	}
	/**
	 * @return airPickDetialId : return the property airPickDetialId.
	 * @author 269701-foss-lln
	 * @update 2016年5月19日 下午3:27:22
	 * @version V1.0
	 */
	public String getAirPickDetialId() {
		return airPickDetialId;
	}
	/**
	 * @param airPickDetialId : set the property airPickDetialId.
	 * @author 269701-foss-lln
	 * @update 2016年5月19日 下午3:27:22
	 * @version V1.0
	 */
	
	public void setAirPickDetialId(String airPickDetialId) {
		this.airPickDetialId = airPickDetialId;
	}
	/**
	 * @return waybillNo : return the property waybillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月15日 下午4:49:10
	 * @version V1.0
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo : set the property waybillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月15日 下午4:49:10
	 * @version V1.0
	 */
	
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * @return airWaybillNo : return the property airWaybillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月15日 下午4:49:10
	 * @version V1.0
	 */
	public String getAirWaybillNo() {
		return airWaybillNo;
	}
	/**
	 * @param airWaybillNo : set the property airWaybillNo.
	 * @author 269701-foss-lln
	 * @update 2016年5月15日 下午4:49:10
	 * @version V1.0
	 */
	
	public void setAirWaybillNo(String airWaybillNo) {
		this.airWaybillNo = airWaybillNo;
	}
	/**
	 * @return operType : return the property operType.
	 * @author 269701-foss-lln
	 * @update 2016年5月15日 下午4:49:10
	 * @version V1.0
	 */
	public String getOperType() {
		return operType;
	}
	/**
	 * @param operType : set the property operType.
	 * @author 269701-foss-lln
	 * @update 2016年5月15日 下午4:49:10
	 * @version V1.0
	 */
	
	public void setOperType(String operType) {
		this.operType = operType;
	}
	/**
	 * @return exceptionType : return the property exceptionType.
	 * @author 269701-foss-lln
	 * @update 2016年5月15日 下午4:49:10
	 * @version V1.0
	 */
	public String getExceptionType() {
		return exceptionType;
	}
	/**
	 * @param exceptionType : set the property exceptionType.
	 * @author 269701-foss-lln
	 * @update 2016年5月15日 下午4:49:10
	 * @version V1.0
	 */
	
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
	/**
	 * @return airPickInfo : return the property airPickInfo.
	 * @author 269701-foss-lln
	 * @update 2016年5月15日 下午4:49:10
	 * @version V1.0
	 */
	public AirPickUpInfoEntity getAirPickInfo() {
		return airPickInfo;
	}
	/**
	 * @param airPickInfo : set the property airPickInfo.
	 * @author 269701-foss-lln
	 * @update 2016年5月15日 下午4:49:10
	 * @version V1.0
	 */
	/**
	 * @return deliverFee : return the property deliverFee.
	 * @author 269701-foss-lln
	 * @update 2016年5月19日 下午4:42:16
	 * @version V1.0
	 */
	public BigDecimal getDeliverFee() {
		return deliverFee;
	}
	/**
	 * @param deliverFee : set the property deliverFee.
	 * @author 269701-foss-lln
	 * @update 2016年5月19日 下午4:42:16
	 * @version V1.0
	 */
	
	public void setDeliverFee(BigDecimal deliverFee) {
		this.deliverFee = deliverFee;
	}
	/**
	 * @return detialInfo : return the property detialInfo.
	 * @author 269701-foss-lln
	 * @update 2016年5月19日 下午4:42:16
	 * @version V1.0
	 */
	public AirPickUpDetialInfoEntity getDetialInfo() {
		return detialInfo;
	}
	/**
	 * @param detialInfo : set the property detialInfo.
	 * @author 269701-foss-lln
	 * @update 2016年5月19日 下午4:42:16
	 * @version V1.0
	 */
	
	public void setDetialInfo(AirPickUpDetialInfoEntity detialInfo) {
		this.detialInfo = detialInfo;
	}

	/**
	 * @return serialList : return the property serialList.
	 * @author 269701-foss-lln
	 * @update 2016年5月20日 上午10:49:23
	 * @version V1.0
	 */
	public List<AirPickUpSerialEntity> getSerialList() {
		return serialList;
	}
	/**
	 * @param serialList : set the property serialList.
	 * @author 269701-foss-lln
	 * @update 2016年5月20日 上午10:49:23
	 * @version V1.0
	 */
	
	public void setSerialList(List<AirPickUpSerialEntity> serialList) {
		this.serialList = serialList;
	}
	/**
	 * @param airPickInfo : set the property airPickInfo.
	 * @author 269701-foss-lln
	 * @update 2016年5月19日 下午4:42:16
	 * @version V1.0
	 */
	
	public void setAirPickInfo(AirPickUpInfoEntity airPickInfo) {
		this.airPickInfo = airPickInfo;
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
	public String getModifyDeptCode() {
		return modifyDeptCode;
	}
	public void setModifyDeptCode(String modifyDeptCode) {
		this.modifyDeptCode = modifyDeptCode;
	}
	public String getModifyDeptName() {
		return modifyDeptName;
	}
	public void setModifyDeptName(String modifyDeptName) {
		this.modifyDeptName = modifyDeptName;
	}
	
}
