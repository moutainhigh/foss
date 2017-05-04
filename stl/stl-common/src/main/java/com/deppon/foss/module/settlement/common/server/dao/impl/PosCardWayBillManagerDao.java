package com.deppon.foss.module.settlement.common.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IPosCardWaybillManageDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;

/**
 * NCI项目运单更改，预付款不变
 * 
 * @ClassName: PosCardWayBillManagerDao
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-4-6 下午3:01:04
 */
public class PosCardWayBillManagerDao extends iBatis3DaoImpl implements IPosCardWaybillManageDao {
	
	public static String NAMESPACE = "foss.stl.PosCardWayBillManagerDao.";
	/**
	 * 
	 * 运单更改，代收货款预付金额不变 修改对应的明细的单据总金额 参数：单据号和总金额
	 * 
	 * @Title: updatePosCardDetail
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @date 2016-4-6 下午2:57:25
	 */
	@Override
	public int changePosCardDetail(PosCardDetailEntity entity) {
		int result = this.getSqlSession().update(NAMESPACE+"changePosCardDetail",entity);
		return result;
	}
}
