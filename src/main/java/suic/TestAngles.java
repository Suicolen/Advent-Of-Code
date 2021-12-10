package suic;

import suic.util.MathUtils;

public class TestAngles {
    static int new_angle(double angle) {
        double temp_angle = ((angle + Math.PI) / (2.0 * Math.PI)) * 2047.0;
        return (int) Math.ceil(temp_angle);
    }

    public static void main(String[] args) {
        float[] smallValues = {
                1.4192234E-7f,
                -5.155359E-8f,
                1.4600505E-7f,
                -3.85001E-8f,
                1.4869062E-7f,
                -2.6283601E-8f,
                1.5018138E-7f,
                -1.5661206E-8f,
                -0.3f, 0.5f
        };

        float EPSILON = 1E-4f;

        for(float value : smallValues) {
            if(Math.abs(value) < EPSILON) {
                value = 0;
            }
            System.out.println(value);
        }

    }

}
