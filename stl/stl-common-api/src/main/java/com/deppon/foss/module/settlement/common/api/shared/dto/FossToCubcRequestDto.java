package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/**
 * 1、foss调用cubc接口请求参数模型；2、结清货款调用 存放运单信息和到达部门信息Dto
 * 
 * 到付运费转临欠/月结
 *
 * @author 378375
 * @date
 * 
 */
@SuppressWarnings("serial")
public class FossToCubcRequestDto implements Serializable {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 378375001L;
	
	/**
	 * 
	 * 接送货1、通知客户；2、结清货款调用 存放运单信息和到达部门信息Dto
	 * 
	 * 到付运费转临欠/月结
	 * @author dp-wujiangtao
	 * @date 2012-10-15 上午9:10:52
	 * @since
	 * @version
	 */
	private PaymentSettlementDto dto; 
	
	/**
	 * 当前系统信息
	 */
	private CurrentInfo currentInfo;
	/************************* CUBC新增 ****************************************/
	/**
     * 是否是合伙人运单  默认是N
     */
	private String isPtp = "N";
    
	public String getIsPtp() {
		return isPtp;
	}
	
	public void setIsPtp(String isPtp) {
		this.isPtp = isPtp;
	}
	
    /**
     * 原结清Dto
     * @return
     */
	public PaymentSettlementDto getDto() {
		return dto;
	}
    /**
     * 原结清Dto
     * @param dto
     */
	public void setDto(PaymentSettlementDto dto) {
		this.dto = dto;
	}

	public CurrentInfo getCurrentInfo() {
		return currentInfo;
	}

	public void setCurrentInfo(CurrentInfo currentInfo) {
		this.currentInfo = currentInfo;
	}
    
	
}
