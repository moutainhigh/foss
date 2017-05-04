package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.SqlUtil;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILdpAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LdpAgencyCompanyException;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.common.FossTTLCache;

/**
 * 快递代理公司接口实现
 * 
 * @author WangPeng
 * @date 2013-07-16 2:31PM
 * 
 */
public class LdpAgencyCompanyService implements ILdpAgencyCompanyService {

	/**
	 * 记录日志
	 */
	Logger logger = LoggerFactory.getLogger(LdpAgencyCompanyService.class);

	// 快递代理公司Dao层
	private ILdpAgencyCompanyDao ldpAgencyCompanyDao;

	// 快递代理公司网点Service层
	private ILdpAgencyDeptService ldpAgencyDeptService;

	public void setLdpAgencyDeptService(ILdpAgencyDeptService ldpAgencyDeptService) {
		this.ldpAgencyDeptService = ldpAgencyDeptService;
	}

	/**
	 * 新增快递代理公司
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 2:31PM
	 * @param entity
	 *            快递代理公司实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	@Transactional
	public int addLdpAgencyCompany(BusinessPartnerExpressEntity entity) {
		if (null == entity) {
			throw new LdpAgencyCompanyException("新增对象为空！");
		} else {
			//313353 sonar
			this.sonarSplitOne(entity);
		}
		if (!StringUtils.isEmpty(entity.getInterFaceServiceCode())) {
			// 标记接口服务编码是否存在
			boolean flag = ldpAgencyCompanyDao
					.queryInterfaceServiceCodeIsExists(entity
							.getInterFaceServiceCode().trim(), entity
							.getActive());

			if (flag) {
				throw new LdpAgencyCompanyException("新增快递代理公司接口服务编码重复！");
			}
		}
		if (null == entity.getCreateDate()) {
			entity.setCreateDate(new Date());
		}
		if (null == entity.getCreateUser()) {
			entity.setCreateUser(FossUserContext.getCurrentUser().getEmpCode());
		}
		if (null == entity.getModifyDate()) {
			entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		}
		if (null == entity.getModifyUser()) {
			entity.setModifyUser(FossUserContext.getCurrentUser().getEmpCode());
		}

		entity.setVirtualCode(UUIDUtils.getUUID().toString());
		// 记录操作的行数
		// 新增一条快递代理公司信息
		int count = ldpAgencyCompanyDao.addLdpAgencyCompany(entity);
		return count;
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitOne(BusinessPartnerExpressEntity entity) {
		if (StringUtils.isBlank(entity.getAgentCompanyCode())) {
			throw new LdpAgencyCompanyException("新增快递代理公司编码为空！");
		}else if (StringUtils.isBlank(entity.getManagement())) {
			throw new LdpAgencyCompanyException("快递代理公司管理部门为空！");

		} else if (StringUtils.isEmpty(entity.getAgentCompanyName())) {
			throw new LdpAgencyCompanyException("新增快递代理公司名称为空！");

		} else if (StringUtils.isEmpty(entity.getSimplename())) {
			throw new LdpAgencyCompanyException("新增快递代理公司简称为空！");

		} else if (StringUtils.isEmpty(entity.getProvCode())) {
			throw new LdpAgencyCompanyException("新增快递代理公司所在省份信息为空！");

		} else if (StringUtils.isEmpty(entity.getCityCode())) {
			throw new LdpAgencyCompanyException("新增快递代理公司所在城市信息为空！");

		} else if (StringUtils.isEmpty(entity.getContactAddress())) {
			throw new LdpAgencyCompanyException("新增快递代理公司联系地址为空！");

		} else if (StringUtils.isEmpty(entity.getContact())) {
			throw new LdpAgencyCompanyException("新增快递代理公司联系人为空！");

		} else if (StringUtils.isEmpty(entity.getContactPhone())) {
			throw new LdpAgencyCompanyException("新增快递代理公司联系电话为空！");
		}
	}

	/**
	 * 根据code作废快递代理公司
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 2:31PM
	 * @param codeStr
	 *            虚拟code拼接的字符串
	 * @return 1：成功；-1：失败
	 * @see
	 */
	@Transactional
	public int deleteLdpAgencyCompanyByCode(String codeStr, String modifyUser) {
		// 获取前台传递的虚拟code数组
		String[] virtualCodes = null;
		if (StringUtils.isEmpty(codeStr)) {
			throw new LdpAgencyCompanyException("需要删除的记录所对应的虚拟code为空！");
		} else {
			virtualCodes = codeStr.split(",");
		}
		if (StringUtils.isEmpty(modifyUser)) {
			modifyUser = FossUserContext.getCurrentUser().getEmpCode();
		}

		//记录数组长度
		int length = virtualCodes.length;
	//	List<OuterBranchExpressEntity> list = new ArrayList<OuterBranchExpressEntity>();
		// 更具快递代理公司虚拟编码来获取旗下的公司网点
		List<OuterBranchExpressEntity> list = ldpAgencyDeptService
				.queryLdpAgencyDeptsByComVirtualCodes(virtualCodes);
		// 存放仍含有有效的快递代理公司网点的的快递代理公司名称
		StringBuffer buffer = new StringBuffer();
		// 标记是否在要删除的公司下面仍有有效的网点
		boolean flag = false;
		if(CollectionUtils.isNotEmpty(list)){
			
			if (list.size() > 0) {
				for (OuterBranchExpressEntity outerBranchExpressEntity : list) {
					buffer = buffer
							.append(outerBranchExpressEntity.getAgentCompanyName())
							.append(",")
							.append(outerBranchExpressEntity.getAgentDeptName())
							.append(";");
					flag = true;
				}
			}
		}
		if (flag) {
			throw new LdpAgencyCompanyException("以下" + buffer.toString()
					+ "快递代理公司下面还存在有效的公司网点，不能删除！");
		}
		
		// 删除选中的记录
		int count = ldpAgencyCompanyDao.deleteLdpAgencyCompanyByCode(
				virtualCodes, modifyUser);
		
		
		return (count == length) ? 1 : -1;
	}

