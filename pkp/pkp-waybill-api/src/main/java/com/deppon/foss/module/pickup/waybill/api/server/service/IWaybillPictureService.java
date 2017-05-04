package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPictureDto;

public interface IWaybillPictureService extends IService{
	
	 //查询图片开单列表
	 List<WaybillPictureDto> queryWaybillPictureDtoPage(WaybillPictureDto waybillPictureDto);
	 
	 //查询开单总数
	 Long  queryWaybillPictureByDtoPageCounts(WaybillPictureDto waybillPictureDto);
	 
	 //更改运单审批状态
	 void  updateWaybillPictureById(WaybillPictureDto waybillPictureDto);
}
