package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.pay.api.server.dao.ICashCollectionRptDEntityDao;
import com.deppon.foss.module.settlement.pay.api.shared.domain.CashCollectionRptDEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInDDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInDResultDto;

/**
 * 现金收入报表明细Dao
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-26 上午9:04:00
 */
public class CashCollectionRptDEntityDao extends iBatis3DaoImpl implements
		ICashCollectionRptDEntityDao {

	private static final String NAMESPACE = "foss.stl.CashCollectionRptDEntityDao.";// 命名现金收入报表明细空间

	/** 
	 * 查询现金收入（缴款）明细
	 * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * CHECK
	 * @author 095793-foss-LiQin
	 * @date 2012-12-12 下午7:34:21
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.ICashCollectionRptDEntityDao#queryByCashRecPayInD(com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CashCollectionRptDEntity> queryByCashRecPayInD(
			BillCashRecPayInDDto billCashRecPayInDDto,int start,int limit) {
		//现金收入缴款报表dto输入是否为空		
		if(null!=billCashRecPayInDDto){
			//根据条件查询现金收入缴款报表明细信息			
			RowBounds rowBound=new RowBounds(start,limit);
			return this.getSqlSession().selectList(NAMESPACE+"selectCashRecPayInDetail",billCashRecPayInDDto,rowBound);
		}
		return null;
	}
	
	
	/** 
	 * 查询现金缴款报表明细条数
	 * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * CHECK
	 * @author 095793-foss-LiQin
	 * @date 2013-3-28 上午11:16:15
	 * @param billCashRecPayInDDto
	 * @return
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.ICashCollectionRptDEntityDao#queryCountByCashRecPayDIn(com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInDDto)
	 */
	@Override
	public BillCashRecPayInDResultDto queryCountByCashRecPayDIn(
			BillCashRecPayInDDto billCashRecPayInDDto) {
		//查询现金缴款报表明细总条数
		if(null!=billCashRecPayInDDto){
			return (BillCashRecPayInDResultDto)this.getSqlSession().selectOne(NAMESPACE+"selectCountCashRecPayInDetail",billCashRecPayInDDto);
		}
		return null;
	}

	/** 
	 * (提供财务自助接口)根据现金收入汇总传入报表编号、部门查明细
	 * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * 	CHECK
	 * @author 095793-foss-LiQin
	 * @date 2012-12-14 下午4:52:42
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.ICashCollectionRptDEntityDao#queryUpdateCashinDComerpt(com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInDDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CashCollectionRptDEntity> queryUpdateCashinDComerpt(
			BillCashRecPayInDDto billCashRecDDto) {
		return this.getSqlSession().selectList(NAMESPACE+"selectUpdateCashRecPayInDetail",billCashRecDDto);
	}

	/** 
	 * 更新现金收入（缴款）报表明细已缴款金额和未缴款金额
	 * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * CHECK
	 * @author 095793-foss-LiQin
	 * @date 2012-12-15 上午9:16:04
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.ICashCollectionRptDEntityDao#updateCashincomerptD(com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInDDto)
	 */
	@Override
	public int updateCashincomerptD(List<CashCollectionRptDEntity> perList ,BillCashRecPayInDDto billCashRecDDto) {
		Map<String ,Object> map = new HashMap<String, Object>();
		map.put("perList" ,perList);
		map.put("billCashRecDDto" ,billCashRecDDto);
		return this.getSqlSession().update(NAMESPACE+"updateCashRecPayInD", map);
	}


	/** 
	 * 根据部门查询累计未交账的营业款和预收款金额
	 * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * HAS CHECK
	 * @author 095793-foss-LiQin
	 * @date 2013-4-27 上午11:13:17
	 * @param billCashRecDDto
	 * @return
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.ICashCollectionRptDEntityDao#queryDeptUncollectedAmount(com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInDDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CashCollectionRptDEntity> queryDeptUncollectedAmount(String orgCode,String paymentType) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgCode", orgCode);
		map.put("paymentType", paymentType);
		return this.getSqlSession().selectList(NAMESPACE+"selectDeptUncollectedAmount", map);
	}


	 /**
     * 根据DTO查询部门的已交款金额
     *
     *
     * @author 092036-foss-bochenlong
     * @date 2014-2-17 下午3:39:53 
     * @param billCashRecDDto
     * @return
     */
	@Override
	public BigDecimal queryPaiAmount(BillCashRecPayInDDto billCashRecDDto) {
		return (BigDecimal) this.getSqlSession().selectOne(NAMESPACE+"queryPaiAmount" ,billCashRecDDto);
	}

	
	/**
     * 根据DTO查询部门的最早缴款时间
     *
     *
     * @author 092036-foss-bochenlong
     * @date 2014-2-17 下午3:39:53 
     * @param billCashRecDDto
     * @return
     */
	@Override
	public Date queryEarLiestDate(BillCashRecPayInDDto billCashRecDDto) {
		return (Date) this.getSqlSession().selectOne(NAMESPACE+"queryEarLiestDate" ,billCashRecDDto);
	}
}
