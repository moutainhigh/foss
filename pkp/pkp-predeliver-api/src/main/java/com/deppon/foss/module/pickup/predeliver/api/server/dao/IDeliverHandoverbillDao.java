package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverHandoverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AutoPreDeliverHandoverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverHandoverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverHandoverbillOtherDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverHandoverbillQueryDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PreDeliverHandoverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PreDeliverHandoverbillQueryDto;


/**
 * 派送交单DAO
 * @author 159231 meiying
 * 2015-4-20  上午11:17:33
 */
public interface IDeliverHandoverbillDao {

    int insertSelective(DeliverHandoverbillEntity record);
    /**
	 * 查询待交单运单总数
	 * @author 159231 meiying
	 * 2015-4-21  上午8:47:36
	 * @param preDeliverHandoverbillQueryDto
	 * @return
	 */
	 Long queryPreDeliverHandoverCount(PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto);
	 /**
	  * 查询待交单运单List
	  * @author 159231 meiying
	  * 2015-4-21  上午8:58:52
	  * @param preDeliverHandoverbillQueryDto
	  * @return
	  */
	 List<PreDeliverHandoverbillDto> queryPreDeliverHandoverList(PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto,int start, int limit);

    /**
	 * 查询已交单运单总数
	 * @author 159231 meiying
	 * 2015-4-21  上午8:47:36
	 * @param
	 * @return
	 */
	 Long queryDeliverHandoverCount(DeliverHandoverbillQueryDto deliverHandoverbillQueryDto);
	 /**
	  * 查询已交单运单List
	  * @author 159231 meiying
	  * 2015-4-21  上午8:58:52
	  * @param
	  * @return
	  */
	 List<DeliverHandoverbillDto> queryDeliverHandoverList(DeliverHandoverbillQueryDto deliverHandoverbillQueryDto, int start, int limit);
	 /**
	  * 修改部分派送交单信息
	  * @author 159231 meiying
	  * 2015-5-19  上午10:26:33
	  * @param en
	  * @return
	  */
     int updatePartByParams(DeliverHandoverbillEntity en);
     /**
      * 
      * @author 159231 meiying
      * 2015-5-19  上午10:26:53
      * @param en
      * @return
      */
     int updateBywaybillNOs(PreDeliverHandoverbillQueryDto en);
     /**
      * 
      * @author 159231 meiying
      * 2015-5-19  上午10:27:18
      * @param en
      * @return
      */
     DeliverHandoverbillEntity queryByWaybillNo(DeliverHandoverbillEntity en);
     /**
      * 
      * @author 159231 meiying
      * 2015-5-19  上午10:27:22
      * @param en
      * @return
      */
     int updateByWaybillNoSelective(DeliverHandoverbillEntity en);

    /**
     * 查库存状态的待交单信息总数
     * @author 159231 meiying
     * 2015-4-21  上午8:47:36
     * @param preDeliverHandoverbillQueryDto
     * @return
     */
    Long queryPreDeliverHandoverByStockCount(PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto);
    /**
     * 查库存状态的待交单信息List
     * @author 159231 meiying
     * 2015-4-21  上午8:58:52
     * @param preDeliverHandoverbillQueryDto
     * @return
     */
    List<PreDeliverHandoverbillDto> queryPreDeliverHandoverByStockList(PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto,int start, int limit);
    /**
     * 查预计到达的待交单信息总数
     * @author 159231 meiying
     * 2015-4-21  上午8:47:36
     * @param preDeliverHandoverbillQueryDto
     * @return
     */
    Long queryPreDeliverHandoverPreArriveCount(PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto);
    /**
     * 查询预计到达待交单运单List
     * @author 159231 meiying
     * 2015-4-21  上午8:58:52
     * @param preDeliverHandoverbillQueryDto
     * @return
     */
    List<PreDeliverHandoverbillDto> queryPreDeliverHandoverPreArriveList(PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto,int start, int limit);
    /**
     * 查已到达的待交单信息总数
     * @author 159231 meiying
     * 2015-4-21  上午8:47:36
     * @param preDeliverHandoverbillQueryDto
     * @return
     */
    Long queryPreDeliverHandoverArriveCount(PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto);
    /**
     * 查询已到达待交单运单List
     * @author 159231 meiying
     * 2015-4-21  上午8:58:52
     * @param preDeliverHandoverbillQueryDto
     * @return
     */
    List<PreDeliverHandoverbillDto> queryPreDeliverHandoverArrive(PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto,int start, int limit);
    /**
     * 根据交单号查待交单信息总数
     * @author 159231 meiying
     * 2015-4-21  上午8:47:36
     * @param preDeliverHandoverbillQueryDto
     * @return
     */
    Long queryPreDeliverHandoverByhandoverNoCountCount(PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto);
    /**
     * 根据交单号查询待交单运单List
     * @author 159231 meiying
     * 2015-4-21  上午8:58:52
     * @param preDeliverHandoverbillQueryDto
     * @return
     */
    List<PreDeliverHandoverbillDto> queryPreDeliverHandoverByhandoverNoList(PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto,int start, int limit);
    /**
     * 根据车次号查待交单信息总数
     * @author 159231 meiying
     * 2015-4-21  上午8:47:36
     * @param preDeliverHandoverbillQueryDto
     * @return
     */
    Long queryPreDeliverHandoverByVehicleNoCount(PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto);
    /**
     * 根据车次号查询待交单运单List
     * @author 159231 meiying
     * 2015-4-21  上午8:58:52
     * @param preDeliverHandoverbillQueryDto
     * @return
     */
    List<PreDeliverHandoverbillDto> queryPreDeliverHandoverByVehicleNoList(PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto,int start, int limit);
    /**
     * 查询自动交单信息
     * @author 159231 meiying
     * 2015-5-28  下午1:47:45
     * @param pre
     * @return
     */
    List<AutoPreDeliverHandoverbillDto> queryByAutoDeliverHandover(	AutoPreDeliverHandoverbillDto pre);
    /**
     * 根据传入的运单号集合＋active = 'Y'查询满足条件的运单
     * @author 159231 meiying
     * 2015-6-1  下午5:13:40
     * @param pre
     * @return
     */
    List<DeliverHandoverbillEntity> queryDeliverHandoverbillByWaybillNos(
			DeliverHandoverbillOtherDto pre);
    /**
     * 根据运单号集合＋active = 'Y'修改满足条件的信息
     * @author 159231 meiying
     * 2015-6-1  下午5:14:51
     * @param pre
     * @return
     */
    int updatePartByWaybillNos(DeliverHandoverbillOtherDto pre);
    /**
     * 根据运单号查询交单列表
     * @author foss-sunyanfei
     * 2015-8-12 下午 15:15:29
     * @param entity
     * @return
     */
    List<DeliverHandoverbillEntity> queryListByWaybillNo(DeliverHandoverbillEntity entity);
}