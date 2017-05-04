package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILdpAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerExpressEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class LdpAgencyCompanyDao extends SqlSessionDaoSupport implements ILdpAgencyCompanyDao {
	
	//定义NAMESPACE常量
	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".ldpAgencyCompany";
	/**
	 * 新增快递代理代理公司
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 3:02PM
	 * @param entity
	 *            快递代理代理公司
	 * @return 1：成功；-1：失败
	 * @see
	 */
	public int addLdpAgencyCompany(BusinessPartnerExpressEntity entity) {
		entity.setAgentCompanySort(DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);
		entity.setId(UUIDUtils.getUUID().toString());
		entity.setVersionNo(new Date().getTime());		//zxy 20140327 MANA-2018 设置版本号
		return this.getSqlSession().insert(NAMESPACE + ".addLdpAgencyCompany", entity);
	}

	/**
	 * 根据虚拟编码作废快递代理代理公司
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 3:02PM
	 * @param codes
	 *            code字符串数组
	 * @return 1：成功；-1：失败
	 * @see
	 */
	public int deleteLdpAgencyCompanyByCode(String[] codes, String modifyUser) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("virtualCodes", Arrays.asList(codes));
		map.put("agentCompanySort",DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);	
		map.put("active", FossConstants.ACTIVE);
		map.put("modifyUser", modifyUser);
		return this.getSqlSession().update(NAMESPACE + ".deleteByVirtualCode", map);
	}

	/**
	 * 修改快递代理代理公司
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 3:02PM
	 * @param entity
	 *            快递代理代理公司实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	public int updateLdpAgencyCompany(BusinessPartnerExpressEntity entity) {
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
			entity.setAgentCompanySort(DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);
			entity.setId(UUIDUtils.getUUID().toString());
			entity.setVirtualCode(UUIDUtils.getUUID());
			entity.setCreateDate(new Date());
			entity.setCreateUser(FossUserContext.getCurrentUser().getEmpCode());
			entity.setModifyDate(new Date(NumberConstants.ENDTIME));
			entity.setModifyUser(FossUserContext.getCurrentUser().getEmpCode());
			count = this.getSqlSession().insert(NAMESPACE + ".addLdpAgencyCompany", entity);
		}
		return count;
	}

	/**
	 * 根据传入对象查询符合条件所有快递代理代理公司信息
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 3:02PM
	 * @param entity
	 *            快递代理代理公司实体
	 * @param limit
	 *            每页最大显示记录数
	 * @param start
	 *            开始记录数
	 * @return 符合条件的实体列表
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public List<BusinessPartnerExpressEntity> queryLdpAgencyCompanys(
			BusinessPartnerExpressEntity entity, int limit, int start) {
			RowBounds rowBounds = new RowBounds(start, limit);
		entity.setAgentCompanySort(DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);
	return this.getSqlSession().selectList(NAMESPACE + ".queryAllInfos", entity,rowBounds);
	}

	/**
	 * 统计总记录数
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 3:02PM
	 * @param entity
	 *            快递代理代理公司实体
	 * @return 符合条件的总记录数
	 * @see
	 */
	public Long queryRecordCount(BusinessPartnerExpressEntity entity) {
		entity.setAgentCompanySort(DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);
		return (Long) this.getSqlSession().selectOne(NAMESPACE + ".queryRecordCount", entity);
	}

	/**
	 * 根据快递代理代理公司名称查询代理公司信息 (验证该代理公司是否存在)
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 3:02PM
	 * @param agentCompanyName
	 *            快递代理代理公司名称
	 * @return null:不存在 BusinessPartnerExpressEntity:存在
	 * @see
	 */
	public BusinessPartnerExpressEntity queryEntityByName(
			String agentCompanyName,String isActive) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("agentCompanyName", agentCompanyName);
		map.put("agentCompanySort",DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);	
		if(!StringUtils.isEmpty(isActive)){
			map.put("active",isActive);
		}
		return (BusinessPartnerExpressEntity) this.getSqlSession().selectOne(NAMESPACE + ".queryEntityByName", map);
	}

	/**
	 * 根据快递代理代理公司简称查询代理公司信息 (验证该代理公司是否存在)
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 3:02PM
	 * @param simplename
	 *            代理公司简称
	 * @return null:不存在 BusinessPartnerExpressEntity:存在
	 * @see
	 */
	public BusinessPartnerExpressEntity queryEntityBySimplename(
			String simplename,String isActive) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("simplename", simplename);
		map.put("agentCompanySort",DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);	
		map.put("active",isActive);
		return (BusinessPartnerExpressEntity) this.getSqlSession().selectOne(NAMESPACE + ".queryEntityBySimplename", map);
	}

	/**
	 * 根据快递代理公司编码查询快递代理公司信息 (验证该快递代理公司是否存在)
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 3:02PM
	 * @param agentCompanyCode
	 *            代理公司编码
	 * @return null:不存在 BusinessPartnerExpressEntity:存在
	 * @see
	 */
	public BusinessPartnerExpressEntity queryEntityByCode(
			String agentCompanyCode,String isActive) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("agentCompanyCode", agentCompanyCode);
		map.put("agentCompanySort",DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);	
		map.put("active",isActive);
		return (BusinessPartnerExpressEntity) this.getSqlSession().selectOne(NAMESPACE + ".queryEntityByCode", map);
	}

	/**
	 * <p>
	 * 通过代理网点的编码查询所属的代理公司信息
	 * </p>
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 3:02PM
	 * @param agencyDeptCode
	 *            代理网点编码
	 * @return
	 * @see
	 */
	public BusinessPartnerExpressEntity queryBusinessPartnerByAgencyDeptCode(
			String agencyDeptCode,String isActive) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("agencyDeptCode", agencyDeptCode);
		map.put("agentCompanySort",DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);	
		map.put("active",isActive);
		return (BusinessPartnerExpressEntity) this.getSqlSession().selectOne(NAMESPACE + ".queryBusinessPartnerByAgencyDeptCode", map);
	}

	 /**
     * 查询判断该接口服务编码是否存在
     * 
     * @author  WangPeng
     * @Date    2013-7-22 下午2:28:11
     * @param   interfaceServiceCode
     * @return  boolean
     * 
     *
     */
    public boolean queryInterfaceServiceCodeIsExists(String interfaceServiceCode,String isActive){
    	//封装成map，有效的，类型快递代理的
    	Map<String,Object> map = new HashMap<String,Object>();
		map.put("interfaceServiceCode", interfaceServiceCode);
		map.put("agentCompanySort",DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);		
		map.put("active",isActive);
    	@SuppressWarnings("unchecked")
		List<BusinessPartnerExpressEntity> businessPartner = this.getSqlSession().selectList(NAMESPACE + ".queryInterfaceServiceCodeIsExists", map);
    	//标记是否存在
    	boolean flag = false;
    	if(CollectionUtils.isNotEmpty(businessPartner)){
    		flag = true;
    	}
    	return flag;
    }
    
    /**
     * 查询判断该接口服务编码是否存在
     * 
     * @author  WangPeng
     * @Date    2013-7-22 下午2:28:11
     * @param   interfaceServiceCode
     * @return  boolean
     * 
     *
     */
    public boolean checkInterfaceServiceCodeIsExists(String interfaceServiceCode,String id){
    	//封装成map，有效的，类型快递代理的
    	Map<String,Object> map = new HashMap<String,Object>();
		map.put("interfaceServiceCode", interfaceServiceCode);
		map.put("agentCompanySort",DictionaryValueConstants.EXPRESS_DELIVERY_TYPE_LDP);		
		map.put("active",FossConstants.ACTIVE);
    	@SuppressWarnings("unchecked")
		List<BusinessPartnerExpressEntity> businessPartner = this.getSqlSession().selectList(NAMESPACE + ".queryInterfaceServiceCodeIsExists", map);
    	//标记是否存在
    	boolean flag = false;
    	if(CollectionUtils.isNotEmpty(businessPartner)){
    		for (BusinessPartnerExpressEntity entity : businessPartner) {
    			if(null == entity){
    				continue;
    			}
				if(!StringUtils.equals(entity.getId(), id)){
					flag = true;
					break;
				}
			}
    	}
    	return flag;
    }
}
