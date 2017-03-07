/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources;

import Dao.UserDAO;
import java.io.IOException;
import java.util.Base64;

import java.util.List;
import java.util.StringTokenizer;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author lwown
 */
@Provider
public class SecurityFilter implements ContainerRequestFilter {
    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
        if (authHeader != null && authHeader.size() > 0) {
            String authToken = authHeader.get(0);
            authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
//            String decodedString = Base64.decodeAsString(authToken);
            String decodedString = new String(Base64.getDecoder().decode(authToken));
            StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
            String username = tokenizer.nextToken();
            String password = tokenizer.nextToken();
            String path = requestContext.getUriInfo().getPath();
            
            UserDAO dao = new UserDAO();
            
            if (dao.isUserAuthenticated(username, password, path)) {
                return;
            }
            
            Response unauthorizedStatus = Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("User cannot access the resource.")
                    .build();

            requestContext.abortWith(unauthorizedStatus);


        }
    }
}
