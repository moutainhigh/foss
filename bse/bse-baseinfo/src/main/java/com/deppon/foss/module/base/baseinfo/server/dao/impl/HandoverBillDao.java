package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IHandoverBillDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.HandoverBillInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
 * 交单管理 dao impl类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:189284,date:2015-4-9 上午11:39:40,content:TODO </p>
 * @author 189284 
 * @date 2015-4-9 上午11:39:40
 * @since
 * @version
 */
public class HandoverBillDao extends  SqlSessionDaoSupport implements IHandoverBillDao{
	 private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".handoverBillInfo.";
	 /**
	  * <p>新增 交单管理 基础信息</p> 
	  * @author 189284 
	  * @date 2015-4-9 上午11:52:21
	  * @param handoverBillInfoEntity（时间建模）
	  * @param user（操作人）
	  * @return
	  * @see
	  */
	 @Override
	 public int addHandoverBill(HandoverBillInfoEntity handoverBillInfoEntity,String user){
		 handoverBillInfoEntity.setId(UUIDUtils.getUUID());
		 handoverBillInfoEntity.setActive(FossConstants.ACTIVE);
		 handoverBillInfoEntity.setCreateTime(new Date());
		 handoverBillInfoEntity.setCreateUser(user);
		 handoverBillInfoEntity.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());
		 handoverBillInfoEntity.setModifyTime(new Date(NumberConstants.ENDTIME));
		 if(!StringUtil.equals(FossConstants.ACTIVE,handoverBillInfoEntity.getAutomationMark())){
			 handoverBillInfoEntity.setAutomationMark(FossConstants.INACTIVE);
		 }
		 if(!StringUtil.equals(FossConstants.ACTIVE,handoverBillInfoEntity.getArtificialMark())){
			 handoverBillInfoEntity.setArtificialMark(FossConstants.INACTIVE);
		 }
		 return getSqlSession().insert(NAMESPACE+"add_handoverBill", handoverBillInfoEntity);
	 }
	 /**
	  * 
	  * <p>根据条件查询 交单管理 信息（有效）</p> 
	  * @author 189284 
	  * @date 2015-4-9 下午12:05:08
	  * @param handoverBillInfoEntity
	  * @param start
	  * @param limit
	  * @return
	  * @see
	  */
	 @SuppressWarnings("unchecked")
	@Override
	 public List<HandoverBillInfoEntity> qurey(HandoverBillInfoEntity handoverBillInfoEntity,Set<String> orgids,int start, int limit){
		 handoverBillInfoEntity.setActive(FossConstants.ACTIVE);
		 Map map=new HashMap<String,Object>();
		 map.put("handoverBillInfoEntity", handoverBillInfoEntity);
		 map.put("orgids",orgids);
		 return getSqlSession().selectList(NAMESPACE+"handoverBill_query_byCondition", map, new RowBounds(start, limit));
	 }
	 /**
	  * 
	  * <p>根据条件查询 交单管理 信息（有效） 总数</p> 
	  * @author 189284 
	  * @date 2015-4-9 下午12:11:19
	  * @param handoverBillInfoEntity
	  * @return
	  * @see
	  */
	 @Override
	 public long quryCount(HandoverBillInfoEntity handoverBillInfoEntity,Set<String> orgids){
		 handoverBillInfoEntity.setActive(FossConstants.ACTIVE);
		 Map map=new HashMap<String,Object>();
		 map.put("handoverBillInfoEntity", handoverBillInfoEntity);
		 map.put("orgids",orgids);
		 return (Long)getSqlSession().selectOne(NAMESPACE+"handoverBill_Count_byCondition", map);
	 }
	 /**
	  * 
	  * <p>根据IDs  批量  作废 交单管理 信息</p> 
	  * @author 189284 
	  * @date 2015-4-9 下午12:21:57
	  * @param ids
	  * @return
	  * @see
	  */
	 @Override
	 public int  deleteHandoverBillInfo(List<String> ids){
		 Map map=new HashMap();
		 map.put("ids", ids);
		 HandoverBillInfoEntity entity=new HandoverBillInfoEntity();
	     entity.setModifyTime(new Date());
		 entity.setModifyUser(FossUserContext.getCurrentInfo().getEmpCode());
		 entity.setActive(FossConstants.INACTIVE);
		 map.put("entity", entity);
		 map.put("conditionActive", FossConstants.ACTIVE);
		 return getSqlSession().update(NAMESPACE+"deleteHandoverBillByIds", map);
	 }
		/**
		 * 
		 * <p>根据部门编码 查询交单信息</p> 
		 * @author 189284 
		 * @date 2015-5-21 上午10:31:25
		 * @param Department 交单部门code
		 * @return
		 * @see
		 */
	 public  HandoverBillInfoEntity queryHandoverBillInfoByDepartment(String department ){
		 HandoverBillInfoEntity handoverBillInfoEntity=new HandoverBillInfoEntity();
		 handoverBillInfoEntity.setActive(FossConstants.ACTIVE);
		 handoverBillInfoEntity.setDepartment(department);
		 Map map=new HashMap<String,Object>();
		 map.put("handoverBillInfoEntity", handoverBillInfoEntity);
		 return (HandoverBillInfoEntity) getSqlSession().selectOne(NAMESPACE+"handoverBill_query_byCondition", map);
	 }
	 /**
	  * 
	  * <p>根据ID 修改交单管理信息</p> 
	  * （作废之后在新增）
	  * @author 189284 
	  * @date 2015-4-20 下午7:19:03
	  * @param handoverBillInfo
	  * @param empCode 修改人
	  * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IHandoverBillDao#updateHandoverBillInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.HandoverBillInfoEntity, java.lang.String)
	  */
	@Override
	public void updateHandoverBillInfo(HandoverBillInfoEntity handoverBillInfo,
			String empCode) {
		handoverBillInfo.setModifyTime(new Date());
		handoverBillInfo.setModifyUser(empCode);
		handoverBillInfo.setActive(FossConstants.INACTIVE);
		getSqlSession().update(NAMESPACE+"update_handoverBill", handoverBillInfo);
	}
}
