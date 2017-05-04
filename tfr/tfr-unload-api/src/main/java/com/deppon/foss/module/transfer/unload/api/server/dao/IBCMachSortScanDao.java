package com.deppon.foss.module.transfer.unload.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.BCMachSortScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.BCMachWaybillSortScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.vo.BCMachSortScanVo;


/**
* @description 计泡机DAO
* @version 1.0
* @author 105869-foss-heyongdong
* @update 2015年5月7日 下午2:45:22
*/
public interface IBCMachSortScanDao {
	
	/**
	* @description 查询称重量方数据
	* @param vo
	* @return
	* @version 1.0
	* @author 105869-foss-heyongdong
	 * @param j 
	 * @param i 
	* @update 2015年5月7日 下午2:45:32
	*/
	public List<BCMachSortScanEntity> queryBCMachSortScan(BCMachSortScanVo vo, int i, int j);

	
	/**
	* @description 计泡机查询称重量方数据总条数
	* @param vo
	* @return
	* @version 1.0
	* @author 105869-foss-heyongdong
	* @update 2015年5月7日 下午3:54:49
	*/
	public Long queryBCMachSortScanCount(BCMachSortScanVo vo);


	
	/**
	* @description 保存扫描信息
	* @param entity
	* @return
	* @version 1.0
	* @author 105869-foss-heyongdong
	* @update 2015年5月8日 下午4:50:07
	*/
	public int saveScanMsg(BCMachSortScanEntity entity);


	
	/**
	* @description 运单号流水好查询数据
	* @param waybillNo
	* @param serialNo
	* @return
	* @version 1.0
	* @author 105869-foss-heyongdong
	* @update 2015年5月19日 下午4:40:13
	*/
	public BCMachSortScanEntity queryBCMachSortScanBySeriaNo(String waybillNo, String serialNo);


	
	/**
	* @description 删除扫描信息
	* @param sortEntity
	* @return
	* @version 1.0
	* @author 105869-foss-heyongdong
	* @update 2015年5月19日 下午4:40:49
	*/
	public long deleteScanMsg(BCMachSortScanEntity sortEntity);


	
	/**
	* @description 保存上计泡机日志
	* @param entity
	* @return
	* @version 1.0
	* @author 105869-foss-heyongdong
	* @update 2015年5月25日 下午7:45:16
	*/
	public int saveScanMsgLog(BCMachSortScanEntity entity);


	
	/**
	* @description 更新或者插入 上计泡机运单信息综合表
	* @param waybillNo
	* @return
	* @version 1.0
	* @author 105869-foss-heyongdong
	* @update 2015年5月25日 下午7:45:19
	*/
	public int updateOrInsertWaybillMsg(String waybillNo);
	
	/**
	 * 更具运单号在计泡机总重总体积表里面查询一条数据
	 * @param waybillNo
	 * @return 计泡机的运单实体
	 * @author 268084
	 */
	public BCMachWaybillSortScanEntity queryOneBillFromBCMachine(String waybillNo);
	
}
