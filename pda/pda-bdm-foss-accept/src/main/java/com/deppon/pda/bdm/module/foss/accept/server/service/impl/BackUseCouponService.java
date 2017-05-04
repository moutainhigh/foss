package com.deppon.pda.bdm.module.foss.accept.server.service.impl;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponReverseResultDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.BackUseCouponsDetailEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.BackUseCouponsEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.server.service.impl 
 * @file BackUseCouponService.java 
 * @description 退回优惠券使用服务类
 * @author ChenLiang
 * @created 2012-12-29 下午1:42:36    
 * @version 1.0
 */
public class BackUseCouponService implements IBusinessService<BackUseCouponsDetailEntity, BackUseCouponsEntity> {
	
	private Logger log = Logger.getLogger(getClass());
	
	private IPdaWaybillService pdaWaybillService;

	public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
		this.pdaWaybillService = pdaWaybillService;
	}

	/**
	 * 
	 * @description 解析包体
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public BackUseCouponsEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析内容
		BackUseCouponsEntity backUseCoupons = JsonUtil.parseJsonToObject(BackUseCouponsEntity.class,asyncMsg.getContent());
		return backUseCoupons;
	}
	
	/**
	 * 
	 * @description 校验数据合法性
	 * @param asyncMsg
	 * @param backUseCoupons
	 * @throws PdaBusiException 
	 * @created 2012-12-29 下午2:02:01
	 */
	private void validate(AsyncMsg asyncMsg, BackUseCouponsEntity backUseCoupons) throws PdaBusiException {
		Argument.notNull(asyncMsg, "AsyncMsg");
		//pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		Argument.notNull(backUseCoupons, "BackUseCouponsEntity");
		Argument.hasText(backUseCoupons.getCouponCode(), "BackUseCouponsEntity.couponCode");
	}

	/**
	 * 
	 * @description 服务方法
	 * @param asyncMsg
	 * @param backUseCoupons
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Override
	public BackUseCouponsDetailEntity service(AsyncMsg asyncMsg, BackUseCouponsEntity backUseCoupons) throws PdaBusiException {
		this.validate(asyncMsg, backUseCoupons);
		log.debug("---调用FOSS退回优惠劵接口开始---");
		CouponReverseResultDto couponReverseResult = null;
		try {
			//调用foss接口
			couponReverseResult = pdaWaybillService.reverseCouponState(backUseCoupons.getCouponCode());
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.debug("---调用FOSS退回优惠劵接口结束---");
		BackUseCouponsDetailEntity backUseDetail = new BackUseCouponsDetailEntity();
		backUseDetail.setIsCanAntiUse(couponReverseResult.isSuccess()?"1":"0");
		backUseDetail.setReason(couponReverseResult.getMessage());
		return backUseDetail;
	}

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_BACK_USE_COUPON.VERSION;
	}

	/**
	 * 同步还是异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

}
