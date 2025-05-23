package das.uah.apiusuariosdas.util;

import java.util.List;

public class ConstantsHelper {
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";
    public static final String SOURCE_USUARIO_TYPE = "U";
    public static final String SOURCE_PELICULA_TYPE = "P";
    public static final String DOCUMENT_GEN_TYPE = "Portada";
    public static final List<String> SOURCE_TYPES = List.of("U".toLowerCase(), "P".toLowerCase());
    public static final List<String> DOCUMENT_TYPES = List.of("Portada".toLowerCase());

    public ConstantsHelper() {
    }
}
