package br.com.jorgemussato.marcadortruco.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class History implements Serializable {

    @SerializedName("pts_time1")
    public int pts_team1;

    @SerializedName("pts_time2")
    public int pts_team2;

    @SerializedName("date")
    public String date;

    @SerializedName("time")
    public String time;

    public History(int pts_team1, int pts_team2){

        this.pts_team1 = pts_team1;
        this.pts_team2 = pts_team2;

        Date dataAtual = Calendar.getInstance().getTime();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:MM");
        this.date = formatDate.format(dataAtual);
        this.time = formatTime.format(dataAtual);


    }



}
