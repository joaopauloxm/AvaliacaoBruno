package com.br.AvaliacaoBruno.resources;

import com.br.AvaliacaoBruno.models.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PhotoResource {
    @GET("photos")
    Call<List<Photo>> get();

    @POST("photos")
    Call<Photo> post(@Body Photo photo);

    @PUT("photos/{id}")
    Call<Photo> put(@Body Photo usuario, @Path("id") Long id);

    @DELETE("photos/{id}")
    Call<Void> delete(@Path("id") Long id);
}
