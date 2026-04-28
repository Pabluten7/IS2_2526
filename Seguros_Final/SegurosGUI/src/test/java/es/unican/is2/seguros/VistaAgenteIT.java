package es.unican.is2.seguros;

import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.jupiter.api.Test;

import es.unican.is.seguros.ClientesDAO;
import es.unican.is.seguros.GestionSeguros;
import es.unican.is.seguros.SegurosDAO;

public class VistaAgenteIT extends AssertJSwingJUnitTestCase {

    private FrameFixture window;

    @Override
    protected void onSetUp() {
        // 1. Instanciamos los DAOs reales
        ClientesDAO clientesDAO = new ClientesDAO();
        SegurosDAO segurosDAO = new SegurosDAO();

        // 2. Instanciamos tu GestionSeguros con el orden correcto (primero Clientes, luego Seguros)
        GestionSeguros gSeguros = new GestionSeguros(clientesDAO, segurosDAO);

        // 3. Lanzamos la vista pasando gSeguros a los 3 parámetros, 
        // ya que implementa las interfaces IGestionClientes, IGestionSeguros e IInfoSeguros.
        VistaAgente frame = new VistaAgente(gSeguros, gSeguros, gSeguros);
        
        window = new FrameFixture(robot(), frame);
        window.show(); 
    }

    @Test
    void testConsultaClienteJuan() {
        // Datos de la Tabla 1 del Anexo
        window.textBox("txtDNICliente").enterText("11111111A");
        window.button("btnBuscar").click();

        window.textBox("txtNombreCliente").requireText("Juan");
        // El total real es: 400 (1111AAA) + 1000 (1111BBB) + 420 (1111CCC) = 1820.0
        window.textBox("txtTotalCliente").requireText("1820.0");
    }

    @Test
    void testConsultaClienteLuis() {
        // Luis tiene minusvalía pero 0 seguros en la base de datos inicial
        window.textBox("txtDNICliente").enterText("33333333A");
        window.button("btnBuscar").click();

        window.textBox("txtNombreCliente").requireText("Luis");
        window.textBox("txtTotalCliente").requireText("0.0");
    }
}