package com.deppon.pda.bdm.module.foss.test.server.service.clear;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.module.transfer.pda.api.server.service.IPdaDifferReportService;
import com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PdaDifferDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PdaDifferEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAGoodsSerialNoDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PdaStTaskDto;

public class PdaDifferReportService implements IPdaDifferReportService {
	private static final Log LOG = LogFactory
			.getLog(PdaDifferReportService.class);

    @Override
    public List<PdaDifferEntity> queryDifferReportList(String deptCode, String operatorCode, String pdaNo) {
        List<PdaDifferEntity>  differs = new ArrayList<PdaDifferEntity>();
        PdaDifferEntity df = new PdaDifferEntity();
        df.setGoodsAreaName("货区名称");
        df.setLessQty(10);
        df.setReportCode("Abcdesfdse");
        differs.add(df);
        return differs;
    }



    @Override
    public boolean handleDifferDetail(String reportCode, String waybillNo, String serialNo, Date scanTime,
            String operatorCode, String pdaNo) {
     
        return false;
    }

    @Override
    public boolean commitHandleTask(String reportCode, String operatorCode, String pdaNo) {
        
        return false;
    }

	@Override
	public List<PdaDifferDetailEntity> queryDifferDetailByReportNo(
			String reportNo, String deptCode, String operatorCode,
			String pdaNo, String handInputFlg) {
		  List<PdaDifferDetailEntity>  differs = new ArrayList<PdaDifferDetailEntity>();
	        PdaDifferDetailEntity df = new PdaDifferDetailEntity();
	       df.setWaybillNo("1233451231");
	       df.setSerialNo("0001");
	       df.setHandleStatus("Y");
	       df.setDestStation("W123912391");
	        differs.add(df);
	        
	        df = new PdaDifferDetailEntity();
	        df.setWaybillNo("1231344316");
	        df.setSerialNo("0001");
	        df.setHandleStatus("Y");
	        df.setDestStation("W123912391");
	        differs.add(df);
	        
	        
	        return differs;
	}


}
