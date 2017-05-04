/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.cxf.common.util.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillExpressDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillQueryCityProvinceDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class WaybillExpressDao  extends iBatis3DaoImpl implements IWaybillExpressDao {
	private static final String NAMESPACE = "foss.pkp.WaybillExpressEntityMapper.";

	/**
	 * 提交时新增运单快递
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 上午11:35:42
	 */
	public String addWaybillExpressEntity(WaybillExpressEntity waybillExpress) {
		// 设置UUID
		waybillExpress.setId(UUIDUtils.getUUID());
		//修改时间
//		if(waybillExpress.getBillTime() == null){
//			//开单时间
//			waybillExpress.setBillTime(new Date());
//			//修改时间
//			waybillExpress.setModifyDate(waybillExpress.getBillTime());
//		}else{
			//修改时间
//			waybillExpress.setModifyDate(waybillExpress.getBillTime());
//		    waybillExpress.setModifyDate(new Date());
//		}
		this.getSqlSession().insert(NAMESPACE + "insert", waybillExpress);
		return waybillExpress.getId();
	}

	/**
	 *通过运单编号修改运单
	 * 
	 * @param waybill
	 */
	public int updateWaybillExpressByWaybillNo(
			WaybillExpressEntity waybillExpress) {
		//修改时间
		waybillExpress.setModifyDate(new Date());
		return this.getSqlSession().update(
				NAMESPACE + "updateByWaybillNoSelective", waybillExpress);
	}

	/**
	 * 修改运单
	 * @param waybill
	 */
	public int updateWaybillExpressById(WaybillExpressEntity waybillExpress) {
		//修改时间
		waybillExpress.setModifyDate(new Date());
		return this.getSqlSession().update(
				NAMESPACE + "updateByPrimaryKeySelective", waybillExpress);
	}

	/**
	 * 通过运单编号查询运单快递
	 * 
	 * @param waybill
	 */
	@SuppressWarnings("unchecked")
	public WaybillExpressEntity queryWaybillExpressByNo(String waybillNo) {
		List<WaybillExpressEntity> list =  this.getSqlSession().selectList(
				NAMESPACE + "queryWaybillExpressByNo", waybillNo);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 通过运单Id查询运单快递
	 * 
	 * @param waybill
	 */
	@SuppressWarnings("unchecked")
	public WaybillExpressEntity queryWaybillExpressByWaybillId(String waybillId) {
		List<WaybillExpressEntity> list =  this.getSqlSession().selectList(
				NAMESPACE + "queryWaybillExpressByWaybillId", waybillId);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 通过id询运单快递
	 * 
	 * @param waybill
	 */
	@SuppressWarnings("unchecked")
	public WaybillExpressEntity queryWaybillExpressById(String id) {
		List<WaybillExpressEntity> list =  this.getSqlSession().selectList(
				NAMESPACE + "queryWaybillExpressById", id);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 查询城市
	 * @param orgCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public WaybillQueryCityProvinceDto queryCityProvince(String orgCode){
		List<WaybillQueryCityProvinceDto> list =  this.getSqlSession().selectList(
				NAMESPACE + "queryCityProvince", orgCode);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 通过原单编号、开单类型查询运单快递
	 * @param waybillNo
	 * @param returnType
	 * @return
	 */
	@Override
	public List<WaybillExpressEntity> queryWaybillByOriginalWaybillNo(
			String waybillNo) {
		if(StringUtils.isEmpty(waybillNo)){
			return null;
		}
		List<WaybillExpressEntity> list =  this.getSqlSession().selectList(
				NAMESPACE + "queryWaybillByOriginalWaybillNo", waybillNo);
		return list;
	}
	/**
	 * 通过原单号查询快递表中的信息
	 * 232608
	 * @param waybillNo
	 * @return
	 */
	public WaybillExpressEntity queryExpressWaybillByOriginalWaybillNo(String waybillNo){
 
		return (WaybillExpressEntity)this.getSqlSession().selectOne(NAMESPACE + "queryExpressWaybillByOriginalWaybillNo", waybillNo);
	}
	/**
	 * 返货开单
	 */
	@Override
	public void addWaybillExpressEntityReturn(
			WaybillExpressEntity waybillExpress) {
		// 设置UUID
		waybillExpress.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert(NAMESPACE + "addWaybillExpressEntityReturn", waybillExpress);
		 
		
	}

	@Override
	public List<WaybillExpressEntity> queryWaybillByWaybillNo(String waybillNo) {
		List<WaybillExpressEntity> list =  this.getSqlSession().selectList(
				NAMESPACE + "queryWaybillByWaybillNo", waybillNo);
		return list;
	}

	@Override
	//生成返货单
	public void addWaybillExpressEntityReturn(List<WaybillExpressEntity> entitys) {
		this.getSqlSession().insert(NAMESPACE + "addWaybillExpressEntitysReturn", entitys);
	}

	
	/**
	 * 通过返单号查出返货原单号
	 * @param waybillNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillExpressEntity> queryWaybillReturnByWaybillNo(
			WaybillExpressEntity waybillExpress) {
		if(waybillExpress.getWaybillNo() != null){
			return this.getSqlSession().selectList(
					NAMESPACE + "queryWaybillReturnByWaybillNo", waybillExpress);
		}
		return null;
	}

}
