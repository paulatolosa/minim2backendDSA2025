package services;

import services.GrupoService;
import services.DTOs.Grupo;

import manager.GrupoManagerImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import services.DTOs.CoinsResponse;
import services.DTOs.ItemInventario;
import services.DTOs.MessageResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/grupo", description = "Servicios de grupos")
@Path("/grupo")
public class GrupoService {

    private final GrupoManagerImpl grupoManager;

    public GrupoService() {
        this.grupoManager = GrupoManagerImpl.getInstance();
    }

    // T2: Nueva ruta en el backend que proporcione la lista de grupos
    // endpoint GET GRUPOS --> android(GrupoService): @GET("/v1/grupo/grupos")
    @GET
    @Path("/grupos")
    @ApiOperation(value = "Obtener todos los grupos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Grupo.class, responseContainer = "List") //
    })
    @Produces(MediaType.APPLICATION_JSON)

    public Response getGrupos() {
        List<Grupo> grupos = grupoManager.getGrupos();

        GenericEntity<List<Grupo>> entity = new GenericEntity<List<Grupo>>(grupos) {};

        return Response.status(200)
                .entity(entity)
                .build();
    }


    // T3. Nueva ruta en el backend para añadirse a un grupo
    // endpoint JOIN GRUPO --> android(GrupoService): @POST("/v1/grupo/join/{id}")
    @POST
    @Path("/join/{grupoId}")
    @ApiOperation(value = "Unirse a un grupo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario se ha unido al grupo correctamente"),
            @ApiResponse(code = 400, message = "Error al unirse al grupo", response = MessageResponse.class)
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response joinGrupo(@PathParam("grupoId") String grupoId, String username) {
        try {
            grupoManager.joinGrupo(grupoId, username);

            return Response.status(200)
                    .build();

        } catch (RuntimeException e) {

            return Response.status(400)
                    .entity(new MessageResponse(e.getMessage()))
                    .build();
        }
    }

}
