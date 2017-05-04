package com.deppon.pda.bdm.module.core.server.dao;

import java.util.List;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPicturePdaDto;
import com.deppon.pda.bdm.module.core.shared.domain.WaybillPictureOcrDto;

/**
 * @项目：ocr二期
 * @功能：数据库操作
 * @author:218371-foss-zhaoyanjun
 * @date:20160918
 */
public interface IOcrDao{
	//查询N秒前未发送给foss的运单
	public List<WaybillPictureOcrDto> queryWaybillsWithoutSend(String jobID);
	//修改所有当次发送给foss的状态
	public void updateWetherSendForApp(List<WaybillPictureOcrDto> list);
	//修改所有当次发送给foss的状态
	public void updateWetherSendForOcr(List<WaybillPictureOcrDto> list);
	//插入ocr表中
	public void insertToOcr(WaybillPicturePdaDto wbp);	
	//修改所有当次发送给foss的状态
	public void updateWetherSendForFailForApp(List<WaybillPictureOcrDto> list);
	//修改所有当次发送给foss的状态
	public void updateWetherSendForFailForOcr(List<WaybillPictureOcrDto> list);
	//修改当前jobID
	public int updateJobID(int waittime, String jobId);
	//查询是否有运单没有发送给foss
	public int waybillNoSend(int waittime);
}
