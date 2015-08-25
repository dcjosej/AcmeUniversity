package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Degree;


@Component
@Transactional
public class DegreeToStringConverter implements Converter<Degree, String>{

	@Override
	public String convert(Degree object) {
		String result;

		if (object == null)
			result = null;
		else
			result = String.valueOf(object.getId());

		return result;

	}
}
