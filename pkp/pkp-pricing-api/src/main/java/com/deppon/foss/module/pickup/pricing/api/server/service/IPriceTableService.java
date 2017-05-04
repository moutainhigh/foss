package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PDFPriceTableEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceTableHeadEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceTables;

public interface IPriceTableService extends IService {
	/**
	 * 导出PDF格式价格表
	 * @param startDeptCode
	 * @param productType
	 * @param currentDateTime
	 * @return
	 */
	List<PDFPriceTableEntity> expPDFPriceTableList(String startDeptCode,
			String productType, Date currentDateTime);

	/**
	 * 查询界面查询价格表接口
	 * @param startDeptCode
	 * @param productType
	 * @param effectiveDate
	 * @param start
	 * @param limit
	 * @return
	 */
	PriceTables queryPriceTableListInfo(String startDeptCode, String productType,
			Date effectiveDate, int start, int limit);
	
	/**
	 * <p>[POP]查询界面查询价格表接口（包括分段對應價格表信息2-6段）</p> 
	 * @author foss-148246-sunjianbo
	 * @date 2014-11-7 下午4:07:39
	 * @param startDeptCode
	 * @param productType
	 * @param currentDateTime
	 * @param sectionID
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	Object[] queryPriceTableListInfo(String startDeptCode,
			String productType, Date currentDateTime, int sectionID, int start,
			int limit);
	
	/**
	 * <p>[POP]导出PDF格式价格表</p> 
	 * @author foss-148246-sunjianbo
	 * @date 2014-11-11 下午2:45:01
	 * @param startDeptCode
	 * @param productType
	 * @param currentDateTime
	 * @param sectionID
	 * @return
	 * @see
	 */
	Object[] expPDFPriceTableList(String startDeptCode,
			String productType, Date currentDateTime, int sectionID);
	
	/**
	 * <p>导出PDF格式价格表(合伙人汽运价格表导出)</p> 
	 * @author 370613-foss-LianHe
	 * @date 2016年9月29日 上午10:03:42
	 * @param startDeptCode
	 * @param productType
	 * @param currentDateTime
	 * @param sectionID
	 * @return
	 * @see
	 */
	Object[] expPDFPartnerPriceTableList(String startDeptCode,
			String productType, Date currentDateTime, int sectionID);
    /**
     * 获得动态表头信息
     * @param startDeptCode
     * @param productType
     * @param currentDateTime
     * @return
     */
	PriceTableHeadEntity getTableHead(String startDeptCode, String productType,
			Date currentDateTime);
	/**
	 * <p>TODO(合伙人汽运价格表查询 -- 查询功能)</p> 
	 * @author Foss-352676-YUANHB 
	 * @date 2016-9-22 下午4:32:58
	 * @param startDeptCode
	 * @param productType
	 * @param effectiveDate
	 * @param sectionID
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	Object[] queryPartnerPriceTableListInfo(String startDeptCode,
			String productType, Date effectiveDate, int sectionID, int start,
			int limit);
	
	/**
	 * <p>(合伙人汽运价格表查询 -- PDF导出功能)</p> 
	 * @author 370613-foss-LianHe
	 * @date 2016年9月29日 下午12:25:33
	 * @param startDeptCode
	 * @param productType
	 * @param currentDateTime
	 * @param sectionID
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	Object[] queryPartnerPriceTableListInfo4PDF(String startDeptCode,
			String productType, Date currentDateTime, int sectionID);
	
}
