package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Degree;

@Repository
public interface DegreeRepository extends JpaRepository<Degree, Integer>{

	@Query("select s.degrees from Student s where s.id = ?1")
	Collection<Degree> findByStudentId(int idStudent);

}
