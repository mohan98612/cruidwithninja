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
 * Copyright (C) 2012 the original author or authors.
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

import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.session.Session;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;
import models.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.UserDao;
import etc.LoggedInUser;
import models.UserDto;
import dao.EventDao;;

@Singleton
public class LoginLogoutController {
    
	public long cuser;
    @Inject
    UserDao userDao;
    @Inject
    EventDao eventDao;
   
    
    
    ///////////////////////////////////////////////////////////////////////////
    // Login
    ///////////////////////////////////////////////////////////////////////////
    public Result login(Context context) {

        return Results.html();

    }

    public Result loginPost(@Param("username") String username,
                            @Param("password") String password,
                            @Param("rememberMe") Boolean rememberMe,
                            Context context) {

        boolean isUserNameAndPasswordValid = userDao.isUserAndPasswordValid(username, password);

        if (isUserNameAndPasswordValid) {
        	 User user=  eventDao.currentuser(username);  
       	  cuser=user.id;
           
            Session session = context.getSession();
            session.put("username", username);

            if (rememberMe != null && rememberMe) {
                session.setExpiryTime(24 * 60 * 60 * 1000L);
            }

            context.getFlashScope().success("login.loginSuccessful");

            return Results.redirect("/");

        } else {

            // something is wrong with the input or password not found.
            context.getFlashScope().put("username", username);
            context.getFlashScope().put("rememberMe", String.valueOf(rememberMe));
            context.getFlashScope().error("login.errorLogin");

            return Results.redirect("/login");

        }

    }

    ///////////////////////////////////////////////////////////////////////////
    // Logout
    ///////////////////////////////////////////////////////////////////////////
    public Result logout(Context context) {

        // remove any user dependent information
        context.getSession().clear();
        context.getFlashScope().success("login.logoutSuccessful");

        return Results.redirect("/");

    }
    public Result signup(Context context) {

        return Results.html();

    }

    public Result signupPost(@LoggedInUser String username,Context context
    						,@JSR303Validation UserDto userDto
    						,Validation validation) {

    	        if (validation.hasViolations()) {

    	            context.getFlashScope().error("Please correct field.");
    	            context.getFlashScope().put("username", userDto.username);
    	            context.getFlashScope().put("password", userDto.password);
    	            context.getFlashScope().put("fullname", userDto.fullname);
    	            return Results.redirect("/signup");
    	        }
    	        
     boolean a=   eventDao.newuser(username,userDto);
     
    	 User user=  eventDao.currentuser(userDto.username);  
    	  cuser=user.id;
        
        	 if (a) {
                 Session session = context.getSession();
                 session.put("username", userDto.username);

               //  if (rememberMe != null && rememberMe) {
                 //    session.setExpiryTime(24 * 60 * 60 * 1000L);
                 //}

                 context.getFlashScope().success("SignUp Successful");

                 return Results.redirect("/");

             } else {

                 // something is wrong with the input or password not found.
                 context.getFlashScope().put("username", userDto.username);
               //  context.getFlashScope().put("rememberMe", String.valueOf(rememberMe));
                 context.getFlashScope().error("login.errorLogin");

                 return Results.redirect("/signup");

             }

        }
    public long getcuser() {
    	return cuser;
    }

    
}
    
