package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerExpressEntity;

/**
 * 快递代理代理公司
 * 
 * @author WangPeng
 * @date   2013-07-16 3:02PM
 *
 */
public interface ILdpAgencyCompanyDao {

	 /**
     * 新增快递代理代理公司
     * @author WangPeng
     * @date 2013-07-16 3:02PM
     * @param entity 快递代理代理公司
     * @return 1：成功；-1：失败
     * @see
     */
    int addLdpAgencyCompany(BusinessPartnerExpressEntity entity) ;
    
    /**
     * 根据code作废快递代理代理公司
     * @author WangPeng
     * @date 2013-07-16 3:02PM
     * @param codes code字符串数组
     * @return 1：成功；-1：失败
     * @see
     */
    int deleteLdpAgencyCompanyByCode(String[] codes,String modifyUser);
    
    
    /**
     * 修改快递代理代理公司
     * @author WangPeng
     * @date 2013-07-16 3:02PM
     * @param entity 快递代理代理公司实体
     * @return 1：成功；-1：失败
     * @see
     */
    int updateLdpAgencyCompany(BusinessPartnerExpressEntity entity);
    
    /**
     * 根据传入对象查询符合条件所有快递代理代理公司信息 
     * @author WangPeng
     * @date 2013-07-16 3:02PM
     * @param entity 快递代理代理公司实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    List<BusinessPartnerExpressEntity> queryLdpAgencyCompanys(BusinessPartnerExpressEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author WangPeng
     * @date 2013-07-16 3:02PM
     * @param entity 快递代理代理公司实体
     * @return 符合条件的总记录数
     * @see
     */
    Long queryRecordCount(BusinessPartnerExpressEntity entity);
    
    /**
     * 根据快递代理代理公司名称查询代理公司信息 (验证该代理公司是否存在)
     * @author WangPeng
     * @date 2013-07-16 3:02PM
     * @param agentCompanyName 快递代理代理公司名称
     * @return null:不存在  BusinessPartnerExpressEntity:存在
     * @see
     */
    BusinessPartnerExpressEntity queryEntityByName(String agentCompanyName,String isActive);
    
    /**
     * 根据快递代理代理公司简称查询代理公司信息 (验证该代理公司是否存在) 
     * @author WangPeng
     * @date 2013-07-16 3:02PM
     * @param simplename 代理公司简称
     * @return null:不存在  BusinessPartnerExpressEntity:存在
     * @see
     */
    BusinessPartnerExpressEntity queryEntityBySimplename(String simplename,String isActive);
    
    /**
     * 根据快递代理代理公司编码查询代理公司信息 (验证该代理公司是否存在) 
     * @author WangPeng
     * @date 2013-07-16 3:02PM
     * @param agentCompanyCode 代理公司编码
     * @return null:不存在  BusinessPartnerExpressEntity:存在
     * @see
     */
    BusinessPartnerExpressEntity queryEntityByCode(String agentCompanyCode,String isActive);
    
    /**
     * <p>通过代理网点的编码查询所属的代理公司信息</p> 
     * @author WangPeng
     * @date 2013-07-16 3:02PM
     * @param agencyDeptCode 代理网点编码
     * @return
     * @see
     */
    BusinessPartnerExpressEntity queryBusinessPartnerByAgencyDeptCode(String agencyDeptCode,String isActive);
    
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
    boolean queryInterfaceServiceCodeIsExists(String interfaceServiceCode,String isActive);
    
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
    boolean checkInterfaceServiceCodeIsExists(String interfaceServiceCode,String id);
}
