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
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PublicPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.NewPublishPriceVo;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.PublishPriceVo;
import com.deppon.foss.module.pickup.pricing.server.service.impl.PublishPriceService;

/**
 * 公布价管理ACTION
 * 
 * @author 张斌
 * @date 2012-10-23
 * @version 1.0
 */
public class PublishPriceAction extends AbstractAction {
        /**
         * 序列化
         */
        private static final long serialVersionUID = 2883644272419312426L;
        /**
         * 公布价VO
         */
        private PublishPriceVo publishPriceVo = new PublishPriceVo();
        
        /**
         * 
         * <p>获得公布价VO</p> 
         * @author DP-Foss-YueHongJie
         * @date 2013-3-17 上午11:11:12
         * @return
         * @see
         */
        public PublishPriceVo getPublishPriceVo() {
    		return publishPriceVo;
        }
        
        /**
         * 
         * <p>设置公布价VO</p> 
         * @author DP-Foss-YueHongJie
         * @date 2013-3-17 上午11:11:08
         * @param publishPriceVo
         * @see
         */
        public void setPublishPriceVo(PublishPriceVo publishPriceVo) {
    		this.publishPriceVo = publishPriceVo;
        }

        /**
         * 公布价VO(新)
         */
        private NewPublishPriceVo newPublishPriceVo = new NewPublishPriceVo();
        /**
         * 
         * <p>公布价VO(新)</p> 
         * @author DP-Foss-YueHongJie
         * @date 2013-3-17 上午11:09:32
         * @return
         * @see
         */
        public NewPublishPriceVo getNewPublishPriceVo() {
    		return newPublishPriceVo;
        }
        /**
         * 
         * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
         * @author DP-Foss-YueHongJie
         * @date 2013-3-17 上午11:09:43
         * @param newPublishPriceVo
         * @see
         */
        public void setNewPublishPriceVo(NewPublishPriceVo newPublishPriceVo) {
    		this.newPublishPriceVo = newPublishPriceVo;
        }
        /**
         * 公布价服务
         */
        public PublishPriceService publishPriceService;
        /**
         * 
         * <p>注入公布价服务</p> 
         * @author DP-Foss-YueHongJie
         * @date 2013-3-17 上午11:10:39
         * @param publishPriceService
         * @see
         */
        public void setPublishPriceService(PublishPriceService publishPriceService) {
    		this.publishPriceService = publishPriceService;
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
	public String searchPublishPriceByCondition() {
		try {
			List<PublishPriceEntity> publishPriceEntityList = publishPriceService.queryPublishPriceDetailForSalesAndPx(publishPriceVo.getPublishPriceEntity().getStartDeptCode(), publishPriceVo.getPublishPriceEntity().getArrvRegionCode());
			publishPriceVo.setPublishPriceEntityList(publishPriceEntityList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * .
	 * <p>
	 * 根据条件查询公布价(新)<br/>
	 * 方法名：searchNewPublishPriceByCondition
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2013-01-24
	 * @since JDK1.6
	 */
	@JSON
	public String searchNewPublishPriceByCondition() {
		try {
			List<PublicPriceDto> publicPriceDtoList = publishPriceService.queryPublishPriceDetailByConditionWithFlight(newPublishPriceVo.getStartCityCode()
					, newPublishPriceVo.getDestinationCountryCode(), newPublishPriceVo.getDestinationProvinceCode(), newPublishPriceVo.getDestinationCityCode(), newPublishPriceVo.getDestinationCountyCode()
					, newPublishPriceVo.getDestinationAddress(), newPublishPriceVo.getFlightSort(), null);
			newPublishPriceVo.setPublicPriceDtoList(publicPriceDtoList);
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
	public String queryOuterAndDepartment() {
		try {
			List<SaleDepartmentInfoDto> saleDepartmentInfoDtoList = publishPriceService.getOuterAndDepartment(newPublishPriceVo.getDeptCodes(), newPublishPriceVo.getDeptRegionId(), newPublishPriceVo.getRegionNature(),newPublishPriceVo.getProductCode(),newPublishPriceVo.getPriceRegionIdClass());
			newPublishPriceVo.setSaleDepartmentInfoDtoList(saleDepartmentInfoDtoList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
}