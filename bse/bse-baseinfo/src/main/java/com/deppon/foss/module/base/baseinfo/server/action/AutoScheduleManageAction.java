package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.Date;
import java.util.List;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAutoScheduleManageService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AutoScheduleManageEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AutoScheduleManageVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

public class AutoScheduleManageAction extends AbstractAction {
    
    //"自动调度管理信息"参数和结果对象
    private AutoScheduleManageVo vo = new AutoScheduleManageVo();
    
    //"自动调度管理信息"服务
    private IAutoScheduleManageService autoScheduleManageService;
	/**
	 * 自动调度的开启和关闭action
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 *<P>新增部门自动调度管理的信息<P>
	 * @author :130376-yangkang
	 * @date : 2014-4-21
	 * @return
	 */
	@JSON
	public String addautoScheduleManage(){
		
		try {
			//获取新增的实体
			AutoScheduleManageEntity addAutoScheduleManageEntity =vo.getEntity();
			//判断同一个部门是否重复添加自动调度管理信息
			addAutoScheduleManageEntity.setActive("Y");
			Long totalCount = autoScheduleManageService.queryDeptNameCount(addAutoScheduleManageEntity);
			if(totalCount>0){
				return returnError("该部门已经添加自动调度信息，不允许重复添加！");
			}
			//结束时间不能小于开始时间
			String startTime = addAutoScheduleManageEntity.getStartTime().substring(
					NumberConstants.STRING_LOCATION_0, NumberConstants.STRING_LOCATION_2)+addAutoScheduleManageEntity.getStartTime().substring(NumberConstants.STRING_LOCATION_3);
			String endTime = addAutoScheduleManageEntity.getEndTime().substring(
					NumberConstants.STRING_LOCATION_0, NumberConstants.STRING_LOCATION_2)+addAutoScheduleManageEntity.getEndTime().substring(NumberConstants.STRING_LOCATION_3);
			if(Integer.parseInt(endTime)<=Integer.parseInt(startTime)){
				
				return returnError("结束时间不能小于开始时间！");
				
			}
			UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
			
			addAutoScheduleManageEntity.setCreateUser(user.getEmployee().getEmpCode());
			addAutoScheduleManageEntity.setCreateDate(new Date());
			
			addAutoScheduleManageEntity.setModifyUser(user.getEmployee().getEmpName());
			addAutoScheduleManageEntity.setModifyUserPsncode(user.getEmployee().getEmpCode());
			addAutoScheduleManageEntity.setModifyDate(new Date());
			addAutoScheduleManageEntity.setActive("Y");
			
			autoScheduleManageService.addautoScheduleManage(addAutoScheduleManageEntity);
			
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	 *<P>分页查询部门自动调度管理信息<P>
	 * @author :130376-yangkang
	 * @date : 2014-4-22
	 * @return
	 */
	@JSON
	public String queryautoScheduleManages(){
		try {
			AutoScheduleManageEntity queryEntity = vo.getEntity();
			
			queryEntity.setActive("Y");
			//分页查询信息
		    List<AutoScheduleManageEntity> entityList = autoScheduleManageService.queryAutoScheduleManageListByDeptName(queryEntity, this.getLimit(), this.getStart());	    
		    
		    vo.setEntityList(entityList);
		    // 查询总记录数
		    Long totalCount = autoScheduleManageService.queryRecordCount(queryEntity);
		    // 设置totalCount
		    this.setTotalCount(totalCount);
		    
			return returnSuccess();
		} catch (Exception e) {
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	 *<P>删除部门自动调度管理信息<P>
	 * @author :130376-yangkang
	 * @date : 2014-4-22
	 * @return
	 */
	@JSON
	public String deleteautoScheduleManage(){
		try {
			//逻辑删除，将原来的数据设置为不启用的状态，并把当前时间作为版本号
			UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
			AutoScheduleManageEntity updateEntity = new AutoScheduleManageEntity();
			for(int i=0;i<vo.getCodeList().length;i++){
				updateEntity.setId(vo.getCodeList()[i]);
				updateEntity.setModifyDate(new Date());
				updateEntity.setModifyUser(user.getEmployee().getEmpName());
				updateEntity.setModifyUserPsncode(user.getEmployee().getEmpCode());
				updateEntity.setVersionNo(new Date().getTime());
				updateEntity.setActive("N");
				
				autoScheduleManageService.updateAutoScheduleManage(updateEntity);
			}
			
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (Exception e) {
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	 *<P>更新部门自动调度管理信息<P>
	 * @author :130376-yangkang
	 * @date : 2014-4-22
	 * @return
	 */
	@JSON
	public String updateautoScheduleManage(){
		try {
			//获取实体
			AutoScheduleManageEntity updateAutoScheduleManageEntity =vo.getEntity();
			
			//结束时间不能小于开始时间
			String startTime = updateAutoScheduleManageEntity.getStartTime().substring(
					NumberConstants.STRING_LOCATION_0, NumberConstants.STRING_LOCATION_2)+updateAutoScheduleManageEntity.getStartTime().substring(NumberConstants.STRING_LOCATION_3);
			String endTime = updateAutoScheduleManageEntity.getEndTime().substring(NumberConstants.STRING_LOCATION_0, NumberConstants.STRING_LOCATION_2)+updateAutoScheduleManageEntity.getEndTime().substring(NumberConstants.STRING_LOCATION_3);
			if(Integer.parseInt(endTime)<=Integer.parseInt(startTime)){
				
				return returnError("结束时间不能小于开始时间！");
				
			}
			UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
			
			updateAutoScheduleManageEntity.setModifyUser(user.getEmployee().getEmpName());
			updateAutoScheduleManageEntity.setModifyUserPsncode(user.getEmployee().getEmpCode());
			updateAutoScheduleManageEntity.setModifyDate(new Date());
			
			autoScheduleManageService.updateAutoScheduleManage(updateAutoScheduleManageEntity);
			
			return returnSuccess(MessageType.SAVE_SUCCESS);
			
		} catch (Exception e) {
			return returnError(e.getMessage());
		}
	}
	public void setAutoScheduleManageService(
			IAutoScheduleManageService autoScheduleManageService) {
		this.autoScheduleManageService = autoScheduleManageService;
	}
	public AutoScheduleManageVo getVo() {
		return vo;
	}
	public void setVo(AutoScheduleManageVo vo) {
		this.vo = vo;
	}
	
	
	
}
