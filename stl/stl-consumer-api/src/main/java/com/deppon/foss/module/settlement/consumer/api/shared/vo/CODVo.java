package com.deppon.foss.module.settlement.consumer.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;

/**
 * 
 * 代收货款出发申请VO
 * 
 * @author dp-zengbinwen
 * @date 2012-10-15 下午2:05:20
 */
public class CODVo implements Serializable {

	private static final long serialVersionUID = -7102747143818082546L;

	/**
	 * 代收货款状态
	 */
	private String status;
	
	/**
	 * @author 218392 zhangyongxue 2015-11-09 08:48:10
	 * 业务日期起
	 */
	private Date timeBegin;
	
	/**
	 * @author 218392 zhangyongxue 2015-11-09 08:48:10
	 * 业务日期止
	 */
	private Date timeEnd;
	
	/**
	 * @author 218392 zhangyongxue 2015-11-09 08:48:10
	 * 收款方
	 */
	private String cusAccountName;
	
	/**
	 * @author 218392 zhangyongxue 2015-11-09 14:39:10
	 * 是否按业务日期查询
	 */
	private String timeType;
	
	/**
	 * @author 218392 zhangyongxue  2015-11-09 14:39:10
	 */
	private List<String> statuses;

	/**
	 * 运单号
	 */
	private String waybillNos;

	/**
	 * 运单号数组
	 */
	private String[] waybillNoArr;
	
	/**
	 * 运单号集合
	 */
	private List<String> waybillNoList;
	
	/**
	 * 冻结成功后运单号集合
	 */
	private List<String> waybillNoFreezeSuccessList;
	
	/**
	 * 冻结失败的运单号集合
	 */
	private List<String> waybillNoFreezeErrorList;

	/**
	 * 查询条件，查询所有还是按单号查询
	 */
	private String queryCondition;
	
	/**
	 * 判断用于是否全选后操作
	 */
	private String allCheckBoxStatus; 

	/**
	 * 代收货款出发申请信息
	 */
	private List<CODDto> cods;

	/**
	 * 代收货款出发申请总条数
	 */
	private int totalRecords;

	/**
	 * 截止签收日期
	 */
	private Date endSignDate;

	/**
	 * 退单类型
	 */
	private String codType;

	/**
	 * 开户行
	 */
	private String bank;
	
	/**
	 * 开户行集合
	 */
	private List<String> bankList;

	/**
	 * 对公对私标志
	 */
	private String publicPrivateFlag;

	/**
	 * 代收货款账号
	 */
	private String payeeAccount;

	/**
	 * 省份
	 */
	private String provinceCode;

	/**
	 * 城市
	 */
	private String cityCode;
	
	/**
	 * 省份
	 */
	private String province;

	/**
	 * 城市
	 */
	private String city;

	/**
	 * 银行行号
	 */
	private String bankCode;

	/**
	 * 开户行支行编码
	 */
	private String bankSubbranchCode;
	
	/**
	 * 开户行支行
	 */
	private String bankSubbranch;

	/**
	 * 收款人
	 */
	private String payeeName;

	/**
	 * 代收货款CODEntity ID数组
	 */
	private String[] entityIds;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 退回原因
	 */
	private String refundNotes;

	/**
	 * id
	 */
	private String id;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 收款人电话
	 */
	private String payeePhone;

	/**
	 * 发货客户与收款人关系
	 */
	private String payeeAndConsignor;
	
	/**
	 * 是否支持即日退(Y,N)
	 */
	private String intraDayType;
	
	/**
	 * 总条数
	 */
	private Long totalCount;
	
	/**
	 * 合计总金额
	 */
	private BigDecimal totalAmount;
	
	/**
	 * 资金部冻结总条数
	 */
	private Long freezeTotalCount;
	
	/**
	 * 资金部冻结总金额
	 */
	private BigDecimal freezeTotalAmount;
	
	/**
	 * 批次号
	 */
	private String batchNumber;
	
