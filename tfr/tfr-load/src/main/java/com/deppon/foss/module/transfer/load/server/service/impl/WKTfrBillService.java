package com.deppon.foss.module.transfer.load.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.common.api.server.service.ITfrNotifyService;
import com.deppon.foss.module.transfer.common.api.shared.define.NotifyWkConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrNotifyEntity;
import com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IWKTfrBillDao;
import com.deppon.foss.module.transfer.load.api.server.service.IWKTfrBillService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.LimittitionTermEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.RewardOrPunishAgreementEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.WKBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.WKTfrBillEntity;
import com.deppon.foss.module.transfer.load.server.dao.impl.WKTfrBillDao;
import com.deppon.foss.util.UUIDUtils;

/**
 * @description wk交接单表(表T_WK_TRF_BILL)表的操作
 * @version 1.0
 * @author 328864-foss-xieyang
 * @update 2016年5月11日 下午4:46:28
 */
public class WKTfrBillService implements IWKTfrBillService {

	private static final Logger LOGGER = Logger.getLogger(WKTfrBillService.class);

	private IWKTfrBillDao wktfrBillDao;

	private ITfrNotifyService tfrNotifyService;

	private IVehicleAssembleBillDao vehicleAssembleBillDao;

	public void setTfrNotifyService(ITfrNotifyService tfrNotifyService) {
		this.tfrNotifyService = tfrNotifyService;
	}

	public void setWktfrBillDao(WKTfrBillDao wktfrBillDao) {

		this.wktfrBillDao = wktfrBillDao;
	}

	public void setVehicleAssembleBillDao(IVehicleAssembleBillDao vehicleAssembleBillDao) {
		this.vehicleAssembleBillDao = vehicleAssembleBillDao;
	}

	/**
	 * @description 插入悟空交接单(表T_WK_TRF_BILL)信息 (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IWKTfrBillService#insertBill(com.deppon.foss.module.transfer.load.api.shared.domain.WKTfrBillEntity)
	 * @author 328864-foss-xieyang
	 * @update 2016年5月11日 下午4:46:04
	 * @version V1.0
	 */
	public Map<String, String> insertBill(WKTfrBillEntity wKTfrBillEntity) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("调用" + wktfrBillDao.getClass().getName() + ".insertBill方法开始");
		}
		Map<String, String> result = wktfrBillDao.insertBill(wKTfrBillEntity);
		if ("0".equals(result.get("result"))) {
			LOGGER.error("调用" + wktfrBillDao.getClass().getName() + ".insertBill方法插入数据失败----");
			LOGGER.error("错误信息:" + result.get("errMsg"));
		} else
		if ("1".equals(result.get("result"))) {
			/** 长短途快递交接单才生成车辆任务 */
			if (StringUtils.isNotBlank(wKTfrBillEntity.getHandoverBillNo())
					&& (LoadConstants.WKHANDOVER_TYPE_LONG_DISTANCE.equals(wKTfrBillEntity.getHandoverType())
							|| LoadConstants.WKHANDOVER_TYPE_SHORT_DISTANCE
									.equals(wKTfrBillEntity.getHandoverType()))) {
				/** 创建生成车辆任务的异步任务 */
				tfrNotifyService.addTfrNotifyEntity(
						new TfrNotifyEntity(UUIDUtils.getUUID(), NotifyWkConstants.NOTIFY_TYPE_CREATE_TRUCK_TASK,
								wKTfrBillEntity.getHandoverBillNo(), null, null));
			}
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("调用" + wktfrBillDao.getClass().getName() + ".insertBill方法结束");
		}
		return result;
	}

	/**
	 * @description 更新悟空交接单(表T_WK_TRF_BILL)信息 (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IWKTfrBillService#updateBill(com.deppon.foss.module.transfer.load.api.shared.domain.WKTfrBillEntity)
	 * @author 328864-foss-xieyang
	 * @update 2016年5月11日 下午4:46:09
	 * @version V1.0
	 */
	public Map<String, String> updateBill(WKTfrBillEntity wKTfrBillEntity) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("调用" + wktfrBillDao.getClass().getName() + ".updateBill方法开始");
		}
		
