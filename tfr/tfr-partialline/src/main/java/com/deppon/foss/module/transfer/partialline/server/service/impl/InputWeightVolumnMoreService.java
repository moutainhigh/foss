package com.deppon.foss.module.transfer.partialline.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IInputWeightVolumnMoreDao;
import com.deppon.foss.module.transfer.partialline.api.server.service.IInputWeightVolumnMoreService;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.InputWeightVolumnEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.InputWeightVolumnMoreDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 一票多件外发重量体积录入service
 * @author 257220
 *
 */
public class InputWeightVolumnMoreService implements IInputWeightVolumnMoreService{
	private IInputWeightVolumnMoreDao inputWeightVolumnMoreDao;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	private IWaybillRelateDetailEntityService waybillRelateDetailEntityService;
	private ILdpExternalBillService ldpExternalBillService;
	private IWaybillManagerService waybillManagerService;
	public List<InputWeightVolumnMoreDto> queryInputWeightVolumnMoreList(InputWeightVolumnMoreDto dto,int start, int limit){
		String deptCode = "";
		CurrentInfo user = FossUserContext.getCurrentInfo();
		if(user != null) {
			String currentDeptCode = user.getCurrentDeptCode();
			OrgAdministrativeInfoEntity entity = this.getBigOrg(currentDeptCode);
			if(entity != null ) {
				deptCode = entity.getCode();
				dto.setDeptCode(deptCode);
			}
		}
		return inputWeightVolumnMoreDao.queryInputWeightVolumnMoreList(dto,start,limit);
	}
	
