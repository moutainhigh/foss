package com.deppon.pda.bdm.module.core.server.service;

import java.util.List;
import java.util.Map;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPicturePdaDto;
import com.deppon.pda.bdm.module.core.shared.domain.WaybillPictureOcrDto;

/**
 * @项目：ocr二期项目
 * @功能：定义ocr交互层
 * @author:218371-foss-zhaoyanjun
 * @date:20160918
 */
public interface IOcrService {
	//查询N秒前未发送给foss的运单
	public List<WaybillPictureOcrDto> queryWaybillsWithoutSend(String jobId);
	
	//将未发送给foss的运单发送给foss
	public Map<String,List<WaybillPictureOcrDto>> sendToFoss(List<WaybillPictureOcrDto> list);
	
	//修改所有当次发送给foss的状态
	public void updateWetherSend(List<WaybillPictureOcrDto> list);
	
	//插入ocr表中
	public void insertToOcr(WaybillPicturePdaDto wbp);
	
	//修改档次jobId
	public int updateJobID(int waittime, String jobId);
	
	//修改所有当次发送给foss的状态为false
	public void updateWetherSendForFail(List<WaybillPictureOcrDto> list);

	public int waybillNoSend(int waittime);
}
