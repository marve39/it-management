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

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * An user.
 *
 * @author Greg Turnquist
 * @author Oliver Gierke
 */
@Entity
@Data
@RequiredArgsConstructor
public class User {

	private @Id @GeneratedValue Long id;
	private final String userName;
	private final String password;
	private final String firstName;
	private final String lastName;
	private final String email;
	private final String contactNumber;

	protected User() {
		this(null, null);
	}
}
