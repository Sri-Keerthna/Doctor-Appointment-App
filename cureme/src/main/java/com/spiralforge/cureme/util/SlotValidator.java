package com.spiralforge.cureme.util;

import com.spiralforge.cureme.exception.ValidationFailedException;

public interface SlotValidator<T> {
	
	Boolean validate(T t) throws ValidationFailedException;
}
