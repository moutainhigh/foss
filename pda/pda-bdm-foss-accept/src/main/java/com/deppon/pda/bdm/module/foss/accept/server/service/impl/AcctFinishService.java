package com.deppon.pda.bdm.module.foss.accept.server.service.impl;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPickupDoneService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PickupDoneDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.server.dao.IAcctDao;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.AcctFinishEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.server.service.impl 
 * @file AcctFinishService.java 
 * @description 完成接货服务类
 * @author ChenLiang
 * @created 2012-12-28 下午9:05:46    
 * @version 1.0
 */
public class AcctFinishService implements IBusinessService<Void, AcctFinishEntity> {
	
	private Logger log = Logger.getLogger(getClass());
	private IAcctDao acctDao;
	
	// 完成接货接口
	private IPickupDoneService pickupDoneService;
	
	public void setPickupDoneService(IPickupDoneService pickupDoneService) {
		this.pickupDoneService = pickupDoneService;
	}

	/**
	 * 
	 * @description 解析json字符串
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public AcctFinishEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析内容
		AcctFinishEntity acctFinish = JsonUtil.parseJsonToObject(AcctFinishEntity.class,asyncMsg.getContent());
		//PDA编号
		acctFinish.setPdaCode(asyncMsg.getPdaCode());
		//用户编号
		acctFinish.setUserCode(asyncMsg.getUserCode());
		return acctFinish;
	}
	
	/**
	 * 
	 * @description 校验数据合法性
	 * @param acctFinish
	 * @throws PdaBusiException 
	 * @created 2012-12-28 下午9:08:35
	 */
	private void validate(AsyncMsg asyncMsg, AcctFinishEntity acctFinish) throws PdaBusiException {
		Argument.notNull(asyncMsg, "AsyncMsg");
		//验证部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		//验证用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//验证pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		// 判断完成接货实体
		Argument.notNull(acctFinish, "AcctFinish");
		// 判断车牌号
		Argument.hasText(acctFinish.getTruckCode(), "AcctFinish.truckCode");
		// 判断完成时间
		Argument.notNull(acctFinish.getAcctFinishTime(), "AcctFinish.acctFinishTime");
	}

	/**
	 * 
	 * @description 完成接货服务
	 * @param asyncMsg
	 * @param acctFinish
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Override
	public Void service(AsyncMsg asyncMsg, AcctFinishEntity acctFinish) throws PdaBusiException {
		// 校验数据
		this.validate(asyncMsg, acctFinish);
		//构造DTO
		PickupDoneDto pickupDone = new PickupDoneDto();
		// 完成接货时间
		pickupDone.setCreateTime(acctFinish.getAcctFinishTime());
		// 用户工号
		pickupDone.setCreateUserName(asyncMsg.getUserCode());
		// 司机工号
		pickupDone.setDriverCode(asyncMsg.getUserCode());
		// 车牌号
		pickupDone.setVehicleNo(acctFinish.getTruckCode());
		//部门编号
		pickupDone.setOrgCode(asyncMsg.getDeptCode());
		
		try {
			if(acctDao.queryNoSyncScanMsgCount(asyncMsg.getUserCode(),asyncMsg.getPdaCode())){
				throw new FossInterfaceException(null,"服务器数据正在同步，请耐心等待");
			}
			log.debug("---调用FOSS完成接货接口开始---");
			//调用foss接口
			pickupDoneService.donePickUp(pickupDone);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.debug("---调用FOSS完成接货接口结束---");
		return null;
	}

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_FINISH.VERSION;
	}

	/**
	 * 同步还是异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setAcctDao(IAcctDao acctDao) {
		this.acctDao = acctDao;
	}
	
}
