package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.AssessmentRepository;
import utilities.dpexceptions.DPForbiddenException;
import utilities.dpexceptions.DPNotFoundException;
import domain.Assessment;
import domain.Lecturer;
import domain.Student;
import domain.Teacher;
import domain.Tutor;
import forms.AssessmentForm;

@Service
@Transactional
public class AssessmentService {
	//Managed repository --------------
	@Autowired
	private AssessmentRepository assessmentRepository;
	
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private LecturerService lecturerService;
	@Autowired
	private TutorService tutorService;
	
	//Supporting services ---------------------------------
	
	//Simple CRUD method ------------------------------
	
	public Collection<Assessment> findByIdTeacher(int idTeacher){
		Collection<Assessment> res = assessmentRepository.findByIdTeacher(idTeacher);
		return res;
	}


	public AssessmentForm createForm(int id) {
		Teacher teacher = checkCanAssess(id);
		
		AssessmentForm assessmentForm = new AssessmentForm();
		
		assessmentForm.setTeacher(teacher.getId());
		
		return assessmentForm;
	}
	
	public Assessment reconstruct(AssessmentForm assessmentForm) {
		Teacher teacher = checkCanAssess(assessmentForm.getTeacher());
		Student student = studentService.findByPrincipal();
		
		Assessment assessment = new Assessment();
		assessment.setTeacher(teacher);
		assessment.setStudent(student);
		assessment.setRating(assessmentForm.getRating());
		assessment.setText(assessmentForm.getText());
		
		return assessment;
	}
	


	public Assessment save(Assessment assessment) {
		Assessment result= assessmentRepository.save(assessment);
		return result;
	}
	
	public boolean canAssess(int id){
		boolean result = true;
		try{
			checkCanAssess(id);
		}catch(Exception e){
			result = false;
		}
		
		return result;
	}
	
	private Teacher checkCanAssess(int id){
		Teacher teacher = teacherService.findOne(id);
		Student student = studentService.findByPrincipal();
		
		if(teacher == null){
			throw new DPNotFoundException();
		}
		
		if(student == null){
			throw new DPForbiddenException();
		}
		
		if(teacher instanceof Lecturer){
			Collection<Lecturer> studentsLecturers = lecturerService.findLecturersOfStudent(student); 
			if(!studentsLecturers.contains(teacher)){
				throw new DPForbiddenException();
			}
		}else if(teacher instanceof Tutor){
			Collection<Tutor> studentsTutors = tutorService.findTutorsOfStudent(student);
			if(!studentsTutors.contains(teacher)){
				throw new DPForbiddenException();
			}
		}else{
			throw new DPForbiddenException();
		}
		
		return teacher;
	}



	
}
