package com.deppon.pda.bdm.module.foss.load.server.service.impl.derytranload;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressSendPieceService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.server.dao.ILoadDao;
import com.deppon.pda.bdm.module.foss.load.shared.domain.KdScanBusinessErrorLog;
import com.deppon.pda.bdm.module.foss.load.shared.domain.derytranload.SubmitLoadTaskEntity;
import com.deppon.pda.bdm.module.foss.load.shared.exception.LoadHasNoFnshScanUserException;

/**
 * 提交派件装车任务
 * @ClassName KdSmtLoadTaskService.java 
 * @Description 
 * @author 245955
 * @date 2015-4-20
 */

public class SmtLoadTaskService implements IBusinessService<Set<KdScanBusinessErrorLog>, SubmitLoadTaskEntity> {
	private IPDAExpressSendPieceService pdaExpressSendPieceService;
	private ILoadDao loadDao;
	/**
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @description 
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-20
	 */
	@Override
	public SubmitLoadTaskEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		SubmitLoadTaskEntity model=JsonUtil.parseJsonToObject(SubmitLoadTaskEntity.class, asyncMsg.getContent());
		model.setPdaCode(asyncMsg.getPdaCode());
		model.setUserCode(asyncMsg.getUserCode());
		return model;
	}
	/**
	 * 服务方法
	 * @description 
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-20
	 */
	@Transactional
	@Override
	public Set<KdScanBusinessErrorLog> service(AsyncMsg asyncMsg, SubmitLoadTaskEntity param) throws PdaBusiException {
		this.validate(param);
		try {
			if(loadDao.queryNoSyncScanMsgCount(param.getTaskCode())){
				throw new FossInterfaceException(null,"服务器数据正在同步，请耐心等待");
			}
			//将不符合业务逻辑的扫描数据返回给PDA
			Set<KdScanBusinessErrorLog> scanBusinessErrorLogs = new HashSet<KdScanBusinessErrorLog>();
			KdScanBusinessErrorLog scanBusinessErrorLog = new KdScanBusinessErrorLog();
			scanBusinessErrorLog.setTaskCode(param.getTaskCode());
			//正扫
			scanBusinessErrorLog.setScanStatus(TransferPDADictConstants.LOAD_GOODS_STATE_NORMAL);
			List<KdScanBusinessErrorLog> scanBusinessErrorLogScans = loadDao.selectKdScanBusinessErrorLogByTaskCode(scanBusinessErrorLog);
			//反扫
			scanBusinessErrorLog.setScanStatus(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED);
			//正扫-反扫
			List<KdScanBusinessErrorLog> scanBusinessErrorLogCacels = loadDao.selectKdScanBusinessErrorLogByTaskCode(scanBusinessErrorLog);
			boolean flag = true;
			if(scanBusinessErrorLogScans!=null && scanBusinessErrorLogScans.size()>0){
				for(KdScanBusinessErrorLog ScanBusinessErrorLogScan:scanBusinessErrorLogScans){
					if(scanBusinessErrorLogCacels!=null&&scanBusinessErrorLogCacels.size()>0){
						for(KdScanBusinessErrorLog kdScanBusinessErrorLog:scanBusinessErrorLogCacels){
							if(ScanBusinessErrorLogScan.getWaybillCode().equals(kdScanBusinessErrorLog.getWaybillCode())
							 &&ScanBusinessErrorLogScan.getLabelCode().equals(kdScanBusinessErrorLog.getLabelCode())){
								scanBusinessErrorLogCacels.remove(kdScanBusinessErrorLog);
								flag=false;
								break;
							}
						}
					}
					if(flag){
						scanBusinessErrorLogs.add(ScanBusinessErrorLogScan);
					}else{
						flag=true;
					}					
				}
			}			
			if(scanBusinessErrorLogs!=null && scanBusinessErrorLogs.size()>0){
				return scanBusinessErrorLogs;
			}
			//loadDao.deleteKdScanBusinessErrorLog(param.getTaskCode());
			pdaExpressSendPieceService.submitLoadTask(param.getTaskCode(), param.getScanTime(), asyncMsg.getPdaCode(),asyncMsg.getUserCode());
		} catch (BusinessException e) {
			if(e.getErrorCode().equals(TransferPDAExceptionCode.EXCEPTION_EXTIST_UNFINISH_PDA_MESSAGECODE)){
				throw new LoadHasNoFnshScanUserException();
			}else{
				throw new FossInterfaceException(e.getCause(),e.getErrorCode());
			}
		}
		return null;
	}
	/**
	 * 操作类型
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-20
	 */
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_DELIVERY_TASK_SUBMIT.VERSION;
	}
	/**
	 * 是否异步
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-20
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
	/**
	 * 参数验证
	 * @description 
	 * @param kdSubmitLoadTask
	 * @throws ArgumentInvalidException
	 * @author 245955
	 * @date 2015-4-20
	 */
	private void validate(SubmitLoadTaskEntity submitLoadTaskEntity) throws ArgumentInvalidException{
		Argument.notNull(submitLoadTaskEntity, "submitLoadTaskEntity");
		//提交员工编号非空
		Argument.hasText(submitLoadTaskEntity.getUserCode(), "submitLoadTaskEntity.userCode");
		//提交任务号非空
		Argument.hasText(submitLoadTaskEntity.getTaskCode(), "submitLoadTaskEntity.taskCode");
	}	
	
	public void setPdaExpressSendPieceService(
			IPDAExpressSendPieceService pdaExpressSendPieceService) {
		this.pdaExpressSendPieceService = pdaExpressSendPieceService;
	}
	public void setLoadDao(ILoadDao loadDao) {
		this.loadDao = loadDao;
	}
	
}
