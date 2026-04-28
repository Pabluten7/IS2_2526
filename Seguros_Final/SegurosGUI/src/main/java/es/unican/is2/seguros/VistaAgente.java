package es.unican.is2.seguros;
import es.seguros.IGestionClientes;
import es.seguros.IGestionSeguros;
import es.seguros.IInfoSeguros;
import es.unican.is2.seguros.Cliente;
import es.unican.is2.seguros.Seguro;
import es.unican.is2.seguros.DataAccessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class VistaAgente extends JFrame {

    private JPanel contentPane;
    private JTextField txtDniCliente;
    private JTextField txtTotalCliente;
    private JTextField txtNombreCliente;
    private JList<String> listSeguros; 
    private DefaultListModel<String> listModel;
    private JButton btnBuscar;
    
    // Usamos las interfaces tal cual, ya que no tienen package definido
    private transient IGestionClientes clientes;
    private transient IGestionSeguros seguros;
    private transient IInfoSeguros info;

    public VistaAgente(IGestionClientes clientes, IGestionSeguros seguros, IInfoSeguros info) {
        this.clientes = clientes;
        this.seguros = seguros;
        this.info = info;
        init();
    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // ... El resto del código de los botones y campos que ya tenías ...
        // Asegúrate de que los setName() coincidan con los del Test (txtDNICliente, btnBuscar, etc.)
    }

    private void rellenaDatosCliente(String dni) {
        try {
            Cliente c = info.cliente(dni);
            if (c != null) {
                txtNombreCliente.setText(c.getNombre());
                txtTotalCliente.setText(String.valueOf(c.totalSeguros()));
                listModel.clear();
                for (Seguro s : c.getSeguros()) {
                    listModel.addElement(s.getMatricula());
                }
            }
        } catch (DataAccessException e) {
            // Manejo de error
        }
    }
}