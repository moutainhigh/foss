package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICallCenterWaybillInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CallCenterWaybillInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.util.define.FossConstants;
/**
 * 催单信息action
 * 
 * @author 132599-FOSS-shenweihua
 * @date 2014-07-26 16:10:10
 * @since
 * @version 0.01
 */
public class CallCenterWaybillInfoAction extends AbstractAction{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 催单信息service服务类
	 */
	private ICallCenterWaybillInfoService callCenterWaybillInfoService;
	/**
	 * 催单信息实体
	 */
	private CallCenterWaybillInfoEntity callCenterWaybillInfoEntity = new CallCenterWaybillInfoEntity();
	/**
	 * 催单信息实体list
	 */
	private List<CallCenterWaybillInfoEntity> callCenterInfoList;
	/**
	 * 根据条件查询催单信息
	 * @author 132599-FOSS-shenweihua
	 * @date 2014-07-28 16:10:10
	 * @return
	 */
	@JSON
	public String queryCallCenterWaybillInfos(){
		try {
			callCenterWaybillInfoEntity.setHasDone(FossConstants.INACTIVE);
		    // 查询有效的数据
			callCenterInfoList = callCenterWaybillInfoService.queryCallCenterInfos(callCenterWaybillInfoEntity,
			    limit, start);
		    totalCount = callCenterWaybillInfoService.queryRecordCount(callCenterWaybillInfoEntity);
		    return returnSuccess();
		} catch (BusinessException e) {
		    return returnError(e);
		}
 	}
	
	/**
	 * @author 132599-FOSS-shenweihua
	 * @date 2014-07-28 16:10:10
	 * 更新催单信息
	 * @return
	 */
	@JSON
	public String updateCallCenterInfo(){
		try {
			callCenterWaybillInfoService.updateCallCenterWaybillInfo(callCenterWaybillInfoEntity);
		    return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
		    return returnError(e);
		}
	}
	
	
	
	
	
	/**
	 * 获取催单信息实体
	 * @return
	 */
	public CallCenterWaybillInfoEntity getCallCenterWaybillInfoEntity() {
		return callCenterWaybillInfoEntity;
	}
	
	/**
	 * 设置催单信息实体
	 * @param callCenterWaybillInfoEntity
	 */
	public void setCallCenterWaybillInfoEntity(
			CallCenterWaybillInfoEntity callCenterWaybillInfoEntity) {
		this.callCenterWaybillInfoEntity = callCenterWaybillInfoEntity;
	}
	
	/**
	 * 设置催单信息服务service
	 * @param callCenterWaybillInfoService
	 */
	public void setCallCenterWaybillInfoService(
			ICallCenterWaybillInfoService callCenterWaybillInfoService) {
		this.callCenterWaybillInfoService = callCenterWaybillInfoService;
	}

	/**
	 * 获取催单信息实体list
	 * @return
	 */
	public List<CallCenterWaybillInfoEntity> getCallCenterInfoList() {
		return callCenterInfoList;
	}

	/**
	 * 设置催单信息实体list
	 * @param callCenterInfoList
	 */
	public void setCallCenterInfoList(
			List<CallCenterWaybillInfoEntity> callCenterInfoList) {
		this.callCenterInfoList = callCenterInfoList;
	}
	
	
}
