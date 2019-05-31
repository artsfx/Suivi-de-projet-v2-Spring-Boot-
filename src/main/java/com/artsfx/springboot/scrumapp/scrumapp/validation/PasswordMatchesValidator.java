package com.artsfx.springboot.scrumapp.scrumapp.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.artsfx.springboot.scrumapp.scrumapp.dto.UserDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object>{

	/* (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 */
	
	/* (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	 */
	

	@Override
	public void initialize(PasswordMatches constraintAnnotation) {
	
	}	
	
	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		

	    	        
		UserDto user = (UserDto) obj;
		
		   boolean isValid = user.getPassword().equals(user.getMatchingPassword());
		    if(!isValid){
		         context.disableDefaultConstraintViolation();
		        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
		                .addPropertyNode( "matchingPassword" ).addConstraintViolation();
		    }
		System.out.println("Inside Password match validation" + user.getPassword().equals(user.getMatchingPassword()));
		return isValid; //user.getPassword().equals(user.getMatchingPassword());
		
 
	}


	
	
}
