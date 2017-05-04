package com.deppon.pda.bdm.module.core.server.async.job;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.pda.bdm.module.core.server.service.IOcrService;
import com.deppon.pda.bdm.module.core.shared.constants.OcrContant;
import com.deppon.pda.bdm.module.core.shared.domain.WaybillPictureOcrDto;
/**
 * @项目:Ocr项目
 * @功能：N秒后将数据发送给foss
 * @author:218371-foss-zhaoyanjun
 * @date:2016-09-21下午14:27
 */
public class OcrJob {
	//本次待处理运单
	List<WaybillPictureOcrDto> list;
	//ocrService
	IOcrService ocrService;
	
	public void start(){
		boolean dictionary=false;
		dictionary=whetherToOcr();
		//job方法主业务
		if(dictionary){
			main();
		}
	}
	
	//判断是否经过ocr处理
	private boolean whetherToOcr() {
		// TODO Auto-generated method stub
		try {
			DataDictionaryEntity dictEntity = DictUtil.getDataByTermsCode(OcrContant.OcrTerm);
			List<DataDictionaryValueEntity> dataList = dictEntity.getDataDictionaryValueEntityList();
			if(dataList!=null&&dataList.size()!=0){
				for(DataDictionaryValueEntity entityAgree:dataList){
					if("OCR_CONTROL".equals(entityAgree.getValueCode())){
						return whetherToOcrForSonar(entityAgree.getValueName());
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return false;
	}
	
	//判断是否OCR处理forSonar
	private boolean whetherToOcrForSonar(String name){
		int num=0;
		//全国正式上线
		if(OcrContant.OcrNoArea.equals(name)){
			//查询是否有运单没有发送给foss
			num=ocrService.waybillNoSend(OcrContant.waitTime);
			if(num!=0){
				return true;
			}
			return false;
		}else{
			return true;
		}
	}

	//job方法主业务
	private void main() {
		//生成此次jobID
		String jobId=UUID.randomUUID().toString();
		// 修改当此jobID
		int count = ocrService.updateJobID(OcrContant.waitTime, jobId);
		if (count != 0) {
			// 查询当前时间N秒前未发送给foss的运单
			list = ocrService.queryWaybillsWithoutSend(jobId);
			if (list != null && !list.isEmpty()) {
				// 将未发送给foss的运单发送给foss
				Map<String, List<WaybillPictureOcrDto>> map = ocrService
						.sendToFoss(list);
				// 修改所有当次发送给foss的状态
				if (map.get("SUCCESS") != null && !map.get("SUCCESS").isEmpty()) {
					ocrService.updateWetherSend(map.get("SUCCESS"));
				}
				if (map.get("FAIL") != null && !map.get("FAIL").isEmpty()) {
					ocrService.updateWetherSendForFail(map.get("FAIL"));
				}
			}
		}
	}

	public IOcrService getOcrService() {
		return ocrService;
	}

	public void setOcrService(IOcrService ocrService) {
		this.ocrService = ocrService;
	}
}
