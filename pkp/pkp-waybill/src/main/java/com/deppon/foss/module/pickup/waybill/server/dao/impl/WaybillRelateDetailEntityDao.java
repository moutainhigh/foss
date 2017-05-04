package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRelateDetailEntityDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.EcomWaybillRelateEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.EwaybillRelateEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRelateDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.vo.GenerateUnActiveEWaybillVo;
import com.deppon.foss.util.define.FossConstants;
/**
 * 子母件基础数据数据持久层
 * @author Foss-105888-Zhangxingwang
 * @date 2015-8-6 17:35:44
 */
public class WaybillRelateDetailEntityDao extends iBatis3DaoImpl implements IWaybillRelateDetailEntityDao {
	
	private Logger LOGGER = LoggerFactory.getLogger(WaybillRelateDetailEntityDao.class);
	
	private static final int NUMBER_2000 = 2000;
	
	public static final String NAMESPACE = "foss.pkp.ewaybill.create.WaybillRelateDetailEntityMapper.";
	public static final String NAMESPACE_JOB = "foss.pkp.ewaybill.create.EwaybillRelateEntityMapper.";
	public static final String NAMESPACE_TEMP = "foss.pkp.ewaybill.create.EcomWaybillRelateEntityMapper.";
	@Override
	public int deleteWaybillRelateDetailByPrimaryKey(String id) {
		return this.getSqlSession().delete(NAMESPACE + "deleteWaybillRelateDetailByPrimaryKey", id);
	}

	@Override
	public int insertWaybillRelateDetail(WaybillRelateDetailEntity record) {
		return this.getSqlSession().insert(NAMESPACE + "insertWaybillRelateDetail", record);
	}

	@Override
	public int insertWaybillRelateDetailSelective(WaybillRelateDetailEntity record) {
		return this.getSqlSession().insert(NAMESPACE + "insertWaybillRelateDetailSelective", record);
	}

	@Override
	public WaybillRelateDetailEntity selectWaybillRelateDetailByPrimaryKey(String id) {
		return (WaybillRelateDetailEntity) this.getSqlSession().selectOne(NAMESPACE + "selectWaybillRelateDetailByPrimaryKey", id);
	}

	@Override
	public int updateWaybillRelateDetailByPrimaryKeySelective(WaybillRelateDetailEntity record) {
		return this.getSqlSession().update(NAMESPACE + "updateWaybillRelateDetailByPrimaryKeySelective", record);
	}

	@Override
	public int updateWaybillRelateDetailByPrimaryKey(WaybillRelateDetailEntity record) {
		return this.getSqlSession().update(NAMESPACE + "updateWaybillRelateDetailByPrimaryKey", record);
	}

