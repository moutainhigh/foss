package com.deppon.foss.module.transfer.unload.server.action;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.unload.api.server.service.IOrderTaskService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OtHandOverBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderTaskAddnewDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderTaskModifyDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderTaskMoreDto;
import com.deppon.foss.module.transfer.unload.api.shared.vo.OrderTaskVo;




public class OrderAction extends AbstractAction{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 前后台传参的VO对象
	 */
	private OrderTaskVo orderTaskVo = new OrderTaskVo();
	
	/**
	 * 该模块service
	 */
	private IOrderTaskService orderTaskService;
	
	public void setOrderTaskService(IOrderTaskService orderTaskService) {
		this.orderTaskService = orderTaskService;
	}
	
	public OrderTaskVo getOrderTaskVo() {
		return orderTaskVo;
	}

	public void setOrderTaskVo(OrderTaskVo orderTaskVo) {
		this.orderTaskVo = orderTaskVo;
	}

	public String queryTransferCode() {
		return returnSuccess();
	}
	
	/**
	 * 用于获取当前登录部门和当前登录人
	 * @author 272681-foss-chenlei
	 * @date 2015-12-21 
	 */
	@JSON
	public String orderTaskAddNewIndex(){
		try{
				CurrentInfo ci = FossUserContext.getCurrentInfo();
				orderTaskVo.setSuperOrgCode(ci.getCurrentDeptCode());
				orderTaskVo.setEmpCode(ci.getEmpCode());
			}catch(BusinessException e){
				return returnError(e);
			}
			return returnSuccess();
		}
		
	
	/**
	 * 传入交接单编号，获取交接单信息
	 * @author 272681-foss-chenlei
	 * @date 2015-12-12 上午9:22:31
	 */
	@JSON
	public String queryBillInfoByHandoverNo(){
		try{
		//获取交接单编号
		String handoverNo = orderTaskVo.getHandoverNo();
		//定义单据实体list交接单明细
		List<OtHandOverBillDetailEntity> billList = orderTaskService.queryHandOverBillInfoByNo(handoverNo);
		if(billList.size() != 0){
			orderTaskVo.setBillList(billList);
			orderTaskVo.setBillInfo(billList.get(0));
		}
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
		
	}
	
	/**
	 * 传入交接单编号和运单号，获取流水号
	 * @author 272681-foss-chenlei
	 * @date 2015-12-23 上午9:22:31
	 */
	public String querySerialNoListByHandOverBillNoAndWaybillNo(){
		//获取交接单编号
		String handOverBillNo = orderTaskVo.getHandoverNo();
		//获取运单号
		String waybillNo = orderTaskVo.getWaybillNo();
		orderTaskVo.setSerialNoList(orderTaskService.querySerialNoListByHandOverBillNoAndWaybillNo(handOverBillNo, waybillNo));
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 分配点单任务
	 * @author 272681-foss-chenlei
	 * @date 2015-12-23 
	 */
	public String addOrderTask(){
		//获取传入的新增的点单任务信息
		OrderTaskAddnewDto addnewDto = orderTaskVo.getAddnewDto();
		try{
			//新增点单任务的同时返回前台点单任务号
			String orderTaskNo = orderTaskService.addOrderTask(addnewDto);
			//返回前台点单任务号
			orderTaskVo.setOrderTaskNo(orderTaskNo);
			}catch(BusinessException e){
			//返回业务异常信息
				return returnError(e);
			}
				//返回success
				return returnSuccess();
	}
	
	/**
	 * 查询点单任务
	 * @author 272681-foss-chenlei
	 * @date 2015-12-25 
	 */
	public String queryOrderTask(){
		try {
			//查询点单任务(分页)
			List<OrderTaskDto> orderTaskDtoList = orderTaskService.queryOrderTask(orderTaskVo.getOrderTaskDto(), this.getLimit(), this.getStart());
			//查询结果的总记录数
			Long totCount = orderTaskService.getTotCount(orderTaskVo.getOrderTaskDto());
			this.setTotalCount(totCount);
			orderTaskVo.setOrderTaskDtoList(orderTaskDtoList);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 处理点单任务时，加载修改前的数据
	 * @author 272681-foss-chenlei
	 * @date 2015-12-26
	 */
	@JSON
	public String loadOrderTaskInfo(){
		//获取前台传入的点单任务编号
		String orderTaskNo = orderTaskVo.getOrderTaskNo();
		//基本信息
		orderTaskVo.setBaseEntity(orderTaskService.queryOrderTaskBaseInfoByNo(orderTaskNo));
		//单据列表
		orderTaskVo.setBillDetailList(orderTaskService.queryOrderTaskBillDetailListByNo(orderTaskNo));
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 传入交接单编号和运单号，获取流水号信息
	 * @author 272681-foss-chenlei
	 * @date 2015-12-28
	 */
	public String queryOrderTaskSerialNoListByBillNo(){
		//获取点单任务明细id
		String id = orderTaskVo.getId();
		
		orderTaskVo.setSerialNoDetailList(orderTaskService.queryOrderTaskSerialNoListByBillNo(id));
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 添加多货时，输入的运单号获取流水号
	 * @author 272681-foss-chenlei
	 * @date 2015-12-30
	 */
	@JSON
	public String queryValidateWaybillNoAndSerialNo(){
		try{
		//获取前台传入的运单号
		String waybillNo = orderTaskVo.getWaybillNo();
			orderTaskVo.setSerialNoInfoList(orderTaskService.queryValidateWaybillNoAndSerialNo(waybillNo));
		}catch(BusinessException e){
				//返回业务异常信息
				return returnError(e);
			}
			//返回success
			return returnSuccess();
	}
	
	/**
	 * 处理点单任务时，提交数据
	 * @author 272681-foss-chenlei
	 * @date 2016-01-04 
	 */
	@JSON
	public String updateOrderTask(){
		//获取前台传入的dto
		OrderTaskModifyDto orderTaskModifyDto = orderTaskVo.getOrderTaskModifyDto();
		try{
			//调用service，更新卸车任务信息
			orderTaskService.updateOrderTask(orderTaskModifyDto);
		}catch(BusinessException e){
			//返回业务异常信息
			return returnError(e);
		}
		//返回success
		return returnSuccess();
	}
	
	/**
	 * 处理点单任务界面添加多货流水
	 * @author 272681-foss-chenlei
	 * @date 2016-01-07 
	 */
	@JSON
	public String insertMoreSerialNo(){
	
		OrderTaskMoreDto orderTaskMoreDto = orderTaskVo.getOrderTaskMoreDto();
		try{
			String id = orderTaskService.insertMoreSerialNo(orderTaskMoreDto);
			orderTaskVo.setId(id);
			}catch(BusinessException e){
			//返回业务异常信息
				return returnError(e);
			}
				//返回success
				return returnSuccess();
	}
	
	/**
	 * 处理点单任务时，查询所有流水数据
	 * @author 272681-foss-chenlei
	 * @date 2016-01-10
	 */
	@JSON
	public String queryOrderTaskSerialNo(){
		try{
		//获取前台传入的点单任务编号
		String orderTaskNo = orderTaskVo.getOrderTaskNo();
		//流水基本信息
		orderTaskVo.setSerialDetailDtoList(orderTaskService.queryOrderTaskSerialNo(orderTaskNo));	
		//返回success
		}catch(BusinessException e){
			//返回业务异常信息
		return returnError(e);
		}
		return returnSuccess();
	}
	
	public String queryOrderTaskMoreSerialNoListByNo(){
		try{
		String orderTaskNo = orderTaskVo.getOrderTaskNo();
		String waybillNo = orderTaskVo.getWaybillNo();
		orderTaskVo.setSerialNoDetailList(orderTaskService.queryOrderTaskMoreSerialNoListByNo(orderTaskNo,waybillNo));
		//返回success
		}catch(BusinessException e){
			//返回业务异常信息
		return returnError(e);
		}
		return returnSuccess();
	}
}
