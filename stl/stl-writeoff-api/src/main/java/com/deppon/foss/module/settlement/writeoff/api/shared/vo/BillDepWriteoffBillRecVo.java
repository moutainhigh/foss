package com.deppon.foss.module.settlement.writeoff.api.shared.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepWriteoffBillRecDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepositReceivedDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillReceivableDto;

/**
 * 预收冲应收Vo参数实体
 * 
 * @author foss-qiaolifeng
 * @date 2012-10-22 下午5:43:12
 */
public class BillDepWriteoffBillRecVo implements Serializable {

	/**
	 * 预收冲应收Vo参数实体类序列号
	 */
	private static final long serialVersionUID = 2627063750840599353L;

	/**
	 * 预收冲应收Dto
	 */
	private BillDepWriteoffBillRecDto billDepWriteoffBillRecDto = new BillDepWriteoffBillRecDto();

	/**
	 * 预收Dto
	 */
	private BillReceivableDto billReceivableDto = new BillReceivableDto();

	/**
	 * 应收Dto
	 */
	private BillDepositReceivedDto billDepositReceivedDto = new BillDepositReceivedDto();

	/**
	 * 预收单
	 */
	private BillDepositReceivedEntity billDepositReceivedEntity;

	/**
	 * 预收单列表
	 */
	private List<BillDepositReceivedEntity> billDepositreceivedEntityList = new ArrayList<BillDepositReceivedEntity>();

	/**
	 * 应收单
	 */
	private BillReceivableEntity billReceivableEntity;

	/**
	 * 应收单列表
	 */
	private List<BillReceivableEntity> billReceivableEntityList = new ArrayList<BillReceivableEntity>();

	
	/**
	 * @return 
		billDepWriteoffBillRecDto
	 */
	public BillDepWriteoffBillRecDto getBillDepWriteoffBillRecDto() {
		return billDepWriteoffBillRecDto;
	}

	
	/**
	 * @param 
		billDepWriteoffBillRecDto
	 */
	public void setBillDepWriteoffBillRecDto(BillDepWriteoffBillRecDto billDepWriteoffBillRecDto) {
		this.billDepWriteoffBillRecDto = billDepWriteoffBillRecDto;
	}

	
	/**
	 * @return 
		billReceivableDto
	 */
	public BillReceivableDto getBillReceivableDto() {
		return billReceivableDto;
	}

	
	/**
	 * @param 
		billReceivableDto
	 */
	public void setBillReceivableDto(BillReceivableDto billReceivableDto) {
		this.billReceivableDto = billReceivableDto;
	}

	
	/**
	 * @return 
		billDepositReceivedDto
	 */
	public BillDepositReceivedDto getBillDepositReceivedDto() {
		return billDepositReceivedDto;
	}

	
	/**
	 * @param 
		billDepositReceivedDto
	 */
	public void setBillDepositReceivedDto(BillDepositReceivedDto billDepositReceivedDto) {
		this.billDepositReceivedDto = billDepositReceivedDto;
	}

	
	/**
	 * @return 
		billDepositReceivedEntity
	 */
	public BillDepositReceivedEntity getBillDepositReceivedEntity() {
		return billDepositReceivedEntity;
	}

	
	/**
	 * @param 
		billDepositReceivedEntity
	 */
	public void setBillDepositReceivedEntity(BillDepositReceivedEntity billDepositReceivedEntity) {
		this.billDepositReceivedEntity = billDepositReceivedEntity;
	}

	
	/**
	 * @return 
		billDepositreceivedEntityList
	 */
	public List<BillDepositReceivedEntity> getBillDepositreceivedEntityList() {
		return billDepositreceivedEntityList;
	}

	
	/**
	 * @param 
		billDepositreceivedEntityList
	 */
	public void setBillDepositreceivedEntityList(List<BillDepositReceivedEntity> billDepositreceivedEntityList) {
		this.billDepositreceivedEntityList = billDepositreceivedEntityList;
	}

	
	/**
	 * @return 
		billReceivableEntity
	 */
	public BillReceivableEntity getBillReceivableEntity() {
		return billReceivableEntity;
	}

	
	/**
	 * @param 
		billReceivableEntity
	 */
	public void setBillReceivableEntity(BillReceivableEntity billReceivableEntity) {
		this.billReceivableEntity = billReceivableEntity;
	}

	
	/**
	 * @return 
		billReceivableEntityList
	 */
	public List<BillReceivableEntity> getBillReceivableEntityList() {
		return billReceivableEntityList;
	}

	
	/**
	 * @param 
		billReceivableEntityList
	 */
	public void setBillReceivableEntityList(List<BillReceivableEntity> billReceivableEntityList) {
		this.billReceivableEntityList = billReceivableEntityList;
	}

	
	
	

}
