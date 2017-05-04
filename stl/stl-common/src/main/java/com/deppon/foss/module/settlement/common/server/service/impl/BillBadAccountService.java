package com.deppon.foss.module.settlement.common.server.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.settlement.common.api.server.dao.IBillBadAccountEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillBadAccountService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillBadAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 坏账核销实现类
 * 
 * @author foss-wangxuemin
 * @date Dec 4, 2012 3:55:41 PM
 */
public class BillBadAccountService implements IBillBadAccountService {

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(BillBadAccountService.class);

	/**
	 * 坏账实体Dao
	 */
	private IBillBadAccountEntityDao billBadAccountEntityDao;

	/**
	 * 生成坏账记录
	 * 
	 * @author foss-wangxuemin
	 * @date Dec 4, 2012 3:56:09 PM
	 * @see com.deppon.foss.module.settlement.IBillBadAccountService.api.server.service.IBillBadAccountWriteoffService#add()
	 */
	public void add(BillBadAccountEntity entity) {
		int addRow = billBadAccountEntityDao.add(entity);
		if (addRow != 1) {
			throw new SettlementException("新增坏账失败");
		}
	}

	/**
	 * 查询待坏账处理的应收单
	 * 
	 * @author foss-wangxuemin
	 * @date Dec 4, 2012 3:56:09 PM
	 * @see com.deppon.foss.module.settlement.IBillBadAccountService.api.server.service.IBillBadAccountWriteoffService#queryBadAccountReceiableList(java.lang.String)
	 */
	@Override
	public List<BillReceivableEntity> queryBadAccountReceiableList(
			String customerCode) {
		logger.debug(customerCode);
		return null;
	}

	/**
	 * 根据运单号查询是否存在坏账信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-24 上午11:54:16
	 * @param waybillNo
	 * @return
	 */
	@Override
	public int queryByWaybillNO(String waybillNo) {
		if (StringUtils.isEmpty(waybillNo)) {
			throw new SettlementException("根据运单号查询坏账信息参数不能为空");
		}
		return this.billBadAccountEntityDao.queryByWaybillNO(waybillNo);
	}
	
	@Override
	public List<BillBadAccountEntity> queryByWaybillNOs(List<String> waybillNos){
		if(CollectionUtils.isEmpty(waybillNos)){
			throw new SettlementException("根据运单号查询坏账信息参数不能为空");
		}
		return this.billBadAccountEntityDao.queryByWaybillNOs(waybillNos);
	}


	/**
	 * @return billBadAccountEntityDao
	 */
	public IBillBadAccountEntityDao getBillBadAccountEntityDao() {
		return billBadAccountEntityDao;
	}

	/**
	 * @param billBadAccountEntityDao
	 */
	public void setBillBadAccountEntityDao(
			IBillBadAccountEntityDao billBadAccountEntityDao) {
		this.billBadAccountEntityDao = billBadAccountEntityDao;
	}
	
}
