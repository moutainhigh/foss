package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.io.Serializable;
import java.util.List;


/**
 * FOSS调用CUBC返回的财务数据
 * 
 * @ClassName: FossBillReceivableResponseDto
 * @Description:
 *
 */
public class CubcBillReceivableResponse implements Serializable {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 6050634125130255796L;
	/**
	 * 应收单信息集合（）
	 */
	private List<Object>  list;
	/**
	 * 结果 Y 成功 or N失败
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
	public List<Object> getList() {
		return list;
	}

	/**
	 * 设置应收单信息集合（）
	 * 
	 * @param list
	 *            应收单信息集合（）
	 */
	public void setList(List<Object> list) {
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

}
