package com.deppon.foss.module.base.querying.server.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.common.api.server.service.IOnLineMsgService;
import com.deppon.foss.module.base.common.api.shared.dto.MsgOnlineDto;
import com.deppon.foss.module.base.common.api.shared.vo.MsgOnlineVo;
import com.deppon.foss.module.base.querying.server.service.IMsgOnlineService;


public class WriteNetMsgOnlineAction  extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 日志记录
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(WriteNetMsgOnlineAction.class);
	/**
	 * 在线通知Dto
	 */
	private MsgOnlineVo msgOnlineVo;
	/**
	 * 在线通知service
	 */
	private IMsgOnlineService msgOnlineService;
	/**
	 * 在线通知service
	 */
	private IOnLineMsgService onLineMsgService;
	
	/**
	 * 组织service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 站内消息初始化
	 * 
	 * @author 130346-foss-lifanghong
	 * @param
	 * @date 201-07-15 下午5:14:19
	 * @return
	 */
	public String writeNetMsgline() {
		return returnSuccess();
	}
	@JSON
	public String queryOrgByBillNo(){
		
	try {
		if(msgOnlineVo == null){
			return returnSuccess();
		}
		MsgOnlineDto msgOnlineDto = msgOnlineService.queryOrgByBillNo(msgOnlineVo.getMsgOnlineDto().getBillNo());
		msgOnlineVo.setMsgOnlineDto(msgOnlineDto);
	}catch (BusinessException e){
		LOGGER.debug(e.getMessage());
		return returnError(e);
	}
		
		return returnSuccess();
	}
	/**
	 * 根据正单号，交接单查询运单信息
	 * 
	 * @author 130346-foss-lifanghong
	 * @param
	 * @date 2013-07-15 下午5:14:19
	 * @return
	 */
	@JSON
	public String queryBillNoByNo(){
		try {
		//	List<MsgOnlineDto> msgOnlineDtos = new ArrayList<MsgOnlineDto>();
			//当前用户登录信息
			List<MsgOnlineDto> msgOnlineDtos = msgOnlineService.queryBillNoByNo(msgOnlineVo.getMsgOnlineDto());
			msgOnlineVo.setMsgOnlineDtos(msgOnlineDtos);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage());
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 *发送全网消息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2012-12-20 下午5:14:19
	 * @return
	 */
	@JSON
	public String sendMsgSend(){
		try {
			//Boolean message =false;
			//当前用户登录信息
			//message = onLineMsgService.addOnlineMsgList(msgOnlineVo.getMsgOnlineDtos());
			onLineMsgService.addOnlineMsgList(msgOnlineVo.getMsgOnlineDtos());
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			//日志记录
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
		
	}
	/**
	 *验证部门是否存在
	 * 
	 * @author 130346-foss-lifanghong
	 * @param
	 * @date 2012-12-20 下午5:14:19
	 * @return
	 */
	@JSON
	public String queryOrgByCode(){
		//OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = new OrgAdministrativeInfoEntity();
		try {
			if(StringUtil.isBlank(msgOnlineVo.getMsgOnlineEntity().getReceiveOrgCode())){
				return returnError("请输入正确部门名称！");
			}else{
					//当前用户登录信息
					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(msgOnlineVo.getMsgOnlineEntity().getReceiveOrgCode());
					if(orgAdministrativeInfoEntity==null){
						return returnError("请输入正确部门名称！");
					}else{
						return returnSuccess();
					}
				 }
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage());
			return returnError(e);
		}
		
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
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
	public void setOnLineMsgService(IOnLineMsgService onLineMsgService) {
		this.onLineMsgService = onLineMsgService;
	}
	
}
