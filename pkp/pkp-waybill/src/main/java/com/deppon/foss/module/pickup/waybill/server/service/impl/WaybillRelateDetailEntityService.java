package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRelateDetailEntityDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.server.dao.impl.WaybillRelateDetailEntityDao;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.EcomWaybillRelateEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.EwaybillRelateEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRelateDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillSystemDto;
import com.deppon.foss.module.pickup.waybill.shared.vo.GenerateUnActiveEWaybillVo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 子母件服务层
 * @author Foss-105888-Zhangxingwang
 * @date 2015-8-6 16:17:58
 */
public class WaybillRelateDetailEntityService implements IWaybillRelateDetailEntityService {
	
	private Logger LOGGER = LoggerFactory.getLogger(WaybillRelateDetailEntityDao.class);
	
	/**
	 * 子母件数据持久层
	 */
	private IWaybillRelateDetailEntityDao waybillRelateDetailEntityDao;
	
	/**
	 * 批量添加有效的子母件数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-6 16:38:36
	 * @param waybillRelateDtlList
	 * @param systemDto
	 * @return
	 */
	@Override
	public int addWaybillRelateDetailEntityBatch(List<WaybillRelateDetailEntity> waybillRelateDtlList) {
		return waybillRelateDetailEntityDao.insertWaybillRelateDetailBatchSelective(waybillRelateDtlList);
	}

	/**
	 * 批量添加无效的子母件数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-6 16:38:36
	 * @param waybillRelateDtlList
	 * @param systemDto
	 * @return
	 */
	@Override
	public int appendWaybillRelateDetailEntityBatchAfterChange(List<WaybillRelateDetailEntity> waybillChargeDtlList, WaybillSystemDto systemDto) {
		for (WaybillRelateDetailEntity entity : waybillChargeDtlList) {
			entity.setId(UUIDUtils.getUUID());
			// 设置创建人、创建时间、更新人、更新时间
			entity.setCreateTime(systemDto.getCreateTime());
			entity.setModifyTime(systemDto.getModifyTime());
			entity.setBillTime(systemDto.getBillTime());
			// 设置是否有效
			entity.setActive(FossConstants.INACTIVE);
		}
		return waybillRelateDetailEntityDao.insertWaybillRelateDetailBatchSelective(waybillChargeDtlList);
	}

