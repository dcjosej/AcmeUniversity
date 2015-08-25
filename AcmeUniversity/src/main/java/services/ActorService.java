package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import utilities.dpexceptions.DPNotFoundException;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository actorRepository;

	public Collection<Actor> findAll() {
		Collection<Actor> result = actorRepository.findAll();
		return result;
	}

	public Actor findOne(int id) throws DPNotFoundException {
		Actor result = actorRepository.findOne(id);

		if (result == null) {
			throw new DPNotFoundException();
		}

		return result;
	}

	public Actor findByPrincipal() {
		UserAccount account = LoginService.getPrincipal();
		int id = account.getId();
		Actor result = actorRepository.findByUserAccountId(id);

		return result;
	}

	public Actor save(Actor actor) {
		Actor result = actorRepository.save(actor);
		return result;
	}

	// ------------- Others ---------------------------------------
	public Actor getActorBySocialId(String idSocial) {
		Actor res = actorRepository.findBySocialId(idSocial);
		return res;
	}
}
