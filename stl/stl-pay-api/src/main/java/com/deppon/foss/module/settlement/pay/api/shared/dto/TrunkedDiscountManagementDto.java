package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.domain.TrunkDiscountManEntity;

/**
 * 零担事后折折扣 dto
 * @ClassName: TrunkedDiscountManagementDto
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-9-22 上午10:50:54
 * 
 */
public class TrunkedDiscountManagementDto {
	/**
	 * 零担事后折折扣
	 */
	private List<TrunkDiscountManEntity> trunkDiscountManagementDList;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 运单号集合
	 */
	private List<String> numbers;
	/**
	 * 期间开始日期
	 */
	private Date periodBeginDate;
	/**
	 * 期间结束日期
	 */
	private Date periodEndDate;
	/**
	 * 分页数
	 */
	private Integer pageNum;
	/**
	 * 客户编码
	 */
	private String customerNo;

	/**
	 * 总行数
	 */
	private long count;

	public List<TrunkDiscountManEntity> getTrunkDiscountManagementDList() {
		return trunkDiscountManagementDList;
	}

	public void setTrunkDiscountManagementDList(
			List<TrunkDiscountManEntity> trunkDiscountManagementDList) {
		this.trunkDiscountManagementDList = trunkDiscountManagementDList;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Date getPeriodBeginDate() {
		return periodBeginDate;
	}

	public List<String> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<String> numbers) {
		this.numbers = numbers;
	}

	public void setPeriodBeginDate(Date periodBeginDate) {
		this.periodBeginDate = periodBeginDate;
	}

	public Date getPeriodEndDate() {
		return periodEndDate;
	}

	public void setPeriodEndDate(Date periodEndDate) {
		this.periodEndDate = periodEndDate;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "TrunkedDiscountManagementDto [trunkDiscountManagementDList="
				+ trunkDiscountManagementDList + ", numbers=" + numbers
				+ ", periodBeginDate=" + periodBeginDate + ", periodEndDate="
				+ periodEndDate + ", pageNum=" + pageNum + ", customerNo="
				+ customerNo + ", count=" + count + "]";
	}

}
