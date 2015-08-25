package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Comment;


@Component
@Transactional
public class CommentToStringConverter implements Converter<Comment, String>{

	@Override
	public String convert(Comment object) {
		String result;

		if (object == null)
			result = null;
		else
			result = String.valueOf(object.getId());

		return result;

	}
}
