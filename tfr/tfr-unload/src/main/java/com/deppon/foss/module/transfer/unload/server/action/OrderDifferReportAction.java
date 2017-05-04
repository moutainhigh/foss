package com.deppon.foss.module.transfer.unload.server.action;

import java.util.List;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.unload.api.server.service.IOrderDifferReportService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderDifferReportEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderDifferReportDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderReportDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderReportSerialNoDto;
import com.deppon.foss.module.transfer.unload.api.shared.vo.OrderDifferReportVo;
/**
 * 
 * 点单差异报告Action
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:189284,date:2016-1-15 下午2:33:24,content:TODO </p>
 * @author 189284 
 * @date 2016-1-15 下午2:33:24
 * @since
 * @version
 */
public class OrderDifferReportAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private OrderDifferReportVo orderDifferReportVo;
	private IOrderDifferReportService orderDifferReportService ;	
	/**
	 * @param orderDifferReportService the orderDifferReportService to set
	 */
	public void setOrderDifferReportService(
			IOrderDifferReportService orderDifferReportService) {
		this.orderDifferReportService = orderDifferReportService;
	}
	/**
	 * @return  the orderDifferReportVo
	 */
	public OrderDifferReportVo getOrderDifferReportVo() {
		return orderDifferReportVo;
	}
	/**
	 * @param orderDifferReportVo the orderDifferReportVo to set
	 */
	public void setOrderDifferReportVo(OrderDifferReportVo orderDifferReportVo) {
		this.orderDifferReportVo = orderDifferReportVo;
	}
	/**
	 * 
	 * <p>点单 差异报告 查询</p> 
	 * @author 189284 
	 * @date 2016-1-5 下午4:47:37
	 * @return
	 * @see
	 */
	@JSON
	public String qureyOrderDifferReport(){
		try {
			OrderDifferReportEntity orderDifferReportEntity=orderDifferReportVo.getOrderDifferReportEntity();
			List<OrderDifferReportDto> orderDifferReportList =
					orderDifferReportService.qureyOrderDifferReport(orderDifferReportEntity,this.limit,this.start);
			orderDifferReportVo.setOrderDifferReportList(orderDifferReportList);
			Long totalCount=orderDifferReportService.qureyOrderDifferReportCount(orderDifferReportEntity);
			this.setTotalCount(totalCount);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	 * <p>根据订单编号查询差异报告</p> 
	 * @author 189284 
	 * @date 2016-1-5 下午4:48:01
	 * @return
	 * @see
	 */
	@JSON
	public String queryOrderReportDetailByNo(){
		try{
			List<OrderReportDetailDto> orderReportDetailList=
					orderDifferReportService.queryOrderReportDetailByNo(orderDifferReportVo.getReportNo());
			orderDifferReportVo.setOrderReportDetailList(orderReportDetailList);
			return returnSuccess();
		}catch(BusinessException e){
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	 * <p>根据 运单id查询 流水明细list</p> 
	 * @author 189284 
	 * @date 2016-1-15 下午2:34:41
	 * @return
	 * @see
	 */
	@JSON
	public String querySerialNoListByDetailId(){
		try{
			List<OrderReportSerialNoDto> orderReportSerialNoList=
					orderDifferReportService.querySerialNoListByDetailId(orderDifferReportVo.getId());
			orderDifferReportVo.setOrderReportSerialNoList(orderReportSerialNoList);
			return returnSuccess();
		}catch(BusinessException e){
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	 * <p>根据运单信息处理 点单差异报告</p> 
	 * @author 189284 
	 * @date 2016-1-15 下午2:35:12
	 * @return
	 * @see
	 */
	@JSON
	public String handleOrderDifferReportByWaybillNo(){
		try {
			orderDifferReportService.handleOrderDifferReportByWaybillNo(orderDifferReportVo);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}
	
	@JSON
	public String handleOrderDifferReportBySerialNo(){
		try {
			orderDifferReportService.handleOrderDifferReportBySerialNo(orderDifferReportVo);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
	}
}
