package Retrofit.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;

import Retrofit.Model.AlbumModel;
import Retrofit.Model.PostModel;
import Retrofit.retrofit.Helper;
import Retrofit.retrofit.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class albumFragment extends Fragment
{
    private View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private Retrofit retrofit;
    private Helper helper;
    private RotateLoading rotateLoading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.album_fragment, container , false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        initiaaViews();
        initial_R_A();

    }

    private void initiaaViews()
    {
        recyclerView = view.findViewById(R.id.r_v_album);
        linearLayoutManager = new LinearLayoutManager(getContext() , RecyclerView.VERTICAL , false);
        recyclerView.setLayoutManager(linearLayoutManager);
        dividerItemDecoration = new DividerItemDecoration(getContext() , DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        rotateLoading = view.findViewById(R.id.rotateloading_album);
    }

    private void initial_R_A()
    {
        retrofit = new Retrofit
                .Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        helper = retrofit.create(Helper.class);

        rotateLoading.start();
        getAlbums();
    }

    private void getAlbums()
    {
      Call<ArrayList<AlbumModel>> call = helper.getAlbum();
      call.enqueue(new Callback<ArrayList<AlbumModel>>()
      {
          @Override
          public void onResponse(Call<ArrayList<AlbumModel>> call, Response<ArrayList<AlbumModel>> response)
          {
              rotateLoading.stop();
             ArrayList<AlbumModel> albumModel = response.body();
             getAdapter adapter = new getAdapter(albumModel);
             recyclerView.setAdapter(adapter);
          }

          @Override
          public void onFailure(Call<ArrayList<AlbumModel>> call, Throwable t)
          {
              rotateLoading.stop();
              Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
          }
      });
    }

    private class getAdapter extends RecyclerView.Adapter<getAdapter.getVH>
    {
        ArrayList<AlbumModel> albumModels;

        public getAdapter(ArrayList<AlbumModel> albumModels)
        {
            this.albumModels = albumModels;
        }

        @NonNull
        @Override
        public getVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View item_view = LayoutInflater.from(getContext()).inflate(R.layout.item_album , parent , false);
            return new getVH(item_view);
        }

        @Override
        public void onBindViewHolder(@NonNull getVH holder, int position)
        {
           AlbumModel model = albumModels.get(position);
           holder.text_user_id.setText(String.valueOf(model.getUserId()));
           holder.texxt_id.setText(String.valueOf(model.getId()));
           holder.text_title.setText(model.getTitle());
        }

        @Override
        public int getItemCount()
        {
            return albumModels.size();
        }

private class getVH extends RecyclerView.ViewHolder
{
    private TextView text_user_id,texxt_id,text_title;

     public getVH(@NonNull View itemView)
     {
         super(itemView);
         text_user_id = itemView.findViewById(R.id.txt_user_id_item_album);
         texxt_id = itemView.findViewById(R.id.txt_id_item_album);
         text_title = itemView.findViewById(R.id.txt_title_item_album);
     }
 }
    }
}
