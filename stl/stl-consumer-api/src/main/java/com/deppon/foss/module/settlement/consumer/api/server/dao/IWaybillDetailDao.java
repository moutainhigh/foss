package com.deppon.foss.module.settlement.consumer.api.server.dao;



import com.deppon.foss.module.settlement.consumer.api.shared.domain.WaybillDetailEntity;

import java.util.List;

/**
 * Created by 322906 on 2016/6/30.
 */
public interface IWaybillDetailDao {

   int addWaybillDetailEntity(WaybillDetailEntity waybillDetailEntity);

   void updateWaybillDetailEntity(WaybillDetailEntity waybillDetailEntity);

   List<WaybillDetailEntity> queryWaybillDetailByMergeWaybillNo(String mergeWaybillNo);

   /**
    * 通过运单号查询合并运单号
    * @param waybillNo
    * @return
    */
   String queryMergeWaybillNoByWaybillNo(String waybillNo);

   /**
    * 根据运单号查询运单详细
    * @param waybillNo
    * @return
    */
   WaybillDetailEntity queryWaybillByWaybillNo(String waybillNo);

   /**
    * 作废合并运单时，将运单信息从表中删除
    */
   void deleteWaybill(String mergeWaybillNo);

   /**
    * 作废运单时，将运单信息从表中删除
    */
   void delete(String waybillNo);

}
