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

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.RegionVo;

/**
 * 区域管理ACTION
 * 
 * @author 张斌
 * @date 2012-10-9
 * @version 1.0
 */
public class RegionAction extends AbstractAction {
    	/**
    	 * 序列化
    	 */
	private static final long serialVersionUID = 2883644272419312426L;

	/**
	 *  注入service
	 */
	private IRegionService regionService;

	/**
	 * 
	 * <p>设置注入service</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-3-17 上午11:12:49
	 * @param regionService
	 * @see
	 */
	public void setRegionService(IRegionService regionService) {
		this.regionService = regionService;
	}
	
	/**
	 * 区域Vo页面传参对象
	 */
	private RegionVo regionVo = new RegionVo();
	/**
	 * 
	 * <p>获得区域Vo页面传参对象</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-3-17 上午11:13:34
	 * @return
	 * @see
	 */
	public RegionVo getRegionVo() {
		return regionVo;
	}
	/**
	 * 
	 * <p>设置区域Vo页面传参对象</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2013-3-17 上午11:13:44
	 * @param regionVo
	 * @see
	 */
	public void setRegionVo(RegionVo regionVo) {
		this.regionVo = regionVo;
	}

	/**
	 * .
	 * <p>
	 * 查询所有的区域<br/>
	 * 方法名：searchAllRegion
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-10-09
	 * @since JDK1.6
	 */
	@JSON
	public String searchRegionByCondition() {
		try {
			PriceRegionEntity regionEntity = regionVo.getRegionEntity();
			List<PriceRegionEntity> regionEntityList = regionService.searchRegionByCondition(regionEntity, this.getStart(),this.getLimit());
			regionVo.setRegionEntityList(regionEntityList);
			Long totalCount = regionService.countRegionByCondition(regionEntity);
			this.setTotalCount(totalCount);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * .
	 * <p>
	 * 查询当前区域关联的部门<br/>
	 * 方法名：searchRegionDept
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-10-10
	 * @since JDK1.6
	 */
	@JSON
	public String searchRegionDept() {
		try {
			List<PriceRegioOrgnEntity> priceRegioOrgnEntityList = regionService
					.searchRegionOrgByCondition(regionVo
							.getPriceRegioOrgnEntity());
			regionVo.setPriceRegioOrgnEntityList(priceRegioOrgnEntityList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * .
	 * <p>
	 * 激活区域<br/>
	 * 方法名：activeRegions
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-10-10
	 * @since JDK1.6
	 */
	@JSON
	public String activeRegions() {
		try {			   	
		    Date dd = new Date();
		    if(dd!= null && regionVo.getBeginTime()!= null && dd.compareTo(regionVo.getBeginTime())>0)
		    {
		    	return returnError(MessageType.SHOW_FAILURE_MESSAGE);
		    }
			regionService.immedietelyActiveRegion(regionVo.getRegionEntity().getId(), regionVo.getRegionNature(),regionVo.getBeginTime());
			return returnSuccess(MessageType.ACTIVE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 删除区域<br/>
	 * 方法名：deleteRegions
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2013-1-25
	 * @since JDK1.6
	 */
	@JSON
	public String deleteRegions() {
		try {
			regionService.deleteRegion(regionVo.getRegionIds(),regionVo.getRegionNature());
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * .
	 * <p>
	 * 新增区域<br/>
	 * 方法名：addRegion
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-10-11
	 * @since JDK1.6
	 */
	@JSON
	public String addRegion() {
		try {
			regionService.addRegion(regionVo.getRegionEntity(),regionVo.getAddPriceRegioOrgnEntityList());
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * .
	 * <p>
	 * 修改区域<br/>
	 * 方法名：updateRegion
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-10-11
	 * @since JDK1.6
	 */
	@JSON
	public String updateRegion() {
		try {
			regionService.updateRegion(regionVo.getRegionEntity(),regionVo.getAddPriceRegioOrgnEntityList(),regionVo.getUpdatePriceRegioOrgnEntityList());
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 根据ID查询区域<br/>
	 * 方法名：searchRegionByID
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-12-07
	 * @since JDK1.6
	 */
	@JSON
	public String searchRegionByID() {
		try {
			PriceRegionEntity priceRegionEntity =regionService.searchRegionByID(regionVo.getRegionEntity().getId(), regionVo.getRegionEntity().getRegionNature());
			regionVo.setRegionEntity(priceRegionEntity);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 
	 * @Description: 立即中止
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-3-20 下午5:51:40
	 * 
	 * @return
	 * 
	 * @version V1.0
	 * 
	 */
	@JSON
	public String immedietelyStopRegion() {
		try {
			//执行中止操作
			regionService.immedietelyStopRegion(regionVo.getRegionEntity().getId(), regionVo.getRegionNature(),regionVo.getEndTime());
			//返回状态值
			return returnSuccess(MessageType.STOP_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
}