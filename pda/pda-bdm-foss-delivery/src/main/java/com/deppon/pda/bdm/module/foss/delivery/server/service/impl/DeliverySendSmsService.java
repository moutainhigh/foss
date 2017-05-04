package com.deppon.pda.bdm.module.foss.delivery.server.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IAppSendMsgService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.AppSMSDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.DeliverbillSMSEntiy;
/**
 * 接送货异常短信推送
 * @author 354682
 *
 */
public class DeliverySendSmsService implements
		IBusinessService<Void, DeliverbillSMSEntiy> {
	private static final Log LOG = LogFactory
			.getLog(DeliverySendSmsService.class);
	private IAppSendMsgService appSendMsgService;

	/**
	 * 解析包体
	 */
	@Override
	public DeliverbillSMSEntiy parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		DeliverbillSMSEntiy deliverbillSMSEntiy = JsonUtil.parseJsonToObject(
				DeliverbillSMSEntiy.class, asyncMsg.getContent());
		return deliverbillSMSEntiy;
	}

	/**
	 * 服务方法
	 */

	@Override
	public Void service(AsyncMsg asyncMsg,
			DeliverbillSMSEntiy deliverbillSMSEntiy) throws PdaBusiException {
		// TODO Auto-generated method stub
		LOG.info(deliverbillSMSEntiy);
		try {
			// 验证数据的有效性
			this.validate(asyncMsg, deliverbillSMSEntiy);
			// 封装给foss的值
			AppSMSDto appSMSDto = wrapAppSMSDto( asyncMsg, deliverbillSMSEntiy);
			appSendMsgService.appSendMsg(appSMSDto);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		} catch (Exception e) {
			throw new FossInterfaceException(null, "未知异常"+e.getMessage());
		}
		return null;
	}

	/**
	 * 封装数据
	 */
	public AppSMSDto wrapAppSMSDto(AsyncMsg asyncMsg,
			DeliverbillSMSEntiy deliverbillSMSEntiy) {
		AppSMSDto appSMSDto = new AppSMSDto();
		// 司机工号
		appSMSDto.setDriverCode(deliverbillSMSEntiy.getDriverCode());
		// 运单号
		appSMSDto.setWaybillNo(deliverbillSMSEntiy.getWaybillNo());
		// 短信类型
		appSMSDto.setMsgType(deliverbillSMSEntiy.getNotifyType());
		//客户手机
		appSMSDto.setMobile(deliverbillSMSEntiy.getMobile());
		return appSMSDto;
	}

	/**
	 * 验证数据有效性
	 * 
	 * @param asyncMsg
	 * @param deliverbillSMSDto
	 */
	private void validate(AsyncMsg asyncMsg,
			DeliverbillSMSEntiy deliverbillSMSEntiy) {
		Argument.notNull(asyncMsg, "AsyncMsg");
		// 司机工号
		Argument.hasText(deliverbillSMSEntiy.getDriverCode(),
				"DeliverbillSMSEntiy.driverCode");
		// 运单号
		Argument.hasText(deliverbillSMSEntiy.getWaybillNo(),
				"DeliverbillSMSEntiy.waybillNo");
		// 短信类型
		Argument.hasText(deliverbillSMSEntiy.getNotifyType(),
				"DeliverbillSMSEntiy.notifyType");
		// 客户手机
		Argument.hasText(deliverbillSMSEntiy.getMobile(),
				"DeliverbillSMSEntiy.mobile ");

	}

	/**
	 * 服务类型
	 */
	@Override
	public String getOperType() {
		// TODO Auto-generated method stub
		return DeliveryConstant.OPER_TYPE_DELIVER_SEND_SMS.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setAppSendMsgService(IAppSendMsgService appSendMsgService) {
		this.appSendMsgService = appSendMsgService;

	}

}
