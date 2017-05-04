package com.deppon.pda.bdm.module.foss.unload.server.service.impl.partner;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAOrderTaskService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.partner.ScanTaskUploadEntity;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.partner.ScanTaskUploadResult;

/**
 * 
 * @ClassName: ScanTaskUploadService 
 * @Description: TODO(扫描任务上传) 
 * @author &268974  wangzhili
 * @date 2016-1-27 上午11:11:33 
 *
 */
public class ScanTaskUploadService implements IBusinessService< ScanTaskUploadResult, ScanTaskUploadEntity>{

	private static final Logger log = Logger.getLogger(ScanTaskUploadService.class);
	private IPDAOrderTaskService pdaOrderTaskService;
	//解析参数
	@Override
	public ScanTaskUploadEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		ScanTaskUploadEntity entity = JsonUtil.parseJsonToObject(ScanTaskUploadEntity.class, asyncMsg.getContent());
		return entity;
	}

	//入口方法
	@Override
	public  ScanTaskUploadResult service(AsyncMsg asyncMsg, ScanTaskUploadEntity param)
			throws PdaBusiException {
		//校验参数的合法性
		this.validate(param);
		log.info("参数值为:"+param);
		ScanTaskUploadResult scanTaskUpload = new ScanTaskUploadResult();
		 List<String> result = new ArrayList<String>();
		try {
			result = pdaOrderTaskService.scanOrderTask(param.getOrderTaskNo(), param.getWaybillNo(), param.getSerialNo(), param.getUserCode(), param.getId());
			scanTaskUpload.setScanTaskUpload(result);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		} catch(Exception e){
			throw new FossInterfaceException(null,"调用foss出现未知异常");
		}
		return scanTaskUpload;
	}

	//操作类型
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_SCAN_TASK_UPLOAD.VERSION;
	}

	//异步
	@Override
	public boolean isAsync() {
		return false;
	}
	/**
	 * 
	 * @Title: validate 
	 * @Description: TODO(校验参数的合法性)  
	 * @return void    返回类型 
	 * @param entity
	 * @throws ArgumentInvalidException
	 * @author： 268974  wangzhili
	 */
	private void validate(ScanTaskUploadEntity entity) throws ArgumentInvalidException{
		//检验扫描任务上传实体非空
		Argument.notNull(entity,"ScanTaskUploadEntity");
		//检验任务编号非空
		Argument.hasText(entity.getOrderTaskNo(), "entity.orderTaskNo");
		//检验id非空
		Argument.hasText(entity.getId(), "entity.id");
		//校验流水号非空
		Argument.hasText(entity.getSerialNo(), "entity.serialNo");
		//校验运单号非空
		Argument.hasText(entity.getWaybillNo(), "entity.waybillNo");
		//校验用户编码非空
		Argument.hasText(entity.getUserCode(), "entity.userCode");
	}

	public void setPdaOrderTaskService(IPDAOrderTaskService pdaOrderTaskService) {
		this.pdaOrderTaskService = pdaOrderTaskService;
	}
	

}
