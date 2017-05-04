package com.deppon.foss.module.pickup.crm.server.service.impl;

import java.util.List;
import java.util.Map;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.ludanworkflow.ExpWaybillRequest;
import com.deppon.esb.inteface.domain.ludanworkflow.ExpWaybillResponse;
import com.deppon.esb.inteface.domain.ludanworkflow.ExpressAutomaticMakeup;
import com.deppon.foss.module.pickup.waybill.api.server.service.IEamService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IFossWithOthersForEmaService;
import com.deppon.foss.module.pickup.waybill.shared.domain.EamDto;

public class FossToWicsService implements IProcess{
	/**
	 * @项目：快递自动补录项目
	 * @功能：导入订单表与日志表service
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-06-04上午09:51
	 */
	private IEamService eamService;
	public void setEamService(IEamService eamService) {
		this.eamService = eamService;
	}
	
	/**
	 * @项目：快递自动补录项目
	 * @功能：导入fossWithOthersForEmaService
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-06-04上午09:51
	 */
	private IFossWithOthersForEmaService fossWithOthersForEmaService;
	public void setFossWithOthersForEmaService(
			IFossWithOthersForEmaService fossWithOthersForEmaService) {
		this.fossWithOthersForEmaService = fossWithOthersForEmaService;
	}
	/**
	 * @项目：快递自动补录项目
	 * @功能：运单信息传递接口
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-06-04上午09:51
	 */
	@Override
	public Object process(Object req) throws ESBBusinessException {
		// TODO Auto-generated method stub
		ExpWaybillResponse expWaybillResponse=new ExpWaybillResponse();
		ExpWaybillRequest expWaybillRequest;
		//转换成功继续处理数据，失败为“0”并返回
		try {	
			expWaybillRequest=(ExpWaybillRequest)req;
		} catch (Exception e) {
			// TODO: handle exception
			expWaybillResponse.setState("1");
			return null;
		}
		//若请求没有数据，不进行处理，并返回成功状态“1”
		List<ExpressAutomaticMakeup> expressAutomaticMakeups;
		if(expWaybillRequest==null
				||expWaybillRequest.getExpressAutomaticMakeups()==null){
			expWaybillResponse.setState("1");
			return null;
		}else{
			expressAutomaticMakeups=expWaybillRequest.getExpressAutomaticMakeups();
		}
		/*
		 *以下代码为业务逻辑处理 
		 */
		//验证是否满足接口要求并分类为满足与不满足两个集合
		Map<String,List<EamDto>> map=eamService.validateEAM(expressAutomaticMakeups);
		List<EamDto> s=map.get("SUCCESS");
		List<EamDto> f=map.get("FAIL");
		//插入订单表
		if(s!=null &&s.size()>0){
			Map<String,List<String>> orderInsert=eamService.eamOrderInsert(s);
			if(orderInsert.get("FAIL")!=null &&orderInsert.get("FAIL").size()>0){
				List<String> temp=orderInsert.get("FAIL");
				for(String waybillNo:temp){
					for(EamDto eam:s){
						if(waybillNo.equals(eam.getWaybillNo())){
							f.add(eam);
						}
					}
				}
			}
		}
		//插入日志表
		if(f!=null &&f.size()>0){
			Map<String,List<String>> logInsert=eamService.eamLogInsert(f);
			//将失败的运单传回给“录单中心”
			if(logInsert.get("FAIL")!=null&&logInsert.get("FAIL").size()>0){
				//如果有单号插入日志表失败，则返回处理失败
				expWaybillResponse.setState("1");
			}else{
				for(EamDto eam:f){
					fossWithOthersForEmaService.postRecordCenter(eam.getWaybillNo(),"N",eam.getUploadTime(),eam.getRemarks());
				}
				expWaybillResponse.setState("1");
			}
		}else{
			expWaybillResponse.setState("1");
		}
		//返回参数
		return null;
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
