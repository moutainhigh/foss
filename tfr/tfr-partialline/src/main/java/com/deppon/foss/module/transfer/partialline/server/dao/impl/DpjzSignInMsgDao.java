package com.deppon.foss.module.transfer.partialline.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IDpjzSignInMsgDao;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.DpjzSignInDetialBillEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.DpjzSignInDetailDto;
/**
 * 家装送装明细及签收确认信息DAO
 * @author 269701--LLN
 * @date 2015-12-05
 *
 */
public class DpjzSignInMsgDao extends iBatis3DaoImpl implements IDpjzSignInMsgDao{

	/**
	 * 前缀
	 */
	private static String prefix = "foss.partialline.dpjzSignInMsgMapper.";

   /**
    *	查询家装送装明细及签收确认信息
    *	@author 269701--lln
    *	@date 2015-12-05
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<DpjzSignInDetailDto> querydpjzSignInDetailBills(
			DpjzSignInDetailDto detailDto, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		// 查询
		return this.getSqlSession().selectList(prefix + "querydpjzSignInDetailBills", detailDto, rowBounds);
	}

	/**
	 * 查询 家装送装明细及签收确认信息的总条数
	 * @author 269701--lln
	 * @date 2015-12-05
	 */
	@Override
	public Long querydpjzSignInDetailBillsCount(DpjzSignInDetailDto dto) {
		// 查询
		return (Long) this.getSqlSession().selectOne(prefix + "querydpjzSignInDetailBillsCount", dto);
	}

	/**
	 * 核对 德邦家装送装信息以及签收确认
	 * 修改状态--同意or不同意
	 * @author foss-lln--269701
	 * @date 2015-12-05  上午11:00:11
	 */
	@Override
	public int dpjzSignInDetialCheck(DpjzSignInDetialBillEntity entity) {
		return this.getSqlSession().update(prefix+"dpjzSignInDetialCheck", entity);
	}
	/**
	 * FOSS接收DOP推送的家装送装明细及签收确认信息
	 * @author 269701--lln
	 * @date 2015-12-05
	 */
	@Override
	public int insert(DpjzSignInDetialBillEntity record) {
		
		return this.getSqlSession().insert(prefix + "insert", record);
	}

	/**
	 * 接收DOP推送的家装送装明细及签收确认信息
	 * 校验DOP推送运单是否存在
	 * 1、已经推送，但是未审核，老数据作废 active=N;新数据active=Y
	 * 2、已经推送，但是已经审核同意，新数据active='N'；审核未同意时，老数据active='N'，新数据active=‘Y’
	 * @author 269701--lln
	 * @date 2015-12-05
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DpjzSignInDetailDto> queryWayBillNoIsExist(
			DpjzSignInDetialBillEntity record) {
		// 查询
		return this.getSqlSession().selectList(prefix + "queryWayBillNoIsExist", record);
	}

	/**
	 * 更新状态
	 * 1、已经推送，但是未审核，之前的数据作废 active=N;新数据active=Y
	 * 2、已经推送，但是已经审核同意，新数据active='N'；审核未同意时，老数据active='N'，新数据active=‘Y’
	 * @author 269701--lln
	 * @date 2015-12-05 
	 */
	@Override
	public int updateDpjzSignInStatus(String id, String active) {
		Map<String,String> map = new HashMap<String,String>();
		//id
		map.put("id", id);
		//active 是否有效
		map.put("active", active);
		return this.getSqlSession().update(prefix+"updateDpjzSignInStatus",map);
	}

	/**
	 * 自动更新德邦家装送装信息
	 */
	@Override
	public int updateDpjzSignInMsgWaybill(DpjzSignInDetailDto entity) {
		Map<String,String> map = new HashMap<String,String>();
		//运单号
		map.put("waybillNo",entity.getWaybillNo());
		//最后操作人
		map.put("lastOperUser",entity.getLastOperUser());
		//最后操作人code
		map.put("lastOperUserCode",entity.getLastOperUserCode());
		//审核意见
		map.put("checkOpinion",entity.getCheckOpinion());
		//审核状态
		map.put("status",entity.getStatus());
		
		return this.getSqlSession().update(prefix + "updateDpjzSignInMsgWaybill", map);
	}
	/**
	 * 收到DOP推送的信息后，给收货部门发送在线通知;
	 * 根据运单号查询运单表 获取收货部门信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DpjzSignInDetailDto> queryDpjzReceiveMsgWaybill(String waybillNo){
		Map<String,Object> paramMap=new HashMap<String,Object>();
		//运单号
		paramMap.put("waybillNo", waybillNo);
		// 查询
		return this.getSqlSession().selectList(prefix + "queryDpjzReceiveMsgWaybill",paramMap);
	}
}
