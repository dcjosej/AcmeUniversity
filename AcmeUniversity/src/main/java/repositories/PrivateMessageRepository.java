package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.PrivateMessage;

@Repository
public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Integer>{

	@Query("select a.messagesReceived from Actor a where a = ?1")
	Collection<PrivateMessage> getReceived(Actor actor);

}
