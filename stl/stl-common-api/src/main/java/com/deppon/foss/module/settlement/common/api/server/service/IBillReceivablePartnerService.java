package com.deppon.foss.module.settlement.common.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillingQueryRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StlBillDetailDto;

import java.util.List;

/**
 * 应收单服务
 * 
 * @author 099995-foss-wujiangtao
 * 
 */
public interface IBillReceivablePartnerService extends IService {
	
	/**
     * 按照运单号和应付部门编码集合查询应收单信息
     * 
     * @author 099995-foss-wujiangtao
     * @date 2012-12-28 下午3:14:31
     * @param waybillNos
     * @param orgCodeList
     * @param active
     * @return
     */
     List<BillReceivableEntity> queryByWaybillNosAndOrgCodes(List<String> waybillNos, List<String> orgCodeList, String active,CurrentInfo currentInfo);
    
     /**
      * 根据来源单号集合和应收部门编码集合，查询应收单信息
      * 
      * @author 099995-foss-wujiangtao
      * @date 2013-1-22 下午4:35:02
      * @param sourceBillNos
      * @param orgCodes
      * @param sourceBillType
      * @param active
      * @return
      */
     List<BillReceivableEntity> queryBySourceBillNOsAndOrgCodes
     (List<String> sourceBillNos,List<String> orgCodes,String sourceBillType, String active,CurrentInfo currentInfo);
     
     /**
      * 按应收单号和数据权限查询应收单
      * @author 045738-foss-maojianqiang
      * @date 2013-6-12 下午6:36:24
      * @param receivableNos
      * @param active
      * @param currentInfo
      * @return
      */
     List<BillReceivableEntity> queryByReceivableNosAndOrgCodes(List<String> receivableNos, String active,CurrentInfo currentInfo);

 	/**
 	 * 根据传入的一到多个应收单号，获取一到多条应收单信息
 	 * 
 	 * @author 099995-foss-wujiangtao
 	 * @date 2012-10-12 上午11:58:36
 	 * @param receivableNos
 	 *            应收单号集合
 	 * @param active
 	 *            是否有效
 	 * @return
 	 * @see
 	 */
 	List<BillReceivableEntity> queryByReceivableNOs(List<String> receivableNos,	String active);

 	/**
 	 * ptp监控查询应收单各单据的总记录数和总金额数
 	 * 
 	 * @author gpz
 	 * @date 2016年8月5日
 	 * @param requestDto 查询参数
 	 */
	List<StlBillDetailDto> countReceivableBills(BillingQueryRequestDto requestDto);
}
