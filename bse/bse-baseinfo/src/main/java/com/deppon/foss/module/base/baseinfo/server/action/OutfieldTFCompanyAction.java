package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldTFCompanyService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldTFCompanyEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.OutfieldTFCompanyVo;

/**
 * 外场与所属运输财务公司关系Action
 * @author 132599-foss-shenweihua
 * @date 2013-11-27 上午10:35:52
 * @since
 * @version
 */
public class OutfieldTFCompanyAction extends AbstractAction{
	
	/**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OutfieldTFCompanyAction.class);

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 外场与所属运输财务公司关系Service接口
	 */
	private IOutfieldTFCompanyService outfieldTFCompanyService;
	
	/**
	 * 外场与所属运输财务公司关系VO
	 */
	private OutfieldTFCompanyVo outfieldTFCompanyVo = new OutfieldTFCompanyVo();
	
	 /**
     * 添加外场与所属运输财务公司关系信息
     * 
     * @author 132599-foss-shenweihua
     * @date 2013-11-27 上午11:20:10
     * @return
     * @see
     */
    @JSON
    public String addOutfieldTFCompany() {
	try {
		//判断macWhiteEntity是否为null
	    if(null == outfieldTFCompanyVo.getEntity()){
	    	LOGGER.info("新增外场与所属运输财务公司关系信息传入的参数不能为空!");
	    	return null;
	    }
	    OutfieldTFCompanyEntity entity = outfieldTFCompanyVo.getEntity();
	    UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    // 保存成功返回一个对象
	    outfieldTFCompanyService.addOutfieldTFCompany(entity, userCode);

	    return returnSuccess(MessageType.SAVE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * 作废外场与所属运输财务公司关系信息
     * 
     * @author 132599-foss-shenweihua
     * @date 2013-11-27 上午11:25:10
     * @return
     * @see
     */
    @JSON
    public String deleteOutfieldTFCompany() {
	try {
		if(null == outfieldTFCompanyVo.getIdList()){
	    	LOGGER.info("删除外场与所属运输财务公司关系信息传入的参数不能为空!");
	    	return null;
	    }
		UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
		outfieldTFCompanyService.deleteOutfieldTFCompanyById(outfieldTFCompanyVo.getIdList(), userCode);
	    return returnSuccess(MessageType.DELETE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * 修改外场与所属运输财务公司关系信息
     *
     * @return 
     * @author 132599-foss-shenweihua
     * @date 2013-11-27 上午11:26:10
     * @see
     */
    @JSON
    public String updateOutfieldTFCompany() {
	try {
	    // 获取Form信息
		OutfieldTFCompanyEntity entity = outfieldTFCompanyVo.getEntity();
	    if(null == entity){
	    	LOGGER.info("更新外场与所属运输财务公司关系信息传入的参数不能为空!");
	    	return null;
	    }
	    UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    // 保存成功返回一个对象
	    outfieldTFCompanyService.updateOutfieldTFCompany(entity, userCode);
	    return returnSuccess(MessageType.UPDATE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * 分页查询外场与所属运输财务公司关系信息
     *
     * @return 
     * @author 132599-foss-shenweihua
     * @date 2013-11-27 上午11:28:10
     * @see
     */
    @JSON
    public String queryOutfieldTFCompanys() {
	try {
		
	    List<OutfieldTFCompanyEntity> entityList = outfieldTFCompanyService.queryAllOutfieldTFCompany(outfieldTFCompanyVo.getEntity(), limit, start);
	    outfieldTFCompanyVo.setEntityList(entityList);
	    // 查询总记录数
	    Long totalCount = outfieldTFCompanyService.queryRecordCount(outfieldTFCompanyVo.getEntity());
	    // 设置totalCount
	    this.setTotalCount(totalCount);

	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.info(e.getMessage(), e);
	    return returnError(e);
	}
    }
	
	
	/**
	 * 获取外场与所属运输财务公司关系VO
	 * @return
	 */
	public OutfieldTFCompanyVo getOutfieldTFCompanyVo() {
		return outfieldTFCompanyVo;
	}
	
	/**
	 * 设置外场与所属运输财务公司关系VO
	 * @param outfieldTFCompanyVo
	 */
	public void setOutfieldTFCompanyVo(OutfieldTFCompanyVo outfieldTFCompanyVo) {
		this.outfieldTFCompanyVo = outfieldTFCompanyVo;
	}
	
	/**
	 * 设置外场与所属运输财务公司关系Service接口
	 * @param outfieldTFCompanyService
	 */
	public void setOutfieldTFCompanyService(
			IOutfieldTFCompanyService outfieldTFCompanyService) {
		this.outfieldTFCompanyService = outfieldTFCompanyService;
	}
	

}
