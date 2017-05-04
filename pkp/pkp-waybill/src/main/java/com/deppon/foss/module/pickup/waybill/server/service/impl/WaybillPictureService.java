package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPictureDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPictureService;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPictureDto;


@Transactional
@Service
public class WaybillPictureService implements IWaybillPictureService {

	@Autowired
	private  IWaybillPictureDao  waybillPictureDao; 
	
	@Transactional(readOnly=true)
	public List<WaybillPictureDto> queryWaybillPictureDtoPage(
			WaybillPictureDto waybillPictureDto) {
		return waybillPictureDao.queryWaybillPictureDtoPage(waybillPictureDto);
	}
	
	@Transactional(readOnly=true)
    public Long queryWaybillPictureByDtoPageCounts(WaybillPictureDto waybillPictureDto)
    {
    	return waybillPictureDao.queryWaybillPictureByDtoPageCounts(waybillPictureDto);
    }

	public void updateWaybillPictureById(WaybillPictureDto waybillPictureDto) {
		// TODO Auto-generated method stub
	    waybillPictureDao.updateWaybillPictureById(waybillPictureDto);
	}

}
