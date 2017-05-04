package com.deppon.foss.module.transfer.load.server.action;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IExpressHandOverBillService;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.NewHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.ExpressHandOverBillVo;

/** 
 * @className: ExpressHandOverBillAction
 * @author: ShiWei shiwei@outlook.com
 * @description: 包交接单action
 * @date: 2013-7-26 下午2:13:09
 * 
 */
public class ExpressHandOverBillAction extends AbstractAction {

	private static final long serialVersionUID = 18846235655658L;
	
	private ExpressHandOverBillVo expressHandOverBillVo = new ExpressHandOverBillVo();
	
	private IExpressHandOverBillService expressHandOverBillService;
	
	/**
	 * 业务锁service
	 */
	private IBusinessLockService businessLockService;

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	
	public void setExpressHandOverBillService(
			IExpressHandOverBillService expressHandOverBillService) {
		this.expressHandOverBillService = expressHandOverBillService;
	}

	public ExpressHandOverBillVo getExpressHandOverBillVo() {
		return expressHandOverBillVo;
	}

	public void setExpressHandOverBillVo(ExpressHandOverBillVo expressHandOverBillVo) {
		this.expressHandOverBillVo = expressHandOverBillVo;
	}

	/**
	 * 新增包交接单界面跳转
	 * @author 045923-foss-shiwei
	 * @date 2013-7-29 上午10:54:46
	 */
	public String expressHandOverbillAddnewIndex(){
		return SUCCESS;
	}
	
	/**
	 * 新增包交接单时，加载包信息
	 * @author 045923-foss-shiwei
	 * @date 2013-7-29 下午2:36:55
	 */
	@SuppressWarnings("unchecked")
	@JSON
	public String loadExpressPackageInfo(){
		String packageNo = expressHandOverBillVo.getPackageNo();
		try{
			List<Object> rList = expressHandOverBillService.loadExpressPackageInfo(packageNo);
			expressHandOverBillVo.setHandOverBillEntity((HandOverBillEntity) rList.get(0));
			expressHandOverBillVo.setWaybillList((List<HandOverBillDetailEntity>) rList.get(1));
			//zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
			String departDeptCode =  expressHandOverBillVo.getHandOverBillEntity().getDepartDeptCode();
			expressHandOverBillVo.setExpressConverParameter(expressHandOverBillService.queryExpressConverParameter(departDeptCode));
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 保存包交接单
	 * @author 045923-foss-shiwei
	 * @date 2013-7-30 下午3:27:35
	 */
	@JSON
	public String saveExpressHandOverBill(){
		//获取前台传入的待新增的交接单dto
		NewHandOverBillDto newHandOverBillDto = expressHandOverBillVo.getNewHandOverBillDto();
		MutexElement mutex = null;
		try {
			//包号
			String lockStr = newHandOverBillDto.getHandOverBillEntity().getHandOverBillNo();
			mutex = new MutexElement(lockStr, "新增包交接单", MutexElementType.TFR_LOAD_EXPRESS_HANDOVERBILL_ADDNEW);
			// 锁定
			boolean flag = businessLockService.lock(mutex, 0);
			if (flag) {
				expressHandOverBillService.saveExpressHandOverBill(newHandOverBillDto);
				// 解锁
				businessLockService.unlock(mutex);
			} else {
				throw new TfrBusinessException("该包正在生成交接单，请稍候重试或者重新点击保存按钮！");
			}
			// 成功
			return returnSuccess();
		} catch (TfrBusinessException e) {
			// 解锁
			businessLockService.unlock(mutex);
			// 返回错误信息
			return returnError(e);
			// 捕捉业务异常
		} catch (BusinessException e) {
			// 解锁
			businessLockService.unlock(mutex);
			// 返回错误信息
			return returnError(e);
		} // 捕捉异常
		catch (Exception e) {
			// 解锁
			businessLockService.unlock(mutex);
			// 返回错误信息
			return returnError(e.getMessage());
		}
	}

}
