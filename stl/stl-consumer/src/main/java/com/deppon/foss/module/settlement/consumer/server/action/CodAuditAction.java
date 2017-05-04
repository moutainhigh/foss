package com.deppon.foss.module.settlement.consumer.server.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodAuditService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CodAuditEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CodAuditDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.CodAuditVo;
import com.deppon.foss.util.CollectionUtils;
import com.primeton.fc.bfms.log.sys.Logger;
import org.springframework.beans.factory.annotation.Value;
import java.util.Date;

/**
 * Created by 073615 on 2015/11/30.
 */
public class CodAuditAction extends AbstractAction {
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 参数传递和返回
     */
    private CodAuditVo vo;

    /**
     * 代收货款审核
     */
    private  ICodAuditService codAuditService;

    private final String exportName = "代收货款审核";
    /**
     * Excel 名称
     */
    private String excelName;
    /**
     * 输出流
     */
    private InputStream inputStream;
    /**
     * 文件上传的地址
     * */
    @Value(value="${file.rootDir}")
	private String rootDir;
    /***
     * 上传的文件
     */
    private String uploadFile;
    /***
     *上传的文件名
     */
    private String uploadFileFileName;
    /**
     *上传文件的文件格式 
     */
    private String uploadFileContentType;
    /**
     *上传文件时的审核意见 
     */
    private String codAuditSuggestion;
  
    /**
     * 资金部审核
     * @return
     */
    public String fundAudit(){
        try{
        if(vo!= null && StringUtil.isNotBlank(vo.getAuditStatus())){
            if(SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWAUDIT.equals(vo.getAuditStatus())){
                CodAuditDto dto = new CodAuditDto();
                dto.setWaybillNos(vo.getQueryWaybillNos());
                dto.setLockStatus(vo.getAuditStatus());
                codAuditService.updateCodAuditStatusBath(dto);
            }else{
                Logger.info("资金部审核传入的状态错误");
            }
        }else{
        	Logger.info("传入的参数不合法"); 
        }
        }catch(BusinessException e){
        	
            return returnError(e);
        }

        return returnSuccess();
    }


    /**
     * 资金部锁定/取消锁定
     * @return
     */
    public String fundLock(){
        try{
            if(vo!= null && StringUtil.isNotBlank(vo.getAuditStatus())){
                if(SettlementDictionaryConstants.SETTLE_CODAUDIT_FUNDLOCK.equals(vo.getAuditStatus())
                        ||SettlementDictionaryConstants.SETTLE_CODAUDIT_FUNDCANCELLOCK.equals(vo.getAuditStatus())){
                    CodAuditDto dto = new CodAuditDto();
                    dto.setWaybillNos(vo.getQueryWaybillNos());
                    dto.setLockStatus(vo.getAuditStatus());
                    codAuditService.updateCodAuditStatusBath(dto);
                }else{
                    Logger.info("传入的审核状态不正确");
                }

            }else{
            	Logger.info("传入的参数不合法"); 
            }
        }catch(BusinessException e){
            return returnError(e);
        }
    return returnSuccess();
    }

    /**
     *审核会计 锁定/取消锁定
     * @return
     */
    public String reviewFundLock(){
        try{
            if(vo!= null && StringUtil.isNotBlank(vo.getAuditStatus())){
                if(SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWLOCK.equals(vo.getAuditStatus())
                        ||SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWCANLELLOCK.equals(vo.getAuditStatus())){
                    CodAuditDto dto = new CodAuditDto();
                    dto.setWaybillNos(vo.getQueryWaybillNos());
                    dto.setLockStatus(vo.getAuditStatus());
                    dto.setCreateTime(new Date());
                    codAuditService.updateCodAuditStatusBath(dto);
                }else{
                    Logger.info("传入的审核状态不正确");
                }

            }else{
            	Logger.info("传入的参数不合法"); 
            }
        }catch(BusinessException e){
            return returnError(e);
        }
    return returnSuccess();
    }

    /**
     * 审核会计审核 或者 取消锁定
     * @return
     */
    public String reviewFundAudit(){
        try{
            if(vo!= null && StringUtil.isNotBlank(vo.getAuditStatus())){
                if(SettlementDictionaryConstants.SETTLE_CODAUDIT_REVIEWOVER.equals(vo.getAuditStatus()) || "RSSL".equals(vo.getAuditStatus())){
                    CodAuditDto dto = new CodAuditDto();
                    dto.setWaybillNos(vo.getQueryWaybillNos());
                    dto.setLockStatus(vo.getAuditStatus());
                    codAuditService.updateCodAuditStatusBath(dto);
                }else {
                    Logger.info("复核会计审核传入状态不正确！");
                }

            }else{
                Logger.info("传入的参数不合法"); 
            }
        }catch(BusinessException e){
            return returnError(e);
        }

        return returnSuccess();
    }

