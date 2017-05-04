/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-partialline
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 落地配外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/server/dao/impl/LdpExternalBillDao.java
 * 
 *  FILE NAME     :LdpExternalBillDao.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.partialline.server.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.partialline.api.server.dao.ILdpExternalBillDao;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillWayBillInfoDto;

/**
 * 落地配Dao
 * @author ibm-liuzhaowei
 * @date 2013-07-16 上午9:18:36
 */
public class LdpExternalBillDao extends iBatis3DaoImpl implements ILdpExternalBillDao {

	/**
	 * 前缀
	 */
	private static String prefix = "foss.partialline.ldpExternalBillMapper.";

	/**
	 * 查询落地配列表
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午9:02:27
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LdpExternalBillDto> selectByParams(LdpExternalBillDto dto, int limit, int start) {
		// 运单可用
		dto.setActive(PartiallineConstants.WAY_BILL_ACTIVE_Y);
		// 分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		
		return this.getSqlSession().selectList(prefix + "selectLdpExternalBillList", dto, rowBounds);
	}
	
	/**
	 * 查询落地配列表
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午9:02:27
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LdpExternalBillDto> queryLdpExternalBillList(LdpExternalBillDto dto) {
		// 运单可用
		dto.setActive(PartiallineConstants.WAY_BILL_ACTIVE_Y);
		
		return this.getSqlSession().selectList(prefix + "selectLdpExternalBillList", dto);
	}

	/**
	 * 插入落地配外发单
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午11:52:20
	 */
	@Override
	public int insert(LdpExternalBillDto record) {
		// 插入
		this.getSqlSession().insert(prefix + "insert", record);
		return 0;
	}

	/**
	 * 根据主键查询外发单
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午11:52:20
	 */
	@Override
	public LdpExternalBillDto queryByPrimaryKey(String id) {
		return (LdpExternalBillDto) this.getSqlSession().selectOne(prefix + "selectByPrimaryKey", id);
	}

	/**
	 * 更新落地配外发单
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午11:52:20
	 */
	@Override
	public int updateByPrimaryKey(LdpExternalBillDto record) {
		// 更新
		this.getSqlSession().update(prefix + "updateExternalBill", record);
		return PartiallineConstants.SERVICE_RESULT_INT_VALUE;
	}

	/**
	 * 根据参数查询落地配外发单的条数，用于分页查询
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午3:04:11
	 */
	@Override
	public Long queryCount(LdpExternalBillDto dto) {
		// 执行查询
		return (Long) this.getSqlSession().selectOne(prefix + "getCount", dto);
	}

	/**
	 * 查询已交接但未做录入的运单
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午11:31:50
	 */
	@Override
	public Long queryHandedUninputWaybill(LdpExternalBillDto dto) {
		// 可用状态
		List<String> availableStatus = new ArrayList<String>();
		// 待审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
		// 已审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
		// 反审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT);
		dto.setList(availableStatus);
		// 落地配
		dto.setHandoverType(PartiallineConstants.HANDOVER_TYPE_LDP_HANDOVER);
		// 运单状态
		dto.setActive(PartiallineConstants.WAY_BILL_ACTIVE_Y);
		// 非已预配、非作废状态
		List<Integer> billStatuslist = new ArrayList<Integer>();
		// 20：已交接，
		billStatuslist.add(LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER);
		// (集配交接单专属状态，21：已配载)
		billStatuslist.add(LoadConstants.HANDOVERBILL_STATE_ALREADY_ASSEMBLE);
		// 30：已出发，
		billStatuslist.add(LoadConstants.HANDOVERBILL_STATE_ALREADY_DEPART);
		// 40：已到达，
		billStatuslist.add(LoadConstants.HANDOVERBILL_STATE_ALREADY_ARRIVE);
		// 50：已入库
		billStatuslist.add(LoadConstants.HANDOVERBILL_STATE_ALREADY_INSTOCK);

		dto.setBillStatuslist(billStatuslist);
		// 执行查询
		return (Long) this.getSqlSession().selectOne(prefix + "selectHandedUninputWaybill", dto);
	}

	/**
	 * 根据对应的SQL和参数查询总的结果数
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午2:59:29
	 */
	@Override
	public Long queryCount(LdpExternalBillDto dto, String mapperSqlId) {
		// 执行查询
		return (Long) this.getSqlSession().selectOne(prefix + mapperSqlId, dto);
	}

