/**
 * company : com.deppon poroject : foss结算 copyright : copyright (c) 2016
 * 
 * @description: TODO
 * @author : panshiqi (309613)
 * @date : 2016年2月18日 下午8:11:07
 * @version : v1.0
 */
package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IWSCWayBillManageDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillParamEntity;

/**
 * 
* @description: 待刷卡运单管理数据层接口实现
* @className: WSCWayBillManageDao
* 
* @authorCode 309613
* @date 2016年2月18日 下午8:11:15 
*
 */
public class WSCWayBillManageDao extends iBatis3DaoImpl implements IWSCWayBillManageDao {

	/**
	 * SQL Mapper namespace
	 */
	public static final String NAMESPACES = "foss.common.WSCWayBillManageDao.";

	//----------------------------------------待刷卡相关操作------------------------------------------

	/**
	 * 添加代刷卡运单数据
	 */
	@Override
	public int addWSCWayBill(WSCWayBillParamEntity params) throws Exception {
		return getSqlSession().insert(NAMESPACES.concat("addWSCWayBill"), params);
	}

	/**
	 * 运单更改时更新待刷卡运单数据, 注:sql中并非全字段更新,当前仅根据业务更新部分字段.
	 */
	@Override
	public int updateWSCWayBillByItemID(WSCWayBillParamEntity params) throws Exception {
		return getSqlSession().update(NAMESPACES.concat("updateWSCWayBillByItemID"), params);
	}

	/**
	 * 根据运单号查询有效的待刷卡数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WSCWayBillEntity> getWayBillsByWayBillNo(String wayBillNo, String wayBillSource) throws Exception {
		// 整合查询参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("wayBillNo", wayBillNo);
		params.put("wayBillSource", wayBillSource);
		return getSqlSession().selectList(NAMESPACES.concat("getWayBillsByWayBillNo"), params);
	}

	/**
	 * 获取运单号下最新的有效待刷卡运单数据
	 */
	@Override
	public WSCWayBillEntity getLastActiveDataByWayBillNo(String wayBillNo) throws Exception {
		return (WSCWayBillEntity) getSqlSession().selectOne(NAMESPACES.concat("getLastActiveDataByWayBillNo"), wayBillNo);
	}

	/**
	 * 根据部门编号查询部门下有效&未支付待刷卡运单数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WSCWayBillEntity> getWayBillListByOrgCode(String orgCode, String... wayBillNo) throws Exception {
		// 整合查询参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("orgCode", orgCode);
		if (wayBillNo != null && wayBillNo.length > 0) {
			params.put("wayBillNo", wayBillNo[0]);
		}
		return getSqlSession().selectList(NAMESPACES.concat("getWayBillListByOrgCode"), params);
	}

	/**
	 * 根据数据编号查询待刷卡运单数据
	 */
	@Override
	public WSCWayBillEntity getWayBillInfoByWSCItemId(String id) throws Exception {
		return (WSCWayBillEntity) getSqlSession().selectOne(NAMESPACES.concat("getWayBillInfoByWSCItemId"), id);
	}

	/**
	 * 根据ItemId修改待刷卡运单数据为无效状态
	 */
	@Override
	public int invalidWSCWayBillByItemId(WSCWayBillParamEntity params) throws Exception {
		return getSqlSession().update(NAMESPACES.concat("invalidWSCWayBillByItemId"), params);
	}

	/**
	 * 根据运单号修改待刷卡运单数据为无效状态
	 */
	@Override
	public int invalidWSCWayBillByWayBillNo(WSCWayBillParamEntity params) throws Exception {
		return getSqlSession().update(NAMESPACES.concat("invalidWSCWayBillByWayBillNo"), params);
	}

	/**
	 * 更新运单下未支付待刷卡数据为无效
	 */
	@Override
	public int invalidUnPayMentWSCDataByWayBillNo(WSCWayBillParamEntity params) throws Exception {
		return getSqlSession().update(NAMESPACES.concat("invalidUnPayMentWSCDataByWayBillNo"), params);
	}

	/**
	 * 记录刷卡结果
	 */
	@Override
	public int recordSwipeCardResult(WSCWayBillEntity params) throws Exception {
		return getSqlSession().insert(NAMESPACES.concat("addWSCWayBill"), params);
	}

	/**
	 * 同步运单字段变化到待刷卡
	 */
	@Override
	public int synchBillInfo2Wsc(WSCWayBillParamEntity params) throws Exception {
		return getSqlSession().update(NAMESPACES.concat("synchBillInfo2Wsc"), params);
	}

	/**
	 * 根据运单号获取运单有效刷卡记录次数
	 */
	@Override
	public int getSwipedCountByWayBillNo(String wayBillNo) throws Exception {
		return (Integer) getSqlSession().selectOne(NAMESPACES.concat("getSwipedCountByWayBillNo"), wayBillNo);
	}

