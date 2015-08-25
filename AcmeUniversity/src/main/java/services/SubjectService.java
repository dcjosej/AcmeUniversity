package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.SubjectRepository;
import utilities.dpexceptions.DPForbiddenException;
import utilities.dpexceptions.DPNotFoundException;
import domain.Application;
import domain.Degree;
import domain.Lecturer;
import domain.Subject;
import forms.SubjectDashboardForm;
import forms.SubjectForm;

@Service
@Transactional
public class SubjectService {

	// Managed repository ----------------------
	@Autowired
	private SubjectRepository subjectRepository;

	// Supporting services ---------------------
	@Autowired
	private LecturerService lecturerService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private DegreeService degreeService;
	
	
	// Simple CRUD methods ------------------------
	public Collection<Subject> findByIdDegree(int idDegree) {
		Collection<Subject> res = subjectRepository.findByIdDegree(idDegree);
		return res;
	}

	public Subject findOne(int idSubject) {
		Subject res = subjectRepository.findOne(idSubject);
		
		if (res == null) {
			throw new DPNotFoundException();
		}
		
		return res;
	}

	public Collection<Subject> findAll() {
		return subjectRepository.findAll();
	}

	public Collection<Subject> getSubjectsByLecturer(Lecturer lecturer) {
		Collection<Subject> result = subjectRepository.getSubjectsByLecturer(lecturer);
		return result;
	}


	// Other methods ------------------------------
	

	public Application register(int id) {
		Subject subject = findOne(id);
		
		checkCanRegister(subject);
		
		Lecturer lecturer = lecturerService.findByPrincipal();

		Application application = new Application();
		application.setAccepted(false);
		application.setSubject(subject);
		application.setLecturer(lecturer);
		
		Application dbApplication = applicationService.save(application);
		
		subject.getApplications().add(dbApplication);
		
		subjectRepository.save(subject);
		
		return dbApplication;
	}
	
	private void checkCanRegister(Subject subject){
		Lecturer lecturer = lecturerService.findByPrincipal();
		
		if(lecturer == null){
			throw new DPForbiddenException();
		}
		
		Integer numApplications = applicationService.getNumApplications(lecturer, subject);
		
		if(numApplications >= 1){
			throw new DPForbiddenException();
		}
	}

	public Collection<SubjectDashboardForm> findSubjectsWithMoreComments() {
		Collection<Object[]> objects = subjectRepository.findSubjectsWithMoreComments();
		Collection<SubjectDashboardForm> subjectDashboardForms = new HashSet<>();
		
		for(Object[] o : objects){
			Subject subject = (Subject) o[0];
			Long num = (Long) o[1];
			SubjectDashboardForm subjectDashboardForm = new SubjectDashboardForm();
			subjectDashboardForm.setSubject(subject);
			subjectDashboardForm.setNum(num);
			subjectDashboardForms.add(subjectDashboardForm);
		}
		
		return subjectDashboardForms;
	}
	
	public boolean canRegister(Subject subject){
		boolean result = true;
		
		try{
			checkCanRegister(subject);
		}catch(Exception e){
			result = false;
		}
		
		return result;
	}
	
	public boolean canEdit(Subject subject){
		boolean result = true;
		
		try{
			checkCanEdit(subject);
		}catch(Exception exc){
			result = false;
		}
		
		return result;
	}
	
	public void checkCanEdit(Subject subject){
		Lecturer lecturer = lecturerService.findByPrincipal();
		
		if(lecturer == null){
			throw new DPForbiddenException();
		}
		
		
		if(applicationService.isLecturerInSubject(lecturer, subject) == 0){
			throw new DPForbiddenException();
		}
	}

	public SubjectForm createForm(int id) {
		Degree degree = degreeService.findOne(id);
		
		SubjectForm subjectForm = new SubjectForm();
		subjectForm.setDegree(degree);
		
		return subjectForm;
	}

	public Subject reconstruct(SubjectForm subjectForm) {
		Subject result;
		
		if(subjectForm.getId() == 0){
			result = new Subject();
			if(subjectForm.getDegree() == null){
				throw new DPForbiddenException();
			}
			
			result.setDegree(subjectForm.getDegree());
		}else{
			result = findOne(subjectForm.getId());
			if(!canEdit(result)){
				throw new DPForbiddenException();
			}
		}
		
		
		
		result.setDescription(subjectForm.getDescription());
		result.setName(subjectForm.getName());
		
		return result;
	}

	public Subject save(Subject subject) {
		Subject result = subjectRepository.save(subject);
		return result;
	}

	public SubjectForm createForm(Subject subject) {
		if(!canEdit(subject)){
			throw new DPForbiddenException();
		}
		
		SubjectForm subjectForm = new SubjectForm();
		
		subjectForm.setId(subject.getId());
		subjectForm.setVersion(subject.getVersion());
		subjectForm.setDegree(subject.getDegree());
		subjectForm.setName(subject.getName());
		subjectForm.setDescription(subject.getDescription());
		
		
		return subjectForm;
	}
	
}
