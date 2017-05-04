package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TransferVolumeDayEntity;

/**
 * 外场日承载货量接口
 * @author 130346
 *
 */
public interface ITransferVolumeDayService extends IService{
   
  
	/**
	 *<P>添加日承载货量信息<P>
	 * @author :130346-lifanghong
	 * @date : 2014-4-16下午3:28:02
	 * @param entity
	 * @return
	 */
	int addTransferVolumeDay(TransferVolumeDayEntity entity);
	/**
	 *<P>根据CODE作废信息<P>
	 * @author :130346-lifanghong
	 * @date : 2014-4-16下午3:33:35
	 * @param entity
	 * @return
	 */
	int deleteTransferVolumeDayEntityByCode(TransferVolumeDayEntity entity);
	
	/**
	 *<P>根据条件分页查询实体<P>
	 * @author :130346-lifanghong
	 * @date : 2014-4-16下午3:33:35
	 * @param satelliteDeptCode
	 * @param salesDeptcode
	 * @return
	 */
	List<TransferVolumeDayEntity> queryTransferVolumeDayList(TransferVolumeDayEntity entity, int start, int limit);
	/**
	 *<P>查询总数<P>
	 * @author :130346-lifanghong
	 * @date : 2014-4-16下午3:33:35
	 * @param entity
	 * @return
	 */
	long queryTransferVolumeDayListCount(TransferVolumeDayEntity entity);
	/**
     * 更新 
     * 
     * @author :130346-lifanghong
	 * @date : 2014-4-16下午3:33:35
     */
	TransferVolumeDayEntity updateTransferVolumeDay(TransferVolumeDayEntity entity);
	
	/**
     * 查询外场对应仓库饱和度危险值和预警值
     * 
     * @author :130346-lifanghong
	 * @date : 2014-4-16下午3:33:35
     */
	List<TransferVolumeDayEntity> selectTransferFullAndDangerValue(
			TransferVolumeDayEntity entity);
	
//	/**
//     * 查询外场的月承载货量
//     * 
//     * @author :130346-lifanghong
//	 * @date : 2014-4-16下午3:33:35
//     */
//	List<TransferVolumeDayEntity> selectTransferMonthValue(
//			TransferVolumeDayEntity entity);



}
