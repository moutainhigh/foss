package com.deppon.foss.module.transfer.departure.server.action;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.departure.api.server.service.IAgingNodesLogService;
import com.deppon.foss.module.transfer.departure.api.shared.domain.AgingNodesLogEntity;
import com.deppon.foss.module.transfer.departure.api.shared.vo.AgingNodesLogVo;

public class AgingNodesLogAction extends AbstractAction{

	private static final long serialVersionUID = 1942911289923441899L;
	
	private AgingNodesLogVo agingNodesLogVo=new AgingNodesLogVo();
	
	private IAgingNodesLogService agingNodesLogService;
	

	public AgingNodesLogVo getAgingNodesLogVo() {
		return agingNodesLogVo;
	}

	public void setAgingNodesLogVo(AgingNodesLogVo agingNodesLogVo) {
		this.agingNodesLogVo = agingNodesLogVo;
	}
	
	
	public void setAgingNodesLogService(IAgingNodesLogService agingNodesLogService) {
		this.agingNodesLogService = agingNodesLogService;
	}
	


	/**
	 * 查询时效节点修改日志
	 * @author foss-heyongdong
	 * @date 2014年4月11日 14:55:23
	 * @return String
	 */
	@JSON
	public String queryAgingNodesLog(){
		List<AgingNodesLogEntity> agingNodesLogs=agingNodesLogService.queryAgingNodesLog(agingNodesLogVo.getBillNo());
		agingNodesLogVo.setAgingNodesLogs(agingNodesLogs);
		return returnSuccess();
	}
	
	/**
	 * 保存修改出发到达时间日志，同时更新出发到达时间
	 * @author foss-heyongdong
	 * @date 2014年4月21日 11:40:18
	 * @return String
	 */
	@JSON
	public String saveAgingNodesLog(){
		try {
			agingNodesLogService.saveAgingNodesLog(agingNodesLogVo);
			
		}catch(BusinessException e){
			return super.returnError(e.getMessage());
		}
		catch (Exception e) {
			return super.returnError(e.toString(),"");
		}
		return returnSuccess();
	}
	/**
	 * 更具单号查询同一个车辆任务下的相关联单号
	 * @author foss-heyongdong
	 * @date 2014年4月29日 09:20:34
	 * @return String
	 */
	@JSON
	public String queryRelationbillNos(){
		try {
			String relationbillNos=agingNodesLogService.queryRelationbillNos(agingNodesLogVo.getBillNo());
			agingNodesLogVo.setBillNo(relationbillNos);
		}catch(BusinessException e){
			return super.returnError(e.getMessage());
		}
		catch (Exception e) {
			return super.returnError(e.toString(),"");
		}
		return returnSuccess();
	}
}
