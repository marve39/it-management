/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

/**
 * Application configuration file. Used for bootstrap and data setup.
 *
 * @author Greg Turnquist
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
@SpringBootApplication
//@EnableDiscoveryClient
//@EnableZuulProxy
//@EnableHystrix
@EnableFeignClients
//@EnableAutoConfiguration
//@PropertySource(value = "classpath:application.properties")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	//@Value("${id.jumpaja.salt.key}")
	public static String saltKey="2449e154324b52bca7ccec4d06930642";
	public static String jumpajaAPI = "http://euro-mtg.jumpaja.id:8888/bigbluebutton/api/";
}
