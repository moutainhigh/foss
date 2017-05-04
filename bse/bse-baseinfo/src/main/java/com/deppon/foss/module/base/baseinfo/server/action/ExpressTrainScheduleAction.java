/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineScheduleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressTrainProgramService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineScheduleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressTrainProgramEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressTrainProgramDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OrgGisLongitudeDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressTrainScheduleVo;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 *<p>Title: ExpressTrainScheduleAction</p>
 * <p>Description: 快递班车时刻表action</p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-8
 */
public class ExpressTrainScheduleAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 前后台交互的vo
	 */
	private ExpressTrainScheduleVo vo;
	/**
	 * 这个输入流对应上面struts.xml中配置的那个excelStream，两者必须一致
	 */
	private InputStream excelStream;
	/**
	 * 这个名称就是用来传给上面struts.xml中的${fileName}的
	 */
	private String fileName;
	/**
	 * 快递线路service
	 */
	//private ILineService lineService;
	private IExpressLineService expresslineService;
	/**
	 * 营业部Service
	 */
	private ISaleDepartmentService saleDepartmentService;
	/**
	 * 快递支线班车方案Service
	 */
	private IExpressTrainProgramService expressTrainProgramService;
	/**
	 * 快递支线班车线路时刻表Service
	 */
	private IExpressLineScheduleService expressLineScheduleService;
	/**
	 * 组织service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 *<p>Title: querySalesByOutfield</p>
	 *<p>根据外场获取营业部</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-10下午5:36:32
	 * @return
	 */
	@JSON
	public String querySalesByOutfield(){
		try {
			/**
			 * 130566--实现零担/快递支线分离：快递班车使用的线路是快递到达线路
			 */
			//LineEntity lineEntity =new LineEntity();
			ExpressLineEntity lineEntity =new ExpressLineEntity();
			//到达线路
			lineEntity.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_TARGET);
			//获取出发外场
			String originDept=vo.getExpressTrainProgramDto().getExpressTrainProgramEntity().getOriginOutfieldCode();
			//设置出发外场
			lineEntity.setOrginalOrganizationCode(originDept);
			
			//查询快递到达线路
			//List<LineEntity> lineEntities =lineService.queryLineListByCondition(lineEntity);
			List<ExpressLineEntity> lineEntities =expresslineService.queryLineListByCondition(lineEntity);
			//组织集合
			List<OrgAdministrativeInfoEntity> orgList =new ArrayList<OrgAdministrativeInfoEntity>();
			//非空校验
			if(CollectionUtils.isNotEmpty(lineEntities)){
				this.nullLineEntityChack(lineEntities,orgList);
				/*for (ExpressLineEntity lineResult : lineEntities) {
					if(StringUtils.isNotBlank(lineResult.getDestinationOrganizationCode())){
						SaleDepartmentEntity salesEntity =saleDepartmentService.querySaleDepartmentByCode(lineResult.getDestinationOrganizationCode());
						//营业部 是可到达
						if(null !=salesEntity && StringUtils.isNotBlank(salesEntity.getArrive())&& StringUtils.equals(salesEntity.getArrive(), FossConstants.YES)){
							//可快递派送or可快递自提
							if((StringUtils.isNotBlank(salesEntity.getCanExpressDelivery())&&StringUtils.equals(FossConstants.YES,salesEntity.getCanExpressDelivery()))
									||(StringUtils.isNotBlank(salesEntity.getCanExpressPickupSelf())&&StringUtils.equals(FossConstants.YES,salesEntity.getCanExpressPickupSelf()))){
								orgList.add(orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(salesEntity.getCode()));
							}
						}
					}
				}*/
			}
			vo.setOrgList(orgList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 多层嵌套不合sonar规则
	 * @author 273311
	 * @param lineEntities
	 * @param orgList
	 */
	private void nullLineEntityChack(List<ExpressLineEntity> lineEntities, List<OrgAdministrativeInfoEntity> orgList) {
		for (ExpressLineEntity lineResult : lineEntities) {
			if(StringUtils.isNotBlank(lineResult.getDestinationOrganizationCode())){
				SaleDepartmentEntity salesEntity =saleDepartmentService.querySaleDepartmentByCode(lineResult.getDestinationOrganizationCode());
				//营业部 是可到达
				if(null !=salesEntity && StringUtils.isNotBlank(salesEntity.getArrive())&& StringUtils.equals(salesEntity.getArrive(), FossConstants.YES)){
					//可快递派送or可快递自提
					if((StringUtils.isNotBlank(salesEntity.getCanExpressDelivery())&&StringUtils.equals(FossConstants.YES,salesEntity.getCanExpressDelivery()))
							||(StringUtils.isNotBlank(salesEntity.getCanExpressPickupSelf())&&StringUtils.equals(FossConstants.YES,salesEntity.getCanExpressPickupSelf()))){
						orgList.add(orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(salesEntity.getCode()));
					}
				}
			}
		}	
	}
	/**
	 * 
	 *<p>Title: addExpressTrainProgram</p>
	 *<p>生成快递支线班车方案</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-12下午1:55:24
	 * @return
	 */
	@JSON
	public String addExpressTrainProgram(){
		try {
			ExpressTrainProgramEntity entity =vo.getExpressTrainProgramDto().getExpressTrainProgramEntity();
			//获取当前登陆人
			CurrentInfo currentInfo =FossUserContext.getCurrentInfo();
			entity.setCreateUser(currentInfo.getEmpCode());
			ExpressTrainProgramDto dto =vo.getExpressTrainProgramDto();
			dto.setExpressTrainProgramEntity(entity);
			//执行新增操作
			expressTrainProgramService.addExpressTrainProgram(dto);
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e.getMessage(), e);
		}
	}
	/**
	 * 
	 *<p>Title: queryExpressTrainProgramList</p>
	 *<p>分页查询方案</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-12下午7:38:29
	 * @return
	 */
	@JSON
	public String queryExpressTrainProgramList(){
		try {
			ExpressTrainProgramEntity entity =vo.getExpressTrainProgramDto().getExpressTrainProgramEntity();
			
			List<ExpressTrainProgramEntity> entities =expressTrainProgramService.queryExpressTrainProgramList(entity, start, limit);
			
			vo.setProgramEntityList(entities);
			this.setTotalCount(expressTrainProgramService.queryCount(entity));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e.getMessage(),e);
		}
		
	}
	/**
	 * 
	 *<p>Title: queryExpressLineSchedule</p>
	 *<p>分页查询快递班车线路时刻表</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午1:35:25
	 * @return
	 */
	@JSON
	public String queryExpressLineScheduleList(){
		try {
			ExpressLineScheduleEntity entity =vo.getExpressLineScheduleEntity();
			List<ExpressLineScheduleEntity> resultList =expressLineScheduleService.queryExpressLineScheduleList(entity, start, limit);
			vo.setLineScheduleEntityList(resultList);
			this.setTotalCount(expressLineScheduleService.queryCount(entity));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * 
	 *<p>Title: deleteProgramMore</p>
	 *<p>批量作废方案</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14下午2:10:39
	 * @return
	 */
	@JSON
	public String deleteProgramMore(){
		try {
			//获取要作废的集合
			List<ExpressTrainProgramEntity> programList =vo.getProgramEntityList();
			//获取当前登陆人
			CurrentInfo currentInfo =FossUserContext.getCurrentInfo();
			expressTrainProgramService.deleteProgramList(programList,currentInfo.getEmpCode());
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e.getMessage(), e);
		}
	}
	/**
	 * 
	 *<p>Title: deleteLineScheduleMore</p>
	 *<p>批量作废线路时刻表信息</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-15下午3:18:27
	 * @return
	 */
	@JSON
	public String deleteLineScheduleMore(){
		try {
			List<String> lineNameList =vo.getLineNameList();
			//获取方案名称
			String programName =vo.getExpressLineScheduleEntity().getProgramName();
			CurrentInfo currentInfo =FossUserContext.getCurrentInfo();
			//根据线路名称集合(已经去重过的集合)和登陆人作废
			expressLineScheduleService.deleteLineScheduleListByLineName(lineNameList,programName,currentInfo.getEmpCode());
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e.getMessage(), e);
		}
	}
	/**
	 *<p>Title: queryDeptGisIdsByLineName</p>
	 *<p>根据线路集合查找</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-17上午9:40:15
	 * @return
	 */
	@JSON
	public String queryDeptGisIdsByLineName(){
		try {
			//获取线路信息集合
			ExpressLineScheduleEntity entity =vo.getExpressLineScheduleEntity();
			//获取去重的部门集合
			List<OrgGisLongitudeDto> orgGisIds =expressLineScheduleService.queryDeptGisIdsByLineName(entity.getLineName(),entity.getProgramName());
			vo.setOrgGisList(orgGisIds);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e.getMessage(), e);
		}	
	}
	/**
	 *<p>Title: updateExpressLineSchedule</p>
	 *<p>更新线路信息</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-17下午7:25:47
	 * @return
	 */
	@JSON
	public String updateExpressLineSchedule(){
		try {
			ExpressLineScheduleEntity entity =vo.getExpressLineScheduleEntity();
			CurrentInfo currentInfo =FossUserContext.getCurrentInfo();
			entity.setModifyUser(currentInfo.getEmpCode());
			expressLineScheduleService.updateExpressLineSchedule(entity);
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (Exception e) {
			return returnError(e.getMessage(), e);
		}
	}
	/**
	 * 
	 *<p>Title: exportLineSchedule</p>
	 *<p>导出线路信息Excel</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-21上午11:29:53
	 * @return
	 */
	public String exportLineSchedule(){
		try {
			ExpressLineScheduleEntity entity =vo.getExpressLineScheduleEntity();
			String excelName="";
			if(null !=entity && StringUtils.isNotBlank(entity.getProgramName())){
				excelName =entity.getProgramName()+"的线路时刻表信息";
			}
			fileName =new String(excelName.getBytes("UTF-8"),"iso-8859-1");
			excelStream =expressLineScheduleService.exportLeaseVehicle(entity);
		} catch (UnsupportedEncodingException e) {
			return returnError("UnsupportedEncodingException", e);
		}
		return returnSuccess();
	}
	
	public ExpressTrainScheduleVo getVo() {
		return vo;
	}
	
	public void setVo(ExpressTrainScheduleVo vo) {
		this.vo = vo;
	}
	
	/*public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}*/

	
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	public void setExpresslineService(IExpressLineService expresslineService) {
		this.expresslineService = expresslineService;
	}
	public void setExpressTrainProgramService(
			IExpressTrainProgramService expressTrainProgramService) {
		this.expressTrainProgramService = expressTrainProgramService;
	}
	public void setExpressLineScheduleService(
			IExpressLineScheduleService expressLineScheduleService) {
		this.expressLineScheduleService = expressLineScheduleService;
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
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
}
