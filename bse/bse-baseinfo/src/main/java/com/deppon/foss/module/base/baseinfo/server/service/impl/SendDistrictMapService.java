package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISendDistrictMapDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISendDistrictMapService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SendDistrictItemAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SendDistrictMapEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SendDistrictMapException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.SendDistrictMapVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * TODO(实现ISendDistrictMapService接口，实现增删改查)
 * @author  WeiXing
 * @date 2014-10-21 下午1:33:33
 * @since
 * @version
 */
public class SendDistrictMapService implements ISendDistrictMapService {

	private ISendDistrictMapDao sendDistrictMapDao;
    
	/**
	 * @param sendDistrictMapDao the sendDistrictMapDao to set
	 */
	public void setSendDistrictMapDao(ISendDistrictMapDao sendDistrictMapDao) {
		this.sendDistrictMapDao = sendDistrictMapDao;
	}
	
	/** 
	 * <p> 根据 外场code 分区code active状态 查询 分区（唯一）信息 分页</p> 
	 * @author WeiXing
	 * @date 2014-10-21 下午2:45:46
	 * @param entity
	 * @param limit
	 * @param start
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISendDistrictMapService#queryPackagingSupplierUnique(com.deppon.foss.module.base.baseinfo.api.shared.domain.SendDistrictMapEntity, int, int)
	 */
	@Override
	public List<SendDistrictMapEntity> querySendDistrictMap(
			SendDistrictMapEntity entity, int limit, int start) {
		if (null == entity) {
			entity=new SendDistrictMapEntity();
		} 
		entity.setGoodsType("send");
		entity.setActive(FossConstants.ACTIVE);
			return sendDistrictMapDao.querySendDistrictMap(entity, limit, start);
	}
	/**
	 * 
	* @Title: querySendDistrictMap(提供给中转清仓的接口（过滤掉自提区的）)
	* @Description:根据 外场code 分区code active状态 查询 分区（唯一）信息 
	* @author 189284--zhang xu 
	* @date 2014-11-14 上午10:11:52 
	* @param @param entity
	* @param @return
	* @return List<SendDistrictMapEntity>    返回类型
	 */
	public List<SendDistrictMapEntity> querySendDistrictMap(SendDistrictMapEntity entity) {
		if (null == entity) {
			entity=new SendDistrictMapEntity();
		} 
		entity.setGoodsType("send");
		entity.setActive(FossConstants.ACTIVE);
			return sendDistrictMapDao.querySendDistrictMap(entity);
	}
	
	/** 
	 * <p>统计</p> 
	 * @author WeiXing
	 * @date 2014-10-21 下午3:33:33
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISendDistrictMapService#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SendDistrictMapEntity)
	 */
	@Override
	public Long queryRecordCount(SendDistrictMapEntity entity) {
		if (null == entity) {
			entity=new SendDistrictMapEntity();
		}
		   entity.setActive(FossConstants.ACTIVE);
			return sendDistrictMapDao.queryRecordCount(entity);
	}
	
	/**
	 * <P> 根据 实体条件 分页查询 <P>
	 * 
	 * @author :WeiXing
	 * @date : 2014-10-29下午2:54:26
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public List<SendDistrictMapEntity> querySendDistrictMapList(
			SendDistrictMapEntity entity, int start, int limit) {
		if(null == entity){
			entity =new SendDistrictMapEntity();
		}
		return sendDistrictMapDao.querySendDistrictMapList(entity,
				start, limit);
	}
	
	/**
	 * <P>根据 实体条件 查询总数<P>
	 * 
	 * @author :WeiXing
	 * @date : 2014-10-29下午2:59:09
	 * @param entity
	 * @return
	 */
	@Override
	public long querySendDistrictMapCount(SendDistrictMapEntity entity) {
		return sendDistrictMapDao.querySendDistrictMapCount(entity);
	}
	
