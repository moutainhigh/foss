package com.deppon.pda.bdm.module.foss.load.server.service.impl.electronicbill;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.server.dao.ILoadPdaDao;
import com.deppon.pda.bdm.module.foss.load.shared.domain.HandoverDept;


/**
 * @ClassName GetHandoverDeptsEWaybillService.java 
 * @Description 获取交接部门信息
 * @author 201638
 * @date 2015-1-30
 */
public class GetHandoverDeptsEWaybillService implements IBusinessService<List<HandoverDept>, HandoverDept> {
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(GetHandoverDeptsEWaybillService.class);
	
	private ILoadPdaDao loadPdaDao;

	public void setLoadPdaDao(ILoadPdaDao loadPdaDao) {
		this.loadPdaDao = loadPdaDao;
	}
	
	@Override
	public HandoverDept parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		 //解析内容
		HandoverDept handoverDept = JsonUtil.parseJsonToObject(HandoverDept.class, asyncMsg.getContent());
        return handoverDept;
	}

	/**
	 * @description 获取交接部门
	 * @param asyncMsg 异步数据
	 * @param param void
	 * @return 交接部门list
	 * @throws PdaBusiException
	 * @author 201638
	 * @date 2015-1-30 
	 */
	@Transactional
	@Override
	public List<HandoverDept> service(AsyncMsg asyncMsg, HandoverDept param) throws PdaBusiException {
		Argument.hasText(asyncMsg.getUserCode(), "asyncMsg.getUserCode()");//校验工号非空
		List<HandoverDept> depts = null;
		try {
			//如果城市编码为空，则根据工号查
			if("".equals(param.getCityCode())){
				depts = loadPdaDao.getHandoverDepts(asyncMsg.getUserCode());
			}else{
				//根据城市编码查询该传城市下的所有交接部门
				depts = loadPdaDao.getHandoverDeptsByCityCode(param.getCityCode());
			}
		} catch (PdaBusiException e) {
			throw new PdaBusiException(e.getCause());
		}
		return depts;
	}


	/**
	 * @description 电子运单二期 接货装车 获取交接部门
	 * @return ACCT_42
	 * @author 201638
	 * @date 2015-1-29
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_EXP_RCV_GET_HANDOVER_DEPT.VERSION;
	}

	/**
	 * @description 同步接口
	 * @return false
	 * @author 201638
	 * @date 2015-1-29
	 */
	@Override
	public boolean isAsync() {
		return false;
	}


}
