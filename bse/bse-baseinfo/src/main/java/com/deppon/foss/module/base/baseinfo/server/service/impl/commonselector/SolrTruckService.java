package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ISolrTruckService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.util.PropertiesUtil;

public class SolrTruckService implements ISolrTruckService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SolrTruckService.class);
	
	/**
	 * 通过solr服务查询数据
	 * @Title: truckSolrQuery 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param listParams
	 * @param @param classz
	 * @param @return    设定文件 
	 * @return List<V>    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	public SolrDocumentList truckSolrQuery(OwnTruckEntity ownTruck,String key , int start, int limit) {
	//	List<OwnTruckEntity> list = new ArrayList<OwnTruckEntity>();
		SolrDocumentList results = null;
		try {
			HttpSolrServer solrServer = new HttpSolrServer(getSolrServer(key));// sor地址
			
			//  区域名称非空，车辆表与定人定区表join联合查询
			SolrQuery solrQuery = new SolrQuery();  
			solrQuery.setStart(start);
			solrQuery.setRows(limit);
			if(!StringUtil.isEmpty(ownTruck.getReginName()) && key.equals("truck.solr.truck.core.address")){
				solrQuery.set("q", "{!join from=reginName to=vehicleNo fromIndex=vehicleNo}reginName:"+ownTruck.getReginName());
			}
			StringBuilder params = setStrParams(ownTruck);
			if(null != params && 0 < params.length()){
				solrQuery.setQuery(params.toString());  
			}
			QueryResponse response = solrServer.query(solrQuery); 
			results = response.getResults();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			LOGGER.error("SOLR查询车辆有误：  "+e.getMessage());
			throw new BusinessException("SOLR查询车辆有误 ");
		}
		return results;
	}
	
	public StringBuilder setStrParams(OwnTruckEntity ownTruck){
		StringBuilder params = new StringBuilder();
		params.append("active:Y");
		if(!StringUtil.isEmpty(ownTruck.getId())){
			params.append(" AND id:"+ownTruck.getId());
		}
		if(!StringUtil.isEmpty(ownTruck.getVehicleNo())){
			params.append(" AND vehicleNo:"+ownTruck.getVehicleNo());
		}
		if(!StringUtil.isEmpty(ownTruck.getOrgId())){
			params.append(" AND orgId:"+ownTruck.getOrgId());
		}
		if(!StringUtil.isEmpty(ownTruck.getStatus())){
			params.append(" AND status:"+ownTruck.getStatus());
		}
		if(null != ownTruck.getSelfWeight()){
			params.append(" AND selfWfight:"+ownTruck.getSelfWeight());
		}
		if(!StringUtil.isEmpty(ownTruck.getBrand())){
			params.append(" AND brand:"+ownTruck.getBrand());
		}
		if(!StringUtil.isEmpty(ownTruck.getUsedType())){
			params.append(" AND usedType:"+ownTruck.getUsedType());
		}
		if(null != ownTruck.getVehicleLength()){
			params.append(" AND vehicleLength:"+ownTruck.getVehicleLength());
		}
		if(null != ownTruck.getVehicleWidth()){
			params.append(" AND vehicleWidth:"+ownTruck.getVehicleWidth());
		}
		if(null != ownTruck.getVehicleHeight()){
			params.append(" AND vehicleHeight:"+ownTruck.getVehicleHeight());
		}
		if(!StringUtil.isEmpty(ownTruck.getTailBoard())){
			params.append(" AND tailBoard:"+ownTruck.getTailBoard());
		}
		if(null != ownTruck.getDeadLoad()){
			params.append(" AND beadLoad"+ownTruck.getDeadLoad());
		}
		if(!StringUtil.isEmpty(ownTruck.getGpsNo())){
			params.append(" AND gpsNo:"+ownTruck.getGpsNo());
		}
		if(!StringUtil.isEmpty(ownTruck.getBridge())){
			params.append(" AND bridge:"+ownTruck.getBridge());
		}
		/*if(!StringUtil.isEmpty(ownTruck.getActive())){
			params.append("active:"+ownTruck.getActive());
		}*/
		if(!StringUtil.isEmpty(ownTruck.getVehicleType())){
			params.append(" AND vehicleType:"+ownTruck.getVehicleType());
		}
		if(!StringUtil.isEmpty(ownTruck.getContainerCode())){
			params.append(" AND containerCode:"+ownTruck.getContainerCode());
		}
		if(!StringUtil.isEmpty(ownTruck.getVehcleLengthCode())){
			params.append(" AND vehcleLengthCode:"+ownTruck.getVehcleLengthCode());
		}
		if(!StringUtil.isEmpty(ownTruck.getQueryParam())){
			params.append(" AND vehicleNo:"+ownTruck.getVehicleNo()+" or containerCode:"+ownTruck.getContainerCode());
		}
		if(!StringUtil.isEmpty(ownTruck.getVehicleNoNoLike())){
			params.append(" AND vehicleNo:"+ownTruck.getVehicleNoNoLike());
		}
		if(null != ownTruck.getOrgIds() && 0 < ownTruck.getOrgIds().size()){
			for(String orgId: ownTruck.getOrgIds()){
				params.append(" OR orgId:"+orgId);
			}
		}
		return params;
	}
	
	public ModifiableSolrParams setModParams(OwnTruckEntity ownTruck){
		ModifiableSolrParams params = new ModifiableSolrParams();
		if(!StringUtil.isEmpty(ownTruck.getId())){
			params.add("id:"+ownTruck.getId());
		}
		if(!StringUtil.isEmpty(ownTruck.getVehicleNo())){
			params.add("vehicleNo:"+ownTruck.getVehicleNo());
		}
		if(!StringUtil.isEmpty(ownTruck.getOrgId())){
			params.add("orgId:"+ownTruck.getOrgId());
		}
		if(!StringUtil.isEmpty(ownTruck.getStatus())){
			params.add("status:"+ownTruck.getStatus());
		}
		if(null != ownTruck.getSelfWeight()){
			params.add("selfWfight:"+ownTruck.getSelfWeight());
		}
		if(!StringUtil.isEmpty(ownTruck.getBrand())){
			params.add("brand:"+ownTruck.getBrand());
		}
		if(!StringUtil.isEmpty(ownTruck.getUsedType())){
			params.add("usedType:"+ownTruck.getUsedType());
		}
		if(null != ownTruck.getVehicleLength()){
			params.add("vehicleLength:"+ownTruck.getVehicleLength());
		}
		if(null != ownTruck.getVehicleWidth()){
			params.add("vehicleWidth:"+ownTruck.getVehicleWidth());
		}
		if(null != ownTruck.getVehicleHeight()){
			params.add("vehicleHeight:"+ownTruck.getVehicleHeight());
		}
		if(!StringUtil.isEmpty(ownTruck.getTailBoard())){
			params.add("tailBoard:"+ownTruck.getTailBoard());
		}
		if(null != ownTruck.getDeadLoad()){
			params.add("beadLoad"+ownTruck.getDeadLoad());
		}
		if(StringUtil.isEmpty(ownTruck.getGpsNo())){
			params.add("gpsNo:"+ownTruck.getGpsNo());
		}
		if(StringUtil.isEmpty(ownTruck.getBridge())){
			params.add("bridge:"+ownTruck.getBridge());
		}
		if(!StringUtil.isEmpty(ownTruck.getActive())){
			params.add("active:"+ownTruck.getActive());
		}
		if(!StringUtil.isEmpty(ownTruck.getVehicleType())){
			params.add("vehicleType:"+ownTruck.getVehicleType());
		}
		if(!StringUtil.isEmpty(ownTruck.getContainerCode())){
			params.add("containerCode:"+ownTruck.getContainerCode());
		}
		if(!StringUtil.isEmpty(ownTruck.getVehcleLengthCode())){
			params.add("vehcleLengthCode:"+ownTruck.getVehcleLengthCode());
		}
		if(!StringUtil.isEmpty(ownTruck.getQueryParam())){
			params.add("vehicleNo:"+ownTruck.getVehicleNo()+" or containerCode:"+ownTruck.getContainerCode());
		}
		if(!StringUtil.isEmpty(ownTruck.getVehicleNoNoLike())){
			params.add("vehicleNo:"+ownTruck.getVehicleNoNoLike());
		}
		return params;
	}
	
	/**
	 * 获取solr服务地址
	 * @Title: getSolrServer 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	private String getSolrServer(String key){
		return PropertiesUtil.getKeyValue(key);
	}
}