	/**
	 * 268217
	 * 冻结失败的运单号集合
	 */
	private List<String> waybillNoErrorList;

	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return waybillNos
	 */
	public String getWaybillNos() {
		return waybillNos;
	}

	/**
	 * @param waybillNos
	 */
	public void setWaybillNos(String waybillNos) {
		this.waybillNos = waybillNos;
	}

	/**
	 * @return waybillNoArr
	 */
	public String[] getWaybillNoArr() {
		return waybillNoArr;
	}

	/**
	 * @param waybillNoArr
	 */
	public void setWaybillNoArr(String[] waybillNoArr) {
		this.waybillNoArr = waybillNoArr;
	}

	/**
	 * @return queryCondition
	 */
	public String getQueryCondition() {
		return queryCondition;
	}

	/**
	 * @param queryCondition
	 */
	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}

	/**
	 * @return cods
	 */
	public List<CODDto> getCods() {
		return cods;
	}

	/**
	 * @param cods
	 */
	public void setCods(List<CODDto> cods) {
		this.cods = cods;
	}

	/**
	 * @return totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param totalRecords
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * @return endSignDate
	 */
	public Date getEndSignDate() {
		return endSignDate;
	}

	/**
	 * @param endSignDate
	 */
	public void setEndSignDate(Date endSignDate) {
		this.endSignDate = endSignDate;
	}

	/**
	 * @return codType
	 */
	public String getCodType() {
		return codType;
	}

	/**
	 * @param codType
	 */
	public void setCodType(String codType) {
		this.codType = codType;
	}

	/**
	 * @return bank
	 */
	public String getBank() {
		return bank;
	}

	/**
	 * @param bank
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * @return publicPrivateFlag
	 */
	public String getPublicPrivateFlag() {
		return publicPrivateFlag;
	}

	/**
	 * @param publicPrivateFlag
	 */
	public void setPublicPrivateFlag(String publicPrivateFlag) {
		this.publicPrivateFlag = publicPrivateFlag;
	}

	/**
	 * @return payeeAccount
	 */
	public String getPayeeAccount() {
		return payeeAccount;
	}

	/**
	 * @param payeeAccount
	 */
	public void setPayeeAccount(String payeeAccount) {
		this.payeeAccount = payeeAccount;
	}

	/**
	 * @return province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * @param bankCode
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * @return bankSubbranch
	 */
	public String getBankSubbranch() {
		return bankSubbranch;
	}

	/**
	 * @param bankSubbranch
	 */
	public void setBankSubbranch(String bankSubbranch) {
		this.bankSubbranch = bankSubbranch;
	}

	/**
	 * @return payeeName
	 */
	public String getPayeeName() {
		return payeeName;
	}

	/**
	 * @param payeeName
	 */
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	/**
	 * @return entityIds
	 */
	public String[] getEntityIds() {
		return entityIds;
	}

	/**
	 * @param entityIds
	 */
	public void setEntityIds(String[] entityIds) {
		this.entityIds = entityIds;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return refundNotes
	 */
	public String getRefundNotes() {
		return refundNotes;
	}

	/**
	 * @param refundNotes
	 */
	public void setRefundNotes(String refundNotes) {
		this.refundNotes = refundNotes;
	}

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return payeePhone
	 */
	public String getPayeePhone() {
		return payeePhone;
	}

	/**
	 * @param payeePhone
	 */
	public void setPayeePhone(String payeePhone) {
		this.payeePhone = payeePhone;
	}

	/**
	 * @return payeeAndConsignor
	 */
	public String getPayeeAndConsignor() {
		return payeeAndConsignor;
	}

	/**
	 * @param payeeAndConsignor
	 */
	public void setPayeeAndConsignor(String payeeAndConsignor) {
		this.payeeAndConsignor = payeeAndConsignor;
	}

	/**
	 * @return intraDayType
	 */
	public String getIntraDayType() {
		return intraDayType;
	}

	/**
	 * @param  intraDayType  
	 */
	public void setIntraDayType(String intraDayType) {
		this.intraDayType = intraDayType;
	}

	/**
	 * @return provinceCode
	 */
	public String getProvinceCode() {
		return provinceCode;
	}

	/**
	 * @param  provinceCode  
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	/**
	 * @return cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param  cityCode  
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @return waybillNoList
	 */
	public List<String> getWaybillNoList() {
		return waybillNoList;
	}

	/**
	 * @param waybillNoList
	 */
	public void setWaybillNoList(List<String> waybillNoList) {
		this.waybillNoList = waybillNoList;
	}

	/**
	 * @return totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return totalCount
	 */
	public Long getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 */
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return freezeTotalCount
	 */
	public Long getFreezeTotalCount() {
		return freezeTotalCount;
	}

	/**
	 * @param freezeTotalCount
	 */
	public void setFreezeTotalCount(Long freezeTotalCount) {
		this.freezeTotalCount = freezeTotalCount;
	}

	/**
	 * @return freezeTotalAmount
	 */
	public BigDecimal getFreezeTotalAmount() {
		return freezeTotalAmount;
	}

	/**
	 * @param freezeTotalAmount
	 */
	public void setFreezeTotalAmount(BigDecimal freezeTotalAmount) {
		this.freezeTotalAmount = freezeTotalAmount;
	}

	/**
	 * @return allCheckBoxStatus
	 */
	public String getAllCheckBoxStatus() {
		return allCheckBoxStatus;
	}

	/**
	 * @param allCheckBoxStatus
	 */
	public void setAllCheckBoxStatus(String allCheckBoxStatus) {
		this.allCheckBoxStatus = allCheckBoxStatus;
	}

	/**
	 * @return waybillNoFreezeSuccessList
	 */
	public List<String> getWaybillNoFreezeSuccessList() {
		return waybillNoFreezeSuccessList;
	}

	/**
	 * @param waybillNoFreezeSuccessList
	 */
	public void setWaybillNoFreezeSuccessList(
			List<String> waybillNoFreezeSuccessList) {
		this.waybillNoFreezeSuccessList = waybillNoFreezeSuccessList;
	}

	/**
	 * @return waybillNoFreezeErrorList
	 */
	public List<String> getWaybillNoFreezeErrorList() {
		return waybillNoFreezeErrorList;
	}

	/**
	 * @param waybillNoFreezeErrorList
	 */
	public void setWaybillNoFreezeErrorList(List<String> waybillNoFreezeErrorList) {
		this.waybillNoFreezeErrorList = waybillNoFreezeErrorList;
	}

	/**
	 * @return batchNumber
	 */
	public String getBatchNumber() {
		return batchNumber;
	}

	/**
	 * @param batchNumber
	 */
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	/**
	 * @return bankList
	 */
	public List<String> getBankList() {
		return bankList;
	}

	/**
	 * @param bankList
	 */
	public void setBankList(List<String> bankList) {
		this.bankList = bankList;
	}

	/**
	 * @return bankSubbranchCode
	 */
	public String getBankSubbranchCode() {
		return bankSubbranchCode;
	}

	/**
	 * @param bankSubbranchCode
	 */
	public void setBankSubbranchCode(String bankSubbranchCode) {
		this.bankSubbranchCode = bankSubbranchCode;
	}

	public Date getTimeBegin() {
		return timeBegin;
	}

	public void setTimeBegin(Date timeBegin) {
		this.timeBegin = timeBegin;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getCusAccountName() {
		return cusAccountName;
	}

	public void setCusAccountName(String cusAccountName) {
		this.cusAccountName = cusAccountName;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public List<String> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<String> statuses) {
		this.statuses = statuses;
	}

	public List<String> getWaybillNoErrorList() {
		return waybillNoErrorList;
	}

	public void setWaybillNoErrorList(List<String> waybillNoErrorList) {
		this.waybillNoErrorList = waybillNoErrorList;
	}
	
}
