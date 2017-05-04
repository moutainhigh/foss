package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverHandoverbillDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverHandoverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AutoPreDeliverHandoverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverHandoverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverHandoverbillOtherDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverHandoverbillQueryDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PreDeliverHandoverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PreDeliverHandoverbillQueryDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * 派送交单DAO
 * @author 159231 meiying
 * 2015-4-20  上午11:18:47
 */
public class DeliverHandoverbillDao extends iBatis3DaoImpl implements IDeliverHandoverbillDao{
	/**
	 * 运单综合查询命名空间
	 */
	private static final String DELIVER_HANDOVERBILL_NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.server.dao.DeliverHandoverbillEntityMapper.";


	@Override
	public int insertSelective(DeliverHandoverbillEntity record) {
        record.setId(UUIDUtils.getUUID());
        int result = this.getSqlSession().insert(
                DELIVER_HANDOVERBILL_NAMESPACE + "insertSelective", record);

        return result;
	}
	/**
	 * 查询待交单运单总数
	 * @author 159231 meiying
	 * 2015-4-21  上午8:47:36
	 * @param preDeliverHandoverbillQueryDto
	 * @return
	 */

	@Override
	public Long queryPreDeliverHandoverCount(
			PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto) {
		if(preDeliverHandoverbillQueryDto==null){
			return null;
		}
		return (Long) this.getSqlSession().selectOne(DELIVER_HANDOVERBILL_NAMESPACE + "selectPreDeliverHandoverCount", preDeliverHandoverbillQueryDto);
	}
	/**
	 * 查询待交单运单List
	 * @author 159231 meiying
	 * 2015-4-21  上午9:08:38
	 * @param preDeliverHandoverbillQueryDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PreDeliverHandoverbillDto> queryPreDeliverHandoverList(
			PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(DELIVER_HANDOVERBILL_NAMESPACE + "selectPreDeliverHandoverByParams", preDeliverHandoverbillQueryDto, rowBounds);
	}
	/**
	 * 查询已交单运单总数
	 * @author 159231 meiying
	 * 2015-4-21  上午9:08:47
	 * @param deliverHandoverbillQueryDto
	 * @return
	 */
	@Override
	public Long queryDeliverHandoverCount(
			DeliverHandoverbillQueryDto deliverHandoverbillQueryDto) {
        if(deliverHandoverbillQueryDto==null){
            return null;
        }
        return (Long) this.getSqlSession().selectOne(DELIVER_HANDOVERBILL_NAMESPACE + "selectDeliverHandoverCount", deliverHandoverbillQueryDto);

    }
	/**
	 * 查询已交单运单List
	 * @author 159231 meiying
	 * 2015-4-21  上午9:08:50
	 * @param deliverHandoverbillQueryDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliverHandoverbillDto> queryDeliverHandoverList(
			DeliverHandoverbillQueryDto deliverHandoverbillQueryDto, int start, int limit) {
        RowBounds rowBounds = new RowBounds(start, limit);
        return this.getSqlSession().selectList(DELIVER_HANDOVERBILL_NAMESPACE + "selectDeliverHandoverByParams", deliverHandoverbillQueryDto, rowBounds);

    }


    @Override
    public int updatePartByParams(DeliverHandoverbillEntity en)  {
        en.setModifyTime(new Date());
        int result = this.getSqlSession().update(
                DELIVER_HANDOVERBILL_NAMESPACE + "updatePartByParams", en);
        return result;
    }

    @Override
    public int updateBywaybillNOs(PreDeliverHandoverbillQueryDto en) {
        int result = this.getSqlSession().update(
                DELIVER_HANDOVERBILL_NAMESPACE + "updateBywaybillNOs", en);
        return result;
    }
    @Override
    public int updateByWaybillNoSelective(DeliverHandoverbillEntity en) {
        int result = this.getSqlSession().update(
                DELIVER_HANDOVERBILL_NAMESPACE + "updateByWaybillNoSelective", en);
        return result;
    }
    /**
     * 根据运单号查询交单信息
     * @author 159231 meiying
     * 2015-5-19  上午10:57:45
     * @param en
     * @return
     */
    @Override
    public DeliverHandoverbillEntity queryByWaybillNo(DeliverHandoverbillEntity en){
        @SuppressWarnings("unchecked")
		List<DeliverHandoverbillEntity> list =this.getSqlSession().selectList(DELIVER_HANDOVERBILL_NAMESPACE +
                "queryDeliverHandoverbillByWaybillNo",en);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }
    /**
     * 查库存状态的待交单信息总数
     * @author 159231 meiying
     * 2015-5-19  上午10:59:44
     * @param preDeliverHandoverbillQueryDto
     * @return
     */
	@Override
	public Long queryPreDeliverHandoverByStockCount(
			PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto) {
		if(preDeliverHandoverbillQueryDto==null){
			return null;
		}
		return (Long) this.getSqlSession().selectOne(DELIVER_HANDOVERBILL_NAMESPACE + "selectPreDeliverHandoverStockCount", preDeliverHandoverbillQueryDto);
	}
	/**
	 * 查库存状态的待交单信息List
	 * @author 159231 meiying
	 * 2015-5-19  上午10:59:41
	 * @param preDeliverHandoverbillQueryDto
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PreDeliverHandoverbillDto> queryPreDeliverHandoverByStockList(
			PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto,
			int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(DELIVER_HANDOVERBILL_NAMESPACE + "selectPreDeliverHandoverStock", preDeliverHandoverbillQueryDto, rowBounds);
	}
	/**
	 * 查预计到达的待交单信息总数
	 * @author 159231 meiying
	 * 2015-5-19  上午10:59:37
	 * @param preDeliverHandoverbillQueryDto
	 * @return
	 */
	@Override
	public Long queryPreDeliverHandoverPreArriveCount(
			PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto) {
		if(preDeliverHandoverbillQueryDto==null){
			return null;
		}
		return (Long) this.getSqlSession().selectOne(DELIVER_HANDOVERBILL_NAMESPACE + "selectPreDeliverHandoverPreArriveCount", preDeliverHandoverbillQueryDto);
	}
	/**
	 * 查询预计到达待交单运单List
	 * @author 159231 meiying
	 * 2015-5-19  上午10:59:34
	 * @param preDeliverHandoverbillQueryDto
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PreDeliverHandoverbillDto> queryPreDeliverHandoverPreArriveList(
			PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto,
			int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(DELIVER_HANDOVERBILL_NAMESPACE + "selectPreDeliverHandoverPreArrive", preDeliverHandoverbillQueryDto, rowBounds);
	}
	/**
	 * 查已到达的待交单信息总数
	 * @author 159231 meiying
	 * 2015-5-19  上午10:59:31
	 * @param preDeliverHandoverbillQueryDto
	 * @return
	 */
	@Override
	public Long queryPreDeliverHandoverArriveCount(
			PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto) {
		if(preDeliverHandoverbillQueryDto==null){
			return null;
		}
		return (Long) this.getSqlSession().selectOne(DELIVER_HANDOVERBILL_NAMESPACE + "selectPreDeliverHandoverArriveCount", preDeliverHandoverbillQueryDto);
	}
	/**
	 * 查询已到达待交单运单List
	 * @author 159231 meiying
	 * 2015-5-19  上午10:59:28
	 * @param preDeliverHandoverbillQueryDto
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PreDeliverHandoverbillDto> queryPreDeliverHandoverArrive(
			PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto,
			int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(DELIVER_HANDOVERBILL_NAMESPACE + "selectPreDeliverHandoverArrive", preDeliverHandoverbillQueryDto, rowBounds);
	}
	/**
	 * 根据交单号查待交单信息总数
	 * @author 159231 meiying
	 * 2015-5-19  上午10:59:25
	 * @param preDeliverHandoverbillQueryDto
	 * @return
	 */
	@Override
	public Long queryPreDeliverHandoverByhandoverNoCountCount(
			PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto) {
		if(preDeliverHandoverbillQueryDto==null){
			return null;
		}
		return (Long) this.getSqlSession().selectOne(DELIVER_HANDOVERBILL_NAMESPACE + "selectPreDeliverHandoverByhandoverNoCount", preDeliverHandoverbillQueryDto);
	}
	/**
	 * 根据交单号查询待交单运单List
	 * @author 159231 meiying
	 * 2015-5-19  上午10:59:22
	 * @param preDeliverHandoverbillQueryDto
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PreDeliverHandoverbillDto> queryPreDeliverHandoverByhandoverNoList(
			PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto,
			int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(DELIVER_HANDOVERBILL_NAMESPACE + "selectPreDeliverHandoverByhandoverNo", preDeliverHandoverbillQueryDto, rowBounds);
	}
	/**
	 * 根据车次号查待交单信息总数
	 * @author 159231 meiying
	 * 2015-5-19  上午10:59:18
	 * @param preDeliverHandoverbillQueryDto
	 * @return
	 */
	@Override
	public Long queryPreDeliverHandoverByVehicleNoCount(
			PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto) {
		if(preDeliverHandoverbillQueryDto==null){
			return null;
		}
		return (Long) this.getSqlSession().selectOne(DELIVER_HANDOVERBILL_NAMESPACE + "selectPreDeliverHandoverByVehicleNoCount", preDeliverHandoverbillQueryDto);
	}
	/**
	 * 根据车次号查询待交单运单List
	 * @author 159231 meiying
	 * 2015-5-19  上午10:59:14
	 * @param preDeliverHandoverbillQueryDto
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PreDeliverHandoverbillDto> queryPreDeliverHandoverByVehicleNoList(
			PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto,
			int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(DELIVER_HANDOVERBILL_NAMESPACE + "selectPreDeliverHandoverByVehicleNo", preDeliverHandoverbillQueryDto, rowBounds);
	}
	/**
	 * 查询满足自动交单信息
	 * @author 159231 meiying
	 * 2015-5-28  下午1:53:31
	 * @param pre
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AutoPreDeliverHandoverbillDto> queryByAutoDeliverHandover(
			AutoPreDeliverHandoverbillDto pre) {
		return this.getSqlSession().selectList(DELIVER_HANDOVERBILL_NAMESPACE + "selectByAutoDeliverHandover", pre);
	}
	/**
	 * 根据传入的运单号集合＋active = 'Y'查询满足条件的运单
	 * @author 159231 meiying
	 * 2015-6-1  下午5:12:54
	 * @param pre
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<DeliverHandoverbillEntity> queryDeliverHandoverbillByWaybillNos(
			DeliverHandoverbillOtherDto pre) {
		return this.getSqlSession().selectList(DELIVER_HANDOVERBILL_NAMESPACE + "queryDeliverHandoverbillByWaybillNos", pre);
	}
	/**
	 * 根据运单号集合＋active = 'Y'修改满足条件的信息
	 * @author 159231 meiying
	 * 2015-6-1  下午5:15:18
	 * @param pre
	 * @return
	 */
	 @Override
    public int updatePartByWaybillNos(DeliverHandoverbillOtherDto pre) {
        int result = this.getSqlSession().update(
                DELIVER_HANDOVERBILL_NAMESPACE + "updatePartByWaybillNos", pre);
        return result;
    }
	/**
	 * 根据运单号查询交单列表
	 * @author foss sunyanfei
	 * 2015-8-12  下午 15:13:45
	 * @param en
	 * @return
	 */
	@Override
	public List<DeliverHandoverbillEntity> queryListByWaybillNo(DeliverHandoverbillEntity entity){
		@SuppressWarnings("unchecked")
		List<DeliverHandoverbillEntity> list =this.getSqlSession().selectList(DELIVER_HANDOVERBILL_NAMESPACE +
                "queryDeliverHandoverbillListByWaybillNo",entity);
        if(list != null && list.size() > 0){
            return list;
        }
        return null;
    }
}