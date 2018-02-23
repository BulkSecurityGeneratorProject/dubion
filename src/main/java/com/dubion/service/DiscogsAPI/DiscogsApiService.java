package com.dubion.service.DiscogsAPI;

import com.dubion.service.dto.DiscogsAPI.AlbumDTO;
import com.dubion.service.dto.DiscogsAPI.DiscogsApiDTO;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;


@Service
public class DiscogsApiService {
    public static final String token = "yqqlmpPsfiVPoXkvXpxNajivvHlAdPpNBrZqUQZt";
    static DiscogsApiRepository apiService = DiscogsApiRepository.retrofit.create(DiscogsApiRepository.class);

    public static DiscogsApiDTO getDisc(int id){
        DiscogsApiDTO movie = null;
        Call<DiscogsApiDTO> callMovie = apiService.getRelease(58963);
        System.out.println(callMovie);
        try {
            movie = callMovie.execute().body();
            System.out.println(movie);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movie;
    }
    public static AlbumDTO findByReleasetitle(String releaseTitle){
        AlbumDTO movie = null;
        Call<AlbumDTO> callMovie = apiService.findByReleaseTitle(releaseTitle,token);
        System.out.println(callMovie);
        try {
            movie = callMovie.execute().body();
            System.out.println(movie);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movie;
    }
    /*public static List<Movie> getTopRated(){
        Call<MovieResponse> call = apiService.getTopRatedMovies(apiKey);
        List<Movie> moviesList = null;
        try {
            moviesList = call.execute().body().results;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moviesList;
    }*/


}
