//package es.unican.is2.seguros;

public class GestionSeguros implements IGestionSeguros, IGestionClientes, IInfoSeguros {

    private IClientesDAO clientesDAO;
    private ISegurosDAO segurosDAO;

    public GestionSeguros(IClientesDAO clientesDAO, ISegurosDAO segurosDAO) {
        this.clientesDAO = clientesDAO;
        this.segurosDAO = segurosDAO;
    }

    @Override
    public Cliente cliente(String dni) throws DataAccessException {
        return clientesDAO.cliente(dni);
    }

    @Override
    public Seguro seguro(String matricula) throws DataAccessException {
        return segurosDAO.seguroPorMatricula(matricula);
    }

    @Override
    public Cliente nuevoCliente(Cliente c) throws DataAccessException {
        return clientesDAO.creaCliente(c);
    }

    @Override
    public Cliente bajaCliente(String dni) throws OperacionNoValida, DataAccessException {
        Cliente c = clientesDAO.cliente(dni);
        if (c == null) {
            return null;
        }
        if (!c.getSeguros().isEmpty()) {
            throw new OperacionNoValida("No se puede borrar el cliente porque tiene seguros");
        }
        return clientesDAO.eliminaCliente(dni);
    }

    @Override
    public Seguro nuevoSeguro(Seguro s, String dni) throws OperacionNoValida, DataAccessException {
        Cliente c = clientesDAO.cliente(dni);
        if (c == null) {
            return null;
        }
        Seguro existente = segurosDAO.seguroPorMatricula(s.getMatricula());
        if (existente != null) {
            throw new OperacionNoValida("El seguro ya existe");
        }
        c.getSeguros().add(s);
        clientesDAO.actualizaCliente(c);
        return segurosDAO.creaSeguro(s);
    }

    @Override
    public Seguro bajaSeguro(String matricula, String dni) throws OperacionNoValida, DataAccessException {
        Seguro s = segurosDAO.seguroPorMatricula(matricula);
        Cliente c = clientesDAO.cliente(dni);
        if (s == null || c == null) {
            return null;
        }
        boolean pertenece = false;
        for (Seguro seguroCliente : c.getSeguros()) {
            if (seguroCliente.getMatricula().equals(matricula)) {
                pertenece = true;
                break;
            }
        }
        if (!pertenece) {
            throw new OperacionNoValida("El seguro no pertenece al cliente");
        }
        c.getSeguros().remove(s);
        clientesDAO.actualizaCliente(c);
        return segurosDAO.eliminaSeguro(s.getId());
    }

    @Override
    public Seguro anhadeConductorAdicional(String matricula, String conductor) throws DataAccessException {
        Seguro s = segurosDAO.seguroPorMatricula(matricula);
        if (s == null) {
            return null;
        }
        s.setConductorAdicional(conductor);
        return segurosDAO.actualizaSeguro(s);
    }
}