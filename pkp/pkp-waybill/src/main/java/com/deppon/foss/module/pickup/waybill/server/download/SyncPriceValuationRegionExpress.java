/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.server.download;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.base.util.ClientUpdateDataPack;
import com.deppon.foss.base.util.DataBundle;
import com.deppon.foss.framework.entity.SyncDataRequest;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceDownLoadExpressService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.PriceValuationRegionExpressDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author ibm-foss-sxw
 *
 */
public class SyncPriceValuationRegionExpress  extends iBatis3DaoImpl implements ISyncDataDao<PriceValuationRegionExpressDto> {
	/**
	 *  价格 下载服务
	 */
	private IPriceDownLoadExpressService priceDownLoadExpressService;
	/**
	 * 返回数据列表
	 */
	/**
	 * @功能：同步数据
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSyncData(Date fromDate,
			String empCode, String regionID,String pagionation, int page) {
		List<PriceValuationRegionExpressDto> priceValuationProductAddList = new ArrayList<PriceValuationRegionExpressDto> ();
		List<PriceValuationEntity> list = new ArrayList<PriceValuationEntity> ();
		List<Map<String, List<PriceValuationRegionExpressDto>>> resultList = new ArrayList<Map<String, List<PriceValuationRegionExpressDto>>> ();
		DataBundle data;
		ClientUpdateDataPack clientInfo = new ClientUpdateDataPack();
		String orgCode = FossUserContext.getCurrentDeptCode();
		clientInfo.setLastUpdateTime(fromDate);
		clientInfo.setEmpCode(empCode);
		clientInfo.setOrgCode(orgCode);
		clientInfo.setRegionId(regionID);
		clientInfo.setPagination(pagionation);
		clientInfo.setSyncPage(page);
		data = priceDownLoadExpressService
				.downPricingValuationRegionValueAddServerData(clientInfo);
		if (data != null) {
			if (data.getObject() != null) {
				list = ((List<PriceValuationEntity>) data.getObject());
				if (list != null && list.size() > 0) {
					Map<String, List<PriceValuationRegionExpressDto>> map = new HashMap<String, List<PriceValuationRegionExpressDto>>();
					String needDeleteLocalData = data.getNeedDeleteLocalData();
					priceValuationProductAddList = new ArrayList();
					for (PriceValuationEntity entity : list) {
						PriceValuationRegionExpressDto dto = new PriceValuationRegionExpressDto();
						BeanUtils.copyProperties(entity, dto);
						priceValuationProductAddList.add(dto);
					}
					if (needDeleteLocalData != null
							&& !"".equals(needDeleteLocalData)) {
						// 放原时效区域ID
						if (FossConstants.YES.equals(needDeleteLocalData)) {
							map.put(regionID, priceValuationProductAddList);
						}else{
						    map.put(needDeleteLocalData,
								priceValuationProductAddList);
						}
					}
					resultList = new ArrayList();
					resultList.add(map);
				}
			}
		}
		return resultList;
	}
	
	

	public IPriceDownLoadExpressService getPriceDownLoadExpressService() {
		return priceDownLoadExpressService;
	}



	public void setPriceDownLoadExpressService(
			IPriceDownLoadExpressService priceDownLoadExpressService) {
		this.priceDownLoadExpressService = priceDownLoadExpressService;
	}



	public Date getLastModifyTime() {
		return null;
	}

	/** 同步
	 * @see com.deppon.foss.framework.server.components.sync.ISyncDataDao#getSyncData(com.deppon.foss.framework.entity.SyncDataRequest)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getSyncData(SyncDataRequest request) {
		return null;
	}
}
