/*
 * Copyright 2014 the original author or authors.
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
package data.domain;

import data.Application;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.PreUpdate;
import javax.persistence.PrePersist;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * An Meeting.
 *
 */
@Entity
@Data
@RequiredArgsConstructor
public class Meeting {

	private @Id @GeneratedValue Long id;
	private final String meetingID;
	private final String name;
	private final String attendeePW;
	private final String moderatorPW;
	private final String welcome;
	private final String logoutURL;
	private final String record;
	private final Long createdBy;
	private final Boolean isMeetingEnded;
	private String createAPI;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	private final Calendar startTime;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	private final Calendar endTime;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	private final Calendar createdTime;

	protected Meeting() {
		this(null, null, null, null, null, null, null, null, null, null, null, null);
	}

	@PreUpdate
	@PrePersist
	public void createAPIToken() throws NoSuchAlgorithmException {

		String DELIMITTER = "&";

		String name_URL = "name=" + this.name;
		String meetingID_URL = "meetingID=" + this.meetingID;
		String attendeePW_URL = "attendeePW=" + this.attendeePW;
		String moderatorPW_URL = "moderatorPW=" + this.moderatorPW;
		String welcome_URL = "welcome=" + this.welcome;
		String logout_URL = "logoutURL=" + this.logoutURL;
		String record_URL = "record=" + this.record;

		String createAPI_URL = name_URL + DELIMITTER + meetingID_URL + DELIMITTER + attendeePW_URL + DELIMITTER
				+ moderatorPW_URL + DELIMITTER + welcome_URL + DELIMITTER + logout_URL + DELIMITTER + record_URL;

		String composeCreateURL = createAPI_URL + Application.saltKey;
		
		System.out.println("SALT KEY = " + Application.saltKey);

		// SHA1 Decode

		MessageDigest mDigest = MessageDigest.getInstance("SHA1");
		byte[] result = mDigest.digest(composeCreateURL.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}

		createAPI = createAPI_URL + DELIMITTER + "checksum=" + sb.toString();
	}
}
