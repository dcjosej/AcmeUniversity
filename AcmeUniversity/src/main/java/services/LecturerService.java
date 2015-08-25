package services;

import java.sql.Blob;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import javax.sql.rowset.serial.SerialBlob;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import repositories.LecturerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.comparators.LecturerDashboardComparator;
import utilities.dpexceptions.DPInvalidRegistrationException;
import domain.Lecturer;
import domain.Student;
import domain.Subject;
import forms.LecturerDashboardForm;
import forms.LecturerRatedForm;
import forms.RegistrationTeacherForm;

@Service
@Transactional
public class LecturerService {
	@Autowired
	private LecturerRepository lecturerRepository;
	
	public Lecturer save(Lecturer lecturer){
		return lecturerRepository.save(lecturer);
	}
	
	public Lecturer findByPrincipal() {
		UserAccount account = LoginService.getPrincipal();
		int id = account.getId();
		Lecturer lecturer = lecturerRepository.findByUserAccountId(id);
		return lecturer;
	}
	
	public Lecturer reconstruct(RegistrationTeacherForm form){
		if(!form.getPassword().equals(form.getPasswordVerification())){
			throw new DPInvalidRegistrationException("password.notMatch");
		}
		
		if(!form.isTerms()){
			throw new DPInvalidRegistrationException("terms.notChecked");
		}
		
		
		Lecturer lecturer = new Lecturer();
		
		Authority authority = new Authority();
		authority.setAuthority(Authority.LECTURER);
		
		Collection<Authority> authorities = new HashSet<Authority>();
		authorities.add(authority);
		
		UserAccount account = new UserAccount();
		account.setAuthorities(authorities);
		account.setUsername(form.getUsername());
		Md5PasswordEncoder enc = new Md5PasswordEncoder();
		String password = enc.encodePassword(form.getPassword(), null);
		account.setPassword(password);
		
		lecturer.setUserAccount(account);
		lecturer.setName(form.getName());
		lecturer.setSurname(form.getSurname());
		lecturer.setEmail(form.getEmail());
		lecturer.setPhoneNumber(form.getPhoneNumber());
		
		lecturer.setDescription(form.getDescription());
		lecturer.setCurriculum(form.getCurriculum());
		lecturer.setIdSocial(form.getIdSocial());
		
		MultipartFile photo = form.getPhoto();
		try {
			Blob  p = new SerialBlob(photo.getBytes());
			lecturer.setPhoto(p);
		} catch (Exception e) {
			throw new DPInvalidRegistrationException("commit.error");
		}
		
		return lecturer;
	}

	public Collection<Lecturer> findLecturersOfStudent(Student student) {
		Collection<Lecturer> lecturersOfStudent = lecturerRepository.findLecturersOfStudent(student);
		return lecturersOfStudent;
	}

	public Collection<LecturerRatedForm> findBestRated() {
		Collection<Object[]> lecturers = lecturerRepository.findBestRated();
		if(lecturers.size() > 5){
			List<Object[]> lecturersList = (List<Object[]>)lecturers;
			lecturersList = lecturersList.subList(0, 5);
			lecturers = lecturersList;
		}
		Collection<LecturerRatedForm> result = new HashSet<LecturerRatedForm>();
		for(Object[] o: lecturers){
			Lecturer lecturer = (Lecturer) o[0];
			long rating = (long) o[1];
			LecturerRatedForm lecturerRatedForm = new LecturerRatedForm();
			lecturerRatedForm.setLecturer(lecturer);
			lecturerRatedForm.setRating(rating);
			result.add(lecturerRatedForm);
		}
		return result;
	}
	
	public Collection<LecturerDashboardForm> findLecturersWithMoreSubjects(){
		Collection<Object[]> objects = lecturerRepository.findLecturersWithMoreSubjects();
		Collection<LecturerDashboardForm> result = new TreeSet<LecturerDashboardForm>(new LecturerDashboardComparator());
		
		for(Object[] o : objects){
			Lecturer lecturer = (Lecturer) o[0];
			Long subjects = (Long) o[1];
			
			LecturerDashboardForm lecturerDashboardForm = new LecturerDashboardForm();
			lecturerDashboardForm.setLecturer(lecturer);
			lecturerDashboardForm.setSubjects(subjects);
			
			result.add(lecturerDashboardForm);
		}
		
		return result;
	}

	public Collection<Lecturer> findLecturersOfSubject(Subject subject) {
		return lecturerRepository.findLecturersOfSubject(subject);
	}
}
