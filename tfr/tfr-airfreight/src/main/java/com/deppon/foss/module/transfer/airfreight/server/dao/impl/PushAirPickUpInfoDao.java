package com.deppon.foss.module.transfer.airfreight.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IPushAirPickUpInfoDao;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpDetialInfoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpInfoRequest;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpSerialEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpSerialInfoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillTempEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirWayBillDto;

public  class PushAirPickUpInfoDao extends iBatis3DaoImpl  implements IPushAirPickUpInfoDao {

	private static final String NAMESPACE = "foss.airfreight.toOpp.";
	/**
	 * 
	* @description FOSS推送数据至OPP系统
	* 根据正单号和运单号查询临时表
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IPushAirPickUpInfoDao#queryAirPickUpTemInfo(java.util.Map)
	* @author 269701-foss-lln
	* @update 2016年4月28日 上午10:24:34
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirWaybillTempEntity> queryAirPickUpTemInfo() {
		List<AirWaybillTempEntity> resultList=this.getSqlSession().selectList(NAMESPACE + "queryAirPickUpTemInfo");
		return resultList;
	}

	/**
	 * 
	* @description FOSS推送信息至OPP系统
	* 根据清单号查询需要推送合大票主表信息
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IPushAirPickUpInfoDao#queryAirPickUpInfo(java.lang.String)
	* @author 269701-foss-lln
	* @update 2016年4月28日 上午10:25:06
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AirPickUpInfoRequest queryAirPickUpInfo(String airPickId,String airWaybillNo) {
		//sql传参
		Map<String,String> params=new HashMap<String, String>();
		//合票清单ID
		params.put("airPickId", airPickId);
		//q清单号
		params.put("airWaybillNo", airWaybillNo);
		List<AirPickUpInfoRequest> resultList=this.getSqlSession().selectList(NAMESPACE + "queryAirPickUpInfo",params);
		if(resultList!=null && resultList.size()>0){
		return resultList.get(0);	
		}else{
			return null;
		}
	}

	/**
	 * 
	* @description  FOSS推送信息至OPP系统
	* 根据合大票ID查询需要推送的合大票明细信息
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IPushAirPickUpInfoDao#queryAirPickUpDetialInfo(java.lang.String)
	* @author 269701-foss-lln
	* @update 2016年4月28日 上午10:25:35
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirPickUpDetialInfoEntity> queryAirPickUpDetialInfo(
			String airPickUpNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryAirPickUpDetialInfo",airPickUpNo);
	}
	/**
	 * 
	* @description  FOSS推送信息至OPP系统
	* 推送合大票流水表
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IPushAirPickUpInfoDao#queryAirPickUpSerialInfo(java.util.List)
	* @author 269701-foss-lln
	* @update 2016年4月28日 上午10:26:31
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirPickUpSerialInfoEntity> queryAirPickUpSerialInfo(String detialId) {
		return this.getSqlSession().selectList(NAMESPACE + "queryAirPickUpSerialInfo",detialId);
	}
	
	/**
	 * 
	* @description FOSS推送数据至OPP
	* 修改临时表数据
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IPushAirPickUpInfoDao#updateAirPickUpTemStatus(java.util.Map)
	* @author 269701-foss-lln
	* @update 2016年4月28日 上午11:03:29
	* @version V1.0
	 */
	@Override
	public void updateAirPickUpTemStatus(Map<String, String> params) {
		this.getSqlSession().update(NAMESPACE +"updateAirPickUpTemStatus",params);
	}

	/**
	 * 
	* @description FOSS推送合大票信息至OPP 暂存临时表 job定时查询并推送
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IPushAirPickUpInfoDao#addAirPickToTemp(com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillTempEntity)
	* @author 269701-foss-lln
	* @update 2016年5月11日 下午2:38:57
	* @version V1.0
	 */
	@Override
	public void addAirPickToTemp(AirWaybillTempEntity temEntity) {
		this.getSqlSession().insert(NAMESPACE +"addAirPickToTemp",temEntity);
	}

	/**
	 * 
	* @description 校验临时表 是否存在合大票清单信息
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IPushAirPickUpInfoDao#checkDataIsExist(com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillTempEntity)
	* @author 269701-foss-lln
	* @update 2016年5月11日 下午3:44:43
	* @version V1.0
	 */
	@Override
	public boolean checkDataIsExist(AirWaybillTempEntity entity) {
		//sql参数
		Map<String,String> params=new HashMap<String, String>();
		//清单号
		params.put("airPickNo", entity.getAirPickNo());
		//清单主表id
		params.put("airPickUpId", entity.getAirPickUpId());
		//航空正单号
		params.put("airWaybillNo", entity.getAirPickNo());
		//航空正单主表id
		params.put("airWaybillId", entity.getAirWaybillId());
		//单号类型 清单：20 正单 10
		params.put("billType", entity.getBillType());
		int  count=(Integer) this.getSqlSession().selectOne(NAMESPACE + "checkDataIsExist",params);
		return count>0?true:false;
	}
	/**
	 * 
	* @description 根据明细id 修改合大票明细送货费
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IPushAirPickUpInfoDao#updateAirPickDetialFeeData(java.util.Map)
	* @author 269701-foss-lln
	* @update 2016年5月19日 下午4:58:26
	* @version V1.0
	 */
	@Override
	public int updateAirPickDetialFeeData(Map<String,Object> params) {
		return getSqlSession().update(NAMESPACE + "updateAirPickDetialFeeData", params);
	}
	
