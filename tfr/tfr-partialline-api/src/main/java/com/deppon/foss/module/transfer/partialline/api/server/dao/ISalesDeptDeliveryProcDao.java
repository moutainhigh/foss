package com.deppon.foss.module.transfer.partialline.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.domain.SalesdeptDeliveryEntity;

public interface ISalesDeptDeliveryProcDao {
    int deleteByPrimaryKey(String id);

    int insert(SalesdeptDeliveryEntity record);

    int insertSelective(SalesdeptDeliveryEntity record);

    SalesdeptDeliveryEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SalesdeptDeliveryEntity record);

    int updateByPrimaryKey(SalesdeptDeliveryEntity record);
    
    List<SalesdeptDeliveryEntity> salesDeptDeliveryQuery(SalesdeptDeliveryEntity record);
    /**
     * 根据ID或者运单号删除
     * @return
     */
    public Long deleteByNoOrId(SalesdeptDeliveryEntity deleteEntity);
    /**
     * 批量新增
     * @param batchEntitys
     */
    public void salesdeptDeProcInsert(SalesdeptDeliveryEntity entity);
    
    /**
     * 
    * @Title: deleteByWayBillNo 
    * @Description: 
    * @param @param entity    设定文件 
    * @return void    返回类型 
    * @throws
     */
    public void deleteByWayBillNo(SalesdeptDeliveryEntity entity);
}