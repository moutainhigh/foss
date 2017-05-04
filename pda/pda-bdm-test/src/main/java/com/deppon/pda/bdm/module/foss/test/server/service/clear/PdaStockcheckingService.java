package com.deppon.pda.bdm.module.foss.test.server.service.clear;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAGoodsSerialNoDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PdaStTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.StTaskZoneDto;

public class PdaStockcheckingService implements IPdaStockcheckingService {
	private static final Log LOG = LogFactory
			.getLog(PdaStockcheckingService.class);

	@Override
	public String queryGoodsAreaCode(String waybillNo, String serialNo,
			String deptCode) {
		LOG.info("运单号：" + waybillNo);
		return "D02";
	}

	public List<PdaStTaskDto> queryPdaStTaskDtoList(String stTaskNo) {
		List<PdaStTaskDto> dtos = new ArrayList<PdaStTaskDto>();
		for (int j = 0; j < 1; j++) {
			PdaStTaskDto dto = new PdaStTaskDto();
			dto.setChangeContent("changeConteng");
			dto.setContraband("N");// 是否违禁物品
			dto.setCreatorCode("076544");
			dto.setGoodsName("毛衣");
			dto.setGoodsVolume(1.2000000000);
			dto.setGoodsWeight(500.0000000000);
			dto.setHasChange("N");
			dto.setInStockTime(new Date());
			dto.setLabelTrash("N");
			dto.setNeedWoodenPackage("N");
			dto.setPackageType("代打木架");
			dto.setPreciousGoods("N");
			dto.setProductCode("精准汽运");
			dto.setReceiveOrgCode("W011305080107");
			dto.setReceiveOrgName("惠州博罗县园洲营业部");
			//dto.setSerialNo("0001");
			dto.setStTaskNo("12344566990");
			dto.setTargetOrgCode("W011305080107");
			dto.setTargetOrgName("北京顺义区通顺路营业部");
			if(j<10){
				dto.setWaybillNo("2222000"+j);
			}else if(j>=10&&j<100){
				dto.setWaybillNo("222200"+j);
			}else if(j>=100&&j<1000){
				dto.setWaybillNo("22220"+j);
			}else{
				dto.setWaybillNo("2222"+j);
			}
			dto.setGoodsWeight(10.0);
			dto.setGoodsVolume(1.0);
			//流水号
			List<PDAGoodsSerialNoDto> serials = new ArrayList<PDAGoodsSerialNoDto>();
			for (int i = 1; i < 10000; i++) {
				PDAGoodsSerialNoDto ser = new PDAGoodsSerialNoDto();
				/*if(i%5==0||i%3==0||i%7==0){
					continue;
				}*/
				if(i%2==0){
					ser.setIsUnPacking("N");
				}else{
					ser.setIsUnPacking("Y");
				}
				if(i<10){
					ser.setSerialNo("000"+i);
				}else if(i<100){
					ser.setSerialNo("00"+i);
				}else if(i<1000){
					ser.setSerialNo("0"+i);
				}else if(i<10000){
					ser.setSerialNo(""+i);
				}
				ser.setStockAreaCode("D02");
				serials.add(ser);
			}
			dto.setSerialNos(serials);
			dtos.add(dto);
		}
		return dtos;
	}

	public boolean scanStTaskDetail(String stTaskNo, String waybillNo,
			String serialNo, String scanResultStatus, Date scanTime,
			String operatorCode, String pdaNo) {
		LOG.info("任务号：" + stTaskNo);
		LOG.info("success!!!");
		return true;
	}

	@Override
	public boolean submitPdaStTask(String stTaskNo, String pdaNo, Date scanTime) {
		LOG.info("任务号：" + stTaskNo);
		LOG.info("success!!!");
		return true;
	}

	@Override
	public boolean enforceSubmitPdaStTask(String stTaskNo, String pdaNo,
			Date scanTime) {
		LOG.info("任务号：" + stTaskNo);
		LOG.info("success!!!");
		return true;
	}

	@Override
	public boolean cancelPdaStTask(String stTaskNo, String pdaNo,
			Date scanTime, String operatorCode) {
		LOG.info("任务号：" + stTaskNo);
		LOG.info("success!!!");
		return true;
	}

	@Override
	public boolean finishPdaStTask(String stTaskNo, Date scanTime, String pdaNo) {
		LOG.info("任务号：" + stTaskNo);
		LOG.info("success!!!");
		return true;
	}


	public PdaStTaskDto createPdaStTask(String arg0, String arg1, String arg2,
			String arg3, String arg4, String arg5, Integer arg6, Integer arg7) {
		List<PdaStTaskDto> dtos = new ArrayList<PdaStTaskDto>();
		PdaStTaskDto dto = new PdaStTaskDto();
		dto.setChangeContent("changeConteng");
		dto.setContraband("N");// 是否违禁物品
		dto.setCreatorCode("076544");
		dto.setGoodsName("PDA");
		dto.setGoodsVolume(1.2000000000);
		dto.setGoodsWeight(500.0000000000);
		dto.setHasChange("N");
		dto.setInStockTime(new Date());
		dto.setLabelTrash("N");
		dto.setNeedWoodenPackage("N");
		dto.setPackageType("PackageType");
		dto.setPreciousGoods("N");
		dto.setProductCode("producttype");
		dto.setReceiveOrgCode("q9/03wEhEADgBESRwKgCZcznrtQ=");
		dto.setReceiveOrgName("上海外场");
		//dto.setSerialNo("0001");
		dto.setStTaskNo("12344566990");
		dto.setTargetOrgCode("q9/03wEhEADgBESRwKgCZcznrtQ=");
		dto.setTargetOrgName("上海派送部");
		dto.setWaybillNo("222111823");
		dto.setGoodsWeight(10.0);
		dto.setGoodsVolume(1.0);
		//流水号
		List<PDAGoodsSerialNoDto> serials = new ArrayList<PDAGoodsSerialNoDto>();
		PDAGoodsSerialNoDto s1 = new PDAGoodsSerialNoDto();
		s1.setIsUnPacking("Y");
		s1.setSerialNo("0002");
		s1.setStockAreaCode("D02");
		serials.add(s1);
		dto.setSerialNos(serials);
		dtos.add(dto);
		return dto;
	}

	@Override
	public PdaStTaskDto createPdaStTask(String deptCode, String goodsAreaCode,
			String operatorCode, String pdaNo, String stTaskNo,
			String isStation, Integer startQty, Integer endQty,
			String receiveMethod, String districtCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean scanStTaskDetail(String stTaskNo, String waybillNo,
			String serialNo, String scanResultStatus, Date scanTime,
			String operatorCode, String pdaNo, String positionNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<StTaskZoneDto> queryZoneInfo(String deptCode) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
