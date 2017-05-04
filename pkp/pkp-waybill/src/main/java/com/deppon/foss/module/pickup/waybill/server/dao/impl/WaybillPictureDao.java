package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPictureDao;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.WaybillPicturePushLogDto;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.PDAWoodenRequireEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPicturePushLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPicturePushMessageEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureSendSmsEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPushMessageEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.CenterBillOrgDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SearchPictureWaybillCondiction;
import com.deppon.foss.module.pickup.waybill.shared.dto.SubCenterBillOrgDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPictureDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.waybill.shared.vo.SearchPictureVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillPicturePushVo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 图片开单DAO
 * ClassName: WaybillPictureDao <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-10-10 下午2:58:16 <br/>
 *
 * @author 157229-zxy
 * @version 
 * @since JDK 1.6
 */
public class WaybillPictureDao extends iBatis3DaoImpl implements IWaybillPictureDao{
	
	private static final String NAMESPACE = "foss.pkp.WaybillPictureEntityMapper.";
	//新增
	private static final String INSERT_WAYBILL_PICTURE = "insertWaybillPicture";
	//修改
	private static final String UPDATE_WAYBILL_PICTURE_BY_ENTITY = "updateWaybillPictureByEntity";
	//根据运单号删除
	private static final String DELETE_WAYBILL_PICTURE_BY_WAYBILLNO = "deleteWaybillPictureByWaybillNo";
	//根据ID删除
	private static final String DELETE_WAYBILL_PICTURE_BY_ID = "deleteWaybillPictureById";
	//查询图片开单列表
	private static final String QUERY_WAYBILL_PICTURE_BY_ENTITY = "queryWaybillPictureByEntity";
	//查询图片运单列表
	private static final String QUERY_WAYBILL_PICTURE_BY_ENTITY_LIST =  "queryWaybillPictureByEntityList";
	//查询图片开单列表总数
	private static final String QUERY_WAYBILL_PICTURE_BY_ENTITY_COUNT = "queryWaybillPictureByEntitycount";
	//根据ID更改运单审批状态
	private static final String UPDATE_WAYBILL_PICTURE_BY_ID = "updatePictureWaybillById";
	
	IConfigurationParamsService configurationParamsService;
	
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	@Override
	public int insertWaybillPicture(WaybillPictureEntity waybillPictureEntity) {
		waybillPictureEntity.setId(UUIDUtils.getUUID());
    	return this.getSqlSession().insert(NAMESPACE + INSERT_WAYBILL_PICTURE, waybillPictureEntity);

	}

	@Override
	public int updateWaybillPictureByEntity(
			WaybillPictureEntity waybillPictureEntity) {
		return this.getSqlSession().update(NAMESPACE + UPDATE_WAYBILL_PICTURE_BY_ENTITY, waybillPictureEntity);
	}

	@Override
	public int deleteWaybillPictureByWaybillNo(String waybillNo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("waybillNo", waybillNo);
		return this.getSqlSession().delete(NAMESPACE + DELETE_WAYBILL_PICTURE_BY_WAYBILLNO, param);
	}

