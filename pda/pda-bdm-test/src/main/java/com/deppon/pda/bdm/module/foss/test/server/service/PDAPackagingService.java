package com.deppon.pda.bdm.module.foss.test.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PackagingSupplierEntity;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAPackagingService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAPackagingInfoEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryPDAUnpackResEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.FinishPackResultDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.SerialNoStatusDto;

public class PDAPackagingService implements IPDAPackagingService{

	@Override
	public List<QueryPDAUnpackResEntity> queryPDAUnpackResult(String packedDept) {
		List<QueryPDAUnpackResEntity> list = new ArrayList<QueryPDAUnpackResEntity>();
		QueryPDAUnpackResEntity model = new QueryPDAUnpackResEntity();
		model.setWaybillCreateDept("上海外场");
		model.setWaybillNum("3");
		model.setWayBillNumber("091223341");
		model.setPackStockNum("3");
		List<SerialNoStatusDto> sers = new ArrayList<SerialNoStatusDto>();
		SerialNoStatusDto ser = new SerialNoStatusDto();
		ser.setSerialNo("0001");
		ser.setStatus("包装货区");
		sers.add(ser);
		ser = new SerialNoStatusDto();
		ser.setSerialNo("0002");
		ser.setStatus("外场");
		sers.add(ser);
		model.setSerialNoStatusDto(sers);
		model.setTransportationType("3");
		model.setPredictDepartDate(new Date());
		model.setPackRequire("木架");
		list.add(model);
		return list;
	}

	@Override
	public FinishPackResultDto addPackagingInfo(
			PDAPackagingInfoEntity pdaPackagingInfoEntity) {
		FinishPackResultDto model = new FinishPackResultDto();
		model.setNewSerialNo("007");
		model.setSuccess(true);
		return model;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAPackagingService#queryPackagingSupplierListByEmpCode(java.lang.String)
	 */
	@Override
	public List<PackagingSupplierEntity> queryPackagingSupplierListByEmpCode(
			String empCode) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAPackagingService#queryPackagingSupplierListByEmpCode(java.lang.String)
	 */
	/*@Override
	public List<PackagingSupplierEntity> queryPackagingSupplierListByEmpCode(
			String empCode) {
		// TODO Auto-generated method stub
		return null;
	}*/

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.pda.api.server.service.IPDAPackagingService#queryPackagingSupplierListByEmpCode(java.lang.String)
	 */

/*	@Override
	public List<PackagingSupplierEntity> queryPackagingSupplierListByEmpCode(
			String empCode) {

		return null;
	}*/

}
