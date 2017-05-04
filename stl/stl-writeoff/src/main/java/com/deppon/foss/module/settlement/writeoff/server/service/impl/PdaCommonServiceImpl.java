package com.deppon.foss.module.settlement.writeoff.server.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IPdaStatementManageDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IPdaCommonService;

/**
 * 修改POS数据
 * 
 * @ClassName: PdaCommonServiceImpl
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-3-3 下午6:40:15
 */
public class PdaCommonServiceImpl implements IPdaCommonService {
	// 返回默认值
	private static final String isSuccess = "1";
	/**
	 * Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(PdaCommonServiceImpl.class);

	/**
	 * 注入Dao
	 */
	private IPdaStatementManageDao pdaStatementManageDao;
	
	/**
	 * 单条更新(根据单据号和交易流水号去更新明细交易流水号金额和未核销金额)
	 * 
	 * @Title: updatePosCardMessage
	 * @author： 269052 |zhouyuan008@deppon.com
	 * @param entity :明细数据
	 */
	@Override
	public String updateSinglePosCardDetail(PosCardDetailEntity entity) {
		logger.info("******POS管理，更新明细数据开始********");
		if (entity == null) {
			throw new SettlementException("数据错误：传入参数为空！");
		}
		// 单据号不能为空
		if (StringUtil.isBlank(entity.getInvoiceNo())) {
			throw new SettlementException("数据错误：单据号不能为空！");
		}
		// 交易流水号不能为空
		if(StringUtil.isBlank(entity.getTradeSerialNo())){
			throw new SettlementException("数据错误：交易流水号不能为空！");
		}
		// 交易流水号金额不能为null
		if (entity.getOccupateAmount() == null) {
			throw new SettlementException("数据错误：该单据本次刷卡金额不能为null！");
		}
		// 更新数据
		pdaStatementManageDao.updateSinglePosCardDetail(entity);
		logger.info("******POS管理，更新明细数据结束********");
		return isSuccess;
	}

	/**
	 * 根据交易流水号去更新T+0
	 * 
	 * @Title: updatePosCardByNumber
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@Override
	public String updatePosCardByNumber(PosCardDetailEntity entity) {
		logger.info("*******根据交易流水号去更新T+0报表开始*********");
		if (entity == null) {
			throw new SettlementException("数据错误：传入参数为空！");
		}
		// 交易流水号不能为空
		if (StringUtil.isBlank(entity.getTradeSerialNo())) {
			throw new SettlementException("数据错误：交易流水号不能为空！");
		}
		// 交易流水号金额不能为null
		if (entity.getOccupateAmount() == null) {
			throw new SettlementException("数据错误：交易流水号金额不能为null！");
		}
		// 更新数据
		logger.info("*******根据交易流水号去更新T+0报表结束*********");
		pdaStatementManageDao.updatePosCardByNumber(entity);
		return isSuccess;
	}

	public void setPdaStatementManageDao(
			IPdaStatementManageDao pdaStatementManageDao) {
		this.pdaStatementManageDao = pdaStatementManageDao;
	}
}
