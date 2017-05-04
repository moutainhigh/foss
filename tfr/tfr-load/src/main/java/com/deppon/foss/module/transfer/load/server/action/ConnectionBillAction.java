package com.deppon.foss.module.transfer.load.server.action;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IConnectionBillService;
import com.deppon.foss.module.transfer.load.api.shared.domain.ConnectionBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ConnectionBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.NewConnectionBillDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.ArrivalConnectionBillVo;
import com.deppon.foss.module.transfer.load.api.shared.vo.ConnectionBillVo;
//import com.fasterxml.jackson.annotation.JsonAnyGetter;

/** 
 * 接驳交接单模块action类
 * 用于接驳交接单的管理
 * @author 205109-foss-zenghaibin
 * @date 2015-04-02 上午9：39:38
 */
public class ConnectionBillAction extends AbstractAction{

	private static final long serialVersionUID = 9166752079380663534L;
	
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	/**
	 * 这个名称就是用来传给上面struts.xml中的${fileName}的
	 */
	private String excelFileName; 
	/**导出的文件流**/
	private InputStream exportExcelStream;
	
	/**接驳交接单管理模块的service**/
	private IConnectionBillService connectionBillService;
	
	/**页面传参载体vo**/
	private ConnectionBillVo connectionBillVo=new ConnectionBillVo();
	
