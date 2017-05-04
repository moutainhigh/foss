package com.deppon.foss.module.transfer.partialline.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.trackings.api.server.service.IPushTrackForCaiNiaoService;
import com.deppon.foss.module.trackings.api.server.service.IWaybillTrackingsService;
import com.deppon.foss.module.trackings.api.shared.define.WaybillTrackingsConstants;
import com.deppon.foss.module.trackings.api.shared.domain.SynTrackingEntity;
import com.deppon.foss.module.trackings.api.shared.dto.WaybillTrackingsDto;
import com.deppon.foss.module.transfer.partialline.api.server.dao.ILdpTrajectoryDao;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpTrajectoryService;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.ExpressOpreateRecordEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.LdpExternalBillTrackEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.QueryLdpTrajectoryEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.exception.ExternalBillException;
import com.deppon.foss.module.transfer.partialline.api.shared.vo.QueryLdpTrajectoryVo;
import com.deppon.foss.util.UUIDUtils;

public class LdpTrajectoryService implements ILdpTrajectoryService {
    
	
    private ILdpTrajectoryDao ldpTrajectoryDao;
    
    private IWaybillTrackingsService waybillTrackingsService;
    
    /**轨迹service***/
	private IPushTrackForCaiNiaoService pushTrackForCaiNiaoService;

	/**
	 * 获取运单信息
	 */
	private IWaybillDao waybillDao;
	
	public void setWaybillTrackingsService(
			IWaybillTrackingsService waybillTrackingsService) {
		this.waybillTrackingsService = waybillTrackingsService;
	}
    
	public void setLdpTrajectoryDao(ILdpTrajectoryDao ldpTrajectoryDao) {
		this.ldpTrajectoryDao = ldpTrajectoryDao;
	}

