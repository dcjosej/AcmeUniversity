package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import repositories.StudentRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.comparators.StudentDashboardComparator;
import utilities.dpexceptions.DPInvalidRegistrationException;
import utilities.dpexceptions.DPNotFoundException;
import domain.Student;
import forms.RegistrationStudentForm;
import forms.StudentDashboardForm;

@Service
@Transactional
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	public Student findOne(int id){
		Student student = studentRepository.findOne(id);
		
		if(student == null){
			throw new DPNotFoundException();
		}
		
		return student;
	}
	
	public Student save(Student student){
		return studentRepository.save(student);
	}
	
	public Collection<StudentDashboardForm> findStudentsWithMoreValidatedLN(){
		Collection<Object[]> objects = studentRepository.findStudentsWithMoreValidatedLN();
		Collection<StudentDashboardForm> result = new TreeSet<>(new StudentDashboardComparator());
		
		for(Object[] o : objects){
			Student student = (Student) o[0];
			Long num = (Long) o[1];
			
			StudentDashboardForm sdfDashboardForm = new StudentDashboardForm();
			sdfDashboardForm.setStudent(student);
			sdfDashboardForm.setNumLectureNotes(num);
			
			result.add(sdfDashboardForm);
		}
		
		return result;
	}
	
	public Student reconstruct(RegistrationStudentForm form){
		if(!form.getPassword().equals(form.getPasswordVerification())){
			throw new DPInvalidRegistrationException("password.notMatch");
		}
		
		if(!form.isTerms()){
			throw new DPInvalidRegistrationException("terms.notChecked");
		}
		
		
		Student student = new Student();
		
		Authority authority = new Authority();
		authority.setAuthority(Authority.STUDENT);
		
		Collection<Authority> authorities = new HashSet<Authority>();
		authorities.add(authority);
		
		UserAccount account = new UserAccount();
		account.setAuthorities(authorities);
		account.setUsername(form.getUsername());
		Md5PasswordEncoder enc = new Md5PasswordEncoder();
		String password = enc.encodePassword(form.getPassword(), null);
		account.setPassword(password);
		
		student.setUserAccount(account);
		student.setName(form.getName());
		student.setSurname(form.getSurname());
		student.setEmail(form.getEmail());
		student.setPhoneNumber(form.getPhoneNumber());
		student.setIdSocial(form.getIdSocial());
		return student;
	}

	public Student findByPrincipal() {
		UserAccount account = LoginService.getPrincipal();
		int id = account.getId();
		return studentRepository.findByUserAccountId(id);
	}
}
