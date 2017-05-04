package com.deppon.foss.module.pickup.order.server.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.ICombinateBillDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.BigCustomeEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.CombinateBillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabelPrintDto;
import com.deppon.foss.util.UUIDUtils;

public class CombinateBillDao extends iBatis3DaoImpl implements ICombinateBillDao {
	private static final String NAMESPACE = "foss.pkp.CombinateBillMapper.";
	private static final String NAMESPACE_BigCustomeEntity = "com.deppon.foss.module.pickup.order.api.shared.domain.BigCustomeEntity.";
	private static final String NAMESPACE_WaybillPendingEntity = "foss.pkp.WaybillPendingEntityMapper.";
	
	
	/**
	 * 
	 * <p>新增合并数据</p> 
	 * @author 183272
	 * @date 2015年9月17日 下午1:41:53
	 * @param combinateBill
	 * @return
	 * @see
	 */
	@Override
	public  int addCombinateBillEntity(CombinateBillEntity combinateBill) {
		combinateBill.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(NAMESPACE + "insert", combinateBill);
	}


	@Override
	public String queryMaxWaybillNo() {
		return (String) this.getSqlSession().selectOne(NAMESPACE+"findMaxWaybillNo");
	}


	@Override
	public ArrayList<BigCustomeEntity> queryCombinateBigCustomeByTime(Date billTimeFrom,
			Date billTimeTo,int option) {

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("billTimeFrom", billTimeFrom);
        map.put("billTimeTo", billTimeTo);
        map.put("option", option);
		return (ArrayList<BigCustomeEntity>) getSqlSession().selectList(NAMESPACE_BigCustomeEntity + "queryCombinateBigCustomeByTime", map);
	}
	
	@Override
	public String queryNextBigCustomWaybillNo() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("sequenceName", "BIGCUSTOME_WAYBILLNO_SEQ");
		return (String) this.getSqlSession().selectOne(NAMESPACE_WaybillPendingEntity+"getNextEWaybillNo", params);
	}
	
	/**
	 * 
	 */
	@Override
	public String disActiveCombinatebill(String waybillno) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("active", "N");
		params.put("waybillno", waybillno);
		params.put("modifyTime", new Date());
		return (String) this.getSqlSession().selectOne(NAMESPACE+"disActiveCombinatebill", params);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CombinateBillEntity> downloadCombillTotal()
	{
		return this.getSqlSession().selectList(NAMESPACE+"getCombinatebillTotal");
	}


	@Override
	public void insertGxgLabel(LabelPrintDto lpd) {
		lpd.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert(NAMESPACE + "insertGxgLabel", lpd);
	}
	
	
}
