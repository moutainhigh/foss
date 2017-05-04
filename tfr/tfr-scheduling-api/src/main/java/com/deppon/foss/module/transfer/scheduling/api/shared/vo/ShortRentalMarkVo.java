package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ShortRentalMarkEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ShortRentalMarkDto;

/**
 * @title: ShortRentalMarkVo 
 * @description： 租车标记VO
 * @author： ruilibao
 * @date： 2016-12-10 下午3:23:34
 */
public class ShortRentalMarkVo implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2675622414307981170L;
	
	//短途租车标记查询数据Entity
	private List<ShortRentalMarkEntity> shortRentalMarkEntityList;
	
	//查询条件DTO
	private ShortRentalMarkDto shortRentalMarkDto;//查询的条件dto
	
	//约车编号
	private String inviteVehicleNo;
	
	//约车状态
	private String inviteState;
	
	//约车受理人
	private String acceptPerson;
	
	//约车受理人编号
	private String acceptPersonCode;
	
	//是否多次标记
	private String isrepeatMark;
	
	//出发部门编码
	private List<String> origOrgCodeList;
	
	//到达部门编码
	private List<String> destOrgCodeList;
	
	//公里数
	private String kmsNum;
	
	//约车金额
    private String rentalAmount;

	/**
	 * @return the shortRentalMarkEntityList
	 */
	public List<ShortRentalMarkEntity> getShortRentalMarkEntityList() {
		return shortRentalMarkEntityList;
	}

	/**
	 * @param shortRentalMarkEntityList the shortRentalMarkEntityList to set
	 */
	public void setShortRentalMarkEntityList(List<ShortRentalMarkEntity> shortRentalMarkEntityList) {
		this.shortRentalMarkEntityList = shortRentalMarkEntityList;
	}

	/**
	 * @return the shortRentalMarkDto
	 */
	public ShortRentalMarkDto getShortRentalMarkDto() {
		return shortRentalMarkDto;
	}

	/**
	 * @param shortRentalMarkDto the shortRentalMarkDto to set
	 */
	public void setShortRentalMarkDto(ShortRentalMarkDto shortRentalMarkDto) {
		this.shortRentalMarkDto = shortRentalMarkDto;
	}

	/**
	 * @return the inviteVehicleNo
	 */
	public String getInviteVehicleNo() {
		return inviteVehicleNo;
	}

	/**
	 * @param inviteVehicleNo the inviteVehicleNo to set
	 */
	public void setInviteVehicleNo(String inviteVehicleNo) {
		this.inviteVehicleNo = inviteVehicleNo;
	}

	/**
	 * @return the inviteState
	 */
	public String getInviteState() {
		return inviteState;
	}

	/**
	 * @param inviteState the inviteState to set
	 */
	public void setInviteState(String inviteState) {
		this.inviteState = inviteState;
	}

	/**
	 * @return the acceptPerson
	 */
	public String getAcceptPerson() {
		return acceptPerson;
	}

	/**
	 * @param acceptPerson the acceptPerson to set
	 */
	public void setAcceptPerson(String acceptPerson) {
		this.acceptPerson = acceptPerson;
	}

	/**
	 * @return the acceptPersonCode
	 */
	public String getAcceptPersonCode() {
		return acceptPersonCode;
	}

	/**
	 * @param acceptPersonCode the acceptPersonCode to set
	 */
	public void setAcceptPersonCode(String acceptPersonCode) {
		this.acceptPersonCode = acceptPersonCode;
	}

	/**
	 * @return the isrepeatMark
	 */
	public String getIsrepeatMark() {
		return isrepeatMark;
	}

	/**
	 * @param isrepeatMark the isrepeatMark to set
	 */
	public void setIsrepeatMark(String isrepeatMark) {
		this.isrepeatMark = isrepeatMark;
	}

	/**
	 * @return the origOrgCodeList
	 */
	public List<String> getOrigOrgCodeList() {
		return origOrgCodeList;
	}

	/**
	 * @param origOrgCodeList the origOrgCodeList to set
	 */
	public void setOrigOrgCodeList(List<String> origOrgCodeList) {
		this.origOrgCodeList = origOrgCodeList;
	}

	/**
	 * @return the destOrgCodeList
	 */
	public List<String> getDestOrgCodeList() {
		return destOrgCodeList;
	}

	/**
	 * @param destOrgCodeList the destOrgCodeList to set
	 */
	public void setDestOrgCodeList(List<String> destOrgCodeList) {
		this.destOrgCodeList = destOrgCodeList;
	}

	/**
	 * @return the kmsNum
	 */
	public String getKmsNum() {
		return kmsNum;
	}

	/**
	 * @param kmsNum the kmsNum to set
	 */
	public void setKmsNum(String kmsNum) {
		this.kmsNum = kmsNum;
	}

	/**
	 * @return the rentalAmount
	 */
	public String getRentalAmount() {
		return rentalAmount;
	}

	/**
	 * @param rentalAmount the rentalAmount to set
	 */
	public void setRentalAmount(String rentalAmount) {
		this.rentalAmount = rentalAmount;
	}
	
}
