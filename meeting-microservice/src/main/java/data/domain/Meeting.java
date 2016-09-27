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

import java.net.URLEncoder;

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
	private final String participantList;
	private final String moderatorList;
	private String createAPI;
	private String meetingInfoAPI;
	private String isMeetingRunningAPI;
	private String endMeetingAPI;

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
		this(null, null, null, null, null, null, null, null, null, null, null, null, null, null);
	}

	@PreUpdate
	@PrePersist
	public void createAPIToken() throws NoSuchAlgorithmException {

		String DELIMITTER = "&";

		String name_URL = (this.name != null)?"name=" + URLEncoder.encode(this.name):"";
		String meetingID_URL = (this.meetingID != null)?"meetingID=" + URLEncoder.encode(this.meetingID):"";
		String attendeePW_URL = (this.attendeePW != null)?"attendeePW=" + this.attendeePW:"";
		String moderatorPW_URL = (this.moderatorPW != null)?"moderatorPW=" + this.moderatorPW:"";
		String welcome_URL = (this.welcome != null)?"welcome=" + URLEncoder.encode(this.welcome):"";
		String logout_URL = (this.logoutURL != null)?"logoutURL=" + URLEncoder.encode(this.logoutURL):"";
		String record_URL = (this.record != null)?"record=" + this.record:"";

		// Create API
		String createAPI_URL = name_URL + DELIMITTER + meetingID_URL + DELIMITTER + attendeePW_URL + DELIMITTER
				+ moderatorPW_URL + DELIMITTER + welcome_URL + DELIMITTER + logout_URL + DELIMITTER + record_URL;
		createAPI = "create?" + createAPI_URL + DELIMITTER + "checksum=" + sha1("create" + createAPI_URL + Application.saltKey);

		// getMeetingInfo
		String meetingInfoAPI_URL = meetingID_URL + DELIMITTER + moderatorPW_URL;
                meetingInfoAPI = "getMeetingInfo?" + meetingInfoAPI_URL + DELIMITTER + "checksum=" + sha1("getMeetingInfo" + meetingInfoAPI_URL + Application.saltKey);
		// is Meeting Running API
		String isMeetingRunning_URL = meetingID_URL;
	        isMeetingRunningAPI = "isMeetingRunning?" + isMeetingRunning_URL + DELIMITTER + "checksum=" + sha1("isMeetingRunning" + isMeetingRunning_URL + Application.saltKey);	
	
		// end Meeting API
		String password_URL = (this.moderatorPW != null)?"password=" + this.moderatorPW:"";
		String endMeetingAPI_URL = meetingID_URL + DELIMITTER + password_URL;
		endMeetingAPI = "end?" + endMeetingAPI_URL + DELIMITTER + "checksum=" + sha1("end" + endMeetingAPI_URL + Application.saltKey);
	}

	public String joinAPIURL(Long userID, String name, Boolean isModerator) throws NoSuchAlgorithmException{
		String DELIMITTER = "&";
		String fullName_URL = "fullName=" + name;
		String password = attendeePW;
		if (isModerator) password = moderatorPW;
		//String password_URL = "password=" + (isModerator)?moderatorPW:attendeePW;
		String password_URL = "password=" + password;
		String userID_URL = "userID=" + userID;
		String meetingID_URL = (this.meetingID != null)?"meetingID=" + URLEncoder.encode(this.meetingID):"";

		String joinURL = meetingID_URL + DELIMITTER + fullName_URL + DELIMITTER + password_URL + DELIMITTER + userID_URL;
		return "join?" + joinURL + DELIMITTER + "checksum=" + sha1("join" + joinURL + Application.saltKey);
	}

	private String sha1(String input) throws NoSuchAlgorithmException{
	 	MessageDigest mDigest = MessageDigest.getInstance("SHA1");
                byte[] result = mDigest.digest(input.getBytes());
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < result.length; i++) {
                        sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
                }
		return sb.toString();
	}
}
