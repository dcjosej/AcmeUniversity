package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.GoogleUser;

@Repository
public interface GoogleUserRepository extends JpaRepository<GoogleUser, Integer>{

	@Query("select g from GoogleUser g where g.idGoogle = ?1")
	public GoogleUser findById(String id);
	
}
