package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;


/**
* @description 快递到达实体类
* @version 1.0
* @author 218381-foss-lijie
* @update 2015年5月12日 上午11:24:32
*/
public class ExpressArrivalEntity extends BaseEntity{
	
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
	 * 确认时间
	 */
	private Date createTime;
	/**
	 * 确认人code
	 */
	private String createUserCode;
	/**
	 * 确认人name
	 */
	private String createUserName;
	/**
	 * 确认部门code
	 */
	private String createOrgCode;
	/**
	 * 确认部门name
	 */
	private String createOrgName;
	/**
	 * 状态
	 */
	private int status;
	/**
	 * 是否启用
	 */
	private String active;
	
	/**
	* @description 获取id
	* (non-Javadoc)
	* @see com.deppon.foss.framework.entity.BaseEntity#getId()
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:40:28
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
	* @update 2015年5月15日 上午10:40:37
	* @version V1.0
	*/
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	* @description 获取运单号
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:40:44
	*/
	public String getWaybillNo() {
		return waybillNo;
	}
	
	/**
	* @description 设置运单号
	* @param waybillNo
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:40:53
	*/
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	/**
	* @description 获取确认时间
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:41:01
	*/
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	* @description 设置确认时间
	* @param createTime
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:41:09
	*/
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	* @description 获取确认人code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:41:22
	*/
	public String getCreateUserCode() {
		return createUserCode;
	}
	
	/**
	* @description 设置确认人code
	* @param createUserCode
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:41:32
	*/
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	
	/**
	* @description 获取确认人name
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:41:43
	*/
	public String getCreateUserName() {
		return createUserName;
	}
	
	/**
	* @description 设置确认人name
	* @param createUserName
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:41:55
	*/
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	/**
	* @description 获取确认人部门code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:42:06
	*/
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	
	/**
	* @description 设置确认人部门code
	* @param createOrgCode
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:42:18
	*/
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	
	/**
	* @description 获取确认人部门name
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:42:33
	*/
	public String getCreateOrgName() {
		return createOrgName;
	}
	
	/**
	* @description 获取确认人部门name
	* @param createOrgName
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:42:44
	*/
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	
	/**
	* @description 获取状态
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:42:58
	*/
	public int getStatus() {
		return status;
	}
	
	/**
	* @description 设置状态
	* @param status
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:43:06
	*/
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	* @description 获取是否启用
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:43:15
	*/
	public String getActive() {
		return active;
	}
	
	/**
	* @description 设置是否启用
	* @param active
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 上午10:43:24
	*/
	public void setActive(String active) {
		this.active = active;
	}

	

	
}
