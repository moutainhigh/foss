package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISendDistrictMapDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SendDistrictItemAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SendDistrictMapEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * TODO(实现ISendDistrictMapDao接口)
 * @author WeiXing
 * @date 2014-10-21 下午1:09:05
 * @since
 * @version
 */
public class SendDistrictMapDao extends SqlSessionDaoSupport implements ISendDistrictMapDao {

	private static final String NAMESPACE = "foss.bse.bse-baseinfo.sendDistrictMap.";
	private static final String NAMESPACE_ITEMAREA="foss.bse.bse-baseinfo.sendDistrictItemArea.";
	/** 
	 * <p> 根据 外场code 分区code 状态 查询 分区（唯一）信息 分页 </p> 
	 * @author WeiXing
	 * @date 2014-10-21 下午3:42:14
	 * @param entity
	 * @param limit
	 * @param start
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISendDistrictMapDao#querySendDistrictMap(com.deppon.foss.module.base.baseinfo.api.shared.domain.SendDistrictMapEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<SendDistrictMapEntity> querySendDistrictMap(SendDistrictMapEntity entity, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "selectUnique",entity, rowBounds);
	}
	/**
	 * 
	* @Title: querySendDistrictMap
	* @Description: 根据 外场code 分区code 状态 查询 分区（唯一）信息
	* @author 189284--zhang xu 
	* @date 2014-11-14 上午10:10:00 
	* @param @param entity
	* @param @return
	* @return List<SendDistrictMapEntity>    返回类型
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SendDistrictMapEntity> querySendDistrictMap(SendDistrictMapEntity entity) {
		return this.getSqlSession().selectList(NAMESPACE + "selectUnique",entity);
	}
	
	
	/** 
	 * <p>TODO(统计)</p> 
	 * @author WeiXing
	 * @date 2014-10-21 下午3:09:05
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISendDistrictMapDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SendDistrictMapEntity)
	 */
	@Override
	public Long queryRecordCount(SendDistrictMapEntity entity) {
		// TODO Auto-generated method stub
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",entity);
	}
	
	/** 
	 *<P>根据条件分页查询<P>
	 * @author :WeiXing
	 * @date : 2014-3-25下午4:48:37
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SendDistrictMapEntity> querySendDistrictMapList(
			SendDistrictMapEntity entity,int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+"querySendDistrictMapList",entity,rowBounds);
	}
	
	/**
	 *<P>查询总数<P>
	 * @author :WeiXIng
	 * @date : 2014-3-26上午11:22:06
	 * @param entity
	 * @return
	 */
	@Override
	public long querySendDistrictMapCount(SendDistrictMapEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		return (Long) getSqlSession().selectOne(NAMESPACE+"querySendDistrictMapCount",entity);
	}
	
	/** 
	 *<P>根据条件分页查询<P>
	 *根据transfer_center_code,goods_area_code,zone_code进行分组查询
	 * @author :WeiXing
	 * @date : 2014-3-25下午4:48:37
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SendDistrictMapEntity> querySendDistrictMapGroupList(
			SendDistrictMapEntity entity,int limit, int start ) {
		RowBounds rowBounds = new RowBounds(start, limit);
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+"querySendDistrictMapGroupList",entity,rowBounds);
	}
	
	/**
	 *<P>查询总数<P>
	 *根据transfer_center_code,goods_area_code,zone_code进行分组查询
	 * @author :WeiXIng
	 * @date : 2014-3-26上午11:22:06
	 * @param entity
	 * @return
	 */
	@Override
	public long querySendDistrictMapGroupCount(SendDistrictMapEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		return (Long) getSqlSession().selectOne(NAMESPACE+"querySendDistrictMapGroupCount",entity);
	}
	
