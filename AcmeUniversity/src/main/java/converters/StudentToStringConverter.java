package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Student;


@Component
@Transactional
public class StudentToStringConverter implements Converter<Student, String>{
	@Override
	public String convert(Student object) {
		String result;

		if (object == null)
			result = null;
		else
			result = String.valueOf(object.getId());

		return result;

	}
}
