package com.deppon.pda.bdm.module.foss.delivery.server.service.impl;


import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaPickupService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.SelfProvidedCasEntity;

/**   
 * @ClassName SelfProvidedListService  
 * @Description 客户自提列表清单 
 * @author  092038 张贞献  
 * @date 2014-12-9    
 */ 
public class CalSelfProvidedService implements IBusinessService<Void,SelfProvidedCasEntity > {

	private IPdaPickupService pdaPickupService;

	/**
	 * 解析包体
	 */
	@Override
	public SelfProvidedCasEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		SelfProvidedCasEntity selfProvidedCasEntity = JsonUtil.parseJsonToObject(SelfProvidedCasEntity.class, asyncMsg.getContent());
		
		return selfProvidedCasEntity;
	}

	/**
	 * 服务方法
	 */
	@Override
	public Void service(AsyncMsg asyncMsg, SelfProvidedCasEntity param) throws PdaBusiException {
		

		
		try {
			// 参数验证
			this.validate(asyncMsg,param);
			
			pdaPickupService.pdaChangeWaybillState(param.getWblCodes());
						
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		
		
		
		return null;
	}

	
	
	/**
	 * 验证数据有效性
	 * @param asyncMsg
	 * @param getDeryTask
	 */
	private void validate(AsyncMsg asyncMsg,SelfProvidedCasEntity param){
		Argument.notNull(asyncMsg, "AsyncMsg");
		//pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		//用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//操作类型
		Argument.hasText(asyncMsg.getOperType(), "AsyncMsg.operType");
		
		Argument.notNull(param, "SelfProvidedCasEntity");
		

		//运单号
		//Argument.hasText(param.getWayBillCode(), "SelfProvidedCasEntity.wayBillCode");
		
		
			}

	/**
	 * 服务类型
	 */
	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_CAL_SELF_PROVIDED.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaPickupService(IPdaPickupService pdaPickupService) {
		this.pdaPickupService = pdaPickupService;
	}





}










