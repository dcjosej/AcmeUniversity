package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.TutorialRepository;
import utilities.dpexceptions.DPForbiddenException;
import utilities.dpexceptions.DPInvalidRegistrationException;
import utilities.dpexceptions.DPNotFoundException;
import domain.Slot;
import domain.Student;
import domain.Tutor;
import domain.Tutorial;
import forms.SlotForm;
import forms.TutorialForm;

@Service
@Transactional
public class TutorialService {

	@Autowired
	private TutorialRepository tutorialRepository;
	@Autowired
	private StudentService studentService;
	@Autowired
	private TutorService tutorService;
	@Autowired
	private SlotService slotService;

	public TutorialForm createForm() {
		TutorialForm result = new TutorialForm();
		result.setNumSlots(1);
		result.setSlots(new ArrayList<SlotForm>());
		SlotForm slot = new SlotForm();
		result.getSlots().add(slot);

		return result;
	}

	public Tutorial findOne(int id) {
		Tutorial tutorial = tutorialRepository.findOne(id);

		if (tutorial == null) {
			throw new DPNotFoundException();
		}

		return tutorial;
	}

	public void register(int id) {
		checkRegistration(id);

		Tutorial tutorial = findOne(id);

		Student student = studentService.findByPrincipal();
		tutorial.getStudents().add(student);
		if (tutorial.getStudents().size() >= tutorial.getMaxStudents()) {
			tutorial.setAvailable(false);
		}
		student.getActivity().add(tutorial);
		studentService.save(student);
	}

	public boolean canRegister(int idTutorial) {
		boolean result = true;
		try {
			checkRegistration(idTutorial);
		} catch (Exception exc) {
			result = false;
		}

		return result;
	}

	public Collection<Tutorial> findByIdSubject(int idSubject) {
		Collection<Tutorial> res = tutorialRepository
				.findByIdSubject(idSubject);
		return res;
	}

	private void checkRegistration(int idTutorial) {
		Tutorial tutorial = findOne(idTutorial);
		if (!tutorial.isAvailable()) {
			throw new DPForbiddenException();
		}

		Student student = studentService.findByPrincipal();

		if (student == null) {
			throw new DPForbiddenException();
		} else {
			if (tutorial.getStudents().contains(student)) {
				throw new DPForbiddenException();
			}
		}
	}

	public Tutorial reconstruct(TutorialForm tutorialForm)
			throws DPForbiddenException {
		Tutor tutor = tutorService.findByPrincipal();

		if (tutor == null) {
			throw new DPForbiddenException();
		}

		Tutorial result;
		
		if(tutorialForm.getId() != 0){
			result = checkCanEdit(tutorialForm.getId());
			Collection<Student> students = result.getStudents();
			if(tutorialForm.getMaxStudents() > students.size()){
				result.setAvailable(true);
			}else{
				result.setAvailable(false);
			}
		}else{
			result = new Tutorial();
			result.setTutor(tutor);
			result.setAvailable(true);
		}
		
		result.setVersion(tutorialForm.getVersion());
		if(tutorialForm.getMaxStudents() < tutorialForm.getMinStudents()){
			throw new DPInvalidRegistrationException("tutorial.numberStudents");
		}
		result.setMaxStudents(tutorialForm.getMaxStudents());
		result.setMinStudents(tutorialForm.getMinStudents());
		result.setPricePerHour(tutorialForm.getPricePerHour());

		Collection<Slot> slots = new HashSet<Slot>();
		for (SlotForm sf : tutorialForm.getSlots()) {
			Slot s = new Slot();
			s.setActivity(result);
			if(sf.getId() != 0){
				s = slotService.findOne(sf.getId());
			}
			
			s.setDayOfTheWeek(sf.getDayOfTheWeek());
			if(sf.getStart().compareTo(sf.getFinish()) > 0){
				throw new DPInvalidRegistrationException("tutorial.datesError");
			}
			s.setFinish(sf.getFinish());
			s.setStart(sf.getStart());
			slots.add(s);
		}
		result.setSlots(slots);
		result.setSubject(tutorialForm.getSubject());
		

		return result;
	}

	public Tutorial save(Tutorial tutorial) {
		Collection<Slot> slotsCopy = new HashSet<Slot>(tutorial.getSlots());
		Tutorial result = tutorialRepository.save(tutorial);
		Collection<Slot> slots = new HashSet<Slot>();
		for (Slot s : slotsCopy) {
			Slot slot = new Slot();
			slot.setId(s.getId());
			slot.setVersion(s.getVersion());
			slot.setDayOfTheWeek(s.getDayOfTheWeek());
			slot.setFinish(s.getFinish());
			slot.setStart(s.getStart());
			slot.setActivity(result);

			slots.add(slot);
		}
		slotService.saveAll(slots);
		return result;
	}

	public TutorialForm findToEdit(int id) {
		Tutorial tutorial = checkCanEdit(id);

		TutorialForm tutorialForm = createForm(tutorial);
		return tutorialForm;
	}

	private TutorialForm createForm(Tutorial tutorial) {
		TutorialForm tutorialForm = new TutorialForm();

		tutorialForm.setId(tutorial.getId());
		tutorialForm.setMaxStudents(tutorial.getMaxStudents());
		tutorialForm.setMinStudents(tutorial.getMinStudents());
		tutorialForm.setPricePerHour(tutorial.getPricePerHour());
		tutorialForm.setSubject(tutorial.getSubject());

		List<SlotForm> slotForms = new ArrayList<SlotForm>();
		for (Slot slot : tutorial.getSlots()) {
			SlotForm slotForm = new SlotForm();
			slotForm.setId(slot.getId());
			slotForm.setVersion(slot.getVersion());
			slotForm.setDayOfTheWeek(slot.getDayOfTheWeek());
			slotForm.setFinish(slot.getFinish());
			slotForm.setStart(slot.getStart());

			slotForms.add(slotForm);
		}

		tutorialForm.setSlots(slotForms);
		tutorialForm.setNumSlots(slotForms.size());

		return tutorialForm;
	}

	public boolean canEdit(int id) {
		boolean result = true;
		try{
			checkCanEdit(id);
		}catch(Exception e){
			result = false;
		}
		return result;
	}
	
	private Tutorial checkCanEdit(int id){
		Tutorial tutorial = findOne(id);
		
		Tutor tutor = tutorService.findByPrincipal();
		
		if(tutor == null){
			throw new DPForbiddenException();
		}
		
		if(!tutorial.getTutor().equals(tutor)){
			throw new DPForbiddenException();
		}
		
		return tutorial;
	}
}
