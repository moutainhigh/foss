package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.VTSResverSettleRepaymentEntity;

/**
 * @author 218392 zhangyongxue
 *  2016-06-16 19:53:20 
 *	FOSS结算开发组
 * 自动反签收响应实体
 */
public class VTSReverseSettlResponse implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 是否成功
	 */
	private boolean ifSuccess;
	
	/**
	 * 错误原因
	 */
	private String errorMsg;
	
	/**
	 * VTS自动反签收界面需要的参数List<VTSResverSettleRepaymentEntity>
	 * @return
	 */
	private List<VTSResverSettleRepaymentEntity> resverSettleRepaymentEntity;
	
	public boolean isIfSuccess() {
		return ifSuccess;
	}

	public void setIfSuccess(boolean ifSuccess) {
		this.ifSuccess = ifSuccess;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public List<VTSResverSettleRepaymentEntity> getResverSettleRepaymentEntity() {
		return resverSettleRepaymentEntity;
	}

	public void setResverSettleRepaymentEntity(
			List<VTSResverSettleRepaymentEntity> resverSettleRepaymentEntity) {
		this.resverSettleRepaymentEntity = resverSettleRepaymentEntity;
	}


	
}
