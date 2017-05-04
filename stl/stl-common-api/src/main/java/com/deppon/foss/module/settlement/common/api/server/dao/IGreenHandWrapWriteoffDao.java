package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.GreenHandWrapWriteoffEntity;

public interface IGreenHandWrapWriteoffDao {
		
	/**
	 * 保存从Dop传过来的数据至暂存表
	 * @author 310970
	 * @date 2016-2-17
	 * @param entity 应收单
	 * @return
	 */
    int addDopInfo(GreenHandWrapWriteoffEntity greenHandWrapWriteoffEntity);
    
    /**
     * @218392 zhangyongxue
     * @date 2016-02-18 14:15:30
     */
    List<BillReceivableEntity> queryByWaybillNOs(String waybillNo);
    
    /**
     * @author 218392 张永雪
     * @date 2016-02-23 12:25:30
     * 根据单号查询未核销暂存表的信息
     */
    List<GreenHandWrapWriteoffEntity> queryGreenHandWrapByWaybillNo(String waybillNo);
    
    /**
     * @218392 zhangyongxue
     * @date 2016/02-21 14:28:20
     * 更新暂存表是否核销
     */
    int updateGreenHandWrapInfo(GreenHandWrapWriteoffEntity greenHandWrapWriteoffEntity);
    
    /**
     * @author 218392 张永雪
     * 将暂存表的数据转储到 转储表
     * @param greenHandWrapWriteoffEntity
     * @return
     */
    int addDumpGreenHandFromWrap(GreenHandWrapWriteoffEntity greenHandWrapWriteoffEntity);
    
    /**
     * @author 218392 张永雪
     * 讲转移走的数据从暂存表中删除
     */
    int deleteWrapGreenHandInfo(GreenHandWrapWriteoffEntity greenHandWrapWriteoffEntity);
    
   /**
    * 更具运单号查询无效的应收单
    * @Title: queryReceiveByWaybillNOs 
    * @author： 269052 |zhouyuan008@deppon.com
    * @date 2016-7-8 下午10:05:32
    */
    List<BillReceivableEntity> queryReceiveByWaybillNos(String waybillNo);
    
    
    /**
     * 更具运单号查询无效的应收单
     * @Title: queryReceiveByWaybillNOs 
     * @author： 269052 |zhouyuan008@deppon.com
     * @date 2016-7-8 下午10:05:32
     */
     List<BillReceivableEntity> queryDrReceiveByWaybillNos(String waybillNo);
    
}
