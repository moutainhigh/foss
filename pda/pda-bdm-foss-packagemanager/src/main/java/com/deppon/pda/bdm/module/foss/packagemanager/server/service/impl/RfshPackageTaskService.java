package com.deppon.pda.bdm.module.foss.packagemanager.server.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPackageService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressPackageDetailDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.PackageManagerConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.BitUtil;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.packagemanager.shared.domain.PackageGoodsDetail;
import com.deppon.pda.bdm.module.foss.packagemanager.shared.domain.QryPackageInfo;
import com.deppon.pda.bdm.module.foss.packagemanager.shared.domain.SerialNo;

/**
 * 
  * @ClassName RfshPackageTaskService 
  * @Description TODO 刷新建包明细
  * @author mt 
  * @date 2013-7-25 上午9:07:57
 */
public class RfshPackageTaskService implements IBusinessService<List<PackageGoodsDetail>, QryPackageInfo> {
	private static final Log LOG = LogFactory.getLog(RfshPackageTaskService.class);
	private IPDAExpressPackageService pdaExpressPackageService;
	/**
	 * 
	 * <p>TODO 刷新建包明细</p> 
	 * @author mt
	 * @date 2013年7月30日9:58:34
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 */
	@Override
	public QryPackageInfo parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		QryPackageInfo model = JsonUtil.parseJsonToObject(QryPackageInfo.class,asyncMsg.getContent());
		return model;
	}
	
	/**
	 * 
	 * <p>TODO 刷新建包明细</p> 
	 * @author mt
	 * @date 2013年7月30日9:57:45
	 * @param asyncMsg 请求消息
	 * @param param 刷新建包明细信息
	 * @return
	 * @throws PdaBusiException 
	 */
	@Override
	public List<PackageGoodsDetail> service(AsyncMsg asyncMsg, QryPackageInfo param)
			throws PdaBusiException {
		this.validate(param);
		List<ExpressPackageDetailDto> list = null;
		List<PackageGoodsDetail> result = null;
		try {
			if(param.getExpressPackageType() !=null && "1".equals(param.getExpressPackageType())){//“1”表示为直达包
				list = pdaExpressPackageService.refrushThroughPackageDetail(param.getPackageCode());//调用直达包接口
			}else if(param.getExpressPackageType() !=null && "0".equals(param.getExpressPackageType())){//“0”表示为正常包
				list = pdaExpressPackageService.refrushPackageDetail(param.getPackageCode());//调用普通包接口
			}else if(param.getExpressPackageType() !=null && "3".equals(param.getExpressPackageType())){//'3'表示航空件包
				list = pdaExpressPackageService.refrushAirPackageDetail(param.getPackageCode());//调用航空件建包
			}else if(param.getExpressPackageType() !=null && "4".equals(param.getExpressPackageType())){//'4'表示空运直达包
				list = pdaExpressPackageService.resreshAirThroughPackageDetail(param.getPackageCode());//调用空运直达包
			}else {//'2'表示二程接驳建包
				list = pdaExpressPackageService.refrushPackageDetail(param.getPackageCode());//调用二程接驳建包
			}
			if(list != null){
				//封装实体
				result = wrapPdaPackageDto(list,param.getPackageCode());
			}
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return result;
	}
	
	/**
	 * 
	* @Description: TODO 封装实体
	* @param list
	* @return
	* @return List<GetPackageTask>    
	* @author mt
	* @date 2013年7月30日9:58:46
	 */
	private List<PackageGoodsDetail> wrapPdaPackageDto(List<ExpressPackageDetailDto> list,String packageNo){
		List<PackageGoodsDetail> result = new ArrayList<PackageGoodsDetail>();
		for (int i = 0; i < list.size(); i++) {
			PackageGoodsDetail goodsDetail = new PackageGoodsDetail();
			ExpressPackageDetailDto detailDto = list.get(i);
			//货物名称
			goodsDetail.setGoodsName(detailDto.getGoodsName());
			goodsDetail.setPackageNo(packageNo);
			//已扫数量
			goodsDetail.setOperateQty(detailDto.getOperateQty());
			//包装
			goodsDetail.setPack(detailDto.getPack());
			
			//到达部门编码
			goodsDetail.setReachOrgCode(detailDto.getReachOrgCode());
			//到达部门名称
			goodsDetail.setReachOrgName(detailDto.getReachOrgName());
			//收货部门编号
			goodsDetail.setReceiveOrgCode(detailDto.getReceiveOrgCode());
			//收货部门名称
			goodsDetail.setReceiveOrgName(detailDto.getReceiveOrgName());
			List<SerialNo> serialNos = new ArrayList<SerialNo>();
			for (int j = 0; j < detailDto.getSerialNos().size(); j++) {
				SerialNo serialNo = new SerialNo();
				serialNo.setSerialNo(detailDto.getSerialNos().get(j));
				serialNos.add(serialNo);
			}
			
			//------对流水号进行排序
			Collections.sort(serialNos, new Comparator<SerialNo>() {
				@Override
				public int compare(SerialNo o1, SerialNo o2) {
					if(Integer.parseInt(o1.getSerialNo())>Integer.parseInt(o2.getSerialNo())){
						return 1;
					}else if(Integer.parseInt(o1.getSerialNo())==Integer.parseInt(o2.getSerialNo())){
						return 0;
					}else{
						return -1;
					}
				}
			});
			if(serialNos.size()>0){
				//--生成流水号二进制
				//获得最大的流水号需要多少位二进制
				int maxSer = 9999;
				try {
					maxSer = Integer.parseInt(serialNos.get(serialNos.size()-1).getSerialNo());
				} catch (Exception e) {
					throw new FossInterfaceException(null, "运单号:"+goodsDetail.getWayBillNo()+"流水号："+serialNos.get(serialNos.size()-1).getSerialNo()+"不合法");
				}
				int max = maxSer%32==0?
						maxSer/32:maxSer/32+1;
				//流水号二进制数组
			    long[] serNos = new long[max];
				for(int j=0;j<serialNos.size();j++){
					if(StringUtil.isEmpty(serialNos.get(j).getSerialNo())){
						LOG.warn("流水号为空");
						continue;
					}
					int serialNo = 0;
					try {
						serialNo = Integer.parseInt(serialNos.get(j).getSerialNo());
					}catch (Exception e) {
						throw new FossInterfaceException(null, "运单号:"+goodsDetail.getWayBillNo()+"流水号："+serialNos.get(j).getSerialNo()+"不合法");
					}
					if(serialNo==0){
						LOG.warn("运单号"+goodsDetail.getWayBillNo()+ "存在为0000的流水号");
						continue;
					}
					int index = serialNo%32==0?serialNo/32:serialNo/32+1;
					serNos[index-1] = BitUtil.setBit(serNos[index-1],serialNo%32==0?32:serialNo%32);
				}
				StringBuffer serNoBuf = new StringBuffer("");
				for(int j=0;j<max;j++){
					serNoBuf.append(Long.toHexString(serNos[j])).append("|");
				}
				//流水号列表
				goodsDetail.setSerialNo(serNoBuf.toString());
			}
			
			//库存数量
			goodsDetail.setStockQty(detailDto.getStockQty());
			//运输性质Code
			goodsDetail.setTransportTypeCode(detailDto.getTransportTypeCode());
			//运输性质名称
			goodsDetail.setTransportTypeName(detailDto.getTransportTypeName());
			//体积
			goodsDetail.setVolum(detailDto.getVolume());
			//运单号
			goodsDetail.setWayBillNo(detailDto.getWayBillNo());
			//开单数量
			goodsDetail.setWayBillQty(detailDto.getWayBillQty());
			//重量
			goodsDetail.setWeight(detailDto.getWeight());
			result.add(goodsDetail);
		}
		return result;
	}
	
	/**
	 * 
	 * <p>TODO字段非空验证</p> 
	 * @author mt
	 * @date 2013年7月30日9:56:56
	 * @param createLoadTask
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(QryPackageInfo qryPackageInfo)
			throws ArgumentInvalidException {
		Argument.notNull(qryPackageInfo, "qryPackageInfo");
		// 检验包号非空
		Argument.hasText(qryPackageInfo.getPackageCode(),"qryPackageInfo.packageCode");
	}
	
	@Override
	public String getOperType() {
		return PackageManagerConstant.OPER_TYPE_PACKAGE_TASK_RFSH.VERSION;
	}
	
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaExpressPackageService(
			IPDAExpressPackageService pdaExpressPackageService) {
		this.pdaExpressPackageService = pdaExpressPackageService;
	}
	
	
}
