package com.deppon.pda.bdm.module.foss.clear.server.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.ClearConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.clear.server.dao.IClearDao;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.ClearScanEntity;

/**
 * 
  * @ClassName ClearScanService 
  * @Description 清仓扫描
  * @author xujun dpxj@deppon.com 
  * @date 2013-1-10 下午7:02:49
 *
 */
public class ClearScanService implements IBusinessService<Void, ClearScanEntity> {
	private static final Log LOG = LogFactory.getLog(ClearScanService.class);
	public static final int FOUR = 4;
	private IClearDao clearDao;
	
	private IPdaStockcheckingService pdaStockcheckingService;
	
	@Transactional
	@Override
	public ClearScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		ClearScanEntity clearScanEntity = JsonUtil.parseJsonToObject(ClearScanEntity.class, asyncMsg.getContent());
		clearScanEntity.setDeptCode(asyncMsg.getDeptCode()); // 部门
		clearScanEntity.setPdaCode(asyncMsg.getPdaCode());   // PDA
		clearScanEntity.setScanUser(asyncMsg.getUserCode()); // user
		clearScanEntity.setScanType(asyncMsg.getOperType()); // 操作类型
		clearScanEntity.setId(asyncMsg.getId());			  // ID
		clearScanEntity.setUploadTime(asyncMsg.getUploadTime()); // 上传时间
		return clearScanEntity;
	}

	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, ClearScanEntity clearScanEntity)
			throws PdaBusiException {
		LOG.info("clear scan :"+clearScanEntity.getLabelCode());
		this.validate(asyncMsg,clearScanEntity);
		try {
			long startTime = System.currentTimeMillis();
			pdaStockcheckingService.scanStTaskDetail(clearScanEntity.getTaskCode(),
					clearScanEntity.getWblCode(),
					clearScanEntity.getLabelCode().substring(clearScanEntity.getLabelCode().length()-FOUR), 
					clearScanEntity.getScanStatus(), 
					clearScanEntity.getScanTime(), 
					asyncMsg.getUserCode(), 
					asyncMsg.getPdaCode(),
					clearScanEntity.getPosition());// 库位号 2013-08-14 xujun
			
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			LOG.info("[asyncinfo]清仓扫描接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			LOG.error("扫描数据异常："+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		//保存扫描数据
		clearDao.saveScanMsg(clearScanEntity);
		LOG.info("clear scan success...");
		return null;
	}
	
	
	private void validate(AsyncMsg asyncMsg, ClearScanEntity param) {
		Argument.notNull(param, "ClearScanEntity");
		Argument.hasText(param.getTaskCode(), "ClearScanEntity.TaskCode");
		Argument.hasText(param.getWblCode(), "ClearScanEntity.WblCode");
		Argument.hasText(param.getScanStatus(), "ClearScanEntity.ScanStatus");
		Argument.hasText(asyncMsg.getOperType(), "AsyncMsg.OperType");
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.PdaCode");
	}

	@Override
	public String getOperType() {
		return ClearConstant.OPER_TYPE_CLEAR_SCAN.VERSION;
	}

	@Override
	public boolean isAsync() {
		return true;
	}

	public void setClearDao(IClearDao clearDao) {
		this.clearDao = clearDao;
	}

	public void setPdaStockcheckingService(
			IPdaStockcheckingService pdaStockcheckingService) {
		this.pdaStockcheckingService = pdaStockcheckingService;
	}

}
