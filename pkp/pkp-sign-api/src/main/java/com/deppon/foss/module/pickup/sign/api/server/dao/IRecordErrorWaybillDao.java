package com.deppon.foss.module.pickup.sign.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorImportantWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorImportantWaybillResultDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordErrorWaybillResultDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RecordUnnormalSignWaybillDto;

/***
 * @clasaName:com.deppon.foss.module.pickup.sign.api.server.dao.IRecordErrorWaybillDao
 * @author: foss-yuting
 * @description: foss记录内物短少、异常线上划责差错 上报QMS
 * @date:2014年12月8日 下午08:39:20
 */
public interface IRecordErrorWaybillDao {

	/***
	 * @clasaName:com.deppon.foss.module.pickup.sign.api.server.dao.IRecordErrorWaybillDao.insertEntity
	 * @author: foss-yuting
	 * @description: 保存OA上报需要的数据
	 * @date:2014年12月8日 下午08:38:21
	 */
	public void insertEntity(RecordErrorWaybillDto recordErrorWaybillDto);
	
	/***
	 * @author 306548-foss-honglujun
	 * foss保存重大货物异常自动上报信息 OA
	 */
	public void insertImportantEntity(RecordErrorImportantWaybillDto recordErrorImportantWaybillDto);

	/***
	 * @clasaName:com.deppon.foss.module.pickup.sign.api.server.dao.IRecordErrorWaybillDao.getTotalCount
	 * @author: foss-yuting
	 * @description: 获取符合条件的记录数
	 * @date:2014年12月8日 下午08:38:21
	 */
	public Long getTotalCount();

	/***
	 * @clasaName:com.deppon.foss.module.pickup.sign.api.server.dao.IRecordErrorWaybillDao.findReportOAExceptionList
	 * @author: foss-yuting
	 * @description: 分页获取数据给OA
	 * @date:2014年12月8日 下午08:38:21
	 */
	public List<RecordErrorWaybillResultDto> findReportOAExceptionList();
	
	/***
	 * @clasaName:com.deppon.foss.module.pickup.sign.api.server.dao.IRecordErrorWaybillDao.findImportentReportOAExceptionList
	 * @author 306548-foss-honglujun
	 * @description: 分页获取重大货物异常数据给OA
	 */
	public List<RecordErrorImportantWaybillResultDto> findImportantReportOAExceptionList();

	/***
	 * @clasaName:com.deppon.foss.module.pickup.sign.api.server.dao.IRecordErrorWaybillDao.updateEntity
	 * @author: foss-yuting
	 * @description: 更新数据状态
	 * @date:2014年12月15日 下午08:43:21
	 */
	public void updateEntity(String id);
	
	/***
	 * @clasaName:com.deppon.foss.module.pickup.sign.api.server.dao.IRecordErrorWaybillDao.findImportentReportOAExceptionList
	 * @author 306548-foss-honglujun
	 * @description: 更新数据状态
	 */
	public void updateImportantEntity(String id);

	/***
	 * @clasaName:com.deppon.foss.module.pickup.sign.api.server.dao.IRecordErrorWaybillDao.getRecordErrorWaybill
	 * @author: foss-yuting
	 * @description: 获取数据
	 * @date:2014年12月19日 下午08:43:21
	 */
	public RecordErrorWaybillResultDto getRecordErrorWaybill(String id);

	/***
	 * @clasaName:com.deppon.foss.module.pickup.sign.api.server.dao.IRecordErrorWaybillDao.getDeptNameByCode
	 * @author: foss-yuting
	 * @description: 通过部门编码获取部门名称
	 * @date:2014年12月19日 下午08:43:21
	 */
	public List<String> getDeptNameByCode(List<String> deptList);
	
	/**
	 * @author: foss-231434-bieyexiong
	 * @description: 保存QMS上报异常线上划责需要的数据
	 * @date:2016年02月18日 下午17:32:21
	 * @param unnormalDto
	 */
	public void insertUnnormalEntity(RecordUnnormalSignWaybillDto unnormalDto);
	
	/**
	 * @author: foss-231434-bieyexiong
	 * @description: 查询QMS上报异常线上划责需要的数据
	 * @date:2016年02月19日 上午10:45:09
	 */
	public List<RecordUnnormalSignWaybillDto> findRecordUnnormalSignEntitys();
	
	/**
	 * @author: foss-231434-bieyexiong
	 * @description: 更改QMS上报异常线上划责数据的状态、备注
	 * @date:2016年02月19日 下午15:15:34
	 * @param unnormalDto
	 */
	public void updateUnnormalEntity(RecordUnnormalSignWaybillDto unnormalDto);

}