	/**
	 * 根据ID 更新审核状态
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午12:18:47
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao#updateAuditStatusByPrimaryKey(java.util.List,
	 *      java.lang.String)
	 */
	@Override
	public void updateAuditStatusByPrimaryKey(List<String> auditIds, String auditStatus) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		// 状态
		parameter.put("auditStatus", auditStatus);
		// 更改列表
		parameter.put("list", auditIds);
		// 执行更新
		this.getSqlSession().update(prefix + "updateAuditStatusByPrimaryKey", parameter);
	}

	/**
	 * 根据运单号查询，被录入的外发单列表
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午11:36:42
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LdpExternalBillDto> queryByWaybillNo(LdpExternalBillDto dto) {
		// 可用状态
		List<String> availableStatus = new ArrayList<String>();
		// 待审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
		// 已审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
		// 反审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT);

		dto.setList(availableStatus);
		// 执行查询
		return this.getSqlSession().selectList(prefix + "queryByWaybillNo", dto);
	}

	/**
	 * 根据外发单号及落地配代理编号 查询,非作废 的外发单列表
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午1:48:51
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao#queryByExternalBillNo(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LdpExternalBillDto> queryByExternalBillNo(LdpExternalBillDto dto) {
		// 可用状态
		List<String> availableStatus = new ArrayList<String>();
		// 待审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
		// 已审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
		// 反审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT);

		dto.setList(availableStatus);
		// 执行查询
		return this.getSqlSession().selectList(prefix + "queryByExternalBillNo", dto);
	}

	/**
	 * 根据运单号查询,特定时间之后的被录入的外发单列表
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午10:26:10
	 */
	@Override
	public Long queryByWaybillNoAndRegisterTime(LdpExternalBillDto dto) {
		// 可用状态
		List<String> availableStatus = new ArrayList<String>();
		// 待审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
		// 已审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
		// 反审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT);

		dto.setList(availableStatus);
		// 执行查询
		return (Long) this.getSqlSession().selectOne(prefix + "queryByWaybillNoAndRegisterTime", dto);
	}

	/**
	 * 根据主键列表查询落地配外发单列表
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午3:09:14
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LdpExternalBillDto> selectLdpExternalBillByPrimaryKeys(List<String> externalBillIds) {
		// 执行查询	
		return this.getSqlSession().selectList(prefix + "selectExternalBillByPrimaryKeys", externalBillIds);

	}

	/**
	 * 根据运单号查询运单信息
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午5:26:49
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LdpExternalBillWayBillInfoDto selectWayBillByWaybillNo(LdpExternalBillDto dto) {
		// 查询当前有效的运单
		dto.setActive(PartiallineConstants.WAY_BILL_ACTIVE_CHAR_VALUE);
		// 执行查询
		List<LdpExternalBillWayBillInfoDto> tempList = this.getSqlSession().selectList(
				prefix + "selectWayBillByWaybillNo", dto);
		if (CollectionUtils.isNotEmpty(tempList)) {
			return tempList.get(0);
		} else {
			return null;
		}

	}

	/**
	 * 根据运单号查询落地配外发单
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午3:42:45
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao#queryExternalBillByWaybillNo(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto)
	 */
	@Override
	public LdpExternalBillDto queryLdpExternalBillByWaybillNo(LdpExternalBillDto tempDto) {
		// 已审核
		tempDto.setAuditStatus(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
		// 非中转外发
		tempDto.setTransferExternal(PartiallineConstants.IS_TRANSFER_EXTERNAL_NO);
		@SuppressWarnings("unchecked")
		// 执行查询
		List<LdpExternalBillDto> tempList = this.getSqlSession().selectList(prefix + "queryExternalBillByWaybillNo",
				tempDto);
		if (CollectionUtils.isNotEmpty(tempList)) {
			return tempList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据运单号查询落地配外发单
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午3:42:45
	 */
	@Override
	public LdpExternalBillDto queryLdpExternalBillByWaybillNoForChange(LdpExternalBillDto ldpExternalBillDto) {
		// 状态列表
		List<String> availableStatus = new ArrayList<String>();
		// 待审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
		// 已审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
		// 反审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT);
		ldpExternalBillDto.setList(availableStatus);
		// 非中转外发
		ldpExternalBillDto.setTransferExternal(PartiallineConstants.IS_TRANSFER_EXTERNAL_NO);
		
		//269701--lln--2015-10-26 begin
		//一票多件时，流水号跟外发单号一一对应，为了不影响历史数据，在此追加判断
		//如果流水号为空，按照运单查询
		if(StringUtils.isEmpty(ldpExternalBillDto.getSerialNo())){
			@SuppressWarnings("unchecked")
			// 执行查询
			List<LdpExternalBillDto> tempList= this.getSqlSession().selectList(
					prefix + "queryExternalBillByWaybillNoForChange", ldpExternalBillDto);
			if (CollectionUtils.isNotEmpty(tempList)) {
				return tempList.get(0);
			} else {
				return null;
			}
		}else{
			//如果流水号不为空，按照运单+流水号查询
			@SuppressWarnings("unchecked")
			// 执行查询
			List<LdpExternalBillDto> tempList= this.getSqlSession().selectList(
					prefix + "queryExternalBillByWaybillNoSerialNoForChange", ldpExternalBillDto);
			if (CollectionUtils.isNotEmpty(tempList)) {
				return tempList.get(0);
			} else {
				return null;
			}
		}
		//269701--lln--2015-10-26 end
	}

	
	@Override
	public long queryLdpExternalBillCountByWaybillNo(String waybillNo) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("waybillNo", waybillNo);
		params.put("notTransferExternal", PartiallineConstants.IS_TRANSFER_EXTERNAL_NO);
		params.put("auditStatus", PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID);
		return (Long) this.getSqlSession().selectOne(prefix + "queryLdpExternalBillCountByWaybillNo", params);
	}

	@Override
	public Long queryCountIfExistValidLdpExternalBillForStl(LdpExternalBillDto ldpExternalBillDto) {
		
		return (Long) this.getSqlSession().selectOne(prefix + "queryCountIfExistValidLdpExternalBillForStl", ldpExternalBillDto);
	}
	
	@Override
	public Long queryCountHasAuditedLdpExternalBill(LdpExternalBillDto ldpExternalBillDto){
		return (Long) this.getSqlSession().selectOne(prefix + "queryCountHasAuditedLdpExternalBill", ldpExternalBillDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LdpExternalBillDto> queryExternalBillListForLdpSign(String waybillNo) {
		LdpExternalBillDto ldpExternalBillDto = new LdpExternalBillDto();
		ldpExternalBillDto.setWaybillNo(waybillNo);
//		ldpExternalBillDto.setAuditStatus(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
		ldpExternalBillDto.setTransferExternal(PartiallineConstants.IS_TRANSFER_EXTERNAL_NO);
		
		return this.getSqlSession().selectList(prefix + "queryExternalBillListForLdpSign", ldpExternalBillDto);
	}

	/**
	 * @author nly
	 * @date 2014年9月3日 上午9:02:54
	 * @function 更新落地配外发单是否上报OA字段
	 * @param billId
	 * @param isReport
	 * @return
	 */
	@Override
	public int updateReportOAByPrimaryKey(String billId, String isReport) {
		Map<String,String> parameter = new HashMap<String,String>();
		parameter.put("billId", billId);
		parameter.put("isReport", isReport);
		return this.getSqlSession().update(prefix + "updateReportOAByPrimaryKey", parameter);
	}
	/**
	 * @author nly
	 * @date 2015年1月19日 下午3:50:10
	 * @function 新增运单号流水号至中间表，用于并发控制
	 * @param waybillNo
	 * @param serialNo
	 * @return
	 */
	@Override
	public int addUnInputWaybillNo(String id,String waybillNo,String serialNo) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", id);
		map.put("waybillNo", waybillNo);
		map.put("serialNo", serialNo);
		return this.getSqlSession().insert(prefix + "addUnInputWaybillNo", map);
	}
	/**
	 * @author nly
	 * @date 2015年1月20日 上午9:41:04
	 * @function 更新中间表中的状态
	 * @param waybillNo
	 * @param serialNo
	 */
	@Override
	public int updateUnInputWaybillNo(String waybillNo, String serialNo) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("waybillNo", waybillNo);
		map.put("serialNo", serialNo);
		return this.getSqlSession().update(prefix + "updateUnInputWaybillNo", map);
	}
	/**
	 * @author nly
	 * @date 2015年1月20日 上午11:05:54
	 * @function 更新isAdd
	 * @param waybillNo
	 * @param string
	 */
	@Override
	public void updateIsAdd(String waybillNo, String serialNo) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("waybillNo", waybillNo);
		map.put("serialNo", serialNo);
		this.getSqlSession().update(prefix + "updateIsAdd", map);
	}
	/**
	 * @author chigo
	 * @date 2015年1月29日 下午5:10:56
	 * @function 生成外发单号
	 * @param waybillNo
	 * @param serialNo
	 * @return
	 */
	@Override
	public String createExternalBillNo(String waybillNo,String serialNo){
		Map<String, String> params = new HashMap<String, String>();
		params.put("waybillNo", waybillNo);
		params.put("serialNo", serialNo);
		 return (String)this.getSqlSession().selectOne(prefix+"createExternalBillNo", params);
	 }
	/**
	 * 通过运单号查询外发单信息列表
	 * @author zwd 200968
	 * @date 2015-04-24 
	 * @param waybillNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LdpExternalBillDto> queryExternalBillListByWaybillNo(
			String waybillNo) {
		LdpExternalBillDto ldpExternalBillDto = new LdpExternalBillDto();
		ldpExternalBillDto.setWaybillNo(waybillNo);
		return this.getSqlSession().selectList(prefix + "queryExternalBillListByWaybillNo", ldpExternalBillDto);
	}

	/**
	 * @date 2015-12-23
	 * @author 269701--lln
	 * 根据运单号查询 交接单流水号表，得到第一个流水号
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryFirstSerialNoByWayBill(String waybillNo) {
		//查询交接单流水号表
		List<LdpExternalBillDto> serialNoList=this.getSqlSession().selectList(prefix + "queryFirstSerialNoByWayBill", waybillNo);
		return serialNoList.get(0).getSerialNo();
		
	}

	/**
	 * 按照运单号查找外发单号和外发公司  zwd 200968 2016-2-24
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LdpExternalBillDto> queryLdpExternalBillNoList(
			List<String> list) {
		return this.getSqlSession().selectList(prefix + "queryLdpExternalBillNoList", list);
	}
	
}