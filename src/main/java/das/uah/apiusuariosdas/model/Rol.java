package das.uah.apiusuariosdas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrol", nullable = false)
    private Long id;

    @Column(name = "codigo", nullable = false, length = Integer.MAX_VALUE)
    private String codigo;

    @Column(name = "nobre", nullable = false, length = Integer.MAX_VALUE)
    private String nobre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNobre() {
        return nobre;
    }

    public void setNobre(String nobre) {
        this.nobre = nobre;
    }

}