    /**
     * 分页查询
     * @return
     */
    public String queryByCondition(){
        try{
            if(vo!= null&&vo.getActiveTab()==1){
                //根据查询条件汇总
                CodAuditDto dto = codAuditService.queryTotalByConditon(vo.getQueryDto());
                //如果存在数据继续查询明细
                if(dto.getTotalCount()>0){
                	this.setTotalCount((long)dto.getTotalCount());
                	//分页查询
                    List<CodAuditEntity> list =  codAuditService.queryCodAuditByPage(vo.getQueryDto(),start,limit);
                    if(dto.getTotalCount()>0){
	                    vo.setCodAuditList(list);
	                    vo.setTotalCount(dto.getTotalCount());
	                    vo.setLockCount(dto.getLockCount());
	                    vo.setUnlockCount(dto.getUnlockCount());
	                    vo.setShortFreeze(dto.getShortFreeze());
	                    vo.setLongFreeze(dto.getLongFreeze());
                    }
                }
            }else if(vo!= null && vo.getActiveTab() == 0){
                queryByWaybillNos();
            }


        }catch(BusinessException e){
            return returnError(e);
        }
        return returnSuccess();
    }

    /**
     * 根据运单号查询
     */

    public String queryByWaybillNos(){

        //判断参数是否合法
        if(vo!= null && CollectionUtils.isNotEmpty(vo.getQueryWaybillNos())){
            //根据运单号查询
            CodAuditDto dto = new CodAuditDto();
            dto.setWaybillNos(vo.getQueryWaybillNos());
            //根据条件查询总条数
            CodAuditDto result = codAuditService.queryTotalByConditon(dto);
            if(result.getTotalCount()>0){
                List<CodAuditEntity> list = codAuditService.queryCodAuditByPage(dto, start, limit);
                vo.setCodAuditList(list);
                vo.setTotalCount(result.getTotalCount());
                vo.setLockCount(result.getLockCount());
                vo.setUnlockCount(result.getUnlockCount());
                vo.setShortFreeze(result.getShortFreeze());
                vo.setLongFreeze(result.getLongFreeze());
            }
        }else{
            throw new SettlementException("传入的单据编号为空");
        }
        return returnSuccess();
    }

    /**
     * 根据条件导出
     * @return
     */
    public String exprtExcel(){
        ByteArrayOutputStream os = null;
        CodAuditDto dto = new CodAuditDto();
        try {
                if(vo!= null &&
                        vo.getActiveTab() ==0){
                //根据查询条件汇总
                    dto.setWaybillNos(vo.getQueryWaybillNos());
                }else if(vo!= null &&
                        vo.getActiveTab() ==1){
                    dto = vo.getQueryDto();
                    dto.setWaybillNos(null);
                }
            CodAuditDto resultCount = codAuditService.queryTotalByConditon(dto);
            //如果存在数据继续查询明细
            if(resultCount.getTotalCount()>0){

                //设置导出的Excel文件的名字
                excelName = new String(
                        (exportName).getBytes(SettlementConstants.UNICODE_GBK),
                        SettlementConstants.UNICODE_ISO);
                os = new ByteArrayOutputStream();
                //货物Excel对象
                HSSFWorkbook work = codAuditService.codAuditExportEXCEL(dto);
                work.write(os);
                inputStream = new ByteArrayInputStream(os.toByteArray());
            }else{
               throw new SettlementException("没有数据可以导出");
            }

        } catch (IOException e) {
            return returnError("导出Excel失败");
        } catch(BusinessException e){
            return returnError(e);
        }finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    new ByteArrayOutputStream();
                    return returnError("导出Excel失败");
                }
            }
        }

        return returnSuccess();
    }




    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(ByteArrayInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setCodAuditService(ICodAuditService codAuditService) {
        this.codAuditService = codAuditService;
    }

    public CodAuditVo getVo() {
        return vo;
    }

    public void setVo(CodAuditVo vo) {
        this.vo = vo;
    }
}
