package com.deppon.foss.module.transfer.dubbo.api.service.impl.expose;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.dubbo.crm.api.define.masterdata.CrmSyncDataEntity;
import com.deppon.foss.dubbo.crm.api.define.masterdata.CustCustbasedata;
import com.deppon.foss.dubbo.crm.api.service.ISyncCustomerInfoService4dubbo;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICrmpushfossLogService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CrmpushfossLogEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.transfer.dubbo.api.service.ISyncInfosFromCrmService4dubbo;

public class SyncCustomerInfoService4dubbo implements ISyncCustomerInfoService4dubbo {
	private static final Logger LOGGER = LogManager.getLogger(SyncCustomerInfoService4dubbo.class);
	private ICrmpushfossLogService crmpushfossLogService;
	private IBusinessLockService businessLockService;
	private ISyncInfosFromCrmService4dubbo syncInfosFromCrmService;

	@Override
	public boolean syncCrmCustomerMasterInfo(CrmSyncDataEntity crmSyncRequest) {
		LOGGER.info("开始进入syncCrmCustomerMasterInfo");
		CustCustbasedata custbasedata = null;
		CrmpushfossLogEntity crmpushfossLog = new CrmpushfossLogEntity();
		LOGGER.info("请求参数:" + crmSyncRequest);
		// 把CRM传递过来的信息传入日志记录表 2015-07-13 css 261997
		crmpushfossLog.setContent(crmSyncRequest.toString());
		crmpushfossLog.setCreateTime(new Date());

		custbasedata = crmSyncRequest.gettCustCustbasedata();

		if (null != custbasedata) {
			// 业务锁
			MutexElement mutex = new MutexElement(String.valueOf(custbasedata.getFid()), "CUSTOMER_CRMID",
					MutexElementType.CUSTOMER_CRMID);
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			crmpushfossLog.setCrmCusId(BigDecimal.valueOf(Long.valueOf(mutex.getBusinessNo())));
			boolean result = getBusinessLockService().lock(mutex, 0);
			if (result) {
				LOGGER.info("成功加锁：" + mutex.getBusinessNo());
			} else {
				LOGGER.info("失败加锁：" + mutex.getBusinessNo());
				crmpushfossLog.setFalsereason("失败加锁：" + mutex.getBusinessNo());
				crmpushfossLog.setResult("false");
				crmpushfossLog.setTrueorfalse("0");
				getCrmpushfossLogService().insert(crmpushfossLog);
				return false;
			}
			// 客户同步主数据处理
			boolean res = false;
			try {
				res = getSyncInfosFromCrmService().syncInfo(crmSyncRequest);
				LOGGER.info("主数据同步完成：" + crmSyncRequest);
			} catch (Exception e) {
				crmpushfossLog.setFalsereason("客户主数据为空！.......");
				crmpushfossLog.setResult(""+false);
				crmpushfossLog.setTrueorfalse("0");
				crmpushfossLog.setCode(custbasedata.getFcustnumber());
				crmpushfossLog.setFalsereason("ORA-00001: 违反唯一约束条件 (BSE.UQ_CRMID)!");
				getCrmpushfossLogService().insert(crmpushfossLog);
				e.printStackTrace();
			}

			LOGGER.info("开始解锁：" + mutex.getBusinessNo());
			// 解业务锁
			getBusinessLockService().unlock(mutex);
			LOGGER.info("完成解锁：" + mutex.getBusinessNo());
			crmpushfossLog.setResult("" + res);
			crmpushfossLog.setTrueorfalse("1");
			crmpushfossLog.setCode(custbasedata.getFcustnumber());
			getCrmpushfossLogService().insert(crmpushfossLog);
			LOGGER.info("主数据log：" + crmpushfossLog);
			return res;
		} else {
			LOGGER.info("客户主数据为空！.......");
			crmpushfossLog.setFalsereason("客户主数据为空！.......");
			crmpushfossLog.setResult("false");
			crmpushfossLog.setTrueorfalse("0");
			getCrmpushfossLogService().insert(crmpushfossLog);
			return false;
		}
	}

	public ICrmpushfossLogService getCrmpushfossLogService() {
		return crmpushfossLogService;
	}

	@Autowired
	public void setCrmpushfossLogService(ICrmpushfossLogService crmpushfossLogService) {
		this.crmpushfossLogService = crmpushfossLogService;
	}

	public IBusinessLockService getBusinessLockService() {
		return businessLockService;
	}

	@Autowired
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public ISyncInfosFromCrmService4dubbo getSyncInfosFromCrmService() {
		return syncInfosFromCrmService;
	}
	@Autowired
	public void setSyncInfosFromCrmService(ISyncInfosFromCrmService4dubbo syncInfosFromCrmService) {
		this.syncInfosFromCrmService = syncInfosFromCrmService;
	}

}
