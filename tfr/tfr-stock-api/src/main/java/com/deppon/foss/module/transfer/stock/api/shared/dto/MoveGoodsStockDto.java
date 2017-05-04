package com.deppon.foss.module.transfer.stock.api.shared.dto;

import java.util.Date;



/**
* @description 库存迁移查询结果相关数据
* @version 1.0
* @author 218381-foss-lijie
* @update 2014-12-12 上午10:29:38
*/
public class MoveGoodsStockDto implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 申请人是否是场地搬迁信息维护员
	 */
	private String isMoveGoodsMan;
	
	
	/**
	* @description 获取申请人是否是场地搬迁信息维护员
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015-1-8 上午8:50:30
	*/
	public String getIsMoveGoodsMan() {
		return isMoveGoodsMan;
	}
	
	/**
	* @description 设置申请人是否是场地搬迁信息维护员
	* @param isMoveGoodsMan
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015-1-8 上午8:50:37
	*/
	public void setIsMoveGoodsMan(String isMoveGoodsMan) {
		this.isMoveGoodsMan = isMoveGoodsMan;
	}



	/**
	 * 申请人工号
	 */
	
	private String applicant_code;

	/**
	 * 
	* @description 获取申请人工号
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-12 下午4:47:57
	 */
	public String getApplicant_code() {
		return applicant_code;
	}
	/**
	 * 
	* @description 设置申请人工号
	* @param applicant_code
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-12 下午4:48:02
	 */
	public void setApplicant_code(String applicantCode) {
		this.applicant_code = applicantCode;
	}
	
	
	
	/**
	 * 起始时间
	 */
	private Date beginInStockTime;
	
	public Date getBeginInStockTime() {
		return beginInStockTime;
	}
	public void setBeginInStockTime(Date beginInStockTime) {
		this.beginInStockTime = beginInStockTime;
	}
	public Date getEndInStockTime() {
		return endInStockTime;
	}
	public void setEndInStockTime(Date endInStockTime) {
		this.endInStockTime = endInStockTime;
	}
	/**
	 * 结束时间
	 */
	private Date endInStockTime;
	/**
	 * 状态
	 */
	private String state;
	/**
	 * 
	* @description 获取 状态
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-12 下午4:48:57
	 */
	public String getState() {
		return state;
	}
	/**
	 * 
	* @description 设置 状态
	* @param state
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2014-12-12 下午4:49:00
	 */
	public void setState(String state) {
		this.state = state;
	}	
}
