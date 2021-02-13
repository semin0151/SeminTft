package com.example.mytft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mytft.Data.Result;
import com.example.mytft.Data.SummonerID;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView tv_id;
    private TextView tv_tier;
    private TextView tv_game;
    private TextView tv_win;
    private ImageView iv_tier;
    private EditText et_id;
    private Button btn_id;

    private RetrofitClient retrofitClient;
    private RetrofitInterface retrofitInterface;

    private String api_key = "RGAPI-5ec654df-67e8-4ddc-8058-d8200a7a8579";
    private String myID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_id = (TextView)findViewById(R.id.tv_id);
        tv_tier = (TextView)findViewById(R.id.tv_tier);
        tv_game = (TextView)findViewById(R.id.tv_game);
        tv_win = (TextView)findViewById(R.id.tv_win);
        iv_tier = (ImageView) findViewById(R.id.iv_tier);
        et_id = (EditText)findViewById(R.id.et_id);
        btn_id = (Button)findViewById(R.id.btn_id);

        btn_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallRetrofit(et_id.getText().toString());
            }
        });
    }

    public void CallRetrofit(String summonerName){
        try{

            retrofitClient = RetrofitClient.getInstance();
            retrofitInterface = RetrofitClient.getRetrofitInterface();

            retrofitInterface.getID(summonerName, api_key).enqueue(new Callback<SummonerID>() {
                @Override
                public void onResponse(Call<SummonerID> call, Response<SummonerID> response) {
                    SummonerID summonerID = response.body();
                    myID = summonerID.getId();

                    retrofitInterface.getInf(myID,api_key).enqueue(new Callback<ArrayList<Result>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Result>> call, Response<ArrayList<Result>> response) {

                            if(response.body().size() != 0) {
                                Result result = response.body().get(0);
                                tv_id.setText(result.getSummonerName());
                                tv_tier.setText(result.getTier() + " " + result.getRank() + " " + result.getLeaguePoints() + "LP");
                                tv_game.setText("전체 게임 수 : " + Integer.toString(result.getWins() + result.getLosses()));
                                tv_win.setText("승리 : " + result.getWins());
                                setImageTier(result.getTier());
                            }
                            else {
                                tv_id.setText(et_id.getText());
                                tv_game.setText("전적 정보가 없습니다.");
                                iv_tier.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.provisional));
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<Result>> call, Throwable t) {
                            tv_id.setText(t.getMessage());
                        }
                    });

                }
                @Override
                public void onFailure(Call<SummonerID> call, Throwable t) {
                    tv_id.setText(t.getMessage());
                }
            });

        }
        catch (Exception e){
            tv_id.setText(e.getMessage());
        }
    }
    public void setImageTier(String tier){
        switch (tier){
            case "IRON" :
                iv_tier.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.iron_1));
                break;
            case "BRONZE" :
                iv_tier.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.bronze_1));
                break;
            case "SILVER" :
                iv_tier.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.silver_1));
                break;
            case "GOLD" :
                iv_tier.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.gold_1));
                break;
            case "PLATINUM" :
                iv_tier.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.platinum_1));
                break;
            case "DIAMOND" :
                iv_tier.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.diamond_1));
                break;
            case "MASTER" :
                iv_tier.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.master_1));
                break;
            case "GRANDMASTER" :
                iv_tier.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.grandmaster_1));
                break;
            case "CHALLENGER" :
                iv_tier.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.challenger_1));
                break;

        }

    }
}

