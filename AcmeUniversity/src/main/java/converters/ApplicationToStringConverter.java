package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Application;


@Component
@Transactional
public class ApplicationToStringConverter implements Converter<Application, String>{

	@Override
	public String convert(Application object) {
		String result;

		if (object == null)
			result = null;
		else
			result = String.valueOf(object.getId());

		return result;

	}
}
