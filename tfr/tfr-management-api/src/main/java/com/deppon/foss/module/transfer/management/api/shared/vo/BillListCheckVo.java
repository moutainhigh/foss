package com.deppon.foss.module.transfer.management.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.management.api.shared.dto.BillListCheckDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.BillListCheckStaDto;

public class BillListCheckVo implements Serializable {

	/**
	 * 序号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	private String id;
	/**
	 * 账单日期
	 */
	private Date billDate;
	/**
	 * 燃油费金额
	 */
	private BigDecimal fuelFee;
	/**
	 * 燃油费优惠金额
	 */
	private BigDecimal fuelFeeSell;
	/**
	 * 燃油费优惠后金额
	 */
	private BigDecimal fuelFeeSellAfter;
	/**
	 * 路桥费金额
	 */
	private BigDecimal roadTollFee;
	/**
	 * 路桥费优惠金额 
	 */
	private BigDecimal roadTollFeeSell;
	/**
	 * 路桥费优惠后金额 
	 */
	private BigDecimal roadTollFeeSellAfter;
	/**
	 * 所属事业部code
	 */
	private String divisionOrgCode;
	/**
	 * 所属事业部Name
	 */
	private String divisionOrgName;
	/**
	 * 所属车队code
	 */
	private String transDepartmentCode;
	/**
	 * 所属车队Name
	 */
	private String transDepartmentName;
	/**
	 * 操作人code
	 */
	private String operatorCode;
	/**
	 * 操作人name
	 */
	private String operatorName;
	/**
	 * 操作日期
	 */
	private String operateDate;
	
	
	/**
	 * 总记录数
	* @fields totalCount
	* @author 14022-foss-songjie 
	* @update 2013年11月26日 下午1:55:59
	* @version V1.0
	*/
	private long totalCount;
	
	
	/**
	* @fields billListCheckList
	* @author 14022-foss-songjie 
	* @update 2013年11月26日 上午11:12:36
	* @version V1.0
	*/
	private List<BillListCheckDto> billListCheckList;
	
	/**
	* @fields billListCheckStaDto
	* @author 14022-foss-songjie 
	* @update 2013年11月26日 下午1:46:55
	* @version V1.0
	*/
	private BillListCheckStaDto  billListCheckStaDto;
	
	
	/**
	* @fields billListCheckDto
	* @author 14022-foss-songjie 
	* @update 2013年11月27日 上午8:32:55
	* @version V1.0
	*/
	private BillListCheckDto billListCheckDto;
	
	
	/**
	* @fields beginDate
	* @author 14022-foss-songjie 
	* @update 2013年11月26日 下午4:13:37
	* @version V1.0
	*/
	private Date beginDate;
	
	/**
	* @fields endDate
	* @author 14022-foss-songjie 
	* @update 2013年11月26日 下午4:13:40
	* @version V1.0
	*/
	private Date endDate;
	
	
	/**
	 * 当前部门的code
	* @fields currentOrgCode
	* @author 14022-foss-songjie 
	* @update 2013年11月28日 下午3:21:45
	* @version V1.0
	*/
	private String currentOrgCode;
	
	/**
	 * 当前部门的名称
	* @fields currentOrgName
	* @author 14022-foss-songjie 
	* @update 2013年11月28日 下午3:22:01
	* @version V1.0
	*/
	private String currentOrgName;
	
	/**
	 * 当前部门对应的事业部code
	* @fields currentCareerCode
	* @author 14022-foss-songjie 
	* @update 2013年11月28日 下午3:22:15
	* @version V1.0
	*/
	private String currentCareerCode;
	
	/**
	 * 当前部门对应的事业部名称
	* @fields currentCareerName
	* @author 14022-foss-songjie 
	* @update 2013年11月28日 下午3:22:37
	* @version V1.0
	*/
	private String currentCareerName;
	
	
	/**
	 * 是否车队
	* @fields isTransTeam
	* @author 14022-foss-songjie 
	* @update 2013年11月28日 下午3:32:19
	* @version V1.0
	*/
	private String isTransTeam;
	
