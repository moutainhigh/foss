package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CodAuditEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CodAuditDto;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Date;
import java.util.List;

/**
 * Created by 073615 on 2015/11/30.
 */
public interface ICodAuditService extends IService {
    /**
     *代收货款审核按照单号
     */
    int codAuditByWaybillNo(CodAuditDto dto);


    /**
     *根据条件分页查询
     */
    List<CodAuditEntity> queryCodAuditByPage(CodAuditDto dto,int start,int limit);


    /**
     * 根据条件导出
     */

    List<CodAuditEntity> queryCodAuditByCondition(CodAuditDto dto);

    /**
     * 根据条件查询汇总
     * @param dto
     * @return
     */
    CodAuditDto queryTotalByConditon(CodAuditDto dto);

    /**
     * 批量更新代收货款审核状态
     * @param dto
     * @return
     */
    int updateCodAuditStatusBath(CodAuditDto dto);

    /**
     * 根据条件导出
     */
    HSSFWorkbook codAuditExportEXCEL(CodAuditDto dto);

    /**
     *添加带审核代收货款
     */
    int addCodAudit(CodAuditEntity record);
    
    /**
     * @author 218392 zhangyongxue
     * @date 2016-07-14 08:37:00
     * 添加长期未退款代收货款短期冻结，长期冻结
     */
    int addShortOrLongLock(CodAuditEntity record);
    
    /**
     * @218392 zhangyongxue
     * @date 2016-07-08 11:51:00
     * 长期未退款代收货款 插入代收支付审核表中
     */
    int addCodAuditLock(CodAuditEntity record);

    /**
     * 作废待审核代收货款信息
     */
    int cancelCodAudit(String waybillNo);
    
    /**
     * 审核前新增的三个筛选条件需要用到的，需要获取到代收货款类型为（即日退，审核退，三日退）
     * @author 310970
     * @param waybillNo
     * @return CODEntity
     * */
    List<CodAuditEntity> queryCodAuditByWaybillNo(String waybillNo);
    
    /**
     * 审核前新增的三个筛选条件需要用到的，需要获取到代收货款类型为（即日退，审核退，三日退）
     * @author 218392 2016-10-17
     * @param waybillNo
     * @return CODEntity
     * */
    List<CodAuditEntity> queryCodDtoByWaybillNo(String waybillNo);
    
    /**
     * 审核前新增的第三个筛选条件里需要用到运单变更时间
     * @author 310970
     * @param waybillNo
     * @return the Date
     * */
    List<CodAuditEntity> queryCodChangeTime(CodAuditDto codAuditDto);


    CodAuditDto queryCodAudit(CodAuditDto queryDto);
    
    /**
     * @218392 ZYX
     */
    int addNonRunfundCodLock(CodAuditEntity record);
    
}
