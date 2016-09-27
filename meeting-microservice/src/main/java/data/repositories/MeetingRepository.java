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
package data.repositories;

import java.util.List;

import javax.transaction.Transactional;
import data.domain.Meeting;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * A repository to manage {@link User} instances.
 * 
 * @author Greg Turnquist
 * @author Oliver Gierke
 */

@Transactional
public interface MeetingRepository extends CrudRepository<Meeting, Long> {
//	@Query("SELECT m FROM Meeting m")
//	public List<Meeting> scheduledMeeting();
}
