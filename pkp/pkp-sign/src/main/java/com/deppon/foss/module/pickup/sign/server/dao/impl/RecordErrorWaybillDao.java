package com.deppon.foss.module.pickup.sign.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.sign.api.server.dao.IRecordErrorWaybillDao;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorImportantWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorImportantWaybillResultDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorWaybillResultDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordUnnormalSignWaybillDto;

/***
 * @clasaName:com.deppon.foss.module.pickup.sign.server.dao.impl.RecordErrorWaybillDao
 * @author: foss-yuting
 * @description: foss记录内物短少、异常线上划责差错 上报QMS
 * @date:2014年12月8日 下午08:39:20
 */
public class RecordErrorWaybillDao extends iBatis3DaoImpl implements IRecordErrorWaybillDao {

	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorWaybillDto.";
	/***
	 * @clasaName:com.deppon.foss.module.pickup.sign.server.dao.impl.RecordErrorWaybillDao.insertEntity
	 * @author: foss-yuting
	 * @description: 保存OA上报需要的数据
	 * @date:2014年12月8日 下午08:38:21
	 */
	@Override
	public void insertEntity(RecordErrorWaybillDto recordErrorWaybillDto) {
		this.getSqlSession().insert(NAMESPACE + "insertEntity", recordErrorWaybillDto);
	}
	
	/**
	 * @author 306548-foss-honglujun
	 * foss记录重大货物异常自动上报信息 OA
	 * RecordErroImportantWaybillDto recordErroImportantWaybillDto
	 */
	@Override
	public void insertImportantEntity(RecordErrorImportantWaybillDto recordErrorImportantWaybillDto) {
		this.getSqlSession().insert(NAMESPACE + "insertImportantEntity", recordErrorImportantWaybillDto);
	}
	
	/***
	 * @clasaName:com.deppon.foss.module.pickup.sign.server.dao.impl.RecordErrorWaybillDao.getTotalCount
	 * @author: foss-yuting
	 * @description: 获取符合条件的记录数
	 * @date:2014年12月8日 下午08:38:21
	 */
	@Override
	public Long getTotalCount() {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getTotalCount");
	}
	
	/***
	 * @clasaName:com.deppon.foss.module.pickup.sign.server.dao.impl.RecordErrorWaybillDao.findReportOAExceptionList
	 * @author: foss-yuting
	 * @description: 获取数据给OA那边
	 * @date:2014年12月8日 下午08:38:21
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RecordErrorWaybillResultDto> findReportOAExceptionList() {
		return this.getSqlSession().selectList(NAMESPACE + "queryReportOAExceptionList");
	}
	
	/***
	 * @author 306548-foss-honglujun
	 * @description: 获取重大货物异常数据给OA那边
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RecordErrorImportantWaybillResultDto> findImportantReportOAExceptionList() {
		return this.getSqlSession().selectList(NAMESPACE + "queryImportantReportOAExceptionList");
	}
	
	/***
	 * @clasaName:com.deppon.foss.module.pickup.sign.api.server.dao.impl.RecordErrorWaybillDao.updateEntity
	 * @author: foss-yuting
	 * @description: 更新数据状态
	 * @date:2014年12月15日 下午08:43:21
	 */
	@Override
	public void updateEntity(String id) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("id", id);
		params.put("active", SignConstants.NO);
		params.put("modifyTime", new Date());
		
		this.getSqlSession().update(NAMESPACE+"updateEntity", params);
	}
	
	/***
	 * @author 306548-foss-honglujun
	 * @description: 更新数据状态
	 */
	@Override
	public void updateImportantEntity(String id) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("id", id);
		params.put("active", SignConstants.NO);
		params.put("modifyTime", new Date());
		
		this.getSqlSession().update(NAMESPACE+"updateImportantEntity", params);
	}
	
	/***
	 * @clasaName:com.deppon.foss.module.pickup.sign.api.server.dao.impl.RecordErrorWaybillDao.getRecordErrorWaybill
	 * @author: foss-yuting
	 * @description: 获取数据
	 * @date:2014年12月19日 下午08:43:21
	 */
	@Override
	public RecordErrorWaybillResultDto getRecordErrorWaybill(String id) {
		return (RecordErrorWaybillResultDto) this.getSqlSession().selectOne(NAMESPACE+"getRecordErrorWaybill", id);
	}
	
	/***
	 * @clasaName:com.deppon.foss.module.pickup.sign.api.server.dao.RecordErrorWaybillDao.getDeptNameByCode
	 * @author: foss-yuting
	 * @description: 通过部门编码获取部门名称
	 * @date:2014年12月19日 下午08:43:21
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getDeptNameByCode(List<String> codes) {
		return this.getSqlSession().selectList(NAMESPACE+"getDeptNameByCode",codes);
	}

	/**
	 * @author: foss-231434-bieyexiong
	 * @description: 保存QMS上报异常线上划责需要的数据
	 * @date:2016年02月18日 下午17:32:21
	 * @param unnormalDto
	 */
	@Override
	public void insertUnnormalEntity(RecordUnnormalSignWaybillDto unnormalDto) {
		this.getSqlSession().insert(NAMESPACE + "insertUnnormalEntity",unnormalDto);
	}

	/**
	 * @author: foss-231434-bieyexiong
	 * @description: 查询QMS异常线上划责需要的数据
	 * @date:2016年02月19日 上午10:45:09
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RecordUnnormalSignWaybillDto> findRecordUnnormalSignEntitys() {
		return this.getSqlSession().selectList(NAMESPACE + "findRecordUnnormalSignEntitys");
	}

	/**
	 * @author: foss-231434-bieyexiong
	 * @description: 更改QMS上报异常线上划责数据的状态、备注
	 * @date:2016年02月19日 下午15:15:34
	 * @param unnormalDto
	 */
	@Override
	public void updateUnnormalEntity(RecordUnnormalSignWaybillDto unnormalDto) {
		this.getSqlSession().update(NAMESPACE + "updateUnnormalEntity" , unnormalDto);
	}
	
}
