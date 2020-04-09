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

import Retrofit.Model.userModel.UserFragmentModel;
import Retrofit.retrofit.Helper;
import Retrofit.retrofit.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class userFragment extends Fragment
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
        view = inflater.inflate(R.layout.user_fragment , container , false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        initiaaViews();
        initial_R_U();
    }

    private void initiaaViews()
    {
        recyclerView = view.findViewById(R.id.r_v_user);
        linearLayoutManager = new LinearLayoutManager(getContext() , RecyclerView.VERTICAL , false);
        recyclerView.setLayoutManager(linearLayoutManager);
        dividerItemDecoration = new DividerItemDecoration(getContext() , DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        rotateLoading = view.findViewById(R.id.rotateloading_user);
    }

    private void initial_R_U()
    {
        retrofit = new Retrofit
                .Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        helper = retrofit.create(Helper.class);

        rotateLoading.start();
        getUsers();
    }

    private void getUsers()
    {
       Call<ArrayList<UserFragmentModel>> call = helper.getUser();
       call.enqueue(new Callback<ArrayList<UserFragmentModel>>()
       {
           @Override
           public void onResponse(Call<ArrayList<UserFragmentModel>> call, Response<ArrayList<UserFragmentModel>> response)
           {
               if (response.isSuccessful())
               {
                   rotateLoading.stop();
                  ArrayList<UserFragmentModel> fragmentModels = response.body();
                  getAdapter adapter = new getAdapter(fragmentModels);
                  recyclerView.setAdapter(adapter);
               }
           }

           @Override
           public void onFailure(Call<ArrayList<UserFragmentModel>> call, Throwable t)
           {
               rotateLoading.stop();
               Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });
    }

    private class getAdapter extends RecyclerView.Adapter<getAdapter.getVH>
    {
        ArrayList<UserFragmentModel> userFragmentModels;

        public getAdapter(ArrayList<UserFragmentModel> userFragmentModels)
        {
            this.userFragmentModels = userFragmentModels;
        }


        @NonNull
        @Override
        public getVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View item_view = LayoutInflater.from(getContext()).inflate(R.layout.item_user , parent , false);
            return new getVH(item_view);
        }

        @Override
        public void onBindViewHolder(@NonNull getVH holder, int position)
        {
            UserFragmentModel fragmentModel = userFragmentModels.get(position);
            holder.text_id.setText(String.valueOf(fragmentModel.getId()));
            holder.text_name.setText(fragmentModel.getName());
            holder.text_user_name.setText(fragmentModel.getUsername());
            holder.text_email.setText(fragmentModel.getEmail());
            holder.text_street.setText(fragmentModel.getAddress().getStreet());
            holder.text_suite.setText(fragmentModel.getAddress().getSuite());
            holder.text_city.setText(fragmentModel.getAddress().getCity());
            holder.text_zipcode.setText(fragmentModel.getAddress().getZipcode());
            holder.text_lat.setText(fragmentModel.getAddress().getGeo().getLat());
            holder.text_lng.setText(fragmentModel.getAddress().getGeo().getLng());
            holder.text_phone.setText(fragmentModel.getPhone());
            holder.text_website.setText(fragmentModel.getWebsite());
            holder.text_name_company.setText(fragmentModel.getCompany().getName());
            holder.text_catchPhrase.setText(fragmentModel.getCompany().getCatchPhrase());
            holder.text_bs.setText(fragmentModel.getCompany().getBs());
        }

        @Override
        public int getItemCount()
        {
            return userFragmentModels.size();
        }

        private class getVH extends RecyclerView.ViewHolder
        {
            private TextView text_id,text_name,text_user_name,text_email;
            private TextView text_street,text_suite,text_city,text_zipcode;
            private TextView text_lat,text_lng,text_phone,text_website;
            private TextView text_name_company,text_catchPhrase,text_bs;

            public getVH(@NonNull View itemView)
            {
                super(itemView);

                text_id = itemView.findViewById(R.id.txt_id_item_user);
                text_name = itemView.findViewById(R.id.txt_name_item_user);
                text_user_name = itemView.findViewById(R.id.txt_user_name_item_user);
                text_email = itemView.findViewById(R.id.txt_email_item_user);
                text_street = itemView.findViewById(R.id.txt_street_item_user);
                text_suite = itemView.findViewById(R.id.txt_suite_item_user);
                text_city = itemView.findViewById(R.id.txt_city_item_user);
                text_zipcode = itemView.findViewById(R.id.txt_zip_code_item_user);
                text_lat = itemView.findViewById(R.id.txt_lat_item_user);
                text_lng = itemView.findViewById(R.id.txt_lng_item_user);
                text_phone = itemView.findViewById(R.id.txt_phone_item_user);
                text_website = itemView.findViewById(R.id.txt_website_item_user);
                text_name_company = itemView.findViewById(R.id.txt_company_name_item_user);
                text_catchPhrase = itemView.findViewById(R.id.txt_catchPhrase_item_user);
                text_bs = itemView.findViewById(R.id.txt_bs_item_user);

            }
        }
    }
}
