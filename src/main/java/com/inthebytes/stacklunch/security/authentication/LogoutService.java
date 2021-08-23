package com.inthebytes.stacklunch.security.authentication;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.inthebytes.stacklunch.object.entity.Authorization;
import com.inthebytes.stacklunch.repository.AuthorizationRepository;
import com.inthebytes.stacklunch.security.StackLunchJwtDecoder;

@Service
@ConditionalOnProperty(
		value = "security.login-logout.enabled",
		havingValue = "true",
		matchIfMissing = false
		)
public class LogoutService implements LogoutHandler {

	@Autowired
	AuthorizationRepository authorizationDao;
	
	@Autowired
	private StackLunchJwtDecoder decoder;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
			String token = request.getHeader(decoder.getHeader()).replace(decoder.getPrefix(), "");

			if (!authorizationDao.existsById(token)) {
				Authorization authorization = new Authorization();
				authorization.setToken(token);
				authorization.setExpirationDate(Timestamp.from(decoder.decode(token).getExpiresAt()));

				authorizationDao.save(authorization);
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				System.out.println("Token already invalidated");
				response.setStatus(HttpServletResponse.SC_CONFLICT);
			}
	}
}
