package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IReverseBillWriteoffQueryDao;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.ReverseBillWriteoffDto;

/**
 * 反核消查询Dao实现
 * 
 * @author foss-qiaolifeng
 * @date 2012-10-24 上午11:28:51
 */
public class ReverseBillWriteoffQueryDao extends iBatis3DaoImpl implements IReverseBillWriteoffQueryDao {
	//命名空间
	private static final String NAMESPACE = "foss.stl.BillWriteoffEntityDao.";

	/**
	 * 根据参数集合，分页查询核销单列表
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-7 上午10:48:47
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IReverseBillWriteoffQueryDao#queryBillWriteoffEntityListByParams(com.deppon.foss.module.settlement.writeoff.api.shared.dto.ReverseBillWriteoffDto,
	 *      int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillWriteoffEntity> queryBillWriteoffEntityListByParams(ReverseBillWriteoffDto reverseBillWriteoffDto, int start, int limit) {
		//分页设置
		RowBounds rowBounds = new RowBounds(start, limit);
		//执行查询操作
		return this.getSqlSession().selectList(NAMESPACE + "selectEntityListByParams", reverseBillWriteoffDto,rowBounds);

	}

	/**
	 * 根据核销单号，分页查询核销单列表
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-7 上午10:48:27
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IReverseBillWriteoffQueryDao#queryBillWriteoffEntityByWriteoffBillNo(com.deppon.foss.module.settlement.writeoff.api.shared.dto.ReverseBillWriteoffDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillWriteoffEntity> queryBillWriteoffEntityByWriteoffBillNo(ReverseBillWriteoffDto reverseBillWriteoffDto, int start, int limit) {
		//分页设置
		RowBounds rowBounds = new RowBounds(start, limit);
		//执行查询操作
		return this.getSqlSession().selectList(NAMESPACE + "selectEntityByWriteoffBillNo",reverseBillWriteoffDto, rowBounds);
	}

	/**
	 * 根据运单号，分页查询核销单列表
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-7 上午10:48:23
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IReverseBillWriteoffQueryDao#queryBillWriteoffEntityByWaybillNo(com.deppon.foss.module.settlement.writeoff.api.shared.dto.ReverseBillWriteoffDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillWriteoffEntity> queryBillWriteoffEntityByWaybillNo(ReverseBillWriteoffDto reverseBillWriteoffDto, int start, int limit) {
		//分页设置
		RowBounds rowBounds = new RowBounds(start, limit);
		//执行查询操作
		return this.getSqlSession().selectList(NAMESPACE + "selectEntityListByWaybillNo",reverseBillWriteoffDto, rowBounds);
	}

	/**
	 * 根据核销单号，查询待核销的数据
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-7 上午10:48:33
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IReverseBillWriteoffQueryDao#queryBillWriteoffEntityByNo(com.deppon.foss.module.settlement.writeoff.api.shared.dto.ReverseBillWriteoffDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillWriteoffEntity> queryBillWriteoffEntityByNo(ReverseBillWriteoffDto reverseBillWriteoffDto) {
		//执行查询操作
		return this.getSqlSession().selectList(NAMESPACE + "selectBillWriteoffEntityListByWirteoffNo",reverseBillWriteoffDto);
	}

	/**
	 * 根据核销单号，查询全部核销单列表
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-20 上午10:00:52
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IReverseBillWriteoffQueryDao#queryBillWriteoffEntityALLByParams(com.deppon.foss.module.settlement.writeoff.api.shared.dto.ReverseBillWriteoffDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillWriteoffEntity> queryBillWriteoffEntityALLByParams(ReverseBillWriteoffDto reverseBillWriteoffDto) {
		//执行查询操作
		return this.getSqlSession().selectList(NAMESPACE + "selectEntityListByParams", reverseBillWriteoffDto);
	}

	/**
	 * 根据核销单号，查询全部核销单列表
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-20 上午10:00:55
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IReverseBillWriteoffQueryDao#queryBillWriteoffEntityAllByWriteoffBillNo(com.deppon.foss.module.settlement.writeoff.api.shared.dto.ReverseBillWriteoffDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillWriteoffEntity> queryBillWriteoffEntityAllByWriteoffBillNo(ReverseBillWriteoffDto reverseBillWriteoffDto) {
		//执行查询操作
		return this.getSqlSession().selectList(NAMESPACE + "selectEntityByWriteoffBillNo",reverseBillWriteoffDto);
	}

	/**
	 * 根据运单号，查询待核销的数据
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-20 上午10:00:57
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IReverseBillWriteoffQueryDao#queryBillWriteoffEntityAllByWaybillNo(com.deppon.foss.module.settlement.writeoff.api.shared.dto.ReverseBillWriteoffDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillWriteoffEntity> queryBillWriteoffEntityAllByWaybillNo(ReverseBillWriteoffDto reverseBillWriteoffDto) {
		//执行查询操作
		return this.getSqlSession().selectList(NAMESPACE + "selectEntityListByWaybillNo",reverseBillWriteoffDto);
	}

	/**
	 * 根据参数集合，获取核销单统计信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-20 下午3:27:16
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IReverseBillWriteoffQueryDao#queryBillWriteoffTotalByParams(com.deppon.foss.module.settlement.writeoff.api.shared.dto.ReverseBillWriteoffDto)
	 */
	@Override
	public ReverseBillWriteoffDto queryBillWriteoffTotalByParams(ReverseBillWriteoffDto reverseBillWriteoffDto) {
		//执行查询操作
		return (ReverseBillWriteoffDto) this.getSqlSession().selectOne(NAMESPACE + "selectWriteoffTotalByParams",reverseBillWriteoffDto);

	}

