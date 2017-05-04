package com.deppon.pda.bdm.module.foss.dprtarr.server.service.impl;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAArriveService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDADepartDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DprtarrConstnat;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.dprtarr.server.dao.IDprtArrDAO;
import com.deppon.pda.bdm.module.foss.dprtarr.shared.domain.ArrivalScanEntity;

//import com.deppon.pda.bdm.module.foss.dprtarr.ws.ArriveImpl;

/**
 * 车辆到达服务类
 * 
 * @author xujun
 * @version 1.0
 * @created 2012-9-13 上午08:45:13
 */
public class TruckArrService implements
		IBusinessService<String, ArrivalScanEntity> {
	private static final Logger LOG = Logger.getLogger(TruckArrService.class);

	private IPDAArriveService pdaArriveService;

	private IDprtArrDAO dprtArrDAO;
	
	public void setDprtArrDAO(IDprtArrDAO dprtArrDAO) {
		this.dprtArrDAO = dprtArrDAO;
	}

	@Override
	public ArrivalScanEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		Argument.notNull(asyncMsg, "AsyncMsg");
		ArrivalScanEntity arrivalScan = JsonUtil.parseJsonToObject(
				ArrivalScanEntity.class, asyncMsg.getContent());
		arrivalScan.setDeptCode(asyncMsg.getDeptCode()); // 部门
		arrivalScan.setPdaCode(asyncMsg.getPdaCode());   // PDA
		arrivalScan.setScanUser(asyncMsg.getUserCode()); // user
		arrivalScan.setScanType(asyncMsg.getOperType()); // 操作类型
		
		arrivalScan.setUploadTime(asyncMsg.getUploadTime());
		return arrivalScan;
	}
	@Transactional
	@Override
	public String service(AsyncMsg asyncMsg, ArrivalScanEntity param)
			throws PdaBusiException {
		LOG.debug("invoke arrive service start ...");
		this.validate(param);
		// 发送数据到FOSS
		String result = null;
		try {
			long startTime = System.currentTimeMillis();
			result = invokeFossArrvieService(param);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			LOG.info("[asyncinfo]车辆到达接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			LOG.info("车辆到达服务异常："+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		// 保存扫描数据
		dprtArrDAO.saveArrivalScan(param);
		LOG.debug("invoke arrive service end ...");
		return result;
	}

	@Override
	public String getOperType() {
		return DprtarrConstnat.OPER_TYPE_ARR_INFO_SCAN.VERSION;
	}

	@Override
	public boolean isAsync() {
		return true; 
	}

	private String invokeFossArrvieService(ArrivalScanEntity param) {
		PDADepartDto departDto = new PDADepartDto();
		departDto.setOperatingTime(param.getScanTime()); // 扫描时间
		departDto.setOperator(param.getScanUser());   // 用户工号
		departDto.setOrgCode(param.getDeptCode());    // 部门编号
		departDto.setPdaTerminalNo(param.getPdaCode());// PDA编码
		departDto.setVehicleNo(param.getRelseCode());	  // 车牌号

		// 调用FOSS 服务
		String result = pdaArriveService.writeArriveData(departDto);
		return result;
	}

	public void setPdaArriveService(IPDAArriveService pdaArriveService) {
		this.pdaArriveService = pdaArriveService;
	}

	private void validate(ArrivalScanEntity param) {
		Argument.notNull(param, "ArrivalScanEntity");
		//Argument.hasText(param.getTruckCode(), "ArrivalScanEntity.truckCode");
		Argument.hasText(param.getRelseCode(), "ArrivalScanEntity.relseCode");
		Argument.hasText(param.getSealsStatus(),
				"ArrivalScanEntity.sealsStatus");
		Argument.notNull(param.getSealsBreaks(),
				"ArrivalScanEntity.sealsBreaks");
//		Argument.hasText(param.getScanFlag(),
//				"ArrivalScanEntity.ScanFlag");
		Argument.notNull(param.getScanTime(),
				"ArrivalScanEntity.ScanTime");
	}
}
