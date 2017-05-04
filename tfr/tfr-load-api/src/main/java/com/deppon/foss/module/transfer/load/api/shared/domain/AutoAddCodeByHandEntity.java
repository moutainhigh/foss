/**  
 * Project Name:tfr-load-api  
 * File Name:AutoAddCodeByHandEntity.java  
 * Package Name:com.deppon.foss.module.transfer.load.api.shared.domain  
 * Date:2015年6月16日上午1:32:58  
 *  
 */
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**  
 * ClassName: AutoAddCodeByHandEntity <br/>  
 * Function: 待手工补码实体. <br/>  
 * date: 2015年6月16日 上午1:32:58 <br/>  
 *  
 * @author shiwei-045923 shiwei@outlook.com  
 * @version   
 * @since JDK 1.6  
 */
public class AutoAddCodeByHandEntity implements Serializable {

	/**  
	 * serialVersionUID
	 * @since JDK 1.6  
	 */
	private static final long serialVersionUID = 16874981560165L;
	
	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 转手工补码原因(补码被退回、GIS未匹配网点)
	 */
	private String reason;
	
	/**
	 * 操作人工号(补码被退回时不为空)
	 */
	private String operatorCode;
	
	/**
	 * 操作人姓名(补码被退回时不为空)
	 */
	private String operatorName;
	
	/**
	 * 记录创建时间戳
	 */
	private Date createTime;

	/**  
	 * id.  
	 *  
	 * @return  the id  
	 * @since   JDK 1.6  
	 */
	public String getId() {
		return id;
	}

	/**  
	 * id.  
	 *  
	 * @param   id    the id to set  
	 * @since   JDK 1.6  
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**  
	 * waybillNo.  
	 *  
	 * @return  the waybillNo  
	 * @since   JDK 1.6  
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**  
	 * waybillNo.  
	 *  
	 * @param   waybillNo    the waybillNo to set  
	 * @since   JDK 1.6  
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**  
	 * reason.  
	 *  
	 * @return  the reason  
	 * @since   JDK 1.6  
	 */
	public String getReason() {
		return reason;
	}

	/**  
	 * reason.  
	 *  
	 * @param   reason    the reason to set  
	 * @since   JDK 1.6  
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**  
	 * operatorCode.  
	 *  
	 * @return  the operatorCode  
	 * @since   JDK 1.6  
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**  
	 * operatorCode.  
	 *  
	 * @param   operatorCode    the operatorCode to set  
	 * @since   JDK 1.6  
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**  
	 * operatorName.  
	 *  
	 * @return  the operatorName  
	 * @since   JDK 1.6  
	 */
	public String getOperatorName() {
		return operatorName;
	}

	/**  
	 * operatorName.  
	 *  
	 * @param   operatorName    the operatorName to set  
	 * @since   JDK 1.6  
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**  
	 * createTime.  
	 *  
	 * @return  the createTime  
	 * @since   JDK 1.6  
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**  
	 * createTime.  
	 *  
	 * @param   createTime    the createTime to set  
	 * @since   JDK 1.6  
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
