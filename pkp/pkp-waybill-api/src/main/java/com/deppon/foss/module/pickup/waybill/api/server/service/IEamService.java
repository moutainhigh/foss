package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.esb.inteface.domain.ludanworkflow.ExpressAutomaticMakeup;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.EamDto;
import com.deppon.foss.module.pickup.waybill.shared.vo.ExpressAutoMakeupVo;


/**
 * @项目：快递补录项目
 * @功能：定义订单表（刷单表）与日志表的增删改查
 * @author:218371-foss-zhaoyanjun
 * @date:2015-06-01下午13:40
 */
public interface IEamService extends IService{
	//订单表的新增
	public Map<String,List<String>> eamOrderInsert(List<EamDto> eamDtos);
	
	//订单表的删除
	public String eamOrderDelete(List<String> wayBillNos);
	
	//订单表的查找
	public Map<String,List<EamDto>> eamOrderQuery(List<String> wayBillNos);
	
	//日志表的新增
	public Map<String,List<String>> eamLogInsert(List<EamDto> eamDtos);
		
	//日志表的删除
	public String eamLogDelete(List<String> wayBillNos);
		
	//日志表的修改
	public Map<String,List<String>> eamLogeUpdate(List<EamDto> eamDtos);
		
	//日志表的查找
	public Map<String,List<EamDto>> eamLogeQuery(List<String> wayBillNos);
	
	//筛选订单是否满足接口开单要求
	public Map<String,List<EamDto>> validateEAM(List<ExpressAutomaticMakeup> expressAutomaticMakeups);
	
	//修改JobId
	public int updateJobIDTopByRowNum(ExpressAutoMakeupVo vo);
	
	//根据jibId查询数据
	public List<EamDto> queryGenerateUnActiveEamByJobID(String jobId);
	//补录完成后处理扫描表和日志表数据
	public void handleOrderAndLog(EamDto eamDto, String billNumberState);
}
