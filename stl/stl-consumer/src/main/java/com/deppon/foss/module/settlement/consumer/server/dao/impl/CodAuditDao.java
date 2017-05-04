package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICodAuditDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CodAuditEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CodAuditSugEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CodAuditDto;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by 073615 on 2015/11/30.
 */
public class CodAuditDao extends iBatis3DaoImpl implements ICodAuditDao {
    private static final String NAMESPACE="foss.stl.codAuditMapper.";
    /**
     * 插入对象
     * @param record
     * @return
     */
    @Override
    public int insert(CodAuditEntity record) {
        return getSqlSession().insert(NAMESPACE+"insert",record);
    }

    @Override
    public int updateByPrimaryKeySelective(CodAuditEntity record) {
        return 0;
    }

    /**
     * 通过ID 更新对象
     * @param record
     * @return
     */
    @Override
    public int updateByWaybillNo(CodAuditEntity record) {
        if(StringUtil.isBlank(record.getWaybillNo())){
            throw new SettlementException("根据运单号 更新，运单号 不能为空");
        }

        return getSqlSession().update(NAMESPACE+"updateByRecord",record);
    }

    /**
     * 根据条件查询
     * @param dto
     * @return
     */
    @SuppressWarnings("unchecked")
	@Override
    public List<CodAuditEntity> selectByCondition(CodAuditDto dto) {
        List<CodAuditEntity> list = getSqlSession().selectList(NAMESPACE+"selectCodAuditBycondition",dto);
        return list;
    }

    /**
     * 根据条件分页查询
     * @param dto
     * @param start
     * @param limit
     * @return
     */
    @SuppressWarnings("unchecked")
	@Override
    public List<CodAuditEntity> selectByPage(CodAuditDto dto, int start, int limit) {
        RowBounds rb = new RowBounds(start,limit);
        List<CodAuditEntity> list = getSqlSession().selectList(NAMESPACE+"selectCodAuditBycondition",dto,rb);
        return list;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<CodAuditEntity> selectCodAuditEntity(CodAuditDto queryDto) {
		List<CodAuditEntity> list = getSqlSession().selectList(NAMESPACE+"selectCodAuditBycondition",queryDto);
		return list;
	}

    /**
     * 根据条件汇总
     * @param dto
     * @return
     */
    @Override
    public CodAuditDto queryTotalCount(CodAuditDto dto) {
        return (CodAuditDto)getSqlSession().selectOne(NAMESPACE+"selectTotal",dto);
    }

    @Override
    public int updateAuditStatusBath(CodAuditDto dto) {

        return getSqlSession().update(NAMESPACE+"updateCodStatusBath",dto);
    }
    
    /**
     *新增一个查询方法,查询出运单表的到付金额：对应退款运单的到付总金额。（运费+代收金额）
     * */
    @SuppressWarnings("unchecked")
    @Override
	public  List<CodAuditEntity> selectCodFCAmountByWaybillNos(CodAuditDto codAuditDto){
    	
    	return getSqlSession().selectList(NAMESPACE+"selectCodFCAmount",codAuditDto);
    }
    
    
   @SuppressWarnings("unchecked")
   @Override
    public List<CodAuditEntity> queryCodAuditByWaybillNo(String waybillNo){
    	if(StringUtils.isEmpty(waybillNo)){
    		return null;
    	}
    	return (List<CodAuditEntity> )getSqlSession().selectList(NAMESPACE+"queryCodAuditByWaybillNo",waybillNo);
    }
   
   @SuppressWarnings("unchecked")
   @Override
    public List<CodAuditEntity> queryCodDtoByWaybillNo(String waybillNo){
    	if(StringUtils.isEmpty(waybillNo)){
    		return null;
    	}
    	return (List<CodAuditEntity> )getSqlSession().selectList(NAMESPACE+"queryCodDtoByWaybillNo",waybillNo);
    }
   
   @SuppressWarnings("unchecked")
   @Override
    public List<CodAuditEntity> queryCodAuditBySysJob(String waybillNo){
	   
    	if(waybillNo==null||waybillNo.isEmpty()){
    		return null;
    	}
    	return (List<CodAuditEntity> )getSqlSession().selectList(NAMESPACE+"queryCodAuditBySysJob",waybillNo);
    }
   
    /**
     * 查询代收运单发更改的时间
     * */
    @SuppressWarnings("unchecked")
	@Override
	public List<CodAuditEntity> queryCodChangeTime(CodAuditDto codAuditDto) {
		if(StringUtil.isEmpty(codAuditDto.getWaybillNo())){
    		return null;
    	}  
		return getSqlSession().selectList(NAMESPACE+"queryCodChangeTime",codAuditDto);
	}

	@Override
	public int insert(CodAuditSugEntity codAuditSugEntity) {
		
		return  getSqlSession().insert(NAMESPACE+"insertAuditSug",codAuditSugEntity);
	}

	

	
	
}
