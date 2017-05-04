package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.pay.api.server.dao.IOnlinePaymentReceiveBillDao;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableOnlineQueryDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillSOADOnlineResultDto;
/**
 * 
 * 网上支付查询应收单信息DAO接口
 * @author 088933-foss-zhangjiheng
 * @date 2012-11-19 下午3:59:49
 */
public class OnlinePaymentReceiveBillDao extends iBatis3DaoImpl implements
		IOnlinePaymentReceiveBillDao {
	private static final String NAMESPACES = "foss.stl.BillReceivableEntityDao.";// 命名空间路径
	/**
	 * 
	 * 网上支付查询未核销的应收单信息，按客户编码查询
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-19 下午3:46:28
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryReceiveBillInfoByCustomer(
			BillReceivableOnlineQueryDto queryDto,int pageNo,int pageSize) {
		RowBounds rowBounds=new RowBounds(pageNo*pageSize,pageSize);
		return (List<BillReceivableEntity>) this.getSqlSession().selectList(NAMESPACES+"selectByCustomer", 
				queryDto, rowBounds);
	}
	
	/**
	 * 
	 * 网上支付查询未核销的应收单信息，按业务客户编码查询,求总条数
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-19 下午3:46:28
	 */
	@Override
	public BillSOADOnlineResultDto queryCountReceiveBillInfoByCustomer(
			BillReceivableOnlineQueryDto queryDto) {
		return (BillSOADOnlineResultDto) this.getSqlSession().selectOne(NAMESPACES+"selectCountByCustomer", queryDto);
	}
	
	/**
	 * 
	 * 网上支付查询未核销的应收单信息，按运单号+手机号码查询(最多输入10个单号，所以不执行分页查询)
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-19 下午3:46:28
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryReceiveBillInfoByWaybillNo(
			BillReceivableOnlineQueryDto queryDto) {
		return (List<BillReceivableEntity>) this.getSqlSession().selectList(NAMESPACES+"selectByWaybillNo", 
				queryDto);
	}
	
	
	/**
	 * 
	 * 网上支付查询未核销的应收单信息，按业务日期查询查询
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-19 下午3:46:28
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryReceiveBillInfoByDate(
			BillReceivableOnlineQueryDto queryDto, int pageNo, int pageSize) {
		RowBounds rowBounds=new RowBounds(pageNo*pageSize,pageSize);
		return (List<BillReceivableEntity>) this.getSqlSession().selectList(NAMESPACES+"selectByDate", 
				queryDto, rowBounds);
	}
	
	/**
	 * 
	 * 网上支付查询未核销的应收单信息，按业务日期查询查询,求总条数
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-19 下午3:46:28
	 */
	@Override
	public BillSOADOnlineResultDto queryCountReceiveBillInfoByDate(
			BillReceivableOnlineQueryDto queryDto) {
		return (BillSOADOnlineResultDto) this.getSqlSession().selectOne(NAMESPACES+"selectCountByDate", queryDto);
	}
	
	/**
	 * 
	 * 网上支付查询未核销的应收单信息，按运单号+客户编码
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-19 下午3:46:28
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillReceivableEntity> queryReceiveBillInfoByWaybillAndCus(
			BillReceivableOnlineQueryDto queryDto) {
		return (List<BillReceivableEntity>) this.getSqlSession().selectList(NAMESPACES+"selectByWaybillNoAndCustomer",queryDto);
	}
	
	
	/**
	 * 锁定应收单信息
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-23 上午9:05:22
	 */
	@Override
	public int updateReceiveBillInfoForLock(
			BillReceivableOnlineQueryDto queryDto) {
		return this.getSqlSession().update(NAMESPACES+"updateByUnlockDate", queryDto);
	}


}
