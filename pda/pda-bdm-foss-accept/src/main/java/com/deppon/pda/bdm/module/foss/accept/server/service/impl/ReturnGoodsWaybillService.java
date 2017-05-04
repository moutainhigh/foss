package com.deppon.pda.bdm.module.foss.accept.server.service.impl;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.ReturnGoodsWaybillEntity;
/**
 * 
* @ClassName: ReturnGoodsWaybill 
* @Description: TODO(返货上报逻辑判断) 
* @author 268974 wangzhili
* @date 2015-10-14
*
 */
public class ReturnGoodsWaybillService implements IBusinessService<Void, ReturnGoodsWaybillEntity> {
	// 日志
	private Logger log = Logger.getLogger(getClass());
	//
	private IPdaWaybillService pdaWaybillService;

	public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
		this.pdaWaybillService = pdaWaybillService;
	}
	
	/**
	 * @description 解析json字符串
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public ReturnGoodsWaybillEntity parseBody(AsyncMsg asyncMsg)throws PdaBusiException {
		ReturnGoodsWaybillEntity returnGoodsWaybillEntity = JsonUtil
				.parseJsonToObject(ReturnGoodsWaybillEntity.class,
						asyncMsg.getContent());
		return returnGoodsWaybillEntity;
	}

	/**
	 * 
	 * @description 服务方法
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Override
	public Void service(AsyncMsg asyncMsg, ReturnGoodsWaybillEntity param)
			throws PdaBusiException {
		// 校验数据合法性
		this.validate(asyncMsg, param);
		try {
			long startTime = System.currentTimeMillis();
			// 调用foss接口
			pdaWaybillService.validateReturnGoodsWaybillNo(
					param.getWayBillCode(), param.getReturnMode());
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("校验单号和返货方式消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
		return null;
	}

	/**
	 * 校验数据合法性
	 * @param asyncMsg
	 * @param returnGoodsWaybillEntity
	 * @throws PdaBusiException
	 */
	private void validate(AsyncMsg asyncMsg,
			ReturnGoodsWaybillEntity returnGoodsWaybillEntity)
			throws PdaBusiException {
		Argument.notNull(asyncMsg, "AsyncMsg");
		// 校验运单编号
		Argument.hasText(returnGoodsWaybillEntity.getWayBillCode(),
				"returnGoodsWaybillEntity.wayBillCode");
		// 校验返货方式
		Argument.hasText(returnGoodsWaybillEntity.getReturnMode(),
				"returnGoodsWaybillEntity.returnMode");
	}

	/**
	 * 操作类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_EXP_RCV_RETURN_GOODS_WAY_BILL.VERSION;
	}

	/**
	 * 是否异步
	 */
	@Override
	public boolean isAsync() {
		//同步
		return false;
	}

}
