package com.deppon.foss.module.settlement.consumer.api.server.dao;


import com.deppon.foss.module.settlement.consumer.api.shared.domain.CodAuditEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CodAuditSugEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CodAuditDto;

import java.util.Date;
import java.util.List;

public interface ICodAuditDao {

    /**
     * 插入待审核
     * @param record
     * @return
     */
    int insert(CodAuditEntity record);

    /**
     * 根据主键更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(CodAuditEntity record);

    /**
     * 更新代收货款信息
     * @param record
     * @return
     */
    int updateByWaybillNo(CodAuditEntity record);

    /**
     * 根据条件查询
     * @param dto
     * @return
     */

    List<CodAuditEntity> selectByCondition(CodAuditDto dto);

    /**
     * 分页查询
     * @param dto
     * @param start
     * @param limit
     * @return
     */
    List<CodAuditEntity> selectByPage(CodAuditDto dto,int start,int limit);


    /**
     * 根据条件汇总
     * @param dto
     * @return
     */
    CodAuditDto queryTotalCount(CodAuditDto dto);

    /**
     *根据条件批量更新
     */
    int updateAuditStatusBath(CodAuditDto dto);
    
   /**
     * 根据单号 查询
     * @param  waybillNo
     * @return CODEntity
     * */
    List<CodAuditEntity> queryCodAuditByWaybillNo(String waybillNo);
    
    /**
     * 根据单号 查询
     * @param  waybillNo
     * @return CODEntity
     * */
    List<CodAuditEntity> queryCodDtoByWaybillNo(String waybillNo);
    
    /**
     * 根据单号 查询
     * @param  waybillNo
     * @return CODEntity
     * */
    List<CodAuditEntity> queryCodAuditBySysJob(String waybillNo);
    
    /**
     * 根据单号  查询
     * @param  waybillNo
     * @return the Date 
     * */
	List<CodAuditEntity> queryCodChangeTime(CodAuditDto codAuditDto);
	/**
	 *往审核意见表里插入审核数据 
	 **/
	int insert(CodAuditSugEntity codAuditSugEntity);
	
	List<CodAuditEntity> selectCodFCAmountByWaybillNos(CodAuditDto codAuditDto);

	List<CodAuditEntity> selectCodAuditEntity(CodAuditDto queryDto);

}