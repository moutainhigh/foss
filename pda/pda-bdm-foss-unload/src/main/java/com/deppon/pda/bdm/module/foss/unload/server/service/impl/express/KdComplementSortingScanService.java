package com.deppon.pda.bdm.module.foss.unload.server.service.impl.express;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAComplementService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAComplementDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.SortingScanDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.kuaidi.QrySortingReqModel;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.kuaidi.QrySortingResModel;



/**
 * 
 * 分拣扫描接口和查询补码
 * @author 245955
 * @version 1.0
 * @created 2015年8月13日14:51:12
 */
public class KdComplementSortingScanService implements IBusinessService<List<QrySortingResModel>, QrySortingReqModel> {
	private static final Log LOG = LogFactory.getLog(KdComplementSortingScanService.class);
	private IPDAComplementService pdaComplementService;
	@Override
	public QrySortingReqModel parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		QrySortingReqModel sortingReq = JsonUtil.parseJsonToObject(QrySortingReqModel.class, asyncMsg.getContent());
		return sortingReq;
	}
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<QrySortingResModel> service(AsyncMsg asyncMsg, QrySortingReqModel param)
			throws PdaBusiException {
		LOG.debug("=============分拣扫描和查询补码start====================");
		//校验请求参数
		this.validateBusinessData(param);
		List<QrySortingResModel> list=new ArrayList<QrySortingResModel>();
		try {
			Date date = param.getSignTime();
			boolean paramBoolean = param.isQueryType();
			String wblCode = param.getWblCode();
			if (wblCode != null && !wblCode.equals("")) {// 运单号不等于空
				date = null;
			} else {
				if (date == null) {
					Calendar canlendar = Calendar.getInstance(); // java.util包
					canlendar.add(Calendar.DATE, -3); // 日期减 如果不够减会将月变动
					date = canlendar.getTime();
				}
			}
			//封装FOSS请求参数
			SortingScanDto fossScanReq=this.fossSortingScanReq(param);
			// 调用FOSS接口
			Map<String,Object> map=pdaComplementService.queryComplement(date, wblCode, paramBoolean, asyncMsg.getDeptCode(),param.getIfPackage(),fossScanReq);
			List<SortingScanDto> slist=(List<SortingScanDto>) map.get("SevenReturnList");
			List<PDAComplementDto> plist=(List<PDAComplementDto>) map.get("ComplementList");
			list=getResult(plist, slist);
		} catch (BusinessException e) {
			LOG.error("分拣扫描异常"+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		LOG.debug("=============分拣扫描和查询补码start====================");
		return list;
	}

	/**
	 * 操作类型
	 */
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_COMP_QUERY_SORTING_SCAN.VERSION;
	}

	/**
	 * 是否异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	/**
	 * 参数有效新校验
	 */
	private void validateBusinessData(QrySortingReqModel param) {
		Argument.notNull(param, "QrySortingReq");
		Argument.hasText(param.getWblCode(), "QrySortingReq.wblCode");
		Argument.hasText(param.getUserCode(), "QrySortingReq.userCode");
		Argument.hasText(param.getDeptCode(), "QrySortingReq.deptCode");
		Argument.notNull(param.getSignTime(), "QrySortingReq.signTimeStr");
		Argument.hasText(param.getScanType(), "QrySortingReq.scanType");
		Argument.hasText(param.getLabelCode(), "QrySortingReq.labelCode");
		Argument.hasText(param.getPdaCode(), "QrySortingReq.pdaCode");
		Argument.hasText(param.getIfPackage(), "QrySortingReq.ifPackage");
	}
	/**
	 * 封装返回参数
	 */
	private SortingScanDto fossSortingScanReq(QrySortingReqModel param){
		SortingScanDto scan=new SortingScanDto();
		scan.setWayBillNo(param.getWblCode());//运单号
		scan.setOperatorCode(param.getUserCode());//员工号
		scan.setOrgCode(param.getDeptCode());//部门编码
		scan.setScanTime(param.getSignTime());//操作时间
		scan.setScanType(param.getScanType());//扫描类型
		scan.setSerialNo(param.getLabelCode());//标签号
		scan.setDeviceNo(param.getPdaCode());//PDA 设备号
		return scan;
	}
	/**
	 * 封装返回实体
	 */
	private List<QrySortingResModel> getResult(List<PDAComplementDto> plist,List<SortingScanDto> slist) {
		List<QrySortingResModel> resList = new ArrayList<QrySortingResModel>();
		if (plist == null || plist.isEmpty()) {
			QrySortingResModel qsRes = new QrySortingResModel();
			qsRes.setSevenReturnList(slist);
			resList.add(qsRes);
			return resList;
		}
		for (PDAComplementDto d : plist) {
			QrySortingResModel qsRes = new QrySortingResModel();
			qsRes.setWblCode(d.getWayBillNo());// 运单号
			qsRes.setTargetOrgCode(d.getTargetOrgCode());// 目的部门编码
			qsRes.setDeryCrgAddress(d.getReceiveCustomerAddress());// 收货地址
			qsRes.setComplementDate(d.getComplementTime());// 补码时间
			qsRes.setReachOrgCode(d.getReachOrgCode());// 提货网点编码
			qsRes.setReachOrgName(d.getReachOrgName());// 提货网点名称
			qsRes.setIsBeAddCode(d.getBeAddCode());// 是否补码
			qsRes.setFinalOutDept(d.getFinalOutDept());// 最终到达外场
			//author:245960 Date 2015-06-09
			qsRes.setBeEWaybill(d.getBeEWaybill());//是否电子面单
			qsRes.setCityShort(d.getSimpleOrgName());
			qsRes.setNextOrgCode(d.getNextOrgCode());
			qsRes.setProductCode(d.getProductCode());
			qsRes.setSevenReturnList(slist);
			resList.add(qsRes);
		}
		return resList;
	}
	
	
	public void setPdaComplementService(IPDAComplementService pdaComplementService) {
		this.pdaComplementService = pdaComplementService;
	}

}
