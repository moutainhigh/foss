package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;

/**
 * 预收冲应收返回用实体
 * 
 * @author foss-qiaolifeng
 * @date 2012-10-25 下午2:42:01
 */
public class BillDepWriteoffBillRecDto implements Serializable {

	/**
	 * 预收冲应收Dto参数实体序列号
	 */
	private static final long serialVersionUID = -6595412583022931803L;

	/**
	 * 预收单列表
	 */
	private List<BillDepositReceivedEntity> billDepositreceivedEntityList = new ArrayList<BillDepositReceivedEntity>();

	/**
	 * 应收单列表
	 */
	private List<BillReceivableEntity> billReceivableEntityList = new ArrayList<BillReceivableEntity>();

	/**
	 * 预收单总条数
	 */
	private int totalNumDep;

	/**
	 * 预收单总金额
	 */
	private BigDecimal totalAmountDep;

	/**
	 * 预收单未核消总金额
	 */
	private BigDecimal unverifyTotalAmountDep;

	/**
	 * 预收单已核消总金额
	 */
	private BigDecimal verifyTotalAmountDep;

	/**
	 * 应收单总条数
	 */
	private int totalNumRec;

	/**
	 * 应收单总金额
	 */
	private BigDecimal totalAmountRec;

	/**
	 * 应收单未核消总金额
	 */
	private BigDecimal unverifyTotalAmountRec;

	/**
	 * 应收单已核消总金额
	 */
	private BigDecimal verifyTotalAmountRec;

	
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

	
	/**
	 * @return 
		totalNumDep
	 */
	public int getTotalNumDep() {
		return totalNumDep;
	}

	
	/**
	 * @param 
		totalNumDep
	 */
	public void setTotalNumDep(int totalNumDep) {
		this.totalNumDep = totalNumDep;
	}

	
	/**
	 * @return 
		totalAmountDep
	 */
	public BigDecimal getTotalAmountDep() {
		return totalAmountDep;
	}

	
	/**
	 * @param 
		totalAmountDep
	 */
	public void setTotalAmountDep(BigDecimal totalAmountDep) {
		this.totalAmountDep = totalAmountDep;
	}

	
	/**
	 * @return 
		unverifyTotalAmountDep
	 */
	public BigDecimal getUnverifyTotalAmountDep() {
		return unverifyTotalAmountDep;
	}

	
	/**
	 * @param 
		unverifyTotalAmountDep
	 */
	public void setUnverifyTotalAmountDep(BigDecimal unverifyTotalAmountDep) {
		this.unverifyTotalAmountDep = unverifyTotalAmountDep;
	}

	
	/**
	 * @return 
		verifyTotalAmountDep
	 */
	public BigDecimal getVerifyTotalAmountDep() {
		return verifyTotalAmountDep;
	}

	
	/**
	 * @param 
		verifyTotalAmountDep
	 */
	public void setVerifyTotalAmountDep(BigDecimal verifyTotalAmountDep) {
		this.verifyTotalAmountDep = verifyTotalAmountDep;
	}

	
	/**
	 * @return 
		totalNumRec
	 */
	public int getTotalNumRec() {
		return totalNumRec;
	}

	
	/**
	 * @param 
		totalNumRec
	 */
	public void setTotalNumRec(int totalNumRec) {
		this.totalNumRec = totalNumRec;
	}

	
	/**
	 * @return 
		totalAmountRec
	 */
	public BigDecimal getTotalAmountRec() {
		return totalAmountRec;
	}

	
	/**
	 * @param 
		totalAmountRec
	 */
	public void setTotalAmountRec(BigDecimal totalAmountRec) {
		this.totalAmountRec = totalAmountRec;
	}

	
	/**
	 * @return 
		unverifyTotalAmountRec
	 */
	public BigDecimal getUnverifyTotalAmountRec() {
		return unverifyTotalAmountRec;
	}

	
	/**
	 * @param 
		unverifyTotalAmountRec
	 */
	public void setUnverifyTotalAmountRec(BigDecimal unverifyTotalAmountRec) {
		this.unverifyTotalAmountRec = unverifyTotalAmountRec;
	}

	
	/**
	 * @return 
		verifyTotalAmountRec
	 */
	public BigDecimal getVerifyTotalAmountRec() {
		return verifyTotalAmountRec;
	}

	
	/**
	 * @param 
		verifyTotalAmountRec
	 */
	public void setVerifyTotalAmountRec(BigDecimal verifyTotalAmountRec) {
		this.verifyTotalAmountRec = verifyTotalAmountRec;
	}

	

}
