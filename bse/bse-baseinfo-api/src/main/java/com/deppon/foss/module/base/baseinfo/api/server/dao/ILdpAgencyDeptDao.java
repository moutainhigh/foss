package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;

/**
 * 快递代理代理网点
 * 
 * @author WangPeng
 * @date   2013-07-16 3:24PM
 *
 */
public interface ILdpAgencyDeptDao {

	/**
     * 新增快递代理代理网点
     * @author WangPeng
     * @date   2013-07-16 3:24PM
     * @param entity 快递代理代理网点实体
     * @return 1：成功；-1：失败 
     * @see
     */
    int addLdpAgencyDept(OuterBranchExpressEntity entity) ;
    
    /**
     * 根据code作废快递代理代理网点
     * @author WangPeng
     * @date   2013-07-16 3:24PM
     * @param codes 快递代理代理网点虚拟编码数组
     * @return 1：成功；-1：失败
     * @see
     */
    int deleteLdpAgencyDeptByCode(String[] codes,String modifyUser);
    
    /**
     * 修改快递代理代理网点
     * @author WangPeng
     * @date   2013-07-16 3:24PM
     * @param entity 快递代理代理网点实体
     * @return 1：成功；-1：失败 
     * @see
     */
    OuterBranchExpressEntity updateLdpAgencyDept(OuterBranchExpressEntity entity);
    
    /**
     * 根据传入对象查询符合条件所有快递代理代理网点信息 
     * @author WangPeng
     * @date   2013-07-16 3:24PM
     * @param entity 快递代理代理网点实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    List<OuterBranchExpressEntity> queryLdpAgencyDepts(OuterBranchExpressEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author WangPeng
     * @date   2013-07-16 3:24PM
     * @param entity 快递代理代理网点实体
     * @return
     * @see
     */
    Long queryRecordCount(OuterBranchExpressEntity entity);
    
    /**
     * <p>根据代理公司虚拟编码查询所属代理网点</p> 
     * @author WangPeng
     * @date   2013-07-16 3:24PM
     * @param comVirtualCode 代理公司虚拟编码
     * @return
     * @see
     */
    List<OuterBranchExpressEntity> queryLdpAgencyDeptsByComVirtualCode(String comVirtualCode,String isActive);
    
    /**
     * 根据网点编码查询是否存在
     * 
     * @author  WangPeng
     * @Date    2013-7-24 上午10:47:03
     * @param   deptCode
     * @return  boolean
     * 
     */
    boolean queryLdpAgencyDeptIsExistsByCode(String deptCode);
    
    /**
     * 根据网点名称查询是快递代理网点
     * 
     * @author  WangPeng
     * @Date    2013-7-24 上午10:47:03
     * @param   deptCode
     * @return  boolean
     * 
     */
    List<OuterBranchExpressEntity> queryLdpAgencyDeptIsExistsByName(String deptName);
    
    /**
     * 根据网点编码查询是快递代理网点信息
     * 
     * @author  WangPeng
     * @Date    2013-7-24 上午10:47:03
     * @param   deptCode
     * @return  boolean
     * 
     */
    List<OuterBranchExpressEntity> queryLdpAgencyDeptByCode(String deptCode,String isActive);
    
    /**
     * <p>根据快递代理公司虚拟编码查询所管辖的理网点</p> 
     * @author WangPeng
     * @date 2013-07-22 11:38 AM
     * @param comVirtualCode 快递代理公司虚拟编码数组
     * @return
     * @see
     */
     List<OuterBranchExpressEntity> queryLdpAgencyDeptsByComVirtualCodes(String[] comVirtualCode);
     
     /**
      * <p>根据快递代理公司编码查询所管辖的理网点</p> 
      * @author WangPeng
      * @date 2013-07-22 11:38 AM
      * @param agencyCompanyCode 快递代理公司编码
      * @return list
      * 
      */
      List<OuterBranchExpressEntity> queryLdpAgencyDeptsByagencyCompanyCode(String agencyCompanyCode,String isActive);
      
      /**
       * <p>根据快递代理公司编码查询所管辖的理网点</p> 
       * @author WangPeng
       * @date 2013-07-22 11:38 AM
       * @param agencyCompanyCode 快递代理公司编码
       * @return list
       * 
       */
       List<OuterBranchExpressEntity> queryLdpAgencyDeptsByagencyCompanyCode(String agencyCompanyCode,String isActive
     		  ,int limit,int start);
       
       /**
        * <P>判断提货网点编码时候重复</P>
        * 
        * @author  WangPeng
        * @Date    2013-9-24 上午9:59:18
        * @param   num
        * @return  OuterBranchExpressEntity
        *
        */
      List<OuterBranchExpressEntity> queryLdpAgencyStationNumberIsExist(String num);
}
