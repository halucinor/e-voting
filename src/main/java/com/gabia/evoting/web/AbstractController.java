package com.gabia.evoting.web;

import com.gabia.evoting.domain.UserModel;
import com.gabia.evoting.web.dto.ResponseMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public abstract class AbstractController {

//    private static Logger logger = LoggerFactory.getLogger(AbstractController.class);

//	protected ModelMapper modelMapper;

    protected ResponseMessageDto successMessage(Object data) {
        ResponseMessageDto responseMessageData = new ResponseMessageDto();
        responseMessageData.setStatus("success");
        responseMessageData.setData(data);
        return responseMessageData;
    }

    protected ResponseMessageDto failureMessage(Object data) {
        ResponseMessageDto responseMessageData = new ResponseMessageDto();
        responseMessageData.setStatus("fail");
        responseMessageData.setData(data);
        return responseMessageData;
    }

    protected UserModel getUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        return (UserModel) principal;

    }

    protected boolean isGuestUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.contains(new SimpleGrantedAuthority("ROLE_GUEST"));
    }
}
