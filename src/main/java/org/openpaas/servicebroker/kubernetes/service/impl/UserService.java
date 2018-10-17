package org.openpaas.servicebroker.kubernetes.service.impl;

import org.openpaas.servicebroker.kubernetes.model.JpaServiceInstance;
import org.openpaas.servicebroker.kubernetes.model.User;
import org.openpaas.servicebroker.kubernetes.service.RestTemplateService;
import org.openpaas.servicebroker.model.Plan;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * User에 관한 서비스 클래스
 * @author hyerin
 * @since 2018.08.22
 * @version 20180822
 */
@Service
public class UserService {
	
	@Autowired
	RestTemplateService restTemplateService;
	
	public void request(JpaServiceInstance jpaInstance, HttpMethod httpMethod) {
		request(jpaInstance, null, httpMethod);
	}
	
	public void request(JpaServiceInstance jpaInstance, Plan plan, HttpMethod httpMethod) {
		restTemplateService.requestUser(convert(jpaInstance, plan), httpMethod);
	}

	/**
	 * id의 경우 broker에서 채워져서 통신하면 안되기에 null로 세팅
	 * setUserID는 parameter("owner")의 값으로 세팅
	 * @author hyerin
	 * @since 2018.08.22
	 * @version 20180822
	 */
	private User convert(JpaServiceInstance jpaInstance, Plan plan) {
		User user = new User();
		BeanUtils.copyProperties(jpaInstance, user);
		user.setId(null);
		if(plan != null) {
			user.setPlanName(plan.getName());
			user.setPlanDescription(plan.getDescription());
		}
		return user;
	}
}
