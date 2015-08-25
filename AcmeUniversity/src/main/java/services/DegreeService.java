package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.DegreeRepository;
import utilities.dpexceptions.DPForbiddenException;
import utilities.dpexceptions.DPNotFoundException;
import domain.Degree;
import domain.Student;
import forms.DegreeForm;

@Service
@Transactional
public class DegreeService {

	// Managed repository ----------------------
	@Autowired
	private DegreeRepository degreeRepository;

	// Supporting services ---------------------
	@Autowired
	private StudentService studentService;

	// Simple CRUD methods ------------------------

	public Collection<Degree> findAll() {
		Collection<Degree> res = degreeRepository.findAll();
		return res;
	}

	public Degree findOne(int idDegree) {
		Degree res = degreeRepository.findOne(idDegree);
		
		if (res == null) {
			throw new DPNotFoundException();
		}
		
		return res;
	}

	public Degree save(Degree degree) {
		Degree res = degreeRepository.save(degree);
		return res;
	}

	// Other methods ------------------------------

	public void enrols(int idDegree) {
		Student principal = studentService.findByPrincipal();
		
		if(principal == null){
			throw new DPForbiddenException();
		}
		
		Degree degree = findOne(idDegree);

		if(principal.getDegrees().contains(degree)){
			throw new DPForbiddenException();
		}
		
		if(degree.getStudents().contains(principal)){
			throw new DPForbiddenException(); 
		}
		
		degree.getStudents().add(principal);
		principal.getDegrees().add(degree);
		studentService.save(principal);
	}

	public Collection<Degree> findDegreesByPrincipal() {
		Student principal = studentService.findByPrincipal();
		Collection<Degree> res = degreeRepository.findByStudentId(principal
				.getId());
		return res;
	}

	public Degree reconstruct(DegreeForm degreeForm) {
		Degree result = new Degree();
		result.setId(degreeForm.getId());
		result.setVersion(degreeForm.getVersion());
		result.setName(degreeForm.getName());
		return result;
	}
}