	public void setPushTrackForCaiNiaoService(
			IPushTrackForCaiNiaoService pushTrackForCaiNiaoService) {
		this.pushTrackForCaiNiaoService = pushTrackForCaiNiaoService;
	}

	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}
	
	/**
	 * 
	 ***/ 
	@Override
	public List<QueryLdpTrajectoryEntity> queryLdpTrajectory(QueryLdpTrajectoryVo queryLdpTrajectoryVo,int start, int limit){
		if(queryLdpTrajectoryVo==null){
			throw new ExternalBillException("参数为空");
		}
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		queryLdpTrajectoryVo.setOrgCode(orgCode);
		List<QueryLdpTrajectoryEntity> list=ldpTrajectoryDao.queryLdpTrajectory(queryLdpTrajectoryVo, start, limit);
		return list;
	}
	
	/**
	 * 
	 ***/ 
	@Override
	public Long queryLdpTrajectoryCount(QueryLdpTrajectoryVo queryLdpTrajectoryVo){
		
		return ldpTrajectoryDao.queryLdpTrajectoryCount(queryLdpTrajectoryVo);
	}
	
	/**
	 * 
	 ***/ 
	@Override
	@Transactional
	public  void insertLdpTrajectory(QueryLdpTrajectoryVo queryLdpTrajectoryVo ){
		CurrentInfo user = FossUserContext.getCurrentInfo();
		Date newDate=new Date();
		if(queryLdpTrajectoryVo==null){
			throw new ExternalBillException("参数为空");
		}
		//外发单操作记录实体，用于后台数据插入
		ExpressOpreateRecordEntity expressOpreateRecordEntity=queryLdpTrajectoryVo.getExpressOpreateRecordEntity();
		//外发轨迹实体
		LdpExternalBillTrackEntity ldpExternalBillTrackEntity=queryLdpTrajectoryVo.getLdpExternalBillTrackEntity();
		
		if(expressOpreateRecordEntity==null||ldpExternalBillTrackEntity==null){
			throw new ExternalBillException("轨迹操作记录参数为空");
		}
		
		if(expressOpreateRecordEntity.getWayBillNo()==null||expressOpreateRecordEntity.getWayBillNo().equals("")){
			throw new ExternalBillException("运单号为空");
		}
		if(StringUtils.isBlank(expressOpreateRecordEntity.getOperationCity())){
			throw new ExternalBillException("操作城市不能为空");
		}
		
		String notes="";
		
		
		//if(expressOpreateRecordEntity.getNotes()==null||StringUtils.isBlank(expressOpreateRecordEntity.getNotes().trim())){
			
			//操作类型，1-到达，2-出发，3-派送
			if(StringUtils.equals(expressOpreateRecordEntity.getOperationType(), "1")){
				notes = expressOpreateRecordEntity.getOperationCity() + "到达";
			}else if(StringUtils.equals(expressOpreateRecordEntity.getOperationType(), "3")){
				notes = expressOpreateRecordEntity.getOperationCity() + "派送";
			}else if(StringUtils.equals(expressOpreateRecordEntity.getOperationType(), "2")){
				notes = expressOpreateRecordEntity.getOperationCity() + "出发";
			}else{
				notes = expressOpreateRecordEntity.getOperationCity() + expressOpreateRecordEntity.getOperationType();
			}
		//}else{
			//notes=expressOpreateRecordEntity.getNotes();
		//}
		expressOpreateRecordEntity.setNotes(notes);
		ldpExternalBillTrackEntity.setOperationDescribe(notes);
		expressOpreateRecordEntity.setId(UUIDUtils.getUUID());
		expressOpreateRecordEntity.setManualOperationTime(newDate);
		expressOpreateRecordEntity.setOperatorCode(user.getEmpCode());
		expressOpreateRecordEntity.setOperatorName(user.getEmpName());
		
		String id= UUIDUtils.getUUID();
		
		ldpExternalBillTrackEntity.setId(id);
		ldpExternalBillTrackEntity.setCreateDate(newDate);
		ldpExternalBillTrackEntity.setCreateUser(user.getEmpCode());
		ldpExternalBillTrackEntity.setOperationUserName(user.getEmpName());
		ldpExternalBillTrackEntity.setOperationTime(expressOpreateRecordEntity.getUpdateTime());
		ldpExternalBillTrackEntity.setTraceId(expressOpreateRecordEntity.getId());
		
		ldpTrajectoryDao.insertLdpTrajectory(expressOpreateRecordEntity);
		ldpTrajectoryDao.insertLdpExternalBillTrack(ldpExternalBillTrackEntity);
		
		
		
		//插入轨迹
		try{
			String type= WaybillTrackingsConstants.OPERATE_TYPE_LDP_TRACK_ARRIVE;
			
			if(StringUtils.equals(expressOpreateRecordEntity.getOperationType(), "2")){
				type = WaybillTrackingsConstants.OPERATE_TYPE_LDP_TRACK_LEAVE;
			}else if(StringUtils.equals(expressOpreateRecordEntity.getOperationType(), "3")){
				type = WaybillTrackingsConstants.OPERATE_TYPE_LDP_TRACK_DELIVER;
			}
			WaybillTrackingsDto trackDto = new WaybillTrackingsDto();
			trackDto.setWaybillNo(ldpExternalBillTrackEntity.getWaybillNo());
			trackDto.setOperateType(type);
			trackDto.setOperateDeptCode(ldpExternalBillTrackEntity.getAgentOrgCode()); 
			trackDto.setOperateDeptName(ldpExternalBillTrackEntity.getAgentOrgName()); 
			trackDto.setOperateTime(ldpExternalBillTrackEntity.getOperationTime());
			trackDto.setOperatorName(user.getEmpName());
			trackDto.setOperateDesc(ldpExternalBillTrackEntity.getOperationcity());
			
			waybillTrackingsService.addOneWaybillTrack(trackDto);
			
			//插入待同步轨迹表中
			SynTrackingEntity synTrackEntity = new SynTrackingEntity();
			
			synTrackEntity.setId(UUIDUtils.getUUID());
			synTrackEntity.setWayBillNo(ldpExternalBillTrackEntity.getWaybillNo());
			synTrackEntity.setTrackInfo(ldpExternalBillTrackEntity.getOperationDescribe());
			synTrackEntity.setEventType(type);
			synTrackEntity.setOperateCity(ldpExternalBillTrackEntity.getOperationcity());
			synTrackEntity.setOrgCode(ldpExternalBillTrackEntity.getAgentOrgCode());
			synTrackEntity.setOrgName(ldpExternalBillTrackEntity.getAgentOrgName());
			synTrackEntity.setOperateTime(ldpExternalBillTrackEntity.getOperationTime());
			synTrackEntity.setOperatorName(user.getEmpName());
			
			//2016-8-11 16:08:23 311396 加上目的部门名称
			WaybillEntity waybillEntity = waybillDao.queryWaybillByNo(ldpExternalBillTrackEntity.getWaybillNo());//获取运单信息
			if (waybillEntity != null) {
				synTrackEntity.setDestinationDeptName(waybillEntity.getCustomerPickupOrgName());
			}
			pushTrackForCaiNiaoService.addSynTrack(synTrackEntity);
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * 
	 ***/ 
	@Override
	public List<ExpressOpreateRecordEntity> queryOperationRecords(String wayBillNo,int start,int limit){
		
		if(StringUtils.isBlank(wayBillNo)){
			throw new ExternalBillException("运单号为空");
		}
    	 return ldpTrajectoryDao.queryOperationRecords(wayBillNo, start, limit);
     }
	
	/**
	 * 
	 ***/ 
	@Override
	public Long queryOperationRecordsCount(String wayBillNo){
		return ldpTrajectoryDao.queryOperationRecordsCount(wayBillNo);
	}
	
	/**
	 * 
	 ***/ 
	@Override
	public void deleteValue(String id){
		ldpTrajectoryDao.deleteValue(id);
		ldpTrajectoryDao.deleteLdpExternalBillTrack(id);
	}
}
