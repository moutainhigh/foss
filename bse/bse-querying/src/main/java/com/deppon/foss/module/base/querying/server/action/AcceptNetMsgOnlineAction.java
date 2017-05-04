package com.deppon.foss.module.base.querying.server.action;

import java.util.Date;
import java.util.List;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.common.api.server.service.IOnLineMsgService;
import com.deppon.foss.module.base.common.api.shared.domain.MsgOnlineEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MsgOnlineDto;
import com.deppon.foss.module.base.common.api.shared.vo.MsgOnlineVo;
import com.deppon.foss.module.base.querying.server.service.IMsgOnlineService;
import com.deppon.foss.util.DateUtils;

public class AcceptNetMsgOnlineAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2821996703125090764L;
	/**
	 * 在线通知Dto
	 */
	private MsgOnlineVo msgOnlineVo;
	/**
	 * 在线通知service
	 */
	private IMsgOnlineService msgOnlineService;
	/**
	 * 在线通知DTO
	 */
	private MsgOnlineDto msgOnlineDto;
	/**
	 * 在线通知service
	 */
	private IOnLineMsgService onLineMsgService;
	/**
	 * 站内消息初始化
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2012-12-20 下午5:14:19
	 * @return
	 */
	public String  acceptNetMsgOnline() {
		return returnSuccess();
	}
	@JSON
	public String queryMsgByBillNo(){
		int i=0;
		int j=NumberConstants.NUMBER_1000;
	//	List<MsgOnlineEntity> msgOnlineEntitys = new ArrayList<MsgOnlineEntity>();
		if(msgOnlineVo.getMsgOnlineEntity().getCreateEndTime()!= null){
			//站内消息结束时间
			Date endTime = msgOnlineVo.getMsgOnlineEntity().getCreateEndTime();
			//结束时间加1天
			msgOnlineVo.getMsgOnlineEntity().setCreateEndTime(DateUtils.addDayToDate(endTime, 1));
		}
		List<MsgOnlineEntity> msgOnlineEntitys = msgOnlineService.queryOnlineMsgByEntity(msgOnlineVo.getMsgOnlineEntity(),i,j);
	
		msgOnlineVo.setMsgOnlineEntitys(msgOnlineEntitys);
		return returnSuccess();
	}
	@JSON
	public String updateMsgByBillNo(){
		
		onLineMsgService.updateOnlineMsg(msgOnlineVo.getMsgOnlineEntity());
	
		return returnSuccess(MessageType.UPDATE_SUCCESS);
	}
	public MsgOnlineVo getMsgOnlineVo() {
		return msgOnlineVo;
	}
	public void setMsgOnlineVo(MsgOnlineVo msgOnlineVo) {
		this.msgOnlineVo = msgOnlineVo;
	}
	public void setMsgOnlineService(IMsgOnlineService msgOnlineService) {
		this.msgOnlineService = msgOnlineService;
	}
	public MsgOnlineDto getMsgOnlineDto() {
		return msgOnlineDto;
	}
	public void setMsgOnlineDto(MsgOnlineDto msgOnlineDto) {
		this.msgOnlineDto = msgOnlineDto;
	}
	public void setOnLineMsgService(IOnLineMsgService onLineMsgService) {
		this.onLineMsgService = onLineMsgService;
	}
	
}
