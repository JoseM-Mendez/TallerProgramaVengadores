public class Vengador {
    private final int id;
    private String nombre;
    private String mision;
    private int peligrosidad;
    private double pagoMensual;

    public Vengador(int id, String nombre, String mision, int peligrosidad, double pagoMensual) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un entero positivo.");
        }
        this.id = id;
        setDatos(nombre, mision, peligrosidad, pagoMensual);
    }
    public final void setDatos(String nombre, String mision, int peligrosidad, double pagoMensual) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (mision == null || mision.trim().isEmpty()) {
            throw new IllegalArgumentException("La misión es obligatoria.");
        }
        if (peligrosidad < 1 || peligrosidad > 5) {
            throw new IllegalArgumentException("La peligrosidad debe estar entre 1 y 5.");
        }
        if (pagoMensual <= 0) {
            throw new IllegalArgumentException("El pago mensual debe ser mayor a 0.");
        }
        this.nombre = nombre.trim();
        this.mision = mision.trim();
        this.peligrosidad = peligrosidad;
        this.pagoMensual = pagoMensual;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getMision() { return mision; }
    public int getPeligrosidad() { return peligrosidad; }
    public double getPagoMensual() { return pagoMensual; }
}
