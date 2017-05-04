package com.deppon.foss.module.transfer.lostwarning.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.LostWarningDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.LostWarningLogDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.LostWarningTempDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.TransferMotorcadeDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.WayBillInfoDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.WayBillPkgInfoDto;

/**
 * 丢货数据处理接口
 * 
 * 项目名称：tfr-lostwarning-api
 * 
 * 类名称：ILostWarningDataDao
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-6-9 下午7:06:14
 * 
 * 版权所有：上海德邦物流有限公司
 */
public interface ILostWarningDataDao {
	
	/**
	 * @Description: 根据运单号和上报环节 清除中间表数据
	 * @date 2015-6-9 下午7:17:33 
	 * @author 263072 
	 */
	public void delLostTempDataByBill(String wayBillNo,String repType);
	
	/**
	 * @Description: 同步出发库存丢货数据
	 * @date 2015-6-9 下午7:20:14   
	 * @author 263072 
	 */
	public void synStartDptLostData(); 
	
	
	/**
	 * @Description: 同步集中接货丢货数据
	 * @date 2015-6-16 下午6:22:59   
	 * @author 263072
	 */
	public void synJZReceiveLostData();
	
	/**
	 * @Description: 同步运输丢货数据
	 * @date 2015-6-19 下午1:56:20   
	 * @author 263072
	 */
	public void synTransferLostData();
	
	/**
	 * @Description: 同步已到达丢货数据
	 * @date 2015-6-24 上午10:09:34   
	 * @author 263072
	 */
	public void synAlreadyArriveLostData();
	
	
	/**
	 * @Description: 同步中转库存丢货数据
	 * @date 2015-6-25 上午11:09:02   
	 * @author 263072
	 */
	public void synTransferStoreLostData();
	
	/**
	 * @Description: 同步已交接丢货数据
	 * @date 2015-6-26 下午6:51:13   
	 * @author 263072
	 */
	public void synHandoverLostData();
	
	/**
	 * @Description:同步三次以上异常库存数据 
	 * @date 2015-6-30 上午11:06:22   
	 * @author 263072
	 */
	public void synDifferStockLostData();
	
	/**
	 * @Description: 同步派送丢货数据
	 * @date 2015-7-3 下午3:25:28   
	 * @author 263072 
	 * @param handleTime
	 */
	public void synDeliverLostData();
	
	/**
	 * @Description: 同步空运外发丢货数据
	 * @date 2015-7-6 下午4:35:23   
	 * @author 263072 
	 * @param handleTime
	 */
	public void synAirTransferLostData();
	
	/**
	 * @Description: 同步快递外发丢货数据
	 * @date 2015-7-6 下午7:04:38   
	 * @author 263072 
	 * @param handleTime
	 */
	public void synExpressExternalLostData();
	
	
	/**
	 * 
	 * @Description: 查询丢货数据
	 * @date 2015-6-10 下午3:45:18   
	 * @author 263072 
	 * @return
	 */
	public List<LostWarningTempDto> searchLostData(String jobID);
	
	/**
	 * @Description: 查询出发部门丢货数据 - 快车
	 * @date 2015-6-15 上午8:39:19   
	 * @author 263072 
	 * @return
	 */
	public List<LostWarningDto> searchStartDptLostData_Fast(String wayBillNo);
	
	/**
	 * @Description: 查询出发部门丢货数据 - 慢车
	 * @date 2015-6-15 08:58:29 
	 * @author 263072 
	 * @return
	 */
	public List<LostWarningDto> searchStartDptLostData_Slow(String wayBillNo);
	
	
	/**
	 * @Description: 根据运单号查询运单信息
	 * @date 2015-6-15 上午11:32:46   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	public WayBillInfoDto searchWayBillInfo(String wayBillNo);
	
	
	/**
	 * @Description: 获取丢货预警ID序列值
	 * @date 2015-6-15 下午6:24:47   
	 * @author 263072 
	 * @return
	 */
	public long getSeqNextVal();
	
	
	/**
	 * @Description: 根据运单号查询运单打包信息
	 * @date 2015-6-16 下午1:52:47   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	public List<WayBillPkgInfoDto> searchWayBillPkgList(String wayBillNo);
	
	
	/**
	 * @Description: 根据运单号查询集中接货丢货数据
	 * @date 2015-6-17 下午3:21:08   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	public List<LostWarningDto> searchJZReceiveLostData(String wayBillNo);
	
	/**
	 * @Description: 根据运单号查询运输丢货数据
	 * @date 2015-6-19 下午1:57:24   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	public List<LostWarningDto> searchTransferLostData(String wayBillNo);
	
	/**
	 * @Description: 根据运单号查询已到达丢货数据
	 * @date 2015-6-24 上午10:10:17   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	public List<LostWarningDto> searchAlreadyArriveLostData(String wayBillNo);
	
	/**
	 * @Description: 根据运单号查询中转丢货数据
	 * @date 2015-6-25 上午11:10:30   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	public List<LostWarningDto> searchTransferStoreData(String wayBillNo);
	
	/**
	 * @Description: 根据运单号查询已交接丢货数据
	 * @date 2015-6-26 下午6:53:48   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	public List<LostWarningDto> searchHandoverLostData(String wayBillNo);
	
	
	/**
	 * @Description: 根据运单号查询已交接（派送）  丢货数据
	 * @date 2015-6-26 下午6:54:11   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	public List<LostWarningDto> searchHandoverForDeliverData(String wayBillNo);
	
	/**
	 * @Description: 根据运单号查询三次以上异常库存数据
	 * @date 2015-6-30 上午11:06:44   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	public List<LostWarningDto> searchDifferStockData(String wayBillNo);
	
	/**
	 * @Description: 根据运单号查询派送丢货数据
	 * @date 2015-7-3 下午3:26:18   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	public List<LostWarningDto> searchDeliverLostData(String wayBillNo);
	
	/**
	 * @Description: 根据运单号查询空运外发丢货数据
	 * @date 2015-7-6 下午4:35:59   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	public List<LostWarningDto> searchAirTransferLostData(String wayBillNo);
	
	/**
	 * @Description: 根据运单号查询快递外发丢货数据
	 * @date 2015-7-6 下午7:05:06   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	public List<LostWarningDto> searchExpressExternalLostData(String wayBillNo);
	
	
	/**
	 * @Description: 查询所有外场、车队信息
	 * @date 2015-7-14 下午6:48:11   
	 * @author 263072 
	 * @return
	 */
	public List<TransferMotorcadeDto> queryTransferMotorCadeList();
	
