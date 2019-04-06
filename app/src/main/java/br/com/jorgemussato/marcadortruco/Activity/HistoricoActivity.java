package br.com.jorgemussato.marcadortruco.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

import br.com.jorgemussato.marcadortruco.API.API;
import br.com.jorgemussato.marcadortruco.Adapters.HistoricoAdapter;
import br.com.jorgemussato.marcadortruco.Interface.AdaptorPositionOnClickListener;
import br.com.jorgemussato.marcadortruco.Models.History;
import br.com.jorgemussato.marcadortruco.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoricoActivity extends AppCompatActivity implements AdaptorPositionOnClickListener {

    private Toolbar mToolbar;
    private List<History> mList;
    private HistoricoAdapter mAdapter;
    private RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

//        if(getIntent().hasExtra("history")){
//            mList = (List<History>) getIntent().getSerializableExtra("history");
//        }else{
//            mList = new ArrayList<>();
//        }

        mRecycler = findViewById(R.id.rv_history);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(manager);
        mRecycler.setHasFixedSize(true);

        loadData();

//        mAdapter = new HistoricoAdapter(this, mList);
//        mAdapter.setAdaptorPositionOnClickListener(this);
//        mRecycler.setAdapter(mAdapter);

        //Insere toolbar na activity
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //Coloca o botão de home
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    public void validacao(){
        if(mList == null || mList.isEmpty()){
            findViewById(R.id.lbl_message).setVisibility(View.VISIBLE);
            mRecycler.setVisibility(View.GONE);
        }else {
            findViewById(R.id.lbl_message).setVisibility(View.INVISIBLE);
            mRecycler.setVisibility(View.VISIBLE);
        }
    }

    private void loadData(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.mocky.io").addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);
        Call<List<History>> call = api.getHistory();

        call.enqueue(new Callback<List<History>>() {
            @Override
            public void onResponse(Call<List<History>> call, Response<List<History>> response) {
                if(response.isSuccessful()){
                    mList = response.body();

                    mAdapter = new HistoricoAdapter(HistoricoActivity.this, mList);
                    mAdapter.setAdaptorPositionOnClickListener(HistoricoActivity.this);
                    mRecycler.setAdapter(mAdapter);

                    validacao();
                }
                else {
                    Toast.makeText(HistoricoActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<History>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        //Criar o intent
        Intent i = new Intent();

        //Inserir o valor da lista atualizada no intent
        i.putExtra("lista", (Serializable) mAdapter.mList);

        //passa a lista atualizada
        setResult(RESULT_OK, i);
        finish();

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setAdaptorPositionOnClickListener(View view, final int position) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Excluir");
        alertBuilder.setMessage("Deseja realmente deletar este histórico?");
        alertBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAdapter.deleteItem(position);
            }
        });

        alertBuilder.setNegativeButton("Não", null);
        alertBuilder.show();


    }
}