	/**
	 * 修改快递代理公司
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 2:31PM
	 * @param entity
	 *            快递代理公司实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	@Transactional
	public int updateLdpAgencyCompany(BusinessPartnerExpressEntity entity) {
		if (null == entity) {
			throw new LdpAgencyCompanyException("查询的对象参数为空！");
		} else {
			//313353 sonar
			this.sonarSplitTwo(entity);
		}
		if (!StringUtils.isEmpty(entity.getInterFaceServiceCode())) {
			// 标记接口服务编码是否存在
			boolean flag = ldpAgencyCompanyDao
					.checkInterfaceServiceCodeIsExists(entity
							.getInterFaceServiceCode().trim(), entity.getId());

			if (flag) {
				throw new LdpAgencyCompanyException("快递代理公司接口服务编码重复！");
			}
		}
		// 记录操作行数
		int count = ldpAgencyCompanyDao.updateLdpAgencyCompany(entity);
		if(count == 1){
			invalidEntity(entity.getAgentCompanyCode());
		}
		return count;
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitTwo(BusinessPartnerExpressEntity entity) {
		if (StringUtils.isBlank(entity.getAgentCompanyCode())) {
			throw new LdpAgencyCompanyException("快递代理公司编码为空！");
		} else if (StringUtils.isBlank(entity.getManagement())) {
			throw new LdpAgencyCompanyException("快递代理公司管理部门为空！");

		} else if (StringUtils.isEmpty(entity.getAgentCompanyName())) {
			throw new LdpAgencyCompanyException("快递代理公司名称为空！");

		} else if (StringUtils.isEmpty(entity.getSimplename())) {
			throw new LdpAgencyCompanyException("快递代理公司简称为空！");

		} else if (StringUtils.isEmpty(entity.getProvCode())) {
			throw new LdpAgencyCompanyException("快递代理公司所在省份信息为空！");

		} else if (StringUtils.isEmpty(entity.getCityCode())) {
			throw new LdpAgencyCompanyException("快递代理公司所在城市信息为空！");

		} else if (StringUtils.isEmpty(entity.getContactAddress())) {
			throw new LdpAgencyCompanyException("快递代理公司联系地址为空！");

		} else if (StringUtils.isEmpty(entity.getContact())) {
			throw new LdpAgencyCompanyException("快递代理公司联系人为空！");

		} else if (StringUtils.isEmpty(entity.getContactPhone())) {
			throw new LdpAgencyCompanyException("新增快递代理公司联系电话为空！");
		}
	}

	/**
	 * 根据传入对象查询符合条件所有快递代理公司信息
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 2:31PM
	 * @param entity
	 *            快递代理公司实体
	 * @param limit
	 *            每页最大显示记录数
	 * @param start
	 *            开始记录数
	 * @return 符合条件的实体列表
	 * @see
	 */
	public List<BusinessPartnerExpressEntity> queryLdpAgencyCompanys(
			BusinessPartnerExpressEntity entity, int limit, int start) {
		if (null == entity) {
			throw new LdpAgencyCompanyException("查询的对象参数为空！");
		}
		
		return ldpAgencyCompanyDao.queryLdpAgencyCompanys(entity, limit, start);
	}
	
