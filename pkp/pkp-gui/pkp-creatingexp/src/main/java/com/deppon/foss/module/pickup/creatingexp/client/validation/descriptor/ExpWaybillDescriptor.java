/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.validation.descriptor;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.utils.NumberValidate;
import com.deppon.foss.module.pickup.common.client.vo.ProductEntityVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creating.client.service.IWaybillService;
import com.deppon.foss.module.pickup.creating.client.service.WaybillServiceFactory;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 026123-foss-lifengteng
 * 
 */
public class ExpWaybillDescriptor {

	private static final int EIGHT = 8;

	private static final int ElEVEN = 11;
	
	private static final int SEVEN = 7;
	private static final int FORTY = 40;

	/**
	 * 日志
	 */
	private static final Log LOG = LogFactory.getLog(ExpWaybillDescriptor.class);

	/**
	 * servivce
	 */
	private IWaybillService waybillService = WaybillServiceFactory.getWaybillService();

	/**
	 * 国际化
	 */
	private static final II18n i18n = I18nManager.getI18n(ExpWaybillDescriptor.class);

	private static final int TEN = 10;

	private static final int FIVE = 5;

	private static final int NINE = 9;

	private static final int SIX = 6;

	private static final int NUM_9999 = 9999;

	private static final int NUM_999 = 999;


	/**
	 * 
	 * <p>
	 * (运单号校验)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-17 下午03:12:22
	 * @param number
	 * @return
	 * @see
	 */
	public String validateWaybillNo(String waybillNo, ExpWaybillPanelVo bean) {
		// 已开单、PDA提交，PDA已补录时，不进行校验
		if (WaybillConstants.WAYBILL_STATUS_PC_ACTIVE.equals(bean.getWaybillstatus()) || WaybillConstants.WAYBILL_STATUS_PENDING_ACTIVE.equals(bean.getWaybillstatus())) {
			return WaybillConstants.SUCCESS;
		} else {
			if (NumberValidate.checkBetweenLength(waybillNo, TEN, TEN)) {

				if (waybillNo.startsWith(String.valueOf(FIVE)) || waybillNo.startsWith(String.valueOf(SIX)) || waybillNo.startsWith(String.valueOf(SEVEN)) || waybillNo.startsWith(String.valueOf(NumberConstants.NUMBER_8)) || waybillNo.startsWith(String.valueOf(NINE))) {
					
					//是否传递开关(FOSS开单运单号通过悟空接口校验是否已经存在)
					boolean switch4EcsFlag = waybillService.queryPkpSwitch4Ecs();
					if(switch4EcsFlag){
						boolean isExists = waybillService.valuateFoss2EcsWaybillNo(waybillNo,bean.getCreateUserCode());
						//如果为true表示运单号已存在
						if(isExists){
							LOG.info(i18n
									.get("foss.gui.creating.waybillDescriptor.waybillNo.exist"));
							return i18n.get("foss.gui.creating.waybillDescriptor.waybillNo.exist");
						}
					}
					
					Boolean bool;
					// 暂存或者PDA待补录的运单需要判断当前运单号如果与原运单号不一致的时候判断是否有重复运单号
					if (StringUtil.isNotEmpty(bean.getOldWaybillNo()) && !waybillNo.equals(bean.getOldWaybillNo())) {
						bool = waybillService.checkWaybillNo(waybillNo);
						if (bool) {
							LOG.info(i18n.get("foss.gui.creating.waybillDescriptor.waybillNo.exist"));
							return i18n.get("foss.gui.creating.waybillDescriptor.waybillNo.exist");
						} else {
							boolean result = checkEWaybill(waybillNo);
							if(result){
								LOG.info(i18n.get("foss.gui.creating.waybillDescriptor.waybillNo.ewaybillerror"));
								return i18n.get("foss.gui.creating.waybillDescriptor.waybillNo.ewaybillerror");
							}
							return WaybillConstants.SUCCESS;
						}
					} else {
						// PDA待补录运单只判断非PDA待补录的单号存不存在
						bool = waybillService.checkWaybillNoExceptPDAPending(waybillNo) && waybillService.checkEWaybillNoExceptPDAPending(waybillNo);
						if (bool) {
							LOG.info(i18n.get("foss.gui.creating.waybillDescriptor.waybillNo.exist"));
							return i18n.get("foss.gui.creating.waybillDescriptor.waybillNo.exist");
						} else {
							boolean result = checkEWaybill(waybillNo);
							if(result){
								LOG.info(i18n.get("foss.gui.creating.waybillDescriptor.waybillNo.ewaybillerror"));
								return i18n.get("foss.gui.creating.waybillDescriptor.waybillNo.ewaybillerror");
							}
							return WaybillConstants.SUCCESS;
						}
					}
				} else {
					return i18n.get("foss.gui.creating.waybillDescriptor.waybillNo.startexp");
				}

			} else {
				return i18n.get("foss.gui.creating.waybillDescriptor.waybillNo.lengthexp");
			}
		}
	}


