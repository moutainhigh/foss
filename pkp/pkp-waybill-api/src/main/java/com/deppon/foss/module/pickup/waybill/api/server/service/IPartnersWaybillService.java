package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.PartnersWaybillEntity;

/**
 * 
 * @author 272311-sangwenhao
 * @date 2016-1-18
 */
public interface IPartnersWaybillService extends IService {
	
	/**
	 * 合伙人提交运单时 新增至合伙人记录表
	 * @param waybill
	 * @return uuid
	 * @author 272311-sangwenhao
	 * @date 2016-1-18
	 */
	String addPartnersWaybillEntity(PartnersWaybillEntity waybill);
	
	/**
	 * 更新合伙人运单信息
	 * @param waybill
	 * @return
	 * @author 272311-sangwenhao
	 * @date 2016-1-18
	 */
	int updatePartnersWaybill(PartnersWaybillEntity waybill);
	
	/**
	 * 更新合伙人运单信息是否可用
	 * @param map 只有两个参数 active：是否可激活,waybillNo:运单号
	 * @return
	 * @author 272311-sangwenhao
	 * @date 2016-1-18
	 */
	int updateActiveByWaybillNo(Map<String, String> map);
	
	/**
	 * 通过运单表中的运单Id查询合伙人表中的运单信息
	 * @param waybillNoId 运单表中的运单Id
	 * @return PartnersWaybillEntity
	 * @author 272311-sangwenhao
	 * @date 2016-1-27
	 */
	PartnersWaybillEntity queryPartnerWaybillEntityByWaybillId(String waybillNoId) ;
	
	/**
	 * 通过id更新partnerWaybill实体
	 * @param waybill PartnersWaybillEntity
	 * @return 
	 * @author 272311-sangwenhao
	 * @date 2016-1-27
	 */
	int updatePartnerWaybillById(PartnersWaybillEntity waybill);
	
	//通过运单编号查询合伙人表中的运单信息 2016年3月15日 19:49:02 葛亮亮
	PartnersWaybillEntity queryPartnerWaybillEntityByWaybillNo(Map<String, String> map);

}