	/**
	 * 根据核销单号，获取核销单统计信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-20 下午3:27:30
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IReverseBillWriteoffQueryDao#queryBillWriteoffTotalByWriteoffBillNo(com.deppon.foss.module.settlement.writeoff.api.shared.dto.ReverseBillWriteoffDto)
	 */
	@Override
	public ReverseBillWriteoffDto queryBillWriteoffTotalByWriteoffBillNo(ReverseBillWriteoffDto reverseBillWriteoffDto) {
		//执行查询操作
		return (ReverseBillWriteoffDto) this.getSqlSession().selectOne(NAMESPACE + "selectWriteoffTotalByWriteoffBillNo",reverseBillWriteoffDto);
	}

	/**
	 * 根据运单号，获取核销单统计信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-20 下午3:27:33
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IReverseBillWriteoffQueryDao#queryBillWriteoffTotalByWaybillNo(com.deppon.foss.module.settlement.writeoff.api.shared.dto.ReverseBillWriteoffDto)
	 */
	@Override
	public ReverseBillWriteoffDto queryBillWriteoffTotalByWaybillNo(ReverseBillWriteoffDto reverseBillWriteoffDto) {
		//执行查询操作
		return (ReverseBillWriteoffDto) this.getSqlSession().selectOne(NAMESPACE + "selectWriteoffTotalByByWaybillNo",reverseBillWriteoffDto);
	}

	/**
	 * 按预付单号查询核销单
	 * 
	 * 杨书硕 
	 * 2013-7-25 下午4:00:53
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.dao.IReverseBillWriteoffQueryDao#queryBillWriteoffEntityByAdvPayillNo(com.deppon.foss.module.settlement.writeoff.api.shared.dto.ReverseBillWriteoffDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillWriteoffEntity> queryBillWriteoffEntityByAdvPayillNo( ReverseBillWriteoffDto reverseBillWriteoffDto) {
		return this.getSqlSession().selectList(NAMESPACE + "selectWriteoffEntityByAdvPayillNo",reverseBillWriteoffDto);
	}

	/**
	 * 按应付单查询核销单
	 * 
	 * 杨书硕
	 * 2013-7-25 下午4:01:32
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillWriteoffEntity> queryBillWriteoffEntityByPayableBillNo(ReverseBillWriteoffDto reverseBillWriteoffDto) {
		return  this.getSqlSession().selectList(NAMESPACE + "selectWriteoffEntityByPayableBillNo",reverseBillWriteoffDto);
	}

	/**
	 * 根据预付单号，获取核销单统计信息
	 * 
	 * 杨书硕
	 * 2013-7-25 下午6:41:25
	 */
	@Override
	public ReverseBillWriteoffDto queryBillWriteoffTotalByAdvPayillNo(
			ReverseBillWriteoffDto reverseBillWriteoffDto) {
		return (ReverseBillWriteoffDto) this.getSqlSession().selectOne(NAMESPACE + "selectWriteoffTotalByAdvPayillNo",reverseBillWriteoffDto);
	}

	/**
	 * 根据应付单号，获取核销单统计信息
	 * 
	 * 杨书硕
	 * 2013-7-25 下午6:41:25
	 */
	@Override
	public ReverseBillWriteoffDto queryBillWriteoffTotalByPayableBillNo(
			ReverseBillWriteoffDto reverseBillWriteoffDto) {
		return (ReverseBillWriteoffDto) this.getSqlSession().selectOne(NAMESPACE + "selectWriteoffTotalByPayableBillNo",reverseBillWriteoffDto);
	}
	
	/**
	 * 根据预收单号，获取核销单统计信息
	 * 
	 * 毛建强
	 * 2014-4-03 下午6:41:25
	 */
	@Override
	public ReverseBillWriteoffDto queryBillWriteoffTotalByDepositNo(
			ReverseBillWriteoffDto reverseBillWriteoffDto) {
		return (ReverseBillWriteoffDto) this.getSqlSession().selectOne(NAMESPACE + "selectWriteoffTotalByDepositNo",reverseBillWriteoffDto);
	}


	/**
	 * 根据预收单号，获取核销单信息
	 * 
	 * 毛建强
	 * 2014-4-03 下午6:41:25
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillWriteoffEntity> queryBillWriteoffEntityByDepositNo(
			ReverseBillWriteoffDto reverseBillWriteoffDto) {
		return this.getSqlSession().selectList(NAMESPACE + "selectWriteoffEntityByDepositNo",reverseBillWriteoffDto);
	}

}
