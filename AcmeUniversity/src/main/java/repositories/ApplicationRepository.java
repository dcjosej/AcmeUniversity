package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.Lecturer;
import domain.Subject;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer>{

	@Query("select count(a) from Application a where a.lecturer = ?1 and a.subject=?2 and a.accepted = true")
	public Integer isLecturerInSubject(Lecturer lecturer, Subject subject);

	@Query("select count(a) from Application a where a.lecturer = ?1 and a.subject=?2")
	public Integer getNumApplications(Lecturer lecturer, Subject subject);

	@Query("select a from Application a where a.accepted = false")
	public Collection<Application> findPending();
}
