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

public class VistaAgenteIT {

    private FrameFixture window;
    private Robot robot;

    // Configuración obligatoria para JUnit 5 + AssertJ Swing
    @BeforeAll
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeEach
    public void setUp() {
        ClientesDAO clientesDAO = new ClientesDAO();
        SegurosDAO segurosDAO = new SegurosDAO();
        GestionSeguros gSeguros = new GestionSeguros(clientesDAO, segurosDAO);

        // Forzamos un robot completamente nuevo y aislado para que no haya ventanas fantasma
        robot = BasicRobot.robotWithNewAwtHierarchy();

        VistaAgente frame = GuiActionRunner.execute(new GuiQuery<VistaAgente>() {
            @Override
            protected VistaAgente executeInEDT() {
                return new VistaAgente(gSeguros, gSeguros, gSeguros);
            }
        });
        
        // Unimos el nuevo robot a nuestra ventana
        window = new FrameFixture(robot, frame);
        window.show(); 
    }

    @AfterEach
    public void tearDown() {
        if (window != null) {
            window.cleanUp();
        }
    }

    @Test
    void testConsultaClienteJuan() {
        window.textBox("txtDNICliente").enterText("11111111A");
        window.button("btnBuscar").click();

        window.textBox("txtNombreCliente").requireText("Juan");
        window.textBox("txtTotalCliente").requireText("1820.0");
    }

    @Test
    void testConsultaClienteLuis() {
        window.textBox("txtDNICliente").enterText("33333333A");
        window.button("btnBuscar").click();

        window.textBox("txtNombreCliente").requireText("Luis");
        window.textBox("txtTotalCliente").requireText("0.0");
    }
}