	/**
	 * 批量添加无效的子母件数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-6 16:38:36
	 * @param waybillRelateDtlList
	 * @param systemDto
	 * @return
	 */
	@Override
	public int batchUpdateWaybillRelateDetailEntity(List<WaybillRelateDetailEntity> waybillChargeDtlList) {
	/*	for (WaybillRelateDetailEntity entity : waybillChargeDtlList) {
			entity.setId(UUIDUtils.getUUID());
			// 设置创建人、创建时间、更新人、更新时间
			entity.setCreateTime(systemDto.getCreateTime());
			entity.setModifyTime(systemDto.getModifyTime());
			entity.setBillTime(systemDto.getBillTime());
			// 设置是否有效
			entity.setActive(FossConstants.INACTIVE);
		}*/
		return waybillRelateDetailEntityDao.batchUpdateWaybillRelateDetailEntity(waybillChargeDtlList);
	}
	/**
	 * 添加待激活的数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-7 10:00:27
	 */
	@Override
	public int addUnActiveWaybillRelateDetailEntityBatch(List<String> waybillNoList, String waybillNo) {
		if(CollectionUtils.isEmpty(waybillNoList) || StringUtils.isEmpty(waybillNo)){
			return 0;
		}
		int count = 0;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentWaybillNo", waybillNo);
		params.put("active", FossConstants.YES);
		List<WaybillRelateDetailEntity> waybillChargeDtlList =
				waybillRelateDetailEntityDao.queryWaybillRelateDetailListByOrderOrWaybillNo(params);
		WaybillRelateDetailEntity entity = null;
		List<WaybillRelateDetailEntity> relateDetailList = new ArrayList<WaybillRelateDetailEntity>();
		Date nowDate = new Date();
		//如果数据为空，说明没有数据，直接进行数据的插入吧
		if(CollectionUtils.isEmpty(waybillChargeDtlList)){
			for(String addWaybillNo : waybillNoList){
				entity = new WaybillRelateDetailEntity();
				entity.setId(UUIDUtils.getUUID());
				entity.setActive(FossConstants.YES);
				entity.setParentOrderNo(waybillNo);
				entity.setWaybillNo(addWaybillNo);
				entity.setCreateTime(nowDate);
				entity.setModifyTime(nowDate);
				entity.setBillTime(nowDate);
				entity.setPendingType(WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING);
				relateDetailList.add(entity);
			}
			count = waybillRelateDetailEntityDao.insertWaybillRelateDetailBatchSelective(relateDetailList);
		}else{
			//常用时间模型数据的封装
			WaybillSystemDto systemDto = new WaybillSystemDto();
			systemDto.setBillTime(nowDate);
			systemDto.setCreateTime(nowDate);
			systemDto.setModifyTime(nowDate);
			//现有系统存储的子单号集合
			List<String> relateDetailCodeList = new ArrayList<String>();
			//这里可能是原有单号有，但是第二次更新这个单子被干掉了
			List<String> unRelateDetailCodeList = new ArrayList<String>();
			//本次添加的运单号集合
			List<String> addRelateDetailCodeList = new ArrayList<String>();
			//筛选出原有的数据，需要作废的运单数据
			for(int i = 0; i < relateDetailList.size(); i++){
				relateDetailCodeList.add(relateDetailList.get(i).getWaybillNo());
				if(!waybillNoList.contains(relateDetailList.get(i).getWaybillNo())){
					unRelateDetailCodeList.add(relateDetailList.get(i).getWaybillNo());
				}
			}
			//新增的数据
			for(int i = 0; i < waybillNoList.size(); i++){
				if(!relateDetailCodeList.contains(waybillNoList.get(i))){
					addRelateDetailCodeList.add(waybillNoList.get(i));
				}
			}
			//新增的数据
			if(CollectionUtils.isNotEmpty(addRelateDetailCodeList)){
				for(String addWaybillNo : addRelateDetailCodeList){
					entity = new WaybillRelateDetailEntity();
					entity.setId(UUIDUtils.getUUID());
					entity.setActive(FossConstants.YES);
					entity.setParentOrderNo(waybillNo);
					entity.setWaybillNo(addWaybillNo);
					entity.setCreateTime(nowDate);
					entity.setModifyTime(nowDate);
					entity.setBillTime(nowDate);
					entity.setPendingType(WaybillConstants.WAYBILL_STATUS_EWAYBILL_PENDING);
					relateDetailList.add(entity);
				}
				count = count + this.addWaybillRelateDetailEntityBatch(relateDetailList);
			}
			//需要被干掉的数据
			if(CollectionUtils.isNotEmpty(unRelateDetailCodeList)){
				for(WaybillRelateDetailEntity detailEntity : waybillChargeDtlList){
					if(unRelateDetailCodeList.contains(detailEntity.getWaybillNo())){
						detailEntity.setActive(FossConstants.NO);
						detailEntity.setModifyTime(nowDate);
						relateDetailList.add(detailEntity);
					}
				}
				count = count + this.batchUpdateWaybillRelateDetailEntity(relateDetailList);
			}
		}
		return count;
	}
	
	/**
	 * 判定是否子母件
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-31 10:47:56
	 */
	@Override
	public TwoInOneWaybillDto queryWaybillRelateByWaybillOrOrderNo(Map<String, Object> params) {
		if(params == null || params.size() <= 0){
			throw new BusinessException("传入的参数不能为空");
		}
		TwoInOneWaybillDto twoInOneWaybillDto = new TwoInOneWaybillDto();
		List<WaybillRelateDetailEntity> list = this.queryCommonLevelRelateDetailListByOrderOrWaybillNo(params);
		if(CollectionUtils.isEmpty(list)){
			twoInOneWaybillDto.setIsTwoInOne(FossConstants.NO);
			return twoInOneWaybillDto;
		}
		twoInOneWaybillDto.setIsTwoInOne(FossConstants.YES);
		List<String> waybillNoList = new ArrayList<String>();
		//母运单号
		twoInOneWaybillDto.setMainWaybillNo(list.get(0).getParentWaybillNo());
		for(WaybillRelateDetailEntity entity : list){
			if(FossConstants.NO.equals(entity.getIsPicPackage())){
				waybillNoList.add(entity.getWaybillNo());
			}
			
		}
		twoInOneWaybillDto.setWaybillNoList(waybillNoList);
		return twoInOneWaybillDto;
	}

