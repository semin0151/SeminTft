package com.example.mytft;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mytft.Data.Result;
import com.example.mytft.Data.SummonerID;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView tv_inf;
    private EditText et_id;
    private Button btn_id;

    private RetrofitClient retrofitClient;
    private RetrofitInterface retrofitInterface;

    private String api_key = "RGAPI-384c5823-53e1-4937-b4b5-87ffd641f373";
    private String myID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_inf = (TextView)findViewById(R.id.tv_inf);
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
                                String str = result.getSummonerName() + "\n" +
                                        result.getTier() + " " + result.getRank() + " " + result.getLeaguePoints() + "LP" + "\n" +
                                        "전체 게임 수 : " + Integer.toString(result.getWins() + result.getLosses()) + "\n" +
                                        "승리 : " + result.getWins();
                                tv_inf.setText(str);
                            }
                            else tv_inf.setText("전적 정보가 없습니다.");
                        }

                        @Override
                        public void onFailure(Call<ArrayList<Result>> call, Throwable t) {
                            tv_inf.setText(t.getMessage());
                        }
                    });

                }
                @Override
                public void onFailure(Call<SummonerID> call, Throwable t) {
                    tv_inf.setText(t.getMessage());
                }
            });
        }
        catch (Exception e){
            tv_inf.setText(e.getMessage());
        }
    }
}