	/**
	 * <P>分页查询 <P>
	 * 根据transfer_center_code,goods_area_code,zone_code进行分组查询
	 * @author :WeiXing
	 * @date : 2014-10-29下午2:54:26
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public List<SendDistrictMapEntity> querySendDistrictMapGroupList(
			SendDistrictMapEntity entity, int limit, int start) {
		if(null == entity){
			entity =new SendDistrictMapEntity();
		}
		List<SendDistrictMapEntity> sendDistrictMapEntitys=sendDistrictMapDao.querySendDistrictMapGroupList(entity,
				limit, start);
		
		return sendDistrictMapEntitys;
	}
	
	/**
	 * <P>查询总数<P>
	 * 根据transfer_center_code,goods_area_code,zone_code进行分组查询
	 * @author :WeiXing
	 * @date : 2014-10-29下午2:59:09
	 * @param entity
	 * @return
	 */
	@Override
	public long querySendDistrictMapGroupCount(SendDistrictMapEntity entity) {
		return sendDistrictMapDao.querySendDistrictMapGroupCount(entity);
	}
	
	/**
	 * <P>根据SendDistrictMapEntity查询实体列表 <P>
	 * 
	 * @author :WeiXing
	 * @date : 2014-10-28下午3:00:39
	 * @param entity
	 * @return
	 */
	@Override
	public List<SendDistrictMapEntity> querySendDistrictMapListbyEntity(SendDistrictMapEntity entity) {
		if (entity==null) {
			throw new SendDistrictMapException("实体类为空");
		}
		return sendDistrictMapDao.querySendDistrictMapListbyEntity(entity);
	}
	/**
	 * <P>根据ID批量删除 <P>
	 * 
	 * @author :WeiXing
	 * @date : 2014-11-04下午3:00:39
	 * @param entity
	 * @return
	 */
	@Override
	@Transactional
	public long deleteSendDistrictMapByIds(List<String> ids,String deleteUser) {
		if (ids==null||deleteUser==null) {
			throw new SendDistrictMapException("传入参数为空");
		}
		return sendDistrictMapDao.deleteSendDistrictMapByIds(ids, deleteUser);
	}
	
