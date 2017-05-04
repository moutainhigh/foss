package com.deppon.foss.module.settlement.common.server.service.impl;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.common.api.server.dao.IDopBillPayableReceivableEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IDopBillPayableReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.DopBillEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 家装应收应付单服务
 * 
 * @ClassName: DopPayableAndReceiveService
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-11-30 上午10:43:17
 * 
 */
public class DopBillPayableReceivableService implements
		IDopBillPayableReceivableService {
	private Logger logger = LogManager
			.getLogger(DopBillPayableReceivableService.class);

	// 注入Dao
	private IDopBillPayableReceivableEntityDao dopPayRecDao;

	/**
	 * 新增家装应收，应付
	 */
	@Transactional
	@Override
	public int addDopPayAndRec(List<DopBillEntity> list) {
		logger.info("*********************新增家装应收单应付单Service开始**************");
		// 更新应收单的条数
		int payRow = 0;
		int recRow = 0;
		// 判断
		if (list == null) {
			throw new SettlementException("数据错误:DOP参数为空");
		}
		// 只处理单条数据处理
		if(list.size()>1){
			throw new SettlementException("数据错误：DOP参数长度大于1！");
		}
		// 获取所属子公司名称和子公司编码
		List<DopBillEntity> subComs = dopPayRecDao.getSubCompanyNameAndCode(list);
		// 循环处理
		for (int i = 0; i < list.size(); i++) {
			// 获取DOP传入实体对象
			DopBillEntity dopBillEntity = list.get(i);
			// 获取有供应商编码和名称的实体对象
			DopBillEntity comEntity = subComs.get(i);
			if(subComs.size() == 0){
				throw new SettlementException("数据错误:所属子公司不存在！");
			}
			if (subComs.size() != list.size()) {
				throw new SettlementException("数据错误:所属子公司不唯一！");
			}
			// 返回信息值
			// 获取应收明细和应付明细
			String payDetail = dopBillEntity.getPayDetail();
			String recDetail = dopBillEntity.getRecDetail();
			// 校验备注信息
			if (payDetail != null) {
				if (payDetail.getBytes().length > SettlementConstants.MAX_LIST_SIZE) {
					throw new SettlementException("数据错误：应付明细过长不可超过1000个字节");
				}
			}
			if (recDetail != null) {
				if (recDetail.getBytes().length > SettlementConstants.MAX_LIST_SIZE) {
					throw new SettlementException("数据错误：应收明细过长不可超过1000个字节");
				}
			}

			// 校验运单号
			if (StringUtil.isBlank(dopBillEntity.getWayBillNo())) {
				throw new SettlementException("数据错误：DOP运单号字段为空。");
			}

			// 判断部门编码是否为空
			if (StringUtil.isBlank(dopBillEntity.getOrgCode())) {
				throw new SettlementException("数据错误：DOP参数部门编码字段为空。");
			}
			// 判断部门名称是否为空
			if (StringUtil.isBlank(dopBillEntity.getOrgName())) {
				throw new SettlementException("数据错误：DOP参数部门名称字段为空。");
			}

			// 判断所属子公司是否为空
			if (StringUtil.isBlank(comEntity.getSubCompanyName())) {
				throw new SettlementException("数据错误：所属子公司字段为空");
			}
			// 设置所属子公司
			dopBillEntity.setSubCompanyName(comEntity.getSubCompanyName());

			// 判断所属子公司编码是否为空
			if (StringUtil.isBlank(comEntity.getSubCompanyCode())) {
				throw new SettlementException("数据错误：所属子公司编码为空");
			}
			// 判断金额
			if (dopBillEntity.getPayAmount().compareTo(BigDecimal.valueOf(0)) == 0
					&& dopBillEntity.getRecAmount().compareTo(
							BigDecimal.valueOf(0)) == 0) {
				throw new SettlementException("数据错误：应收金额和应付金额不能同时为空！");
			}
			// 设置所属子公司编码
			dopBillEntity.setSubCompanyCode(comEntity.getSubCompanyCode());

			// 判断应收金额是否大于零
			if (dopBillEntity.getRecAmount().compareTo(BigDecimal.valueOf(0)) > 0) {
				// 判断是否生成了家装应收单
				List<DopBillEntity> lists = dopPayRecDao
						.judgeReceive(dopBillEntity);
				if (!CollectionUtils.isEmpty(lists) && lists.size() > 0) {
					throw new SettlementException("已经生成应收单，不能重复生成！");
				}
				// 生成家装应收单
				recRow = dopPayRecDao.insertDopReceiveEntity(dopBillEntity)+ recRow;
				// 判断应付金额是否大于零
			}
			if (dopBillEntity.getPayAmount().compareTo(BigDecimal.valueOf(0)) > 0) {
				// 判断是否生成了家装应付单
				List<DopBillEntity> lists = dopPayRecDao.judgePayable(dopBillEntity);
				if (!CollectionUtils.isEmpty(lists) && lists.size() > 0) {
					throw new SettlementException("已经生成应付单,不能重复生成！");
				}
				// 生成家装应付单
				payRow = dopPayRecDao.insertDopPayableEntity(dopBillEntity)+ payRow;
			}
		}
		logger.info("*********************新增家装应收单应付单Service结束**************");
		// 返回生成的条数
		return payRow + recRow;
	}

	/**
	 * 家装应收单应付单反审核
	 */
	@Transactional
	@Override
	public int cacleDopPayAndRec(List<DopBillEntity> list) {
		int rowUpdatePay = 0;
		int rowUpdateRec = 0;
		int rowInsertPay = 0;
		int rowInsertRec = 0;
		// 判断数据是否存在
		if (list == null) {
			throw new SettlementException("数据错误：DOP参数为空！");
		}
		// 只处理单条数据处理
		if(list.size()>1){
			throw new SettlementException("数据错误：DOP参数长度大于1！");
		}
		// 查询是否已经反审核
		List<DopBillEntity> dopEntitys = dopPayRecDao.queryDopPayAndRec(list);
		if (CollectionUtils.isEmpty(dopEntitys) || dopEntitys.size() == 0) {
			throw new SettlementException("已经反审核过的家装应收应付，不能重复反审核！");
		}
		// 循环处理
		for (DopBillEntity dopBillEntity : dopEntitys) {
			// 判断是否进入对账单,对账单号开始为"N/A"
			if (!"N/A".equals(dopBillEntity.getStatementNo())) {
				throw new SettlementException("数据错误：已经进入对账单的应付单应收单的数据不能反审核！");
			}
			// 判断是否已被核销
			if (dopBillEntity.getVerifyAmount()
					.compareTo(BigDecimal.valueOf(0)) > 0) {
				throw new SettlementException("数据错误：已经被核销的应付单应收单不能反审核！");
			}
		}

		// 插入应付红冲单
		rowInsertPay = dopPayRecDao.insetDopRedPayable(list);
		// 插入应收红冲单
		rowInsertRec = dopPayRecDao.insetDopRedReceive(list);
		// 反审核应付单
		rowUpdatePay = dopPayRecDao.updateDopPayable(list);
		// 反审核应收单
		rowUpdateRec = dopPayRecDao.updateDopReceive(list);
		// 判断插入条数和更新条数是否一致
		if (rowUpdatePay + rowUpdateRec != rowInsertPay + rowInsertRec) {
			throw new SettlementException("已经反审核的应付单应收单不能重复反审核！");
		}
		// 返回更新条数
		return rowUpdatePay + rowUpdateRec;
	}

	/***** getter/setter **********/
	public IDopBillPayableReceivableEntityDao getDopPayRecDao() {
		return dopPayRecDao;
	}

	public void setDopPayRecDao(IDopBillPayableReceivableEntityDao dopPayRecDao) {
		this.dopPayRecDao = dopPayRecDao;
	}

}
