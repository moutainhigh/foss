/**
 * 
 */
package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.domain.WithholdingEntityDetail;

/**
 * @author 045738
 * @预提明细实体
 */
public interface IWithholdingEntityDetailService {
	/**
	 * 功能：插入预提明细
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-14
	 * @return
	 */
	public int add(WithholdingEntityDetail entity);
	
	/**
	 * 功能：根据工作流号查询预提明细
	 * @date 2014-8-15
	 * @return
	 */
	public List<String> selectRentCarNoByWorkFlowNo(String workFlowNo);
}
