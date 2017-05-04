package com.deppon.foss.module.pickup.order.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.order.api.server.service.IBigCustomeService;
import com.deppon.foss.module.pickup.order.api.server.service.ICombinateBillService;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.dto.BigCustomeDto;
import com.deppon.foss.module.pickup.order.api.shared.vo.BigCustomeVo;


/**
 * caohuibin
 * Created by 268217 on 2015/9/11.
 */
public class BigCustomeAction extends AbstractAction {

    private BigCustomeVo vo;

    private IBigCustomeService iBigCustomeService;
    private ICombinateBillService combinateBillService;

    /**
     * Log 对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BigCustomeAction.class);

    /**
     * 导出文件名
     */
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * 这个输入流对应上面struts.xml中配置的那个excelStream，两者必须一致
     */
    private InputStream excelStream;
    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }

    /**
     *查询页面显示
     * @return
     */
    @JSON
    public String queryBigCustome(){
        Long totalCount = iBigCustomeService.queryBigCustomeTotalCount(vo.getBillTimeFrom(),vo.getBillTimeTo());

        List<BigCustomeDto> bigCustomeDtoList=iBigCustomeService.queryBigCustomeList(vo.getBillTimeFrom(),vo.getBillTimeTo(), this.getStart(), this.getLimit());
        vo.setBigCustomeDtoList(bigCustomeDtoList);

        this.setTotalCount(totalCount);
        return returnSuccess();
    }
    /**
     * 合票
     * 183272
     * @return
     */
    public String combineBigCustome() {
    	try {
    		Date timeFrom = vo.getBillTimeFrom();
        	Date timeTo = vo.getBillTimeTo();
        	String str =combinateBillService.combine(timeFrom,timeTo);
			return returnSuccess(str);
		} catch (BusinessException e) {
			// 失败
			return returnError(e);
		}
     }
    /**
     * 反合票
     * 183272
     * @return
     */
    public String disCombineBigCustome() {
    	try {
    		Date timeFrom = vo.getBillTimeFrom();
    		Date timeTo = vo.getBillTimeTo();
    		combinateBillService.disCombine(timeFrom,timeTo);
    		return returnSuccess(DispatchOrderStatusConstants.CLOSETICKETERROR);
		} catch (BusinessException e) {
			// 失败
			return returnError(e);
		}
	}
    /**
     *
     * 转换导出文件的文件名
     * @author CAOHUIBIN
     * @date 2015-09-11
     * @see
     */
    public String encodeFileName(String name) throws UnsupportedEncodingException {
        String returnStr;
        String agent = (String) ServletActionContext.getRequest().getHeader("USER-AGENT");
        if(agent != null && agent.indexOf("MSIE") == -1) {
            returnStr = new String(name.getBytes("UTF-8"), "iso-8859-1");
        } else {
            returnStr = URLEncoder.encode(name, "UTF-8");
        }
        return returnStr;
    }
    /**
     *
     * 转换导出文件的文件名
     * @author CAOHUIBIN
     * @date 2015-09-11
     * @see
     */
    public String export(){
        try {
            //设置文件名
            fileName = encodeFileName("合票明细导出");
        } catch (UnsupportedEncodingException se) {
            //记录错误信息
            LOGGER.error("error", se);
        }
        if(vo != null){
            excelStream = iBigCustomeService.queryExport(vo.getBillTimeFrom(),vo.getBillTimeTo());
        }
        //返回成功
        return returnSuccess();
    }

    public BigCustomeVo getVo() {
        return vo;
    }

    public void setVo(BigCustomeVo vo) {
        this.vo = vo;
    }

    public IBigCustomeService getiBigCustomeService() {
        return iBigCustomeService;
    }

    public void setiBigCustomeService(IBigCustomeService iBigCustomeService) {
        this.iBigCustomeService = iBigCustomeService;
    }
    
    public void setCombinateBillService(
			ICombinateBillService combinateBillService) {
		this.combinateBillService = combinateBillService;
	}
}