	@Override
	public int deleteWaybillPictureById(String id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		return this.getSqlSession().delete(NAMESPACE + DELETE_WAYBILL_PICTURE_BY_ID, param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillPictureEntity> queryWaybillPictureByEntity(
			WaybillPictureEntity waybillPictureEntity) {
		WaybillPictureDto waybillPictureDto = new WaybillPictureDto();
		BeanUtils.copyProperties(waybillPictureEntity, waybillPictureDto);
		return this.getSqlSession().selectList(NAMESPACE + QUERY_WAYBILL_PICTURE_BY_ENTITY, waybillPictureDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public WaybillPictureEntity queryWaybillPictureByWaybillNo(String waybillNo) {
		WaybillPictureDto waybillPictureDto = new WaybillPictureDto();
		waybillPictureDto.setWaybillNo(waybillNo);
		String excludePendgingType = WaybillConstants.WAYBILL_PICTURE_TYPE_CANCEL;
		waybillPictureDto.setExcludePendgingType(excludePendgingType);
		waybillPictureDto.setActive(FossConstants.ACTIVE);
		List<WaybillPictureEntity> list = this.getSqlSession().selectList(NAMESPACE + QUERY_WAYBILL_PICTURE_BY_ENTITY, waybillPictureDto);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillPictureEntity> queryWaybillPictureByDtoPage(WaybillPictureDto waybillPictureDto) {
		if(waybillPictureDto.getLimit() == -1){
			List<WaybillPictureEntity> waybillPictureEntityLst =  this.getSqlSession().selectList(NAMESPACE + QUERY_WAYBILL_PICTURE_BY_ENTITY_LIST, waybillPictureDto);
			return waybillPictureEntityLst;
		}else{
			RowBounds rowBounds = new RowBounds(waybillPictureDto.getStart(),waybillPictureDto.getLimit());
			return (List<WaybillPictureEntity>) this.getSqlSession().selectList(NAMESPACE + QUERY_WAYBILL_PICTURE_BY_ENTITY, waybillPictureDto, rowBounds);
		}
	}
	
	@Override
	public List<WaybillPictureDto> queryWaybillPictureDtoPage(WaybillPictureDto waybillPictureDto) {
		if(waybillPictureDto.getLimit() == -1){
			List<WaybillPictureDto> waybillPictureEntityLst =  this.getSqlSession().selectList(NAMESPACE + QUERY_WAYBILL_PICTURE_BY_ENTITY_LIST, waybillPictureDto);
			return waybillPictureEntityLst;
		}else{
			RowBounds rowBounds = new RowBounds(waybillPictureDto.getStart(),waybillPictureDto.getLimit());
			return (List<WaybillPictureDto>) this.getSqlSession().selectList(NAMESPACE + QUERY_WAYBILL_PICTURE_BY_ENTITY_LIST, waybillPictureDto, rowBounds);
		}		
	}
	
	@Override
	public Long queryWaybillPictureByDtoPageCounts(
			WaybillPictureDto waybillPictureDto) {
            return (Long)this.getSqlSession().selectOne(NAMESPACE + QUERY_WAYBILL_PICTURE_BY_ENTITY_COUNT, waybillPictureDto);
	}
	/**
	 * 查询图片开单信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SearchPictureVo> searchPictureWaybillByCondiction(
			SearchPictureWaybillCondiction condiction) {
		
		return this.getSqlSession().selectList(NAMESPACE + "searchPictureWaybillByCondiction",
				condiction);
	}

	@Override
	public int findWaybillPictureCount(WaybillPictureEntity entity) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"findWaybillPictureCount",entity);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public WaybillPictureEntity findWaybillPictureOne(WaybillPictureEntity wpe) {
		/*注释掉原来的排序查询逻辑
		WaybillPictureEntity waybillPictureEntity = (WaybillPictureEntity) this.getSqlSession().selectOne(NAMESPACE+"findWaybillPictureByOperator",wpe);
		if(waybillPictureEntity == null){
			//退回优先
			waybillPictureEntity = (WaybillPictureEntity) this.getSqlSession().selectOne(NAMESPACE+"findWaybillPictureByReturnType",wpe);
			if(waybillPictureEntity == null){
				waybillPictureEntity = (WaybillPictureEntity) this.getSqlSession().selectOne(NAMESPACE+"findWaybillPictureAndGigGoodsFlagAndCashPayFlag",wpe);
			}
			//添加展会货--zoushengli
			if(waybillPictureEntity == null){
				waybillPictureEntity = (WaybillPictureEntity) this.getSqlSession().selectOne(NAMESPACE+"findWaybillPictureAndIsExhibitCargo",wpe);
			}
			if(waybillPictureEntity == null){
				waybillPictureEntity = (WaybillPictureEntity) this.getSqlSession().selectOne(NAMESPACE+"findWaybillPictureOne",wpe);
			}
			if(waybillPictureEntity == null){
				return null;
			}
		}*/
		//查询图片开单设置 参数   by:352676
		ConfigurationParamsEntity entity = configurationParamsService
				.queryConfigurationParamsByOrgCode(
						DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
						ConfigurationParamsConstants.PKP_FOSS_PIC_WAYBILL_PENDING_SET,
						wpe.getBelongOrgCode());
		String confValue=entity.getConfValue();
		//将confValue转换成秒数
		int totalSecond=0;
		try {
			if(StringUtil.isNotBlank(confValue)){
				String[] times=confValue.split("-");
				int hour=Integer.valueOf(times[0]).intValue();
				int miniute=Integer.valueOf(times[1]).intValue();
				int second=Integer.valueOf(times[2]).intValue();
				totalSecond=hour*3600+miniute*60+second;
			}
	 } catch (Exception e) {
			throw new WaybillValidateException("图片开单设置图片参数有误");
		}
		//按照【现金】>【空运】>【展会】>【其他】运单排序   by: 352676
		//如果图片开单界面异常关闭，则优先分配异常关闭的运单到对应开单员
		WaybillPictureEntity waybillPictureEntity =(WaybillPictureEntity) this.getSqlSession().selectOne(NAMESPACE+"getPictureWaybillByDistributer",wpe); 
		if(waybillPictureEntity==null){
		//查询现金
		waybillPictureEntity = (WaybillPictureEntity) this.getSqlSession().selectOne(NAMESPACE+"findWaybillPictureByCash",wpe);
		}
		if(waybillPictureEntity==null){
			//如果现金为空，查询空运
			waybillPictureEntity = (WaybillPictureEntity) this.getSqlSession().selectOne(NAMESPACE+"findWaybillPictureByAir",wpe);
		}
		if(waybillPictureEntity==null){
			//如果现金和空运为空，查询展会
			waybillPictureEntity = (WaybillPictureEntity) this.getSqlSession().selectOne(NAMESPACE+"findWaybillPictureByExhibitCargo",wpe);
		}
		if(waybillPictureEntity==null){
			//如果现金和空运展会为空，查询其他本地资金池图片
			Map wpeMap=new HashMap();
			wpeMap.put("waybillPictureEntity", wpe);
			wpeMap.put("totalSecond",totalSecond);
			waybillPictureEntity = (WaybillPictureEntity) this.getSqlSession().selectOne(NAMESPACE+"findWaybillPictureByLocal",wpeMap);
		}
		if(waybillPictureEntity==null){
			//如果没有本地图片和现金空运展会，查询其他运单
			
			waybillPictureEntity = (WaybillPictureEntity) this.getSqlSession().selectOne(NAMESPACE+"findWaybillPictureByOther",wpe);
		}
		return waybillPictureEntity;
	}

	@Override
	public int updateWaybillPicture(WaybillPictureEntity wpe) {
		logger.info("进入updateWaybillPicture方法");
		return this.getSqlSession().update(NAMESPACE+"updateWaybillPicture",wpe);
	}

	@Override
	public int updatePictureWaybillByWaybillno(
			WaybillPictureEntity waybillPictureEntity) {
		return this.getSqlSession().update(NAMESPACE+"updatePictureWaybillByWaybillno",waybillPictureEntity);
	}
	
	/**
	 * 根据waybillId查询运单信息
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPictureDao#queryWaybillPictureById(java.lang.String)
	 */
	@Override
	public WaybillPictureEntity queryWaybillPictureById(String waybillId) {
		WaybillPictureDto waybillPictureDto = new WaybillPictureDto();
		waybillPictureDto.setId(waybillId);
		waybillPictureDto.setActive(FossConstants.ACTIVE);
		return (WaybillPictureEntity) this.getSqlSession().selectOne(NAMESPACE + QUERY_WAYBILL_PICTURE_BY_ENTITY, waybillPictureDto);
		
	}
    /**
     * 外场补录重量体积时，保存从pda传来的包装货物信息
     */
	@Override
	public void insertWaybillPDAWoodenRequireEntity(PDAWoodenRequireEntity requireEntity){
		requireEntity.setId(UUIDUtils.getUUID());
    	 this.getSqlSession().insert(NAMESPACE + "insertWaybillPDAWoodenRequireEntity", requireEntity);
	}
	@Override
	public int saveWaybillPushMessage(WaybillPushMessageEntity e){
		e.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(NAMESPACE + "insertWaybillPushMessageEntity", e);
	}
	@Override
	public int delWaybillPushMessage(WaybillPushMessageEntity e){
		
		return this.getSqlSession().delete(NAMESPACE + "delWaybillPushMessageEntity", e);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillPushMessageEntity> queryWaybillPushMessageEntity(int min){
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillPushMessageEntity",min);
	}
	
	
	@Override
	public void insertWaybillPicturePushMessage(
			WaybillPicturePushMessageEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert(NAMESPACE + "insertWaybillPicturePushMessage",entity);
	}

	@Override
	public void insertWaybillPictureSendSms(
			WaybillPictureSendSmsEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert(NAMESPACE + "insertWaybillPictureSendSms",entity);
	}

	@Override
	public void deleteWaybillPicturePushMessageById(String id) {
		this.getSqlSession().delete(NAMESPACE + "deleteWaybillPicturePushMessage",id);
	}

	@Override
	public void deleteWaybillPictureSendSmsById(String id) {
		this.getSqlSession().delete(NAMESPACE + "deleteWaybillPictureSendSms",id);
	}

	@Override
	public int updatePushMessageByRowNum(WaybillPicturePushVo vo) {
		return this.getSqlSession().update(NAMESPACE + "updatePushMessageByRowNum",vo);
	}

	@Override
	public int updateSendSmsByRowNum(WaybillPicturePushVo vo) {
		return this.getSqlSession().update(NAMESPACE + "updateSendSmsByRowNum",vo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillPicturePushMessageEntity> queryWaybillPicturePushMessageByJobId(String jobId) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillPicturePushMessageByJobId",jobId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillPictureSendSmsEntity> queryWaybillPictureSendSmsByJobId(String jobId) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillPictureSendSmsByJobId",jobId);
	}

	@Override
	public void insertWaybillPicturePushLog(WaybillPicturePushLogEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert(NAMESPACE + "insertWaybillPicturePushLog", entity);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillPicturePushLogDto> queryOuterWaybillPicturePushLog(List<String> waybillNos) {
		if(CollectionUtils.isEmpty(waybillNos)){
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("waybillNos", waybillNos);
		return getSqlSession().selectList(NAMESPACE+"queryOuterWaybillPicturePushLog", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CenterBillOrgDto> queryPicBillOrgAndNotCount(
			Map<String, Object> params) {
		return this.getSqlSession().selectList(NAMESPACE + "queryPicBillOrgAndNotCount",params);
	}

	@Override
	public void updateWaybillPictureById(WaybillPictureDto waybillPictureDto) {
		// TODO Auto-generated method stub
	     this.getSqlSession().update(NAMESPACE+UPDATE_WAYBILL_PICTURE_BY_ID, waybillPictureDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> iteratorQueryDepartmentCodeLists(
			com.deppon.foss.module.pickup.waybill.shared.dto.OrgCodeTreeByCodeAndCondition treeByCodeAndCondition) {
		return this.getSqlSession().selectList(NAMESPACE+"iteratorQueryDepartmentCodeLists", treeByCodeAndCondition);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SubCenterBillOrgDto> queryDistributeInfoBySql(
			Map<String, Object> params) {
		if(params ==null || params.get("pendgingType")==null || params.get("orgCode")==null ){
			return new ArrayList<SubCenterBillOrgDto>();
		}
		return this.getSqlSession().selectList(NAMESPACE+"queryDistributeInfoBySql", params);
	}
	/**
	 *  图片开单查询本地未补录订单
	 * @author Foss-352676-YUANHB 
	 * @date 2017-3-9 下午10:03:33
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPictureDao#getPictureWaybillLocalCount(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity)
	 */
	@Override
	public int getPictureWaybillLocalCount(WaybillPictureEntity entity) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"getPictureWaybillLocalCount",entity);
	}
	/**
	 *  图片开单查询全国未补录订单
	 * @author Foss-352676-YUANHB 
	 * @date 2017-3-9 下午10:03:44
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPictureDao#getPictureWaybillAllCount()
	 */
	@Override
	public int getPictureWaybillAllCount(WaybillPictureEntity entity) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"getPictureWaybillAllCount",entity);
	}
	
	
}
