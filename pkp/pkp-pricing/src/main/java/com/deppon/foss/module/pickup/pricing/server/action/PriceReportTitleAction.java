/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.pickup.pricing.server.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceReportTitleService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceReportTitleEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.PriceReportTitleVo;


/**
 * 汽运价格报表表头信息Action
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2014-1-13 上午10:02:30 </p>
 * @author 094463-foss-xieyantao
 * @date 2014-1-13 上午10:02:30
 * @since
 * @version
 */
public class PriceReportTitleAction extends AbstractAction {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 3437498647981770175L;
    
    /**
     * 获取日志类.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PriceReportTitleAction.class);
    
    /**
     * 汽运价格报表表头信息Service接口.
     */
    private IPriceReportTitleService priceReportTitleService;
    
    /**
     * 汽运价格报表表头信息VO.
     */
    private PriceReportTitleVo vo = new PriceReportTitleVo();
    /**
     * 获取 汽运价格报表表头信息VO.
     *
     * @return  the vo
     */
    public PriceReportTitleVo getVo() {
        return vo;
    }
    
    /**
     * 设置 汽运价格报表表头信息VO.
     *
     * @param vo the vo to set
     */
    public void setVo(PriceReportTitleVo vo) {
        this.vo = vo;
    }
    /**
     * 设置 汽运价格报表表头信息Service接口.
     *
     * @param priceReportTitleService the priceReportTitleService to set
     */
    public void setPriceReportTitleService(
    	IPriceReportTitleService priceReportTitleService) {
        this.priceReportTitleService = priceReportTitleService;
    }
    /**
     * <p>分页查询所有汽运价格报表表头信息</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-26 下午3:06:46
     * @see
     */
    @JSON
    public String queryAllReportTitles() {
	try {
	    //根据传入对象查询符合条件所有快递代理公司运价方案信息
	    List<PriceReportTitleEntity> entityList = priceReportTitleService.queryInfos(vo.getPriceReportTitle(), limit, start);
	    vo.setPriceReportTitleList(entityList);
	    // 查询总记录数
	    Long totalCount = priceReportTitleService.queryRecordCount(vo.getPriceReportTitle());
	    // 设置totalCount
	    this.setTotalCount(totalCount);
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.error(e.getMessage(),e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>修改汽运价格报表表头信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2014-1-13 下午3:38:39
     * @return
     * @see
     */
    @JSON
    public String updateReportTitle() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    
	    // 获取 汽运价格报表表头信实体类
	    PriceReportTitleEntity entity = vo.getPriceReportTitle();
	    
	    entity.setModifyUser(userCode);
	    
	    // 保存成功返回一个对象
	    priceReportTitleService.updateInfo(entity);

	    return returnSuccess(MessageType.UPDATE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.error(e.getMessage(),e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>根据ＩＤ查询信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2014-2-12 上午9:46:46
     * @return
     * @see
     */
    @JSON
    public String queryReportTitleById(){
	try {
	    PriceReportTitleEntity entity = priceReportTitleService.queryInfoById(vo.getPriceReportTitle().getId());
	    vo.setPriceReportTitle(entity);
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.error(e.getMessage(),e);
	    return returnError(e);
	}
    }
    
    
}
