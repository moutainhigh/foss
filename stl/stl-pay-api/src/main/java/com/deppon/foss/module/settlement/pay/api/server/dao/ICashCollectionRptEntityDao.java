package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.domain.CashCollectionRptEntity;

/**
 * 现金收入缴款报表dao
 * @author 095793-foss-LiQin
 * @date 2012-12-24 下午8:05:29
 */
public interface ICashCollectionRptEntityDao {
    
    /**
     * 查询每日前一天现金收入缴款汇总用于报表到上传财务自助
     * @author 095793-foss-LiQin
     * @date 2012-12-13 下午3:54:06
    //List <CashCollectionRptEntity> queryUploadCashinComerpt(BillCashRecPayInDto billCashRecPayInDto);
    
    /**
	 * 每天定时生成所有网点的现金收入报表及明细信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-12-15 下午5:18:07
	 */
	void createAllReportCashRecPayIn(Date beginDate, Date endDate);

	/**
	 * 实时生成单个网点的现金收入报表及明细信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-12-15 下午5:18:07
	 */
	void createOneReportCashRecPayIn(Date beginDate, Date endDate,
			String orgCode ,Date currentTime);
	/**
	 * 每天定时查询网点前一天的现金营业收入
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-12-18 下午4:57:54
	 */
	List<CashCollectionRptEntity> queryUploadCashAllAmount(Date beginDate, Date endDate,
			List<String> paymentType);
	
}