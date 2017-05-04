package com.deppon.foss.module.transfer.unload.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.BCMachSortScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.vo.BCMachSortScanVo;


/**
* @description 计泡机称重量方服务
* @version 1.0
* @author 105869-foss-heyongdong
* @update 2015年5月7日 下午2:41:05
*/
public interface IBCMachSortScanService {
	
  
/**
* @description 计泡机查询接口
* @param vo
* @return
* @version 1.0
* @author 105869-foss-heyongdong
 * @param j 
 * @param i 
* @update 2015年5月7日 下午2:41:33
*/
public List<BCMachSortScanEntity> queryBCMachSortScan(BCMachSortScanVo vo, int i, int j);


/**
* @description 查询称重量方数据总条数
* @param vo
* @return
* @version 1.0
* @author 105869-foss-heyongdong
* @update 2015年5月7日 下午3:52:07
*/
public Long queryBCMachSortScanCount(BCMachSortScanVo vo);



/**
* @description 保存计泡机称重量方信息
* @param entity
* @return
* @version 1.0
* @author 105869-foss-heyongdong
* @update 2015年5月8日 下午4:07:25
*/
public int saveScanMsg(BCMachSortScanEntity entity);



/**
* @description 通过运单号流水号查询扫描信息
* @param waybillNo
* @param serialNo
* @return
* @version 1.0
* @author 105869-foss-heyongdong
* @update 2015年5月19日 下午2:31:02
*/
public BCMachSortScanEntity queryBCMachSortScanBySeriaNo(String waybillNo,String serialNo);



/**
* @description 删除扫描表中记录
* @param sortEntity
* @return
* @version 1.0
* @author 105869-foss-heyongdong
* @update 2015年5月19日 下午3:15:29
*/
public long deleteScanMsg(BCMachSortScanEntity sortEntity);



/**
* @description 上计泡机扫描日志
* @param entity
* @return
* @version 1.0
* @author 105869-foss-heyongdong
* @update 2015年5月25日 下午4:31:17
*/
public int saveScanMsgLog(BCMachSortScanEntity entity);




/**
* @description 更新或者插入 上计泡机运单信息综合信息表
* @param waybillNo
* @return
* @version 1.0
* @author 105869-foss-heyongdong
* @update 2015年5月25日 下午7:42:25
*/
public int updateOrInsertWaybillMsg(String waybillNo);


/**
* @description 将计泡机传过来的数据获取超方超重的自动上报到QMS
* @param 开始时间，结束时间，线程号，线程数
*
* @version 1.0
* @author 268084-foss-renchao
* @update 2015年12月12日 下午7:42:25
*/
public void executeoverWeightToQMS(Date jobStartTime,Date jobEndTime );
}
