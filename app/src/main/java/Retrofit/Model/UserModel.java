package Retrofit.Model;

import java.util.ArrayList;
import Retrofit.Model.userModel.UserFragmentModel;

public class UserModel
{
    private ArrayList<PostModel> postModels;
    private ArrayList<CommentModel> commentModels;
    private ArrayList<AlbumModel> albumModels;
    private ArrayList<TodoModel> todoModels;
    private ArrayList<UserFragmentModel> userFragmentModels;
    private ArrayList<PhotoModel> photoModels;

    public UserModel()
    {

    }

    public UserModel(ArrayList<PostModel> postModels, ArrayList<CommentModel> commentModels, ArrayList<AlbumModel> albumModels, ArrayList<TodoModel> todoModels, ArrayList<UserFragmentModel> userFragmentModels, ArrayList<PhotoModel> photoModels)
    {
        this.postModels = postModels;
        this.commentModels = commentModels;
        this.albumModels = albumModels;
        this.todoModels = todoModels;
        this.userFragmentModels = userFragmentModels;
        this.photoModels = photoModels;
    }

    public ArrayList<PostModel> getPostModels() {
        return postModels;
    }

    public void setPostModels(ArrayList<PostModel> postModels) {
        this.postModels = postModels;
    }

    public ArrayList<CommentModel> getCommentModels() {
        return commentModels;
    }

    public void setCommentModels(ArrayList<CommentModel> commentModels) {
        this.commentModels = commentModels;
    }

    public ArrayList<AlbumModel> getAlbumModels() {
        return albumModels;
    }

    public void setAlbumModels(ArrayList<AlbumModel> albumModels) {
        this.albumModels = albumModels;
    }

    public ArrayList<TodoModel> getTodoModels() {
        return todoModels;
    }

    public void setTodoModels(ArrayList<TodoModel> todoModels) {
        this.todoModels = todoModels;
    }

    public ArrayList<UserFragmentModel> getUserFragmentModels() {
        return userFragmentModels;
    }

    public void setUserFragmentModels(ArrayList<UserFragmentModel> userFragmentModels) {
        this.userFragmentModels = userFragmentModels;
    }

    public ArrayList<PhotoModel> getPhotoModels() {
        return photoModels;
    }

    public void setPhotoModels(ArrayList<PhotoModel> photoModels) {
        this.photoModels = photoModels;
    }
}
