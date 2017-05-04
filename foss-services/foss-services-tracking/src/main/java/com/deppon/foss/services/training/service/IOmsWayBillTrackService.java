package com.deppon.foss.services.training.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.deppon.foss.service.waybilltrackservice.CommonException;
import com.deppon.foss.services.training.shared.request.QueryWaybillTrackingInfoRequest;
import com.deppon.foss.services.training.shared.request.QueryWaybillTrajectoryRequest;
import com.deppon.foss.services.training.shared.response.QueryWaybillTrackingInfoResponse;
import com.deppon.foss.services.training.shared.response.QueryWaybillTrajectoryResponse;
public interface IOmsWayBillTrackService {
	
	@RequestMapping(value = "/queryWaybillTrackingInfo", method = RequestMethod.POST)
	QueryWaybillTrackingInfoResponse queryWaybillTrackingInfo(QueryWaybillTrackingInfoRequest queryWaybillTrackingInfoRequest)throws CommonException;
	
	@RequestMapping(value = "/queryWaybillTrajectory", method = RequestMethod.POST)
	QueryWaybillTrajectoryResponse queryWaybillTrajectory(QueryWaybillTrajectoryRequest queryWaybillTrajectoryRequest)throws CommonException;
}
