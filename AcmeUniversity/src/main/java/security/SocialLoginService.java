package security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@Transactional
public class SocialLoginService implements SocialUserDetailsService {

	@Autowired
	private LoginService loginService;

	@Override
	public SocialUserDetails loadUserByUserId(String userId)
			throws UsernameNotFoundException, DataAccessException {

		Assert.notNull(userId);

		UserDetails result;

		result = loginService.loadUserByUsername(userId);

		return (SocialUserDetails)result;
	}

}
