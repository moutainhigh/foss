package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;

/**
 * 派件交接 提交任务后更新派送单数据DAO接口
 * @author 243921-foss-zhangtingting
 * @date 2015-04-17 下午5:03:05
 */
public interface IDeliverbillExpressTaskDao {

	/**
	 * 根据派送单号、运单号查询派送单明细
	 * @author 243921-foss-zhangtingting
	 * @date 2015-05-11 下午5:19:16
	 * @param deliverbillId 派送单Id
	 * @param waybillNo 运单号
	 * @return
	 */
	DeliverbillDetailEntity getDeliverbillDetail(String deliverbillId,String waybillNo);
	/**
	 * 更新派送单数据
	 * @author 243921-foss-zhangtingting
	 * @date 2015-04-18 下午4:42:16
	 * @param deliverbillEntity
	 * @return
	 */
	int updateDeliverbillByDeliverbillNo(DeliverbillEntity deliverbillEntity);
	/**
	 * 根据派送单明细ID更新派送单明细中的派送单ID
	 * @author 243921-foss-zhangtingting
	 * @date 2015-04-18 下午4:53:42
	 * @param deliverbillId 派送单ID
	 * @param deliverbillDetailIds 派送单明细Id集合 
	 * @return
	 */
	int updateDeliverbillDetailByCondition(String deliverbillId,List<String> deliverbillDetailIds);
}
