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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/FreightRouteDto.java
 * 
 * FILE NAME        	: FreightRouteDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FreightRouteLineDto;

/**
 * The Class FreightRouteDto.
 */
public class FreightRouteDto implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -4379292260867621838L;

	/**
	 * The freight route entity.
	 */
	private FreightRouteEntity freightRouteEntity;
	
	/**
	 * The freight route linelist.
	 */
	private List<FreightRouteLineDto> freightRouteLinelist;

	
	/**
	 * @return the freightRouteEntity .
	 */
	public FreightRouteEntity getFreightRouteEntity() {
		return freightRouteEntity;
	}

	
	/**
	 *@param freightRouteEntity the freightRouteEntity to set.
	 */
	public void setFreightRouteEntity(FreightRouteEntity freightRouteEntity) {
		this.freightRouteEntity = freightRouteEntity;
	}

	
	/**
	 * @return the freightRouteLinelist .
	 */
	public List<FreightRouteLineDto> getFreightRouteLinelist() {
		return freightRouteLinelist;
	}

	
	/**
	 *@param freightRouteLinelist the freightRouteLinelist to set.
	 */
	public void setFreightRouteLinelist(List<FreightRouteLineDto> freightRouteLinelist) {
		this.freightRouteLinelist = freightRouteLinelist;
	}


}