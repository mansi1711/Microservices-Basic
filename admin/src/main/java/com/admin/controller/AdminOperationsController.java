package com.admin.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.common.entities.Item;
import com.admin.service.AdminOperationsService;

@RestController
@RequestMapping(value = "/admin")
@EnableCircuitBreaker
public class AdminOperationsController {

	@Value("${server.port}")
	private int port;

	@Resource(name = "restTemp")
	private RestTemplate restTemplate;

	@Resource
	AdminOperationsService adminOperationsService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addItem(@RequestBody Item item) {

		System.out.println("Working from port " + port + " of Admin Service");

		return adminOperationsService.addItem(item);

	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public String deleteItem(@RequestBody Item item) {

		System.out.println("Working from port " + port + " of Admin Service");

		return adminOperationsService.deleteItem(item);

	}

}
