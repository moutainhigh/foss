package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.Map;

import com.deppon.foss.module.settlement.common.api.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillSignResultEntity;

/**
 * 整车项目同步运单和实际承运表数据
 * 
 * @ClassName: IVtsWaybillAndActualDao
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-5-5 下午6:42:38
 */
public interface IVtsSyncWaybillAndActualDao {
	/**
	 * 插入运单数据
	 * 
	 * @Title: syncWayBillMessage
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-5-5 下午6:44:15
	 */
	int buildWayBillMessage(WaybillEntity entity);

	/**
	 * 插入实际承运表数据
	 * 
	 * @Title: syncWayBillMessage
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-5-5 下午6:44:15
	 */
	int buildActualFreightMessage(ActualFreightEntity entity);

	/**
	 * 插入运单更改表数据
	 * 
	 * @Title: syncWayBillMessage
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-5-5 下午6:44:15
	 */
	int buildWayBillRfcMessage(WaybillRfcEntity entity);

	/**
	 * 同步签收结果表信息数据
	 * 
	 * @Title: syncActualFreightMessage
	 * @author： 306579 |guoxinru
	 * @date 2016-5-23
	 */
	int buildWaybillSignResult(WaybillSignResultEntity entity);

	/**
	 * 新增财务签收表
	 * 
	 * @Title: syncActualFreightMessage
	 * @author： 306579 |guoxinru
	 * @date 2016-5-30
	 */
	int insertPod(WaybillSignResultEntity entity);
	

	/**
	 * 查询有效的运单数据
	 * 
	 * @Title: queryWaybillEntity
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-5-25 下午2:21:51
	 */
	int queryWaybillEntity(String waybillNo);

	/**
	 * 作废运单数据
	 * 
	 * @Title: cancelWaybillEntity
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-5-25 下午2:49:46
	 */
	int cancelWaybillEntity(String waybillId);

	/**
	 * 查询是否存在此单号未审核、未受理的更改单；
	 * @param entity
	 * @return
	 */
	int queryExsitWaybillRfc(WaybillRfcEntity entity);

	/**
	 * 更新更改单
	 * @param entity
	 * @return
	 */
	int updateWayBillRfcMessage(WaybillRfcEntity entity);

	/**
	 * 查询该单号有效运单id
	 * @param waybillNo
	 */
	String queryActiveYWaybillNo(Map<String, String> map);

	/**
	 * 查询该单号最后一条无效运单id
	 * @param waybillNo
	 */
	String queryLastActiveNWaybillNo(Map<String, String> map);

	/**
	 * 同步更改运单状态
	 * @author 339073|guojing
	 * @date 2016-6-15
	 */
	int syncWaybillUpdateState(Map<String, String> map);

	/**
	 * 根据运单号查询应付单count
	 * @param map
	 * @return
	 */
	int queryApcOrRfPayable(Map<String, String> map);

	/**
	 * 更新代收货款应付单签收日期
	 * @param map
	 * @return
	 */
	int updateApcPayable(WaybillSignResultEntity entity);
	
	/**
	 * 更新装卸费应付单签收日期
	 * @param map
	 * @return
	 */
	int updateSfPayable(WaybillSignResultEntity entity);

	/**
	 * 根据运单号查询现金收款单count
	 * @param map
	 * @return
	 */
	int queryCashColl(Map<String, String> casMap);

	/**
	 * 更新现金收款单确定收入日期--签收日期
	 * @param map
	 * @return
	 */
	int updateCashColl(WaybillSignResultEntity entity);

	/**
	 * 根据运单号查询应收单
	 * @param map
	 * @return
	 */
	int queryCrDrOrReceivable(Map<String, String> recMap);

	/**
	 * 更新代收货款应收单确认收入日期
	 * @param map
	 * @return
	 */
	int updateCrReceivable(WaybillSignResultEntity entity);

	/**
	 * 更新始发提成应收单确认收入日期
	 * @param map
	 * @return
	 */
	int updateOrReceivable(WaybillSignResultEntity entity);

	/**
	 * 更新到达应收单确认收入日期
	 * @param map
	 * @return
	 */
	int updateDrReceivable(WaybillSignResultEntity entity);
}
