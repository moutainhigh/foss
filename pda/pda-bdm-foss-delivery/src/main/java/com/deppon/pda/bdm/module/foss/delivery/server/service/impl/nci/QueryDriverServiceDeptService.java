package com.deppon.pda.bdm.module.foss.delivery.server.service.impl.nci;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.delivery.server.dao.IDeliveryPdaDao;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.DriverSerDeptResultEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.QueryDriverServiceEntity;


/**
 * 
 * @ClassName: QueryDriverServiceDeptService 
 * @Description: TODO(司机服务部门) 
 * @author &268974  wangzhili
 * @date 2016-1-27 下午2:20:51 
 *
 */
public class QueryDriverServiceDeptService implements IBusinessService<List<DriverSerDeptResultEntity>, QueryDriverServiceEntity>{

	private static final Log LOG = LogFactory.getLog(QueryDriverServiceDeptService.class);
	
	private IDeliveryPdaDao deliveryPdaDao;
	//解析参数
	@Override
	public QueryDriverServiceEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		QueryDriverServiceEntity entity = JsonUtil.parseJsonToObject(QueryDriverServiceEntity.class,asyncMsg.getContent());
		return entity;
	}

	//方法入口
	@Override
	public List<DriverSerDeptResultEntity> service(AsyncMsg asyncMsg, QueryDriverServiceEntity param) throws PdaBusiException {
		//校验参数合法性
		this.validate(param,asyncMsg);
		List<DriverSerDeptResultEntity>  result = null;
		try{
			LOG.info("查询司机服务部门");
		    result = deliveryPdaDao.queryDriverDeptCode(param.getDeptName());
			LOG.info("部门编码"+result);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}catch(Exception e){
			throw new FossInterfaceException(null,"出现未知异常");
		}
		return result;
	}

	//操作类型
	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_DRIVEY_DEPT.VERSION;
	}

	//同步
	@Override
	public boolean isAsync() {
		return false;
	}

	/**
	 * 
	 * @Title: validate 
	 * @Description: TODO(校验参数的合法性) 
	 * @param @param asyncMsg    设定文件 
	 * @return void    返回类型 
	 * @throws 
	 * @author： 268974  wangzhili
	 */
	private void validate(QueryDriverServiceEntity entity , AsyncMsg asyncMsg) {
		Argument.notNull(asyncMsg, "AsyncMsg");
		// 工号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		// 部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		// 用户类型
		Argument.hasText(asyncMsg.getUserType(), "AsyncMsg.userType");
		//部门名称
		Argument.hasText(entity.getDeptName(), "entity.deptName");
	}

	public void setDeliveryPdaDao(IDeliveryPdaDao deliveryPdaDao) {
		this.deliveryPdaDao = deliveryPdaDao;
	}


	

}