//		truncateStableFields(wKTfrBillEntity);
		LOGGER.info("去掉无用字段后的实体： wKTfrBillEntity = " + wKTfrBillEntity.toString());

		Map<String, String> result = wktfrBillDao.updateBill(wKTfrBillEntity);

		if ("0".equals(result.get("result"))) {
			LOGGER.error("调用" + wktfrBillDao.getClass().getName() + ".updateBill方法更新数据失败----");
			LOGGER.error("错误信息:" + result.get("errMsg"));
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("调用" + wktfrBillDao.getClass().getName() + ".updateBill方法结束");
		}
		return result;
	}

	/*
	 * 去掉不会变更的字段 - 字段设为null为不更新
	 * @param wKTfrBillEntity
	 */
	/*private void truncateStableFields(WKTfrBillEntity e) {
		e.setCreateName(null);//创建人
		e.setCreateNo(null);//创建人
		e.setCreateOrgCode(null);//创建部门
		e.setCreateOrgName(null);//创建部门
		e.setCreateTime(null);//创建时间
		e.setDepartOrgCode(null);//出发部门
		e.setDepartOrgName(null);//出发部门
		e.setArriveOrgCode(null);//达到部门
		e.setArriveOrgName(null);//到达部门
		e.setHandoverType(null);//交接类型
	}*/

	@Override
	public WKTfrBillEntity getWKTfrBillEntity(WKTfrBillEntity wktfrBillEntity) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("调用" + wktfrBillDao.getClass().getName() + ".getWKTfrBillEntity方法开始");
		}

		WKTfrBillEntity result = wktfrBillDao.getWKTfrBillEntity(wktfrBillEntity);

		if (result == null) {
			LOGGER.error("调用" + wktfrBillDao.getClass().getName() + ".getWKTfrBillEntity方法获取数据失败----");
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("调用" + wktfrBillDao.getClass().getName() + ".getWKTfrBillEntity方法结束");
		}
		return result;
	}

	@Override
	@Transactional
	public Map<String, String> insertWKBill(WKBillEntity wkBillEntity) {
		WKTfrBillEntity wkTfrBillEntity = wkBillEntity.getLoadHandoverBillEntity();
		if (wkTfrBillEntity == null) {
			LOGGER.info("悟空同步交接单参数为空：wkBillEntity= "+ wkBillEntity);
			Map<String, String> map = new HashMap<String, String>();
			map.put("result", "0");
			map.put("errMsg", "传入参数为空");
			return map;
		}
		// 接口传输,金额可能为NULL
		wkTfrBillEntity.setTotalFee(wkTfrBillEntity.getTotalFee() == null ? null
				: wkTfrBillEntity.getTotalFee().multiply(BigDecimal.valueOf(LoadConstants.INSURANCE_VALUE_MULTIPLY)));
		wkTfrBillEntity.setTotalPrepaidFee(wkTfrBillEntity.getTotalPrepaidFee() == null ? null
				: wkTfrBillEntity.getTotalPrepaidFee().multiply(BigDecimal.valueOf(LoadConstants.INSURANCE_VALUE_MULTIPLY)));
		wkTfrBillEntity.setTotalArriveFee(wkTfrBillEntity.getTotalArriveFee() == null ? null
				: wkTfrBillEntity.getTotalArriveFee().multiply(BigDecimal.valueOf(LoadConstants.INSURANCE_VALUE_MULTIPLY)));
		Map<String, String> result = insertBill(wkTfrBillEntity);
		if ("1".equals(result.get("result"))) {

			handleLimititionTerms(wkBillEntity.getLimititionTermEntitys(), wkTfrBillEntity.getHandoverBillNo(), result);

		}
		return result;
	}

	private void handleLimititionTerms(List<LimittitionTermEntity> list, String handOverBillNo, Map<String, String> result) {
		List<RewardOrPunishAgreementEntity> reqList = new ArrayList<RewardOrPunishAgreementEntity>();

		if (list != null && list.size() > 0) {
			BigDecimal rewardOrPunishMaxMoney = new BigDecimal(0);
			/** 遍历所有条款金额，获取奖罚金额上限 */
			for (LimittitionTermEntity limittitionTermEntity : list) {
				if (limittitionTermEntity.getItemAmount() != null
						&& limittitionTermEntity.getItemAmount().compareTo(rewardOrPunishMaxMoney) >= 0)
					rewardOrPunishMaxMoney = limittitionTermEntity.getItemAmount();
			}

			for (LimittitionTermEntity limittitionTermEntity : list) {
				RewardOrPunishAgreementEntity dto = limittitionTermEntity.toRewardOrPunishAgreementEntity();
				// dto.setAgreementMoney(dto.getAgreementMoney().multiply(BigDecimal.valueOf(100)));
				dto.setRewardOrPunishMaxMoney(rewardOrPunishMaxMoney);
				reqList.add(dto);
			}
			LOGGER.info("存在时效条款(" + reqList.size() + ")：LimititionTermEntitys = " + reqList);
		}

		// 删除配载单
		int agreement = vehicleAssembleBillDao.deletReWardOrPunishAgreement(handOverBillNo);
		LOGGER.info("删除时效条款完成，一共删除了：" + agreement);
		// 插入配载单
		if (reqList.size() > 0) {
			try {
				vehicleAssembleBillDao.saveVehicleRewardProt(reqList);
				LOGGER.info("插入時效條款完成");
			} catch (Exception e) {
				LOGGER.error("插入时效条款数据失败", e);
				result.put("result", "0");
				result.put("errMsg", "插入时效条款数据失败");
			}
		}
	}

	@Override
	@Transactional
	public Map<String, String> updateWKBill(WKBillEntity wkBillEntity) {
		WKTfrBillEntity wkTfrBillEntity = wkBillEntity.getLoadHandoverBillEntity();

		wkTfrBillEntity.setTotalFee(wkTfrBillEntity.getTotalFee() == null ? null
				: wkTfrBillEntity.getTotalFee().multiply(BigDecimal.valueOf(LoadConstants.INSURANCE_VALUE_MULTIPLY)));
		wkTfrBillEntity.setTotalPrepaidFee(wkTfrBillEntity.getTotalPrepaidFee() == null ? null
				: wkTfrBillEntity.getTotalPrepaidFee().multiply(BigDecimal.valueOf(LoadConstants.INSURANCE_VALUE_MULTIPLY)));
		wkTfrBillEntity.setTotalArriveFee(wkTfrBillEntity.getTotalArriveFee() == null ? null
				: wkTfrBillEntity.getTotalArriveFee().multiply(BigDecimal.valueOf(LoadConstants.INSURANCE_VALUE_MULTIPLY)));
		Map<String, String> result = updateBill(wkTfrBillEntity);
		
		if ("1".equals(result.get("result"))) {
			/**
			 * @更新日志：2016年7月6日 14:38:52
			 * @author 335284
			 * 当交接单状态为HAVE_STOCK HAVE_DEPART HAVE_ARRIVE时不修改时效条款(from快递 何浩, 郑新洋)
			 */
			if (!notModifableStatus(wkTfrBillEntity.getHandoverState())) {
				handleLimititionTerms(wkBillEntity.getLimititionTermEntitys(), wkTfrBillEntity.getHandoverBillNo(), result);
			}
		}
		return result;
	}

	/**
	 * 当交接单状态为HAVE_STOCK HAVE_DEPART HAVE_ARRIVE时返回true，此时不可以操作时效条款
	 * @param handoverState
	 * @return
	 */
	private boolean notModifableStatus(String handoverState) {
		if (handoverState == null) {
			return false;
		}
		if (handoverState.equals(LoadConstants.WKHANDOVERBILL_STATE_ALREADY_INSTOCK)) {
			return true;
		}
		if (handoverState.equals(LoadConstants.WKHANDOVERBILL_STATE_ALREADY_DEPART)) {
			return true;
		}
		if (handoverState.equals(LoadConstants.WKHANDOVERBILL_STATE_ALREADY_ARRIVE)) {
			return true;
		}
		return false;
	}

}
