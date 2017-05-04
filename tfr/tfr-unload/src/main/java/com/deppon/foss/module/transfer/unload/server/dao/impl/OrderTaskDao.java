 package com.deppon.foss.module.transfer.unload.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.unload.api.server.dao.IOrderTaskDao;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OtHandOverBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QuerySerialInfoBywaybillNoDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QuerySerialListByTskNumDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 点单任务dao类
 * @author chenlei
 * @date 2015-12-12 上午10:22:31
 */
public class OrderTaskDao extends iBatis3DaoImpl implements IOrderTaskDao{

	/**
	 * mapper文件命名空间
	 */
	private static final String NAMESPACE = "foss.unload.ordertask.";
	
	/**
	 * 根据交接单号获取交接单信息list
	 * @author 272681-foss-chenlei
	 * @date 2015-12-14
	 */
	@SuppressWarnings("unchecked")
	public List<OtHandOverBillDetailEntity> queryHandOverBillInfoByNo(
			String handoverNo) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"queryHandOverBillInfo", handoverNo);
	}
	
	/**
	 * 新增点单任务基本信息
	 * @author 272681-foss-chenlei
	 * @date 2012-12-24
	 */
	@Override
	public int addOrderTaskBasicInfo(OrderTaskEntity baseEntity) {
		//新增点单任务基本信息
		this.getSqlSession().insert(NAMESPACE+"saveOrderTaskBaseInfo",baseEntity);
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 新增点单任务明细
	 * @author 272681-foss-chenlei
	 * @date 2015-12-24 
	 */
	@Override
	public int addOrderTaskBillDetailList(
			List<OtHandOverBillDetailEntity> billDetailList) {
		//新增点单任务的单据信息
		this.getSqlSession().insert(NAMESPACE+"saveOrderBillDetailList",billDetailList);
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 新增点单任务流水
	 * @author 272681-foss-chenlei
	 * @date 2015-12-24 
	 */
	@Override
	public int addOrderTaskSerialNoDetailList(
			List<OrderSerialNoDetailEntity> serialNoDetailList) {
		//新增点单任务的单据信息
		this.getSqlSession().insert(NAMESPACE+"saveOrderSerialNoDetailList",serialNoDetailList);
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 查询点单任务(分页)
	 * @author 272681-foss-chenlei
	 * @date 2015-12-25
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderTaskDto> queryOrderTask(OrderTaskDto orderTaskDto,
			int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryOrderTask", orderTaskDto, rowBounds);
	}

	/**
	 * 查询点单任务总数
	 * @author 272681-foss-chenlei
	 * @date 2015-12-25 
	 */
	@Override
	public Long getTotCount(OrderTaskDto orderTaskDto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getTotCount", orderTaskDto);
	}
	
	/**
	 * 通过交接单号查询点单任务
	 * @author 272681-foss-chenlei 
	 * @date 2015-12-14 
	 */
	@SuppressWarnings("unchecked")
	public List<OtHandOverBillDetailEntity> queryOrderTaskByHandoverNo(String handoverNo){
		return this.getSqlSession().selectList(NAMESPACE+"queryOrderTaskByHandoverNo", handoverNo);
	}
	
	/**
	 * 根据点单任务号获取任务基本信息
	 * @author 272681-foss-chenlei
	 * @date 2015-12-26 
	 */
	@Override
	public OrderTaskEntity queryOrderTaskBaseInfoByNo(String orderTaskNo) {
		@SuppressWarnings("unchecked")
		List<OrderTaskEntity> entityList = this.getSqlSession().selectList(NAMESPACE+"queryOrderTaskBaseInfoByNo", orderTaskNo);
		//返回查询结果
		if(CollectionUtils.isNotEmpty(entityList)){
			return entityList.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 根据点单任务号获取其下单据列表
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 上午10:21:24
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OtHandOverBillDetailEntity> queryOrderTaskBillDetailListByNo(String orderTaskNo) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"queryOrderTaskBillDetailListByNo", orderTaskNo);
	}
	
	/**
	 * 传入交接单编号和运单号，获取流水号信息
	 * @author 272681-foss-chenlei
	 * @date 2015-12-28
	 */
	@SuppressWarnings("unchecked")
	public List<OrderSerialNoDetailEntity> queryOrderTaskSerialNoListByBillNo(String id) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"queryOrderTaskSerialNoListByBillNo", id);
	}

	/**
	 * 添加多货时，输入的运单号获取流水号
	 * @author 272681-foss-chenlei
	 * @date 2015-12-30
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QuerySerialInfoBywaybillNoDto> queryValidateWaybillNoAndSerialNo(
			String waybillNo) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"queryValidateWaybillNoAndSerialNo", waybillNo);
	}
	
	/**
	 * 处理点单任务时，提交数据
	 * @author 272681-foss-chenlei
	 * @date 2016-01-04 
	 */
	@Override
	public int updateOrderTaskBasicInfo(OrderTaskEntity entity) {
		//更新卸车任务基本信息
		this.getSqlSession().update(NAMESPACE+"updateOrderTaskBasicInfo", entity);
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 根据ID集合批量更新点单任务流水信息状态
	 * @author 272681-foss-chenlei
	 * @date 2016-01-04
	 */
	@Override
	public int updateOrderSerialReportTypeInfo(List<String> idList,String reportType,Date modifyTime,Date createTime) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("reportType", reportType);
		dataMap.put("idList", idList);
		dataMap.put("modifyTime", modifyTime);
		dataMap.put("createTime", createTime);
		return this.getSqlSession().update(NAMESPACE + "updateOrderSerialInfo",dataMap);
	}
	
	/**
	 * 根据ID集合批量更新点单任务明细
	 * @author 272681-foss-chenlei
	 * @date 2016-01-05
	 */
	@Override
	public int updateOrderTaskDetailById(String id,String orderGoodsQty) {
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("id", id);
		params.put("orderGoodsQty", orderGoodsQty);
		return this.getSqlSession().update(NAMESPACE + "updateOrderTaskDetailById",params);
	}

	
	/**
	 * 处理点单任务界面添加多货流水
	 * @author 272681-foss-chenlei
	 * @date 2016-01-07 
	 */
	@Override
	public int insertMoreSerialNoList(List<OrderSerialNoDetailEntity> moreList) {
		this.getSqlSession().insert(NAMESPACE+"insertMoreSerialNo",moreList);
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 处理点单任务界面添加多货单据明细
	 * @author 272681-foss-chenlei
	 * @date 2016-01-07 
	 */
	@Override
	public int insertMoreBillNoInfo(OtHandOverBillDetailEntity bean) {
		this.getSqlSession().insert(NAMESPACE+"insertMoreBillNoInfo",bean);
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuerySerialListByTskNumDto> queryOrderTaskSerialNo(
			String orderTaskNo) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(NAMESPACE + "querySerialInfoBywaybillNoDto",orderTaskNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderTaskEntity> queryUnfinishOrderTask() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(NAMESPACE + "queryUnfinishOrderTask");
	}

	/**
	 * 处理点单任务界面添加多货时查询是否已经添加过
	 * @author 272681-foss-chenlei
	 * @date 2016-01-26
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderSerialNoDetailEntity> queryMoreSerialNoInfoByWaybillNo(
			String waybillNo, String handoverNo, String orderTaskNo) {
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("waybillNo", waybillNo);
		params.put("handoverNo", handoverNo);
		params.put("orderTaskNo", orderTaskNo);
		return this.getSqlSession().selectList(NAMESPACE + "queryMoreSerialNoInfoByWaybillNo",params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderSerialNoDetailEntity> queryOrderTaskMoreSerialNoListByNo(
			String orderTaskNo, String waybillNo) {
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("orderTaskNo", orderTaskNo);
		params.put("waybillNo", waybillNo);
		return this.getSqlSession().selectList(NAMESPACE + "queryOrderTaskMoreSerialNoListByNo",params);
	}
	
	/**
	 * pda扫描任务上传，根据运单号和流水号判断是否存在于点单任务流水明细中
	 * @author 272681-foss-chenlei
	 * @date 2016-01-25
	 */
	@Override
	public OrderSerialNoDetailEntity queryOrderTaskSerialNoBaseInfoByNo(
			String waybillNo, String serialNo,String orderTaskNo) {
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("waybillNo", waybillNo);
		params.put("serialNo", serialNo);
		params.put("orderTaskNo", orderTaskNo);
		@SuppressWarnings("unchecked")
		List<OrderSerialNoDetailEntity> entityList = this.getSqlSession().selectList(NAMESPACE + "queryOrderTaskSerialNoBaseInfoByNo", params);
		//返回查询结果
		if(CollectionUtils.isNotEmpty(entityList)){
			return entityList.get(0);
		}else{
			return null;
		}
	}

	/**
	 * pda扫描任务上传，根据运单号和点单任务编号点查询单任务明细
	 * @author 272681-foss-chenlei
	 * @date 2016-01-26
	 */
	@Override
	public OtHandOverBillDetailEntity queryOrderTaskDetailByNo(
			String orderTaskNo, String waybillNo) {
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("orderTaskNo", orderTaskNo);
		params.put("waybillNo", waybillNo);
		@SuppressWarnings("unchecked")
		List<OtHandOverBillDetailEntity> entityList = this.getSqlSession().selectList(NAMESPACE + "queryOrderTaskDetailByNo", params);
		//返回查询结果
		if(CollectionUtils.isNotEmpty(entityList)){
			return entityList.get(0);
		}else{
			return null;
		}
	}

	/**
	 * pda扫描任务上传，根据id 查询点单任务明细
	 * @author 272681-foss-chenlei
	 * @date 2016-01-26
	 */
	@Override
	public OtHandOverBillDetailEntity queryOrderTaskDetailById(String id) {	
		//返回查询结果
		@SuppressWarnings("unchecked")
		List<OtHandOverBillDetailEntity> entityList =  this.getSqlSession().selectList(NAMESPACE+"queryOrderTaskDetailById", id);
		//返回查询结果
		if(CollectionUtils.isNotEmpty(entityList)){
			return entityList.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 根据明细ID查询对应点单任务流水 数量 
	 * @creater 272681 2016-4-6 14:36:41
	 * @param orderDetailID
	 * @return
	 */
	public List<QuerySerialListByTskNumDto> querySerialCountsByDetailID(List<String> idList){
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("detailIDList", idList);
		return this.getSqlSession().selectList(NAMESPACE + "querySerialCountsByDetailID",dataMap);
	}

	@Override
	public Long queryOrderTaskByHandoverNoStrs(String handOverNoStrs) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("handOverNoStrs", handOverNoStrs);
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryOrderTaskByHandoverNoStrs",dataMap );
	}
}
