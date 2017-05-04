package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICourierScheduleDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CourierScheduleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CourierScheduleReportEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
 *<p>Title: CourierScheduleDao</p>
 * <p>Description: 快递员排班Dao的实现类</p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-4-16
 */
public class CourierScheduleDao extends SqlSessionDaoSupport implements
		ICourierScheduleDao {
	/**
	 *命名空间
	 */
	private final static String NAMESPACE =ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".courierSchedule.";
	/**
	 *<p>Title: addCourierSchedule</p>
	 *<p>新增快递员排班信息</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-16下午4:56:42
	 * @param courierScheduleEntity
	 * @return
	 */
	@Override
	public CourierScheduleEntity addCourierSchedule(
			CourierScheduleEntity courierScheduleEntity) {
		courierScheduleEntity.setId(UUIDUtils.getUUID());
		//修改时间，创建时间
		courierScheduleEntity.setCreateDate(new Date());
		courierScheduleEntity.setModifyDate(new Date(NumberConstants.ENDTIME));
		courierScheduleEntity.setModifyUser(courierScheduleEntity.getCreateUser());
		//版本号
		courierScheduleEntity.setVersionNo(new Date().getTime());
		//设置为有效的
		courierScheduleEntity.setActive(FossConstants.ACTIVE);
		int result =this.getSqlSession().insert(NAMESPACE+"addCourierSchedule",courierScheduleEntity);
		return result == NumberConstants.ZERO?null:courierScheduleEntity;
	}
	/**
	 *<p>Title: deleteCourierSchedule</p>
	 *<p>作废快递员排班</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-16下午5:03:26
	 * @param courierScheduleEntity
	 * @return
	 */
	@Override
	public CourierScheduleEntity deleteCourierSchedule(
			CourierScheduleEntity courierScheduleEntity) {
		Map<String,Object> map =new HashMap<String, Object>();
		
		courierScheduleEntity.setActive(FossConstants.INACTIVE);
		courierScheduleEntity.setModifyDate(new Date());
		courierScheduleEntity.setVersionNo(new Date().getTime());
		map.put("entity", courierScheduleEntity);
		map.put("conditionActive", FossConstants.ACTIVE);
		//执行作废操作
		int result =getSqlSession().update(NAMESPACE+"deleteCourierSchedule", map);
		return result==NumberConstants.ZERO?null:courierScheduleEntity;
	}
	/**
	 *<p>Title: updateCourierSchedule</p>
	 *<p>修改快递员排班</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-16下午5:20:46
	 * @param courierScheduleEntity
	 * @return
	 */
	@Override
	public CourierScheduleEntity updateCourierSchedule(
			CourierScheduleEntity courierScheduleEntity) {
		//作废当前对象
		CourierScheduleEntity result=this.deleteCourierSchedule(courierScheduleEntity);
		if(null==result){
			return null;
		}
		courierScheduleEntity.setId(UUIDUtils.getUUID());
		courierScheduleEntity.setActive(FossConstants.ACTIVE);
		courierScheduleEntity.setCreateUser(courierScheduleEntity.getModifyUser());
		courierScheduleEntity.setCreateDate(new Date());
		courierScheduleEntity.setVersionNo(new Date().getTime());
		//执行新增操作
		int resultNum =this.getSqlSession().insert(NAMESPACE+"addCourierSchedule",courierScheduleEntity);
		return resultNum == NumberConstants.ZERO?null:courierScheduleEntity;
	}
	/**
	 *<p>Title: queryCourierScheduleList</p>
	 *<p>动态查询</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-17下午2:21:51
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<CourierScheduleEntity> queryCourierScheduleList(
			CourierScheduleEntity entity, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+"queryCourierScheduleList",entity,rowBounds);
	}
	/**
	 *<p>Title: queryCount</p>
	 *<p>查询总数</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-17下午2:27:55
	 * @param entity
	 * @return
	*/
	@Override
	public long queryCount(CourierScheduleEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		return (Long) getSqlSession().selectOne(NAMESPACE+"queryCount",entity);
	}
	/**
	 *<p>Title: queryCourierScheduleListByEntity</p>
	 *<p>通过小区编码和排单日期、快递员属性查询</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-18下午1:42:17
	 * @param entity
	 * @return
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<CourierScheduleEntity> queryCourierScheduleListByEntity(
			CourierScheduleEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+"queryCourierScheduleListByEntity",entity);
	}
	/**
	 *<p>Title: queryCountByIFloor</p>
	 *<p>根据小区，排班时间，楼层查询重叠排班情况</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-18下午3:58:43
	 * @param entity
	 * @return
	*/
	@Override
	public long queryCountByIFloor(CourierScheduleEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		return (Long) getSqlSession().selectOne(NAMESPACE+"queryCountByIFloor",entity);
	}
	/**
	 *<p>Title: queryCourierScheduleById</p>
	 *<p>根据id查询/p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-18下午6:09:17
	 * @param entity
	 * @return
	*/
	@SuppressWarnings("unchecked")
	@Override
	public CourierScheduleEntity queryCourierScheduleById(
			CourierScheduleEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		List<CourierScheduleEntity> resultList=getSqlSession().selectList(NAMESPACE+"queryCourierScheduleById",entity);
		if(CollectionUtils.isEmpty(resultList)){
			return null;
		}
		return resultList.get(0);
	}
	/**
	 *<p>Title: queryCourierScheduleListByOther</p>
	 *<p>根据条件动态查询</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-24下午6:45:16
	 * @param entity
	 * @return
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<CourierScheduleEntity> queryCourierScheduleListByOther(
			CourierScheduleEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+"queryCourierScheduleListByOther",entity);
	}
	/**
	 *<p>Title: queryCourierScheduleReportList</p>
	 *<p>报表式查看</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-28下午7:18:29
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<CourierScheduleReportEntity> queryCourierScheduleReportList(
			CourierScheduleEntity entity, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE+"queryCourierScheduleReportList",entity,rowBounds);
	}
	/**
	 *<p>Title: queryReportListCount</p>
	 *<p>报表式查看计算count</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-29下午1:36:20
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICourierScheduleDao#queryReportListCount()
	 * @return
	*/
	@Override
	public long queryReportListCount(CourierScheduleEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"queryReportListCount", entity);
	}
	/**
	 *<p>Title: queryCourierScheduleByCondition</p>
	 *<p>根据收派小区编码和楼层查询排班集合</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午6:44:56
	 * @param courierSchedule
	 * @param intFloor
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CourierScheduleEntity> queryCourierScheduleByCondition(
			CourierScheduleEntity courierSchedule, int intFloor) {
		courierSchedule.setActive(FossConstants.ACTIVE);
		courierSchedule.setPlanType(DictionaryValueConstants.PLAN_TYPE_WORK);
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("entity", courierSchedule);
		map.put("floor", intFloor);
		return this.getSqlSession().selectList(NAMESPACE+"queryCourierScheduleByCondition", map);
	}
	/**
	 *<p>Title: queryCountByIFloor</p>
	 *<p>根据小区，排班时间，楼层查询重叠排班情况,(不包括本身entity)</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-4-18下午3:58:43
	 * @param entity
	 * @return
	*/
	@Override
	public long queryCountByFloorNotIncludeEntity(CourierScheduleEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		return (Long) getSqlSession().selectOne(NAMESPACE+"queryCountByFloorNotIncludeEntity",entity);
	}
	/**
	 * <p>根据小区编码,小区名称作废相关的小区编码的排班信息</p>
	 * @param expressSmallZoneCode
	 * @return
	 */
	@Override
	public int deleteCourierScheduleByExpressSmallZoneCode(
			String[] expressSmallZoneCodes) {
		CourierScheduleEntity entity =new CourierScheduleEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setVersionNo(new Date().getTime());
		entity.setModifyUser("system");
		entity.setModifyDate(new Date());
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("entity", entity);
		map.put("conditionActive", FossConstants.INACTIVE);
		map.put("expressSmallZoneCodes", expressSmallZoneCodes);
		return this.getSqlSession().update(NAMESPACE+"deleteCourierScheduleByExpressSmallZoneCode", map);
	}
	/**
	 * 根据小区编码,小区名称修改相关的小区编码，小区名称排班信息
	 * @param newCourierSchedule
	 * @param oldRegionsCode
	 * @param oldRegionsName
	 * @return
	 */
	@Override
	public int updateCourierScheduleByCondition(
			CourierScheduleEntity newCourierSchedule, String oldRegionsCode,
			String oldRegionsName) {
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("newEntity", newCourierSchedule);
		map.put("oldRegionsName", oldRegionsName);
		map.put("oldRegionsCode", oldRegionsCode);
		map.put("active", FossConstants.ACTIVE);
		return getSqlSession().update(NAMESPACE+"updateCourierScheduleByCondition", map);
	}
}
