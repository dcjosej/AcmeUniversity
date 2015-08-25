package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Lecturer;

@Component
@Transactional
public class LecturerToStringConverter implements Converter<Lecturer, String> {
	@Override
	public String convert(Lecturer object) {
		String result;

		if (object == null)
			result = null;
		else
			result = String.valueOf(object.getId());

		return result;

	}
}
