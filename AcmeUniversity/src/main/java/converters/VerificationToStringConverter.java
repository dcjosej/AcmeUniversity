package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Verification;


@Component
@Transactional
public class VerificationToStringConverter implements Converter<Verification, String>{

	@Override
	public String convert(Verification object) {
		String result;

		if (object == null)
			result = null;
		else
			result = String.valueOf(object.getId());

		return result;

	}
}
