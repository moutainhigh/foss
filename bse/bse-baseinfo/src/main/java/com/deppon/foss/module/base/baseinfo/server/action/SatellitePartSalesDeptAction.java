package com.deppon.foss.module.base.baseinfo.server.action;



import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISatellitePartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SatellitePartSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.SatellitePartSalesDeptVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
/**
 * 卫星点部与营业部关系Action
 * @author 130566
 *
 */
public class SatellitePartSalesDeptAction extends AbstractAction {
	
	private static final long serialVersionUID = 9033163481315747560L;
	//日志
	private static final Logger LOGGER =LoggerFactory.getLogger(SatellitePartSalesDeptAction.class);
	//与前台交互的vo
	private SatellitePartSalesDeptVo vo;
	//卫星点营业部映射的Service
	private ISatellitePartSalesDeptService satellitePartSalesDeptService;

	public SatellitePartSalesDeptVo getVo() {
		return vo;
	}

	public void setVo(SatellitePartSalesDeptVo vo) {
		this.vo = vo;
	}

	public void setSatellitePartSalesDeptService(ISatellitePartSalesDeptService satellitePartSalesDeptService){
		this.satellitePartSalesDeptService = satellitePartSalesDeptService;
	}
	/**
	 * <P>
	 * 根据条件分页查询
	 * <P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-27上午11:23:49
	 * @return
	 */
	@JSON
	public String querySatellitePartSalesList() {
		try {
			SatellitePartSalesDeptEntity entity = vo
					.getSatellitePartSalesDeptEntity();
			vo.setSatellitePartSalesList(satellitePartSalesDeptService
					.querySatellitePartSalesList(entity, start, limit));
			long totalCount = satellitePartSalesDeptService
					.querySatellitePartSalesCount(entity);
			this.setTotalCount(totalCount);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	 *<P>根据id作废<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-31下午5:59:17
	 * @return
	 */
	@JSON
	public String deleteSatellitePartSalesById(){
		try {
			String id =vo.getSatellitePartSalesDeptEntity().getId();
			//获取当前用户
			CurrentInfo currentInfo =FossUserContext.getCurrentInfo();
			SatellitePartSalesDeptEntity entity =new SatellitePartSalesDeptEntity();
			entity.setModifyUser(currentInfo.getEmpCode());
			entity.setId(id);
			//作废
			satellitePartSalesDeptService.deleteSatellitePartSalesById(entity);
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	/**
	 *<P>批量作废<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-31下午6:17:41
	 * @return
	 */
	@JSON
	public String deleteSatellitePartSalesByIdList(){
		try {
			//idlist集合
			List<String> idList =vo.getIdList();
			CurrentInfo currentInfo =FossUserContext.getCurrentInfo();
			//批量作废
			satellitePartSalesDeptService.deleteSatellitePartSalesByIdList(idList,currentInfo);
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	 *<P>根据卫星点查询<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-31下午6:21:13
	 * @return
	 */
	@JSON
	public String querySatellitePartSaleBySatelliteCode(){
		try {
			//获取卫星点部编码
			String satelliteCode =vo.getSatellitePartSalesDeptEntity().getSatelliteDeptCode();
			SatellitePartSalesDeptEntity entity =satellitePartSalesDeptService.querySatellitePartsalesBySatelliteCode(satelliteCode);
			vo.setSatellitePartSalesDeptEntity(entity);
			return returnSuccess();
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	/**
	 * 
	 *<P>添加方法<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-3-31下午7:06:12
	 * @return
	 */
	@JSON
	public String addSatellitePartSales(){
		try {
			//获取新增的实体
			List<SatellitePartSalesDeptEntity> addDeptEntities =vo.getAddSatellitePartSalesList();
			CurrentInfo currentInfo =FossUserContext.getCurrentInfo();
			satellitePartSalesDeptService.addSatellitePartSalesList(addDeptEntities,currentInfo.getEmpCode());
			return returnSuccess(MessageType.SAVE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
	/**
	 *<P>修改方法<P>
	 * @author :130566-zengJunfan
	 * @date : 2014-4-1下午1:53:06
	 * @return
	 */
	@JSON
	public String updateSatellitePartSales(){
		try {
			CurrentInfo currentInfo =FossUserContext.getCurrentInfo();
			String modifyUser =currentInfo.getEmpCode();
			if(null ==vo){
				throw new BusinessException("vo 为空");
			}
			satellitePartSalesDeptService.updateSatellitePartSalesList(vo,modifyUser);
			return returnSuccess(MessageType.UPDATE_SUCCESS);
		} catch (BusinessException e) {
			LOGGER.debug(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}
}
