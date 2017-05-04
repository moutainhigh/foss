/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.server.download;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.ClientUpdateDataPack;
import com.deppon.foss.base.util.DataBundle;
import com.deppon.foss.framework.entity.SyncDataRequest;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceDownLoadExpressService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.PricePlanExpressDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author ibm-foss-sxw
 *syncPricePlanExpress
 */
public class SyncPricePlanExpress extends iBatis3DaoImpl implements ISyncDataDao<PricePlanExpressDto> {
	private static final Logger LOGGER = LoggerFactory.getLogger(SyncPricePlanExpress.class);
	
	/**
	 *  价格 下载服务
	 */
	private IPriceDownLoadExpressService priceDownLoadExpressService;
	/**
	 * 返回数据列表
	 */
	/**
	 * 返回数据列表
	 */
	
	/**
	 * @功能：同步数据
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSyncData(Date fromDate, 
			String empCode, String regionID,String pagionation, int page) {
		List<Map<String, List<PricePlanExpressDto>>> resultList = null;
		List<PricePlanExpressDto> list = new ArrayList<PricePlanExpressDto>();
		
		DataBundle data;
		String orgCode = FossUserContext.getCurrentDeptCode();
		ClientUpdateDataPack clientInfo = new ClientUpdateDataPack();
		clientInfo.setLastUpdateTime(fromDate);
		clientInfo.setEmpCode(empCode);
		clientInfo.setOrgCode(orgCode);
		clientInfo.setRegionId(regionID);
		data = priceDownLoadExpressService.downPricePlanServerData(clientInfo);
		if (data != null) {
			if (data.getObject() != null) {
				List  list2 = ((List<PricePlanEntity>) data.getObject());
				if (list2 != null && list2.size() > 0) {
					
					for(int i =0 ; i<list2.size();i++){
						PricePlanEntity orig = (PricePlanEntity)list2.get(i);
						PricePlanExpressDto dest = new PricePlanExpressDto();
						try{
							PropertyUtils.copyProperties(dest, orig);
						}catch(Exception e){
							LOGGER.info(e.getMessage());
						}
						list.add(dest);
					}
					
					
					Map<String, List<PricePlanExpressDto>> map = new HashMap<String, List<PricePlanExpressDto>>();
					String needDeleteLocalData = data.getNeedDeleteLocalData();
					if (needDeleteLocalData != null
							&& !"".equals(needDeleteLocalData)) {
						//放原价格区域ID
						if (FossConstants.YES.equals(needDeleteLocalData)) {
							map.put(regionID, list);
						} else{
						    map.put(needDeleteLocalData, list);
						}
					}
					resultList = new ArrayList();
					resultList.add(map);
				}
			}
		}
		return  resultList;
	}
	
	/**
	 * @功能：获取最后跟新时间
	 */
	public Date getLastModifyTime() {
		return null;
	}

	


	public IPriceDownLoadExpressService getPriceDownLoadExpressService() {
		return priceDownLoadExpressService;
	}

	public void setPriceDownLoadExpressService(
			IPriceDownLoadExpressService priceDownLoadExpressService) {
		this.priceDownLoadExpressService = priceDownLoadExpressService;
	}

	/** 同步
	 * @see com.deppon.foss.framework.server.components.sync.ISyncDataDao#getSyncData(com.deppon.foss.framework.entity.SyncDataRequest)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSyncData(SyncDataRequest request) {
		return null;
	}


}
