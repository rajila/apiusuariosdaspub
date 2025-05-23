package das.uah.apiusuariosdas.util;

import java.util.ArrayList;
import java.util.List;

public class ResponseHelper {
    private String status = "SUCCESS";
    private List<ErrorHelper> errors = new ArrayList();
    private Integer idData = 0;

    public ResponseHelper() {
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ErrorHelper> getErrors() {
        return this.errors;
    }

    public void setErrors(List<ErrorHelper> errors) {
        this.errors = errors;
    }

    public Integer getIdData() {
        return this.idData;
    }

    public void setIdData(Integer idData) {
        this.idData = idData;
    }
}
