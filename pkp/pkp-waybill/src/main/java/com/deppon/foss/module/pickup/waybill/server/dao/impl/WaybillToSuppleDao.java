package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillToSuppleDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillSupplementLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillToSuppleCondtionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillToSuppleResultDto;

public class WaybillToSuppleDao extends iBatis3DaoImpl implements IWaybillToSuppleDao {
	
	private static final String NAMESPACE = "foss.pkp.waybill.WaybillSupplementLogEntityMapper.";
	
	/**
	 * 添加待补录作废日志记录
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-12 15:14:14
	 */
	@Override
	public int addWaybillToSuppleRecord(WaybillSupplementLogEntity waybillSupplementLogEntity) {
		return this.getSqlSession().insert(NAMESPACE + "addWaybillToSuppleRecord", waybillSupplementLogEntity);
	}

	/**
	 * 查询待补录记录数据记录
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-13 18:48:34
	 * @param waybillToSuppleCondtionDto
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillToSuppleResultDto> queryWaybillToSuppleAction(WaybillToSuppleCondtionDto waybillToSuppleCondtionDto, int start, int limit) {
		RowBounds row = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillToSuppleAction", waybillToSuppleCondtionDto, row);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillToSuppleResultDto> queryWaybillToSuppleActionAll(WaybillToSuppleCondtionDto waybillToSuppleCondtionDto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillToSuppleAction", waybillToSuppleCondtionDto);
	}

	/**
	 * 查询待补录记录数据记录条数
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-13 18:48:34
	 * @param waybillToSuppleCondtionDto
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public Long queryWaybillToSuppleActionCount(WaybillToSuppleCondtionDto waybillToSuppleCondtionDto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"queryWaybillToSuppleActionCount", waybillToSuppleCondtionDto);
	}

	/**
	 * 调用存储过程
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-13 18:49:41
	 * @param waybillNo
	 * @param beginDate
	 * @param endDate
	 */
	@Override
	public void renewWaybillByProcedure(String waybillNo, Date beginDate, Date endDate, String claimPay) {
		Map<Object,Object> map=new HashMap<Object, Object>();
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		map.put("waybillNo", waybillNo);
		map.put("claimPay", claimPay);
		this.getSqlSession().selectOne(NAMESPACE+"renewWaybillByProcedure", map);
	}
}
