package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.Date;
import java.util.List;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressAutoScheduleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressAutoScheduleVo;
import com.deppon.foss.module.base.baseinfo.server.service.impl.ExpressAutoScheduleService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

public class ExpressAutoScheduleAction extends AbstractAction {

	/**
	 *快递类快递自动调度城市管理action
	 */
	private static final long serialVersionUID = 1L;
	
	private ExpressAutoScheduleService expressAutoScheduleService;
	private ExpressAutoScheduleVo vo = new ExpressAutoScheduleVo();

	public void setExpressAutoScheduleService(
			ExpressAutoScheduleService expressAutoScheduleService) {
		this.expressAutoScheduleService = expressAutoScheduleService;
	}
	public ExpressAutoScheduleVo getVo() {
		return vo;
	}
	public void setVo(ExpressAutoScheduleVo vo) {
		this.vo = vo;
	}
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.base.baseinfo.server.action.ExpressAutoScheduleAction.addExpressAutoSchedule
	 * @Description:新增快递自动调度城市管理信息
	 *
	 * @return
	 *
	 * @version:v1.0
	 * @author:yangkang
	 * @date:2014-4-24 上午10:29:45
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-4-24    yangkang      v1.0.0         create
	 */
	public String addExpressAutoSchedule(){
		try {
			//获取新增的实体
			ExpressAutoScheduleEntity addExpressAutoScheduleEntity =vo.getEntity();
			//判断同一个部门是否重复添加自动调度管理信息
			addExpressAutoScheduleEntity.setActive("Y");
			Long totalCount = expressAutoScheduleService.queryCityNameCount(addExpressAutoScheduleEntity);
			if(totalCount>0){
				return returnError("该城市已经添加自动调度信息，不允许重复添加！");
			}
			//结束时间不能小于开始时间
			String startTime = addExpressAutoScheduleEntity.getStartTime().substring(
					NumberConstants.STRING_LOCATION_0, NumberConstants.STRING_LOCATION_2)+addExpressAutoScheduleEntity.getStartTime().substring(NumberConstants.STRING_LOCATION_3);
			String endTime = addExpressAutoScheduleEntity.getEndTime().substring(NumberConstants.STRING_LOCATION_0, NumberConstants.STRING_LOCATION_2)+addExpressAutoScheduleEntity.getEndTime().substring(NumberConstants.STRING_LOCATION_3);
			if(Integer.parseInt(endTime)<=Integer.parseInt(startTime)){
				
				return returnError("结束时间不能小于开始时间！");
				
			}
			UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
			
			addExpressAutoScheduleEntity.setCreateUser(user.getEmployee().getEmpCode());
			addExpressAutoScheduleEntity.setCreateDate(new Date());
			
			addExpressAutoScheduleEntity.setModifyUser(user.getEmployee().getEmpName());
			addExpressAutoScheduleEntity.setModifyUserPsncode(user.getEmployee().getEmpCode());
			addExpressAutoScheduleEntity.setModifyDate(new Date());
			addExpressAutoScheduleEntity.setActive("Y");
			
			expressAutoScheduleService.addExpressAutoSchedule(addExpressAutoScheduleEntity);
			
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.base.baseinfo.server.action.ExpressAutoScheduleAction.queryExpressAutoSchedule
	 * @Description:分页查询快递自动调度城市管理的信息
	 *
	 * @return
	 *
	 * @version:v1.0
	 * @author:yangkang
	 * @date:2014-4-24 上午10:35:46
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-4-24    yangkang      v1.0.0         create
	 */
	public String queryExpressAutoSchedule(){
		try {
			ExpressAutoScheduleEntity queryEntity = vo.getEntity();
			//查询已经启用的信息
			queryEntity.setActive("Y");
			//分页查询信息
		    List<ExpressAutoScheduleEntity> entityList = expressAutoScheduleService.queryExpressAutoScheduleListByCityName(queryEntity, this.getLimit(), this.getStart());	    
		    
		    vo.setEntityList(entityList);
		    // 查询总记录数
		    Long totalCount = expressAutoScheduleService.queryRecordCount(queryEntity);
		    // 设置totalCount
		    this.setTotalCount(totalCount);
		    
			return returnSuccess();
		} catch (Exception e) {
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.base.baseinfo.server.action.ExpressAutoScheduleAction.deleteExpressAutoSchedule
	 * @Description:批量删除快递自动调度城市管理信息
	 *
	 * @return
	 *
	 * @version:v1.0
	 * @author:yangkang
	 * @date:2014-4-24 上午10:47:18
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-4-24    yangkang      v1.0.0         create
	 */
	public String deleteExpressAutoSchedule(){
		try {
		//逻辑删除，将原来的数据设置为不启用的状态，并把当前时间作为版本号
		UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
		ExpressAutoScheduleEntity updateEntity = new ExpressAutoScheduleEntity();
		for(int i=0;i<vo.getCodeList().length;i++){
			updateEntity.setId(vo.getCodeList()[i]);
			updateEntity.setModifyDate(new Date());
			updateEntity.setModifyUser(user.getEmployee().getEmpName());
			updateEntity.setModifyUserPsncode(user.getEmployee().getEmpCode());
			updateEntity.setVersionNo(new Date().getTime());
			updateEntity.setActive("N");
			
			expressAutoScheduleService.updateExpressAutoSchedule(updateEntity);
		}
		
		return returnSuccess(MessageType.DELETE_SUCCESS);
	} catch (Exception e) {
		return returnError(e.getMessage());
	}
	}
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.base.baseinfo.server.action.ExpressAutoScheduleAction.updateExpressAutoSchedule
	 * @Description:更新快递自动调度城市管理的信息
	 *
	 * @return
	 *
	 * @version:v1.0
	 * @author:yangkang
	 * @date:2014-4-24 上午10:49:46
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-4-24    yangkang      v1.0.0         create
	 */
	@JSON
	public String updateExpressAutoSchedule(){
		try {
			//获取更新实体
			ExpressAutoScheduleEntity updateEntity =vo.getEntity();
			//结束时间不能小于开始时间
			String startTime = updateEntity.getStartTime().substring(NumberConstants.STRING_LOCATION_0, NumberConstants.STRING_LOCATION_2)+updateEntity.getStartTime().substring(NumberConstants.STRING_LOCATION_3);
			String endTime = updateEntity.getEndTime().substring(NumberConstants.STRING_LOCATION_0, NumberConstants.STRING_LOCATION_2)+updateEntity.getEndTime().substring(NumberConstants.STRING_LOCATION_3);
			if(Integer.parseInt(endTime)<=Integer.parseInt(startTime)){
				
				return returnError("结束时间不能小于开始时间！");
				
			}
			UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
			
			updateEntity.setModifyUser(user.getEmployee().getEmpName());
			updateEntity.setModifyUserPsncode(user.getEmployee().getEmpCode());
			updateEntity.setModifyDate(new Date());
			
			expressAutoScheduleService.updateExpressAutoSchedule(updateEntity);
			
			return returnSuccess(MessageType.SAVE_SUCCESS);
			
		} catch (Exception e) {
			return returnError(e.getMessage());
		}
	}
}
