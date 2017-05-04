/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/service/impl/ShareJobService.java
 * 
 * FILE NAME        	: ShareJobService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IWorkdayComplexService;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.INotifyCustomerDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IShareJobDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IShareJobService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobExceptionLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobSuccessLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.StorageExecdateDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.StorageExecdateEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.StorageJobDto;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 仓储费计算service实现类
 * 
 * @author ibm-wangfei
 * @date Feb 27, 2013 5:14:09 PM
 */
public class ShareJobService implements IShareJobService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ShareJobService.class);

	/**
	 * 仓储费定时任务相关DAO接口
	 */
	private IShareJobDao shareJobDao;

	/**
	 * 工作日接口
	 */
	private IWorkdayComplexService workdayComplexService;

	/**
	 * 客户通知
	 */
	private INotifyCustomerDao notifyCustomerDao;

	/**
	 * 客户通知service接口
	 */
	private INotifyCustomerService notifyCustomerService;
	
	/**
	 * 仓储费计算service实现类
	 */
	private IShareJobService shareJobService;

	/**
	 * 
	 * 仓储费日期执行表记录操作入口
	 * 
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 5:19:15 PM
	 */
	@Transactional
	@Override
	public void executeStorageExecdate() {
		LOGGER.info("仓储费日期执行表记录操作入口begin");
		// 设置执行日期为当前日期的前一天
		Date storageDate = DateUtils.addDayToDate(new Date(), -1);
		LOGGER.info("执行日期{}", storageDate);
		// 判断是否工作日
		if (!isWorkDay(storageDate)) {
			LOGGER.info("{}不是工作日，退出仓储费执行。", storageDate);
			return;
		}

		// 查询日期执行表是否有执行日期的数据
		StorageExecdateEntity storageExecdateEntity = new StorageExecdateEntity();
		storageExecdateEntity.setStorageDate(DateUtils.convert(DateUtils.convert(storageDate, DateUtils.DATE_FORMAT), DateUtils.DATE_FORMAT));
		StorageExecdateEntity entity = shareJobDao.queryStorageExecdateEntityByStorageDate(storageExecdateEntity);
		if (entity == null) {
			/*
			 *  新增一条记录 
			 *  0-未处理
			 *  1-处理中
			 *  2-处理失败
			 *  3-部分成功
			 *  4-处理完成
			 *  5-运单数据插入成功
			 */
			storageExecdateEntity.setExecStatus(NumberConstants.NUMERAL_S_ZORE);
			shareJobDao.addStorageExecdateEntity(storageExecdateEntity);
		} else {
			LOGGER.info("当前执行日期在数据库已存在，退出job");
		}
		LOGGER.info("仓储费日期执行表记录操作入口end");
	}

	/**
	 * 
	 * 批量新增待执行明细表记录入口
	 * 
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 5:21:01 PM
	 */
	@Override
	public void batchAddStorageExecdateDetail() {
		LOGGER.info("批量新增待执行明细表记录begin");
		StorageExecdateEntity storageExecdateEntity = new StorageExecdateEntity();
		storageExecdateEntity.setExecStatus(NumberConstants.NUMERAL_S_ZORE);
		// 根据执行状态查询记录列表
		List<StorageExecdateEntity> storageExecdateEntityList = this.shareJobDao.queryStorageExecdateEntityByExeStatus(storageExecdateEntity);
		if (CollectionUtils.isEmpty(storageExecdateEntityList)) {
			LOGGER.error("数据库没有可执行记录，退出本JOB。");
			return;
		}
		// 默认运单不等于空运、偏线、整车 ,标准快递，3.60 特惠件 ,商务专递
		String productCodes[] = { ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT, ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE,ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE,ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE,ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE,ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE,ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT};
		for (StorageExecdateEntity entity : storageExecdateEntityList) {
			shareJobService.exeBatchAddStorageExecdateDetail(entity, productCodes);
		}
		LOGGER.info("批量新增待执行明细表记录end");
	}

	/**
	 * 
	 * 新增可执行明细数据
	 * 
	 * @param execdateId
	 * @param productCodes
	 * @author ibm-wangfei
	 * @date Feb 28, 2013 5:14:00 PM
	 */
	@Transactional
	@Override
	public void exeBatchAddStorageExecdateDetail(StorageExecdateEntity entity, String[] productCodes) {
		this.shareJobDao.batchAddStorageExecdateDetailEntity(entity.getId(), productCodes);
		// 5-运单数据插入成功
		entity.setExecStatus(NumberConstants.NUMERAL_S_FIVE);
		this.shareJobDao.updateStorageExecdateEntity(entity);
	}

	/**
	 * 
	 * 计算仓储费--定时任务<br>
	 * 
	 * Job说明：<br>
	 * 1：查询出符合到达件数 ≥ 开单件数 AND 库存件数 > 0 AND 运输性质 空运、偏线的运单信息<br>
	 * 2：【到达时间】：检索出来的库存表最后入库时间<br>
	 * 3：【入库日期】：到达时间<12:00，则【入库日期】=当天日期；到达时间≥12:00，则【入库日期】=（当天日期+1） <br>
	 * 4：【库存天数】=当前日期-入库日期，当前日期和入库日期的运算按工作日的日期进行计算；（当前日期≥入库日期）
	 * 5：【超期天数】=（库存天数-X），超期天数＞0，则计算仓储费；否则，仓储费=0<br>
	 * 6：【仓储费】=货物体积*超期天数*10元/方/天，仓储费收取标准为10元/方/天，最低5元/票，最高1000元/票；<br>
	 * 7：停止计算仓储费规则：若当前日期≥出库日期，则不再计算仓储费，仓储费金额显示最后一次计算的数值不变；<br>
	 * 若库存天数≥90天，则不再计算仓储费，并且仓储费=0<br>
	 * 8：空运、偏线、中转下线的不计算仓储费 <br>
	 * 
	 * 9：本JOB每日一次，进行仓储费的累加计算，节假日不执行
	 * 
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:30:13 AM
	 */
	@Override
	public void computeStorage() {
		LOGGER.info("计算仓储费JOB-begin");
		StorageExecdateEntity storageExecdateEntity = new StorageExecdateEntity();
		storageExecdateEntity.setExecStatus(NumberConstants.NUMERAL_S_FIVE);
		storageExecdateEntity.setOperatorTime(new Date());
		// 根据执行状态查询记录列表
		List<StorageExecdateEntity> storageExecdateEntityList = this.shareJobDao.queryStorageExecdateEntityByExeStatus(storageExecdateEntity);
		if (CollectionUtils.isEmpty(storageExecdateEntityList)) {
			LOGGER.error("数据库没有可执行记录，退出本JOB。");
			return;
		}

		// 设置仓储费计算的共通参数
		String[] codes = { NotifyCustomerConstants.MAX_WAREHOUSE_FREE_SAFE_DATA_DESC, // 仓储费计费最大天数
				NotifyCustomerConstants.STORAGE_CHARGE_MAX_DESC, // 最大仓储费金额
				NotifyCustomerConstants.STORAGE_CHARGE_MIN_DESC, // 最小仓储费金额
				NotifyCustomerConstants.STORAGE_CHARGE_UNIT_DESC,// 仓储费收取标准
				NotifyCustomerConstants.WAREHOUSE_FREESAFE_DATA_NUM_DESC, // 默认的免费库存天数
				NotifyCustomerConstants.IS_STORAGE_CHARGE_INIT_DESC // 是否仓储费初始化
		};
		StorageJobDto storageJobDto = this.notifyCustomerService.getConfigurationParams(codes);

		for (StorageExecdateEntity entity : storageExecdateEntityList) {
			// 查询对应执行日期的明细数据Count
			Long detailCount = this.shareJobDao.selectStorageExecdateDetailEntityCountByParam(entity.getId(), NumberConstants.NUMERAL_S_ZORE);
			if (detailCount == null || detailCount.intValue() == 0) {
				LOGGER.info("{}没有可执行明细记录", entity.getStorageDate());
				continue;
			}
			// 更新执行日状态为处理中
			entity.setExecStatus(NumberConstants.NUMERAL_S_ONE);
			this.shareJobDao.updateStorageExecdateEntity(entity);
			// 对可执行日期的运单进行计算仓储费
			executeStorageCompute(entity, detailCount, storageJobDto);
			// 更新执行日状态为处理完成
			entity.setExecStatus(NumberConstants.NUMERAL_S_FOUR);
			entity.setOperatorTime(new Date());
			this.shareJobDao.updateStorageExecdateEntity(entity);
		}
		LOGGER.info("计算仓储费JOB-end");
	}

	/**
	 * 
	 * 执行仓储费计算
	 * 
	 * @param storageExecdateEntity
	 * @param detailCount
	 * @author ibm-wangfei
	 * @date Feb 28, 2013 6:55:50 PM
	 */
	private void executeStorageCompute(StorageExecdateEntity storageExecdateEntity, Long detailCount, StorageJobDto storageJobDto) {
		// 设置临时变量的值为记录总数
		Long count = detailCount;
		// 更新所有明细记录的执行状态为处理中
		this.shareJobDao.updateStorageExecdateDetailEntityByParam(storageExecdateEntity.getId(), NumberConstants.NUMERAL_S_ZERE, NumberConstants.NUMERAL_S_ONE);
		boolean hasLongRecord = true;
		List<StorageExecdateDetailEntity> storageExecdateDetailEntityList = null;
		while (hasLongRecord) {
			LOGGER.info("可执行明细记录数：{}", count);
			if (count.intValue() <= NotifyCustomerConstants.ROWNUM) {
				hasLongRecord = false;
			}
			// 查询符合条件的记录数，记录数 <= NotifyCustomerConstants.ROWNUM
			storageExecdateDetailEntityList = this.shareJobDao.queryStorageExecdateDetailEntity(storageExecdateEntity.getId(), NumberConstants.NUMERAL_S_ONE, NotifyCustomerConstants.ROWNUM);
			if (CollectionUtils.isEmpty(storageExecdateDetailEntityList)) {
				continue;
			}
			for (StorageExecdateDetailEntity storageExecdateDetailEntity : storageExecdateDetailEntityList) {
				this.shareJobService.execute(storageExecdateDetailEntity, storageExecdateEntity, storageJobDto);
			}
			// 减去已执行的记录数
			count -= NotifyCustomerConstants.ROWNUM;
		}
	}

	/**
	 * 
	 * 具体仓储费计算逻辑
	 * 
	 * @param storageExecdateDetailEntity
	 * @author ibm-wangfei
	 * @date Feb 28, 2013 7:26:32 PM
	 */
	@Transactional
	@Override
	public void execute(StorageExecdateDetailEntity storageExecdateDetailEntity, StorageExecdateEntity storageExecdateEntity, StorageJobDto storageJobDto) {
		if (storageExecdateDetailEntity.getLastInStockTime() == null) {
			return;
		}
		// 输入时间<12:00，返回输入日期；到达时间≥12:00，则返回（输入日期+1）.
		String arriveTime = getArriveTime(storageExecdateDetailEntity.getLastInStockTime());
		LOGGER.info("判断时间为： {}", arriveTime);
		// 库存天数及保管费计算
		ActualFreightEntity actualFreightEntity = getActualFreightEntity(storageExecdateDetailEntity, arriveTime, storageExecdateEntity, storageJobDto);
		// 更新运单附加表
		int i = updateActualFreightEntityByWaybillNo(actualFreightEntity);
		if (i == 0) {
			LOGGER.error("运单号{}更新失败", actualFreightEntity.getWaybillNo());
			// 执行失败将运单号及失败详细信息插入到t_srv_job_exception_log
			JobExceptionLogEntity jobExceptionLogEntity = new JobExceptionLogEntity();
			jobExceptionLogEntity.setJobName(NotifyCustomerConstants.STORAGE_COMPUTE_JOB_NAME);// job名称
			jobExceptionLogEntity.setErrorCode(storageExecdateDetailEntity.getWaybillNo()); // 设置成功运单编号
			jobExceptionLogEntity.setErrorId(storageExecdateEntity.getId());
			jobExceptionLogEntity.setErrorMsg("运单号:" + actualFreightEntity.getWaybillNo() + "无法查询到对应记录，未更新仓储费。");
			this.shareJobDao.addJobExceptionLogEntity(jobExceptionLogEntity);
		} else {
			// 添加job成功日志 执行成功将运单号及详细信息插入到t_srv_job_success_log并删除运单号
			JobSuccessLogEntity jobSuccessLogEntity = new JobSuccessLogEntity();
			jobSuccessLogEntity.setJobName(NotifyCustomerConstants.STORAGE_COMPUTE_JOB_NAME);// job名称
			jobSuccessLogEntity.setSuccessMsg(storageExecdateDetailEntity.getWaybillNo()); // 设置成功运单编号
			jobSuccessLogEntity.setSuccessId(storageExecdateEntity.getId());
			this.shareJobDao.addJobSuccessLogEntity(jobSuccessLogEntity);
		}
		// 删除临时表的明细记录
		this.shareJobDao.deleteStorageExecdateDetailEntity(storageExecdateDetailEntity);
	}

	/**
	 * 库存天数及保管费计算.
	 * 
	 * @param dto the dto
	 * @param arriveTime the arrive time
	 * @param exeDate the cur date
	 * @param warehouseFreeSafeDataNum the warehouse free safe data num
	 * @return the actualFreightEntity
	 * @author ibm-wangfei
	 * @date Nov 12, 2012 6:01:58 PM
	 */
	private ActualFreightEntity getActualFreightEntity(StorageExecdateDetailEntity detail, String arriveTime, StorageExecdateEntity storageExecdateEntity, StorageJobDto storageJobDto) {
		// 对数据初始化，库存天数、保管费、逾期天数初始计算
		// 库存天数 = 当前日期-入库日期,按工作日计算
		int storageDay = 0;
		// 逾期天数 = 库存天数 - 免费保管天数
		int overdueDay = 0;
		// 【保管费】=货物体积*超期天数*10元/方/天
		BigDecimal storageCharge = BigDecimal.valueOf(0);
		BigDecimal storageChargeTmp = BigDecimal.valueOf(0);
		if (FossConstants.YES.equals(storageJobDto.getIsStorageChargeInit())) {
			LOGGER.info("系统初始化保管费");
			storageDay = workDays(arriveTime, storageExecdateEntity.getStorageDate());
			LOGGER.info("库存天数：{}", storageDay);
			overdueDay = storageDay - storageJobDto.getWarehouseFreeSafeDataNum();
			LOGGER.info("逾期天数：{}", overdueDay);
			if (storageDay > storageJobDto.getWarehouseFreeSafeDataNum() && storageDay < storageJobDto.getMaxWarehouseFreeSafeData()) {
				storageChargeTmp = detail.getGoodsVolumeTotal().multiply(storageJobDto.getStorageChargeUnit());
				storageCharge = storageChargeTmp.multiply(BigDecimal.valueOf(overdueDay));
				LOGGER.info("实际保管费：{}", storageCharge);
			} else {
				// 这里不做处理
			}
		} else {
			// 系统正常运行后，库存天数、保管费累加计算
			LOGGER.info("系统正常运行后，库存天数、保管费累加计算");
			if (detail.getStorageDay() == null || detail.getStorageDay() == 0) {
				storageDay = workDays(arriveTime, storageExecdateEntity.getStorageDate());
				overdueDay = storageDay - storageJobDto.getWarehouseFreeSafeDataNum();
			} else {
				storageDay = detail.getStorageDay().intValue() + 1;
				if (storageDay > storageJobDto.getWarehouseFreeSafeDataNum()) {
					overdueDay = (detail.getOverdueDay() == null ? 0 : detail.getOverdueDay()) + 1;
				}
			}
			LOGGER.info("库存天数：{}", storageDay);
			LOGGER.info("逾期天数：{}", overdueDay);
			if (storageDay > storageJobDto.getWarehouseFreeSafeDataNum() && storageDay < storageJobDto.getMaxWarehouseFreeSafeData()) {
				storageChargeTmp = detail.getGoodsVolumeTotal().multiply(storageJobDto.getStorageChargeUnit());
				storageCharge = (detail.getStorageCharge() == null ? BigDecimal.valueOf(0) : detail.getStorageCharge()).add(storageChargeTmp);
				LOGGER.info("实际保管费：{}", storageCharge);
			} else {
				LOGGER.info("不作处理");
			}
		}
		if (storageJobDto.getStorageChangeMax().compareTo(storageCharge) < 0) {
			// 最高1000元/票
			LOGGER.info("最高1000元/票后的保管费：{}", storageCharge);
			storageCharge = storageJobDto.getStorageChangeMax();
		}
		ActualFreightEntity actualFreightEntity = new ActualFreightEntity();
		actualFreightEntity.setWaybillNo(detail.getWaybillNo());
		// 库存天数
		actualFreightEntity.setStorageDay(storageDay);
		// 保管费
		actualFreightEntity.setStorageCharge(storageCharge);
		// 逾期天数
		actualFreightEntity.setOverdueDay(overdueDay < 0 ? 0 : overdueDay);
		LOGGER.info(ReflectionToStringBuilder.toString(actualFreightEntity));
		return actualFreightEntity;
	}

	/**
	 * 判断日期是否是工作日.
	 * 
	 * @param date the date
	 * @return true, if is work day
	 * @author ibm-wangfei
	 * @date Nov 29, 2012 7:07:14 PM
	 */
	private boolean isWorkDay(Date date) {
		return workdayComplexService.isWorkday(date);
	}

	/**
	 * 获取两个日期之间的工作日天数.
	 * 
	 * @param beginDate the begin date
	 * @param endDate the end date
	 * @return the int
	 * @author ibm-wangfei
	 * @date Nov 29, 2012 7:07:09 PM
	 */
	private int workDays(String beginDate, Date endDate) {
		return workdayComplexService.countWorkday(DateUtils.convert(beginDate, DateUtils.DATE_FORMAT), endDate);
	}

	/**
	 * 输入时间<12:00，返回输入日期；到达时间≥12:00，则返回（输入日期+1）.
	 * 
	 * @param arriveTime the arrive time
	 * @return the arriveTime
	 * @author ibm-wangfei
	 * @date Nov 12, 2012 5:18:53 PM
	 */
	private String getArriveTime(Date arriveTime) {
		String hm = DateUtils.convert(arriveTime, NotifyCustomerConstants.DATE_TIME_FORMAT_HHMM);
		if (NotifyCustomerConstants.DATE_TIME_AM_12.compareTo(hm) > 0) {
			return DateUtils.convert(arriveTime, DateUtils.DATE_FORMAT);
		} else {
			return DateUtils.addDay(arriveTime, 1);
		}
	}

	/**
	 * 根据运单编号，更新运单附属表的通知信息.
	 * 
	 * @param actualFreightEntity the actual freight entity
	 * @return the int
	 * @author ibm-wangfei
	 * @date Dec 18, 2012 7:02:41 PM
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService#updateActualFreightEntityByWaybillNo(com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity)
	 */
	private int updateActualFreightEntityByWaybillNo(ActualFreightEntity actualFreightEntity) {
		LOGGER.info("待更新的actualFreightEntity：" + ReflectionToStringBuilder.toString(actualFreightEntity));
		return notifyCustomerDao.updateActualFreightEntityByWaybillNo(actualFreightEntity);
	}

	public void setShareJobDao(IShareJobDao shareJobDao) {
		this.shareJobDao = shareJobDao;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	public void setWorkdayComplexService(IWorkdayComplexService workdayComplexService) {
		this.workdayComplexService = workdayComplexService;
	}

	public void setNotifyCustomerDao(INotifyCustomerDao notifyCustomerDao) {
		this.notifyCustomerDao = notifyCustomerDao;
	}

	public void setNotifyCustomerService(INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}

	public void setShareJobService(IShareJobService shareJobService) {
		this.shareJobService = shareJobService;
	}

}