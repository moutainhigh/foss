package com.deppon.foss.module.pickup.order.api.server.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.order.api.shared.domain.BigCustomeEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.CombinateBillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabelPrintDto;





public interface ICombinateBillDao {
	/**
	 * 
	 * <p>新增合并数据</p> 
	 * @author 183272
	 * @date 2015年9月17日 下午1:41:53
	 * @param combinateBill
	 * @return
	 * @see
	 */
	public  int addCombinateBillEntity(CombinateBillEntity combinateBill) ;
	
	/**
	 * 
	 * <p>查询最大运单号</p> 
	 * @author 183272
	 * @date 2015年9月17日 下午2:56:48
	 * @return
	 * @see
	 */
	public String queryMaxWaybillNo();

	/**
	 * 查询待合票数据
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 */
	public ArrayList<BigCustomeEntity> queryCombinateBigCustomeByTime(Date billTimeFrom,
			Date billTimeTo,int option);
	/**
	 * 查询下一个运单号 通过序列BIGCUSTOME_WAYBILLNO_SEQ
	 * @return
	 * BIGCUSTOME_WAYBILLNO_SEQ：
	 * 
	 *		 CREATE SEQUENCE PKP.BIGCUSTOME_WAYBILLNO_SEQ
			INCREMENT BY 1 -- 每次加几个
			START WITH 1000000000 -- 从1开始计数
			MAXVALUE 1000500000  -- 不设置最大值
			NOCYCLE -- 一直累加，不循环
			CACHE 10 --设置缓存cache个序列，如果系统down掉了或者其它情况将会导致序列不连续，也可以设置为---------NOCACHE
			;
	 */
	public String queryNextBigCustomWaybillNo() ;
	/**
	 * 将运单active 设置为N
	 * @return
	 */
	public String disActiveCombinatebill(String waybillno);
	
	
	//查明细表
	public List<CombinateBillEntity> downloadCombillTotal();

	public void insertGxgLabel(LabelPrintDto lpd);
	
}
