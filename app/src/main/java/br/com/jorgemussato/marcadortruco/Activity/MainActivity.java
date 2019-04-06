package br.com.jorgemussato.marcadortruco.Activity;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.jorgemussato.marcadortruco.Adapters.HistoricoAdapter;
import br.com.jorgemussato.marcadortruco.Models.History;
import br.com.jorgemussato.marcadortruco.R;

public class MainActivity extends AppCompatActivity implements Serializable {

    private final int _HISTORYREQUEST = 1234;

    private List<History> mList = new ArrayList<>();

    private ImageButton btn_plus_t1;
    private ImageButton btn_less_t1;
    private TextView lbl_pontos_t1;

    private ImageButton btn_plus_t2;
    private ImageButton btn_less_t2;
    private TextView lbl_pontos_t2;

    private int pts_t1 = 0;
    private int pts_t2 = 0;

    private void startView(){
        setContentView(R.layout.activity_main);

        lbl_pontos_t1 = findViewById(R.id.lbl_pontos_time1);
        btn_plus_t1 = findViewById(R.id.btn_plus_time1);
        btn_less_t1 = findViewById(R.id.btn_less_time1);

        lbl_pontos_t2 = findViewById(R.id.lbl_pontos_time2);
        btn_plus_t2 = findViewById(R.id.btn_plus_time2);
        btn_less_t2 = findViewById(R.id.btn_less_time2);

        lbl_pontos_t1.setText(String.valueOf(pts_t1));
        lbl_pontos_t2.setText(String.valueOf(pts_t2));

        findViewById(R.id.btn_historico).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, HistoricoActivity.class);
                i.putExtra("history", (Serializable) mList);

//                startActivity(i);
                startActivityForResult(i, _HISTORYREQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case _HISTORYREQUEST:
                if(resultCode == RESULT_OK){
                    mList = (List<History>) data.getSerializableExtra("lista");
                }
        }

    }

    private void alerta(String mensagem){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setMessage(mensagem);
        dialogo.show();
    }

    private void reset(){
        pts_t1 = 0;
        pts_t2 = 0;
        lbl_pontos_t1.setText(String.valueOf(pts_t1));
        lbl_pontos_t2.setText(String.valueOf(pts_t2));
    }

    //Função on click do botão + time 1
    private View.OnClickListener click_plus_t1 = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pts_t1++;
            lbl_pontos_t1.setText(String.valueOf(pts_t1));
            if(pts_t1 == 12){
                alerta("Nós ganhamos!");
                mList.add(new History(pts_t1, pts_t2));
                reset();
            }
        }
    };

    //Função on click do botão - time 1
    private View.OnClickListener click_less_t1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(pts_t1 > 0) {
                pts_t1--;
                lbl_pontos_t1.setText(String.valueOf(pts_t1));
            }
        }
    };

    //Função on click do botão + time 2
    private View.OnClickListener click_plus_t2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pts_t2++;
            lbl_pontos_t2.setText(String.valueOf(pts_t2));
            if(pts_t2 == 12){
                alerta("Eles ganharam!");
                mList.add(new History(pts_t1, pts_t2));
                reset();
            }
        }
    };

    //Função on click do botão - time 2
    private View.OnClickListener click_less_t2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(pts_t2 > 0) {
                pts_t2--;
                lbl_pontos_t2.setText(String.valueOf(pts_t2));
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startView();

        //Inserir a função de click no botão + e - dos times 1 e 2
        btn_plus_t1.setOnClickListener(click_plus_t1);
        btn_less_t1.setOnClickListener(click_less_t1);
        btn_plus_t2.setOnClickListener(click_plus_t2);
        btn_less_t2.setOnClickListener(click_less_t2);




    }



}
