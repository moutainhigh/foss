/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.savers;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.framework.client.component.sync.AbstractSyncDataSaver;
import com.deppon.foss.module.pickup.common.client.service.DownLoadDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.impl.PriceCriteriaDetailService;
import com.deppon.foss.module.pickup.common.client.service.impl.PriceValuationService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.PriceValuationRegionExpressDto;

/**
 * @author ibm-foss-sxw
 *
 */
public class SrvPriceValuationRegionExpressSaver extends AbstractSyncDataSaver {
	//区域id
	private String regionID;
	
	/**
	 * 
	 * 功能：映射实体类
	 * 该方法主要用于返回实体类型  用于给客户端判断
	 * 下载的是何种类型的数据实体
	 * 和对应实体的是那一张票
	 * 该实体的类名会保存在水位表的的类信息中
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	public Class<?> getSaveType() {
		return PriceValuationRegionExpressDto.class;
	}
	
	/**
	 * 
	 * 功能：保存数据
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public Date saveData(List<?> data) {
		Date date = null;
		List<PriceCriteriaDetailEntity> detailList;
		long mxVersion = 0;
		List<PriceValuationRegionExpressDto> list = (List<PriceValuationRegionExpressDto>)data;
		if(list != null && !list.isEmpty()){
			Map<String, List<PriceValuationRegionExpressDto>> map =  (Map)list.get(0);
			for (Entry<String, List<PriceValuationRegionExpressDto>> entry : map.entrySet()) {
			PriceValuationService priceValuationService = DownLoadDataServiceFactory
					.getPriceValuationService();
			PriceCriteriaDetailService priceCriteriaDetailService = DownLoadDataServiceFactory
					.getPriceCriteriaDetailService();
			regionID = entry.getKey();
			
			for(PriceValuationRegionExpressDto priceValuationRegionAddDto : entry.getValue()){
				priceValuationService.saveOrUpdate(priceValuationRegionAddDto);
				detailList = priceValuationRegionAddDto.getCriteriaDetailEntities();
				if(detailList != null){
					for(PriceCriteriaDetailEntity detailEntity : detailList) {
						priceCriteriaDetailService.saveOrUpdate(detailEntity);
					}
				}
				
				Long version = priceValuationRegionAddDto.getVersionNo();
				//取新价格区域ID
				regionID = priceValuationRegionAddDto.getDeptRegionId();
				//获得最大版本号
				if(version != null && version>mxVersion){
					mxVersion = version;
				}
				
			}
			
		  } 
		} 
		//创建水位日期
		date = new Date();
		//设置水位时间戳
		//该时间戳标记了上次下载的位置
		//返回以后保存于水位表中
		//下次下载会从该时间戳起进行增量下载
		date.setTime(mxVersion);
		//返回水位信息
		//由于hqlsql 和oracle精度不同， 
		//因此我们在下次下载的时候 可能会对该时间戳做1秒钟的提前量
		//但是这些多余的数据会进行update操作  
		//所以不会有很多影像
		return date;
	}
	
	/**
	 * 
	 * 功能：取区域ID
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	public String getRegionID() {
		return regionID;
	}

}
