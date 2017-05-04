package com.deppon.pda.bdm.module.foss.dprtarr.server.service.impl;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDADepartService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDADepartDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDADepartResultDTO;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DprtarrConstnat;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.dprtarr.shared.domain.DprtTruckInfo;
import com.deppon.pda.bdm.module.foss.dprtarr.shared.domain.DprtTruckScanInfo;

//import com.deppon.pda.bdm.module.foss.dprtarr.ws.DepartImpl;

/**
 * 车辆出发服务类
 * 
 * @author xujun
 * @version 1.0
 * @created 2012-9-13 上午08:45:47
 */
public class TruckDprtService implements
		IBusinessService<DprtTruckInfo, DprtTruckScanInfo> {
	private static final Logger LOG = Logger.getLogger(TruckDprtService.class);

	private IPDADepartService pdaDepartService;

	@Override
	public DprtTruckScanInfo parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		DprtTruckScanInfo dprtTruckScanInfo = JsonUtil.parseJsonToObject(
				DprtTruckScanInfo.class, asyncMsg.getContent());
		return dprtTruckScanInfo;
	}
	@Transactional
	@Override
	public DprtTruckInfo service(AsyncMsg asyncMsg, DprtTruckScanInfo param)
			throws PdaBusiException {
		LOG.debug("invoke depart service start...");
		validate(param);
		// 调用FOSS接口提交数据
		DprtTruckInfo dprtTruckInfo = null;
		try {
			dprtTruckInfo = invokeFossDepartService(asyncMsg, param);
		} catch (BusinessException e) {
			LOG.info("车辆到达服务异常："+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		LOG.debug("invoke depart service end...");
		return dprtTruckInfo;
	}

	private void validate(DprtTruckScanInfo param) {
		Argument.notNull(param, "DprtTruckScanInfo");
		//Argument.hasText(param.getTruckCode(), "DprtTruckScanInfo.truckCode");
		Argument.hasText(param.getRelseCode(), "DprtTruckScanInfo.RelseCode");
		Argument.notNull(param.getScanTime(), "DprtTruckScanInfo.ScanTime");
	}

	@Override
	public String getOperType() {
		// 任务类型:出发
		return DprtarrConstnat.OPER_TYPE_DPRT_INFO_SCAN.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	private DprtTruckInfo invokeFossDepartService(AsyncMsg asyncMsg,
			DprtTruckScanInfo param) {
		// 构造FOSS接口调用参数
		PDADepartDto departDto = new PDADepartDto();
		departDto.setOperatingTime(param.getScanTime());
		departDto.setOperator(asyncMsg.getUserCode());
		departDto.setOrgCode(asyncMsg.getDeptCode());
		departDto.setPdaTerminalNo(asyncMsg.getPdaCode());
		departDto.setVehicleNo(param.getRelseCode());

		// 调用FOSS服务
		// IPDADepartService departService = new DepartImpl(); //模拟代码
		PDADepartResultDTO departResultDTO = pdaDepartService
				.writeDepartData(departDto);

		// 封装FOSS返回数据
		DprtTruckInfo dprtTruckInfo = new DprtTruckInfo();
		dprtTruckInfo.setDriverName(departResultDTO.getDriverName());
		dprtTruckInfo.setDriverPhone(departResultDTO.getDriverPhone());
		
		//交接单号需要判空  mt 2013年5月21日17:13:26
		if(departResultDTO.getHandoverbills() != null){
			dprtTruckInfo.setReceiptCodes(departResultDTO.getHandoverbills()
					.toString());
		}
		dprtTruckInfo.setRelseCause(departResultDTO.getDepartItems()); // ???
		dprtTruckInfo.setRelseTime(departResultDTO.getDepartTime());
		dprtTruckInfo.setRelseType(departResultDTO.getDepartType());
		dprtTruckInfo.setRelseUserCode(departResultDTO.getDepartUser());
		//封签号需要判空 mt 2013年5月24日16:50:51
		if(departResultDTO.getSealNos() != null){
			dprtTruckInfo.setSealsCodes(departResultDTO.getSealNos().toString());
		}
		dprtTruckInfo.setStatus(departResultDTO.getStatus());
		dprtTruckInfo.setTruckCode(departResultDTO.getVehicleNo());
		dprtTruckInfo.setTruckStatus(departResultDTO.getVehicleStatus());
		return dprtTruckInfo;
	}

	public void setPdaDepartService(IPDADepartService pdaDepartService) {
		this.pdaDepartService = pdaDepartService;
	}

}
