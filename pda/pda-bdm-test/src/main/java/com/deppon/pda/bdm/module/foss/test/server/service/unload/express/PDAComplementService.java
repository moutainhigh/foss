package com.deppon.pda.bdm.module.foss.test.server.service.unload.express;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.pda.api.server.service.IPDAComplementService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAComplementDto;


//查询补码接口实现类test 
public class PDAComplementService implements IPDAComplementService {

	public List<PDAComplementDto> queryComplement(Date arg0, String arg1,
			boolean arg2) {
		List<PDAComplementDto> list=new ArrayList();
		for(int i=0;i<10;i++){
			PDAComplementDto d=new PDAComplementDto();
			d.setWayBillNo("运单号"+i);//运单号
			d.setTargetOrgCode("目的站编码"+i);//目的站编码
			d.setReceiveCustomerAddress("收货地址"+i);//收货地址
			d.setComplementTime(new Date());//时间
			list.add(d);
		}
		return list;
	}

	@Override
	public List<PDAComplementDto> queryComplement(Date arg0, String arg1,
			boolean arg2, String arg3) {
		// TODO Auto-generated method stub
		return null;
	}

}
