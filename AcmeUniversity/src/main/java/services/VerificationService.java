package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.VerificationRepository;
import utilities.dpexceptions.DPForbiddenException;
import utilities.dpexceptions.DPNotFoundException;
import domain.LectureNote;
import domain.Lecturer;
import domain.Subject;
import domain.Verification;

@Service
@Transactional
public class VerificationService {

	// Managed repository ----------------------
	@Autowired
	private VerificationRepository verificationRepository;

	// Supporting services ---------------------
	@Autowired
	private LecturerService lecturerService;
	@Autowired
	private LectureNoteService lectureNoteService;
	@Autowired
	private SubjectService subjectService;

	// Simple CRUD methods ------------------------

	public Verification create(int idLectureNote) {
		Verification res = new Verification();
		LectureNote lectureNote = lectureNoteService.findOne(idLectureNote);
		Lecturer principal = lecturerService.findByPrincipal();
		res.setLecturer(principal);
		res.setLectureNote(lectureNote);
		return res;
	}

	public Verification save(Verification verification) {
		Lecturer principal = lecturerService.findByPrincipal();
		Collection<Subject> subjectsByPrincipal = subjectService
				.getSubjectsByLecturer(principal);

		Collection<Verification> verificationsByPrincipalForLectureNote = findByPrincipalAndLectureNote(verification
				.getLectureNote().getId());

		if (verification.getId() == 0
				&& verificationsByPrincipalForLectureNote.size() != 0) {
			throw new DPForbiddenException();
		}

		if (!principal.equals(verification.getLecturer())) {
			throw new DPForbiddenException();
		}
		if (!subjectsByPrincipal.contains(verification.getLectureNote()
				.getSubject())) {
			throw new DPForbiddenException();
		}

		Verification res = verificationRepository.save(verification);
		return res;
	}

	public Verification findOne(int idVerification) {
		Verification res = verificationRepository.findOne(idVerification);
		if (res == null) {
			throw new DPNotFoundException();
		}
		return res;
	}

	// Other methods -----------------------------------------

	public Collection<Verification> findByIdLectureNote(int idLectureNote) {
		Collection<Verification> res = verificationRepository
				.findByIdLectureNote(idLectureNote);
		return res;
	}

	public Collection<Verification> findByPrincipalAndLectureNote(
			int idLectureNote) {
		Lecturer principal = lecturerService.findByPrincipal();
		if (principal == null) {
			throw new DPForbiddenException();
		}
		Collection<Verification> res = verificationRepository
				.findByLecturerAndLectureNote(principal.getId(), idLectureNote);
		return res;
	}

}
