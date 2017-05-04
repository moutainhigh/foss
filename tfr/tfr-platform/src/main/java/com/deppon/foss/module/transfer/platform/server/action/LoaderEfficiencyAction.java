package com.deppon.foss.module.transfer.platform.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.service.ILoaderEfficiencyService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.LoaderEfficiencyEntity;
import com.deppon.foss.module.transfer.platform.api.shared.utils.PlatformUtils;
import com.deppon.foss.module.transfer.platform.api.shared.vo.LoaderEfficiencyVo;
import com.deppon.foss.util.DateUtils;

/**
 * 个人装卸车效率管理
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:163580,date:2014-4-29 上午9:00:18,content:</p>
 * @author 163580
 * @date 2014-4-29 上午9:00:18
 * @since
 * @version
 */
public class LoaderEfficiencyAction extends AbstractAction {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoaderEfficiencyAction.class);
	private static final long serialVersionUID = -5905230997143001014L;
	
	/**
	 * 个人装卸车效率管理Service
	 */
	private ILoaderEfficiencyService loaderEfficiencyService;
	
	/**
	 * 导出Excel 文件流
	 */
	private transient InputStream excelStream;
	/**
	 * 导出Excel 文件名
	 */
	private String fileName;
	/**
	 * 装卸车效率VO
	 */
	private LoaderEfficiencyVo loaderEfficiencyVo = new LoaderEfficiencyVo();
	
	/**
	 * 日均装卸车效率查询
	 * @author 163580
	 * @date 2014-4-29 上午10:33:36
	 * @return
	 * @see
	 */
	@JSON
	public String queryLoaderEfficiencyByDay(){
		try {
			LoaderEfficiencyEntity loaderEfficiency = loaderEfficiencyVo.getLoaderEfficiency();
			Date queryDate = loaderEfficiency.getQueryDate();
			if(queryDate == null) {
				return returnError("查询日期不能为空");
			}
			loaderEfficiency.setQueryDate(PlatformUtils.getFirstMomentOfDay(queryDate));
			loaderEfficiency.setBeginDate(DateUtils.convert(PlatformUtils.getFirstMomentOfDay(queryDate)));
			loaderEfficiency.setEndDate(DateUtils.convert(PlatformUtils.getLastMomentOfDay(queryDate)));
			//查询当前部门下所有子部门
			List<String> loaderOrgCodes = loaderEfficiencyService.getChildDept(loaderEfficiency.getLoaderOrgCode());
			loaderEfficiency.setLoaderOrgCodes(loaderOrgCodes);
			List<LoaderEfficiencyEntity> loaderEfficiencys = loaderEfficiencyService.queryLoaderEfficiencyByDay(loaderEfficiency, this.getLimit(), this.getStart());
			//获取查询的总记录数
			Long totCount = loaderEfficiencyService.queryLoaderEfficiencyByDayCount(loaderEfficiency);
			//设置总记录数
			this.setTotalCount(totCount);
			//设置查询的结果集
			loaderEfficiencyVo.setLoaderEfficiencys(loaderEfficiencys);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage());
			return returnError(e);
		}
	}
	
	/**
	 * 月均装卸车效率查询
	 * @author 163580
	 * @date 2014-4-29 上午10:33:36
	 * @return
	 * @see
	 */
	@JSON
	public String queryLoaderEfficiencyByMonth(){
		try {
			LoaderEfficiencyEntity loaderEfficiency = loaderEfficiencyVo.getLoaderEfficiency();
			String queryMonth = loaderEfficiency.getQueryMonth();
			if(queryMonth == null) {
				return returnError("查询月份不能为空");
			}
			loaderEfficiency.setBeginDate(DateUtils.convert(getBeginOfMonth(DateUtils.convert(queryMonth, "yyyy-MM"))));
			loaderEfficiency.setEndDate(DateUtils.convert(getEndOfMonth(DateUtils.convert(queryMonth, "yyyy-MM"))));
			//查询当前部门下所有子部门
			List<String> loaderOrgCodes = loaderEfficiencyService.getChildDept(loaderEfficiency.getLoaderOrgCode());
			loaderEfficiency.setLoaderOrgCodes(loaderOrgCodes);
			List<LoaderEfficiencyEntity> loaderEfficiencys = loaderEfficiencyService.queryLoaderEfficiencyByMonth(loaderEfficiency, this.getLimit(), this.getStart());
			//获取查询的总记录数
			Long totCount = loaderEfficiencyService.queryLoaderEfficiencyByMonthCount(loaderEfficiency);
			//设置总记录数
			this.setTotalCount(totCount);
			//设置查询的结果集
			loaderEfficiencyVo.setLoaderEfficiencys(loaderEfficiencys);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage());
			return returnError(e);
		}
	}
	
	/**
	 * <p>日装车效率导出</p> 
	 * @author 163580
	 * @date 2014-4-30 上午10:15:25
	 * @return
	 * @see
	 */
	public String exportLoaderEfficiencyByDayExcel() {
		try{
			LoaderEfficiencyEntity loaderEfficiency = loaderEfficiencyVo.getLoaderEfficiency();
			Date queryDate = loaderEfficiency.getQueryDate();
			if(queryDate == null) {
				return returnError("查询日期不能为空");
			}
			loaderEfficiency.setQueryDate(PlatformUtils.getFirstMomentOfDay(queryDate));
			loaderEfficiency.setBeginDate(DateUtils.convert(PlatformUtils.getFirstMomentOfDay(queryDate)));
			loaderEfficiency.setEndDate(DateUtils.convert(PlatformUtils.getLastMomentOfDay(queryDate)));
			//查询当前部门下所有子部门
			List<String> loaderOrgCodes = loaderEfficiencyService.getChildDept(loaderEfficiency.getLoaderOrgCode());
			loaderEfficiency.setLoaderOrgCodes(loaderOrgCodes);
			fileName = encodeFileName("个人日均装卸车效率");
			excelStream = loaderEfficiencyService.exportLoaderEfficiencyByDayExcel(loaderEfficiency);
		}catch(TfrBusinessException e){
			LOGGER.error(e.getMessage());
			return returnError(e);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage());
			return returnError("转换文件编码出错");
		}
		return returnSuccess();
	}
	
	/**
	 * <p>月装车效率导出</p> 
	 * @author 163580
	 * @date 2014-4-30 上午10:15:25
	 * @return
	 * @see
	 */
	public String exportLoaderEfficiencyByMonthExcel() {
		try{
			LoaderEfficiencyEntity loaderEfficiency = loaderEfficiencyVo.getLoaderEfficiency();
			String queryMonth = loaderEfficiency.getQueryMonth();
			if(queryMonth == null) {
				return returnError("查询月份不能为空");
			}
			loaderEfficiency.setBeginDate(DateUtils.convert(getBeginOfMonth(DateUtils.convert(queryMonth, "yyyy-MM"))));
			loaderEfficiency.setEndDate(DateUtils.convert(getEndOfMonth(DateUtils.convert(queryMonth, "yyyy-MM"))));
			//查询当前部门下所有子部门
			List<String> loaderOrgCodes = loaderEfficiencyService.getChildDept(loaderEfficiency.getLoaderOrgCode());
			loaderEfficiency.setLoaderOrgCodes(loaderOrgCodes);
			fileName = encodeFileName("个人月均装卸车效率");
			excelStream = loaderEfficiencyService.exportLoaderEfficiencyByMonthExcel(loaderEfficiency);
		}catch(TfrBusinessException e){
			LOGGER.error(e.getMessage());
			return returnError(e);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage());
			return returnError("转换文件编码出错");
		}
		return returnSuccess();
	}
	
	/**
	 * @Title: querySuperiorOrgByOrgCode 
	 * @Description: 根据当前部门code获取上级外场 
	 * @return    
	 * @return String    返回类型 
	 * querySuperiorOrgByOrgCode
	 * @author: 163580
	 * @throws 
	 * Date: 2014年4月30日 15:40:37
	 */
	@JSON
	public String querySuperiorOrgByOrgCode() {
		try{
			//当前部门Code
			String currentDeptCode = FossUserContext.getCurrentDeptCode();
			//当前部门顶级组织
			OrgAdministrativeInfoEntity administrativeInfo = loaderEfficiencyService.querySuperiorOrgByOrgCode(currentDeptCode);
			//当前部门顶级组织code
			String orgCode = administrativeInfo.getCode();
			loaderEfficiencyVo.setSuperOrgCode(orgCode);
		}catch(TfrBusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 获取当前日期的月份的第一天
	 * @author 163580
	 * @date 2014-4-29 下午4:17:27
	 * @param date
	 * @return
	 * @see
	 */
	private Date getBeginOfMonth(Date date) {
		if(date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
		return c.getTime();
	}

	/**
	 * 获取当前日期的月份的最后一天
	 * @author 163580
	 * @date 2014-4-29 下午4:17:45
	 * @param date
	 * @return
	 * @see
	 */
	private Date getEndOfMonth(Date date) {
		if(date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY,  c.getActualMaximum(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE,  c.getActualMaximum(Calendar.MINUTE));
		c.set(Calendar.SECOND,  c.getActualMaximum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND,  c.getActualMaximum(Calendar.MILLISECOND));
		return c.getTime();
	}
	
	/**
	 * 将文件名转成UTF-8编码以防止乱码
	 * @param 文件名
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-26 下午2:01:05
	 */
	private String encodeFileName(String name)
			throws UnsupportedEncodingException {
		String returnStr;
		String agent = (String) ServletActionContext.getRequest().getHeader(
				"USER-AGENT");
		if (agent != null && agent.indexOf("MSIE") == -1) {
			returnStr = new String(name.getBytes("UTF-8"), "iso-8859-1");
		} else {
			returnStr = URLEncoder.encode(name, "UTF-8");
		}
		return returnStr;
	}

	public void setLoaderEfficiencyService(
			ILoaderEfficiencyService loaderEfficiencyService) {
		this.loaderEfficiencyService = loaderEfficiencyService;
	}

	public void setLoaderEfficiencyVo(LoaderEfficiencyVo loaderEfficiencyVo) {
		this.loaderEfficiencyVo = loaderEfficiencyVo;
	}
	
	public LoaderEfficiencyVo getLoaderEfficiencyVo() {
		return loaderEfficiencyVo;
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
}