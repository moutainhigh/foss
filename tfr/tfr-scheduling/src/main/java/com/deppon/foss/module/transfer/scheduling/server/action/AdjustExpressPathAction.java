package com.deppon.foss.module.transfer.scheduling.server.action;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IAdjustExpressPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AdjustEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.SchedulingVO;

public class AdjustExpressPathAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4422123513465490421L;

	/**
	 * 调整货物走货路径VO
	 */
	private SchedulingVO schedulingVO = new SchedulingVO();
	
	private IAdjustExpressPathService adjustExpressPathService;
	
	@JSON
	//统计快递货区票数
	public String queryExpressWaybills()
	{
		
		try {
			/**
			 * 分页查询以线路为单位的信息
			 */
			List<AdjustEntity> goodsAreaList = adjustExpressPathService.queryExpressWaybills(schedulingVO.getAdjustEntity(), this.getLimit(), this.getStart());
			schedulingVO.setAdjustList(goodsAreaList);
			/**
			 * get count
			 */
			Long totalCount = adjustExpressPathService.getCount(schedulingVO.getAdjustEntity());
			this.setTotalCount(totalCount);
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		
	}
	//查询快递货区下快递运单
	@JSON
	public String queryExpressWaybillList()
	{
		try {
			/**
			 * 以线路中运单号为单位的信息
			 */
			List<AdjustEntity> wayBillList = adjustExpressPathService.queryExpressWaybillList(schedulingVO.getAdjustEntity());
			schedulingVO.setAdjustList(wayBillList);
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	} 
	
	//set

	public void setSchedulingVO(SchedulingVO schedulingVO) {
		this.schedulingVO = schedulingVO;
	}

	public SchedulingVO getSchedulingVO() {
		return schedulingVO;
	}
	
	public void setAdjustExpressPathService(
			IAdjustExpressPathService adjustExpressPathService) {
		this.adjustExpressPathService = adjustExpressPathService;
	}
	
	
	
}
