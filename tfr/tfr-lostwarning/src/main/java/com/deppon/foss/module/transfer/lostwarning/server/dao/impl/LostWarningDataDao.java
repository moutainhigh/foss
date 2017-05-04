package com.deppon.foss.module.transfer.lostwarning.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.lostwarning.api.server.dao.ILostWarningDataDao;
import com.deppon.foss.module.transfer.lostwarning.api.server.define.LostWarningConstant;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.LostWarningDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.LostWarningLogDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.LostWarningTempDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.TransferMotorcadeDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.WayBillInfoDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.WayBillPkgInfoDto;
import com.deppon.foss.module.transfer.lostwarning.api.shared.utils.Utils;

/**
 * 丢货预警数据处理接口实现类
 * 
 * 项目名称：tfr-lostwarning
 * 
 * 类名称：LostWarningDataDao
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-6-9 下午7:03:15
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class LostWarningDataDao extends iBatis3DaoImpl  implements ILostWarningDataDao  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LostWarningDataDao.class);
	
	private static final String NAMESPACE = "foss.lostwarning.dealWarningData.";
	/**
	 * @Description: 根据运单号和上报环节 清除中间表数据
	 * @date 2015-6-10 上午8:53:20   
	 * @author 263072 
	 */
	@Override
	public void delLostTempDataByBill(String wayBillNo,String repType) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		dataMap.put("repType", repType);
		this.getSqlSession().delete(NAMESPACE+"deleteLostWarningTemp",dataMap);
	}

	/**
	 * @Description: 出发库存丢货数据同步到中间表
	 * @date 2015-6-10 上午8:53:16   
	 * @author 263072 
	 * @return
	 */
	@Override
	public void synStartDptLostData() {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("queryStartTime", LostWarningConstant.SQL_QUERY_STARTTIME);
		this.getSqlSession().insert(NAMESPACE+"insertStartDptLostData_Fast",dataMap);
		this.getSqlSession().insert(NAMESPACE+"insertStartDptLostData_Slow",dataMap);
	}

	/**
	 * @Description: 查询丢货数据
	 * @date 2015-6-10 下午3:51:04   
	 * @author 263072 
	 * @param threadNo
	 * @param threadCount
	 * @param repType 上报业务类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LostWarningTempDto> searchLostData(String jobID) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("jobID", jobID);
		return this.getSqlSession().selectList(NAMESPACE + "queryLostWarningTempData",dataMap);
	}

	/**
	 * @Description: 查询出发部门丢货数据-快车
	 * @date 2015-6-15 上午8:47:22   
	 * @author 263072 
	 * @return
	 */
	@Override
	public List<LostWarningDto> searchStartDptLostData_Fast(String wayBillNo) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		return this.getSqlSession().selectList(NAMESPACE + "queryLostWarningStartDptData_Fast",dataMap);
	}
	
	/**
	 * @Description: 查询出发部门丢货数据-慢车
	 * @date 2015-6-15 08:58:29
	 * @author 263072 
	 * @return
	 */
	@Override
	public List<LostWarningDto> searchStartDptLostData_Slow(String wayBillNo) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		return this.getSqlSession().selectList(NAMESPACE + "queryLostWarningStartDptData_Slow",dataMap);
	}

	/**
	 * @Description: 根据运单号查询运单信息
	 * @date 2015-6-15 上午11:32:46   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public WayBillInfoDto searchWayBillInfo(String wayBillNo) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		return (WayBillInfoDto) getSqlSession().selectOne(NAMESPACE+"queryWayBillInfo", 
						dataMap);
	}

	/**
	 * @Description: 获取丢货预警编码序列值
	 * @date 2015-6-15 下午6:26:42   
	 * @author 263072 
	 * @return
	 */
	@Override
	public long getSeqNextVal() {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getNextSequenceValue");
	}

	/**
	 * @Description: 根据运单号查询运单打包信息
	 * @date 2015-6-16 下午1:52:47   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public List<WayBillPkgInfoDto> searchWayBillPkgList(String wayBillNo) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		return this.getSqlSession().selectList(NAMESPACE + "queryWayBillPkgInfo",dataMap);
	}

	/**
	 * @Description: 同步集中接货丢货数据
	 * @date 2015-6-16 下午6:22:59   
	 * @author 263072
	 */
	@Override
	public void synJZReceiveLostData() {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("hours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_JZRECEIVE"));
		dataMap.put("queryStartTime", LostWarningConstant.SQL_QUERY_STARTTIME);
		this.getSqlSession().insert(NAMESPACE+"insertJZReceiveLostData",dataMap);
	}

	/**
	 * @Description: 根据运单号查询集中接货丢货数据
	 * @date 2015-6-17 下午3:21:08   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public List<LostWarningDto> searchJZReceiveLostData(String wayBillNo) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		dataMap.put("hours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_JZRECEIVE"));
		return this.getSqlSession().selectList(NAMESPACE + "queryLostWarningJZReceiveLostData",dataMap);
	}

	/**
	 * @Description: 同步运输丢货数据 
	 * @date 2015-6-19 下午1:58:52   
	 * @author 263072
	 */
	@Override
	public void synTransferLostData() {
		try{
			//零担
			Map<String,Object> dataMap=new HashMap<String,Object>();
			dataMap.put("longHours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_TRANSPORT_LD_LONGDISTANCE"));
			dataMap.put("shortHours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_TRANSPORT_LD_SHORTDISTANCE"));
			dataMap.put("queryStartTime", LostWarningConstant.SQL_QUERY_STARTTIME);
			this.getSqlSession().insert(NAMESPACE+"insertTransferLostData",dataMap);
			//快递
			dataMap.put("longHours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_TRANSPORT_KD_LONGDISTANCE"));
			dataMap.put("shortHours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_TRANSPORT_KD_SHORTDISTANCE"));
			dataMap.put("queryStartTime", LostWarningConstant.SQL_QUERY_STARTTIME);
			this.getSqlSession().insert(NAMESPACE+"insertTransferLostData_KD",dataMap);
		}catch (Exception e) {
			LOGGER.error("同步运输丢货数据异常："+e.getMessage());
		}
	}

	/**
	 * @Description: 根据运单号查询运输丢货数据
	 * @date 2015-6-19 下午1:57:24   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public List<LostWarningDto> searchTransferLostData(String wayBillNo) {
		List<LostWarningDto> list = new ArrayList<LostWarningDto>();
		try{
			Map<String,Object> dataMap=new HashMap<String,Object>();
			//零担
			dataMap.put("wayBillNo", wayBillNo);
			dataMap.put("longHours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_TRANSPORT_LD_LONGDISTANCE"));
			dataMap.put("shortHours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_TRANSPORT_LD_SHORTDISTANCE"));
			list = this.getSqlSession().selectList(NAMESPACE + "queryLostWarningTransferLostData",dataMap);
			
			if(list==null||list.size()==0){
				//快递
				dataMap.put("wayBillNo", wayBillNo);
				dataMap.put("longHours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_TRANSPORT_KD_LONGDISTANCE"));
				dataMap.put("shortHours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_TRANSPORT_KD_SHORTDISTANCE"));
				list = this.getSqlSession().selectList(NAMESPACE + "queryLostWarningTransferLostData_KD",dataMap);
			}
		}catch (Exception e) {
			LOGGER.error("查询运输丢货数据异常："+e.getMessage());
		}
		return list;
	}

	/**
	 * @Description: 同步已到达丢货数据
	 * @date 2015-6-24 上午10:09:34   
	 * @author 263072
	 */
	@Override
	public void synAlreadyArriveLostData() {
		try{
			Map<String,Object> dataMap=new HashMap<String,Object>();
			dataMap.put("kdHours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_ALARRIVE_KD"));
			dataMap.put("ldHours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_ALARRIVE_LD"));
			dataMap.put("queryStartTime", LostWarningConstant.SQL_QUERY_STARTTIME);
			this.getSqlSession().insert(NAMESPACE+"insertAlreadyArriveLostData",dataMap);
		}catch (Exception e) {
			LOGGER.error("同步已到达丢货数据异常："+e.getMessage());
		}
	}

	
	/**
	 * @Description: 根据运单号查询已到达丢货数据
	 * @date 2015-6-24 上午10:10:17   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public List<LostWarningDto> searchAlreadyArriveLostData(String wayBillNo) {
		List<LostWarningDto> list = new ArrayList<LostWarningDto>();
		try{
			Map<String,Object> dataMap=new HashMap<String,Object>();
			dataMap.put("wayBillNo", wayBillNo);
			dataMap.put("kdHours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_ALARRIVE_KD"));
			dataMap.put("ldHours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_ALARRIVE_LD"));
			list = this.getSqlSession().selectList(NAMESPACE + "queryLostWarningAlreadyArriveLostData",dataMap);
		}catch (Exception e) {
			LOGGER.error("查询已到达丢货数据异常："+e.getMessage());
		}
		return list;
	}

	/**
	 * @Description: 同步中转库存丢货数据
	 * @date 2015-6-25 上午11:09:02   
	 * @author 263072
	 */
	@Override
	public void synTransferStoreLostData() {
		try{
			Map<String,Object> dataMap=new HashMap<String,Object>();
			dataMap.put("kdHours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_TRANSFERSTORE_KD"));
			dataMap.put("ldFastHours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_TRANSFERSTORE_LD_FAST"));
			dataMap.put("ldSlowHours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_TRANSFERSTORE_LD_SLOW"));
			dataMap.put("queryStartTime", LostWarningConstant.SQL_QUERY_STARTTIME);
			this.getSqlSession().insert(NAMESPACE+"insertTransferStoreLostData",dataMap);
		}catch (Exception e) {
			LOGGER.error("同步中转库存丢货数据异常："+e.getMessage());
		}
	}

	/**
	 * @Description: 根据运单号查询中转库存丢货数据
	 * @date 2015-6-25 上午11:10:30   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public List<LostWarningDto> searchTransferStoreData(String wayBillNo) {
		List<LostWarningDto> list = new ArrayList<LostWarningDto>();
		try{
			Map<String,Object> dataMap=new HashMap<String,Object>();
			dataMap.put("wayBillNo", wayBillNo);
			dataMap.put("kdHours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_TRANSFERSTORE_KD"));
			dataMap.put("ldFastHours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_TRANSFERSTORE_LD_FAST"));
			dataMap.put("ldSlowHours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_TRANSFERSTORE_LD_SLOW"));
			list = this.getSqlSession().selectList(NAMESPACE + "queryLostWarningTransferStoreData",dataMap);
		}catch (Exception e) {
			LOGGER.error("查询中转库存丢货数据异常："+e.getMessage());
		}
		return list;
	}

	/**
	 * @Description: 同步已交接丢货数据
	 * @date 2015-6-26 下午6:51:13   
	 * @author 263072
	 */
	@Override
	public void synHandoverLostData() {
		try{
			Map<String,Object> dataMap=new HashMap<String,Object>();
			dataMap.put("handOverHours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_HANDOVER"));
			dataMap.put("handOverDeliverHours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_HANDOVER_DELIVER"));
			dataMap.put("queryStartTime", LostWarningConstant.SQL_QUERY_STARTTIME);
			this.getSqlSession().insert(NAMESPACE+"insertHandoverLostData",dataMap);
		}catch (Exception e) {
			LOGGER.error("同步已交接丢货异常："+e.getMessage());
		}
		
	}

	/**
	 * @Description: 根据运单号查询已交接丢货数据
	 * @date 2015-6-26 下午6:53:48   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public List<LostWarningDto> searchHandoverLostData(String wayBillNo) {
		List<LostWarningDto> list = new ArrayList<LostWarningDto>();
		try{
			Map<String,Object> dataMap=new HashMap<String,Object>();
			dataMap.put("wayBillNo", wayBillNo);
			dataMap.put("hours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_HANDOVER"));
			list = this.getSqlSession().selectList(NAMESPACE + "queryLostWarningHandoverData",dataMap);
		}catch (Exception e) {
			LOGGER.error("查询已交接-正常装车丢货异常："+e.getMessage());
		}
		return list;
	}
		
	
	/**
	 * @Description: 根据运单号查询已交接（派送）  丢货数据
	 * @date 2015-6-26 下午6:54:11   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public List<LostWarningDto> searchHandoverForDeliverData(String wayBillNo) {
		List<LostWarningDto> list = new ArrayList<LostWarningDto>();
		try{
			Map<String,Object> dataMap=new HashMap<String,Object>();
			dataMap.put("wayBillNo", wayBillNo);
			dataMap.put("hours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_HANDOVER_DELIVER"));
			list = this.getSqlSession().selectList(NAMESPACE + "queryLostWarningHandoverData_deliver",dataMap);
		}catch (Exception e) {
			LOGGER.error("查询已交接-派送装车丢货异常："+e.getMessage());
		}
		return list;
	}

	/**
	 * @Description:同步三次以上异常库存数据 
	 * @date 2015-6-30 上午11:06:22   
	 * @author 263072
	 */
	@Override
	public void synDifferStockLostData() {
		try{
			Map<String,Object> dataMap=new HashMap<String,Object>();
			dataMap.put("countLimitKD", LostWarningConstant.timeLimitMap.get("COUNTLIMIT_DIFFERSTOCK_KD"));
			dataMap.put("countLimitLD", LostWarningConstant.timeLimitMap.get("COUNTLIMIT_DIFFERSTOCK_LD"));
			dataMap.put("queryStartTime", LostWarningConstant.SQL_QUERY_STARTTIME);
			this.getSqlSession().insert(NAMESPACE+"insertDifferStockLostData",dataMap);
		}catch (Exception e) {
			LOGGER.error("同步三次以上库存差异报告数据 异常："+e.getMessage());
		}
	}

	/**
	 * @Description: 根据运单号查询三次以上异常库存数据
	 * @date 2015-6-30 上午11:06:44   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public List<LostWarningDto> searchDifferStockData(String wayBillNo) {
		List<LostWarningDto> list = new ArrayList<LostWarningDto>();
		try{
			Map<String,Object> dataMap=new HashMap<String,Object>();
			dataMap.put("wayBillNo", wayBillNo);
			dataMap.put("countLimitKD", LostWarningConstant.timeLimitMap.get("COUNTLIMIT_DIFFERSTOCK_KD"));
			dataMap.put("countLimitLD", LostWarningConstant.timeLimitMap.get("COUNTLIMIT_DIFFERSTOCK_LD"));
			list = this.getSqlSession().selectList(NAMESPACE + "queryLostWarningDifferStockData",dataMap);
		}catch (Exception e) {
			LOGGER.error("查询三次以上库存差异报告数据 异常："+e.getMessage());
		}
		return list;
	}

	/**
	 * @Description: 同步派送丢货数据
	 * @date 2015-7-3 下午3:25:28   
	 * @author 263072 
	 * @param handleTime
	 */
	@Override
	public void synDeliverLostData() {
		this.getSqlSession().insert(NAMESPACE+"insertDeliverLostData");
		
	}

	
	/**
	 * @Description: 根据运单号查询派送丢货数据
	 * @date 2015-7-3 下午3:26:18   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public List<LostWarningDto> searchDeliverLostData(String wayBillNo) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		return this.getSqlSession().selectList(NAMESPACE + "queryLostWarningDeliverData",dataMap);
	}

	
	/**
	 * @Description: 同步空运外发丢货数据
	 * @date 2015-7-6 下午4:35:23   
	 * @author 263072 
	 * @param handleTime
	 */
	@Override
	public void synAirTransferLostData() {
		try{
			Map<String,Object> dataMap=new HashMap<String,Object>();
			dataMap.put("hours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_AIRTRANSPORT"));
			dataMap.put("queryStartTime", LostWarningConstant.SQL_QUERY_STARTTIME);
			this.getSqlSession().insert(NAMESPACE+"insertAirTransferLostData",dataMap);
		}catch (Exception e) {
			LOGGER.error("同步空运外发数据 异常："+e.getMessage());
		}
		
	}

	/**
	 * @Description: 根据运单号查询空运外发丢货数据
	 * @date 2015-7-6 下午4:35:59   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public List<LostWarningDto> searchAirTransferLostData(String wayBillNo) {
		List<LostWarningDto> list = new ArrayList<LostWarningDto>();
		try{
			Map<String,Object> dataMap=new HashMap<String,Object>();
			dataMap.put("wayBillNo", wayBillNo);
			dataMap.put("hours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_AIRTRANSPORT"));
			list = this.getSqlSession().selectList(NAMESPACE + "queryLostWarningAirTransferData",dataMap);
		}catch (Exception e) {
			LOGGER.error("查询空运外发数据 异常："+e.getMessage());
		}
		return list;
	
	}
	
	
	/**
	 * @Description: 同步快递外发丢货数据
	 * @date 2015-7-6 下午7:04:38   
	 * @author 263072 
	 * @param handleTime
	 */
	@Override
	public void synExpressExternalLostData() {
		try{
			Map<String,Object> dataMap=new HashMap<String,Object>();
			dataMap.put("hours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_EXPRESSEXTERNAL"));
			dataMap.put("queryStartTime", LostWarningConstant.SQL_QUERY_STARTTIME);
			this.getSqlSession().insert(NAMESPACE+"insertExpressExternalLostData",dataMap);
		}catch (Exception e) {
			LOGGER.error("同步快递外发数据 异常："+e.getMessage());
		}
	}

	/**
	 * @Description: 根据运单号查询快递外发丢货数据
	 * @date 2015-7-6 下午7:05:06   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public List<LostWarningDto> searchExpressExternalLostData(String wayBillNo) {
		List<LostWarningDto> list = new ArrayList<LostWarningDto>();
		try{
			Map<String,Object> dataMap=new HashMap<String,Object>();
			dataMap.put("wayBillNo", wayBillNo);
			dataMap.put("hours", LostWarningConstant.timeLimitMap.get("TIMELIMIT_EXPRESSEXTERNAL"));
			list = this.getSqlSession().selectList(NAMESPACE + "queryLostWarningExpressExternalData",dataMap);
		}catch (Exception e) {
			LOGGER.error("查询快递外发数据 异常："+e.getMessage());
		}
		return list;
	
	}

	/**
	 * @Description: 查询所有外场、车队信息
	 * @date 2015-7-14 下午6:48:11   
	 * @author 263072 
	 * @return
	 */
	@Override
	public List<TransferMotorcadeDto> queryTransferMotorCadeList() {
		return this.getSqlSession().selectList(NAMESPACE + "queryTransferCenter");
	}

	/**
	 * @Description: 查询丢货临时表数据量
	 * @date 2015-7-18 下午3:51:23   
	 * @author 263072 
	 * @return
	 */
	@Override
	public Long getLostWarningTempDataCount() {
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryLostWarningTempDataCount");
	}

	
	/**
	 * @Description: 新增日志信息且删除预警中间表记录
	 * @date 2015-7-18 下午6:26:52   
	 * @author 263072 
	 * @param list
	 * @param infoType 当该信息为空时，则不进行删除操作
	 */
	@Override
	public void insertAndDelWarningData(List<LostWarningLogDto> list,String infoType) {
		
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = getSqlSession().getConfiguration().getEnvironment().getDataSource().getConnection();
			//设置为手动提交事务
			conn.setAutoCommit(false);
			/**** 新增到日志表 ***/
			pstm = conn.prepareStatement("insert into tfr.T_OPT_LOSTWARNING_LOG(WAYBILL_NO,SERIAL_NO,REP_TYPE," +
					"REP_SCENE,REP_CODE,REP_MSG,LOST_REPCODE,IS_FIND,RESP_DEPTCODE,UPLOAD_MSG,CREATE_TIME,ID) values (?,?,?,?,?,?,?,?,?,?,sysdate,sys_guid())");
			for(LostWarningLogDto bean:list){
				pstm.setString(1, bean.getWayBillNo());
				pstm.setString(2, bean.getSerialNo());
				pstm.setString(LostWarningConstant.SONAR_NUMBER_3, bean.getRepType());
				pstm.setString(LostWarningConstant.SONAR_NUMBER_4, bean.getRepScene());
				pstm.setString(LostWarningConstant.SONAR_NUMBER_5, bean.getRepCode());
				pstm.setString(LostWarningConstant.SONAR_NUMBER_6, bean.getRepMsg());
				pstm.setString(LostWarningConstant.SONAR_NUMBER_7, bean.getLostRepCode());
				pstm.setString(LostWarningConstant.SONAR_NUMBER_8, bean.getIsFind());
				pstm.setString(LostWarningConstant.SONAR_NUMBER_9, bean.getRespDeptCode());
				pstm.setString(LostWarningConstant.SONAR_NUMBER_10, bean.getUploadMsg());
				pstm.addBatch(); 
			}
			//批量插入
			pstm.executeBatch();
			
			/**** 删除临时表数据 ****/
			if(!Utils.isStrNull(infoType)){
				pstm = conn.prepareStatement("DELETE FROM TFR.T_OPT_LOSTWARNING_TEMP T WHERE T.WAYBILL_NO=? AND T.REP_TYPE=?");
				pstm.setString(1, list.get(0).getWayBillNo());
				pstm.setString(2, infoType);            
			    pstm.executeUpdate();  
			}
			
			//事务提交
			conn.commit();
		}catch (Exception e) {
			try {
				//回滚
				//sonar-352203
				if(conn != null){
					conn.rollback();
				}
			} catch (SQLException e1) {
				LOGGER.error(e1.getMessage());
			}
		}finally{
			//关闭ps
			try {
				if(pstm != null){
					pstm.close();
				}
			} catch (SQLException e) {
				try {
					//回滚
					//sonar-352203
					if(conn != null){
						conn.rollback();
					}
				} catch (SQLException e1) {
					LOGGER.error(e1.getMessage());
				}
			}finally{
				if(conn != null){
					try {
						//关闭connection
						conn.close();
					} catch (SQLException e) {
						try {
							//回滚
							conn.rollback();
						} catch (SQLException e1) {
							LOGGER.error(e1.getMessage());
						}
					}
				}
			}
		}
		
	}

	/**
	 * @Description: 更改丢货数据JOBID
	 * @date 2015-7-21 上午9:00:04   
	 * @author 263072
	 */
	@Override
	public void upateWarningDataForJob(String jobID, int limitCount) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("jobID", jobID);
		dataMap.put("limitCount", limitCount);
		this.getSqlSession().update(NAMESPACE+"updateLostWarningTemp",dataMap);
	}

	/**
	 * @Description: 更新所有丢货数据jobID
	 * @date 2015-7-21 上午10:39:17   
	 * @author 263072
	 */
	@Override
	public void updateALLWarningDataJob(String jobID) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("jobID", jobID);
		this.getSqlSession().update(NAMESPACE+"updateALLLostWarningTemp",dataMap);
	}

	/**
	 * @Description: 根据运单号查询丢货数据日志
	 * @date 2015-7-23 上午9:25:40   
	 * @author 263072 
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public List<LostWarningLogDto> queryUploadLogSuccByWayBill(String wayBillNo) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		return this.getSqlSession().selectList(NAMESPACE + "queryUploadWarningDataSuc",dataMap);
	}
	
	
	
	/**
	 * @Description: 根据运单号、流水号查询建包、解包数据
	 * @date 2015-7-31 上午10:52:45   
	 * @author 263072 
	 * @param wayBillNo
	 * @param serialList
	 * @return
	 */
	public List<WayBillPkgInfoDto> queryWayBillPkgBySerialList(String wayBillNo,List<String> serialList){
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		dataMap.put("serialList", serialList);
		return this.getSqlSession().selectList(NAMESPACE + "queryWayBillPkgBySerial",dataMap);
	}

	/**
	 * @Description: 删除中间表所有数据（用于系统启动初始化）
	 * @date 2015-11-28 上午9:32:13   
	 * @author 263072
	 */
	@Override
	public void delALLWarningTempData() {
		this.getSqlSession().update(NAMESPACE+"deleteALLLostWarningTemp");
	}

	/**
	 * @Description:已交接丢货上报
	 *  根据运单号查询是否进行装车扫描,卸车扫描
	 * 单票入库，清仓扫描
	 * @date 2016-10-21 上午9:25:40   
	 * @author 336785 
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public boolean isSanAndSignleInStockLostData(String wayBillNo) {
		int num = (Integer) this.getSqlSession().selectOne(NAMESPACE + "querySanAndSignleInStockLostData",wayBillNo);
		if(num > 0){
			return false;
		}
		return true;
	}
	/**
	 * @Description: 已到达丢货上报
	 * 根据运单号查询是否进行装车扫描,卸车扫描
	 * 单票入库，清仓扫描
	 * @date 2016-10-21 上午9:22:40   
	 * @author 336785 
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public boolean isSanAndSignleInStockArrive(String wayBillNo) {
		int num = (Integer) this.getSqlSession().selectOne(NAMESPACE + "querySanAndSignleInStockArrive",wayBillNo);
		if(num > 0){
			return false;
		}
		return true;
	}
	/**
	 * @Description: 集中接货丢货上报
	 * 根据运单号查询是否进行装车扫描,卸车扫描
	 * 单票入库，清仓扫描
	 * @date 2016-10-21 上午9:26:40   
	 * @author 336785 
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public List<String> searchSanAndSignleInStockJZReceive(String wayBillNo) {
		return this.getSqlSession().selectList(NAMESPACE + "querySanAndSignleInStockJZReceive",wayBillNo);
	}
	/**
	 * @Description: 到达库存丢货上报
	 * 根据运单号查询是否进行装车扫描,卸车扫描
	 * 单票入库，清仓扫描
	 * @date 2016-12-21 上午9:26:40   
	 * @author 336785 
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public List<String> searchSanAndSignleIDifferStock(String wayBillNo) {
		return this.getSqlSession().selectList(NAMESPACE + "querySanAndSignleInStockIDifferStock",wayBillNo);
	}
	/**
	 * @Description: 运输丢货上报
	 * 根据运单号查询是否进行装车扫描,卸车扫描
	 * 单票入库，清仓扫描
	 * @date 2016-10-21 上午9:28:40   
	 * @author 336785 
	 * @param wayBillNo
	 * @return
	 */
	@Override
	public boolean isSanAndSignleInStockTransfer(String wayBillNo) {
		int num = (Integer) this.getSqlSession().selectOne(NAMESPACE + "querySanAndSignleInStockTransfer",wayBillNo);
		if(num > 0){
			return false;
		}
		return true;
	}
	
}
