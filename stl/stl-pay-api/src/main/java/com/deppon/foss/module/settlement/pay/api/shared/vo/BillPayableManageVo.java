package com.deppon.foss.module.settlement.pay.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageResultDto;

/**
 * 查询应付单Action
 * 
 * @author 045738-foss-maojianqiang
 * @date 2012-11-16 下午4:24:03
 */
public class BillPayableManageVo implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 6250425463486513629L;

	/**
	 * 声明查询dto
	 */
	private BillPayableManageDto manageDto = new BillPayableManageDto();

	/**
	 * 应付单列表
	 */
	private BillPayableManageResultDto resultDto = new BillPayableManageResultDto();

	private String payToSystem;
	//判断
	private String isNot;
	
	public String getPayToSystem() {
		return payToSystem;
	}

	public void setPayToSystem(String payToSystem) {
		this.payToSystem = payToSystem;
	}
	
	/**
	 * @return manageDto
	 */
	public BillPayableManageDto getManageDto() {
		return manageDto;
	}

	
	/**
	 * @param manageDto
	 */
	public void setManageDto(BillPayableManageDto manageDto) {
		this.manageDto = manageDto;
	}

	
	/**
	 * @return resultDto
	 */
	public BillPayableManageResultDto getResultDto() {
		return resultDto;
	}

	
	/**
	 * @param resultDto
	 */
	public void setResultDto(BillPayableManageResultDto resultDto) {
		this.resultDto = resultDto;
	}

	public String getIsNot() {
		return isNot;
	}

	public void setIsNot(String isNot) {
		this.isNot = isNot;
	}
	

	

}
