package frc.robot;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Seconds;

import edu.wpi.first.units.Current;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Time;


public final class Constants {

    public static final int linearActuatorRelayID = 0;

    public static final class JoysticksK {
        public static final int joystickPort = 0;
    }

    public static final class DriveK {
        public static final int talonFRID = 1;
        public static final int talonBRID = 2;
        public static final int talonBLID = 3;
        public static final int talonFLID = 4;

        public static final double speedAdjustment = 1;
        public static final double turnAdjustment = 0.65;

        public static final double joystickDeadband = 0.07;
        public static final double deadbandSmoothing = 4;
    }

    public static final class PneumaticsK {
        public static final int compressorLID = 1;
        public static final int compressorRID = 2;
        public static final int solenoidID = 0;
        public static final int lightRelayID = 1;
        public static final Measure<Current> maxCurrent = Amps.of(18);
        public static final Measure<Time> currentTripTime = Seconds.of(0.5);
        public static final Measure<Time> timeToFire = Seconds.of(0.75);
        public static final Measure<Time> lightFlashPeriod = Seconds.of(1);
    }

}