	/**
	 *<P>根据SendDistrictMapEntity查询<P>
	 * @author :WeiXing
	 * @date : 2014-10-29上午11:11:29
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SendDistrictMapEntity> querySendDistrictMapListbyEntity(SendDistrictMapEntity entity) {
		if(entity==null){
			return null;
		}
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+"querySendDistrictMapList", entity);
	}
	
	/**
	 *<P>根据ID批量删除<P>
	 * @author :WeiXing
	 * @date : 2014-11-04上午11:11:29
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public long deleteSendDistrictMapByIds(List<String> ids,String deleteUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deleteids", ids);
		SendDistrictMapEntity entity=new SendDistrictMapEntity();
		entity.setModifyDate(new Date());
		entity.setModifyUser(deleteUser);
		entity.setActive(FossConstants.INACTIVE);
		map.put("entity", entity);
		map.put("conditionActive", FossConstants.ACTIVE);
		return this.getSqlSession().update(NAMESPACE+"deleteSendDistrictMapByIds",map);
	}
	/**
	 * 
	* @Title: deleteSendDistrictItemAreaByIds 
	* @Description:批量作废 件区信息
	* @param @param delteItemAreas 作废件区 Ids
	* @param @return    设定文件 
	* @return long    返回类型 
	* @author 189284 ZhangXu
	* @date 2015-3-21 下午2:05:50 
	* @throws
	 */
	@Override
	public long deleteSendDistrictItemAreaByIds(List<String> delteItemAreas) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delteItemAreas", delteItemAreas);
		SendDistrictMapEntity entity=new SendDistrictMapEntity();
		entity.setModifyDate(new Date());
		entity.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());
		entity.setActive(FossConstants.INACTIVE);
		map.put("entity", entity);
		map.put("conditionActive", FossConstants.ACTIVE);
		return this.getSqlSession().update(NAMESPACE_ITEMAREA+"deleteSendDistrictItemAreaByIds",map);
	}
	 /**
	  * 
	 * <p>Title: addSendDistrictMap</p> 
	 * <p>Description: </p> 
	 * @author 189284--zhang xu
	 * @date 2014-10-22 下午5:19:14 
	 * @param sendDistrictMap 
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISendDistrictMapDao#addSendDistrictMap(com.deppon.foss.module.base.baseinfo.api.shared.domain.SendDistrictMapEntity) 
	  */
	@Override
	 public Integer addSendDistrictMap(SendDistrictMapEntity sendDistrictMap,List<SendDistrictMapEntity> sendDistrictMapList) {
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("sendDistrictMap", sendDistrictMap);
		 map.put("sendDistrictMapList", sendDistrictMapList);
		 int result = getSqlSession().insert(
					NAMESPACE + "addSendDistrictMap", map);
				return result;
	}
	/**
	 * 
	* @Title: addSendDistrictItemArea 
	* @Description: 批量新增件区信息 
	* @param @param sendDistrictMap
	* @param @param sendDistrictItemAreaList件区list
	* @return Integer    返回类型 
	* @author 189284 ZhangXu
	* @date 2015-3-18 上午11:32:38 
	* @throws
	 */
	@Override
	 public Integer addSendDistrictItemArea(SendDistrictMapEntity sendDistrictMap,List<SendDistrictItemAreaEntity> sendDistrictItemAreaList) {
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("sendDistrictMap", sendDistrictMap);
		 map.put("sendDistrictItemAreaList", sendDistrictItemAreaList);
		 int result = getSqlSession().insert(
				 NAMESPACE_ITEMAREA + "addSendDistrictItemArea", map);
				return result;
	}
	 public Integer addSendDistrictItemArea(SendDistrictItemAreaEntity sendDistrictItemAreaEntity) {
		 //Map<String, Object> map = new HashMap<String, Object>();
		// map.put("sendDistrictItemAreaEntity", sendDistrictItemAreaEntity);
		// map.put("sendDistrictItemAreaList", sendDistrictItemAreaList);
		 sendDistrictItemAreaEntity.setId(UUIDUtils.getUUID());
		 sendDistrictItemAreaEntity.setActive( FossConstants.ACTIVE);
		 sendDistrictItemAreaEntity.setModifyDate(new Date());		 
		 sendDistrictItemAreaEntity.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());		 
		 double volume;
			//计算体积
			volume = Double.parseDouble(sendDistrictItemAreaEntity.getWidth())*Double.parseDouble(sendDistrictItemAreaEntity.getHeight())*Double.parseDouble(sendDistrictItemAreaEntity.getLength());
			sendDistrictItemAreaEntity.setVolume(volume+"");
		 int result = getSqlSession().insert(
				 NAMESPACE_ITEMAREA + "insert", sendDistrictItemAreaEntity);
				return result;
	}
	public void updateSendDistrictMap(SendDistrictMapEntity sendDistrictMap) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 
	* @Title: querySendDistrictMapByAreaCodeOrZoneName
   * @Description:根据分区名称 或者分区Code查询 派送货区行政区域映射基础资料 信息
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
 public  List<SendDistrictMapEntity> querySendDistrictMapBydistrictCodeOrdistrictName(
			SendDistrictMapEntity sendDistrictMap) {
		return getSqlSession().selectList(NAMESPACE + "querySendDistrictMapByAreaCodeOrZoneName", sendDistrictMap);				   
  }/**
	 * 
	* @Title: deleteZoneName
	* @Description: 根据条件作废  派送货区行政区域映射基础资料
	* @author 189284--zhang xu 
	* @date 2014-11-3 下午6:18:25 
	* @param @return
	* @return String    返回类型
	 */
	@Override
	public void deleteByZoneName(SendDistrictMapEntity sendDistrictMap){
		 getSqlSession().update(NAMESPACE + "deleteZoneName", sendDistrictMap);	
	}
	  /**
	    * 
	   * @Title: queryItemAreaByIdOrZoneCode 
	   * @Description: 根据zoneCode分区 查询件区信息
	   * @param @param id 主键ID
	   * @param @param zoneCode分区Code
	   * @param @return    设定文件 
	   * @return List<SendDistrictItemAreaEntity>    返回类型 
	   * @author 189284 ZhangXu
	   * @date 2015-3-19 下午3:21:04 
	   * @throws
	    */
	@Override
	public List<SendDistrictItemAreaEntity> queryItemAreaByIdOrZoneCode(String id,
			String zoneCode){
		SendDistrictItemAreaEntity sendDistrictItemAreaEntity =new SendDistrictItemAreaEntity();
		sendDistrictItemAreaEntity.setId(id);
		sendDistrictItemAreaEntity.setZoneCode(zoneCode);
		sendDistrictItemAreaEntity.setActive(FossConstants.ACTIVE);
		return getSqlSession().selectList(NAMESPACE_ITEMAREA+"qureySendDistrictItemAreaByIdOrZoneCode", sendDistrictItemAreaEntity);
	}
	/**
	 * 
	* @Title: updateItemAreaById 
	* @Description:根据ID or zoneCode修改 件区信息(作废)
	* @param @param sendDistrictItemAreaEntity
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @author 189284 ZhangXu
	* @date 2015-3-21 上午11:41:20 
	* @throws
	 */
	@Override
	public Integer updateItemAreaByIdOrZoneCode(SendDistrictItemAreaEntity sendDistrictItemAreaEntity){
		sendDistrictItemAreaEntity.setActive(FossConstants.INACTIVE);
		sendDistrictItemAreaEntity.setModifyDate(new Date());
		sendDistrictItemAreaEntity.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());
		return getSqlSession().update(NAMESPACE_ITEMAREA+"update", sendDistrictItemAreaEntity);
	}
	
	/**
	 * 
	* @Title: queryItemAreaByDistricCode 
	* @Description: 根据行政区域编码查询件区信息
	* @param queryEntity
	* @return List<SendDistrictItemAreaEntity>
	* @author 187862-dujunhui
	* @date 2015-7-17 下午2:11:35 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SendDistrictItemAreaEntity> queryItemAreaByDistricCode(
			SendDistrictMapEntity queryEntity) {
		queryEntity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE_ITEMAREA + "selectItemAreaByDistrictCode", queryEntity);
	}
	
}
