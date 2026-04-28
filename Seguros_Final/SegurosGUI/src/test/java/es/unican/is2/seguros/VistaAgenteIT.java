package es.unican.is2.seguros;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Dimension;

public class VistaAgenteIT {

    private FrameFixture window;
    private Robot robot;

    @BeforeAll
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeEach
    public void setUp() {
        // Inicializamos la lógica real
        ClientesDAO clientesDAO = new ClientesDAO();
        SegurosDAO segurosDAO = new SegurosDAO();
        GestionSeguros gSeguros = new GestionSeguros(clientesDAO, segurosDAO);

        robot = BasicRobot.robotWithNewAwtHierarchy();

        VistaAgente frame = GuiActionRunner.execute(new GuiQuery<VistaAgente>() {
            @Override
            protected VistaAgente executeInEDT() {
                VistaAgente v = new VistaAgente(gSeguros, gSeguros, gSeguros);
                v.setPreferredSize(new Dimension(450, 300)); // Forzamos tamaño
                v.pack();
                return v;
            }
        });
        
        window = new FrameFixture(robot, frame);
        window.show(); 
        window.focus(); // Requerido para que el teclado funcione
    }

    @AfterEach
    public void tearDown() {
        if (window != null) {
            window.cleanUp();
        }
    }

    @Test
    void testConsultaClienteJuan() {
        // Reemplazamos enterText por setText para evitar fallos de velocidad de teclado
        window.textBox("txtDNICliente").setText("11111111A");
        window.button("btnBuscar").click();

        // Esperamos un momento a que la BD responda y la UI se actualice
        window.textBox("txtNombreCliente").requireText("Juan");
        window.textBox("txtTotalCliente").requireText("1820.0");
    }

    @Test
    void testConsultaClienteLuis() {
        window.textBox("txtDNICliente").setText("33333333A");
        window.button("btnBuscar").click();

        window.textBox("txtNombreCliente").requireText("Luis");
        window.textBox("txtTotalCliente").requireText("0.0");
    }
    @Test
    void testConsultaClienteNoExiste() {
        // 1. Buscamos primero a Juan para que haya datos en la pantalla
        window.textBox("txtDNICliente").setText("11111111A");
        window.button("btnBuscar").click();
        
        // 2. Ahora buscamos un DNI que sabemos que NO está en H2ServerConnectionManager
        window.textBox("txtDNICliente").setText("99999999Z");
        window.button("btnBuscar").click();

        // 3. Verificamos que los campos se han limpiado (según tu lógica en rellenaDatosCliente)
        window.textBox("txtNombreCliente").requireText("");
        window.textBox("txtTotalCliente").requireText("");
        
        // Verificamos que la lista de seguros está vacía
        String[] items = window.list("listSeguros").contents();
        assert(items.length == 0);
    }
}