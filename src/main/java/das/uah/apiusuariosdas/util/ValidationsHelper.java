package das.uah.apiusuariosdas.util;

public final class ValidationsHelper {
    public ValidationsHelper() {
    }

    public static boolean emailIsValid(String email) {
        email = email == null ? "" : email;
        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(EMAIL_REGEX);
    }

    public static boolean TypeSourceIsValid(String eTypeSource) {
        eTypeSource = eTypeSource == null ? "" : eTypeSource.trim().toLowerCase();
        return ConstantsHelper.SOURCE_TYPES.contains(eTypeSource);
    }

    public static boolean TypeDocumentIsValid(String eTypeDocument) {
        eTypeDocument = eTypeDocument == null ? "" : eTypeDocument.trim().toLowerCase();
        return ConstantsHelper.DOCUMENT_TYPES.contains(eTypeDocument);
    }
}
