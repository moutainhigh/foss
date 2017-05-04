package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ITemporaryRentalCarMarkTimeManagementService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TemporaryRentalCarMarkTimeManagementEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.TemporaryRentalCarMarkTimeManagementVo;
/**
 * 
 * @author 218392  张永雪
 * @date 创建时间：2014-12-18 下午4:36:41
 *
 */
public class TemporaryRentalCarMarkTimeManagementAction extends AbstractAction{

	/**
	 * serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *  日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TemporaryRentalCarMarkTimeManagementAction.class);
	
	/**
	 * 临时租车标记时间管理 Service 接口
	 */
	private ITemporaryRentalCarMarkTimeManagementService temporaryRentalCarMarkTimeManagementService;

	/**
	 * 临时租车标记时间管理信息Vo
	 * 
	 */
	private TemporaryRentalCarMarkTimeManagementVo  temporaryRentalCarMarkTimeManagementVo = new TemporaryRentalCarMarkTimeManagementVo();
	
	/**
	 * 添加临时租车标记时间管理信息 
	 */
	@JSON
	public String addTemporaryRentalCarMarkTimeManagement(){
		try {
			//判断 TemporaryRentalCarMarkTimeManagementEntity 是否为空
			if(null == temporaryRentalCarMarkTimeManagementVo.getEntity()){
				LOGGER.info("增加临时租车标记时间管理传入的参数不能为空!");
				return null;
			}
			TemporaryRentalCarMarkTimeManagementEntity entity = temporaryRentalCarMarkTimeManagementVo.getEntity();
			if(StringUtils.isEmpty(entity.getDeptAttributes()) || StringUtils.isEmpty(entity.getSetTime())){
				LOGGER.info("部门属性或设置时长为空");
				return null;
			}
			temporaryRentalCarMarkTimeManagementService.addTemporaryRentalCarMarkTimeManagement(entity);
			//保存成功返回一个对象
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	
	/**
	 * 作废添加临时租车标记时间管理信息
	 */
	@JSON
	public String deleteTemporaryRentalCarMarkTimeManagement(){
		try {
			if(null == temporaryRentalCarMarkTimeManagementVo.getIdList()){
		    	LOGGER.info("删除临时租车标记时间管理信息传入的参数不能为空!");
		    	return null;
		    }
			temporaryRentalCarMarkTimeManagementService.deleteByIdTemporaryRentalCarMarkTimeManagement(temporaryRentalCarMarkTimeManagementVo.getIdList());
		    return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
		    LOGGER.debug(e.getMessage(), e);
		    return returnError(e);
		}
		
	}
	
	
	 /**
     * 修改临时租车标记时间管理信息
     *
     */
    @JSON
    public String updateTemporaryRentalCarMarkTimeManagement() {
	try {
	    // 获取Form信息
		TemporaryRentalCarMarkTimeManagementEntity entity = temporaryRentalCarMarkTimeManagementVo.getEntity();
	    if(null == entity){
	    	LOGGER.info("更新临时租车标记时间管理信息传入的参数不能为空!");
	    	return null;
	    }
	    // 保存成功返回一个对象
	    temporaryRentalCarMarkTimeManagementService.updateTemporaryRentalCarMarkTimeManagement(entity);
	    return returnSuccess(MessageType.UPDATE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	  }
    }
    
    
    /**
     * 分页查询临时租车标记时间管理信息
     */
    @JSON
    public String queryTemporaryRentalCarMarkTimeManagement() {
	try {
	    List<TemporaryRentalCarMarkTimeManagementEntity> entityList =
	    		temporaryRentalCarMarkTimeManagementService.queryAllTemporaryRentalCarMarkTimeManagement(temporaryRentalCarMarkTimeManagementVo.getEntity(), limit, start);
	    temporaryRentalCarMarkTimeManagementVo.setEntityList(entityList);
	    // 查询总记录数
	    Long totalCount = temporaryRentalCarMarkTimeManagementService.queryCount(temporaryRentalCarMarkTimeManagementVo.getEntity());
	    // 设置totalCount
	    this.setTotalCount(totalCount);
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.info(e.getMessage(), e);
	    return returnError(e);
	  }
    }
	

	/**
	 * 
	 *设置临时租车标记时间管理 接口Service
	 */
	public void setTemporaryRentalCarMarkTimeManagementService(
			ITemporaryRentalCarMarkTimeManagementService temporaryRentalCarMarkTimeManagementService) {
		this.temporaryRentalCarMarkTimeManagementService = temporaryRentalCarMarkTimeManagementService;
	}


	/**
	 * 获取临时租车标记时间管理信息Vo
	 */
	public TemporaryRentalCarMarkTimeManagementVo getTemporaryRentalCarMarkTimeManagementVo() {
		return temporaryRentalCarMarkTimeManagementVo;
	}


	/**
	 * 设置临时租车标记时间管理信息Vo
	 */
	public void setTemporaryRentalCarMarkTimeManagementVo(
			TemporaryRentalCarMarkTimeManagementVo temporaryRentalCarMarkTimeManagementVo) {
		this.temporaryRentalCarMarkTimeManagementVo = temporaryRentalCarMarkTimeManagementVo;
	}

	
}
