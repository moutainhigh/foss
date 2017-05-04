package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CallCenterWaybillInfoEntity;

/**
 * cc催运单信息DAO接口
 * @author 132599-foss-shenweihua
 * @date 2014-07-21 上午 11:17:54
 * @since
 * @version
 */
public interface ICallCenterWaybillInfoDao {
	/**
	 * 新增CC催运单信息
	 * @author 132599-foss-shenweihua
	 * @date 2014-07-21 上午 11:17:54
	 * @param entity
	 * @return
	 */
	int addCallCenterWaybillInfo(CallCenterWaybillInfoEntity entity);
	
	/**
	 * 修改CC催运单信息
	 * @author 132599-foss-shenweihua
	 * @date 2014-07-21 上午 11:17:54
	 * @param entity
	 * @return
	 */
	int updateCallCenterWaybillInfo(CallCenterWaybillInfoEntity entity);
	
	/**
	 * 根据运单凭证号查询催运单信息
	 * @author 132599-foss-shenweihua
	 * @date 2014-07-21 上午 11:17:54
	 * @param callCenterWaybillNo
	 * @return
	 */
	CallCenterWaybillInfoEntity queryCcInfoByCallCenterWaybillNo(String callCenterWaybillNo);
	
	/**
	 * 根据运单凭证号判断催运单信息是否已存在
	 * @author 132599-foss-shenweihua
	 * @date 2014-07-21 上午 11:17:54
	 * @param callCenterWaybillNo
	 * @return
	 */
    boolean queryCallInfoByCallCenterWaybillNo(String callCenterWaybillNo);
    
    /**
     * 根据传入对象查询符合条件催韵达信息
     * 
     * @author 132599-foss-shenweihua
     * @date 2014-07-21 下午10:06:59
     * @param entity
     * @param limit
     * @param start
     * @return
     * @see
     */
    List<CallCenterWaybillInfoEntity> queryCallCenterInfos(CallCenterWaybillInfoEntity entity, int limit,
	    int start);

    /**
     * 统计总记录数
     * 
     * @author 132599-foss-shenweihua
     * @date 2014-07-21 下午10:06:59
     * @param entity
     * @return
     * @see
     */
    Long queryRecordCount(CallCenterWaybillInfoEntity entity);
}
