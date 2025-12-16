package manager;

import services.DTOs.Grupo;

import java.util.List;

public interface GrupoManager {

    List<Grupo> getGrupos();

    void joinGrupo(String grupoId, String username);

}
