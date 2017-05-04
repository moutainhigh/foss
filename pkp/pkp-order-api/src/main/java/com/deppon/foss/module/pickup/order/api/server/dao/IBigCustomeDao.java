package com.deppon.foss.module.pickup.order.api.server.dao;

import com.deppon.foss.module.pickup.order.api.shared.domain.BigCustomeEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.CombinateBillEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.BigCustomeDto;

import java.util.Date;
import java.util.List;

/**
 * caohuibin
 * Created by 268217 on 2015/9/11.
 */
public interface IBigCustomeDao {
    /**
     * 按时间查询记录
     */
    //public List<BigCustomeEntity> queryBigCustomeByDay(Date curDate, Date preDate);

    /**
     * 按时间查询记录
     */
    public BigCustomeDto queryBigCustomeDto(Date curDate, Date preDate);

    /**
     * 页面显示
     * 按时间查询记录
     */
    public List<BigCustomeDto> queryBigCustomeList(Date curDate, Date preDate,int start, int limit);

    /**
     * 页面显示明细总数
     * 按时间查询记录
     */
    Long queryBigCustomeTotalCount(Date billTimeFrom, Date billTimeTo);

    /**
     * 导出
     * 按时间查询记录
     */
    public List<CombinateBillEntity> queryBigCustomeSummaryList(Date billTimeFrom, Date billTimeTo);
    
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 183272
	 * @date 2015年9月18日 下午2:40:18
	 * @param bigCustome
	 * @return
	 * @see
	 */
	public int updateWaybillnoSerialno(BigCustomeEntity bigCustome);
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 183272
	 * @date 2015年9月18日 下午2:40:21
	 * @param waybillno
	 * @return
	 * @see
	 */
	public int updateWaybillnoSerialnoDisCombine(String  waybillno);;

}
