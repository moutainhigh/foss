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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IPdaBillCaculateService.java
 * 
 * FILE NAME        	: IPdaBillCaculateService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.HashMap;
import java.util.List;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaResultBillCalculateDto;

public interface IPdaBillCaculateService extends IService {
	
	/**
	 * 
	 * @Description: 计算运费
	 * Company:IBM
	 * @author FOSSDP-sz
	 * @date 2013-1-14 上午10:33:35
	 * @param billCalculateDto
	 * @return
	 * @version V1.0
	 */
	 List<PdaResultBillCalculateDto>  queryPdaTransportBillPrice(PdaQueryBillCalculateDto billCalculateDto);
	
	/**
	 * 
	 * @Description: 计算增值服务
	 * Company:IBM
	 * @author FOSSDP-sz
	 * @date 2013-1-14 下午4:48:15
	 * @param billCalculateDto
	 * @return
	 * @version V1.0
	 */
	 List<PdaResultBillCalculateDto> queryPdaValueAddBillPrice(PdaQueryBillCalculateDto billCalculateDto);
 
	/**
	 * 
	 * @Description: 计算物流费用
	 * Company:IBM
	 * @author FOSSDP-sz
	 * @date 2013-1-14 下午3:05:21
	 * @param billCalculateDto
	 * @return
	 * @version V1.0
	 */
	 List<PdaResultBillCalculateDto>  queryPdaBillPrice(PdaQueryBillCalculateDto billCalculateDto);
	 
	 
	 /**
		 * 
		 * @Description: 计算快遞物流费用
		 * 
		 * Company:IBM
		 * 
		 * @author FOSSDP-zdp
		 * 
		 * @date 2013-1-14 下午3:05:21
		 * 
		 * @param billCalculateDto
		 * 
		 * @return
		 * 
		 * @version V1.0
	 */
	  public List<PdaResultBillCalculateDto> queryPdaExpressBillPrice(
			PdaQueryBillCalculateDto billCalculateDto);
	  
	  /**
	   * RFOSS2015041729
	   * 
	   * @author foss-206860
	   * 
	   *  PDA不传产品类型查询物流费用接口
	   * */
	  public HashMap<String, List<PdaResultBillCalculateDto>> queryPdaExpressBillPriceNoProduct(
				PdaQueryBillCalculateDto billCalculateDto);
}