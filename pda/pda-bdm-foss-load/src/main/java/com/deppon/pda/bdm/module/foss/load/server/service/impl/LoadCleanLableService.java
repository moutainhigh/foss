package com.deppon.pda.bdm.module.foss.load.server.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.module.transfer.load.api.server.service.ISealInformationService;
import com.deppon.foss.module.transfer.load.api.shared.domain.PDAUserEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.RequestSealBindANDCheckOutEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ResponseJsonInfoEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.shared.exception.LoadException;

/**
 * 装车-WK校验封签
 * @author LiuLiPeng - PDA后台 - 314587
 * @date 2016-05-06 17:02:33
 * @version 1.0
 */
public class LoadCleanLableService implements IBusinessService<String, RequestSealBindANDCheckOutEntity>{
	private static final Log LOG = LogFactory.getLog(LoadCleanLableService.class);
	
	private ISealInformationService itfSealInfomationService;
	
	@Override
	public RequestSealBindANDCheckOutEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		LOG.info("**************解析包体-开始*************");
		RequestSealBindANDCheckOutEntity result = JsonUtil.parseJsonToObject(RequestSealBindANDCheckOutEntity.class, asyncMsg.getContent());
		PDAUserEntity pdaUserEntity = new PDAUserEntity();
		pdaUserEntity.setDeptCode(asyncMsg.getDeptCode());//部门编码
//		result.setDestination(asyncMsg.getDeptCode());
		pdaUserEntity.setUserCode(asyncMsg.getUserCode());//员工工号
		pdaUserEntity.setPdaCode(asyncMsg.getPdaCode());//pda编号
		pdaUserEntity.setOperType(asyncMsg.getOperType());//操作类型
		pdaUserEntity.setPdaType(asyncMsg.getPdaType());//pda型号
		pdaUserEntity.setPgmVer(asyncMsg.getPgmVer());//程序版本号
		pdaUserEntity.setUserType(asyncMsg.getUserType());//用户类型
		result.setpDAUserEntity(pdaUserEntity);
		LOG.info("**************解析包体-结束*************");
		return result;
	}

	@Override
	public String service(AsyncMsg asyncMsg, RequestSealBindANDCheckOutEntity param) throws PdaBusiException {
		LOG.info("**************开始执行服务方法*************");
		ResponseJsonInfoEntity bean = itfSealInfomationService.checkOutSealForExpressService(param);
		String result = "";
		if(bean.getState()=="1"){
			result = bean.getReturnInfo();
		}else{
			throw new LoadException(bean.getReturnInfo());
		}
		LOG.info("**************结束执行服务方法*************");
		return result;
	}

	@Override
	public String getOperType() {
		return LoadConstant.WK_LOAD_DELETE_LABLE.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public ISealInformationService getItfSealInfomationService() {
		return itfSealInfomationService;
	}

	public void setItfSealInfomationService(ISealInformationService itfSealInfomationService) {
		this.itfSealInfomationService = itfSealInfomationService;
	}

}
