package com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto;

import java.util.Date;


/**
* @description 上分拣和标签打印实体类
* @version 1.0
* @author 218381-foss-lijie
* @update 2015年7月3日 下午2:35:31
*/
public class SortingAndPringLabelDto implements java.io.Serializable{

	
	/**
	* @fields serialVersionUID
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午2:35:24
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private String id;
	/**
	 * 1:标签打印   0:上分拣扫描
	 */
	private String isPrint;
	
	
	/**
	* @description 获取是否是标签打印
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月10日 下午4:05:09
	*/
	public String getIsPrint() {
		return isPrint;
	}




	
	/**
	* @description 设置是否是标签打印
	* @param isPrint
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月10日 下午4:05:23
	*/
	public void setIsPrint(String isPrint) {
		this.isPrint = isPrint;
	}




	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 流水号
	 */
	private String serialNo;
	/**
	 * 操作人code
	 */
	private String operateCode;
	/**
	 * 操作人name
	 */
	private String operateName;
	/**
	 * 标签打印
	 */
	private String labelPrint;
	/**
	 * 开始时间
	 */
	private Date beginTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 操作时间
	 */
	private Date operateTime;
	/**
	 * 是否是始发外场
	 */
	private Boolean isFirst;
	/**
	 * 部门code
	 */
	private String orgCode;
	/**
	 * 部门name
	 */
	private String orgName;
	
	
	
	
	/**
	* @description 获取操作人姓名
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月8日 上午11:12:45
	*/
	public String getOperateName() {
		return operateName;
	}



	
	/**
	* @description 设置操作人姓名
	* @param operateName
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月8日 上午11:12:55
	*/
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}



	
	/**
	* @description 获取操作时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月8日 上午11:13:04
	*/
	public Date getOperateTime() {
		return operateTime;
	}



	
	/**
	* @description 设置操作时间
	* @param operateTime
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月8日 上午11:13:14
	*/
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}



	
	/**
	* @description 获取部门名称
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月8日 上午11:13:22
	*/
	public String getOrgName() {
		return orgName;
	}



	
	/**
	* @description 设置部门名称
	* @param orgName
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月8日 上午11:13:30
	*/
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}



	/**
	* @description 获取部门code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月8日 上午10:02:02
	*/
	public String getOrgCode() {
		return orgCode;
	}


	
	/**
	* @description 设置部门code
	* @param orgCode
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月8日 上午10:02:11
	*/
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}


	/**
	* @description 获取是否是始发外场
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午3:00:25
	*/
	public Boolean getIsFirst() {
		return isFirst;
	}

	
	/**
	* @description 设置是否是始发外场
	* @param isFirst
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午3:00:36
	*/
	public void setIsFirst(Boolean isFirst) {
		this.isFirst = isFirst;
	}

	/**
	* @description 获取运单号
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午2:45:47
	*/
	public String getWaybillNo() {
		return waybillNo;
	}
	
	/**
	* @description 设置运单号
	* @param waybillNo
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午2:45:58
	*/
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	/**
	* @description 获取流水号
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午2:46:06
	*/
	public String getSerialNo() {
		return serialNo;
	}
	
	/**
	* @description 设置流水号
	* @param serialNo
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午2:46:15
	*/
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	/**
	* @description 获取操作人code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午2:46:23
	*/
	public String getOperateCode() {
		return operateCode;
	}
	
	/**
	* @description 设置操作人code
	* @param operateCode
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午2:46:32
	*/
	public void setOperateCode(String operateCode) {
		this.operateCode = operateCode;
	}
	
	/**
	* @description 获取标签打印
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午2:46:41
	*/
	public String getLabelPrint() {
		return labelPrint;
	}
	/**
	* @description 设置标签打印
	* @param labelprint
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月8日 上午10:02:31
	*/
	public void setLabelPrint(String labelPrint) {
		this.labelPrint = labelPrint;
	}
	
	/**
	* @description 获取起始时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午2:47:01
	*/
	public Date getBeginTime() {
		return beginTime;
	}
	
	/**
	* @description 设置起始时间
	* @param beginTime
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午2:47:14
	*/
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	/**
	* @description 获取结束时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午2:47:23
	*/
	public Date getEndTime() {
		return endTime;
	}
	
	/**
	* @description 设置结束时间
	* @param endTime
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午2:47:32
	*/
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}
	
	
}







