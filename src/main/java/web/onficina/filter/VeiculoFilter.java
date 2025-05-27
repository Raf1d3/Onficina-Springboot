package web.onficina.filter;

    public class VeiculoFilter {
    private String placa;
    private String modelo;
    private String marca;

    // Getters e Setters
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return "VeiculoFilter [placa=" + placa + ", modelo=" + modelo + ", marca=" + marca + "]";
    }

    

}
