package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDelayDeductRecordService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DelayDeductRecordEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SyncDelayDeductRecordCheckResponseEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SyncDelayDeductRecordResponseEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DelayDeductRecordQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.DelayDeductRecordVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
/**
 * 代收延迟扣款
 * @author 308861 
 * @date 2016-10-29 上午9:48:26
 * @since
 * @version
 */
public class DelayDeductRecordAction extends AbstractAction{
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -2457717831510656384L;	
	/**
	 * 日志
	 */
	private static final Logger LOGGER =LoggerFactory.getLogger(DelayDeductRecordAction.class);

	/**
	 * 延时扣款申请审核vo
	 */
	private DelayDeductRecordVo delayDeductVo = new DelayDeductRecordVo();

	public DelayDeductRecordVo getDelayDeductVo() {
		return delayDeductVo;
	}
	public void setDelayDeductVo(DelayDeductRecordVo delayDeductVo) {
		this.delayDeductVo = delayDeductVo;
	}

	/**
	 * 注入delayDeductRecordService 延时扣款申请审核服务
	 */
	private IDelayDeductRecordService delayDeductRecordService;
	
	public void setDelayDeductRecordService(
			IDelayDeductRecordService delayDeductRecordService) {
		this.delayDeductRecordService = delayDeductRecordService;
	}
	/**
	 * 
	 * 条件查询代收延迟扣款 
	 * @author 308861 
	 * @date 2016-10-29 上午9:50:48
	 * @return
	 * @see
	 */
	@JSON
	public String queryDelayDeductRecordList(){
		try {
			//获取查询参数
			DelayDeductRecordQueryDto queryDto = delayDeductVo.getQueryDto();
			//获取当前登录人部门
			CurrentInfo user = FossUserContext.getCurrentInfo();
			//申请部门编码
			queryDto.setCheckOrgCode(user.getCurrentDeptCode());
			//查询条件：运单号
			String waybillNo = queryDto.getWaybillNo();
			//运单号优先级大于其他条件
			if(!waybillNo.isEmpty()){
				queryDto = new DelayDeductRecordQueryDto();
				queryDto.setWaybillNo(waybillNo);
			}
			queryDto.setLimit(limit);//每页显示条数
			queryDto.setStart(start);//偏移值
			//设置数据列表
			SyncDelayDeductRecordResponseEntity responseEntity = delayDeductRecordService
					.queryDelayDeductRecordForPage(queryDto);
			//获取集合
			List<DelayDeductRecordEntity> delayDeductList=responseEntity.getRecordList();
			this.delayDeductVo.setDelayDeductRecordList(delayDeductList);
			//设置数据总条数
			this.totalCount=responseEntity.getTotalCount();
			return returnSuccess();
		} catch (Exception e) {
			e.getStackTrace();
			LOGGER.info(e.getMessage(), e);
			return returnError(e.getMessage());
		}
	}

	/**
	 * 
	 * 代收延时扣款审核 
	 * @author 308861 
	 * @date 2016-10-29 上午11:11:29
	 * @return
	 * @see
	 */
	@JSON
	public String checkDelayDeduct(){
		try {
			//获取审核数据实体
			DelayDeductRecordEntity delayDeductRecordEntity =delayDeductVo.getDelayDeductRecordentity();
			//获取当前登录人部门
			CurrentInfo user = FossUserContext.getCurrentInfo();
			if(user==null){
				return returnError("系统登录异常，请重新登录");
			}
			if(!user.getCurrentDeptCode().equals(delayDeductRecordEntity.getCheckOrgCode())){
				return returnError("当前操作人非该运单收货部门");
			}
			//审核人工号
			delayDeductRecordEntity.setCheckPersonCode(user.getEmpCode());
			//审核人姓名
			delayDeductRecordEntity.setCheckPersonName(user.getEmpName());
			//审核代收延迟货款记录
			SyncDelayDeductRecordCheckResponseEntity response=delayDeductRecordService.updateCheckStatusById(delayDeductRecordEntity);
			//返回值不为0均视为失败
			if(!response.getIsSuccess().equals("0")){
				LOGGER.info(response.getExptionMSG());
				return returnError(response.getExptionMSG());
			}
			return returnSuccess();	
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info(e.getMessage(), e);
			return returnError("数据审核系统异常");
		}
	}
}
