package com.deppon.pda.bdm.module.foss.test.server.service.delivery;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDeliverTaskService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverTaskDetailsDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverTaskDto;

public class PdaDeliverTaskService implements IPdaDeliverTaskService {
	private static final Log LOG = LogFactory.getLog(PdaDeliverTaskService.class);
	
	@Override
	public List<PdaDeliverTaskDto> getDeliverTaskList(String driverCode,
			String vehicleNo) {
		List<PdaDeliverTaskDto> deliverTaskDtos = new ArrayList<PdaDeliverTaskDto>();
		PdaDeliverTaskDto deliverTaskDto = new PdaDeliverTaskDto();
		deliverTaskDto.setDeliverbillNo("12345678");

		List<PdaDeliverTaskDetailsDto> deliverTaskDetailsDtos = new ArrayList<PdaDeliverTaskDetailsDto>();
		
		SimpleDateFormat ddHHmmss = new SimpleDateFormat("ddHHmmss");
		SimpleDateFormat mms = new SimpleDateFormat("mms");
		
		String waybillNo = ddHHmmss.format(new Date());
		String arriveSheetNo = waybillNo + mms.format(new Date());
		
		PdaDeliverTaskDetailsDto deliverTaskDetailsDto = new PdaDeliverTaskDetailsDto();
		deliverTaskDetailsDto.setArriveSheetGoodsQty(50);
		deliverTaskDetailsDto.setArriveSheetNo(arriveSheetNo);
		deliverTaskDetailsDto.setCodAmount(new BigDecimal(10));
		deliverTaskDetailsDto.setDeliverbillNo("123");
		deliverTaskDetailsDto.setGoodsPackage("木");
		deliverTaskDetailsDto.setNotes("50");
		deliverTaskDetailsDto.setPreciousGoods("是");
		deliverTaskDetailsDto.setReceiveCustomerAddress("青浦区徐泾镇明珠路德邦物流C区");
		deliverTaskDetailsDto.setReceiveCustomerMobilephone("123456789");
		deliverTaskDetailsDto.setReceiveCustomerName("xujun");
		deliverTaskDetailsDto.setWeight(new BigDecimal(10));
		deliverTaskDetailsDto.setWaybillNo(String.valueOf(waybillNo));
		deliverTaskDetailsDto.setToPayAmount(new BigDecimal(10));
		deliverTaskDetailsDto.setSubmitTime(new Date());
		List<String> serialNum = new ArrayList<String>();
		serialNum.add("0001");
		serialNum.add("0002");
		deliverTaskDetailsDto.setSerialNum(serialNum);
		deliverTaskDetailsDto.setComplainStatus("Y");
		
		deliverTaskDetailsDtos.add(deliverTaskDetailsDto);
		deliverTaskDto.setDeliverTaskDetailsDtos(deliverTaskDetailsDtos);
		
		deliverTaskDtos.add(deliverTaskDto);
		return deliverTaskDtos;
	}
	
	@Override
	public int updateDeliverbillStatus(String deliverbillNo) {
		LOG.info("updateDeliverbillStatus  success.....");
		return 0;
	}

}
