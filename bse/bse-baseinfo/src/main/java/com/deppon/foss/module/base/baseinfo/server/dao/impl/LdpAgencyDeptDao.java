package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILdpAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class LdpAgencyDeptDao extends SqlSessionDaoSupport implements ILdpAgencyDeptDao {

	//定义NAMESPACE常量
	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".ldpAgencyDept";
	
	/**
	 * 新增快递代理代理网点
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 3:24PM
	 * @param entity
	 *            快递代理代理网点实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	public int addLdpAgencyDept(OuterBranchExpressEntity entity) {
		entity.setBranchtype(DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);
		entity.setVersionNo(new Date().getTime());		//zxy 20140327 MANA-2018 设置版本号
		return this.getSqlSession().insert(NAMESPACE + ".addLdpAgencyDept", entity);
	}

	/**
	 * 根据code作废快递代理代理网点
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 3:24PM
	 * @param codes
	 *            快递代理代理网点虚拟编码数组
	 * @return 1：成功；-1：失败
	 * @see 
	 */
	public int deleteLdpAgencyDeptByCode(String[] codes, String modifyUser) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("virtualCodes", Arrays.asList(codes));
		map.put("branchtype",DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);	
		map.put("modifyUser", modifyUser);
		return this.getSqlSession().update(NAMESPACE + ".deleteByVirtualCode", map);
	}

	/**
	 * 修改快递代理代理网点
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 3:24PM
	 * @param entity
	 *            快递代理代理网点实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	public OuterBranchExpressEntity updateLdpAgencyDept(OuterBranchExpressEntity entity) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isEmpty(entity.getModifyUser())){
			map.put("modifyUser", FossUserContext.getCurrentUser().getModifyUser());
		}
		map.put("modifyUser", entity.getModifyUser());
		map.put("id", entity.getId());
		int m  = this.getSqlSession().update(NAMESPACE + ".updateById", map);
		//记录修改的行数
		int count = 0;
		if(m == 1){
			entity.setBranchtype(DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);
			entity.setId(UUIDUtils.getUUID());
			entity.setActive(FossConstants.ACTIVE);
			entity.setVirtualCode(UUIDUtils.getUUID());
			count = this.getSqlSession().insert(NAMESPACE + ".addLdpAgencyDept", entity);
			
			if(count == 0)
				return null;
		}
		return entity;
	}

	/**
	 * 根据传入对象查询符合条件所有快递代理代理网点信息
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 3:24PM
	 * @param entity
	 *            快递代理代理网点实体
	 * @param limit
	 *            每页最大显示记录数
	 * @param start
	 *            开始记录数
	 * @return 符合条件的实体列表
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<OuterBranchExpressEntity> queryLdpAgencyDepts(
			OuterBranchExpressEntity entity, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		entity.setBranchtype(DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);
	return this.getSqlSession().selectList(NAMESPACE + ".queryAllInfos", entity,rowBounds);
	}

	/**
	 * 统计总记录数
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 3:24PM
	 * @param entity
	 *            快递代理代理网点实体
	 * @return
	 * @see
	 */
	public Long queryRecordCount(OuterBranchExpressEntity entity) {
		entity.setBranchtype(DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);
		return (Long) this.getSqlSession().selectOne(NAMESPACE + ".queryRecordCount", entity);
	}

	/**
	 * <p>
	 * 根据代理公司虚拟编码查询所属代理网点
	 * </p>
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 3:24PM
	 * @param comVirtualCode
	 *            代理公司虚拟编码
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<OuterBranchExpressEntity> queryLdpAgencyDeptsByComVirtualCode(
			String virtualCode,String isActive) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("virtualCode", virtualCode);
		map.put("branchtype",DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);	
		map.put("active",isActive);
		return (List<OuterBranchExpressEntity>) this.getSqlSession().selectList(NAMESPACE + ".queryLdpAgencyDeptsByComVirtualCode", map);
	}
	
	/**
     * 根据网点编码查询是否存在
     * 
     * @author  WangPeng
     * @Date    2013-7-24 上午10:47:03
     * @param   deptCode
     * @return  boolean
     * 
     */
    public boolean queryLdpAgencyDeptIsExistsByCode(String deptCode){
    	Map<String,Object> map = new HashMap<String,Object>();
		map.put("agentDeptCode", deptCode);
		map.put("branchtype",DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);	
		map.put("active",FossConstants.ACTIVE);
		OuterBranchExpressEntity outerBranchExp = (OuterBranchExpressEntity) this.getSqlSession().selectOne(NAMESPACE + ".queryLdpAgencyDeptByCode", map);
		return  (outerBranchExp != null) ? true : false;
    }
    
    /**
     * 根据网点名称查询是否存在
     * 
     * @author  WangPeng
     * @Date    2013-7-24 上午10:47:03
     * @param   deptCode
     * @return  boolean
     * 
     */
    @SuppressWarnings("unchecked")
	public List<OuterBranchExpressEntity> queryLdpAgencyDeptIsExistsByName(String deptName){
    	Map<String,Object> map = new HashMap<String,Object>();
		map.put("agentDeptName", deptName);
		map.put("branchtype",DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);	
		map.put("active",FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + ".queryLdpAgencyDeptByName", map);
    }

    /**
     * 根据网点编码查询是快递代理网点信息
     * 
     * @author  WangPeng
     * @Date    2013-7-24 上午10:47:03
     * @param   deptCode
     * @return  boolean
     * 
     */
    @SuppressWarnings("unchecked")
	public List<OuterBranchExpressEntity> queryLdpAgencyDeptByCode(String deptCode,String isActive) {
    	Map<String,Object> map = new HashMap<String,Object>();
		map.put("agentDeptCode", deptCode);
		map.put("branchtype",DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);	
		map.put("active",FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + ".queryLdpAgencyDeptByCode", map);
	}
    
    /**
     * <p>根据快递代理公司虚拟编码查询所管辖的理网点</p> 
     * @author WangPeng
     * @date 2013-07-22 11:38 AM
     * @param comVirtualCode 快递代理公司虚拟编码数组
     * @return
     * @see
     */
     @SuppressWarnings("unchecked")
	public List<OuterBranchExpressEntity> queryLdpAgencyDeptsByComVirtualCodes(String[] virtualCode){
    	 Map<String,Object> map = new HashMap<String,Object>();
 		map.put("virtualCodes", Arrays.asList(virtualCode));
 		map.put("branchtype",DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);	
 		map.put("active",FossConstants.ACTIVE);
 		return (List<OuterBranchExpressEntity>) this.getSqlSession().selectList(NAMESPACE + ".queryOutBranchExpressByVirtualCodes", map);
     }
     
     /**
      * <p>根据快递代理公司编码查询所管辖的理网点</p> 
      * @author WangPeng
      * @date 2013-07-22 11:38 AM
      * @param agencyCompanyCode 快递代理公司编码
      * @return list
      * 
      */
      @SuppressWarnings("unchecked")
	public List<OuterBranchExpressEntity> queryLdpAgencyDeptsByagencyCompanyCode(String agencyCompanyCode, String isActive) {
        Map<String,Object> map = new HashMap<String,Object>();
   		map.put("agencyCompanyCode", agencyCompanyCode);
   		map.put("branchtype",DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);	
   		map.put("active",isActive);
   		
 		return this.getSqlSession().selectList(NAMESPACE + ".queryOutBranchExpressByCode", map);
 	}
    
      /**
       * <p>根据快递代理公司编码查询所管辖的理网点</p> 
       * @author WangPeng
       * @date 2013-07-22 11:38 AM
       * @param agencyCompanyCode 快递代理公司编码
       * @return list
       * 
       */
       @SuppressWarnings("unchecked")
 	public List<OuterBranchExpressEntity> queryLdpAgencyDeptsByagencyCompanyCode(String agencyCompanyCode, String isActive,int limit,int start) {
         Map<String,Object> map = new HashMap<String,Object>();
    		map.put("agencyCompanyCode", agencyCompanyCode);
    		map.put("branchtype",DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);	
    		map.put("active",isActive);
    		RowBounds rowBounds = new RowBounds(start, limit);
    		
  		return this.getSqlSession().selectList(NAMESPACE + ".queryOutBranchExpressByCode", map,rowBounds);
  	}
       
       /**
        * <P>判断提货网点编码时候重复</P>
        * 
        * @author  WangPeng
        * @Date    2013-9-24 上午9:59:18
        * @param   num
        * @return  OuterBranchExpressEntity
        *
        */
       @SuppressWarnings("unchecked")
	public List<OuterBranchExpressEntity> queryLdpAgencyStationNumberIsExist(String num) {
    	Map<String,Object> map = new HashMap<String,Object>();
   		map.put("branchtype",DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);	
   		map.put("active",FossConstants.ACTIVE);
   		map.put("stationNumber", num);
		return this.getSqlSession().selectList(NAMESPACE + ".queryLdpAgencyStationNumberIsExist", map);
	}
}
