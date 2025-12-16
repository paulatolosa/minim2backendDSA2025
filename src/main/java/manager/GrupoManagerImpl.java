package manager;

import org.apache.log4j.Logger;
import services.DTOs.Grupo;

import java.util.ArrayList;
import java.util.List;

public class GrupoManagerImpl implements GrupoManager {

    private static final Logger LOGGER = Logger.getLogger(GrupoManagerImpl.class);

    private static GrupoManagerImpl instance;

    private GrupoManagerImpl() {
        grupos.add(new Grupo("1", "Grupo A"));
        grupos.add(new Grupo("2", "Grupo B"));
        grupos.add(new Grupo("3", "Grupo C"));
    }

    public static GrupoManagerImpl getInstance() {
        if (instance == null) {
            instance = new GrupoManagerImpl();
            LOGGER.info("Instancia de GrupoManagerImpl creada");
        }
        return instance;
    }

    List<Grupo> grupos = new ArrayList<>();

    // T2: obtener lista de grupos
    @Override
    public List<Grupo> getGrupos() {
        return grupos;
    }

    // T3: unir usuario a un grupo
    @Override
    public void joinGrupo(String grupoId, String username) {

        if (grupoId == null || grupoId.isEmpty()) {
            throw new RuntimeException("El ID del grupo no puede estar vacío");
        }

        if (username == null || username.isEmpty()) {
            throw new RuntimeException("El nombre de usuario no puede estar vacío");
        }

        Grupo grupoEncontrado = null;

        // busquem grup al que es vol unir el username
        for (Grupo g : grupos) {
            if (g.getId().equals(grupoId)) {
                grupoEncontrado = g;
                break;
            }
        }

        if(grupoEncontrado == null) {
            throw new RuntimeException("Grupo no encontrado con ID: " + grupoId);
        }

        if(!grupoEncontrado.getMiembros().contains(username)) {
            grupoEncontrado.getMiembros().add(username);
            LOGGER.info("Usuario " + username + " se ha unido al grupo " + grupoId);

        } else {
            throw new RuntimeException("El usuario ya es miembro del grupo: " + grupoId);
        }

    }
}
