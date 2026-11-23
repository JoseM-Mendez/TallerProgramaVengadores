import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class Ventana {
    private JTabbedPane tbpInforme;
    private JPanel ventana;
    private JPanel tbpRegistro;
    private JPanel tbpListado;
    private JPanel tbp;
    private JLabel lblID;
    private JTextField txtID;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JTextField txtMision;
    private JLabel lblPeligrosidad;
    private JLabel lblMision;
    private JSpinner spnPeligrosidad;
    private JTextField txtPago;
    private JLabel lblPago;
    private JButton btnRegistrar;
    private JButton btnModificar;
    private JButton btnLimpiar;
    private JTextArea txtListado;
    private JButton btnModSeleccionado;
    private JTextArea txtInforme;


    private final Map<Integer, Vengador> vengadores = new LinkedHashMap<>();

    public Ventana() {
        spnPeligrosidad.setModel(new SpinnerNumberModel(1, 1, 5, 1));
        Font mono = new Font(Font.MONOSPACED, Font.PLAIN, 12);
        txtListado.setFont(mono);
        txtInforme.setFont(mono);
        txtListado.setEditable(false);
        txtInforme.setEditable(false);

        btnRegistrar.addActionListener(e -> registrar());
        btnModificar.addActionListener(e -> modificar());
        btnLimpiar.addActionListener(e -> limpiar());
        btnModSeleccionado.addActionListener(e -> cargarSeleccionado());

        tbpInforme.addChangeListener(e -> {
            if (tbpInforme.getSelectedIndex() == 2) {
                txtInforme.setText(Informe.generar(vengadores.values()));
            }
        });
    }

    private void registrar() {
        try {
            int id = leerId();
            if (vengadores.containsKey(id)) {
                throw new IllegalArgumentException("Ya existe un Vengador con el ID " + id + ".");
            }
            vengadores.put(id, new Vengador(id, txtNombre.getText(), txtMision.getText(),
                    (Integer) spnPeligrosidad.getValue(), leerPago()));
            actualizarListado();
            limpiar();
            JOptionPane.showMessageDialog(ventana, "Vengador registrado correctamente.");
        } catch (IllegalArgumentException ex) {
            error(ex.getMessage());
        }
    }

    private void modificar() {
        try {
            int id = leerId();
            Vengador v = vengadores.get(id);
            if (v == null) {
                throw new IllegalArgumentException("No existe un Vengador con el ID " + id + ".");
            }
            v.setDatos(txtNombre.getText(), txtMision.getText(),
                    (Integer) spnPeligrosidad.getValue(), leerPago());
            actualizarListado();
            limpiar();
            JOptionPane.showMessageDialog(ventana, "Vengador modificado correctamente.");
        } catch (IllegalArgumentException ex) {
            error(ex.getMessage());
        }
    }

    private void cargarSeleccionado() {
        if (vengadores.isEmpty()) {
            error("No hay Vengadores registrados.");
            return;
        }
        try {
            int linea = txtListado.getLineOfOffset(txtListado.getCaretPosition());
            int inicio = txtListado.getLineStartOffset(linea);
            int fin = txtListado.getLineEndOffset(linea);
            String texto = txtListado.getText(inicio, fin - inicio).trim();
            if (!texto.startsWith("ID: ")) {
                error("Haga clic sobre la línea de un Vengador en el listado.");
                return;
            }
            int id = Integer.parseInt(texto.substring(4, texto.indexOf('|')).trim());
            Vengador v = vengadores.get(id);
            txtID.setText(String.valueOf(v.getId()));
            txtNombre.setText(v.getNombre());
            txtMision.setText(v.getMision());
            spnPeligrosidad.setValue(v.getPeligrosidad());
            txtPago.setText(String.valueOf(v.getPagoMensual()));
            tbpInforme.setSelectedIndex(0); // vuelve a la pestaña Registro
        } catch (BadLocationException | NumberFormatException ex) {
            error("Haga clic sobre la línea de un Vengador en el listado.");
        }
    }

    private void limpiar() {
        txtID.setText("");
        txtNombre.setText("");
        txtMision.setText("");
        spnPeligrosidad.setValue(1);
        txtPago.setText("");
        txtID.requestFocus();
    }

    private void actualizarListado() {
        StringBuilder sb = new StringBuilder();
        for (Vengador v : vengadores.values()) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append(String.format(Locale.US, "ID: %d | %s | %s | Peligrosidad: %d | Pago: $%,.2f",
                    v.getId(), v.getNombre(), v.getMision(), v.getPeligrosidad(), v.getPagoMensual()));
        }
        txtListado.setText(sb.toString());
        txtListado.setCaretPosition(0);
    }

    private int leerId() {
        try {
            return Integer.parseInt(txtID.getText().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El ID debe ser un número entero.");
        }
    }

    private double leerPago() {
        try {
            return Double.parseDouble(txtPago.getText().trim().replace(",", "."));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El pago mensual debe ser un número.");
        }
    }

    private void error(String mensaje) {
        JOptionPane.showMessageDialog(ventana, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ventana");
        frame.setContentPane(new Ventana().ventana);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
