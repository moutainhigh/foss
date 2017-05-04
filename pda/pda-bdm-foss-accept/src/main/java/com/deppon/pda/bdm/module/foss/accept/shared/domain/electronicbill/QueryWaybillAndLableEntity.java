package com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill;

import java.io.Serializable;

/**
 * 
 * @ClassName: QueryWaybillAndLableEntity 
 * @Description: TODO(查询运单及标签信息实体) 
 * @author &268974  wangzhili
 * @date 2016-5-17 下午2:56:46 
 *
 */
public class QueryWaybillAndLableEntity implements Serializable{
	 
	/**  
	 * @author 268974  wangzhili
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 *   
	 * @since Ver 1.0   
	*/
	private static final long serialVersionUID = 1L;
	//运单号
	private String wblCode;
	//出发部门编码
	private String departDeptCode;
	public String getWblCode() {
		return wblCode;
	}
	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}
	public String getDepartDeptCode() {
		return departDeptCode;
	}
	public void setDepartDeptCode(String departDeptCode) {
		this.departDeptCode = departDeptCode;
	}
	
	

}
