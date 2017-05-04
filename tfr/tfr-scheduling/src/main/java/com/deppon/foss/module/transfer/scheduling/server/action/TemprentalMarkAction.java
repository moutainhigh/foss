package com.deppon.foss.module.transfer.scheduling.server.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.shared.domain.WKTfrBillEntity;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITemprentalMarkService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.RentalMarkEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.RentalMarkDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.RentalMarkVo;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.TempRentalMatkVo;

/**
 *临时租车标记
 *@author zenghaibin
 *@date 2014-5-28  
 **/
@SuppressWarnings("serial")
public class TemprentalMarkAction extends AbstractAction{
	
	@SuppressWarnings("unused")
	private static long serialVersionUID = 4087294908525422087L;
	private RentalMarkVo rentalMarkVo = new RentalMarkVo();
	private RentalMarkDto rentalMarkDto = new RentalMarkDto();
	private TempRentalMatkVo tempRentalMatkVo = new TempRentalMatkVo();
//	private WKTfrBillDto   wkTfrBillDto = new WKTfrBillDto();
//	private WKTfrBillVo wkTfrBillVo = new WKTfrBillVo();

	private static final Logger LOGGER = LoggerFactory.getLogger(TemprentalMarkAction.class);
	/** 导出Excel 文件流*/
	private InputStream excelStream;
	/** 导出Excel 文件流 313352*/
	private InputStream excelStreamMark;
	/** 导出Excel 文件名*/
	private String fileName;
	/**导出Excel快递 文件名 313352*/
	private String fileNameMark;
	
	/**
	 * 租车标记service
	 */
	private ITemprentalMarkService temprentalMarkService;
	/**
	 * 业务锁service
	 */
	private IBusinessLockService businessLockService;
	/**
	 *获取部门编号和标识
	 *@author zenghain
	 *@date 2014-5-28 
	 *@return String
	 **/
	@JSON
	public String temprentalmarkIndex(){
		String departmentSignle = temprentalMarkService.queryOrgCode();//获取部门标识
		rentalMarkDto.setDepartmentSignle(departmentSignle);
		rentalMarkVo.setRentalMarkDto(rentalMarkDto);
	
		return returnSuccess();
	}
	
	/**
	 * 查询租车标记数据
	 * @author zenghaibin
	 * @date 2014-5-28 
	 * @return   expressWaybillNoList
	 **/
	@SuppressWarnings("unchecked")
	@JSON
	public String temprentalmarkQuery(){
		String type = rentalMarkVo.getRentalMarkDto().getType();
		HashMap<String,Object> hsp = new HashMap<String,Object>();
		hsp = temprentalMarkService.queryRentalMarkEntityList(rentalMarkVo,this.getLimit(),this.getStart());
		setTotalCount((Long)hsp.get("totalCount"));
		LOGGER.info("temprentalmarkQuery count = " + totalCount);
		if (StringUtil.equals(type, "1")) {
			rentalMarkVo.setRentalMarkEntityList((List<RentalMarkEntity>)hsp.get("rentalMarkEntityList"));
			return returnSuccess();
		}
		if (StringUtil.equals(type, "2")) {
			rentalMarkVo.setExpressMarkEntityList((List<WKTfrBillEntity>) hsp.get("expressMarkEntityList"));
			return returnSuccess();
		}
		return returnSuccess();
	}
	
	/**
	 * 查询约车编号
	 * @author zenghaibin
	 * @date 2014-06-13
	 * @return
	 **/
	@JSON
	public String  queryInviteVehicleNo(){
		
		 rentalMarkVo=temprentalMarkService.queryInviteVehicleNo(rentalMarkVo);
		return returnSuccess();
	}
	
	/**
	 * 判断小票单号控件是否隐藏
	 * @author zenghaibin
	 * @date 2014-06-13
	 * @return
	 **/
	@JSON
	public String smallTicketValidate(){
		boolean isSmallTicketVisbile;
		try{
			isSmallTicketVisbile=temprentalMarkService.smallTicketValidate(rentalMarkVo.getRentalMarkDto());
			rentalMarkVo.getRentalMarkDto().setSmallTicketVisbile(isSmallTicketVisbile);
		}catch(Exception e){
			return returnError(e.toString());
		}
		return returnSuccess();
	}
	
	/**
	 *检查小票单号的合法性
	 * @author zenghaibin
	 * @date 2014-06-13
	 * @return
	 **/
	@JSON
	public String querysmallTicketNum(){
		HashMap<String,String> result=new HashMap<String,String>();
		result = temprentalMarkService.querysmallTicketNum(rentalMarkVo.getRentalMarkDto());
		//设置不合法的小票单号并返回页面
		
		rentalMarkVo.getRentalMarkDto().setSmallTickets(result.get("smallTickets"));
		rentalMarkVo.getRentalMarkDto().setWaybillNos(result.get("wayBill"));
		
		return returnSuccess();
	}
	
	/**
	 *检查小票单号的合法性
	 * @author zenghaibin
	 * @date 2014-06-13
	 * @return
	 **/
	@JSON
	public String querySmallTicketForWayBill(){
		HashMap<String,String> result=new HashMap<String,String>();
		result = temprentalMarkService.querySmallTicketForWayBill(rentalMarkVo.getRentalMarkDto());
		//设置不合法的小票单号并返回页面
		rentalMarkVo.getRentalMarkDto().setSmallTickets(result.get("smallTickets"));
		rentalMarkVo.getRentalMarkDto().setWaybillNos(result.get("wayBill"));
		
		return returnSuccess();
	}
	
