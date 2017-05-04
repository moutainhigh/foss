package com.deppon.foss.module.settlement.closing.server.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.settlement.closing.api.server.service.IEcsFossExecuteErrorLogService;
import com.deppon.foss.module.settlement.closing.api.server.service.ILogEcsFossService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MqLogEntity;
import com.deppon.foss.module.settlement.closing.api.shared.vo.EcsFossLogVo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 查询异常日志并重发日志请求参数
 * 
 * @author 326181
 * 
 */
public class EcsFossExecuteErrorLogAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private EcsFossLogVo vo;

	private ILogEcsFossService logEcsFossService;

	private IEcsFossExecuteErrorLogService ecsFossExecuteErrorLogService;
	
	/**
	 * 互斥锁接口
	 */
	private IBusinessLockService businessLockService;
	
	List<MqLogEntity> logList=new ArrayList<MqLogEntity>();
	
	@JSON
	public String queryEcsFossLog() {
		try {
			if (vo == null || vo.getEsbCode() == null) {
				throw new SettlementException("查询条件不能为空！");
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("waybillNo", vo.getWaybillNo());
			params.put("esbCode", vo.getEsbCode());
			params.put("startDate", vo.getStartDate());
			params.put("endDate", vo.getEndDate());
			logList = logEcsFossService.queryMqLogEntity(params, start, limit);
			this.setTotalCount(this.logEcsFossService.queryLogTotalCount(params));
		} catch (BusinessException e) {
			return this.returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 重新执行异步接口异常信息
	 * @author 326181
	 * @return
	 */
	public String executeLogRequestMsg() {
		if (vo == null) {
			throw new SettlementException("请勾选数据！");
		}
		MutexElement mutex = null;
		Boolean flag = null;
		try {
			MqLogEntity log = vo.getLogEntityList();
			mutex = new MutexElement(log.getWaybillNo(), "异常任务查询页面重新执行", MutexElementType.ARRIVE_SHEET_CREATE_WAYBILLNO);
			flag = businessLockService.lock(mutex, NumberConstants.ZERO);
			if(!flag){
				throw new SettlementException("该运单正在执行，请稍后在试！");
		    }
			
			this.ecsFossExecuteErrorLogService.doExecuteLogReqMsg(log);
		} catch (BusinessException e) {
			return this.returnError(e);
		} finally {
			businessLockService.unlock(mutex);
		}
		return returnSuccess("发送成功");
	}

	

	public EcsFossLogVo getVo() {
		return vo;
	}

	public void setVo(EcsFossLogVo vo) {
		this.vo = vo;
	}

	public void setLogEcsFossService(ILogEcsFossService logEcsFossService) {
		this.logEcsFossService = logEcsFossService;
	}

	public void setEcsFossExecuteErrorLogService(
			IEcsFossExecuteErrorLogService ecsFossExecuteErrorLogService) {
		this.ecsFossExecuteErrorLogService = ecsFossExecuteErrorLogService;
	}

	public IBusinessLockService getBusinessLockService() {
		return businessLockService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	
	public List<MqLogEntity> getLogList() {
		return logList;
	}

	public void setLogList(List<MqLogEntity> logList) {
		this.logList = logList;
	}
}
