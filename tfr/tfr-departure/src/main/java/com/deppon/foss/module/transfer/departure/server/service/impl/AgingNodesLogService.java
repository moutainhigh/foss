package com.deppon.foss.module.transfer.departure.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.departure.api.server.dao.IAgingNodesLogDao;
import com.deppon.foss.module.transfer.departure.api.server.service.IAgingNodesLogService;
import com.deppon.foss.module.transfer.departure.api.shared.domain.AgingNodesLogEntity;
import com.deppon.foss.module.transfer.departure.api.shared.vo.AgingNodesLogVo;
import com.deppon.foss.module.transfer.load.api.server.service.ITruckTaskService;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.util.UUIDUtils;

public class AgingNodesLogService implements IAgingNodesLogService{
	private IAgingNodesLogDao agingNodesLogDao;
	
	public void setAgingNodesLogDao(IAgingNodesLogDao agingNodesLogDao) {
		this.agingNodesLogDao = agingNodesLogDao;
	}
	private ITruckTaskService truckTaskService;
	
	public void setTruckTaskService(ITruckTaskService truckTaskService) {
		this.truckTaskService = truckTaskService;
	}

	/**
	 *查询修改出发到达时间日志
	 *@author foss-heyongdong
	 *@date 2014年4月18日 18:01:16
	 *@param billNo 
	 *@see com.deppon.foss.module.transfer.departure.api.server.service.IAgingNodesLogService#queryAgingNodesLog(java.lang.String)
	 */
	@Override
	public List<AgingNodesLogEntity> queryAgingNodesLog(String billNo) {
		List<AgingNodesLogEntity> agingNodesLogs = agingNodesLogDao.queryAgingNodesLog(billNo);
		return agingNodesLogs;
	}
	
	/**
	 * 修改时效节点并保存修改日志
	 * @author foss-heyongdong 
	 * @param agingNodesLogVo
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.IAgingNodesLogService#saveAgingNodesLog(com.deppon.foss.module.transfer.departure.api.shared.vo.AgingNodesLogVo)
	 */
	@Override
	@Transactional
	public String saveAgingNodesLog(AgingNodesLogVo agingNodesLogVo) {
		
		List<TruckTaskDetailEntity> truckTaskDetails=truckTaskService.queryTruckTaskDetail(agingNodesLogVo.getBillNo());
		TruckTaskDetailEntity truckTaskDetailEntity=null;
		
		if(CollectionUtils.isNotEmpty(truckTaskDetails)&&truckTaskDetails.size()>0){
			truckTaskDetailEntity=truckTaskDetails.get(0);
		}else{
			throw new TfrBusinessException("没有该单号："+agingNodesLogVo.getBillNo()+"的车辆任务！");
		}
		// 获取当前用户、部门等
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//构造修改时效节点日志实体
		AgingNodesLogEntity aglogs = new AgingNodesLogEntity();
		//id
		aglogs.setId(UUIDUtils.getUUID());
		//修改时间
		aglogs.setModifyTime(new Date());
		//关联单号
		aglogs.setRelationbillNos(agingNodesLogVo.getRelationbillNos());
		//修改后时间
		aglogs.setAfterModifyTime(agingNodesLogVo.getAfterModifyTime());
		//修改的单号
		aglogs.setBillNo(agingNodesLogVo.getBillNo());
		//设置修改人
		aglogs.setModifyCode(currentInfo.getEmpCode());
		//设置修改人姓名
		aglogs.setModifyName(currentInfo.getEmpName());
		//修改时间类型为发车时
		if(StringUtils.equals(agingNodesLogVo.getModifyType(),"DEPART_TIME")){
			if(StringUtils.equals(truckTaskDetailEntity.getState(),TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART)){
				throw new TfrBusinessException("车辆未出发不能修改发车时间！");
			}else{
				//需要更新的车辆任务明细实体参数
				TruckTaskDetailEntity temp = new TruckTaskDetailEntity();
				temp.setId(truckTaskDetailEntity.getId());
				temp.setActualDepartTime(agingNodesLogVo.getAfterModifyTime());
				temp.setManualDepartTime(agingNodesLogVo.getAfterModifyTime());
				//跟新车辆任务明细状态
				agingNodesLogDao.updateTrucktastDeteil(temp);
				
				//插入修改日志
				aglogs.setModifyType(agingNodesLogVo.getModifyType());
				aglogs.setBeforeModifyTime(truckTaskDetailEntity.getActualDepartTime());
				agingNodesLogDao.saveAgingNodesLog(aglogs);
			}
		}else if(StringUtils.equals(agingNodesLogVo.getModifyType(),"ARRID_TIME")){
			if(StringUtils.equals(truckTaskDetailEntity.getState(),TaskTruckConstant.TASK_TRUCK_STATE_UNDEPART)||
					StringUtils.equals(truckTaskDetailEntity.getState(),TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY)){
				throw new TfrBusinessException("车辆未到达不能修改到达世间！");
			}else{
				//需要更新的车辆任务明细实体参数
				TruckTaskDetailEntity temp =new  TruckTaskDetailEntity();
				temp.setId(truckTaskDetailEntity.getId());
				//如果人工到达时间为空修改时间到达时间否则修改人工到达时间（应为到达时间优先取的是人工到达时间）
				//if(truckTaskDetailEntity.getManualArriveTime()==null){
					temp.setActualArriveTime(agingNodesLogVo.getAfterModifyTime());
				//}else{
					temp.setManualArriveTime(agingNodesLogVo.getAfterModifyTime());
				//}
				
				//跟新车辆任务明细状态
				agingNodesLogDao.updateTrucktastDeteil(temp);
				
				
				//插入修改日志
				aglogs.setModifyType(agingNodesLogVo.getModifyType());
				if(truckTaskDetailEntity.getManualArriveTime()==null){
					aglogs.setBeforeModifyTime(truckTaskDetailEntity.getActualDepartTime());
				}else{
					aglogs.setBeforeModifyTime(truckTaskDetailEntity.getManualArriveTime());
				}
				
				agingNodesLogDao.saveAgingNodesLog(aglogs);
			}
		}else{
			throw new TfrBusinessException("没有该修改类型！");
		}
		return truckTaskDetailEntity.getId();
	}
	/**
	 * 更具单号查询同一个车辆任务下的相关联单号
	 * @date 2014年4月29日 09:23:29
	 * @Param billNo
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.IAgingNodesLogService#queryRelationbillNos(java.lang.String)
	 */
	@Override
	public String queryRelationbillNos(String billNo) {
		List<String> billNos=agingNodesLogDao.queryRelationbillNos(billNo);
		StringBuffer relationbillNos=new StringBuffer();
		//把所有单号合并成一个字符串用"/"
		if(CollectionUtils.isNotEmpty(billNos)&&billNos.size()>0){
			for(int i=0;i<billNos.size();i++){
				String temp=billNos.get(i);
				if(i==(billNos.size()-1)){
					relationbillNos.append(temp);
				}else{
					relationbillNos.append(temp);
					relationbillNos.append("/");
				}
				
			}
		}else{
			throw new TfrBusinessException("没有该单号："+billNo+"的车辆任务！");
		}
		return relationbillNos.toString();
	}

}
