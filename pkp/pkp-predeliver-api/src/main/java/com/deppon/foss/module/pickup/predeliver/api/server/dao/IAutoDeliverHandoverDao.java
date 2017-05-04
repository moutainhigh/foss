package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AutoDeliverHandoverEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AutoPreDeliverHandoverbillDto;
/**
 * 待自动交单临时表
 * @author 159231 meiying
 * 2015-5-28  下午3:26:35
 */
public interface IAutoDeliverHandoverDao {
	/**
	 * 根据运单号删除记录
	 * @author 159231 meiying
	 * 2015-5-28  下午3:38:59
	 * @param waybillNo
	 * @return
	 */
    int deleteByWaybillNo(String waybillNo);
    /**
     * 添加一条记录
     * @author 159231 meiying
     * 2015-5-28  下午3:39:12
     * @param record
     * @return
     */
    int insertSelective(AutoDeliverHandoverEntity record);
    /**
     * 根据运单号查询待自动交单临时表
     * @author 159231 meiying
     * 2015-5-28  下午3:39:21
     * @param waybillNo
     * @return
     */
    AutoDeliverHandoverEntity selectByWaybillNo(String waybillNo);
    /**
     * 查询待自动交单临时表集合
     * @author 159231 meiying
     * 2015-5-29  上午8:03:29
     * @return
     */
    List<AutoDeliverHandoverEntity> selectList();
    /**
     * 更新ＪＯＢＩＤ
     * @author 159231 meiying
     * 2015-5-29  上午9:23:34
     * @param record
     * @return
     */
    int updateAutoDeliverJobList(AutoPreDeliverHandoverbillDto record);
}