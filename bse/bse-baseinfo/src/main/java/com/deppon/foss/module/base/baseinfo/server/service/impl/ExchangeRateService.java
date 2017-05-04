/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExchangeRateDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExchangeRateService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExchangeRateEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExchangeRateException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 汇率信息维护Dao接口实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2013-4-15 下午3:22:37
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-4-15 下午3:22:37
 * @since
 * @version
 */
public class ExchangeRateService implements IExchangeRateService {

	/**
	 * 日志.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExchangeRateService.class);

	/**
	 * 汇率信息维护Dao接口.
	 */
	private IExchangeRateDao exchangeRateDao;

	/**
	 * 设置 汇率信息维护Dao接口.
	 * 
	 * @param exchangeRateDao
	 *            the exchangeRateDao to set
	 */
	public void setExchangeRateDao(IExchangeRateDao exchangeRateDao) {
		this.exchangeRateDao = exchangeRateDao;
	}

	/**
	 * <p>
	 * 新增汇率信息
	 * </p>
	 * .
	 * 
	 * @param entity
	 * @return
	 * @author 094463-foss-xieyantao
	 * @date 2013-4-15 下午3:22:37
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExchangeRateService#addExchangeRate(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExchangeRateEntity)
	 */
	@Transactional
	@Override
	public int addExchangeRate(ExchangeRateEntity entity) {

		if (null == entity) {
			throw new ExchangeRateException("传入的参数不允许为空！");
		}
		// 第一次记录新增时，虚拟编码为记录的id
		entity.setId(UUIDUtils.getUUID());
		entity.setVirtualCode(entity.getId());
		entity.setCreateDate(new Date());
		// 设置状态为有效
		entity.setActive(FossConstants.ACTIVE);
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		LOGGER.debug("virtualCode: " + entity.getVirtualCode());
		// 根据币种和id查询汇率信息
		// ExchangeRateEntity entity2 =
		// exchangeRateDao.queryRateEntityByCurrency(entity.getCurrency(),
		// null);
		/*
		 * if(null != entity2){ throw new
		 * ExchangeRateException("该币种已存在，请重新选择！"); }
		 */
		if (entity.getBeginTime().getTime() >= entity.getEndTime().getTime()) {
			throw new ExchangeRateException("生效时间不能大于失效时间！");
		}
		// 根据币种查询失效时间最大的汇率信息
		ExchangeRateEntity entity3 = exchangeRateDao
				.queryRateEntityByMaxEndTimeCurrency(entity.getCurrency(), null);
		if (null != entity3) {
			if (entity.getBeginTime().getTime() < entity3.getEndTime()
					.getTime()) {
				// 将时间格式转换
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String endTime = sdf.format(entity3.getEndTime());

				throw new ExchangeRateException("生效时间不能小于历史记录的失效时间:" + endTime);
			}
		}
		return exchangeRateDao.addExchangeRate(entity);
	}

