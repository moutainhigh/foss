package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAsteriskSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AsteriskSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AsteriskSalesDeptVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * 星标营业部信息维护Action类
 * 
 * @author 132599-foss-shenweihua
 * @date 2013-5-4 下午3:48:12
 * @since
 * @version
 */

public class AsteriskSalesDeptAction extends AbstractAction{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -7274461546563153755L;
	
	/**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(AsteriskSalesDeptAction.class);
    
    /**
     * 星标营业部信息Service接口
     */
    private IAsteriskSalesDeptService asteriskSalesDeptService;
    
    /**
     * 星标营业部信息VO
     */
    private AsteriskSalesDeptVo asteriskSalesDeptVo = new AsteriskSalesDeptVo();
    
    /**
     * <p>新增加星标营业部信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-5-4 下午4:05:12
     * @param entity
     * @return
     * @see
     */
    @JSON
    public String addAsteriskSalesDept() {
	try {
		//判断asteriskSalesDeptEntity是否为null
	    if(null == asteriskSalesDeptVo.getEntity()){
	    	LOGGER.info("增加星标营业部信息传入的参数不能为空!");
	    	return null;
	    }
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    // 获取Form信息
		AsteriskSalesDeptEntity entity = asteriskSalesDeptVo.getEntity();
		entity.setCreateUser(userCode);
	    entity.setModifyUser(userCode);
	    if(StringUtils.isEmpty(entity.getSalesDeptCode())||StringUtils.isEmpty(entity.getSalesDeptName())){
	    	LOGGER.info("星标营业部名称为空或星标营业部编码为空!");
	    	return null;
	    }
	  
	    // 保存成功返回一个对象
	    asteriskSalesDeptService.addAsteriskSalesDept(entity);

	    return returnSuccess(MessageType.SAVE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>作废加星标营业部信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-5-4 下午4:18:12
     * @param asteriskDeptVirtualCodes 虚拟编码集合
     * @param modifyUser 修改人编码
     * @return
     * @see
     */
    @JSON
    public String deleteAsteriskSalesDept() {
	try {
		if(null == asteriskSalesDeptVo.getAsteriskDeptVirtualCodes()){
	    	LOGGER.info("删除星标营业部信息传入的参数不能为空!");
	    	return null;
	    }
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    
	    asteriskSalesDeptService.deleteAsteriskSalesDeptByVirtualCode(asteriskSalesDeptVo.getAsteriskDeptVirtualCodes(), userCode);

	    return returnSuccess(MessageType.DELETE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * 根据传入对象分页查询符合条件所有加星标营业部信息
     * 
     * @author 132599-foss-shenweihua
     * @date 2013-5-4 下午2:28:12
     * @param entity
     *            加星标营业部信息实体
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    @JSON
    public String queryAsteriskSalesDepts() {
	try {

	    List<AsteriskSalesDeptEntity> entityList = asteriskSalesDeptService.queryAllAsteriskSalesDept(asteriskSalesDeptVo.getEntity(), this.getLimit(), this.getStart());
	    asteriskSalesDeptVo.setEntityList(entityList);

	    // 查询总记录数
	    Long totalCount = asteriskSalesDeptService.queryRecordCount(asteriskSalesDeptVo.getEntity());
	    // 设置totalCount
	    this.setTotalCount(totalCount);

	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    /**
     * 获取星标营业部信息VO
     */
	public AsteriskSalesDeptVo getAsteriskSalesDeptVo() {
		return asteriskSalesDeptVo;
	}
	
	/**
     * 设置星标营业部信息VO
     */
	public void setAsteriskSalesDeptVo(AsteriskSalesDeptVo asteriskSalesDeptVo) {
		this.asteriskSalesDeptVo = asteriskSalesDeptVo;
	}
	
	/**
     * 设置星标营业部信息Service接口
     */
	public void setAsteriskSalesDeptService(
			IAsteriskSalesDeptService asteriskSalesDeptService) {
		this.asteriskSalesDeptService = asteriskSalesDeptService;
	}

}
