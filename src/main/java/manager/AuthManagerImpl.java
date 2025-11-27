package manager;

import database.BaseDeDatos;
import database.impl.BaseDeDatosHashMap;
import database.models.Usuario;
import org.apache.log4j.Logger;

import java.util.List;

public class AuthManagerImpl implements AuthManager {
    private static final Logger LOGGER = Logger.getLogger(AuthManagerImpl.class);

    private static AuthManagerImpl instance;
    private final BaseDeDatos baseDeDatos;

    private AuthManagerImpl() {
        this.baseDeDatos = BaseDeDatosHashMap.getInstance();
    }

    public static AuthManagerImpl getInstance() {
        if (instance == null) {
            instance = new AuthManagerImpl();
            LOGGER.info("Instancia de AuthManagerImpl creada");
        }
        return instance;
    }

    // ==========================
    // VALIDACIÓN DE REGISTRO
    // ==========================
    // Método interno que valida username, nombre y apellido para evitar
    // caracteres especiales en campos no-email.
    private void validateRegistrationData(Usuario usr) {
        if (usr == null) {
            throw new RuntimeException("Datos de usuario inválidos");
        }

        // username: solo letras y números, sin espacios ni caracteres especiales
        String regexUsername = "^[a-zA-Z0-9]+$";
        // nombre/apellido: letras, acentos y espacios
        String regexNombre = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";

        if (usr.getUsername() == null || !usr.getUsername().matches(regexUsername)) {
            throw new RuntimeException("El usuario solo puede contener letras y números (sin espacios ni caracteres especiales).");
        }

        if (usr.getNombre() == null || !usr.getNombre().matches(regexNombre)) {
            throw new RuntimeException("El nombre solo puede contener letras y espacios.");
        }

        if (usr.getApellido() == null || !usr.getApellido().matches(regexNombre)) {
            throw new RuntimeException("El apellido solo puede contener letras y espacios.");
        }
        // No validamos password ni email/fecha aquí porque tienen sus propias reglas.
    }

    @Override
    public void register(Usuario usr) {
        // Validación preventiva: evita que clientes puedan registrar usernames con caracteres especiales
        validateRegistrationData(usr);

        LOGGER.info(" Inicio registro: username: " + usr.getUsername()+ " password: " + usr.getPassword()+" nombre: " + usr.getNombre() +" apellido: " + usr.getApellido()+" gmail: " + usr.getEmail()+" fechaNacimiento: " + usr.getFechaNacimiento());
        if (baseDeDatos.getUsuario(usr.getUsername()) != null) {
            LOGGER.error("Intento de registro fallido: El usuario ya existe: " + usr);
            throw new RuntimeException("El usuario ya existe");
        }
        LOGGER.info("Se ha registrado un nuevo usuario: " + usr);
        baseDeDatos.addUsuario(usr);
    }

    @Override
    public Usuario login(Usuario usr) {
        LOGGER.info(" Inicio login: username: " + usr.getUsername()+ " password: " + usr.getPassword());
        Usuario usuario = baseDeDatos.getUsuario(usr.getUsername());
        if (usuario == null || !usuario.getPassword().equals(usr.getPassword())) {
            LOGGER.error("Intento de login fallido para el usuario: " + usr);
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }
        LOGGER.info("Inicio de sesión exitoso: Usuario " + usr);
        return usuario;
    }

    @Override
    public List<Usuario> getRegisteredUsers() {
        return baseDeDatos.getUsuarios();
    }
}
