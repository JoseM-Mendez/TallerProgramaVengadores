public class CalcularNomina {
    public static final double PORCENTAJE_FONDO = 0.08;
    public static double aporteFondo(Vengador v) {
        return v.getPagoMensual() * PORCENTAJE_FONDO;
    }

    public static double impuestoAnual(Vengador v) {
        double anual = v.getPagoMensual() * 12;
        if (anual <= 50000) {
            return 0;
        } else if (anual <= 100000) {
            return 0.10 * (anual - 50000);
        } else if (anual <= 200000) {
            return 0.20 * (anual - 100000);
        } else {
            return 0.30 * (anual - 200000);
        }
    }

    public static double impuestoMensual(Vengador v) {
        return impuestoAnual(v) / 12;
    }

    public static double pagoNeto(Vengador v) {
        return v.getPagoMensual() - aporteFondo(v) - impuestoMensual(v);
    }
}
