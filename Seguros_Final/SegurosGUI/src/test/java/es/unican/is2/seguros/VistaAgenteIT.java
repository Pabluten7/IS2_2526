package es.unican.is2.seguros;

import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.jupiter.api.Test;

import es.seguros.GestionSeguros;
import es.unican.is.seguros.ClientesDAO;
import es.unican.is.seguros.SegurosDAO;

public class VistaAgenteIT extends AssertJSwingJUnitTestCase {

    private FrameFixture window;

    @Override
    protected void onSetUp() {
        // 1. Instanciamos los DAOs reales
        ClientesDAO clientesDAO = new ClientesDAO();
        SegurosDAO segurosDAO = new SegurosDAO();

        // 2. Instanciamos tu GestionSeguros (que sí existe)
        GestionSeguros gSeguros = new GestionSeguros(segurosDAO, clientesDAO);

        // 3. Lanzamos la vista. 
        // Pasamos clientesDAO (donde pida gestión de clientes) 
        // y gSeguros (donde pida gestión de seguros).
        VistaAgente frame = new VistaAgente(clientesDAO, gSeguros, clientesDAO);
        
        window = new FrameFixture(robot(), frame);
        window.show(); 
    }

    @Test
    void testConsultaClienteJuan() {
        // Datos de la Tabla 1 del Anexo
        window.textBox("txtDNICliente").enterText("11111111A");
        window.button("btnBuscar").click();

        window.textBox("txtNombreCliente").requireText("Juan");
        window.textBox("txtTotalCliente").requireText("1784.0");
    }

    @Test
    void testConsultaClienteLuis() {
        window.textBox("txtDNICliente").enterText("33333333A");
        window.button("btnBuscar").click();

        window.textBox("txtNombreCliente").requireText("Luis");
        window.textBox("txtTotalCliente").requireText("0.0");
    }
}