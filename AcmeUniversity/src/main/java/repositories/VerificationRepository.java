package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Verification;

@Repository
public interface VerificationRepository extends
		JpaRepository<Verification, Integer> {

	@Query("select v from Verification v where v.lectureNote.id = ?1")
	Collection<Verification> findByIdLectureNote(int idLectureNote);

	@Query("select v from Verification v where v.lecturer.id = ?1 and v.lectureNote.id = ?2")
	Collection<Verification> findByLecturerAndLectureNote(int idLecturer, int idLectureNote);
}
