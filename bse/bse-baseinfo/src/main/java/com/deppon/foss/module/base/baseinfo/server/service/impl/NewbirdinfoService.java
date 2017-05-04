package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.INewbirdinfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.INewbirdinfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NewbirdinfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DopcnEwbQueryReqDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DopcnEwbQueryRespDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.NewbirdInfoException;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.ws.syncdata.CommonException;
import com.opensymphony.xwork2.inject.Inject;
import com.deppon.foss.base.util.define.NumberConstants;

public class NewbirdinfoService implements INewbirdinfoService {

	/**
	 * 
	 * 日志
	 */
	private static final Logger log = Logger
			.getLogger(NewbirdinfoService.class);

	public String dopCainiaoAddress;

	public NewbirdinfoService(String dopCainiaoAddress) {
		this.dopCainiaoAddress = dopCainiaoAddress;
	}

	public String getDopCainiaoAddress() {
		return dopCainiaoAddress;
	}

	public void setDopCainiaoAddress(String dopCainiaoAddress) {
		this.dopCainiaoAddress = dopCainiaoAddress;
	}

	/**
	 * 
	 * netGroupDao
	 */
	@Inject
	private INewbirdinfoDao newbirdinfoDao;

	public INewbirdinfoDao getNewbirdinfoDao() {
		return newbirdinfoDao;
	}

	public void setNewbirdinfoDao(INewbirdinfoDao newbirdinfoDao) {
		this.newbirdinfoDao = newbirdinfoDao;
	}

	@Override
	public List<NewbirdinfoEntity> queryNewbirdinfo(NewbirdinfoEntity entity,
			int limit, int start) {
		entity.setActive(FossConstants.ACTIVE);

		return newbirdinfoDao.queryNewbirdinfo(entity, limit, start);
	}

	@Override
	public Long queryRecordCount(NewbirdinfoEntity entity) {
		entity.setActive(FossConstants.ACTIVE);

		return newbirdinfoDao.queryRecordCount(entity);
	}

	@Override
	public int addNewbirdinfo(NewbirdinfoEntity entity) {
		// 判断菜鸟破损单实体类是否为空
		if (null == entity) {
			throw new NewbirdInfoException("", "对象为空");
		}

		List<NewbirdinfoEntity> oldlist = newbirdinfoDao
				.queryNewbirdinfoByNoPage(entity);

		if (oldlist.size() > 0) {
			throw new NewbirdInfoException("", "面单号已经存在");
		} else {
			newbirdinfoDao.addNewbirdinfo(entity);
		}
		return FossConstants.SUCCESS;
	}

	@Override
	public boolean isBoolTaoBao(String str) {

		return newbirdinfoDao.isBoolTaoBao(str);
	}

	/**
	 * <p>
	 * 接口获取信息
	 * </p>
	 * .
	 * 
	 * @param String
	 *            运单号
	 * @return
	 * @throws CommonException
	 * @author 261997-foss-css
	 * @date 2015-6-8 上午9:30:43
	 */
	public NewbirdinfoEntity syncNewbirdinfoInfo(String waybillno)
			throws CommonException {
		log.info("菜鸟异常单从DOP获取接口信息开始.......");
		NewbirdinfoEntity newbirdinfoEntity = null;
		DopcnEwbQueryReqDto dopcnEwbQueryReqDto = new DopcnEwbQueryReqDto();
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(this.getDopCainiaoAddress());
		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, NumberConstants.NUMBER_5000);
		client.getHttpConnectionManager().getParams()
				.setConnectionTimeout(NumberConstants.NUMBER_5000);
		method.getParams().setContentCharset("UTF-8");
		method.getParams().setHttpElementCharset("UTF-8");
		try {
			dopcnEwbQueryReqDto.setWaybillNo(waybillno);
			String js = JSONObject.fromObject(dopcnEwbQueryReqDto).toString();
			RequestEntity entity = null;
			entity = new StringRequestEntity(js, "application/json", "UTF-8");
			method.setRequestEntity(entity);
			method.addRequestHeader("Content-Type",
					"application/json;charset=UTF-8");

			int statuCode = client.executeMethod(method);
			if (statuCode == HttpStatus.SC_OK) {
				String jsonResponse = method.getResponseBodyAsString();
				// dop传递数据异常情况 菜鸟平台查询繁忙，请稍后再试！
				System.out.println(jsonResponse);
				String contentstr = "菜鸟平台查询繁忙，请稍后再试！";
				if (!contentstr.equals(jsonResponse)) {
					Map<String, Class> classMap = new HashMap<String, Class>();
					classMap.put("DopcnEwbQueryRespDto",
							DopcnEwbQueryRespDto.class);

					DopcnEwbQueryRespDto response = (DopcnEwbQueryRespDto) net.sf.json.JSONObject
							.toBean(net.sf.json.JSONObject
									.fromObject(jsonResponse),
									DopcnEwbQueryRespDto.class, classMap);

					newbirdinfoEntity = new NewbirdinfoEntity();
					// 收货人
					newbirdinfoEntity.setName(response.getConsigneeName());

					String telphonenumber = response.getConsigneePhone();

					if (null != telphonenumber && !"".equals(telphonenumber)
							&& telphonenumber.length() > 0) {
						if ("1".equals(telphonenumber.substring(0, 1))) {
							newbirdinfoEntity.setTelphone(telphonenumber);
						} else {
							newbirdinfoEntity.setPhone(telphonenumber);
						}
					}

				}
			} else {
				System.out.println("HTTP: Status "+ statuCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new NewbirdInfoException(e.getMessage());
		}
		return newbirdinfoEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.deppon.foss.module.base.baseinfo.api.server.service.INewbirdinfoService
	 * #
	 * updateWaybillInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.
	 * NewbirdinfoEntity)
	 */
	@Override
	public void updateWaybillInfo(NewbirdinfoEntity newbirdinfo1) {
		String waybillNo = newbirdinfo1.getTransport();
		String phone = newbirdinfo1.getPhone();
		String mobilephone = newbirdinfo1.getTelphone();
		String contact = newbirdinfo1.getName();
		if (StringUtil.isNotBlank(waybillNo)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("waybillNo", waybillNo);
			map.put("contact", contact);
			if (!StringUtil.isBlank(contact) && !StringUtil.isBlank(phone)) {
				map.put("phone", phone);
				this.updateWaybillPhone(map);
			}
			if (!StringUtil.isBlank(contact)
					&& !StringUtil.isBlank(mobilephone)) {
				map.put("mobilephone", mobilephone);
				this.updateWaybillMobilephone(map);
			}
		}

	}

	@Override
	public int updateWaybillPhone(Map<String, Object> map) {

		return newbirdinfoDao.updateWaybillPhone(map);
	}

	@Override
	public int updateWaybillMobilephone(Map<String, Object> map) {

		return newbirdinfoDao.updateWaybillMobilephone(map);
	}

}
