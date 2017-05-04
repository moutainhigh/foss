package com.deppon.foss.module.settlement.common.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.module.settlement.common.api.server.dao.IWaybillChangeMsgEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IWaybillChangeMsgService;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillChangeMsgEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 运单结算业务完结状态-财务变更消息Service
 * @author 000123-foss-huangxiaobo
 * @date 2012-12-3 下午2:34:08
 */
public class WaybillChangeMsgService implements IWaybillChangeMsgService {
	private static final Logger logger = LogManager.getLogger(WaybillChangeMsgService.class);

	/**
	 * 财务变更消息Dao
	 */
	private IWaybillChangeMsgEntityDao waybillChangeMsgEntityDao;
	
	/** 
	 * 添加业务完结消息
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-12-3 上午11:06:49
	 * @param entity
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IWaybillChangeMsgService#addChangeMsg(com.deppon.foss.module.settlement.common.api.shared.domain.WaybillChangeMsgEntity)
	 */
	@Override
	public void addChangeMsg(WaybillChangeMsgEntity entity) {
		
		// 输入参数校验
		if(entity==null||StringUtils.isEmpty(entity.getWaybillNo())
				||entity.getMsgDate()==null
				||StringUtils.isEmpty(entity.getMsgAction())
				||StringUtils.isEmpty(entity.getSourceBillType())
				||StringUtils.isEmpty(entity.getSourceBillNo())
				){
			throw new SettlementException("新增财务变更传入的参数不能为空");
		}
		
		logger.info("---Start 新增财务变更信息，运单号：" + entity.getWaybillNo());
		
		int i = this.waybillChangeMsgEntityDao.addChangeMsg(entity);
		if (i != 1) {
			throw new SettlementException("新增财务变更信息失败");
		}
		
		logger.info("---End 新增财务变更信息");
	}

	/** 
	 * 批量删除业务完结消息
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-12-3 上午11:07:15
	 * @param list 业务完结消息集合
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IWaybillChangeMsgService#deleteChangeMsgByBatch(java.util.List)
	 */
	@Override
	public void deleteChangeMsgByBatch(List<WaybillChangeMsgEntity> list) {
		
		// 输入参数校验
		if (CollectionUtils.isEmpty(list)) {
			throw new SettlementException("输入参数错误");
		}
		
		logger.info("删除消息：");
		
		int i = this.waybillChangeMsgEntityDao.deleteByBatch(list);
		if (i != list.size()) {
			throw new SettlementException("删除财务变更信息失败");
		}
	}
	
	/** 
	 * 查询待处理业务完结消息
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-12-3 上午11:07:39
	 * @param beginTime 开始时间
	 * @param endTime   截止时间
	 * @param limit     限制最大行
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IWaybillChangeMsgService#queryChangeMsgByWaybillNO(java.lang.String)
	 */
	@Override
	public List<WaybillChangeMsgEntity> queryChangeMsgByWaybillNO(
			String waybillNo) {
		
		// 输入参数校验
		if (StringUtils.isEmpty(waybillNo)) {
			throw new SettlementException("运单号不能为空！");
		}
		
		logger.debug("运单号：" + waybillNo);
		
		return this.waybillChangeMsgEntityDao.queryChangeMsgByWaybillNO(waybillNo);
	}

	/** 
	 * 根据运单号查询财务变更信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-5 下午3:47:51
	 * @param waybillNo 运单号
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IWaybillChangeMsgService#queryChangeMsg(java.util.Date, java.util.Date, int)
	 */
	@Override
	public List<WaybillChangeMsgEntity> queryChangeMsg(Date beginTime, Date endTime, int limit) {
		
		// 输入参数校验
		if (beginTime == null || endTime == null) {
			throw new SettlementException("时间参数不合法");
		}
		
		if (limit <= 0) {
			throw new SettlementException("数据限制不合法");
		}
		
		logger.debug("开始时间：" + beginTime + " , 截止时间：" + endTime);
		
		return this.waybillChangeMsgEntityDao.queryChangeMsg(beginTime, endTime, limit);
	}
	
	
	public void setWaybillChangeMsgEntityDao(
			IWaybillChangeMsgEntityDao waybillChangeMsgEntityDao) {
		this.waybillChangeMsgEntityDao = waybillChangeMsgEntityDao;
	}
	
}
