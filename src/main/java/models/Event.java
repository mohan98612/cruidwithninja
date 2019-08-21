/**
 * Copyright (C) 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package models;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.common.collect.Lists;

@Entity
public class Event {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;
    public String name;
    public String eventmanager;
    public String eventdate;
    public String eventtype;
    public String location;
    
   
   // @ElementCollection(fetch=FetchType.EAGER)
    //public List<Long> authorIds;
    @ElementCollection(fetch=FetchType.EAGER)
    public List<Long> authorIds;
    public Event() {}
    
    public Event(User author ,String name, String eventmanager, String eventdate, String eventtype ,String location) {
    	 //this.authorIds = Lists.newArrayList(author.id);
    	this.authorIds = Lists.newArrayList(author.id);
        this.name = name;
        this.eventmanager = eventmanager;
        this.eventdate = eventdate;
        this.eventtype=eventtype;
        this.location=location;
    }
 
}
