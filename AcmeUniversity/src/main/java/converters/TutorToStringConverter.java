package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Tutor;


@Component
@Transactional
public class TutorToStringConverter implements Converter<Tutor, String>{

	@Override
	public String convert(Tutor object) {
		String result;

		if (object == null)
			result = null;
		else
			result = String.valueOf(object.getId());

		return result;

	}
}
