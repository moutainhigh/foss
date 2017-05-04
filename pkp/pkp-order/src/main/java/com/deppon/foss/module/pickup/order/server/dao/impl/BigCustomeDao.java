package com.deppon.foss.module.pickup.order.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.IBigCustomeDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.BigCustomeEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.CombinateBillEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.BigCustomeDto;
import com.deppon.foss.util.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * caohuibin
 * Created by 268217 on 2015/9/11.
 */
public class BigCustomeDao extends iBatis3DaoImpl implements IBigCustomeDao {
    static final String NAMESPACE = "com.deppon.foss.module.pickup.order.api.shared.domain.BigCustomeEntity." ;
    static final String NAMESPACEs = "foss.pkp.CombinateBillMapper." ;


    @Override
    public BigCustomeDto queryBigCustomeDto(Date curDate, Date preDate){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("curDate", curDate);
        map.put("preDate", preDate);
        List<BigCustomeDto> bigCustomeDtoList = this.getSqlSession().selectList(NAMESPACE + "queryBigCustome",map);
        return CollectionUtils.isEmpty(bigCustomeDtoList) ? null : bigCustomeDtoList.get(0);
    }

    /**
     * 页面显示
     * @param curDate
     * @param preDate
     * @return
     */
    @Override
    public List<BigCustomeDto> queryBigCustomeList(Date curDate, Date preDate,int start, int limit){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("curDate", curDate);
        map.put("preDate", preDate);
        RowBounds rowBounds = new RowBounds(start, limit);
        return getSqlSession().selectList(NAMESPACE + "queryBigCustome",map,rowBounds);
    }

    /**
     * 导出
     * @param billTimeFrom
     * @param billTimeTo
     * @return
     */
    @Override
    public List<CombinateBillEntity> queryBigCustomeSummaryList(Date billTimeFrom, Date billTimeTo){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("billTimeFrom", billTimeFrom);
        map.put("billTimeTo", billTimeTo);
        return getSqlSession().selectList(NAMESPACEs + "queryBigCustome",map);
    }


    /**
     * 页面显示明细记录数-全部
     * 按时间查询记录
     */
    @Override
    public Long queryBigCustomeTotalCount(Date billTimeFrom, Date billTimeTo){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("billTimeFrom", billTimeFrom);
        map.put("billTimeTo", billTimeTo);
        return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryBigCustometotalcount", map);
    }

	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 183272
	 * @date 2015年9月18日 下午3:30:33
	 * @param bigCustome
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IBigCustomeEntityDao#updateWaybillnoSerialno(com.deppon.foss.module.pickup.waybill.api.shared.dto.BigCustomeEntity)
	 */
	@Override
	public int updateWaybillnoSerialno(BigCustomeEntity bigCustome) {
		
		return this.getSqlSession().insert(NAMESPACE + "updateWaybillnoSerialno", bigCustome);
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 183272
	 * @date 2015年9月18日 下午3:30:37
	 * @param waybillno
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IBigCustomeEntityDao#updateWaybillnoSerialnoDisCombine(java.lang.String)
	 */
	@Override
	public int updateWaybillnoSerialnoDisCombine(String waybillno) {
		
		return this.getSqlSession().insert(NAMESPACE + "updateWaybillnoSerialnoDisCombine", waybillno);
	}
}