	@Override
	public List<WaybillRelateDetailEntity> queryRelateDetailListByPdaScanTaskId(Map<String, Object> params) {
		return waybillRelateDetailEntityDao.queryRelateDetailListByPdaScanTaskId(params);
	}
	
	/**
	 * 根据订单号、母单号、子单号删除数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-10 17:12:44
	 */
	@Override
	public int deleteWaybillRelateDetailByParentWaybillNo(Map<String, Object> maps) {
		return waybillRelateDetailEntityDao.deleteWaybillRelateDetailByParentWaybillNo(maps);
	}

	/**
	 * 根据单号、订单号进行数据查询
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-8 16:04:13
	 */
	@Override
	public List<WaybillRelateDetailEntity> queryWaybillRelateDetailListByOrderOrWaybillNo(Map<String, Object> params) {
		return waybillRelateDetailEntityDao.queryWaybillRelateDetailListByOrderOrWaybillNo(params);
	}

	/**
	 * 根据运单号、开单时间、修改时间、业务时间查询对应的子母单详情数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-6 17:34:36
	 */
	@Override
	public List<WaybillRelateDetailEntity> queryNewWaybillRelateDtlEntityByNo(LastWaybillRfcQueryDto queryDto) {
		return waybillRelateDetailEntityDao.queryNewWaybillRelateDtlEntityByNo(queryDto);
	}

	@Override
	public List<WaybillRelateDetailEntity> queryCommonLevelRelateDetailListByOrderOrWaybillNo(Map<String, Object> params) {
		if(params == null || params.size() < 0){
			return null;
		}
		return waybillRelateDetailEntityDao.queryCommonLevelRelateDetailListByOrderOrWaybillNo(params);
	}
	
	@Override
	public int deleteWaybillRelateDetailByPrimaryKey(String id) {
		return waybillRelateDetailEntityDao.deleteWaybillRelateDetailByPrimaryKey(id);
	}

	@Override
	public int insertWaybillRelateDetail(WaybillRelateDetailEntity record) {
		return waybillRelateDetailEntityDao.insertWaybillRelateDetail(record);
	}

	@Override
	public int insertWaybillRelateDetailSelective(WaybillRelateDetailEntity record) {
		return waybillRelateDetailEntityDao.insertWaybillRelateDetailSelective(record);
	}

	@Override
	public WaybillRelateDetailEntity selectWaybillRelateDetailByPrimaryKey(String id) {
		return waybillRelateDetailEntityDao.selectWaybillRelateDetailByPrimaryKey(id);
	}

	@Override
	public int updateWaybillRelateDetailByPrimaryKeySelective(WaybillRelateDetailEntity record) {
		return waybillRelateDetailEntityDao.updateWaybillRelateDetailByPrimaryKeySelective(record);
	}

	@Override
	public int updateWaybillRelateDetailByWaybillNoSelective(WaybillRelateDetailEntity record) {
		return waybillRelateDetailEntityDao.updateWaybillRelateDetailByWaybillNoSelective(record);
	}
	

	@Override
	public int updateWaybillRelateDetailByPrimaryKey(WaybillRelateDetailEntity record) {
		return waybillRelateDetailEntityDao.updateWaybillRelateDetailByPrimaryKey(record);
	}

	@Override
	public List<String> queryWaybillNoListByParentWaybillNo(String waybillNo) {
		return waybillRelateDetailEntityDao.queryWaybillNoListByParentWaybillNo(waybillNo);
	}
	
