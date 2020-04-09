package Retrofit.Model.userModel;

import java.util.ArrayList;

public class Model
{
    private ArrayList<UserFragmentModel> userFragmentModels;

    public Model()
    {

    }

    public Model(ArrayList<UserFragmentModel> userFragmentModels)
    {
        this.userFragmentModels = userFragmentModels;
    }

    public ArrayList<UserFragmentModel> getUserFragmentModels() {
        return userFragmentModels;
    }

    public void setUserFragmentModels(ArrayList<UserFragmentModel> userFragmentModels) {
        this.userFragmentModels = userFragmentModels;
    }
}
