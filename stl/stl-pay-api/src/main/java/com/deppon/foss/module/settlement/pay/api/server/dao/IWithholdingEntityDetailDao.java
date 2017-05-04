package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.domain.WithholdingEntityDetail;

/**
 * @author 045738
 * @描述：预提记录Dao
 */
public interface IWithholdingEntityDetailDao {
	/**
	 * 功能：插入预提明细
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-14
	 * @return
	 */
	public int insert(WithholdingEntityDetail record);
	
	/**
	 * 功能：根据工作流号查询预提明细
	 * @date 2014-8-15
	 * @return
	 */
	public List<String> selectRentCarNoByWorkFlowNo(String workFlowNo);
}
