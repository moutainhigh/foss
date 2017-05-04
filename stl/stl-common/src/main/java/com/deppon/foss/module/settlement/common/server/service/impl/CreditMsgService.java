package com.deppon.foss.module.settlement.common.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.settlement.common.api.server.dao.ICreditMsgEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.ICreditCustomerService;
import com.deppon.foss.module.settlement.common.api.server.service.ICreditMsgService;
import com.deppon.foss.module.settlement.common.api.server.service.ICreditOrgService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.CreditMsgEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CreditMsgDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 财务收支平衡消息 Service
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-1-18 上午10:56:46
 * @since
 * @version
 */
public class CreditMsgService implements ICreditMsgService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CreditMsgService.class);

	/**
	 * 财务收支平衡消息 Dao
	 */
	private ICreditMsgEntityDao creditMsgEntityDao;

	/**
	 * 组织欠款额度管理服务
	 */
	private ICreditOrgService creditOrgService;

	/**
	 * 客户信用额度服务
	 */
	private ICreditCustomerService creditCustomerService;

	/**
	 * 新增财务收支平衡消息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-18 上午11:01:21
	 * @param entity
	 */
	@Override
	public void add(CreditMsgEntity entity) {
		if (entity == null || entity.getAmount() == null
				|| entity.getCode() == null || entity.getType() == null
				|| entity.getCreditType() == null) {
			throw new SettlementException("新增财务收支平衡表消息参数不能为空");
		}
		LOGGER.info(" start 新增财务收支平衡消息--------- ");
		int i = this.creditMsgEntityDao.add(entity);
		if (i != 1) {
			throw new SettlementException("新增财务收支平衡表消息失败！");
		}
		LOGGER.info(" end 新增财务收支平衡消息--------- ");
	}

	/**
	 * 批量修改财务收支平衡消息的状态为已执行
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-18 上午11:44:14
	 * @param dto
	 */
	@Override
	public void batchUpdateCreditMsgForStatus(CreditMsgDto dto) {
		
		if (dto == null || CollectionUtils.isEmpty(dto.getList())) {
			throw new SettlementException("批量修改财务收支平衡消息参数不能为空！");
		}
		
		LOGGER.info(" start 批量修改财务收支平衡消息--状态 ---------");


		// 字义总条数和更新的条数
		int totalSize = dto.getList().size();
		int updated = 0;
		
		//字义需要更新的DTO对象
		CreditMsgDto msgDto = new CreditMsgDto();
		msgDto.setStatus(SettlementDictionaryConstants.CREDIT_MSG_STATUS_HAN_EXECUTE);

		// 因为update用的是in,对超过1000条记录的进行特殊处理，分割后分批处理
		List<List<CreditMsgEntity>> splitList = com.deppon.foss.util.CollectionUtils
				.splitListBySize(dto.getList(), SettlementConstants.MAX_LIST_SIZE);
		for (List<CreditMsgEntity> entityList : splitList) {
			msgDto.setList(entityList);
			updated += this.creditMsgEntityDao.updateBatchCreditMsgForStatus(msgDto);
		}

		if (updated != totalSize) {
			throw new SettlementException("更新条数和总条数不匹配,批量修改财务收支平衡消息表失败");
		}

		LOGGER.info(" end 批量修改财务收支平衡消息--状态成功 ---------");
	}

	/**
	 * 查询时间段内（结束时间默认为系统时间）所有未执行的财务收支平衡消息表
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-18 上午11:23:38
	 * @param creditType
	 * @return
	 */
	@Override
	public List<CreditMsgEntity> queryCreditMsgEntity(Date date,
			String creditType) {

		// 开始时间与结束时间相差3天
		Date startDate = DateUtils.addDays(date,
				(SettlementConstants.QUERY_CREDIT_DIFF_DAY * -1));
		Date endDate = DateUtils.addDays(date, 1);
		return this.creditMsgEntityDao.queryCreditMsgEntity(startDate, endDate,
				creditType);

	}

	/**
	 * 批量处理财务收支平衡消息表，来还原或增加客户的信用额度/部门的可用额度
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-18 下午4:33:06
	 */
	public void batchUpdateCreditMsg(Date date) {

		LOGGER.info(" start 批量处理财务收支平衡消息表 ---------");

		date = (date == null) ? new Date() : date;

		// 参数可传
		List<CreditMsgEntity> list = this.queryCreditMsgEntity(date, null);

		if (CollectionUtils.isNotEmpty(list)) {
			for (CreditMsgEntity entity : list) {
				if (entity != null && SettlementDictionaryConstants.CREDIT_MSG_CREDIT_TYPE__CUSTOMER
						.equals(entity.getCreditType())  && entity.getAmount() != null) {
					// 扣减客户信息额度（实际为增加客户已使用额度）
					// 核销，红冲数据库存储为正数，乘以-1为负数和已用额度相加，已用额度值会减少；
					// 反核销数据库存储为负数乘以-1为正数，和已用额度相加，已用额度值会增加；
					this.creditCustomerService.updateUsedAmount(
							entity.getCode(), entity.getAmount().negate());
					
				} else if ( entity != null && SettlementDictionaryConstants.CREDIT_MSG_CREDIT_TYPE__ORG
						.equals(entity.getCreditType())  && entity.getAmount() != null) {
					// 扣减组织已用额度（实际为增加部门已使用额度）
					// 核销，红冲数据库存储为正数，乘以-1为负数和已用额度相加，已用额度值会减少；
					// 反核销数据库存储为负数乘以-1为正数，和已用额度相加，已用额度值会增加；
					this.creditOrgService.updateUsedAmount(entity.getCode(),
							entity.getAmount().negate());
				}
			}

			// 修改额度以后，进行设置该批数据状态为：已处理
			CreditMsgDto dto = new CreditMsgDto();
			dto.setList(list);
			this.batchUpdateCreditMsgForStatus(dto);
		}

		LOGGER.info(" end 批量处理财务收支平衡消息表成功 ---------");
	}

	/**
	 * @param creditMsgEntityDao
	 *            the creditMsgEntityDao to set
	 */
	public void setCreditMsgEntityDao(ICreditMsgEntityDao creditMsgEntityDao) {
		this.creditMsgEntityDao = creditMsgEntityDao;
	}

	/**
	 * @param creditOrgService
	 *            the creditOrgService to set
	 */
	public void setCreditOrgService(ICreditOrgService creditOrgService) {
		this.creditOrgService = creditOrgService;
	}

	/**
	 * @param creditCustomerService
	 *            the creditCustomerService to set
	 */
	public void setCreditCustomerService(
			ICreditCustomerService creditCustomerService) {
		this.creditCustomerService = creditCustomerService;
	}

}