	//----------------------------------------T+0相关操作------------------------------------------

	/**
	 * 已刷卡运单更改，金额增加时，修改运单的T+0明细数据
	 */
	@Override
	public int updateT0DetailWhenBillAmountAdd(PosCardDetailEntity params) throws Exception {
		return getSqlSession().update(NAMESPACES.concat("updateT0DetailWhenBillAmountAdd"), params);
	}

	/**
	 * 根据运单号获取所有T+0明细数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PosCardDetailEntity> queryT0DetailByWayBillNo(String wayBillNo, String invoiceType) throws Exception {
		// 整合查询参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("invoiceNo", wayBillNo);
		params.put("invoiceType", invoiceType);
		return getSqlSession().selectList(NAMESPACES.concat("queryT0DetailByWayBillNo"), params);
	}

	/**
	 * 根据交易流水号获取T+0数据
	 */
	@Override
	public PosCardEntity queryT0BySerialNo(String serialNo) throws Exception {
		return (PosCardEntity) getSqlSession().selectOne(NAMESPACES.concat("queryT0BySerialNo"), serialNo);
	}

	/**
	 * 根据ID置T0明细数据为无效
	 */
	@Override
	public int invalidT0DetailByID(String id) throws Exception {
		return getSqlSession().update(NAMESPACES.concat("invalidT0DetailByID"), id);
	}

	/**
	 * 释放T0明细表刷卡金额到T0报表
	 */
	@Override
	public int releaseDetailAmount2T0(PosCardEntity params) throws Exception {
		return getSqlSession().update(NAMESPACES.concat("releaseDetailAmount2T0"), params);
	}

	/**
	 * 根据ID置T0明细数据为无效(批量)
	 */
	@Override
	public int invalidT0DetailByIDBatch(List<String> idList) throws Exception {
		return getSqlSession().update(NAMESPACES.concat("invalidT0DetailByIDBatch"), idList);
	}

	/**
	 * 根据ID置T0明细数据本次刷卡金额为0(批量)
	 */
	@Override
	public int resetT0DetailOccAmountByIDBatch(List<String> idList) throws Exception {
		return getSqlSession().update(NAMESPACES.concat("resetT0DetailOccAmountByIDBatch"), idList);
	}

	/**
	 * 释放T0明细表刷卡金额到T0报表(批量)
	 */
	@Override
	public int releaseDetailAmount2T0Batch(Map<String, PosCardEntity> paramsMap) throws Exception {
		Map<String, Object> parmas = new HashMap<String, Object>();
		parmas.put("paramsMap", paramsMap);
		return getSqlSession().update(NAMESPACES.concat("releaseDetailAmount2T0Batch"), parmas);
	}

	/**
	 * 更新T+0明细数据(批量)
	 */
	@Override
	public int updateT0DetailBatch(List<PosCardDetailEntity> t0DetailEntityList) throws Exception {
		return getSqlSession().update(NAMESPACES.concat("updateT0DetailBatch"), t0DetailEntityList);
	}

	/**
	 * 更新T+0报表数据的相关金额
	 */
	@Override
	public int updateT0Amount(PosCardEntity t0) throws Exception {
		return getSqlSession().update(NAMESPACES.concat("updateT0Amount"), t0);
	}

	/**
	 * 更新T+0明细数据的相关金额
	 */
	@Override
	public int updateT0DetailAmount(PosCardDetailEntity t0Detail) throws Exception {
		return getSqlSession().update(NAMESPACES.concat("updateT0DetailAmount"), t0Detail);
	}

	/**
	 * 根据运单号和交易流水号获取T+0明细数据
	 */
	@Override
	public PosCardDetailEntity queryT0DetailByWayBillNoAndSerialNo(String wayBillNo, String serialNo, String invoiceType) throws Exception {
		// 整合查询参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("invoiceNo", wayBillNo);
		params.put("invoiceType", invoiceType);
		params.put("tradeSerialNo", serialNo);
		return (PosCardDetailEntity) getSqlSession().selectOne(NAMESPACES.concat("queryT0DetailByWayBillNoAndSerialNo"), params);
	}

	/**
	 *  获取T+0明细表中运单有效已刷卡金额总和
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, BigDecimal> getBillAmountAndSumOccupateAmount(String wayBillNo, String invoiceType) throws Exception {
		// 整合查询参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("invoiceNo", wayBillNo);
		params.put("invoiceType", invoiceType);
		return (Map<String, BigDecimal>) getSqlSession().selectOne(NAMESPACES.concat("getBillAmountAndSumOccupateAmount"), params);
	}

	/**
	 * 同步运单字段变化到T+0明细
	 */
	@Override
	public int synchBillInfo2T0(PosCardDetailEntity t0Detail) throws Exception {
		return getSqlSession().update(NAMESPACES.concat("synchBillInfo2T0"), t0Detail);
	}

}
