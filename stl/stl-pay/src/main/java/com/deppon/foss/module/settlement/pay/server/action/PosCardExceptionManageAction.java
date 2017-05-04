package com.deppon.foss.module.settlement.pay.server.action;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.settlement.common.api.shared.dto.PosCardManageDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.server.service.IPosCardManageService;
import com.deppon.foss.module.settlement.pay.api.shared.vo.PosCardManageVo;

/**
 * 
 * @ClassName: PosCardManageAction
 * @Description: (POS刷卡管理)
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2016-1-12 下午4:08:20
 * 
 */
public class PosCardExceptionManageAction extends AbstractAction {
	private static final Logger LOGGER = LogManager
			.getLogger(PosCardExceptionManageAction.class);

	private static final long serialVersionUID = 1L;
	/**
	 * POS VO
	 */
	private PosCardManageVo vo;

	/**
	 * POS Service
	 */
	private IPosCardManageService posCardManageService;
	
	/**
	 * 导入异常数据
	 * 
	 * @Title: inportExceptionData
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@JSON
	public String importExceptionData() {
		try {
			LOGGER.info("导入异常数据");
			// 获取前台传递的参数
			String param = vo.getPosCardManageDto().getExceptionMessage();
			vo = new PosCardManageVo();
			PosCardManageDto dto = new PosCardManageDto();
			
			if(StringUtils.isEmpty(param)){
				throw new SettlementException("输入参数为空！");
			}
			String tradeSerialNo = posCardManageService.importExceptionData(param);
			
			/**
			 * 设置返回值
			 */
			dto.setRetMessage("流水号: "+tradeSerialNo+",导入成功！");
			vo.setPosCardManageDto(dto);
		} catch (SettlementException e) {
			// 返回界面异常
			return returnError(e.getErrorCode());
		} 
		// 返回界面
		return returnSuccess();
	}

	/**
	 * 修改异常数据
	 * 
	 * @Title: updateExceptionData
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	@JSON
	public String updateExceptionData() {
		try {
			// 获取前台传递的参数
			String param = vo.getPosCardManageDto().getExceptionMessage();
			if(StringUtils.isEmpty(param)){
				throw new SettlementException("输入参数为空！");
			}
			vo = new PosCardManageVo();
			PosCardManageDto dto = new PosCardManageDto();
			
			String tradeSerialNo = posCardManageService.updateExceptionMoney(param);
			/**
			 * 设置返回值
			 */
			dto.setRetMessage("流水号: "+tradeSerialNo +",修改成功！");
			vo.setPosCardManageDto(dto);  
		} catch (SettlementException e) {
			// 返回界面异常
			return returnError(e.getErrorCode());
		}
		// 返回界面
		return returnSuccess();
	}
	
	/**
	 * 删除导入的异常数据
	 *
	 * @Title: deleteExceptionData
	 * @author： 357637 |yuanhuijun001@deppon.com
	 */
	@JSON
	public String deleteExceptionData(){
		try {
			// 获取前台传递的参数
			String param = vo.getPosCardManageDto().getExceptionMessage();

			vo = new PosCardManageVo();

			PosCardManageDto dto = new PosCardManageDto();

			String tradeSerialNo = posCardManageService.deleteExceptionDataTX(param);

			//设置返回值
			dto.setRetMessage("流水号: "+tradeSerialNo +",修改成功！");
			vo.setPosCardManageDto(dto);
		} catch (SettlementException e) {
			// 返回界面异常
			return returnError(e.getErrorCode());
		}
		// 返回界面
		return returnSuccess();
	}
	/*********** getter**setter *************/
	public void setVo(PosCardManageVo vo) {
		this.vo = vo;
	}

	public PosCardManageVo getVo() {
		return vo;
	}

	public void setPosCardManageService(
			IPosCardManageService posCardManageService) {
		this.posCardManageService = posCardManageService;
	}

}