	/**
	 * <p>
	 * 修改汇率信息
	 * </p>
	 * .
	 * 
	 * @param entity
	 * @return
	 * @author 094463-foss-xieyantao
	 * @date 2013-4-15 下午3:22:37
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExchangeRateService#updateExchangeRate(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExchangeRateEntity)
	 */
	@Transactional
	@Override
	public int updateExchangeRate(ExchangeRateEntity entity) {
		if (null == entity) {
			throw new ExchangeRateException("传入的参数不允许为空！");
		} else if (StringUtils.isBlank(entity.getVirtualCode())) {
			throw new ExchangeRateException("虚拟编码不允许为空！");
		}
		// List<String> list = new ArrayList<String>();
		// list.add(entity.getVirtualCode());
		// //作废汇率信息
		// int result = exchangeRateDao.deleteExchangeRateByVirtualCode(list,
		// entity.getModifyUser());
		/*
		 * if(result > 0){ //作废成功 entity.setActive(FossConstants.ACTIVE);
		 * entity.setId(UUIDUtils.getUUID()); entity.setCreateDate(new Date());
		 * entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		 * //根据币种和id查询汇率信息 ExchangeRateEntity entity2 = exchangeRateDao
		 * .queryRateEntityByCurrency(entity.getCurrency(), null); if (null !=
		 * entity2) { throw new ExchangeRateException("该币种已存在，请重新选择！"); } if
		 * (entity.getBeginTime().getTime() >= entity.getEndTime() .getTime()) {
		 * throw new ExchangeRateException("生效时间不能大于失效时间！"); } //
		 * 根据币种查询失效时间最大的汇率信息 ExchangeRateEntity entity3 = exchangeRateDao
		 * .queryRateEntityByMaxEndTimeCurrency(entity.getCurrency()); if (null
		 * != entity3) { if (entity.getBeginTime().getTime() <
		 * entity3.getEndTime() .getTime()) { throw new
		 * ExchangeRateException("生效时间不能小于历史记录的失效时间！"); } }
		 * 
		 * return exchangeRateDao.addExchangeRate(entity); }else { return
		 * FossConstants.FAILURE; }
		 */

		entity.setModifyDate(new Date());
		// 根据币种和id,和生效时间段查询汇率信息 ,
		List<ExchangeRateEntity> entities = exchangeRateDao
				.queryExchangeRateBytimeAndCurrency(entity);
		if (CollectionUtils.isNotEmpty(entities)) {
			throw new ExchangeRateException("该币种在该时间段已存在，请重新选择！");
		}
		if (entity.getBeginTime().getTime() >= entity.getEndTime().getTime()) {
			throw new ExchangeRateException("生效时间不能大于失效时间！");
		}
		// 根据id查询库中该要修改的实体信息
		ExchangeRateEntity entity1 = exchangeRateDao
				.queryExchangeRateById(entity.getId());
		if (null != entity1) {
			// 若用户没有修改生效时间(失效时间)，只修改汇率，
			if ((entity1.getBeginTime().getTime() == entity.getBeginTime().getTime())
					&& (entity1.getEndTime().getTime() == entity.getEndTime().getTime())) {
				// 可以直接修改
				return exchangeRateDao.updateExchangeRate(entity);
			}
		}
		// 根据币种查询失效时间最大的汇率信息
		ExchangeRateEntity entity2 = exchangeRateDao
				.queryRateEntityByMaxEndTimeCurrency(entity.getCurrency(),
						entity.getId());
		if (null != entity2) {
			if (entity.getBeginTime().getTime() < entity2.getEndTime()
					.getTime()) {
				// 将时间格式转换
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String endTime = sdf.format(entity2.getEndTime());
				throw new ExchangeRateException("生效时间不能小于历史记录的失效时间:" + endTime);
			}
		}
		return exchangeRateDao.updateExchangeRate(entity);
	}

	/**
	 * <p>
	 * 作废汇率信息
	 * </p>
	 * .
	 * 
	 * @param virtualCodeList
	 * @param modifyUser
	 * @return
	 * @author 094463-foss-xieyantao
	 * @date 2013-4-15 下午3:22:39
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExchangeRateService#deleteExchangeRateByVirtualCode(java.util.List,
	 *      java.lang.String)
	 */
	@Override
	public int deleteExchangeRateByVirtualCode(List<String> virtualCodeList,
			String modifyUser) {
		if (CollectionUtils.isEmpty(virtualCodeList)) {
			throw new ExchangeRateException("虚拟编码不允许为空！");
		} else {
			return exchangeRateDao.deleteExchangeRateByVirtualCode(
					virtualCodeList, modifyUser);
		}
	}

	/**
	 * 根据传入对象查询符合条件所有汇率信息.
	 * 
	 * @param entity
	 *            汇率信息实体
	 * @param limit
	 *            每页最大显示记录数
	 * @param start
	 *            开始记录数
	 * @return 符合条件的实体列表
	 * @author dp-xieyantao
	 * @date 2013-4-15 下午3:10:32
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExchangeRateService#queryAllExchangeRate(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExchangeRateEntity,
	 *      int, int)
	 */
	@Override
	public List<ExchangeRateEntity> queryAllExchangeRate(
			ExchangeRateEntity entity, int limit, int start) {
		if (null == entity) {
			throw new ExchangeRateException("传入的参数不允许为空！");
		} else {
			// entity.setActive(FossConstants.ACTIVE);
			return exchangeRateDao.queryAllExchangeRate(entity, limit, start);
		}
	}

