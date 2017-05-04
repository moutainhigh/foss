package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISecurityTfrMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SecurityTfrMotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.SecurityTfrMotorcadeVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * TODO(SecurityTfrMotorcade的Action类)
 * @author 187862-dujunhui
 * @date 2014-5-15 上午11:17:23
 * @since
 * @version
 */
public class SecurityTfrMotorcadeAction extends AbstractAction {

	/**
	 * TODO（序列化）
	 */
	private static final long serialVersionUID = -7982676262479047996L;
	
	private SecurityTfrMotorcadeVo vo;
	private ISecurityTfrMotorcadeService securityTfrMotorcadeService;
	/**
	 * @return  the vo
	 */
	public SecurityTfrMotorcadeVo getVo() {
		return vo;
	}
	/**
	 * @param vo the vo to set
	 */
	public void setVo(SecurityTfrMotorcadeVo vo) {
		this.vo = vo;
	}
	/**
	 * @param securityTfrMotorcadeService the securityTfrMotorcadeService to set
	 */
	public void setSecurityTfrMotorcadeService(
			ISecurityTfrMotorcadeService securityTfrMotorcadeService) {
		this.securityTfrMotorcadeService = securityTfrMotorcadeService;
	}
	
	/**
	 * 
	 *<P>查询保安组信息<P>
	 * @author :187862-杜军辉
	 * @date : 2014-5-15 上午11:17:23
	 * @return
	 */
	@JSON
	public String querySecurityTfrMotorcade(){
		try {
			SecurityTfrMotorcadeEntity queryEntity = vo.getEntity();
			queryEntity.setActive("Y");
			List<SecurityTfrMotorcadeEntity> entityList = securityTfrMotorcadeService.querySecurityTfrMotorcadeListBySecurityCode(queryEntity, limit, start);
		    vo.setEntityList(entityList);
		    // 查询总记录数
		    Long totalCount = securityTfrMotorcadeService.querySecurityCodeCount(queryEntity);
		    // 设置totalCount
		    this.setTotalCount(totalCount);
		    
			return returnSuccess();
		} catch (Exception e) {
			return returnError(e.getMessage());
		}
	}
	
	/**
	 * 
	 *<P>查询保安组编码是否存在<P>
	 * @author :187862-杜军辉
	 * @date : 2014-5-15 上午11:17:23
	 * @return
	 */
	@JSON
	public boolean existSecurityTfrMotorcade(){
		try {
			SecurityTfrMotorcadeEntity queryEntity = vo.getEntity();
			queryEntity.setActive("Y");
		    // 查询总记录数
		    Long totalCount = securityTfrMotorcadeService.querySecurityCodeCount(queryEntity);
		    // 设置totalCount
		    if(totalCount>0) {
		    	return true;    
		    }
		    else {
		    	return false;
		    }
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 
	 *<P>新增保安组信息<P>
	 * @author :187862-dujunhui
	 * @date : 2014-5-16 下午5:22:34
	 * @return
	 */
	@JSON
	public String addSecurityTfrMotorcade(){
		
		try {
			//获取新增的实体
			SecurityTfrMotorcadeEntity addEntity =vo.getEntity();
			
			UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
			
			addEntity.setCreateUser(user.getEmployee().getEmpName());
			addEntity.setCreateDate(new Date());		
			addEntity.setModifyUser(user.getEmployee().getEmpName());
			addEntity.setModifyDate(new Date());
			
			addEntity.setActive("Y");
			
			securityTfrMotorcadeService.addSecurityTfrMotorcade(addEntity);		
			
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}
	
	/**
	 * 
	 *<P>删除保安组信息<P>
	 * @author :187862-dujunhui
	 * @date : 2014-5-16 下午5:22:34
	 * @return
	 */
	@JSON
	public String deleteSecurityTfrMotorcade(){
		try {
			//逻辑删除
			UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
			SecurityTfrMotorcadeEntity deleteEntity = new SecurityTfrMotorcadeEntity();
			for(int i=0;i<vo.getCodeList().length;i++){
				deleteEntity.setId(vo.getCodeList()[i]);
				deleteEntity.setModifyDate(new Date());
				deleteEntity.setModifyUser(user.getEmployee().getEmpName());
							
				deleteEntity.setActive("N");
				
				securityTfrMotorcadeService.updateSecurityTfrMotorcade(deleteEntity);
			}	
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (Exception e) {
			return returnError(e.getMessage());
		}
	}
	
	/**
	 * 
	 *<P>更新保安组信息<P>
	 * @author :187862-dujunhui
	 * @date : 2014-5-16 下午5:22:34
	 * @return
	 */
	@JSON
	public String updateSecurityTfrMotorcade(){
		try {
			//获取实体
			SecurityTfrMotorcadeEntity updateEntity =vo.getEntity();
			
			UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
			
			updateEntity.setCreateUser(updateEntity.getModifyUser());
			updateEntity.setCreateDate(updateEntity.getModifyDate());		
			updateEntity.setModifyUser(user.getEmployee().getEmpName());
			updateEntity.setModifyDate(new Date());
			
			securityTfrMotorcadeService.updateSecurityTfrMotorcade(updateEntity);
			
			return returnSuccess(MessageType.SAVE_SUCCESS);		
		} catch (Exception e) {
			return returnError(e.getMessage());
		}
	}


}
