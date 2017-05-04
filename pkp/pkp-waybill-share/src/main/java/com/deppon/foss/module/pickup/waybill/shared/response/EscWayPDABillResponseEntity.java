package com.deppon.foss.module.pickup.waybill.shared.response;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaResultBillCalculateDto;

/**
 * 价格计算响应实体
 * @author Foss-308595-GELL
 *
 */
@XmlRootElement(name="EscWayPDABillResponseEntity")
public class EscWayPDABillResponseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 11L;

	/**
     * 成功标志 0:失败；1：成功
     */
    private String success;
    /**
     * 失败原因
     */
    private String message;
    
    /**
     * 计算返回信息
     */
    List<PdaResultBillCalculateDto> pdaResultBillCalculateDto;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<PdaResultBillCalculateDto> getPdaResultBillCalculateDto() {
		return pdaResultBillCalculateDto;
	}

	public void setPdaResultBillCalculateDto(
			List<PdaResultBillCalculateDto> pdaResultBillCalculateDto) {
		this.pdaResultBillCalculateDto = pdaResultBillCalculateDto;
	}
}
