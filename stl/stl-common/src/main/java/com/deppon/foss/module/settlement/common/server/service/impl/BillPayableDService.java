/**
 * 
 */
package com.deppon.foss.module.settlement.common.server.service.impl;

import com.deppon.foss.module.settlement.common.api.server.dao.IBillPayableDEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableDService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableDEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.UUIDUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

/**
 *应付单明细Service
 * @author hemingyu
 * @date 2016-01-21 16:07:21
 */
public class BillPayableDService implements IBillPayableDService {

	/**
	 * 日志属性
	 */
	private static final Logger logger = LogManager
			.getLogger(BillPayableDService.class);
	
	/**
	 * 应付单dao
	 */
	private IBillPayableDEntityDao billPayableDEntityDao;
	
	/**
	 * 新增一条应付单明细记录
     * @author hemingyu
     * @date 2016-01-21 16:07:21
	 * @param entity
	 * @return
	 */
	@Override
	public void add(BillPayableDEntity entity) {
		logger.info("新增应付单明细开始");
		//判断待新增的数据是否为空
		if (entity == null 
				//应付单号
				|| StringUtils.isBlank(entity.getPayableNo())
				//来源单号		
				|| StringUtils.isBlank(entity.getSourceBillNo())
				//金额
				|| entity.getAmount() == null
				//是否有效
				|| StringUtils.isBlank(entity.getActive())
				) {
			//如果参数为空则抛出异常
			throw new SettlementException("新增应付单明细参数不能为空！");
		}
		//设置ID
		entity.setId(UUIDUtils.getUUID());
		//保存返回结果
		int i = billPayableDEntityDao.add(entity);
		//如果不等于1，则说明执行不成功
		if (i != 1) {
			throw new SettlementException("生成应付单明细失败！");
		}
		logger.info("新增应付单明细结束");
	}
	
	/**
	 * 生成多条应付单明细
     * @author hemingyu
     * @date 2016-01-21 16:07:21
	 * @param list
	 * @return
	 */
	@Override
	public void addList(List<BillPayableDEntity> list) {
		//判断传入的应付单明细是否为空,为空就不生成
		if (CollectionUtils.isNotEmpty(list)) {
			//循环插入应付单明细
			for (BillPayableDEntity dEntity : list) {
				this.add(dEntity);
			}
		}
	}

	/**
	 * 根据传入的一到多个应付单号,查询一到多条应付单明细信息
     * @author hemingyu
     * @date 2016-01-21 16:07:21
	 * @param payableNos
	 * @param active
	 * @return
	 */
	public List<BillPayableDEntity> queryByPayableNOs(
			List<String> payableNos, String active) {
        //判断传入的应付单编码和状态是否为空
        if (CollectionUtils.isEmpty(payableNos) || StringUtils.isEmpty(active)) {
            throw new SettlementException("应付单编码和状态不能为空！");
        }
		return billPayableDEntityDao.queryByPayableNOs(
				payableNos, active);
	}

	/**
	 * 根据传入的一到多个来源单号，获取一到多条应付单明细信息
     * @author hemingyu
     * @date 2016-01-21 16:07:21
	 * @param sourceBillNos
	 * @param active
	 * @return
	 */
	@Override
	public List<BillPayableDEntity> queryBySourceBillNOs(
			List<String> sourceBillNos, String active) {
        //判断传入的应付单来源单号和状态是否为空
        if (CollectionUtils.isEmpty(sourceBillNos) || StringUtils.isEmpty(active)) {
            throw new SettlementException("应付单来源单号和状态不能为空！");
        }
		return billPayableDEntityDao.queryBySourceBillNOs(
				sourceBillNos, active);
	}

	public IBillPayableDEntityDao getBillPayableDEntityDao() {
		return billPayableDEntityDao;
	}

	public void setBillPayableDEntityDao(
			IBillPayableDEntityDao billPayableDEntityDao) {
		this.billPayableDEntityDao = billPayableDEntityDao;
	}

}
