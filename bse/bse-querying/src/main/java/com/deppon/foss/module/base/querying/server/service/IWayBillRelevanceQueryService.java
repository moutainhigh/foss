package com.deppon.foss.module.base.querying.server.service;

import java.io.InputStream;
import java.util.Date;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.TrackRecordEntity;
import com.deppon.foss.module.base.querying.shared.vo.WaybillVo;

/**
 * 
 * 综合查询-运单相关查询
 * @author DP-Foss-YueHongJie
 * @date 2013-4-13 上午10:52:52
 * @version 1.0
 */
public interface IWayBillRelevanceQueryService {
    /**
     * 
     * <p>跟踪记录</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-13 上午11:01:34
     * @param entity
     * @return
     * @see
     */
    WaybillVo queryTrackRecords(TrackRecordEntity entity);
    
    /**
     * 
     * <p>运单更改</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-13 上午10:56:27
     * @param waybillNo
     * @return
     * @see
     */
    WaybillVo queryWayBilllChangeByWaybillNo(String waybillNo);
   
    /**
     * 
     * <p>财务情况</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-13 上午11:09:34
     * @param waybillNo
     * @param billDate 开单日期
     * @return
     * @see
     */
    WaybillVo queryFinanceConditionByWayBillNo(String waybillNo,Date billTime,String leaveDeptCode,String arriveDeptCode);
    
    /**
     * 
     * <p>财务情况</p> 
     * @author 132599-Foss-shenweihua
     * @date 2013-7-16 下午5:09:34
     * @param waybillNo
     * @param 
     * @return
     * @see
     */
    WaybillVo queryWaybillFinanceInfo(String waybillNo);
    
    /**
     * 
     * <p>签收单</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-13 上午11:03:23
     * @param waybillNo
     * @return
     * @see
     */
    WaybillVo querySignedBillByWaybillNo(String waybillNo);
    
    /**
     * 
     * <p>标签打印记录</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-13 上午10:59:47
     * @param waybillNo
     * @return
     * @see
     */
    WaybillVo queryLabelPrintByWaybillNo(String waybillNo);
    
    /**
     * 
     * <p>派送情况</p> 
     * @author DP-Foss-shenweihua
     * @date 2013-7-04 下午1:39:47
     * @param waybillNo
     * @return
     * @see
     */
    WaybillVo queryLabelDeliverySituationByWaybillNo(String waybillNo);
    
    /**
     *  查询标签打印记录（导出用）
     * 
     * @author 132599-FOSS-shenweihua
     * @date 2013-07-06 下午5:08:22
     */
    InputStream queryLablePrinting(String waybillNo);
    
    /**
     *  查询跟踪记录（导出用）
     * 
     * @author 132599-FOSS-shenweihua
     * @date 2013-07-29 下午11:00:22
     */
    InputStream queryExportTrackRecords(String waybillNo);
}
