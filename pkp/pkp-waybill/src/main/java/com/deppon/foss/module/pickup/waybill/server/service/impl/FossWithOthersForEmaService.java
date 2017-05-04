package com.deppon.foss.module.pickup.waybill.server.service.impl;
/**
 * @项目：快递自动项目
 * @功能：实现IFossWithOthersForEma接口
 * @author:218371-foss-zhaoyanjun
 * @date:2015-07-15上午09:37
 */
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.inteface.domain.fossworkflow.ExpWaybillResultRequest;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IFossWithOthersForEmaService;

public class FossWithOthersForEmaService implements IFossWithOthersForEmaService{
	//导入日志文件
	private static final Logger LOGGER = LoggerFactory.getLogger(FossWithOthersForEmaService.class);
	
	//传送成功
	private static final String SUCCESS="1";
	
	//传送失败
	private static final String FAIL="1";
	
	//运单信息传递版本信息
	private static final String WICS_ESB2FOSS_WAYBILLINFO_ERC="1.0";
	
	//将快递自动补录项目运单开单结果传回录单中心（只适合一个一个传）
	@Override
	public String postRecordCenter(String waybillNo, String billNumberState,Date uploadTime,String context) {
		// TODO Auto-generated method stub
		if(waybillNo==null||billNumberState==null||uploadTime==null||context==null){
			throw new BusinessException("此运单单号："+waybillNo+"发送消息不全！，请查实");
		}
		// 准备消息头信息
		String esbServiceCode="ESB_FOSS2ESB_WAYBILLRESULT_ERC";
		String version=WICS_ESB2FOSS_WAYBILLINFO_ERC;
		AccessHeader header = createAccessHeader(waybillNo, esbServiceCode, version);
		// 创建具体消息
		ExpWaybillResultRequest request = new ExpWaybillResultRequest();
		//填充消息信息
		request.setWaybillNo(waybillNo);
		request.setBillNumberState(billNumberState);
		request.setUploadTime(uploadTime);
		request.setContext(context);
		// 发送请求
		try {
			ESBJMSAccessor.asynReqeust(header, request);
			return SUCCESS;
		} catch (Exception e) {
			LOGGER.error("运单开单结果传回录单中心发送请求报错" + e);
			return FAIL;
		}
	}
	
	//准备消息头，可以共用
	private AccessHeader createAccessHeader(String waybillNo,String esbServiceCode,String version) {
		AccessHeader header = new AccessHeader();
		//business code 根据esb提供(需按不同Code进行修改)
		header.setEsbServiceCode(esbServiceCode);
		//版本随意
		header.setVersion(version);
		//business id 随意
		header.setBusinessId(waybillNo);
		//运单号放在消息头中传给oa waybillNo
		header.setBusinessDesc1(waybillNo);
		return header;
	}
}
