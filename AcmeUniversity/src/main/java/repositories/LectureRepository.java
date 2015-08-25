package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Lecture;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Integer> {

	@Query("select l from Lecture l where l.subject.id = ?1")
	Collection<Lecture> findByIdSubject(int idSubject);
}
