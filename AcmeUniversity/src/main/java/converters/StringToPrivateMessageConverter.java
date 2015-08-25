package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.PrivateMessageRepository;
import domain.PrivateMessage;


@Component
@Transactional
public class StringToPrivateMessageConverter implements Converter<String, PrivateMessage>{

	@Autowired
	private PrivateMessageRepository repository;

	@Override
	public PrivateMessage convert(String text) {
		PrivateMessage result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = repository.findOne(id);
			}

		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
