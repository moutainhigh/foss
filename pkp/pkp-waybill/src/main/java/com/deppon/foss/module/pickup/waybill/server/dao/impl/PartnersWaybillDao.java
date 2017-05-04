package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPartnersWaybillDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.PartnersWaybillEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class PartnersWaybillDao extends iBatis3DaoImpl implements IPartnersWaybillDao {

	private Logger logger = LoggerFactory.getLogger(PartnersWaybillDao.class);

	private static final String NAMESPACE = "foss.pkp.PartnerWaybillEntityMapper.";

	@Override
	public String addPartnersWaybillEntity(PartnersWaybillEntity waybill) {
		logger.info("合伙人添加运单信息");
		// 设置UUID
		waybill.setId(UUIDUtils.getUUID());

		this.getSqlSession().insert(NAMESPACE + "insert", waybill);

		return waybill.getId();
	}

	@Override
	public int updatePartnersWaybill(PartnersWaybillEntity waybill) {
		waybill.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().update(
				NAMESPACE + "updatePartnersWaybillByWaybillNo", waybill);
	}

	/**
	 * 更新合伙人运单信息是否可用
	 * @param map 只有两个参数 active：是否可激活,waybillNo:运单号
	 * @return
	 * @author 272311-sangwenhao
	 * @date 2016-1-18
	 */
	@Override
	public int updateActiveByWaybillNo(Map<String, String> map) {
		return this.getSqlSession().update(
				NAMESPACE + "updateActiveByWaybillNo", map);
	}

	/**
	 * 通过运单表中的运单Id查询合伙人表中的运单信息
	 * @param waybillNoId 运单表中的运单Id
	 * @return PartnersWaybillEntity
	 * @author 272311-sangwenhao
	 * @date 2016-1-27
	 */
	@Override
	public PartnersWaybillEntity queryPartnerWaybillEntityByWaybillId(String waybillNoId) {
		return (PartnersWaybillEntity) this.getSqlSession().selectOne(NAMESPACE + "queryPartnerWaybillEntityByWaybillId", waybillNoId);
	}

	/**
	 * 通过id更新partnerWaybill实体
	 * @param waybill PartnersWaybillEntity
	 * @return 
	 * @author 272311-sangwenhao
	 * @date 2016-1-27
	 */
	@Override
	public int updatePartnerWaybillById(PartnersWaybillEntity waybill) {
		if(waybill!=null){
			return this.getSqlSession().update(NAMESPACE + "updatePartnerWaybillById", waybill);
		}
		return 0;
	}
	
	//通过运单编号查询合伙人表中的运单信息 2016年3月15日 19:49:02 葛亮亮
	public PartnersWaybillEntity queryPartnerWaybillEntityByWaybillNo(Map<String, String> map){
		return (PartnersWaybillEntity) this.getSqlSession().selectOne(NAMESPACE + "queryPartnerWaybillEntityByWaybillNo", map);
	}
}