	/**
	 * 
	* @description 根据主表id 修改合大票总费用
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IPushAirPickUpInfoDao#updateAirPickFeeData(java.util.Map)
	* @author 269701-foss-lln
	* @update 2016年5月19日 下午4:59:41
	* @version V1.0
	 */
	@Override
	public int updateAirPickFeeData(Map<String,Object> params) {
		return getSqlSession().update(NAMESPACE + "updateAirPickFeeData", params);
	}

	/**
	 * 
	* @description 根据合大票清单明细id 以及运单号 删除合大票流水数据
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IPushAirPickUpInfoDao#deleteAirPickSerial(java.lang.String, java.lang.String)
	* @author 269701-foss-lln
	* @update 2016年5月19日 下午6:32:22
	* @version V1.0
	 */
	@Override
	public void deleteAirPickSerial(String waybillNo, String airPickDetialId) {
		//sql参数
		Map<String,String> params=new HashMap<String, String>();
		//运单号
		params.put("waybillNo", waybillNo);
		//清单明细id
		params.put("airPickDetialId", airPickDetialId);
		this.getSqlSession().delete(NAMESPACE +"deleteAirPickSerial",params);
		
	}

	/**
	 * 
	* @description 根据合大票清单明细id 以及运单号 删除合大票明细数据
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IPushAirPickUpInfoDao#deleteAirPickDetial(java.lang.String, java.lang.String)
	* @author 269701-foss-lln
	* @update 2016年5月19日 下午6:32:38
	* @version V1.0
	 */
	@Override
	public void deleteAirPickDetial(String waybillNo, String airPickDetialId) {
				//sql参数
				Map<String,String> params=new HashMap<String, String>();
				//运单号
				params.put("waybillNo", waybillNo);
				//清单明细id
				params.put("airPickDetialId", airPickDetialId);
		this.getSqlSession().delete(NAMESPACE +"deleteAirPickDetial",params);
	}

	/**
	 * 
	* @description 根据清单主表id 修改清单主表 总票数 总件数的值
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IPushAirPickUpInfoDao#updateAirPickData(java.util.Map)
	* @author 269701-foss-lln
	* @update 2016年5月19日 下午6:45:52
	* @version V1.0
	 */
	@Override
	public int updateAirPickData(Map<String, Object> param) {
		return getSqlSession().update(NAMESPACE + "updateAirPickData", param);

	}

	/**
	 * 
	* @description 根据运单流水号删除流水信息
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IPushAirPickUpInfoDao#deleteAirPickSerialPart(java.util.Map)
	* @author 269701-foss-lln
	* @update 2016年5月19日 下午6:59:34
	* @version V1.0
	 */
	@Override
	public void deleteAirPickSerialPart(Map<String, Object> paramSerial) {
		this.getSqlSession().delete(NAMESPACE +"deleteAirPickSerialPart",paramSerial);

	}

	/**
	 * 
	* @description 根据运单号 清单明id 修改明细件数
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IPushAirPickUpInfoDao#updateAirPickDetialData(java.util.Map)
	* @author 269701-foss-lln
	* @update 2016年5月19日 下午7:05:30
	* @version V1.0
	 */
	@Override
	public int updateAirPickDetialData(Map<String, Object> paramDetial) {
		int backInt= this.getSqlSession().update(NAMESPACE + "updateAirPickDetialData", paramDetial);
		return backInt;
	}

	/**
	 * 
	* @description 用一句话描述该文件做什么
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IPushAirPickUpInfoDao#addAirPickUpDetial(com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpDetialInfoEntity)
	* @author 269701-foss-lln
	* @update 2016年5月19日 下午7:20:42
	* @version V1.0
	 */
	@Override
	public void addAirPickUpDetial(AirPickUpDetialInfoEntity airpickDetialEntity) {
		this.getSqlSession().insert(NAMESPACE + "addAirPickUpDetial", airpickDetialEntity);
	}

	/**
	 * 
	* @description 清单流水新增 流水信息
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IPushAirPickUpInfoDao#addAirPickUpSerial(com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickUpSerialInfoEntity)
	* @author 269701-foss-lln
	* @update 2016年5月19日 下午7:31:27
	* @version V1.0
	 */
	@Override
	public void addAirPickUpSerial(AirPickUpSerialEntity entity) {
		this.getSqlSession().insert(NAMESPACE + "addAirPickUpSerial", entity);
		
	}
	
	/**
	 * 根据航空正单id 查询航空正单信息
	* @author 269701-foss-lln
	* @update 2016年7月7日 下午7:31:27
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AirWaybillEntity queryAirWaybillInfoById(AirWayBillDto airWayBillDto) {
		List<AirWaybillEntity> resultList=this.getSqlSession().selectList(NAMESPACE + "queryAirwaybillById",airWayBillDto);
		if(resultList!=null&&resultList.size()>0){
			return resultList.get(0);
		}else{
			return null;
		}
	}


	/**
	 * 根据合大票主表id 修改任务表中 未推送数据的数据状态为T
	 */
	@Override
	public void updateAirPickUpTemById() {
		 this.getSqlSession().update(NAMESPACE + "updateAirPickUpTemById");

	}

}