	public void setWaybillRelateDetailEntityDao(IWaybillRelateDetailEntityDao waybillRelateDetailEntityDao) {
		this.waybillRelateDetailEntityDao = waybillRelateDetailEntityDao;
	}

	@Override
	public int deleteEwaybillRelateByPrimaryKey(String id) {
		return waybillRelateDetailEntityDao.deleteEwaybillRelateByPrimaryKey(id);
	}

	@Override
	public int insertEwaybillRelate(EwaybillRelateEntity record) {
		return waybillRelateDetailEntityDao.insertEwaybillRelate(record);
	}

	@Override
	public int insertEwaybillRelateSelective(EwaybillRelateEntity record) {
		return waybillRelateDetailEntityDao.insertEwaybillRelate(record);
	}

	@Override
	public EwaybillRelateEntity selectEwaybillRelateByPrimaryKey(String id) {
		return waybillRelateDetailEntityDao.selectEwaybillRelateByPrimaryKey(id);
	}

	@Override
	public int updateEwaybillRelateByPrimaryKeySelective(EwaybillRelateEntity record) {
		return waybillRelateDetailEntityDao.updateEwaybillRelateByPrimaryKeySelective(record);
	}

	@Override
	public int updateEwaybillRelateByPrimaryKey(EwaybillRelateEntity record) {
		return waybillRelateDetailEntityDao.updateEwaybillRelateByPrimaryKey(record);
	}

	@Override
	public List<EwaybillRelateEntity> queryAllEwaybillRelateByCommon(Map<String, Object> params) {
		return waybillRelateDetailEntityDao.queryAllEwaybillRelateByCommon(params);
	}

	@Override
	public int updateEwaybillRelateForJob(GenerateUnActiveEWaybillVo vo) {
		return waybillRelateDetailEntityDao.updateEwaybillRelateForJob(vo);
	}

	@Override
	public int deleteEcomWaybillRelateByPrimaryKey(String id) {
		return waybillRelateDetailEntityDao.deleteEcomWaybillRelateByPrimaryKey(id);
	}

	@Override
	public int insertEcomWaybillRelate(EcomWaybillRelateEntity record) {
		return waybillRelateDetailEntityDao.insertEcomWaybillRelate(record);
	}

	@Override
	public int insertEcomWaybillRelateSelective(EcomWaybillRelateEntity record) {
		return waybillRelateDetailEntityDao.insertEcomWaybillRelateSelective(record);
	}

	@Override
	public EcomWaybillRelateEntity selectEcomWaybillRelateByPrimaryKey(String id) {
		return waybillRelateDetailEntityDao.selectEcomWaybillRelateByPrimaryKey(id);
	}

	@Override
	public int updateEcomWaybillRelateByPrimaryKeySelective(EcomWaybillRelateEntity record) {
		return waybillRelateDetailEntityDao.updateEcomWaybillRelateByPrimaryKeySelective(record);
	}

	@Override
	public int updateEcomWaybillRelateByPrimaryKey(EcomWaybillRelateEntity record) {
		return waybillRelateDetailEntityDao.updateEcomWaybillRelateByPrimaryKey(record);
	}

	@Override
	public List<EcomWaybillRelateEntity> queryAllEwaybillRelateByEcomWaybillRelate(Map<String, Object> params) {
		return waybillRelateDetailEntityDao.queryAllEwaybillRelateByEcomWaybillRelate(params);
	}

