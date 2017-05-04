/**
 * @author foss 257200
 * 2015-8-15
 * 257220
 */
package com.deppon.foss.module.transfer.partialline.server.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.transfer.partialline.api.server.service.IPrintAgentWaybillService;
import com.deppon.foss.module.transfer.partialline.api.server.service.IPrintCZMTipsService;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillRecordEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintCZMTipsEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.PrintCZMTipsDto;
import com.deppon.foss.module.transfer.partialline.api.shared.exception.ExternalBillException;
import com.deppon.foss.module.transfer.partialline.api.shared.vo.PrintTipsVo;
import com.deppon.foss.module.transfer.partialline.server.service.impl.PrintCZMTipsService;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 外发打印提示标签action类
 * @author 257220
 * @date 2015-08-15 上午10:25:36
 */
public class PrintCZMTipsAction extends AbstractAction  {

	private static final long serialVersionUID = 7676767L;
	private static final Logger LOGGER=LoggerFactory.getLogger(PrintCZMTipsAction.class);
	private PrintTipsVo vo;
	private IPrintCZMTipsService printCZMTipsService;
	private IPrintAgentWaybillService printAgentWaybillService;
	private IWaybillRelateDetailEntityService waybillRelateDetailEntityService;
	/**
	 * <p>查询打印提示信息list</p>
	 * @return
	 * @author 257220
	 * @date 2015-8-17下午4:31:42
	 */
	public String queryCZMTipsList(){
		try {
			LOGGER.info("查询打印提示信息list开始...");
			if(vo == null){
				return returnSuccess();
			}
			String waybillNos = vo.getWaybillNos();
			String handOverbillNos = vo.getHandOverBillNos();
			//查询条件为空，则不往下执行
			if(StringUtils.isEmpty(waybillNos)&&StringUtils.isEmpty(handOverbillNos)){
				return returnSuccess(); 
			}
			List<String> waybillNoList = this.getListFromStr(vo.getWaybillNos());
			List<String> handoverNoList = this.getListFromStr(vo.getHandOverBillNos());
			String tips = "";
			//校验运单是否子母件
			//XXXXXXXX,XXXXXXXXX,XXXXXXX 不属于子母件，不展示相关信息！
			for (String waybillNo : waybillNoList) {
				Map<String,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("waybillNo", waybillNo);
				paramMap.put("active", FossConstants.ACTIVE);
				//判断是否子母件 
				TwoInOneWaybillDto twoInOneWaybillDto = waybillRelateDetailEntityService.queryWaybillRelateByWaybillOrOrderNo(paramMap);
				if(twoInOneWaybillDto != null){
					String twoInOne = twoInOneWaybillDto.getIsTwoInOne();
					if(!StringUtils.equals(twoInOne, FossConstants.ACTIVE)){
						tips = tips + waybillNo + ",";
					}
				}
			}
			if(tips.length() != 0){
				tips = tips.substring(0, tips.length() - 1) + "不属于子母件，不展示相关信息！";
			}
			PrintCZMTipsDto printTipsDto = new PrintCZMTipsDto(); 
			if(waybillNoList != null && waybillNoList.size() != 0){
				printTipsDto.setWaybillNoList(waybillNoList);
			}else if(handoverNoList != null && handoverNoList.size() != 0){
				printTipsDto.setHandOverBillList(handoverNoList);
			}
			totalCount = printCZMTipsService.queryCZMTipsListCount(printTipsDto);
			if(totalCount > 0){
				List<PrintCZMTipsDto> printTipsDtoList = printCZMTipsService.queryCZMTipsList(printTipsDto,this.getStart(),this.getLimit());
				vo.setList(printTipsDtoList);
			}
			this.setTotalCount(totalCount);
			vo.setTips(tips);
			return returnSuccess();
		} catch (ExternalBillException e) {
			LOGGER.info("查询打印提示信息...");
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String obtainPrintTipsData(){
		try {
			LOGGER.info("提示信息开始...");
			//校验是否能打印
			//校验运单是否绑定 多选或单选的运单中存在某些运单未绑定代理信息，或其对应子母件未绑定代理单号时（即子母件任意子件、母件未绑定，都算异常)
			//“XXXXXXXX,XXXXXXXXX,XXXXXXX运单或其子母件未绑定代理单号，不能打印标签！
			List<PrintCZMTipsDto> list = vo.getList();
			if(list == null || list.size() == 0){
				return returnError("请选择数据");
			}
			List<PrintCZMTipsDto> retList = new ArrayList<PrintCZMTipsDto>();
			//提示信息
			String tips = "";//“XXXXXXXX,XXXXXXXXX,XXXXXXX运单或其子母件未绑定代理单号，不能打印标签！
			for (PrintCZMTipsDto printCZMTipsDto : list) {
				String waybillNo = printCZMTipsDto.getWaybillNo();
				String parentWaybillNo = printCZMTipsDto.getParentWaybillNo();
				//关联的代理单号
				String agentWaybillNos = "";
				int number = 0; 
				boolean isExistNoBind = false;//是否存在未绑定的运单
				//获取运单对应的子母件信息
				List<String> relateList = waybillRelateDetailEntityService.queryWaybillNoListByParentWaybillNo(parentWaybillNo);
				if(relateList != null){
					for(String refWaybillNo : relateList){
						//获取子母件对应代理单号
						List<PrintAgentWaybillRecordEntity> printAgentWaybills = printAgentWaybillService.queryRecordByWaybillNo(refWaybillNo,"LDP");
						//判断子母件中是否有未绑定的运单
						if(printAgentWaybills == null||printAgentWaybills.size() == 0 || printAgentWaybills.get(0) == null){
							isExistNoBind = true;
							break;
						}
						String agentWaybillNo = printAgentWaybills.get(0).getAgentWaybillNo();
						if(!StringUtils.equals(waybillNo, refWaybillNo)){//去除自身运单号
							agentWaybillNos = agentWaybillNos + agentWaybillNo + ",";
						}
					}
				}
				//如果存在未绑定的运单，则将该运单号加入提示信息中
				if(isExistNoBind){
					tips = tips + waybillNo + ",";
				}else{//检查通过的运单返回
					if(agentWaybillNos != null){
						agentWaybillNos = agentWaybillNos.substring(0, agentWaybillNos.length() - 1);//去除最后的逗号
					}
					number = CollectionUtils.isEmpty(relateList) ? 0 : relateList.size();
					printCZMTipsDto.setRefAgentWaybillNos(agentWaybillNos);
					printCZMTipsDto.setNumber(number);
					retList.add(printCZMTipsDto);
				}
			}
			if(!StringUtils.isEmpty(tips)){
				tips = tips.substring(0, tips.length() - 1);//去除最后的逗号
				tips = tips + "运单或其子母件未绑定代理单号，不能打印标签！";
			}
			vo.setTips(tips);
			vo.setList(retList);
			return returnSuccess();
		} catch (ExternalBillException e) {
			return returnError(e);
		}
	}
	
	public String savePrintRecord(){
		try {
			LOGGER.info("保存打印信息开始...");
			PrintCZMTipsDto printCZMTipsDto = vo.getPrintCZMTipsDto();
			if(printCZMTipsDto == null){
				return returnError("保存数据为空!");
			}
			PrintCZMTipsEntity printCZMTipsEntity = new PrintCZMTipsEntity();
			BeanUtils.copyProperties(printCZMTipsDto, printCZMTipsEntity);
			CurrentInfo user = FossUserContext.getCurrentInfo();
			OrgAdministrativeInfoEntity org = FossUserContext.getCurrentDept();
			String userCode = user.getEmpCode();
			String userName = user.getUserName();
			String orgCode = org.getCode();
			String orgName = org.getName();
			printCZMTipsEntity.setPrintUserCode(userCode);
			printCZMTipsEntity.setPrintUserName(userName);
			printCZMTipsEntity.setPrintOrgCode(orgCode);
			printCZMTipsEntity.setPrintOrgName(orgName);
			printCZMTipsEntity.setUpdateOrgCode(orgCode);
			//数据转换
			printCZMTipsService.savePrintRecord(printCZMTipsEntity);
			return returnSuccess();
		} catch (ExternalBillException e) {
			return returnError(e);
		}
	}
	
	/**
	 * @author 257220
	 * @date 2015年8月26日下午8:43:55
	 * @function 把string转为list
	 * @param s
	 * @return
	 */
	private List<String> getListFromStr(String s) {
		List<String> list = new ArrayList<String>();
		if(StringUtils.isNotEmpty(s)) {
			String[] handOverBills = s.split("\\n");
			for(String billNo:handOverBills) {
				if(StringUtils.isNotEmpty(billNo)) {
					list.add(billNo);
				}
			}
		}
		return list;
	}
	public PrintTipsVo getVo() {
		return vo;
	}

	public void setVo(PrintTipsVo vo) {
		this.vo = vo;
	}

	public void setPrintCZMTipsService(PrintCZMTipsService printCZMTipsService) {
		this.printCZMTipsService = printCZMTipsService;
	}

	public void setPrintCZMTipsService(IPrintCZMTipsService printCZMTipsService) {
		this.printCZMTipsService = printCZMTipsService;
	}

	public void setPrintAgentWaybillService(
			IPrintAgentWaybillService printAgentWaybillService) {
		this.printAgentWaybillService = printAgentWaybillService;
	}

	public void setWaybillRelateDetailEntityService(
			IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
		this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
	}
	
	
}
