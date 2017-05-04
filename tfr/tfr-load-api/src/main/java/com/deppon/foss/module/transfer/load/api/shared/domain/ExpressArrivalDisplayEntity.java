package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;


/**
* @description 快递到达页面展示实体类
* @version 1.0
* @author 218381-foss-lijie
* @update 2015年5月12日 上午11:24:32
*/
public class ExpressArrivalDisplayEntity extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private String id;
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 收货城市code
	 */
	private String receiveCustomerCityCode;
	/**
	 * 收货城市name
	 */
	private String receiveCustomerCityName;
	/**
	 * 收货省份code
	 */
	private String receiveCustomerProvCode;
	/**
	 * 收货省份name
	 */
	private String receiveCustomerProvName;
	
	
	/**
	* @description 获取收货省份code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月16日 下午5:46:10
	*/
	public String getReceiveCustomerProvCode() {
		return receiveCustomerProvCode;
	}

	
	/**
	* @description 设置收货省份code
	* @param receiveCustomerProvCode
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月16日 下午5:46:24
	*/
	public void setReceiveCustomerProvCode(String receiveCustomerProvCode) {
		this.receiveCustomerProvCode = receiveCustomerProvCode;
	}




	
	/**
	* @description 获取收货省份name
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月16日 下午5:46:42
	*/
	public String getReceiveCustomerProvName() {
		return receiveCustomerProvName;
	}




	
	/**
	* @description 设置收货省份name
	* @param receiveCustomerProvName
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月16日 下午5:46:54
	*/
	public void setReceiveCustomerProvName(String receiveCustomerProvName) {
		this.receiveCustomerProvName = receiveCustomerProvName;
	}




	/**
	* @description 获取收货城市code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月16日 下午5:28:05
	*/
	public String getReceiveCustomerCityCode() {
		return receiveCustomerCityCode;
	}



	
	/**
	* @description 设置收货城市code
	* @param receiveCustomerCityCode
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月16日 下午5:28:18
	*/
	public void setReceiveCustomerCityCode(String receiveCustomerCityCode) {
		this.receiveCustomerCityCode = receiveCustomerCityCode;
	}



	
	/**
	* @description 获取收货城市name
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月16日 下午5:28:37
	*/
	public String getReceiveCustomerCityName() {
		return receiveCustomerCityName;
	}



	
	/**
	* @description 设置收货城市name
	* @param receiveCustomerCityName
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月16日 下午5:28:50
	*/
	public void setReceiveCustomerCityName(String receiveCustomerCityName) {
		this.receiveCustomerCityName = receiveCustomerCityName;
	}



	/**
	* @description 获取运单号
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月14日 下午3:34:27
	*/
	public String getWaybillNo() {
		return waybillNo;
	}


	
	/**
	* @description 设置运单号
	* @param waybillNo
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月14日 下午3:34:36
	*/
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 目的地址
	 */
	private String receiveCustomerAddress;
	/**
	 * 提货网点部门名称
	 */
	private String customerPickupOrgName;
	/**
	 * 提货网点部门code
	 */
	private String customerPickupOrgCode;
	/**
	 * 预计到达时间
	 */
	private Date preArriveTime;
	/**
	 * 开单部门名称
	 */
	private String createUserDeptName;
	/**
	 * 开单部门code
	 */
	private String createOrgCode;
	/**
	 * 状态
	 */
	private int status;
	/**
	 * 是否启用
	 */
	private String active;
	
	
	
	/**
	* @description 获取是否启用的状态值
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 下午3:28:44
	*/
	public String getActive() {
		return active;
	}

	
	/**
	* @description 设置是否启用
	* @param active
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 下午3:29:08
	*/
	public void setActive(String active) {
		this.active = active;
	}

	/**
	* @description 获取id
	* (non-Javadoc)
	* @see com.deppon.foss.framework.entity.BaseEntity#getId()
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午11:24:57
	* @version V1.0
	*/
	public String getId() {
		return id;
	}
	
	/**
	* @description 设置id
	* (non-Javadoc)
	* @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午11:25:06
	* @version V1.0
	*/
	public void setId(String id) {
		this.id = id;
	}
	

	
	/**
	* @description 获取目的地址
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午11:25:34
	*/
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}
	
	/**
	* @description 设置目的地址
	* @param receiveCustomerAddress
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午11:26:03
	*/
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}
	
	/**
	* @description 获取提货网点
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午11:26:12
	*/
	public String getCustomerPickupOrgName() {
		return customerPickupOrgName;
	}
	
	/**
	* @description 设置提货网点
	* @param customerPickupOrgName
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午11:26:25
	*/
	public void setCustomerPickupOrgName(String customerPickupOrgName) {
		this.customerPickupOrgName = customerPickupOrgName;
	}
	
	/**
	* @description 获取提货网点部门code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午11:26:39
	*/
	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}
	
	/**
	* @description 设置提货网点部门code
	* @param customerPickupOrgCode
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午11:26:55
	*/
	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}
	
	/**
	* @description 获取预计到达时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午11:27:06
	*/
	public Date getPreArriveTime() {
		return preArriveTime;
	}
	
	/**
	* @description 设置预计到达时间
	* @param preArriveTime
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午11:27:21
	*/
	public void setPreArriveTime(Date preArriveTime) {
		this.preArriveTime = preArriveTime;
	}
	
	/**
	* @description 获取开单部门名称
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午11:27:32
	*/
	public String getCreateUserDeptName() {
		return createUserDeptName;
	}
	
	/**
	* @description 设置开单部门名称
	* @param createUserDeptName
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午11:27:42
	*/
	public void setCreateUserDeptName(String createUserDeptName) {
		this.createUserDeptName = createUserDeptName;
	}
	
	/**
	* @description 获取开单部门code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午11:27:55
	*/
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	
	/**
	* @description 设置开单部门code
	* @param createOrgCode
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午11:28:08
	*/
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	
	/**
	* @description 获取状态
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午11:28:19
	*/
	public int getStatus() {
		return status;
	}
	
	/**
	* @description 设置状态
	* @param status
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 上午11:28:29
	*/
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