	/**页面传参VO**/
	private ArrivalConnectionBillVo arrivalConnectionBillVo = new ArrivalConnectionBillVo();
	                              
	
	/**生成接驳交接单流水号**/
	private ITfrCommonService tfrCommonService;
	/**
	 *进入接驳交接单管理跳转action 
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 ***/
	@JSON
	public String connectionBillQueryIndex(){
		try{
			String[] orgInfo = connectionBillService.queryOrgCode();
			connectionBillVo.setOrgCode(orgInfo[0]);
			connectionBillVo.setOrgName(orgInfo[1]);
			connectionBillVo.setTransferCenter(orgInfo[2]);
		}catch(BusinessException e){
			connectionBillVo.setErrorMessage(e.getMessage());
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 *进入新增接驳交接单页面跳转action 
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 ***/
	@JSON
	public String connectionBillAddNewIndex(){
		try{
			//生成交接单号，规则为：C+8为随机数
			LOGGER.info("TFR-LOAD connectionBillAddNewIndex action Start.........");
			LOGGER.info("TFR-LOAD connectionBillAddNewIndex action 生成接驳交接单号开始");
			String connectionBillNo = tfrCommonService.showSerialNumber(TfrSerialNumberRuleEnum.JBJJD);
			LOGGER.info("TFR-LOAD connectionBillAddNewIndex action 生成接驳交接单号"+connectionBillNo+"结束");
			String[] orgInfo = connectionBillService.queryOrgCode();
			connectionBillVo.setOrgCode(orgInfo[0]);
			connectionBillVo.setOrgName(orgInfo[1]);
			connectionBillVo.setTransferCenter(orgInfo[2]);
			connectionBillVo.setConnectionBillNo(connectionBillNo);
	
		}catch(BusinessException e){
			connectionBillVo.setErrorMessage(e.getMessage());
			return returnError(e);
		}
		return returnSuccess();
	}
	
	
	
	/**
	 *查询接驳交接单数据的action
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 ***/
	@JSON
	public String queryConnectionBillList(){
		
		LOGGER.info("TFR-LOAD queryConnectionBillList action Start.........");
		//调用service查询接驳交接单信息
		List<ConnectionBillEntity>  entrityList=connectionBillService.queryConnectionBillList(connectionBillVo,this.getLimit(),this.getStart());
		//设置到vo里，把结果集传回页面
		connectionBillVo.setConnectionBillList(entrityList);
		//查询总条数
		Long totalCount = connectionBillService.queryConnectionBillListCount(connectionBillVo);
		//返回前台，分页
		this.setTotalCount(totalCount);
		return returnSuccess();
	}

	/**
	 * 查询接驳单（到达）数据的action
	 * @author 218427-foss-hongwy
	 * @date 2015-10-29
	 */
	@JSON
	public String queryArrivalConnectionBillList(){
		LOGGER.info("TFR-LOAD queryArrivalConnectionBillList action Start.........");
		//调用service 查询接驳交接单（到达）信息
		List<ConnectionBillEntity> entities  = connectionBillService.queryArrivalConnectionBillList(arrivalConnectionBillVo,this.getLimit(),this.getStart());
		//设置到vo里，把结果集传回页面
		arrivalConnectionBillVo.setConnectionBillList(entities);
		//查询总条数
		Long totalCount = connectionBillService.queryArrivalConnectionBillListCount(arrivalConnectionBillVo);
		//返回前台，分页
		this.setTotalCount(totalCount);
		return returnSuccess();
	} 
	/**
	 *查询库存运单数据的action
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 ***/
	@JSON
	public String queryWaybillStockList(){
		LOGGER.info("TFR-LOAD queryWaybillStockList action Start.........");
		try{
		Map<String,Object> hsp = connectionBillService.queryWaybillStockList(connectionBillVo,this.getLimit(),this.getStart());
		connectionBillVo.setWaybillStockList((List<ConnectionBillDetailEntity>)hsp.get("waybillStockList"));
		totalCount=(Long)hsp.get("totalCount");
		//返回前台，分页
		this.setTotalCount(totalCount);
		}catch(TfrBusinessException e){
			return this.returnError(e);
		}catch(BusinessException e){
			return this.returnError(e);
		}catch(Exception e){
			return this.returnError(e.toString());
		}
		return returnSuccess();
	}
	
	/**
	 *保存新增的接驳交接单
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 ***/
	@JSON
	public String saveConnectionBill(){
		//获取前台传入的待新增的交接单dto
		NewConnectionBillDto newConnectionBillDto = connectionBillVo.getNewConnectionBillDto();
		//try catch块
		try{
			//保存时重新生成交接单号
			String connectionBillNo = connectionBillService.saveConnectionBill(newConnectionBillDto);
			//生成的交接单号返回前台
			connectionBillVo.setConnectionBillNo(connectionBillNo);
			LOGGER.error("交接单号：" + connectionBillNo + "生成完毕，action返回处理成功");
		}catch(BusinessException e){
			//返回业务异常信息
			return this.returnError(e);
		}
		//返回success
		return returnSuccess();
	}
	
	/**
	 *通过交接单号查询交接单实体
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 ***/
	@JSON
	public String queryConnectionBillByNo(){
		
		ConnectionBillEntity entity=connectionBillService.queryConnectionBillByNo(connectionBillVo.getConnectionBillNo());
		connectionBillVo.setConnectionBillEntity(entity);
		return returnSuccess();
	}
	
	/**
	 *通过交接单号查询交接单实体
	 * @author 218427-foss-hongwy
	 * @date 2015-11-01 14:26:19
	 ***/
	@JSON
	public String queryArrivalConnectionBillByNo(){
		ConnectionBillEntity entity = connectionBillService.queryArrivalConnectionBillByNo(arrivalConnectionBillVo.getArrivalConnectionBillNo());
	    arrivalConnectionBillVo.setConnectionBillEntity(entity);
	    return returnSuccess();
	}
	
	
	/**
	 *通过交接单号查询交接单明细
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 ***/
	@JSON
	public String queryConnectionBillDetailByNo(){
		
		List<ConnectionBillDetailEntity> waybillStockList=connectionBillService.queryConnectionBillDetailByNo(connectionBillVo.getConnectionBillNo());
		connectionBillVo.setWaybillStockList(waybillStockList);
		return returnSuccess();
	}
	
	
	/**
	 *通过交接单号查询交接单明细
	 * @author 218427-foss-hongwy
	 * @date 2015-10-31 15:30:30
	 ***/
	@JSON
	public String  queryArrivalConnectionBillDetailByNo(){
		
		List<ConnectionBillDetailEntity> waybillStockList = connectionBillService.queryArrivalConnectionBillDetailByNo(arrivalConnectionBillVo.getArrivalConnectionBillNo());
		arrivalConnectionBillVo.setWaybillStockList(waybillStockList);
		return returnSuccess();
	}
	
	/**
	 *通过交接单号,运单号查流水明细
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 ***/
	@JSON
	public String queryWaybillDetailByNos(){
		
		List<HandOverBillSerialNoDetailEntity> serialNoList=connectionBillService.queryWaybillDetailByNos(connectionBillVo.getConnectionBillNo(),connectionBillVo.getWaybillNo());
		connectionBillVo.setSerialNoList(serialNoList);
		return returnSuccess();
	}
	
	/**
	 *通过交接单号,运单号查流水明细
	 * @author 218427-foss-hongwy
	 * @date 2015-10-31 16:37:30
	 ***/
	public String queryArrivalWaybillDetailByNos(){
		List<HandOverBillSerialNoDetailEntity> serialNoList = connectionBillService.queryArrivalWaybillDetailByNos(arrivalConnectionBillVo.getArrivalConnectionBillNo(),arrivalConnectionBillVo.getWaybillNo());
		arrivalConnectionBillVo.setSerialNoList(serialNoList);
		return returnSuccess();
	}
	
	/**
	 *通过交接单号,运单号查流水明细
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 ***/
	@JSON
	public String updateConnectionBill(){

		try{
			//调用service，更新交接单信息
			connectionBillService.updateConnectionBill(connectionBillVo.getUpdateConnectionBillDto());
			//try catch语句块
		}catch(BusinessException e){
			//返回业务异常信息
			return this.returnError(e);
		}
		//返回success
		return returnSuccess();
	
	}
	
	/**
	 *通过交接单号,作废交接单
	 * @author 205109-foss-zenghaibin
	 * @date 2015-04-09 10:09:30
	 ***/
	@JSON
	public String cancelConnectionBillbyNo(){

		try{
			//调用service，更新交接单信息
			connectionBillService.cancelConnectionBillbyNo(connectionBillVo.getConnectionBillNo());
			//try catch语句块
		}catch(BusinessException e){
			//返回业务异常信息
			return this.returnError(e);
		}
		//返回success
		return returnSuccess();
	
	}
	
	/**
	 * 查询接驳交接单交接单界面，导出交接单
	 * @author 205109-foss-zenghaibin
	 * @date 2015-5-14 下午3:02:26
	 */
	public String exportConnectionDetailExcel(){
		
		try{
			excelFileName = connectionBillService.encodeFileName("接驳交接单明细数据");
			exportExcelStream = connectionBillService.exportConnectionDetailExcel(connectionBillVo.getConnectionBillNo());
		}catch(TfrBusinessException e){
			return returnError(e);
		}catch(Exception e){
			return returnError(e.toString());
		}
		//文件名
		return returnSuccess();
	}
	
	/**
	 * 查询接驳交接单交接单界面，导出交接单
	 * @author 205109-foss-zenghaibin
	 * @date 2015-5-14 下午3:02:26
	 */
	public String exportConnectionBillExcel(){
		
		try{
			excelFileName = connectionBillService.encodeFileName("接驳交接单数据");
			exportExcelStream = connectionBillService.exportConnectionBillExcel(connectionBillVo);
		}catch(TfrBusinessException e){
			return returnError(e);
		}catch(Exception e){
			return returnError(e.toString());
		}
		//文件名
		return returnSuccess();
	}
	
	public void setConnectionBillService(
			IConnectionBillService connectionBillService) {
		this.connectionBillService = connectionBillService;
	}


	public ConnectionBillVo getConnectionBillVo() {
		return connectionBillVo;
	}


	public void setConnectionBillVo(ConnectionBillVo connectionBillVo) {
		this.connectionBillVo = connectionBillVo;
	}

	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public InputStream getExportExcelStream() {
		return exportExcelStream;
	}

	public void setExportExcelStream(InputStream exportExcelStream) {
		this.exportExcelStream = exportExcelStream;
	}

	public ArrivalConnectionBillVo getArrivalConnectionBillVo() {
		return arrivalConnectionBillVo;
	}

	public void setArrivalConnectionBillVo(
			ArrivalConnectionBillVo arrivalConnectionBillVo) {
		this.arrivalConnectionBillVo = arrivalConnectionBillVo;
	}
    
	
}