	/**
	 * <p>根据传入对象查询符合条件所有快递代理公司信息</p> 
	 * @author 094463-foss-xieyantao
	 * @date 2013-9-10 上午11:58:07
	 * @param entity
	 * @param limit
	 * @param start
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyCompanyService#queryInfos(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerExpressEntity, int, int)
	 */
	@Override
	public List<BusinessPartnerExpressEntity> queryInfos(
	    BusinessPartnerExpressEntity entity, int limit, int start) {
	    if (null == entity) {
		throw new LdpAgencyCompanyException("查询的对象参数为空！");
	    }
	    BusinessPartnerExpressEntity entity2 = new BusinessPartnerExpressEntity();
	    entity2.setCompanyCode(entity.getAgentCompanyCode());
	    entity2.setCompanyName(entity.getAgentCompanyName());
	    entity2.setSimplename(entity.getSimplename());
	    entity2.setActive(entity.getActive());
	
	    return ldpAgencyCompanyDao.queryLdpAgencyCompanys(entity2, limit, start);
	}

	/**
	 * 统计总记录数
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 2:31PM
	 * @param entity
	 *            快递代理公司实体
	 * @return 符合条件的总记录数
	 * @see
	 */
	public Long queryRecordCount(BusinessPartnerExpressEntity entity) {
		if (null == entity) {
			throw new LdpAgencyCompanyException("查询对象为空！");
		}
		return ldpAgencyCompanyDao.queryRecordCount(entity);
	}

	/**
	 * 根据快递代理公司名称查询公司信息 (验证该公司是否存在)
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 2:31PM
	 * @param agentCompanyName
	 *            快递代理公司名称
	 * @return null:不存在 BusinessPartnerExpressEntity:存在
	 * @see
	 */
	public BusinessPartnerExpressEntity queryEntityByName(
			String agentCompanyName, String isActive) {
		BusinessPartnerExpressEntity busPartner = null;
		if (StringUtils.isEmpty(agentCompanyName)) {
			throw new LdpAgencyCompanyException("传入的快递代理公司名称为空！");
		} else {
			if (!StringUtils.isEmpty(isActive) && !"Y".equals(isActive)
					&& !"N".equals(isActive)) {
				throw new LdpAgencyCompanyException("传入的是否有效字段在非空的情况下只能为Y或N！");
			}
			busPartner = ldpAgencyCompanyDao.queryEntityByName(
					agentCompanyName, isActive);
		}
		return busPartner;
	}

	/**
	 * 根据快递代理公司简称查询快递代理公司信息 (验证该公司是否存在)
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 2:31PM
	 * @param simplename
	 *            快递代理公司简称
	 * @return null:不存在 BusinessPartnerExpressEntity:存在
	 * @see
	 */
	public BusinessPartnerExpressEntity queryEntityBySimplename(
			String simplename, String isActive) {
		BusinessPartnerExpressEntity busPartner = null;
		if (StringUtils.isEmpty(simplename)) {
			throw new LdpAgencyCompanyException("传入的快递代理公司简称为空！");
		} else {
			if (!StringUtils.isEmpty(isActive) && !"Y".equals(isActive)
					&& !"N".equals(isActive)) {
				throw new LdpAgencyCompanyException("传入的是否有效字段在非空的情况下只能为Y或N！");
			}
			busPartner = ldpAgencyCompanyDao.queryEntityBySimplename(simplename,
					isActive);
		}
		return busPartner;
	}

