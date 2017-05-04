package com.deppon.foss.module.transfer.partialline.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.domain.ExpressOpreateRecordEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.LdpExternalBillTrackEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.QueryLdpTrajectoryEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.vo.QueryLdpTrajectoryVo;

public interface ILdpTrajectoryDao {
	
    List<QueryLdpTrajectoryEntity> queryLdpTrajectory(QueryLdpTrajectoryVo queryLdpTrajectoryVo,int start, int limit);
    Long queryLdpTrajectoryCount(QueryLdpTrajectoryVo queryLdpTrajectoryVo);
    public void insertLdpTrajectory(ExpressOpreateRecordEntity expressOpreateRecordEntity);
    public void insertLdpExternalBillTrack(LdpExternalBillTrackEntity ldpExternalBillTrackEntity);
    public List<ExpressOpreateRecordEntity> queryOperationRecords(String wayBillNo,int start,int limit); 
    
    public Long queryOperationRecordsCount(String wayBillNo);
    public int deleteValue(String id);
    public int deleteLdpExternalBillTrack(String id);
}
