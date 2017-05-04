package com.deppon.pda.bdm.module.foss.unload.server.service.impl;


import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDATrayScanService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayScanTaskEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.server.dao.IUnloadDao;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.FinishPalletBindingTask;

/**
 * 
 * @description 叉车任务完成接口service实现类
 * @version 1.0
 * @author wenwuneng 
 * @update 2013-8-13 上午9:02:24
 */
 
public class SmtPalletScanService implements
		IBusinessService<Void, FinishPalletBindingTask> {
	private IPDATrayScanService pdaTrayScanService;
	private IUnloadDao unloadDao;
	//private Logger log = Logger.getLogger(getClass());

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2013-3-20 下午6:36:21
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public FinishPalletBindingTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		FinishPalletBindingTask entity = new FinishPalletBindingTask();
		entity = JsonUtil.parseJsonToObject(FinishPalletBindingTask.class,
				asyncMsg.getContent());
		entity.setDeptCode(asyncMsg.getDeptCode());
		entity.setPdaCode(asyncMsg.getPdaCode());
		entity.setScanUser(asyncMsg.getUserCode());
		entity.setScanType(asyncMsg.getOperType());
		entity.setUploadTime(asyncMsg.getUploadTime());
		return entity;
	}

	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, FinishPalletBindingTask param)
			throws PdaBusiException {
		this.validate(asyncMsg,param);
		try {
			unloadDao.saveFinishUnldPalletBindingTask(param);
			PDATrayScanTaskEntity trayScanTask=this.getFossReqEntity(asyncMsg,param);
			pdaTrayScanService.updateTrayScanTask(trayScanTask);
			return null;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}

	}
	private PDATrayScanTaskEntity getFossReqEntity(AsyncMsg asyncMsg,
			FinishPalletBindingTask param) {
		//封装foss 请求数据
		PDATrayScanTaskEntity trayScanTask=new PDATrayScanTaskEntity();
		trayScanTask.setForkliftDriverCode(asyncMsg.getUserCode());//叉车司机工号
		trayScanTask.setTaskNo(param.getBindingNo());//绑定唯一编号
		trayScanTask.setForkliftDept(asyncMsg.getDeptCode());//叉车司机部门
		trayScanTask.setTrayscanTime(param.getFnshDate());//叉车时间点击完成时间
		return trayScanTask;
	}


	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2013-3-20 下午6:36:27
	 * @param unldScanEnity
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(AsyncMsg asyncMsg,FinishPalletBindingTask entity)
			throws ArgumentInvalidException {
		// 检验卸车扫描非空
		Argument.notNull(entity, "FinishPalletBindingTask");
		// 检验标签号非空
		Argument.hasText(entity.getBindingNo(),
				"FinishPalletBindingTask.bindingNo");
		// 检验任务号非空
		Argument.notNull(entity.getFnshDate(),"FinishPalletBindingTask.fnshDate");
	}

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2013-3-20 下午6:36:31
	 * @return
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#getOperType()
	 */
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_FINISH_BOUND_TASK.VERSION;
	}

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2013-3-20 下午6:36:35
	 * @return
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#isAsync()
	 */
	@Override
	public boolean isAsync() {
		return true;
	}


	public void setUnloadDao(IUnloadDao unloadDao) {
		this.unloadDao = unloadDao;
	}

	public void setPdaTrayScanService(IPDATrayScanService pdaTrayScanService) {
		this.pdaTrayScanService = pdaTrayScanService;
	}

}
