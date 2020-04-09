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

import Retrofit.Model.CommentModel;
import Retrofit.retrofit.Helper;
import Retrofit.retrofit.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class commentFragment extends Fragment
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
        view = inflater.inflate(R.layout.comment_fragment, container , false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        initiaaViews();
        initial_R_C();
    }

    private void initiaaViews()
    {
        recyclerView = view.findViewById(R.id.r_v_comment);
        linearLayoutManager = new LinearLayoutManager(getContext() , RecyclerView.VERTICAL , false);
        recyclerView.setLayoutManager(linearLayoutManager);
        dividerItemDecoration = new DividerItemDecoration(getContext() , DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        rotateLoading = view.findViewById(R.id.rotateloading_comment);
    }

    private void initial_R_C()
    {
        retrofit = new Retrofit
                .Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        helper = retrofit.create(Helper.class);

        rotateLoading.start();
        getComments();
    }

    private void getComments()
    {
      Call<ArrayList<CommentModel>> call = helper.getComment();
      call.enqueue(new Callback<ArrayList<CommentModel>>()
      {
          @Override
          public void onResponse(Call<ArrayList<CommentModel>> call, Response<ArrayList<CommentModel>> response)
          {
              if (response.isSuccessful())
              {
                  rotateLoading.stop();
                 ArrayList<CommentModel> commentModels = response.body();
                 getAdapter adapter = new getAdapter(commentModels);
                 recyclerView.setAdapter(adapter);
              }
          }

          @Override
          public void onFailure(Call<ArrayList<CommentModel>> call, Throwable t)
          {
              rotateLoading.stop();
              Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
          }
      });
    }

    private class getAdapter extends RecyclerView.Adapter<getAdapter.getVH>
    {
        ArrayList<CommentModel> commentModels;

        public getAdapter(ArrayList<CommentModel> commentModels)
        {
            this.commentModels = commentModels;
        }

        @NonNull
        @Override
        public getVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View item_view = LayoutInflater.from(getContext()).inflate(R.layout.item_comment , parent , false);
            return new getVH(item_view);
        }

        @Override
        public void onBindViewHolder(@NonNull getVH holder, int position)
        {
           CommentModel model = commentModels.get(position);
           holder.text_post_id.setText(String.valueOf(model.getPostId()));
           holder.text_id.setText(String.valueOf(model.getId()));
           holder.text_name.setText(model.getName());
           holder.text_email.setText(model.getEmail());
           holder.text_body.setText(model.getBody());
        }

        @Override
        public int getItemCount()
        {
            return commentModels.size();
        }

        private class getVH extends RecyclerView.ViewHolder
        {
            private TextView text_post_id,text_id,text_name,text_email,text_body;

            public getVH(@NonNull View itemView)
            {
                super(itemView);

                text_post_id = itemView.findViewById(R.id.txt_post_id_item_comment);
                text_id = itemView.findViewById(R.id.txt_id_item_comment);
                text_name = itemView.findViewById(R.id.txt_name_item_comment);
                text_email = itemView.findViewById(R.id.txt_email_item_comment);
                text_body = itemView.findViewById(R.id.txt_body_item_comment);
            }
        }
    }
}
