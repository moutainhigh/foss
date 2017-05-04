package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPackagingSupplierService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.PackagingSupplierVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * TODO(PackagingSupplierAction实现)
 * @author 187862-杜军辉
 * @date 2014-5-9 上午8:12:22
 * @since
 * @version
 */
public class PackagingSupplierAction extends AbstractAction {

	/**
	 * TODO（序列化）
	 */
	private static final long serialVersionUID = 7788925406560075330L;
	
	private PackagingSupplierVo vo;
	
	private IPackagingSupplierService packagingSupplierService;
	
	/**
	 * @return  the vo
	 */
	public PackagingSupplierVo getVo() {
		return vo;
	}

	/**
	 * @param vo the vo to set
	 */
	public void setVo(PackagingSupplierVo vo) {
		this.vo = vo;
	}

	/**
	 * @param packagingSupplierService the packagingSupplierService to set
	 */
	public void setPackagingSupplierService(
			IPackagingSupplierService packagingSupplierService) {
		this.packagingSupplierService = packagingSupplierService;
	}
	
	/**
	 * 
	 *<P>新增包装供应商基础信息<P>
	 * @author :187862-杜军辉
	 * @date : 2014-5-9 上午8:12:22
	 * @return
	 */
	@JSON
	public String addPackagingSupplier(){
		
		try {
			//获取新增的实体
			PackagingSupplierEntity addEntity =vo.getEntity();

			//同一外场下同一包装供应商信息不允许重复添加
			addEntity.setActive("Y");
			//查询信息
		    PackagingSupplierEntity entity = packagingSupplierService.queryPackagingSupplierByEntity(addEntity.getOrgCodeCode(), addEntity.getPackagingSupplierCode());	    
		    if(entity!=null){
				return returnError("该供应商信息已在本外场添加，不允许重复添加！");
			}
		    
			UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
			
			addEntity.setCreateUser(user.getEmployee().getEmpName());
			addEntity.setCreateDate(new Date());		
			addEntity.setModifyUser(user.getEmployee().getEmpName());
			addEntity.setModifyDate(new Date());
			
			addEntity.setActive("Y");
			
			packagingSupplierService.addPackagingSupplier(addEntity);		
			
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}
	
	/**
	 * 
	 *<P>删除供应商信息<P>
	 * @author :187862-杜军辉
	 * @date : 2014-5-9 上午8:12:22
	 * @return
	 */
	@JSON
	public String deletePackagingSupplier(){
		try {
			//逻辑删除
			UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
			PackagingSupplierEntity deleteEntity = new PackagingSupplierEntity();
				deleteEntity.setModifyDate(new Date());
				deleteEntity.setModifyUser(user.getEmployee().getEmpName());
				//以删除时间作为版本号
				deleteEntity.setVersionNo(new Date().getTime());				
				deleteEntity.setActive("N");			
			String[] codeList=vo.getCodeList();
			packagingSupplierService.deletePackagingSupplier(codeList);
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (Exception e) {
			return returnError(e.getMessage());
		}
	}
	
	/**
	 * 
	 *<P>更新供应商信息<P>
	 * @author :187862-杜军辉
	 * @date : 2014-5-9 上午8:12:22
	 * @return
	 */
	@JSON
	public String updatePackagingSupplier(){
		try {
			//获取实体
			PackagingSupplierEntity updateEntity =vo.getEntity();
			
			UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
			
			updateEntity.setCreateUser(updateEntity.getModifyUser());
			updateEntity.setCreateDate(updateEntity.getModifyDate());		
			updateEntity.setModifyUser(user.getEmployee().getEmpName());
			updateEntity.setModifyDate(new Date());
			
			packagingSupplierService.updatePackagingSupplier(updateEntity);
			
			return returnSuccess(MessageType.SAVE_SUCCESS);		
		} catch (Exception e) {
			return returnError(e.getMessage());
		}
	}
	
	/**
	 * 
	 *<P>分页查询供应商信息<P>
	 * @author :187862-杜军辉
	 * @date : 2014-5-9 上午8:12:22
	 * @return
	 */
	@JSON
	public String queryPackagingSupplier(){
		try {
			PackagingSupplierEntity queryEntity = vo.getEntity();
			queryEntity.setActive("Y");
			//分页查询信息
		    List<PackagingSupplierEntity> entityList = packagingSupplierService.queryPackagingSupplierListByOrgCode(queryEntity, limit, start);	    
		    
		    vo.setEntityList(entityList);
		    // 查询总记录数
		    Long totalCount = packagingSupplierService.queryOrgCodeCount(queryEntity);
		    // 设置totalCount
		    this.setTotalCount(totalCount);
		    
			return returnSuccess();
		} catch (Exception e) {
			return returnError(e.getMessage());
		}
	}
	
/*	*//**
	 * 
	 *<P>精确查询供应商信息<P>
	 * @author :187862-杜军辉
	 * @date : 2014-5-9 上午8:12:22
	 * @return
	 *//*
	@JSON
	public String queryAccuratePackagingSupplier(){
		try {
			PackagingSupplierEntity queryEntity = vo.getEntity();
			queryEntity.setActive("Y");
			//精确查询信息
		    PackagingSupplierEntity entityList = packagingSupplierService.queryPackagingSupplierByEntity(queryEntity.getOrgCodeCode(), queryEntity.getPackagingSupplierCode());	    
		    
		    vo.setEntity(entityList);
		    // 查询总记录数
		    Long totalCount = packagingSupplierService.queryRecordCount(queryEntity);
		    // 设置totalCount
		    this.setTotalCount(totalCount);
		    
			return returnSuccess();
		} catch (Exception e) {
			return returnError(e.getMessage());
		}
	}*/
	
}
