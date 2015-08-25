package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.GoogleUserRepository;
import domain.GoogleUser;

@Service
@Transactional
public class GoogleUserService {

	@Autowired
	private GoogleUserRepository googleUserRepository;
	
	public GoogleUser findOne(String id){
		return googleUserRepository.findById(id);
	}
	
	public GoogleUser save(GoogleUser user){
		return googleUserRepository.save(user);
	}

	public void delete(GoogleUser googleUser) {
		googleUserRepository.delete(googleUser);
	}
}
