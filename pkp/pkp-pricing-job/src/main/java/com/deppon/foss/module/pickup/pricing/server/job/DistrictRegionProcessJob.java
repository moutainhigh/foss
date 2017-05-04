/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-pricing-job
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.job;


import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.jobgrid.GridJob;
import com.deppon.foss.module.pickup.pricing.api.server.service.IDistrictRegionService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.DistrictRegionEntity;
import com.deppon.foss.module.pickup.pricing.server.service.impl.DistrictRegionService;
import com.deppon.foss.util.CollectionUtils;
/**
 * 
 * @Description: 行政区域与时效、汽运、空运价格区域关系JOB
 * DistrictRegionProcessJob.java Create on 2013-4-19 下午3:47:23
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class DistrictRegionProcessJob extends GridJob {
	private static final Logger LOGGER = LoggerFactory.getLogger(DistrictRegionProcessJob.class);
	/**
	 * 行政区域与时效、汽运、空运价格区域关系更新JOB
	 * @author foss-sz
	 * @date 2013-04-19下午创建在pkp-job工程下
	 * @date 2017-02-16下午从pkp-job拆分到了pkp-pricing-job工程下  ：348757
	 * @param context
	 * @throws JobExecutionException 
	 */
	@Override
	protected void doExecute(JobExecutionContext context) throws JobExecutionException {
		try {
			//获取SERVICE
			IDistrictRegionService districtRegionService = getBean("districtRegionService", DistrictRegionService.class);
			LOGGER.info("行政区域与时效、汽运、空运价格区域关系更新开始   DistrictRegionProcessJob begin");
			//查询所有已存在数据
			List<DistrictRegionEntity> districtRegionEntities = districtRegionService.searchDistrictRegion();
			if(CollectionUtils.isNotEmpty(districtRegionEntities)) {
				for (DistrictRegionEntity districtRegionEntity : districtRegionEntities) {
					districtRegionService.addDistrictRegion(districtRegionEntity.getDistrictCode());
				}
			}
			LOGGER.info("行政区域与时效、汽运、空运价格区域关系更新结束   DistrictRegionProcessJob end");
		} catch (Exception e) {
			LOGGER.error("error", e);
			throw new JobExecutionException("行政区域与时效、汽运、空运价格区域关系更新异常", e);
		}
	}
}