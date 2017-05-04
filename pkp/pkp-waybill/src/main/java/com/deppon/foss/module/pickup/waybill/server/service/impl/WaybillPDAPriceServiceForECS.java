package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.BillCaculateServiceException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPDAPriceServiceForECS;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPDAServiceForEcs;
import com.deppon.foss.module.pickup.waybill.shared.response.EscWayPDABillResponseEntity;

/**
 * 提供悟空PDA计算运费
 * @author Foss-308595-GELL
 *
 */
@Service
public class WaybillPDAPriceServiceForECS implements IWaybillPDAPriceServiceForECS {
	
	@Resource
	private IWaybillPDAServiceForEcs waybillPDAServiceForEcs;
	
	@Override
	public EscWayPDABillResponseEntity queryPDABillPrice(PdaQueryBillCalculateDto billCalculateDto) {
		//返回实体
    	EscWayPDABillResponseEntity escWayPDABillResponseEntity = new EscWayPDABillResponseEntity();
    	//FOSS计价方法返回
    	List<PdaResultBillCalculateDto> billCalculateDtoList = new ArrayList<PdaResultBillCalculateDto>();
    	
    	if(null == billCalculateDto){
    		escWayPDABillResponseEntity.setSuccess("0");
    		escWayPDABillResponseEntity.setMessage("请求实体为空！");
    		return escWayPDABillResponseEntity;
    	}else{
    		try {
				//FOSS中的计价方法
    			//解决类不能依赖注入，所以才这样直接从容器中取
//    			waybillPDAServiceForEcs=(IPdaPriceService)applicationContext.getBean("pdaPriceService");
    			billCalculateDtoList = waybillPDAServiceForEcs.queryExpressBillCalculate(billCalculateDto);
    			//未能获取运费信息
    			if(null == billCalculateDtoList || billCalculateDtoList.isEmpty()){
    				escWayPDABillResponseEntity.setSuccess("0");
            		escWayPDABillResponseEntity.setMessage("未能获取有效运费！");
            		escWayPDABillResponseEntity.setPdaResultBillCalculateDto(new ArrayList<PdaResultBillCalculateDto>());
            		return escWayPDABillResponseEntity;
    			}
			} catch (BillCaculateServiceException e) {
				escWayPDABillResponseEntity.setSuccess("0");
        		escWayPDABillResponseEntity.setMessage(e.getMessage().toString());
        		escWayPDABillResponseEntity.setPdaResultBillCalculateDto(new ArrayList<PdaResultBillCalculateDto>());
        		return escWayPDABillResponseEntity;
			}
    	}
    	
    	//返回处理好的返回实体    	
    	escWayPDABillResponseEntity.setPdaResultBillCalculateDto(billCalculateDtoList);
    	escWayPDABillResponseEntity.setSuccess("1");
		return escWayPDABillResponseEntity;
	}
}
