package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import repositories.LectureNoteRepository;
import utilities.dpexceptions.DPForbiddenException;
import utilities.dpexceptions.DPInvalidRegistrationException;
import utilities.dpexceptions.DPNoFileException;
import utilities.dpexceptions.DPNotFoundException;
import domain.LectureNote;
import domain.Student;
import domain.Subject;
import forms.LectureNoteForm;

@Service
@Transactional
public class LectureNoteService {

	// Managed repository -----------------------
	@Autowired
	private LectureNoteRepository lectureNoteRepository;
	@Autowired
	private StudentService studentService;

	// Supporting services --------------------------
	@Autowired
	private SubjectService subjectService;

	public LectureNote findOne(int id) {
		LectureNote res = lectureNoteRepository.findOne(id);

		if (res == null) {
			throw new DPNotFoundException();
		}

		return res;
	}

	public Collection<LectureNote> findByIdSubject(int idSubject) {
		Collection<LectureNote> res = lectureNoteRepository
				.findByIdSubject(idSubject);
		return res;
	}

	public LectureNote create(int idSubject) {
		LectureNote res = new LectureNote();
		Subject subject = subjectService.findOne(idSubject);
		Student principal = studentService.findByPrincipal();
		res.setSubject(subject);
		res.setStudent(principal);
		return res;
	}

	public LectureNoteForm createForm(int idSubject) {
		LectureNoteForm res = new LectureNoteForm();
		Subject subject = subjectService.findOne(idSubject);
		res.setSubject(subject);
		return res;
	}

	public LectureNote saveLectureNote(LectureNote lectureNote) {
		Student principal = studentService.findByPrincipal();
		if (!principal.getDegrees().contains(
				lectureNote.getSubject().getDegree())) {
			throw new DPForbiddenException();
		}

		if (lectureNote.getFile() == null) {
			throw new DPNoFileException();
		}

		LectureNote res = lectureNoteRepository.save(lectureNote);
		return res;
	}

	// Other methods -------------------------------------

	public LectureNote recontruct(LectureNoteForm form) {
		LectureNote res = new LectureNote();
		res.setDescription(form.getDescription());
		res.setSubject(form.getSubject());

		
		try {
			MultipartFile file = form.getFile();
			res.setName(file.getOriginalFilename());
			res.setFile(file.getBytes());
		} catch (Exception e) {
			throw new DPNoFileException();
		}

		Student principal = studentService.findByPrincipal();
		
		if(principal == null){
			throw new DPForbiddenException();
		}
		
		res.setStudent(principal);
		return res;
	}
}
