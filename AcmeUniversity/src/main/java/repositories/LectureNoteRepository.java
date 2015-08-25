package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LectureNote;

@Repository
public interface LectureNoteRepository extends JpaRepository<LectureNote, Integer>{

	@Query("select ln from LectureNote ln where ln.subject.id = ?1")
	Collection<LectureNote> findByIdSubject(int idSubject);

}
