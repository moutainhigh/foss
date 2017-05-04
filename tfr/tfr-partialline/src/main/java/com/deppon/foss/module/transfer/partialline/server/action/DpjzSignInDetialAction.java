package com.deppon.foss.module.transfer.partialline.server.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.partialline.api.server.service.IDpjzSignInMsgService;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.DpjzSignInDetailDto;
import com.deppon.foss.module.transfer.partialline.api.shared.exception.ExternalBillException;
import com.deppon.foss.module.transfer.partialline.api.shared.vo.DpjzSignInDetailVo;

/**
 * 家装送装明细及签收确认
 * @author 269701
 * @date 2015-12-04
 * DOP推送供应商以及家装签收信息至FOSS系统服务端
 * 
 * 本菜单中信息来自接口中供应商推送过来的信息
 */
public class DpjzSignInDetialAction extends AbstractAction{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DpjzSignInDetialAction.class);

	/**
	 * 前端数据Vo
	 */
	private DpjzSignInDetailVo dpjzSignInVo=new DpjzSignInDetailVo();
	
	private IDpjzSignInMsgService dpjzSignInMsgService;
	
	
	/**
	 * 查询 家装送装明细及签收确认信息
	 * 按运单号查询
	 * DOP限制
	 *  输入框中运单号需在foss储存表中存在、非已中止/已作废、且属于家装类运单的，系统表结构才展示，其余情况均不展示
	 *  dop限制
	 *  按起止时间查询
	 *  （1）FOSS接收到的信息存储的时间在起止时间内；
	 *  （2）运单收货部门为当前登陆人所在营业部；
	 *  （3）非已中止/已作废，且当前标记“特殊增值服务”的家装类运单(DOP限制)
	 *  
	 *@date  2015-12-04下午2:21:40
	 *@author 269701
	 */
	public String querydpjzSignInDetailBills(){
		LOGGER.info("开始查询数据");
		try{			
			DpjzSignInDetailDto detailDto = null;
			if(dpjzSignInVo != null){
				
				detailDto = dpjzSignInVo.getDpjzSignInDetialBillDto();
				//获取当前部门code
				String orgCode = FossUserContext.getCurrentInfo().getCurrentDeptCode();
				
				if(detailDto!=null){
					// 按运单号查询时不受其他条件的限制。若无此单号，弹框提示：请输入正确的单号
					if (StringUtils.isNotBlank(detailDto.getWaybillNo())) {
						// 查询条件设空
						// 起止时间
						detailDto.setStartEndTimeFrom(null);
						detailDto.setStartEndTimeTo(null);
					}
					//当前登录部门
					detailDto.setCurrentOrg(orgCode);
					//查询
					List<DpjzSignInDetailDto> detailDtoList = dpjzSignInMsgService.querydpjzSignInDetailBills(detailDto, this.getLimit(), this.getStart());
					//将数据返回前台
					dpjzSignInVo.setDpjzSignInDetialBill(detailDtoList);
					// 查询总条数
					this.setTotalCount(dpjzSignInMsgService.querydpjzSignInDetailBillsCount(detailDto));
				}
				
			}else{
				return super.returnSuccess();
			}
			return super.returnSuccess();
		}catch(ExternalBillException e){
			return super.returnError(e);
		}
	}

	/**
	 * 核对德邦家装送装信息以及签收确认（同意）
	 * 
	 * @author 269701-foss-lln
	 * 
	 * @date 2015-12-07 下午5:45:50
	 * 
	 */
	@JSON
	public String dpjzSignInDetialCheckPass() {
		LOGGER.info("核对同意---家装送装明细及签收确认信息");
		try {
			dpjzSignInMsgService.dpjzSignInDetialCheckPass(dpjzSignInVo.getDpjzSignInDetialCheckEntity());
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	/**
	 * 核对德邦家装送装信息以及签收确认（不同意）
	 * 
	 * @author 269701-foss-lln
	 * 
	 * @date 2015-12-07 下午5:45:50
	 * 
	 */
	@JSON
	public String dpjzSignInDetialCheckNotPass() {
		LOGGER.info("核对不同意---家装送装明细及签收确认信息");
		try {
			dpjzSignInMsgService.dpjzSignInDetialCheckNotPass(dpjzSignInVo.getDpjzSignInDetialCheckEntity());
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * @return the dpjzSignInVo
	 */
	public DpjzSignInDetailVo getDpjzSignInVo() {
		return dpjzSignInVo;
	}
	/**
	 * @param dpjzSignInVo the dpjzSignInVo to set
	 */
	public void setDpjzSignInVo(DpjzSignInDetailVo dpjzSignInVo) {
		this.dpjzSignInVo = dpjzSignInVo;
	}
	/**
	 * @param dpjzSignInMsgService the dpjzSignInMsgService to set
	 */
	public void setDpjzSignInMsgService(IDpjzSignInMsgService dpjzSignInMsgService) {
		this.dpjzSignInMsgService = dpjzSignInMsgService;
	}
	
}
