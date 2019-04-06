package br.com.jorgemussato.marcadortruco.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.jorgemussato.marcadortruco.Interface.AdaptorPositionOnClickListener;
import br.com.jorgemussato.marcadortruco.Models.History;
import br.com.jorgemussato.marcadortruco.R;

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.ViewHolder> {

    private Context mContext;
    public List<History> mList;
    private AdaptorPositionOnClickListener adapter;

    public HistoricoAdapter(Context context, List<History> list){
        this.mContext = context;
        this.mList = list;
    }

    public void setAdaptorPositionOnClickListener(AdaptorPositionOnClickListener click){
        adapter = click;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View holder = inflater.inflate(R.layout.item_historico, viewGroup , false);
        ViewHolder viewHolder = new ViewHolder(holder);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        History history = mList.get(i);

        viewHolder.lbl_time_vencedor.setText(history.pts_team1 < history.pts_team2 ? "Eles ganharam" : "Nós ganhamos");
        viewHolder.lbl_pontuacao.setText(String.valueOf(history.pts_team1) + " x " + String.valueOf(history.pts_team2));
        viewHolder.lbl_data.setText(history.date);
        viewHolder.lbl_horario.setText(history.time);

    }

    public void deleteItem(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView lbl_time_vencedor;
        public TextView lbl_pontuacao;
        public TextView lbl_data;
        public TextView lbl_horario;
        public ImageView btn_excluir;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            lbl_time_vencedor = itemView.findViewById(R.id.lbl_time_vencedor);
            lbl_pontuacao = itemView.findViewById(R.id.lbl_pontuacao);
            lbl_data = itemView.findViewById(R.id.lbl_data);
            lbl_horario = itemView.findViewById(R.id.lbl_horario);
            btn_excluir = itemView.findViewById(R.id.btn_excluir);

            // Aplica função do click no botão
            btn_excluir.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(adapter != null){
                adapter.setAdaptorPositionOnClickListener(v, getPosition());
            }
        }
    }

}
