package com.shade.enterprise.user.interfaces;

import com.shade.enterprise.user.application.service.UserService;
import com.shade.enterprise.user.domain.model.User;
import com.shade.enterprise.user.interfaces.api.UsersApi;
import com.shade.enterprise.user.interfaces.model.UserCreationApiBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.jbosslog.JBossLog;

import java.net.URI;

@Path("/api/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@JBossLog
public class UserRestController implements UsersApi {

    private final UserService userService;

    private final UserApiMapper userApiMapper;

    @Inject
    public UserRestController(UserService userService, UserApiMapper userApiMapper) {
        this.userService = userService;
        this.userApiMapper = userApiMapper;
    }

    @Override
    public Response createUser(UserCreationApiBean userCreationApiBean) {
        User user = userService.createUser(userApiMapper.mapToDto(userCreationApiBean));
        return Response.created(URI.create("/api/v1/users/" + user.getId())).build();
    }

}