	/**
	 * 获取主键ID
	 * @return
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置主键ID
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取账单日期
	 * @return
	 */
	public Date getBillDate() {
		return billDate;
	}
	/**
	 * 设置账单日期
	 * @param billDate
	 */
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	/**
	 * 获取燃油费金额
	 * @return
	 */
	public BigDecimal getFuelFee() {
		return fuelFee;
	}
	/**
	 * 设置燃油费金额
	 * @param fuelFee
	 */
	public void setFuelFee(BigDecimal fuelFee) {
		this.fuelFee = fuelFee;
	}
	/**
	 * 获取燃油费优惠金额
	 * @return
	 */
	public BigDecimal getFuelFeeSell() {
		return fuelFeeSell;
	}
	/**
	 * 设置燃油费优惠金额
	 * @param fuelFeeSell
	 */
	public void setFuelFeeSell(BigDecimal fuelFeeSell) {
		this.fuelFeeSell = fuelFeeSell;
	}
	/**
	 * 获取燃油费优惠后金额
	 * @return
	 */
	public BigDecimal getFuelFeeSellAfter() {
		return fuelFeeSellAfter;
	}
	/**
	 * 设置燃油费优惠后金额
	 * @param fuelFeeSellAfter
	 */
	public void setFuelFeeSellAfter(BigDecimal fuelFeeSellAfter) {
		this.fuelFeeSellAfter = fuelFeeSellAfter;
	}
	/**
	 * 获取路桥费金额
	 * @return
	 */
	public BigDecimal getRoadTollFee() {
		return roadTollFee;
	}
	/**
	 * 设置路桥费金额
	 * @param roadTollFee
	 */
	public void setRoadTollFee(BigDecimal roadTollFee) {
		this.roadTollFee = roadTollFee;
	}
	/**
	 * 获取路桥费优惠金额
	 * @return
	 */
	public BigDecimal getRoadTollFeeSell() {
		return roadTollFeeSell;
	}
	/**
	 * 设置路桥费优惠金额
	 * @param roadTollFeeSell
	 */
	public void setRoadTollFeeSell(BigDecimal roadTollFeeSell) {
		this.roadTollFeeSell = roadTollFeeSell;
	}
	/**
	 * 获取路桥费优惠后金额
	 * @return
	 */
	public BigDecimal getRoadTollFeeSellAfter() {
		return roadTollFeeSellAfter;
	}
	/**
	 * 设置路桥费优惠后金额
	 * @param roadTollFeeSellAfter
	 */
	public void setRoadTollFeeSellAfter(BigDecimal roadTollFeeSellAfter) {
		this.roadTollFeeSellAfter = roadTollFeeSellAfter;
	}
	/**
	 * 获取所属事业部code
	 * @return
	 */
	public String getDivisionOrgCode() {
		return divisionOrgCode;
	}
	/**
	 * 设置所属事业部code
	 * @param divisionOrgCode
	 */
	public void setDivisionOrgCode(String divisionOrgCode) {
		this.divisionOrgCode = divisionOrgCode;
	}
	/**
	 * 获取所属事业部Name
	 * @return
	 */
	public String getDivisionOrgName() {
		return divisionOrgName;
	}
	/**
	 * 设置所属事业部name
	 * @param divisionOrgName
	 */
	public void setDivisionOrgName(String divisionOrgName) {
		this.divisionOrgName = divisionOrgName;
	}
	/**
	 * 获取所属车队code
	 * @return
	 */
	public String getTransDepartmentCode() {
		return transDepartmentCode;
	}
	/**
	 * 设置所属车队code
	 * @param transDepartmentCode
	 */
	public void setTransDepartmentCode(String transDepartmentCode) {
		this.transDepartmentCode = transDepartmentCode;
	}
	/**
	 * 获取所属车队Name
	 * @return
	 */
	public String getTransDepartmentName() {
		return transDepartmentName;
	}
	/**
	 * 设置所属车队Name
	 * @param transDepartmentName
	 */
	public void setTransDepartmentName(String transDepartmentName) {
		this.transDepartmentName = transDepartmentName;
	}
	/**
	 * 获取操作人code
	 * @return
	 */
	public String getOperatorCode() {
		return operatorCode;
	}
	/**
	 * 设置操作人code
	 * @param operatorCode
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	/**
	 * 获取操作人name
	 * @return
	 */
	public String getOperatorName() {
		return operatorName;
	}
	/**
	 * 设置操作人name
	 * @param operatorName
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	/**
	 * 获取操作日期
	 * @return
	 */
	public String getOperateDate() {
		return operateDate;
	}
	/**
	 * 设置操作日期
	 * @param operateDate
	 */
	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}
	
	/**
	* @description 获取对账单对象集合 (对账单主表)
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月26日 下午1:47:24
	*/
	public List<BillListCheckDto> getBillListCheckList() {
		return billListCheckList;
	}
	
	/**
	* @description 设置对账单集合 (对账单主表)
	* @param billListCheckList
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月26日 下午1:47:51
	*/
	public void setBillListCheckList(List<BillListCheckDto> billListCheckList) {
		this.billListCheckList = billListCheckList;
	}
	
	/**
	* @description 获取对账单汇总的总和对象 (对账单主表)
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月26日 下午1:48:02
	*/
	public BillListCheckStaDto getBillListCheckStaDto() {
		return billListCheckStaDto;
	}
	
	/**
	* @description 设置对账单汇总的 总和对象 (对账单主表)
	* @param billListCheckStaDto
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月26日 下午1:48:34
	*/
	public void setBillListCheckStaDto(BillListCheckStaDto billListCheckStaDto) {
		this.billListCheckStaDto = billListCheckStaDto;
	}
	
	/**
	* @description 获取总记录数
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月26日 下午1:56:26
	*/
	public long getTotalCount() {
		return totalCount;
	}
	
	/**
	* @description 设置总记录数
	* @param totalCount
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月26日 下午1:56:46
	*/
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
	/**
	* @description 获取账单查询时的开单时间  起始日期
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月26日 下午4:13:53
	*/
	public Date getBeginDate() {
		return beginDate;
	}
	
	/**
	* @description 设置账单查询时的开单时间 起始日期
	* @param beginDate
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月26日 下午4:14:32
	*/
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	/**
	* @description 获取账单查询时的开单时间 截至日期
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月26日 下午4:14:46
	*/
	public Date getEndDate() {
		return endDate;
	}
	
	/**
	* @description 设置账单查询时的开单时间 截至日期
	* @param endDate
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月26日 下午4:15:37
	*/
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	* @description 获取对账单的对象 (对账单主表)
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月27日 上午8:33:22
	*/
	public BillListCheckDto getBillListCheckDto() {
		return billListCheckDto;
	}
	
	/**
	* @description 设置对账单的对象 (对账单主表)
	* @param billListCheckDto
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月27日 上午8:33:26
	*/
	public void setBillListCheckDto(BillListCheckDto billListCheckDto) {
		this.billListCheckDto = billListCheckDto;
	}
	
	/**
	* @description 获取当前部门的code
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月28日 下午3:23:18
	*/
	public String getCurrentOrgCode() {
		return currentOrgCode;
	}
	
	/**
	* @description 设置当前部门的code
	* @param currentOrgCode
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月28日 下午3:23:47
	*/
	public void setCurrentOrgCode(String currentOrgCode) {
		this.currentOrgCode = currentOrgCode;
	}
	
	/**
	* @description 获取当前部门的Name
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月28日 下午3:24:15
	*/
	public String getCurrentOrgName() {
		return currentOrgName;
	}
	
	/**
	* @description 设置当前部门的Name
	* @param currentOrgName
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月28日 下午3:24:32
	*/
	public void setCurrentOrgName(String currentOrgName) {
		this.currentOrgName = currentOrgName;
	}
	
	/**
	* @description 获取当前部门对应的事业部门code
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月28日 下午3:24:44
	*/
	public String getCurrentCareerCode() {
		return currentCareerCode;
	}
	
	/**
	* @description 设置当前部门对应的事业部门code
	* @param currentCareerCode
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月28日 下午3:25:11
	*/
	public void setCurrentCareerCode(String currentCareerCode) {
		this.currentCareerCode = currentCareerCode;
	}
	
	/**
	* @description 获取当前部门对应的事业部门Name
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月28日 下午3:25:24
	*/
	public String getCurrentCareerName() {
		return currentCareerName;
	}
	
	/**
	* @description 设置当前部门对应的事业部门Name
	* @param currentCareerName
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月28日 下午3:25:44
	*/
	public void setCurrentCareerName(String currentCareerName) {
		this.currentCareerName = currentCareerName;
	}
	
	/**
	* @description 获取 是否车队
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月28日 下午3:32:40
	*/
	public String getIsTransTeam() {
		return isTransTeam;
	}
	
	/**
	* @description 设置 是否车队
	* @param isTransTeam
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2013年11月28日 下午3:33:06
	*/
	public void setIsTransTeam(String isTransTeam) {
		this.isTransTeam = isTransTeam;
	}
	
	
	
	
}