	@Override
	public int insertWaybillRelateDetailBatchSelective(List<WaybillRelateDetailEntity> waybillRelateDtlList) {
		return this.getSqlSession().insert(NAMESPACE + "insertWaybillRelateDetailBatchSelective", waybillRelateDtlList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillRelateDetailEntity> queryNewWaybillRelateDtlEntityByNo(LastWaybillRfcQueryDto queryDto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryNewWaybillRelateDtlEntityByNo", queryDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillRelateDetailEntity> queryWaybillRelateDetailListByOrderOrWaybillNo(Map<String, Object> params) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillRelateDetailListByOrderOrWaybillNo", params);
	}

	@Override
	public int batchUpdateWaybillRelateDetailEntity(List<WaybillRelateDetailEntity> waybillChargeDtlList) {
		return this.getSqlSession().update(NAMESPACE + "batchUpdateWaybillRelateDetailEntity", waybillChargeDtlList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryWaybillNoListByParentWaybillNo(String waybillNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillNoListByParentWaybillNo", waybillNo);
	}

	@Override
	public int deleteWaybillRelateDetailByParentWaybillNo(Map<String, Object> maps) {
		return this.getSqlSession().delete(NAMESPACE + "deleteWaybillRelateDetailByParentWaybillNo", maps);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillRelateDetailEntity> queryCommonLevelRelateDetailListByOrderOrWaybillNo(Map<String, Object> params) {
		return this.getSqlSession().selectList(NAMESPACE+"queryCommonLevelRelateDetailListByOrderOrWaybillNo", params);
	}
	
	@Override
	public int updateWaybillRelateDetailByWaybillNoSelective(WaybillRelateDetailEntity record) {
		//添加更新子母件条件校验，限制传入条件不为空
		if (record == null) {
			return 0;
		}
		if (StringUtils.isEmpty(record.getWaybillNo())&&StringUtils.isEmpty(record.getParentWaybillNo())) {
			return 0;
		}
		return this.getSqlSession().update(NAMESPACE+"updateWaybillRelateDetailByWaybillNoSelective", record);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public WaybillRelateDetailEntity queryWaybillRelateDetailByWaybillNo(String waybillNo) {
		//判定运单号是否为空
		if(StringUtils.isEmpty(waybillNo)){
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("waybillNo", waybillNo);
		params.put("active", FossConstants.YES);
		List<WaybillRelateDetailEntity> list = this.getSqlSession().selectList(NAMESPACE+"queryCommonLevelRelateDetailListByOrderOrWaybillNo", params);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}
	
	@Override
	public WaybillRelateDetailEntity queryWaybillParentRelateDetailByWaybillNo(
			String waybillNo) {
		//判定运单号是否为空
		if(StringUtils.isEmpty(waybillNo)){
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("waybillNo", waybillNo);
		params.put("active", FossConstants.YES);
		params.put("isPicPackage", FossConstants.YES);
		List<WaybillRelateDetailEntity> list = this.getSqlSession().selectList(NAMESPACE+"queryCommonLevelRelateDetailListByOrderOrWaybillNo", params);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}
	
	/**
	 * 根据Pda盲扫记录查询出同一接货任务中的子母单记录
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-12 20:56:39
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillRelateDetailEntity> queryRelateDetailListByPdaScanTaskId(Map<String, Object> params) {
		return this.getSqlSession().selectList(NAMESPACE+"queryRelateDetailListByPdaScanTaskId", params);
	}

	
	//EwaybillRelate的天下
	@Override
	public int deleteEwaybillRelateByPrimaryKey(String id) {
		return this.getSqlSession().delete(NAMESPACE_JOB + "deleteEwaybillRelateByPrimaryKey", id);
	}

	@Override
	public int insertEwaybillRelate(EwaybillRelateEntity record) {
		return this.getSqlSession().insert(NAMESPACE_JOB + "insertEwaybillRelate", record);
	}

	@Override
	public int insertEwaybillRelateSelective(EwaybillRelateEntity record) {
		return this.getSqlSession().insert(NAMESPACE_JOB + "insertEwaybillRelateSelective", record);
	}

	@Override
	public EwaybillRelateEntity selectEwaybillRelateByPrimaryKey(String id) {
		return (EwaybillRelateEntity) this.getSqlSession().selectOne(NAMESPACE_JOB + "selectEwaybillRelateByPrimaryKey", id);
	}

	@Override
	public int updateEwaybillRelateByPrimaryKeySelective(EwaybillRelateEntity record) {
		return this.getSqlSession().update(NAMESPACE_JOB + "updateEwaybillRelateByPrimaryKeySelective", record);
	}

	@Override
	public int updateEwaybillRelateByPrimaryKey(EwaybillRelateEntity record) {
		return this.getSqlSession().update(NAMESPACE_JOB + "updateEwaybillRelateByPrimaryKey", record);
	}
	@Override
	public int updateEwaybillRelateForJob(GenerateUnActiveEWaybillVo vo) {
		return this.getSqlSession().update(NAMESPACE_JOB + "updateEwaybillRelateForJob", vo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EwaybillRelateEntity> queryAllEwaybillRelateByCommon(Map<String, Object> params) {
		return this.getSqlSession().selectList(NAMESPACE_JOB + "queryAllEwaybillRelateByCommon", params);
	}

	@Override
	public int deleteEcomWaybillRelateByPrimaryKey(String id) {
		return this.getSqlSession().delete(NAMESPACE_TEMP+"deleteEcomWaybillRelateByPrimaryKey", id);
	}

	@Override
	public int insertEcomWaybillRelate(EcomWaybillRelateEntity record) {
		return this.getSqlSession().insert(NAMESPACE_TEMP+"insertEcomWaybillRelate", record);
	}

	@Override
	public int insertEcomWaybillRelateSelective(EcomWaybillRelateEntity record) {
		return this.getSqlSession().insert(NAMESPACE_TEMP+"insertEcomWaybillRelateSelective", record);
	}

	@Override
	public EcomWaybillRelateEntity selectEcomWaybillRelateByPrimaryKey(String id) {
		return (EcomWaybillRelateEntity) this.getSqlSession().selectOne(NAMESPACE_TEMP+"selectEcomWaybillRelateByPrimaryKey", id);
	}

	@Override
	public int updateEcomWaybillRelateByPrimaryKeySelective(EcomWaybillRelateEntity record) {
		return this.getSqlSession().update(NAMESPACE_TEMP+"updateEcomWaybillRelateByPrimaryKeySelective", record);
	}

	@Override
	public int updateEcomWaybillRelateByPrimaryKey(EcomWaybillRelateEntity record) {
		return this.getSqlSession().update(NAMESPACE_TEMP+"updateEcomWaybillRelateByPrimaryKey", record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EcomWaybillRelateEntity> queryAllEwaybillRelateByEcomWaybillRelate(Map<String, Object> params) {
		return this.getSqlSession().selectList(NAMESPACE_TEMP+"queryAllEwaybillRelateByEcomWaybillRelate", params);
	}

	@Override
	public int updateEcomWaybillRelateByWaybillNoSelective(EcomWaybillRelateEntity record) {
		return this.getSqlSession().update(NAMESPACE_TEMP+"updateEcomWaybillRelateByWaybillNoSelective", record);
	}
	
	/**
	 * 根据运单号集合来查询各个运单号详情 
	 * @param waybillNumList 运单集合
	 * @param start 起始页
	 * @param limit 每页显示记录数
	 * @param flag 是否启用分页，true:启用，则start,limit不能为空；false:不启用，则start,limit可为0
	 * @return List<WaybillRelateDetailEntity>子母运单信息集合
	 * @see queryWaybillRelateDetailsByWaybillNos
	 * @author 272311-sangwenhao
	 * 2015.09.06
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillRelateDetailEntity> queryWaybillRelateDetailByWaybillNos(List<String> waybillNumList,int start,int limit,boolean flag) {
		LOGGER.info("进入Dao层 queryWaybillRelateDetailByWaybillNos");
		if(waybillNumList==null || waybillNumList.size()<=0){
			LOGGER.error("运单集合为空");
			throw new BusinessException("运单集合不能为空");
		}
		if(flag){//分页查询
			if(start < 0 ) {
				start  = 0 ;
			}
			if(limit> NUMBER_2000) {
				limit = NUMBER_2000 ;
			}
			RowBounds rb = new RowBounds(start, limit);
			return this.getSqlSession().selectList(NAMESPACE+"queryWaybillRelateDetailByWaybillNums", waybillNumList,rb);
		}else{//不进行分页
			return this.getSqlSession().selectList(NAMESPACE+"queryWaybillRelateDetailByWaybillNums", waybillNumList);
		}
	}
	
	/**
	 * 根据运单号集合来查询各个运单号详情 
	 * @param waybillNumList 运单集合
	 * @param start 起始页
	 * @param limit 每页显示记录数
	 * @param flag 是否启用分页，true:启用，则start,limit不能为空；false:不启用，则start,limit可为0
	 * @return List<WaybillRelateDetailEntity>子母运单信息集合
	 * @see queryWaybillRelateDetailByWaybillNos
	 * @author 272311-sangwenhao
	 * 2015.09.06
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillRelateDetailEntity> queryWaybillRelateDetailsByWaybillNos(List<String> waybillNumList,int start,int limit,boolean flag) {
		LOGGER.info("进入Dao层 queryWaybillRelateDetailsByWaybillNos");
		if(waybillNumList==null || waybillNumList.size()<=0){
			LOGGER.error("运单集合为空");
			throw new BusinessException("运单集合不能为空");
		}
		if(flag){//分页查询
			if(start < 0 ) {
				start  = 0 ;
			}
			if(limit> NUMBER_2000) {
				limit = NUMBER_2000 ;
			}
			RowBounds rb = new RowBounds(start, limit);
			return this.getSqlSession().selectList(NAMESPACE+"queryWaybillRelateDetailsByWaybillNums", waybillNumList,rb);
		}else{//不进行分页
			return this.getSqlSession().selectList(NAMESPACE+"queryWaybillRelateDetailsByWaybillNums", waybillNumList);
		}
	}

	/**
	 * TODO 根据原始订单号查询dop同步数据和pda扫描表重量体积
	 * @param originalNumber
	 * @author foss-206860
	 * @date 2015-9-11 下午2:45:54
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EcomWaybillRelateEntity> queryAllEwaybillRelateByEcomWaybillRelateAndPdascan(Map<String, Object> params) {
		return this.getSqlSession().selectList(NAMESPACE_TEMP+"queryAllEwaybillRelateByEcomWaybillRelateAndPdascan", params);
	}
	
	/**
	  * @description 通过任务ID查询所有原始订单号
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-13 t下午2:24:11
	  * @param params
	  * @return
	  * List<EcomWaybillRelateEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EcomWaybillRelateEntity> queryEcomWaybillRelateListByPdaScanTaskId(Map<String, Object> params){
		return this.getSqlSession().selectList(NAMESPACE_TEMP+"queryEcomWaybillRelateListByPdaScanTaskId", params);
	}

	/**
	  * @description 
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-13 t下午3:43:42
	  * @param @param vo
	  * @param @return
	  */
	@Override
	public int updateEWaybillRelateJobIDTopByRowNum(GenerateUnActiveEWaybillVo vo) {
		return this.getSqlSession().update(NAMESPACE_JOB+"updateEwaybillRelateForJob", vo);
	}

	/**
	  * @description 
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-13 t下午6:47:39
	  * @param @param params
	  * @param @return
	  */
	@SuppressWarnings("unchecked")
	@Override
	public List<EwaybillRelateEntity> queryEwaybillRelateByTaskId(Map<String, Object> params) {
		return this.getSqlSession().selectList(NAMESPACE_JOB+"selectEwaybillRelateByTaskId",params);
	}

	/**
	  * @description 
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-14 t上午9:11:33
	  * @param @param ewaybillRelateEntity
	  * @param @return
	  */
	@Override
	public int updateEwaybillRelateByPrimaryKeyNoSelective(EwaybillRelateEntity ewaybillRelateEntity) {
		return this.getSqlSession().update(NAMESPACE_JOB+"updateEwaybillRelateByPrimaryKeyNoSelective",ewaybillRelateEntity);
	}

	/**
	  * @description 
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-14 t下午8:47:46
	  * @param @param params
	  * @param @return
	  */
	@SuppressWarnings("unchecked")
	@Override
	public List<EcomWaybillRelateEntity> queryAllEwaybillRelateByEcomWaybillRelateWayBillNo(Map<String, Object> params) {
		return this.getSqlSession().selectList(NAMESPACE_TEMP+"queryAllEwaybillRelateByEcomWaybillRelateWayBillNo",params);
	}

	/**
	  * @description 
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-17 t下午1:58:26
	  * @param @param params
	  * @param @return
	  */
	@Override
	public List<EwaybillRelateEntity> selectEwaybillRelateByPrimaryOrderNo(Map<String, Object> params) {
		return this.getSqlSession().selectList(NAMESPACE_JOB+"selectEwaybillRelateByPrimaryOrderNo",params);
	}

	/**
	  * @description 
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-23 t下午9:29:14
	  * @param @param ecomWaybillRelateEntity
	  * @param @return
	  */
	@Override
	public int updateEcomWaybillRelateByWaybillByOrignalOrderNo(EcomWaybillRelateEntity ecomWaybillRelateEntity) {
		return this.getSqlSession().update(NAMESPACE_TEMP+"updateEcomWaybillRelateByWaybillByOrignalOrderNo",ecomWaybillRelateEntity);
	}
	
	/**
	 * <p>子母件激活线程处理，线程池满数据回滚操作，将jobId 置为N/A,状态置为N待于再执行一次子母件激活后续流程</p> 
	 * @author Foss-151211-yangtaohong 
	 * @date 2016-8-14 下午5:35:15
	 * @param id
	 * @return
	 * @see
	 */
	@Override
	public int updateJobIdForActive(String id) {
		return this.getSqlSession().update(NAMESPACE_JOB+"updateJobIdForActive",id);
	}
}