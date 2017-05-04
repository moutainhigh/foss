/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.server.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IHolidayService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.HolidayEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.HolidayException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.HolidayVo;

/**
 * 法定节假日基础资料Action
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:187862,date:2015-3-19 下午4:51:27,content:TODO </p>
 * @author 187862-dujunhui 
 * @date 2015-3-19 下午4:51:27
 * @since
 * @version
 */
public class HolidayAction extends AbstractAction {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 日志
	 */
	private static final Logger LOGGER=LoggerFactory.getLogger(HolidayAction.class);
	/**
	 * 法定节假日基础资料Vo
	 */
	private HolidayVo vo;
	/**
	 * 法定节假日基础资料Service
	 */
	private IHolidayService holidayService;
	
	/**
	 * @return  the vo
	 */
	public HolidayVo getVo() {
		return vo;
	}

	/**
	 * @param vo the vo to set
	 */
	public void setVo(HolidayVo vo) {
		this.vo = vo;
	}

	/**
	 * @param holidayService the holidayService to set
	 */
	public void setHolidayService(IHolidayService holidayService) {
		this.holidayService = holidayService;
	}
	
	@JSON
	public String queryHoliday(){
		try{//从前台获取查询条件
			if(vo==null){
				throw new HolidayException("查询条件为空！");
			}
			HolidayEntity queryEntity=vo.getEntity();
			if(queryEntity!=null){//判断查询条件非空
				//设置实体链表
				vo.setEntityList(holidayService.queryHolidayListByCondition(queryEntity, start, limit));
				//设置查询条数
				this.setTotalCount(holidayService.queryHolidayListCountByCondition(queryEntity));
			}
			return returnSuccess();
		}catch(BusinessException e){
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	@JSON
	public String addUpdateHoliday(){
		try{
			//从前台获取实体和标识符信息
			HolidayEntity addUpdateEntity=vo.getEntity();
			String flag=addUpdateEntity.getFlag();
			if(flag.equals("UPDATE")){
				holidayService.updateHoliday(addUpdateEntity);
				return returnSuccess(MessageType.UPDATE_SUCCESS);
			}else{
				holidayService.addHoliday(addUpdateEntity);
				return returnSuccess(MessageType.SAVE_SUCCESS);
			}
		}catch(BusinessException e){
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}
	@JSON
	public String voidHoliday(){
		try{
			//从前台获取ids
			String[] ids=vo.getIds();
			holidayService.deleteHoliday(ids);
			return returnSuccess(MessageType.DELETE_SUCCESS);
		}catch(BusinessException e){
			LOGGER.debug(e.getMessage(), e);
			return returnError(e);
		}
	}

}
