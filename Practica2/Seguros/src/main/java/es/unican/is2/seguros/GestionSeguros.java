package es.unican.is2.seguros;

// Implementamos las DOS interfaces que nos pide el sistema
public class GestionSeguros implements IGestionSeguros, IGestionClientes, IInfoSeguros {
    
    // Variables para guardar los accesos a datos
    private IClientesDAO clientesDAO;
    private ISegurosDAO segurosDAO;

    // Constructor que recibe los dos DAOs (Esto soluciona el primer error)
    public GestionSeguros(IClientesDAO clientesDAO, ISegurosDAO segurosDAO) {
        this.clientesDAO = clientesDAO;
        this.segurosDAO = segurosDAO;
    }

    @Override
    public Cliente nuevoCliente(Cliente c) throws DataAccessException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'nuevoCliente'");
    }

    @Override
    public Cliente bajaCliente(String dni) throws OperacionNoValida, DataAccessException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'bajaCliente'");
    }

    @Override
    public Seguro nuevoSeguro(Seguro s, String dni) throws OperacionNoValida, DataAccessException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'nuevoSeguro'");
    }

    @Override
    public Seguro bajaSeguro(String matricula, String dni) throws OperacionNoValida, DataAccessException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'bajaSeguro'");
    }

    @Override
    public Seguro anhadeConductorAdicional(String matricula, String conductor) throws DataAccessException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'anhadeConductorAdicional'");
    }

    @Override
    public Cliente cliente(String dni) throws DataAccessException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cliente'");
    }

    @Override
    public Seguro seguro(String matricula) throws DataAccessException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'seguro'");
    }

    // --- A PARTIR DE AQUÍ TIENES QUE GENERAR LOS MÉTODOS ---
    
    
}