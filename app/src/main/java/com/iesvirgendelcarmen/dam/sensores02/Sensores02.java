package com.iesvirgendelcarmen.dam.sensores02;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Sensores02 extends AppCompatActivity implements SensorEventListener {
    private int contador;
    private double x=0, y=0, z=0, a=0, amax=0;
    private double gravedad= SensorManager.STANDARD_GRAVITY;
    private TextView tvax, tvay, tvaz, tva, tvaMax, tvG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensores02);

        tvax=(TextView)findViewById(R.id.ejeX);
        tvay=(TextView)findViewById(R.id.ejeY);
        tvaz=(TextView)findViewById(R.id.ejeZ);
        tva=(TextView)findViewById(R.id.aceleracion);
        tvaMax=(TextView)findViewById(R.id.aceleracion_max);
        tvG=(TextView)findViewById(R.id.gravedad_estandar);

        SensorManager sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor acelerometro=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this,acelerometro,SensorManager.SENSOR_DELAY_FASTEST);

        new hiloAsincrono().execute();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        x=event.values[0];
        y=event.values[1];
        z=event.values[2];
        a=Math.sqrt(x*x+y*y+z*z);
        if(a>amax){
            amax=a;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    class hiloAsincrono extends AsyncTask<Void,Void,Void>{
        public hiloAsincrono() {
            super();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            tvax.setText(""+x);
            tvay.setText(""+y);
            tvaz.setText(""+z);
            tva.setText(""+a);
            tvaMax.setText(""+amax);
            tvG.setText(""+gravedad);
            tvG.append("\n"+"CONTADOR: "+contador);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            while(true){
                try{
                    Thread.sleep(100);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                contador++;
                publishProgress();
            }

        }
    }
}
