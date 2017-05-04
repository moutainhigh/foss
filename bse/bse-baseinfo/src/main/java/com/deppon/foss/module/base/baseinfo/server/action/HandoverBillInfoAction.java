package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IHandoverBillInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.HandoverBillInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.HandoverBillInfoException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.HandoverBillInfoVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

public class HandoverBillInfoAction extends AbstractAction {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	private HandoverBillInfoVo handoverBillInfoVo;
	private IHandoverBillInfoService handoverBillInfoService;
	/**
	 * 
	 * <p>查询交单管理信息</p> 
	 * @author 189284 
	 * @date 2015-4-16 上午10:27:16
	 * @return
	 * @see
	 */
	@JSON
	public String queryHandoverBillInfoList(){
		HandoverBillInfoEntity handoverBillInfo=handoverBillInfoVo.getHandoverBillInfoEntity();
		try {
			List<HandoverBillInfoEntity> handoverBillInfoEntitys=handoverBillInfoService.queryHandoverBillInfoList(handoverBillInfo,start, limit);
			handoverBillInfoVo.setHandoverBillInfoEntitys(handoverBillInfoEntitys);
			totalCount=handoverBillInfoService.queryHandoverBillInfoCount(handoverBillInfo);
			return returnSuccess();
		} catch (HandoverBillInfoException e) {
			return returnError(e.getMessage());
		}
		
	}
	/**
	 * 
	 * <p>新增 交单管理</p> 
	 * @author 189284 
	 * @date 2015-4-16 上午10:28:56
	 * @return
	 * @see
	 */
	@JSON
	public String addHandoverBillInfo(){
		HandoverBillInfoEntity handoverBillInfo=handoverBillInfoVo.getHandoverBillInfoEntity();
		try {
			handoverBillInfoService.addHandoverBillInfo(handoverBillInfo,FossUserContext.getCurrentInfo().getEmpCode());
			return returnSuccess();
		} catch (HandoverBillInfoException e) {
		  return	returnError(e.getMessage());
		}
		
	}
	/**
	 * 
	 * <p>修改交单管理</p> 
	 * @author 189284 
	 * @date 2015-4-16 上午10:29:20
	 * @return
	 * @see
	 */
	@JSON
	public String updateHandoverBillInfo(){
		HandoverBillInfoEntity handoverBillInfo=handoverBillInfoVo.getHandoverBillInfoEntity();
		try {
			handoverBillInfoService.updateHandoverBillInfo(handoverBillInfo,FossUserContext.getCurrentInfo().getEmpCode());
			return returnSuccess();
		} catch (HandoverBillInfoException e) {
			return returnError(e.getMessage());
		}
		
	}
	@JSON
	public String deleteHandoverBillInfo(){
		List<String> ids=handoverBillInfoVo.getIds();
		try {
			handoverBillInfoService.deleteHandoverBillInfo(ids);
			return returnSuccess();
		} catch (HandoverBillInfoException e) {
		  return	returnError(e.getMessage());
		}
		
	}
	
	/**
	 * @param handoverBillInfoService the handoverBillInfoService to set
	 */
	public void setHandoverBillInfoService(
			IHandoverBillInfoService handoverBillInfoService) {
		this.handoverBillInfoService = handoverBillInfoService;
	}
	/**
	 * @return  the handoverBillInfoVo
	 */
	public HandoverBillInfoVo getHandoverBillInfoVo() {
		return handoverBillInfoVo;
	}
	/**
	 * @param handoverBillInfoVo the handoverBillInfoVo to set
	 */
	public void setHandoverBillInfoVo(HandoverBillInfoVo handoverBillInfoVo) {
		this.handoverBillInfoVo = handoverBillInfoVo;
	}
	
 
}