	public Long queryInputWeightVolumnMoreListCount(InputWeightVolumnMoreDto dto){
		return inputWeightVolumnMoreDao.queryInputWeightVolumnMoreListCount(dto);
	}
	/**
	 * <ul>批量保存修改的重量体积方法<ul>
	 * <li>如果有录入则修改</li>
	 * <li>如果未曾录入则新增</li>
	 * @param list
	 * @author 257220
	 * @date 2015-11-16下午5:52:31
	 */
	@Transactional
	public void save(List<InputWeightVolumnMoreDto> list){
		if(list == null || list.size()==0){
			return ;
		}
		UserEntity user = FossUserContext.getCurrentUser();
		OrgAdministrativeInfoEntity orgInfo = FossUserContext.getCurrentDept();
		for(InputWeightVolumnMoreDto inputWeightVolumnMoreDto : list){
			//完善更新用户机构等信息
			inputWeightVolumnMoreDto = completeWeightVolumnDto(inputWeightVolumnMoreDto,user,orgInfo);
			//保存一条记录
			save(inputWeightVolumnMoreDto);
		}
	}
	/**
	 * 保存一条记录
	 *@param 
	 *@param inputWeightVolumnMoreDto
	 *@return
	 *@date  2015-11-16上午9:56:27
	 *@author 257220
	 */
	public int save(InputWeightVolumnMoreDto inputWeightVolumnMoreDto){
		//是否新增过,新增过则修改
		if(this.queryInputWeightVolumnEntity(inputWeightVolumnMoreDto) != null){
			return updateWeightVolumnMoreInfo(inputWeightVolumnMoreDto);
		}else{
			inputWeightVolumnMoreDto.setId(UUIDUtils.getUUID());
			return addWeightVolumnMoreInfo(inputWeightVolumnMoreDto);
		}
	}
	/**
	 *查询一条记录
	 *@param inputWeightVolumnMoreDto
	 *@return
	 *@date  2015-11-16上午10:01:23
	 *@author 257220
	 */
	public InputWeightVolumnEntity queryInputWeightVolumnEntity(InputWeightVolumnMoreDto inputWeightVolumnMoreDto){
		String waybillNo = inputWeightVolumnMoreDto.getWaybillNo();
		String serialNo = inputWeightVolumnMoreDto.getSerialNo();
		return this.inputWeightVolumnMoreDao.queryEntityByWaybillNoSerialNo(waybillNo,serialNo);
	}
	/**
	 * 批量保存数据
	 *@param 
	 *@date  2015-11-16下午2:35:13
	 *@author 257220
	 */
	public void batchSaveDataFromExcel(List<InputWeightVolumnMoreDto> list){
		//验证数据
		validDataFromExcel(list);
		//保存数据
		save(list);
	}
	/**
	 * 验证数据
	 * <li>验证重量是否大于0</li>
	 * <li>验证是否子母件</li>
	 * <li>是否一票多件</li>
	 * <li>校验每一票是否生成外发单</li>
	 * <li>未生成交接单的不能导入 (已去除)</li>
	 *@param list
	 *@date  2015-11-16下午2:37:32
	 *@author 257220
	 */
	private void validDataFromExcel(List<InputWeightVolumnMoreDto> list){
		if(list == null){
			return;
		}
		for (InputWeightVolumnMoreDto inputWeightVolumnMoreDto : list) {
			String waybillNo = inputWeightVolumnMoreDto.getWaybillNo();
			String serialNo = inputWeightVolumnMoreDto.getSerialNo();
			BigDecimal weight = inputWeightVolumnMoreDto.getWeight();
			BigDecimal volumn = inputWeightVolumnMoreDto.getVolumn();
			//校验是否一票多件
			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNo);
			if(waybillEntity == null){
				throw new BusinessException("运单号为 "+ waybillNo +"的运单不存在，请检查！","运单号 "+ waybillNo + "的运单不存在，请检查！");
			}
			Integer goodsQtyTotal = waybillEntity.getGoodsQtyTotal();
			if(goodsQtyTotal == null || goodsQtyTotal <= 1){
				throw new BusinessException("运单号为 "+ waybillNo +"的运单非一票多件货，请检查！","运单号 "+ waybillNo + "的运单不存在，请检查！");
			}
			//验证重量是否大于0
			if(weight ==null || weight.compareTo(BigDecimal.ZERO) <= 0){
				throw new BusinessException("运单号 "+ waybillNo + "流水号"+serialNo+"的重量为空或小于等于0，请检查！","运单号 "+ waybillNo +  "流水号"+serialNo+"的重量小于等于0，请检查！");
			}
			//验证体积是否大于等于0
			if(volumn == null || volumn.compareTo(BigDecimal.ZERO) < 0){
				throw new BusinessException("运单号 "+ waybillNo + "流水号"+serialNo+"的体积为空或小于0，请检查！","运单号 "+ waybillNo +  "流水号"+serialNo+"的重量小于等于0，请检查！");
			}
			//判断是否子母件
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("waybillNo", waybillNo);
			paramMap.put("active", FossConstants.ACTIVE);
			TwoInOneWaybillDto twoInOneWaybillDto = waybillRelateDetailEntityService.queryWaybillRelateByWaybillOrOrderNo(paramMap);
			if(twoInOneWaybillDto != null){
				String twoInOne = twoInOneWaybillDto.getIsTwoInOne();
				if(StringUtils.equals(twoInOne, FossConstants.ACTIVE)){
					throw new BusinessException("运单号 "+ waybillNo +"为子母件，请在子母件录入重量体积界面录入!","运单号 "+ waybillNo +  "为子母件，请在子母件录入重量体积界面录入!");
				}
			}
			//校验是否存在外发单
			List<LdpExternalBillDto> listExternalBillDtos = this.queryLdpExternalBill(waybillNo,serialNo);
			if(listExternalBillDtos!=null && listExternalBillDtos.size() > 0){
				throw new BusinessException("运单号 "+ waybillNo + "流水号"+serialNo+ "已生成外发单！","运单号 "+ waybillNo + "已生成外发单！");
			}
			//校验是否生成交接单
			/*boolean existHandOverBill = handOverBillService.queryBeLdpHandOveredByWaybillNo(waybillNo);
			if(!existHandOverBill){
				throw new BusinessException("运单号 "+ waybillNo + "未生成交接单,不能导入！","运单号 "+ waybillNo + "未生成交接单,不能导入！");
			}*/
		}
	}
	private InputWeightVolumnMoreDto completeWeightVolumnDto(InputWeightVolumnMoreDto inputWeightVolumnMoreDto,UserEntity currentUser,OrgAdministrativeInfoEntity currentOrg){
		inputWeightVolumnMoreDto.setModifyUserCode(currentUser.getEmployee().getEmpCode());
		inputWeightVolumnMoreDto.setModifyUserName(currentUser.getEmpName());
		inputWeightVolumnMoreDto.setModifyOrgCode(currentOrg.getCode());
		inputWeightVolumnMoreDto.setModifyOrgName(currentOrg.getName());
		return inputWeightVolumnMoreDto;
	}
	public int updateWeightVolumnMoreInfo(InputWeightVolumnMoreDto inputWeightVolumnMoreDto){
		return this.inputWeightVolumnMoreDao.updateWeightVolumnMoreInfo(inputWeightVolumnMoreDto);
	}
	
	public int addWeightVolumnMoreInfo(InputWeightVolumnMoreDto inputWeightVolumnMoreDto){
		return this.inputWeightVolumnMoreDao.addWeightVolumnMoreInfo(inputWeightVolumnMoreDto);
	}
	/**
	 * @author nly
	 * @date 2014年9月22日 下午5:18:27
	 * @function 获取上级外场
	 * @param currentOrg
	 * @return
	 */
	private OrgAdministrativeInfoEntity getBigOrg(String currentOrgCode){
			//设置查询参数
			List<String> bizTypesList = new ArrayList<String>();
			//外场类型
			bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
			OrgAdministrativeInfoEntity bigOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(currentOrgCode, bizTypesList);
			if(bigOrg == null){
				throw new TfrBusinessException("查询当前部门所属外场失败","查询当前部门所属外场失败");
			}
			return bigOrg;
	}
	/**
	 * @author 257220
	 * @date 2015年4月29日 上午11:05:12
	 * @function 是否存在有效的快递代理外发单
	 * @param waybillNos
	 * @return
	 * @throws Exception 
	 */
	
	private List<LdpExternalBillDto> queryLdpExternalBill(String waybillNo,String serialNo) {
			LdpExternalBillDto dto = new LdpExternalBillDto();
			dto.setWaybillNo(waybillNo);
			dto.setTransferExternal(PartiallineConstants.IS_TRANSFER_EXTERNAL_NO);
			List<String> statusList = new ArrayList<String>();
			statusList.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
			statusList.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
			statusList.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT);
			dto.setList(statusList);
			dto.setSerialNo(serialNo);
			List<LdpExternalBillDto> resultList = ldpExternalBillService.queryOrigLdpExternalBillList(dto);
			return resultList;
	}
	public void setInputWeightVolumnMoreDao(
			IInputWeightVolumnMoreDao inputWeightVolumnMoreDao) {
		this.inputWeightVolumnMoreDao = inputWeightVolumnMoreDao;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setWaybillRelateDetailEntityService(
			IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
		this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
	}
	
	public void setLdpExternalBillService(
			ILdpExternalBillService ldpExternalBillService) {
		this.ldpExternalBillService = ldpExternalBillService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
}
