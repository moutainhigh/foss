/**
 * 
 */
package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.dao.IWithholdingEntityDetailDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IWithholdingEntityDetailService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.WithholdingEntityDetail;

/**
 * @author 045738
 *
 */
public class WithholdingEntityDetailService implements
		IWithholdingEntityDetailService {
	
	//注入预提明细dao
	private IWithholdingEntityDetailDao withholdingEntityDetailDao;
	/**
	 * 功能：插入预提明细
	 * @author 045738-foss-maojianqiang
	 * @date 2014-7-14
	 * @return 
	 */
	@Override
	public int add(WithholdingEntityDetail entity) {
		//预提明细
		if(entity==null || StringUtils.isBlank(entity.getWithholdingNo())){
			throw new SettlementException("传入的预提明细信息不能为空！");
		}
		//声明时间
		entity.setCreateTime(new Date());
		return this.withholdingEntityDetailDao.insert(entity);
	}
	
	/**
	 * 功能：根据工作流号查询预提明细
	 * @date 2014-8-15
	 * @return
	 */
	public List<String> selectRentCarNoByWorkFlowNo(String workFlowNo){
		//校验传入参数
		if(StringUtils.isBlank(workFlowNo)){
			throw new SettlementException("工作流号不能为空！");
		}
		return this.withholdingEntityDetailDao.selectRentCarNoByWorkFlowNo(workFlowNo);
	}
	
	public IWithholdingEntityDetailDao getWithholdingEntityDetailDao() {
		return withholdingEntityDetailDao;
	}
	public void setWithholdingEntityDetailDao(
			IWithholdingEntityDetailDao withholdingEntityDetailDao) {
		this.withholdingEntityDetailDao = withholdingEntityDetailDao;
	}
}
