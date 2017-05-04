package com.deppon.foss.module.settlement.common.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.settlement.common.api.server.dao.IClaimStatusMsgEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IClaimStatusMsgService;
import com.deppon.foss.module.settlement.common.api.shared.domain.ClaimStatusMsgEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 理赔状态消息服务
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-12-3 下午2:33:53
 */
public class ClaimStatusMsgService implements IClaimStatusMsgService {

	private static final Logger logger = LogManager
			.getLogger(ClaimStatusMsgService.class);

	/**
	 * dao
	 */
	private IClaimStatusMsgEntityDao claimStatusMsgEntityDao;

	/**
	 * 新增理赔支付消息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-4 下午3:46:35
	 * @param entity
	 */
	@Override
	public void addClaimStatusMsg(ClaimStatusMsgEntity entity) {
		if (entity == null || StringUtils.isEmpty(entity.getId())
				|| StringUtils.isEmpty(entity.getWaybillNo())) {
			throw new SettlementException("新增理赔支付消息参数不能为空");
		}
		if (entity.getMsgDate() == null) {
			throw new SettlementException("消息发生日期不能为空");
		}
		if (StringUtils.isEmpty(entity.getMsgAction())) {
			throw new SettlementException("消息动作不能为空");
		}
		logger.info("---Start 新增理赔支付消息" + entity.getWaybillNo());
		int i = this.claimStatusMsgEntityDao.addClaimStatusMsg(entity);
		if (i != 1) {
			throw new SettlementException("新增理赔支付消息失败");
		}
		logger.info("---End 新增理赔支付消息" + entity.getWaybillNo());
	}

	/**
	 * 删除理赔支付消息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-4 下午3:47:00
	 * @param id
	 */
	@Override
	public void deleteClaimStatusMsg(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new SettlementException("删除理赔支付消息参数不能为空");
		}

		logger.info(" ---Start 删除理赔支付信息 ID: " + id);
		int i = this.claimStatusMsgEntityDao.deleteClaimStatusMsg(id);
		if (i != 1) {
			throw new SettlementException("删除理赔支付消息失败");
		}
		logger.info(" ---END 删除理赔支付信息 ");
	}

	/**
	 * 根据运单号查询理赔支付消息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-5 下午3:48:52
	 * @param waybillNo
	 * @return
	 */
	@Override
	public List<ClaimStatusMsgEntity> queryClaimStatusMsgByWaybillNO(
			String waybillNo) {
		if (StringUtils.isEmpty(waybillNo)) {
			throw new SettlementException("运单号不能为空！");
		}
		return this.claimStatusMsgEntityDao
				.queryClaimStatusMsgByWaybillNO(waybillNo);
	}

	/**
	 * 
	 * 获得未发送的消息
	 * @author 00123-foss-huangxiaobo
	 * @date 2012-12-24 下午8:39:43
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IClaimStatusMsgService#queryNotSendMsg()
	 */
	public List<ClaimStatusMsgEntity> queryNotSendMsg() {
		return claimStatusMsgEntityDao.queryNotSend(FossConstants.NO);
	}

	/**
	 * 更新已发送消息状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-5 上午10:51:20
	 */
	public void updateMsgStatusSended(String entityId) {

		// 构造更新条件
		ClaimStatusMsgEntity entity = new ClaimStatusMsgEntity();
		entity.setId(entityId);
		entity.setMsgStatus(FossConstants.YES);

		// 执行更新操作
		int rows = claimStatusMsgEntityDao.updateMsgStatus(entity);

		// 更新行数不为1，抛出异常
		if (rows != 1) {
			throw new SettlementException("更新行数不唯一");
		}

	}

	
	/**
	 * @return claimStatusMsgEntityDao
	 */
	public IClaimStatusMsgEntityDao getClaimStatusMsgEntityDao() {
		return claimStatusMsgEntityDao;
	}
	
	/**
	 * @param claimStatusMsgEntityDao
	 */
	public void setClaimStatusMsgEntityDao(
			IClaimStatusMsgEntityDao claimStatusMsgEntityDao) {
		this.claimStatusMsgEntityDao = claimStatusMsgEntityDao;
	}

}
