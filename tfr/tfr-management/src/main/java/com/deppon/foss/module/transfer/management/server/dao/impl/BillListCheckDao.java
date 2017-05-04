package com.deppon.foss.module.transfer.management.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.management.api.server.dao.IBillListCheckDao;
import com.deppon.foss.module.transfer.management.api.shared.dto.BillListCheckDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.BillListCheckStaDto;


/**
* @description 对账单DAO
* @version 1.0
* @author 14022-foss-songjie
* @update 2013年11月30日 下午5:13:38
*/
public class BillListCheckDao extends iBatis3DaoImpl implements IBillListCheckDao {
	private static final String NAMESPACE = "foss.management.billListCheck.";

	@Override
	public void batchInsertBillListCheckLog(
			List<BillListCheckDto> billListCheckDtoList) {
		this.getSqlSession().insert(NAMESPACE+"batchInsertBillListCheckLog",billListCheckDtoList);
	}

	@Override
	public void insertBillListCheckLogOne(BillListCheckDto billListCheckDto) {
		this.getSqlSession().insert(NAMESPACE+"insertBillListCheckLogOne",billListCheckDto);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<BillListCheckDto> queryBillListCheckLogGroup(Map map) {
		return this.getSqlSession().selectList(NAMESPACE+"queryBillListCheckLogGroup",map);
	}

	@Override
	public void insertBillListCheckOne(BillListCheckDto billListCheckDto) {
		this.getSqlSession().insert(NAMESPACE+"insertBillListCheckOne",billListCheckDto);
	}
	
	@SuppressWarnings({"rawtypes", "unchecked" })
	@Override
	public BillListCheckDto queryBillListCheck(Map map) {
		List<BillListCheckDto> list = this.getSqlSession().selectList(NAMESPACE+"queryBillListCheckOne",map);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<BillListCheckDto> queryBillListCheck(Map map,int start,int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+"queryBillListCheck",map,rowBounds);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<BillListCheckDto> exportBillListCheck(Map map) {
		return this.getSqlSession().selectList(NAMESPACE+"queryBillListCheck",map);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public long queryBillListCheckCount(Map map) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"queryBillListCheckCount",map);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BillListCheckStaDto queryBillListCheckStatic(Map map) {
		return (BillListCheckStaDto) this.getSqlSession().selectOne(NAMESPACE+"queryBillListCheckStatic",map);
	}

	@Override
	public void updateBillListCheck(BillListCheckDto billListCheckDto) {
		this.getSqlSession().update(NAMESPACE+"updateBillListCheck",billListCheckDto);
	}

	
}
