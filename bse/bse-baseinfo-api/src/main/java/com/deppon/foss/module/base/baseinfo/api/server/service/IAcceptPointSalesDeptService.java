package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AcceptPointSalesChildrenDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AcceptPointSalesDeptEntity;


/**
 * 接驳点与营业部映射接口类
 * @author 132599-ShenWeiHua
 *
 * @date 2015-4-15 下午6:12:07
 */

public interface IAcceptPointSalesDeptService extends IService{
	
	/**
     * <p>新增接驳点与营业部映射主干及子信息</p> 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-15 上午10:40:21
     * @param entity
     * @return
     * @see
     */
	int addAcceptPointSalesDept(AcceptPointSalesDeptEntity entity);
	
    
    /**
     * <p>修改接驳点与营业部映射主干及子信息</p> 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-15 上午10:40:21
     * @param entity
     * @return
     * @see
     */
    int updateAcceptPointSalesDept(AcceptPointSalesDeptEntity entity);
    
    
    /**
     * <p>作废接驳点与营业部映射主干信息</p> 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-15 上午10:40:21
     * @param idList 接驳点与营业部信息ID集合
     * @return
     * @see
     */
    int deleteAcceptPointSalesDeptById(String id,Date modifyDate, String modifyUser, String modifyUserName);
    
    /**
     * <p>修改接驳点与营业部映射主干状态信息</p> 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-15 上午10:40:21
     * @param idList 接驳点与营业部信息ID集合
     * @return
     * @see
     */
    int updateAcceptPointSalesStatusById(List<String> idList,String status,Date modifyDate, String modifyUser, String modifyUserName);
    

    /**
     * 根据传入对象查询符合条件所有接驳点与营业部映射信息
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2013-4-25 上午10:50:21
     * @param entity
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    List<AcceptPointSalesDeptEntity> queryAllAcceptPointSalesDept(AcceptPointSalesDeptEntity entity,
	    int limit, int start);

    /**
     * 统计总记录数
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2013-4-15 下午3:10:32
     * @param entity
     * @return
     * @see
     */
    Long queryRecordCount(AcceptPointSalesDeptEntity entity);
    
    /**
     * 根据上级编码查询下面的营业部
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2013-4-25 上午10:50:21
     * @param entity
     * @return 符合条件的实体列表
     * @see
     */
    List<AcceptPointSalesChildrenDeptEntity> queryAllOrgAdministrativeInfoByParentOrgCode(String acceptPointCode, String smallRegion, String smallRegionName);
    
    /**
     * 根据大区编码从接驳点基础资料里面查询接驳点信息和中转场信息
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-25 上午10:50:21
     * @param bigRegionCode
     * @return 符合条件的实体列表
     * @see
     */
    List<AcceptPointSalesDeptEntity> queryAcceptPointTransferInfoByBigRegionCode(String bigRegionCode);
    
    /**
     * 根据大区编码从组织表总查询大区下面的营业区信息
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-25 上午10:50:21
     * @param bigRegionCode
     * @return 符合条件的实体列表
     * @see
     */
    List<AcceptPointSalesChildrenDeptEntity> querySmallRegionInfoByBigRegionCode(String bigRegionCode);
    
    /**
     * 根据接驳点查询接驳点与营业部映射子信息
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-25 上午10:50:21
     * @param entity
     * @return 符合条件的实体列表
     * @see
     */
    List<AcceptPointSalesChildrenDeptEntity> queryChildrenDeptByAcceptSmallCode(String acceptPointCode);
    
    /**
     * 根据接驳点编码查询
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-25 上午10:50:21
     * @param acceptPointCodes
     * @return 符合条件的实体列表
     * @see
     */
    List<AcceptPointSalesDeptEntity> queryAcceptPointSalesByAcceptPointCode(List<String> acceptPointCodes);
   
    /**
     * 根据接驳点编码查询接驳点与营业部关系
     * 
     * @author 132599-FOSS-ShenweiHua
     * @date 2015-4-25 上午10:50:21
     * @param acceptPointCodes
     * @return 符合条件的实体列表
     * @see
     */
    List<AcceptPointSalesChildrenDeptEntity> queryAcceptPointSaleDeptsByAcceptPointCode(List<String> acceptPointCodes);
    
    /**
     * 根据员工号查询快递员对应的接驳点
     * @author 132599-FOSS-ShenweiHua
     * @date   2015-4-29 上午10:50:21
     * @param empCode
     * @return
     */
    List<String> queryExpressAcceptPointByEmployeeCode(String empCode);
    
}
