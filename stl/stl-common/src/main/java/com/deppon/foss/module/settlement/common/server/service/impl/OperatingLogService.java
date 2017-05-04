package com.deppon.foss.module.settlement.common.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IOperatingLogEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IOperatingLogService;
import com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.UUIDUtils;

/**
 * 
 * 操作日志
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-11-8 上午10:51:45
 */
public class OperatingLogService implements IOperatingLogService {

	private static final Logger logger = LogManager
			.getLogger(OperatingLogService.class);

	/**
	 * 操作日志Dao
	 */
	private IOperatingLogEntityDao operatingLogEntityDao;

	/**
	 * 
	 * 新加操作日志
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-8 上午10:52:00
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IOperatingLogService#addOperatingLog(com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity)
	 */
	@Override
	public void addOperatingLog(OperatingLogEntity item, CurrentInfo currentInfo) {

		logger.info("开始插入日志！");

		if (item != null && StringUtils.isNotEmpty(item.getOperateBillNo())) {
			// 设置Id
			item.setId(UUIDUtils.getUUID());
			// 设置操作时间
			Date sysDate = new Date();
			item.setCreateDate(sysDate);
			item.setOperateTime(sysDate);
			// 操作组织编码
			item.setOperateOrgCode(currentInfo.getCurrentDeptCode());
			// 操作组织名称
			item.setOperateOrgName(currentInfo.getCurrentDeptName());
			// 操作用户名称
			item.setOperatorName(currentInfo.getEmpName());
			// 操作用户编码
			item.setOperatorCode(currentInfo.getEmpCode());
			// 插入日志
			this.operatingLogEntityDao.addOperatingLog(item);

			logger.info("插入日志结束！");
		} else {
			logger.info("插入日志出错,操作单据号为空！");

			throw new SettlementException("内部错误，操作单据号为空！");
		}
	}

	/**
	 * 根据操作单据编号进行查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-8 上午11:23:52
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IOperatingLogService#queryByOperateBillNo(java.lang.String)
	 */
	@Override
	public List<OperatingLogEntity> queryByOperateBillNo(String billNo) {
		if (!StringUtils.isEmpty(billNo)) {
			return this.operatingLogEntityDao.queryByOperateBillNo(billNo);
		} else {
			throw new SettlementException("内部错误，参数为空");
		}
	}

	/**
	 * @param operatingLogEntityDao
	 *            the operatingLogEntityDao to set
	 */
	public void setOperatingLogEntityDao(
			IOperatingLogEntityDao operatingLogEntityDao) {
		this.operatingLogEntityDao = operatingLogEntityDao;
	}

	/**
	 * 新增多条操作日志信息
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-25 下午2:27:56
	 * @param list
	 * @return
	 */
	@Override
	public void addBatchOperatingLog(List<OperatingLogEntity> list) {
		if(CollectionUtils.isEmpty(list)){
			throw new SettlementException("新增多条操作日志信息,参数不能为空！");
		}
		logger.info(" Start 新增多条操作日志信息 ");
		
		this.operatingLogEntityDao.addBatchOperatingLog(list);
		
		logger.info(" end 新增多条操作日志信息 ");
	}

}
