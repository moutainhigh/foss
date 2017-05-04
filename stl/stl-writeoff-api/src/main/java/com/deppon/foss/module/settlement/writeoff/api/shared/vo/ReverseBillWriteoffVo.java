package com.deppon.foss.module.settlement.writeoff.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.ReverseBillWriteoffDto;

/**
 * 反核消时，传递参数并返回核销单明细VO类
 * 
 * @date 2012-10-24 上午11:08:36
 */
public class ReverseBillWriteoffVo implements Serializable {

	/**
	 * 反核消时，传递参数并返回核销单明细VO类序列号
	 */
	private static final long serialVersionUID = -2314549576498651764L;

	/**
	 * 反核消时，传递参数Dto类
	 */
	private ReverseBillWriteoffDto reverseBillWriteoffDto;

	/**
	 * 核销单明细实体
	 */
	private BillWriteoffEntity billWriteoffEntity;

	/**
	 * 核销单明细实体列表
	 */
	private List<BillWriteoffEntity> billWriteoffEntityList = new ArrayList<BillWriteoffEntity>();

	/**
	 * 反核销总条数
	 */
	private Long writeoffTotalRows;

	/**
	 * 反核销总金额
	 */
	private BigDecimal writeoffTotalAmout;

	
	/**
	 * @return 
		reverseBillWriteoffDto
	 */
	public ReverseBillWriteoffDto getReverseBillWriteoffDto() {
		return reverseBillWriteoffDto;
	}

	
	/**
	 * @param 
		reverseBillWriteoffDto
	 */
	public void setReverseBillWriteoffDto(ReverseBillWriteoffDto reverseBillWriteoffDto) {
		this.reverseBillWriteoffDto = reverseBillWriteoffDto;
	}

	
	/**
	 * @return 
		billWriteoffEntity
	 */
	public BillWriteoffEntity getBillWriteoffEntity() {
		return billWriteoffEntity;
	}

	
	/**
	 * @param 
		billWriteoffEntity
	 */
	public void setBillWriteoffEntity(BillWriteoffEntity billWriteoffEntity) {
		this.billWriteoffEntity = billWriteoffEntity;
	}

	
	/**
	 * @return 
		billWriteoffEntityList
	 */
	public List<BillWriteoffEntity> getBillWriteoffEntityList() {
		return billWriteoffEntityList;
	}

	
	/**
	 * @param 
		billWriteoffEntityList
	 */
	public void setBillWriteoffEntityList(List<BillWriteoffEntity> billWriteoffEntityList) {
		this.billWriteoffEntityList = billWriteoffEntityList;
	}

	
	/**
	 * @return 
		writeoffTotalRows
	 */
	public Long getWriteoffTotalRows() {
		return writeoffTotalRows;
	}

	
	/**
	 * @param 
		writeoffTotalRows
	 */
	public void setWriteoffTotalRows(Long writeoffTotalRows) {
		this.writeoffTotalRows = writeoffTotalRows;
	}

	
	/**
	 * @return 
		writeoffTotalAmout
	 */
	public BigDecimal getWriteoffTotalAmout() {
		return writeoffTotalAmout;
	}

	
	/**
	 * @param 
		writeoffTotalAmout
	 */
	public void setWriteoffTotalAmout(BigDecimal writeoffTotalAmout) {
		this.writeoffTotalAmout = writeoffTotalAmout;
	}

	
	

}
