package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Subject;


@Component
@Transactional
public class SubjectToStringConverter implements Converter<Subject, String>{

	@Override
	public String convert(Subject object) {
		String result;

		if (object == null)
			result = null;
		else
			result = String.valueOf(object.getId());

		return result;

	}
}
