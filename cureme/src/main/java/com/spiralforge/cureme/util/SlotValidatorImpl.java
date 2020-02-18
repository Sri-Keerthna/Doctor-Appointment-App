package com.spiralforge.cureme.util;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.spiralforge.cureme.constants.ApiConstant;
import com.spiralforge.cureme.dto.AddSlotRequestDto;
import com.spiralforge.cureme.exception.ValidationFailedException;

/**
 * 
 * @author Sujal The slot validator is used to validate the slot
 *         information
 *
 */
@Component("slotValidator")
public class SlotValidatorImpl implements SlotValidator<AddSlotRequestDto> {

	@Override
	public Boolean validate(AddSlotRequestDto addSlotRequestDto) throws ValidationFailedException {
		
		if(Objects.isNull(addSlotRequestDto)) {
			throw new ValidationFailedException(ApiConstant.INVALID_SLOT_DETAIL);
		}else if(!addSlotRequestDto.getAvailableFromDate().isAfter(LocalDate.now())){
			throw new ValidationFailedException(ApiConstant.INVALID_SLOT_FROMDATE);
		}else if(!addSlotRequestDto.getAvailableToDate().isAfter(LocalDate.now())){
			throw new ValidationFailedException(ApiConstant.INVALID_SLOT_TODATE);
		}
		else if(addSlotRequestDto.getAvailableFrom().isAfter(addSlotRequestDto.getAvailableTo())){
			throw new ValidationFailedException(ApiConstant.INVALID_SLOT_TIME);
		}
		return true;
	}

}
