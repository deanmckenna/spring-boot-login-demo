package dmk.login.example.app.domain;


import java.util.Arrays;
import java.util.Optional;

public enum Role {

    ADMIN("ROLE_ADMIN"),
    LIBRARIAN("ROLE_LIBRARIAN"),
    CO_LIBRARIAN("ROLE_CO_LIBRARIAN"),
    CUSTOMER("ROLE_CUSTOMER");

    private String value;

    Role(String value){
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static Optional<Role> fromString(String strValue) {
        return Arrays.stream(Role.values())
                     .filter(r -> r.value.equalsIgnoreCase(strValue))
                     .findFirst();
    }
}