	/**
	 * @Description: 查询丢货临时表数据量
	 * @date 2015-7-18 下午3:51:23   
	 * @author 263072 
	 * @return
	 */
	public Long getLostWarningTempDataCount();
	
	
	/**
	 * @Description: 新增日志信息且删除预警中间表记录
	 * @date 2015-7-18 下午6:26:52   
	 * @author 263072 
	 * @param list
	 * @param infoType 当该信息为空时，则不进行删除操作
	 */
	public void insertAndDelWarningData(List<LostWarningLogDto> list,String infoType);
	
	/**
	 * @Description: 更改丢货数据JOBID
	 * @date 2015-7-21 上午9:00:04   
	 * @author 263072
	 */
	public void upateWarningDataForJob(String jobID,int limitCount);
	
	/**
	 * @Description: 更新所有丢货数据jobID
	 * @date 2015-7-21 上午10:39:17   
	 * @author 263072
	 */
	public void updateALLWarningDataJob(String jobID);
	
	
	/**
	 * @Description: 根据运单号查询丢货数据
	 * @date 2015-7-23 上午9:25:40   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	public List<LostWarningLogDto> queryUploadLogSuccByWayBill(String wayBillNo);
	
	
	/**
	 * @Description: 根据运单号、流水号查询建包、解包数据
	 * @date 2015-7-31 上午10:52:45   
	 * @author 263072 
	 * @param wayBillNo
	 * @param serialList
	 * @return
	 */
	public List<WayBillPkgInfoDto> queryWayBillPkgBySerialList(String wayBillNo,List<String> serialList);
	
	/**
	 * @Description: 删除中间表所有数据（用于系统启动初始化）
	 * @date 2015-11-28 上午9:32:13   
	 * @author 263072
	 */
	public void delALLWarningTempData();
	/**
	 * @Description: 已交接丢货上报
	 * 通过运单号查询是否有进行装车扫描,卸车扫描
	 * 单票入库，清仓扫描
	 * @date 2016-10-21 上午9:32:13   
	 * @author 336785
	 */
	public boolean isSanAndSignleInStockLostData(String wayBillNo);
	/**
	 * @Description: 已到达丢货上报
	 * 通过运单号查询是否有进行装车扫描,卸车扫描
	 * 单票入库，清仓扫描
	 * @date 2016-10-21 上午9:32:13   
	 * @author 336785
	 */
	public boolean isSanAndSignleInStockArrive(String wayBillNo);
	/**
	 * @Description: 接货丢货上报
	 * 通过运单号查询是否有进行装车扫描,卸车扫描
	 * 单票入库，清仓扫描
	 * @date 2016-10-21 上午9:33:13   
	 * @author 336785
	 */
	public List<String> searchSanAndSignleInStockJZReceive(String wayBillNo);
	/**
	 * @Description: 到达库存丢货上报
	 * 通过运单号查询是否有进行装车扫描,卸车扫描
	 * 单票入库，清仓扫描
	 * @date 2016-12-21 上午9:33:13   
	 * @author 336785
	 */
	public List<String> searchSanAndSignleIDifferStock(String wayBillNo);
	/**
	 * @Description: 运输丢货上报
	 * 通过运单号查询是否有进行装车扫描,卸车扫描
	 * 单票入库，清仓扫描
	 * @date 2016-10-21 上午9:35:13   
	 * @author 336785
	 */
	public boolean isSanAndSignleInStockTransfer(String wayBillNo);
	
}