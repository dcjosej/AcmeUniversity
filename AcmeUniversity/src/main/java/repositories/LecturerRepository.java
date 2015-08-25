 package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Lecturer;
import domain.Student;
import domain.Subject;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Integer>{

	@Query("select l from Lecturer l where l.userAccount.id = ?1")
	Lecturer findByUserAccountId(int id);

	@Query("select distinct l from Lecturer l join l.applications app join app.subject sub join sub.activities ac join ac.students stu where stu=?1 and app.accepted = true")
	Collection<Lecturer> findLecturersOfStudent(Student student);

	@Query("select distinct l, ((select sum(a.rating) from Lecturer lec join lec.assessment a where lec=l)/(select count(a) from Lecturer lec join lec.assessment a where lec=l)) as rating from Lecturer l join l.assessment a where a != null order by rating desc")
	Collection<Object[]> findBestRated();

	@Query("select l, (select count(a) from Application a where a.lecturer = l and a.accepted = true) from Lecturer l")
	Collection<Object[]> findLecturersWithMoreSubjects();

	@Query("select distinct app.lecturer from Application app join app.subject sub where sub=?1 and app.accepted = true")
	Collection<Lecturer> findLecturersOfSubject(Subject subject);

}
