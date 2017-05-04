package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AsteriskSalesDeptEntity;

/**
 * 加星标营业部Dao接口
 * 
 * @author 132599-foss-shenweihua
 * @date 2013-5-4 上午11:28:12
 * @since
 * @version
 */

public interface IAsteriskSalesDeptDao {
	
	/**
     * <p>新增加星标营业部信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-5-4 上午11:28:12
     * @param entity
     * @return
     * @see
     */
    int addAsteriskSalesDept(AsteriskSalesDeptEntity entity);
    
    /**
     * <p>修改加星标营业部信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-5-4 上午11:28:12
     * @param entity
     * @return
     * @see
     */
    int updateAsteriskSalesDept(AsteriskSalesDeptEntity entity);
    
    /**
     * <p>作废加星标营业部信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-5-4 上午11:28:12
     * @param asteriskDeptVirtualCodes 虚拟编码集合
     * @param modifyUser 修改人编码
     * @return
     * @see
     */
    int deleteAsteriskSalesDeptByVirtualCode(List<String> asteriskDeptVirtualCodes,
	    String modifyUser);

    /**
     * 根据传入对象查询符合条件所有加星标营业部信息
     * 
     * @author 132599-foss-shenweihua
     * @date 2013-5-4 上午11:28:12
     * @param entity
     *            加星标营业部信息实体
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    List<AsteriskSalesDeptEntity> queryAllAsteriskSalesDept(AsteriskSalesDeptEntity entity,
	    int limit, int start);

    /**
     * 统计总记录数
     * 
     * @author 132599-foss-shenweihua
     * @date 2013-5-4 上午11:28:12
     * @param entity
     *            加星标营业部信息实体
     * @return
     * @see
     */
    Long queryRecordCount(AsteriskSalesDeptEntity entity);
    
    /**
     * 验证此营业部是否已是加星营业部
     * 
     * @author 132599-foss-shenweihua
     * @date 2013-5-4 下午5:58:12
     * @param salesDeptCode
     *            加星标营业部编码
     * @return
     * @see
     */
    boolean queryAsteriskDeptByCode(String salesDeptCode);

	/**
	 * 提供客户端下载接口
	 * 
	 * @author 132599-shixiaowei
	 * @date 2013-5-17 下午3:33:12
	 * @param entity 下载查询条件
	 * @return
	 */
	 List<AsteriskSalesDeptEntity>  queryAsteriskSalesDepForDownload(AsteriskSalesDeptEntity entity);

}
