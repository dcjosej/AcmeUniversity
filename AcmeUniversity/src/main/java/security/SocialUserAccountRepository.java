package security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialUserAccountRepository  extends JpaRepository<UserAccount, Integer>{
	
	UserAccount findByUserId(String userId);
}
