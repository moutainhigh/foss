/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/action/RegionAction.java
 * 
 * FILE NAME        	: RegionAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.action;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPriceRegionService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.ExpressPriceRegionVo;

/**
 * 
 * 快递价格区域Action
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2013-7-25 下午5:35:32
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-7-25 下午5:35:32
 * @since
 * @version
 */
public class ExpressPriceRegionAction extends AbstractAction {


    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 6121253971261904524L;
    
    /**
     * 快递价格区域Service接口.
     */
    private IExpressPriceRegionService expressPriceRegionService;
    
    /**
     * 设置 快递价格区域Service接口.
     *
     * @param expressPriceRegionService the expressPriceRegionService to set
     */
    public void setExpressPriceRegionService(
    	IExpressPriceRegionService expressPriceRegionService) {
        this.expressPriceRegionService = expressPriceRegionService;
    }

    /**
     * 快递价格区域Vo页面传参对象.
     */
    private ExpressPriceRegionVo regionVo = new ExpressPriceRegionVo();
    
    /**
     * 获取 快递价格区域Vo页面传参对象.
     *
     * @return  the regionVo
     */
    public ExpressPriceRegionVo getRegionVo() {
        return regionVo;
    }
    
    /**
     * 设置 快递价格区域Vo页面传参对象.
     *
     * @param regionVo the regionVo to set
     */
    public void setRegionVo(ExpressPriceRegionVo regionVo) {
        this.regionVo = regionVo;
    }

    /**
     * <p>分页查询所有的快递价格区域</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午5:39:26
     * @see
     */
    @JSON
    public String searchRegionByCondition() {
	try {
	    PriceRegionExpressEntity regionEntity = regionVo.getRegionEntity();
	    List<PriceRegionExpressEntity> regionEntityList = expressPriceRegionService
		    .searchRegionByCondition(regionEntity, this.getStart(),
			    this.getLimit());
	    regionVo.setRegionEntityList(regionEntityList);
	    Long totalCount = expressPriceRegionService
		    .countRegionByCondition(regionEntity);
	    this.setTotalCount(totalCount);
	    return returnSuccess();
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * <p>查询当前快递价格区域关联的部门</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午5:39:07
     * @see
     */
    @JSON
    public String searchRegionDept() {
	try {
	    List<PriceRegioOrgnExpressEntity> priceRegioOrgnEntityList = expressPriceRegionService
		    .searchRegionOrgByCondition(regionVo
			    .getPriceRegioOrgnEntity());
	    regionVo.setPriceRegioOrgnEntityList(priceRegioOrgnEntityList);
	    return returnSuccess();
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * <p>激活快递价格区域</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午5:38:50
     * @see
     */
    @JSON
    public String activeRegions() {
	try {
	    expressPriceRegionService.immedietelyActiveRegion(regionVo.getRegionEntity()
		    .getId(), regionVo.getRegionNature(), regionVo
		    .getBeginTime());
	    return returnSuccess(MessageType.ACTIVE_SUCCESS);
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * <p>删除快递价格区域</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午5:38:33
     * @see
     */
    @JSON
    public String deleteRegions() {
	try {
	    expressPriceRegionService.deleteRegion(regionVo.getRegionIds(),
		    regionVo.getRegionNature());
	    return returnSuccess(MessageType.DELETE_SUCCESS);
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * <p>新增快递价格区域</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午5:38:07
     * @see
     */
    @JSON
    public String addRegion() {
	try {
	    expressPriceRegionService.addRegion(regionVo.getRegionEntity(),
		    regionVo.getAddPriceRegioOrgnEntityList());
	    return returnSuccess(MessageType.SAVE_SUCCESS);
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * <p>修改快递价格区域</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午5:37:43
     * @see
     */
    @JSON
    public String updateRegion() {
	try {
	    expressPriceRegionService.updateRegion(regionVo.getRegionEntity(),
		    regionVo.getAddPriceRegioOrgnEntityList(),
		    regionVo.getUpdatePriceRegioOrgnEntityList());
	    return returnSuccess(MessageType.UPDATE_SUCCESS);
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * <p>根据ID查询快递价格区域</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午5:37:23
     * @see
     */
    @JSON
    public String searchRegionByID() {
	try {
	    PriceRegionExpressEntity priceRegionEntity = expressPriceRegionService
		    .searchRegionByID(regionVo.getRegionEntity().getId(),
			    regionVo.getRegionEntity().getRegionNature());
	    regionVo.setRegionEntity(priceRegionEntity);
	    return returnSuccess();
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * <p>立即中止</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-7-25 下午5:37:06
     * @see
     */
    @JSON
    public String immedietelyStopRegion() {
	try {
	    // 执行中止操作
	    expressPriceRegionService
		    .immedietelyStopRegion(regionVo.getRegionEntity().getId(),
			    regionVo.getRegionNature(), regionVo.getEndTime());
	    // 返回状态值
	    return returnSuccess(MessageType.STOP_SUCCESS);
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }
}