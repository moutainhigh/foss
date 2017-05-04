package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;

/**
 * FOSS调用CUBC返回的财务数据
 * 
 * @ClassName: FossBillReceivableResponseDto
 * @Description:
 * @author: 353654
 * @date: 2016年11月7日 上午10:55:54
 *
 */
public class FossBillReceivableResponseDO implements Serializable {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 6050634125130255796L;
	/**
	 * 客户编码:新增需求,无法修改客户问题
	 */
	private String customerCode;
	/**
	 * 客户名称:新增需求,无法修改客户问题
	 */
	private String customerName;
	/**
	 * 应收单信息集合（）
	 */
	private List<BillReceivableEntity>  list;
	/**
	 * 结果 Y or N
	 */
	private String result;
	/**
	 * 消息
	 */
	private String message;

	/**
	 * 获取应收单信息集合（）
	 * 
	 * @return list 应收单信息集合（）
	 */
	public List<BillReceivableEntity> getList() {
		return list;
	}

	/**
	 * 设置应收单信息集合（）
	 * 
	 * @param list
	 *            应收单信息集合（）
	 */
	public void setList(List<BillReceivableEntity> list) {
		this.list = list;
	}

	/**
	 * 获取结果YorN
	 * 
	 * @return result 结果YorN
	 */
	public String getResult() {
		return result;
	}

	/**
	 * 获取消息
	 * 
	 * @return message 消息
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置结果YorN
	 * 
	 * @param result
	 *            结果YorN
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * 设置消息
	 * 
	 * @param message
	 *            消息
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	

}
