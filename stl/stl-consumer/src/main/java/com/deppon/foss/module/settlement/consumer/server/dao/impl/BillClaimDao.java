package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillClaimEntity;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IBillClaimDao;
import java.util.List;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillClaimQueryDto;



/**
 * 理赔、服务补救、退运费单据Dao
 * @author foss-qiaolifeng
 * @date 2013-1-25 上午10:23:07
 */
public class BillClaimDao extends iBatis3DaoImpl implements IBillClaimDao {

	private static final String NAMESPACE = "foss.stl.BillClaimEntityDao.";
	
	/** 
	 * 新增理赔、服务补救、退运费单据
	 * @author foss-qiaolifeng
	 * @date 2013-1-25 上午10:23:09
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.IBillClaimDao#addBillClaimEntity(com.deppon.foss.module.settlement.common.api.shared.domain.BillClaimEntity)
	 */
	@Override
	public int addBillClaimEntity(BillClaimEntity entity) {
		
		//插入理赔等实体
		return this.getSqlSession().insert(NAMESPACE + "insert", entity);
	}

	/** 
	 * 查询理赔、服务补救、退运费单据根据根据运单号
	 * @author foss-qiaolifeng
	 * @date 2013-1-25 上午10:23:11
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.IBillClaimDao#queryBillClaimEntity(java.lang.String, java.lang.String)
	 */
	@Override
	public BillClaimEntity queryBillClaimEntity(String waybillNo, String active) {
	
		//设置查询条件map
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		//设置运单、是否有效条件
		paramMap.put("waybillNo", waybillNo);
		paramMap.put("active", active);
		
		//查询理赔等实体
		return (BillClaimEntity)this.getSqlSession().selectOne(NAMESPACE + "selectBillClaimByWaybillNo", paramMap);
	}

	/** 
	 * 查询理赔总条数
	 * @author foss-qiaolifeng
	 * @date 2013-1-29 下午3:51:49
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.IBillClaimDao#queryBillClaimCount(com.deppon.foss.module.settlement.consumer.api.shared.dto.BillClaimQueryDto)
	 */
	@Override
	public Long queryBillClaimCount(BillClaimQueryDto billClaimQueryDto) {
		return (Long) getSqlSession().selectOne(NAMESPACE+"queryBillClaimCount", billClaimQueryDto);
	}

	/** 
	 * 分页查询理赔单
	 * @author foss-qiaolifeng
	 * @date 2013-1-29 下午3:51:51
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.IBillClaimDao#queryBillClaimListByPage(com.deppon.foss.module.settlement.consumer.api.shared.dto.BillClaimQueryDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillClaimEntity> queryBillClaimListByPage(
			BillClaimQueryDto billClaimQueryDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + "selectBillClaimListByParam", billClaimQueryDto, rowBounds);
		
	}

	/** 
	 * 根据运单号查询理赔信息
	 * @author foss-qiaolifeng
	 * @date 2013-1-29 下午4:27:10
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.IBillClaimDao#queryBillClaimListByWayBillNos(com.deppon.foss.module.settlement.consumer.api.shared.dto.BillClaimQueryDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillClaimEntity> queryBillClaimListByWayBillNos(
			BillClaimQueryDto billClaimQueryDto) {
		return this.getSqlSession().selectList(
				NAMESPACE + "selectBillClaimListByWayBillNos", billClaimQueryDto);
	}

	/** 
	 * 修改理赔信息
	 * @author foss-qiaolifeng
	 * @date 2013-1-30 下午3:59:50
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.IBillClaimDao#updateBillClaimEntity(com.deppon.foss.module.settlement.common.api.shared.domain.BillClaimEntity)
	 */
	@Override
	public int updateBillClaimEntity(BillClaimEntity entity) {
		
		return this.getSqlSession().update(NAMESPACE+"updateBillClaimEntity",entity);
	}

}
