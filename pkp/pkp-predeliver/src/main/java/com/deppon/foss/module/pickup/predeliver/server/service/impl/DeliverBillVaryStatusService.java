package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverBillVaryStatusDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverBillVaryStatusService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverBillVaryStatusEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * @ClassName: DeliverBillVaryStatusService
 * @Description: 派送单状态更新记录表Service实现
 * @author 237982-foss-fangwenjun
 * @date 2015-5-27 下午4:11:57
 * 
 */
public class DeliverBillVaryStatusService implements IDeliverBillVaryStatusService {

	/**
	 * 派送单状态更新记录表Dao
	 */
	private IDeliverBillVaryStatusDao deliverBillVaryStatusDao;

	/**
	 * 设置deliverBillVaryStatusDao
	 * 
	 * @param deliverBillVaryStatusDao
	 *            要设置的deliverBillVaryStatusDao
	 */
	@Resource
	public void setDeliverBillVaryStatusDao(IDeliverBillVaryStatusDao deliverBillVaryStatusDao) {
		this.deliverBillVaryStatusDao = deliverBillVaryStatusDao;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverBillVaryStatusService#insertDBVaryStatus(com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverBillVaryStatusEntity)
	 */
	@Override
	public void insertDBVaryStatus(DeliverBillVaryStatusEntity deliverBillVaryStatusEntity) {
		// 判断传入对象是否为空
		if (deliverBillVaryStatusEntity == null) {
			throw new RuntimeException("传入对象不能为空");
		}
		// 判断派送单号是否为空
		if (deliverBillVaryStatusEntity.getDeliverBillNo() == null
				|| "".equals(deliverBillVaryStatusEntity.getDeliverBillNo().trim())) {
			throw new RuntimeException("派送单号不能为空");
		}
		// 判断派送单状态是否为空
		if (deliverBillVaryStatusEntity.getDeliverBillStatus()== null
				|| "".equals(deliverBillVaryStatusEntity.getDeliverBillStatus().trim())) {
			throw new RuntimeException("派送单状态不能为空");
		}
		// 设置Id
		deliverBillVaryStatusEntity.setId(UUIDUtils.getUUID());
		// 设置操作日期
		deliverBillVaryStatusEntity.setOperateDate(new Date());
		
		// 返回结果
		deliverBillVaryStatusDao.insertOne(deliverBillVaryStatusEntity);
	}

}
