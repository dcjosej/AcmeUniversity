package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.PrivateMessage;


@Component
@Transactional
public class PrivateMessageToStringConverter implements Converter<PrivateMessage, String>{

	@Override
	public String convert(PrivateMessage object) {
		String result;

		if (object == null)
			result = null;
		else
			result = String.valueOf(object.getId());

		return result;

	}
}
