package Retrofit.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;

import Retrofit.Model.PhotoModel;
import Retrofit.retrofit.Helper;
import Retrofit.retrofit.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class photoFragment extends Fragment
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
        view = inflater.inflate(R.layout.photo_fragment , container , false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        initiaaViews();
        initial_R_Photo();
    }

    private void initiaaViews()
    {
        recyclerView = view.findViewById(R.id.r_v_photo);
        linearLayoutManager = new LinearLayoutManager(getContext() , RecyclerView.VERTICAL , false);
        recyclerView.setLayoutManager(linearLayoutManager);
        dividerItemDecoration = new DividerItemDecoration(getContext() , DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        rotateLoading = view.findViewById(R.id.rotateloading_photo);
    }

    private void initial_R_Photo()
    {
        retrofit = new Retrofit
                .Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        helper = retrofit.create(Helper.class);

        rotateLoading.start();
        getPhotos();
    }

    private void getPhotos()
    {
       Call<ArrayList<PhotoModel>> call = helper.getPhoto();
       call.enqueue(new Callback<ArrayList<PhotoModel>>()
       {
           @Override
           public void onResponse(Call<ArrayList<PhotoModel>> call, Response<ArrayList<PhotoModel>> response)
           {
               if (response.isSuccessful())
               {
                   rotateLoading.stop();
                  ArrayList<PhotoModel> photoModels = response.body();
                  getAdapter adapter = new getAdapter(photoModels);
                  recyclerView.setAdapter(adapter);
               }
           }

           @Override
           public void onFailure(Call<ArrayList<PhotoModel>> call, Throwable t)
           {
               rotateLoading.stop();
               Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });
    }

    private class getAdapter extends RecyclerView.Adapter<getAdapter.getVH>
    {
        ArrayList<PhotoModel> photoModels;

        public getAdapter(ArrayList<PhotoModel> photoModels)
        {
            this.photoModels = photoModels;
        }


        @NonNull
        @Override
        public getVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View item_view = LayoutInflater.from(getContext()).inflate(R.layout.item_photo , parent , false);
            return new getVH(item_view);
        }

        @Override
        public void onBindViewHolder(@NonNull getVH holder, int position)
        {
           PhotoModel model = photoModels.get(position);
           holder.text_id.setText(String.valueOf(model.getId()));
           holder.text_title.setText(model.getTitle());
            Picasso.get()
                    .load(model.getThumbnailUrl())
                    .into(holder.imageView);

        }

        @Override
        public int getItemCount()
        {
            return photoModels.size();
        }

        private class getVH extends RecyclerView.ViewHolder
        {
            private ImageView imageView;
            private TextView text_id,text_title;
            public getVH(@NonNull View itemView)
            {
                super(itemView);
                imageView = itemView.findViewById(R.id.img_view_item_photo);
                text_id = itemView.findViewById(R.id.txt_id_item_photo);
                text_title = itemView.findViewById(R.id.txt_title_item_photo);
            }
        }
    }
}
