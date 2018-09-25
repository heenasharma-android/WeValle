package com.OldScreens;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.albaniancircle.R;
import com.models.Profession;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditProfile extends AppCompatActivity {
    public static Activity fa;
    List<Profession> professions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        fa = this;
        ButterKnife.bind(this);
        if (getIntent().getParcelableArrayListExtra("profession")==null){

        }
        else {
            professions=getIntent().getParcelableArrayListExtra("profession");
            tvProfession.setText(professions.get(0).getProfession());
        }
    }

    @OnClick(R.id.tv_profession)
    void profession(){
        Intent intent=new Intent(getApplicationContext(),ProfessionActivity.class);
        startActivity(intent);
    }

    @BindView(R.id.tv_profession)
    TextView tvProfession;

    @OnClick(R.id.tv_cancel)
    void tvCancel(){
        finish();
    }

}
