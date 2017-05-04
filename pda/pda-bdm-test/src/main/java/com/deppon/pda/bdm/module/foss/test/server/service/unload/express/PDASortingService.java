package com.deppon.pda.bdm.module.foss.test.server.service.unload.express;

import com.deppon.foss.module.transfer.pda.api.server.service.IPDASortingService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.SortingScanDto;

public class PDASortingService implements IPDASortingService {
	@Override
	public void scan(SortingScanDto arg0) {
		System.out.println("=============调用分拣扫描本地接口====================");
		System.out.println(arg0.getDeviceNo());
		System.out.println(arg0.getOperatorCode());
		System.out.println(arg0.getOrgCode());
		System.out.println(arg0.getScanType());
		System.out.println(arg0.getSerialNo());
		System.out.println(arg0.getWayBillNo());
		System.out.println(arg0.getScanTime());
	}

	@Override
	public void scanPackage(SortingScanDto record) {
		// TODO Auto-generated method stub
		
	}

}
