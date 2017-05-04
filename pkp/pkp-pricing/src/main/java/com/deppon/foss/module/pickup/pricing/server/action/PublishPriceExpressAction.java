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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/action/PublishPriceAction.java
 * 
 * FILE NAME        	: PublishPriceAction.java
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
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SaleDepartmentInfoDto;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceExpressService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.NewPublishPriceExpressVo;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.PublishPriceExpressVo;

/**
 * 公布价管理ACTION
 * 
 * @author 张斌
 * @date 2012-10-23
 * @version 1.0
 */
public class PublishPriceExpressAction extends AbstractAction {
	/**
     * 序列化
     */
	private static final long serialVersionUID = 5944940889630134223L;
        /**
         * 公布价VO
         */
        private PublishPriceExpressVo publishPriceExpressVo = new PublishPriceExpressVo();

        /**
         * 公布价服务
         */
        public IPublishPriceExpressService publishPriceExpressService;
        
        
        
        /**
         * 公布价VO(新)
         */
        private NewPublishPriceExpressVo newPublishPriceExpressVo = new NewPublishPriceExpressVo();
        
        /**
         * 
         * <p>设置公布价VO</p> 
         * @author DP-Foss-YueHongJie
         * @date 2013-3-17 上午11:11:08
         * @param publishPriceExpressVo
         * @see
         */
		public void setPublishPriceExpressVo(PublishPriceExpressVo publishPriceExpressVo) {
			this.publishPriceExpressVo = publishPriceExpressVo;
		}
		
		/**
		 *  <p>设置公布价VO</p> 
		 * @return
		 */
        public PublishPriceExpressVo getPublishPriceExpressVo() {
			return publishPriceExpressVo;
		}


	/**
	 * .
	 * <p>
	 * 根据条件查询公布价<br/>
	 * 方法名：searchPublishPriceByCondition
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-10-23
	 * @since JDK1.6
	 */
	@JSON
	public String searchPublishPriceExpressByCondition() {
		try {
			List<PublishPriceExpressEntity> publishPriceEntityList = publishPriceExpressService.queryPublishPriceDetail(publishPriceExpressVo.getPublishPriceExpressEntity().getStartDeptCode(), publishPriceExpressVo.getPublishPriceExpressEntity().getArrvRegionCode(), null);
			publishPriceExpressVo.setPublishPriceExpressEntityList(publishPriceEntityList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * .
	 * <p>
	 * 根据条件查询公布价(新)<br/>
	 * 方法名：searchNewPublishPriceExpressByCondition
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2013-01-24
	 * @since JDK1.6
	 */
	@JSON
	public String searchNewPublishPriceExpressByCondition() {
		try {
			List<PublishPriceExpressEntity> publishPriceExpressList = publishPriceExpressService
					.queryPublishPriceExpressDetailByCondition(
							newPublishPriceExpressVo.getStartCityCode(), //施法城市
							newPublishPriceExpressVo.getDestinationCountryCode(),//目的国家
							newPublishPriceExpressVo.getDestinationProvinceCode(),//目的省份
							newPublishPriceExpressVo.getDestinationCityCode(),//目的城市
							newPublishPriceExpressVo.getDestinationCountyCode(),//目的区县
							newPublishPriceExpressVo.getDestinationAddress(),//目的地址
							null);
			newPublishPriceExpressVo.setPublishPriceExpressList(publishPriceExpressList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 根据条件查询公布价(新)<br/>
	 * 方法名：getOuterAndDepartment
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2013-01-24
	 * @since JDK1.6
	 */
	@JSON
	public String queryOuterAndDepartmentExpress() {
		try {
			List<SaleDepartmentInfoDto> saleDepartmentInfoDtoList = publishPriceExpressService.getOuterAndDepartment(newPublishPriceExpressVo.getDeptCodes(), newPublishPriceExpressVo.getDeptRegionId(), newPublishPriceExpressVo.getRegionNature());
			newPublishPriceExpressVo.setSaleDepartmentInfoDtoList(saleDepartmentInfoDtoList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	public NewPublishPriceExpressVo getNewPublishPriceExpressVo() {
		return newPublishPriceExpressVo;
	}

	public void setNewPublishPriceExpressVo(
			NewPublishPriceExpressVo newPublishPriceExpressVo) {
		this.newPublishPriceExpressVo = newPublishPriceExpressVo;
	}

	public void setPublishPriceExpressService(
			IPublishPriceExpressService publishPriceExpressService) {
		this.publishPriceExpressService = publishPriceExpressService;
	}
	
}


















