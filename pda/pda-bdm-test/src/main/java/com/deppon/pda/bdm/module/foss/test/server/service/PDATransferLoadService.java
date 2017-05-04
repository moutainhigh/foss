package com.deppon.pda.bdm.module.foss.test.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDADeliverLoadService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadGoodsDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadScanDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskResultDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoaderDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAGoodsSerialNoDto;



public class PDATransferLoadService implements IPDADeliverLoadService{

	@Override
	public String cancelLoadTask(String arg0, String arg1, String arg2,
			Date arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String finishLoadTask(String arg0, Date arg1, String arg2,
			String arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String forceSubmitLoadTask(String arg0, Date arg1, String arg2,
			String arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String loadScan(LoadScanDetailDto arg0) {
		if(arg0.getSerialNo().equals("0001")){
			//throw new BusinessException();
		}else{
			//throw new RuntimeException();
		}
		return null;
	}

	@Override
	public List<LoadGoodsDetailDto> refrushLoadTaskDetail(String arg0) {
		List<LoadGoodsDetailDto> res = new ArrayList<LoadGoodsDetailDto>();
		LoadGoodsDetailDto detail = null;
		for (int j = 1; j < 10; j++) {
			detail = new LoadGoodsDetailDto();
			detail.setReceiveOrgCode("W01140503030102");
			detail.setReceiveOrgName("广州火车站营业部");
			detail.setReachOrgCode("W011305080107");
			detail.setReachOrgName("北京顺义区通顺路营业部");
			detail.setGoodsName("小龙");
			detail.setStockTime(new Date());
			detail.setBePriorityGoods("N");
			detail.setPacking("木箱");
			detail.setNotes("代打木架");
			detail.setTaskNo(arg0);
			detail.setTransportType("精准汽运");
			detail.setVolume(100);
			if(j<10){
				detail.setWayBillNo("082200000"+j);
			}else if(j>=10&&j<100){
				detail.setWayBillNo("08220000"+j);
			}else if(j>=100&&j<1000){
				detail.setWayBillNo("0822000"+j);
			}
			detail.setWeight(100);
			detail.setIsValue("Y");
			//detail.setModifyContent("目的地更改");
			//detail.setIsModify("Y");
			//detail.setCreatorCode(arg0.getOperatorCode());
			//detail.setVehicleDeadWeight(1000);
			//detail.setVehicleDeadVolume(1000);
			List<PDAGoodsSerialNoDto> sers = new ArrayList<PDAGoodsSerialNoDto>();
			PDAGoodsSerialNoDto  ser = null;
			for (int i = 1; i <= 1; i++) {
				ser = new PDAGoodsSerialNoDto();
				ser.setIsUnPacking("Y");
				if(i<10){
					ser.setSerialNo("000"+i);
				}else{
					ser.setSerialNo("00"+i);
				}
				if(i%2==0){
					ser.setIsUnPacking("N");
				}else{
					ser.setIsUnPacking("Y");
				}
				if(i==10||i==12||i==17){
					ser.setStockAreaCode("0001");
				}else if(i==15||i==34){
					ser.setStockAreaCode("0002");
				}else{
					ser.setStockAreaCode("0003");
				}
				//ser.setHasToDoList("N");
				sers.add(ser);
			}
			detail.setSerialNos(sers);
			detail.setStockQty(10);
			detail.setBeJoinCar("Y");
			//detail.setCreatorCode(arg0.getOperatorCode());
			//List<LoaderDto> userCodes = arg0.getLoaderCodes();
			//detail.setLoaders(userCodes);
			detail.setWayBillQty(10);
			//detail.setVehicleDeadWeight(1000);
			//detail.setVehicleDeadVolume(1000);
			detail.setPackGoodsAreaCode("0001");
			detail.setValueGoodsAreaCode("0002");
			detail.setOperateQty(new Random().nextInt(10));
			detail.setReceiveCustDistName("闵行区");//行政区域
			res.add(detail);
		}
		return res;
	}

	@Override
	public String modifyLoader(String taskNo, List<LoaderDto> loaderCode,
			Date operateTime, String loaderState) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String submitLoadTask(String taskNo, Date loadEndTime,
			String deviceNo, String loaderCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PDAAssignLoadTaskEntity> queryAssignedLoadTask(
			QueryAssignedLoadTaskEntity condition) {
		List<PDAAssignLoadTaskEntity> res = new ArrayList<PDAAssignLoadTaskEntity>();
		PDAAssignLoadTaskEntity en = new PDAAssignLoadTaskEntity();
		en.setDeliverBillNo("1232342308");
		en.setLoadTaskType("DELIVER_LOAD");
		en.setPlatformNo("0001");
		en.setState("LOADING");
		en.setVehicleNo("粤ASDCCV");
		en.setTaskNo("1245756757");
		res.add(en);
		return res;
	}

	@Override
	public String deliverLoadNotes(LoadScanDetailDto notesRecord) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public LoadTaskResultDto createLoadTask(LoadTaskDto loadTask) {
		LoadTaskResultDto dto = new LoadTaskResultDto();
		dto.setCreatorCode(loadTask.getOperatorCode());
		dto.setLoaders(loadTask.getLoaderCodes());
		dto.setTaskNo("12334567780008");
		dto.setVehicleDeadVolume(1000);
		dto.setVehicleDeadWeight(1000);
		return dto;
	}




	@Override
	public LoadGoodsDetailDto moreGoodsLoadScan(LoadScanDetailDto scanRecord) {
		LoadGoodsDetailDto detail = new LoadGoodsDetailDto();
		detail.setReceiveOrgCode("W01140503030102");
		detail.setReceiveOrgName("广州火车站营业部");
		detail.setReachOrgCode("W011305080107");
		detail.setReachOrgName("北京顺义区通顺路营业部");
		detail.setGoodsName("小龙");
		detail.setStockTime(new Date());
		detail.setBePriorityGoods("N");
		detail.setPacking("木箱");
		detail.setNotes("代打木架");
		detail.setTaskNo(scanRecord.getTaskNo());
		detail.setTransportType("精准汽运");
		detail.setVolume(100);
		detail.setWayBillNo(scanRecord.getWayBillNo());
		detail.setWeight(100);
		detail.setIsValue("Y");
		//detail.setModifyContent("目的地更改");
		//detail.setIsModify("Y");
		//detail.setCreatorCode(arg0.getOperatorCode());
		//detail.setVehicleDeadWeight(1000);
		//detail.setVehicleDeadVolume(1000);
		List<PDAGoodsSerialNoDto> sers = new ArrayList<PDAGoodsSerialNoDto>();
		PDAGoodsSerialNoDto  ser = null;
			ser = new PDAGoodsSerialNoDto();
			ser.setIsUnPacking("Y");
				ser.setStockAreaCode("0003");
			//ser.setHasToDoList("N");
			sers.add(ser);
		detail.setSerialNos(sers);
		detail.setStockQty(10);
		detail.setBeJoinCar("Y");
		//detail.setCreatorCode(arg0.getOperatorCode());
		//List<LoaderDto> userCodes = arg0.getLoaderCodes();
		//detail.setLoaders(userCodes);
		detail.setWayBillQty(10);
		//detail.setVehicleDeadWeight(1000);
		//detail.setVehicleDeadVolume(1000);
		detail.setPackGoodsAreaCode("0001");
		detail.setValueGoodsAreaCode("0002");
		detail.setOperateQty(new Random().nextInt(10));
		return detail;
	}

	@Override
	public List<LoadGoodsDetailDto> refrushLoadTaskPackageDetail(String taskNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LoadGoodsDetailDto> refrushLoadTaskExpressDetail(String taskNo) {
		List<LoadGoodsDetailDto> res = new ArrayList<LoadGoodsDetailDto>();
		LoadGoodsDetailDto detail = null;
		for (int j = 1; j < 10; j++) {
			detail = new LoadGoodsDetailDto();
			detail.setReceiveOrgCode("W01140503030102");
			detail.setReceiveOrgName("广州火车站营业部");
			detail.setReachOrgCode("W011305080107");
			detail.setReachOrgName("北京顺义区通顺路营业部");
			detail.setGoodsName("小龙");
			detail.setStockTime(new Date());
			detail.setBePriorityGoods("N");
			detail.setPacking("木箱");
			detail.setNotes("代打木架");
			detail.setTaskNo(taskNo);
			detail.setTransportType("精准汽运");
			detail.setVolume(100);
			if(j<10){
				detail.setWayBillNo("082200000"+j);
			}else if(j>=10&&j<100){
				detail.setWayBillNo("08220000"+j);
			}else if(j>=100&&j<1000){
				detail.setWayBillNo("0822000"+j);
			}
			detail.setWeight(100);
			detail.setIsValue("Y");
			//detail.setModifyContent("目的地更改");
			//detail.setIsModify("Y");
			//detail.setCreatorCode(arg0.getOperatorCode());
			//detail.setVehicleDeadWeight(1000);
			//detail.setVehicleDeadVolume(1000);
			List<PDAGoodsSerialNoDto> sers = new ArrayList<PDAGoodsSerialNoDto>();
			PDAGoodsSerialNoDto  ser = null;
			for (int i = 1; i <= 1; i++) {
				ser = new PDAGoodsSerialNoDto();
				ser.setIsUnPacking("Y");
				if(i<10){
					ser.setSerialNo("000"+i);
				}else{
					ser.setSerialNo("00"+i);
				}
				if(i%2==0){
					ser.setIsUnPacking("N");
				}else{
					ser.setIsUnPacking("Y");
				}
				if(i==10||i==12||i==17){
					ser.setStockAreaCode("0001");
				}else if(i==15||i==34){
					ser.setStockAreaCode("0002");
				}else{
					ser.setStockAreaCode("0003");
				}
				//ser.setHasToDoList("N");
				sers.add(ser);
			}
			detail.setSerialNos(sers);
			detail.setStockQty(10);
			detail.setBeJoinCar("Y");
			//detail.setCreatorCode(arg0.getOperatorCode());
			//List<LoaderDto> userCodes = arg0.getLoaderCodes();
			//detail.setLoaders(userCodes);
			detail.setWayBillQty(10);
			//detail.setVehicleDeadWeight(1000);
			//detail.setVehicleDeadVolume(1000);
			detail.setPackGoodsAreaCode("0001");
			detail.setValueGoodsAreaCode("0002");
			detail.setOperateQty(new Random().nextInt(10));
			detail.setReceiveCustDistName("闵行区");//行政区域
			res.add(detail);
		}
		return res;
	}

	

	

	
	

}
