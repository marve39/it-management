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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;
import com.fasterxml.jackson.annotation.JsonFormat;

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

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM/dd/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	private final Calendar startTime;

        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM/dd/yyyy HH:mm")
        @Temporal(TemporalType.TIMESTAMP)
        private final Calendar endTime;

        protected Meeting() {
                this(null, null, null, null, null, null,  null, null, null, null, null);
        }

//	public String createAPIToken(String saltKey){
				
//	}
}
