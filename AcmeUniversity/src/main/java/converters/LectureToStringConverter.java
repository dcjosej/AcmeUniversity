package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Lecture;


@Component
@Transactional
public class LectureToStringConverter implements Converter<Lecture, String>{

	@Override
	public String convert(Lecture object) {
		String result;

		if (object == null)
			result = null;
		else
			result = String.valueOf(object.getId());

		return result;

	}
}
