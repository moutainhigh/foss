package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IComplementDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.AsyncComplementEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ComplementLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.AsyncComplementFailedQcDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ComplementLogDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.ComplementQueryDto;
import com.deppon.foss.util.define.FossConstants;

/** 
 * @className: ComplementLogDao
 * @author: ShiWei shiwei@outlook.com
 * @description: 补码日志Dao类
 * @date: 2013-7-19 下午2:34:57
 * 
 */
public class ComplementDao extends iBatis3DaoImpl implements IComplementDao {
	
	private static final String NAMESPACE = "foss.load.express.complement.";
	
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/**
	 * 新增一条补码日志
	 * @author 045923-foss-shiwei
	 * @date 2013-7-19 下午2:35:25
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IComplementLogDao#addComplementLog(com.deppon.foss.module.transfer.load.api.shared.domain.ComplementLogEntity)
	 */
	@Override
	public int addComplementLog(ComplementLogEntity logEntity) {
		this.getSqlSession().insert(NAMESPACE + "addComplementLog",logEntity);
		return FossConstants.SUCCESS;
	}

	/**
	 * 查询补码信息
	 * @author 045923-foss-shiwei
	 * @date 2013-7-23 下午1:58:55
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IComplementDao#queryComplementList(com.deppon.foss.module.transfer.load.api.shared.dto.ComplementQueryDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ComplementQueryDto> queryComplementList(ComplementQueryDto queryDto, int start, int limit) {
		RowBounds rw = new RowBounds(start,limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryComplementList",queryDto,rw);
	}

	/**
	 * 获取补码总记录数
	 * @author 045923-foss-shiwei
	 * @date 2013-7-23 下午1:59:09
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IComplementDao#queryComplementCount(com.deppon.foss.module.transfer.load.api.shared.dto.ComplementQueryDto)
	 */
	@Override
	public Long queryComplementCount(ComplementQueryDto queryDto) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryComplementCount",queryDto);
	}

	/**
	 * 查询补码退回信息
	 * @author 269701-foss-lln
	 * @date 2015-11-05 下午1:58:55
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IComplementDao#queryComplementBackList(com.deppon.foss.module.transfer.load.api.shared.dto.ComplementQueryDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ComplementQueryDto> queryComplementBackList(ComplementQueryDto queryDto, int start, int limit) {
		RowBounds rw = new RowBounds(start,limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryComplementBackList",queryDto,rw);
	}

	/**
	 * 获取补码退回总记录数
	 * @author 269701-foss-lln
	 * @date 2015-11-05 下午1:59:09
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IComplementDao#queryComplementBackCount(com.deppon.foss.module.transfer.load.api.shared.dto.ComplementQueryDto)
	 */
	@Override
	public Long queryComplementBackCount(ComplementQueryDto queryDto) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryComplementBackCount",queryDto);
	}
	/**
	 * 根据运单号查询补码日志
	 * @author 045923-foss-shiwei
	 * @date 2013-7-24 下午4:38:18
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IComplementDao#queryComplementListByWaybillNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ComplementLogEntity> queryComplementLogListByWaybillNo(String waybillNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryComplementLogListByWaybillNo",waybillNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplementLogEntity> queryComplementLogList(
			ComplementLogDto complementLogDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		
		return this.getSqlSession().selectList(
				NAMESPACE + "queryComplementLogList", complementLogDto,
				rowBounds);
	}
	
	/**
	* @description 根据运单号查询对应的目的地址
	* @param waybillNo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年7月22日 下午4:12:29
	*/
	@Override
	public String queryComplementLogAddress(String waybillNo) {
		@SuppressWarnings("unchecked")
		List<String> backValue =  this.getSqlSession().selectList(NAMESPACE + "queryComplementLogAddress",waybillNo);
		if(backValue!=null && backValue.size()>0){
			return backValue.get(0);
		}else{
			return "";
		}
	}

	@Override
	public Long queryComplementLogCount(ComplementLogDto complementLogDto) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryComplementLogCount",complementLogDto);
	}

	
  /**
	* @description 根据运单号查询补码部门
	* @param waybillNo
	* @return
	* @author duhao
	* @update 2015年8月21日 下午2:14:21
	*/
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ComplementLogEntity> queryOperationOrg(String waybillNo) {
		// TODO Auto-generated method stub

		return this.getSqlSession().selectList(NAMESPACE + "queryOperationOrgCode",waybillNo);
	}
	
//	/**
//	 * @description 通过参数运单号查询出 所有非子母件单号及母件单号
//	 * @param waybillNo
//	 * @return
//	 * @date 2015-09-10 上午9:13:56
//	 * @author hongwy
//	 */
//	@Override
//	public 	List<String> queryWaybillNos(String waybillNo){
//		return this.getSqlSession().selectList(NAMESPACE+"queryWaybillNo",waybillNo);
//	}

	@Override
	public String queryWaybillNoCZM(String waybillNo) {
		return (String) this.getSqlSession().selectOne(NAMESPACE+"queryWaybillNoCZM",waybillNo);
	}

	@Override
	public void insertAsyncComplement(AsyncComplementEntity info) {
		this.getSqlSession().insert(NAMESPACE+"insertAsyncComplement", info);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AsyncComplementEntity> findAsyncComplement() {
		return this.getSqlSession().selectList(NAMESPACE+"findAsyncComplement");
	}

	@Override
	public int deleteAsyncComplement(AsyncComplementEntity info) {
		return this.getSqlSession().delete(NAMESPACE+"deleteAsyncComplement", info);
	}

	@Override
	public void updateAsyncFailed(AsyncComplementEntity info) {
		this.getSqlSession().update(NAMESPACE+"updateAsyncFailed", info);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AsyncComplementEntity> findAsyncComplementFailed(AsyncComplementFailedQcDto info, int start, int limit) {
		return this.getSqlSession().selectList(NAMESPACE + "findAsyncComplementFailed", info,
				new RowBounds(start, limit));
	}

	@Override
	public Long cntAsyncComplementFailed(AsyncComplementFailedQcDto info) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"cntAsyncComplementFailed", info);
	}

	@Override
	public void deleteAsyncComplementFailed(AsyncComplementEntity info) {
		this.getSqlSession().delete(NAMESPACE+"deleteAsyncComplementFailed", info);
	}

}


