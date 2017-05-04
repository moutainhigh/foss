package com.deppon.pda.bdm.module.foss.test.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignUnloadBillEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignUnloadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoaderDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAGoodsSerialNoDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadGoodsDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadScanDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadTaskResultDto;

public class PDAUnldService implements IPDAUnloadTaskService{

	@Override
	public List<PDAAssignUnloadTaskEntity> pdaQueryAssignedUnloadTask(
			String loaderCode, String loadOrgCode) {
		List<PDAAssignUnloadTaskEntity> res = new ArrayList<PDAAssignUnloadTaskEntity>();
		PDAAssignUnloadTaskEntity e = new PDAAssignUnloadTaskEntity();
		e.setVehicleNo("粤AC3111");
		List<PDAAssignUnloadBillEntity> bills = new ArrayList<PDAAssignUnloadBillEntity>();
		PDAAssignUnloadBillEntity en = new PDAAssignUnloadBillEntity();
		en.setPlatformNo("123");
		en.setBillNo("123456p");
		en.setState("UNSTART");
		en.setUnloadOrderType("GOODS_BACK");
		bills.add(en);
		en = new PDAAssignUnloadBillEntity();
		en.setPlatformNo("123");
		en.setBillNo("234457");
		en.setState("UNSTART");
		en.setUnloadOrderType("RECEIVE");
		bills.add(en);
		e.setBills(bills);
		res.add(e);
		return res;
	}
	
	@Override
	public List<UnloadGoodsDetailDto> refrushUnloadTaskDetail(String taskNo) {
		List<UnloadGoodsDetailDto> res = new ArrayList<UnloadGoodsDetailDto>();
		for (int j = 0; j < 1; j++) {
			UnloadGoodsDetailDto en = new UnloadGoodsDetailDto();
			en.setBeContraband("N");
			en.setBePriorityGoods("Y");
			en.setBillNo("6000112345");
			//en.setCreatorCode("056281");
			en.setDestOrgCode("W011304040208");
			en.setGoodsName("小龙");
			//en.setIsModify("Y");
			en.setIsValue("Y");
			//en.setModifyContent("目的地更改");
			en.setOrigOrgCode("W011304040208");
			en.setPacking("木架");
			en.setReachOrgCode("W011305080107");
			en.setReachOrgName("北京顺义区通顺路营业部");
			en.setReceiveOrgCode("W011304040208");
			en.setReceiveOrgName("惠州博罗县园洲营业部");
			en.setStockTime(new Date());
			en.setTaskNo("011212281234");
			en.setTransportType("精准汽运");
			en.setVolume(1000);
			if(j<10){
				en.setWayBillNo("2222000"+j);
			}else if(j>=10&&j<100){
				en.setWayBillNo("222200"+j);
			}else if(j>=100&&j<1000){
				en.setWayBillNo("22220"+j);
			}else{
				en.setWayBillNo("2222"+j);
			}
			en.setWeight(1000);
			en.setOperateQty(1);
			en.setNotes("代打木架");
			List<PDAGoodsSerialNoDto> sers = new ArrayList<PDAGoodsSerialNoDto>();
			for (int i = 1; i < 356; i++) {
				PDAGoodsSerialNoDto ser = new PDAGoodsSerialNoDto();
				if(i>100&&i<300){
					continue;
				}
				if(i%2==0){
					ser.setIsUnPacking("Y");
				}else{
					ser.setIsUnPacking("N");
				}
				if(i<10){
					ser.setSerialNo("000"+i);
				}else if(i<100){
					ser.setSerialNo("00"+i);
				}else if(i<1000){
					ser.setSerialNo("0"+i);
				}
				sers.add(ser);
			}
			en.setSerialNos(sers);
			res.add(en);
		}
		return res;
	}

	@Override
	public String cancelUnloadTask(String taskNo, String deviceNo,
			String operatorCode, Date cancelTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String finishUnloadTask(String taskNo, Date loadEndTime,
			String deviceNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String submitUnloadTask(String taskNo, Date loadEndTime,
			String deviceNo, String loaderCode, String beException, String notes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String forceSubmitUnloadTask(String taskNo, Date loadEndTime,
			String deviceNo, String loaderCode, String beException, String notes) {
		return null;
	}

	@Override
	public String unloadScan(UnloadScanDetailDto scanRecord) {
		return null;
	}

	@Override
	public String modifyLoader(String taskNo, List<LoaderDto> loaderCode,
			Date operateTime, String loaderState) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public UnloadTaskResultDto createLoadTask(UnloadTaskDto unloadTask) {
		UnloadTaskResultDto dto = new UnloadTaskResultDto();
		dto.setCreatorCode(unloadTask.getOperatorCode());
		dto.setTaskNo("011212281234");
		dto.setLoaders(unloadTask.getLoaderCodes());
		return dto;
	}

@Override
	public UnloadGoodsDetailDto moreGoodsUnloadVerify(String arg0, String arg1,
			String arg2) {
	UnloadGoodsDetailDto en = new UnloadGoodsDetailDto();
	en.setBeContraband("N");
	en.setBePriorityGoods("Y");
	en.setBillNo("6000112345");
	//en.setCreatorCode("056281");
	en.setDestOrgCode("W011304040208");
	en.setGoodsName("小龙");
	//en.setIsModify("Y");
	en.setIsValue("Y");
	//en.setModifyContent("目的地更改");
	en.setOrigOrgCode("W011304040208");
	en.setPacking("木架");
	en.setReachOrgCode("W011305080107");
	en.setReachOrgName("北京顺义区通顺路营业部");
	en.setReceiveOrgCode("W011304040208");
	en.setReceiveOrgName("惠州博罗县园洲营业部");
	en.setStockTime(new Date());
	en.setTaskNo(arg0);
	en.setTransportType("精准汽运");
	en.setVolume(1000);
	
	en.setWeight(1000);
	en.setOperateQty(1);
	en.setNotes("代打木架");
	en.setWayBillNo(arg1);
	List<PDAGoodsSerialNoDto> sers = new ArrayList<PDAGoodsSerialNoDto>();
		PDAGoodsSerialNoDto ser = new PDAGoodsSerialNoDto();
		ser.setIsUnPacking("N");
		ser.setSerialNo(arg2);
		sers.add(ser);
	en.setSerialNos(sers);
	return en;
	}

@Override
public List<UnloadGoodsDetailDto> refrushUnloadTaskPackage(String taskNo) {
	// TODO Auto-generated method stub
	return null;
}

	

}