	/**
	 * 电子运单运单号校验
	 * @param waybillNo 运单号
	 * @return
	 */
	private boolean checkEWaybill(String waybillNo){
		//TODO 新增规则，普通快递不能开9开头的运单，9开头的只归电子运单使用，从电子运单上线时间后开启此功能，liyongfei-2014-11-04
		//获取系统参数配置
		ConfigurationParamsEntity config = waybillService.getConfig(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,WaybillConstants.EWAYBILL_ONLINE_DAYS_CODE,FossConstants.ROOT_ORG_CODE);
		//电子运单上线时间
		String ewaybillDate =null;
		if(config!=null){
			ewaybillDate = config.getConfValue();
		}
		if(ewaybillDate!=null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			try {
				date = dateFormat.parse(ewaybillDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//如果是8或者9开头的，且当前时间大于电子运单上线的时间，则不允许
			if((waybillNo.startsWith(String.valueOf(8))
					||waybillNo.startsWith(String.valueOf(9))) && date.before(new Date()) ){
				WaybillPendingDto dto = waybillService.queryPendingWaybill(waybillNo);
				WaybillPendingEntity pending = null;
				if(dto!=null){
					pending = dto.getWaybillPending();
				}
				if(pending==null){//若是通过gui新开的普通快递单（不是电子运单）
					return true;
				}else if(!WaybillConstants.EWAYBILL.equals(pending.getWaybillType())){
					//如果运单号是8或者9开头，且运单类型不是电子运单的，返回true
					return true;
				}

			}
		}
		return false;
	}
	/**
	 * 
	 * <p>
	 * (司机工号校验)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-17 下午03:12:22
	 * @param number
	 * @return
	 * @see
	 */
	public String validateDriverCode(String driverCode) {
		if (StringUtils.isEmpty(driverCode)) {
			return WaybillConstants.SUCCESS;
		} else if (!NumberValidate.checkBetweenLength(driverCode, 6, 6)) {
			return i18n.get("foss.gui.creating.waybillDescriptor.driver.length");
		} else if (!NumberValidate.checkIsAllNumber(driverCode)) {
			return i18n.get("foss.gui.creating.waybillDescriptor.driver.numer");
		} else if (!waybillService.isOwnDriverExists(driverCode)) {
			return i18n.get("foss.gui.creating.waybillDescriptor.driver.exist");
		} else {
			return WaybillConstants.SUCCESS;
		}
	}

	/**
	 * 
	 * <p>
	 * (货物品名校验-违禁品)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-20 上午09:42:03
	 * @param goodsName
	 * @return
	 * @see
	 */
	public String validateGoodsName(String goodsName, ExpWaybillPanelVo bean) {
		if (goodsName != null && !"".equals(goodsName)) {
			boolean bool = false;
			List<ProhibitedArticlesEntity> list = new ArrayList<ProhibitedArticlesEntity>();
			ProductEntityVo productVo = bean.getProductCode();
			/**
			 * 判断运输性质是否为空
			 */
			if (null != productVo) {
				/**
				 * 判断运输性质是否为空运
				 */
				if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(productVo.getCode())) {
					// 获取空运违禁品
					list = waybillService.queryProhibitGoodsByType(DictionaryValueConstants.AIR_CONTRABAND);
				} else {
					// 获取汽运违禁品
					list = waybillService.queryProhibitGoodsForAutomobile();
				}
			} else {
				// 获取所有违禁品
				list = waybillService.queryAllProhibitGoods();
			}
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					ProhibitedArticlesEntity entity = list.get(i);
					if (!StringUtil.isEmpty(entity.getKeyWords())) {
						String keyWords = entity.getKeyWords();
						if (keyWords.contains(",")) {
							String[] keyWord = keyWords.split(",");
							for (int j = 0; j < keyWord.length; j++) {
								if (goodsName.contains(keyWord[j])) {
									bool = true;
									break;
								}
							}
							if (bool) {
								break;
							}
						} else {
							if (goodsName.contains(keyWords)) {
								bool = true;
								break;
							}
						}
					}
				}
			}
			if (bool) {
				return i18n.get("foss.gui.creating.waybillDescriptor.embargo.name");
			}
		} else {
			return i18n.get("foss.gui.creating.descriptor.Waybill.goodsName.null");
		}
		return WaybillConstants.SUCCESS;
	}

	/**
	 * 
	 * <p>
	 * (发货客户手机号码输入值校验)
	 * </p>
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-20 上午09:43:42
	 * @param goodsName
	 * @return
	 * @see
	 */
	public String validateDeliveryCustomerMobilephone(String deliveryCustomerMobilephone, ExpWaybillPanelVo bean) {
		// 解决NullPointerException liyongfei-20140718
		if (deliveryCustomerMobilephone != null) {
			if (!"".equals(deliveryCustomerMobilephone.trim())) {
				int phoneLength = deliveryCustomerMobilephone.trim().length();
	
				int lengthLimitEight = EIGHT;
				int lengthLimitEleven = ElEVEN;
	
				if (phoneLength != lengthLimitEight && phoneLength != lengthLimitEleven) {
					return i18n.get("foss.gui.creating.waybillDescriptor.deliver.mobile");
				}
	
				if (phoneLength == lengthLimitEleven) {
					if (!"1".equals(deliveryCustomerMobilephone.substring(0, 1))) {
						return i18n.get("foss.gui.creating.waybillDescriptor.deliver.mobile.one");
					}
				}
				//判定手机号码与电话号码是否存在一致的情况：存在电话号码组成：0+手机号码这种情况，需要过滤掉
				if(StringUtils.isNotEmpty(bean.getDeliveryCustomerPhone())){
					String deliveryCustomerPhone = bean.getDeliveryCustomerPhone().replaceFirst("^0*", ""); 
					if (StringUtils.isNotEmpty(deliveryCustomerPhone)//不为空
							&& (deliveryCustomerPhone.length() == 8//并且满足手机号码的校验条件
							|| (deliveryCustomerPhone.length() == 11 && deliveryCustomerPhone.startsWith("1")))) {
						if(StringUtils.isNotEmpty(bean.getDeliveryCustomerMobilephone())
								&& bean.getDeliveryCustomerMobilephone().equals(deliveryCustomerPhone)){
							return i18n.get("foss.gui.creating.listener.Waybill.telephoneInMobilePhoneisExist");
						}
					}
				}
			}
		}
		return WaybillConstants.SUCCESS;
	}

	/**
	 * 
	 * （收货客户手机号码输入值校验）
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-20 下午05:22:08
	 */
	public String validateReceiveCustomerMobilephone(String receiveCustomerMobilephone, ExpWaybillPanelVo bean) {
		// 解决NullPointerException 李永飞-20140718
		if (receiveCustomerMobilephone != null) {
			if (!"".equals(receiveCustomerMobilephone.trim())) {
				int phoneLength = receiveCustomerMobilephone.trim().length();
				int lengthLimitEight = EIGHT;
				int lengthLimitEleven = ElEVEN;
	
				if (phoneLength != lengthLimitEight && phoneLength != lengthLimitEleven) {
					return i18n.get("foss.gui.creating.waybillDescriptor.receiver.mobile");
				}
	
				if (phoneLength == lengthLimitEleven) {
					if (!"1".equals(receiveCustomerMobilephone.substring(0, 1))) {
						return i18n.get("foss.gui.creating.waybillDescriptor.receiver.mobile");
					}
				}
				//判定手机号码与电话号码是否存在一致的情况：存在电话号码组成：0+手机号码这种情况，需要过滤掉
				if(StringUtils.isNotEmpty(bean.getReceiveCustomerPhone())){
					String receiveCustomerPhone = bean.getReceiveCustomerPhone().replaceFirst("^0*", ""); 
					if (StringUtils.isNotEmpty(receiveCustomerPhone)//不为空
							&& (receiveCustomerPhone.length() == 8//并且满足手机号码的校验条件
							|| (receiveCustomerPhone.length() == 11 && receiveCustomerPhone.startsWith("1")))) {
						if(StringUtils.isNotEmpty(bean.getDeliveryCustomerMobilephone())
								&& bean.getDeliveryCustomerMobilephone().equals(receiveCustomerPhone)){
							return i18n.get("foss.gui.creating.listener.Waybill.telephoneInMobilePhoneisExist");
						}
					}
				}
			}
		}
		return WaybillConstants.SUCCESS;
	}
	
	/**
	 * 
	 * 尺寸输入合法性校验
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-1 上午11:50:21
	 */
	public String validateGoodsSize(String goodsSize, ExpWaybillPanelVo bean) {

		if (goodsSize != null && !"".equals(goodsSize.trim())) {
			if (!NumberValidate.checkIsGoodsSize(goodsSize)) {
				StringBuffer str = new StringBuffer(i18n.get("foss.gui.creating.waybillDescriptor.size.rule"));
				str.append("(\n").append(i18n.get("foss.gui.creating.waybillDescriptor.example"));
				str.append("：0.5*0.5*0.5*2").append(i18n.get("foss.gui.creating.waybillDescriptor.or")).append("\n");
				str.append("0.5*0.5*0.5*2+1*1*1*5").append(i18n.get("foss.gui.creating.waybillDescriptor.or")).append("\n");
				str.append("0.5*0.5*0.5*2+1*1*1*5-0.3*0.3*0.6*1)");
				return str.toString();
			} else {
				String size = goodsSize;
				String[] addAll = StringUtils.split(size, "+");
				double length = 0;
				double width = 0;
				double height = 0;
				
				
				// 需要配置的
				BigDecimal low = bean.getVolumeLow();
				BigDecimal up = bean.getVolumeUp();
				double minizeLength = ((low != null) ? low.doubleValue() : NUM_999) * NumberConstants.NUMBER_100;
				double maxLength = ((up != null) ? up.doubleValue() : NUM_9999) * NumberConstants.NUMBER_100;
				
				if (addAll != null && addAll.length > 0) {
					for (String leng : addAll) {
						leng = StringUtils.substringBefore(leng, "-");
						String[] eachLengAll = StringUtils.split(leng, "*");
						if (eachLengAll.length < 3) {
							continue;
						}
						double minleng = 0;
						double tmplength = Double.parseDouble(eachLengAll[0]);
						String minLen = "length";
						minleng = tmplength;
						double tmpwidth = Double.parseDouble(eachLengAll[1]);
						if (tmpwidth < minleng) {
							minleng = tmpwidth;
							minLen = "width";
						}
						double tmpheight = Double.parseDouble(eachLengAll[2]);
						if (tmpheight < minleng) {
							minleng = tmpheight;
							minLen = "height";
						}
						double count = 1;
						if ("length".equals(minLen)) {
							length = length + (minleng * count);
							width = width + tmpwidth;
							height = height + tmpheight;
						}
						if ("width".equals(minLen)) {
							width = width + (minleng * count);
							height = height + tmpheight;
							length = length + tmplength;
						}
						if ("height".equals(minLen)) {
							height = height + (minleng * count);
							length = length + tmplength;
							width = width + tmpwidth;
						}
						
						
						/**
						 * KDTE-4618
						 */
						if (tmplength > minizeLength || tmpwidth > minizeLength || tmpheight > minizeLength) {
							String result = i18n.get("foss.gui.creating.waybillDescriptor.size.ruleExpress") + minizeLength
									+ i18n.get("foss.gui.creating.waybillDescriptor.size.ruleExpress2");
							return result;
						}else if ((tmplength + tmpwidth + tmpheight) > maxLength) {
							String result = i18n.get("foss.gui.creating.waybillDescriptor.size.ruleExpress3") + maxLength
									+ i18n.get("foss.gui.creating.waybillDescriptor.size.ruleExpress2");
							return result;
						}
					}
				}
			}
		}
		return WaybillConstants.SUCCESS;
	}

	/**
	 * 
	 * 验证目的站长度
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2013-1-17 下午05:12:08
	 * @param goodsSize
	 * @return
	 */
	public String validateTargetOrgCode(String targetOrgCode) {

		if (targetOrgCode != null && !"".equals(targetOrgCode.trim())) {
			if (!NumberValidate.checkLength(targetOrgCode, WaybillConstants.LENGTH_FIFTY)) {
				return i18n.get("foss.gui.creating.waybillDescriptor.dest.length");
			}
		} else {
			return i18n.get("foss.gui.creating.waybillDescriptor.targetOrgCodeNull");
		}
		return WaybillConstants.SUCCESS;
	}

	/**
	 * 重量校验
	 * 
	 * @param billWeight
	 * @param bean
	 * @return
	 */
	public String validateGoodsWeightTotal(BigDecimal goodsWeightTotal, WaybillPanelVo bean) {

		if(goodsWeightTotal==null || goodsWeightTotal.doubleValue()<=0){
			return i18n.get("foss.gui.creating.waybillDescriptor.goodsWeightTotal");
		}
		return CommonUtils.validateGoodsWeightTotal(goodsWeightTotal, bean);
	}

	/**
	 * 发货联系人 不能为空
	 * @param deliveryCustomerName
	 * @param bean
	 * @return
	 */
	public String validateDeliveryCustomerContact(String deliveryCustomerContact){
		if(StringUtils.isBlank(deliveryCustomerContact)){
			return i18n.get("foss.gui.creating.waybillDescriptor.deliveryCustomerContact.validateContent") ;
 		}
		return WaybillConstants.SUCCESS;
	}
	
	/**
	 * 收货联系人 不能为空
	 * @param receiveCustomerContact
	 * @param bean
	 * @return
	 */
	public String validateReceiveCustomerContact(String receiveCustomerContact){
		if(StringUtils.isBlank(receiveCustomerContact)){
			return i18n.get("foss.gui.creating.waybillDescriptor.receiveCustomerContact.validateContent") ;
 		}
		return WaybillConstants.SUCCESS;
	}
	
	/**
	 * 体积校验
	 * 
	 * @param billWeight
	 * @param bean
	 * @return
	 */
	public String validateGoodsVolumeTotal(BigDecimal goodsVolumeTotal, WaybillPanelVo bean) {
		if(goodsVolumeTotal==null ){
			return WaybillConstants.SUCCESS;
			//return i18n.get("foss.gui.creating.waybillDescriptor.goodsVolumeTotal");
		}
		return CommonUtils.validateGoodsVolumeTotal(goodsVolumeTotal, bean);
	}
	/**
	 * 
	 * 发货客户电话合法性校验
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-1 上午11:50:21
	 */
	public String validateDeliveryCustomerPhone(String deliveryCustomerPhone) {		
		if (StringUtil.isNotEmpty(deliveryCustomerPhone)) {
			 if(!NumberValidate.checkPhoneWaybillExp(deliveryCustomerPhone)){
				 return i18n.get("foss.gui.creating.listener.Waybill.telephoneCorrectFormat");
			 }
		}
		return WaybillConstants.SUCCESS;
	}
	
	/**
	 * 
	 * 收货客户电话合法性校验
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-1 上午11:50:21
	 */
	public String validateReceiveCustomerPhone(String receiveCustomerPhone) {		
		if (StringUtil.isNotEmpty(receiveCustomerPhone)) {
			 if(!NumberValidate.checkPhoneWaybillExp(receiveCustomerPhone)){
				 return i18n.get("foss.gui.creating.listener.Waybill.telephoneCorrectFormat");
			 }
		}
		return WaybillConstants.SUCCESS;
	}
	
	/**
	 * 
	 * <p>
	 * (内部发货工号校验)
	 * </p>
	 * 
	 * @author 025000-FOSS-guohao
	 * @date 2015-4-16 下午03:12:22
	 * @param number
	 * @return
	 * @see
	 */
	public String validateEmployeeNo(String jobNumber,WaybillPanelVo bean) {
		return CommonUtils.validateEmployeeNo(jobNumber, bean);
	}
	//TODO
	/**
	 * 校验伙伴名称
	 */
	public String validatePartnerName(String partnerName){
		if(StringUtils.isBlank(partnerName)){
			return WaybillConstants.SUCCESS;
		}
		Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]{1,70}");
		Matcher matcher = pattern.matcher(partnerName);
		boolean b= matcher.matches();
		if(!b){
			return i18n.get("foss.gui.creating.listener.Waybill.validatePartnerName");
		}
		return WaybillConstants.SUCCESS;
	}
	/**
	 * 校验伙伴电话
	 */
	public String validatePartnerPhone(String partnerPhone){
		if(StringUtils.isBlank(partnerPhone)){
			return WaybillConstants.SUCCESS;
		}
		Pattern pattern = Pattern.compile("^1\\d{10}$");
		Matcher matcher = pattern.matcher(partnerPhone);
		boolean b= matcher.matches();
		if(!b){
			return i18n.get("foss.gui.creating.listener.Waybill.validatePartnerPhome");
		}
		return WaybillConstants.SUCCESS;
	}
}
