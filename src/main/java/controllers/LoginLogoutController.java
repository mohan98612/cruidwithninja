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
import ninja.params.PathParam;
import ninja.session.Session;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;
import models.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.io.IOUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.UserDao;
import etc.LoggedInUser;
import models.UserDto;
import dao.EventDao;;

@Singleton
public class LoginLogoutController {
    
	public long cuser;
	public static long temp;
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
       	  this.cuser=user.id;
       	  temp=this.cuser;
           
            Session session = context.getSession();
            session.put("username", username);

            if (rememberMe != null && rememberMe) {
                session.setExpiryTime(5*60*60*1000L);
            }
            else {
            	session.setExpiryTime(5*60*1000L);
            }
            
            context.getFlashScope().success("login.loginSuccessful");
          //  "login.loginSuccessful"
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
        temp=0L;

        return Results.redirect("/");

    }
    public Result signup(Context context) {

        return Results.html();

    }
    public Result image(Context context) {

        return Results.html();

    }
    public Result imagePost(Context context) throws Exception {

        // Make sure the context really is a multipart context...
        if (context.isMultipart()) {

            // This is the iterator we can use to iterate over the
            // contents of the request.
            FileItemIterator fileItemIterator = context.getFileItemIterator();

            while (fileItemIterator.hasNext()) {

                FileItemStream item = fileItemIterator.next();
                
                String name = item.getName();
                InputStream stream = item.openStream();

                String contentType = item.getContentType();
                byte[] buffer =IOUtils.toByteArray(stream);
                stream.read(buffer);
               File targetFile = new File("C:\\project\\newevent\\src\\main\\java\\assets\\images\\"+name);
               //eventDao.saveimage(name);
               OutputStream outputstream = new FileOutputStream(targetFile);
               outputstream.write(buffer);
               outputstream.close();
               return Results.ok().render("file",item);
            }

        }
        
        // We always return ok. You don't want to do that in production ;)
        return Results.ok();

    }
    public Result imagePostid(@PathParam("id") Long id,Context context) throws Exception {

        // Make sure the context really is a multipart context...
     

            // This is the iterator we can use to iterate over the
            // contents of the request.
    	String a=eventDao.getimagename(id);
          
               return Results.ok().render("file",a);
            

        
        
        // We always return ok. You don't want to do that in production ;)
      

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
    	  this.cuser=user.id;
    	  temp=this.cuser;
        
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
    	return temp;
    }
    

    
}
    
