package com.deppon.pda.bdm.module.foss.load.server.service.impl;

import java.text.SimpleDateFormat;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.SmtWKLoadTaskDto;
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
import com.deppon.pda.bdm.module.foss.load.shared.domain.SubmitLoadTask;
import com.deppon.pda.bdm.module.foss.load.shared.exception.LoadHasNoFnshScanUserException;
/**
 * 提交装车任务
 * @author gaojia
 * @date Sep 10,2012 14:42:30 PM
 * @version 1.0
 * @since
 */
public class SmtLoadTaskService implements IBusinessService<Void, SubmitLoadTask>{
	private IPDATransferLoadService pdaTransferLoadService;
	private ILoadDao loadDao;
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:21:37
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public SubmitLoadTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		SubmitLoadTask model = JsonUtil.parseJsonToObject(SubmitLoadTask.class, asyncMsg.getContent());
		model.setPdaCode(asyncMsg.getPdaCode());
		model.setUserCode(asyncMsg.getUserCode());
		return model;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:21:43
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, SubmitLoadTask param)
			throws PdaBusiException {
		this.validate(param);
		try { 
			if(loadDao.queryNoSyncScanMsgCount(param.getTaskCode())){
				throw new FossInterfaceException(null,"服务器数据正在同步，请耐心等待");
			}
			if(param.getIsForceSmt().equals("1")){
				//pdaTransferLoadService.forceSubmitLoadTask(param.getTaskCode(), param.getScanTime(), asyncMsg.getPdaCode(),asyncMsg.getUserCode());
				pdaTransferLoadService.forceSubmitLoadTask(packaging(asyncMsg, param));
			
			}else{
				/***
				 * 变更日志：335284
				 * 提交后同步给悟空
				 */
//				pdaTransferLoadService.submitLoadTask(param.getTaskCode(), param.getScanTime(), asyncMsg.getPdaCode(),asyncMsg.getUserCode());
				pdaTransferLoadService.submitLoadTask(packaging(asyncMsg, param));
			}
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
	 * 封装参数 2016-05-11 15:29:01
	 * @param param PDA传入参数
	 * @return FinshWKLoadTaskDto（完成装车任务）
	 */
	private SmtWKLoadTaskDto packaging (AsyncMsg asyncMsg, SubmitLoadTask param){
		
		SmtWKLoadTaskDto wkload = new SmtWKLoadTaskDto();
		
		//装车最后时间
		wkload.setLoadEndTime(param.getScanTime());
		//String 装车编号
		wkload.setLoaderCode(asyncMsg.getUserCode());
		//装车类型 快递 1, 零担0, 合单2
		wkload.setSendType(param.getSendType());
		//装车编号
		wkload.setLoadTaskNo(param.getTaskCode());
		
		wkload.setDeviceNo(asyncMsg.getPdaCode());
		
		/** 操作部门编号 **/
		wkload.setOperationOrgCode(asyncMsg.getDeptCode());
		/** 操作时间 **/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		wkload.setOperationTime(sdf.format(param.getScanTime()));
		/** 操作人工号 **/
		wkload.setOperatorNo(param.getUserCode());
		return wkload;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:21:50
	 * @param submitLoadTask
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(SubmitLoadTask submitLoadTask) throws ArgumentInvalidException{
		Argument.notNull(submitLoadTask, "submitLoadTask");
		//提交员工编号非空
		Argument.hasText(submitLoadTask.getUserCode(), "submitLoadTask.userCode");
		//任务号非空
		Argument.hasText(submitLoadTask.getTaskCode(), "submitLoadTask.taskCode");
		//扫描时间非空
		Argument.notNull(submitLoadTask.getScanTime(), "submitLoadTask.scanTime");
		//强制提交检验
		Argument.hasText(submitLoadTask.getIsForceSmt(),"submitLoadTask.isForceSmt");
	}
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_LOAD_SUBMIT.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaTransferLoadService(
			IPDATransferLoadService pdaTransferLoadService) {
		this.pdaTransferLoadService = pdaTransferLoadService;
	}
	public void setLoadDao(ILoadDao loadDao) {
		this.loadDao = loadDao;
	}
	
	
	
	
}
