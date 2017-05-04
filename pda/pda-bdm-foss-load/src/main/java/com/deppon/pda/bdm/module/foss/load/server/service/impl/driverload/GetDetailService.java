package com.deppon.pda.bdm.module.foss.load.server.service.impl.driverload;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDispatchOrderService;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpressFeederPickupDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpressFeederPickupQueryDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.shared.domain.driverload.GetDetail;
import com.deppon.pda.bdm.module.foss.load.shared.domain.driverload.GetDetailResult;

public class GetDetailService implements IBusinessService<List<GetDetailResult>,GetDetail>{

	private IPdaDispatchOrderService  pdaDispatchOrderService;

	@Override
	public GetDetail parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		GetDetail entity=JsonUtil.parseJsonToObject(GetDetail.class,asyncMsg.getContent());
		
		return entity;
	}

	@Override
	public List<GetDetailResult> service(AsyncMsg asyncMsg, GetDetail param) throws PdaBusiException {
		List<ExpressFeederPickupDetailDto> expressFeederPickupDetailDtos = null;
		List<GetDetailResult> list=new ArrayList<GetDetailResult>();
		try {

			this.validate(param,asyncMsg);
			ExpressFeederPickupQueryDto queryDto=new ExpressFeederPickupQueryDto();
			queryDto.setDriverCode(asyncMsg.getUserCode()); //司机工号
			queryDto.setExpressEmpCode(param.getCourierCode()); //快递员工号
			queryDto.setTaskNo(param.getTaskCode());//任务号
			expressFeederPickupDetailDtos=pdaDispatchOrderService.getExpressFeederPickupDetail(queryDto);
			if (expressFeederPickupDetailDtos != null && !expressFeederPickupDetailDtos.isEmpty()) { 
			for (ExpressFeederPickupDetailDto expressFeederPickupDetailDto : expressFeederPickupDetailDtos) { 
			GetDetailResult detail=new GetDetailResult();
			detail.setWaybillNo(expressFeederPickupDetailDto.getWaybillNo());//运单号
			detail.setGoodsWeightTotal(expressFeederPickupDetailDto.getGoodsWeightTotal());//重量
			detail.setGoodsVolumeTotal(expressFeederPickupDetailDto.getGoodsVolumeTotal());//体积
			//detail.setCustomerPickupOrgCode(expressFeederPickupDetailDto.getCustomerPickupOrgCode());
			detail.setProductCode(expressFeederPickupDetailDto.getProductCode());//类型
			//detail.setStockTime(expressFeederPickupDetailDto.getStockTime());
			//detail.setSerialNo(expressFeederPickupDetailDto.getSerialNo());//流水号
			//detail.setStockOrgCode(expressFeederPickupDetailDto.getStockOrgCode());
			detail.setScanStatus(expressFeederPickupDetailDto.getScanStatus());//已扫状态
			detail.setScanQtyTotal(expressFeederPickupDetailDto.getScanQtyTotal());//扫描件数
			//detail.setLoadQtyTotal(expressFeederPickupDetailDto.getLoadQtyTotal());
			detail.setGoodsQtyTotal(expressFeederPickupDetailDto.getGoodsQtyTotal());//开单件数
			list.add(detail);
			}	
		 }
	     return list;
		}catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
	   }
	}
	
	@Override
	public String getOperType() {
	return LoadConstant.OPER_TYPE_DRIVER_RASK_DETAIL.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaDispatchOrderService(IPdaDispatchOrderService pdaDispatchOrderService) {
		this.pdaDispatchOrderService = pdaDispatchOrderService;
	}
	// 参数验证
	private void validate(GetDetail getDetail,AsyncMsg asyncMsg) throws ArgumentInvalidException {
	 //  Argument.hasText(getDetail.getUserCode(), "getDetail.userCode");
	   Argument.hasText(asyncMsg.getUserCode(), "asyncMsg.userCode");
	}
	
}
