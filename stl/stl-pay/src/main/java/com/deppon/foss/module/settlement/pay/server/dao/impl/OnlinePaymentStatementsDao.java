package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.pay.api.server.dao.IOnlinePaymentStatementsDao;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillSOAOnlineResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillSOAOnlineResultListDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.StatementOnlineQueryDto;
/**
 * 
 * DAO：对账单网上支付服务接口实现类
 * @author 088933-foss-zhangjiheng
 * @date 2012-11-29 下午5:38:32
 */
public class OnlinePaymentStatementsDao extends iBatis3DaoImpl implements IOnlinePaymentStatementsDao {
	private static final String NAMESPACES = "foss.stl.StatementOfAccountEntityDao.";// 命名空间路径
	/**
	 * 
	 * 按客户编码和日期查询对账单信息
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-29 下午6:49:36
	 */
	@Override
	public BillSOAOnlineResultListDto queryCountStatementByCustomer(
			StatementOnlineQueryDto queryDto) {
		return (BillSOAOnlineResultListDto) this.getSqlSession().selectOne(NAMESPACES+"selectCountByCustomer", queryDto);
	}
	/**
	 * 
	 * 按客户编码和日期查询对账单信息（分页）
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-29 下午6:49:40
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillSOAOnlineResultDto> queryStatementByCustomer(
			StatementOnlineQueryDto queryDto, int pageNo, int pageSize) {
		RowBounds rowBounds=new RowBounds(pageNo, pageSize);
		return (List<BillSOAOnlineResultDto>) this.getSqlSession().selectList(NAMESPACES+"selectByCustomer", queryDto, rowBounds);
	}
	/**
	 * 
	 * 按对账单号查询对账单
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-29 下午6:49:44
	 */
	@Override
	public BillSOAOnlineResultDto queryStatementByNo(
			StatementOnlineQueryDto queryDto) {
		return (BillSOAOnlineResultDto) this.getSqlSession().selectOne(NAMESPACES+"selectByNo", queryDto);
	}
	/**
	 * 
	 * 锁定对账单信息
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-29 下午6:54:15
	 */
	@Override
	public int lockOnlinePaymentStatementBill(StatementOnlineQueryDto queryDto) {
		return this.getSqlSession().update(NAMESPACES+"updateForUnlockDate", queryDto);
		
	}

}
