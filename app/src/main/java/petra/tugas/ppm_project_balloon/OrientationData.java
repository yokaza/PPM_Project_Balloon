package petra.tugas.ppm_project_balloon;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class OrientationData implements SensorEventListener {
    private SensorManager manager;
    private Sensor accelerometer;
    private Sensor magnometer;
    private Sensor proximity;

    private float[] accelOutput;
    private float[] magOutput;
    //
    private boolean proxout;
    public boolean getProx(){return proxout;}

    private float[] orientations = new float[3];
    public float[] getOrientations() {
        return orientations;
    }

    private float[] startOrientation = null;
    public float[] getStartOrientation() {
        return startOrientation;
    }
    public void newGame() {
        startOrientation = null;
    }

    public OrientationData() {
        manager = (SensorManager)Constants.CURRENT_CONTEXT.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnometer = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        //
        proximity = manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    public void register() {
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(this, magnometer, SensorManager.SENSOR_DELAY_GAME);
        //
        manager.registerListener(this,proximity,SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            accelOutput = event.values;
        else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            magOutput = event.values;
        if(accelOutput != null && magOutput != null) {
            float[] R = new float[9];
            float[] I = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, accelOutput, magOutput);
            if(success) {
                SensorManager.getOrientation(R, orientations);
                if(startOrientation == null) {
                    startOrientation = new float[orientations.length];
                    System.arraycopy(orientations, 0, startOrientation, 0, orientations.length);
                }
            }
        }
        //
        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
            if(event.values[0] <proximity.getMaximumRange()){
                proxout=true;
            }else{
                proxout=false;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
