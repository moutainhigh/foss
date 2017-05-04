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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonOwnTractorsAction.java
 * 
 * FILE NAME        	: CommonOwnTractorsAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.action
 * FILE    NAME: CommonOwnTractorsSelectorAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOwnTractorsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ISolrTruckService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.TruckDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.OwnTruckVo;
import com.deppon.foss.module.base.baseinfo.server.util.SolrObject;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;

/**
 * 公共选择器--公司车Action.
 *
 * @author panGuangJun
 * @date 2012-12-3 上午8:24:37
 */
public class CommonOwnTractorsAction extends AbstractAction implements
		IQueryAction {
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 282806190196083258L;
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonOwnTractorsAction.class);
	// vo
	/** The truck vo. */
	private OwnTruckVo truckVo = new OwnTruckVo();
	// service
	/** The common own tractors service. */
	private ICommonOwnTractorsService commonOwnTractorsService;

	/**
     * 系统配置参数 Service接口
     */
    private IConfigurationParamsService configurationParamsService;
    
    private ISolrTruckService solrTruckService;
    
    public void setSolrTruckService(ISolrTruckService solrTruckService) {
		this.solrTruckService = solrTruckService;
	}

	/**
     * @param configurationParamsService the configurationParamsService to set
     */
    public void setConfigurationParamsService(
    	IConfigurationParamsService configurationParamsService) {
        this.configurationParamsService = configurationParamsService;
    }
	
    private final String OWNTRUCK_SOLR_SERVER = "truck.solr.owntruck.core.address";
    private final String TRUCK_SOLR_SERVER = "truck.solr.truck.core.address";
    
	/**
	 * 查询公司车辆.
	 *
	 * @return the string
	 * @author panGuangJun
	 * @date 2012-12-3 上午8:24:58
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@JSON
	@Override
	public String query() {
		if(configurationParamsService.queryVehicleBSolr()){
			LOGGER.info("SOLR搜索开始");
			try{
				OwnTruckEntity entity = commonOwnTractorsService.setOwnTruckEntityOrgIds(truckVo.getTruck());
				SolrDocumentList results = solrTruckService.truckSolrQuery(entity, 
						OWNTRUCK_SOLR_SERVER, start, limit);
				SolrObject<OwnTruckEntity> solrObject = new SolrObject<OwnTruckEntity>();
				if(0 < results.getNumFound()){
					List<OwnTruckEntity> entityList = commonOwnTractorsService.setownTruckListOrgName(solrObject.toBeanList(results, OwnTruckEntity.class));
					truckVo.setOwnTrucks(entityList);
					setTotalCount(results.getNumFound());
				}
			} catch (Exception e){
				LOGGER.info("SOLR错误:"+e.getMessage());
				return returnError("错误:"+e.getMessage());
			}
			LOGGER.info("SOLR搜索结束");
		} else {
			truckVo.setOwnTrucks(commonOwnTractorsService
					.queryOwnTractorsListByCondition(truckVo.getTruck(), start,
							limit));
			setTotalCount(commonOwnTractorsService
					.queryOwnTractorsRecordCountByCondition(truckVo.getTruck()));
		}
		return returnSuccess();
	}

	/**
	 * 所有公司车辆.
	 *
	 * @return the string
	 * @author panGuangJun
	 * @date 2012-12-3 上午8:24:58
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@JSON
	public String queryAllOwnTractors() { 
		if(configurationParamsService.queryVehicleBSolr()){
			LOGGER.info("SOLR搜索开始");
			try{
				OwnTruckEntity entity = commonOwnTractorsService.setTractorsOrgIds(truckVo.getTruck());
				SolrDocumentList results = solrTruckService.truckSolrQuery(entity, 
						TRUCK_SOLR_SERVER, start, limit);
				SolrObject<TruckDto> solrObject = new SolrObject<TruckDto>();
				if(0 < results.getNumFound()){
					List<TruckDto> entityList = commonOwnTractorsService.setTractorsOrgName(solrObject.toBeanList(results, TruckDto.class));
					boolean noExact =true;
					String vehicleNo= entity.getVehicleNo();
					if(StringUtils.isNotBlank(vehicleNo)){
						for(TruckDto truckDto:entityList){
							if(truckDto.getVehicleNo().equals(vehicleNo)){
									List<TruckDto> newEntityList = new ArrayList<TruckDto>();
									newEntityList.add(truckDto);
									truckVo.setTruckDtos(newEntityList);
									noExact=false;
									setTotalCount(1L);
									break;
							} 
						}
					}
					if(noExact){
						truckVo.setTruckDtos(entityList);
						setTotalCount(results.getNumFound());
					}
					
				}
			} catch (Exception e){
				LOGGER.info("SOLR错误:"+e.getMessage());
				return returnError("错误:"+e.getMessage());
			}
			LOGGER.info("SOLR搜索结束");
		} else {
			truckVo.setTruckDtos(commonOwnTractorsService
					.queryTractorsListByCondition(truckVo.getTruck(), start,
							limit));
			setTotalCount(commonOwnTractorsService
					.queryTractorsRecordCountByCondition(truckVo.getTruck()));
		}
		
		return returnSuccess();
	}

	/**
	 * Gets the truck vo.
	 *
	 * @return the truck vo
	 */
	public OwnTruckVo getTruckVo() {
		return truckVo;
	}

	/**
	 * Sets the truck vo.
	 *
	 * @param truckVo the new truck vo
	 */
	public void setTruckVo(OwnTruckVo truckVo) {
		this.truckVo = truckVo;
	}

	/**
	 * Sets the common own tractors service.
	 *
	 * @param commonOwnTractorsService the new common own tractors service
	 */
	public void setCommonOwnTractorsService(
			ICommonOwnTractorsService commonOwnTractorsService) {
		this.commonOwnTractorsService = commonOwnTractorsService;
	}
}
