package com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill;

import java.io.Serializable;

/**   
 * @ClassName QryBigCustomEntity  
 * @Description 查询大客户实体   
 * @author  092038 张贞献  
 * @date 2014-7-10    
 */ 
public class QryCustomEntity implements Serializable{
   
	/**  
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 车牌号
	 */
	private String truckCode;
	/**
	 * 员工编号
	 */
	private String userCode;
	public String getTruckCode() {
		return truckCode;
	}
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	
}
