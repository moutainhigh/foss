package com.deppon.foss.module.transfer.unload.server.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadService;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.server.dao.IOrderTaskDao;
import com.deppon.foss.module.transfer.unload.api.server.service.IOrderTaskService;
import com.deppon.foss.module.transfer.unload.api.shared.define.OrderConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OtHandOverBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderTaskAddnewDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderTaskModifyDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderTaskMoreDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QuerySerialInfoBywaybillNoDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QuerySerialListByTskNumDto;
import com.deppon.foss.module.transfer.unload.api.shared.exception.OrderTaskException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;



public class OrderTaskService implements IOrderTaskService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderTaskService.class);
	/**
	 * 本模块dao
	 */
	private IOrderTaskDao orderTaskDao;
	
	/**
	 * 用于获取上级组织
	 */
	private ILoadService loadService;
	
	/**
	 * 交接单service，用于获取其下运单号、流水号
	 */
	private IHandOverBillService handOverBillService;
	
	/**
	 * 生成点单任务号
	 */
	private ITfrCommonService tfrCommonService;
	
	public void setOrderTaskDao(IOrderTaskDao orderTaskDao) {
		this.orderTaskDao = orderTaskDao;
	}
	
	public void setLoadService(ILoadService loadService) {
		this.loadService = loadService;
	}
	
	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}
	
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}	
	
	/**
	 * 根据交接单号获取交接单信息list
	 * @author 272681-foss-chenlei
	 * @date 2015-12-14 下午10:36:23
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOrderTaskService#queryHandOverBillInfoByNo(List)
	 */
	public  List<OtHandOverBillDetailEntity> queryHandOverBillInfoByNo(
			String handoverNo){
		List<OtHandOverBillDetailEntity> list = new ArrayList<OtHandOverBillDetailEntity>();
		List<OtHandOverBillDetailEntity> list1 = new ArrayList<OtHandOverBillDetailEntity>();
		
		list1 = orderTaskDao.queryOrderTaskByHandoverNo(handoverNo);
		//交接单是否生成点单任务
		if(list1.size() == 0){
		    list = orderTaskDao.queryHandOverBillInfoByNo(handoverNo);
		}else{
			throw new OrderTaskException("交接单号"+handoverNo+"已经在"+list1.get(0).getOrderTaskNo()+"点单任务中添加!");
		}
		return list;
	
	}
	
	/**
	 * 点单任务界面 传入交接单编号和运单号，获取流水号
	 * @author 272681-foss-chenlei
	 * @date 2015-12-23 上午9:22:31
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOrderTaskService#querySerialNoListByHandOverBillNoAndWaybillNo(String, String)
	 */
	@Override
	public List<HandOverBillSerialNoDetailEntity> querySerialNoListByHandOverBillNoAndWaybillNo(
			String handOverBillNo, String waybillNo) {
		return handOverBillService.getHandOverBillSerialNoDetailsByWayBillNo(waybillNo, handOverBillNo);
	}

	/**
	 * 新增点单任务信息
	 * @author 272681-foss-chenlei
	 * @date 2015-12-23 
	 */
	public String addOrderTask(OrderTaskAddnewDto addnewDto){
		//获取基本信息
		OrderTaskEntity baseEntity = outPutBaseEntity(addnewDto);
		//获取单据信息列表
		List<OtHandOverBillDetailEntity> billDetailList = outPutBillDetailEntityList(addnewDto.getBillList(),baseEntity);
		List<OrderSerialNoDetailEntity> serialNoDetailList = new ArrayList<OrderSerialNoDetailEntity>();
		if(billDetailList.size()==0){
			return baseEntity.getOrderTaskNo();
		}
		
		StringBuffer sb = new StringBuffer();
		for(OtHandOverBillDetailEntity entity : billDetailList){
			sb.append("'"+entity.getHandoverNo()+"'").append(",");
		}
		String handOverNoStrs = sb.toString();
		//去掉最后一个逗号“,”
		handOverNoStrs=handOverNoStrs.substring(0, handOverNoStrs.length()-1);
		Long counts = orderTaskDao.queryOrderTaskByHandoverNoStrs(handOverNoStrs);
		if(counts>0){
			throw new OrderTaskException("交接单号已经在点单任务中添加!");
		}
		
		
		for(OtHandOverBillDetailEntity entity : billDetailList){
			//获取交接单号
			String handOverBillNo = entity.getHandoverNo();
			//获取运单号
			String waybillNo = entity.getWaybillNo();
			//获取流水列表
			List<HandOverBillSerialNoDetailEntity> oldSerialNoList = handOverBillService.getHandOverBillSerialNoDetailsByWayBillNo(waybillNo, handOverBillNo);
			//遍历交接单中运单下的流水号
			for(HandOverBillSerialNoDetailEntity oldSerialNo : oldSerialNoList){
				OrderSerialNoDetailEntity tempSerialNo = new OrderSerialNoDetailEntity();
				tempSerialNo.setId(UUIDUtils.getUUID());
				tempSerialNo.setSerialNo(oldSerialNo.getSerialNo());
				tempSerialNo.setOrderReportType(OrderConstants.ORDER_REPORT_TYPE_LOSE);//默认少货
				tempSerialNo.setHandoverNo(handOverBillNo);
				tempSerialNo.setWaybillNo(waybillNo);
				tempSerialNo.setOrderTaskDetailId(entity.getId());
				serialNoDetailList.add(tempSerialNo);
			}
		}
		//新增点单任务基本信息
		this.addOrderTaskBasicInfo(baseEntity);
		//新增点单任务明细
		this.addOrderTaskBillDetailList(billDetailList);
		//新增点单任务流水
		this.addOrderTaskSerialNoDetailList(serialNoDetailList);
		//返回卸车任务编号
		return baseEntity.getOrderTaskNo();
	}
	
	/**
	 * 新增点单任务明细
	 * @author 272681-foss-chenlei
	 * @date 2015-12-24
	 */
	@Override
	public int addOrderTaskBillDetailList(List<OtHandOverBillDetailEntity> billDetailList) {
		//插入点单任务明细表
		orderTaskDao.addOrderTaskBillDetailList(billDetailList);
		return FossConstants.SUCCESS;
	}
	
	
	
	/**
	 * 私有方法，构造卸车任务基本信息实体
	 * @author 272681-foss-chenlei
	 * @date 2015-12-24 
	 */
	private OrderTaskEntity outPutBaseEntity(OrderTaskAddnewDto addnewDto){
		//取出一条单据信息
		OtHandOverBillDetailEntity billEntity = addnewDto.getBillList().get(0);
		//创建基本信息
		OrderTaskEntity baseEntity = new OrderTaskEntity();
		//获取当前部门编码、名称
		String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		//获取车牌号
		String vehicleNo = addnewDto.getVehicleNo();
		//ID
		baseEntity.setId(UUIDUtils.getUUID());
		
		/*
		 * 生成点单任务编号
		 */
		String taskNo = tfrCommonService.generateSerialNumberRequireNew(TfrSerialNumberRuleEnum.OC, orgCode);
		baseEntity.setOrderTaskNo(taskNo);
		//车牌号
		baseEntity.setVehicleNo(vehicleNo);
		//创建时间
		baseEntity.setCreateTime(new Date());
		//点单任务开始时间
		baseEntity.setOrderStartTime(new Date());
		//点单任务结束时间，新增时，此处为空
		//任务状态
		baseEntity.setOrderTaskState(OrderConstants.ASSIGN_ORDER_TASK_STATE_IN);
		//点单人code
		baseEntity.setOrderCode(addnewDto.getOrderCode());
		//点单人name
		baseEntity.setOrderName(addnewDto.getOrderName());
		//报告处理状态新增时为空
		//出发部门
		baseEntity.setDepartCode(billEntity.getDepartCode());
		baseEntity.setDepartName(billEntity.getDepartName());
		//到达部门
		baseEntity.setArriveCode(billEntity.getArriveCode());
		baseEntity.setArriveName(billEntity.getArriveName());
		//总票数
		baseEntity.setTotWaybillQty(addnewDto.getTotWaybillQty());
		//总件数
		baseEntity.setTotGoodsQty(addnewDto.getTotGoodsQty());
		//总重量
		baseEntity.setTotWeight(addnewDto.getTotWeight());
		//总体积
		baseEntity.setTotVolume(addnewDto.getTotVolume());
		//创建部门code
		baseEntity.setCreateOrgCode(addnewDto.getCreateOrgCode());
		//创建部门name
		baseEntity.setCreateOrgName(addnewDto.getCreateOrgName());
		//异常备注，新增时，此处为空
		return baseEntity;
	}
	
	
	/**
	 * 私有方法，构造待插入的点单任务明细列表
	 * @author 272681-foss-chenlei
	 * @date 2015-12-24 
	 */
	private List<OtHandOverBillDetailEntity> outPutBillDetailEntityList(List<OtHandOverBillDetailEntity> billList,OrderTaskEntity baseEntity){
		List<OtHandOverBillDetailEntity> newBillList = new ArrayList<OtHandOverBillDetailEntity>();
		for(OtHandOverBillDetailEntity bill : billList){
			//ID
			bill.setId(UUIDUtils.getUUID());
			//点单件数
			bill.setOrderGoodsQty(BigDecimal.ZERO);
			//点单任务编号
			bill.setOrderTaskNo(baseEntity.getOrderTaskNo());
			//配合BI，新增创建时间
			bill.setCreateDate(new Date());
			//配合BI，新增修改时间时间
			bill.setModifyDate(new Date());
			newBillList.add(bill);
		}
		return newBillList;
	}

	/**
	 * 新增点单任务流水
	 * @author 272681-foss-chenlei
	 * @date 2015-12-28
	 */
	@Override
	public int addOrderTaskSerialNoDetailList(List<OrderSerialNoDetailEntity> serialNoDetailList) {
		//插入点单任务明细表
		orderTaskDao.addOrderTaskSerialNoDetailList(serialNoDetailList);
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 新增卸车任务基本信息
	 * @author 272681-foss-chenlei
	 * @date 2015-12-24
	 */
	@Override
	public int addOrderTaskBasicInfo(OrderTaskEntity baseEntity) {
		//新增卸车任务
		orderTaskDao.addOrderTaskBasicInfo(baseEntity);
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 查询点单任务(分页)
	 * @author 272681-foss-chenlei
	 * @date 2015-12-25
	 */
	@Override
	public List<OrderTaskDto> queryOrderTask(OrderTaskDto orderTaskDto,
			int limit, int start) {
		
		return orderTaskDao.queryOrderTask(orderTaskDto, limit, start);
	}

	/**
	 * 查询点单任务总数
	 * @author 272681-foss-chenlei
	 * @date 2015-12-25 
	 */
	@Override
	public Long getTotCount(OrderTaskDto orderTaskDto) {
		
		return orderTaskDao.getTotCount(orderTaskDto);
	}
	
	
	/**
	 * 获取上级组织实体
	 * @author 272681-foss-chenlei
	 * @date 2015-12-21 
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IOrderTaskService#querySuperOrgByOrgCode(java.lang.String)
	 */
	@Override
	public OrgAdministrativeInfoEntity querySuperOrgByOrgCode(String orgCode) {
		if(StringUtils.isBlank(orgCode)){
			orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
		}
		OrgAdministrativeInfoEntity org = loadService.querySuperiorOrgByOrgCode(orgCode);
		if(org == null){
			throw new TfrBusinessException("获取当前部门的上级组织失败(包括营业部、派送部、外场、总调)！");
		}
		return loadService.querySuperiorOrgByOrgCode(orgCode);
	}

	/**
	 * 根据点单任务号获取任务基本信息
	 * @author 272681-foss-chenlei
	 * @date 2015-12-26 
	 */
	@Override
	public OrderTaskEntity queryOrderTaskBaseInfoByNo(String orderTaskNo) {
		//返回查询结果
		return orderTaskDao.queryOrderTaskBaseInfoByNo(orderTaskNo);
	}

	/**
	 * 根据点单任务编号获取其下单据列表
	 * @author 272681-foss-chenlei
	 * @date 2015-12-28
	 */
	@Override
	public List<OtHandOverBillDetailEntity> queryOrderTaskBillDetailListByNo(String orderTaskNo) {
		//返回查询结果
		return orderTaskDao.queryOrderTaskBillDetailListByNo(orderTaskNo);
	}
	
	/**
	 * 传入交接单编号和运单号，获取流水号信息
	 * @author 272681-foss-chenlei
	 * @date 2015-12-28
	 */
	public List<OrderSerialNoDetailEntity> queryOrderTaskSerialNoListByBillNo(String id) {
		//返回查询结果
		return orderTaskDao.queryOrderTaskSerialNoListByBillNo(id);
	}

	@Override
	public List<QuerySerialInfoBywaybillNoDto> queryValidateWaybillNoAndSerialNo(
			String waybillNo) {
		//返回查询结果
		return orderTaskDao.queryValidateWaybillNoAndSerialNo(waybillNo);
	}
	
	/**
	 * 处理点单任务时，提交数据
	 * @author 272681-foss-chenlei
	 * @date 2016-01-04 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int updateOrderTask(OrderTaskModifyDto modifyDto) {
		//获取传入的点单任务编号
		String orderTaskNo = modifyDto.getOrderTaskNo();
		//获取传入的车牌号
		String vehicleNo = modifyDto.getVehicleNo();
		//获取传入的修改的流水明细list
		List<OrderSerialNoDetailEntity> addedBillList = modifyDto.getAddedBillList();
		//获取传入的修改的点单任务明细list
		List<OtHandOverBillDetailEntity> addedBillDetailList = modifyDto.getAddedBillDetailList();
		//根据ID，获取修改前的实体
		OrderTaskEntity oldEntity = this.queryOrderTaskBaseInfoByNo(orderTaskNo);
		//如果点单任务不存在，则无法修改
		if(oldEntity == null){
		//抛业务异常，中断
		      throw new OrderTaskException("点单任务不存在！");
		}
		//如果点单任务已结束，则无法修改
		if(StringUtils.equals(oldEntity.getOrderTaskState(), OrderConstants.ASSIGN_ORDER_TASK_STATE_END)){
		//抛业务异常，中断
		      throw new OrderTaskException("点单任务已经结束，无法修改！");
		}
		try{
			//构造实体，更新基本信息
			OrderTaskEntity entity = new OrderTaskEntity();
			//点单任务编号
			entity.setOrderTaskNo(orderTaskNo);
			//点单任务车牌号
			entity.setVehicleNo(vehicleNo);
			//点单任务状态
			entity.setOrderTaskState(OrderConstants.ASSIGN_ORDER_TASK_STATE_END);
			//点单任务完成时间
			entity.setOrderEndTime(new Date());
			//修改时间
			entity.setModifyTime(new Date());
			//更新基本信息
			this.updateOrderTaskBasicInfo(entity);
			//更新流水信息
			List<String> idList = new ArrayList<String>();
			for(OrderSerialNoDetailEntity bean : addedBillList){
				if(OrderConstants.ORDER_REPORT_TYPE_NORMAL.equals(bean.getOrderReportType())){
					idList.add(bean.getId());
				}
			}
			if(idList.size()>0){
				updateOrderSerialReportTypeInfo(idList,OrderConstants.ORDER_REPORT_TYPE_NORMAL,new Date(),null);
			}
			//更新点单任务明细
			for(OtHandOverBillDetailEntity bean : addedBillDetailList){
				String id = bean.getId();
                String orderGoodsQty = bean.getOrderGoodsQty().toString();
				updateOrderTaskDetailById(id,orderGoodsQty);
			}
		}
		catch (Exception e) {
			return FossConstants.FAILURE;
			// TODO: handle exception
		}
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 根据点单任务号更新点单任务基本信息
	 * @author 272681-foss-chenlei
	 * @date 2016-01-04 
	 */
	@Override
	public int updateOrderTaskBasicInfo(OrderTaskEntity entity) {
		//更新点单任务基本信息
		orderTaskDao.updateOrderTaskBasicInfo(entity);
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 根据ID集合批量更新点单任务流水信息状态
	 * @author 272681-foss-chenlei
	 * @date 2016-01-04 
	 */
	public int updateOrderSerialReportTypeInfo(List<String> idList,String reportType,Date modifyTime,Date createTime) {
		//更新流水基本信息
		orderTaskDao.updateOrderSerialReportTypeInfo(idList,reportType,modifyTime,createTime);
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 根据ID更新点单任务明细点单件数
	 * @author 272681-foss-chenlei
	 * @date 2016-01-05
	 */
	public int updateOrderTaskDetailById(String id, String orderGoodsQty) {
		//更新卸车任务基本信息
		orderTaskDao.updateOrderTaskDetailById(id,orderGoodsQty);
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	/**
	 * 处理点单任务界面添加多货流水
	 * @author 272681-foss-chenlei
	 * @date 2016-01-07 
	 */
	@Override
	public String insertMoreSerialNo(
			OrderTaskMoreDto orderTaskMoreDto) {
		//点单任务号
		String orderTaskNo =  orderTaskMoreDto.getOrderTaskNo();
		//总票数
		Long totWaybillQty = orderTaskMoreDto.getTotWaybillQty();
		//多货流水list
		List<OrderSerialNoDetailEntity> moreSerialNoList = orderTaskMoreDto.getMoreSerialNoList();
		OtHandOverBillDetailEntity moreBillNo = orderTaskMoreDto.getMoreBillInfo();
		//将多货的运单明细插入点单任务明细表
		OtHandOverBillDetailEntity bilBean = outPutBillDetailEntity(moreBillNo,orderTaskNo);
		List<OrderSerialNoDetailEntity> moreList = new ArrayList<OrderSerialNoDetailEntity>();
		OrderTaskEntity orderEntity = new OrderTaskEntity();
		orderEntity.setTotWaybillQty(totWaybillQty);
		orderEntity.setOrderTaskNo(orderTaskNo);
		//插入多货点单任务明细
	    String waybillNo = moreBillNo.getWaybillNo();
	    String handoverNo = moreBillNo.getHandoverNo();
	    List<OrderSerialNoDetailEntity> list = orderTaskDao.queryMoreSerialNoInfoByWaybillNo(waybillNo,handoverNo,orderTaskNo);
		if(list.size() == 0){
			for(OrderSerialNoDetailEntity entity :moreSerialNoList){
				OrderSerialNoDetailEntity bean = new OrderSerialNoDetailEntity();
				bean.setId(UUIDUtils.getUUID());
				bean.setOrderTaskDetailId(bilBean.getId());
				bean.setWaybillNo(entity.getWaybillNo());
				bean.setSerialNo(entity.getSerialNo());
				bean.setOrderReportType(entity.getOrderReportType());
				moreList.add(bean);
			
			}
			//插入多货流水信息
		   orderTaskDao.insertMoreSerialNoList(moreList);
		   //插入多货明细
		   orderTaskDao.insertMoreBillNoInfo(bilBean);
		   //更新点单任务中的总票数
		   orderTaskDao.updateOrderTaskBasicInfo(orderEntity);
			//返回点单任务明细的id
			return bilBean.getId();
		}else{
		  //如果添加的运单号已经添加过了，就不再插入点单任务明细表,更新点单任务明细表点单件数
		  String id = list.get(0).getOrderTaskDetailId();
		  String orderGoodsQty = moreBillNo.getOrderGoodsQty().toString();
		  orderTaskDao.updateOrderTaskDetailById(id,orderGoodsQty);
		  for(OrderSerialNoDetailEntity entity :moreSerialNoList){
				OrderSerialNoDetailEntity bean = new OrderSerialNoDetailEntity();
				bean.setId(UUIDUtils.getUUID());
				bean.setOrderTaskDetailId(id);
				bean.setWaybillNo(entity.getWaybillNo());
				bean.setSerialNo(entity.getSerialNo());
				bean.setOrderReportType(entity.getOrderReportType());
				moreList.add(bean);
			}
		//插入多货流水信息
		orderTaskDao.insertMoreSerialNoList(moreList);
		//返回点单任务明细的id
		return id;
		}
	}

	
	/**
	 * 私有方法，添加多货时构造待插入的点单任务明细列表
	 * @author 272681-foss-chenlei
	 * @date 2016-01-11
	 */
	private OtHandOverBillDetailEntity outPutBillDetailEntity(OtHandOverBillDetailEntity moreBillNo,String orderTaskNo){
		        //将多货的运单明细插入点单任务明细表
				OtHandOverBillDetailEntity bilBean = new OtHandOverBillDetailEntity();
				//点单任务明细id
				bilBean.setId(UUIDUtils.getUUID());
				//点单任务编号
				bilBean.setOrderTaskNo(orderTaskNo);
				//多货的运单号
				bilBean.setWaybillNo(moreBillNo.getWaybillNo());
				//点单件数
				bilBean.setOrderGoodsQty(moreBillNo.getOrderGoodsQty());
				//运输性质
				bilBean.setTransportType(moreBillNo.getTransportType());
				//开单件数
				bilBean.setCreateBillQty(moreBillNo.getCreateBillQty());
				//修改时间
				bilBean.setModifyDate(new Date());
				//创建时间
				bilBean.setCreateDate(new Date());
		return bilBean;
	}
	
	/**
	 * 点击一键正常,获得流水信息和流水数量
	 * @author 272681-foss-chenlei
	 * @date 2016-01-10
	 * @updatetime 2016-4-6 14:40:28
	 */
	@Override
	public List<QuerySerialListByTskNumDto> queryOrderTaskSerialNo(
			String orderTaskNo) {
		/**1.根据任务号获取t_opt_order_task_serialno表  基本字段信息***/
		List<QuerySerialListByTskNumDto> list = orderTaskDao.queryOrderTaskSerialNo(orderTaskNo);
		if(list==null||list.size()==0){
			return list;
		}
		Set<String> serialSet = new HashSet<String>();
		for(QuerySerialListByTskNumDto bean:list){
		serialSet.add(bean.getOrderTaskDetailId());
		}
		List<String> detailIDList = new ArrayList<String>(serialSet);
		/**2.根据task_detail_id获取对应下有多少个 流水号 ***/
		List<QuerySerialListByTskNumDto> serialCountList = orderTaskDao.querySerialCountsByDetailID(detailIDList);
		//serialCountList转map 便于封装数据，节省循环次数
		Map<String,Long> serialCountMap = new HashMap<String,Long>();
		for(QuerySerialListByTskNumDto bean :serialCountList){
			serialCountMap.put(bean.getOrderTaskDetailId(),bean.getSerialcounts());
		}
		
		/**填充流水数量数据*/
		for(int i=0;i<list.size();i++){
			list.get(i).setSerialcounts(serialCountMap.get(list.get(i).getOrderTaskDetailId()));
		}
		return list;
	}

	/** 
	 * 超过4小时还没生成点单任务的自动完成
	 * @author 272681-foss-chenlei
	 * @date 2016-01-18
	 */
	public void orderTaskFinish() {
		try{
			List<OrderTaskEntity> unfinishEntity = orderTaskDao.queryUnfinishOrderTask();
			if(unfinishEntity.size()>0){
			for(OrderTaskEntity entity:unfinishEntity){
				entity.setOrderTaskState(OrderConstants.ASSIGN_ORDER_TASK_STATE_END);
				entity.setOrderEndTime(new Date());
				entity.setModifyTime(new Date());
				orderTaskDao.updateOrderTaskBasicInfo(entity);
			}
			}
		}catch(Exception e){
			LOGGER.error("自动完成点单任务处理异常： ",e);
		}
		
	}

	@Override
	public List<OrderSerialNoDetailEntity> queryOrderTaskMoreSerialNoListByNo(
			String orderTaskNo, String waybillNo) {
		
		return orderTaskDao.queryOrderTaskMoreSerialNoListByNo(orderTaskNo,waybillNo);
	}
	
	
}
