package com.deppon.foss.module.transfer.platform.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.platform.api.server.service.ICarEfficiencyManageService;
import com.deppon.foss.module.transfer.platform.api.shared.define.CarEfficiencyConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.CarEfficiencyEntity;
import com.deppon.foss.module.transfer.platform.api.shared.vo.CarEfficiencyManageVo;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;


/**
* @description 车辆效率管理（装卸货）
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年4月25日 下午1:51:20
*/
public class CarEfficiencyManageAction  extends AbstractAction {

	
	/**
	* @fields serialVersionUID
	* @author 14022-foss-songjie
	* @update 2014年4月25日 下午1:51:07
	* @version V1.0
	*/
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 车辆效率管理（装卸货）VO
	* @fields carEfficiencyManageVo
	* @author 14022-foss-songjie
	* @update 2014年4月25日 下午2:00:04
	* @version V1.0
	*/
	private CarEfficiencyManageVo carEfficiencyManageVo = new CarEfficiencyManageVo(); 
	
	
	/**
	 * 车辆装卸车效率管理Service
	* @fields carEfficiencyManageService
	* @author 14022-foss-songjie
	* @update 2014年5月5日 下午5:07:40
	* @version V1.0
	*/
	private ICarEfficiencyManageService carEfficiencyManageService;
	
	/**
	 * 导出Excel 文件流
	 */
	transient InputStream excelStream;
	/**
	 * 导出Excel 文件名
	 */
	private String fileName;
	
	
	
	
	/**
	* @description 车辆装卸车效率管理Service
	* @param carEfficiencyManageService
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 下午5:08:07
	*/
	public void setCarEfficiencyManageService(
			ICarEfficiencyManageService carEfficiencyManageService) {
		this.carEfficiencyManageService = carEfficiencyManageService;
	}


	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	
	/**
	* @description 加载车辆效率管理（装卸货）页面
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月25日 下午2:01:09
	*/
	public String carEfficiencyManageIndex(){
		String[] outfieldInfo = carEfficiencyManageService.queryOutfieldInfo();
		if(outfieldInfo != null){
			CarEfficiencyEntity tempCar = new CarEfficiencyEntity();
			tempCar.setOrgCode(outfieldInfo[0]);
			tempCar.setOrgName(outfieldInfo[1]);
			carEfficiencyManageVo.setCarEfficiencyDto(tempCar);
		}
		return SUCCESS;
	}
	
	
	/**
	* @description 长途卸车效率
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月28日 下午2:32:44
	*/
	@JSON
	public String carEfficiencyManageLongWay(){
		if(carEfficiencyManageVo.getCarEfficiencyDto()!=null){
			if(StringUtils.isBlank(carEfficiencyManageVo.getCarEfficiencyDto().getActualDepartTime())){
				return returnError("查询日期不能为空");
			}
			if(StringUtils.isBlank(carEfficiencyManageVo.getCarEfficiencyDto().getOrgCode())){
				return returnError("查询外场不能为空");
			}
			carEfficiencyManageVo.setCarEfficiencyList(carEfficiencyManageService.queryCarEfficiencyWayLong(carEfficiencyManageVo.getCarEfficiencyDto(), start, limit));
			this.setTotalCount(carEfficiencyManageService.queryCarEfficiencyWayLongCount(carEfficiencyManageVo.getCarEfficiencyDto()));
		}else{
			return returnError("参数异常");
		}
		return SUCCESS;
	}
	
	
	/**
	* @description 长途卸车效率 导出
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月6日 下午2:58:05
	*/
	@JSON
	public String carEfficiencyLongWayExport(){
		try {
			try {
				fileName = this.encodeFileName(CarEfficiencyConstants.CAREFFICIENCY_LONG_WAY_SHEET_NAME);
			} catch (UnsupportedEncodingException e) {
				LOGGER.info("CarEfficiencyManageAction.carEfficiencyLongWayExport 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
			}
			excelStream = carEfficiencyManageService.carEfficiencyLongWayExport(carEfficiencyManageVo.getCarEfficiencyDto());
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	/**
	* @description 短途卸车效率 导出
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月6日 下午2:58:03
	*/
	@JSON
	public String carEfficiencyShortWayExport(){
		try {
			try {
				fileName = this.encodeFileName(CarEfficiencyConstants.CAREFFICIENCY_SHOET_WAY_SHEET_NAME);
			} catch (UnsupportedEncodingException e) {
				LOGGER.info("CarEfficiencyManageAction.carEfficiencyShortWayExport 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
			}
			excelStream = carEfficiencyManageService.carEfficiencyShortWayExport(carEfficiencyManageVo.getCarEfficiencyDto());
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
		return returnSuccess();
	}
	
	/**
	* @description 短途卸车效率
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月28日 下午2:33:59
	*/
	@JSON
	public String carEfficiencyManageShortWay(){
		if(carEfficiencyManageVo.getCarEfficiencyDto()!=null){
			if(StringUtils.isBlank(carEfficiencyManageVo.getCarEfficiencyDto().getActualDepartTime())){
				return returnError("查询日期不能为空");
			}
			if(StringUtils.isBlank(carEfficiencyManageVo.getCarEfficiencyDto().getOrgCode())){
				return returnError("查询外场不能为空");
			}
			carEfficiencyManageVo.setCarEfficiencyList(carEfficiencyManageService.queryCarEfficiencyWayShort(carEfficiencyManageVo.getCarEfficiencyDto(), start, limit));
			this.setTotalCount(carEfficiencyManageService.queryCarEfficiencyWayShortCount(carEfficiencyManageVo.getCarEfficiencyDto()));
		}else{
			return returnError("参数异常");
		}
		return SUCCESS;
	}	
	
	
	/**
	* @description 查询对应的理货员工号和姓名
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年5月5日 下午5:16:40
	*/
	@JSON
	public String queryLoaderInfo(){
		if(carEfficiencyManageVo.getCarEfficiencyDto()!=null && carEfficiencyManageVo.getCarEfficiencyDto().getTaskIds()!=null){
			carEfficiencyManageVo.setCarEfficiencyList(carEfficiencyManageService.queryLoaderListByTaskId(carEfficiencyManageVo.getCarEfficiencyDto().getTaskIds()));
		}else{
			 return returnError("无任务id");
		}
		return SUCCESS;
	}
	
	/**
	* @description 车辆效率管理（装卸货）VO
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月25日 下午2:00:19
	*/
	public CarEfficiencyManageVo getCarEfficiencyManageVo() {
		return carEfficiencyManageVo;
	}

	
	/**
	* @description 车辆效率管理（装卸货）VO
	* @param carEfficiencyManageVo
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月25日 下午2:00:28
	*/
	public void setCarEfficiencyManageVo(CarEfficiencyManageVo carEfficiencyManageVo) {
		this.carEfficiencyManageVo = carEfficiencyManageVo;
	}
	
	/**
	 * @description 获取Excel文件名
	 * @return
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2014年5月6日 下午2:56:12
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @description 设置Excel文件名
	 * @param fileName
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2014年5月6日 下午2:56:12
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @description 获取导出工作流
	 * @return
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2014年5月6日 下午2:56:12
	 */
	public InputStream getExcelStream() {
		return excelStream;
	}

	/**
	 * 转换导出文件的文件名
	 * 
	 * @param name
	 * @return
	 * @throws UnsupportedEncodingException
	 * @description 转换导出文件的文件名
	 * @version 1.0
	 * @author 14022-foss-songjie
	 * @update 2014年5月6日 下午2:56:12
	 */
	public String encodeFileName(String name)
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
	
}
