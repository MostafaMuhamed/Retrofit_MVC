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
import Retrofit.Model.TodoModel;
import Retrofit.retrofit.Helper;
import Retrofit.retrofit.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class todoFragment extends Fragment
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
        view = inflater.inflate(R.layout.todo_fragment , container , false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        initiaaViews();
        initial_R_T();
    }

    private void initiaaViews()
    {
        recyclerView = view.findViewById(R.id.r_v_todo);
        linearLayoutManager = new LinearLayoutManager(getContext() , RecyclerView.VERTICAL , false);
        recyclerView.setLayoutManager(linearLayoutManager);
        dividerItemDecoration = new DividerItemDecoration(getContext() , DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        rotateLoading = view.findViewById(R.id.rotateloading_todo);
    }

    private void initial_R_T()
    {
        retrofit = new Retrofit
                .Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        helper = retrofit.create(Helper.class);

        rotateLoading.start();
        getTodos();
    }

    private void getTodos()
    {
        Call<ArrayList<TodoModel>> call = helper.getTodo();
        call.enqueue(new Callback<ArrayList<TodoModel>>()
        {
            @Override
            public void onResponse(Call<ArrayList<TodoModel>> call, Response<ArrayList<TodoModel>> response)
            {
                if (response.isSuccessful())
                {
                   rotateLoading.stop();
                   ArrayList<TodoModel> todoModels = response.body();
                   getAdapter adapter = new getAdapter(todoModels);
                   recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TodoModel>> call, Throwable t)
            {
                rotateLoading.stop();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class getAdapter extends RecyclerView.Adapter<getAdapter.getVH>
    {
        ArrayList<TodoModel> todoModels;

        public getAdapter(ArrayList<TodoModel> todoModels)
        {
            this.todoModels = todoModels;
        }

        @NonNull
        @Override
        public getVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View item_view = LayoutInflater.from(getContext()).inflate(R.layout.item_todo , parent , false);
            return new getVH(item_view);
        }

        @Override
        public void onBindViewHolder(@NonNull getAdapter.getVH holder, int position)
        {
           TodoModel model = todoModels.get(position);
           holder.text_user_id.setText(String.valueOf(model.getUserId()));
           holder.text_id.setText(String.valueOf(model.getId()));
           holder.text_title.setText(model.getTitle());
           holder.text_completed.setText(String.valueOf(model.getCompleted()));
        }

        @Override
        public int getItemCount()
        {
            return todoModels.size();
        }

        private class getVH extends RecyclerView.ViewHolder
        {
            private TextView text_user_id,text_id,text_title,text_completed;

            public getVH(@NonNull View itemView)
            {
                super(itemView);

                text_user_id = itemView.findViewById(R.id.txt_user_id_item_todo);
                text_id = itemView.findViewById(R.id.txt_id_item_todo);
                text_title = itemView.findViewById(R.id.txt_title_item_todo);
                text_completed = itemView.findViewById(R.id.txt_completed_item_todo);

            }
        }
    }
}
