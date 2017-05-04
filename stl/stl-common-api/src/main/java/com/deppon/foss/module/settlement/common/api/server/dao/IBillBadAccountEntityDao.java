package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillBadAccountEntity;

/**
 * 坏账单Dao接口类
 * 
 * @author foss-wangxuemin
 * @date Dec 5, 2012 10:06:51 AM
 */
public interface IBillBadAccountEntityDao {

	/**
	 * 新增坏账单
	 * 
	 * @author foss-wangxuemin
	 * @date Dec 5, 2012 10:07:24 AM
	 * @param entity
	 *            坏账单
	 * @return
	 */
	int add(BillBadAccountEntity entity);

	/**
	 * 根据运单号查询是否存在坏账信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-24 上午11:51:20
	 * @param waybillNo
	 *            运单
	 * @return
	 */
	int queryByWaybillNO(String waybillNo);
	
	/**
	 * 根据运单号集合查询坏账信息
	 * @author 198704 weitao
	 * @date Oct 4, 2014 4:30 pm
	 * @param waybillNos
	 * @return List<BillBadAccountEntity>
	 */
	List<BillBadAccountEntity> queryByWaybillNOs(List<String> waybillNos);
}
