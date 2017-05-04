package com.deppon.pda.bdm.module.foss.test.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressDeliverLoadService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressDeliverLoadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressDeliverScanDto;

public class PDAExpressDeliverLoadService implements IPDAExpressDeliverLoadService{

	@Override
	public String cancelLoadTask(String s, String s1, String s2, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PDAAssignLoadTaskEntity> queryExpressDeliverLoadTask(String s,
			String s1, Date date, Date date1) {
		// TODO Auto-generated method stub
		List<PDAAssignLoadTaskEntity> result = new ArrayList<PDAAssignLoadTaskEntity>();
		PDAAssignLoadTaskEntity loadTask = new PDAAssignLoadTaskEntity();
		loadTask.setDeliverBillNo("12323423");
		loadTask.setDestOrgCodes(new ArrayList<String>());
		loadTask.setDestOrgNames(new ArrayList<String>());
		loadTask.setLoadTaskType("DELIVER_LOAD");
		loadTask.setPlatformNo("05");
		loadTask.setState("LOADING");
		loadTask.setTaskNo("022013071800007");
		loadTask.setVehicleNo("沪BE9179");
		result.add(loadTask);
		return result;
	}

	@Override
	public void scan(ExpressDeliverScanDto expressdeliverscandto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String submitLoadTask(String s, Date date, String s1, String s2) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String createTask(ExpressDeliverLoadTaskDto arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressDeliverLoadService#queryWayBillNo(java.lang.String)
	 */
	@Override
	public List<String> queryWayBillNo(String taskNo) {
		List<String> lists = new ArrayList<String>();
		for (int i = 1; i <= 10; i++) {
			lists.add("12345678" + i);
		}
		return lists;
	}

}
