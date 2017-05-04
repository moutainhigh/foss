package com.deppon.pda.bdm.module.foss.test.server.service.unload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadDiffReportService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAUnloadDiffReportDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAUnloadDiffReportDto;

public class PdaUnloadDiffReportService implements IPDAUnloadDiffReportService{

	@Override
	public List<PDAUnloadDiffReportDto> queryPDAUnloadDiffReportList(
			String deptCode, String operatorCode, String pdaNo) {
		
		 List<PDAUnloadDiffReportDto> lists = new ArrayList<PDAUnloadDiffReportDto>();
		 PDAUnloadDiffReportDto dto = new PDAUnloadDiffReportDto();
		 dto.setDiffReportNo("dsfsf");
		 dto.setLackGoodsPieces(10);
		 dto.setReportCreateTime(new Date());
		 dto.setVehicleNo("车牌号");
		 
		 lists.add(dto);
		 
		 dto = new PDAUnloadDiffReportDto();
		 dto.setDiffReportNo("dsfsf");
		 dto.setLackGoodsPieces(10);
		 dto.setReportCreateTime(new Date());
		 dto.setVehicleNo("车牌号");
		 
		 lists.add(dto);
		 
		 
		return lists;
	}

	@Override
	public List<PDAUnloadDiffReportDetailDto> queryPDAUnloadDiffReportDetailList(
			String reportCode, String operatorCode, String pdaNo,
			String handInputFlg, String deptCode) {
		
		List<PDAUnloadDiffReportDetailDto> lists = new ArrayList<PDAUnloadDiffReportDetailDto>();
		PDAUnloadDiffReportDetailDto dto = new PDAUnloadDiffReportDetailDto();
		dto.setSerialNo("0002");
		dto.setStatus("Y");
		dto.setTargetOrg("W120321");
		dto.setWaybillNo("5123456789");
		lists.add(dto);
		
		
		dto = new PDAUnloadDiffReportDetailDto();
		dto.setSerialNo("0001");
		dto.setStatus("N");
		dto.setTargetOrg("W120321");
		dto.setWaybillNo("5123456789");
		lists.add(dto);
		
		
		return lists;
	}

	@Override
	public int handleUnloadDiffReport(String deptCode, String reportCode,
			String waybillNo, String serialNo, Date ScanTime,
			String operatorCode, String PdaNo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int commitUnloadDiffReport(String reportCode, String operatorCode,
			String pdaNo) {
		// TODO Auto-generated method stub
		return 0;
	}

}
