package Retrofit.retrofit;

import java.util.ArrayList;

import Retrofit.Model.AlbumModel;
import Retrofit.Model.CommentModel;
import Retrofit.Model.PhotoModel;
import Retrofit.Model.PostModel;
import Retrofit.Model.TodoModel;
import Retrofit.Model.userModel.UserFragmentModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Helper
{
  //start getPost

    @GET("posts")
    public Call<ArrayList<PostModel>> getPost();

  //end getPost

  //start getComment

    @GET("comments")
    public Call<ArrayList<CommentModel>> getComment();

  //end getComment

  //start getAlbum

    @GET("albums")
    public Call<ArrayList<AlbumModel>> getAlbum();

  //end getAlbum

  //start getTodo

    @GET("todos")
    public Call<ArrayList<TodoModel>> getTodo();

  //end getTodo

  //start getUser

    @GET("users")
    public Call<ArrayList<UserFragmentModel>> getUser();

  //end getUser

  //start getPhoto

    @GET("photos")
    public Call<ArrayList<PhotoModel>> getPhoto();

  //end getPhoto

}
