package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Student;
import domain.Subject;
import domain.Tutor;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Integer>{

	@Query("select t from Tutor t where t.userAccount.id = ?1")
	public Tutor findByUserAccountId(int id);

	@Query("select distinct t from Tutor t join t.tutorials tut join tut.subject s where s = ?1")
	public Collection<Tutor> getTutorsBySubject(Subject sub);

	@Query("select distinct t from Tutor t join t.tutorials tut join tut.students stud where stud=?1")
	public Collection<Tutor> findTutorsOfStudent(Student student);

	@Query("select distinct t, ((select sum(a.rating) from Tutor tut join tut.assessment a where tut=t) /(select count(a) from Tutor tut join tut.assessment a where tut=t)) as rating from Tutor t join t.assessment a where a != null order by rating desc")
	public Collection<Object[]> findBestRated();
}
