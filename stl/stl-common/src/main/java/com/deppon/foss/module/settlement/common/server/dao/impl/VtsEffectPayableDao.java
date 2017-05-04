package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillQueryArgsDto;
import com.deppon.foss.module.settlement.common.api.server.dao.IVtsEffectPayableDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.VtsWaybillFRcQueryByWaybillNoDto;
import com.deppon.foss.util.define.FossConstants;

public class VtsEffectPayableDao extends iBatis3DaoImpl implements IVtsEffectPayableDao {
	// 命名空间
	public final static String NAMESPACE = "foss.stl.VtsEffectPayableDao.";
	
	/**
	 * 传入运单号，判断传入的运单号是否存在未处理的更改单
	 * 
	 * @param waybillNo
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean isExsitsWayBillRfc(
			VtsWaybillFRcQueryByWaybillNoDto waybillFRcQueryByWaybillNoDto) {
		
		String waybillNoResult=null;
		List<String> waybills = getSqlSession().selectList(
				NAMESPACE + "vtsQueryWaybillRFcByWaybillNo",
				waybillFRcQueryByWaybillNoDto);
		if (waybills != null && waybills.size() > 0) {
			 waybillNoResult= waybills.get(0);
		}
		return waybillNoResult !=null ? true :false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public WaybillEntity queryPartWaybillByNo(String waybillNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNo", waybillNo);//运单号
		map.put("active", FossConstants.ACTIVE);//有效		
		List<WaybillEntity> list=this.getSqlSession().selectList(NAMESPACE + "vtsQueryPartWaybillByNo", map);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 通过运单编号查询第一次开单时运单信息
	 * 
	 * @param waybill
	 */
	@Override
	public WaybillEntity queryFirstWaybillByWaybillNo(String waybillNo) {
		// 封装查询条件
		WaybillQueryArgsDto argsDto = new WaybillQueryArgsDto();
		argsDto.setWaybillNo(waybillNo);

		return (WaybillEntity) this.getSqlSession().selectOne(
				NAMESPACE + "vtsSelectFirstWaybillByWaybillNo", argsDto);
	}

}
