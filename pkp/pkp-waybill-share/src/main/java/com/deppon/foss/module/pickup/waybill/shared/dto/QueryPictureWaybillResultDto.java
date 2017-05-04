package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.util.List;

/**
 * 图片开单运单下拉Dto
 * ClassName: QueryPictureWaybillResultDto <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014-10-14 上午10:37:53 <br/>
 *
 * @author 157229-zxy
 * @version 
 * @since JDK 1.6
 */
public class QueryPictureWaybillResultDto {
	
	//成功
    public static final String FAIL = "0";
    //失败
    public static final String SUCCESS = "1"; 

	//结果返回代码：0，失败；1，成功
    private String code;
    
    //当code为0，失败的情况下，这里返回错误信息
    private String msg;
    
    private List<WaybillPictureLabelDto> WaybillPictureLabelDtoLst;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<WaybillPictureLabelDto> getWaybillPictureLabelDtoLst() {
		return WaybillPictureLabelDtoLst;
	}

	public void setWaybillPictureLabelDtoLst(
			List<WaybillPictureLabelDto> waybillPictureLabelDtoLst) {
		WaybillPictureLabelDtoLst = waybillPictureLabelDtoLst;
	}

}
