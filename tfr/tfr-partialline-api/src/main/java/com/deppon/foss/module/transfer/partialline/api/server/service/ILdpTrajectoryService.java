package com.deppon.foss.module.transfer.partialline.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.ExpressOpreateRecordEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.QueryLdpTrajectoryEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.vo.QueryLdpTrajectoryVo;


/**
 * @author 218427
 * 查询外发代理轨迹
 * 2015年3月9日
 */
public interface ILdpTrajectoryService extends IService {
    
	List<QueryLdpTrajectoryEntity> queryLdpTrajectory(QueryLdpTrajectoryVo queryLdpTrajectoryVo,int start,int limit);
	
	Long queryLdpTrajectoryCount(QueryLdpTrajectoryVo queryLdpTrajectoryVo);
	
	public  void insertLdpTrajectory(QueryLdpTrajectoryVo queryLdpTrajectoryVo );
	
	public List<ExpressOpreateRecordEntity> queryOperationRecords(String wayBillNo,int start,int limit); 
	
	public Long queryOperationRecordsCount(String wayBillNo);
	
	public  void deleteValue(String id);
}
