package com.deppon.foss.module.pickup.sign.server.dao.impl;

import java.util.List;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto;
import com.deppon.foss.module.pickup.sign.api.server.dao.IDopSignDao;
import com.deppon.foss.module.pickup.sign.api.shared.domain.DopSignEntity;
import com.deppon.foss.util.UUIDUtils;
/**
 * DOP传送数据处理Dao
 * @author foss-zhuliangzhi
 * @date 2015-10-18 下午6:16:31
 * @since
 * @version
 */
public class DopSignDao extends iBatis3DaoImpl implements IDopSignDao {
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.sign.api.shared.domain.DopSignEntity.";
	/**
	 * 根据运单号查询DOP传来的信息
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DopSignEntity> queryDopListByWaybillNo(SignDto dto){
		return this.getSqlSession().selectList(NAMESPACE+"queryDopListByWaybillNo",dto);
	}

	@Override
	public int insertDopCacheWaybillInfo(DopSignEntity dto) {
		dto.setId(UUIDUtils.getUUID());
		int insert = this.getSqlSession().insert(NAMESPACE+"insertDopCacheWaybillInfo",dto);
		System.out.println("insert is :　"+insert);
		return insert;
	}
	/**
	 * 将DOP签收信息经过FOSS系统签收后保存到本地签收日志表T_SRV_DOPSIGN表中
	 * @param dto
	 * @author 269871 foss-zhuliangzhi
	 */
	@Override
	public int insertDopSign(DopSignEntity dopSingEntity) {
		// TODO Auto-generated method stub
		dopSingEntity.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(NAMESPACE+"insertDopSign",dopSingEntity);
	}
	/**
	 * dop家装订单签收后，
	 * 在暂存表（t_srv_dopcache）中根据运单号删除对应记录保存到签收日志记录表（t_srv_dopsign）
	 */
	@Override
	public int deleteDopCache(String waybillNo) {
		return this.getSqlSession().delete(NAMESPACE+"deleteByWaybillNo",waybillNo);
	}

	@Override
	public long queryDopSignRfc(String waybillNo) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "queryDopSignRfcByWaybill", waybillNo);
	}

	/**
	 * 记录回传给DOP的签收信息
	 */
	@Override
	  public int updateByWaybillNo(DopSignEntity dopSignEntity){
			return this.getSqlSession().update(NAMESPACE+"updateByWaybillNo", dopSignEntity);
		}
	/**
	 * 更新反签收标记
	 */
	@Override
	  public int updateRfc(String waybillNo){
			return this.getSqlSession().update(NAMESPACE+"updateRfc", waybillNo);
		}

	@Override
	public List<DopSignEntity> queryDopSignListByWaybillNo(SignDto dto) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(NAMESPACE+"queryDopSignListByWaybillNo",dto);
	}
	}