	/**
	 * 根据快递代理公司编码查询公司信息 (验证该公司是否存在)
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 2:31PM
	 * @param agentCompanyCode
	 *            快递代理公司编码
	 * @return null:不存在 BusinessPartnerExpressEntity:存在
	 * @see
	 */
	public BusinessPartnerExpressEntity queryEntityByCode(
			String agentCompanyCode, String isActive) {
		BusinessPartnerExpressEntity busPartner = null;
		if (StringUtils.isEmpty(agentCompanyCode)) {
			throw new LdpAgencyCompanyException("传入的快递代理公司编码为空！");
		} else {
			if (!StringUtils.isEmpty(isActive) && !"Y".equals(isActive)
					&& !"N".equals(isActive)) {
				throw new LdpAgencyCompanyException("传入的是否有效字段在非空的情况下只能为Y或N！");
			}
			if (SqlUtil.loadCache) {
				busPartner = queryEntityCache(agentCompanyCode);
			} else {

				busPartner = ldpAgencyCompanyDao.queryEntityByCode(
						agentCompanyCode, isActive);
			}
		}
		return busPartner;
	}

	/**
	 * <p>
	 * 通过快递代理网点的编码查询所属的公司信息
	 * </p>
	 * 
	 * @author WangPeng
	 * @date 2013-07-16 2:31PM
	 * @param agencyDeptCode
	 *            快递代理网点编码
	 * @return
	 * @see
	 */
	public BusinessPartnerExpressEntity queryBusinessPartnerByAgencyDeptCode(
			String agencyDeptCode, String isActive) {
		BusinessPartnerExpressEntity busPartner = null;
		if (StringUtils.isEmpty(agencyDeptCode)) {
			throw new LdpAgencyCompanyException("传入的快递代理公司网点code为空！");
		} else {
			if (!StringUtils.isEmpty(isActive) && !"Y".equals(isActive)
					&& !"N".equals(isActive)) {
				throw new LdpAgencyCompanyException("传入的是否有效字段在非空的情况下只能为Y或N！");
			}
			busPartner = ldpAgencyCompanyDao.queryBusinessPartnerByAgencyDeptCode(agencyDeptCode,
					isActive);
		}
		return busPartner;
	}

	/**
	 * 清空指定的缓存实体
	 * 
	 * @author WangPeng
	 * @Date 2013-7-23 上午9:33:01
	 * @param key
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void invalidEntity(String key) {
		((ICache<String, BusinessPartnerExpressEntity>) CacheManager
				.getInstance()
				.getCache(FossTTLCache.BUSINESSPARTNER_EXPRESS_CACHE_UUID))
				.invalid(key);
	}

	/**
	 * 取缓存中的实体
	 * 
	 * @author WangPeng
	 * @Date 2013-7-23 上午9:31:53
	 * @param key
	 * @return AdministrativeRegionsEntity
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private BusinessPartnerExpressEntity queryEntityCache(String key) {
		BusinessPartnerExpressEntity result = null;
		try {
			CacheManager cacheManager = CacheManager.getInstance();
			if (cacheManager == null) {
				return null;
			}
			ICache cache = cacheManager
					.getCache(FossTTLCache.BUSINESSPARTNER_EXPRESS_CACHE_UUID);
			if (cache == null) {
				return null;
			}
			result = (BusinessPartnerExpressEntity) cache.get(key);
		} catch (Exception t) {
			logger.error("cache找不到", t);
		}
		return result;
	}

	/**
	 * @return the ldpAgencyCompanyDao
	 */
	public ILdpAgencyCompanyDao getLdpAgencyCompanyDao() {
		return ldpAgencyCompanyDao;
	}

	/**
	 * @param ldpAgencyCompanyDao
	 *            the ldpAgencyCompanyDao to set
	 */
	public void setLdpAgencyCompanyDao(ILdpAgencyCompanyDao ldpAgencyCompanyDao) {
		this.ldpAgencyCompanyDao = ldpAgencyCompanyDao;
	}

}
