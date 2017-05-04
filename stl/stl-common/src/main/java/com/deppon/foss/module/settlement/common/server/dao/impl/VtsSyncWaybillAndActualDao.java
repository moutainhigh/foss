package com.deppon.foss.module.settlement.common.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.common.api.server.dao.IVtsSyncWaybillAndActualDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.define.FossConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * 整车项目同步运单和实际承运表数据
 * 
 * @ClassName: IVtsWaybillAndActualDao
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-5-5 下午6:42:38
 */
public class VtsSyncWaybillAndActualDao extends iBatis3DaoImpl implements
		IVtsSyncWaybillAndActualDao {
	// 命名空间
	public final static String NAMESPACE = "foss.stl.VtsWaybillAndActualDao.";

	/**
	 * 插入运单表数据
	 * 
	 * @ClassName: buildWayBillMessage
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2016-5-5 下午6:42:38
	 */
	@Override
	public int buildWayBillMessage(WaybillEntity entity) {
		return this.getSqlSession().insert(NAMESPACE + "insertWaybillMessage",
				entity);
	}

	/**
	 * 实际承运表数据
	 * 存在则更新，不存在则插入
	 * @ClassName: buildActualFreightMessage
	 * @author &269052 |zhouyuan008@deppon.com
	 * @date 2016-5-5 下午6:42:38
	 */
	@Override
	public int buildActualFreightMessage(ActualFreightEntity entity) {
		return this.getSqlSession().insert(
				NAMESPACE + "insertActualFreightMess", entity);
	}
	
	/**
	 * 插入运单更改表数据
	 * 
	 * @Title: syncWayBillMessage
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-5-5 下午6:44:15
	 */
	@Override
	public int buildWayBillRfcMessage(WaybillRfcEntity entity) {
		return this.getSqlSession().insert(
				NAMESPACE + "vtsInsertWaybillRfcMess", entity);
	}
	
	/**
	 * 查询有效的运单数据
	 * 
	 * @Title: queryWaybillEntity
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-5-25 下午2:21:51
	 */
	@Override
	public int queryWaybillEntity(String waybillNo) {
		Map<String,String> map = new HashMap<String,String>();
		/**
		 * 判空
		 */
		if(StringUtil.isEmpty(waybillNo)){
			throw new SettlementException("运单号不能为空！");
		}
		map.put("waybillNo", waybillNo);
		map.put("active", FossConstants.YES);
		int result = (Integer) this.getSqlSession().selectOne(NAMESPACE+"queryWaybillEntity", map);
		return result;
	}

	/**
	 * 同步签收结果表信息数据
	 * 
	 * @Title: syncActualFreightMessage
	 * @author： 306579 |guoxinru
	 * @date 2016-5-23
	 */
	@Override
	public int buildWaybillSignResult(WaybillSignResultEntity entity) {
		return this.getSqlSession().insert(
				NAMESPACE + "vtsInsertWaybillSignResult", entity);
	}
	/**
	 * 新增财务签收表
	 * 
	 * @Title: syncActualFreightMessage
	 * @author： 306579 |guoxinru
	 * @date 2016-5-30
	 */
	@Override
	public int insertPod(WaybillSignResultEntity entity) {
		return this.getSqlSession().insert(NAMESPACE + "insertPod", entity);
	}
	
	/**
	 * 作废运单数据
	 * @Title: queryWaybillEntity
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-5-25 下午2:21:51
	 */
	@Override
	public int cancelWaybillEntity(String waybillId) {
		Map<String,String> map = new HashMap<String,String>();
		/**
		 * 判空
		 */
		if(StringUtil.isEmpty(waybillId)){
			throw new SettlementException("运单ID不能为空！");
		}
		map.put("waybillId", waybillId);
		map.put("active", FossConstants.YES);
		int result = this.getSqlSession().update(NAMESPACE+"cancelWaybillEntity", map);
		return result;
	}
	
	/**
	 * 查询是否存在此单号未审核、未受理的更改单；
	 * @param entity
	 * @return
	 */
	@Override
	public int queryExsitWaybillRfc(WaybillRfcEntity entity) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("waybillNo", entity.getWaybillNo());
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"queryExsitWaybillRfc", map);
	}
	
	/**
	 * 更新更改单
	 * @param entity
	 * @return
	 */
	@Override
	public int updateWayBillRfcMessage(WaybillRfcEntity entity) {
		return this.getSqlSession().update(NAMESPACE+"updateWayBillRfcMessage", entity);
	}

	/**
	 * 查询该单号有效运单id
	 * @param waybillNo
	 */
	@Override
	public String queryActiveYWaybillNo(Map<String, String> map) {
		return (String) this.getSqlSession().selectOne(NAMESPACE+"queryActiveYWaybillNo", map);
	}

	/**
	 * 查询该单号最后一条无效运单id
	 * @param waybillNo
	 */
	@Override
	public String queryLastActiveNWaybillNo(Map<String, String> map) {
		return (String) this.getSqlSession().selectOne(NAMESPACE+"queryLastActiveNWaybillNo", map);
	}
	
	/**
	 * 修改运单状态
	 */
	@Override
	public int syncWaybillUpdateState(Map<String, String> map) {
		return this.getSqlSession().update(NAMESPACE+"updateWaybillState", map);
	}

	/**
	 * 根据运单号查询应付单count
	 * @param map
	 * @return
	 */
	@Override
	public int queryApcOrRfPayable(Map<String, String> map) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"queryApcOrRfPayable", map);
	}

	/**
	 * 更新代收货款应付单签收日期
	 * @param map
	 * @return
	 */
	@Override
	public int updateApcPayable(WaybillSignResultEntity entity) {
		return this.getSqlSession().update(NAMESPACE+"updateApcPayable", entity);
	}
	
	/**
	 * 更新装卸费应付单签收日期
	 * @param map
	 * @return
	 */
	@Override
	public int updateSfPayable(WaybillSignResultEntity entity) {
		return this.getSqlSession().update(NAMESPACE+"updateSfPayable", entity);
	}

	/**
	 * 根据运单号查询现金收款单count
	 * @param map
	 * @return
	 */
	@Override
	public int queryCashColl(Map<String, String> casMap) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"queryCashColl", casMap);
	}

	/**
	 * 更新现金收款单确定收入日期--签收日期
	 * @param map
	 * @return
	 */
	@Override
	public int updateCashColl(WaybillSignResultEntity entity) {
		return this.getSqlSession().update(NAMESPACE+"updateCashColl", entity);
	}

	/**
	 * 根据运单号查询应收单
	 * @param map
	 * @return
	 */
	@Override
	public int queryCrDrOrReceivable(Map<String, String> recMap) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"queryCrDrOrReceivable", recMap);
	}

	/**
	 * 更新代收货款应收单确认收入日期
	 * @param map
	 * @return
	 */
	@Override
	public int updateCrReceivable(WaybillSignResultEntity entity) {
		return this.getSqlSession().update(NAMESPACE+"updateCrReceivable", entity);
	}

	/**
	 * 更新始发提成应收单确认收入日期
	 * @param map
	 * @return
	 */
	@Override
	public int updateOrReceivable(WaybillSignResultEntity entity) {
		return this.getSqlSession().update(NAMESPACE+"updateOrReceivable", entity);
	}
	
	/**
	 * 更新到达应收单确认收入日期
	 * @param map
	 * @return
	 */
	@Override
	public int updateDrReceivable(WaybillSignResultEntity entity) {
		return this.getSqlSession().update(NAMESPACE+"updateDrReceivable", entity);
	}
	
	
}
