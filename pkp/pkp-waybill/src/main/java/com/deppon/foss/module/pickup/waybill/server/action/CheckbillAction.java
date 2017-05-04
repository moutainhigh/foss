package com.deppon.foss.module.pickup.waybill.server.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPictureService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPictureDto;
import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
import com.deppon.foss.module.pickup.waybill.shared.vo.CheckbillVo;

/**
 * @author 265041
 * @deprecated: 跳转进入审单页面
 * @Date : 2015-06-13
 * */
@Service
public class CheckbillAction extends AbstractAction {

	private CheckbillVo vo;

	private static final long serialVersionUID = 1L;

	@Autowired
	private IWaybillPictureService waybillPictureService;
	
	
	private String pictureUrl;
	
	
	@Override
	public String execute(){
		this.pictureUrl= PropertiesUtil.getKeyValue(WaybillConstants.PICTURE_WAYBILL_ADDRESS);
		return  returnSuccess();
	}
	@JSON
	public String getwaybillpicturelist() {
		try {
			String checkStatus = vo.getWaybillPictureDto().getCheckbillstatus();
			if(checkStatus.equals("E"))
			{
				vo.getWaybillPictureDto().setCheckbillstatus(null);
			}
			Long pagecounts = waybillPictureService
					.queryWaybillPictureByDtoPageCounts(vo
							.getWaybillPictureDto());
			if (pagecounts>0) {
			    vo.getWaybillPictureDto().setStart(this.start);
				vo.getWaybillPictureDto().setLimit(this.limit);
				List<WaybillPictureDto> list =waybillPictureService
						.queryWaybillPictureDtoPage(vo.getWaybillPictureDto());
				vo.setWaybillPictureEntityList(list);
			} else
			{
				vo.setWaybillPictureEntityList(null);
			}
			this.setTotalCount(pagecounts);
		} catch (Exception e) {
			returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	@JSON
    public  String  updatestatus()
    {
		try{
			waybillPictureService.updateWaybillPictureById(vo.getWaybillPictureDto());
		}catch(Exception e){
			returnError(e.getMessage());
		}
    	return returnSuccess(MessageType.UPDATE_SUCCESS);
    }
	public CheckbillVo getVo() {
		return vo;
	}

	public void setVo(CheckbillVo vo) {
		this.vo = vo;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	
}
