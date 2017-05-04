package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.INewbirdinfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NewbirdinfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.NewbirdInfoException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.NewbirdinfoVO;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.primeton.ext.system.config.CommonException;

public class NewbirdinfoAction extends AbstractAction {
	/**
	 * 下面是声明的属性
	 */
	private static final long serialVersionUID = -8372339694836134644L;

	/**
	 * 日志.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(NewbirdinfoAction.class);

	// service接口
	private INewbirdinfoService newbirdinfoService;
	
	/**
	 * 业务互斥锁服务.
	 */
	private IBusinessLockService businessLockService;
	
	// 表单对象
	private NewbirdinfoVO newbirdinfoVO;

	public NewbirdinfoVO getNewbirdinfoVO() {
		return newbirdinfoVO;
	}

	public void setNewbirdinfoVO(NewbirdinfoVO newbirdinfoVO) {
		this.newbirdinfoVO = newbirdinfoVO;
	}

	public void setNewbirdinfoService(INewbirdinfoService newbirdinfoService) {
		this.newbirdinfoService = newbirdinfoService;
	}
	
	/**
	 * 设置 业务互斥锁服务.
	 * 
	 * @param businessLockService
	 *            the businessLockService to set
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/**
	 * 
	 * <p>
	 * 查询菜鸟破损单信息,并且反写入到本地数据库
	 * </p>
	 * 
	 * @author 261997
	 * @date 2015-4-16 上午10:27:16
	 * @return
	 * @throws CommonException
	 * @see
	 */
	@JSON
	public String queryNewbirdInfoList() {
		// 从表单对象中获取实体类
		NewbirdinfoEntity newbirdinfo = newbirdinfoVO.getNewbirdinfoEntity();
		try {
			// 实体类中获取运单号
			String waybillno = newbirdinfo.getTransport();
			// 查询本地数据库中 是否有该运单号 通过业务层方法，获取分页对象集合
			List<NewbirdinfoEntity> newbirdinfoEntitys = newbirdinfoService
					.queryNewbirdinfo(newbirdinfo, limit, start);
			if (null == waybillno || "".equals(waybillno)) {
				// 往表单对象中注入查询出的分页对象
				newbirdinfoVO.setNewbirdinfoEntitys(newbirdinfoEntitys);
				// 查询出数据总量
				totalCount = newbirdinfoService.queryRecordCount(newbirdinfo);
			} else {
				// 判断该运单号是否是淘宝订单
				boolean booltaobao = newbirdinfoService.isBoolTaoBao(waybillno);
				// 如果是淘宝订单
				if (booltaobao) {
					// 通过该运单号在本地数据库中已经查询到破损信息反写数据，说明不用调用dop接口去继续查询后反写
					if (newbirdinfoEntitys.size() > 0) {
						// 往表单对象中注入查询出的分页对象
						newbirdinfoVO.setNewbirdinfoEntitys(newbirdinfoEntitys);
						// 查询出数据总量
						totalCount = newbirdinfoService
								.queryRecordCount(newbirdinfo);
					} else {
						// 如果本地库中无该淘宝运单号的破损反写信息，则调用接口查询菜鸟破损单据，反写入本地数据库
						// 此处为调用DOP接口方法，传递该运单号给DOP接口
						NewbirdinfoEntity newbirdinfo1 = null;
						try {
							newbirdinfo1 = newbirdinfoService
									.syncNewbirdinfoInfo(waybillno);
						} catch (Exception e) {							
							returnError(e.getMessage());
						}
						
						if (null == newbirdinfo1) {
							throw new NewbirdInfoException("查询失败，接口不通！");
						}

						// 调用接口成功返回数据后，反写数据进入本地数据库
						UserEntity user = FossUserContext.getCurrentUser(); 
						String userCode = user.getEmployee().getEmpCode();
						String userName = user.getEmployee().getEmpName();
						newbirdinfo1.setOperater(userName + "-" + userCode);
						newbirdinfo1.setTransport(waybillno);
						
						
						// 业务锁
						MutexElement mutex = new MutexElement(waybillno, "CAINIAO_YCD_NO",
								MutexElementType.CAINIAO_YCD_NO);
						LOGGER.info("开始加锁：" + mutex.getBusinessNo());
						boolean result = businessLockService.lock(mutex,
								NumberConstants.ZERO);
						if (result) {
							LOGGER.info("成功加锁：" + mutex.getBusinessNo());
						} else {
							LOGGER.info("失败加锁：" + mutex.getBusinessNo());							
						}
						
						int resultnum = newbirdinfoService
								.addNewbirdinfo(newbirdinfo1);
						
						LOGGER.info("开始解锁：" + mutex.getBusinessNo());
						// 解业务锁
						businessLockService.unlock(mutex);
						LOGGER.info("完成解锁：" + mutex.getBusinessNo());

						if (resultnum > 0) {
							// 反写到接送货的面单表
							newbirdinfoService.updateWaybillInfo(newbirdinfo1);

							List<NewbirdinfoEntity> newbirdinfoEntitys1 = newbirdinfoService
									.queryNewbirdinfo(newbirdinfo1, limit,
											start);
							// 往表单对象中注入查询出的分页对象
							newbirdinfoVO
									.setNewbirdinfoEntitys(newbirdinfoEntitys1);
							// 查询出数据总量
							totalCount = newbirdinfoService
									.queryRecordCount(newbirdinfo1);
						}

					}
				} else {
					throw new NewbirdInfoException("查询失败，非淘宝订单！");
				}
			}
			return returnSuccess();
		} catch (NewbirdInfoException e) {
			return returnError(e.getMessage());
		}

	}

}
