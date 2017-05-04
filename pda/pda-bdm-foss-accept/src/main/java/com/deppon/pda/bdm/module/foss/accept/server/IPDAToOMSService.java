package com.deppon.pda.bdm.module.foss.accept.server;


import com.deppon.foss.framework.service.IService;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.PdaOrderOmsDto;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.PdaOrderWorkerOmsDto;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.QryAcctOrderEntity;

/**
 * PDA同步信息至OMS
 * @param request
 * @return
 * @author 245955
 */
//@RequestMapping("/OMS")
public interface IPDAToOMSService extends IService{
	/**
	 * 快递下拉订单数据
	 * @param request
     * @return
     * @author 245955
	 */
	//@RequestMapping(value = "/queryAcctOrder", method = RequestMethod.POST)
	public String queryAcctOrder(QryAcctOrderEntity entity);
	
	/**
	 * 快递开启暂停接口
	 * @param request
     * @return
     * @author 245955
	 */
	public String pdaSendWorkerToOMS(PdaOrderWorkerOmsDto pdaOrderWorkerOmsDto);
	
	/**
	 * 快递订单退回/转发接口
	 * @param request
     * @return
     * @author 245955
	 */
	public String pdaBackOrderOMS(PdaOrderOmsDto backOrder);
	
	/**
	 * 快递订单已读接口
	 * @param request
     * @return
     * @author 245955
	 */
	public String pdaReadOrderOMS(PdaOrderOmsDto readOrder);
	/**
	 * 快递订单已读接口
	 * @param request
     * @return
     * @author 245955
	 */
	public String pdaRvcOrderOMS(PdaOrderOmsDto rvcOrder);
	
	/**
	 * 快递查询转发人员集合
	 */
	public String pdaKdQueryForwardListByDriverCodeOMS(String userCode);
	
	/**
	 * 快递查询开启暂停状态
	 */
	public String pdaKdQueryWorkerStatusByDriverCodeOMS(String userCode);
}
