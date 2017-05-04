package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.WKTfrBillEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.RentalMarkEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.RentalMarkDto;

/**
 * @title: RentalMarkVo 
 * @description： 租车标记VO
 * @author： wuyaqing
 * @date： 2014-5-19 下午3:23:34
 */
public class RentalMarkVo implements Serializable {

	/**
	 * 序列号
	 */
	
	private static final long serialVersionUID = -2675622414307981170L;
	
	private List<RentalMarkEntity> rentalMarkEntityList;//租车标记查询数据Entity
	//313352 gouyangyang
	private List<WKTfrBillEntity> expressMarkEntityList; // 租车标记查询快递交接单数据Entity
	
	private RentalMarkDto rentalMarkDto;//查询的条件dto
	
	private String inviteVehicleNo;//约车编号
	
	private String inviteState;//约车状态
	
	private String acceptPerson;//约车受理人
	
	private String acceptPersonCode;//约车受理人编号
	
	private String isrepeatMark;//是否多次标记
	
	private List<String> origOrgCodeList;//出发部门编码
	
	private List<String> destOrgCodeList;//到达部门编码
	
	private String kmsNum;//公里数
	
    private String rentalAmount;//约车金额310248
    
    private String singleSum; //单票金额 332219
    
	
	public String getRentalAmount() {
		return rentalAmount;
	}

	public void setRentalAmount(String rentalAmount) {
		this.rentalAmount = rentalAmount;
	}
	
	public List<String> getOrigOrgCodeList() {
		return origOrgCodeList;
	}

	public void setOrigOrgCodeList(List<String> origOrgCodeList) {
		this.origOrgCodeList = origOrgCodeList;
	}

	public List<String> getDestOrgCodeList() {
		return destOrgCodeList;
	}

	public void setDestOrgCodeList(List<String> destOrgCodeList) {
		this.destOrgCodeList = destOrgCodeList;
	}

	public String getInviteState() {
		return inviteState;
	}

	public void setInviteState(String inviteState) {
		this.inviteState = inviteState;
	}

	public String getIsrepeatMark() {
		return isrepeatMark;
	}

	public void setIsrepeatMark(String isrepeatMark) {
		this.isrepeatMark = isrepeatMark;
	}

	public String getAcceptPersonCode() {
		return acceptPersonCode;
	}

	public void setAcceptPersonCode(String acceptPersonCode) {
		this.acceptPersonCode = acceptPersonCode;
	}

	public String getAcceptPerson() {
		return acceptPerson;
	}

	public void setAcceptPerson(String acceptPerson) {
		this.acceptPerson = acceptPerson;
	}

	public String getInviteVehicleNo() {
		return inviteVehicleNo;
	}

	public void setInviteVehicleNo(String inviteVehicleNo) {
		this.inviteVehicleNo = inviteVehicleNo;
	}

	public RentalMarkDto getRentalMarkDto() {
		return rentalMarkDto;
	}

	public void setRentalMarkDto(RentalMarkDto rentalMarkDto) {
		this.rentalMarkDto = rentalMarkDto;
	}


	public List<RentalMarkEntity> getRentalMarkEntityList() {
		return rentalMarkEntityList;
	}

	public List<WKTfrBillEntity> getExpressMarkEntityList() {
		return expressMarkEntityList;
	}

	public void setExpressMarkEntityList(List<WKTfrBillEntity> expressMarkEntityList) {
		this.expressMarkEntityList = expressMarkEntityList;
	}

	public void setRentalMarkEntityList(List<RentalMarkEntity> rentalMarkEntityList) {
		this.rentalMarkEntityList = rentalMarkEntityList;
	}

	public String getKmsNum() {
		return kmsNum;
	}

	public void setKmsNum(String kmsNum) {
		this.kmsNum = kmsNum;
	}

	public String getSingleSum() {
		return singleSum;
	}

	public void setSingleSum(String singleSum) {
		this.singleSum = singleSum;
	}
}