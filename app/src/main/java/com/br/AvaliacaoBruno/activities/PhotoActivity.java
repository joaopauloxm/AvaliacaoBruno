package com.br.AvaliacaoBruno.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.br.AvaliacaoBruno.R;
import com.br.AvaliacaoBruno.adapters.PhotoAdapter;
import com.br.AvaliacaoBruno.bootstrap.APIClient;
import com.br.AvaliacaoBruno.models.Photo;
import com.br.AvaliacaoBruno.resources.PhotoResource;

import java.util.ArrayList;
import java.util.List;

import com.br.AvaliacaoBruno.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoActivity extends AppCompatActivity implements PhotoAdapter.ItemClickListener {

    private RecyclerView lstPhotos;
    private PhotoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Photo> photos = new ArrayList<>();

    private TextView txtTitle;
    private TextView txtUrl;
    private TextView txtThumbnailUrl;

    private boolean edicao = false;
    private Photo photo = new Photo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        loadPhotosFromService();

        lstPhotos = findViewById(R.id.lstPhotos);
        txtTitle = findViewById(R.id.txtTitle);
        txtUrl = findViewById(R.id.txtUrl);
        txtThumbnailUrl = findViewById(R.id.txtThumbnailUrl);

        //lstPhotos.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        lstPhotos.setLayoutManager(layoutManager);

        adapter = new PhotoAdapter(photos);
        adapter.setClickListener(this);
        lstPhotos.setAdapter(adapter);
    }

    private void loadPhotosFromService() {
        APIClient.getClient().create(PhotoResource.class).get().enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                photos.clear();
                photos.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void savePhoto(final Photo photo) {
        PhotoResource photoResource = APIClient.getClient().create(PhotoResource.class);
        if (photo.getId() == null)
            photoResource.post(photo).enqueue(new Callback<Photo>() {
                @Override
                public void onResponse(Call<Photo> call, Response<Photo> response) {
                    Toast.makeText(getApplicationContext(),
                            String.format("Photo saved successfully!" +
                                            "\nId: %d" +
                                            "\nTitle: %s" +
                                            "\nUrl: %s" +
                                            "\nThumbnail url: %s" +
                                            "\n",
                                    response.body().getId(),
                                    response.body().getTitle(),
                                    response.body().getUrl(),
                                    response.body().getThumbnailUrl()
                            ), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Photo> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        else
            photoResource.put(photo, photo.getId()).enqueue(new Callback<Photo>() {
                @Override
                public void onResponse(Call<Photo> call, Response<Photo> response) {
                    Toast.makeText(getApplicationContext(),
                            String.format("Photo saved successfully!" +
                                            "\nId: %d" +
                                            "\nTitle: %s" +
                                            "\nUrl: %s" +
                                            "\nThumbnail url: %s" +
                                            "\n",
                                    response.body().getId(),
                                    response.body().getTitle(),
                                    response.body().getUrl(),
                                    response.body().getThumbnailUrl()
                            ), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Photo> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
    }

    public void deletePhoto(Photo photo) {
        APIClient.getClient().create(PhotoResource.class).delete(photo.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getApplicationContext(), "Photo deleted successfully!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void atualizar(View view) {
        limpar();
        loadPhotosFromService();
    }

    public void salvar(View view) {
        String title = txtTitle.getText().toString();
        String url = txtUrl.getText().toString();
        String thumbnailUrl = txtThumbnailUrl.getText().toString();
        photo.setTitle(title);
        photo.setUrl(url);
        photo.setThumbnailUrl(thumbnailUrl);
        savePhoto(photo);
        limpar();
    }


    @Override
    public void onItemClick(View view, int position) {
        edicao = true;
        photo = photos.get(position);
        txtTitle.setText(photo.getTitle());
        txtUrl.setText(photo.getUrl());
        txtThumbnailUrl.setText(photo.getThumbnailUrl());
    }

    private void limpar() {
        txtTitle.setText("");
        txtUrl.setText("");
        txtThumbnailUrl.setText("");
    }


    public void apagar(View view) {

        if (edicao && photo.getId() != null) {
            deletePhoto(photo);
            limpar();
        } else
            Toast.makeText(this, "Selecione uma foto para ser apagada",
                    Toast.LENGTH_SHORT).show();
    }

    public void alterar(View view) {
        limpar();
        txtTitle.requestFocus();
        photo = new Photo();
        edicao = false;
    }
}
