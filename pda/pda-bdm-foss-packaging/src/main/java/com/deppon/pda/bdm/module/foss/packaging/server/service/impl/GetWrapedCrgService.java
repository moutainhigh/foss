package com.deppon.pda.bdm.module.foss.packaging.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAPackagingService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryPDAUnpackResEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.SerialNoStatusDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.PackagingConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.foss.packaging.shared.domain.WrapedCrgDetail;
import com.deppon.pda.bdm.module.foss.packaging.shared.domain.WrapedSerial;

/**
 * 
 * TODO(查询待包装货物)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-12-3 下午5:01:58,content:TODO
 * </p>
 * 
 * @author Administrator
 * @date 2012-12-3 下午5:01:58
 * @since
 * @version
 */
public class GetWrapedCrgService implements
		IBusinessService<List<WrapedCrgDetail>, Void> {
	private IPDAPackagingService pdaPackagingService;
	private Logger log = Logger.getLogger(getClass());

	@Override
	public Void parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		return null;
	}

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2013-3-20 下午6:31:09
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg,
	 *      java.lang.Object)
	 */
	@Transactional
	@Override
	public List<WrapedCrgDetail> service(AsyncMsg asyncMsg, Void param)
			throws PdaBusiException {
		log.debug("查询待包装货物开始");
		List<QueryPDAUnpackResEntity> unpackRes = null;
		try {
			// 调用FOSS接口
			unpackRes = pdaPackagingService.queryPDAUnpackResult(asyncMsg
					.getDeptCode());
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
		// 封装返回PDA信息实体
		List<WrapedCrgDetail> res = new ArrayList<WrapedCrgDetail>();
		for (QueryPDAUnpackResEntity entity : unpackRes) {
			WrapedCrgDetail model = new WrapedCrgDetail();
			model.setBillDeptCode(entity.getWaybillCreateDept());
			if (entity.getWaybillNum() != null
					&& !entity.getWaybillNum().isEmpty()) {
				model.setBillPieces(Integer.parseInt(entity.getWaybillNum()));
			}
			model.setDriveTime(entity.getPredictDepartDate());
			if (entity.getPackStockNum() != null
					&& !entity.getPackStockNum().isEmpty()) {
				model.setInvtPieces(Integer.parseInt(entity.getPackStockNum()));
			}
			List<WrapedSerial> sers = new ArrayList<WrapedSerial>();
			Map<String, WrapedSerial> map = new HashMap<String, WrapedSerial>();
			for (SerialNoStatusDto dto : entity.getSerialNoStatusDto()) {
				if (!map.containsKey(dto.getSerialNo())) {
					WrapedSerial ser = new WrapedSerial();
					ser.setSerialNo(dto.getSerialNo());
					ser.setStatus(dto.getStatus());
					ser.setWrapType(dto.getPackageType());
					map.put(dto.getSerialNo(), ser);
				} else {
					WrapedSerial ser = map.get(dto.getSerialNo());
					ser.setWrapType(((ser.getWrapType() == null || ser
							.getWrapType().isEmpty()) ? ""
							: (ser.getWrapType() + ","))
							+ ((dto.getPackageType() == null || dto
									.getPackageType().isEmpty()) ? "" : dto
									.getPackageType()));
					map.put(dto.getSerialNo(), ser);
				}
			}
			//将map中的value转换成list
			Set<Entry<String, WrapedSerial>> set = map.entrySet();
			Iterator<Entry<String, WrapedSerial>> it = set.iterator();
			while(it.hasNext()){
				Entry<String, WrapedSerial> entry = it.next();
				sers.add(entry.getValue());
			}
			model.setSerialNo(sers);
			model.setTransType(entity.getTransportationType());
			model.setWblCode(entity.getWayBillNumber());
			model.setWrapRequest(entity.getPackRequire());
			res.add(model);
		}
		log.debug("查询待包装货物成功");
		return res;
	}

	@Override
	public String getOperType() {
		return PackagingConstant.OPER_TYPE_WRAP_QUERY.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaPackagingService(IPDAPackagingService pdaPackagingService) {
		this.pdaPackagingService = pdaPackagingService;
	}
}
