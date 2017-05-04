package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAcceptPointSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AcceptPointSalesChildrenDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AcceptPointSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AcceptPointSalesDeptDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * 接驳点营业部映射关系action
 * @author 132599 ShenWeiHua
 * @date 2015-4-20 下午7:26:50
 */
public class AcceptPointSalesDeptAction extends AbstractAction{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AcceptPointSalesDeptAction.class);
    
    /**
     * 接驳点对应营业部映射的service
     */
    private IAcceptPointSalesDeptService acceptPointSalesDeptService;
    
    /**
     * 接驳点与营业部映射关系的DTO
     */
    private AcceptPointSalesDeptDto acceptPointSalesDeptDto;
    
    
    
    /**
     * 添加接驳点与营业部映射关系
     * 
     * @author 132599-FOSS-ShenWeiHua
     * @date 2015-4-21 下午5:44:10
     * @return
     * @see
     */
    @JSON
    public String addAcceptPointSalesDept() {
	try {
		//判断传入参数是否为null
	    if(null == acceptPointSalesDeptDto ||null == acceptPointSalesDeptDto.getAcceptPointEntity()
	    		|| CollectionUtils.isEmpty(acceptPointSalesDeptDto.getAcceptPointEntity().getChildrenSalesDeptEntitys())){
	    	LOGGER.info("增加接驳点与营业部映射关系传入的参数不能为空!");
	    	return null;
	    }
	    
	    AcceptPointSalesDeptEntity entity =acceptPointSalesDeptDto.getAcceptPointEntity();
		
	    if(StringUtils.isEmpty(entity.getAcceptPointCode())){
	    	LOGGER.info("接驳点为空!");
	    	return null;
	    }
	  
	    // 保存成功
	    acceptPointSalesDeptService.addAcceptPointSalesDept(entity);

	    
	    return returnSuccess(MessageType.SAVE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * 作废接驳点与营业部映射关系
     * 
     * @author 132599-FOSS-ShenWeiHua
     * @date 2015-4-21 下午5:44:10
     * @return
     * @see
     */
    @JSON
    public String deleteAcceptPointSalesDept() {
	try {
		if(null == acceptPointSalesDeptDto || StringUtils.isEmpty(acceptPointSalesDeptDto.getId())){
	    	LOGGER.info("删除接驳点与营业部映射关系传入的参数不能为空!");
	    	return null;
	    }
		String id = acceptPointSalesDeptDto.getId();
		Date modifyDate = new Date();
		String modifyUser = FossUserContext.getCurrentInfo().getEmpCode();
		String modifyUserName = FossUserContext.getCurrentInfo().getEmpName();
		acceptPointSalesDeptService.deleteAcceptPointSalesDeptById(id, modifyDate, modifyUser, modifyUserName);
	    return returnSuccess(MessageType.DELETE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * 修改接驳点与营业部映射关系
     * 
     * @author 132599-FOSS-ShenWeiHua
     * @date 2015-4-21 下午5:44:10
     * @see
     */
    @JSON
    public String updateAcceptPointSalesDept() {
	try {
		//判断传入参数是否为null
	    if(null == acceptPointSalesDeptDto || null == acceptPointSalesDeptDto.getAcceptPointEntity()
	    		|| CollectionUtils.isEmpty(acceptPointSalesDeptDto.getAcceptPointEntity().getChildrenSalesDeptEntitys())){
	    	LOGGER.info("增加接驳点与营业部映射关系传入的参数不能为空!");
	    	return null;
	    }
	    // 保存成功返回一个对象
	    acceptPointSalesDeptService.updateAcceptPointSalesDept(acceptPointSalesDeptDto.getAcceptPointEntity());
	    return returnSuccess(MessageType.UPDATE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * 修改接驳点与营业部映射关系的状态信息（即启用未启用）
     * 
     * @author 132599-FOSS-ShenWeiHua
     * @date 2015-4-21 下午5:44:10
     * @see
     */
    @JSON
    public String updateAcceptPointSalesDeptStatus() {
	try {
		//判断传入参数是否为null
		if(null == acceptPointSalesDeptDto || CollectionUtils.isEmpty(acceptPointSalesDeptDto.getIdList()) ||
			StringUtils.isEmpty(acceptPointSalesDeptDto.getStatus())){
	    	LOGGER.info("修改接驳点与营业部映射状态信息传入的参数不能为空!");
	    	return null;
	    }
	    List<String> idList = acceptPointSalesDeptDto.getIdList();
	    String status = acceptPointSalesDeptDto.getStatus();
	    Date modifyDate = new Date();
	    String modifyUser = FossUserContext.getCurrentInfo().getEmpCode();
		String modifyUserName = FossUserContext.getCurrentInfo().getEmpName();
	    // 保存成功返回一个对象
	    acceptPointSalesDeptService.updateAcceptPointSalesStatusById(idList, status, modifyDate, modifyUser, modifyUserName);
	    return returnSuccess(MessageType.UPDATE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * 分页查询接驳点与营业部映射关系信息
     * 
     * @author 132599-FOSS-ShenWeiHua
     * @date 2015-4-21 下午5:44:10
     * @see
     */
    @JSON
    public String queryAcceptPointSalesDepts() {
	try {
	    List<AcceptPointSalesDeptEntity> entitys = acceptPointSalesDeptService.queryAllAcceptPointSalesDept(acceptPointSalesDeptDto.getAcceptPointEntity(), limit, start);
	    // 查询总记录数
	    Long totalCount = acceptPointSalesDeptService.queryRecordCount(acceptPointSalesDeptDto.getAcceptPointEntity());
	    // 设置totalCount
	    acceptPointSalesDeptDto.setAcceptPointEntitys(entitys);
	    this.setTotalCount(totalCount);
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.info(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * 根据上级编码查询下面的营业部
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-25 上午10:50:21
     * @return 符合条件的实体列表
     * @see
     */
    @JSON
    public String querySalesDeptBySmallRegion() {
	try {
		//判断传入参数是否为null
		if(null == acceptPointSalesDeptDto || StringUtils.isEmpty(acceptPointSalesDeptDto.getSmallRegion()) ||
			StringUtils.isEmpty(acceptPointSalesDeptDto.getSmallRegionName()) || StringUtils.isEmpty(acceptPointSalesDeptDto.getAcceptPointCode())){
			LOGGER.info("根据上级编码查询下面的营业部时传入的参数不能为空!");
			return null;
		}
		String smallRegion = acceptPointSalesDeptDto.getSmallRegion();
		String smallRegionName = acceptPointSalesDeptDto.getSmallRegionName();
		String acceptPointCode = acceptPointSalesDeptDto.getAcceptPointCode();
		//查询营业区下面的营业部
		List<AcceptPointSalesChildrenDeptEntity> childrenEntitys = acceptPointSalesDeptService.queryAllOrgAdministrativeInfoByParentOrgCode(acceptPointCode,smallRegion, smallRegionName);
	    if(CollectionUtils.isEmpty(childrenEntitys)){
	    	LOGGER.info("该营业区对应的营业部为空!");
	    	return null;
	    }else{
	    	acceptPointSalesDeptDto.setChildAcceptPointEntitys(childrenEntitys);
	    }
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.info(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * 根据大区编码查询接驳点和中转场信息
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-25 上午10:50:21
     * @param entity
     * @return 符合条件的实体列表
     * @see
     */
    @JSON
    public String queryAcceptPointTransferByBigRegion() {
	try {
		//判断传入参数是否为null
		if(null == acceptPointSalesDeptDto || StringUtils.isEmpty(acceptPointSalesDeptDto.getBigRegion())){
			LOGGER.info("根据大区编码查询接驳点和中转场信息时传入的参数不能为空!");
			return null;
		}
		String bigRegion = acceptPointSalesDeptDto.getBigRegion();
	    //根据大区code查询对应的接驳点和中转场信息
		List<AcceptPointSalesDeptEntity> acceptPointSalesDeptEntitys = acceptPointSalesDeptService.queryAcceptPointTransferInfoByBigRegionCode(bigRegion);
	    acceptPointSalesDeptDto.setAcceptPointEntitys(acceptPointSalesDeptEntitys);
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.info(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    
    /**
     * 根据大区编码查询下面的营业区信息
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-25 上午10:50:21
     * @param entity
     * @return 符合条件的实体列表
     * @see
     */
    @JSON
    public String querySmallRegionInfoByBigRegion() {
	try {
		//判断传入参数是否为null
		if(null == acceptPointSalesDeptDto || StringUtils.isEmpty(acceptPointSalesDeptDto.getBigRegion())){
			LOGGER.info("根据大区编码查询下面的营业区信息时传入的参数不能为空!");
			return null;
		}
		String bigRegion = acceptPointSalesDeptDto.getBigRegion();
		//根据大区code查询下面的营业区信息
		List<AcceptPointSalesChildrenDeptEntity> childrenEntitys = acceptPointSalesDeptService.querySmallRegionInfoByBigRegionCode(bigRegion);
	    acceptPointSalesDeptDto.setChildAcceptPointEntitys(childrenEntitys);
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.info(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * 根据接驳点查询接驳点与营业部映射子信息
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2013-4-25 上午10:50:21
     * @param entity
     * @return 符合条件的实体列表
     * @see
     */
    @JSON
    public String queryChildrenDeptByAcceptSmallCode() {
	try {
		//判断传入参数是否为null
		if(null == acceptPointSalesDeptDto 
				|| StringUtils.isEmpty(acceptPointSalesDeptDto.getAcceptPointCode())){
			LOGGER.info("根据接驳点查询接驳点与营业部映射子信息时传入的参数不能为空!");
			return null;
		}
		String acceptPointCode = acceptPointSalesDeptDto.getAcceptPointCode();
		//根据接驳点、营业区编码查询接驳点与营业部映射子信息
		List<AcceptPointSalesChildrenDeptEntity> childrenEntitys = acceptPointSalesDeptService.queryChildrenDeptByAcceptSmallCode(acceptPointCode);
	    acceptPointSalesDeptDto.setChildAcceptPointEntitys(childrenEntitys);
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.info(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    
    
    
    /**
     * 获取接驳点与营业部映射关系的DTO
     * @return
     */
	public AcceptPointSalesDeptDto getAcceptPointSalesDeptDto() {
		return acceptPointSalesDeptDto;
	}
	
	/**
	 * 设置接驳点与营业部映射关系的DTO
	 * @param acceptPointSalesDeptDto
	 */
	public void setAcceptPointSalesDeptDto(
			AcceptPointSalesDeptDto acceptPointSalesDeptDto) {
		this.acceptPointSalesDeptDto = acceptPointSalesDeptDto;
	}
	
	/**
	 * 设置接驳点与营业部映射关系service
	 * @param acceptPointSalesDeptService
	 */
	public void setAcceptPointSalesDeptService(
			IAcceptPointSalesDeptService acceptPointSalesDeptService) {
		this.acceptPointSalesDeptService = acceptPointSalesDeptService;
	}
	
    
}
