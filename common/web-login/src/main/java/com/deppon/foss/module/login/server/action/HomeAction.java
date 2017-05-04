package com.deppon.foss.module.login.server.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISystemHelpService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SystemHelpEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SystemHelpDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.UserException;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.server.util.CryptoUtil;
import com.deppon.foss.module.login.server.service.IAnnouncementService;
import com.deppon.foss.module.login.shared.define.MessageType;
import com.deppon.foss.module.login.shared.domain.AnnouncementEntity;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:主页WEB服务层</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
 * 1 2013-03-15 钟庭杰    新增
* </div>  
********************************************
 */
public class HomeAction extends AbstractAction {
	
	private static final long serialVersionUID = -5182857592065055743L;
	
	private IUserService userService;
	
	private IAnnouncementService announcementService;
	
	private String sysHelpId;
	
	private String systemHelp="";
	
	//新的密码
	private String newPassword;
	
	//最后登录时间
	private String lastLoginTime;
	
	//公告信息列表
	private List<AnnouncementEntity> announcementEntityList;
	
	//系统帮助列表
	private List<SystemHelpEntity> sysHelpInfoList;
	
	//系统帮助servcie
	private ISystemHelpService systemHelpService;

	/**
	 * 获得用户的最后登录时间
	 * @return
	 */
	public String homeInfo() {
		UserEntity user  = (UserEntity) UserContext.getCurrentUser();
		//获得登录用户最后登录时间
		user = userService.queryUserByEmpCode(user.getUserName());
		Date lastLoginDate = user.getLastLogin();
		// 更新用户最后登录时间
		userService.updateLastLoginDate(user.getId());			
		if(lastLoginDate!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			lastLoginTime = sdf.format(lastLoginDate);			
			// 更新用户最后登录时间
			//userService.updateLastLoginDate(user.getId());
		}else{
			lastLoginTime = messageBundle.getMessage(MessageType.FIRST_LOGIN);
		}
//		//获得系统帮助信息
//		SystemHelpDto dto = new SystemHelpDto();
//		SystemHelpEntity entity = new SystemHelpEntity();
//		dto.setSystemHelpEntity(entity);
//		sysHelpInfoList = systemHelpService.querySystemHelpEntity(dto, Integer.MAX_VALUE, this.start);
		return returnSuccess();
	}
	
	/**
	 * 
	 * <p>获得公告信息</p> 
	 * @author ztjie
	 * @date 2013-3-18 上午11:21:50
	 * @return
	 * @see
	 */
	@JSON
	public String announcementInfo() {
		//获得公告信息
		announcementEntityList = announcementService.queryAnnouncementByCount(this.start,this.limit);
		this.totalCount = announcementService.queryAllAnnouncementCount();
		return returnSuccess();
	}
	
	/**
	 * 
	 * <p>获得系统帮助信息</p> 
	 * @author ztjie
	 * @date 2013-3-18 上午11:21:50
	 * @return
	 * @see
	 */
	@JSON
	public String sytemHelpInfo() {
		//获得系统帮助信息
		SystemHelpDto dto = new SystemHelpDto();
		SystemHelpEntity entity = new SystemHelpEntity();
		dto.setSystemHelpEntity(entity);
		sysHelpInfoList = systemHelpService.querySystemHelpEntity(dto, this.limit, this.start);
		this.totalCount = systemHelpService.queryRecordCount(dto);
		return returnSuccess();
	}
	
	/**
	 * 
	 * <p>系统帮助</p>
	 * @author ztjie
	 * @date 2013-3-18 上午11:21:50
	 * @return
	 * @see 
	 */
	@JSON
	public String sysHelpInfoById() {
		//获得系统帮助信息
		SystemHelpEntity systemHelpEntity = systemHelpService.querySystemHelpEntityById(sysHelpId);
		if(systemHelpEntity!=null){
			systemHelp = systemHelpEntity.getSystemHelp();
		}
		return returnSuccess();
	}

	/**
	 * 
	 * <p>修改用户密码</p> 
	 * @author ztjie
	 * @date 2013-3-29 下午4:10:46
	 * @return
	 * @see
	 */
	@JSON
	public String updateCurrentUserPassword(){
		try{
			UserEntity user = FossUserContext.getCurrentUser();
			/*Date lastLoginDate = user.getLastLogin();
			if(lastLoginDate==null){
				// 只有当用户登录时间为空的时候，才更新用户最后登录时间
				userService.updateLastLoginDate(user.getId());				
			}*/
			newPassword = userService.modifyUserPwd(user.getUserName(), newPassword);
			return returnSuccess();
		}catch (UserException e) {
			return returnError(e);
		}
	}
	public boolean checkPSW(String password) {
		 //返回值flag为true则表示密码全相同字符，返回值false则表示密码非全相同字符
		  boolean flag = true;
		  char str = password.charAt(0);
		  for (int i = 0; i < password.length(); i++) {
		      if (str != password.charAt(i)) {
		          flag = false;
		          break;
		      }
		  }
		  return flag;
	}
	
	@JSON
	public String validatePassword(){
		try{
			UserEntity user = FossUserContext.getCurrentUser();
//			if(this.checkPSW(newPassword)){
//				return returnError(MessageType.PASSWORD_SAMECHAR_ERROR);
//			}
			String pwd = user.getPassword();
			String inputPwd = CryptoUtil.digestByMD5(newPassword);
			if(pwd.equals(inputPwd)){
				return returnSuccess();				
			}else{
				return returnError(MessageType.OLD_PASSWORD_ERROR);
			}
		}catch (UserException e) {
			return returnError(e);
		}
	}
	
	public void setAnnouncementService(IAnnouncementService announcementService) {
		this.announcementService = announcementService;
	}

	public List<AnnouncementEntity> getAnnouncementEntityList() {
		return announcementEntityList;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	
	/**
	 * @get
	 * @return sysHelpInfoList
	 */
	public List<SystemHelpEntity> getSysHelpInfoList() {
		/*
		 * @get
		 * @return sysHelpInfoList
		 */
		return sysHelpInfoList;
	}

	
	/**
	 * @set
	 * @param sysHelpInfoList
	 */
	public void setSysHelpInfoList(List<SystemHelpEntity> sysHelpInfoList) {
		/*
		 *@set
		 *@this.sysHelpInfoList = sysHelpInfoList
		 */
		this.sysHelpInfoList = sysHelpInfoList;
	}
	
	/**
	 * @get
	 * @return systemHelp
	 */
	public String getSystemHelp() {
		/*
		 * @get
		 * @return systemHelp
		 */
		return systemHelp;
	}

	
	/**
	 * @set
	 * @param sysHelpId
	 */
	public void setSysHelpId(String sysHelpId) {
		/*
		 *@set
		 *@this.sysHelpId = sysHelpId
		 */
		this.sysHelpId = sysHelpId;
	}

	/**
	 * @set
	 * @param systemHelpService
	 */
	public void setSystemHelpService(ISystemHelpService systemHelpService) {
		/*
		 *@set
		 *@this.systemHelpService = systemHelpService
		 */
		this.systemHelpService = systemHelpService;
	}
	
}
