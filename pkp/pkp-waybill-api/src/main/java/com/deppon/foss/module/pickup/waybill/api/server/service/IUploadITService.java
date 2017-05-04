package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.dto.ITServiceResultDto;
import com.deppon.foss.module.pickup.waybill.shared.vo.ITServiceVo;


/**
 * 
 * @author WangQianJin
 *
 */
public interface IUploadITService {  
	
	/**
     * IT服务台一键上报
     * @author WangQianJin
     * @return
     */
    ITServiceResultDto uploadItService(List<ITServiceVo> itList);

    /**
     * IT服务台一键上报是否启用
     * @author WangQianJin
     * @return
     */
	boolean isStartItServiceUpload();
}