	/**
	 * 
	* <p>Title: addSendDistrictMap</p> 
	* <p>新增  派送货区行政区域映射基础资料</p> 
	* @author 189284--zhang xu
	* @date 2014-10-23 上午9:23:55 
	* @param sendDistrictMap 派送货区基础资料(外场，驻地库区，分区··)
	* @param sendDistrictMapList 行政区
	* @param sendDistrictItemAreaEntitys 件区
	* @return 
	* 1、外场名称默认为当前登录人所属顶级外场不可编辑
	* 2、库区名称为该顶级外场库区类型为驻地派送库区的库区名称不可编辑
	* 3、分区名称为必填项。分区名称由人工手输，后台生成分区编码唯一
	* 4、一个分区  可对应多个行政区域
	* 5.一个分区多个件区
	* 6.库区类型分为派送区，自提区
	* @see com.deppon.foss.module.base.baseinfo.api.server.service.ISendDistrictMapService#addSendDistrictMap(com.deppon.foss.module.base.baseinfo.api.shared.domain.SendDistrictMapEntity) 
	* SendDistrictMapService
	 */
	@Transactional
	@Override
	public Integer addSendDistrictMap(SendDistrictMapEntity sendDistrictMap,
			List<SendDistrictMapEntity> sendDistrictMapList,
			List<SendDistrictItemAreaEntity> sendDistrictItemAreaEntitys) {
		checkParams( sendDistrictMap, sendDistrictMapList,sendDistrictItemAreaEntitys);	
		String zoneCode=UUIDUtils.getUUID();
		SendDistrictMapEntity myEntity=new SendDistrictMapEntity();
		if(StringUtils.equals(sendDistrictMap.getGoodsType(),"send" )){
			for (int i = 0; i < sendDistrictMapList.size(); i++) {
//				sendDistrictMap.setDistrictCode(sendDistrictMapList.get(i).getDistrictCode());
//				List<SendDistrictMapEntity> sendDistrictMaps=querySendDistrictMapBydistrictCodeOrdistrictName(sendDistrictMap);
				myEntity.setDistrictCode(sendDistrictMapList.get(i).getDistrictCode());
				List<SendDistrictMapEntity> sendDistrictMaps= querySendDistrictMapListbyEntity(myEntity);
				 if(sendDistrictMaps!=null&&sendDistrictMaps.size()>0){    
				    setSendDistrictMapEntity(sendDistrictMap,
							sendDistrictMapList, sendDistrictItemAreaEntitys,
							i, sendDistrictMaps);
				}
			}
			for (int i = 0; i < sendDistrictMapList.size(); i++) {
				sendDistrictMapList.get(i).setId(UUIDUtils.getUUID());
			}
		}else{
			List<SendDistrictMapEntity> sendDistrictMaps=querySendDistrictMapBydistrictCodeOrdistrictName(sendDistrictMap);
			if(sendDistrictMaps!=null&&sendDistrictMaps.size()>0){    
			    for (int j = 0; j < sendDistrictMaps.size(); j++) {
						if(StringUtils.equals(sendDistrictMap.getZoneName(),sendDistrictMaps.get(j).getZoneName())){
							throw new SendDistrictMapException("","已经存在分区名字为："+sendDistrictMap.getZoneName()+"的映射派送货区关系！请勿再添加!");
						}
					}
			}
			sendDistrictMapList = new ArrayList<SendDistrictMapEntity>();
			String id = UUIDUtils.getUUID();
			sendDistrictMap.setId(id);
			sendDistrictMapList.add(sendDistrictMap);
		}
		sendDistrictMap.setCreateDate(new Date());
		sendDistrictMap.setModifyDate(new Date());
		sendDistrictMap.setCreateUser(FossUserContext.getCurrentInfo().getEmpCode());
		sendDistrictMap.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());
		sendDistrictMap.setZoneCode(zoneCode);
		sendDistrictMap.setActive(FossConstants.YES);
		for(int j=0;j<sendDistrictItemAreaEntitys.size();j++){
			sendDistrictItemAreaEntitys.get(j).setZoneCode(zoneCode);
			sendDistrictItemAreaEntitys.get(j).setCreateDate(new Date());
			sendDistrictItemAreaEntitys.get(j).setCreateUser(FossUserContext.getCurrentInfo().getEmpCode());
			sendDistrictItemAreaEntitys.get(j).setId(UUIDUtils.getUUID());
			sendDistrictItemAreaEntitys.get(j).setActive(FossConstants.YES);
			sendDistrictMapDao.addSendDistrictItemArea(sendDistrictItemAreaEntitys.get(j));
		}
		//sendDistrictMapDao.addSendDistrictItemArea(sendDistrictMap, sendDistrictItemAreaEntitys);
		return sendDistrictMapDao.addSendDistrictMap(sendDistrictMap, sendDistrictMapList);
	}

	private void setSendDistrictMapEntity(
			SendDistrictMapEntity sendDistrictMap,
			List<SendDistrictMapEntity> sendDistrictMapList,
			List<SendDistrictItemAreaEntity> sendDistrictItemAreaEntitys,
			int i, List<SendDistrictMapEntity> sendDistrictMaps) {
		for (int j = 0; j < sendDistrictMaps.size(); j++) {
				if(StringUtils.equals(sendDistrictMap.getZoneName(),sendDistrictMaps.get(j).getZoneName())){
					throw new SendDistrictMapException("","已经存在分区名字为："+sendDistrictMap.getZoneName()+"的映射派送货区关系！请勿再添加!");
				}
				if(StringUtils.equals(sendDistrictMapList.get(i).getDistrictCode(),sendDistrictMaps.get(j).getDistrictCode())){
					//行政区域+件数唯一，取消单一映射限制-187862-dujunhui
					//根据行政区域编码查询件数详情
					SendDistrictMapEntity queryEntity=new SendDistrictMapEntity();
					queryEntity.setDistrictCode(sendDistrictMapList.get(i).getDistrictCode());
					List<SendDistrictItemAreaEntity> checkDtos=sendDistrictMapDao.
							queryItemAreaByDistricCode(queryEntity);
				    if(CollectionUtils.isNotEmpty(checkDtos)){
				    	//行政区域+件数唯一
					    if(this.checkScopeInteval(sendDistrictItemAreaEntitys, checkDtos)){
							throw new SendDistrictMapException("新增的行政区域 " + sendDistrictMapList.get(i).getDistrictName()+ " 件数与已有件数存在交叉！不允许添加!");
						}
				    }
					//throw new SendDistrictMapException("已经存在："+sendDistrictMapList.get(i).getDistrictName()+"的映射派送货区关系！请勿再添加!");
				}
			}
	}
	/**
	 * 
	* @Title: checkParams 
	* @Description: 参数检验 
	* @param @param sendDistrictMap 派送货区基础信息（外场，驻地库区，货物类型，分区）
	* @param @param sendDistrictMapList行政区域List
	* @param @param SendDistrictItemAreaEntitys    件区List
	* @return void    返回类型 
	* @author 189284 ZhangXu
	* @date 2015-3-18 上午11:35:20 
	* @throws
	 */
	public void checkParams(SendDistrictMapEntity sendDistrictMap,
			List<SendDistrictMapEntity> sendDistrictMapList,
			List<SendDistrictItemAreaEntity> sendDistrictItemAreaEntitys) {
		//313353 sonar
		this.sonarSplitOne(sendDistrictMap, sendDistrictItemAreaEntitys);
		/**
		 * 外场不存在
		 */
		if(StringUtils.isBlank(sendDistrictMap.getTransferCenterCode())){
			throw new SendDistrictMapException(SendDistrictMapException.SENDDISTRICTMAP_TRANSFERCENTERCODE_NULL);
		}
		/**
		 * 驻地派送库区不存在
		 */
		if(StringUtils.isBlank(sendDistrictMap.getGoodsAreaCode())){
			throw new SendDistrictMapException(SendDistrictMapException.SENDDISTRICTMAP_GOODSAREACODE_NULL);
		}
		/**
		 * 分区不存在
		 */
		if(StringUtils.isBlank(sendDistrictMap.getZoneName())){
			throw new SendDistrictMapException(SendDistrictMapException.SENDDISTRICTMAP_ZONENAME_NULL);
		}
		
		/**
		 * 如果
		 * 1.货区类型为派送区（即goodsType=‘send’）的时候，行政区域必填，
		 * 2.货区类型为自提区（即goodsType=‘personally’）的时候不需要填行政区域
		 */
		if(StringUtils.equals(sendDistrictMap.getGoodsType(),"send")){
			if(sendDistrictMapList==null||sendDistrictMapList.size()<1){
				throw new SendDistrictMapException(SendDistrictMapException.SENDDISTRICTMAP_DISTRICTCODE_NULL);
			}
			for (int i = 0; i < sendDistrictMapList.size(); i++) {
				if(StringUtils.isBlank(sendDistrictMapList.get(i).getDistrictCode())){
					throw new SendDistrictMapException(SendDistrictMapException.SENDDISTRICTMAP_DISTRICTCODE_NULL);
				}
			}
		}
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitOne(SendDistrictMapEntity sendDistrictMap,
			List<SendDistrictItemAreaEntity> sendDistrictItemAreaEntitys) {
		if(sendDistrictMap==null){
			throw new SendDistrictMapException(SendDistrictMapException.SENDDISTRICTMAP_PARMS_NULL);
		}
		/**
		 * 检查件区信息
		 */
		if(sendDistrictItemAreaEntitys==null||sendDistrictItemAreaEntitys.size()<1){
			throw new SendDistrictMapException(SendDistrictMapException.SENDDISTRICTMAP_ITEMAREA_PARMS_NULL);
		}
		/**
		 * 货区类型不存在
		 */
		if(StringUtils.isBlank(sendDistrictMap.getGoodsType())){
			throw new SendDistrictMapException(SendDistrictMapException.SENDDISTRICTMAP_GOODSTYPE_NULL);
		}
	}
	
	/**
	 * 
	* @Title: querySendDistrictMapByAreaCodeOrZoneName
    * @Description:根据分区名称 或者行政区域Code查询 派送货区行政区域映射基础资料 信息
	* sendDistrictMapVo.sendDistrictMap实体
	*zoneName分区名称
	*areaCode分区Code	
	* @author 189284--zhang xu 
	* @date 2014-10-30 上午10:59:35 
	* @param @param sendDistrictMap
	* @param @return
	* @return SendDistrictMapEntity    返回类型
	 */
	@Override
	public List<SendDistrictMapEntity> querySendDistrictMapBydistrictCodeOrdistrictName(
			SendDistrictMapEntity sendDistrictMap) {
		if(StringUtils.isBlank(sendDistrictMap.getZoneName())){
			throw new SendDistrictMapException(SendDistrictMapException.SENDDISTRICTMAP_PARMS_ERROR);
		}
		if(StringUtils.equals(sendDistrictMap.getGoodsType(),"send")){
			if(StringUtils.isBlank(sendDistrictMap.getDistrictCode())){
				throw new SendDistrictMapException(SendDistrictMapException.SENDDISTRICTMAP_DISTRICTCODE_NULL);
			}
		}
		if(StringUtils.isBlank(sendDistrictMap.getTransferCenterCode())){
			throw new SendDistrictMapException(SendDistrictMapException.SENDDISTRICTMAP_PARMS_NULL);
		}
		return sendDistrictMapDao.querySendDistrictMapBydistrictCodeOrdistrictName(sendDistrictMap);
	}
	/**
	 * 
	* @Title: deleteZoneName
	* @Description: 根据条件作废  派送货区行政区域映射基础资料
	* @author 189284--zhang xu 
	* @date 2014-11-3 下午6:18:25 
	* @param @return
	* @return String    返回类型
	 */
	@Override
	@Transactional
	public void deleteByZoneName(SendDistrictMapEntity sendDistrictMap) {
		if(StringUtils.isBlank(sendDistrictMap.getZoneCode())){
			throw new SendDistrictMapException(SendDistrictMapException.SENDDISTRICTMAP_ZONENAME_NULL);
		}
		sendDistrictMapDao.deleteByZoneName(sendDistrictMap);
		SendDistrictItemAreaEntity sendDistrictItemAreaEntity=new SendDistrictItemAreaEntity();
		sendDistrictItemAreaEntity.setZoneCode(sendDistrictMap.getZoneCode());
		sendDistrictMapDao.updateItemAreaByIdOrZoneCode(sendDistrictItemAreaEntity);
		
	}
	/**
	 * 
	* @Title: deleteSendDistrictMapList
	* @Description: 作废多条  派送货区行政区域映射基础资料
	* @author 189284--zhang xu 
	* @date 2014-11-4 上午10:02:00 
	* @param @param sendDistrictMapList
	* @return void    返回类型
	 */
	@Transactional
	public void deleteSendDistrictMapList(List<SendDistrictMapEntity> sendDistrictMapList){
		if(sendDistrictMapList==null||sendDistrictMapList.size()<1){
			throw new SendDistrictMapException(SendDistrictMapException.SENDDISTRICTMAP_PARMS_NULL);
		}
		for (int j = 0; j < sendDistrictMapList.size(); j++) {
			if(StringUtils.isBlank(sendDistrictMapList.get(j).getZoneCode())){
				throw new SendDistrictMapException(SendDistrictMapException.SENDDISTRICTMAP_PARMS_NULL);
			}
			sendDistrictMapDao.deleteByZoneName(sendDistrictMapList.get(j));
		}
		
	}
	
	/**
	 * 
	* @Title: deleteAddSendDistrictMapList
	* @Description: 作废和新增多条派送货区行政区域映射基础资料
	* @author WeiXing
	* @date 2014-11-4 上午10:02:00 
	* @param @param SendDistrictMapVo
	* @return void    返回类型
	 */
	@Transactional
	public void deleteAddSendDistrictMapList(SendDistrictMapVo sendDistrictMapVo){
		//行政区域+件数唯一，取消单一映射限制-187862-dujunhui
		//313353 sonar
		this.sonarSplitTwo(sendDistrictMapVo);
	    
		//获取要作废的数据的ID
		List<String> deleteIds=sendDistrictMapVo.getDeleteids();
		//作废
		if(deleteIds!=null&&deleteIds.size()>0){
			sendDistrictMapDao.deleteSendDistrictMapByIds(deleteIds, FossUserContext.getCurrentInfo().getEmpCode());
		}
		
		
		SendDistrictMapEntity sendDistrictMapEntity=sendDistrictMapVo.getSendDistrictMapEntity();
		List<SendDistrictMapEntity>sendDistrictMapEntities=sendDistrictMapVo.getSendDistrictMapEntities();
		if(StringUtils.equals(sendDistrictMapEntity.getGoodsType(), "send")){
			//新增
			SendDistrictMapEntity halfEntity=sendDistrictMapVo.getSendDistrictMapEntity(); 
			halfEntity.setCreateDate(new Date());
			halfEntity.setModifyDate(new Date());
			halfEntity.setCreateUser(FossUserContext.getCurrentInfo().getEmpCode());
			halfEntity.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());
			halfEntity.setActive(FossConstants.YES);
			
			List<SendDistrictMapEntity> halfEntities= sendDistrictMapVo.getSendDistrictMapEntities();
			for(int y=0;y<halfEntities.size();y++){
				SendDistrictMapEntity tmp=halfEntities.get(y);
				if(StringUtil.isNotEmpty(tmp.getId())){
					halfEntities.remove(tmp);
					y--;
				}else{
					tmp.setId(UUIDUtils.getUUID());
				}
			}
			if(sendDistrictMapEntities!=null&&sendDistrictMapEntities.size()>0){
				//checkParams( sendDistrictMapEntity, sendDistrictMapEntities);
				sendDistrictMapDao.addSendDistrictMap(sendDistrictMapEntity, sendDistrictMapEntities);
			}
		}
		//件区list
		List<SendDistrictItemAreaEntity> sendDistrictItemAreaEntitys=sendDistrictMapVo.getSendDistrictItemAreaEntitys();
		//删除  件区list
		List<String> delteItemAreas=sendDistrictMapVo.getDelteItemAreas();
		for (int i = 0; i < sendDistrictItemAreaEntitys.size(); i++) {
		   if(StringUtils.isBlank(sendDistrictItemAreaEntitys.get(i).getId())){
			   sendDistrictItemAreaEntitys.get(i).setId(UUIDUtils.getUUID());
			   sendDistrictItemAreaEntitys.get(i).setZoneCode(sendDistrictMapEntity.getZoneCode());
			   sendDistrictItemAreaEntitys.get(i).setCreateDate(new Date());
			   sendDistrictItemAreaEntitys.get(i).setCreateUser(FossUserContext.getCurrentInfo().getEmpCode());
			   sendDistrictMapDao.addSendDistrictItemArea(sendDistrictItemAreaEntitys.get(i));
		   }else{
			   if(StringUtils.equals(sendDistrictItemAreaEntitys.get(i).getActionType(),"update")){
				   if(StringUtils.isBlank(sendDistrictItemAreaEntitys.get(i).getId())){
					   throw new SendDistrictMapException(SendDistrictMapException.SENDDISTRICTMAP_PARMS_NULL);
				   }
				   List<SendDistrictItemAreaEntity> sendDistrictItemAreaList=sendDistrictMapDao.queryItemAreaByIdOrZoneCode(sendDistrictItemAreaEntitys.get(i).getId(), null);
				   sendDistrictMapDao.updateItemAreaByIdOrZoneCode(sendDistrictItemAreaEntitys.get(i));
				   if(sendDistrictItemAreaList!=null&&sendDistrictItemAreaList.size()>0){
					   sendDistrictItemAreaEntitys.get(i).setCreateDate(sendDistrictItemAreaList.get(0).getCreateDate());
					   sendDistrictItemAreaEntitys.get(i).setCreateUser(sendDistrictItemAreaList.get(0).getCreateUser());
				   }
				   sendDistrictItemAreaEntitys.get(i).setZoneCode(sendDistrictMapEntity.getZoneCode());
				   sendDistrictMapDao.addSendDistrictItemArea(sendDistrictItemAreaEntitys.get(i));
				}
		   }
		}
		if(delteItemAreas!=null && delteItemAreas.size()>0){
			sendDistrictMapDao.deleteSendDistrictItemAreaByIds(delteItemAreas);
		}
				
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitTwo(SendDistrictMapVo sendDistrictMapVo) {
		if(StringUtils.equals(sendDistrictMapVo.getSendDistrictMapEntity().getGoodsType(), "send")){//派送货区
			for(SendDistrictMapEntity districtMap:sendDistrictMapVo.getSendDistrictMapEntities()){
				SendDistrictMapEntity queryEntity = new SendDistrictMapEntity();
				queryEntity.setDistrictCode(districtMap.getDistrictCode());
				//根据行政区域编码查询件数详情
				List<SendDistrictItemAreaEntity> checkDtos=sendDistrictMapDao.
						queryItemAreaByDistricCode(queryEntity);
			    if(CollectionUtils.isNotEmpty(checkDtos)){
		    		//行政区域+件数唯一
				    if(this.checkScopeInteval(sendDistrictMapVo.getSendDistrictItemAreaEntitys(), checkDtos)){
						throw new SendDistrictMapException("修改后的行政区域 "+ districtMap.getDistrictName() + " 件数与已有件数存在交叉！不允许添加!");
					}
			    }
			}
		}
	}
	
	/**
	 * 
	* @Title: queryItemAreaByIdOrZoneCode 
	* @Description:根据zoneCode分区 查询件区信息
	* @param @param id 主键id
	* @param @param zoneCode分区Code
	* @param @return    设定文件 
	* @return List<SendDistrictItemAreaEntity>    返回类型 
	* @author 189284 ZhangXu
	* @date 2015-3-19 下午3:17:52 
	* @throws
	 */
	@Override
	public List<SendDistrictItemAreaEntity> queryItemAreaByIdOrZoneCode(String id,
			String zoneCode){		
		return sendDistrictMapDao.queryItemAreaByIdOrZoneCode(id,zoneCode);
	}
	
	/**
	 * @Description: 检查件数范围交集（返回false则不交叉，返回true则交叉）
	 * @author dujunhui-187862
	 * @date 2015-7-16 上午9:40:29
	 * @param checkDto
	 * @return
	 * @version V1.0
	 */
	private boolean checkScopeInteval(List<SendDistrictItemAreaEntity> dtos, List<SendDistrictItemAreaEntity> checkDtos) {
		boolean flag = false;//默认件数不存在交叉，标识位为false
		for(SendDistrictItemAreaEntity dto:dtos){
			for(SendDistrictItemAreaEntity checkDto:checkDtos){
				
				boolean flag1=true,flag2=true;//两种不交叉情况的标识符，默认为交叉
				
				if(StringUtil.equals(checkDto.getId(), dto.getId())){//先排除本条正在修改的数据
					continue;
				}
				
				if(Integer.parseInt(checkDto.getScopeEnd()) < Integer.parseInt(dto.getScopeStart())) {
					//当前件区dto范围起点在对比件区checkDto范围终点之后，则件数不交叉
					flag1 = false;
				}
				if(Integer.parseInt(dto.getScopeEnd()) < Integer.parseInt(checkDto.getScopeStart())) {
					//当前件区dto范围终点在对比件区checkDto范围起点之前，则件数不交叉
					flag2 = false;
				}
				if(flag1 && flag2){//flag1、flag2都不为false，则件数交叉
					flag=true;
					break;//存在交叉，则跳出循环(内循环)
				}
			}
			if(flag==true){//存在交叉，则跳出循环(外循环)
				break;
			}
		}
		return flag;
	}

}
