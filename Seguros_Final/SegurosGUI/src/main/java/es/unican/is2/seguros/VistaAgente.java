package es.unican.is2.seguros;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
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

        // 1. Etiqueta y Cuadro de Texto para el DNI
        JLabel lblDni = new JLabel("DNI Cliente:");
        lblDni.setBounds(10, 20, 100, 20);
        contentPane.add(lblDni);

        txtDniCliente = new JTextField();
        txtDniCliente.setName("txtDNICliente"); // VITAL PARA EL TEST
        txtDniCliente.setBounds(120, 20, 150, 20);
        contentPane.add(txtDniCliente);

        // 2. Botón de Buscar
        btnBuscar = new JButton("Buscar");
        btnBuscar.setName("btnBuscar"); // VITAL PARA EL TEST
        btnBuscar.setBounds(280, 20, 100, 20);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rellenaDatosCliente(txtDniCliente.getText());
            }
        });
        contentPane.add(btnBuscar);

        // 3. Etiqueta y Cuadro de Texto para el Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(10, 60, 100, 20);
        contentPane.add(lblNombre);

        txtNombreCliente = new JTextField();
        txtNombreCliente.setName("txtNombreCliente"); // VITAL PARA EL TEST
        txtNombreCliente.setEditable(false);
        txtNombreCliente.setBounds(120, 60, 150, 20);
        contentPane.add(txtNombreCliente);

        // 4. Etiqueta y Cuadro de Texto para el Total
        JLabel lblTotal = new JLabel("Total Seguros:");
        lblTotal.setBounds(10, 100, 100, 20);
        contentPane.add(lblTotal);

        txtTotalCliente = new JTextField();
        txtTotalCliente.setName("txtTotalCliente"); // VITAL PARA EL TEST
        txtTotalCliente.setEditable(false);
        txtTotalCliente.setBounds(120, 100, 150, 20);
        contentPane.add(txtTotalCliente);

        // 5. Lista de Seguros
        listModel = new DefaultListModel<>();
        listSeguros = new JList<>(listModel);
        listSeguros.setName("listSeguros"); // VITAL PARA EL TEST
        listSeguros.setBorder(new LineBorder(new Color(0, 0, 0)));
        listSeguros.setBounds(120, 140, 150, 100);
        contentPane.add(listSeguros);
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
            } else {
                txtNombreCliente.setText("");
                txtTotalCliente.setText("");
                listModel.clear();
            }
        } catch (DataAccessException e) {
            // Manejo de error si falla la base de datos
        }
    }
}