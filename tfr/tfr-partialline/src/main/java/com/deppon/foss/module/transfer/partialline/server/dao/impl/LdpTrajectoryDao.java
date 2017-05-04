package com.deppon.foss.module.transfer.partialline.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.partialline.api.server.dao.ILdpTrajectoryDao;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.ExpressOpreateRecordEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.LdpExternalBillTrackEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.QueryLdpTrajectoryEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.vo.QueryLdpTrajectoryVo;

/**
 * @author hongwy
 * @date 2015-3-7
 */
public class LdpTrajectoryDao extends iBatis3DaoImpl implements ILdpTrajectoryDao {
   
	private static String prefix="foss.partialline.queryLdpTrajectoryMapper.";
	
	/**
	 * 
	 ***/ 
	@Override
	public List<QueryLdpTrajectoryEntity> queryLdpTrajectory(QueryLdpTrajectoryVo queryLdpTrajectoryVo,int start,int limit){
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(prefix+"queryLdpTrajectory",queryLdpTrajectoryVo,rowBounds);
	}
	
	/**
	 * 
	 ***/ 
	@Override
	public Long queryLdpTrajectoryCount(QueryLdpTrajectoryVo queryLdpTrajectoryVo){
		return (Long)this.getSqlSession().selectOne(prefix+"queryLdpTrajectoryCount",queryLdpTrajectoryVo);
	}
	
	/**
	 * 
	 ***/ 
	@Override
	public void  insertLdpTrajectory(ExpressOpreateRecordEntity expressOpreateRecordEntity){
		
		 this.getSqlSession().insert(prefix+"insertLdpTrajectory",expressOpreateRecordEntity);
   }
	
	/**
	 * 
	 ***/ 
	@Override
	public void insertLdpExternalBillTrack(LdpExternalBillTrackEntity ldpExternalBillTrackEntity){
		this.getSqlSession().insert(prefix+"insertLdpExternalBillTrack",ldpExternalBillTrackEntity);
	}
	
	/**
	 * 
	 ***/ 
	@Override
	public List<ExpressOpreateRecordEntity> queryOperationRecords(String wayBillNo,int start,int limit){
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(prefix+"queryOperationRecords",wayBillNo,rowBounds);
	}
	
	/**
	 * 
	 ***/ 
	@Override
	public Long queryOperationRecordsCount(String wayBillNo){
		
		return (Long)this.getSqlSession().selectOne(prefix+"queryOperationRecordsCount",wayBillNo);
	}
	
	/**
	 * 
	 ***/ 
	@Override
	public int deleteValue(String id) {
		return this.getSqlSession().delete(prefix+"deleteValue",id);
	}
	
	/**
	 * 
	 ***/ 
	@Override
	public int deleteLdpExternalBillTrack(String id) {
		return this.getSqlSession().delete(prefix+"deleteLdpExternalBillTrack",id);
	}
}
