package hu.ltk.jakabgabor.validator;

import hu.ltk.jakabgabor.exception.InvalidRegistrationNumberException;

public class TollSystemValidator {
    public void checkRegistrationNumberIsNull(String registrationNumber) {
        try {
            if (registrationNumber.equals(null)) {
                throw new InvalidRegistrationNumberException();
            }
        }catch (InvalidRegistrationNumberException e){

        }
    }
}
