package com.deppon.foss.module.pickup.predeliver.server.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverbillService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IPartitionedViewService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWaybilldetailNewService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PermissionControlDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDetailBillArrageDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.DeliverbillException;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.PartitionedViewVo;
import com.deppon.foss.util.define.FossConstants;

public class PartitionedViewAction extends AbstractAction{
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PartitionedViewAction.class);

	/** 
	 * 序列化
	 */
	private static final long serialVersionUID = 7512460180364008438L;

	/**
	 * 符号
	 */
	private static final String STRING_DELIMETER = ",";
	
	/**
	 * 分区查看service
	 */
	@Resource
	private IPartitionedViewService partitionedviewservice;
	
	/**
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 营业部车队对应
	 */
	private ISalesMotorcadeService salesMotorcadeService;
	
	/** 
	 * 部门查询起始. 
	 */
	private static final int BEGIN_NUM = 0;
	
	private IBusinessLockService businessLockService;
	
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/**
	 * 派送单service
	 */
	private IDeliverbillService deliverbillService;
	
	public void setDeliverbillService(IDeliverbillService deliverbillService) {
		this.deliverbillService = deliverbillService;
	}
	
	private IWaybilldetailNewService waybilldetailNewService;

	public void setWaybilldetailNewService(
			IWaybilldetailNewService waybilldetailNewService) {
		this.waybilldetailNewService = waybilldetailNewService;
	}

	private IHandleQueryOutfieldService handleQueryOutfieldService;
	
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

    private ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 车队编码
	 */
	private String fleetCode;
	
	/** 
	 * 派送部查询页面大小. 
	 */
	private static final int PAGE_SIZE = 100;

	
	/**
	 * 查询大区小区所属的运单集合
	 * @return
	 */
	public String queryWaybills_BigZoneAndSmallZone(){
		try {
			//获取部门
			init();
			List<String> list=this.partitionedviewservice.querywaybills_BigZoneAndSmallZone(
					this.partitionedviewVo.getPartitionedViewDto());
			String[] str=new String[list.size()];
			for(int i=0;i<list.size();i++){
				str[i]=list.get(i);
			}
			this.partitionedviewVo.setWaybills(str);
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 获取组织所对应的车队.
	 *
	 * @return the string
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-22 上午10:50:18
	 */
	@JSON
	public String querySuperOrg() {
		String orgCode = FossUserContextHelper.getOrgCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(orgCode);
		if(orgAdministrativeInfoEntity != null)
		{
			if(FossConstants.YES.equals(orgAdministrativeInfoEntity.getSalesDepartment()))
			{
				//如果是营业部派送部 则通过营业部-车队对应关系表获取车队code
				SalesMotorcadeEntity entity = new SalesMotorcadeEntity();
				entity.setSalesdeptCode(orgCode);
				List<SalesMotorcadeEntity> salesMotorcadeList = salesMotorcadeService.querySalesMotorcadeExactByEntity(entity, BEGIN_NUM, PAGE_SIZE);
				if (!CollectionUtils.isEmpty(salesMotorcadeList))
				{
					StringBuffer sb = new StringBuffer();
					for (SalesMotorcadeEntity salesMotorcadeEntity : salesMotorcadeList) 
					{
							sb.append(salesMotorcadeEntity.getMotorcadeCode()) ;
							sb.append(",");
					}
					fleetCode=StringUtil.isNotEmpty(sb.toString()) ? sb.substring(0, sb.length()-1) :null;
				}else
				{
					fleetCode = FossUserContextHelper.getOrgCode();
				}
			}else{
				// 调用综合组的服务获取当前组织所在的车队
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity1 = orgAdministrativeInfoComplexService.getTopFleetByCode(orgCode);
				if (orgAdministrativeInfoEntity1 != null) 
				{
					fleetCode = orgAdministrativeInfoEntity1.getCode();
				}else
				{
					fleetCode = FossUserContextHelper.getOrgCode();
				}
			}
		}
		//返回成功
		return returnSuccess();
	}
	
	/**
	 * 生成派送单号
	 */
	@JSON
	public String querySequence(){
		try {
			// SUC-447 –创建预派送单 SR6 预派送单号生成规则为：p+序列号（数据库生成，保证唯一）。如p00000001。
			this.sequence="P"+deliverbillService.querySequence();
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	@JSON
	public String waybillDetailSaveDeliverbill() {
		try {
			//外请司机不能修改
			if(StringUtils.isNotBlank(this.partitionedviewVo.getDeliverbill().getDelStatus())) {
				if(this.partitionedviewVo.getDeliverbill().getDelStatus().equals("LOADED")|| this.partitionedviewVo.getDeliverbill().getDelStatus().equals("CONFIRMED")){
					int driverCode=this.partitionedviewVo.getDeliverbill().getDriverCode().length();
					if(driverCode==15 || driverCode==18) {
						return super.returnError("外请司机暂时不能更改，请选择其他类型司机");
					}
				}
			}
			//保存(新增/更新)派送单
			DeliverbillEntity deliverbill = this.waybilldetailNewService.saveDeliverbill(this.partitionedviewVo.getDeliverbill());
			//若派送单为空
			if (deliverbill == null) {
				//返回异常
				return super.returnError("保存失败");
			}
			//设置派送单
			this.partitionedviewVo.setDeliverbill(deliverbill);
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 点击"添加到派送单->"按钮
	 * @return
	 */
	public String waybillDetailAddWaybillToArrange(){
		try {
			
			if(this.partitionedviewVo.getWaybillNos()==null){
				throw new DeliverbillException("传入参数错误，运单号集合不能为空");
			}

			this.partitionedviewVo.setDeliverbill(this.deliverbillService.queryDeliverbill(this.partitionedviewVo.getDeliverbill().getId()));
			
			for(int i=0;i<this.partitionedviewVo.getWaybillNos().length;i++){
				if(this.partitionedviewVo.getNotwaybillNos()!=null && this.partitionedviewVo.getNotwaybillNos().length>0){
					for (int j = 0; j < this.partitionedviewVo.getNotwaybillNos().length; j++) {
						if(!this.partitionedviewVo.getNotwaybillNos()[j].equals(this.partitionedviewVo.getWaybillNos()[i])){
							waybilldetailNewService.waybillDetailAddToArrangeByWaybillNo(this.partitionedviewVo.getDeliverbill(),this.partitionedviewVo.getWaybillNos()[i],this.partitionedviewVo.getDeliverbill().getDeliverDate());
						}
					}
				}else{
					waybilldetailNewService.waybillDetailAddToArrangeByWaybillNo(this.partitionedviewVo.getDeliverbill(),this.partitionedviewVo.getWaybillNos()[i],this.partitionedviewVo.getDeliverbill().getDeliverDate());
				}
			}

			this.partitionedviewVo.setDeliverbill(this.deliverbillService.queryDeliverbill(this.partitionedviewVo.getDeliverbill().getId()));

			//设置装载率
			queryLoadingRate(this.partitionedviewVo.getDeliverbill());

			//返回成功
			return super.returnSuccess("排单成功");
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	public String waybillDetailQueryDetailList() {
		try {
			if(StringUtils.isNotBlank(this.partitionedviewVo.getDeliverbill().getId())){
				//已排单明细
				List<WaybillDetailBillArrageDto> waybillDetailArrageDtos = this.waybilldetailNewService.queryDeliverbillDetailList(partitionedviewVo.getDeliverbill().getId());
				this.partitionedviewVo.setWaybillDetailArrageDtos(waybillDetailArrageDtos);
			} else {
				this.partitionedviewVo.setWaybillDetailArrageDtos(null);
			}
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	public String queryGisWaybillNoDetail(){
		try {
			this.partitionedviewVo.setAddressLabel(this.partitionedviewservice.queryGisWaybillNoDetail(this.partitionedviewVo));
			//返回成功
			return super.returnSuccess();
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}
	
	/**
	 * 设置装载率
	 */
	public void queryLoadingRate(DeliverbillEntity entity){
		try {
			this.partitionedviewVo.setDeliverbill(this.partitionedviewservice.LoadingRate(entity));
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	public String  waybillDetailAddToArrangeByWaybillNo() {
		try {
			
			if(this.partitionedviewVo.getDeliverbill().getId()!=null && StringUtils.isNotBlank(this.partitionedviewVo.getWaybillNo())){
				this.partitionedviewVo.setDeliverbill(this.deliverbillService.queryDeliverbill(this.partitionedviewVo.getDeliverbill().getId()));
				waybilldetailNewService.waybillDetailAddToArrangeByWaybillNo(this.partitionedviewVo.getDeliverbill(),this.partitionedviewVo.getWaybillNo(),this.partitionedviewVo.getDeliverbill().getDeliverDate());
				this.partitionedviewVo.setDeliverbill(this.deliverbillService.queryDeliverbill(this.partitionedviewVo.getDeliverbill().getId()));
				//设置装载率
				queryLoadingRate(this.partitionedviewVo.getDeliverbill());
				return super.returnSuccess("排单成功");
			}else{
				return super.returnError("请输入运单号!");
			}
			//返回成功
		} catch (BusinessException e) {
			//记录日志
			LOGGER.error(e.getMessage(), e);
			//返回异常
			return super.returnError(e);
		}
	}

	public void init(){
		String orgCode = FossUserContextHelper.getOrgCode();
        PermissionControlDto permissionControlDto = new PermissionControlDto();
        permissionControlDto.setNext(true);
        permissionControlDto.setOrgRoleType(0);
        //若当前部门编码不为空时
        if (!StringUtils.isEmpty(orgCode))
        {
            //获取当前用户设置的当前部门
            OrgAdministrativeInfoEntity org = FossUserContext.getCurrentDept();
            //若用户设置的当前部门不为空
            if (org != null)
            {
                String salesDepartment = org.getSalesDepartment();

                // 若当前部门为营业部，则取最终配载部门为当前部门的运单进行排单
                if (FossConstants.YES.equals(salesDepartment))
                {
                    permissionControlDto.setOrgRoleType(1);
                    permissionControlDto.setLastLoadOrgCode(orgCode);
                    // 添加库存外场、库区默认查询条件
                    List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(orgCode);
                    if (CollectionUtils.isNotEmpty(list)) {
                        permissionControlDto.setEndStockOrgCode(list.get(0));
                        permissionControlDto.setGoodsAreaCode(list.get(1));
                    }
                } else
                {
                    // 查询排单服务外场
                    OrgAdministrativeInfoEntity transferCenter = this.orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoOfConnOutfieldByCode(orgCode);
                    String orgCode1 = null;
                    permissionControlDto.setOrgRoleType(0);
                    //
                    if (transferCenter != null)
                    {
                        // 取外场驻地派送部的运单进行排单
                        SaleDepartmentEntity sale = new SaleDepartmentEntity();
                        sale.setTransferCenter(transferCenter.getCode());
                        sale.setDelivery(FossConstants.YES);
                        sale.setActive(FossConstants.ACTIVE);
                        sale.setStation(FossConstants.ACTIVE);
                        List<SaleDepartmentEntity> salesList = saleDepartmentService.querySaleDepartmentExactByEntity(sale, BEGIN_NUM, PAGE_SIZE);
                        //若salelist集合不为空
                        if (!CollectionUtils.isEmpty(salesList))
                        {
                            orgCode1 = salesList.get(0).getCode();
                            // 添加库存外场、库区默认查询条件
                            List<String> list = handleQueryOutfieldService.getEndStockCodeAndAreaCode(orgCode1);
                            if (CollectionUtils.isNotEmpty(list)) {
                                permissionControlDto.setEndStockOrgCode(list.get(0));
                                permissionControlDto.setGoodsAreaCode(list.get(1));
                            }
                            permissionControlDto.setLastLoadOrgCode(orgCode1);
                        }else{
                            permissionControlDto.setNext(false);
                            this.partitionedviewVo.getPartitionedViewDto().setPermissionControlDto(permissionControlDto);
                        }
                    }else{
                        permissionControlDto.setNext(false);
                        this.partitionedviewVo.getPartitionedViewDto().setPermissionControlDto(permissionControlDto);
                    }

                }
                this.partitionedviewVo.getPartitionedViewDto().setPermissionControlDto(permissionControlDto);
            } else{
                permissionControlDto.setNext(false);
                this.partitionedviewVo.getPartitionedViewDto().setPermissionControlDto(permissionControlDto);
            }

        } else
        {
            permissionControlDto.setNext(false);
            this.partitionedviewVo.getPartitionedViewDto().setPermissionControlDto(permissionControlDto);
        }
	}
	
	public void setPartitionedviewservice(
			IPartitionedViewService partitionedviewservice) {
		this.partitionedviewservice = partitionedviewservice;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setSalesMotorcadeService(
			ISalesMotorcadeService salesMotorcadeService) {
		this.salesMotorcadeService = salesMotorcadeService;
	}

	public void setFleetCode(String fleetCode) {
		this.fleetCode = fleetCode;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	
	private String  sequence;
	

	public String getSequence() {
		return sequence;
	}

	public PartitionedViewVo partitionedviewVo=new PartitionedViewVo();

	public void setPartitionedviewVo(PartitionedViewVo partitionedviewVo) {
		this.partitionedviewVo = partitionedviewVo;
	}

	public PartitionedViewVo getPartitionedviewVo() {
		return partitionedviewVo;
	}

	public void setHandleQueryOutfieldService(
			IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
}
