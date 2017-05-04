package com.deppon.foss.module.transfer.load.server.action;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoAddCodeByHandService;
import com.deppon.foss.module.transfer.load.api.server.service.IExpressArrivalService;
import com.deppon.foss.module.transfer.load.api.shared.define.AutoAddCodeConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.AutoAddCodeByHandEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressArrivalDisplayEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressArrivalEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.ExpressArrivalQueryDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.ExpressArrivalVo;
import com.deppon.foss.util.UUIDUtils;

public class ExpressArrivalAction extends AbstractAction{

	private static final long serialVersionUID = 1L;

	
	
	/**
	 * 快递到达service
	 */
	private IExpressArrivalService expressArrivalService;
	/**
	 * 快递到达页面Vo
	 */
	private ExpressArrivalVo expressArrivalVo = new ExpressArrivalVo();
	/**
	 * 待人工补码记录service
	 */
	private IAutoAddCodeByHandService autoAddCodeByHandService;
	
	
	/**
	* @description 设置自动补码service
	* @param autoAddCodeByHandService
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年6月23日 上午10:45:23
	*/
	public void setAutoAddCodeByHandService(
			IAutoAddCodeByHandService autoAddCodeByHandService) {
		this.autoAddCodeByHandService = autoAddCodeByHandService;
	}
	/**
	* @description 设置快递到达service
	* @param expressArrivalService
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 下午2:37:43
	*/
	public void setExpressArrivalService(
			IExpressArrivalService expressArrivalService) {
		this.expressArrivalService = expressArrivalService;
	}

	
	/**
	* @description 获取快递到达页面Vo
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 下午2:37:16
	*/
	public ExpressArrivalVo getExpressArrivalVo() {
		return expressArrivalVo;
	}

	
	/**
	* @description 设置快递到达页面Vo
	* @param expressArrivalVo
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 下午2:37:29
	*/
	public void setExpressArrivalVo(ExpressArrivalVo expressArrivalVo) {
		this.expressArrivalVo = expressArrivalVo;
	}
	
	
	/**
	* @description 查询快递到达货
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月12日 下午4:20:20
	*/
	public String queryExpressArrival(){
		//当前部门编号
		String orgCode = FossUserContext.getCurrentDept().getCode();
		ExpressArrivalQueryDto expressArrivalQueryDto = expressArrivalVo.getExpressArrivalQueryDto();
		expressArrivalQueryDto.setOrgCode(orgCode);
		List<ExpressArrivalDisplayEntity> expressArrivalDisplayEntityList = expressArrivalService.queryExpressArrival(expressArrivalQueryDto, this.getLimit(), this.getStart());
		expressArrivalVo.setExpressArrivalDisplayEntityList(expressArrivalDisplayEntityList);
		//查询总记录数
		Long totalCount = expressArrivalService.queryExpressArrivalCount(expressArrivalQueryDto);
		this.setTotalCount(totalCount);
		return returnSuccess();
	}
	
	
	/**
	* @description 快递到达确认action
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 下午2:50:26
	*/
	public String expressArrivalConfirm(){
		ExpressArrivalEntity expressArrivalEntity = new ExpressArrivalEntity();
		Date newTime = new Date();
		String orgCode = FossUserContext.getCurrentDept().getCode();
		String orgName = FossUserContext.getCurrentDept().getName();
		//工号
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		//姓名
		String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		expressArrivalEntity.setId(expressArrivalVo.getExpressArrivaId());
		expressArrivalEntity.setWaybillNo(expressArrivalVo.getWaybillNo());
		expressArrivalEntity.setCreateTime(newTime);
		expressArrivalEntity.setCreateUserCode(userCode);
		expressArrivalEntity.setCreateUserName(userName);
		expressArrivalEntity.setCreateOrgCode(orgCode);
		expressArrivalEntity.setCreateOrgName(orgName);
		//状态1为确认
		expressArrivalEntity.setStatus(1);
		expressArrivalEntity.setActive("Y");
		//在写入之前,先判断一下此条数据是否存在
		expressArrivalService.expressArrivalConfirm(expressArrivalEntity);
		return returnSuccess();
	}
	
	
	/**
	* @description 根据id查询此条数据是否存在 
	* @param id
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 下午3:14:17
	*/
	public ExpressArrivalEntity expressArrivalSelectById(String id){
		ExpressArrivalEntity expressArrivalEntity = new ExpressArrivalEntity();
		expressArrivalEntity = expressArrivalService.expressArrivalSelectById(id);
		return expressArrivalEntity;
	}
	
	/**
	* @description 快递到达退回action  
	* 退回的数据直接插入到  tfr.t_opt_auto_add_code_byhand (自动补码-待手工补码表) 
	*  			
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年5月15日 下午2:49:49
	*/
	public String expressArrivalBack(){
		String id = expressArrivalVo.getExpressArrivaId();
		ExpressArrivalEntity e = new ExpressArrivalEntity();
		//在退回之前,先判断一下此条数据是否存在
		e = expressArrivalSelectById(id);

		AutoAddCodeByHandEntity autoAddCodeByHandEntity = new AutoAddCodeByHandEntity();
		String id1 = UUIDUtils.getUUID();
		autoAddCodeByHandEntity.setId(id1);
		//269701--lln--2015/11/14--begin
		//记录操作时间，保证退回时间和运单跟踪记录时间一样
		Date currentDate=new Date();
		//269701--lln--2015/11/14--end
		autoAddCodeByHandEntity.setCreateTime(currentDate);
		//工号
		String userCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
		autoAddCodeByHandEntity.setOperatorCode(userCode);
		//姓名
		String userName = FossUserContext.getCurrentUser().getEmployee().getEmpName();
		autoAddCodeByHandEntity.setOperatorName(userName);
		autoAddCodeByHandEntity.setReason(AutoAddCodeConstants.RETURN_ARRIVE);
		autoAddCodeByHandEntity.setWaybillNo(expressArrivalVo.getWaybillNo());
		try {
			autoAddCodeByHandService.insertAddCodeByHand(autoAddCodeByHandEntity);
			//如果快递到达表中有此条记录,删除掉(这种情况是确认过的又退回)
			String waybillNo = expressArrivalVo.getWaybillNo();
			if(e!= null){
				expressArrivalService.expressArrivalDelete(id, waybillNo);
			}
			//269701--lln--2015/11/13--begin
			//装车管理-快递到达界面，选中运单点击退回并选择“是”按钮，
			//触发FOSS发送一条消息至综合查询-综合信息查询-运单相关信息-跟踪记录
			
			expressArrivalService.expressArrivalBackLog(currentDate,waybillNo);
			//269701--lln--2015/11/13--end
			
		}catch(BusinessException e2){
			return returnError(e2);
		}
		return returnSuccess();		
			
	}	
	
}
