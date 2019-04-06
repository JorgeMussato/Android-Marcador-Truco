package br.com.jorgemussato.marcadortruco.API;

import java.util.List;

import br.com.jorgemussato.marcadortruco.Models.History;
import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    @GET("/v2/5bd71bf9350000c039fd7f5d")
    public Call<List<History>> getHistory();

}
