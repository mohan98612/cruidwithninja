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

/**
 * Copyright (C) 2013 the original author or authors.
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

package controllers;
import models.Event;
import java.util.List;
import java.util.Map;

import models.Article;
import ninja.Result;
import ninja.Results;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

import dao.ArticleDao;
import dao.EventDao;
import dao.SetupDao;

public class ApplicationController {

    @Inject
    ArticleDao articleDao;
    @Inject
    EventDao eventDao;
    @Inject
    SetupDao setupDao;

    public ApplicationController() {

    }

    /**
     * Method to put initial data in the db...
     * 
     * @return
     */
    public Result setup() {

        setupDao.setup();

        return Results.ok();

    }

  /* public Result index() {

       Article frontPost = articleDao.getFirstArticleForFrontPage();

        List<Article> olderPosts = articleDao.getOlderArticlesForFrontPage();

        Map<String, Object> map = Maps.newHashMap();
        map.put("frontArticle", frontPost);
        map.put("olderArticles", olderPosts);

        return Results.html().render("frontArticle", frontPost)
                .render("olderArticles", olderPosts);

    }*/
	   
    public Result index() {

        Event frontPost = eventDao.getFirstEventForFrontPage();
        String front="mohan";
        List<Event> olderPosts = eventDao.getOlderEventsForFrontPage();
        LoginLogoutController newu=new LoginLogoutController();
        long cuser=newu.getcuser();
        Map<String, Object> map = Maps.newHashMap();
        
        map.put("frontEvent", frontPost);
        map.put("olderEvents", olderPosts);
       
        	map.put("front",front);
        	if(cuser!=0) {
        return Results.html().render("frontEvent", frontPost)
                .render("olderEvents", olderPosts);

    }else {
    	 return Results.html().render("frontEvent", frontPost).render("front",front);
    }
        	}
}
