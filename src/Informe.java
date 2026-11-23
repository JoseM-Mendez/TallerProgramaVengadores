import java.util.Collection;
import java.util.Locale;

public class Informe {
    private static String dinero(double v) {
        return String.format(Locale.US, "$%,.2f", v);
    }

    public static String generar(Collection<Vengador> vengadores) {
        if (vengadores.isEmpty()) {
            return "No hay Vengadores registrados.";
        }
        String formato = "%-20s %12s %12s %12s %12s%n";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(formato, "Nombre", "Pago mensual", "Fondo (8%)", "Impuesto", "Pago neto"));
        sb.append(String.format("%s%n", "-".repeat(72)));

        double tPago = 0, tFondo = 0, tImp = 0, tNeto = 0;
        for (Vengador v : vengadores) {
            double fondo = CalcularNomina.aporteFondo(v);
            double imp = CalcularNomina.impuestoMensual(v);
            double neto = CalcularNomina.pagoNeto(v);
            String nombre = v.getNombre().length() > 20 ? v.getNombre().substring(0, 20) : v.getNombre();
            sb.append(String.format(formato, nombre, dinero(v.getPagoMensual()),
                    dinero(fondo), dinero(imp), dinero(neto)));
            tPago += v.getPagoMensual();
            tFondo += fondo;
            tImp += imp;
            tNeto += neto;
        }
        sb.append(String.format("%s%n", "-".repeat(72)));
        sb.append(String.format(formato, "TOTAL", dinero(tPago), dinero(tFondo), dinero(tImp), dinero(tNeto)));
        return sb.toString();
    }
}
