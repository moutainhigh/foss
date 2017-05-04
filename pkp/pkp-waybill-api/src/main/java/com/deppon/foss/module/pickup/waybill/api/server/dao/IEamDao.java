package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;
import java.util.Map;
import com.deppon.foss.module.pickup.waybill.shared.domain.EamDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.TimeLookDto;
import com.deppon.foss.module.pickup.waybill.shared.vo.ExpressAutoMakeupVo;

/**
 *@项目：快递自动补录
 *@功能：订单表（刷单表）与日志表的增删改查
 *@author:218371-foss-zhaoyanjun
 *@date:2015-06-01下午14:06
 */
public interface IEamDao {
	// 订单表的新增
	public int eamOrderInsert(EamDto eamDto);

	// 订单表的删除
	public int eamOrderDelete(List<String> waybillNos);

	// 订单表的查找
	public List<EamDto> eamOrderQuery(String waybillNo);

	// 日志表的新增
	public int eamLogInsert(EamDto eamDto);

	// 日志表的删除
	public int eamLogDelete(List<String> waybillNos);

	// 日志表的修改
	public int eamLogeUpdate(Map<String,Object> map);

	// 日志表的查找
	public List<EamDto> eamLogeQuery(String waybillNo);
	
	//修改JobId
	public int updateJobIDTopByRowNum(ExpressAutoMakeupVo vo);
	
	//根据jibId查询数据
	public List<EamDto> queryGenerateUnActiveEamByJobID(String jobId);

	public void timeLookInsert(TimeLookDto timelookDto);

	public void timeLookDelete(String wayBillNo);
	//修改JobId
	public int updateJobIDtimeLookByRowNum(ExpressAutoMakeupVo vo);
		
	//根据jibId查询数据
	public List<TimeLookDto> querytimeLookActiveEamByJobID(String jobId);
}
