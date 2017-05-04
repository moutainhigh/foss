package com.deppon.pda.bdm.module.foss.test.server.service.packagemanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPackageService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressPackageDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressPackageDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressPackageScanDetailDto;
import com.deppon.pda.bdm.module.foss.test.server.service.delivery.PdaDeliverTaskService;

public class PdaExpressPackageService implements IPDAExpressPackageService{
	
	private static final Log LOG = LogFactory.getLog(PdaDeliverTaskService.class);
	
	/**查询未完成包任务*/
	public List<ExpressPackageDto> queryUnFinishedPackage(String userCode,Date queryStartTime,Date queryEndTime){
		List<ExpressPackageDto> list = new ArrayList<ExpressPackageDto>();
		for (int i = 1; i <= 10; i++) {
			ExpressPackageDto dto = new ExpressPackageDto();
			dto.setArriveOrgCode("ArriveOrgCode"+i);
			dto.setPackageNo("100000"+i);
			dto.setStatus("Y");
			dto.setUserCode(userCode);
			list.add(dto);
		}
		return list;
	}
	
	/**创建包任务*/
	public String createPackage(String packageNo,String origOrgCode,String arriveOrgCode,String userCode,String deviceNo,Date createTime){
		return "success";
	}
	
	/**刷新包任务*/
	public List<ExpressPackageDetailDto> refrushPackageDetail(String packageNo){
		List<ExpressPackageDetailDto> list = new ArrayList<ExpressPackageDetailDto>();
		for(int i = 1 ; i <= 5; i ++){
			ExpressPackageDetailDto detailDto = new ExpressPackageDetailDto();
			detailDto.setGoodsName("");
			detailDto.setNotes("备注");
			detailDto.setOperateQty(5);
			detailDto.setPack(packageNo);
			detailDto.setPackageNo(packageNo);
			detailDto.setReachOrgCode("W060002070701");
			detailDto.setReachOrgName("上海徐泾营业部");
			detailDto.setReceiveOrgCode("00002");
			detailDto.setReceiveOrgName("北京天安门营业部");
			List<String> serialNos = new ArrayList<String>();
			serialNos.add("0001");
			serialNos.add("0002");
			serialNos.add("0003");
			detailDto.setSerialNos(serialNos);
			detailDto.setStockQty(100);
			detailDto.setTransportTypeCode("3");
			detailDto.setTransportTypeName("包裹");
			detailDto.setVolume(0.25);
			detailDto.setWayBillNo("10000000004");
			detailDto.setWayBillQty(5);
			detailDto.setWeight(10);
			list.add(detailDto);
		}
		return list;
	}
	
	/**多货校验*/
	public ExpressPackageDetailDto moreGoodsVerify(String packageNo,String wayBillNo,String serialNo){
		ExpressPackageDetailDto detailDto = new ExpressPackageDetailDto();
		detailDto.setGoodsName("");
		detailDto.setNotes("备注");
		detailDto.setOperateQty(5);
		detailDto.setPack(packageNo);
		detailDto.setPackageNo(packageNo);
		detailDto.setReachOrgCode("W060002070701");
		detailDto.setReachOrgName("上海徐泾营业部");
		detailDto.setReceiveOrgCode("00002");
		detailDto.setReceiveOrgName("北京天安门营业部");
		List<String> serialNos = new ArrayList<String>();
		serialNos.add("0001");
		serialNos.add("0002");
		serialNos.add("0003");
		detailDto.setSerialNos(serialNos);
		detailDto.setStockQty(100);
		detailDto.setTransportTypeCode("3");
		detailDto.setTransportTypeName("包裹");
		detailDto.setVolume(0.25);
		detailDto.setWayBillNo("10000000004");
		detailDto.setWayBillQty(5);
		detailDto.setWeight(10);
		return detailDto;
	}
	
	/**扫描*/
	public void scan(ExpressPackageScanDetailDto scanDto){
		LOG.info("packageNo = " + scanDto.getPack() + " , scan  success.....");
	}
	
	/**取消包任务*/
	public void cancelPackage(String packageNo,String deviceNo,String operatorCode,Date cancelTime){
		LOG.info("packageNo = " + packageNo + " , cancelPackage  success.....");
	}
	
	/**提交包任务*/
	public void submitPackage(String packageNo,String deviceNo,String operatorCode,Date cancelTime){
		LOG.info("packageNo = " + packageNo + " , submitPackage  success.....");
	}

	@Override
	public String createPackage(String packageNo, String origOrgCode,
			String arriveOrgCode, String userCode, String deviceNo,
			Date createTime, String expressPackageType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> obtainThroughPackArriveOrgCode(String packageNo,
			String waybillNo, String serialNo, String currentOrgCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExpressPackageDetailDto> refrushThroughPackageDetail(
			String packageNo) {
		// TODO Auto-generated method stub
		return null;
	}
}
