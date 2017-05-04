package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 快递业务 查询财务单据 返回的集合DTO
 * @author 243921-zhangtingting
 * @date 2016-04-14 下午03:20:22
 *
 */
public class EcsBillReceivableResponseDto implements Serializable {

	//序列号
	private static final long serialVersionUID = 1L;
	
	//结果
	private int result;
	
	//处理信息说明
	private String message;
	
	//应收单信息集合
	private List<EcsBillReceivableDto> list;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<EcsBillReceivableDto> getList() {
		return list;
	}

	public void setList(List<EcsBillReceivableDto> list) {
		this.list = list;
	}
}
