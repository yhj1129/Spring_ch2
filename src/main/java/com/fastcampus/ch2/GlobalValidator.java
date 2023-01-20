package com.fastcampus.ch2;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class GlobalValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
//			return User.class.equals(clazz); // �����Ϸ��� ��ü�� UserŸ������ Ȯ��
        return User.class.isAssignableFrom(clazz); // clazz�� User �Ǵ� �� �ڼ����� Ȯ��
    }

    @Override
    public void validate(Object target, Errors errors) {
        System.out.println("GlobalValidator.validate() is called");

        User user = (User)target; //target�� Object���̱� ������ ����ȯ

        String id = user.getId();

        //		if(id==null || "".equals(id.trim())) {
        //			errors.rejectValue("id", "required");
        //		}
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id",  "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pwd", "required");

        if(id==null || id.length() <  5 || id.length() > 12) {
            errors.rejectValue("id", "invalidLength", new String[] {"5", "12"}, null);
        }
    }
}
