package com.deppon.foss.module.pickup.waybill.server.action;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.waybill.api.server.service.ITodoActionService;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExceptMsgActionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabelGoodTodoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PendingMsgActionDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillRfcChangeException;
import com.deppon.foss.module.pickup.waybill.shared.vo.ExceptMsgActionVo;

public class ExceptMsgAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	
	//查询异常Vo
	private ExceptMsgActionVo vo;
	
	ITodoActionService todoActionService;	
	
	/**
	 * @功能  根据条件查询所有异常数据
	 * @author 105888-foss-zhangxingwang
	 * @date 2013-7-26 20:43:48
	 * @return
	 */
	@JSON
	public String queryExceptMsgAction(){
		try{
			//查询所有异常数据
			Long totalCount = todoActionService.queryExceptMsgInfoCount(vo.getExceptMsgConditionDto());
			if(totalCount != null && totalCount.intValue() > 0){
				List<ExceptMsgActionDto> exceptMsgActionDtoList = todoActionService.queryExceptMsgActionDto(vo.getExceptMsgConditionDto(), this.getStart(), this.getLimit());
				vo.setExceptMsgActionDtoList(exceptMsgActionDtoList);
			}else{
				vo.setExceptMsgActionDtoList(null);
				this.setTotalCount(totalCount);
			}
			this.setTotalCount(totalCount);
		}catch (BusinessException e) {
			returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 查询所有未生成待办的数据
	 * @author 105888-foss-zhangxingwang
	 * @date 2013-7-30 14:24:50
	 * @return
	 */
	@JSON
	public String queryExceptMsgPendingTodo(){
		try{
			Long totalCount = todoActionService.queryPendTodoInfoCount(vo.getPendingMsgConditionDto());
			if(totalCount != null && totalCount.intValue() > 0){
				List<PendingMsgActionDto> pendingMsgActionDtoList = todoActionService.queryPendTodoActionDto(vo.getPendingMsgConditionDto(), this.getStart(), this.getLimit());
				vo.setPendingMsgActionDtoList(pendingMsgActionDtoList);
			}else{
				vo.setPendingMsgActionDtoList(null);
				this.setTotalCount(totalCount);
			}
			this.setTotalCount(totalCount);
		}catch (BusinessException e) {
			returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 查询所有该更改单所有代办异常数据,依据此重置代办
	 * @return
	 */
	@JSON
	public String queryTodoExceptMsg(){
		try{
			List<LabelGoodTodoDto> labelGoodTodoList = todoActionService.queryLabelGoodTodo(vo.getExceptMsgConditionDto().getWaybillRfcId());
			if(CollectionUtils.isNotEmpty(labelGoodTodoList)){
				vo.setLabelGoodTodoList(labelGoodTodoList);
			}
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 重置代办数据并且重新生成代办
	 * @author 105888-foss-zhangxingwang
	 * @date 2013-8-7 9:25:30
	 * @return
	 */
	@JSON
	public String updateTodoStatusAndNewPathDetail(){
		try{
			if(vo.getExceptMsgConditionDto() == null){
				throw new WaybillRfcChangeException("传入参数有误，请重试");
			}
			todoActionService.updateLabelTodoAndNewPathDetail(vo.getExceptMsgConditionDto().getWaybillRfcId());
		}catch (BusinessException e) {
				return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 重置Pend代办failReason数据
	 * @author 105888-foss-zhangxingwang
	 * @date 2013-8-7 9:25:30
	 * @return
	 */
	@JSON
	public String updatePendTodoFailReason(){
		try{
			todoActionService.updatePendTodoFailReason(vo.getPendTodoIdList());
		}catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	@JSON
	public String updateExceptMsgBatch(){
		try{
			todoActionService.updateExceptMsgBatch(vo.getWaybillRfcIdList());
		}catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	
	@JSON
	public String updateExceptMsgBatchStatus(){
		try{
			todoActionService.updateExceptMsgBatchStatus(vo.getWaybillRfcIdList());
		}catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	//get、set方法
	public ExceptMsgActionVo getVo() {
		return vo;
	}

	public void setVo(ExceptMsgActionVo vo) {
		this.vo = vo;
	}

	public void setTodoActionService(ITodoActionService todoActionService) {
		this.todoActionService = todoActionService;
	}

}
