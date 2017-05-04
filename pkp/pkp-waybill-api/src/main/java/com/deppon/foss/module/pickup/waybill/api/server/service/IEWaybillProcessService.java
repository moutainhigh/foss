package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.EWaybillProcessEntity;

/**
 * @author 045925
 *
 */
public interface IEWaybillProcessService extends IService {
		

	
	/**
	 * 
	 * <p>新增待激活运单数据</p> 
	 * @author FOSS-YANGBIN
     * @date 2015-01-30 
     * @param waybillNo
     * @return integer
     * @see
	 */
   public  int insert(EWaybillProcessEntity entity);

    /**
     * 
     * <p>通过设置的JOBID查询待激活运单数据</p> 
     * @author FOSS-YANGBIN
     * @date 2015-01-30 
     * @param waybillNo
     * @return integer
     * @see
     */
    public List<EWaybillProcessEntity> queryAllByCommon(String jobId);
    
    /**
     * 
     * <p>更新一批待激活运单数据</p> 
     * @author FOSS-YANGBIN
     * @date 2015-01-30 
     * @param waybillNo
     * @return integer
     * @see
     */
    public int updateForJob(Map<String,Object> map);
    
    /**
     * 
     * <p>激活完成后删除待激活运单数据</p> 
     * @author FOSS-YANGBIN
     * @date 2015-01-30 
     * @param waybillNo
     * @return integer
     * @see
     */
    public int deleteByWaybillNo(String waybillNo);
    
    /**
     * 根据条件查询对应的条件个数
     * @author Foss-105888-Zhangxingwang
     * @date 2015-3-17 20:37:34
     * @param maps
     * @return
     */
    List<EWaybillProcessEntity> queryEWaybillByCondition(Map<String, Object> maps);
    
    /**
     * 根据运单号更新对应数据
     * @author Foss-105888-Zhangxingwang
     * @date 2015-3-18 10:44:33
     * @param entity
     * @return
     */
    int updateEWaybillProcessByWaybillNo(EWaybillProcessEntity entity);
}
