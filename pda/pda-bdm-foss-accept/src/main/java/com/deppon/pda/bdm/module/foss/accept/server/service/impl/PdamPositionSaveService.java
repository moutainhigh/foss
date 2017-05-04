package com.deppon.pda.bdm.module.foss.accept.server.service.impl;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoginConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.server.dao.IPdamPositionDao;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.PositionEntity;

/***
 * pdam经纬度数据保存service
 * @author 245960
 *
 */
public class PdamPositionSaveService implements IBusinessService<String, PositionEntity> {
	private Logger log = Logger.getLogger(PdamPositionSaveService.class);
	
	private IPdamPositionDao pdamPositionDao;
	
	public void setPdamPositionDao(IPdamPositionDao pdamPositionDao) {
		this.pdamPositionDao = pdamPositionDao;
	}

	/**
	 * 解析包体
	 */
	@Override
	public PositionEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		PositionEntity positionEntity =JsonUtil.parseJsonToObject(PositionEntity.class,
				asyncMsg.getContent());
		positionEntity.setUserCode(asyncMsg.getUserCode());
		
		return positionEntity;
	}

	/**
	 * 服务方法
	 */
	@Transactional
	@Override
	public String service(AsyncMsg asyncMsg, PositionEntity param)
			throws PdaBusiException {
		System.out.println("测试" + param);
		//校验参数是否合法。
		this.validateBusinessData(param);
		log.info("保存pdam经纬度数据开始");
		//调用dao保存数据
		pdamPositionDao.savePosition(param);
		log.info("保存pdam经纬度数据结束");
		
		return param.getUuid();
	}
	/**
	 * 参数有效校验
	 */
	private void validateBusinessData(PositionEntity param) {
		Argument.notNull(param, "PositionEntity");
		Argument.hasText(param.getLatitude(), "PositionEntity.latitude");
		Argument.hasText(param.getLongitude(), "PositionEntity.longitude");
		Argument.hasText(param.getUserCode(), "PositionEntity.userCode");
		Argument.notNull(param.getPositionTime(), "PositionEntity.positionTime");
		Argument.hasText(param.getUuid(), "PositionEntity.uuid");
	}
	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {

		return LoginConstant.OPER_TYPE_DPAM_SYS_POSITION.VERSION;
	}

	/**
	 * 是否异步
	 */
	@Override
	public boolean isAsync() {

		return false;
	}

}
