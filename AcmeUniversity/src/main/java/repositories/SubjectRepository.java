package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Lecturer;
import domain.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

	@Query("select s from Subject s where s.degree.id = ?1")
	Collection<Subject> findByIdDegree(int idDegree);

	@Query("select s from Subject s join s.applications a where a.lecturer = ?1 and a.accepted = true")
	Collection<Subject> getSubjectsByLecturer(Lecturer lecturer);

	@Query("select s, count(c) as com from Subject s join s.activities a join a.comments c order by com desc")
	Collection<Object[]> findSubjectsWithMoreComments();
}
