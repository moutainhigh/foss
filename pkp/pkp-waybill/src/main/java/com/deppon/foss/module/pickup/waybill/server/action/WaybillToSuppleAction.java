package com.deppon.foss.module.pickup.waybill.server.action;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.MessageType;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillToSuppleService;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillToSuppleResultDto;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillToSuppleVo;

/**
 * 待补录数据action
 * @author Foss-105888-Zhangxingwang
 * @date 2014-8-9 08:10:20
 */
public class WaybillToSuppleAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 待补录Vo
	 */
	private WaybillToSuppleVo vo;
	
	/**
	 * 待补录Service
	 */
	private IWaybillToSuppleService waybillToSuppleService;	
	
	/**
     * 导出文件名称
     */
    private String downloadFileName;
    
    /**
     * 导出Excel 文件流
     */
    private InputStream inputStream;
	
	@JSON
	public String addWaybillToSuppleRecord(){
		try{
			waybillToSuppleService.addWaybillToSuppleRecord(vo.getWaybillSupplementLogEntity());
		} catch (BusinessException e){
			return returnError(e.getMessage());
		}
		return returnSuccess(MessageType.DELETE_SUCCESS);
	}

	
	@JSON
	public String queryWaybillToSuppleAction(){
		try{
			Long totalCount = waybillToSuppleService.queryWaybillToSuppleActionCount(vo.getWaybillToSuppleCondtionDto());
			if(totalCount != null && totalCount.intValue() > 0){
				List<WaybillToSuppleResultDto> list = waybillToSuppleService.queryWaybillToSuppleAction(vo.getWaybillToSuppleCondtionDto(), this.getStart(), this.getLimit());
				vo.setWaybillSuppleResultDtoList(list);
			}else{
				vo.setWaybillSuppleResultDtoList(null);
			}
			this.setTotalCount(totalCount);
		} catch (BusinessException e){
			returnError(e.getMessage());
		}
		return returnSuccess(MessageType.SAVE_SUCCESS);
	}
	
	public WaybillToSuppleVo getVo() {
		return vo;
	}

	public void setVo(WaybillToSuppleVo vo) {
		this.vo = vo;
	}
	
	public void setWaybillToSuppleService(IWaybillToSuppleService waybillToSuppleService) {
		this.waybillToSuppleService = waybillToSuppleService;
	}


	public String getDownloadFileName() {
		return downloadFileName;
	}


	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}


	public InputStream getInputStream() {
		return inputStream;
	}


	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}