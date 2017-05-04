package com.deppon.pda.bdm.module.foss.accept.server.service.impl.express;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmReturnedGoodsDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.KdBackGoodsDealEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.QryKdBackGoodsDealEntity;

/**
 * 
 * TODO(快递返货工单受理)
 * 
 * @author 245960
 * @date 2015-8-19 下午12:16:55
 * @since
 * @version
 * @discription 2015-09-08日常版本需求,pda前端对接人：袁苗苗,foss对接人：刘金伟
 */
public class KdBackGoodsDealService implements
		IBusinessService<List<KdBackGoodsDealEntity>, QryKdBackGoodsDealEntity> {

	// 注册日志
	private Logger log = Logger.getLogger(getClass());

	// foss接口
	private IPdaWaybillService pdaWaybillService;

	/**
	 * @param pdaWaybillService
	 *            the pdaWaybillService to set
	 */
	public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
		this.pdaWaybillService = pdaWaybillService;
	}

	/**
	 * @description 解析包体
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public QryKdBackGoodsDealEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		// 解析内容 封装实体便于以后扩展，但是由于现在没有字段传上来所以解析出来为空，直接new 一个对象
		//QryKdBackGoodsDealEntity qryKdBackGoodsDealEntity = JsonUtil.parseJsonToObject(QryKdBackGoodsDealEntity.class, asyncMsg.getContent());
		QryKdBackGoodsDealEntity qryKdBackGoodsDealEntity = new QryKdBackGoodsDealEntity();
		
		//封装数据
		qryKdBackGoodsDealEntity.setUserCode(asyncMsg.getUserCode());
		qryKdBackGoodsDealEntity.setDeptCode(asyncMsg.getDeptCode());
		
		return qryKdBackGoodsDealEntity;
	}

	/**
	 * @description 校验数据合法性
	 */
	private void validate(AsyncMsg asyncMsg,
			QryKdBackGoodsDealEntity qryKdBackGoodsDealEntity)
			throws PdaBusiException {
		// 信息校验
		//Argument.notNull(asyncMsg, "AsyncMsg");
	}

	/**
	 * @description 服务方法
	 * @param asyncMsg
	 * @param qryActOrder
	 * @return
	 * @throws PdaBusiException
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg,
	 *      java.lang.Object)
	 */
	@Override
	public List<KdBackGoodsDealEntity> service(AsyncMsg asyncMsg,
			QryKdBackGoodsDealEntity qryKdBackGoodsDealEntity)
			throws PdaBusiException {
		// 参数校验     没有需要校验的参数
		this.validate(asyncMsg, qryKdBackGoodsDealEntity);
		
		log.debug("---调用FOSS  快递返货受理     单接口开始---参数：" + asyncMsg.getUserCode() + asyncMsg.getDeptCode());
		
		List<CrmReturnedGoodsDto> list = null;
		List<KdBackGoodsDealEntity> result = new ArrayList<KdBackGoodsDealEntity>();
		try {
			list = pdaWaybillService.disposeReturnedDworkOrder(qryKdBackGoodsDealEntity.getUserCode());
		} catch (Exception e) {
			throw new PdaBusiException(e);
		}
		//封装返回值给前台
		if(list != null){
			for(CrmReturnedGoodsDto crmReturnedGoodsDto: list){
				KdBackGoodsDealEntity kdBackGoodsDealEntity = new KdBackGoodsDealEntity();
				//返货原因
				kdBackGoodsDealEntity.setReturnReason(crmReturnedGoodsDto.getReturnReason());
				//返货类型
				kdBackGoodsDealEntity.setReturnType(crmReturnedGoodsDto.getReturnType());
				//调查原因
				kdBackGoodsDealEntity.setSurveyReason(crmReturnedGoodsDto.getReportContent());
				//工单编号
				kdBackGoodsDealEntity.setWorkOrderCode(crmReturnedGoodsDto.getDealnumber());
				//运单号
				kdBackGoodsDealEntity.setWblCode(crmReturnedGoodsDto.getOriWaybill());
				//处理状态
				kdBackGoodsDealEntity.setHandleStatus(crmReturnedGoodsDto.getReturnStatus());
				//返货方式
				kdBackGoodsDealEntity.setReturnWay(crmReturnedGoodsDto.getReturnMode());
				result.add(kdBackGoodsDealEntity);
			}
		}
		
		log.debug("---调用FOSS  快递返货受理     单接口结束---参数：" + asyncMsg.getUserCode() + asyncMsg.getDeptCode());

		return result;
	}

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_KD_BACKGOODS_DEAL.VERSION;
	}

	/**
	 * 同步还是异步 false:同步 true：异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

}
