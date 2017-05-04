package com.deppon.foss.module.settlement.common.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.settlement.common.api.server.dao.IVtsCodAuditDao;
import com.deppon.foss.module.settlement.common.api.server.service.IVtsCodAuditService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.CodAuditForVtsSignEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditForVtsSignDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.CollectionUtils;


public class VtsCodAuditService implements  IVtsCodAuditService{
	IVtsCodAuditDao vtsCodAuditDao;
	
	@Override
	public List<CodAuditForVtsSignEntity> queryCodChangeTime(CodAuditForVtsSignDto codAuditDto) {
		List<CodAuditForVtsSignEntity> codAuditForVtsSignEntity= vtsCodAuditDao.queryCodChangeTime(codAuditDto);
		if(CollectionUtils.isNotEmpty(codAuditForVtsSignEntity)){
			return codAuditForVtsSignEntity;
		}else{
			return null;
		}
		
		}
	 /**
     * 新增审核信息
     * @param record
     * @return
     */
    @Override
    public int addCodAudit(CodAuditForVtsSignEntity record) {

        if(record == null){
            throw new SettlementException("传入对象不能为空");
        }else if(StringUtils.isBlank(record.getWaybillNo())){
            throw new SettlementException("运单号不能为空");
        }else if(record.getComfirmTime()== null ||
                record.getSigTime() == null){
            throw new SettlementException("收银确认时间或者签收时间不能为空");
            //新增审核信息  改动了判断条件  多了一个条件复合会计组待审核状态
        }else if(!SettlementDictionaryConstants.SETTLE_CODAUDIT_FUNDAUDIT.equals(record.getLockStatus())&&
        		!SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWAUDIT.equals(record.getLockStatus())){
            throw new SettlementException("插入状态不是资金部待审核状态或复合会计组待审核状态");
        }
        return vtsCodAuditDao.insert(record);
    }
	
	public void setVtscodAuditDao(IVtsCodAuditDao vtscodAuditDao) {
		this.vtsCodAuditDao = vtsCodAuditDao;
	}
}
