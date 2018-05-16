package com.example.lenovo.cardlessatm;

/**
 * Created by LENOVO on 11-05-2018.
 */
        import android.app.Activity;

        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;
        import android.widget.ArrayAdapter;
        import android.widget.AdapterView;

        import java.util.List;


public class UserAccountsList extends ArrayAdapter<Userdetails> {

    private Activity context;
    private List<Userdetails> userlist;
    public UserAccountsList(Activity context,List<Userdetails> userlist){

        super(context,R.layout.activity_user_accounts_list,userlist);
        this.context=context;
        this.userlist=userlist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.activity_user_accounts_list,null,true);
        TextView textViewAccount=(TextView)listViewItem.findViewById(R.id.textViewAccount);
        //TextView textViewName=(TextView)listViewItem.findViewById(R.id.textViewName);
        Userdetails u=userlist.get(position);
        textViewAccount.setText(String.valueOf(u.getAcc_no()));
        //textViewName.setText(u.getName());
        return listViewItem;

    }
}