	/**
	 * 交接单和派送单
	 * 查询租车标记明细表里是否有list里对应单号数据
	 * 有，说明已经被标记过。
	 * author zenghaibin
	 * @modifiy 273247
	 *@date 2014-6-25  15:51:10
	 **/
	@JSON
	public String handOverBillRepeatMark(){
		try {
			Long count = temprentalMarkService.queryHandOverBillRepeatMark(rentalMarkVo.getRentalMarkDto().getHandoverBillNoList());			
			List<RentalMarkEntity> entityList=new ArrayList<RentalMarkEntity>();			
			RentalMarkEntity rentalMarkEntity=null;			
			List<String> origOrgCodeList=rentalMarkVo.getOrigOrgCodeList();						
			List<String> destOrgCodeList=rentalMarkVo.getDestOrgCodeList();
			for(int i=0;i<origOrgCodeList.size();i++ ){
				rentalMarkEntity=new RentalMarkEntity();
				rentalMarkEntity.setOrigOrgCode(origOrgCodeList.get(i)); 
				rentalMarkEntity.setDestOrgCode(destOrgCodeList.get(i)); 
				entityList.add(rentalMarkEntity);
			}			
			Long distance=temprentalMarkService.queryMaxDistance(entityList);
			if(count>0){
				rentalMarkVo.setIsrepeatMark("Y");
			}else{
				rentalMarkVo.setIsrepeatMark("N");
			}
			rentalMarkVo.setKmsNum(distance.toString());
		}catch(Exception e){
			return returnError(e.toString());
		}
		return returnSuccess();
	}
	
	/**
	 * 运单：
	 *查询租车标记明细表里是否有list里对应单号数据
	 *有，说明已经被标记过。
	 * author zenghaibin
	 *@date 2014-6-25  15:51:10
	 **/
	@JSON
	public String wayBillRepeatMark(){
		try {
			Long count = temprentalMarkService.queryWayBillRepeatMark(rentalMarkVo.getRentalMarkDto());
			if(count>0){
				rentalMarkVo.setIsrepeatMark("Y");
			}else{
				rentalMarkVo.setIsrepeatMark("N");
			}
		}catch(Exception e){
			return returnError(e.toString());
		}
		return returnSuccess();
	}
	/**
	 *添加租车标记
	 * @author zenghaibin
	 * @date 2014-06-13
	 * @return String
	 **/
	@JSON
	public String addTempRentalMark(){
		//是否锁定标志，如果flag=true则不锁定
		try{
		temprentalMarkService.addTempRentalMark(tempRentalMatkVo);
		}catch(TfrBusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 *导出临时租车数据 零担数据
	 *@author
	 ***/
	public String exportTempRentalExcel(){
		try{
//			RentalMarkDto quertRentalMarkDto = rentalMarkVo.getRentalMarkDto();//查询条件的dto
			fileName = temprentalMarkService.encodeFileName("临时租车数据");
			excelStream = temprentalMarkService.exportTempRentalExcel(rentalMarkVo);   // 导出零担的数据
		}catch(TfrBusinessException e){
			return returnError(e);
		}
		
		return returnSuccess();
	}
	/**
	 * 导出临时租车数据 快递数据   
	 * @author 313352  gyy
	 * @return
	 */
	public String exportTempRentalMarkExcel(){
		try{
//			RentalMarkDto quertRentalMarkDto = rentalMarkVo.getRentalMarkDto();//查询条件的dto
			fileName = temprentalMarkService.encodeFileName("临时租车数据");
			excelStream = temprentalMarkService.exportTempRentalMarkExcel(rentalMarkVo, this.getLimit(), this.getStart());
		}catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}


	@SuppressWarnings("unused")
	private void unlock(List<MutexElement> mutexList){
		if(mutexList!=null&&!mutexList.isEmpty()&&mutexList.size()>0){
			 businessLockService.unlock(mutexList);
		 }
	}
	public RentalMarkVo getRentalMarkVo() {
		return rentalMarkVo;
	}

	public void setRentalMarkVo(RentalMarkVo rentalMarkVo) {
		this.rentalMarkVo = rentalMarkVo;
	} 
  
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public void setTemprentalMarkService(
			ITemprentalMarkService temprentalMarkService) {
		this.temprentalMarkService = temprentalMarkService;
	}
	public void setTempRentalMatkVo(TempRentalMatkVo tempRentalMatkVo) {
		this.tempRentalMatkVo = tempRentalMatkVo;
	}
	public TempRentalMatkVo getTempRentalMatkVo() {
		return tempRentalMatkVo;
	}

	public RentalMarkDto getRentalMarkDto() {
		return rentalMarkDto;
	}

	public void setRentalMarkDto(RentalMarkDto rentalMarkDto) {
		this.rentalMarkDto = rentalMarkDto;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileNameMark() {
		return fileNameMark;
	}

	public void setFileNameMark(String fileNameMark) {
		this.fileNameMark = fileNameMark;
	}

	public InputStream getExcelStreamMark() {
		return excelStreamMark;
	}

	public void setExcelStreamMark(InputStream excelStreamMark) {
		this.excelStreamMark = excelStreamMark;
	}
}