	@Override
	public int updateEcomWaybillRelateByWaybillNoSelective(EcomWaybillRelateEntity record) {
		return waybillRelateDetailEntityDao.updateEcomWaybillRelateByWaybillNoSelective(record);
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
	@Override
	public List<WaybillRelateDetailEntity> queryWaybillRelateDetailByWaybillNos(List<String> waybillNoList,int start,int limit,boolean flag) {
		LOGGER.info("进入Service层 queryWaybillRelateDetailByWaybillNos");
		if(CollectionUtils.isEmpty(waybillNoList)){
			return null ;
		}
		return waybillRelateDetailEntityDao.queryWaybillRelateDetailByWaybillNos(waybillNoList, start, limit, flag);
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
	@Override
	public List<WaybillRelateDetailEntity> queryWaybillRelateDetailsByWaybillNos(List<String> waybillNoList,int start,int limit,boolean flag) {
		LOGGER.info("进入Service层 queryWaybillRelateDetailsByWaybillNos");
		if(CollectionUtils.isEmpty(waybillNoList)){
			return null ;
		}
		return waybillRelateDetailEntityDao.queryWaybillRelateDetailsByWaybillNos(waybillNoList, start, limit, flag);
	}

	/**
	  * @description 
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-13 t下午2:04:57
	  * @param @param map
	  * @param @return
	  */
	@Override
	public List<EcomWaybillRelateEntity> queryEcomWaybillRelateListByPdaScanTaskId(Map<String, Object> params) {
		return waybillRelateDetailEntityDao.queryEcomWaybillRelateListByPdaScanTaskId(params);
	}

	/**
	  * @description 
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-13 t下午2:04:57
	  * @param @param params
	  * @param @return
	  */
	@Override
	public List<EcomWaybillRelateEntity> queryAllEwaybillRelateByEcomWaybillRelateAndPdascan(Map<String, Object> params) {
		return waybillRelateDetailEntityDao.queryAllEwaybillRelateByEcomWaybillRelateAndPdascan(params);
	}

	/**
	  * @description 
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-13 t下午3:42:10
	  * @param @param vo
	  * @param @return
	  */
	@Override
	public int updateJobIDTopByRowNum(GenerateUnActiveEWaybillVo vo) {
		return waybillRelateDetailEntityDao.updateEWaybillRelateJobIDTopByRowNum(vo);
	}

	/**
	  * @description 
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-13 t下午6:51:59
	  * @param @param params
	  * @param @return
	  */
	@Override
	public List<EwaybillRelateEntity> queryEwaybillRelateByTaskId(Map<String, Object> params) {
		return waybillRelateDetailEntityDao.queryEwaybillRelateByTaskId(params);
	}
	
	@Override
	public int updateEwaybillRelateByPrimaryKeyNoSelective(EwaybillRelateEntity ewaybillRelateEntity){
		return waybillRelateDetailEntityDao.updateEwaybillRelateByPrimaryKeyNoSelective(ewaybillRelateEntity);
	}

	/**
	  * @description 
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-14 t下午8:52:02
	  * @param @param params
	  * @param @return
	  */
	@Override
	public List<EcomWaybillRelateEntity> queryAllEwaybillRelateByEcomWaybillRelateWayBillNo(Map<String, Object> params) {
		return waybillRelateDetailEntityDao.queryAllEwaybillRelateByEcomWaybillRelateWayBillNo(params);
	}

	/**
	  * @description 
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-17 t下午1:59:53
	  * @param @param params
	  * @param @return
	  */
	@Override
	public List<EwaybillRelateEntity> selectEwaybillRelateByPrimaryOrderNo(Map<String, Object> params) {
		return waybillRelateDetailEntityDao.selectEwaybillRelateByPrimaryOrderNo(params);
	}

	/**
	  * @description 
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-23 t下午9:30:46
	  * @param @param ecomWaybillRelateEntity
	  * @param @return
	  */
	@Override
	public int updateEcomWaybillRelateByWaybillByOrignalOrderNo(EcomWaybillRelateEntity ecomWaybillRelateEntity) {
		return waybillRelateDetailEntityDao.updateEcomWaybillRelateByWaybillByOrignalOrderNo(ecomWaybillRelateEntity);
	}
	
	/**
	 * <p>子母件激活线程处理，线程池满数据回滚操作，将jobId 置为N/A,状态置为N待于再执行一次子母件激活后续流程</p> 
	 * @author Foss-151211-yangtaohong 
	 * @date 2016-8-21 下午5:01:30
	 * @param id
	 * @return
	 * @see
	 */
	@Override
	public int updateJobIdForActive(String id) {
		return waybillRelateDetailEntityDao.updateJobIdForActive(id);
	}
}