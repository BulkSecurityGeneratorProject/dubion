package com.dubion.service.DiscogsAPI;

import com.dubion.service.dto.DiscogsAPI.AlbumDTO;
import com.dubion.service.dto.DiscogsAPI.DiscogsApiDTO;
import org.springframework.stereotype.Repository;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.Map;

@Repository
public interface DiscogsApiRepository {
    @GET("releases/{discoId}")
    Call<DiscogsApiDTO> getRelease(@Path("discoId") int discoId);

    @GET("database/search?&per_page=2000&page=1")
    Call<AlbumDTO> findByReleaseTitle(@Query("release_title") String release_title, @Query("token") String token);
    //FindByArtist

//    query
//    string (optional) Example: nirvana
//    Your search query

    //Good Morning;



    //GET https://api.discogs.com/database/search?release_title=nevermind&artist=nirvana&per_page=3&page=1

    public static String url = "https://api.discogs.com/";
    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