	/**
	 * <p>
	 * 统计总记录数
	 * </p>
	 * .
	 * 
	 * @param entity
	 * @return
	 * @author 094463-foss-xieyantao
	 * @date 2013-4-15 下午3:22:39
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExchangeRateService#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExchangeRateEntity)
	 */
	@Override
	public Long queryRecordCount(ExchangeRateEntity entity) {
		if (null == entity) {
			throw new ExchangeRateException("传入的参数不允许为空！");
		} else {
			// entity.setActive(FossConstants.ACTIVE);
			return exchangeRateDao.queryRecordCount(entity);
		}
	}

	/**
	 * <p>
	 * 根据传入的人民币金额、外币代码、开单日期获取转汇后的外币金额
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2013-7-12 上午10:34:59
	 * @param rmbFee
	 *            人民币金额
	 * @param currencyCode
	 *            外币代码
	 * @param billTime
	 *            开单日期
	 * @return 转汇后的外币金额
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExchangeRateService#getExchangedFee(java.math.BigDecimal,
	 *      java.lang.String, java.util.Date)
	 */
	@Override
	public BigDecimal getExchangedFee(BigDecimal rmbFee, String currencyCode,
			Date billTime) {
		if (null == rmbFee) {
			throw new ExchangeRateException("", "人民币金额不允许为空！");
		}
		if (null == billTime) {
			throw new ExchangeRateException("", "开单日期不允许为空！");
		}
		if (StringUtils.isBlank(currencyCode)) {
			throw new ExchangeRateException("", "外币代码不允许为空！");
		}

		// 根据币种编码、开单日期查询汇率信息
		List<ExchangeRateEntity> list = exchangeRateDao.queryExchangeRate(
				currencyCode, billTime);
		if (CollectionUtils.isNotEmpty(list)) {
			ExchangeRateEntity entity = list.get(0);
			if (null != entity) {
				BigDecimal exchangeFee = rmbFee.multiply(entity.getExchange());

				return exchangeFee;
			}
		}
		return null;
	}

	/**
	 * 
	 * 根据传入的外币代码、开单日期获得汇率
	 * 
	 * @param currencyCode
	 *            外币代码
	 * @param billTime
	 *            开单日期
	 * @return 汇率
	 * @author 025000-FOSS-helong
	 * @date 2013-7-15
	 */
	@Override
	public BigDecimal getExchangedFeeRateByCurrencyCode(String currencyCode,
			Date billTime) {
		if (null == billTime) {
			throw new ExchangeRateException("", "开单日期不允许为空！");
		}
		if (StringUtils.isBlank(currencyCode)) {
			throw new ExchangeRateException("", "外币代码不允许为空！");
		}
		// 根据币种编码、开单日期查询汇率信息
		List<ExchangeRateEntity> list = exchangeRateDao.queryExchangeRate(
				currencyCode, billTime);
		if (CollectionUtils.isNotEmpty(list)) {
			ExchangeRateEntity entity = list.get(0);
			if (null != entity) {
				return entity.getExchange();
			}
		}
		return null;
	}
	/**
	 * 
	 * <p>
	 * 查询根据币种的失效时间进行排序，查询失效时间最大的币种
	 * </p>
	 * 
	 * @author 130566-zengJunfan
	 * @date 2013-9-16下午7:38:15
	 * @param currency
	 * @param id
	 * @return
	 */
	@Override
	public ExchangeRateEntity queryRateEntityByMaxEndTimeCurrency(
			String currency) {
		if (StringUtils.isBlank(currency)) {
			throw new ExchangeRateException("币种不能为空");
		}
		ExchangeRateEntity entity = exchangeRateDao
				.queryRateEntityByMaxEndTimeCurrency(currency, null);
		return entity;
	}

}
