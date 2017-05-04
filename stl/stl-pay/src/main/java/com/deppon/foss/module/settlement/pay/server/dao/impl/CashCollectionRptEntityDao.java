package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.pay.api.server.dao.ICashCollectionRptEntityDao;
import com.deppon.foss.module.settlement.pay.api.shared.domain.CashCollectionRptEntity;

/**
 * 查询现金收入缴款报表Dao
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-26 上午9:04:00
 */
public class CashCollectionRptEntityDao extends iBatis3DaoImpl implements
		ICashCollectionRptEntityDao {

	private static final String NAMESPACE = "foss.stl.CashCollectionRptEntityDao.";// 命名查询现金收入缴款报表空间
	
	 /**
     * 每天定时生成所有网点的现金收入报表及明细信息
     * MODIFY DATE 2013-09-02 092036-FOSS-BOCHENLONG
	 * CHECKED
     * @author 088933-foss-zhangjiheng
     * @date 2012-12-15 下午5:18:07
     */
	@Override
	public void createAllReportCashRecPayIn(Date beginDate,Date endDate) {
		Map<Object,Object> map=new HashMap<Object, Object>();
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		this.getSqlSession().selectOne(NAMESPACE+"createAllReportCashRecPayIn", map);
		
	}
	
	/**
	 * 实时生成单个网点的现金收入报表及明细信息
	 * MODIFY BY 092036-FOSS-BOCHENLONG 2013-09-03
	 * CHECKED
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-12-15 下午5:18:07
	 */
	@Override
	public void createOneReportCashRecPayIn(Date beginDate, Date endDate,
			String orgCode, 
			Date currentTime) {
		Map<Object,Object> map=new HashMap<Object, Object>();
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		map.put("orgCode", orgCode);
		//map.put("createUserCode", createUserCode);
		//map.put("createUserName", createUserName);
		map.put("currentTime", currentTime);
		this.getSqlSession().selectOne(NAMESPACE+"createOneReportCashRecPayIn", map);
	}
	
	   /**
     * 
     * 每天定时查询网点前一天的现金营业收入
     * MODIFY BY 092036-FOSS-BOCHENLONG 2013-09-03
	 * CHECKED
     * @author 088933-foss-zhangjiheng
     * @date 2012-12-18 下午5:12:11
     * @see com.deppon.foss.module.settlement.pay.api.server.dao.ICashCollectionRptEntityDao#queryUploadCashAllAmount(java.util.Date, java.util.Date, java.lang.String)
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<CashCollectionRptEntity> queryUploadCashAllAmount(
			Date beginDate, Date endDate, List<String> paymentTypeList) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		map.put("paymentTypeList", paymentTypeList);
		return this.getSqlSession().selectList(NAMESPACE + "selectUploadCashAllAmount", map);
	}
}
