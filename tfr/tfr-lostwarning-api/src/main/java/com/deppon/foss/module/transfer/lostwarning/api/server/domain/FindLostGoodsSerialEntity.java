package com.deppon.foss.module.transfer.lostwarning.api.server.domain;

import java.io.Serializable;

/**
 * 上报丢货找到流水信息
 * 
 * 项目名称：tfr-lostwarning-api
 * 
 * 类名称：FindLostGoodsSerialEntity
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-8-7 下午3:00:16
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class FindLostGoodsSerialEntity implements Serializable {
	
	private static final long serialVersionUID = -8582615085794168562L;

	//流水号
	private String flowCode;
	
	//找到状态(1:未找到 2:已找到)
	private String findStatus;
	
	//是否有效(1:无效 2:有效)
	private String isEffective;

	public String getFlowCode() {
		return flowCode;
	}

	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}

	public String getFindStatus() {
		return findStatus;
	}

	public void setFindStatus(String findStatus) {
		this.findStatus = findStatus;
	}

	public String getIsEffective() {
		return isEffective;
	}

	public void setIsEffective(String isEffective) {
		this.isEffective = isEffective;
	}
	
}
