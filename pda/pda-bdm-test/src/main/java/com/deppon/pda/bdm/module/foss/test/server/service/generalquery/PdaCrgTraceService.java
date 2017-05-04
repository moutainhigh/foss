package com.deppon.pda.bdm.module.foss.test.server.service.generalquery;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.server.service.IWayBillNoLocusService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WayBillLocusDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WayBillNoLocusDTO;

public class PdaCrgTraceService implements IWayBillNoLocusService{
	/**
	 * PDA
	 */
	@Override
	public WayBillLocusDto getWayBillLocusForPda(String wayBill) {
		WayBillLocusDto wayBillLocusDto = new WayBillLocusDto();
		wayBillLocusDto.setGoodsName("Iphone8");
		wayBillLocusDto.setTargetOrgCode("上海德邦C区四楼");
//		wayBillLocusDto.setArrDeptAddr(cargoTraceInfoResDmn.getArrDeptAddr());
//		wayBillLocusDto.setArrDeptPhone(cargoTraceInfoResDmn.getArrDeptPhone());
//		wayBillLocusDto.setArrivalDept(cargoTraceInfoResDmn.getArrivalDept());
//		wayBillLocusDto.setPieces(cargoTraceInfoResDmn.getPieces());
//		wayBillLocusDto.setTakeType(cargoTraceInfoResDmn.getDeliveryType());
		wayBillLocusDto.setGoodsQtyTotal(5);
		wayBillLocusDto.setReceiveMethod("自提");
		List<WayBillNoLocusDTO> wayBillNoList = new ArrayList<WayBillNoLocusDTO>();
		WayBillNoLocusDTO wayBillNoLocus = null;
		wayBillLocusDto.setWayBillNoLocusDTOList(wayBillNoList);
		for (int i = 0 ;i < 10 ; i++) {
			wayBillNoLocus = new WayBillNoLocusDTO();
			wayBillNoLocus.setNotes("备注呢_"+i);
			wayBillNoLocus.setOperateTime(new Date());
			wayBillNoList.add(wayBillNoLocus);
		}
		wayBillLocusDto.setWayBillNoLocusDTOList(wayBillNoList);
		wayBillLocusDto.setProductCode("精准卡航");
		wayBillLocusDto.setGoodsVolumeTotal(new BigDecimal(100));
		wayBillLocusDto.setWaybillNo(wayBill);
		wayBillLocusDto.setGoodsWeightTotal(new BigDecimal(50));
		return wayBillLocusDto;
	}

	@Override
	public List<WayBillNoLocusDTO> getWayBillNoLocus(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WayBillNoLocusDTO> getWayBillNoLocusForBse(String waybillNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WayBillNoLocusDTO> getWayBillNoLocus(String waybillNo,
			String invokingSource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WayBillNoLocusDTO> getWayBillNoLocusBySerialNo(
			String waybillNo, String serialNo) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
