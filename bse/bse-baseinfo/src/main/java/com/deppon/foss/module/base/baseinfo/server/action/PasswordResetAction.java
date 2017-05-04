package com.deppon.foss.module.base.baseinfo.server.action;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.PasswordRestVo;
import com.deppon.foss.module.base.dict.api.shared.exception.MessageType;
/**
 * 密码初始化的action
 * @author zengjunfan
 * @date 2013-4-24下午6:20:54
 */
public class PasswordResetAction extends AbstractAction {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER =LoggerFactory.getLogger(PasswordResetAction.class);
	/**
	 * 用于前后台数据交换的vo
	 */
	private PasswordRestVo vo;
	
	private IUserService userService;
	
	/**
	 * 查询用户信息
	 * @author zengjunfan
	 * @date 2013-4-24下午6:34:06
	 *@return
	 */
	@JSON
	public String queryAllUserEntity(){
		try {
			List<UserEntity> entitys =userService.queryUserListBySelectiveCondition(vo.getUserEntity(), this.start, this.limit);
			
			//设置用户信息集合
			vo.setUserEntitys(entitys);
			//获取记录数
			Long totalCount =userService.queryUserCountBySelectiveCondition(vo.getUserEntity());
			//设置记录数
			this.setTotalCount(totalCount);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(),e);
			return returnError(e);
		}
	}
	/**
	 * 根据传进来的id 进行查询用户信息
	 * @author zengjunfan
	 * @date 2013-4-24下午6:58:49
	 *@return
	 */
	@JSON
	public String queryUserEntityById(){
		try {
			//获取用户信息
			UserEntity entity =userService.queryUserListBySelectiveCondition(vo.getUserEntity().getId());
			//设置用户信息
			vo.setUserEntity(entity);
			
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	/**
	 * 更新用户的信息（初始化密码）
	 * @author zengjunfan
	 * @date 2013-4-24下午7:01:23
	 *@return
	 */
	@JSON
	public String updateUserEntity(){
		try {
			//获取传过来的修改用户
			UserEntity entity =vo.getUserEntity();
			//设置新的密码
			userService.modifyUserPwd(entity.getUserName(), entity.getPassword());
			
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	/**
	 * this is getter method
	 *@return
	 */
	public PasswordRestVo getVo() {
		return vo;
	}
	/**
	 * this is setter method
	 *@param vo
	 */
	public void setVo(PasswordRestVo vo) {
		this.vo = vo;
	}
	/**
	 * this is setter method
	 *@param userService
	 */
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
}
