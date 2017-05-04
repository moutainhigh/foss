package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillFRcQueryByWaybillNosDto;
import com.deppon.foss.module.settlement.closing.api.server.dao.IWaybillCommonDao;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 同步运单/签收信息 dao
 * @author 326181
 *
 */
public class WaybillCommonDao extends iBatis3DaoImpl implements IWaybillCommonDao {

	private static final String NAMESPACE = "foss.stl.WaybillCommonEntityMapper.";
	
	/**
	 * 通过该运单号查询对应有效的运单数据
	 * 
	 * @author ECS-326181
	 * @param waybillNo
	 *            运单号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public WaybillEntity findWaybillByWaybillNo(String waybillNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("waybillNo", waybillNo);
		params.put("active", FossConstants.YES);
		return (WaybillEntity) this.getSqlSession().selectOne(NAMESPACE + "findWaybillByWaybillNo", params);
	}

	/**
	 * 查询此运单号是否存在
	 * @author ECS-326181
	 * 2016-5-9 11点
	 * @param waybillNo 运单号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> findActualFreightByWaybillNo(String waybillNo) {
		return this.getSqlSession().selectList(NAMESPACE + "findActualFreightByWaybillNo", waybillNo);
	}
	
	/**
     * 通过运单号查询id
     * @author ECS-326181
	 * 2016-5-9 11点
     * @param waybillNo 运单号
     * @return
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> findWaybillExpressByWaybillNo(String waybillNo) {
		return this.getSqlSession().selectList(NAMESPACE + "findWaybillExpressByWaybillNo", waybillNo);
	}
	
	/**
     * 通过运单号查询id
     * @author ECS-326181
	 * 2016-5-9 11点
     * @param id
     * @return
     */
	@SuppressWarnings("unchecked")
	@Override
	public WaybillRfcEntity findWaybillRfcByWaybillNo(String waybillNo) {
		return (WaybillRfcEntity) this.getSqlSession().selectOne(NAMESPACE + "findWaybillRfcByWaybillNo", waybillNo);
	}
	
	/**
	 * 查询运单有没有
	 * ECS-327090-2016-05-09
	 * @author 
	 * @date 
	 * @param entity
	 */
	@Override
	public Integer selectWaybillSignResult(WaybillSignResultEntity entity) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"selectById",entity);
	}
		
	/**
	 * 根据运单号更改运单信息
	 * ECS-327090-2016-05-18
	 * @author 
	 * @date 
	 * @param entity
	 */
	@Override
	public int updateWaybill(WaybillSignResultEntity entity) {		
		return this.getSqlSession().update(NAMESPACE+"updateByWaybillNo",entity);
	}
	
	/**
	 * 创建运单
	 * @author ECS - 326181
	 * @date 2016-5-9
	 * @param waybill
	 */
	@Override
	public int insertWaybillEntity(WaybillEntity waybill) {
		return this.getSqlSession().insert(NAMESPACE + "insertWaybillEntity", waybill);
	}
	
	/**
	 * 根据运单号更改运单信息
	 * ECS-327090-2016-05-18
	 * @author 
	 * @date 
	 * @param entity
	 */
	@Override
	public int addWaybillSignResult(WaybillSignResultEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(NAMESPACE+"insertWaybillsignResult", entity);
	}
	
	/**
	 * 修改运单
	 * 
	 * 通过运单号码更新运单记录
	 * 
	 * @author 326181
	 * @date 2016-07-21
	 * @param waybill
	 * @return
	 */
	@Override
	public int updateWaybillEntityByWaybillNo(WaybillEntity waybill,String oldWaybillNo, boolean isAdd) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybill", waybill);
		map.put("oldWaybillNo", oldWaybillNo);
		map.put("oldActive", isAdd ? FossConstants.NO : FossConstants.YES);
		map.put("modifyTime", new Date());
		return this.getSqlSession().update(
				NAMESPACE + "updateWaybillEntityByWaybillNo", map);
	}
	
	/**
     * 根据主键更新ActualFreightEntity
     * @author 326181
     * @date 2016-07-21
     * @param record
     * @return int
     */
    @Override
    public int updateActualFreightEntityById(ActualFreightEntity record) {
    	record.setModifyTime(new Date());
    	return this.getSqlSession().update(NAMESPACE + "updateActualFreightEntityById", record);
    }
    
    /**
	 * 根据运单号进行数据的更新
	 * @author Foss-326181
	 * @date 2016-07-21
	 * @param actualFreightEntity
	 * @return
	 */
    @Override
	public int updateActualFreightEntityByWaybillNo(ActualFreightEntity actualFreightEntity) {
		return this.getSqlSession().update(NAMESPACE+"updateActualFreightEntityByWaybillNo", actualFreightEntity);
	}
    
    /**
     * 新增ActualFreightEntity实体
     * @author 326181
     * @date 2016-07-21
     * @param record
     * @return
     */
    @Override
    public int insertActualFreightEntity(ActualFreightEntity record) {
		record.setId(UUIDUtils.getUUID());
    	if( record.getCreateTime() != null){
    		record.setModifyTime(new Date());
    	}else{
    		record.setCreateTime(new Date());
    		record.setModifyTime(record.getCreateTime());
    	}
    	return this.getSqlSession().insert(NAMESPACE + "insertActualFreightEntity", record);
    }
    
    /**
	 *通过运单编号修改运单
	 * 
	 * @author 326181
	 * @date 2016-07-21
	 * @param waybillExpress
	 * @return
	 */
	public int updateWaybillExpressByWaybillNo(
			WaybillExpressEntity waybillExpress) {
		//修改时间
		waybillExpress.setModifyDate(new Date());
		return this.getSqlSession().update(
				NAMESPACE + "updateWaybillExpressByWaybillNo", waybillExpress);
	}
	
	/**
	 * 提交时新增运单快递
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 上午11:35:42
	 */
	public String insertWaybillExpressEntity(WaybillExpressEntity waybillExpress) {
		// 设置UUID
		waybillExpress.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert(NAMESPACE + "insertWaybillExpressEntity", waybillExpress);
		return waybillExpress.getId();
	}
	
	/**
	 * 
	 * <p>
	 * 更新更改单实体<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-11-28
	 * @param waybillRfcEntity
	 *            void
	 */
	@Override
	public void updateWaybillRfcEntityById(WaybillRfcEntity waybillRfcEntity) {
		this.getSqlSession()
				.update(NAMESPACE + "updateWaybillRfcEntityById",
						waybillRfcEntity);
	}
	
	/**
	 * 
	 * 新增更改单
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-19 下午2:17:50
	 */
	@Override
	public void insertWaybillRfcEntity(WaybillRfcEntity rfcEntity) {
		// 设置UUID
		rfcEntity.setId(UUIDUtils.getUUID());

		getSqlSession().insert(NAMESPACE + "insertWaybillRfcEntity", rfcEntity);
	}
	
	/**
	 * 通过运单号集合拿到待处理的更改单
	 * 
	 * @param WaybillFRcQueryByWaybillNosDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryWaybillRfcByWaybillNos(
			WaybillFRcQueryByWaybillNosDto waybillFRcQueryByWaybillNosDto) {
		return getSqlSession().selectList(NAMESPACE + "queryWaybillRFcByWaybillNos", waybillFRcQueryByWaybillNosDto);
	}
}
