package com.deppon.foss.module.settlement.pay.server.action;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IPDAPayInReportBillCreateService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DriverCollectionRptDto;
import com.deppon.foss.module.settlement.pay.api.shared.vo.DriverCollectionRptVo;
import com.deppon.foss.util.DateUtils;

/**
 * 生成PDA收入报表，和PDA收入报表明细
 * 
 * @author 045738-foss-maojianqiang
 * @date 2012-12-18 上午11:51:41
 */
public class PDAPayInReportBillCreateAction extends AbstractAction {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -1651143907222707097L;
	/**
	 * 注入日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(PDAPayInReportBillCreateAction.class);
	
	/**
	 * pda缴款vo
	 */
	private DriverCollectionRptVo vo;
	/**
	 * 注入PDA司机缴款服务
	 */
	private IPDAPayInReportBillCreateService pDAPayInReportBillCreateService;

	/**
	 * 
	 * 查询司机PDA接送货明细
	 * 
	 * @author 045738-foss-maojianqiang
	 * @param
	 * @date 2012-12-18 上午11:53:18
	 * @return
	 */
	@JSON
	public String queryReceiptSendGoodsInfo() {
		try{
			//如果dto为空，则抛出异常
			if(vo.getDto()==null){	
				throw new SettlementException("查询参数不能为空！");
			}
			//获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//调用service进行查询
			DriverCollectionRptDto resultDto = pDAPayInReportBillCreateService.queryReceiptSendGoodsInfo(vo.getDto(), currentInfo);
			//将dto放到vo中，传递到前台
			vo.setDto(resultDto);
			return returnSuccess();
		}catch(BusinessException e){
			logger.error(e.getMessage(),e);
			return returnError(e);
		}
	}
	
	/**
	 * njs 送货
	 */
	
	public String querySendGoodsInfo() {
		try{
			//如果dto为空，则抛出异常
			if(vo.getDto()==null){	
				throw new SettlementException("查询参数不能为空！");
			}
			//获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//调用service进行查询
			DriverCollectionRptDto resultDto = pDAPayInReportBillCreateService.querySendGoodsInfo(vo.getDto(), currentInfo);
			//将dto放到vo中，传递到前台
			vo.setDto(resultDto);
			return returnSuccess();
		}catch(BusinessException e){
			logger.error(e.getMessage(),e);
			return returnError(e);
		}
	}
	
	
	/**
	 * njs 送货
	 */
	
	public String queryReceiptGoodsInfo() {
		try{
			//如果dto为空，则抛出异常
			if(vo.getDto()==null){	
				throw new SettlementException("查询参数不能为空！");
			}
			//获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//调用service进行查询
			DriverCollectionRptDto resultDto = pDAPayInReportBillCreateService.queryReceiptGoodsInfo(vo.getDto(), currentInfo);
			//将dto放到vo中，传递到前台
			vo.setDto(resultDto);
			return returnSuccess();
		}catch(BusinessException e){
			logger.error(e.getMessage(),e);
			return returnError(e);
		}
	}
	
	

	/**
	 * 
	 * 保存司机PDA接送货信息和明细
	 * 
	 * @author 045738-foss-maojianqiang
	 * @param
	 * @date 2012-12-18 上午11:53:31
	 * @return
	 */
	@JSON
	public String addReceiveReportBill() {
		try{
			//如果dto为空，则抛出异常
			if(vo.getDto()==null){	
				throw new SettlementException("查询参数不能为空！");
			}
			//获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//调用service进行查询
			DriverCollectionRptDto resultDto = pDAPayInReportBillCreateService.addReceiveReportBill(vo.getDto(), currentInfo);
			//将dto放到vo中，传递到前台
			vo.setDto(resultDto);
			return returnSuccess();
		}catch(BusinessException e){
			logger.error(e.getMessage(),e);
			return returnError(e);
		}
	}

	/**
	 * 
	 * 打印司机报表信息
	 * 
	 * @author 045738-foss-maojianqiang
	 * @param
	 * @date 2012-12-18 上午11:53:40
	 * @return
	 */
	@JSON
	public String exportReceiveReportBillInfo() {
		return returnSuccess();
	}

	/**
	 * 查询PDA司机收款信息
	 * 
	 * @author 045738-foss-maojianqiang
	 * @param
	 * @date 2012-12-18 上午11:53:51
	 * @return
	 */
	@JSON
	public String queryReceiveReportBill() {
		try{
			//如果dto为空，则抛出异常
			if(null==vo || null==vo.getDto()){	
				throw new SettlementException("查询参数不能为空！");
			}
			//获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//如果结束日期不为空，则需要进行+1操作
			Date reportEndDate = vo.getDto().getReportEndDate();
			if(reportEndDate!=null){
				//将结束日期+1天，到00:00:00赋值给查询条件
				vo.getDto().setReportEndDate(DateUtils.getStartDatetime(reportEndDate,1));
			}
			//调用service进行查询
			DriverCollectionRptDto resultDto = pDAPayInReportBillCreateService.queryReceiveReportBill(vo.getDto(), currentInfo,getStart(),getLimit());
			//将dto放到vo中，传递到前台
			vo.setDto(resultDto);
			//设置分页总条数
			this.setTotalCount((long) resultDto.getTotalCount());
			return returnSuccess();
		}catch(BusinessException e){
			logger.error(e.getMessage(),e);
			return returnError(e);
		}
	}
	
	/**
	 * 查看报表明细
	 * @author 045738-foss-maojianqiang
	 * @date 2013-1-23 下午4:54:30
	 * @return
	 */
	@JSON
	public String queryReportDetail(){
		try{
			//如果dto为空，则抛出异常
			if(vo.getDto()==null){	
				throw new SettlementException("查询参数不能为空！");
			}
			//调用service进行查询
			DriverCollectionRptDto resultDto = pDAPayInReportBillCreateService.queryReportDetail(vo.getDto().getReportNo(),vo.getDto().getReportBeginDate());
			//将dto放到vo中，传递到前台
			vo.setDto(resultDto);
			return returnSuccess();
		}catch(BusinessException e){
			logger.error(e.getMessage(),e);
			return returnError(e);
		}
	}
	

	/**
	 * @return vo
	 */
	public DriverCollectionRptVo getVo() {
		return vo;
	}

	/**
	 * @param vo
	 */
	public void setVo(DriverCollectionRptVo vo) {
		this.vo = vo;
	}

	/**
	 * @param  pDAPayInReportBillCreateService  
	 */
	public void setpDAPayInReportBillCreateService(
			IPDAPayInReportBillCreateService pDAPayInReportBillCreateService) {
		this.pDAPayInReportBillCreateService = pDAPayInReportBillCreateService;
	}

}
