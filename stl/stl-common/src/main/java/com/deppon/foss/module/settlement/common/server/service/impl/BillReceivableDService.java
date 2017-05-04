/**
 * 
 */
package com.deppon.foss.module.settlement.common.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.settlement.common.api.server.dao.IBillReceivableDEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableDService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableDEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.UUIDUtils;

/**
 * 
 * @author 黄乐为
 * @date 2016-1-9 下午4:19:42
 */
public class BillReceivableDService implements IBillReceivableDService {

	/**
	 * 日志属性
	 */
	private static final Logger logger = LogManager
			.getLogger(BillReceivableDService.class);
	
	/**
	 * 应收单dao
	 */
	private IBillReceivableDEntityDao billReceivableDEntityDao;
	
	/**
	 * 新增一条应收单明细记录
	 * @author 黄乐为
	 * @date 2016-1-9 下午4:19:42
	 * @param entity
	 * @return
	 */
	@Override
	public void add(BillReceivableDEntity entity) {
		logger.info("新增应收单明细开始");
		//判断待新增的数据是否为空
		if (entity == null) {
			//如果参数为空则抛出异常
			throw new SettlementException("新增应收单明细参数不能为空！");
		}
		//应收单号不能为空
		if(StringUtils.isBlank(entity.getReceivableNo())){
			throw new SettlementException("新增应收单明细的应收单号不能为空！");
		}
		//来源单号不能为空
		if (StringUtils.isBlank(entity.getSourceBillNo())) {
			throw new SettlementException("新增应收单明细的来源单号不能为空！");
		}
		//金额不能为空
		if (entity.getAmount() == null) {
			throw new SettlementException("新增应收单明细的金额不能为空！");
		}
		//是否有效不能为空
		if (StringUtils.isBlank(entity.getActive())) {
			throw new SettlementException("新增应收单明细的是否有效不能为空！");
		}
		//设置ID
		entity.setId(UUIDUtils.getUUID());
		//保存返回结果
		int i = billReceivableDEntityDao.add(entity);
		//如果不等于1，则说明执行不成功
		if (i != 1) {
			throw new SettlementException("生成应收单明细失败！");
		}
		logger.info("新增应收单明细结束");
	}
	
	/**
	 * 生成多条应收单明细
	 * @author 黄乐为
	 * @date 2016-1-12 上午8:21:51
	 * @param list
	 * @return
	 */
	@Override
	public void addList(List<BillReceivableDEntity> list) {
		//判断传入的应收单明细是否为空
		if (null == list || list.size() == 0) {
			throw new SettlementException("生成应收单明细不能为空！");
		}
		//循环插入应收单明细
		for (BillReceivableDEntity dEntity : list) {
			this.add(dEntity);
		}
	}

	/**
	 * 根据传入的一到多个应收单号,查询一到多条应收单明细信息
	 * @author 黄乐为
	 * @date 2016-1-9 下午4:19:43
	 * @param receivableNos
	 * @param active
	 * @return
	 */
	@Override
	public List<BillReceivableDEntity> queryByReceivableNOs(
			List<String> receivableNos, String active) {
		return billReceivableDEntityDao.queryByReceivableNOs(
				receivableNos, active);
	}

	/**
	 * 根据传入的一到多个来源单号，获取一到多条应收单明细信息
	 * @author 黄乐为
	 * @date 2016-1-9 下午4:19:43
	 * @param sourceBillNos
	 * @param active
	 * @return
	 */
	@Override
	public List<BillReceivableDEntity> queryBySourceBillNOs(
			List<String> sourceBillNos, String active) {
		return billReceivableDEntityDao.queryBySourceBillNOs(
				sourceBillNos, active);
	}

	/**
	 * 根据应收单号查询应收单详情
	 * @author 尤坤
	 * @param receivableNo
	 * @return
	 */
	@Override
	public List<BillReceivableDEntity> queryByReceivableNo(String receivableNo) {
		return billReceivableDEntityDao.queryByReceivableNo(receivableNo);
	}

	public IBillReceivableDEntityDao getBillReceivableDEntityDao() {
		return billReceivableDEntityDao;
	}

	public void setBillReceivableDEntityDao(
			IBillReceivableDEntityDao billReceivableDEntityDao) {
		this.billReceivableDEntityDao = billReceivableDEntityDao;
	}

}
