package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IOmsOrderDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.OmsOrderEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LTLEWaybillQueryResultDto;

/**
 * @description OMS订单处理DAO
 * @author 297064
 *
 */
public class OmsOrderDao extends iBatis3DaoImpl implements IOmsOrderDao
{
	private static final String NAMESPACE = "foss.pkp.OmsOrderMapper.";
	
	/**
	 * 保存omsOrderEntity
	 * @param omsOrderEntity
	 */
	@Override
	public int save(OmsOrderEntity omsOrderEntity) {
		return this.getSqlSession().insert(NAMESPACE+"insertSelective", omsOrderEntity);
	}
	
	/**
	 * 根据订单号更新订单 (去除了订运单修改)
	 */
	@Override
	public int updateOmsOrderByOrderNoSelective(OmsOrderEntity omsOrderEntity) {
		return this.getSqlSession().update(NAMESPACE+"updateOmsOrderByOrderNoSelective", omsOrderEntity);
	}

	/**
	 * 根据订单查询OmsOrderEntity
	 * @param orderNo
	 * @return
	 */
	@Override
	public OmsOrderEntity queryOmsOrderByOrderNo(String orderNo) {
		if(StringUtils.isEmpty(orderNo)){
			return null;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderNo",orderNo);
		return (OmsOrderEntity)this.getSqlSession().selectOne(NAMESPACE+"queryOmsOrderByOrderNoOrWaybillNo", params);
	}
	
	/**
	 * 根据运单号查询OmsOrderEntity
	 * @param waybillNo
	 * @return
	 */
	@Override
	public OmsOrderEntity queryOmsOrderByWaybillNo(String waybillNo) {
		if(StringUtils.isEmpty(waybillNo)){
			return null;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("waybillNo",waybillNo);
		return (OmsOrderEntity)this.getSqlSession().selectOne(NAMESPACE+"queryOmsOrderByOrderNoOrWaybillNo", params);
	}

	/**
	 * 查询零担电子运单信息
	 */
	@Override
	public List<LTLEWaybillQueryResultDto> queryOmsOrderOrLabelStatusByWaybillNo(LTLEWaybillQueryResultDto ltleWaybillQueryResultDto){
		return this.getSqlSession().selectList(NAMESPACE+"queryOmsOrderOrLabelStatusByWaybillNo", ltleWaybillQueryResultDto);
	}


	
	/**
	 * 根据ID更新OmsOrderEntity
	 * @param omsOrderEntity
	 * @return
	 */
	@Override
	public int updateOmsOrderByPrimaryKey(OmsOrderEntity omsOrderEntity){
		return this.getSqlSession().update(NAMESPACE+"updateByPrimaryKey", omsOrderEntity);
	}
}
