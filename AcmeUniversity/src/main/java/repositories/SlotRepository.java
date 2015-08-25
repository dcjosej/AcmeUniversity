package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Slot;
import domain.Student;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Integer>{

	@Query("select s from Slot s join s.activity.students stu where stu=?1 and s.dayOfTheWeek = ?2")
	public Collection<Slot> getStudentTimetableDay(Student student, String dayOfTheWeek);
	
	@Query("select s from Slot s where s.activity.id = ?1")
	public Collection<Slot> findByActivityId(int idActivity);
}
