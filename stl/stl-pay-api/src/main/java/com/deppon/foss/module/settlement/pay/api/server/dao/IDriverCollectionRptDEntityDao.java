package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.domain.DriverCollectionRptDEntity;

/**
 * 司机缴款报表明细dao
 * @author 045738-foss-maojianqiang
 * @date 2012-12-20 上午9:23:58
 */
public interface IDriverCollectionRptDEntityDao {
	/**
	 * 新增操作
	 * @author 045738-foss-maojianqiang
	 * @param 缴款明细实体
	 * @date 2012-12-20 上午9:24:21
	 * @return
	 */
    int insert(DriverCollectionRptDEntity entity);
    
    /**
     * 根据id查询明细
     * @author 045738-foss-maojianqiang
     * @param 
     * @date 2012-12-20 上午9:24:39
     * @return
     */
    DriverCollectionRptDEntity queryByPrimaryKey(String id);

    /**
     * 根据报表编号、创建日期（分区键）查询报表明细
     * @author 045738-foss-maojianqiang
     * @date 2012-12-25 上午9:17:09
     * @param reportNo
     * @return
     */
    List<DriverCollectionRptDEntity> queryByReportNo(String reportNo,Date createTime);
    
}
