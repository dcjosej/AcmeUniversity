package services;

import java.sql.Blob;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import repositories.TutorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.dpexceptions.DPForbiddenException;
import utilities.dpexceptions.DPInvalidRegistrationException;
import domain.Lecturer;
import domain.Student;
import domain.Subject;
import domain.Tutor;
import forms.RegistrationTeacherForm;
import forms.TutorListForm;
import forms.TutorRatedForm;

@Service
@Transactional
public class TutorService {

	@Autowired
	private TutorRepository tutorRepository;

	@Autowired
	private LecturerService lecturerService;
	@Autowired
	private SubjectService subjectService;

	public Tutor save(Tutor tutor) {
		return tutorRepository.save(tutor);
	}

	public Tutor findByPrincipal() {
		UserAccount account = LoginService.getPrincipal();
		int id = account.getId();
		return tutorRepository.findByUserAccountId(id);
	}

	public Tutor reconstruct(RegistrationTeacherForm form) {
		if (!form.getPassword().equals(form.getPasswordVerification())) {
			throw new DPInvalidRegistrationException("password.notMatch");
		}

		if (!form.isTerms()) {
			throw new DPInvalidRegistrationException("terms.notChecked");
		}

		Tutor tutor = new Tutor();

		Authority authority = new Authority();
		authority.setAuthority(Authority.TUTOR);

		Collection<Authority> authorities = new HashSet<Authority>();
		authorities.add(authority);

		UserAccount account = new UserAccount();
		account.setAuthorities(authorities);
		account.setUsername(form.getUsername());
		Md5PasswordEncoder enc = new Md5PasswordEncoder();
		String password = enc.encodePassword(form.getPassword(), null);
		account.setPassword(password);

		tutor.setUserAccount(account);
		tutor.setName(form.getName());
		tutor.setSurname(form.getSurname());
		tutor.setEmail(form.getEmail());
		tutor.setPhoneNumber(form.getPhoneNumber());

		tutor.setDescription(form.getDescription());
		tutor.setCurriculum(form.getCurriculum());
		tutor.setIdSocial(form.getIdSocial());

		MultipartFile photo = form.getPhoto();
		if (photo != null) {
			try {
				Blob p = new SerialBlob(photo.getBytes());
				tutor.setPhoto(p);
			} catch (Exception e) {
				throw new DPInvalidRegistrationException("commit.error");
			}
		}

		return tutor;
	}

	public Collection<TutorListForm> getListTutors()
			throws DPForbiddenException {
		Lecturer lecturer = lecturerService.findByPrincipal();

		if (lecturer == null) {
			throw new DPForbiddenException();
		}

		Collection<Subject> subjects = subjectService
				.getSubjectsByLecturer(lecturer);
		Collection<TutorListForm> result = new HashSet<TutorListForm>();
		for (Subject sub : subjects) {
			Collection<Tutor> tutors = getTutorsBySubject(sub);
			for (Tutor t : tutors) {
				TutorListForm tutorListForm = new TutorListForm();
				tutorListForm.setTutor(t);
				tutorListForm.setSubject(sub);

				result.add(tutorListForm);
			}
		}
		return result;
	}

	public Collection<Tutor> getTutorsBySubject(Subject sub) {
		Collection<Tutor> tutors = tutorRepository.getTutorsBySubject(sub);
		return tutors;
	}

	public Collection<Tutor> findTutorsOfStudent(Student student) {
		Collection<Tutor> tutorsOfStudent = tutorRepository
				.findTutorsOfStudent(student);
		return tutorsOfStudent;
	}

	public Collection<TutorRatedForm> findBestRated() {
		Collection<Object[]> tutors = tutorRepository.findBestRated();
		if (tutors.size() > 5) {
			List<Object[]> tutorsList = (List<Object[]>) tutors;
			tutorsList = tutorsList.subList(0, 5);
			tutors = tutorsList;
		}
		Collection<TutorRatedForm> result = new HashSet<TutorRatedForm>();
		for (Object[] o : tutors) {
			Tutor tutor = (Tutor) o[0];
			long rating = (long) o[1];
			TutorRatedForm tutorRatedForm = new TutorRatedForm();
			tutorRatedForm.setTutor(tutor);
			tutorRatedForm.setRating(rating);
			result.add(tutorRatedForm);
		}
		return result;
	}

	public Collection<Tutor> findAll() {
		return tutorRepository.findAll();
	}
}
