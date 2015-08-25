package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Student;
import forms.StudentDashboardForm;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{

	@Query("select s from Student s where s.userAccount.id = ?1")
	public Student findByUserAccountId(int id);
	
	
	@Query("select s, (select count(distinct l) from LectureNote l join l.verifications v where l.student = s and v.correct= true) from Student s")
	public Collection<Object[]> findStudentsWithMoreValidatedLN();

}
