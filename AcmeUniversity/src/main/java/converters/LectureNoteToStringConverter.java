package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.LectureNote;


@Component
@Transactional
public class LectureNoteToStringConverter implements Converter<LectureNote, String>{

	@Override
	public String convert(LectureNote object) {
		String result;

		if (object == null)
			result = null;
		else
			result = String.valueOf(object.getId());

		return result;

	}
}
