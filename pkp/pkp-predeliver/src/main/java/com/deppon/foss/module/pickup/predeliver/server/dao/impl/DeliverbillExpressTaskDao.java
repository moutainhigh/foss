package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillExpressTaskDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;

/**
 * 快递派件交接 提交任务后更新派送单数据
 * @author 243921-foss-zhangtingting
 * @date 2015-04-18 上午8:54:20
 */
@SuppressWarnings("unchecked")
public class DeliverbillExpressTaskDao extends iBatis3DaoImpl implements IDeliverbillExpressTaskDao {

	//派送单明细 name space
	private static final String DELIVERBILL_DETAIL_NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDetailDao";
	//派件交接任务 name space
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillExpressTaskDao";

	/**
	 * 根据派送单号、运单号查询派送单明细
	 * @author 243921-foss-zhangtingting
	 * @date 2015-05-11 下午5:20:36
	 * @param deliverbillId 派送单Id
	 * @param waybillNo 运单号
	 * @return
	 */
	@Override
	public DeliverbillDetailEntity getDeliverbillDetail(String deliverbillId, String waybillNo) {
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("deliverbillId",deliverbillId);
		condition.put("waybillNo",waybillNo);
		return (DeliverbillDetailEntity)this.getSqlSession().selectOne(DELIVERBILL_DETAIL_NAMESPACE + ".queryDeliverbillDetailByMap",condition);
	}

	/**
	 * 更新派送单数据
	 * @author 243921-foss-zhangtingting
	 * @date 2015-04-18 下午5:12:26
	 * @param deliverbillEntity
	 * @return
	 */
	@Override
	public int updateDeliverbillByDeliverbillNo(DeliverbillEntity deliverbillEntity) {
		//添加时间字段，派送单修改时更新该时间字段
		deliverbillEntity.setModifyTime(new Date());
		return this.getSqlSession().update(NAMESPACE + ".updateDeliverbillByNo", deliverbillEntity);
	}

	/**
	 * 根据派送单明细ID更新派送单明细中的派送单ID
	 * @author 243921-foss-zhangtingting
	 * @date 2015-04-20 下午4:38:42
	 * @param deliverbillId 派送单ID
	 * @param deliverbillDetailIds 派送单明细ID列表
	 * @return
	 */
	@Override
	public int updateDeliverbillDetailByCondition(String deliverbillId,
			List<String> deliverbillDetailIds) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("deliverbillId", deliverbillId);
		condition.put("deliverbillDetailIds", deliverbillDetailIds);
		condition.put("modifyTime", new Date());
		return this.getSqlSession().update(NAMESPACE + ".updateDeliverbillDetailByPrimaryKey", condition);
	}
	
}
