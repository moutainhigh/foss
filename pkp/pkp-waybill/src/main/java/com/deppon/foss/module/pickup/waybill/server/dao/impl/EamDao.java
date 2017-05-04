package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IEamDao;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.EamDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.TimeLookDto;
import com.deppon.foss.module.pickup.waybill.shared.vo.ExpressAutoMakeupVo;

/**
 *@项目：快递自动补录
 *@功能：订单表（刷单表）与日志表的增删改查
 *@author:218371-foss-zhaoyanjun
 *@date:2015-06-01下午14:06
 */
public class EamDao extends iBatis3DaoImpl implements IEamDao{
	private static final String NAMESPACE="foss.pkp.expressAutomaticMakeupMapper.";
	
	@Override
	public int eamOrderInsert(EamDto eamDto) {
		// TODO Auto-generated method stub
		eamDto.setCreatTime(new Date());
		eamDto.setJobId(WaybillConstants.UNKNOWN);
		return this.getSqlSession().insert(NAMESPACE+"insertEamOrder",eamDto);
	}

	@Override
	public int eamOrderDelete(List<String> wayBillNos) {
		// TODO Auto-generated method stub
		int flag=0;
		List<String> tempList=new ArrayList<String>();
		for(int i=0;i<wayBillNos.size();i=i+NumberConstants.NUMBER_500){
			int j=NumberConstants.NUMBER_500;
			if(wayBillNos.size()-i<NumberConstants.NUMBER_500){
				j=wayBillNos.size();
			}else{
				j=i+NumberConstants.NUMBER_500;
			}
			for(int k=i;k<j;k++){
				tempList.add(wayBillNos.get(k));
			}
			flag=flag+this.getSqlSession().delete(NAMESPACE+"deleteEamOrder",tempList);
		}
		return flag;
	}

	@Override
	public List<EamDto> eamOrderQuery(String waybillNo) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(NAMESPACE+"selectEamOrder",waybillNo);
	}

	@Override
	public int eamLogInsert(EamDto eamDto) {
		// TODO Auto-generated method stub
		eamDto.setCreatTime(new Date());
		return this.getSqlSession().insert(NAMESPACE+"insertEamLogger",eamDto);
	}

	@Override
	public int eamLogDelete(List<String> wayBillNos) {
		// TODO Auto-generated method stub
		int flag=0;
		List<String> tempList=new ArrayList<String>();
		for(int i=0;i<wayBillNos.size();i=i+NumberConstants.NUMBER_500){
			int j=NumberConstants.NUMBER_500;
			if(wayBillNos.size()-i<NumberConstants.NUMBER_500){
				j=wayBillNos.size();
			}else{
				j=i+NumberConstants.NUMBER_500;
			}
			for(int k=i;k<j;k++){
				tempList.add(wayBillNos.get(k));
			}
			flag=flag+this.getSqlSession().delete(NAMESPACE+"deleteEamLogger",tempList);
		}
		return flag;
	}

	@Override
	public int eamLogeUpdate(Map<String,Object> map) {
		// TODO Auto-generated method stub
		map.put("modifyTime", new Date());
		return this.getSqlSession().update(NAMESPACE+"updateEamLogger",map);
	}

	@Override
	public List<EamDto> eamLogeQuery(String waybillNo) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(NAMESPACE+"selectEamLogger",waybillNo);
	}
	
	//修改需要处理的订单数据的JOBID
	public int updateJobIDTopByRowNum(ExpressAutoMakeupVo vo){
		return this.getSqlSession().update(NAMESPACE+"updateJobIDTopByRowNum", vo);
	}
	
	//根据jobId查询出需要处理的订单
	public List<EamDto> queryGenerateUnActiveEamByJobID(String jobId){
		return this.getSqlSession().selectList(NAMESPACE+"selectGenerateUnActiveEamByJobID",jobId);
	}
	@Override
	public void timeLookInsert(TimeLookDto timelookDto) {
		// TODO Auto-generated method stub
		timelookDto.setJobId(WaybillConstants.UNKNOWN);
	    this.getSqlSession().insert(NAMESPACE+"insertTimeLook",timelookDto);
	}

	@Override
	public void  timeLookDelete(String wayBillNo) {
		this.getSqlSession().delete(NAMESPACE+"deleteTimeLook",wayBillNo);
	}
	
	//修改需要处理的订单数据的JOBID
	public int updateJobIDtimeLookByRowNum(ExpressAutoMakeupVo vo){
		return this.getSqlSession().update(NAMESPACE+"updateJobIDtimeLookByRowNum", vo);
	}
	//根据jobId查询出需要处理的订单
	public List<TimeLookDto> querytimeLookActiveEamByJobID(String jobId){
		return this.getSqlSession().selectList(NAMESPACE+"querytimeLookActiveEamByJobID",jobId);
	}
}
