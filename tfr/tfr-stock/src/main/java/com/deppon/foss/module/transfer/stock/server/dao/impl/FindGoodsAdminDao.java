package com.deppon.foss.module.transfer.stock.server.dao.impl;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.stock.api.server.dao.IFindGoodsAdminDao;
import com.deppon.foss.module.transfer.stock.api.shared.domain.FindGoodsAdminDetailEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.FindGoodsAdminEntity;
import com.deppon.foss.util.UUIDUtils;
/**
 * 
* @ClassName: FindGoodsAdminDao
* @Description:找货管理 信息 Dao
* @author 189284--ZX
* @date 2015-7-11 上午9:19:38
*
 */
public class FindGoodsAdminDao extends iBatis3DaoImpl implements IFindGoodsAdminDao {
 private static final String NAMESPACE="tfr.stock.findGoodsAdmin.";
 /**
  * 
 * @Title: queryFindGoodsAdmin
 * @Description: 分页查询 找货管理信息
 * @author 189284--ZX
 * @param @param findGoodsAdminEntity
 * @param @param limit
 * @param @param start
 * @param @return    设定文件
 * @return List<FindGoodsAdminEntity>    返回类型
 * @throws
  */
 @SuppressWarnings("unchecked")
 @Override
 public List<FindGoodsAdminEntity> queryFindGoodsAdmin(FindGoodsAdminEntity findGoodsAdminEntity, int start, int limit){
	 RowBounds rowBpunds= new RowBounds(start, limit);
	 return this.getSqlSession().selectList(NAMESPACE+"findGoodsAdmin_qurey",findGoodsAdminEntity,rowBpunds);
 }
 /**
  * 
 * @Title: queryFindGoodsAdminQueryCount
 * @Description: 查询 找货管理信息 总数
 * @author 189284--ZX
 * @param @param findGoodsAdminEntity
 * @param @return    设定文件
 * @return Long    返回类型
 * @throws
  */
 @Override
 public Long queryFindGoodsAdminQueryCount(FindGoodsAdminEntity findGoodsAdminEntity){
	 return (Long) this.getSqlSession().selectOne(NAMESPACE+"findGoodsAdmin_qurey_count",findGoodsAdminEntity);
 }
 /**
  * 
 * @Title: queryFindGoodsAdminDetail
 * @Description: 根据 运单 流水 查询找货管理明细
 * @author 189284--ZX
 * @param  findGoodsAdminDetailEntity.waybillNo 运单号
 * @param  findGoodsAdminDetailEntity.serialNo 流水号
 * @param @return    设定文件
 * @return List<FindGoodsAdminDetailEntity>    返回类型
 * @throws
  */
 @SuppressWarnings("unchecked")
 @Override
public List<FindGoodsAdminDetailEntity> queryFindGoodsAdminDetail(FindGoodsAdminDetailEntity findGoodsAdminDetailEntity){
	return this.getSqlSession().selectList(NAMESPACE+"findGoodsAdminDetail_qurey", findGoodsAdminDetailEntity); 
 }
 /**
  * 
 * @Title: commitFindGoodsAdmin
 * @Description: 根据 任务编号 提交找货管理信息
 * @author 189284--ZX
 * @param @param findGoodsAdminEntity    设定文件
 * @return void    返回类型
 * @throws
  */
 @Override
 @Transactional
 public void commitFindGoodsAdmin(FindGoodsAdminEntity findGoodsAdminEntity){
	  this.getSqlSession().update(NAMESPACE+"findGoodsAdmin_commit", findGoodsAdminEntity);
 }
 /**
  * 
 * @Title: findGoodsAdminInsert
 * @Description: 插入找货管理信息明细
 * @author 189284--ZX
 * @param @param findGoodsAdminEntity
 * @param @return    设定文件
 * @return int    返回类型
 * @throws
  */
 @Override
 @Transactional
 public int findGoodsAdminDetailInsert(List<FindGoodsAdminDetailEntity> findGoodsAdminDetailEntitys){
	 return this.getSqlSession().insert(NAMESPACE+"findGoodsAdminDetail_insert", findGoodsAdminDetailEntitys);
 }
 /**
  * 
 * @Title: findGoodsAdminInsert
 * @Description: 新增找货管理信息
 * @author 189284--ZX
 * @param @param findGoodsAdminEntity
 * @param @return    设定文件
 * @return int    返回类型
 * @throws
  */
 @Override
 @Transactional
 public int findGoodsAdminInsert(FindGoodsAdminEntity findGoodsAdminEntity ){
	 findGoodsAdminEntity.setId( UUIDUtils.getUUID());
	 findGoodsAdminEntity.setTaskDate(new Date());
	 findGoodsAdminEntity.setCreateDate(new Date());
	 return this.getSqlSession().insert(NAMESPACE+"findGoodsAdmin_insert", findGoodsAdminEntity);
 }
 /**
  * 
 * @Title: queryFindGoodsAdminDeatil
 * @Description: pda  创建找货任务的时间 查明细
 * @author 189284--ZX
 * @param  findGoodsAdminEntity.goodsAreaCode 货区Code'
 * @param  findGoodsAdminEntity.orgCode 货区所属外场Code
 * @param  findGoodsAdminEntity.taskCrateDate 上报开始时间
 * @param  findGoodsAdminEntity.taskEndDate  上报结束时间
 * @param @return    设定文件
 * @return List<FindGoodsAdminDetailEntity>    返回类型
 * @throws
  */
 @SuppressWarnings("unchecked")
 @Override
public List<FindGoodsAdminDetailEntity> queryFindGoodsAdminDeatil(FindGoodsAdminEntity findGoodsAdminEntity){
	 return this.getSqlSession().selectList(NAMESPACE+"qurey_findGoodsAdminDeatil", findGoodsAdminEntity);
 }
 /**
  * 
 * @Title: insertFGoodsDetailMapper
 * @Description: TODO(这里用一句话描述这个方法的作用)
 * @author 189284--ZX
 * @param  taskNo任务编号
 * @param  findGoodsAdminDetailEntitys.waybillNo运单号
 * @param  findGoodsAdminDetailEntitys.serialNo 流水
 * @param @return    设定文件
 * @return int    返回类型
 * @throws
  */
 @SuppressWarnings({ "rawtypes", "unchecked" })
 @Override
 @Transactional
 public int insertFGoodsDetailMapper(List<FindGoodsAdminDetailEntity> findGoodsAdminDetailEntitys){
//	 Map map=new HashedMap();
//	 map.put("taskNo", taskNo);
//	 map.put("findGoodsAdminDetailEntitys", findGoodsAdminDetailEntitys);
	 return this.getSqlSession().insert(NAMESPACE+"insert_FGoodsfgoodsDetailMap", findGoodsAdminDetailEntitys);
 }
 /***
  * 
 * @Title: scanFGoodsfgoodsDetail
 * @Description: TODO(这里用一句话描述这个方法的作用)
 * @author 189284--ZX
 * @param @param waybillNo运单号
 * @param @param serialNo流水
 * @param @param user修改人
 * @param @return    设定文件
 * @return int    返回类型
 * @throws
  */
 @Override
 @Transactional
 @SuppressWarnings({ "rawtypes", "unchecked" })
public int scanFGoodsfgoodsDetail(String waybillNo,String serialNo,String user){
	 Map map=new HashedMap();
	 map.put("findType", "Y");
	 map.put("modifyUserCode", user);
	 map.put("modifyDate", new Date());
	 map.put("waybillNo", waybillNo);
	 map.put("serialNo", serialNo);
	 return this.getSqlSession().update(NAMESPACE+"scan_FGoodsfgoodsDetail", map);
 }
 /**
  * 
 * @Title: qureyFGoodsDetailByTaskNo
 * @Description: 根据任务号 查询明细
 * @author 189284--ZX
 * @param @param taskNo
 * @param @return    设定文件
 * @return List<FindGoodsAdminDetailEntity>    返回类型
 * @throws
  */
@SuppressWarnings("unchecked")
@Override
public List<FindGoodsAdminDetailEntity> qureyFGoodsDetailByTaskNo(String taskNo){
	return this.getSqlSession().selectList(NAMESPACE+"fGoodsDetailBytaskNo", taskNo);
}
}
