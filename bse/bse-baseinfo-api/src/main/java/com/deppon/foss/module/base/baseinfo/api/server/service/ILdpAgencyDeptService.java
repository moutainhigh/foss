package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;

/**
 * 快递代理公司网点接口
 * 
 * @author WangPeng
 * @date   2013-07-16 2:31PM
 *
 */
public interface ILdpAgencyDeptService extends IService {
	
	/**
     * 新增快递代理公司网点
     * @author WangPeng
     * @date 2013-07-16 2:38 PM
     * @param entity 快递代理公司网点实体
     * @return 1：成功；-1：失败
     * @see
     */
     int addLdpAgencyDept(OuterBranchExpressEntity entity) ;
    
    /**
     * 根据code作废快递代理公司网点
     * @author WangPeng
     * @date 2013-07-16 2:38 PM
     * @param codeStr 快递代理公司网点虚拟编码拼接字符串
     * @return 1：成功；-1：失败
     * @see
     */
     int deleteLdpAgencyDeptByCode(String codeStr,String modifyUser);
    
    /**
     * 修改快递代理公司网点
     * @author WangPeng
     * @date 2013-07-16 2:38 PM
     * @param entity 快递代理公司网点实体
     * @return 1：成功；-1：失败
     * @see
     */
     int updateLdpAgencyDept(OuterBranchExpressEntity entity);
    
    /**
     * 根据传入对象查询符合条件所有快递代理公司网点信息 
     * @author WangPeng
     * @date 2013-07-16 2:38 PM
     * @param entity 快递代理公司网点实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
     List<OuterBranchExpressEntity> queryLdpAgencyDepts(OuterBranchExpressEntity entity,int limit,int start);
    
    /**
     * <p>根据快递代理虚拟编码查询所管辖的理网点</p> 
     * @author WangPeng
     * @date 2013-07-16 2:38 PM
     * @param comVirtualCode 快递代理公司虚拟编码
     * @return
     * @see
     */
     List<OuterBranchExpressEntity> queryLdpAgencyDeptsByComVirtualCode(String comVirtualCode,String isActive);
    
    /**
     * 统计总记录数 
     * @author WangPeng
     * @date 2013-07-16 2:38 PM
     * @param entity 快递代理公司网点实体
     * @return
     * @see
     */
     Long queryRecordCount(OuterBranchExpressEntity entity);
     
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
       * 根据网点编码查询是快递代理网点信息
       * 
       * @author  WangPeng
       * @Date    2013-7-24 上午10:47:03
       * @param   deptCode
       * @return  boolean
       * 
       */
      OuterBranchExpressEntity queryLdpAgencyDeptByCode(String deptCode,String isActive);

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
        List<OuterBranchExpressEntity> queryLdpAgencyDeptsByagencyCompanyCode(String agencyCompanyCode,String isActive,int limit,int start);
        
        /**
         * <p>根据快递代理网点名称查询快递代理网点信息</p> 
         * @author 094463-foss-xieyantao
         * @date 2013-10-21 上午11:27:16
         * @param deptName
         * @return
         * @see
         */
        OuterBranchExpressEntity queryLdpAgencyDeptIsExistsByName(String deptName);
        /**
         * 根据网点编码查询是快递代理网点信息(包括代理公司简称的Code与Name)
         * @author  wusuhua
         * @Date    2015-5-29 上午10:47:03
         * @param   deptCode
         * @return  OuterBranchExpressEntity
         * */
		OuterBranchExpressEntity queryLdpAgencyDeptByDeptCode(String deptCode);
}
